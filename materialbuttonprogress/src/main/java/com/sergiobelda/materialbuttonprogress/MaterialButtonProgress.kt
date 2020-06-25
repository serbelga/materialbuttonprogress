/*
 * Copyright 2020 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sergiobelda.materialbuttonprogress

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.ProgressIndicator

class MaterialButtonProgress(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var button: MaterialButton
    private var progressCircular: ProgressIndicator

    private var buttonText: String
    private var collapsed: Boolean = false

    init {
        inflate(context, R.layout.material_button_progress, this)

        button = findViewById(R.id.button)
        progressCircular = findViewById(R.id.progress_circular)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MaterialButtonProgress,
            0,
            0).apply {

            try {
                buttonText = getString(R.styleable.MaterialButtonProgress_text) ?: ""
            } finally {
                recycle()
            }
        }
        button.text = buttonText
    }

    override fun setOnClickListener(l: OnClickListener?) {
        button.setOnClickListener(l)
    }

    fun setText(text: CharSequence) {
        buttonText = text.toString()
        button.text = buttonText
    }

    fun isClickable(clickable: Boolean) {
        button.isClickable = clickable
    }

    fun collapse() {
        button.text = null
        progressCircular.show()
        val anim = ValueAnimator.ofInt(button.measuredWidth, progressCircular.width)
        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue
            val layoutParams = button.layoutParams
            layoutParams.width = value as Int
            button.layoutParams = layoutParams
        }
        anim.duration = 300
        anim.start()

        collapsed = true
    }

    fun expand() {
        button.text = buttonText
        progressCircular.hide()
        button.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val anim = ValueAnimator.ofInt(progressCircular.width, button.measuredWidth)
        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue
            val layoutParams = button.layoutParams
            layoutParams.width = value as Int
            button.layoutParams = layoutParams
        }
        anim.duration = 300
        anim.start()

        collapsed = false
    }

    fun isCollapsed() = collapsed
}