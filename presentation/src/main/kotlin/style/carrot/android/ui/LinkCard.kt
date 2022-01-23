/*
 * carrot-android © 2022 Ji Sungbin. all rights reserved.
 * carrot-android license is under the MIT.
 *
 * [LinkCard.kt] created by Ji Sungbin on 22. 1. 23. 오후 4:09
 *
 * Please see: https://github.com/carrot-style/carrot-android/blob/main/LICENSE.
 */

package style.carrot.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import style.carrot.android.domain.model.CarrotUrl

// design: https://m3.material.io/components/cards/implementation
@Composable
fun StyledCard(modifier: Modifier, carrotUrl: CarrotUrl) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = Color(242, 240, 240),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(vertical = 10.dp, horizontal = 15.dp)
    ) {
        Text(text = carrotUrl.styled, style = MaterialTheme.typography.bodyMedium)
        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = carrotUrl.memo,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
