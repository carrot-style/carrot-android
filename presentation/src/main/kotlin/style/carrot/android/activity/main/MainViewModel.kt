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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import style.carrot.android.di.qualifier.IoDispatcher
import style.carrot.android.domain.model.CarrotUrl
import style.carrot.android.domain.usecase.CheckStyledUseCase
import style.carrot.android.domain.usecase.LoadCarrotUrlsUseCase
import style.carrot.android.domain.usecase.StylingCarrotUrlUseCase
import style.carrot.android.util.constant.Key
import style.carrot.android.util.extension.get
import style.carrot.android.util.extension.set
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkStyledUseCase: CheckStyledUseCase,
    private val loadCarrotUrlsUseCase: LoadCarrotUrlsUseCase,
    private val stylingCarrotUrlUseCase: StylingCarrotUrlUseCase,
    private val sharedPreferences: SharedPreferences,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    init {
        if (sharedPreferences[Key.Uuid] == null) {
            sharedPreferences[Key.Uuid] = UUID.randomUUID().toString()
        }
    }

    // TODO: apply MVI
    fun loadUrlsWithDoneAction(action: (Result<List<CarrotUrl>>) -> Unit) =
        viewModelScope.launch(coroutineDispatcher) {
            action(loadCarrotUrlsUseCase())
        }

    fun styling(
        path: String,
        url: String,
        sha: String = "" // 업데이트에만 sha 값이 필요함
    ) = viewModelScope.launch(coroutineDispatcher) {
        stylingCarrotUrlUseCase(
            path = path,
            url = url,
            sha = sha
        ).onSuccess {
        }.onFailure {
        }
    }

    fun checkStyled(path: String) = viewModelScope.launch(coroutineDispatcher) {
        checkStyledUseCase(path)
            .onSuccess {
            }.onFailure {
            }
    }
}
