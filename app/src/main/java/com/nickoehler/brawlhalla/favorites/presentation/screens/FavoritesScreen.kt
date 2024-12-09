package com.nickoehler.brawlhalla.favorites.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.favorites))
    }
}

@Preview
@Composable
private fun FavoritesScreenPreview() {
    BrawlhallaTheme {
        Surface {
            FavoritesScreen()
        }
    }
}