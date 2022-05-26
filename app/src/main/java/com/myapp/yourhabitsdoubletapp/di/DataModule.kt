package com.myapp.yourhabitsdoubletapp.di

import android.content.Context
import androidx.room.Room
import com.myapp.data.Networking.ApiConfiguration
import com.myapp.data.Networking.AuthInterceptor
import com.myapp.data.Networking.NetworkApi
import com.myapp.data.RepositoryNetwork
import com.myapp.data.database.HabitDao
import com.myapp.data.database.HabitDataBase
import com.myapp.data.database.HabitRepositoryImpl
import com.myapp.domain.repository.RepositoryHabit
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideHabitRepository(habitDao: HabitDao): RepositoryHabit {
        return HabitRepositoryImpl(habitDao = habitDao)
    }

    @Singleton
    @Provides
    fun provideRepositoryNetwork(
        repositoryHabit: RepositoryHabit,
        networkApi: NetworkApi
    ): RepositoryNetwork {
        return RepositoryNetwork(repositoryHabit = repositoryHabit, networkApi = networkApi)
    }

    @Singleton
    @Provides
    fun provideHabitDao(context: Context): HabitDao {
        val database = Room.databaseBuilder(
            context.applicationContext,
            HabitDataBase::class.java,
            HabitDataBase.DB_NAME
        )
            .build()
        return database.habitDao()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okhttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConfiguration.HOST_NAME)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    }

    @Singleton
    @Provides
    fun provideNetworkApi(retrofit: Retrofit): NetworkApi {
        return retrofit.create(NetworkApi::class.java)
    }
}