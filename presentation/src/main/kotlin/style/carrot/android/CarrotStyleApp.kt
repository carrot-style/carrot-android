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
import dagger.hilt.android.HiltAndroidApp
import io.github.jisungbin.erratum.Erratum
import io.github.jisungbin.logeukes.Logeukes

@HiltAndroidApp
class CarrotStyleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Erratum.setup(this)
        if (BuildConfig.DEBUG) {
            Logeukes.setup()
        }
    }
}
