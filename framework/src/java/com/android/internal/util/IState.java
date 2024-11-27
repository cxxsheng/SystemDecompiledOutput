package com.android.internal.util;

/* loaded from: classes5.dex */
public interface IState {
    public static final boolean HANDLED = true;
    public static final boolean NOT_HANDLED = false;

    void enter();

    void exit();

    java.lang.String getName();

    boolean processMessage(android.os.Message message);
}
