package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    borderRadius: Dp = 40.dp,
    contentPadding: PaddingValues = PaddingValues(20.dp),
    content: @Composable (RowScope.() -> Unit),
) {
    var cardModifier = modifier
        .clip(
            shape = RoundedCornerShape(size = borderRadius)
        )

    if (onClick != null) {
        cardModifier = cardModifier.clickable {
            onClick()
        }
    }

    Row(
        modifier = cardModifier
            .background(
                color
            )
            .padding(contentPadding),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement,
    ) {
        content()
    }

}

@PreviewLightDark
@Composable
private fun CustomCardPreview() {
    BrawlhallaTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            CustomCard {
                Row(Modifier.weight(1f)) {
                    Text("Text", color = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }
        }
    }
}