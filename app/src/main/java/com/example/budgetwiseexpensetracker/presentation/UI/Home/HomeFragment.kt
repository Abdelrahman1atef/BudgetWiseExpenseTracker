package com.example.budgetwiseexpensetracker.presentation.UI.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetwiseexpensetracker.data.model.Model
import com.example.budgetwiseexpensetracker.databinding.FragmentHomeBinding
import com.example.budgetwiseexpensetracker.presentation.adapter.HomeRecyclerAdapter
import kotlinx.coroutines.launch
import org.eazegraph.lib.models.PieModel
import java.util.Calendar

class HomeFragment : Fragment() {
    private val sharedViewModel: HomeViewModel by activityViewModels() //Fragment
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    var fristTime =true
    val now: Calendar = Calendar.getInstance()




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
//        sharedViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

//        private val sharedViewModel: SharedViewModel by activityViewModels() //Fragment

//        private lateinit var sharedViewModel: SharedViewModel //Activity
//        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        if(fristTime){

            // Update RecyclerView
            (binding.fragmentMonthlySpendingSummary.rvRecentSpending.adapter as? HomeRecyclerAdapter)?.setData(sharedViewModel.list)
            // Update Pie Chart
            updatePieChart(sharedViewModel.list)
            fristTime=false
        }


    }

    private fun serObserver() {
        sharedViewModel.spendingData.observe(viewLifecycleOwner) { spendingData ->
            Log.e("FTAG", "2serObserver: $spendingData")
            // Update RecyclerView
            (binding.fragmentMonthlySpendingSummary.rvRecentSpending.adapter as? HomeRecyclerAdapter)?.setData(spendingData)
            // Update Pie Chart
            updatePieChart(spendingData)
        }
//        viewLifecycleOwner.lifecycleScope.launch {
//            sharedViewModel.state.collect() { state ->
//                val spendingData = state.transactions
//                Log.e("FTAG", "2serObserver: $spendingData")
//                // Update RecyclerView
//                (binding.fragmentMonthlySpendingSummary.rvRecentSpending.adapter as? HomeRecyclerAdapter)?.setData(spendingData)
//                // Update Pie Chart
//                updatePieChart(spendingData)
//            }
//        }

        sharedViewModel.totalExpense.observe(viewLifecycleOwner) { totalExpense ->
            Log.e("FTAG", "2serObserver: $totalExpense")
            binding.tvExpenseTotal.text = "$${totalExpense}"
        }
        sharedViewModel.totalIncome.observe(viewLifecycleOwner) { totalIncome ->
            Log.e("FTAG", "2serObserver: $totalIncome")
            binding.tvIncomeTotal.text = "$${totalIncome}"
        }

    }
    private fun updatePieChart(spendingData: MutableList<Model>) {
        binding.fragmentMonthlySpendingSummary.piechart.clearChart()  // Clear previous data to prevent overlap
        if (spendingData != null) {
            spendingData.forEach { item ->
                binding.fragmentMonthlySpendingSummary.piechart.addPieSlice(
                    item.amount?.let {
                        PieModel(
                            item.title,  // Title from the spending data
                            it.toString().replace(),  // Amount converted to Float
                            resources.getColor(item.itemColor, null)  // Color resource ID for each category
                        )
                    }
                )
            }
        }
        binding.fragmentMonthlySpendingSummary.piechart.startAnimation()  // Start animation
    }

    private fun setAdapter(){
        homeRecyclerAdapter=HomeRecyclerAdapter()
        binding.fragmentMonthlySpendingSummary.rvRecentSpending.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeRecyclerAdapter
            homeRecyclerAdapter.setData(sharedViewModel.list)
        }
    }

    private fun String.replace( ): Float {
        return this.replace("- $", "").toFloat()
    }







}