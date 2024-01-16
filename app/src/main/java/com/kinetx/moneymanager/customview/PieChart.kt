package com.kinetx.moneymanager.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.kinetx.moneymanager.dataclass.PieChartData
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

class PieChart@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
)    : View(context, attrs, defStyleAttr)  {



    var radiusInner : Float = 0.0f
    var radiusOuter : Float = 0.0f

    var pieData : List<PieChartData> = emptyList()


    private val paintOuter = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GREEN
    }

    private val paintLegendPie = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }

    private val paintLegend = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.LEFT
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }

    private val paintLegendValue = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.RIGHT
        typeface = Typeface.create( "", Typeface.BOLD)
        color = Color.WHITE
    }

    private val paintLegendBox = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }




    init {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var currentAngle : Float = 270f

        val total = pieData.map {
            it.values
        }.sum()

        val smallestValue = pieData.map {
            it.values
        }.min()

        val minSmallestValue = max(smallestValue/total,0.1f)
        val legendScale = 0.5f*minSmallestValue

        val textSize = minOf(width,height)*legendScale
        val cx = (width/2).toFloat()
        val cy = (width/2).toFloat()
        paintLegendPie.textSize = textSize
        val rectSize = width/10
        val leftXLegend = 0
        val legendYSpacing = 1.5f
        val legendXSpacing = 1.2f

        var count = 1
        for(data in pieData)
        {
            val sweepAngle = data.values/total*360f
            paintOuter.color = data.color
            canvas?.drawArc((0.05*width).toFloat(),(0.05*height).toFloat(),(0.95*width).toFloat(),(0.95*width).toFloat(),currentAngle,sweepAngle,true,paintOuter)
            val textAngle = (currentAngle+sweepAngle/2)*Math.PI/180
            val textX = (cx+0.6*radiusOuter* cos(textAngle)).toFloat()
            val textY = (cy+0.6*radiusOuter* sin(textAngle)).toFloat()
            val percentage = (data.values/total*100).toInt()
            if (percentage>=8) {
                canvas?.drawText("$percentage %", textX, textY, paintLegendPie)
            }
            paintLegendBox.color = data.color
            paintLegend.textSize = (rectSize*0.9).toFloat()
            paintLegendValue.textSize = (rectSize*0.9).toFloat()

            canvas?.drawRect(leftXLegend.toFloat(),width+count*rectSize*legendYSpacing,(leftXLegend+rectSize).toFloat(),width+count*rectSize*legendYSpacing+rectSize,paintLegendBox)
            canvas?.drawText(data.name,(leftXLegend+rectSize*legendXSpacing),width+count*rectSize*legendYSpacing+rectSize*0.8f,paintLegend)
            canvas?.drawText("$percentage%", (width).toFloat(), width+count*rectSize*legendYSpacing+0.8f*rectSize, paintLegendValue)
            currentAngle += sweepAngle
            count++
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radiusInner = (minOf(width,height)/2.0*0.8).toFloat()
        radiusOuter = (minOf(width,height)/2.0*0.9).toFloat()
    }

    fun setPieChartData(pData : List<PieChartData>)
    {
        pieData = pData
    }
}

@BindingAdapter("pieData")
fun setPieChartDataB(view: PieChart, pData : LiveData<List<PieChartData>>)
{
    pData.value?.let { view.setPieChartData(it) }
}