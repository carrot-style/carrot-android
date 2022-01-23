/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [CarrotStyleApp.kt] created by Ji Sungbin on 22. 1. 23. 오전 11:42
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android

import android.app.Application
import android.content.Intent
import dagger.hilt.android.HiltAndroidApp
import io.github.jisungbin.erratum.Erratum
import io.github.jisungbin.erratum.ErratumExceptionActivity
import io.github.jisungbin.logeukes.Logeukes
import style.carrot.android.activity.error.ErrorActivity
import style.carrot.android.util.constant.IntentConstant

@HiltAndroidApp
class CarrotStyleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Erratum.setup(
            application = this,
            registerExceptionActivityIntent = { _, throwable, lastActivity ->
                Intent(lastActivity, ErrorActivity::class.java).apply {
                    putExtra(ErratumExceptionActivity.EXTRA_EXCEPTION_STRING, throwable.toString())
                    putExtra(
                        ErratumExceptionActivity.EXTRA_LAST_ACTIVITY_INTENT,
                        lastActivity.intent
                    )
                    putExtra(IntentConstant.Error, IntentConstant.Exception)
                }
            }
        )
        if (BuildConfig.DEBUG) {
            Logeukes.setup()
        }
    }
}
