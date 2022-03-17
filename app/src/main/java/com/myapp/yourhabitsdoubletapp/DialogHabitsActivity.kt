package com.myapp.yourhabitsdoubletapp

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColor
import androidx.core.view.isVisible
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.myapp.yourhabitsdoubletapp.databinding.ActivityHabitDialogBinding


class DialogHabitsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHabitDialogBinding
    private lateinit var habit: Habit
    private var priorityItem = PriorityHabit.LOW
    private var flag: Boolean = true

    override fun onResume() {
        super.onResume()
        initSpiner()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitDialogBinding.inflate(layoutInflater)
        val view = binding.root
        binding.addHabitButton.isEnabled = false
        binding.selectedColor.isVisible = false
        val editHabit = intent.getParcelableExtra<Habit>(HABIT)
        if (editHabit != null) {
            editHabitFun(editHabit)
            binding.addHabitButton.text = resources.getString(R.string.edit_button_text)
            binding.helloTextView.text = resources.getString(R.string.edit_new_habit_text)
            flag = false
        }
        setContentView(view)
        initSpiner()
        initColorPickerViewGroup()
        addInstallTextWatcher()
        selectedPriority()
        binding.addHabitButton.setOnClickListener {
            //если flag true возвращаем ResultCode 1 - созадние новго элемента списка
            if (flag) {
                val newHabit = newHabit()
                setResult(1, Intent().putExtra(NEW, newHabit))
                finish()
            }
            //если flag false возвращаем ResultCode 2 - редактирование ранее созданого элемента списка
            else {
                val newHabit = newHabit()
                newHabit.id=editHabit!!.id
                setResult(2, Intent().putExtra(HABIT, newHabit))
                finish()
            }
        }
    }

//Создание новго элемента Habit из заполненых данных
    private fun newHabit(): Habit {
       return Habit(
           /*Назначаем id равное хэш коду активити, врят ли повторится думаю надежнее чем Random.Int
           далее переопреелять id не будем
           */
            id = this.hashCode(),
            nameHabit = binding.editHabitNameText.text.toString(),
            descriptionHabit = binding.editHabitDescriptionText.text.toString(),
            typeHabit = selectedTypeHabit(),
            numberExecutions = binding.editHabitNumberExecutionsText.text.toString().toInt(),
            priorityHabit = priorityItem,
            periodText = binding.editHabitPeriodText.text.toString(),
            colorHabit = selectedColor()
        )
    }

    // определяем выбранный тип для передачи в поле класса Habit
    private fun selectedTypeHabit(): TypeHabit {
        return if (binding.radioPositiveHabit.isChecked) TypeHabit.POSITIVE else TypeHabit.NEGATIVE
    }
// Определяем выбраный цвет и переводим в число
    private fun selectedColor(): Int {
        return binding.selectedColor.background.toBitmap(
            2,
            2,
            Bitmap.Config.ARGB_8888
        ).getPixel(1, 1).toColor().toArgb()

    }
//Оперделяем выбраный приоритет
    private fun selectedPriority() {
        binding.spinerPriority.setOnItemClickListener { parent, _,
                                                        position, _ ->
            val item = parent.getItemAtPosition(position).toString()
            when (item) {
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
                binding.addHabitButton.isEnabled = fieldCheck()

            }

            override fun afterTextChanged(s: Editable?) {}
        }
        binding.editHabitNameLayout.editText?.addTextChangedListener(textChangedListenerAdd)
        binding.editHabitDescriptionLayout.editText?.addTextChangedListener(textChangedListenerAdd)
        binding.editHabitNumberExecutionsLayout.editText?.addTextChangedListener(
            textChangedListenerAdd
        )
        binding.spinerPriority.addTextChangedListener(textChangedListenerAdd)
        binding.editHabitPeriodLayout.editText?.addTextChangedListener(textChangedListenerAdd)
    }


    //Функция запонения полей для редактирования привычки
    private fun editHabitFun(habit: Habit) {
        binding.addHabitButton.isEnabled = true
        binding.selectedColor.isVisible = true
        binding.editHabitNameText.setText(habit.nameHabit)
        binding.editHabitDescriptionText.setText(habit.descriptionHabit)
        binding.spinerPriority.setText(habit.priorityHabit.str)
        binding.selectedColor.background = habit.colorHabit.toColor().toDrawable()
        binding.editHabitNumberExecutionsText.setText(habit.numberExecutions.toString())
        binding.editHabitPeriodText.setText(habit.periodText)
        if (habit.typeHabit == TypeHabit.POSITIVE) {
            binding.radioPositiveHabit.isChecked = true
        } else {
            binding.radioNegativeHabit.isChecked = true
        }
    }
