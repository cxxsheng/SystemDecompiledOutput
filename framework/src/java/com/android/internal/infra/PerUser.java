package com.android.internal.infra;

/* loaded from: classes4.dex */
public abstract class PerUser<T> extends android.util.SparseArray<T> {
    protected abstract T create(int i);

    public T forUser(int i) {
        return get(i);
    }

    @Override // android.util.SparseArray
    public T get(int i) {
        T t = (T) super.get(i);
        if (t != null) {
            return t;
        }
        T t2 = (T) com.android.internal.util.Preconditions.checkNotNull(create(i));
        put(i, t2);
        return t2;
    }
}
