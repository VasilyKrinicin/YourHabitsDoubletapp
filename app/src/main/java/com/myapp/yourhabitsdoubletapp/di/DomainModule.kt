package com.myapp.yourhabitsdoubletapp.di

import com.myapp.data.database.HabitDao
import com.myapp.domain.repository.RepositoryHabit
import com.myapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideAddHabit(repository: RepositoryHabit): AddHabit {
        return AddHabit(repository, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideDeleteHabit(repository: RepositoryHabit): DeleteHabit {
        return DeleteHabit(repository, Dispatchers.IO)
    }
    @Singleton
    @Provides
    fun provideExecutionHabit(repository: RepositoryHabit): ExecutionHabit {
        return ExecutionHabit(repository, Dispatchers.IO)
    }
    @Singleton
    @Provides
    fun provideGetHabitByUid(repository: RepositoryHabit): GetHabitByUid {
        return GetHabitByUid(repository)
    }
    @Singleton
    @Provides
    fun provideGetHabitList(repository: RepositoryHabit): GetHabitList {
        return GetHabitList(repository)
    }
    @Singleton
    @Provides
    fun provideGetSortedFilterListHabit(repository: RepositoryHabit): GetSortedFilterListHabit {
        return GetSortedFilterListHabit(repository)
    }
    @Singleton
    @Provides
    fun provideUpdateHabit(repository: RepositoryHabit): UpdateHabit {
        return UpdateHabit(repository, Dispatchers.IO)
    }

}