/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [MainState.kt] created by Ji Sungbin on 22. 1. 24. 오후 7:13
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.activity.main.mvi

data class MainState(
    val type: EventType = EventType.None,
    val exception: Throwable? = null,
    val sha: String? = null,
) {
    val isException get() = exception != null
    val isTakenStyle get() = sha != null
}
