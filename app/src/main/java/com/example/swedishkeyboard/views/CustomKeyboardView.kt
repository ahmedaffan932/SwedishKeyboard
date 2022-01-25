package com.example.swedishkeyboard.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.inputmethodservice.KeyboardView
import android.util.AttributeSet
import com.example.swedishkeyboard.R
import com.example.swedishkeyboard.services.CustomInputMethodService
import com.example.swedishkeyboard.utils.KeyboardClass

class CustomKeyboardView : KeyboardView {

    var customInputMethodService: CustomInputMethodService? = null

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        customInputMethodService = context as CustomInputMethodService

    }

    constructor(context: Context, attributeSet: AttributeSet?, i: Int) : super(
        context,
        attributeSet,
        i
    )

    override fun getKeyboard(): KeyboardClass {

        return (super.getKeyboard() as KeyboardClass)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = resources.getDimensionPixelSize(R.dimen.fab_padding).toFloat()
        paint.color = -1
    }

    override fun setMinimumHeight(minHeight: Int) {
        super.setMinimumHeight(70)
    }

}