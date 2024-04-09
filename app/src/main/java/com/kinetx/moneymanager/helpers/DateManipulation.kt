package com.kinetx.moneymanager.helpers

import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import java.time.Year
import kotlin.math.min

object DateManipulation {

    fun resetToMidnight(myCalendar: Calendar): Calendar {
        myCalendar.set(Calendar.HOUR_OF_DAY,0)
        myCalendar.set(Calendar.MINUTE,0)
        myCalendar.set(Calendar.SECOND,0)
        myCalendar.set(Calendar.MILLISECOND,0)

        return myCalendar
    }


    fun getStartOfWeek(myCalendar: Calendar) : Calendar
    {

        val startOfWeekCalendar = myCalendar.clone() as Calendar
        val shift = startOfWeekCalendar.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY // TODO : Adaptive start and end of the week
        startOfWeekCalendar.add(Calendar.DAY_OF_MONTH, -shift)
        return startOfWeekCalendar
    }


    fun getEndOfWeek(myCalendar: Calendar): Calendar
    {
        val endOfWeekCalendar = myCalendar.clone() as Calendar
        val shift = Calendar.SATURDAY -endOfWeekCalendar.get(Calendar.DAY_OF_WEEK) // TODO : Adaptive start and end of the week
        endOfWeekCalendar.add(Calendar.DAY_OF_MONTH, shift)
        return endOfWeekCalendar
    }

    fun getStartOfMonth(myCalendar: Calendar, startOfMonth : Int, weekendEnabled: Boolean, weekendShift : Int) : Calendar
    {
        var adjustedCalendar = myCalendar.clone() as Calendar


        val day  = myCalendar.get(Calendar.DAY_OF_MONTH)

        if (day < startOfMonth) {
            adjustedCalendar.add(Calendar.MONTH, -1)
        }
        val maxDays = adjustedCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        adjustedCalendar.set(Calendar.DAY_OF_MONTH, min(maxDays, startOfMonth))

        adjustedCalendar = checkWeekend(adjustedCalendar,weekendEnabled,weekendShift)


        return adjustedCalendar

    }


    fun isWeekend(myCalendar: Calendar, weekendList :List<Int>) : Boolean
    {
        val curWeekDay = myCalendar.get(Calendar.DAY_OF_WEEK)

        if (curWeekDay in weekendList)
        {
            return true
        }

        return false
    }



    private fun checkWeekend(myCalendar: Calendar, weekendEnabled: Boolean, weekendShift: Int) : Calendar
    {
        var newCalendar = myCalendar

        if (weekendEnabled)
        {
            val weekendList = listOf(Calendar.SATURDAY, Calendar.SUNDAY)
            // Simplified list subtraction
            val weekDayList = listOf(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY) - weekendList

            val curWeekDay = myCalendar.get(Calendar.DAY_OF_WEEK)

            if (curWeekDay in weekendList) {
                val shift: Int
                val targetDay = when (weekendShift) {
                    1 -> weekDayList.firstOrNull { it > curWeekDay } ?: weekDayList.first()
                    -1 -> weekDayList.lastOrNull { it < curWeekDay } ?: weekDayList.last()
                    else -> null
                }

                shift = when (weekendShift) {
                    1 -> if (targetDay != null) (targetDay - curWeekDay).let { if (it < 0) it + 7 else it } else 0
                    -1 -> if (targetDay != null) (curWeekDay - targetDay).let { if (it < 0) it + 7 else it } else 0
                    else -> 0
                }

                newCalendar = changeDayByN(myCalendar,shift,weekendShift)

            }

        }

        return newCalendar
    }


    fun getEndOfMonth(myCalendar: Calendar, startOfMonth : Int, weekendEnabled: Boolean, weekendShift : Int) : Calendar
    {
        val endMonthCalendar = myCalendar.clone() as Calendar

        val day = myCalendar[Calendar.DAY_OF_MONTH]
        var month = myCalendar[Calendar.MONTH]
        var year = myCalendar[Calendar.YEAR]

        if (day >= startOfMonth) {
            if (month == Calendar.DECEMBER) {
                month = Calendar.JANUARY
                year++
            } else {
                month++
            }
        }

        // Set the adjusted month and year
        endMonthCalendar[Calendar.YEAR] = year
        endMonthCalendar[Calendar.MONTH] = month

        val maxDays = endMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        endMonthCalendar[Calendar.DAY_OF_MONTH] = min(maxDays, startOfMonth)

        val adjustedCalendar = checkWeekend(endMonthCalendar, weekendEnabled, weekendShift)
        adjustedCalendar.add(Calendar.DAY_OF_MONTH, -1)

        return adjustedCalendar

    }

    fun changeDayByN(myCalendar: Calendar,n : Int,direction: Int) : Calendar
    {
        val adjustedCalendar = myCalendar.clone() as Calendar
        val totalShift = direction * n
        adjustedCalendar.add(Calendar.DAY_OF_MONTH, totalShift)
        return adjustedCalendar
    }

    fun changeMonthByN(myCalendar: Calendar,n : Int,direction: Int) : Calendar
    {
        myCalendar.add(Calendar.MONTH, direction * n)
        myCalendar.set(Calendar.DAY_OF_MONTH, 1)
        return myCalendar
    }

    fun changeYearByN(myCalendar: Calendar,n : Int,direction: Int) : Calendar
    {
        val totalShift = direction * n
        myCalendar.add(Calendar.YEAR,totalShift)
        return myCalendar
    }

    fun getDateArray(calendar: Calendar): String
    {
        val day : String = calendar.get(Calendar.DAY_OF_MONTH).toString()
        val month: String = CommonOperations.monthArray[calendar.get(Calendar.MONTH)]
        val year : String = calendar.get(Calendar.YEAR).toString()

        return "$day $month $year"
    }
}