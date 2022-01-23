/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [ExceptionActivity.kt] created by Ji Sungbin on 22. 1. 23. 오후 2:29
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.activity.exception

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.jisungbin.erratum.ErratumExceptionActivity
import style.carrot.android.R
import style.carrot.android.theme.CarrotStyleTheme

class ExceptionActivity : ErratumExceptionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CarrotStyleTheme {
            }
        }
    }

    @Preview
    @Composable
    fun Exception() {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))

        ConstraintLayout() {
            LottieAnimation(
                modifier = Modifier.size(300.dp),
                iterations = LottieConstants.IterateForever,
                composition = composition,
            )
            Text(
                text = "깃메봇이가 예상치 못한 ${content}에 맞았어요 \uD83D\uDE22\n\n$exceptionString",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Button(onClick = {
                finish()
                openLastActivity()
            }) {
                Text(
                    text = stringResource(
                        R.string.activity_exception_button_retry_with_avoid,
                        content
                    )
                )
            }
        }
    }
}
