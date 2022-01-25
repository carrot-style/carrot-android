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
import style.carrot.android.data.util.extension.isValid
import style.carrot.android.data.util.extension.toBase64
import style.carrot.android.data.util.extension.toException
import style.carrot.android.data.util.extension.toObjectNonNull
import style.carrot.android.data.util.extension.toRedirectContent
import style.carrot.android.domain.model.StyledUrl
import style.carrot.android.domain.repository.CarrotRepository
import kotlin.coroutines.resume

class CarrotRepositoryImpl(signedRetrofit: Retrofit) : CarrotRepository {

    private val firestore by lazy { Firebase.firestore }
    private val api = signedRetrofit.create(GithubRepoService::class.java)

    override suspend fun loadMyStyledUrls(uuid: String): List<StyledUrl> =
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

    override suspend fun styling(path: String, url: String, sha: String) {
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

    override suspend fun getStyledSha(path: String): String? {
        val request = api.getFileContent(path = path)
        if (request.isValid()) {
            return request.body()!!.sha
        } else {
            throw request.toException()
        }
    }

    override fun addMyStyledUrl(uuid: String, styledUrl: StyledUrl) {
        firestore.collection(uuid)
            .document(styledUrl.styled)
            .set(styledUrl)
            .addOnFailureListener { exception ->
                throw exception
            }
    }
}
