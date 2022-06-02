package com.myapp.yourhabitsdoubletapp.ui.EditHabitFragment

import android.content.ContentResolver
import android.content.ContentResolver.SCHEME_ANDROID_RESOURCE
import android.provider.Settings.Global.getString
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.myapp.yourhabitsdoubletapp.MainActivity
import com.myapp.yourhabitsdoubletapp.R
import com.myapp.yourhabitsdoubletapp.ui.ViewPagerFragment
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.pager2.KViewPager2
import io.github.kakaocup.kakao.scroll.KScrollView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditHabitFragmentTestKaspresso : TestCase() {

    object ListHabitFragmentScreen : KScreen<ListHabitFragmentScreen>() {
        override val layoutId: Int = R.layout.activity_main
        override val viewClass: Class<*> = MainActivity::class.java

        val addFab = KButton { withId(R.id.addFab) }
    }

    object EditHabitFragmentScreen : KScreen<EditHabitFragmentScreen>() {
        override val layoutId: Int = R.layout.fragment_habit_edit
        override val viewClass: Class<*> = EditHabitFragment::class.java

        val helloTextView = KTextView { withId(R.id.helloTextView) }
        val editHabitNameLayout = KTextInputLayout { withId(R.id.editHabitNameLayout) }
        val editHabitNameText = KEditText { withId(R.id.editHabitNameText) }
        val editHabitDescriptionLayout =
            KTextInputLayout { withId(R.id.editHabitDescriptionLayout) }
        val editHabitDescriptionText = KEditText { withId(R.id.editHabitDescriptionText) }
        val spinerPriorityLayout = KTextInputLayout { withId(R.id.spinerPriorityLayout) }
        val spinerPriority = KTextView { withId(R.id.spinerPriority) }
        val radioPositiveHabit = KButton { withId(R.id.radioPositiveHabit) }
        val radioNegativeHabit = KButton { withId(R.id.radioNegativeHabit) }
        val editHabitNumberExecutionsLayout =
            KTextInputLayout { withId(R.id.editHabitNumberExecutionsLayout) }
        val editHabitNumberExecutionsText = KEditText { withId(R.id.editHabitNumberExecutionsText) }
        val editHabitPeriodLayout = KTextInputLayout { withId(R.id.editHabitPeriodLayout) }
        val editHabitPeriodText = KEditText { withId(R.id.editHabitPeriodText) }
        val horizontalScrollColorPicker = KScrollView { withId(R.id.horizontalScrollColorPicker) }
        val selectedColor = KImageView { withId(R.id.selected_color) }
        val selectedColorRGB = KImageView { withId(R.id.selected_color_RGB) }
        val selectedColorHSV = KImageView { withId(R.id.selected_color_HSV) }
        val addHabitButton = KButton { withId(R.id.add_habit_button) }

    }


    @get:Rule
    val scenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test() = run {
        step("Click Add Fab? open EditHabitFragment") {
            ListHabitFragmentScreen {
                addFab {
                    isVisible()
                    click()
                }
            }
        }
        step("Checking the presence view on the display") {
            EditHabitFragmentScreen {
                helloTextView {
                    isVisible()
                }
                editHabitNameLayout {
                    isVisible()
                }
                editHabitNameText {
                    isVisible()
                    click()
                }
                editHabitDescriptionLayout {
                    isVisible()
                }
                editHabitDescriptionText {
                    isVisible()
                }
                spinerPriorityLayout {
                    isVisible()
                }
                spinerPriority {
                    isVisible()

                }
                radioPositiveHabit {
                    isVisible()
                    isClickable()
                }
                radioNegativeHabit {
                    isVisible()
                    click()
                }
                radioPositiveHabit {
                    click()
                }
                radioNegativeHabit {
                    click()
                }
                editHabitNumberExecutionsLayout {
                    isVisible()
                }
                editHabitNumberExecutionsText {
                    isVisible()
                }
                editHabitPeriodLayout {
                    isVisible()
                }
                editHabitPeriodText {
                    isVisible()
                    click()

                }
                horizontalScrollColorPicker {
                    isVisible()
                }
                selectedColor {
                    isVisible()

                }
                selectedColorRGB {
                    isVisible()
                }
                selectedColorHSV {
                    isVisible()
                }
                addHabitButton {
                    isVisible()
                    click()
                }
            }
        }
    }
}