package com.path_studio.githubuser

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


object Utils {

    fun convertNumberFormat(num: Int): String{
        var temp: String = ""
        if(num >= 1000){
            temp = String.format("%.1f", num.toDouble() / 1000.0) + " K"
        }else if (num >= 1000000){
            temp = String.format("%.1f", num.toDouble() / 1000000.0) + " M"
        }else{
            temp = num.toString()
        }
        return temp
    }

    fun checkEmptyValue(temp: String?): String{
        return when {
            temp.isNullOrBlank() -> {
                "No Data"
            }
            else -> {
                temp.toString()
            }
        }
    }

    fun getDaysAgo(daysAgo: Int): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

        val pattern = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(calendar.time)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkDayswithToday(fromDate: String): List<String>{
        val timeFormater = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val formatter = DateTimeFormatter.ofPattern(timeFormater)
        val from = LocalDateTime.parse(fromDate, formatter)


        val now = LocalDateTime.now()
        var tempDateTime = LocalDateTime.from(from)

        val years: Long = from.until(now, ChronoUnit.YEARS)
        tempDateTime = tempDateTime.plusYears(years)

        val months: Long = tempDateTime.until(now, ChronoUnit.MONTHS)
        tempDateTime = tempDateTime.plusMonths(months)

        val days: Long = tempDateTime.until(now, ChronoUnit.DAYS)
        tempDateTime = tempDateTime.plusDays(days)


        val hours: Long = tempDateTime.until(now, ChronoUnit.HOURS)
        tempDateTime = tempDateTime.plusHours(hours)

        val minutes: Long = tempDateTime.until(now, ChronoUnit.MINUTES)
        tempDateTime = tempDateTime.plusMinutes(minutes)

        val seconds: Long = tempDateTime.until(now, ChronoUnit.SECONDS)


        if(years > 0){
            //if more than half years, rounded up
            return listOf("$years y", "$years years $months ago.")
        }

        if (months > 0){
            return listOf("$months mo", "$months months $days days ago.")
        }

        if (days > 0){
            return listOf("$days d", "$days days ago.")
        }

        if(hours > 0) {
            return listOf("$hours H", "$hours hours $minutes minutes ago.")
        }

        if(minutes > 0) {
            return listOf("$minutes m", "$minutes minutes $seconds second ago.")
        }

        if(seconds > 0){
            return listOf("$seconds s", "$seconds ago.")
        }

        //else
        return listOf("?", "-")
    }

    fun showFailedGetDataFromAPI(context: Context){
        val builder1: AlertDialog.Builder = AlertDialog.Builder(context)
        builder1.setMessage(context.getText(R.string.failed_text))
        builder1.setCancelable(true)

        builder1.setPositiveButton(
            "Oke"
        ) { dialog, _ -> dialog.cancel() }

        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
    }

    fun getBitmapFromURL(context: Context, src: String?): Bitmap {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            // Log exception
            BitmapFactory.decodeResource(context.resources, R.drawable.no_data)
        }
    }

}