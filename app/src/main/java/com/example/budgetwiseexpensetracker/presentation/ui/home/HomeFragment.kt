package com.example.budgetwiseexpensetracker.presentation.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.databinding.FragmentHomeBinding
import com.example.budgetwiseexpensetracker.presentation.adapter.HomeRecyclerAdapter
import kotlinx.coroutines.launch
import org.eazegraph.lib.models.PieModel
import java.util.Calendar

class HomeFragment : Fragment() {
    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    private val now: Calendar = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        initView()

    }

    override fun onResume() {
        super.onResume()
        serObserver()
    }



    private fun initView() {
        setViewModel()
        setMonth()
    }

    private fun setMonth() {
        val monthName = arrayOf(
            "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"
        )
        val month = monthName[now.get(Calendar.MONTH)]
        binding.tvMonth.text = month
    }

    private fun setViewModel() {
        viewModel.getRecentTransaction()
    }
    private fun serObserver() {
//        sharedViewModel.spendingData.observe(viewLifecycleOwner) { spendingData ->
//            Log.e("FTAG", "2serObserver: $spendingData")
//            // Update RecyclerView
//            (binding.fragmentMonthlySpendingSummary.rvRecentSpending.adapter as? HomeRecyclerAdapter)?.setData(spendingData)
//            // Update Pie Chart
//            updatePieChart(spendingData)
//        }
//        sharedViewModel.totalExpense.observe(viewLifecycleOwner) { totalExpense ->
//            Log.e("FTAG", "2serObserver: $totalExpense")
//            binding.tvExpenseTotal.text = "$${totalExpense}"
//        }
//        sharedViewModel.totalIncome.observe(viewLifecycleOwner) { totalIncome ->
//            Log.e("FTAG", "2serObserver: $totalIncome")
//            binding.tvIncomeTotal.text = "$${totalIncome}"
//        }
        lifecycleScope.launch {
            viewModel.showRecentTransaction.collect { trasaction ->
                        Log.e("FTAG", "setObserver: $trasaction")
                // Update RecyclerView
                (binding.fragmentMonthlySpendingSummary.rvRecentSpending.adapter as? HomeRecyclerAdapter)?.setData(
                    trasaction
                )
//            // Update Pie Chart
                updatePieChart(trasaction)
            }
        }

    }

    private fun updatePieChart(spendingData: MutableList<TransactionModel>) {
        binding.fragmentMonthlySpendingSummary.piechart.clearChart()  // Clear previous data to prevent overlap
        spendingData.forEach { item ->
            binding.fragmentMonthlySpendingSummary.piechart.addPieSlice(
                item.amount?.let {
                    PieModel(
                        item.title,  // Title from the spending data
                        it.replace(),  // Amount converted to Float
                        resources.getColor(
                            item.itemColor,
                            null
                        )  // Color resource ID for each category
                    )
                }
            )
        }
        binding.fragmentMonthlySpendingSummary.piechart.startAnimation()  // Start animation
    }

    private fun setAdapter() {
        homeRecyclerAdapter=HomeRecyclerAdapter()
        binding.fragmentMonthlySpendingSummary.rvRecentSpending.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeRecyclerAdapter

        }
    }

    private fun String.replace(): Float {
        return this.replace("- $", "").toFloat()
    }


}