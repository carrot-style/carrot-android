/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [MainViewModel.kt] created by Ji Sungbin on 22. 1. 23. 오전 11:42
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.activity.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import style.carrot.android.domain.model.StyledUrl
import style.carrot.android.domain.usecase.AddStyledUrlUseCase
import style.carrot.android.domain.usecase.DeleteStyledUrlUseCase
import style.carrot.android.domain.usecase.GetStyledShaUseCase
import style.carrot.android.domain.usecase.LoadStyledUrlsUseCase
import style.carrot.android.domain.usecase.StylingUrlUseCase
import style.carrot.android.util.Web
import style.carrot.android.util.constant.Key
import style.carrot.android.util.extension.get
import style.carrot.android.util.extension.set
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getStyledShaUseCase: GetStyledShaUseCase,
    private val loadStyledUrlsUseCase: LoadStyledUrlsUseCase,
    private val stylingUrlUseCase: StylingUrlUseCase,
    private val deleteStyledUrlUseCase: DeleteStyledUrlUseCase,
    private val addStyledUrlUseCase: AddStyledUrlUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _exceptionEvent = MutableStateFlow<Throwable?>(null)
    val exceptionFlow = _exceptionEvent.asStateFlow()

    // https://stackoverflow.com/questions/70729578/how-to-pass-parameter-to-bottomsheet-of-jetpack-compose
    var styledUrlForUpdate: StyledUrl? = null

    /**
     * `StateFlow<T>.collectAsState` 로 받아서 처리해야 함 -> [Flow]로 작성
     */
    private val _styledUrls = MutableStateFlow(mutableListOf<StyledUrl>())
    private val styledUrlsValue get() = _styledUrls.value
    val styledUrls: Flow<List<StyledUrl>> = _styledUrls.asStateFlow()

    private val uuid by lazy { sharedPreferences[Key.Uuid]!! }

    init {
        if (sharedPreferences[Key.Uuid] == null) {
            sharedPreferences[Key.Uuid] = Random.nextInt().toString()
        }
    }

    /**
     * [StyledUrl] 리스트 firestore에서 조회
     * 요청 성공시 [StyledUrl] 리스트를 [styledUrls]에 넘겨줌
     */
    fun loadStyledUrlsWithDoneAction(doneAction: () -> Unit) = viewModelScope.launch {
        loadStyledUrlsUseCase(uuid = uuid)
            .onSuccess { styledUrls ->
                _styledUrls.emit(styledUrls.toMutableList())
                doneAction()
            }
            .onFailure { throwable ->
                throwable.emit()
            }
    }

    /**
     * 요청 상황이 2가지임
     * - `StyledCard` 에서 swipe로 수정 요청일 경우
     * - 새로운 스타일링 요청일 경우
     *
     * - 수정 요청일 때
     * 기존 파일에서 sha값을 조회해야 함 ->
     * [stylingUrl] 호출 전 미리 [getStyledSha] 함수를 호출하여 sha값 조회 ->
     * 위에서 조회한 값이 [sha] 인자로 들어감
     *
     * - 새로운 스타일링 요청일 때
     * 새로운 파일을 만들어야 함 ->
     * [sha] 인자에 `""`(공백) 들어감
     */
    fun stylingUrl(styledUrl: StyledUrl, sha: String) = viewModelScope.launch {
        stylingUrlUseCase(
            path = styledUrl.styled,
            url = styledUrl.origin,
            sha = sha
        ).onSuccess {
            // addStyledUrl(styledUrl): firestore에 등록 요청
            // 요청 성공 - return null
            // 요청 실패 - return throwable
            addStyledUrl(styledUrl)?.emit()
        }.onFailure { throwable ->
            throwable.emit()
        }
    }

    suspend fun checkAlreadyStyled(path: String): Boolean? =
        suspendCancellableCoroutine { continuation ->
            viewModelScope.launch {
                val pathUrl = "https://api.github.com/repos/carrotstyle/carrot.style/contents/$path"
                Web.parse(pathUrl)
                    .onSuccess { html ->
                        continuation.resume(!html.contains("\"message\": \"Not Found\","))
                    }
                    .onFailure { throwable ->
                        continuation.resume(null)
                        throwable.emit()
                    }
            }
        }

    /**
     * @return 요청 성공시 sha 값 리턴, 요청 실패시 null 리턴
     */
    suspend fun getStyledSha(path: String): String? = suspendCancellableCoroutine { continuation ->
        viewModelScope.launch {
            getStyledShaUseCase(path)
                .onSuccess { sha ->
                    continuation.resume(sha)
                }.onFailure { throwable ->
                    continuation.resume(null)
                    throwable.emit()
                }
        }
    }

    fun deleteStyledUrl(styledUrl: StyledUrl) = viewModelScope.launch {
        deleteStyledUrlUseCase(uuid = uuid, styledUrl = styledUrl)
            .onSuccess {
                _styledUrls.emit(styledUrlsValue.apply { remove(styledUrl) })
            }
            .onFailure { throwable ->
                throwable.emit()
            }
    }

    /**
     * [MainViewModel.stylingUrl]에서만 사용됨
     * [StyledUrl]을 firestore에 등록 요청함
     * 만약 요청 성공시 [styledUrls]에 해당 [StyledUrl]을 추가함
     *
     *  @return 요청 성공시: null
     *  요청 실패시: [Throwable]
     */
    private suspend fun addStyledUrl(styledUrl: StyledUrl): Throwable? =
        suspendCancellableCoroutine { continutation ->
            viewModelScope.launch {
                addStyledUrlUseCase(uuid = uuid, styledUrl = styledUrl)
                    .onSuccess {
                        _styledUrls.emit(styledUrlsValue.apply { add(styledUrl) })
                        continutation.resume(null)
                    }
                    .onFailure { throwable ->
                        continutation.resume(throwable)
                    }
            }
        }

    private suspend fun Throwable.emit() {
        _exceptionEvent.emit(this)
    }
}
