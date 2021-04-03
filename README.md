### Custom View Components

[Creating Custom Views](https://developer.android.com/training/custom-views/index.html)
[@JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)
[Custom Components](https://developer.android.com/guide/topics/ui/custom-components.html#compound)
[How Android Draws Views](https://developer.android.com/guide/topics/ui/how-android-draws.html)
[onMeasure()](https://developer.android.com/reference/android/view/View.html#onMeasure%28int,%20int%29)
[onSizeChanged()](https://developer.android.com/reference/android/view/View.html#onSizeChanged%28int,%20int,%20int,%20int%29)
[onDraw()](https://developer.android.com/reference/android/view/View.html#onDraw%28android.graphics.Canvas%29)
[Canvas](https://developer.android.com/reference/android/graphics/Canvas.html)
[Paint](https://developer.android.com/reference/android/graphics/Paint.html)
[rawText()](https://developer.android.com/reference/android/graphics/Canvas.html#drawText%28char[],%20int,%20int,%20float,%20float,%20android.graphics.Paint%29)
[setTypeface()](https://developer.android.com/reference/android/graphics/Paint.html#setTypeface%28android.graphics.Typeface%29)
[setColor()](https://developer.android.com/reference/android/graphics/Paint.html#setColor%28int%29)
[drawRect()](https://developer.android.com/reference/android/graphics/Canvas.html#drawRect%28android.graphics.Rect,%20android.graphics.Paint%29)
[drawOval()](https://developer.android.com/reference/android/graphics/Canvas.html#drawOval%28android.graphics.RectF,%20android.graphics.Paint%29)
[drawArc()](https://developer.android.com/reference/android/graphics/Canvas.html#drawArc%28android.graphics.RectF,%20float,%20float,%20boolean,%20android.graphics.Paint%29)


[drawBitmap()](https://developer.android.com/reference/android/graphics/Canvas.html#drawBitmap%28android.graphics.Bitmap,%20android.graphics.Matrix,%20android.graphics.Paint%29)
[setStyle()](https://developer.android.com/reference/android/graphics/Paint.html#setStyle%28android.graphics.Paint.Style%29)
[invalidate()](https://developer.android.com/reference/android/view/View.html#invalidate%28%29)
When invalidate is 

[View](https://developer.android.com/reference/android/view/View.html)
[PointF](https://developer.android.com/reference/android/graphics/PointF)
[drawCircle()](https://developer.android.com/reference/android/graphics/Canvas.html#drawCircle%28float,%20float,%20float,%20android.graphics.Paint%29)
[drawText()](https://developer.android.com/reference/android/graphics/Canvas.html#drawText%28java.lang.String,%20int,%20int,%20float,%20float,%20android.graphics.Paint%29)


##### CustomView interactions methods
[isClickable](https://developer.android.com/reference/android/view/View.html#isClickable%28%29)
Enables the custom view to respond to clicks

[performClick()](https://developer.android.com/reference/android/view/View#performClick%28%29)
Used to perform operation when the view is clicked.
Normally with a standard Android View, you implement the `onClickListener { }` to perform an action 
when the user clicks in that view. For the `CustomView` you have to implement the view classes 
`performClick()` method instead and call `super.performClick()`.
The default `performClick()` also calls the `onClickListener()`, so you can add you actions to 
`performClick()`, and leave the `onClickListener { }` for further customizations by you or other 
developers that might use your CustomView.

[invalidate()](https://developer.android.com/reference/android/view/View.html#invalidate%28%29)
Instruct the Android System to call the onDraw method to redraw the view.



[OnClickListener()](https://developer.android.com/reference/android/view/View.OnClickListener.html)
Used to perform an action when the user clicks the view