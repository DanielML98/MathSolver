package com.danielml.mathsolver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.danielml.mathsolver.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

  private lateinit var binding: ActivityResultBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityResultBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val result = intent.extras?.getDoubleArray("result")
    setUpUI(result)
  }

  fun setUpUI(result: DoubleArray?) {
    if(result?.count() == 1) {
      binding.titleTextview.text = String.format(getString(R.string.result_area_label), result[0].toString())
      return
    }
    binding.titleTextview.text = String.format(getString(R.string.result_quadratic_lable),
      result?.get(0).toString(), result?.get(1).toString()
    )
  }
}