package com.android.internal.org.bouncycastle.util;

/* loaded from: classes4.dex */
public interface Selector<T> extends java.lang.Cloneable {
    java.lang.Object clone();

    boolean match(T t);
}
