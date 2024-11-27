package com.android.internal.widget.remotecompose.core;

/* loaded from: classes5.dex */
public interface Operation {
    void apply(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext);

    java.lang.String deepToString(java.lang.String str);

    void write(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer);
}
