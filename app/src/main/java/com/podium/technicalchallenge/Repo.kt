package com.podium.technicalchallenge

import com.apollographql.apollo.coroutines.await
import com.podium.technicalchallenge.ui.dashboard.MovieHeaderModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class Repo {

    fun getMovieHeaders(): List<MovieHeaderModel> = runBlocking(Dispatchers.IO) {
        val response = ApiClient.getInstance().movieClient.query(GetMovieHeadersQuery()).await()

        var movieList: List<MovieHeaderModel>? = null
        if (response.data?.movies != null) {
            movieList = response.data?.movies?.mapNotNull { movie ->
                if (movie == null) {
                    null
                } else {
                    MovieHeaderModel(
                        movie.id,
                        movie.title,
                        movie.runtime,
                        movie.releaseDate,
                        movie.popularity,
                        movie.posterPath
                    )
                }
            }
        }

        return@runBlocking movieList ?: throw Exception("Error getting movie headers from GraphQL")
    }

    companion object {
        private var INSTANCE: Repo? = null
        fun getInstance() = INSTANCE
            ?: Repo().also {
                INSTANCE = it
            }
    }
}