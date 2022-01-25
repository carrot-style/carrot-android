/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [toast.kt] created by Ji Sungbin on 22. 1. 25. 오후 4:13
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.util.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Activity.toast(message: String) {
    toast(this, message)
}

fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
