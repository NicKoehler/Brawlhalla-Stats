package com.nickoehler.brawlhalla.settings.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.domain.model.Theme
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard

@Composable
fun SettingCardTheme(
    currentSettingValue: Theme,
    updateValue: (Theme) -> Unit,
    modifier: Modifier = Modifier
) {

    var isOpen by remember {
        mutableStateOf(false)
    }

    val dismiss = { isOpen = false }

    if (isOpen) {
        Dialog(dismiss) {
            Box(
                Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Theme.entries.map { theme ->
                        Row(
                            Modifier.selectable(
                                selected = currentSettingValue == theme,
                                onClick = {
                                    updateValue(theme)
                                    dismiss()
                                },
                                role = Role.RadioButton
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            RadioButton(
                                currentSettingValue == theme,
                                onClick = null
                            )
                            Text(
                                stringResource(
                                    when (theme) {
                                        Theme.System -> R.string.settings_theme_system
                                        Theme.Light -> R.string.settings_theme_light
                                        Theme.Dark -> R.string.settings_theme_dark
                                    }
                                ),
                                Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }

    CustomCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { isOpen = true },
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    )
    {
        when (currentSettingValue) {
            Theme.System -> Icon(painterResource(R.drawable.routine_24px), null)
            Theme.Light -> Icon(Icons.Default.LightMode, null)
            Theme.Dark -> Icon(Icons.Default.DarkMode, null)
        }
        Text(stringResource(R.string.settings_theme_title), modifier = Modifier.weight(1f))
        Text(
            stringResource(
                when (currentSettingValue) {
                    Theme.System -> R.string.settings_theme_system
                    Theme.Light -> R.string.settings_theme_light
                    Theme.Dark -> R.string.settings_theme_dark
                }
            )
        )
    }
}

@Preview
@Composable
fun SettingCardPreview(modifier: Modifier = Modifier) {
    Surface {
        Column(modifier.fillMaxSize()) {
            SettingCardTheme(Theme.System, {

            }, Modifier.fillMaxWidth())
        }
    }
}