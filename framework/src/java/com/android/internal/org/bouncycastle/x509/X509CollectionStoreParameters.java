package com.android.internal.org.bouncycastle.x509;

/* loaded from: classes4.dex */
public class X509CollectionStoreParameters implements com.android.internal.org.bouncycastle.x509.X509StoreParameters {
    private java.util.Collection collection;

    public X509CollectionStoreParameters(java.util.Collection collection) {
        if (collection == null) {
            throw new java.lang.NullPointerException("collection cannot be null");
        }
        this.collection = collection;
    }

    public java.lang.Object clone() {
        return new com.android.internal.org.bouncycastle.x509.X509CollectionStoreParameters(this.collection);
    }

    public java.util.Collection getCollection() {
        return new java.util.ArrayList(this.collection);
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("X509CollectionStoreParameters: [\n");
        stringBuffer.append("  collection: " + this.collection + "\n");
        stringBuffer.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return stringBuffer.toString();
    }
}
