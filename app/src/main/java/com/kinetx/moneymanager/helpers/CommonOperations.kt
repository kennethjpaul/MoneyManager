package com.kinetx.moneymanager.helpers

import android.app.Application

object CommonOperations {
    fun getResourceInt(application: Application, fileName : String) : Int
    {
        val c = application.applicationContext
        val b  =c.resources.getIdentifier(fileName,"drawable",c.packageName)

        return b
    }
    val monthArray = arrayOf(
        "Jan", "Feb",
        "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )
}