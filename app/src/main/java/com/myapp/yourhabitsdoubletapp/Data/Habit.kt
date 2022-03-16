package com.myapp.yourhabitsdoubletapp

import android.graphics.Color
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Habit(
   var id:Int,
   val nameHabit:String,
   val descriptionHabit:String,
   val typeHabit:TypeHabit,
   val priorityHabit:PriorityHabit,
   val numberExecutions:Int,
   val periodText:String,
   val colorHabit: Int
): Parcelable

enum class TypeHabit{
   POSITIVE,
   NEGATIVE
}
enum class PriorityHabit(val str:String){
   HIGH("Высокий"),
   LOW("Низкий"),
   MIDDLE("Средний")
}