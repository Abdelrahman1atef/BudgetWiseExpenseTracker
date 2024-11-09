package com.example.budgetwiseexpensetracker.presentation.UI.Expense

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.databinding.ActivityExpenseBinding
import com.example.budgetwiseexpensetracker.presentation.UI.Home.HomeViewModel
import com.example.budgetwiseexpensetracker.presentation.adapter.CustomSpinnerAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ExpenseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExpenseBinding
//    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedViewModel: HomeViewModel //Activity
    private var SelectedCategory: String? = ""
    val formattedTime =
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Calendar.getInstance().time)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityExpenseBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        sharedViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        initView()
    }

    private fun initView() {
        setBackArrowClick()
        editTextWatcher()
        setspinnerAdapter()
        observeData()
        binding.btnContinue.setOnClickListener {
            if (binding.etAmount.text.toString() == "0") {
                Toast.makeText(this, "Enter Amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (SelectedCategory.toString() == "") {
                Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show()
            }
//            Log.e("etAmount", "Amount: ${binding.etAmount.text}")
//            Log.e("spinner2", "Selected item: $SelectedCategory")
//            Log.e("Time", "time: $formattedTime")
            SelectedCategory?.let { it1 ->
                sharedViewModel.updateSpendingData(
                    it1,
                    binding.etAmount.text.toString(),
                    formattedTime
                )
            }
            setResult(Activity.RESULT_OK)
            finish()
        }
        binding.root.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
    }

    private fun observeData() {

    }

    private fun setBackArrowClick() {
        var isMessageShown = false // Flag to track if message is shown
        binding.backArrow.setOnClickListener {
            if (!isMessageShown) {
                // Show the message
                if (binding.etAmount.text.toString() == "0") {
                    Toast.makeText(
                        this,
                        "Click the back arrow again to confirm",
                        Toast.LENGTH_SHORT
                    ).show()
                    isMessageShown = true // Set flag to true after showing the message
                } else {
                    finish() // Close activity if amount is 0
                }
            } else {
                // Close activity if message has already been shown
                finish()
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let { inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0) }
        binding.etAmount.clearFocus()
        binding.textInputEditText.clearFocus()
    }

    private fun setspinnerAdapter() {
        binding.spinner.setSelection(0)
        // Initialize CustomSpinnerAdapter
        val categories = resources.getStringArray(R.array.Categories)
        val customSpinnerAdapter = CustomSpinnerAdapter(this, categories)
        // Set adapter to spinner
        binding.spinner.adapter = customSpinnerAdapter
        spinnerAdapter(customSpinnerAdapter, categories)
    }

    private fun spinnerAdapter(
        customSpinnerAdapter: CustomSpinnerAdapter,
        categories: Array<String>
    ) {
        var selectedCategory: String
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
                    SelectedCategory = when {
                        customSpinnerAdapter.selectedPosition <= 0 -> categories[customSpinnerAdapter.selectedPosition + 1]
                        customSpinnerAdapter.selectedPosition >= categories.size -> ""
                        else -> categories[customSpinnerAdapter.selectedPosition]
                    }
                    Log.e("spinner1", "Selected item: ${categories[position]}")
                    Log.e("spinner2", "Selected item: $SelectedCategory")

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