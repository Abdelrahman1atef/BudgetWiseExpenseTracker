package com.example.budgetwiseexpensetracker.presentation.ui.income

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.data.model.TransactionModel
import com.example.budgetwiseexpensetracker.databinding.FragmentIncomeBinding
import com.example.budgetwiseexpensetracker.presentation.adapter.CustomSpinnerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class IncomeFragment : Fragment() {
    private lateinit var binding: FragmentIncomeBinding
    private val viewModel by viewModel<IncomeViewModel>()
    private var selectedCategory: String? = ""
    val formattedTime =
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Calendar.getInstance().time)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentIncomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        setBackArrowClick()
        editTextWatcher()
        setspinnerAdapter()
        saveData()

        binding.root.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
    }

    private fun saveData() {
        binding.btnContinue.setOnClickListener {
            if (binding.etAmount.text.toString() == "0") {
                Toast.makeText(requireContext(), "Enter Amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (selectedCategory.toString() == "") {
                Toast.makeText(requireContext(), "Select Category", Toast.LENGTH_SHORT).show()
            }
            lifecycleScope.launchWhenStarted {
                viewModel.saveTransaction(
                    TransactionModel(
                        title = selectedCategory.toString(),
                        subtitle = "",
                        icon = setIcon(),
                        amount = binding.etAmount.text.toString().toDouble(),
                        currentTime = formattedTime,
                        itemColor = setItemColor(),
                        type = "Income"
                    )
                )
            }
            findNavController().navigateUp()
        }
    }

    private fun setItemColor(): Int {
        when (selectedCategory) {
            "Salary" -> return R.color.Salary
            "Passive Income" -> return R.color.Passive_Income
            else -> return R.color.Salary
        }
    }

    private fun setIcon(): Int {
        when (selectedCategory) {
            "Salary" -> return R.drawable.salary
            "Other" -> return R.drawable.passive_income
            else -> return R.drawable.salary
        }
    }

    private fun setBackArrowClick() {
        var isMessageShown = false // Flag to track if message is shown
        binding.backArrow.setOnClickListener {
            if (!isMessageShown) {
                // Show the message
                if (binding.etAmount.text.toString() == "0") {
                    Toast.makeText(
                        requireContext(),
                        "Click the back arrow again to confirm",
                        Toast.LENGTH_SHORT
                    ).show()
                    isMessageShown = true // Set flag to true after showing the message
                } else {
                    findNavController().navigateUp() // Close activity if amount is 0
                }
            } else {
                // Close activity if message has already been shown
                findNavController().navigateUp()
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        activity?.currentFocus?.let { view ->
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
        binding.etAmount.clearFocus()
        binding.textInputEditText.clearFocus()
    }

    private fun setspinnerAdapter() {
        binding.spinner.setSelection(0)
        // Initialize CustomSpinnerAdapter
        val categories = resources.getStringArray(R.array.IncomeCategories)
        val customSpinnerAdapter = CustomSpinnerAdapter(requireContext(), categories)
        // Set adapter to spinner
        binding.spinner.adapter = customSpinnerAdapter
        spinnerAdapter(customSpinnerAdapter, categories)
    }

    private fun spinnerAdapter(
        customSpinnerAdapter: CustomSpinnerAdapter,
        categories: Array<String>
    ) {
        // Set OnItemSelectedListener
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != customSpinnerAdapter.hidingItemIndex) {
                    customSpinnerAdapter.selectedPosition = position
                    selectedCategory = when {
                        customSpinnerAdapter.selectedPosition <= 0 -> categories[customSpinnerAdapter.selectedPosition + 1]
                        customSpinnerAdapter.selectedPosition >= categories.size -> ""
                        else -> categories[customSpinnerAdapter.selectedPosition]
                    }
                    Log.e("spinner1", "Selected item: ${categories[position]}")
                    Log.e("spinner2", "Selected item: $selectedCategory")

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.e("spinner", "No item selected")
            }
        }


    }

    private fun editTextWatcher() {
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