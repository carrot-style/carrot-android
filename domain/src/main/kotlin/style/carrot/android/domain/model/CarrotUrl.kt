/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [CarrotUrl.kt] created by Ji Sungbin on 22. 1. 23. 오전 11:46
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.domain.model

/**
 * @param styled 단축 된 링크
 * @param origin 단축 전 링크
 * @param memo 링크 메모
 */
data class CarrotUrl(val styled: String, val origin: String, val memo: String)
