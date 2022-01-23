/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [CarrotRepositoryImpl.kt] created by Ji Sungbin on 22. 1. 23. 오후 12:21
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.data.repo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Retrofit
import style.carrot.android.data.api.GithubRepoService
import style.carrot.android.data.model.FileContentResponse
import style.carrot.android.data.model.GithubFile
import style.carrot.android.data.util.isValid
import style.carrot.android.data.util.toBase64
import style.carrot.android.data.util.toException
import style.carrot.android.data.util.toRedirectContent
import style.carrot.android.domain.model.CarrotUrl
import style.carrot.android.domain.repository.CarrotRepository
import kotlin.coroutines.resume

class CarrotRepositoryImpl(signedRetrofit: Retrofit) : CarrotRepository {
    private val api = signedRetrofit.create(GithubRepoService::class.java)

    override suspend fun loadUrls(): List<CarrotUrl> {
        // TODO
        return emptyList()
    }

    override suspend fun styling(
        path: String,
        url: String,
        update: Boolean,
        coroutineScope: CoroutineScope
    ): Unit = suspendCancellableCoroutine { continuation ->
        coroutineScope.launch {
            var sha = ""
            if (update) {
                sha = getFileContent(path = path, coroutineScope = coroutineScope).sha
                    ?: throw Exception("sha value is null.")
            }
            continuation.resume(
                updateFile(
                    path = path,
                    url = url,
                    sha = sha,
                    coroutineScope = coroutineScope
                )
            )
        }
    }

    private suspend fun getFileContent(
        path: String,
        coroutineScope: CoroutineScope
    ): FileContentResponse = suspendCancellableCoroutine { continuation ->
        coroutineScope.launch {
            val request = api.getFileContent(path = path)
            if (request.isValid()) {
                continuation.resume(request.body()!!)
            } else {
                throw request.toException()
            }
        }
    }

    private suspend fun updateFile(
        path: String,
        url: String,
        sha: String = "",
        coroutineScope: CoroutineScope
    ): Unit = suspendCancellableCoroutine { continuation ->
        coroutineScope.launch {
            val githubFile = GithubFile(content = url.toRedirectContent().toBase64(), sha = sha)
            val request = api.updateFile(path = path, githubFile = githubFile)
            if (request.isValid()) {
                continuation.resume(Unit)
            } else {
                throw request.toException()
            }
        }
    }
}
