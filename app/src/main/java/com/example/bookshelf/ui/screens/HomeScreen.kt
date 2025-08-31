package com.example.bookshelf.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookshelf.R

@Composable
fun HomeScreen(
    bookshelfUiState: BookShelfUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onSearch: (String) -> Unit = {}
) {
    when (bookshelfUiState) {
        is BookShelfUiState.AskUser -> {
            AppScreen(modifier
                .padding(top = contentPadding.calculateTopPadding()),
                onSearch = onSearch)
        }

        is BookShelfUiState.Loading -> {
            AppScreen(modifier
                .padding(top = contentPadding.calculateTopPadding()),
                loading = true,
                onSearch = onSearch
            )
        }

        is BookShelfUiState.ErrorNotFound -> {
            AppScreen(modifier
                .padding(top = contentPadding.calculateTopPadding()),
                errorNotFound = true,
                onSearch = onSearch
            )
        }

        is BookShelfUiState.ErrorNetwork -> {
            AppScreen(modifier
                .padding(top = contentPadding.calculateTopPadding()),
                errorNetwork = true,
                onSearch = onSearch
            )
        }

        is BookShelfUiState.Success -> {
            AppScreen(
                modifier = modifier.padding(top = contentPadding.calculateTopPadding()),
                books = bookshelfUiState.books,
                success = true,
                onSearch = onSearch
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    books: List<String> = listOf(),
    errorNotFound: Boolean = false,
    errorNetwork: Boolean = false,
    success: Boolean = false,
    loading: Boolean = false,
    onSearch: (String) -> Unit = {}
) {
    var query by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search books") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { onSearch(query) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }

        Spacer(Modifier.height(16.dp))

        when {
            loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorNetwork -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_connection_error),
                        contentDescription = "Connection error"
                    )
                    Text("Network error. Please try again.")
                }

            }

            errorNotFound -> {
                Text("No books found for your search.")
            }

            success -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 120.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(books) { thumbUrl ->
                        BookPhotoCard(
                            thumbUrl = thumbUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(0.6f)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookPhotoCard(
    thumbUrl: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier,
        model = thumbUrl,
        contentDescription = null,
        error = painterResource(R.drawable.ic_broken_image),
        placeholder = painterResource(R.drawable.loading_img),
    )
}