package com.danielml.mathsolver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.danielml.mathsolver.databinding.ActivityMainBinding
import com.google.android.material.internal.TextWatcherAdapter

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, TextWatcher {

  private lateinit var binding: ActivityMainBinding
  private var equations = arrayOf(R.drawable.quadratic, R.drawable.triangle_area, R.drawable.area_rectangle)
  private var equationToSolve: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    Thread.sleep(3000)
    setTheme(R.style.Theme_MathSolver)
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.button.isEnabled = false
    binding.aParameterEditText.addTextChangedListener(this)
    binding.bParameterEditText.addTextChangedListener(this)
    binding.cParameterEditText.addTextChangedListener(this)
    setUpSpinner()
  }

  fun setUpSpinner() {
    ArrayAdapter.createFromResource(
      this,
      R.array.equations,
      android.R.layout.simple_spinner_dropdown_item
    ).also {
      it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
      binding.spinner.adapter = it
    }
    binding.spinner.onItemSelectedListener = this
  }

  override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    if (parent != null) {
      this.equationToSolve = parent.selectedItemPosition
      binding.equationImageView.setImageResource(equations[parent.selectedItemPosition])
      binding.cParameterEditText.isVisible = !(equationToSolve == 1 || equationToSolve == 2)
      binding.cParameterEditText.setText(String())
      binding.bParameterEditText.setText(String())
      binding.aParameterEditText.setText(String())
    }
  }

  override fun onNothingSelected(parent: AdapterView<*>?) {}


  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

  override fun afterTextChanged(s: Editable?) {
    if (equationToSolve == 0) {
      binding.button.isEnabled = binding.aParameterEditText.text.toString() != ""
              && binding.bParameterEditText.text.toString() != ""
              && binding.cParameterEditText.text.toString() != ""
    } else {
      binding.button.isEnabled = binding.aParameterEditText.text.toString() != ""
              && binding.bParameterEditText.text.toString() != ""
    }
  }

  fun didTappedSolveButton(view: View) {
    val a: Double = binding.aParameterEditText.text.toString().toDouble()
    val b: Double = binding.bParameterEditText.text.toString().toDouble()
    var result: Double = 0.0

    when(equationToSolve){
      0 -> {
        val c: Double = binding.cParameterEditText.text.toString().toDouble()
        solveQuadraticEquation(a, b, c)
      }
      1 -> {
        result = a*b/2
        goToResultActivity(doubleArrayOf(result))
      }
      2 -> {
        result = a*b
        goToResultActivity(doubleArrayOf(result))
      }
    }

  }

  private fun goToResultActivity(result: DoubleArray) {
    val bundle: Bundle = Bundle()
    val intent: Intent = Intent(this, ResultActivity::class.java)
    bundle.putDoubleArray("result", result)
    intent.putExtras(bundle)
    startActivity(intent)
  }

  private fun solveQuadraticEquation(a: Double, b: Double, c: Double){
    val determinant = b * b - 4.0 * a * c
    var root1: Double
    var root2: Double

    // condition for real and different roots
    if (determinant > 0) {
      root1 = (-b + Math.sqrt(determinant)) / (2 * a)
      root2 = (-b - Math.sqrt(determinant)) / (2 * a)
    }
    // Condition for real and equal roots
    else if (determinant == 0.0) {
      root2 = -b / (2 * a)
      root1 = root2
    } else {
      Toast.makeText(this,getString(R.string.complex_roots_warning), Toast.LENGTH_LONG).show()
      return
    }
    goToResultActivity(doubleArrayOf(root1,root2))
  }
}

