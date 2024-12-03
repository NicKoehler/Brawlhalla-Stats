package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.nickoehler.brawlhalla.ranking.presentation.Localizable

@Composable
fun <T> CustomDropdownMenu(
    title: String,
    selected: T,
    modifier: Modifier = Modifier,
    items: List<T> = emptyList(),
    onSelect: (T) -> Unit = {}
) where T : Localizable {
    val context = LocalContext.current
    var isOpen by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Button({ isOpen = true }
        ) {
            Text("$title: ${selected.toString(context)}")
        }
        DropdownMenu(isOpen, { isOpen = false }) {
            items.forEach {
                DropdownMenuItem(
                    text = { Text(it.toString(context)) },
                    onClick = { onSelect(it) },
                    trailingIcon = {
                        if (it == selected) {
                            Icon(Icons.Default.Check, null)
                        }
                    }
                )
            }
        }
    }
}