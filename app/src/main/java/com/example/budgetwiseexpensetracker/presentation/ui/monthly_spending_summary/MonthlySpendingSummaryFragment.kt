package com.example.budgetwiseexpensetracker.presentation.ui.monthly_spending_summary


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.databinding.FragmentMonthlySpendingSummaryBinding
import com.example.budgetwiseexpensetracker.presentation.adapter.MSSAdapter
import com.example.budgetwiseexpensetracker.utils.DateUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.eazegraph.lib.models.PieModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MonthlySpendingSummaryFragment : Fragment() {
    private lateinit var binding: FragmentMonthlySpendingSummaryBinding
    private val viewModel by viewModel<MonthlySpendingSummaryViewModel>()
    private lateinit var mSSAdapter: MSSAdapter
    private val tabTitle = arrayListOf("Income", "Expense")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonthlySpendingSummaryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        setAdapter()


        initView()
    }


    private fun initView() {
        binding.tvMonth.text = DateUtils.getMonth()
        handleTabSelection()
        setupTabs()
    }

    private fun setupTabs() {
        // Clear existing tabs
        binding.tabLayout.removeAllTabs()

        // Add tabs with custom views
        tabTitle.forEach { title ->
            val tab = binding.tabLayout.newTab()
            tab.customView = createTabView(title) // Simplified tab view creation
            binding.tabLayout.addTab(tab)
        }
        // Handle tab selection


        // Set default tab programmatically
        val defaultTab = binding.tabLayout.getTabAt(0)
        defaultTab?.select()
        observeIncomeTransactions() // Trigger default data load
    }
    private fun handleTabSelection() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<TextView>(R.id.tab_title)?.apply {
                    setBackgroundResource(R.drawable.tab_item_selected_bg)
                    setTextColor(resources.getColor(R.color.white, null))
                }

                // Load data based on the selected tab
                when (tab?.position) {
                    0 -> observeIncomeTransactions()
                    1 -> observeExpenseTransactions()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<TextView>(R.id.tab_title)?.apply {
                    setBackgroundResource(android.R.color.transparent)
                    setTextColor(resources.getColor(R.color.black, null))
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }
    private fun createTabView(title: String): View {
        return LayoutInflater.from(requireContext()).inflate(R.layout.tab_title, null).apply {
            findViewById<TextView>(R.id.tab_title).text = title
        }
    }
    private fun setViewModel() {
        viewModel.getIncomeTransaction()
        viewModel.getExpenseTransaction()
    }
    private fun observeIncomeTransactions() {
        lifecycleScope.launch {
            viewModel.showIncomeTransaction.collect { transactions ->
                updateView(transactions)
            }
        }
    }

    private fun observeExpenseTransactions() {
        lifecycleScope.launch {
            viewModel.showExpenseTransaction.collect { transactions ->
                updateView(transactions)
            }
        }
    }

    private fun updateView(transactions: MutableList<TransactionModel>) {
        mSSAdapter.setData(transactions) // Update the adapter with new data
        updatePieChart(transactions)    // Update the pie chart
    }


    private fun updatePieChart(spendingData: MutableList<TransactionModel>) {
        binding.piechart.clearChart()  // Clear previous data to prevent overlap
        spendingData.forEach { item ->
            binding.piechart.addPieSlice(item.amount?.let {
                PieModel(
                    item.title,  // Title from the spending data
                    it.toFloat(),  // Amount converted to Float
                    resources.getColor(
                        item.itemColor, null
                    )  // Color resource ID for each category
                )
            })
        }
        binding.piechart.startAnimation()  // Start animation
    }

    private fun setAdapter() {
        mSSAdapter = MSSAdapter()
        binding.rvSummary.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mSSAdapter

        }
    }
}





















