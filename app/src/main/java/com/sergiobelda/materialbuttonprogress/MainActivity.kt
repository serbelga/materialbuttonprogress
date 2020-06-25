package com.sergiobelda.materialbuttonprogress

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.ProgressIndicator


class MainActivity : AppCompatActivity() {
    private lateinit var button: MaterialButton
    private lateinit var myButton: MaterialButtonProgress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        myButton = findViewById(R.id.my_button)

        button.setOnClickListener {
            val anim = ValueAnimator.ofInt(it.measuredWidth, 120)
            anim.addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue
                val layoutParams: ViewGroup.LayoutParams = it.layoutParams
                layoutParams.width = value as Int
                it.layoutParams = layoutParams
                button.text = ""
            }
            anim.duration = 100
            anim.start()
        }
        myButton.setText("qwertffdsfasfdasy")
        myButton.setOnClickListener {
            if (myButton.isCollapsed()) {
                myButton.expand()
            } else {
                myButton.collapse()
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}