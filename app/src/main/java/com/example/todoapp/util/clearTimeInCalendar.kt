package com.example.todoapp.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.util.Calendar

fun Fragment.clearTimeInCalendar(calendar : Calendar){
  calendar.set(Calendar.HOUR,0)
  calendar.set(Calendar.MINUTE,0)
  calendar.set(Calendar.SECOND,0)
  calendar.set(Calendar.MILLISECOND,0)
}

fun AppCompatActivity.clearTimeInCalendar(calendar: Calendar){
  calendar.set(Calendar.HOUR,0)
  calendar.set(Calendar.MINUTE,0)
  calendar.set(Calendar.SECOND,0)
  calendar.set(Calendar.MILLISECOND,0)
}