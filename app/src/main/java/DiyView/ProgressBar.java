package DiyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.tai.TestTestTest.R;

public class ProgressBar extends View {
    private DisplayMetrics dm;
    private Paint paint = new Paint();
    private RectF rectFBar = new RectF();
    private RectF rectFLine = new RectF();
    private Rect textRect = new Rect();
    private Path pathLine = new Path();
    private Path pathBar = new Path();
    private Path pathGetW = new Path();
    private PathMeasure pathMeasure = new PathMeasure();
    private double W;
    private boolean firstRun = true;
    private double maxValue = 0.0;

    private int barColor;
    private float outlineWidth;
    private float cornerRadius;
    private float progressScale;
    private float textSize;
    private int textColor;
    private float textStrokeWidth;

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dm = context.getResources().getDisplayMetrics();
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressBar);
        try {
            barColor = typedArray.getColor(R.styleable.ProgressBar_barColor, Color.rgb(0, 168, 243));
            outlineWidth = typedArray.getDimension(R.styleable.ProgressBar_outlineWidth, fromDpToPx(2));
            cornerRadius = typedArray.getDimension(R.styleable.ProgressBar_cornerRadius, 0);
            progressScale = typedArray.getFloat(R.styleable.ProgressBar_progressScale, 0.85f);
            textSize = typedArray.getDimension(R.styleable.ProgressBar_textSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, dm));
            textColor = typedArray.getColor(R.styleable.ProgressBar_textColor, Color.WHITE);
            textStrokeWidth = typedArray.getDimension(R.styleable.ProgressBar_textStrokeWidth, 1.0f);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wSize = MeasureSpec.getSize(widthMeasureSpec), wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec), hMode = MeasureSpec.getMode(heightMeasureSpec);
        if (wMode != MeasureSpec.EXACTLY)
            wSize = fromDpToPx(200);
        if (hMode != MeasureSpec.EXACTLY)
            hSize = fromDpToPx(15);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(wSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(hSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onDraw(Canvas canvas) {
        float width = getWidth();
        float height = getHeight();
        String text = String.format("%.1f", progressScale * 100) + "%";// 进度条的文字内容
        float progressLen = progressScale * (width - outlineWidth);// 进度条的长度
        // 画框线
        paint.setAntiAlias(true);
        paint.setColor(barColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(outlineWidth);
        rectFLine.set(outlineWidth / 2.0f, outlineWidth / 2.0f, width - outlineWidth / 2.0f, height - outlineWidth / 2.0f);
        canvas.drawRoundRect(rectFLine, cornerRadius, cornerRadius, paint);
        // 画进度条
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(barColor);
        paint.setStyle(Paint.Style.FILL);
        rectFBar.set(outlineWidth, 0, progressLen, height);
        if (cornerRadius > 0) {
            if (firstRun) {
                firstRun = false;
                rectFLine.set(0, 0, width, height);
                pathLine.addRoundRect(rectFLine, cornerRadius, cornerRadius, Path.Direction.CW);
                // 获取框线两个圆角处总宽度
                pathGetW.addRect(0, 0, width, 1.0f, Path.Direction.CW);
                pathGetW.op(pathLine, Path.Op.INTERSECT);
                pathMeasure.setPath(pathGetW, false);
                W = width - pathMeasure.getLength() / 2;
            }
            if (progressLen < W && progressLen > 0)
                pathBar.addCircle(outlineWidth, height / 2.0f, progressLen, Path.Direction.CW);
            else
                pathBar.addRoundRect(rectFBar, cornerRadius, cornerRadius, Path.Direction.CW);
            pathBar.op(pathLine, Path.Op.INTERSECT);
            canvas.drawPath(pathBar, paint);
            pathBar.reset();
        } else
            canvas.drawRect(rectFBar, paint);
        // 画文字
        paint.reset();
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), textRect);
        float offset = (textRect.top + textRect.bottom) / 2.0f;// 基线偏移量
        float drawTextX = width / 2.0f;
        float drawTextY = height / 2.0f - offset;
        float textLeft = width / 2.0f - textRect.width() / 2.0f;
        float textRight = textLeft + textRect.width();
        paint.setTextAlign(Paint.Align.CENTER);
        // 文字描边
        if (progressLen >= textLeft && progressLen <= textRight) {
            paint.setColor(textColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(textStrokeWidth);
            canvas.drawText(text, drawTextX, drawTextY, paint);
        }
        // 文字
        if (progressLen <= textRight)
            paint.setColor(barColor);
        else
            paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, drawTextX, drawTextY, paint);
    }

    private int fromDpToPx(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, dm);
    }

    private void refresh() {
        invalidate();
        forceLayout();
        requestLayout();
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public void setScale(double nowValue) {
        if (maxValue == 0)
            throw new ArithmeticException("被除数maxValue为0 ==> 未设定maxValue的值或设定的maxValue值为0");
        progressScale = (float) (nowValue / maxValue);
        refresh();
    }

    public void setScale(double nowValue, double maxValue) {
        this.maxValue = maxValue;
        if (maxValue == 0)
            throw new ArithmeticException("被除数maxValue为0 ==> 设定的maxValue值为0");
        progressScale = (float) (nowValue / maxValue);
        refresh();
    }
}
