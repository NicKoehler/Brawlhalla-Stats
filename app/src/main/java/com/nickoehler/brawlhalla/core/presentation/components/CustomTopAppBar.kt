package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.AppBarAction
import com.nickoehler.brawlhalla.core.presentation.CustomAppBarState
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    showSearch: Boolean = true,
    showInfo: Boolean = false,
    placeholder: String = stringResource(R.string.search),
    onAppBarAction: (AppBarAction) -> Unit = {},
    state: CustomAppBarState = CustomAppBarState(),
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    AnimatedContent(
        state.isOpenSearch,
        label = "topBarAnimation",
        modifier = modifier
    ) { open ->
        TopAppBar(
            expandedHeight = 80.dp,
            windowInsets = WindowInsets(10.dp),
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
                            .focusRequester(focusRequester),
                        inputField = {
                            SearchBarDefaults.InputField(
                                query = state.searchQuery,
                                onQueryChange = { onAppBarAction(AppBarAction.QueryChange(it)) },
                                onSearch = { onAppBarAction(AppBarAction.Search) },
                                expanded = false,
                                placeholder = { Text(placeholder) },
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
                    Text(
                        title,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            actions = {
                if (open) {
                    IconButton(
                        onClick = { onAppBarAction(AppBarAction.CloseSearch) }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                } else {
                    actions()
                    if (showSearch) {
                        IconButton(
                            onClick = { onAppBarAction(AppBarAction.OpenSearch) }
                        ) {
                            Icon(
                                Icons.Default.Search,
                                null
                            )
                        }
                    }
                    if (showInfo) {
                        IconButton(
                            onClick = {
                                onAppBarAction(
                                    AppBarAction.OpenSettings
                                )
                            }
                        ) {
                            Icon(
                                Icons.Default.Settings,
                                null
                            )
                        }
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
            Column {

                CustomTopAppBar(
                    "Action",
                    showSearch = false,
                    actions = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(Icons.Default.FilterAlt, null)
                        }
                    }
                )

                CustomTopAppBar(
                    "Action",
                    actions = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(Icons.Default.FilterAlt, null)
                        }
                    }
                )


                CustomTopAppBar(
                    "Action",
                    state = CustomAppBarState(isOpenSearch = true),
                    actions = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(Icons.Default.FilterAlt, null)
                        }
                    }
                )
            }
        }
    }
}