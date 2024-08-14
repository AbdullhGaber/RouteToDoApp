package com.example.todoapp.presentaion.fragments.tasks.ui

import android.app.DatePickerDialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.todoapp.data.models.Task
import com.example.todoapp.databinding.FragmentAddTaskDialogBinding
import com.example.todoapp.presentaion.fragments.tasks.HomeViewModel
import com.example.todoapp.util.clearTimeInCalendar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Calendar


class AddTaskDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddTaskDialogBinding? = null
    private val mBinding get() = _binding!!
    private val mViewModel by activityViewModels<HomeViewModel>()
    private val mSelectedDate = Calendar.getInstance()
    init {
        clearTimeInCalendar(mSelectedDate)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View?{
        _binding = FragmentAddTaskDialogBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        // Set the dialog to half of the screen height
        val bottomSheet = (dialog as? BottomSheetDialog)?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            behavior.peekHeight = (resources.displayMetrics.heightPixels * 0.3).toInt()
            it.layoutParams.height = behavior.peekHeight
            it.requestLayout()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        dialog?.setTitle("Add New Task")
        mBinding.tvSelectTime.setOnClickListener {
            showDatePickDialog()
        }
        mBinding.addTaskBtn.setOnClickListener {
            val title = mBinding.etTitle.text.toString()
            val description = mBinding.etDescription.text.toString()
            if(title.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill title field", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val task = Task(title = title, content = description, date = mSelectedDate.timeInMillis)

            mViewModel.addTask(task)

            dismiss()
        }
    }

    private fun showDatePickDialog() {
        val dateDialog = DatePickerDialog(requireContext())
        dateDialog.show()
        dateDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            mSelectedDate.set(Calendar.YEAR, year)
            mSelectedDate.set(Calendar.MONTH, month)
            mSelectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            mBinding.timeTv.text = "$year-${month + 1}-$dayOfMonth"
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
