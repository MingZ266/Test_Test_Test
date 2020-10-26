package DiyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.tai.TestTestTest.R;

import java.util.Arrays;
import java.util.Random;

public class ImageCaptcha extends View {
    private int number;
    private float codeSize;
    private String removeChars;
    private boolean removeNumbers, removeCapitalLetter, removeSmallLetter;

    private StringBuffer code;
    private String codePic;
    private Random random = new Random();
    private Paint paint = new Paint();
    private int viewWidth, viewHeight;
    private int textWidth, textHeight;

    // 画点
    private int pointNum;
    private int[] pointColor;
    private float[] pointX, pointY;
    // 画字
    private int[] charColor;
    private int[][] charFlags;
    private float[] charX;
    // 画线
    private int lineNum;
    private int[] lineColor;
    private float[][] lineLocation;

    public ImageCaptcha(Context context) {
        this(context, null);
    }

    public ImageCaptcha(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageCaptcha(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImageCaptcha);
        try {
            number = typedArray.getInteger(R.styleable.ImageCaptcha_number, 4);
            codeSize = typedArray.getDimension(R.styleable.ImageCaptcha_codeSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 22, dm));
            removeChars = typedArray.getString(R.styleable.ImageCaptcha_removeChars);
            removeNumbers = typedArray.getBoolean(R.styleable.ImageCaptcha_removeNumbers, false);
            removeCapitalLetter = typedArray.getBoolean(R.styleable.ImageCaptcha_removeCapitalLetters, false);
            removeSmallLetter = typedArray.getBoolean(R.styleable.ImageCaptcha_removeSmallLetter, false);
        } finally {
            typedArray.recycle();
        }
        getRandomCode();
        measureTextBounds();
        getRandomPic();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width  = MeasureSpec.getSize(widthMeasureSpec);
        int wMode  = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int hMode  = MeasureSpec.getMode(heightMeasureSpec);
        if (wMode == MeasureSpec.EXACTLY)
            width = Math.max(width, viewWidth);
        else
            width = viewWidth;
        if (hMode == MeasureSpec.EXACTLY)
            height = Math.max(height, viewHeight);
        else
            height = viewHeight;
        widthMeasureSpec  = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画出白色背景
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        // 画点
        for (int i = 0; i < pointNum; i++) {
            paint.reset();
            paint.setColor(pointColor[i]);
            paint.setStrokeWidth(3);
            canvas.drawPoint(pointX[i], pointY[i], paint);
        }
        // 画字
        float oneCharWidth = (float) (textWidth / (2 * number + 1));
        float leftWidth = (float) (viewWidth / 2 - textWidth / 2);
        for (int i = 0, j = 0; i < codePic.length(); i++) {
            if (i % 2 == 1) {
                settingPaintStyle(charColor[j], charFlags[j][0], charFlags[j][1], charFlags[j][2]);
                canvas.drawText(codePic, i - 1, i + 1, leftWidth, charX[j], paint);
                leftWidth += oneCharWidth * 2;
                j++;
            }
        }
        // 画线
        for (int i = 0; i < lineNum; i++) {
            paint.reset();
            paint.setColor(lineColor[i]);
            paint.setStrokeWidth(2);
            paint.setAlpha((int) (255 * 0.8));// 80%透明度
            canvas.drawLine(lineLocation[i][0], lineLocation[i][1], lineLocation[i][2], lineLocation[i][3], paint);
        }
    }

    private int getRandomColor() {
        int r, g, b;
        do {
            r = random.nextInt(256);
            g = random.nextInt(256);
            b = random.nextInt(256);
        } while (r > 222 && g > 222 && b > 222);
        return Color.rgb(r, g, b);
    }

    private void getRandomCode() {
        code = new StringBuffer();
        // 奇数索引处为code，偶数索引处为一个空格
        // +1 使其对称美观
        char[] codeChars = new char[2 * number + 1];
        Arrays.fill(codeChars, ' ');
        if (removeNumbers && removeCapitalLetter && removeSmallLetter) {
            codePic = String.valueOf(codeChars);
            return;
        }
        char[] numbers = new char[10], smallLetter = new char[26], capitalLetter = new char[26];
        makeChars(numbers, '0');
        makeChars(capitalLetter, 'A');
        makeChars(smallLetter, 'a');
        if (removeChars != null) {
            for (int i = 0; i < removeChars.length(); i++) {
                DeleteChar number = new DeleteChar(numbers), capital = new DeleteChar(capitalLetter), small = new DeleteChar(smallLetter);
                char key = removeChars.charAt(i);
                number.deleteChar(key);
                capital.deleteChar(key);
                small.deleteChar(key);
                if (number.have)
                    numbers = number.desChars;
                if (capital.have)
                    capitalLetter = capital.desChars;
                if (small.have)
                    smallLetter = small.desChars;
                if (!(number.have || capital.have || small.have)) {
                    if (('0' <= key && key <= '9') || ('A' <= key && key <= 'Z') || ('a' <= key && key <= 'z'))
                        Log.w("wTAG", "字符'" + key + "'重复。");
                    else
                        Log.w("wTAG", "字符'" + key + "'不在可用字符里。");
                }
            }
        }
        for (int i = 0; i < codeChars.length; i++) {
            if (i % 2 == 1) {
                switch (random.nextInt(3)) {
                    case 0:// 0 - 9
                        if (removeNumbers)
                            i--;
                        else {
                            codeChars[i] = numbers[random.nextInt(numbers.length)];
                            code.append(codeChars[i]);
                        }
                        break;
                    case 1:// A - Z
                        if (removeCapitalLetter)
                            i--;
                        else {
                            codeChars[i] = capitalLetter[random.nextInt(capitalLetter.length)];
                            code.append(codeChars[i]);
                        }
                        break;
                    case 2:// a - z
                        if (removeSmallLetter)
                            i--;
                        else {
                            codeChars[i] = smallLetter[random.nextInt(smallLetter.length)];
                            code.append(codeChars[i]);
                        }
                        break;
                }
            }
        }
        codePic = String.valueOf(codeChars);
    }

    private void getRandomPic() {
        // 画点参数
        // 随机画100-120个点、随机颜色、随机位置
        pointNum = random.nextInt(21) + 100;
        pointColor = new int[pointNum];
        pointX = new float[pointNum];
        pointY = new float[pointNum];
        for (int i = 0; i < pointNum; i++) {
            pointColor[i] = getRandomColor();
            pointX[i] = random.nextFloat() * viewWidth;
            pointY[i] = random.nextFloat() * viewHeight;
        }
        // 画字参数
        // 随机高度、随机颜色、随机样式
        float drawHeight = (float) (viewHeight / 2 + textHeight / 2);
        charColor = new int[number];
        charFlags = new int[number][3];// fillFlag、typefaceFlag、styleFlag
        charX = new float[number];
        for (int i = 0; i < number; i++) {
            charColor[i] = getRandomColor();
            charFlags[i][0] = random.nextInt(3);// fillFlag
            charFlags[i][1] = random.nextInt(4);// typefaceFlag
            charFlags[i][2] = random.nextInt(10);// styleFlag
            charX[i] = (float) (drawHeight + (random.nextFloat() * textHeight / 2 - textHeight / 3));
        }
        // 画线参数
        // 随机画4-5条线、随机颜色、随机位置
        lineNum = random.nextInt(2) + 4;
        lineColor = new int[lineNum];
        lineLocation = new float[lineNum][4];// startX、startY、stopX、stopY
        for (int i = 0; i < lineNum; i++) {
            lineColor[i] = getRandomColor();
            lineLocation[i][0] = random.nextFloat() * viewWidth / 3 + (float) viewWidth / 6;  // startX
            lineLocation[i][1] = random.nextFloat() * viewHeight / 3 + (float) viewHeight / 3;// startY
            lineLocation[i][2] = random.nextFloat() * viewWidth / 3 + (float) viewWidth / 2;  // stopX
            lineLocation[i][3] = random.nextFloat() * viewHeight / 3 + (float) viewHeight / 3;// stopY
        }
    }

    private void makeChars(char[] chars, int firstChar) {
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) firstChar;
            firstChar++;
        }
    }

    private void settingPaintStyle(int color, int fillFlag, int typefaceFlag, int styleFlag) {
        paint.reset();
        paint.setColor(color);
        paint.setTextSize(codeSize);
        // 填充样式
        switch (fillFlag) {
            case 0:// 填充内部
                paint.setStyle(Paint.Style.FILL);
                break;
            case 1:// 描边
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1.5f);
                break;
        }
        // 正常体 粗体 斜体 粗斜体
        switch (typefaceFlag) {
            case 0:// 正常体
                paint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                break;
            case 1:// 粗体
                paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                paint.setTextSkewX(-0.5f);
                break;
            case 2:// 斜体
                paint.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                break;
            case 3:// 粗斜体
                paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
                break;
        }
        // 下划线 删除线
        switch (styleFlag) {// 50%的概率
            // 下划线  20%
            case 0:
            case 6:
                paint.setUnderlineText(true);
                break;
            // 删除线  30%
            case 1:
            case 5:
            case 9:
                paint.setStrikeThruText(true);
                break;
        }
    }

    private void measureTextBounds() {
        Paint textP = new Paint();
        Rect  textR = new Rect();
        textP.setTextSize(codeSize);
        textP.getTextBounds(codePic, 0, codePic.length(), textR);
        textWidth = textR.width();
        textHeight = textR.height();
        viewWidth = (int) (textWidth * 1.2);
        viewHeight = textHeight * 2;
    }

    public boolean correctInput(String inputStr) {
        return code.toString().toUpperCase().equals(inputStr.toUpperCase());
    }

    public void refresh() {
        getRandomCode();
        getRandomPic();
//        invalidate();
//        forceLayout();
        requestLayout();
    }

    private static class DeleteChar {
        boolean have = false;
        char[] desChars;
        private final char[] srcChars;

        DeleteChar(char[] srcChars) {
            this.srcChars = srcChars;
            desChars = new char[srcChars.length - 1];
        }

        void deleteChar(char key) {
            for (int i = 0; i < srcChars.length; i++) {
                if (key == srcChars[i]) {
                    System.arraycopy(srcChars, i + 1, srcChars, i, srcChars.length - i - 1);
                    System.arraycopy(srcChars, 0, desChars, 0, desChars.length);
                    have = true;
                    break;
                }
            }
        }
    }
}
//
// 字符  UNICODE编码
//  0        48
//  9        57
//
//  A        65
//  Z        90
//
//  a        97
//  z       122
//
