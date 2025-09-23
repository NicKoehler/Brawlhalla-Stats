package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FilterAltOff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import com.nickoehler.brawlhalla.R
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SimpleSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterToggle: () -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    lazyColumnState: LazyListState,
    modifier: Modifier = Modifier,
    onSearch: () -> Unit = {},
    placeholder: @Composable () -> Unit = {},
    enabled: Boolean = true,
    isFilterOpen: Boolean = false,
    isFilterEnabled: Boolean = true,
) {
    val coroutineScope = rememberCoroutineScope()
    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        inputField = {
            SearchBarDefaults.InputField(
                enabled = enabled,
                query = query,
                onQueryChange = {
                    onQueryChange(it)
                },
                onSearch = {
                    onSearch()
                    focusManager.clearFocus()
                },
                expanded = false,
                placeholder = placeholder,
                leadingIcon = {
                    if (query.isBlank()) {
                        Icon(Icons.Default.Search, stringResource(R.string.search))
                    } else {
                        IconButton(
                            {
                                onQueryChange("")
                            }
                        ) {
                            Icon(Icons.Default.Close, stringResource(R.string.cancel))
                        }
                    }
                },
                trailingIcon = {
                    IconButton(
                        enabled = isFilterEnabled,
                        onClick = {
                            onFilterToggle()
                            coroutineScope.launch {
                                lazyColumnState.animateScrollToItem(0)
                            }
                        },
                    ) {
                        AnimatedContent(
                            isFilterOpen
                        ) {
                            Icon(
                                if (it) Icons.Default.FilterAltOff else Icons.Default.FilterAlt,
                                stringResource(R.string.filters),
                                tint = if (isFilterEnabled) MaterialTheme.colorScheme.onBackground else LocalContentColor.current
                            )

                        }
                    }
                },
                onExpandedChange = {}
            )
        },
        onExpandedChange = {},
        expanded = false,
        content = {}
    )
}