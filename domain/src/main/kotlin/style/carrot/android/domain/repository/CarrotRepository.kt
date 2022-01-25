/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [CarrotRepository.kt] created by Ji Sungbin on 22. 1. 23. 오전 11:47
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.domain.repository

import style.carrot.android.domain.model.StyledUrl

interface CarrotRepository {
    /**
     * @return 만약 파일이 있다면 해당 파일의 sha값을 리턴하고, 파일이 없다면 null을 리턴함
     */
    suspend fun getStyledSha(path: String): String?

    suspend fun styling(
        path: String, // 커스텀 단축 링크 (carrot.style/${path}.html)
        url: String, // 단축될 링크
        sha: String, // 업데이트하는 값이라면 sha가 필요함
    )

    suspend fun loadMyStyledUrls(uuid: String): List<StyledUrl>

    fun addMyStyledUrl(uuid: String, styledUrl: StyledUrl)
}
