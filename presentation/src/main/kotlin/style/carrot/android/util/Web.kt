/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [Web.kt] created by Ji Sungbin on 22. 1. 26. 오후 12:40
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.util

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import style.carrot.android.R
import style.carrot.android.util.extension.toast

object Web {
    const val TermsOfServiceUrl =
        "https://github.com/carrot-style/terms-of-service/blob/main/README.md"

    suspend fun parse(url: String) = runCatching {
        withContext(Dispatchers.IO) {
            Jsoup.connect(url).ignoreContentType(true).ignoreHttpErrors(true).get().wholeText()
        }
    }

    fun open(context: Context, url: String) {
        try {
            val builder = CustomTabsIntent.Builder()
            builder.build()
                .intent
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            val customTabIntent = builder.build()
            customTabIntent.launchUrl(context, url.toUri())
        } catch (exception: Exception) {
            exception.printStackTrace()
            toast(context, context.getString(R.string.web_toast_non_install_browser))
        }
    }
}
