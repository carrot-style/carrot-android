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
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.systemBarsPadding
import dagger.hilt.android.AndroidEntryPoint
import io.github.jisungbin.logeukes.LoggerType
import io.github.jisungbin.logeukes.logeukes
import javax.inject.Inject
import kotlinx.coroutines.launch
import style.carrot.android.BuildConfig
import style.carrot.android.R
import style.carrot.android.activity.error.ErrorActivity
import style.carrot.android.activity.main.component.CreateStyle
import style.carrot.android.activity.main.component.EmptyStyled
import style.carrot.android.activity.main.component.LazyStyledCard
import style.carrot.android.theme.CarrotStyleTheme
import style.carrot.android.theme.SystemUiController
import style.carrot.android.util.NetworkUtil
import style.carrot.android.util.constant.IntentConstant
import style.carrot.android.util.extension.collectWithLifecycle
import style.carrot.android.util.extension.toast

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isReady = false
    private val vm: MainViewModel by viewModels()
    private val systemUiController by lazy { SystemUiController(window) }

    @Inject
    lateinit var sharedPreferences: SharedPreferences

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

        println("A: $vm")
        vm.loadStyledUrlsWithDoneAction {
            isReady = true
        }
        vm.exceptionFlow.collectWithLifecycle(lifecycleOwner = this, action = ::handleException)
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
                    MainContent()
                }
            }
        }
    }

    @Composable
    private fun MainContent() {
        println("B: $vm")
        val styledUrls by vm.styledUrls.collectAsState(emptyList())
        val backgroundColor = MaterialTheme.colors.background
        val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            systemUiController.setSystemBarsColor(backgroundColor)
        }

        BackHandler(enabled = modalBottomSheetState.isVisible) {
            coroutineScope.launch {
                modalBottomSheetState.hide()
            }
        }

        fun ModalBottomSheetState.expandFull() {
            coroutineScope.launch {
                animateTo(ModalBottomSheetValue.Expanded)
            }
        }

        ModalBottomSheetLayout(
            sheetContent = {
                CreateStyle(
                    modifier = Modifier
                        .navigationBarsWithImePadding()
                        .height(450.dp)
                        .fillMaxWidth()
                        .padding(30.dp)
                )
            },
            sheetState = modalBottomSheetState,
            sheetShape = RoundedCornerShape(20.dp)
        ) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        modalBottomSheetState.expandFull()
                    }) {
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
                    Crossfade(styledUrls.isEmpty()) { isEmpty ->
                        when (isEmpty) {
                            true -> {
                                EmptyStyled()
                            }
                            else -> {
                                LazyStyledCard(
                                    styledUrls = styledUrls,
                                    expandEditStyleModalBottomSheet = { styledUrl ->
                                        vm.styledUrlForUpdate = styledUrl
                                        modalBottomSheetState.expandFull()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun handleException(exception: Throwable?) {
        if (exception != null) {
            val message = when (BuildConfig.DEBUG) {
                true -> {
                    logeukes(type = LoggerType.E) { exception }
                    exception.message.toString()
                }
                else -> getString(R.string.activity_main_toast_error)
            }
            toast(message)
        }
    }
}
