@file:Suppress("unused", "KDocUnresolvedReference", "MemberVisibilityCanBePrivate")

package com.example.ete.util.date_time

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import com.example.ete.R
import com.example.ete.util.date_time.DateTimeFormat.DATE_PATTERN_3
import com.example.ete.util.date_time.DateTimeFormat.DATE_PATTERN_4
import com.example.ete.util.date_time.DateTimeFormat.DATE_PATTERN_5
import com.google.android.material.datepicker.CalendarConstraints
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


/**
 * DateTimeUtils
 * This class contains a bunch of function that can manipulate
 * Date object or Date String to achieve certain operations
 * e.g : Time difference, Time Ago, Date formatting
 *
 * @author thunder413
 * @version 1.5
 */
object DateTimeUtils {
    /**
     * LOG TAG
     */
    private const val LOG_TAG = "DateTimeUtils"

    /**
     * Debug mode
     */
    private var debug = false

    /**
     * Time zone
     */
    private var timeZone = TimeZone.getDefault().id

    /**
     * Enable / Disable
     * @param state Debug state
     */
    fun setDebug(state: Boolean) {
        debug = state
    }

    /**
     * Set TimeZone
     * @param zone TimeZone
     */
    fun setTimeZone(zone: String) {
        timeZone = zone
    }

    fun currentTimeStamp(): Long {
        return (System.currentTimeMillis() / 1000)
    }

    fun currentTimeMilliStamp(): Long {
        return System.currentTimeMillis()
    }

    /**
     * Get Date or DateTime formatting pattern
     *
     * @param dateString Date String
     * @return Format Pattern
     */
    private fun getDatePattern(dateString: String?): String {
        return if (isDateTime(dateString)) {
            if (dateString!!.contains("/")) DateTimeFormat.DATE_TIME_PATTERN_2 else DateTimeFormat.DATE_TIME_PATTERN_1
        } else {
            if (dateString!!.contains("/")) DateTimeFormat.DATE_PATTERN_2 else DateTimeFormat.DATE_PATTERN_1
        }
    }
    /**
     * Convert a Java Date object to String
     *
     * @param date Date Object
     * @param locale Locale
     * @return Date Object string representation
     */
    /**
     * Convert a Java Date object to String
     *
     * @param date Date Object
     * @return Date Object string representation
     */
    @JvmOverloads
    fun formatDate(date: Date?, locale: Locale? = Locale.getDefault()): String {
        if (date == null && debug) {
            Log.e(LOG_TAG, "formatDate >> Supplied date is null")
        }
        val iso8601Format = SimpleDateFormat(DateTimeFormat.DATE_TIME_PATTERN_1, locale)
        iso8601Format.timeZone = TimeZone.getTimeZone(timeZone)
        if (debug) {
            Log.d(LOG_TAG, "formatDate >> Formatting using " + iso8601Format.timeZone.displayName + " | " + iso8601Format.timeZone.id)
        }
        return iso8601Format.format(date!!)
    }

    /**
     * Convert a date string to Java Date Object
     *
     * @param dateString Date String
     * @param locale Locale
     * @return Java Date Object
     */
    fun formatDate(dateString: String?, locale: Locale?): Date? {
        val iso8601Format = SimpleDateFormat(getDatePattern(dateString), locale)
        iso8601Format.timeZone = TimeZone.getTimeZone(timeZone)
        var date: Date? = null
        if (dateString != null) {
            try {
                date = iso8601Format.parse(dateString.trim { it <= ' ' })
            } catch (e: ParseException) {
                if (debug) {
                    Log.e(LOG_TAG, "formatDate >> Fail to parse supplied date string >> $dateString")
                    e.printStackTrace()
                }
            }
        }
        return date
    }

