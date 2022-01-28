/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [StylingTest.kt] created by Ji Sungbin on 22. 1. 28. 오후 1:30
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import style.carrot.android.data.repository.CarrotRepositoryImpl
import style.carrot.android.domain.repository.CarrotRepository
import style.carrot.android.domain.usecase.StylingUrlUseCase

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class StylingTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    val fakeRepository: CarrotRepository = mockk<CarrotRepositoryImpl>()
    val stylingUrlUseCase by lazy { StylingUrlUseCase(fakeRepository) }

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `엄청 긴 주소를 요청했을 때 통과해야 함`(): Unit = runBlocking {
        val veryLongAddress = "www.${"url".repeat(100)}.com"
        val request = stylingUrlUseCase(path = "testlong.html", url = veryLongAddress, sha = "")

    }
}
