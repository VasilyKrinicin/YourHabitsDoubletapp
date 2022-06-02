package com.myapp.data.database

import android.graphics.Color
import androidx.room.Room
import com.myapp.domain.model.HabitModel
import com.myapp.domain.model.PriorityHabit
import com.myapp.domain.model.SortType
import com.myapp.domain.model.TypeHabit
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception
import javax.inject.Inject


class HabitRepositoryImplTest {

    private var habitDao = FakeDAO()
    lateinit var habitRepositoryImpl: HabitRepositoryImpl
    private val habitModelTest1 = HabitModel(
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
    private val habitModelTest2 = HabitModel(
        uid = "123",
        nameHabit = "testHabit",
        descriptionHabit = "testHabit",
        typeHabit = TypeHabit.NEGATIVE,
        priorityHabit = PriorityHabit.HIGH,
        numberExecutions = 10,
        periodText = 10,
        colorHabit = Color.GREEN,
        date = 1,
        doneDate = mutableListOf(),
        unloaded = false
    )

    @Before
    fun createHabitDao() {
        habitRepositoryImpl = HabitRepositoryImpl(habitDao = habitDao)

    }

    @Test
    fun testAddHabit() = runBlocking {
        var listHabit: List<HabitModel>? = null

        habitRepositoryImpl.addHabit(habitModelTest1)
        habitRepositoryImpl.addHabit(habitModelTest2)
        val flowData = habitRepositoryImpl.getAll()
        listHabit = flowData.last()
        assertEquals(2, listHabit?.size)
    }


    @Test
    fun testGetHabitByType() = runBlocking {
        var listHabit: List<HabitModel>? = null
        var listHabit2: List<HabitModel>? = null
        habitRepositoryImpl.addHabit(habitModelTest1)
        habitRepositoryImpl.addHabit(habitModelTest2)
        val flowData1 = habitRepositoryImpl.getSortFilterListHabit(
            SortType.NONE,
            "testHabit"
        )
        try {
            listHabit = flowData1.single().toList()
        } catch (e: Exception) {

        }

        assertEquals(2, listHabit?.size)
        val flowData2 = habitRepositoryImpl.getSortFilterListHabit(
            SortType.NONE,
            "sdf"
        )
        try {
            listHabit2 = flowData2.single().toList()
        } catch (e: Exception) {

        }
        assertEquals(0, listHabit2?.size)
    }
}
