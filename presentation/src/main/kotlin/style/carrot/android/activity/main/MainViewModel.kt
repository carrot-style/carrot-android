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
import style.carrot.android.activity.main.mvi.EventType
import style.carrot.android.activity.main.mvi.MainState
import style.carrot.android.domain.model.StyledUrl
import style.carrot.android.domain.usecase.AddMyStyledUrlUseCase
import style.carrot.android.domain.usecase.GetStyledShaUseCase
import style.carrot.android.domain.usecase.LoadMyStyledUrlsUseCase
import style.carrot.android.domain.usecase.StylingCarrotUrlUseCase
import style.carrot.android.util.constant.Key
import style.carrot.android.util.extension.get
import style.carrot.android.util.extension.set
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getStyledShaUseCase: GetStyledShaUseCase,
    private val loadMyStyledUrlsUseCase: LoadMyStyledUrlsUseCase,
    private val stylingCarrotUrlUseCase: StylingCarrotUrlUseCase,
    private val addMyStyledUrlUseCase: AddMyStyledUrlUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _event = MutableStateFlow(MainState())
    private val eventValue get() = _event.value
    val event = _event.asStateFlow()

    private val _styledUrls = MutableStateFlow(mutableListOf<StyledUrl>())
    private val styledUrlsValue get() = _styledUrls.value
    val styledUrls: Flow<List<StyledUrl>> = _styledUrls.asStateFlow()

    private val uuid by lazy { sharedPreferences[Key.Uuid]!! }

    init {
        if (sharedPreferences[Key.Uuid] == null) {
            sharedPreferences[Key.Uuid] = UUID.randomUUID().toString()
        }
    }

    fun loadCarrotUrls() = viewModelScope.launch {
        var state = eventValue.copy(type = EventType.LoadStyledUrls, exception = null)
        loadMyStyledUrlsUseCase(uuid = uuid)
            .onSuccess { carrotUrls ->
                _styledUrls.emit(carrotUrls.toMutableList())
            }
            .onFailure { throwable ->
                state = state.copy(exception = throwable)
            }
        _event.emit(state)
    }

    fun styling(
        styledUrl: StyledUrl,
        sha: String = ""
    ) = viewModelScope.launch {
        var state = eventValue.copy(type = EventType.Styled, exception = null)
        stylingCarrotUrlUseCase(
            path = styledUrl.styled,
            url = styledUrl.origin,
            sha = sha
        ).onSuccess {
            addMyStyledUrl(styledUrl)
        }.onFailure { throwable ->
            state = state.copy(exception = throwable)
        }
        _event.emit(state)
    }

    fun getStyeldSha(path: String) = viewModelScope.launch {
        var state = eventValue.copy(type = EventType.CheckStyled, exception = null)
        getStyledShaUseCase(path)
            .onSuccess { nullableSha ->
                state = state.copy(sha = nullableSha)
            }.onFailure { throwable ->
                state = state.copy(exception = throwable)
            }
        _event.emit(state)
    }

    private suspend fun addMyStyledUrl(styledUrl: StyledUrl) {
        addMyStyledUrlUseCase(uuid = uuid, carrotUrl = styledUrl)
            .onSuccess {
                _styledUrls.emit()
            }
            .onFailure { throwable ->
                _event.emit(eventValue.copy(exception = throwable))
            }
    }
}
