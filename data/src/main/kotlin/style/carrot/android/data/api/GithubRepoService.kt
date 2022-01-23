/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [GithubRepoService.kt] created by Ji Sungbin on 22. 1. 23. 오후 12:20
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.data.api

import style.carrot.android.data.model.repo.FileContentResponse
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFile
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import style.carrot.android.data.model.GithubFile
import style.carrot.android.data.util.CarrotStyle
import style.carrot.android.data.util.CarrotStyleRepo

interface GithubRepoService {
    @PUT("/repos/{owner}/{repoName}/contents/{path}")
    suspend fun updateFile(
        @Path("owner") owner: String = CarrotStyle,
        @Path("repoName") repoName: String = CarrotStyleRepo,
        @Path("path") path: String,
        @Body githubFile: GithubFile,
    ): Response<ResponseBody>

    @GET("/repos/{owner}/{repoName}/contents/{path}")
    suspend fun getFileContent(
        @Path("owner") owner: String = CarrotStyle,
        @Path("repoName") repoName: String = CarrotStyleRepo,
        @Path("path") path: String,
        @Query("ref") branch: String,
    ): Response<FileContentResponse>
}
