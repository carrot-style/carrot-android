/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [MainViewModel.kt] created by Ji Sungbin on 22. 1. 23. 오전 11:42
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import style.carrot.android.domain.model.CarrotUrl
import style.carrot.android.domain.usecase.LoadCarrotUrlsUseCase
import style.carrot.android.domain.usecase.StylingCarrotUrlUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadCarrotUrlsUseCase: LoadCarrotUrlsUseCase,
    private val stylingCarrotUrlUseCase: StylingCarrotUrlUseCase
) : ViewModel() {
    fun loadUrlsWithDoneAction(action: (Result<List<CarrotUrl>>) -> Unit) = viewModelScope.launch {
        action(loadCarrotUrlsUseCase())
    }

    fun styling(
        path: String,
        url: String,
        update: Boolean = false,
        coroutineScope: CoroutineScope = viewModelScope
    ) = viewModelScope.launch {
        stylingCarrotUrlUseCase(
            path = path,
            url = url,
            update = update,
            coroutineScope = coroutineScope
        )
    }
}
