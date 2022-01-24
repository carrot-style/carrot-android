/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [String.kt] created by Ji Sungbin on 22. 1. 24. 오후 8:38
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.data.util.extension

import android.util.Base64

fun String.toBase64() = Base64.encodeToString(toByteArray(), Base64.NO_WRAP)!!

fun String.toRedirectContent() =
    """<script type="text/javascript"> location.href = "$this"; </script>"""
