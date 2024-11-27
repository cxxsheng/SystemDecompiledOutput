package com.android.server.permission.access;

/* compiled from: AccessState.kt */
/* loaded from: classes2.dex */
public final class MutateStateScope extends com.android.server.permission.access.GetStateScope {

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.MutableAccessState newState;

    @org.jetbrains.annotations.NotNull
    private final com.android.server.permission.access.AccessState oldState;

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.MutableAccessState getNewState() {
        return this.newState;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.AccessState getOldState() {
        return this.oldState;
    }

    public MutateStateScope(@org.jetbrains.annotations.NotNull com.android.server.permission.access.AccessState oldState, @org.jetbrains.annotations.NotNull com.android.server.permission.access.MutableAccessState newState) {
        super(newState);
        this.oldState = oldState;
        this.newState = newState;
    }
}
