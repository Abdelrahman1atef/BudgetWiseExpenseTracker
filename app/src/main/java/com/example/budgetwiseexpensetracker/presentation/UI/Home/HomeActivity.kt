package com.example.budgetwiseexpensetracker.presentation.UI.Home

import android.graphics.Color
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.databinding.ActivityHomeBinding
import org.eazegraph.lib.models.PieModel


class HomeActivity : AppCompatActivity() {
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.to_bottom_anim
        )
    }
    private var clicked = false

    private val tvR: TextView by lazy { binding.fragmentMonthlySpendingSummary.tvR }
    private val tvPython: TextView by lazy { binding.fragmentMonthlySpendingSummary.tvPython }
    private val tvCPP: TextView by lazy { binding.fragmentMonthlySpendingSummary.tvCPP }
    private val tvJava: TextView by lazy { binding.fragmentMonthlySpendingSummary.tvJava }
    private val pieChart: org.eazegraph.lib.charts.PieChart by lazy { binding.fragmentMonthlySpendingSummary.piechart }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationMainPage.menu.getItem(2).isEnabled = false
        initView()

//        val navController = findNavController(R.id.bottomAppBar)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun initView() {
        fabMainPage()
        pieChart()
    }

    private fun pieChart() {
        tvR.text = 100.toString()
        tvPython.text = 50.toString()
        tvCPP.text = 70.toString()
        tvJava.text = 20.toString()




        pieChart.addPieSlice(
            PieModel(
                "R",
                tvR.getText().toString().toInt().toFloat(),
                Color.parseColor("#FFA726")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Python",
                tvPython.text.toString().toInt().toFloat(),
                Color.parseColor("#66BB6A")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "C++",
                tvCPP.text.toString().toInt().toFloat(),
                Color.parseColor("#EF5350")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Java",
                tvJava.text.toString().toInt().toFloat(),
                Color.parseColor("#29B6F6")
            )
        )


        // To animate the pie chart
        pieChart.startAnimation()

    }

    private fun fabMainPage() {
        binding.fabMainPage.setOnClickListener {
            setVisibility(clicked)
            setAnimation(clicked)
            clicked = !clicked
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.addIncome.visibility = android.view.View.VISIBLE
            binding.addExpense.visibility = android.view.View.VISIBLE

        } else {
            binding.addIncome.visibility = android.view.View.INVISIBLE
            binding.addExpense.visibility = android.view.View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
//            binding.addIncome.startAnimation(fromBottom)
            binding.fabMainPage.startAnimation(rotateOpen)

        } else {
//            binding.addIncome.startAnimation(toBottom)
//            binding.addExpense.startAnimation(toBottom)
            binding.fabMainPage.startAnimation(rotateClose)
        }
    }
}