/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [String.kt] created by Ji Sungbin on 22. 1. 24. 오후 8:33
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.util.extension

fun String.checkHttpAndAutoInsert() = if (contains("://")) this else "http://$this"

fun String.replaceLast(from: String, to: String): String {
    val lastIndex = lastIndexOf(from)
    if (lastIndex < 0) return this
    val tail = substring(lastIndex).replaceFirst(from.toRegex(), to)
    return substring(0, lastIndex) + tail
}
