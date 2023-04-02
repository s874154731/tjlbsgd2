package com.sgd.tjlb.zhxf.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.sgd.tjlb.zhxf.R
import com.sgd.tjlb.zhxf.ui.activity.func.WechatLikeActivity
import com.sgd.tjlb.zhxf.utils.ViewUtils

/**
 *
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.widget
 * @ClassName: WeiXinPullHeaderMaskView
 * @Description: 动画遮罩
 * @CreateDate: 2023/3/10/010 16:04
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/10/010 16:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class WeiXinPullHeaderMaskView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet?,
    defStyleAttr: Int
) :
    View(context, attrs, defStyleAttr) {

    var isVibrator: Boolean = false;
    var progress: Int = 0;
    var maxHeight: Int = 0;
    private val CIRCLE_MAX_SIZE = 32;
    var parentHeight = 0;
    var paint = Paint()
    private val DEFAULT_CIRCLE_SIZE = 8f;

    init {
        setBackgroundColor(Color.argb(255, 239, 239, 239))
        paint.alpha = 255;
        paint.color = ContextCompat.getColor(context!!, R.color.white)
        paint.isAntiAlias = true;
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var value = height.toFloat() / maxHeight

        if (height <= maxHeight / 2) {
            canvas.drawCircle(
                (width / 2).toFloat(),
                (height / 2).toFloat(),
                CIRCLE_MAX_SIZE * value,
                paint
            )
        } else {
            if (progress < 100) {
                var diff = (value - 0.5f) * CIRCLE_MAX_SIZE
                canvas.drawCircle(
                    ((width / 2).toFloat() - ((0.4f - value) * 100)),
                    (height / 2).toFloat(),
                    DEFAULT_CIRCLE_SIZE,
                    paint
                )
                canvas.drawCircle(
                    ((width / 2).toFloat() + ((0.4f - value) * 100)),
                    (height / 2).toFloat(),
                    DEFAULT_CIRCLE_SIZE,
                    paint
                )
                if ((CIRCLE_MAX_SIZE * 0.5f) - diff <= DEFAULT_CIRCLE_SIZE) {
                    canvas.drawCircle(
                        (width / 2).toFloat(),
                        (height / 2).toFloat(),
                        DEFAULT_CIRCLE_SIZE,
                        paint
                    )
                } else {
                    canvas.drawCircle(
                        (width / 2).toFloat(),
                        (height / 2).toFloat(),
                        (CIRCLE_MAX_SIZE * 0.5f) - diff,
                        paint
                    )
                }

            } else {
                paint.alpha = getAlphaValue();
                canvas.drawCircle(
                    (width / 2).toFloat(),
                    (height / 2).toFloat(),
                    DEFAULT_CIRCLE_SIZE,
                    paint
                )
                canvas.drawCircle(
                    (width / 2).toFloat() - ((0.4f) * 100),
                    (height / 2).toFloat(),
                    DEFAULT_CIRCLE_SIZE,
                    paint
                )
                canvas.drawCircle(
                    (width / 2).toFloat() + (((0.4f) * 100)),
                    (height / 2).toFloat(),
                    DEFAULT_CIRCLE_SIZE,
                    paint
                )
            }
        }

    }

    private fun getAlphaValue(): Int {
        val dc = parentHeight / 3 - ViewUtils.getStatusBarHeight(resources);
        val alpha = ((height).toFloat() - dc) / (parentHeight - (dc))
        return 255 - (255 * alpha).toInt()
    }

    private fun vibrator() {
        var vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            var createOneShot = VibrationEffect.createOneShot(7, 255)
            vibrator.vibrate(createOneShot)
        } else {
            vibrator.vibrate(7)
        }
    }

    fun setProgress(value: Float, parentHeight: Int) {
        this.progress = value.toInt();
        this.parentHeight = parentHeight;
        if (value >= 100 && !isVibrator) {
            vibrator()
            isVibrator = true;
        }
        if (value < 100) {
            isVibrator = false;
        }
        if (progress >= 100) {
            setBackgroundColor(Color.argb(getAlphaValue(), 239, 239, 239))
            var mainActivity = context as WechatLikeActivity
            mainActivity.changeStatusBackgroundAlphaValue(getAlphaValue())
        } else {
            setBackgroundColor(Color.argb(255, 239, 239, 239))
        }
        invalidate()
    }
}