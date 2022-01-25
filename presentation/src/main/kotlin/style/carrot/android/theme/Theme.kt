/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [Theme.kt] created by Ji Sungbin on 22. 1. 22. 오후 11:48
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CarrotStyleTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors,
        typography = CarrotStyleTypography,
        content = content
    )
}
