package com.android.server.input.debug;

/* loaded from: classes2.dex */
public class RotaryInputGraphView extends android.view.View {
    private static final float DEFAULT_FRAME_CENTER_POSITION = 0.0f;
    private static final int FRAME_BORDER_GAP_SP = 10;
    private static final int FRAME_COLOR = -1082909881;
    private static final int FRAME_TEXT_OFFSET_SP = 2;
    private static final int FRAME_TEXT_SIZE_SP = 10;
    private static final int FRAME_WIDTH_SP = 2;
    private static final int GRAPH_COLOR = -65281;
    private static final int GRAPH_LINE_WIDTH_SP = 1;
    private static final int GRAPH_POINT_RADIUS_SP = 4;
    private static final int MAX_GRAPH_VALUES_SIZE = 400;
    private final java.util.Locale mDefaultLocale;
    private final android.util.DisplayMetrics mDm;
    private float mFrameCenterPosition;
    private final float mFrameCenterToBorderDistance;
    private final android.graphics.Paint mFramePaint;
    private final android.graphics.Paint mFrameTextPaint;
    private final android.graphics.Paint mGraphLinePaint;
    private final android.graphics.Paint mGraphPointPaint;
    private final com.android.server.input.debug.RotaryInputGraphView.CyclicBuffer mGraphValues;
    private final float mScaledVerticalScrollFactor;
    private static final long MAX_SHOWN_TIME_INTERVAL = java.util.concurrent.TimeUnit.SECONDS.toMillis(5);
    private static final long MAX_GESTURE_TIME = java.util.concurrent.TimeUnit.SECONDS.toMillis(1);

    public RotaryInputGraphView(android.content.Context context) {
        super(context);
        this.mDefaultLocale = java.util.Locale.getDefault();
        this.mFramePaint = new android.graphics.Paint();
        this.mFrameTextPaint = new android.graphics.Paint();
        this.mGraphLinePaint = new android.graphics.Paint();
        this.mGraphPointPaint = new android.graphics.Paint();
        this.mGraphValues = new com.android.server.input.debug.RotaryInputGraphView.CyclicBuffer(400);
        this.mFrameCenterPosition = 0.0f;
        this.mDm = ((android.view.View) this).mContext.getResources().getDisplayMetrics();
        this.mFrameCenterToBorderDistance = this.mDm.heightPixels;
        this.mScaledVerticalScrollFactor = android.view.ViewConfiguration.get(context).getScaledVerticalScrollFactor();
        this.mFramePaint.setColor(FRAME_COLOR);
        this.mFramePaint.setStrokeWidth(applyDimensionSp(2, this.mDm));
        this.mFrameTextPaint.setColor(GRAPH_COLOR);
        this.mFrameTextPaint.setTextSize(applyDimensionSp(10, this.mDm));
        this.mGraphLinePaint.setColor(GRAPH_COLOR);
        this.mGraphLinePaint.setStrokeWidth(applyDimensionSp(1, this.mDm));
        this.mGraphLinePaint.setStrokeCap(android.graphics.Paint.Cap.ROUND);
        this.mGraphLinePaint.setStrokeJoin(android.graphics.Paint.Join.ROUND);
        this.mGraphPointPaint.setColor(GRAPH_COLOR);
        this.mGraphPointPaint.setStrokeWidth(applyDimensionSp(4, this.mDm));
        this.mGraphPointPaint.setStrokeCap(android.graphics.Paint.Cap.ROUND);
        this.mGraphPointPaint.setStrokeJoin(android.graphics.Paint.Join.ROUND);
    }

    public void addValue(float f, long j) {
        while (this.mGraphValues.getSize() > 0 && j - this.mGraphValues.getFirst().mTime > MAX_SHOWN_TIME_INTERVAL) {
            this.mGraphValues.removeFirst();
        }
        if (this.mGraphValues.getSize() == 0) {
            this.mFrameCenterPosition = 0.0f;
        }
        float f2 = (this.mGraphValues.getSize() == 0 ? 0.0f : this.mGraphValues.getLast().mPos) + (f * this.mScaledVerticalScrollFactor);
        this.mGraphValues.add(f2, j);
        float abs = java.lang.Math.abs(f2 - this.mFrameCenterPosition) - this.mFrameCenterToBorderDistance;
        if (abs > 0.0f) {
            this.mFrameCenterPosition += (f2 - this.mFrameCenterPosition < 0.0f ? -1 : 1) * abs;
        }
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        float f;
        long j;
        float f2;
        super.onDraw(canvas);
        int applyDimensionSp = applyDimensionSp(10, this.mDm);
        int height = getHeight() - applyDimensionSp;
        int i = (applyDimensionSp + height) / 2;
        float f3 = applyDimensionSp;
        float width = getWidth();
        canvas.drawLine(0.0f, f3, width, f3, this.mFramePaint);
        float f4 = i;
        canvas.drawLine(0.0f, f4, width, f4, this.mFramePaint);
        float f5 = height;
        canvas.drawLine(0.0f, f5, width, f5, this.mFramePaint);
        int applyDimensionSp2 = applyDimensionSp(2, this.mDm);
        canvas.drawText(java.lang.String.format(this.mDefaultLocale, "%.1f", java.lang.Float.valueOf(this.mFrameCenterPosition + this.mFrameCenterToBorderDistance)), 0.0f, applyDimensionSp - applyDimensionSp2, this.mFrameTextPaint);
        canvas.drawText(java.lang.String.format(this.mDefaultLocale, "%.1f", java.lang.Float.valueOf(this.mFrameCenterPosition)), 0.0f, i - applyDimensionSp2, this.mFrameTextPaint);
        canvas.drawText(java.lang.String.format(this.mDefaultLocale, "%.1f", java.lang.Float.valueOf(this.mFrameCenterPosition - this.mFrameCenterToBorderDistance)), 0.0f, height - applyDimensionSp2, this.mFrameTextPaint);
        if (this.mGraphValues.getSize() == 0) {
            return;
        }
        long j2 = this.mGraphValues.getLast().mTime;
        java.util.Iterator<com.android.server.input.debug.RotaryInputGraphView.GraphValue> reverseIterator = this.mGraphValues.reverseIterator();
        float f6 = 0.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        while (reverseIterator.hasNext()) {
            com.android.server.input.debug.RotaryInputGraphView.GraphValue next = reverseIterator.next();
            int i2 = i;
            int i3 = (int) (j2 - next.mTime);
            long j3 = j2;
            float f9 = f7;
            float f10 = f8;
            float f11 = (((MAX_SHOWN_TIME_INTERVAL - i3) / MAX_SHOWN_TIME_INTERVAL) * (r12 + 0)) + 0.0f;
            float f12 = f4 - (((next.mPos - this.mFrameCenterPosition) / this.mFrameCenterToBorderDistance) * (i2 - applyDimensionSp));
            canvas.drawPoint(f11, f12, this.mGraphPointPaint);
            if (i3 == 0) {
                f = f12;
            } else if (i3 - f6 > MAX_GESTURE_TIME) {
                f = f12;
            } else {
                f = f12;
                j = j3;
                f2 = f11;
                canvas.drawLine(f9, f10, f11, f, this.mGraphLinePaint);
                f6 = i3;
                f8 = f;
                i = i2;
                j2 = j;
                f7 = f2;
            }
            j = j3;
            f2 = f11;
            f6 = i3;
            f8 = f;
            i = i2;
            j2 = j;
            f7 = f2;
        }
    }

