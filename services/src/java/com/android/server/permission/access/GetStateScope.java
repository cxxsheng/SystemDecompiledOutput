package com.android.server.permission.access;

/* compiled from: AccessState.kt */
/* loaded from: classes2.dex */
public class GetStateScope {

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.AccessState state;

    public GetStateScope(@org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState state) {
        this.state = state;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.AccessState getState() {
        return this.state;
    }
}
