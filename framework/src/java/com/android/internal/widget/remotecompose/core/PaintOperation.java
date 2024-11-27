package com.android.internal.widget.remotecompose.core;

/* loaded from: classes5.dex */
public abstract class PaintOperation implements com.android.internal.widget.remotecompose.core.Operation {
    public abstract void paint(com.android.internal.widget.remotecompose.core.PaintContext paintContext);

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public void apply(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext) {
        if (remoteContext.getMode() == com.android.internal.widget.remotecompose.core.RemoteContext.ContextMode.PAINT && remoteContext.getPaintContext() != null) {
            paint(remoteContext.getPaintContext());
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.Operation
    public java.lang.String deepToString(java.lang.String str) {
        return str + toString();
    }
}
