/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [CarrotRepositoryImpl.kt] created by Ji Sungbin on 22. 1. 23. 오후 12:21
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.data.repository

import retrofit2.Retrofit
import style.carrot.android.data.api.GithubRepoService
import style.carrot.android.data.model.GithubFile
import style.carrot.android.data.util.checkHttpAndAutoInsert
import style.carrot.android.data.util.isValid
import style.carrot.android.data.util.toBase64
import style.carrot.android.data.util.toException
import style.carrot.android.data.util.toRedirectContent
import style.carrot.android.domain.model.CarrotUrl
import style.carrot.android.domain.repository.CarrotRepository

class CarrotRepositoryImpl(signedRetrofit: Retrofit) : CarrotRepository {
    private val api = signedRetrofit.create(GithubRepoService::class.java)

    override suspend fun loadUrls(): List<CarrotUrl> {
        // TODO
        return emptyList()
    }

    override suspend fun styling(path: String, url: String, sha: String) {
        val githubFile = GithubFile(
            content = url.checkHttpAndAutoInsert().toRedirectContent().toBase64(),
            sha = sha
        )
        val request = api.updateFile(
            path = path.checkHttpAndAutoInsert(),
            githubFile = githubFile
        )
        if (!request.isValid()) {
            throw request.toException()
        }
    }

    override suspend fun checkStyled(path: String): String? {
        val request = api.getFileContent(path = path)
        if (request.isValid()) {
            return request.body()!!.sha
        } else {
            throw request.toException()
        }
    }
}
