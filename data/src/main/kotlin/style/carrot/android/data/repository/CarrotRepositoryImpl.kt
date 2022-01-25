/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [CarrotRepositoryImpl.kt] created by Ji Sungbin on 22. 1. 23. 오후 12:21
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Retrofit
import style.carrot.android.data.api.GithubRepoService
import style.carrot.android.data.model.GithubFile
import style.carrot.android.data.util.DeleteStyledMessage
import style.carrot.android.data.util.extension.isValid
import style.carrot.android.data.util.extension.toBase64
import style.carrot.android.data.util.extension.toException
import style.carrot.android.data.util.extension.toObjectNonNull
import style.carrot.android.data.util.extension.toRedirectContent
import style.carrot.android.domain.model.FileSha
import style.carrot.android.domain.model.StyledUrl
import style.carrot.android.domain.repository.CarrotRepository
import kotlin.coroutines.resume

class CarrotRepositoryImpl(signedRetrofit: Retrofit) : CarrotRepository {

    private val firestore by lazy { Firebase.firestore }
    private val api = signedRetrofit.create(GithubRepoService::class.java)

    override suspend fun loadStyledUrls(uuid: String): List<StyledUrl> =
        suspendCancellableCoroutine { continuation ->
            firestore.collection(uuid)
                .get()
                .addOnSuccessListener { result ->
                    continuation.resume(result.documents.map(DocumentSnapshot::toObjectNonNull))
                }
                .addOnFailureListener { exception ->
                    throw exception
                }
        }

    override suspend fun stylingUrl(path: String, url: String, sha: String) {
        val githubFile = GithubFile(
            content = url.toRedirectContent().toBase64(),
            sha = sha
        )
        val request = api.updateFile(
            path = path,
            githubFile = githubFile
        )
        if (!request.isValid()) {
            throw request.toException()
        }
    }

    override suspend fun getStyledSha(path: String): FileSha {
        val request = api.getFileContent(path = path)
        if (request.isValid()) {
            return FileSha(request.body()!!.sha)
        } else {
            throw request.toException()
        }
    }

    override suspend fun deleteStyledUrl(uuid: String, styledUrl: StyledUrl) {
        val sha = getStyledSha(path = styledUrl.styled).sha!!
        val githubFile = GithubFile(message = DeleteStyledMessage, sha = sha)
        val request = api.deleteFile(
            path = styledUrl.styled,
            githubFile = githubFile
        )
        if (request.isValid()) { // 파일 삭제 성공
            firestore.collection(uuid)
                .document(styledUrl.styled)
                .delete()
                .addOnFailureListener { exception ->
                    throw exception
                }
        } else {
            throw request.toException()
        }
    }

    override fun addStyledUrl(uuid: String, styledUrl: StyledUrl) {
        firestore.collection(uuid)
            .document(styledUrl.styled)
            .set(styledUrl)
            .addOnFailureListener { exception ->
                throw exception
            }
    }
}
