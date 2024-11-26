package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.LegendsListState
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LegendsTopBar(
    state: LegendsListState,
    onLegendAction: (LegendAction) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        state.openSearch, label = "topBarAnimation", modifier = modifier
    ) { open ->
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors().copy(
                scrolledContainerColor = MaterialTheme.colorScheme.background,
            ),
            title = {
                if (open) {
                    val focusRequester = remember { FocusRequester() }
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                    SearchBar(
                        modifier = Modifier
                            .padding(
                                bottom = 10.dp
                            )
                            .focusRequester(focusRequester),
                        inputField = {
                            SearchBarDefaults.InputField(
                                query = state.searchQuery,
                                onQueryChange = { query ->
                                    onLegendAction(LegendAction.SearchQuery(query))
                                },
                                onSearch = {},
                                expanded = false,
                                placeholder = { Text(stringResource(R.string.search)) },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = stringResource(R.string.search)
                                    )
                                },
                                onExpandedChange = {},
                            )
                        },
                        expanded = false,
                        onExpandedChange = { }
                    ) { }
                } else {
                    Text(stringResource(R.string.legends))
                }
            },
            actions = {
                if (open) {
                    IconButton(
                        onClick = {
                            onLegendAction(
                                LegendAction.ToggleSearch(
                                    false
                                )
                            )
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                } else {
                    IconButton(onClick = {
                        onLegendAction(
                            LegendAction.ToggleFilters
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.FilterAlt,
                            contentDescription = null,
                            tint = if (state.openFilters) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                LocalContentColor.current
                            }
                        )
                    }
                    IconButton(
                        onClick = {
                            onLegendAction(
                                LegendAction.ToggleSearch(
                                    true
                                )
                            )
                        },

                        ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search)
                        )

                    }
                }
            },
            scrollBehavior = scrollBehavior
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun LegendsTopBarPreview() {
    BrawlhallaTheme {
        Surface {
            LegendsTopBar(
                state = LegendsListState(

                ),
                {},
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            )
        }
    }
}