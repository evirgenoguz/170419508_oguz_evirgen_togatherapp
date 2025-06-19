package com.evirgenoguz.core.presentation.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.evirgenoguz.core.presentation.R

class CounterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var counter = 1
    private var minValue = 1
    private var maxValue = Int.MAX_VALUE

    private val increaseButton: ImageButton
    private val decreaseButton: ImageButton
    private val counterText: TextView

    var onValueChanged: ((Int) -> Unit)? = null

    init {
        orientation = HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.view_counter, this, true)

        increaseButton = findViewById(R.id.buttonIncrease)
        decreaseButton = findViewById(R.id.buttonDecrease)
        counterText = findViewById(R.id.textViewCounter)

        updateUI()

        increaseButton.setOnClickListener {
            if (counter < maxValue) {
                counter++
                updateUI()
                onValueChanged?.invoke(counter)
            }
        }

        decreaseButton.setOnClickListener {
            if (counter > minValue) {
                counter--
                updateUI()
                onValueChanged?.invoke(counter)
            }
        }
    }

    private fun updateUI() {
        counterText.text = counter.toString()
        decreaseButton.isEnabled = counter > minValue
        increaseButton.isEnabled = counter < maxValue
    }

    fun getValue(): Int = counter

    fun setValue(value: Int) {
        counter = value.coerceIn(minValue, maxValue)
        updateUI()
    }

    fun setRange(min: Int, max: Int) {
        minValue = min
        maxValue = max
        counter = counter.coerceIn(min, max)
        updateUI()
    }
}
