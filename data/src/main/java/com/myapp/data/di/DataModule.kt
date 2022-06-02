package com.myapp.data.di

import android.content.Context
import androidx.room.Room
import com.myapp.data.Networking.ApiConfiguration
import com.myapp.data.Networking.AuthInterceptor
import com.myapp.data.Networking.NetworkApi
import com.myapp.data.NetworkRepositoryImp
import com.myapp.data.database.HabitDao
import com.myapp.data.database.HabitDataBase
import com.myapp.data.database.HabitRepositoryImpl
import com.myapp.domain.repository.NetworkRepository
import com.myapp.domain.repository.HabitRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideHabitRepository(habitDao: HabitDao): HabitRepository {
        return HabitRepositoryImpl(habitDao = habitDao)
    }
    @Singleton
    @Provides
    fun provideNetworkRepository(networkApi:NetworkApi, habitRepository:HabitRepository): NetworkRepository {
        return NetworkRepositoryImp( networkApi = networkApi, habitRepository =  habitRepository)
    }

    @Singleton
    @Provides
    fun provideRepositoryNetwork(
        habitRepository: HabitRepository,
        networkApi: NetworkApi
    ): NetworkRepositoryImp {
        return NetworkRepositoryImp(habitRepository = habitRepository, networkApi = networkApi)
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
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .baseUrl(ApiConfiguration.HOST_NAME)
           // .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
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