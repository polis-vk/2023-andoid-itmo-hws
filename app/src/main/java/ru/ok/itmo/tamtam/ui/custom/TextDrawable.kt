package ru.ok.itmo.tamtam.ui.custom

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

class TextDrawable : Drawable() {

    companion object {
        private const val DEFAULT_BORDER_WIDTH_DP = 2f
        const val TEXT_SIZE = 100f
        const val TEXT_COLOR = Color.WHITE
    }

    var text = ""
    var backgroundColor = ViewUtils.getRandomColor()
    var textColor = TEXT_COLOR
    var sizeOfText = TEXT_SIZE

    private var paint = Paint().apply {
        style = Paint.Style.FILL
        textSize = sizeOfText
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
        isFakeBoldText = true
    }

    override fun draw(canvas: Canvas) {

        paint.color = backgroundColor
        canvas.drawRect(bounds, paint)

        val baseLineDistance = (paint.ascent() + paint.descent()) / 2
        val x = bounds.width() / DEFAULT_BORDER_WIDTH_DP
        val y = bounds.height() / DEFAULT_BORDER_WIDTH_DP - baseLineDistance

        paint.color = textColor
        paint.textSize = sizeOfText
        canvas.drawText(text, x, y, paint)
    }

    override fun getOpacity(): Int = PixelFormat.TRANSPARENT

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        paint.colorFilter = cf
    }

}
