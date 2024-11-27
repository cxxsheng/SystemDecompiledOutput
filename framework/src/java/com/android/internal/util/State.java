package com.android.internal.util;

/* loaded from: classes5.dex */
public class State implements com.android.internal.util.IState {
    protected State() {
    }

    @Override // com.android.internal.util.IState
    public void enter() {
    }

    @Override // com.android.internal.util.IState
    public void exit() {
    }

    @Override // com.android.internal.util.IState
    public boolean processMessage(android.os.Message message) {
        return false;
    }

    @Override // com.android.internal.util.IState
    public java.lang.String getName() {
        java.lang.String name = getClass().getName();
        return name.substring(name.lastIndexOf(36) + 1);
    }
}
