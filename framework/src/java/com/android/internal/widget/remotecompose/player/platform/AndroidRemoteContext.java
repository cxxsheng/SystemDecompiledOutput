package com.android.internal.widget.remotecompose.player.platform;

/* loaded from: classes5.dex */
class AndroidRemoteContext extends com.android.internal.widget.remotecompose.core.RemoteContext {
    AndroidRemoteContext() {
    }

    public void useCanvas(android.graphics.Canvas canvas) {
        if (this.mPaintContext == null) {
            this.mPaintContext = new com.android.internal.widget.remotecompose.player.platform.AndroidPaintContext(this, canvas);
        } else {
            ((com.android.internal.widget.remotecompose.player.platform.AndroidPaintContext) this.mPaintContext).setCanvas(canvas);
        }
        this.mWidth = canvas.getWidth();
        this.mHeight = canvas.getHeight();
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadBitmap(int i, int i2, int i3, byte[] bArr) {
        if (!this.mRemoteComposeState.containsId(i)) {
            this.mRemoteComposeState.cache(i, android.graphics.BitmapFactory.decodeByteArray(bArr, 0, bArr.length));
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void loadText(int i, java.lang.String str) {
        if (!this.mRemoteComposeState.containsId(i)) {
            this.mRemoteComposeState.cache(i, str);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.RemoteContext
    public void addClickArea(int i, int i2, float f, float f2, float f3, float f4, int i3) {
        this.mDocument.addClickArea(i, (java.lang.String) this.mRemoteComposeState.getFromId(i2), f, f2, f3, f4, (java.lang.String) this.mRemoteComposeState.getFromId(i3));
    }
}
