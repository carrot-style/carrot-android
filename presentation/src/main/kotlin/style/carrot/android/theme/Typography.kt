/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [Typography.kt] created by Ji Sungbin on 22. 1. 22. 오후 11:49
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import style.carrot.android.R

private val DefaultFontFamily = FontFamily(Font(R.font.fredokaone))

val CarrotStyleTypography = Typography(defaultFontFamily = DefaultFontFamily)
