package com.example.budgetwiseexpensetracker.presentation.UI.Expense

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.databinding.ActivityExpenseBinding
import com.example.budgetwiseexpensetracker.databinding.FragmentExpenseBinding
import com.example.budgetwiseexpensetracker.presentation.UI.Home.HomeViewModel


class ExpenseFragment : Fragment() {
    private lateinit var binding: FragmentExpenseBinding
    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentExpenseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        initView()

    }
    private fun initView() {
        edittextWatcher()
        binding.backArrow.setOnClickListener {
            if (binding.etAmount.text.toString()!="0") {
                Toast.makeText(requireContext(),"click on save", Toast.LENGTH_SHORT).show()
            }else findNavController().popBackStack()
        }
        spinnerAdapter()
    }

    private fun spinnerAdapter() {
        val categories = resources.getStringArray(R.array.Categories)
        val adapter = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, categories) {
            override fun isEnabled(position: Int): Boolean {
                if (position ==0 ){
                    return false
                }else{
                    return true

                }
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                if (position == 0) {
                    view.setTextColor(Color.GRAY) // Hint color
                } else {
                    view.setTextColor(Color.BLACK) // Regular item color
                }
                return view
            }
        }

// Set the adapter
        binding.spinner.adapter = adapter
    }

    private fun edittextWatcher() {
        binding.etAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // No action here to avoid interfering with typing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Prevents adding "0" when editing, only applies "0" if empty when focus is lost
                if (s.isNullOrEmpty() && !binding.etAmount.hasFocus()) {
                    binding.etAmount.setText("0")
                    binding.etAmount.setSelection(binding.etAmount.text.length)
                }
            }
        })

// Add focus change listener to handle resetting to "0" when focus is lost
        binding.etAmount.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && binding.etAmount.text.isEmpty()) {
                binding.etAmount.setText("0")
            }
        }
    }

}