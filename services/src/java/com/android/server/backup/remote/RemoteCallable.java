package com.android.server.backup.remote;

@java.lang.FunctionalInterface
/* loaded from: classes.dex */
public interface RemoteCallable<T> {
    void call(T t) throws android.os.RemoteException;
}
