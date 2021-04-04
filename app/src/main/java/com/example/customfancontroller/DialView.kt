package com.example.customfancontroller

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35

/**
 * @JvmOverloadsInstructs instructs the Kotlin compiler to generate overloads for this function
 * that substitute default parameter values. If a method has N parameters and M of which have
 * default values, M overloads are generated: the first one takes N-1 parameters (all but the last
 * one that takes a default value), the second takes N-2 parameters, and so on.
 */
class DialView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

	private var fanSpeedLowColor = 0
	private var fanSpeedMediumColor = 0
	private var fanSpeedMaxColor = 0


	/*
	The radius is the current radius of the circle. This value is set when the view is drawn on
	the screen.
	 */
	private var radius = 0.0f
	/*
	The fanSpeed is the current speed of the fan, which is one of the values in the FanSpeed
	enumeration. By default that value is OFF.
	 */
	private var fanSpeed = FanSpeed.OFF
	/*
	Finally pointPosition is an X,Y point that will be used for drawing several of the view's
	elements on the screen.
	 */
	private val pointPosition: PointF = PointF(0.0f, 0.0f)


	private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
		/**
		 * Set the paint's style, used for controlling how primitives'
		 * geometries are interpreted (except for drawBitmap, which always assumes
		 * Fill).
		 */
		style = Paint.Style.FILL
		/**
		 * Set the paint's text alignment. This controls how the
		 * text is positioned relative to its origin. LEFT align means that all of
		 * the text will be drawn to the right of its origin (i.e. the origin
		 * specified the LEFT edge of the text) and so on.
		 */
		textAlign = Paint.Align.CENTER
		/**
		 * Set the paint's text size. This value must be > 0
		 */
		textSize = 55.0f
		/**
		 * Set or clear the typeface object.
		 * Pass null to clear any previous typeface.
		 * As a convenience, the parameter passed is also returned.
		 */
		typeface = Typeface.create("", Typeface.BOLD)
	}

	override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
		radius = (min(width, height) / 2.0 * 0.8).toFloat()
	}

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)

		paint.color = when(fanSpeed) {
			FanSpeed.OFF -> Color.GRAY
			FanSpeed.LOW -> fanSpeedLowColor
			FanSpeed.MEDIUM -> fanSpeedMediumColor
			FanSpeed.HIGH -> fanSpeedMaxColor
		}

		canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)

		val markerRadius = radius + RADIUS_OFFSET_INDICATOR
		pointPosition.computeXYForSpeed(fanSpeed, markerRadius)

		paint.color = Color.BLACK
		canvas.drawCircle(pointPosition.x, pointPosition.y, radius / 12, paint)

		val labelRadius = radius + RADIUS_OFFSET_LABEL
		for(value in FanSpeed.values()) {
			pointPosition.computeXYForSpeed(value, labelRadius)
			val label = resources.getString(value.label)
			canvas.drawText(label, pointPosition.x, pointPosition.y, paint)
		}
	}

	private fun PointF.computeXYForSpeed(position: FanSpeed, radius: Float) {
		// Angles are in radians.
		val startAngle = Math.PI * (9 / 0.8)
		val angle = startAngle + position.ordinal * (Math.PI / 4)
		x = (radius * cos(angle)).toFloat() + width / 2
		y = (radius * sin(angle)).toFloat() + height / 2
	}

	/**
	 * The performClick() method calls onClickListener(). If you override performClick(), another
	 * contributor can still override onClickListener(). For example, if you create a custom view
	 * and make it available through a library for use or subclassing, its user can add their own
	 * click handling through onClickListener().
	 */

	/**
	 * To enable the CustomView to be Clickable:
	 *	- Set isClackBale to true.
	 *	- Implement performClick().
	 *	- Call invalidate()	to trigger reDraw.
	 */

	init {
		isClickable = true

		context.withStyledAttributes(attrs, R.styleable.DialView) {
			fanSpeedLowColor = getColor(R.styleable.DialView_fanColor1, 0)
			fanSpeedMediumColor = getColor(R.styleable.DialView_fanColor2, 0)
			fanSpeedMaxColor = getColor(R.styleable.DialView_fanColor3, 0)
		}
	}

	override fun performClick(): Boolean {
		/**
		 * The call to super.performClick() must happen first. WIch enables accessibility events
		 * as well as calls to OnClickListener.
		 */
		if(super.performClick()) return true

		fanSpeed = fanSpeed.next()
		contentDescription = resources.getString(fanSpeed.label)
		invalidate()
		return true
	}
}
