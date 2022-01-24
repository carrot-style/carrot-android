/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [AddMyStyledUrlUseCase.kt] created by Ji Sungbin on 22. 1. 24. 오후 8:35
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.domain.usecase

import style.carrot.android.domain.model.CarrotUrl
import style.carrot.android.domain.repository.CarrotRepository
import style.carrot.android.domain.util.checkHttpAndAutoInsert

class AddMyStyledUrlUseCase(private val repository: CarrotRepository) {
    operator fun invoke(uuid: String, carrotUrl: CarrotUrl) = runCatching {
        repository.addMyStyledUrl(
            uuid = uuid,
            carrotUrl = carrotUrl.copy(
                styled = carrotUrl.styled.checkHttpAndAutoInsert(),
                origin = carrotUrl.origin.checkHttpAndAutoInsert()
            )
        )
    }
}
