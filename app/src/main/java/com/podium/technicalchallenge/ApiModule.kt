package com.podium.technicalchallenge

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    private val API_URL = "https://podium-fe-challenge-2021.netlify.app/.netlify/functions/graphql"

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(API_URL)
            .okHttpClient(
                OkHttpClient().newBuilder()
                    .build()
            )
            .build()
    }

}