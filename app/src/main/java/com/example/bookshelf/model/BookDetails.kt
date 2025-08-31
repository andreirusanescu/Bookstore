package com.example.bookshelf.model

data class BookDetails(
    val thumbUrl: String,
    val title: String,
    val authors: List<String>,
    val publisher: String,
    val publishedDate: String,
    val description: String,
    val pageCount: String,
    val maturityRating: String,
    val language: String,
    val buyLink: String,
)
