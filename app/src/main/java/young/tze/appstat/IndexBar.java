package young.tze.appstat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Yangzhi on 2017-06-29.
 */

public class IndexBar extends View {
    private int mIndexBarTextSize;
    private int mIndexBarTopPadding;
    private int mIndexBarPadding;
    private int mIndexBarTextLeading;
    private Paint mIndexTextPaint;
    private Rect mIndexBarTextBounds;
    private int mIndexBarTextColor;
    private ArrayList<IndexChangedListener> mIndexChangedListeners;

    private Paint mFloatCirclePaint;
    private int mFloatCircleRadius;
    private Paint mFloatCircleTextPaint;
    private String mIndexText;

    public IndexBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mIndexBarTextSize = getResources().getDimensionPixelSize(R.dimen.index_bar_text_size);
        mIndexBarPadding = getResources().getDimensionPixelSize(R.dimen.index_bar_padding);
        mIndexBarTextLeading = getResources().getDimensionPixelSize(R.dimen.index_bar_text_leading);
        mIndexBarTextColor = getContext().getResources().getColor(R.color.labelTextColor);
        mIndexTextPaint = new Paint();
        mIndexTextPaint.setTextSize(mIndexBarTextSize);
        mIndexTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mIndexTextPaint.setColor(mIndexBarTextColor);
        mIndexBarTextBounds = new Rect();
        mIndexTextPaint.getTextBounds("A", 0, 1, mIndexBarTextBounds);
        mIndexTextPaint.setTextAlign(Paint.Align.CENTER);
        mIndexChangedListeners = new ArrayList<>();

        mIndexBarTopPadding = (Utilities.getDisplayMetrics(getContext()).heightPixels
                - 27 * (mIndexBarTextBounds.height() + mIndexBarTextLeading)) / 2;

        mFloatCirclePaint = new Paint();
        mFloatCirclePaint.setColor(context.getResources().getColor(R.color.itemGapColor));
        mFloatCircleRadius = 3 * context.getResources().getDimensionPixelSize(R.dimen.item_label_text_size) / 2;
        mFloatCircleTextPaint = new Paint();
        mFloatCircleTextPaint.setColor(Color.WHITE);
        mFloatCircleTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mFloatCircleTextPaint.setTextSize(2 * context.getResources().getDimensionPixelSize(R.dimen.item_label_text_size));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(new Rect(getWidth() - mIndexBarTextBounds.width()
                - 2 * mIndexBarPadding, 0, getWidth(), getHeight()), mFloatCircleTextPaint);
        mIndexTextPaint.setAntiAlias(true);
        String indexText;
        for (int i = 0; i < 27; i++) {
            if (i < 26) {
                indexText = "" + (char) ('A' + i);
            } else {
                indexText = "#";
            }
            if (indexText.equals(mIndexText)) {
                mIndexTextPaint.setColor(Color.BLUE);
            } else {
                mIndexTextPaint.setColor(Color.BLACK);
            }
            canvas.drawText(indexText, getWidth() - mIndexBarTextBounds.width() / 2 - mIndexBarPadding, mIndexBarTopPadding
                    + i * (mIndexBarTextBounds.height() + mIndexBarTextLeading), mIndexTextPaint);
        }

        mIndexTextPaint.setAntiAlias(false);

        if (mIndexText == null) {
            return;
        }
        canvas.drawCircle(Utilities.getDisplayMetrics(getContext()).widthPixels / 2,
                Utilities.getDisplayMetrics(getContext()).heightPixels / 2, mFloatCircleRadius, mFloatCirclePaint);
        mFloatCircleTextPaint.setAntiAlias(true);
        //设置文本水平左右居中
        mFloatCircleTextPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fm = mFloatCircleTextPaint.getFontMetrics();
        float y = Utilities.getDisplayMetrics(getContext()).heightPixels / 2 - fm.descent + (fm.bottom - fm.top) / 2;
        canvas.drawText("" + mIndexText, Utilities.getDisplayMetrics(getContext()).widthPixels / 2,
                y, mFloatCircleTextPaint);
        mFloatCircleTextPaint.setAntiAlias(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < Utilities.getDisplayMetrics(getContext())
                        .widthPixels - mIndexBarTextBounds.width() - 2 * mIndexBarPadding) {
                    return false;
                }
            case MotionEvent.ACTION_MOVE:
                if (event.getY() >= mIndexBarTopPadding && event.getY() <= mIndexBarTopPadding
                        + 27 * (mIndexBarTextBounds.bottom - mIndexBarTextBounds.top + mIndexBarTextLeading)) {
                    double alphaIndex = Math.floor((event.getY() - mIndexBarTopPadding) / (mIndexBarTextLeading + mIndexBarTextBounds.height()));
                    String letter = "" + (char) ('A' + alphaIndex);
                    if (alphaIndex == 26) {
                        letter = "#";
                    }

                    if (mIndexChangedListeners.size() > 0) {
                        for (IndexChangedListener l : mIndexChangedListeners) {
                            mIndexText = letter;
                            if (l.indexChanged(letter)) {
                                invalidate();
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIndexChangedListeners.size() > 0) {
                    for (IndexChangedListener l : mIndexChangedListeners) {
                        mIndexText = null;
                        l.indexChanged(null);
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void addListener(IndexChangedListener l) {
        mIndexChangedListeners.add(l);
    }

    public void removeListener(IndexChangedListener l) {
        mIndexChangedListeners.remove(l);
    }


    public interface IndexChangedListener {
        boolean indexChanged(String index);
    }


}
