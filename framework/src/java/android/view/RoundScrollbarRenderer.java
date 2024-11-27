package android.view;

/* loaded from: classes4.dex */
class RoundScrollbarRenderer {
    private static final int DEFAULT_THUMB_COLOR = -1;
    private static final int DEFAULT_TRACK_COLOR = 1291845631;
    private static final float MAX_SCROLLBAR_ANGLE_SWIPE = 26.3f;
    private static final float MIN_SCROLLBAR_ANGLE_SWIPE = 3.1f;
    private static final float OUTER_PADDING_DP = 2.0f;
    private static final float RESIZING_RATE = 0.8f;
    private static final int RESIZING_THRESHOLD_PX = 20;
    private static final float SCROLLBAR_ANGLE_RANGE = 28.8f;
    private static final float THUMB_WIDTH_DP = 4.0f;
    private final int mMaskThickness;
    private final android.view.View mParent;
    private final android.graphics.Paint mThumbPaint = new android.graphics.Paint();
    private final android.graphics.Paint mTrackPaint = new android.graphics.Paint();
    private final android.graphics.RectF mRect = new android.graphics.RectF();
    private float mPreviousMaxScroll = 0.0f;
    private float mMaxScrollDiff = 0.0f;
    private float mPreviousCurrentScroll = 0.0f;
    private float mCurrentScrollDiff = 0.0f;

    public RoundScrollbarRenderer(android.view.View view) {
        this.mThumbPaint.setAntiAlias(true);
        this.mThumbPaint.setStrokeCap(android.graphics.Paint.Cap.ROUND);
        this.mThumbPaint.setStyle(android.graphics.Paint.Style.STROKE);
        this.mTrackPaint.setAntiAlias(true);
        this.mTrackPaint.setStrokeCap(android.graphics.Paint.Cap.ROUND);
        this.mTrackPaint.setStyle(android.graphics.Paint.Style.STROKE);
        this.mParent = view;
        this.mMaskThickness = view.getContext().getResources().getDimensionPixelSize(com.android.internal.R.dimen.circular_display_mask_thickness);
    }

    public void drawRoundScrollbars(android.graphics.Canvas canvas, float f, android.graphics.Rect rect, boolean z) {
        if (f == 0.0f) {
            return;
        }
        float computeVerticalScrollRange = this.mParent.computeVerticalScrollRange();
        float computeVerticalScrollExtent = this.mParent.computeVerticalScrollExtent();
        float computeVerticalScrollOffset = this.mParent.computeVerticalScrollOffset();
        if (computeVerticalScrollExtent <= 0.0f) {
            if (!this.mParent.canScrollVertically(1) && !this.mParent.canScrollVertically(-1)) {
                return;
            } else {
                computeVerticalScrollExtent = 0.0f;
            }
        } else if (computeVerticalScrollRange <= computeVerticalScrollExtent) {
            return;
        }
        if (java.lang.Math.abs(computeVerticalScrollRange - this.mPreviousMaxScroll) > 20.0f && this.mPreviousMaxScroll != 0.0f) {
            this.mMaxScrollDiff += computeVerticalScrollRange - this.mPreviousMaxScroll;
            this.mCurrentScrollDiff += computeVerticalScrollOffset - this.mPreviousCurrentScroll;
        }
        this.mPreviousMaxScroll = computeVerticalScrollRange;
        this.mPreviousCurrentScroll = computeVerticalScrollOffset;
        if (java.lang.Math.abs(this.mMaxScrollDiff) > 20.0f || java.lang.Math.abs(this.mCurrentScrollDiff) > 20.0f) {
            this.mMaxScrollDiff *= 0.8f;
            this.mCurrentScrollDiff *= 0.8f;
            computeVerticalScrollRange -= this.mMaxScrollDiff;
            computeVerticalScrollOffset -= this.mCurrentScrollDiff;
        } else {
            this.mMaxScrollDiff = 0.0f;
            this.mCurrentScrollDiff = 0.0f;
        }
        float max = java.lang.Math.max(0.0f, computeVerticalScrollOffset);
        float dpToPx = dpToPx(THUMB_WIDTH_DP);
        this.mThumbPaint.setStrokeWidth(dpToPx);
        this.mTrackPaint.setStrokeWidth(dpToPx);
        setThumbColor(applyAlpha(-1, f));
        setTrackColor(applyAlpha(DEFAULT_TRACK_COLOR, f));
        float clamp = clamp((computeVerticalScrollExtent / computeVerticalScrollRange) * SCROLLBAR_ANGLE_RANGE, MIN_SCROLLBAR_ANGLE_SWIPE, MAX_SCROLLBAR_ANGLE_SWIPE);
        float clamp2 = clamp(((max * (SCROLLBAR_ANGLE_RANGE - clamp)) / (computeVerticalScrollRange - computeVerticalScrollExtent)) - 14.4f, -14.4f, 14.4f - clamp);
        float f2 = (dpToPx / OUTER_PADDING_DP) + this.mMaskThickness;
        this.mRect.set(rect.left + f2, rect.top + f2, rect.right - f2, rect.bottom - f2);
        if (z) {
            canvas.drawArc(this.mRect, 194.4f, -28.8f, false, this.mTrackPaint);
            canvas.drawArc(this.mRect, 180.0f - clamp2, -clamp, false, this.mThumbPaint);
        } else {
            canvas.drawArc(this.mRect, -14.4f, SCROLLBAR_ANGLE_RANGE, false, this.mTrackPaint);
            canvas.drawArc(this.mRect, clamp2, clamp, false, this.mThumbPaint);
        }
    }

    void getRoundVerticalScrollBarBounds(android.graphics.Rect rect) {
        float dpToPx = dpToPx(OUTER_PADDING_DP);
        int i = this.mParent.mRight - this.mParent.mLeft;
        int i2 = this.mParent.mBottom - this.mParent.mTop;
        int i3 = (int) dpToPx;
        rect.left = this.mParent.mScrollX + i3;
        rect.top = this.mParent.mScrollY + i3;
        rect.right = (this.mParent.mScrollX + i) - i3;
        rect.bottom = (this.mParent.mScrollY + i2) - i3;
    }

    private static float clamp(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        if (f > f3) {
            return f3;
        }
        return f;
    }

    private static int applyAlpha(int i, float f) {
        return android.graphics.Color.argb((int) (android.graphics.Color.alpha(i) * f), android.graphics.Color.red(i), android.graphics.Color.green(i), android.graphics.Color.blue(i));
    }

    private void setThumbColor(int i) {
        if (this.mThumbPaint.getColor() != i) {
            this.mThumbPaint.setColor(i);
        }
    }

    private void setTrackColor(int i) {
        if (this.mTrackPaint.getColor() != i) {
            this.mTrackPaint.setColor(i);
        }
    }

    private float dpToPx(float f) {
        return (f * this.mParent.getContext().getResources().getDisplayMetrics().densityDpi) / 160.0f;
    }
}
