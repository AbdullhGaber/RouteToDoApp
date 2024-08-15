package com.example.todoapp.presentation.activities.task

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.data.models.Task
import com.example.todoapp.databinding.ActivityTaskDetailsBinding
import com.example.todoapp.presentation.fragments.tasks.HomeViewModel
import com.example.todoapp.util.Constants
import com.example.todoapp.util.clearTimeInCalendar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.MONTH
import java.util.Calendar.YEAR

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@AndroidEntryPoint
class TaskDetailsActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityTaskDetailsBinding
    private val mViewModel by viewModels<HomeViewModel>()
    private val mSelectedDate = Calendar.getInstance()
    init {
        clearTimeInCalendar(mSelectedDate)
    }

    private val mTask by lazy{
        intent.extras?.getParcelable(Constants.TASK_KEY,Task::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        bindUI()
    }

    private fun bindUI(){
        mBinding.apply {
            mTask?.let {
                content.title.setText(it.title)
                content.description.setText(it.content)
                mSelectedDate.timeInMillis = it.date
                content.selectTimeTv.setText("${mSelectedDate.get(YEAR)} - ${mSelectedDate.get(MONTH) + 1} - ${mSelectedDate.get(
                    DAY_OF_MONTH)}")
                content.selectDateTv.setOnClickListener {
                    showDatePickDialog()
                }
                content.btnSave.setOnClickListener {
                    val title = content.title.text.toString()
                    val description = content.description.text.toString()
                    if(title.isEmpty()) {
                        Toast.makeText(this@TaskDetailsActivity, "Please fill title field", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnClickListener
                    }

                    val task = mTask?.copy(title = title , content = description , date = mSelectedDate.timeInMillis)

                    task?.let{
                       mViewModel.editTask(it)
                    }

                    finish()
                }
            }
        }
    }

    private fun showDatePickDialog() {
        val dateDialog = DatePickerDialog(this)
        dateDialog.show()
        dateDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            mSelectedDate.set(Calendar.YEAR, year)
            mSelectedDate.set(Calendar.MONTH, month)
            mSelectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            mBinding.content.selectTimeTv.text = "$year-${month + 1}-$dayOfMonth"
        }
    }
}