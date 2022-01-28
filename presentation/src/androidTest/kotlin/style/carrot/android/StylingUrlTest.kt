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
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import style.carrot.android.data.repository.CarrotRepositoryImpl
import style.carrot.android.domain.repository.CarrotRepository

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
@RunWith(MockitoJUnitRunner::class)
class StylingUrlTest {

    private lateinit var repository: CarrotRepository

    @Before
    fun mocking() {
        repository = mock(CarrotRepositoryImpl::class.java)
    }

    @Test
    fun androidTest_stylingRequest_return() = runTest {
        val path = "androidTest.html"
        val url = "https://${"URL".repeat(100)}.com"
        `when`(repository.stylingUrl(path = path, url = url, sha = "")).thenThrow(Exception())
    }
}
