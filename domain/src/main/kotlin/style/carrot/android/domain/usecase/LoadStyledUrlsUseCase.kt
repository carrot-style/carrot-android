/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [LoadCarrotUrlsUseCase.kt] created by Ji Sungbin on 22. 1. 23. 오전 11:48
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.domain.usecase

import style.carrot.android.domain.repository.CarrotRepository

class LoadStyledUrlsUseCase(private val repository: CarrotRepository) {
    suspend operator fun invoke(uuid: String) = runCatching {
        repository.loadStyledUrls(uuid)
    }
}