    /**
     * Convert a date string to Java Date Object
     *
     * @param date Date String
     * @return Java Date Object
     */
    fun formatDate(date: String?): Date? {
        return formatDate(date, Locale.getDefault())
    }
    /**
     * Convert a timeStamp into a date object
     *
     * @param timeStamp TimeStamp
     * @param units Witch unit is whether seconds or milliseconds
     * @see DateTimeUnits.SECONDS
     *
     * @see DateTimeUnits.MILLISECONDS
     *
     * @return Date object
     */
    /**
     * Convert a timeStamp into a date considering given timeStamp in milliseconds
     *
     * @see DateTimeUnits.MILLISECONDS
     *
     * @param timeStamp TimeStamp
     * @return Date object
     */
    @JvmOverloads
    fun formatDate(timeStamp: Long, units: DateTimeUnits = DateTimeUnits.MILLISECONDS): Date {
        return if (units == DateTimeUnits.SECONDS) Date(timeStamp * 1000L) else Date(timeStamp)
    }
    /**
     * Format date using a given pattern
     * and apply supplied locale
     *
     * @param date Date Object
     * @param pattern Pattern
     * @param locale Locale
     * @return Formatted date
     */
    /**
     * Format date using a given pattern
     * apply default locale
     *
     * @param date Date Object
     * @param pattern Pattern
     *
     * @return Formatted date
     */
    @JvmOverloads
    fun formatWithPattern(date: Date?, pattern: String?, locale: Locale? = Locale.getDefault()): String {
        if (date == null && debug) {
            Log.e(LOG_TAG, "FormatWithPattern >> Supplied date is null")
        }
        val iso8601Format = SimpleDateFormat(pattern, locale)
        iso8601Format.timeZone = TimeZone.getTimeZone(timeZone)
        return iso8601Format.format(date!!)
    }
    /**
     * Format date using a given pattern
     * and apply supplied locale
     *
     * @param date Date String
     * @param pattern Pattern
     * @param locale Locale
     * @return Formatted date
     */
    /**
     * Format date using a given pattern
     * apply default locale
     * @param date Date String
     * @param pattern Pattern
     *
     * @return Formatted date
     */
    @JvmOverloads
    fun formatWithPattern(date: String?, pattern: String?, locale: Locale? = Locale.getDefault()): String {
        return formatWithPattern(formatDate(date), pattern, locale)
    }

    /**
     * Build a pattern for given style
     * @param style DateTimeStyle
     * @return Pattern
     */
    private fun getPatternForStyle(style: DateTimeStyle): String {
        val pattern: String = when (style) {
            DateTimeStyle.LONG -> {
                "MMMM dd, yyyy"
            }

            DateTimeStyle.MEDIUM -> {
                "MMM dd, yyyy"
            }

            DateTimeStyle.SHORT -> {
                "MM/dd/yy"
            }

            else -> {
                "EEEE, MMMM dd, yyyy"
            }
        }
        return pattern
    }
    /**
     * Get localized date string
     *
     * @param date Date string
     * @return Formatted localized date string
     */
    /**
     * Get localized date string (Using default locale)
     *
     * @param date Date string
     * @return Formatted localized date string
     */
    @JvmOverloads
    fun formatWithStyle(date: Date?, style: DateTimeStyle, locale: Locale? = Locale.getDefault()): String {
        if (date == null && debug) {
            Log.e(LOG_TAG, "FormatWithPattern >> Supplied date is null")
        }
        return formatWithPattern(date, getPatternForStyle(style), locale)
    }
    /**
     * Get localized date string (Using default locale)
     *
     * @param date Date string
     * @return Formatted localized date string
     */
    /**
     * Get localized date string (Using default locale)
     *
     * @param date Date string
     * @return Formatted localized date string
     */
    @JvmOverloads
    fun formatWithStyle(date: String?, style: DateTimeStyle, locale: Locale? = Locale.getDefault()): String {
        return formatWithStyle(formatDate(date), style, locale)
    }
    /**
     * Extract time from date without seconds
     * @see DateTimeFormat.TIME_PATTERN_1
     *
     * @param date Date object
     * @return Time String
     */
    /**
     * Extract time from date without seconds
     * @param date Date object
     * @return Time string
     */
    @JvmOverloads
    fun formatTime(date: Date?, forceShowHours: Boolean = false, removeSecond: Boolean = false): String {
        val iso8601Format = SimpleDateFormat(DateTimeFormat.TIME_PATTERN_1, Locale.getDefault())
        iso8601Format.timeZone = TimeZone.getTimeZone(timeZone)
        var time = iso8601Format.format(date!!)

        val hhmmss = time.split(":".toRegex()).toTypedArray()
        val hours = hhmmss[0].toInt()
        val minutes = hhmmss[1].toInt()
        var seconds = hhmmss[2].toInt()
        if (removeSecond) {
            seconds = 0
        }
        return ((if (hours == 0 && !forceShowHours) "" else if (hours < 10) "0$hours:" else "$hours:") +
                (if (minutes == 0) "00" else if (minutes < 10) "0$minutes" else minutes.toString())
                + if (seconds == 0) "" else if (seconds < 10) ":0$seconds" else ":$seconds")
    }
    /**
     * Extract time from date without seconds
     * @param date Date object
     * @return Time string
     */
    /**
     * Extract time from date without seconds
     * @param date Date object
     * @return Time string
     */
    @JvmOverloads
    fun formatTime(date: String?, forceShowHours: Boolean = false): String {
        return formatTime(formatDate(date), forceShowHours)
    }

