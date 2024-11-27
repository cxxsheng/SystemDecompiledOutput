package com.android.internal.widget.remotecompose.player.platform;

/* loaded from: classes5.dex */
public class AndroidPaintContext extends com.android.internal.widget.remotecompose.core.PaintContext {
    android.graphics.Canvas mCanvas;
    android.graphics.Paint mPaint;

    public AndroidPaintContext(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext, android.graphics.Canvas canvas) {
        super(remoteContext);
        this.mPaint = new android.graphics.Paint();
        this.mCanvas = canvas;
    }

    public android.graphics.Canvas getCanvas() {
        return this.mCanvas;
    }

    public void setCanvas(android.graphics.Canvas canvas) {
        this.mCanvas = canvas;
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void drawBitmap(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        com.android.internal.widget.remotecompose.player.platform.AndroidRemoteContext androidRemoteContext = (com.android.internal.widget.remotecompose.player.platform.AndroidRemoteContext) this.mContext;
        if (androidRemoteContext.mRemoteComposeState.containsId(i)) {
            this.mCanvas.drawBitmap((android.graphics.Bitmap) androidRemoteContext.mRemoteComposeState.getFromId(i), new android.graphics.Rect(i2, i3, i4, i5), new android.graphics.Rect(i6, i7, i8, i9), this.mPaint);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void scale(float f, float f2) {
        this.mCanvas.scale(f, f2);
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintContext
    public void translate(float f, float f2) {
        this.mCanvas.translate(f, f2);
    }
}
