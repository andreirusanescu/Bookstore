package com.example.bookshelf.ui.screens

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BookshelfRepository
import com.example.bookshelf.model.BookDetails
import com.example.bookshelf.model.BookItem
import com.example.bookshelf.model.BooksResponse
import kotlinx.coroutines.launch
import okio.IOException

sealed interface BookShelfUiState {
    data class Success(val books: List<BookDetails>) : BookShelfUiState
    object ErrorNetwork : BookShelfUiState
    object ErrorNotFound: BookShelfUiState
    object Loading : BookShelfUiState
    object AskUser : BookShelfUiState
}

class BookshelfViewModel(
    private val bookshelfRepository: BookshelfRepository
) : ViewModel() {
    var bookshelfUiState: BookShelfUiState by mutableStateOf(BookShelfUiState.Loading)
        private set

    init {
        askUser()
    }

    private fun askUser() {
        bookshelfUiState = BookShelfUiState.AskUser
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getBooks(userInput: String) {
        viewModelScope.launch {

            val booksDetails = mutableListOf<BookDetails>()

            bookshelfUiState = BookShelfUiState.Loading
            bookshelfUiState = try {
                val booksResponse = bookshelfRepository.getBooks(userInput)
                booksResponse.items?.forEach { item ->
                    val id = item.id
                    if (id != null) {
                        val bookDetails = bookshelfRepository.getBookById(id)
                        val volumeInfo = bookDetails.volumeInfo
                        val thumbnail = volumeInfo?.imageLinks?.thumbnail?.replace("http", "https")
                        if (thumbnail != null) {
                            val title = volumeInfo.title ?: "Unknown"
                            val authors = volumeInfo.authors ?: listOf("Unknown")
                            val publisher = volumeInfo.publisher ?: "Unknown"
                            val publishedDate = volumeInfo.publishedDate ?: "Unknown"
                            val description = volumeInfo.description ?: "Unknown"
                            val pageCount = volumeInfo.pageCount.toString()
                            val maturityRating = volumeInfo.maturityRating ?: "Unknown"
                            val language: String = volumeInfo.language ?: "Unknown"
                            val buyLink: String = bookDetails.saleInfo?.buyLink ?: "Unknown"

                            val bookElem = BookDetails(
                                thumbUrl = thumbnail,
                                title, authors, publisher,
                                publishedDate, description,
                                pageCount, maturityRating,
                                language, buyLink
                            )
                            booksDetails.add(bookElem)
                        }
                    }
                }
                if (!booksDetails.isEmpty()) {
                    BookShelfUiState.Success(booksDetails)
                } else {
                    BookShelfUiState.ErrorNotFound
                }
            } catch (e: IOException) {
                BookShelfUiState.ErrorNetwork
            } catch (e: HttpException) {
                BookShelfUiState.ErrorNotFound
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookshelfViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}