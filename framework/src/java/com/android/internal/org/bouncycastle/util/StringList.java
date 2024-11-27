package com.android.internal.org.bouncycastle.util;

/* loaded from: classes4.dex */
public interface StringList extends com.android.internal.org.bouncycastle.util.Iterable<java.lang.String> {
    boolean add(java.lang.String str);

    java.lang.String get(int i);

    int size();

    java.lang.String[] toStringArray();

    java.lang.String[] toStringArray(int i, int i2);
}
