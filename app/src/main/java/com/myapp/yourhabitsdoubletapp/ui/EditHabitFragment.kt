package com.myapp.yourhabitsdoubletapp.ui

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColor
import androidx.core.view.isVisible
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myapp.yourhabitsdoubletapp.Data.PriorityHabit
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import com.myapp.yourhabitsdoubletapp.Habit
import com.myapp.yourhabitsdoubletapp.R
import com.myapp.yourhabitsdoubletapp.ViewModel.EditHabitViewModel
import com.myapp.yourhabitsdoubletapp.databinding.FragmentHabitEditBinding

class EditHabitFragment() : Fragment(R.layout.fragment_habit_edit) {
    private val editHabitViewModel: EditHabitViewModel by viewModels()
    private var fragmentHabitEditBinding: FragmentHabitEditBinding? = null
    private var priorityItem = PriorityHabit.LOW
    private var editHabit: Habit? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHabitEditBinding.bind(view)
        fragmentHabitEditBinding = binding
        editHabit = arguments?.getParcelable<Habit>(EDIT_HABIT)
        if (editHabit != null) {
            editHabitFun(editHabit!!)
            binding.apply {
                addHabitButton.text = resources.getString(R.string.edit_button_text)
                helloTextView.text = resources.getString(R.string.edit_new_habit_text)
                selectedColor.background = editHabit!!.colorHabit.toDrawable()
                selectedColorBtn(editHabit!!.colorHabit.toColor())
            }
        } else if (savedInstanceState != null) {
            binding.apply {
                selectedColor.background =
                    savedInstanceState.getInt(COLOR).toColor().toDrawable()
                selectedColorRGB.text = savedInstanceState.getString(RGB)
                selectedColorHSV.text = savedInstanceState.getString(HSV)
            }
        }
        initSpiner()
        initColorPickerViewGroup()
        addInstallTextWatcher()
        selectedPriority()
        binding.addHabitButton.setOnClickListener {
            val newHabit = newHabit()
            editHabitViewModel.fieldProcess(newHabit, editHabit)
            findNavController().popBackStack()
        }
    }

    //Создание новго элемента Habit из заполненых данных
    private fun newHabit(): Habit {
        var idHabit = this.hashCode()
        if (editHabit != null) {
            idHabit = editHabit!!.id
        }
        return Habit(
            id = idHabit,
            nameHabit = fragmentHabitEditBinding?.editHabitNameText?.text.toString(),
            descriptionHabit = fragmentHabitEditBinding?.editHabitDescriptionText?.text.toString(),
            typeHabit = selectedTypeHabit(),
            numberExecutions = fragmentHabitEditBinding?.editHabitNumberExecutionsText?.text.toString()
                .toInt(),
            priorityHabit = priorityItem,
            periodText = fragmentHabitEditBinding?.editHabitPeriodText?.text.toString(),
            colorHabit = selectedColor()
        )
    }

    // определяем выбранный тип для передачи в поле класса Habit
    private fun selectedTypeHabit(): TypeHabit {
        return if (fragmentHabitEditBinding?.radioPositiveHabit?.isChecked == true) TypeHabit.POSITIVE else TypeHabit.NEGATIVE
    }

    // Определяем выбраный цвет и переводим в число
    private fun selectedColor(): Int {
        return fragmentHabitEditBinding?.selectedColor?.background?.toBitmap(
            2,
            2,
            Bitmap.Config.ARGB_8888
        )?.getPixel(1, 1)?.toColor()?.toArgb()
            ?: return Color.BLACK
    }

    //Оперделяем выбраный приоритет
    private fun selectedPriority() {
        fragmentHabitEditBinding?.spinerPriority?.setOnItemClickListener { parent, _,
                                                                           position, _ ->
            when (parent.getItemAtPosition(position).toString()) {
                PriorityHabit.LOW.str -> {
                    priorityItem = PriorityHabit.LOW
                }
                PriorityHabit.MIDDLE.str -> {
                    priorityItem = PriorityHabit.MIDDLE
                }
                PriorityHabit.HIGH.str -> {
                    priorityItem = PriorityHabit.HIGH
                }
            }
        }
    }

    /*Созадем слушатель вводимого текста и устанавливаем его в View для активации кнопки,
     если все поля заполнены даём пользователю нажать кнопку. */
    private fun addInstallTextWatcher() {
        val textChangedListenerAdd = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                fragmentHabitEditBinding?.addHabitButton?.isEnabled = fieldCheck() ?: false

            }

            override fun afterTextChanged(s: Editable?) {}
        }
        fragmentHabitEditBinding?.apply {
            editHabitNameLayout.editText?.addTextChangedListener(textChangedListenerAdd)
            editHabitDescriptionLayout.editText?.addTextChangedListener(textChangedListenerAdd)
            editHabitNumberExecutionsLayout.editText?.addTextChangedListener(textChangedListenerAdd)
            spinerPriority.addTextChangedListener(textChangedListenerAdd)
            editHabitPeriodLayout.editText?.addTextChangedListener(textChangedListenerAdd)
        }
    }

    //проверяем заолненость полей для активции кнопки
    private fun fieldCheck(): Boolean? {
        return fragmentHabitEditBinding?.let {
            (it.editHabitNameLayout.editText?.length() != 0
                    && it.editHabitDescriptionLayout.editText?.length() != 0
                    && it.editHabitNumberExecutionsLayout.editText?.length() != 0
                    && it.editHabitPeriodLayout.editText?.length() != 0
                    && it.spinerPriority.text.isNotEmpty())
        }
    }


    //Функция запонения полей для редактирования привычки
    private fun editHabitFun(habit: Habit) {
        fragmentHabitEditBinding?.apply {
            addHabitButton.isEnabled = true
            selectedColor.isVisible = true
            editHabitNameText.setText(habit.nameHabit)
            editHabitDescriptionText.setText(habit.descriptionHabit)
            spinerPriority.setText(habit.priorityHabit.str)
            selectedColor.background = habit.colorHabit.toColor().toDrawable()
            editHabitNumberExecutionsText.setText(habit.numberExecutions.toString())
            editHabitPeriodText.setText(habit.periodText)
            if (habit.typeHabit == TypeHabit.POSITIVE) {
                radioPositiveHabit.isChecked = true
            } else {
                radioNegativeHabit.isChecked = true
            }
        }
    }

    //Инициируем спинер
    private fun initSpiner() {
        val priorityList = mutableListOf(
            PriorityHabit.HIGH.str, PriorityHabit.MIDDLE.str, PriorityHabit.LOW.str
        )
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spiner,
            priorityList
        )
        fragmentHabitEditBinding?.spinerPriority?.setAdapter(adapter)
    }

    /* Инициализация ColorPicker, Создаем 16 кнопок, 180*180 отступ 45 устанавливаем в LinerLayout
      из фона лайнер лайаут по получившемся размерам LinerLayout создаем Bitmap для определения цвета кнопки
      для каждой кнопки назначаем ClickListener который передает в ImageView выбраный цвет
      * */
    private fun initColorPickerViewGroup() {
        val layout = fragmentHabitEditBinding?.horizontalLayoutColorPicker
        val widthBtn = 180
        val heightBtn = 180
        val margin = 45
        val param = LinearLayout.LayoutParams(
            widthBtn,
            heightBtn
        )
        param.setMargins(margin, margin, margin, margin)
        val button: MutableList<Button> = mutableListOf()
        for (i in 0..15) {
            val btn = Button(requireContext())
            val param = LinearLayout.LayoutParams(widthBtn, heightBtn)
            param.setMargins(margin, margin, margin, margin)
            btn.layoutParams = param
            button.add(btn)
        }
        val widthGradient = (param.width + param.leftMargin + param.rightMargin) * button.size
        val heightGradient = param.height + param.bottomMargin + param.topMargin
        val bitmap = fragmentHabitEditBinding?.horizontalLayoutColorPicker?.background?.toBitmap(
            widthGradient,
            heightGradient,
            Bitmap.Config.ARGB_8888
        )
        button.forEachIndexed { position: Int, it ->
            val color = bitmap?.getPixel(
                ((position + 1) * it.marginLeft) + ((position) * it.marginRight) + (position * widthBtn) + widthBtn / 2,
                heightGradient / 2
            )?.toColor()
            val backGroundBtn =
                resources.getDrawable(
                    R.drawable.contour_button_shape,
                    requireContext().theme
                ) as GradientDrawable
            if (color != null) {
                backGroundBtn.color = ColorStateList.valueOf(color.toArgb())
            }
            it.background = backGroundBtn
            it.setOnClickListener {
                if (color != null) {
                    selectedColorBtn(color)
                }
            }
            layout?.addView(it)
            if (position == 0 && fragmentHabitEditBinding?.selectedColor?.isVisible == false) {
                if (color != null) {
                    selectedColorBtn(color)
                }
            }
        }
    }

    private fun selectedColorBtn(color: Color) {
        //Создаем массивы под цвет RGB и HSV и передаем в них данные выбронного цвета
        val colorHsv = FloatArray(3)
        Color.colorToHSV(color.toArgb(), colorHsv)
        val colorRgb = arrayOf(color.red(), color.green(), color.blue())
        //Устанавливаем в Image View выбраный цвет и делаем View видимой при присвоении цвета
        fragmentHabitEditBinding?.apply {
            selectedColor.background = color.toDrawable()
            selectedColor.isVisible = true
            //преобразуем к чиатему виду массивы со занчением цвета и выводим в текстовые поля
            // Функция getRgbNumber преобразует проценты в число для RGB
            selectedColorRGB.text =
                "${getRgbNumber(colorRgb[0])} ${getRgbNumber(colorRgb[1])} ${
                    getRgbNumber(colorRgb[2])
                }"
            selectedColorHSV.text =
                "${colorHsv[0].toInt()} ${(colorHsv[1] * 100).toInt()}% ${(colorHsv[2] * 100).toInt()}%"
        }
    }

    // Преобразуем процентное соотношение RGB цвета в читаем вид
    private fun getRgbNumber(colorRgb: Float) = (((colorRgb * 100) * 255) / 100).toInt()


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(
            COLOR, selectedColor()
        )
        fragmentHabitEditBinding?.apply {
            outState.putString(RGB, selectedColorRGB.text.toString())
            outState.putString(HSV, selectedColorHSV.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val COLOR = "color"
        private const val EDIT_HABIT = "edit_habit"
        private const val RGB = "rgb_text"
        private const val HSV = "hsv_text"
    }

}


