/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [ExceptionActivity.kt] created by Ji Sungbin on 22. 1. 23. 오후 2:29
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.activity.error

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.jisungbin.erratum.ErratumExceptionActivity
import style.carrot.android.BuildConfig
import style.carrot.android.R
import style.carrot.android.theme.CarrotStyleTheme
import style.carrot.android.util.constant.IntentConstant

class ErrorActivity : ErratumExceptionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CarrotStyleTheme {
                Exception()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Exception() {
        val message: String
        val lottieRaw: Int
        when (intent.getStringExtra(IntentConstant.Error)) {
            IntentConstant.NoInternet -> {
                message = stringResource(R.string.activity_error_internet)
                lottieRaw = R.raw.no_internet
            }
            else -> {
                lottieRaw = R.raw.rabbit
                message = when (BuildConfig.DEBUG) {
                    true -> exceptionString!!
                    else -> stringResource(R.string.activity_error_exception)
                }
            }
        }
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRaw))

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (content, footer) = createRefs()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                        bottom.linkTo(footer.top)
                        height = Dimension.fillToConstraints
                    },
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.activity_error_oops),
                    style = MaterialTheme.typography.displaySmall
                )
                Text(text = message)
                Button(onClick = { openLastActivity() }) {
                    Text(text = stringResource(R.string.activity_error_btn_retry))
                }
            }

            LottieAnimation(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(footer) {
                        height = Dimension.wrapContent
                        bottom.linkTo(parent.bottom)
                    },
                iterations = LottieConstants.IterateForever,
                composition = composition,
            )
        }
    }
}