package com.myapp.yourhabitsdoubletapp.di

import com.myapp.yourhabitsdoubletapp.ui.EditHabitFragment.EditHabitFragment
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Scope

@EditHabitScope
@Subcomponent
abstract class EditHabitComponent : ScopedComponent() {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance habitId: String): EditHabitComponent
    }
    abstract fun inject(editHabitFragment: EditHabitFragment)
}
@Scope
annotation class EditHabitScope




