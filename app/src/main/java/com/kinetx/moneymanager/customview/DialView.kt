package com.kinetx.moneymanager.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.kinetx.moneymanager.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35



class DialView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : View(context, attrs, defStyleAttr) {

    private var radius = 0.0f                   // Radius of the circle.
    private var fanSpeed = 0.0f         // The active selection.
    // position variable which will be used to draw label and indicator circle position
    private val pointPosition: PointF = PointF(0.0f, 0.0f)
    private var expenses : Float = 0.0f
    private var balance : Float = 0.0f
    private var currency : String = ""
    init {

        context.withStyledAttributes(attrs,R.styleable.DialView)
        {
            expenses = getFloat(R.styleable.DialView_expenses,0.0f)
            balance = getFloat(R.styleable.DialView_balance, 0.0f)
            currency = getString(R.styleable.DialView_currency).toString()
        }
    }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create( "", Typeface.BOLD)
    }

    private val textCircle = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 40.0f
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }

    private val numberCircle = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 30.0f
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    private fun PointF.computeXYForSpeed(ratio: Float, radius: Float) {
        // Angles are in radians.
        val ratioFinal = min(ratio,1.0f)
        val startAngle = -Math.PI/2.0
        val angle = startAngle + ratioFinal * (2*Math.PI)
        x = (radius * cos(angle)).toFloat() + width / 2
        y = (radius * sin(angle)).toFloat() + height / 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (balance>0)
        {
            paint.color = ContextCompat.getColor(context,R.color.air_force_blue)
        }
        else
        {
            paint.color = ContextCompat.getColor(context,R.color.red)
        }
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
        canvas?.drawText("Expenses",(width / 2).toFloat(), (height / 2-100).toFloat(),textCircle)
        canvas?.drawText("$expenses $currency",(width / 2).toFloat(), (height / 2-50).toFloat(),numberCircle)
        canvas?.drawText("Balance",(width / 2).toFloat(), (height / 2+50).toFloat(),textCircle)
        canvas?.drawText("$balance $currency",(width / 2).toFloat(), (height / 2+100).toFloat(),numberCircle)

    }
}