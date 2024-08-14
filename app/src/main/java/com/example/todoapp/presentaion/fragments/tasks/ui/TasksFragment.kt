package com.example.todoapp.presentaion.fragments.tasks.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.TaskStatus
import com.example.todoapp.databinding.FragmentTasksBinding
import com.example.todoapp.presentaion.activities.task.TaskDetailsActivity
import com.example.todoapp.presentaion.fragments.tasks.HomeViewModel
import com.example.todoapp.presentaion.fragments.tasks.adapters.TaskRvAdapter
import com.example.todoapp.util.Constants
import com.example.todoapp.util.clearTimeInCalendar
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar

class TasksFragment : Fragment() {
    private lateinit var mBinding: FragmentTasksBinding
    private val mViewModel by activityViewModels<HomeViewModel>()
    private val mSelectedDate = Calendar.getInstance()
    init {
        clearTimeInCalendar(mSelectedDate)
    }
    private val mTasksRvAdapter by lazy {
        TaskRvAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentTasksBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUI()
        observeOnTasks()
        setOnDateChangedListener()
    }

    private fun bindUI() {
        setUpTaskRvAdapterListeners()
        setUpTasksRV()
        mBinding.calendarView.selectedDate = CalendarDay.today()
    }

    override fun onResume() {
        super.onResume()
        Log.e("TAG CAL" , "month : ${mSelectedDate.get(Calendar.MONTH)} day : ${mSelectedDate.get(Calendar.DAY_OF_MONTH)}")
    }

    private fun setUpTaskRvAdapterListeners(){
        mTasksRvAdapter.apply {
            onDeleteClick = {
                mViewModel.deleteTask(it)
            }
            onClickListener = {
                val bundle = Bundle().apply{
                    putParcelable(Constants.TASK_KEY , it)
                }
                Intent(
                    activity,TaskDetailsActivity::class.java
                ).apply{
                    putExtras(bundle)
                    startActivity(this)
                }
            }
            onCheckClickListener = { pos , task ->
                val task = task.copy(status = TaskStatus.COMPLETED)
                mViewModel.editTask(task)
                notifyItemChanged(pos)
            }
        }
    }

    private fun setOnDateChangedListener() {
        mBinding.calendarView.setOnDateChangedListener{
                _,date,_ ->
                    mSelectedDate.set(Calendar.YEAR,date.year)
                    mSelectedDate.set(Calendar.MONTH,date.month - 1)
                    mSelectedDate.set(Calendar.DAY_OF_MONTH,date.day)
                    observeOnTasks()
        }
    }

    private fun setUpTasksRV(){
        mBinding.tasksRv.apply {
            adapter = mTasksRvAdapter
            layoutManager = LinearLayoutManager(requireContext() , RecyclerView.VERTICAL , false)
        }
    }

    private fun observeOnTasks(){
        lifecycleScope.launch {
            mViewModel.getTasksByDate(mSelectedDate.timeInMillis).collect{
                    mTasksRvAdapter.asyncListDiffer.submitList(it)
            }
        }
    }
}