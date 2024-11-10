package com.example.budgetwiseexpensetracker.presentation.UI.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.data.model.Model
import com.example.budgetwiseexpensetracker.databinding.FragmentHomeBinding
import com.example.budgetwiseexpensetracker.presentation.UI.Expense.ExpenseActivity
import com.example.budgetwiseexpensetracker.presentation.adapter.HomeRecyclerAdapter
import org.eazegraph.lib.models.PieModel

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
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    var fristTime =true

//        private lateinit var viewModel:HomeViewModel
    private val sharedViewModel: HomeViewModel by activityViewModels() //Fragment

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
        setAdapter()
        initView()

    }

    override fun onResume() {
        super.onResume()
        serObserver()
    }

    private fun initView() {
        fabMainPage()
        setViewModel()
    }

    private fun setViewModel() {
//        sharedViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

//        private val sharedViewModel: SharedViewModel by activityViewModels() //Fragment

//        private lateinit var sharedViewModel: SharedViewModel //Activity
//        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        if(fristTime){
            Log.e("FTAG", "1serObserver: $fristTime")

            // Update RecyclerView
            (binding.fragmentMonthlySpendingSummary.rvRecentSpending.adapter as? HomeRecyclerAdapter)?.setData(sharedViewModel.list)
            // Update Pie Chart
            updatePieChart(sharedViewModel.list)
            Log.e("FTAG", "1serObserver: HHHHHHHHHHHHHHHHHHHHH")
            Log.e("FTAG", "1serObserver: ${sharedViewModel.list}")
            fristTime=false
        }


    }

    private fun serObserver() {
        Log.e("FTAG", "2serObserver: HHHHHHHHHHHHHHHHHHHHH")

        sharedViewModel.spendingData.observe(viewLifecycleOwner) { spendingData ->
            Log.e("FTAG", "2serObserver: $spendingData")
            // Update RecyclerView
            (binding.fragmentMonthlySpendingSummary.rvRecentSpending.adapter as? HomeRecyclerAdapter)?.setData(spendingData)
            // Update Pie Chart
            updatePieChart(spendingData)
        }
    }
    private fun updatePieChart(spendingData: MutableList<Model>?) {
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
//            val intent = Intent(requireContext(), ExpenseActivity::class.java)
//            startActivity(intent)
            setVisibility(clicked)
            setAnimation(clicked)
            clicked = !clicked
        }
        binding.addIncome.setOnClickListener {
//            intent = android.content.Intent(this, ExpenseActivity::class.java)
//            startActivity(intent)
            setVisibility(clicked)
            setAnimation(clicked)
            clicked = !clicked
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.addIncome.visibility = View.VISIBLE
            binding.addExpense.visibility = View.VISIBLE

        } else {
            binding.addIncome.visibility = View.INVISIBLE
            binding.addExpense.visibility = View.INVISIBLE
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