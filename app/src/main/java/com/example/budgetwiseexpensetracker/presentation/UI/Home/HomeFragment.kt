package com.example.budgetwiseexpensetracker.presentation.UI.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.databinding.FragmentHomeBinding
import com.example.budgetwiseexpensetracker.presentation.adapter.HomeRecyclerAdapter

class HomeFragment : Fragment() {
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.to_bottom_anim
        )
    }
    private var clicked = false

//    private val tvShopping: TextView by lazy { binding.fragmentMonthlySpendingSummary.tvShoppingMoneyUsed }
//    private val tvSubscription: TextView by lazy { binding.fragmentMonthlySpendingSummary.tvSubscriptionMoneyUsed }
//    private val tvFood: TextView by lazy { binding.fragmentMonthlySpendingSummary.tvFoodMoneyUsed }
//    private val tvOther: TextView by lazy { binding.fragmentMonthlySpendingSummary.tvOtherMoneyUsed }
    private val pieChart: org.eazegraph.lib.charts.PieChart by lazy { binding.fragmentMonthlySpendingSummary.piechart }
    private lateinit var binding: FragmentHomeBinding
    private val rvRecentSpending:RecyclerView by lazy{binding.fragmentMonthlySpendingSummary.rvRecentSpending}
    private lateinit var viewModel:HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigationMainPage.menu.getItem(2).isEnabled = false
        initView()
    }

    private fun initView() {
        fabMainPage()
        pieChart()
        setAdapter()
        setViewModel()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        serObserver()
    }

    private fun serObserver() {
        viewModel.spendingData.observe(viewLifecycleOwner) { newData ->
            if (rvRecentSpending.adapter is HomeRecyclerAdapter){
                val adapter = rvRecentSpending.adapter as HomeRecyclerAdapter
                adapter.setData(newData)
            }
        }
    }

    private fun setAdapter(){
        rvRecentSpending.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = HomeRecyclerAdapter()
        }
    }

    private fun String.replace( ): Float {
        return this.replace("- $", "").toFloat()
    }
    private fun pieChart() {
//        pieChart.addPieSlice(
//            PieModel(
//                "Shopping",
//                tvShopping.getText().toString().replace(),
////                Color.parseColor("#FCAC12")
////                Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.Shopping)).replace("ff",""))
//                resources.getColor(R.color.Shopping, null)
//
//            )
//        )
//        pieChart.addPieSlice(
//            PieModel(
//                "Subscription",
//                tvSubscription.text.toString().replace(),
////                Color.parseColor("#7F3DFF")
////                Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.Subscription)).replace("ff",""))
//                resources.getColor(R.color.Subscription, null)
//            )
//        )
//        pieChart.addPieSlice(
//            PieModel(
//                "Food",
//                tvFood.text.toString().replace(),
////                Color.parseColor("#FD3C4A")
////                Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.Food)).replace("ff",""))
//                resources.getColor(R.color.Food, null)
//            )
//        )
//        pieChart.addPieSlice(
//            PieModel(
//                "Other",
//                tvOther.text.toString().replace(),
////                Color.parseColor("#0276AD")
////                Color.parseColor("#"+Integer.toHexString(getResources().getColor(R.color.Other)).replace("ff",""))
//                resources.getColor(R.color.Other, null)
//            )
//        )
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
            findNavController().navigate(R.id.action_homeFragment_to_expenseFragment)
        }
        binding.addIncome.setOnClickListener {
//            intent = android.content.Intent(this, ExpenseActivity::class.java)
//            startActivity(intent)
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