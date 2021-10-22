    package com.chillandcode.tipapp_xml_layout

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chillandcode.tipapp_xml_layout.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        binding.calculateButton.setOnClickListener {
            calculateAndUpdateTip()
        }

        binding.calculateSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            calculateAndUpdateTip()
        }
        binding.tipOptions.check(R.id.option1)

        binding.tipOptions.setOnCheckedChangeListener { group, checkedId ->
            calculateAndUpdateTip()
        }
    }


    private fun calculateAndUpdateTip() {

        val tipPercent = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option1 -> .1
            R.id.option2 -> .15
            R.id.option3 -> .2
            R.id.option4 -> .25
            else -> 0
        }
        val cost: Double
        try {

            if (!binding.costOfService.text.isNullOrBlank()) {
                cost = binding.costOfService.text.toString().toDouble()
                val tip: Double = if (binding.calculateSwitch.isChecked) {
                    roundOffDecimal(cost * tipPercent.toDouble())

                } else
                    cost * tipPercent.toDouble()
                val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
                binding.tipValue.text = getString(R.string.tip_amount, formattedTip)
            } else {
                Snackbar.make(
                    binding.tipValue,
                    "Please provide cost of service",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } catch (e: Error) {
            Log.i("TRY N CATCH BLOCK - ", "calculateAndUpdateTip: Error ${e.message}")

            Snackbar.make(binding.tipValue, "Value incorrect", Snackbar.LENGTH_SHORT).show()
        }


    }

    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }
}