/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [CarrotUrl.kt] created by Ji Sungbin on 22. 1. 23. 오전 11:46
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.domain.model

// styled: 단축된 링크
// origin: 단축하기 전 링크
data class CarrotUrl(val styled: String, val origin: String)
