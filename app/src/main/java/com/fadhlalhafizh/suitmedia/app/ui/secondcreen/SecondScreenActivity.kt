package com.fadhlalhafizh.suitmedia.app.ui.secondcreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fadhlalhafizh.suitmedia.app.ui.firstscreen.MainActivity
import com.fadhlalhafizh.suitmedia.app.ui.thirdscreen.ThirdScreenActivity
import com.fadhlalhafizh.suitmedia.databinding.ActivitySecondScreenBinding

class SecondScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name").toString()
        val selectedUser = intent.getStringExtra("username").toString()

        binding.tvName.text = if (name.isNotEmpty()) name else "Fadhl al-Hafizh"
        binding.tvUserSelected.text = selectedUser

        if (selectedUser.isNotEmpty()) {
            binding.tvUserSelected.visibility = View.INVISIBLE
            binding.tvUserNotSelected.visibility = View.VISIBLE
            binding.tvUserNotSelected.text = selectedUser
        } else {
            binding.tvUserSelected.visibility = View.VISIBLE
            binding.tvUserNotSelected.visibility = View.VISIBLE
        }

        binding.ivArrowBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnChoose.setOnClickListener {
            startActivity(Intent(this, ThirdScreenActivity::class.java).apply {
                putExtra("name", binding.tvName.text.toString())
            })
        }

        playAnimation()
    }

    private fun playAnimation() {
        val elementsObjectAnimation = listOf(
            ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f),
            ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f),
            ObjectAnimator.ofFloat(binding.tvUserNotSelected, View.ALPHA, 1f),
            ObjectAnimator.ofFloat(binding.btnChoose, View.ALPHA, 1f),
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