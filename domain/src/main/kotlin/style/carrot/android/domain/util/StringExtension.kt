/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [StringExtension.kt] created by Ji Sungbin on 22. 1. 24. 오후 8:33
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.domain.util

fun String.checkHttpAndAutoInsert() = if (contains("://")) this else "http://$this"
