package com.danielml.mathsolver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.danielml.mathsolver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    Thread.sleep(3000)
    setTheme(R.style.Theme_MathSolver)
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
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
    when(parent?.selectedItemPosition) {
      0 -> binding.equationImageView.setImageResource(R.drawable.quadratic)
      1 -> binding.equationImageView.setImageResource(R.drawable.triangle_area)
      2 -> binding.equationImageView.setImageResource(R.drawable.area_rectangle)
    }
  }

  override fun onNothingSelected(parent: AdapterView<*>?) {}

}