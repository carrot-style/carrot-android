/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [StyledCard.kt] created by Ji Sungbin on 22. 1. 23. 오후 4:09
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.activity.main.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import style.carrot.android.R
import style.carrot.android.domain.model.StyledUrl
import style.carrot.android.theme.CarrotStyleTheme
import style.carrot.android.util.Util
import style.carrot.android.util.extension.replaceLast
import style.carrot.android.util.extension.toast

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StyledCard(
    modifier: Modifier,
    styledUrl: StyledUrl,
    onDismissedToDelete: (styledUrl: StyledUrl) -> Unit,
) {
    val context = LocalContext.current
    val shape = RoundedCornerShape(30.dp)
    val backgroundColor = Color(240, 240, 240)
    val dismissState = rememberDismissState(confirmStateChange = { dismissValue ->
        when (dismissValue) {
            DismissValue.Default -> { // dismissThresholds 만족 안한 상태
                false
            }
            DismissValue.DismissedToEnd -> { // -> 방향 스와이프 (수정)
                toast(context) {
                    getString(R.string.activity_main_component_styledcard_toast_support_in_progress)
                }
                false
            }
            DismissValue.DismissedToStart -> { // <- 방향 스와이프 (삭제)
                onDismissedToDelete(styledUrl)
                true
            }
        }
    })

    SwipeToDismiss(
        state = dismissState,
        dismissThresholds = { FractionalThreshold(0.25f) },
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(shape),
        dismissContent = { // content
            CarrotUrlCard(
                styledUrl = styledUrl,
                backgroundColor = backgroundColor,
                shape = shape
            )
        },
        background = { // dismiss content
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> backgroundColor.copy(alpha = 0.5f) // dismissThresholds 만족 안한 상태
                    DismissValue.DismissedToEnd -> Color.Green.copy(alpha = 0.4f) // -> 방향 스와이프 (수정)
                    DismissValue.DismissedToStart -> Color.Red.copy(alpha = 0.5f) // <- 방향 스와이프 (삭제)
                }
            )
            val icon = when (dismissState.targetValue) {
                DismissValue.Default -> painterResource(R.drawable.ic_round_circle_24)
                DismissValue.DismissedToEnd -> painterResource(R.drawable.ic_round_edit_24)
                DismissValue.DismissedToStart -> painterResource(R.drawable.ic_round_delete_24)
            }
            val scale by animateFloatAsState(
                when (dismissState.targetValue == DismissValue.Default) {
                    true -> 0.8f
                    else -> 1.5f
                }
            )
            val alignment = when (direction) {
                DismissDirection.EndToStart -> Alignment.CenterEnd
                DismissDirection.StartToEnd -> Alignment.CenterStart
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 30.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    modifier = Modifier.scale(scale),
                    painter = icon,
                    contentDescription = null
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CarrotUrlCard(styledUrl: StyledUrl, backgroundColor: Color, shape: Shape) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxSize(),
        shape = shape,
        backgroundColor = backgroundColor,
        onClick = {
            Util.copy(context, "https://carrot.style/${styledUrl.styled.replaceLast(".html", "")}")
            toast(context) {
                getString(R.string.activity_main_component_styledcard_toast_copied)
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = styledUrl.styled.replaceLast(".html", ""),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = styledUrl.memo,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStyledCard() {
    CarrotStyleTheme {
        StyledCard(
            modifier = Modifier,
            styledUrl = StyledUrl("carrot.style/androidTest", "www.naver.com", "just carrot-style."),
            onDismissedToDelete = {}
        )
    }
}
