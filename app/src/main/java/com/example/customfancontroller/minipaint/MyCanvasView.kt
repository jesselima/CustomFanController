package com.example.customfancontroller.minipaint

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import com.example.customfancontroller.R
import kotlin.math.abs

/**
 * Created by jesselima on 4/04/21.
 * This is a part of the project Custom Fan Controller.
 */

private const val STROKE_WIDTH = 12f

class MyCanvasView(context: Context) : View(context) {

	private lateinit var cacheCanvas: Canvas
	private lateinit var cacheBitmap: Bitmap

	private var motionTouchEventX = 0f
	private var motionTouchEventY = 0f
	private var currentX = 0f
	private var currentY = 0f

	private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)
	private val drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)

	private lateinit var frame: Rect

	// scaledTouchSlop: Distance in pixels a touch can wander before we think the user is scrolling
	private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

	// Set up the paint with which to draw.
	private val paint = Paint().apply {
		// Draw color
		color = drawColor
		// Smooths out edges of what is drawn without affecting shape.
		isAntiAlias = true
		// Dithering affects how colors with higher-precision than the device are down-sampled.
		isDither = true
		style = Paint.Style.STROKE		// Default: FILL
		strokeJoin = Paint.Join.ROUND	// Default: MITER
		strokeCap = Paint.Cap.ROUND		// Default: BUTT
		strokeWidth = STROKE_WIDTH		// Default: Hairline-width
	}

	private var path = Path()

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
		 * The param Bitmap.Config.ARGB_8888: Each pixel is stored on 4 bytes. Each channel (RGB and
		 * alpha for translucency) is stored with 8 bits of precision (256 possible values.)
		 * This configuration is very flexible and offers the best quality. It should be used
		 * whenever possible
		 */
		cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
		cacheCanvas = Canvas(cacheBitmap)
		cacheCanvas.drawColor(backgroundColor)

		val inset = 40
		/**
		 * Create a new rectangle with the specified coordinates. Note: no range
		 * checking is performed, so the caller must ensure that left <= right and
		 * top <= bottom.
		 *
		 * @param left   The X coordinate of the left side of the rectangle
		 * @param top    The Y coordinate of the top of the rectangle
		 * @param right  The X coordinate of the right side of the rectangle
		 * @param bottom The Y coordinate of the bottom of the rectangle
		 */
		frame = Rect(inset, inset, width - inset, height - inset)
	}

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)
		canvas.drawBitmap(cacheBitmap, 0f, 0f, null)
		// Info - Draw the frame on the canvas
		canvas.drawRect(frame, paint)
	}

	@SuppressLint("ClickableViewAccessibility")
	override fun onTouchEvent(event: MotionEvent): Boolean {
		motionTouchEventX = event.x
		motionTouchEventY = event.y

		when (event.action) {
			MotionEvent.ACTION_DOWN -> touchStart()
			MotionEvent.ACTION_MOVE -> touchMove()
			MotionEvent.ACTION_UP -> touchUp()
		}
		return true
	}

	private fun touchStart() {
		path.reset()
		path.moveTo(motionTouchEventX, motionTouchEventY)
		currentX = motionTouchEventX
		currentY = motionTouchEventY
	}

	private fun touchMove() {
		// Info - Calculate the traveled distance dx and dy (the distance that has been moved)
		val dx = abs(motionTouchEventX - currentX)
		val dy = abs(motionTouchEventY - currentY)

		// Info - Create a curve between 2 points
		if (dx >= touchTolerance || dy >= touchTolerance) {
			// Info - quadTo() adds a quadratic bezier from the last point,
			//  approaching control point (x1, y1) and ending at (x2, y2).
			//  In other words add a segment to the path.
			//  Using quadTo instead lineTo, create a smoothly draw line without corners
			path.quadTo(
				currentX,
				currentY,
				(motionTouchEventX + currentX) / 2,
				(motionTouchEventY + currentY) / 2
			)
			// Info update the current X and Y values
			//  This set start point for the next segment to the end pond of this segment.
			currentX = motionTouchEventX
			currentY = motionTouchEventY
			// Info - Draw tha path in the extra bitmap to cache it.
			cacheCanvas.drawPath(path, paint)
		}
		// Info - Call invalidate to force redrawing of the screen with the updated path.
		invalidate()

	}

	// Info - When the user lifts their touch we reset the path
	private fun touchUp() {
		path.reset()
	}

}