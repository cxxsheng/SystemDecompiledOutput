package com.android.server.wm;

/* loaded from: classes3.dex */
class StrictModeFlash {
    private static final java.lang.String TAG = "WindowManager";
    private static final java.lang.String TITLE = "StrictModeFlash";
    private final android.graphics.BLASTBufferQueue mBlastBufferQueue;
    private boolean mDrawNeeded;
    private int mLastDH;
    private int mLastDW;
    private final android.view.Surface mSurface;
    private final android.view.SurfaceControl mSurfaceControl;
    private final int mThickness = 20;

    StrictModeFlash(com.android.server.wm.DisplayContent displayContent, android.view.SurfaceControl.Transaction transaction) {
        android.view.SurfaceControl surfaceControl = null;
        try {
            surfaceControl = displayContent.makeOverlay().setName(TITLE).setBLASTLayer().setFormat(-3).setCallsite(TITLE).build();
            transaction.setLayer(surfaceControl, 1010000);
            transaction.setPosition(surfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
            transaction.show(surfaceControl);
            com.android.server.wm.InputMonitor.setTrustedOverlayInputInfo(surfaceControl, transaction, displayContent.getDisplayId(), TITLE);
        } catch (android.view.Surface.OutOfResourcesException e) {
        }
        this.mSurfaceControl = surfaceControl;
        this.mDrawNeeded = true;
        this.mBlastBufferQueue = new android.graphics.BLASTBufferQueue(TITLE, this.mSurfaceControl, 1, 1, 1);
        this.mSurface = this.mBlastBufferQueue.createSurface();
    }

    private void drawIfNeeded() {
        if (!this.mDrawNeeded) {
            return;
        }
        this.mDrawNeeded = false;
        int i = this.mLastDW;
        int i2 = this.mLastDH;
        this.mBlastBufferQueue.update(this.mSurfaceControl, i, i2, 1);
        android.graphics.Canvas canvas = null;
        try {
            canvas = this.mSurface.lockCanvas(null);
        } catch (android.view.Surface.OutOfResourcesException | java.lang.IllegalArgumentException e) {
        }
        if (canvas == null) {
            return;
        }
        canvas.save();
        canvas.clipRect(new android.graphics.Rect(0, 0, i, 20));
        canvas.drawColor(-65536);
        canvas.restore();
        canvas.save();
        canvas.clipRect(new android.graphics.Rect(0, 0, 20, i2));
        canvas.drawColor(-65536);
        canvas.restore();
        canvas.save();
        canvas.clipRect(new android.graphics.Rect(i - 20, 0, i, i2));
        canvas.drawColor(-65536);
        canvas.restore();
        canvas.save();
        canvas.clipRect(new android.graphics.Rect(0, i2 - 20, i, i2));
        canvas.drawColor(-65536);
        canvas.restore();
        this.mSurface.unlockCanvasAndPost(canvas);
    }

    public void setVisibility(boolean z, android.view.SurfaceControl.Transaction transaction) {
        if (this.mSurfaceControl == null) {
            return;
        }
        drawIfNeeded();
        if (z) {
            transaction.show(this.mSurfaceControl);
        } else {
            transaction.hide(this.mSurfaceControl);
        }
    }

    void positionSurface(int i, int i2, android.view.SurfaceControl.Transaction transaction) {
        if (this.mLastDW == i && this.mLastDH == i2) {
            return;
        }
        this.mLastDW = i;
        this.mLastDH = i2;
        transaction.setBufferSize(this.mSurfaceControl, i, i2);
        this.mDrawNeeded = true;
    }
}
