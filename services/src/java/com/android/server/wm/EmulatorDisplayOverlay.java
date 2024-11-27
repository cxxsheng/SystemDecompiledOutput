package com.android.server.wm;

/* loaded from: classes3.dex */
class EmulatorDisplayOverlay {
    private static final java.lang.String TAG = "WindowManager";
    private static final java.lang.String TITLE = "EmulatorDisplayOverlay";
    private final android.graphics.BLASTBufferQueue mBlastBufferQueue;
    private boolean mDrawNeeded;
    private int mLastDH;
    private int mLastDW;
    private final android.graphics.drawable.Drawable mOverlay;
    private int mRotation;
    private android.graphics.Point mScreenSize;
    private final android.view.Surface mSurface;
    private final android.view.SurfaceControl mSurfaceControl;
    private boolean mVisible;

    EmulatorDisplayOverlay(android.content.Context context, com.android.server.wm.DisplayContent displayContent, int i, android.view.SurfaceControl.Transaction transaction) {
        android.view.Display display = displayContent.getDisplay();
        this.mScreenSize = new android.graphics.Point();
        display.getSize(this.mScreenSize);
        android.view.SurfaceControl surfaceControl = null;
        try {
            surfaceControl = displayContent.makeOverlay().setName(TITLE).setBLASTLayer().setFormat(-3).setCallsite(TITLE).build();
            transaction.setLayer(surfaceControl, i);
            transaction.setPosition(surfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
            transaction.show(surfaceControl);
            com.android.server.wm.InputMonitor.setTrustedOverlayInputInfo(surfaceControl, transaction, displayContent.getDisplayId(), TITLE);
        } catch (android.view.Surface.OutOfResourcesException e) {
        }
        this.mSurfaceControl = surfaceControl;
        this.mDrawNeeded = true;
        this.mOverlay = context.getDrawable(android.R.drawable.emo_im_yelling);
        this.mBlastBufferQueue = new android.graphics.BLASTBufferQueue(TITLE, this.mSurfaceControl, this.mScreenSize.x, this.mScreenSize.y, 1);
        this.mSurface = this.mBlastBufferQueue.createSurface();
    }

    private void drawIfNeeded(android.view.SurfaceControl.Transaction transaction) {
        if (!this.mDrawNeeded || !this.mVisible) {
            return;
        }
        this.mDrawNeeded = false;
        android.graphics.Canvas canvas = null;
        try {
            canvas = this.mSurface.lockCanvas(null);
        } catch (android.view.Surface.OutOfResourcesException | java.lang.IllegalArgumentException e) {
        }
        if (canvas != null) {
            canvas.drawColor(0, android.graphics.PorterDuff.Mode.SRC);
            transaction.setPosition(this.mSurfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
            int max = java.lang.Math.max(this.mScreenSize.x, this.mScreenSize.y);
            this.mOverlay.setBounds(0, 0, max, max);
            this.mOverlay.draw(canvas);
            this.mSurface.unlockCanvasAndPost(canvas);
        }
    }

    public void setVisibility(boolean z, android.view.SurfaceControl.Transaction transaction) {
        if (this.mSurfaceControl == null) {
            return;
        }
        this.mVisible = z;
        drawIfNeeded(transaction);
        if (z) {
            transaction.show(this.mSurfaceControl);
        } else {
            transaction.hide(this.mSurfaceControl);
        }
    }

    void positionSurface(int i, int i2, int i3, android.view.SurfaceControl.Transaction transaction) {
        if (this.mLastDW == i && this.mLastDH == i2 && this.mRotation == i3) {
            return;
        }
        this.mLastDW = i;
        this.mLastDH = i2;
        this.mDrawNeeded = true;
        this.mRotation = i3;
        drawIfNeeded(transaction);
    }
}
