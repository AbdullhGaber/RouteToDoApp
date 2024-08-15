package com.example.todoapp.presentation.activities.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityHomeBinding
import com.example.todoapp.presentation.fragments.tasks.ui.AddTaskDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val hostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = hostFragment.findNavController()
        mBinding.bottomNav.setupWithNavController(navController)

        mBinding.fabBtn.setOnClickListener {
           showAddTaskDialog()
        }

    }

    private fun showAddTaskDialog() {
        val addTaskDialog = AddTaskDialogFragment()
        addTaskDialog.show(supportFragmentManager, "AddTaskDialogFragment")
    }
}