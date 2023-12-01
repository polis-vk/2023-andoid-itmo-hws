package ru.ok.itmo.tamtam.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import ru.ok.itmo.tamtam.R
import kotlin.random.Random


class AvatarImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val randomValueForShow = Random.nextInt(0, 10)
    private val clipPath = Path()

    private var textDrawable = TextDrawable()

    var text: String = ""
        set(value) {
            textDrawable.text = convertTextToAvatarText(value)
        }

    init {
        textDrawable.text = text

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageView)

            textDrawable.backgroundColor = a.getColor(
                R.styleable.AvatarImageView_crl_backgroundColor,
                ViewUtils.getRandomColor()
            )

            textDrawable.textColor = a.getColor(
                R.styleable.AvatarImageView_crl_textColor,
                TextDrawable.TEXT_COLOR
            )

            textDrawable.sizeOfText = a.getFloat(
                R.styleable.AvatarImageView_crl_textSize,
                TextDrawable.TEXT_SIZE
            )

            a.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        val x = width / 2f
        val y = height / 2f
        val radius = Math.min(x, y)

        clipPath.addCircle(x, y, radius, Path.Direction.CW)
        canvas.clipPath(clipPath)

        super.onDraw(canvas)

        if ( randomValueForShow < 8) {
            setImageDrawable(textDrawable)
        } else {
            setImageDrawable(resources.getDrawable(R.drawable.avatar_example))
        }
    }

    fun setTextColor(color: Int) {
        textDrawable.textColor = color
        invalidate()
    }

    fun setBackground(color: Int) {
        textDrawable.backgroundColor = color
        invalidate()
    }

    fun setTextSize(size: Float) {
        textDrawable.sizeOfText = size
        invalidate()
    }

    private fun convertTextToAvatarText(str: String): String {
        var ans : String = str.firstOrNull().toString()
        val firstWhiteSpace = str.indexOf(' ')

        if (firstWhiteSpace != -1) {
            ans += str[firstWhiteSpace]
        }
        return ans.uppercase()
    }

}
