package com.example.budgetwiseexpensetracker.presentation.UI.Home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.databinding.ActivityHomeBinding
import android.view.WindowManager
import android.widget.TextView
import com.example.budgetwiseexpensetracker.presentation.UI.Expense.ExpenseActivity
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

    private val tvShopping: TextView by lazy { binding.fragmentMonthlySpendingSummary.tvShoppingMoneyUsed }
    private val tvSubscription: TextView by lazy { binding.fragmentMonthlySpendingSummary.tvSubscriptionMoneyUsed }
    private val tvFood: TextView by lazy { binding.fragmentMonthlySpendingSummary.tvFoodMoneyUsed }
    private val tvOther: TextView by lazy { binding.fragmentMonthlySpendingSummary.tvOtherMoneyUsed }
    private val pieChart: org.eazegraph.lib.charts.PieChart by lazy { binding.fragmentMonthlySpendingSummary.piechart }

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

        pieChart.addPieSlice(
            PieModel(
                "Shopping",
                tvShopping.getText().toString().replace("- $","",).toInt().toFloat(),
                Color.parseColor("#FCAC12")
//                Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.Shopping)).replace("ff",""))

            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Subscription",
                tvSubscription.text.toString().replace("- $","").toInt().toFloat(),
                Color.parseColor("#7F3DFF")
//                Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.Subscription)).replace("ff",""))
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Food",
                tvFood.text.toString().replace("- $","").toInt().toFloat(),
                Color.parseColor("#FD3C4A")
//                Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.Food)).replace("ff",""))
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Other",
                tvOther.text.toString().replace("- $","").toInt().toFloat(),
                Color.parseColor("#0276AD")
//                Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.Other)).replace("ff",""))
            )
        )


        // To animate the pie chart
        pieChart.startAnimation()

    }

    private fun fabMainPage() {
        binding.fabMainPage.setOnClickListener {
            setVisibility(clicked)
            setAnimation(clicked)
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//            )
            clicked = !clicked
        }
        binding.addExpense.setOnClickListener {
            intent = android.content.Intent(this, ExpenseActivity::class.java)
            startActivity(intent)
        }
        binding.addIncome.setOnClickListener {
            intent = android.content.Intent(this, ExpenseActivity::class.java)
            startActivity(intent)
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