/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [CheckStyledUseCase.kt] created by Ji Sungbin on 22. 1. 24. 오후 5:20
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.domain.usecase

import style.carrot.android.domain.repository.CarrotRepository

class CheckStyledUseCase(private val repository: CarrotRepository) {
    suspend operator fun invoke(path: String) = runCatching {
        repository.checkStyled(path = path)
    }
}
