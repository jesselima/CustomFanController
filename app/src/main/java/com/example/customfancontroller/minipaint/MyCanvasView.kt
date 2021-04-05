package com.example.customfancontroller.minipaint

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.customfancontroller.R

/**
 * Created by jesselima on 4/04/21.
 * This is a part of the project Custom Fan Controller.
 */
class MyCanvasView(context: Context) : View(context) {

	private lateinit var cacheCanvas: Canvas
	private lateinit var cacheBitmap: Bitmap

	private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)

	override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int) {
		super.onSizeChanged(width, height, oldwidth, oldheight)

		/**
		 * Looking at onSizeChanged(), a new bitmap and canvas are created every time the function
		 * executes. You need a new bitmap, because the size has changed. However, this is a
		 * memory leak, leaving the old bitmaps around. To fix this, recycle extraBitmap before
		 * creating the next one.
		 */

		if (::cacheBitmap.isInitialized) cacheBitmap.recycle()

		/**
		 * @param Bitmap.Config.ARGB_8888: Each pixel is stored on 4 bytes. Each channel (RGB and
		 * alpha for translucency) is stored with 8 bits of precision (256 possible values.)
		 * This configuration is very flexible and offers the best quality. It should be used
		 * whenever possible
		 */
		cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

		cacheCanvas = Canvas(cacheBitmap)

		cacheCanvas.drawColor(backgroundColor)


	}

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)
		canvas.drawBitmap(cacheBitmap, 0f, 0f, null)
	}

}