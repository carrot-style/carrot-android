/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [Flow.kt] created by Ji Sungbin on 22. 1. 25. 오후 4:04
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.util.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow

fun <T> Flow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    action: suspend (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launchWhenCreated {
        flowWithLifecycle(lifecycleOwner.lifecycle).collect { value ->
            action(value)
        }
    }
}
