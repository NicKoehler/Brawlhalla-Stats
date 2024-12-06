package com.nickoehler.brawlhalla.core.presentation.components

import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun CustomShare(
    title: String?,
    content: String?,
    context: Context = LocalContext.current
) {
    val shareIntent = Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, "https://bh.it/$content")
        putExtra(Intent.EXTRA_TITLE, title)
        type = "text/plain"
    }, null)
    IconButton(
        onClick = {
            context.startActivity(shareIntent)
        }
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = null
        )
    }
}

@PreviewLightDark
@Composable
fun GreetingPreview() {
    BrawlhallaTheme {
        Surface {
            CustomShare(
                title = "hello",
                content = "bh://legend/3",
            )
        }
    }
}