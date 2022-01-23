/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [CarrotRepository.kt] created by Ji Sungbin on 22. 1. 23. 오전 11:47
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.domain.repository

import kotlinx.coroutines.CoroutineScope
import style.carrot.android.domain.model.CarrotUrl

interface CarrotRepository {
    suspend fun styling(update: Boolean = false, coroutineScope: CoroutineScope)
    suspend fun loadUrls(): List<CarrotUrl>
}
