package com.nickoehler.brawlhalla.clans.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.clans.presentation.model.ClanMemberUi
import com.nickoehler.brawlhalla.clans.presentation.model.toClanMemberUi
import com.nickoehler.brawlhalla.clans.presentation.model.toColor
import com.nickoehler.brawlhalla.clans.presentation.screens.clanDetailSample
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun ClanMemberCard(
    member: ClanMemberUi,
    onClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    CustomCard(
        modifier = modifier.fillMaxWidth(),
        onClick = {
            onClick(member.brawlhallaId)
        }
    ) {
        Row {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    member.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "XP ${member.xp.formatted}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(member.rank.toColor())
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        member.rank.toString(),
                        color = Color.Black.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1
                    )
                }
                Text(
                    member.joinDate.formatted,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}


@Preview
@Composable
private fun ClanMemberCardPreview() {
    BrawlhallaTheme {
        Surface {
            ClanMemberCard(
                clanDetailSample.members.first().toClanMemberUi(),
                {}
            )
        }
    }
}