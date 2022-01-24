/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [MainState.kt] created by Ji Sungbin on 22. 1. 24. 오후 7:13
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.activity.main.mvi

import style.carrot.android.domain.model.CarrotUrl
import style.carrot.android.mvi.BaseMviState

data class MainState(
    override val loaded: Boolean = false,
    override val exception: Exception? = null,
    val carrotUrls: List<CarrotUrl> = emptyList()
) : BaseMviState