    public float getFrameCenterPosition() {
        return this.mFrameCenterPosition;
    }

    private static int applyDimensionSp(int i, android.util.DisplayMetrics displayMetrics) {
        return (int) android.util.TypedValue.applyDimension(2, i, displayMetrics);
    }

    private static class GraphValue {
        float mPos;
        long mTime;

        GraphValue(float f, long j) {
            this.mPos = f;
            this.mTime = j;
        }
    }

    private static class CyclicBuffer {
        private final int mCapacity;
        private int mIteratorCount;
        private int mIteratorIndex;
        private final com.android.server.input.debug.RotaryInputGraphView.GraphValue[] mValues;
        private int mSize = 0;
        private int mLastIndex = 0;
        private final java.util.Iterator<com.android.server.input.debug.RotaryInputGraphView.GraphValue> mReverseIterator = new java.util.Iterator<com.android.server.input.debug.RotaryInputGraphView.GraphValue>() { // from class: com.android.server.input.debug.RotaryInputGraphView.CyclicBuffer.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return com.android.server.input.debug.RotaryInputGraphView.CyclicBuffer.this.mIteratorCount <= com.android.server.input.debug.RotaryInputGraphView.CyclicBuffer.this.mSize;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public com.android.server.input.debug.RotaryInputGraphView.GraphValue next() {
                com.android.server.input.debug.RotaryInputGraphView.CyclicBuffer.this.mIteratorCount++;
                com.android.server.input.debug.RotaryInputGraphView.GraphValue[] graphValueArr = com.android.server.input.debug.RotaryInputGraphView.CyclicBuffer.this.mValues;
                com.android.server.input.debug.RotaryInputGraphView.CyclicBuffer cyclicBuffer = com.android.server.input.debug.RotaryInputGraphView.CyclicBuffer.this;
                int i = cyclicBuffer.mIteratorIndex;
                cyclicBuffer.mIteratorIndex = i - 1;
                return graphValueArr[(i + com.android.server.input.debug.RotaryInputGraphView.CyclicBuffer.this.mCapacity) % com.android.server.input.debug.RotaryInputGraphView.CyclicBuffer.this.mCapacity];
            }
        };

        CyclicBuffer(int i) {
            this.mCapacity = i;
            this.mValues = new com.android.server.input.debug.RotaryInputGraphView.GraphValue[i];
        }

        void add(float f, long j) {
            this.mLastIndex = (this.mLastIndex + 1) % this.mCapacity;
            if (this.mValues[this.mLastIndex] == null) {
                this.mValues[this.mLastIndex] = new com.android.server.input.debug.RotaryInputGraphView.GraphValue(f, j);
            } else {
                com.android.server.input.debug.RotaryInputGraphView.GraphValue graphValue = this.mValues[this.mLastIndex];
                graphValue.mPos = f;
                graphValue.mTime = j;
            }
            if (this.mSize != this.mCapacity) {
                this.mSize++;
            }
        }

        int getSize() {
            return this.mSize;
        }

        com.android.server.input.debug.RotaryInputGraphView.GraphValue getFirst() {
            return this.mValues[(this.mLastIndex + ((this.mCapacity - this.mSize) + 1)) % this.mCapacity];
        }

        com.android.server.input.debug.RotaryInputGraphView.GraphValue getLast() {
            return this.mValues[this.mLastIndex];
        }

        void removeFirst() {
            this.mSize--;
        }

        java.util.Iterator<com.android.server.input.debug.RotaryInputGraphView.GraphValue> reverseIterator() {
            this.mIteratorIndex = this.mLastIndex;
            this.mIteratorCount = 1;
            return this.mReverseIterator;
        }
    }
}
