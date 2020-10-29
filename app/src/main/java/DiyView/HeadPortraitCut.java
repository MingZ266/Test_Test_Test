package DiyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class HeadPortraitCut extends View {
    private float oneDp;
    private Paint paint;
    private Path path;

    public HeadPortraitCut(Context context) {
        this(context, null);
    }

    public HeadPortraitCut(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadPortraitCut(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        oneDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.0f, context.getResources().getDisplayMetrics());
        paint = new Paint();
        path = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wSize = MeasureSpec.getSize(widthMeasureSpec), wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec), hMode = MeasureSpec.getMode(heightMeasureSpec);
        if (wMode != MeasureSpec.EXACTLY)
            wSize = (int) (200 * oneDp);
        if (hMode != MeasureSpec.EXACTLY)
            hSize = (int) (200 * oneDp);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(wSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(hSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.rgb(0, 255, 255));
        paint.setStrokeWidth(oneDp);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        path.addRect(oneDp / 2, oneDp / 2, getWidth() - oneDp / 2, getHeight() - oneDp / 2, Path.Direction.CW);
        canvas.drawPath(path, paint);
        reset();
        paint.setColor(Color.rgb(74, 186, 196));
        paint.setStrokeWidth(oneDp);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        path.addOval(oneDp, oneDp, getWidth() - oneDp, getHeight() - oneDp, Path.Direction.CW);
        canvas.drawPath(path, paint);
        reset();
    }

    private void reset() {
        paint.reset();
        path.reset();
    }

    public void refresh() {
        invalidate();
        requestLayout();
    }
}
