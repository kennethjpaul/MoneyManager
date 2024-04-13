package com.kinetx.moneymanager.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.toColorInt
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.kinetx.moneymanager.R
import kotlin.math.round

class CircularProgressBarDefault@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
)    : View(context, attrs, defStyleAttr) {


    var radiusInner : Float = 0.0f
    var radiusOuter : Float = 0.0f
    var percentage : Float = 0.0f

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
        val textSize = (minOf(width,height)/6).toFloat()
        val roundedPercent = round(percentage*100) /100

        val textCenter = "$roundedPercent %"
        val textVertical = (height/2).toFloat() + textSize/2
        paintText.textSize = textSize

        canvas?.drawArc((0.05*width).toFloat(),(0.05*height).toFloat(),(0.95*width).toFloat(),(0.95*width).toFloat(),270f,sweepAngle,true,paintOuter)
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radiusInner, paintInner)
        canvas?.drawText(textCenter,(width/2).toFloat(),textVertical,paintText)

    }

    fun setPercentageText(p: String)
    {
        percentage = p.toFloat()
        invalidate()
    }
}

@BindingAdapter("seekPercentage")
fun setPercentage(view: CircularProgressBarDefault, text : LiveData<String>)
{
    view.setPercentageText(text.value.toString())
}