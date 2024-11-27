package com.android.server.wm;

/* loaded from: classes3.dex */
class Watermark {
    private static final java.lang.String TITLE = "WatermarkSurface";
    private final android.graphics.BLASTBufferQueue mBlastBufferQueue;
    private final int mDeltaX;
    private final int mDeltaY;
    private boolean mDrawNeeded;
    private int mLastDH;
    private int mLastDW;
    private final android.view.Surface mSurface;
    private final android.view.SurfaceControl mSurfaceControl;
    private final java.lang.String mText;
    private final int mTextHeight;
    private final android.graphics.Paint mTextPaint;
    private final int mTextWidth;

    Watermark(com.android.server.wm.DisplayContent displayContent, android.util.DisplayMetrics displayMetrics, java.lang.String[] strArr, android.view.SurfaceControl.Transaction transaction) {
        int i;
        int i2;
        java.lang.StringBuilder sb = new java.lang.StringBuilder(32);
        int length = strArr[0].length() & (-2);
        for (int i3 = 0; i3 < length; i3 += 2) {
            char charAt = strArr[0].charAt(i3);
            char charAt2 = strArr[0].charAt(i3 + 1);
            if (charAt < 'a' || charAt > 'f') {
                i = (charAt < 'A' || charAt > 'F') ? charAt - '0' : (charAt - 'A') + 10;
            } else {
                i = (charAt - 'a') + 10;
            }
            if (charAt2 < 'a' || charAt2 > 'f') {
                i2 = (charAt2 < 'A' || charAt2 > 'F') ? charAt2 - '0' : (charAt2 - 'A') + 10;
            } else {
                i2 = (charAt2 - 'a') + 10;
            }
            sb.append((char) (255 - ((i * 16) + i2)));
        }
        this.mText = sb.toString();
        int propertyInt = com.android.server.wm.WindowManagerService.getPropertyInt(strArr, 1, 1, 20, displayMetrics);
        this.mTextPaint = new android.graphics.Paint(1);
        this.mTextPaint.setTextSize(propertyInt);
        this.mTextPaint.setTypeface(android.graphics.Typeface.create(android.graphics.Typeface.SANS_SERIF, 1));
        android.graphics.Paint.FontMetricsInt fontMetricsInt = this.mTextPaint.getFontMetricsInt();
        this.mTextWidth = (int) this.mTextPaint.measureText(this.mText);
        this.mTextHeight = fontMetricsInt.descent - fontMetricsInt.ascent;
        this.mDeltaX = com.android.server.wm.WindowManagerService.getPropertyInt(strArr, 2, 0, this.mTextWidth * 2, displayMetrics);
        this.mDeltaY = com.android.server.wm.WindowManagerService.getPropertyInt(strArr, 3, 0, this.mTextHeight * 3, displayMetrics);
        int propertyInt2 = com.android.server.wm.WindowManagerService.getPropertyInt(strArr, 4, 0, -1342177280, displayMetrics);
        int propertyInt3 = com.android.server.wm.WindowManagerService.getPropertyInt(strArr, 5, 0, 1627389951, displayMetrics);
        int propertyInt4 = com.android.server.wm.WindowManagerService.getPropertyInt(strArr, 6, 0, 7, displayMetrics);
        int propertyInt5 = com.android.server.wm.WindowManagerService.getPropertyInt(strArr, 8, 0, 0, displayMetrics);
        int propertyInt6 = com.android.server.wm.WindowManagerService.getPropertyInt(strArr, 9, 0, 0, displayMetrics);
        this.mTextPaint.setColor(propertyInt3);
        this.mTextPaint.setShadowLayer(propertyInt4, propertyInt5, propertyInt6, propertyInt2);
        android.view.SurfaceControl surfaceControl = null;
        try {
            surfaceControl = displayContent.makeOverlay().setName(TITLE).setBLASTLayer().setFormat(-3).setCallsite(TITLE).build();
            transaction.setLayer(surfaceControl, 1000000).setPosition(surfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE).show(surfaceControl);
            com.android.server.wm.InputMonitor.setTrustedOverlayInputInfo(surfaceControl, transaction, displayContent.getDisplayId(), TITLE);
        } catch (android.view.Surface.OutOfResourcesException e) {
        }
        this.mSurfaceControl = surfaceControl;
        this.mBlastBufferQueue = new android.graphics.BLASTBufferQueue(TITLE, this.mSurfaceControl, 1, 1, 1);
        this.mSurface = this.mBlastBufferQueue.createSurface();
    }

    void positionSurface(int i, int i2, android.view.SurfaceControl.Transaction transaction) {
        if (this.mLastDW != i || this.mLastDH != i2) {
            this.mLastDW = i;
            this.mLastDH = i2;
            transaction.setBufferSize(this.mSurfaceControl, i, i2);
            this.mDrawNeeded = true;
        }
    }

    void drawIfNeeded() {
        if (!this.mDrawNeeded) {
            return;
        }
        int i = this.mLastDW;
        int i2 = this.mLastDH;
        this.mDrawNeeded = false;
        this.mBlastBufferQueue.update(this.mSurfaceControl, i, i2, 1);
        android.graphics.Canvas canvas = null;
        try {
            canvas = this.mSurface.lockCanvas(null);
        } catch (android.view.Surface.OutOfResourcesException | java.lang.IllegalArgumentException e) {
        }
        if (canvas != null) {
            canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
            int i3 = this.mDeltaX;
            int i4 = this.mDeltaY;
            int i5 = (this.mTextWidth + i) - (((this.mTextWidth + i) / i3) * i3);
            int i6 = i3 / 4;
            if (i5 < i6 || i5 > i3 - i6) {
                i3 += i3 / 3;
            }
            int i7 = -this.mTextHeight;
            int i8 = -this.mTextWidth;
            while (i7 < this.mTextHeight + i2) {
                canvas.drawText(this.mText, i8, i7, this.mTextPaint);
                i8 += i3;
                if (i8 >= i) {
                    i8 -= this.mTextWidth + i;
                    i7 += i4;
                }
            }
            this.mSurface.unlockCanvasAndPost(canvas);
        }
    }
}
