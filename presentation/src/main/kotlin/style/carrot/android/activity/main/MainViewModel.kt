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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import style.carrot.android.di.qualifier.IoDispatcher
import style.carrot.android.domain.model.CarrotUrl
import style.carrot.android.domain.usecase.LoadCarrotUrlsUseCase
import style.carrot.android.domain.usecase.StylingCarrotUrlUseCase
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkStyledUseCase: StylingCarrotUrlUseCase,
    private val loadCarrotUrlsUseCase: LoadCarrotUrlsUseCase,
    private val stylingCarrotUrlUseCase: StylingCarrotUrlUseCase,
    private val sharedPreferences: SharedPreferences,
    @IoDispatcher private val coroutineScope: CoroutineDispatcher
) : ViewModel() {
    fun loadUrlsWithDoneAction(action: (Result<List<CarrotUrl>>) -> Unit) = viewModelScope.launch {
        action(loadCarrotUrlsUseCase())
    }

    fun styling(
        path: String,
        url: String,
        sha: String = "", // 업데이트에만 sha 값이 필요함
        coroutineScope: CoroutineDispatcher = this.coroutineScope
    ) = viewModelScope.launch {
        stylingCarrotUrlUseCase(
            path = path,
            url = url,
            sha = sha,
            coroutineScope = coroutineScope
        )
    }
}
