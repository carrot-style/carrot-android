/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [StylingCarrotUrlUseCase.kt] created by Ji Sungbin on 22. 1. 23. 오후 1:14
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.domain.usecase

import kotlinx.coroutines.CoroutineScope
import style.carrot.android.domain.repository.CarrotRepository

class StylingCarrotUrlUseCase(private val repository: CarrotRepository) {
    suspend operator fun invoke(
        path: String,
        url: String,
        sha: String,
        coroutineScope: CoroutineScope
    ) = runCatching {
        repository.styling(path = path, url = url, sha = sha, coroutineScope = coroutineScope)
    }
}
