/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [DeleteStyledUrlUsecase.kt] created by Ji Sungbin on 22. 1. 25. 오후 1:50
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.domain.usecase

import style.carrot.android.domain.model.StyledUrl
import style.carrot.android.domain.repository.CarrotRepository

class DeleteStyledUrlUseCase(private val repository: CarrotRepository) {
    suspend operator fun invoke(uuid: String, styledUrl: StyledUrl) = runCatching {
        repository.deleteStyledUrl(uuid = uuid, styledUrl = styledUrl)
    }
}
