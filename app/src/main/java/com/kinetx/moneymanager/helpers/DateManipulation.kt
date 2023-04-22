package com.kinetx.moneymanager.helpers

import android.icu.util.Calendar
import android.util.Log

object DateManipulation {

    fun resetToMidnight(myCalendar: Calendar): Calendar {
        myCalendar.set(Calendar.HOUR_OF_DAY,0)
        myCalendar.set(Calendar.MINUTE,0)
        myCalendar.set(Calendar.SECOND,0)
        myCalendar.set(Calendar.MILLISECOND,0)

        return myCalendar
    }


    fun getStartOfWeek(myCalendar: Calendar) : Pair< Int, Int>
    {
        var calendarNew: Calendar = Calendar.getInstance()
        calendarNew = copyDayMonthYear(calendarNew,myCalendar)

        val dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK)
        val shift = dayOfWeek-1

        var day = myCalendar.get(Calendar.DAY_OF_MONTH)-shift
        var month = myCalendar.get(Calendar.MONTH)
        if (day<=0 && month>0)
        {
            month -= 1
            calendarNew.set(Calendar.MONTH,month)
            val maxDays = calendarNew.getActualMaximum(Calendar.DAY_OF_MONTH)
            day += maxDays
        }
        else if(day<=0&& month==0)
        {
            day = 1
        }

        return Pair(day,month)
    }


    fun getEndOfWeek(myCalendar: Calendar): Pair<Int,Int>
    {
        val dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK)
        val shift = dayOfWeek-7

        var day = myCalendar.get(Calendar.DAY_OF_MONTH)+shift
        var month = myCalendar.get(Calendar.MONTH)
        val maxDays = myCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        if(day>maxDays && month<11)
        {
            month+=1
            day -= maxDays
        }
        else if(day>maxDays && month ==11)
        {
            day = 31
        }
        return Pair(day,month)
    }

    fun changeDayByN(myCalendar: Calendar,n : Int,direction: Int) : Calendar
    {
        var calendarNew: Calendar = Calendar.getInstance()
        calendarNew = copyDayMonthYear(calendarNew,myCalendar)

        val day = calendarNew.get(Calendar.DAY_OF_MONTH)
        var month = calendarNew.get(Calendar.MONTH)
        var year = calendarNew.get(Calendar.YEAR)
        val maxDays = calendarNew.getActualMaximum(Calendar.DAY_OF_MONTH)
        var shiftedDay = day + direction*n
        if (shiftedDay<=0 && month>0)
        {
            month-=1
            calendarNew.set(Calendar.MONTH,month)
            val maxDaysNew = calendarNew.getActualMaximum(Calendar.DAY_OF_MONTH)
            shiftedDay += maxDaysNew
        }
        else if(shiftedDay<=0 && month==0)
        {
            month = 11
            year-=1
            calendarNew.set(Calendar.MONTH,month)
            calendarNew.set(Calendar.YEAR,year)
            val maxDaysNew = calendarNew.getActualMaximum(Calendar.DAY_OF_MONTH)
            shiftedDay += maxDaysNew
        }

        else if(shiftedDay>maxDays && month <11)
        {
            month +=1
            calendarNew.set(Calendar.MONTH,month)
            shiftedDay -= maxDays
        }
        else if (shiftedDay>maxDays && month ==11)
        {
            month = 0
            year +=1
            calendarNew.set(Calendar.MONTH,month)
            calendarNew.set(Calendar.YEAR,year)
            shiftedDay -= maxDays
        }

        calendarNew.set(Calendar.DAY_OF_MONTH,shiftedDay)

        return calendarNew
    }

    fun changeMonthByN(myCalendar: Calendar,n : Int,direction: Int) : Calendar
    {
        var month = myCalendar.get(Calendar.MONTH)
        var year = myCalendar.get(Calendar.YEAR)
        month += direction * n

        if(month<0)
        {
            month += 12
            year  -=1
        }
        if(month>11)
        {
            month -= 12
            year +=1
        }

        myCalendar.set(Calendar.DAY_OF_MONTH,1)
        myCalendar.set(Calendar.MONTH,month)
        myCalendar.set(Calendar.YEAR,year)

        return myCalendar
    }

    fun changeYearByN(myCalendar: Calendar,n : Int,direction: Int) : Calendar
    {
        var year = myCalendar.get(Calendar.YEAR)

        year += direction * n
        myCalendar.set(Calendar.YEAR,year)

        return myCalendar


    }

    fun copyDayMonthYear(calendarDestination: Calendar,calendarSource: Calendar) :Calendar
    {
        calendarDestination.set(Calendar.YEAR,calendarSource.get(Calendar.YEAR))
        calendarDestination.set(Calendar.MONTH,calendarSource.get(Calendar.MONTH))
        calendarDestination.set(Calendar.DAY_OF_MONTH,calendarSource.get(Calendar.DAY_OF_MONTH))

        return calendarDestination
    }
}