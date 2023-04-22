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

class CustomDateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
)    : androidx.appcompat.widget.AppCompatImageButton(context, attrs, defStyleAttr)
{

    private var day : String        = ""
    private var weekStart : String  = ""
    private var weekEnd : String    = ""
    private var weekStartMonth : String  = ""
    private var weekEndMonth : String    = ""
    private var month : String      = ""
    private var year : String       = ""
    private var viewType : Int      = 2

    private val paintDay0 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 120.0f
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }

    private val paintMonth0 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 50.0f
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }
    private val paintYear0 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 30.0f
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }

    private val paintWeek1 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 40.0f
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }

    private val paintMonth2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 120.0f
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }
    private val paintYear2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 50.0f
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }

    private val paintYear3 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 100.0f
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }

    init
    {
        context.withStyledAttributes(attrs, R.styleable.CustomDateView)
        {
            day = getString(R.styleable.CustomDateView_day_c).toString()
            month = getString(R.styleable.CustomDateView_month_c).toString()
            year = getString(R.styleable.CustomDateView_year_c).toString()
            weekStart = getString(R.styleable.CustomDateView_weekStart_c).toString()
            weekEnd = getString(R.styleable.CustomDateView_weekEnd_c).toString()
            viewType = getInt(R.styleable.CustomDateView_type_c,2)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        when(viewType)
        {
            0 ->
            {
                canvas?.drawText(day,(width/2).toFloat(),(120).toFloat(),paintDay0)
                canvas?.drawText(month,(width/2).toFloat(),(170).toFloat(),paintMonth0)
                canvas?.drawText(year,(width/2).toFloat(),(220).toFloat(),paintYear0)
            }

            1 ->
            {
                canvas?.drawText("$weekStartMonth $weekStart",(width/2).toFloat(),(50).toFloat(),paintWeek1)
                canvas?.drawText("to",(width/2).toFloat(),(100).toFloat(),paintWeek1)
                canvas?.drawText("$weekEndMonth $weekEnd",(width/2).toFloat(),(150).toFloat(),paintWeek1)
                canvas?.drawText(year,(width/2).toFloat(),(220).toFloat(),paintMonth0)
            }

            2->
            {
                canvas?.drawText(month,(width/2).toFloat(),(120).toFloat(),paintMonth2)
                canvas?.drawText(year,(width/2).toFloat(),(200).toFloat(),paintYear2)
            }

            3->
            {
                canvas?.drawText(year,(width/2).toFloat(),(150).toFloat(),paintYear3)
            }
        }
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

    fun setTextWeekStart(ws: String)
    {
        weekStart = ws
        invalidate()
    }

    fun setTextWeekEnd(we: String)
    {
        weekEnd = we
        invalidate()
    }
    fun setTextWeekStartMonth(wsm: String)
    {
        weekStartMonth = wsm
        invalidate()
    }

    fun setTextWeekEndMonth(wem: String)
    {
        weekEndMonth = wem
        invalidate()
    }

    fun setViewType(type: Int)
    {
        viewType = type
        invalidate()
    }

}


@BindingAdapter("app:day_c")
fun setDay(view: CustomDateView, text: LiveData<String>)
{
    view.setTextDay(text.value.toString())
}

@BindingAdapter("app:month_c")
fun setMonth(view: CustomDateView, text: LiveData<String>)
{
    view.setTextMonth(text.value.toString())
}

@BindingAdapter("app:year_c")
fun setYear(view: CustomDateView, text: LiveData<String>)
{
    view.setTextYear(text.value.toString())
}

@BindingAdapter("app:weekStart_c")
fun setWeekStart(view: CustomDateView, text: LiveData<String>)
{
    view.setTextWeekStart(text.value.toString())
}

@BindingAdapter("app:weekEnd_c")
fun setWeekEnd(view: CustomDateView, text: LiveData<String>)
{
    view.setTextWeekEnd(text.value.toString())
}

@BindingAdapter("app:weekStartMonth_c")
fun setWeekStartMonth(view: CustomDateView, text: LiveData<String>)
{
    view.setTextWeekStartMonth(text.value.toString())
}

@BindingAdapter("app:weekEndMonth_c")
fun setWeekEndMonth(view: CustomDateView, text: LiveData<String>)
{
    view.setTextWeekEndMonth(text.value.toString())
}

@BindingAdapter("app:type_c")
fun setViewType(view: CustomDateView, text: LiveData<Int>)
{
    view.setViewType(text.value!!.toInt())
}

