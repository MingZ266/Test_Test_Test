package DiyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.tai.TestTestTest.R;

public class ProgressBar2 extends View {
    private DisplayMetrics dm;
    private int blockNum;// 进度条中的小方块总数量
    private int drawBlockNum;// 进度条中需绘制的小方块数量
    private boolean noSetDrawNum = true;// 标记是否已通过set方法给出了绘制的小方块数量
    private double maxValue = 0.0;
    private Paint paint = new Paint();
    private RectF rectF = new RectF();

    private int backgroundColor;         // 背景及框线颜色
    private int progressColor;           // 进度条颜色
    private int progressBlockWidth;      // 进度块宽度
    private float progressCornerRadius;  // 进度块圆角半径
    private float backgroundCornerRadius;// 背景及框线圆角半径
    private float backgroundAlpha;       // 背景透明度
    private boolean showBackground;      // 是否显示背景
    private boolean showOutline;         // 是否显示框线
    private int outlineWidth;            // 框线宽度

    public ProgressBar2(Context context) {
        this(context, null);
    }

    public ProgressBar2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBar2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dm = context.getResources().getDisplayMetrics();
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressBar2);
        try {
            backgroundColor = typedArray.getColor(R.styleable.ProgressBar2_backgroundColor, Color.BLACK);
            progressColor = typedArray.getColor(R.styleable.ProgressBar2_progressColor, Color.BLUE);
            progressBlockWidth = (int) typedArray.getDimension(R.styleable.ProgressBar2_progressBlockWidth, fromDpToPx(10));
            progressCornerRadius = typedArray.getDimension(R.styleable.ProgressBar2_progressCornerRadius, fromDpToPx(4.5f));
            backgroundCornerRadius = typedArray.getDimension(R.styleable.ProgressBar2_backgroundCornerRadius, fromDpToPx(4.5f));
            backgroundAlpha = typedArray.getFloat(R.styleable.ProgressBar2_backgroundAlpha, 0.5f);
            showBackground = typedArray.getBoolean(R.styleable.ProgressBar2_showBackground, true);
            showOutline = typedArray.getBoolean(R.styleable.ProgressBar2_showOutline, true);
            outlineWidth = (int) typedArray.getDimension(R.styleable.ProgressBar2_outlineWidth2, fromDpToPx(2));
        } finally {
            typedArray.recycle();
        }
        if (progressBlockWidth <= 0)
            progressBlockWidth = fromDpToPx(1);
    }

    private int fromDpToPx(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, dm);
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public void setPercentage(double value) {
        if (maxValue == 0)
            throw new ArithmeticException("被除数maxValue值为0 ==> 未设定maxValue或设定的maxValue值为0");
        noSetDrawNum = false;
        if (value == maxValue)
            drawBlockNum = blockNum;
        else
            drawBlockNum = (int) ((value / maxValue) * blockNum + 0.25);
        invalidate();
        forceLayout();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wSize = MeasureSpec.getSize(widthMeasureSpec), wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec), hMode = MeasureSpec.getMode(heightMeasureSpec);
        // 进度条与背景边界线间隔2dp
        // 原则：宽度至少需容纳一个进度块；多块宽度不足则取少且总宽度紧贴
        if (2 * progressBlockWidth + fromDpToPx(5) >= wSize)
            blockNum = 1;
        else {
            if (wMode == MeasureSpec.EXACTLY)
                // 由计算总长度逆推得来的公式去算出包含的进度块块数，结果只取整数部分
                blockNum = (wSize - fromDpToPx(3)) / (progressBlockWidth + fromDpToPx(1));
            else
                blockNum = 20;// 默认20块，每块代表5%
        }
        wSize = blockNum * progressBlockWidth + fromDpToPx(blockNum + 3);// 计算总长度
        if (noSetDrawNum)
            drawBlockNum = blockNum;
        if (hMode == MeasureSpec.EXACTLY) {
            if (hSize < fromDpToPx(5))
                hSize = fromDpToPx(5);// 至少需5dp高度
        } else {
            hSize = fromDpToPx(19);// 15dp块高加上下边界2dp
        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(wSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(hSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int oneDp = fromDpToPx(1);
        int twoDp = fromDpToPx(2);
        int height = getHeight();
        // 画背景
        rectF.set(0, 0, getWidth(), height);
        if (showBackground) {
            paint.setColor(backgroundColor);
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha((int) (255 * backgroundAlpha));
            canvas.drawRoundRect(rectF, backgroundCornerRadius, backgroundCornerRadius, paint);
        }
        // 画框线
        if (showOutline) {
            paint.reset();
            paint.setColor(backgroundColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(outlineWidth);
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawRoundRect(rectF, backgroundCornerRadius, backgroundCornerRadius, paint);
        }
        // 画小方块
        int startX = twoDp;
        paint.reset();
        paint.setColor(progressColor);
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < drawBlockNum; i++) {
            rectF.set(startX, twoDp, startX + progressBlockWidth, height - twoDp);
            canvas.drawRoundRect(rectF, progressCornerRadius, progressCornerRadius, paint);
            startX += (progressBlockWidth + oneDp);
        }
    }
}