    /**
     * Convert millis to human readable time
     *
     * @param millis TimeStamp
     *
     * @return Time String
     */
    fun millisToTime(millis: Long): String {
        val seconds = (TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
        val minutes = (TimeUnit.MILLISECONDS.toMinutes(millis)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)))
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        return ((if (hours == 0L) "" else if (hours < 10) "0$hours:" else "$hours:") +
                (if (minutes == 0L) "00" else if (minutes < 10) "0$minutes" else minutes.toString()) + ":"
                + if (seconds == 0L) "00" else if (seconds < 10) "0$seconds" else seconds.toString())
    }

    /**
     * Convert millis to human readable time
     *
     * @param time Time string
     * @return Time String
     */
    fun timeToMillis(time: String): Long {
        val hhmmss = time.split(":".toRegex()).toTypedArray()
        var hours = 0
        val minutes: Int
        val seconds: Int
        if (hhmmss.size == 3) {
            hours = hhmmss[0].toInt()
            minutes = hhmmss[1].toInt()
            seconds = hhmmss[2].toInt()
        } else {
            minutes = hhmmss[0].toInt()
            seconds = hhmmss[1].toInt()
        }
        return ((hours * 60 + minutes * 60 + seconds) * 1000).toLong()
    }

    /**
     * Tell whether or not a given string represent a date time string or a simple date
     *
     * @param dateString Date String
     * @return True if given string is a date time False otherwise
     */
    fun isDateTime(dateString: String?): Boolean {
        return dateString != null && dateString.trim { it <= ' ' }.split(" ".toRegex()).toTypedArray().size > 1
    }

    /**
     * Tell whether or not a given date is yesterday
     * @param date Date Object
     * @return True if the date is yesterday False otherwise
     */
    fun isYesterday(date: Date?): Boolean {
        // Check if yesterday
        val c1 = Calendar.getInstance() // today
        c1.add(Calendar.DAY_OF_YEAR, -1) // yesterday
        val c2 = Calendar.getInstance()
        if (date != null) {
            c2.time = date
        } //
        return (c1[Calendar.YEAR] == c2[Calendar.YEAR]
                && c1[Calendar.DAY_OF_YEAR] == c2[Calendar.DAY_OF_YEAR])
    }

    /**
     * Tell whether or not a given date is yesterday
     * @param dateString Date String
     * @return True if the date is yesterday False otherwise
     */
    fun isYesterday(dateString: String?): Boolean {
        return isYesterday(formatDate(dateString))
    }

    /**
     * Tell whether or not a given date is today date
     * @param date Date object
     * @return True if date is today False otherwise
     */
    fun isToday(date: Date?): Boolean {
        return DateUtils.isToday(date!!.time)
    }

    /**
     * Tell whether or not a given date is today date
     * @param dateString Date string
     * @return True if date is today False otherwise
     */
    fun isToday(dateString: String?): Boolean {
        return isToday(formatDate(dateString))
    }

    /**
     * Get Previous month from a given date
     * @param date Date start
     * @return Date of the previous month
     */
    fun getPreviousMonthDate(date: Date?): Date {
        val c = Calendar.getInstance()
        if (date != null) {
            c.time = date
        } //
        c.add(Calendar.MONTH, -1)
        return c.time
    }

    /**
     * Get Previous month from a given date
     * @param date Date start
     * @return Date of the previous month
     */
    fun getPreviousMonthDate(date: String?): Date {
        return getPreviousMonthDate(formatDate(date))
    }

    /**
     * Get Next month from a given date
     * @param date Date start
     * @return Date of the previous month
     */
    fun getNextMonthDate(date: Date?): Date {
        val c = Calendar.getInstance()
        if (date != null) {
            c.time = date
        } //
        c.add(Calendar.MONTH, 1)
        return c.time
    }

    /**
     * Get Previous month from a given date
     * @param date String Date start
     * @return Date of the previous month
     */
    fun getNextMonthDate(date: String?): Date {
        return getNextMonthDate(formatDate(date))
    }

    /**
     * Get Previous week date
     * @param date Date Object
     * @param dayOfTheWeek Day Of the week
     * @return Date
     */
    fun getPreviousWeekDate(date: Date?, dayOfTheWeek: Int): Date {
        val c = Calendar.getInstance()
        if (date != null) {
            c.time = date
        }
        c.firstDayOfWeek = dayOfTheWeek
        c[Calendar.DAY_OF_WEEK] = dayOfTheWeek
        c.add(Calendar.DATE, -7)
        return c.time
    }

    /**
     * Get Previous week date
     * @param date Date String
     * @param dayOfTheWeek Day Of the week
     * @return Date
     */
    fun getPreviousWeekDate(date: String?, dayOfTheWeek: Int): Date {
        return getPreviousWeekDate(formatDate(date), dayOfTheWeek)
    }

    /**
     * Get Next week date
     * @param date Date Object
     * @param dayOfTheWeek Day Of the week
     * @return Date
     */
    fun getNextWeekDate(date: Date?, dayOfTheWeek: Int): Date {
        val c = Calendar.getInstance()
        if (date != null) {
            c.time = date
        }
        c.firstDayOfWeek = dayOfTheWeek
        c[Calendar.DAY_OF_WEEK] = dayOfTheWeek
        c.add(Calendar.DATE, 7)
        return c.time
    }

    /**
     * Get Next week date
     * @param date Date Object
     * @return Date
     */
    fun getNextWeekDate(date: Date?): Date {
        return getNextWeekDate(date, Calendar.MONDAY)
    }

    /**
     * Get Next week date
     * @param date Date Object
     * @return Date
     */
    fun getNextWeekDate(date: String?): Date {
        return getNextWeekDate(formatDate(date))
    }

    /**
     * Get Next week date
     * @param date Date Object
     * @param dayOfTheWeek Day Of the week
     * @return Date
     */
    fun getNextWeekDate(date: String?, dayOfTheWeek: Int): Date {
        return getNextWeekDate(formatDate(date), dayOfTheWeek)
    }

    /**
     * Get difference between two dates
     *
     * @param nowDate  Current date
     * @param oldDate  Date to compare
     * @param dateDiff Difference Unit
     * @return Difference
     */
    fun getDateDiff(nowDate: Date?, oldDate: Date?, dateDiff: DateTimeUnits?): Int {
        val diffInMs = nowDate!!.time - oldDate!!.time
        val days = TimeUnit.MILLISECONDS.toDays(diffInMs).toInt()
        val hours = (TimeUnit.MILLISECONDS.toHours(diffInMs) - TimeUnit.DAYS.toHours(days.toLong())).toInt()
        val minutes = (TimeUnit.MILLISECONDS.toMinutes(diffInMs) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(diffInMs))).toInt()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMs).toInt()
        return when (dateDiff) {
            DateTimeUnits.DAYS -> days
            DateTimeUnits.SECONDS -> seconds
            DateTimeUnits.MINUTES -> minutes
            DateTimeUnits.HOURS -> hours
            DateTimeUnits.MILLISECONDS -> diffInMs.toInt()
            else -> diffInMs.toInt()
        }
    }

    /**
     * Get difference between two dates
     *
     * @param nowDate  Current date
     * @param oldDate  Date to compare
     * @param dateDiff Difference Unit
     * @return Difference
     */
    fun getDateDiff(nowDate: String?, oldDate: Date?, dateDiff: DateTimeUnits?): Int {
        return getDateDiff(formatDate(nowDate), oldDate, dateDiff)
    }

    /**
     * Get difference between two dates
     *
     * @param nowDate  Current date
     * @param oldDate  Date to compare
     * @param dateDiff Difference Unit
     * @return Difference
     */
    fun getDateDiff(nowDate: Date?, oldDate: String?, dateDiff: DateTimeUnits?): Int {
        return getDateDiff(nowDate, formatDate(oldDate), dateDiff)
    }

    /**
     * Get difference between two dates
     *
     * @param nowDate  Current date
     * @param oldDate  Date to compare
     * @param dateDiff Difference Unit
     * @return Difference
     */
    fun getDateDiff(nowDate: String?, oldDate: String?, dateDiff: DateTimeUnits?): Int {
        return getDateDiff(nowDate, formatDate(oldDate), dateDiff)
    }

    /**
     * Get time ago of given date
     *
     * @param context Context
     * @param date    Date object
     * @param style DateTimeStyle
     * @return Time ago string
     */
    fun getTimeAgo(context: Context, date: Date?, style: DateTimeStyle): String {
        val seconds = getDateDiff(Date(), date, DateTimeUnits.SECONDS).toDouble()
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val phrase: String
        val s: String
        when {
            seconds <= 0 -> {
                phrase = context.getString(R.string.time_ago_now)
            }

            seconds < 45 -> {
                s = if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_seconds) else context.getString(R.string.time_ago_seconds)
                phrase = String.format(s, seconds.roundToInt())
            }

            seconds < 90 -> {
                s = if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_minute) else context.getString(R.string.time_ago_minute)
                phrase = String.format(s, 1)
            }

            minutes < 45 -> {
                s = if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_minutes) else context.getString(R.string.time_ago_minutes)
                phrase = String.format(s, minutes.roundToInt())
            }

            minutes < 90 -> {
                s = if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_hour) else context.getString(R.string.time_ago_hour)
                phrase = String.format(s, 1)
            }

            hours < 24 -> {
                s = if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_hours) else context.getString(R.string.time_ago_hours)
                phrase = String.format(s, hours.roundToInt())
            }

            hours < 42 -> {
                phrase = if (isYesterday(date)) {
                    context.getString(R.string.time_ago_yesterday_at, formatTime(date, removeSecond = true))
                } else {
                    formatWithStyle(date, if (style == DateTimeStyle.AGO_FULL_STRING) DateTimeStyle.FULL else DateTimeStyle.SHORT)
                }
            }

            days < 30 -> {
                s = if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_days) else context.getString(R.string.time_ago_days)
                phrase = String.format(s, days.roundToInt())
            }

            days < 45 -> {
                s = if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_month) else context.getString(R.string.time_ago_month)
                phrase = String.format(s, 1)
            }

            days < 365 -> {
                s = if (style == DateTimeStyle.AGO_FULL_STRING) context.getString(R.string.time_ago_full_months) else context.getString(R.string.time_ago_months)
                phrase = String.format(s, (days / 30).roundToInt())
            }

            else -> {
                phrase = formatWithStyle(date, if (style == DateTimeStyle.AGO_FULL_STRING) DateTimeStyle.FULL else DateTimeStyle.SHORT)
            }
        }
        return phrase
    }

    /**
     * Get time ago of given date
     *
     * @param context    Context
     * @param dateString Representing a date time string
     * @return Time ago string
     */
    fun getTimeAgo(context: Context, dateString: String?): String {
        return getTimeAgo(context, formatDate(dateString), DateTimeStyle.AGO_FULL_STRING)
    }

    /**
     * Get time ago of given date
     *
     * @param context    Context
     * @param date Representing a date time string
     * @return Time ago string
     */
    fun getTimeAgo(context: Context, date: Date?): String {
        return getTimeAgo(context, date, DateTimeStyle.AGO_SHORT_STRING)
    }


    /**
     * Get List year
     * @param endYear  Long
     * @return Current year to end year as list
     * */
    fun getListOfPastYear(endYear: Long): ArrayList<String> {
        val listOfYear: ArrayList<String> = ArrayList()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        for (year in endYear..currentYear) {
            listOfYear.add(year.toString())
        }
        listOfYear.reverse()
        return listOfYear
    }

    //Get age
    fun getAge(milli: Long): Int {
        val date = formatDate(milli)
        return Period.between(
            LocalDate.of(formatWithPattern(date, DATE_PATTERN_3).toInt(), formatWithPattern(date, DATE_PATTERN_4).toInt(), formatWithPattern(date, DATE_PATTERN_5).toInt()),
            LocalDate.now()
        ).years
    }

    //Get Past date according age
    fun getMinDateInMillis(age: Int): Long {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        calendar.set(Calendar.YEAR, currentYear - age)
        return calendar.timeInMillis
    }

    //Validator for date time picker
    fun createValidator(minDate: Long, maxDate: Long): CalendarConstraints.DateValidator {
        return object : CalendarConstraints.DateValidator {
            override fun isValid(date: Long): Boolean {
                return date in minDate..maxDate
            }

            override fun writeToParcel(dest: android.os.Parcel, flags: Int) {}
            override fun describeContents(): Int = 0
        }
    }
}