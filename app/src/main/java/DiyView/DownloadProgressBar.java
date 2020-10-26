package DiyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import com.tai.TestTestTest.R;

public class DownloadProgressBar extends View {
    private int numTextSize;
    private int progressBarColor, backgroundBarColor;
    private float progressBarStrokeWidth, backgroundBarStrokeWidth;
    private float backgroundBarTop = 0, progressBarTop = 0;
    private float progressBarLength = 0, backgroundBarLength = 0;
    private float numWidth, numHeight;
    private double scale = 0;
    private Paint backgroundBarPaint = new Paint(), progressBarPaint = new Paint(), numPaint = new Paint();
    private String percentValueStr = "0.0%";
    private double maxValue = 0;
    private int minHeight;
    private int top;

    public DownloadProgressBar(Context context) {
        this(context, null);
    }

    public DownloadProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownloadProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        TypedArray typedArray    = getContext().obtainStyledAttributes(attrs, R.styleable.DownloadProgressBar);
        try {
            numTextSize              = (int) typedArray.getDimension(R.styleable.DownloadProgressBar_numTextSize, 12);
            //backgroundColor          = typedArray.getColor()
            progressBarColor         = typedArray.getColor(R.styleable.DownloadProgressBar_progressBarColor, Color.CYAN);
            backgroundBarColor       = typedArray.getColor(R.styleable.DownloadProgressBar_backgroundBarColor, Color.GRAY);
            progressBarStrokeWidth   = typedArray.getDimension(R.styleable.DownloadProgressBar_progressBarStrokeWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, dm));
            backgroundBarStrokeWidth = typedArray.getDimension(R.styleable.DownloadProgressBar_backgroundBarStrokeWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        } finally {
            typedArray.recycle();
        }
        measureNumSize();
        minHeight = (int) (Math.max(numHeight, Math.max(progressBarStrokeWidth, backgroundBarStrokeWidth)) + 0.5);
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    @SuppressLint("DefaultLocale")
    public void setProgressValue(final double progressValue) {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scale = progressValue / maxValue;
                percentValueStr = String.format("%.1f", scale * 100) + "%";
            }
        });
        invalidate();
        forceLayout();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width  = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int hMode  = MeasureSpec.getMode(heightMeasureSpec);
        /*
        // padding
        // 精确模式绘图区内缩；最大限制模式外伸，绘图区位置相对父控件不变
        // 太麻烦了，以后再搞
        int paddingStart  = getPaddingStart();
        int paddingEnd    = getPaddingEnd();
        int paddingTop    = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        */
        if (hMode == MeasureSpec.EXACTLY) {// math_parent和给定数值时
            if (height <= minHeight) {
                top = 0;
                height = minHeight;
            }
            else
                top = (height - minHeight) / 2;
        }
        else if (hMode == MeasureSpec.AT_MOST) {// warp_content时
            top = 0;
            height = minHeight;
        }

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);// 父方法是空的
        measureSize();
        // 绘制进度条
        progressBarPaint.setColor(progressBarColor);
        progressBarPaint.setStrokeWidth(progressBarStrokeWidth);
        canvas.drawLine(0, progressBarTop + top, progressBarLength, + progressBarTop + top, progressBarPaint);
        // 绘制背景条
        backgroundBarPaint.setColor(backgroundBarColor);
        backgroundBarPaint.setStrokeWidth(backgroundBarStrokeWidth);
        canvas.drawLine(getWidth() - backgroundBarLength, backgroundBarTop + top, getWidth(), backgroundBarTop + top, backgroundBarPaint);
        // 绘制百分比
        numPaint.setColor(progressBarColor);
        numPaint.setStrokeWidth(1);
        numPaint.setTextSize(numTextSize);
        numPaint.setAntiAlias(true);
        canvas.drawText(percentValueStr, progressBarLength, + numHeight + top, numPaint);
    }

    private void measureNumSize() {
        Paint paint = new Paint();
        Rect rect = new Rect();
        paint.setTextSize(numTextSize);
        paint.getTextBounds("100.0%", 0, "100.0%".length(), rect);
        numWidth = rect.width();
        numHeight = rect.height();
    }

    private void measureSize() {
        float backgroundBarTopTemp = numHeight / 2 - backgroundBarStrokeWidth / 2;
        float progressBarTopTemp   = numHeight / 2 - progressBarStrokeWidth / 2;
        backgroundBarTop = backgroundBarTopTemp > 0 ? backgroundBarTopTemp : 0f;
        progressBarTop   = progressBarTopTemp > 0 ? progressBarTopTemp : 0f;
        progressBarLength   = (float) (getWidth() * scale);
        if (progressBarLength + numWidth > getWidth())// 使得 进度条长度 + 百分比数字宽度 <= 总宽度
            progressBarLength = getWidth() - numWidth;
        backgroundBarLength = getWidth() - progressBarLength - numWidth;
    }
}
