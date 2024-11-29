package com.nickoehler.brawlhalla.search.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.search.presentation.RankingAction
import com.nickoehler.brawlhalla.search.presentation.RankingState
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RankingTopAppBar(
    state: RankingState,
    onRankingAction: (RankingAction) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors().copy(
            scrolledContainerColor = MaterialTheme.colorScheme.background,
        ),
        title = {
            SearchBar(
                modifier = Modifier
                    .padding(
                        bottom = 10.dp
                    )
                    .fillMaxWidth(),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = state.searchQuery,
                        onQueryChange = { query ->
                            onRankingAction(RankingAction.SearchQuery(query))
                        },
                        onSearch = {
                            if (state.searchQuery.isNotEmpty())
                                onRankingAction(RankingAction.Search)
                        },
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
        },
        actions = {
            IconButton(
                { onRankingAction(RankingAction.ResetSearch) }
            ) {
                Icon(Icons.Default.Close, null)
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Composable
private fun LegendsTopBarPreview() {
    BrawlhallaTheme {
        Surface {
            RankingTopAppBar(
                state = RankingState(),
                {},
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            )
        }
    }
}