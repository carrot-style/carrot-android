/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [StylingUrlTest.kt] created by Ji Sungbin on 22. 1. 28. 오후 9:07
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import style.carrot.android.data.repository.CarrotRepositoryImpl
import style.carrot.android.domain.repository.CarrotRepository

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
@RunWith(MockitoJUnitRunner::class)
class StylingUrlTest {

    /*private val repository: CarrotRepository =
        CarrotRepositoryImpl(RetrofitModule.provideSignedRetrofit())*/

    private lateinit var repository: CarrotRepository

    @Before
    fun mocking() {
        repository = Mockito.mock(CarrotRepositoryImpl::class.java)
    }

    @Test
    fun test_stylingRequest_return() = runTest {
        // println(Base64.getEncoder().withoutPadding().encodeToString("HI".toByteArray()))
        val path = "asdasdasd.html"
        val url = "https://${"URL".repeat(100)}.com"
        Mockito.lenient().`when`(repository.stylingUrl(path = path, url = url, sha = ""))
            .thenAnswer {
                { throw Exception() }
            }
    }
}
