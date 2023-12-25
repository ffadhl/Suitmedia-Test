package com.fadhlalhafizh.suitmedia.app.ui.firstscreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fadhlalhafizh.suitmedia.app.ui.secondcreen.SecondScreenActivity
import com.fadhlalhafizh.suitmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnNext()
        btnPalindrome()
        playAnimation()
    }

    private fun btnNext() {
        binding.btnNext.setOnClickListener {
            if (binding.inputName.text.toString().isEmpty()) {
                Toast.makeText(this, "Please input your name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, SecondScreenActivity::class.java).apply {
                    putExtra("name", binding.inputName.text.toString())
                    putExtra("username", "")
                }
                startActivity(intent)
            }
        }
    }


    private fun btnPalindrome() {
        binding.btnCheck.setOnClickListener {
            val input = binding.inputPalindrome.text.toString()
            val originalLetters: Array<String> = input.split("").toTypedArray()
            val reverseLetters: Array<String> = originalLetters.reversedArray()
            val reverseWord = concatenation(reverseLetters)
            if (reverseWord == input) {
                Toast.makeText(this, "Palindrome", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Not Palindrome", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun concatenation(array: Array<String>): String {
        var index = 0
        val size = array.size
        var reversedWord = ""
        while (index < size) {
            reversedWord += array[index]
            index++
        }
        return reversedWord
    }

    private fun playAnimation() {
        val elementsObjectAnimation = listOf(
            ObjectAnimator.ofFloat(binding.ivPhoto, View.ALPHA, 1f),
            ObjectAnimator.ofFloat(binding.inputName, View.ALPHA, 1f),
            ObjectAnimator.ofFloat(binding.inputPalindrome, View.ALPHA, 1f),
            ObjectAnimator.ofFloat(binding.btnNext, View.ALPHA, 1f),
            ObjectAnimator.ofFloat(binding.btnCheck, View.ALPHA, 1f),
        ).map { it.setDuration(200) }

        val playSequentially = AnimatorSet().apply {
            playSequentially(*elementsObjectAnimation.toTypedArray())
        }
        AnimatorSet().apply {
            playSequentially(playSequentially)
            startDelay = 500
            start()
        }
    }
}