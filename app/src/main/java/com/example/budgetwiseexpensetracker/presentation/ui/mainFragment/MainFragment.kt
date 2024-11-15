package com.example.budgetwiseexpensetracker.presentation.ui.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.budgetwiseexpensetracker.R
import com.example.budgetwiseexpensetracker.databinding.FragmentMainBinding

class MainFragment : Fragment() {
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
    private lateinit var binding: FragmentMainBinding
    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigationMainPage.menu.getItem(2).isEnabled = false
        binding.bottomAppBar.setPadding(0,0,0,0)
        binding.bottomNavigationMainPage.setPadding(0,0,0,0)
        // Handle window insets to avoid bottom padding
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomAppBar) { v, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Adjust padding based on system bars insets
            v.setPadding(0, 0, 0, systemBarsInsets.bottom)
            insets // Return the insets to be passed on to the next listener
        }

        // Optionally, you can apply the same for bottomNavigationMainPage
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigationMainPage) { v, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, 0, 0, systemBarsInsets.bottom)
            insets // Return the insets to be passed on
        }
        initView()
    }

    private fun initView() {
        fabMainPage()

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
            findNavController().navigate(R.id.action_mainFragment_to_expenseFragment2)
            setVisibility(clicked)
            setAnimation(clicked)
            clicked = !clicked
        }
        binding.addIncome.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_incomeFragment)
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