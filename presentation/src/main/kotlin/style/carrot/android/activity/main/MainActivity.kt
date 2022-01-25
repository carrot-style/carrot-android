/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 1. 22. 오후 11:52
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.activity.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import dagger.hilt.android.AndroidEntryPoint
import style.carrot.android.R
import style.carrot.android.activity.error.ErrorActivity
import style.carrot.android.domain.model.CarrotUrl
import style.carrot.android.theme.CarrotStyleTheme
import style.carrot.android.theme.SystemUiController
import style.carrot.android.ui.StyledCard
import style.carrot.android.util.NetworkUtil
import style.carrot.android.util.constant.IntentConstant

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isReady = true
    private val vm: MainViewModel by viewModels()
    private val systemUiController by lazy { SystemUiController(window) }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        if (!NetworkUtil.isNetworkAvailable(applicationContext)) {
            finish()
            startActivity(
                Intent(this, ErrorActivity::class.java).apply {
                    putExtra(IntentConstant.Error, IntentConstant.NoInternet)
                }
            )
            return
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                splashScreenView.animate().run {
                    alpha(0f)
                    scaleX(0f)
                    scaleY(0f)
                    interpolator = AnticipateInterpolator()
                    duration = 200L
                    withEndAction { splashScreenView.remove() }
                    withLayer()
                    start()
                }
            }
        }

        setContent {
            val localView = LocalView.current

            localView.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        return if (isReady) {
                            localView.viewTreeObserver.removeOnPreDrawListener(this)
                            true
                        } else {
                            false
                        }
                    }
                }
            )

            CarrotStyleTheme {
                ProvideWindowInsets {
                    val backgroundColor = MaterialTheme.colors.background

                    SideEffect {
                        systemUiController.setSystemBarsColor(backgroundColor)
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
                        backgroundColor = backgroundColor
                    ) { padding ->
                        Column(
                            modifier = Modifier
                                .padding(padding)
                                .padding(
                                    top = 60.dp,
                                    start = 16.dp,
                                    end = 16.dp
                                )
                        ) {
                            Text(
                                text = stringResource(R.string.app_name),
                                style = MaterialTheme.typography.h3,
                            )
                            LazyColumn( // TODO: fading edge
                                modifier = Modifier
                                    .padding(top = 16.dp),
                                contentPadding = PaddingValues(bottom = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(15.dp)
                            ) {
                                items(
                                    List(20) {
                                        CarrotUrl(
                                            "carrot.style/test",
                                            "BBB",
                                            "This is my Awesome carro-styled url."
                                        )
                                    }
                                ) { carrotUrl ->
                                    StyledCard(
                                        modifier = Modifier.animateItemPlacement(),
                                        carrotUrl = carrotUrl,
                                        onEditClick = {},
                                        onDeleteClick = {}
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
