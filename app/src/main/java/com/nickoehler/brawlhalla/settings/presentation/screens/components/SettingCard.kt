package com.nickoehler.brawlhalla.settings.presentation.screens.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.domain.model.Theme
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard

@OptIn(ExperimentalMaterial3Api::class)
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

    val animatedFloat = remember { Animatable(0f) }

    LaunchedEffect(currentSettingValue) {
        animatedFloat.animateTo(360f)
        animatedFloat.snapTo(0f)
    }

    if (isOpen) {
        ModalBottomSheet(dismiss) {
            Box(
                Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Theme.entries.map { theme ->
                        Row(
                            Modifier
                                .clip(CircleShape)
                                .background(
                                    if (currentSettingValue == theme) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surfaceContainer
                                    }
                                )
                                .selectable(
                                    selected = currentSettingValue == theme,
                                    onClick = {
                                        updateValue(theme)
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            AnimatedVisibility(
                                currentSettingValue == theme,
                                enter = expandHorizontally() + fadeIn(),
                                exit = shrinkHorizontally() + fadeOut()
                            ) {
                                Icon(Icons.Default.Done, null)
                            }
                            Spacer(modifier.width(10.dp))
                            when (theme) {
                                Theme.System -> Icon(painterResource(R.drawable.routine_24px), null)
                                Theme.Light -> Icon(Icons.Default.LightMode, null)
                                Theme.Dark -> Icon(Icons.Default.DarkMode, null)
                            }
                            Spacer(modifier.width(10.dp))
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
        AnimatedContent(
            currentSettingValue,
            transitionSpec = { fadeIn().togetherWith(fadeOut()) },
            modifier = Modifier.rotate(animatedFloat.value)
        ) {
            when (it) {
                Theme.System -> Icon(
                    painterResource(R.drawable.routine_24px),
                    null,
                )

                Theme.Light -> Icon(
                    Icons.Default.LightMode,
                    null,
                )

                Theme.Dark -> Icon(
                    Icons.Default.DarkMode,
                    null,
                )
            }
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