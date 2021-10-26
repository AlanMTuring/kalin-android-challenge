package com.podium.technicalchallenge

import com.apollographql.apollo.coroutines.await
import com.podium.technicalchallenge.ui.dashboard.MovieHeader

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class Repo {

    suspend fun getMovieHeaders(): List<MovieHeader> {
        val response = ApiClient.getInstance().movieClient.query(GetMovieHeadersQuery()).await()

        var movieList: List<MovieHeader>? = null
        if (response.data?.movies != null) {
            movieList = response.data?.movies?.mapNotNull { movie ->
                if (movie == null) {
                    null
                } else {
                    MovieHeader(
                        movie.id,
                        movie.title,
                        movie.runtime,
                        movie.posterPath
                    )
                }
            }
        }

        return movieList ?: throw Exception("Error getting movie headers from GraphQL")
    }

    companion object {
        private var INSTANCE: Repo? = null
        fun getInstance() = INSTANCE
            ?: Repo().also {
                INSTANCE = it
            }
    }
}