package com.android.internal.org.bouncycastle.util;

/* loaded from: classes4.dex */
public class CollectionStore<T> implements com.android.internal.org.bouncycastle.util.Store<T>, com.android.internal.org.bouncycastle.util.Iterable<T> {
    private java.util.Collection<T> _local;

    public CollectionStore(java.util.Collection<T> collection) {
        this._local = new java.util.ArrayList(collection);
    }

    @Override // com.android.internal.org.bouncycastle.util.Store
    public java.util.Collection<T> getMatches(com.android.internal.org.bouncycastle.util.Selector<T> selector) {
        if (selector == null) {
            return new java.util.ArrayList(this._local);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (T t : this._local) {
            if (selector.match(t)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    @Override // com.android.internal.org.bouncycastle.util.Iterable, java.lang.Iterable
    public java.util.Iterator<T> iterator() {
        return getMatches(null).iterator();
    }
}
