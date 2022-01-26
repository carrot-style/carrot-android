/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [LazyStyledCard.kt] created by Ji Sungbin on 22. 1. 25. 오후 4:52
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.activity.main.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.jisungbin.logeukes.logeukes
import style.carrot.android.R
import style.carrot.android.activity.main.MainViewModel
import style.carrot.android.util.extension.toast

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyStyledCard(uuid: String) {
    val vm: MainViewModel = viewModel()
    val styledUrlsInstance = vm.styledUrls.collectAsState(emptyList())
    val styledUrls by styledUrlsInstance
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        vm.styledUrls.collect {
            logeukes { "vm.styledUrls.collect: $it" }
        }
    }

    logeukes { "LazyStyledCard ViewModel instance: $vm" }
    logeukes { "LazyStyledCard styledUrls instance: $styledUrlsInstance" }
    logeukes { "LazyStyledCard styledUrls value: $styledUrls" }

    LazyColumn( // TODO: fading edge
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(items = styledUrls, key = { it.styled }) { styledUrl ->
            StyledCard(
                modifier = Modifier.animateItemPlacement(),
                styledUrl = styledUrl,
                onDismissedToDelete = { _styledUrl ->
                    toast(context) {
                        getString(R.string.activity_main_component_createstyle_toast_deleting)
                    }
                    vm.deleteStyledUrl(styledUrl = _styledUrl, uuid = uuid)
                }
            )
        }
    }
}
