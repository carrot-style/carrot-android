/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [CarrotRepositoryImpl.kt] created by Ji Sungbin on 22. 1. 23. 오후 12:21
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.data.repo

import io.github.jisungbin.gitmessengerbot.common.constant.GithubConstant
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.extension.toModel
import style.carrot.android.data.util.isValid
import style.carrot.android.data.util.toFailResult
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFile
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFileContent
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubRepo
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Retrofit
import style.carrot.android.data.api.GithubRepoService
import style.carrot.android.data.mapper.toDomain
import style.carrot.android.domain.repository.CarrotRepository
import kotlin.coroutines.resume

class CarrotRepositoryImpl(retrofit: Retrofit) : CarrotRepository {
    private val api = retrofit.create(GithubRepoService::class.java)
    private val githubData: GithubData = Storage.read(GithubConstant.DataPath, null)?.toModel()
        ?: /*throw DataGithubException("GithubConfig.DataPath 데이터가 null 이에요.")*/ GithubData() // TODO: null 대응 (ScopedStorage 고려해서)

    override suspend fun getFileContent(
        repoName: String,
        path: String,
        branch: String,
        coroutineScope: CoroutineScope
    ): Result<GithubFileContent> = suspendCancellableCoroutine { continuation ->
        coroutineScope.launch {
            try {
                val request = api.getFileContent(githubData.userName, repoName, path, branch)
                continuation.resume(
                    if (request.isValid()) {
                        Result.success(request.body()!!.toDomain())
                    } else {
                        request.toFailResult("getFileContent")
                    }
                )
            } catch (exception: Exception) {
                continuation.resume(Result.failure(exception))
            }
        }
    }

    override suspend fun createRepo(
        githubRepo: GithubRepo,
        coroutineScope: CoroutineScope
    ): Result<Unit> = suspendCancellableCoroutine { continuation ->
        coroutineScope.launch {
            try {
                val request = api.createRepo(githubRepo)
                continuation.resume(
                    if (request.isValid()) {
                        Result.success(Unit)
                    } else {
                        request.toFailResult("createRepo")
                    }
                )
            } catch (exception: Exception) {
                continuation.resume(Result.failure(exception))
            }
        }
    }

    override suspend fun updateFile(
        repoName: String,
        path: String,
        githubFile: GithubFile,
        coroutineScope: CoroutineScope
    ): Result<Unit> = suspendCancellableCoroutine { continuation ->
        coroutineScope.launch {
            try {
                val request = api.updateFile(
                    owner = githubData.userName,
                    repoName = repoName,
                    path = path,
                    githubFile = githubFile
                )
                continuation.resume(
                    if (request.isValid()) {
                        Result.success(Unit)
                    } else {
                        request.toFailResult("updateFile")
                    }
                )
            } catch (exception: Exception) {
                continuation.resume(Result.failure(exception))
            }
        }
    }
}
