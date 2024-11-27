package com.android.internal.widget.remotecompose.player;

/* loaded from: classes5.dex */
public class RemoteComposeDocument {
    com.android.internal.widget.remotecompose.core.CoreDocument mDocument = new com.android.internal.widget.remotecompose.core.CoreDocument();

    public RemoteComposeDocument(java.io.InputStream inputStream) {
        this.mDocument.initFromBuffer(com.android.internal.widget.remotecompose.core.RemoteComposeBuffer.fromInputStream(inputStream, this.mDocument.getRemoteComposeState()));
    }

    public com.android.internal.widget.remotecompose.core.CoreDocument getDocument() {
        return this.mDocument;
    }

    public void setDocument(com.android.internal.widget.remotecompose.core.CoreDocument coreDocument) {
        this.mDocument = coreDocument;
    }

    public void initializeContext(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext) {
        this.mDocument.initializeContext(remoteContext);
    }

    public int getWidth() {
        return this.mDocument.getWidth();
    }

    public int getHeight() {
        return this.mDocument.getHeight();
    }

    public void paint(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext, int i) {
        this.mDocument.paint(remoteContext, i);
    }

    public boolean canBeDisplayed(int i, int i2, long j) {
        return this.mDocument.canBeDisplayed(i, i2, j);
    }
}
