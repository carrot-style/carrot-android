/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 1. 22. 오후 11:52
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import style.carrot.android.theme.CarrotStyleTheme
import style.carrot.android.theme.SystemUiController

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val systemUiController by lazy { SystemUiController(window) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            CarrotStyleTheme {
                ProvideWindowInsets {
                    val containerColor = MaterialTheme.colorScheme.background

                    SideEffect {
                        systemUiController.setSystemBarsColor(containerColor)
                    }

                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .systemBarsPadding(),
                        floatingActionButton = {
                            FloatingActionButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_round_add_24),
                                    contentDescription = null
                                )
                            }
                        },
                        containerColor = containerColor
                    ) { padding ->
                        Column(
                            modifier = Modifier
                                .padding(padding)
                                .padding(
                                    top = 60.dp,
                                    bottom = 16.dp,
                                    start = 16.dp,
                                    end = 16.dp
                                )
                        ) {
                            Text(
                                text = stringResource(R.string.app_name),
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
