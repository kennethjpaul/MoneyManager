package com.kinetx.moneymanager.helpers

import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.util.Log
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
        val startOfWeekCalendar : Calendar =  GregorianCalendar(myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH))
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

        startOfWeekCalendar.set(Calendar.DAY_OF_MONTH,day)
        startOfWeekCalendar.set(Calendar.MONTH,month)
        return startOfWeekCalendar
    }


    fun getEndOfWeek(myCalendar: Calendar): Calendar
    {
        val endOfWeekCalendar : Calendar =  GregorianCalendar(myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH))
        val dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK)
        val shift = 7-dayOfWeek

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

        endOfWeekCalendar.set(Calendar.DAY_OF_MONTH,day)
        endOfWeekCalendar.set(Calendar.MONTH,month)
        return endOfWeekCalendar
    }

    fun getStartOfMonth(myCalendar: Calendar, startOfMonth : Int) : Calendar
    {
        val startMonthCalendar : Calendar =  GregorianCalendar(myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH))

        val day  = myCalendar.get(Calendar.DAY_OF_MONTH)
        var month = myCalendar.get(Calendar.MONTH)
        var year = myCalendar.get(Calendar.YEAR)



        if (month==0 && day < startOfMonth)
        {
            month =11
            year -=1
        }
        else if(month >0 && day < startOfMonth)
        {
            month -=1
        }


        startMonthCalendar.set(Calendar.MONTH,month)
        startMonthCalendar.set(Calendar.YEAR,year)

        val maxDays = startMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        startMonthCalendar.set(Calendar.DAY_OF_MONTH,min(maxDays,startOfMonth))

        return startMonthCalendar

    }


    fun getEndOfMonth(myCalendar: Calendar, startOfMonth : Int) : Calendar
    {

        val endMonthCalendar : Calendar =  GregorianCalendar(myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH))
        val day  = myCalendar.get(Calendar.DAY_OF_MONTH)
        var month = myCalendar.get(Calendar.MONTH)
        var year = myCalendar.get(Calendar.YEAR)

        if (day>=startOfMonth && month == 11)
        {
            month =0
            year +=1
        }
        else if (day >=startOfMonth && month < 11)
        {
            month +=1
        }

        endMonthCalendar.set(Calendar.MONTH,month)
        endMonthCalendar.set(Calendar.YEAR,year)
        val maxDays = endMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        endMonthCalendar.set(Calendar.DAY_OF_MONTH,min(maxDays,startOfMonth-1))

        return endMonthCalendar

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

    fun getDateArray(calendar: Calendar): String
    {
        val day : String = calendar.get(Calendar.DAY_OF_MONTH).toString()
        val month: String = CommonOperations.monthArray[calendar.get(Calendar.MONTH)]
        val year : String = calendar.get(Calendar.YEAR).toString()

        return "$day $month $year"
    }
}