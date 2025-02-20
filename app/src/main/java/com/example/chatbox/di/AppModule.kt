package com.example.chatbox.di

import android.content.Context
import com.example.chatbox.data.local.datastore.DataStoreHelper
import com.example.chatbox.data.network.KtorClient
import com.example.chatbox.data.network.Repository
import com.example.chatbox.data.network.StompWebSocketClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import ua.naiksoftware.stomp.StompClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun dataStore(context: Context): DataStoreHelper {
        return DataStoreHelper(context)
    }
    @Provides
    fun provideApplicationContext(@ApplicationContext application: Context): Context {
        return application
    }

    @Provides
    @Singleton
    fun apiClient(dataStoreHelper: DataStoreHelper):HttpClient{
        return KtorClient(dataStoreHelper).httpClient
    }

    @Provides
    @Singleton
    fun funRepository(httpClient: HttpClient): Repository {
        return Repository(httpClient)
    }

    @Provides
    @Singleton
    fun stompWebSocketClient(dataStoreHelper: DataStoreHelper):StompClient{
        return StompWebSocketClient(dataStoreHelper).stompClient()
    }
}