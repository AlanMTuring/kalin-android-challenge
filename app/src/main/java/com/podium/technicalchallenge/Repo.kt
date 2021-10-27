package com.podium.technicalchallenge

import com.apollographql.apollo.coroutines.await
import com.podium.technicalchallenge.ui.dashboard.MovieHeaderModel
import com.podium.technicalchallenge.ui.moviedetail.CastMemberModel
import com.podium.technicalchallenge.ui.moviedetail.DirectorModel
import com.podium.technicalchallenge.ui.moviedetail.MovieDetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class Repo {

    fun getMovieHeaders(): List<MovieHeaderModel> = runBlocking(Dispatchers.IO) {
        val response = ApiClient.getInstance().movieClient.query(GetMovieHeadersQuery()).await()
        val gqlMovieList = response.data?.movies ?: throw Exception("Error getting movie headers")
        return@runBlocking gqlToHeaderList(gqlMovieList)
    }

    fun getMovieDetail(movieId: Int) = runBlocking(Dispatchers.IO) {
        val response = ApiClient.getInstance().movieClient.query(GetMovieDetailQuery(id = movieId)).await()
        val gqlMovie = response.data?.movie ?: throw Exception("Error getting movie with ID: $movieId")
        return@runBlocking graphQlToDetailModel(gqlMovie)
    }

    fun getTopFiveHeaders(): List<MovieHeaderModel> = runBlocking(Dispatchers.IO) {
        val response = ApiClient.getInstance().movieClient.query(GetTopFiveHeadersQuery()).await()
        val gqlMovieList = response.data?.movies ?: throw Exception("Error getting top five movie headers")
        return@runBlocking gqlToHeaderList2(gqlMovieList)
    }

    //For sake of time, not looking into refactoring so methods can take both. First time working with graphQL, so i've chosen to do a little extra work fast instead of taking time to learn better queries
    private fun gqlToHeaderList2(gqlMovieLIst: List<GetTopFiveHeadersQuery.Movie?>): List<MovieHeaderModel> {
        return gqlMovieLIst.mapNotNull { movie ->
            if (movie != null) {
                MovieHeaderModel(
                    movie.id,
                    movie.title,
                    movie.runtime,
                    movie.releaseDate,
                    movie.popularity,
                    movie.posterPath
                )
            } else {
                null
            }
        }
    }

    private fun gqlToHeaderList(gqlMovieLIst: List<GetMovieHeadersQuery.Movie?>): List<MovieHeaderModel> {
        return gqlMovieLIst.mapNotNull { movie ->
            if (movie != null) {
                MovieHeaderModel(
                    movie.id,
                    movie.title,
                    movie.runtime,
                    movie.releaseDate,
                    movie.popularity,
                    movie.posterPath
                )
            } else {
                null
            }
        }
    }

    private fun graphQlToDetailModel(movie: GetMovieDetailQuery.Movie): MovieDetailModel {
        return MovieDetailModel(
            header = MovieHeaderModel(
                id = movie.id,
                title = movie.title,
                runtime = movie.runtime,
                releaseDate = movie.releaseDate,
                popularity = movie.popularity,
                imageUrl = movie.posterPath
            ),
            movie.originalLanguage,
            movie.originalTitle,
            movie.overview,
            movie.voteAverage,
            movie.voteCount,
            movie.genres,
            movie.cast.map { CastMemberModel(it.profilePath, it.name, it.character, it.order) },
            DirectorModel(movie.director.id, movie.director.name)
        )
    }

    companion object {
        private var INSTANCE: Repo? = null
        fun getInstance() = INSTANCE
            ?: Repo().also {
                INSTANCE = it
            }
    }
}