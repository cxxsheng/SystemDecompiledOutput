package com.android.internal.widget.remotecompose.core;

/* loaded from: classes5.dex */
public abstract class PaintContext {
    protected com.android.internal.widget.remotecompose.core.RemoteContext mContext;

    public abstract void drawBitmap(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10);

    public abstract void scale(float f, float f2);

    public abstract void translate(float f, float f2);

    public PaintContext(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext) {
        this.mContext = remoteContext;
    }

    public void setContext(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext) {
        this.mContext = remoteContext;
    }
}
