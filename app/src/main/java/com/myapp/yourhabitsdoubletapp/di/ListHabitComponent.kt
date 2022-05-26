package com.myapp.yourhabitsdoubletapp.di

import com.myapp.yourhabitsdoubletapp.ui.BottomSheetFragment
import com.myapp.yourhabitsdoubletapp.ui.ListHabitFragmen.ListHabitFragment
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Scope
import javax.inject.Singleton


@ListHabitScoped
@Subcomponent
abstract class ListHabitComponent : ScopedComponent() {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ListHabitComponent
    }

    abstract fun inject(listHabitFragment: ListHabitFragment)
    abstract fun inject(bottomSheetFragment: BottomSheetFragment)
}

@Scope
annotation class ListHabitScoped


