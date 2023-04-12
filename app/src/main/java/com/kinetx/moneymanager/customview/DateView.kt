package com.kinetx.moneymanager.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.kinetx.moneymanager.R

class DateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
)    : androidx.appcompat.widget.AppCompatImageButton(context, attrs, defStyleAttr) {


    private var day : String = "1"
        set(value) {
            field = value
        }
    private var month : String = "Apr"
    private var year : String = "2023"


    private val paintDay = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 120.0f
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }
    private val paintMonth = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 50.0f
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }
    private val paintYear = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 30.0f
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }

    init {
        context.withStyledAttributes(attrs,R.styleable.DateView)
        {
            day = getString(R.styleable.DateView_day).toString()
            month = getString(R.styleable.DateView_month).toString()
            year = getString(R.styleable.DateView_year).toString()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawText(day,(width/2).toFloat(),(120).toFloat(),paintDay)
        canvas?.drawText(month,(width/2).toFloat(),(170).toFloat(),paintMonth)
        canvas?.drawText(year,(width/2).toFloat(),(220).toFloat(),paintYear)
    }

    fun setTextDay(d: String)
    {
        day = d
        invalidate()
    }

    fun setTextMonth(m: String)
    {
        month = m
        invalidate()
    }

    fun setTextYear(y: String)
    {
        year = y
        invalidate()
    }
}

@BindingAdapter("app:day")
fun setDay(view: DateView, text: LiveData<String>)
{
    view.setTextDay(text.value.toString())
}

@BindingAdapter("app:month")
fun setMonth(view: DateView, text: LiveData<String>)
{
    view.setTextMonth(text.value.toString())
}

@BindingAdapter("app:year")
fun setYear(view: DateView, text: LiveData<String>)
{
    view.setTextYear(text.value.toString())
}