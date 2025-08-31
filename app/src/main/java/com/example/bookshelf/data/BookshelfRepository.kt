package com.example.bookshelf.data

import com.example.bookshelf.model.BookItem
import com.example.bookshelf.model.BooksResponse
import com.example.bookshelf.network.BookshelfApiService

interface BookshelfRepository {
    suspend fun getBooks(userInput: String): BooksResponse
    suspend fun getBookById(id: String): BookItem
}

class NetworkBookshelfRepository(
    private val bookShelfApiService: BookshelfApiService
) : BookshelfRepository {
    override suspend fun getBooks(userInput: String): BooksResponse {
        return bookShelfApiService.getBooks(userInput)
    }

    override suspend fun getBookById(id: String): BookItem {
        return bookShelfApiService.getBookById(id)
    }
}