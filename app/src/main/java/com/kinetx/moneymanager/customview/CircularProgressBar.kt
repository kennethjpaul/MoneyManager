package com.kinetx.moneymanager.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.toColorInt
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.kinetx.moneymanager.R
import com.kinetx.moneymanager.helpers.CommonOperations
import kotlin.math.round

class CircularProgressBar@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
)    : View(context, attrs, defStyleAttr) {


    var radiusInner : Float = 0.0f
    var radiusOuter : Float = 0.0f
    var percentage : Float = 0.0f
    var balanceValue : String = ""

    private val paintInner = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }
    private val paintOuter = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GREEN
    }
    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.BLACK
    }

    init {

        context.withStyledAttributes(attrs, R.styleable.CircularProgressBar)
        {
            try
            {
                paintInner.color = getString(R.styleable.CircularProgressBar_innerCircleColor).toString().toColorInt()
            }
            catch (e: IllegalArgumentException)
            {
                val value = TypedValue()
                context.theme.resolveAttribute(android.R.attr.colorBackground,value,true)
                paintInner.color = value.data
            }
            try
            {
                paintOuter.color = getString(R.styleable.CircularProgressBar_seekBarColor).toString().toColorInt()
            }
            catch (e:IllegalArgumentException)
            {
                paintOuter.color = Color.GREEN
            }
            try
            {
                paintText.color = getString(R.styleable.CircularProgressBar_seekBarTextColor).toString().toColorInt()
            }
            catch (e:IllegalArgumentException)
            {
                val value = TypedValue()
                context.theme.resolveAttribute(android.R.attr.colorForeground,value,true)
                paintText.color = value.data
            }

            balanceValue = try {
                getString(R.styleable.CircularProgressBar_seekTextValue).toString()
            } catch (e:IllegalArgumentException) {
                ""
            }

            percentage = try {
                getString(R.styleable.CircularProgressBar_seekPercentage).toString().toFloat()
            } catch (e:IllegalArgumentException) {
                0f
            }


        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radiusInner = (minOf(width,height)/2.0*0.8).toFloat()
        radiusOuter = (minOf(width,height)/2.0*0.9).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val sweepAngle = (percentage/100*360)
        val textSizeBase = (minOf(width,height)/12).toFloat()

        val balanceText = "Balance"

        val textSizeValue = textSizeBase*1.0f
        val textSizeText = textSizeBase*0.8f


        canvas?.drawArc((0.05*width).toFloat(),(0.05*height).toFloat(),(0.95*width).toFloat(),(0.95*width).toFloat(),270f,sweepAngle,true,paintOuter)
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radiusInner, paintInner)


        paintText.textSize = textSizeValue
        val textVertical = (height/2).toFloat() + textSizeValue/2
        canvas?.drawText("$balanceValue CHF",(width/2).toFloat(),textVertical,paintText)
        paintText.textSize = textSizeText
        canvas?.drawText(balanceText,(width/2).toFloat(),textVertical-textSizeValue-textSizeText/2,paintText)

//        val roundedPercent = round(percentage*100) /100
//        val expenseValue ="20000 CHF"
//        val expenseText = "Expense"
//        canvas?.drawText(expenseValue,(width/2).toFloat(),textVertical-(height/5),paintText)
//        canvas?.drawText(expenseText,(width/2).toFloat(),textVertical-(height/3.5f),paintText)
    }

    fun setPercentageText(p: String)
    {
        percentage = p.toFloat()
        invalidate()
    }

    fun setText(p: String)
    {
        balanceValue = p
        invalidate()
    }
}

@BindingAdapter("seekPercentage")
fun setPercentage(view: CircularProgressBar, text : LiveData<Float>)
{
    view.setPercentageText(text.value.toString())
}

@BindingAdapter("seekTextValue")
fun setSeekText(view: CircularProgressBar, text : LiveData<Float>)
{
    view.setText(text.value.toString())
}