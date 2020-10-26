package DiyView;

import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.tai.TestTestTest.R;

public class WaterWaveRefresh extends View {
    private DisplayMetrics dm;
    private Paint paint;
    private Path path;
    private AnimatorSet animator;// 动画集合
    private float rearXDistance, frontXDistance, waveYDistance;// 后面的水波X方向位移  前面的水波X方向位移  Y方向位移

    // 双波：一凹一凸两条贝塞尔曲线组成的图形称为双波
    private float waveLength;// 双波长度
    private float waveHeight;// 控制点距双波中线高度，双波高约与其相等
    private int waveColor;// 主体颜色

    public WaterWaveRefresh(Context context) {
        this(context, null);
    }

    public WaterWaveRefresh(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterWaveRefresh(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dm = context.getResources().getDisplayMetrics();
        path = new Path();
        paint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterWaveRefresh);
        try {
            waveLength = typedArray.getDimension(R.styleable.WaterWaveRefresh_waveLength, fromDpToPx(60));
            waveHeight = typedArray.getDimension(R.styleable.WaterWaveRefresh_waveHeight, fromDpToPx(15));
            waveColor = typedArray.getColor(R.styleable.WaterWaveRefresh_waveColor, Color.rgb(25, 139, 236));
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wSize = MeasureSpec.getSize(widthMeasureSpec), wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec), hMode = MeasureSpec.getMode(heightMeasureSpec);
        if (wMode != MeasureSpec.EXACTLY)
            wSize = fromDpToPx(300);
        if (hMode != MeasureSpec.EXACTLY)
            hSize = fromDpToPx(90);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(wSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(hSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float distance = waveLength / 4.0f;
        paint.setColor(waveColor);
        // 后面的水波
        paint.setAlpha((int) (255 * 0.3));
        path.moveTo(- rearXDistance, getHeight() - waveYDistance);
        for (int i = 0; i < (int) (getWidth() / waveLength + 2.5); i++) {
            path.rQuadTo(distance, - waveHeight, 2.0f * distance, 0.0f);
            path.rQuadTo(distance, waveHeight, 2.0f * distance, 0.0f);
        }
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.lineTo(- rearXDistance, getHeight() - waveYDistance);
        canvas.drawPath(path, paint);
        // 前面的水波
        paint.setAlpha((int) (255 * 0.5));
        path.reset();
        path.moveTo(- frontXDistance, getHeight() - waveYDistance);
        for (int i = 0; i < (int) (getWidth() / waveLength + 0.5) * 2; i++) {
            path.rQuadTo(distance, - waveHeight, 2.0f * distance, 0.0f);
            path.rQuadTo(distance, waveHeight, 2.0f * distance, 0.0f);
        }
        path.rQuadTo(distance, - waveHeight, 2.0f * distance, 0.0f);
        path.rQuadTo(distance, waveHeight, 2.0f * distance, 0.0f);
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.lineTo(- frontXDistance, getHeight() - waveYDistance);
        canvas.drawPath(path, paint);
        paint.reset();
        path.reset();
    }

    public void startAnimator() {
        // 后面的水波X方向动画
        final ValueAnimator rearXAnimator = ValueAnimator.ofFloat(0, 2 * waveLength);
        rearXAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rearXAnimator.setRepeatMode(ValueAnimator.RESTART);
        // 前面的水波X方向动画
        final ValueAnimator frontXAnimator = ValueAnimator.ofFloat(0, 4 * waveLength);
        frontXAnimator.setRepeatCount(ValueAnimator.INFINITE);
        frontXAnimator.setRepeatMode(ValueAnimator.RESTART);
        // Y方向动画
        final ValueAnimator waveYAnimator = ValueAnimator.ofFloat(0, getHeight() * 3.0f / 5.0f);
        // 集合动画
        animator = new AnimatorSet();
        animator.playTogether(rearXAnimator, frontXAnimator, waveYAnimator);
        animator.setDuration(1000);
        animator.setInterpolator(new HalfLinearInterpolator());
        animator.start();

        frontXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rearXDistance = (float) rearXAnimator.getAnimatedValue();
                frontXDistance = (float) frontXAnimator.getAnimatedValue();
                waveYDistance = (float) waveYAnimator.getAnimatedValue() * 2.0f;
                requestLayout();
            }
        });
    }

    public void stopAnimator() {
        animator.cancel();
    }

    private int fromDpToPx(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, dm);
    }

    private static class HalfLinearInterpolator implements TimeInterpolator {

        @Override
        public float getInterpolation(float input) {
            return input * 0.5f;
        }
    }
}
