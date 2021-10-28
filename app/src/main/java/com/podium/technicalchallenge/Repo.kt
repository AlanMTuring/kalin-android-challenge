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

    fun getGenres(): List<String> = runBlocking(Dispatchers.IO) {
        val response = ApiClient.getInstance().movieClient.query(GetGenresQuery()).await()
        return@runBlocking response.data?.genres ?: throw Exception("Error getting genres")
    }

    fun getMoviesByGenre(genre: String): List<MovieHeaderModel> = runBlocking(Dispatchers.IO) {
        val response = ApiClient.getInstance().movieClient.query(GetHeadersByGenreQuery(genre)).await()
        val gqlMovieList = response.data?.movies ?: throw Exception("Error getting movies by genre")
        return@runBlocking gqlToHeaderList3(gqlMovieList)
    }

    //For sake of time, not looking into refactoring the following three methods into one method that can take all kinds of GraphQL query.movies as a .
            //This is my first time working with graphQL, so i've chosen to do the weird work quickly (basically duplicate methods), rather than taking lots of time to learn more Apollo and GraphQL
    private fun gqlToHeaderList(gqlMovieList: List<GetMovieHeadersQuery.Movie?>): List<MovieHeaderModel> {
        return gqlMovieList.mapNotNull { movie ->
            if (movie != null) {
                MovieHeaderModel(
                    movie.id,
                    movie.title,
                    movie.runtime,
                    movie.releaseDate,
                    movie.popularity,
                    movie.voteAverage,
                    movie.voteCount,
                    movie.posterPath
                )
            } else {
                null
            }
        }
    }
    private fun gqlToHeaderList2(gqlMovieList: List<GetTopFiveHeadersQuery.Movie?>): List<MovieHeaderModel> {
        return gqlMovieList.mapNotNull { movie ->
            if (movie != null) {
                MovieHeaderModel(
                    movie.id,
                    movie.title,
                    movie.runtime,
                    movie.releaseDate,
                    movie.popularity,
                    movie.voteAverage,
                    movie.voteCount,
                    movie.posterPath
                )
            } else {
                null
            }
        }
    }
    private fun gqlToHeaderList3(gqlMovieList: List<GetHeadersByGenreQuery.Movie?>): List<MovieHeaderModel> {
        return gqlMovieList.mapNotNull { movie ->
            if (movie != null) {
                MovieHeaderModel(
                    movie.id,
                    movie.title,
                    movie.runtime,
                    movie.releaseDate,
                    movie.popularity,
                    movie.voteAverage,
                    movie.voteCount,
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
                rating = movie.voteAverage,
                numberOfRatings = movie.voteCount,
                imageUrl = movie.posterPath
            ),
            movie.overview,
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