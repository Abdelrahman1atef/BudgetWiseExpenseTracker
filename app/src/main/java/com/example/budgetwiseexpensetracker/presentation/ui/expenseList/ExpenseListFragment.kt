package com.example.budgetwiseexpensetracker.presentation.ui.expenseList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgetwiseexpensetracker.databinding.FragmentExpenseListBinding
import com.example.budgetwiseexpensetracker.presentation.adapter.ExListRecyclerAdapter
import com.example.budgetwiseexpensetracker.presentation.adapter.HomeRecyclerAdapter
import com.example.budgetwiseexpensetracker.utils.DateUtils
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExpenseListFragment : Fragment() {
    private lateinit var binding: FragmentExpenseListBinding
    private lateinit var ExListRecyclerAdapter: ExListRecyclerAdapter
    private val viewModel by viewModel<ExpenseListViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExpenseListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        initView()
    }



    private fun initView() {
        setViewModel()
        setObserver()
        setView()

    }

    private fun setView() {
        binding.tvMonth.text = DateUtils.getMonth()
    }

    private fun setViewModel() {
        viewModel.getAllTransactions()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            viewModel.showAllTransactions.collect { trasaction ->
                (binding.rvTransaction.adapter as? ExListRecyclerAdapter)?.setData(
                    trasaction
                )
        }
    }
}
    private fun setAdapter() {
        ExListRecyclerAdapter = ExListRecyclerAdapter()
        binding.rvTransaction.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ExListRecyclerAdapter
        }
    }
}














