package com.android.internal.widget.remotecompose.core;

/* loaded from: classes5.dex */
public interface CompanionOperation {
    int id();

    java.lang.String name();

    void read(com.android.internal.widget.remotecompose.core.WireBuffer wireBuffer, java.util.List<com.android.internal.widget.remotecompose.core.Operation> list);
}
