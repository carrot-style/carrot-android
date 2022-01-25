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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import style.carrot.android.activity.main.MainViewModel
import style.carrot.android.domain.model.StyledUrl

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyStyledCard(
    styledUrls: List<StyledUrl>,
    expandEditStyleModalBottomSheet: (StyledUrl) -> Unit
) {
    val vm: MainViewModel = viewModel()

    LazyColumn( // TODO: fading edge
        modifier = Modifier.padding(top = 16.dp),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(items = styledUrls, key = { it.styled }) { carrotUrl ->
            StyledCard(
                modifier = Modifier.animateItemPlacement(),
                styledUrl = carrotUrl,
                onEditClick = { styledUrl ->
                    expandEditStyleModalBottomSheet(styledUrl)
                },
                onDeleteClick = { styledUrl ->
                    vm.deleteStyledUrl(styledUrl)
                }
            )
        }
    }
}
