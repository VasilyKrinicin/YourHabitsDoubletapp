package com.myapp.yourhabitsdoubletapp.ui.EditHabitFragment

import android.graphics.Color
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.myapp.domain.model.HabitModel
import com.myapp.domain.model.PriorityHabit
import com.myapp.domain.model.TypeHabit

import com.myapp.yourhabitsdoubletapp.R

import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.test.espresso.matcher.ViewMatchers.*

import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class EditHabitFragmentTest {

    @Test
    fun fragmentEditHabit_DisplayedInUi(){
        val habit =  HabitModel(
            uid = "123",
            nameHabit = "testHabit",
            descriptionHabit = "testHabit",
            typeHabit = TypeHabit.POSITIVE,
            priorityHabit = PriorityHabit.HIGH,
            numberExecutions = 10,
            periodText = 10,
            colorHabit = Color.GREEN,
            date = 1,
            doneDate = mutableListOf(),
            unloaded = false
        )
        val bundle = EditHabitFragmentArgs(uid = "2e49ce10-7a5e-444a-b5c8-2d3ae11662c4").toBundle()

        launchFragmentInContainer<EditHabitFragment>(bundle, R.style.Theme_YourHabitsDoubletapp)

        onView(withId(R.id.helloTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.helloTextView)).check(matches(withText(R.string.edit_new_habit_text)))
        onView(withId(R.id.editHabitNameLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.editHabitNameText)).check(matches(isDisplayed()))

    }


}