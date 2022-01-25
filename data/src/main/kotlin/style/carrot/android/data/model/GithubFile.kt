/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [GithubFile.kt] created by Ji Sungbin on 22. 1. 23. 오후 12:23
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.data.model

import style.carrot.android.data.util.CarrotBranch
import style.carrot.android.data.util.NewStyledMessage

data class GithubFile(
    val message: String = NewStyledMessage,
    val content: String,
    val sha: String = "", // 최초 커밋시에는 빈 값
    val branch: String = CarrotBranch,
)
