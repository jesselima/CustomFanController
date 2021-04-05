package com.example.customfancontroller.minipaint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.customfancontroller.R

class MiniPaintActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		//setContentView(R.layout.activity_mini_paint)

		val myCanvasView = MyCanvasView(this)
		/**
		 * Request the full screen for the layout of myCanvasView. Do this by setting the
		 * SYSTEM_UI_FLAG_FULLSCREEN flag on myCanvasView. In this way, the view completely
		 * fills the screen.
		 */
		myCanvasView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

		myCanvasView.contentDescription = getString(R.string.canvas_content_description)

		setContentView(myCanvasView)
		/**
		 * You will need to know the size of the view for drawing, but you cannot get the size of
		 * the view in the onCreate() method, because the size has not been determined at this point.
		 */


		/**
		 * The onSizeChanged() method is called by the Android System whenever a view change its
		 * size. It is also called after the activity first create and inflates the view.
		 */
	}
}