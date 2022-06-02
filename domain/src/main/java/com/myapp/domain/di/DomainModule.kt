package com.myapp.domain.di

import com.myapp.domain.repository.NetworkRepository
import com.myapp.domain.repository.HabitRepository
import com.myapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideAddHabit(repository: HabitRepository): AddHabitUseCase {
        return AddHabitUseCase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideDeleteHabit(repository: HabitRepository): DeleteHabitUseCase {
        return DeleteHabitUseCase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideExecutionHabit(
        repository: HabitRepository,
        networkRepository: NetworkRepository
    ): ExecutionHabitUseCase {
        return ExecutionHabitUseCase(repository, Dispatchers.IO, networkRepository)
    }

    @Singleton
    @Provides
    fun provideGetHabitByUid(repository: HabitRepository): GetHabitByUidUseCase {
        return GetHabitByUidUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetHabitList(repository: HabitRepository): GetHabitListUseCase {
        return GetHabitListUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetSortedFilterListHabit(repository: HabitRepository): GetSortedFilterListHabitUseCase {
        return GetSortedFilterListHabitUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateHabit(repository: HabitRepository): UpdateHabitUseCase {
        return UpdateHabitUseCase(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun providePutHabitUseCase(networkRepository: NetworkRepository): PutHabitUseCase {
        return PutHabitUseCase(networkRepository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideAddOrUpdateUseCase(
        putHabitUseCase: PutHabitUseCase,
        addHabitUseCase: AddHabitUseCase,
        updateHabitUseCase: UpdateHabitUseCase
    ): AddOrUpdateUseCase {
        return AddOrUpdateUseCase(
            Dispatchers.IO,
            putHabitUseCase,
            addHabitUseCase,
            updateHabitUseCase
        )
    }

}