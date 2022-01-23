/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [FileContentResponse.kt] created by Ji Sungbin on 22. 1. 23. 오후 12:23
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class FileContentResponse(
    @field:JsonProperty("download_url")
    val downloadUrl: String? = null,

    @field:JsonProperty("sha")
    val sha: String? = null,
)
