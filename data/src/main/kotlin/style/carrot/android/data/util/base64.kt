/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [base64.kt] created by Ji Sungbin on 22. 1. 23. 오후 12:40
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.data.util

import android.util.Base64

fun String.toBase64() = Base64.encodeToString(toByteArray(), Base64.NO_WRAP)!!
