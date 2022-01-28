/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [StylingUrlTest.kt] created by Ji Sungbin on 22. 1. 28. 오후 9:07
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import style.carrot.android.data.repository.CarrotRepositoryImpl
import style.carrot.android.di.module.RetrofitModule
import style.carrot.android.domain.repository.CarrotRepository

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
@RunWith(MockitoJUnitRunner::class)
class StylingUrlTest {

    private val repository: CarrotRepository =
        CarrotRepositoryImpl(RetrofitModule.provideSignedRetrofit())

    @Test
    fun `엄청 긴 URL을 단축 요청 했을때 성공해야 함`() = runTest {
        val path = "test.html"
        val url = "https://${"URL".repeat(100)}.com"
        val request = repository.stylingUrl(path = path, url = url, sha = "")
        `when`(request).thenThrow(Exception())
    }
}
