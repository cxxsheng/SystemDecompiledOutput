package com.android.internal.widget.remotecompose.core;

/* loaded from: classes5.dex */
public abstract class RemoteContext {
    protected com.android.internal.widget.remotecompose.core.CoreDocument mDocument;
    public com.android.internal.widget.remotecompose.core.RemoteComposeState mRemoteComposeState;
    protected com.android.internal.widget.remotecompose.core.PaintContext mPaintContext = null;
    com.android.internal.widget.remotecompose.core.RemoteContext.ContextMode mMode = com.android.internal.widget.remotecompose.core.RemoteContext.ContextMode.UNSET;
    boolean mDebug = false;
    private int mTheme = -1;
    public float mWidth = 0.0f;
    public float mHeight = 0.0f;

    public enum ContextMode {
        UNSET,
        DATA,
        PAINT
    }

    public abstract void addClickArea(int i, int i2, float f, float f2, float f3, float f4, int i3);

    public abstract void loadBitmap(int i, int i2, int i3, byte[] bArr);

    public abstract void loadText(int i, java.lang.String str);

    public int getTheme() {
        return this.mTheme;
    }

    public void setTheme(int i) {
        this.mTheme = i;
    }

    public com.android.internal.widget.remotecompose.core.RemoteContext.ContextMode getMode() {
        return this.mMode;
    }

    public void setMode(com.android.internal.widget.remotecompose.core.RemoteContext.ContextMode contextMode) {
        this.mMode = contextMode;
    }

    public com.android.internal.widget.remotecompose.core.PaintContext getPaintContext() {
        return this.mPaintContext;
    }

    public void setPaintContext(com.android.internal.widget.remotecompose.core.PaintContext paintContext) {
        this.mPaintContext = paintContext;
    }

    public com.android.internal.widget.remotecompose.core.CoreDocument getDocument() {
        return this.mDocument;
    }

    public boolean isDebug() {
        return this.mDebug;
    }

    public void setDebug(boolean z) {
        this.mDebug = z;
    }

    public void setDocument(com.android.internal.widget.remotecompose.core.CoreDocument coreDocument) {
        this.mDocument = coreDocument;
    }

    public void header(int i, int i2, int i3, int i4, int i5, long j) {
        this.mDocument.setVersion(i, i2, i3);
        this.mDocument.setWidth(i4);
        this.mDocument.setHeight(i5);
        this.mDocument.setRequiredCapabilities(j);
    }

    public void setRootContentBehavior(int i, int i2, int i3, int i4) {
        this.mDocument.setRootContentBehavior(i, i2, i3, i4);
    }

    public void setDocumentContentDescription(int i) {
        this.mDocument.setContentDescription((java.lang.String) this.mRemoteComposeState.getFromId(i));
    }
}
