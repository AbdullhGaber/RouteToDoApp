package com.example.todoapp.presentation.fragments.tasks.adapters

import android.graphics.Color
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.Task
import com.example.todoapp.data.models.TaskStatus
import com.example.todoapp.databinding.TaskItemBinding

class TaskRvAdapter() : RecyclerView.Adapter<TaskRvAdapter.ViewHolder>() {
    var onDeleteClick : (Task) -> Unit = {}
    var onClickListener : (Task) -> Unit = {}
    var onCheckClickListener : (Int , Task) -> Unit = {pos , task ->}

    private val diffUtil = object : DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
             return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }

    val asyncListDiffer = AsyncListDiffer(this , diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = asyncListDiffer.currentList[position]
        holder.bind(task)
    }

    override fun getItemCount() = asyncListDiffer.currentList.size

    inner class ViewHolder(private val mBinding : TaskItemBinding) : RecyclerView.ViewHolder(mBinding.root){
        fun bind(task : Task){
           if(task.status == TaskStatus.COMPLETED) changeTaskCardStyle()

            mBinding.titleTv.text = task.title
            val selectedDate = Calendar.getInstance()
            selectedDate.apply {
                timeInMillis = task.date
                val year = get(Calendar.YEAR)
                val month = get(Calendar.MONTH)
                val day = get(Calendar.DAY_OF_MONTH)
                mBinding.timeTv.text = "$year-${month+1}-$day"
            }

            mBinding.leftView.setOnClickListener {
                if(!mBinding.swipeLayout.isClosed){
                    onDeleteClick(task)
                    notifyItemRemoved(adapterPosition)
                }
            }

            mBinding.taskCardView.setOnClickListener {
                onClickListener(task)
            }

            mBinding.checkButton.setOnClickListener{
                if(task.status == TaskStatus.PENDING){
                    changeTaskCardStyle()
                    onCheckClickListener(adapterPosition,task)
            }
        }
    }
    private fun changeTaskCardStyle(){
       val context = mBinding.root.context
       mBinding.checkButton.apply {
           setBackgroundColor(Color.TRANSPARENT)
           setCompoundDrawablesWithIntrinsicBounds(null,null,null,null)
           text = getString(context,R.string.done)
           setTextColor(ContextCompat.getColor(context , R.color.success_green))
       }
       mBinding.titleTv.setTextColor(ContextCompat.getColor(context, R.color.success_green))
       mBinding.view.setBackgroundColor(ContextCompat.getColor(context, R.color.success_green))
    }
  }
}