//Инициируем спинер
    private fun initSpiner() {
        val priorityList = mutableListOf(
            PriorityHabit.HIGH.str, PriorityHabit.MIDDLE.str, PriorityHabit.LOW.str
        )
        var adapter = ArrayAdapter(
            this,
            R.layout.item_spiner,
            priorityList
        )
        binding.spinerPriority.setAdapter(adapter)
    }

/* Инициализация ColorPicker, Создаем 16 кнопок, 180*180 отступ 45 устанавливаем в LinerLayout
  из фона лайнер лайаут по получившемся размерам LinerLayout создаем Bitmap для определения цвета кнопки
  для каждой кнопки назначаем ClickListener который передает в ImageView выбраный цвет
  * */
    private fun initColorPickerViewGroup() {
        val layout = binding.horizontalLayoutColorPicker
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
            val btn = Button(this)
            val param = LinearLayout.LayoutParams(widthBtn, heightBtn)
            param.setMargins(margin, margin, margin, margin)
            btn.layoutParams = param
            button.add(btn)
        }
        val widthGradient = (param.width + param.leftMargin + param.rightMargin) * button.size
        val heightGradient = param.height + param.bottomMargin + param.topMargin
        val bitmap = binding.horizontalLayoutColorPicker.background.toBitmap(
            widthGradient,
            heightGradient,
            Bitmap.Config.ARGB_8888
        )
        button.forEachIndexed { position: Int, it ->
            val color = bitmap.getPixel(
                ((position + 1) * it.marginLeft) + ((position) * it.marginRight) + (position * widthBtn) + widthBtn / 2,
                heightGradient / 2
            ).toColor()
            val backGroundBtn =
                resources.getDrawable(R.drawable.contour_button_shape, theme) as GradientDrawable
            backGroundBtn.color = ColorStateList.valueOf(color.toArgb())
            it.background = backGroundBtn
            it.setOnClickListener {
                selectedColorBtn(color)
            }
            layout.addView(it)
            if (position == 0 && !binding.selectedColor.isVisible) {
                selectedColorBtn(color)
            }
        }
    }

    private fun selectedColorBtn(color: Color) {
        //Создаем массивы под цвет RGB и HSV и передаем в них данные выбронного цвета
        val colorHsv = FloatArray(3)
        Color.colorToHSV(color.toArgb(), colorHsv)
        val colorRgb = arrayOf(color.red(), color.green(), color.blue())
        //Устанавливаем в Image View выбраный цвет и делаем View видимой при присвоении цвета
        binding.selectedColor.background = color.toDrawable()
        binding.selectedColor.isVisible = true

        //преобразуем к чиатему виду массивы со занчением цвета и выводим в текстовые поля
        // Функция getRgbNumber преобразует проценты в число для RGB
        binding.selectedColorRGB.text =
            "${getRgbNumber(colorRgb[0])} ${getRgbNumber(colorRgb[1])} ${
                getRgbNumber(colorRgb[2])
            }"
        binding.selectedColorHSV.text =
            "${colorHsv[0].toInt()} ${(colorHsv[1] * 100).toInt()}% ${(colorHsv[2] * 100).toInt()}%"
    }
// Преобразуем процентное соотношение RGB цвета в читаем вид
    private fun getRgbNumber(colorRgb: Float) = (((colorRgb * 100) * 255) / 100).toInt()

    //проверяем заолненость полей для активции кнопки
    private fun fieldCheck(): Boolean {
        return (binding.editHabitNameLayout.editText?.length() != 0
                && binding.editHabitDescriptionLayout.editText?.length() != 0
                && binding.editHabitNumberExecutionsLayout.editText?.length() != 0
                && binding.editHabitPeriodLayout.editText?.length() != 0
                && binding.spinerPriority.text.isNotEmpty()
                )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(
            COLOR, selectedColor()
        )
        outState.putString(RGB,binding.selectedColorRGB.text.toString())
        outState.putString(HSV,binding.selectedColorHSV.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        binding.selectedColor.background = savedInstanceState.getInt(COLOR).toColor().toDrawable()
        binding.selectedColorRGB.text=savedInstanceState.getString(RGB)
        binding.selectedColorHSV.text=savedInstanceState.getString(HSV)
        super.onRestoreInstanceState(savedInstanceState)
    }

    companion object {
        private const val COLOR = "color"
        private const val HABIT = "habit"
        private const val NEW = "new_habit"
        private const val RGB = "rgb_text"
        private const val HSV = "hsv_text"


// Создаем Интент для передачи habit для его редактирования
        fun newIntent(context: Context, habit: Habit): Intent {
            return Intent(context, DialogHabitsActivity::class.java).apply {
                putExtra(HABIT, habit)
            }
        }
    }

}