package com.example.mykotlinapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mykotlinapp.databinding.ActivityMainBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var btnCalc: Button
    lateinit var btnReset: Button

    lateinit var grade1: AutoCompleteTextView
    lateinit var grade2: AutoCompleteTextView
    lateinit var grade3: AutoCompleteTextView
    lateinit var grade4: AutoCompleteTextView
    lateinit var grade5: AutoCompleteTextView
    lateinit var grade6: AutoCompleteTextView

    lateinit var cred1: EditText
    lateinit var cred2: EditText
    lateinit var cred3: EditText
    lateinit var cred4: EditText
    lateinit var cred5: EditText
    lateinit var cred6: EditText


    private lateinit var binding: ActivityMainBinding
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val items = listOf("O", "A+", "A", "B+", "B", "C", "F")
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, items)
        binding.gr1.setAdapter(adapter)
        binding.gr2.setAdapter(adapter)
        binding.gr3.setAdapter(adapter)
        binding.gr4.setAdapter(adapter)
        binding.gr5.setAdapter(adapter)
        binding.gr6.setAdapter(adapter)

        // Initialize EditText and AutoCompleteTextView
        cred1 = findViewById(R.id.cred1)
        cred2 = findViewById(R.id.cred2)
        cred3 = findViewById(R.id.cred3)
        cred4 = findViewById(R.id.cred4)
        cred5 = findViewById(R.id.cred5)
        cred6 = findViewById(R.id.cred6)

        grade1 = findViewById(R.id.gr_1)
        grade2 = findViewById(R.id.gr_2)
        grade3 = findViewById(R.id.gr_3)
        grade4 = findViewById(R.id.gr_4)
        grade5 = findViewById(R.id.gr_5)
        grade6 = findViewById(R.id.gr_6)

        btnCalc = findViewById(R.id.calc)
        btnReset = findViewById(R.id.reset)


        btnCalc.setOnClickListener(this)
        btnReset.setOnClickListener(this)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.calc -> {
                // Retrieve credit hours and grades
                val creditHours = arrayOf(
                    cred1.text.toString().toDoubleOrNull() ?: 0.0,
                    cred2.text.toString().toDoubleOrNull() ?: 0.0,
                    cred3.text.toString().toDoubleOrNull() ?: 0.0,
                    cred4.text.toString().toDoubleOrNull() ?: 0.0,
                    cred5.text.toString().toDoubleOrNull() ?: 0.0,
                    cred6.text.toString().toDoubleOrNull() ?: 0.0
                )

                // Check if any credit hour is missing
                if (creditHours.any { it < 0.0 }) {
                    toast("Please enter valid credit hours for all subjects")
                    return
                }

                val grades = arrayOf(
                    binding.gr1.text.toString(),
                    binding.gr2.text.toString(),
                    binding.gr3.text.toString(),
                    binding.gr4.text.toString(),
                    binding.gr5.text.toString(),
                    binding.gr6.text.toString()
                )

                // Check if any grade is missing
                if (grades.any { it.isEmpty() }) {
                    toast("Please select grades for all subjects")
                    return
                }

                // Calculate grade points
                val gradePoints = grades.map { gradeToGradePoint(it) }

                // Calculate total grade points
                val totalGradePoints = gradePoints.zip(creditHours).sumOf { it.first * it.second }

                // Calculate total credit hours
                val totalCreditHours = creditHours.sum()

                // Calculate CGPA
                val cgpa = totalGradePoints / totalCreditHours

                // Display CGPA
                toast("CGPA: $cgpa")
            }

            R.id.reset -> {
                // Clear all EditText and AutoCompleteTextView
                cred1.setText("")
                cred2.setText("")
                cred3.setText("")
                cred4.setText("")
                cred5.setText("")
                cred6.setText("")

                binding.gr1.setText("")
                binding.gr2.setText("")
                binding.gr3.setText("")
                binding.gr4.setText("")
                binding.gr5.setText("")
                binding.gr6.setText("")

                // Display reset message
                toast("Reset selected!")
            }
        }
    }


    // Function to convert grade to grade point
    private fun gradeToGradePoint(grade: String): Double {
        return when (grade) {
            "O" -> 10.0
            "A+" -> 9.0
            "A" -> 8.0
            "B+" -> 7.0
            "B" -> 6.0
            "C" -> 5.0
            "F" -> 0.0
            else -> 0.0 // Handle invalid grades
        }
    }
}
