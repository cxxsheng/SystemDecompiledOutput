package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
public class OIDTokenizer {
    private int index = 0;
    private java.lang.String oid;

    public OIDTokenizer(java.lang.String str) {
        this.oid = str;
    }

    public boolean hasMoreTokens() {
        return this.index != -1;
    }

    public java.lang.String nextToken() {
        if (this.index == -1) {
            return null;
        }
        int indexOf = this.oid.indexOf(46, this.index);
        if (indexOf == -1) {
            java.lang.String substring = this.oid.substring(this.index);
            this.index = -1;
            return substring;
        }
        java.lang.String substring2 = this.oid.substring(this.index, indexOf);
        this.index = indexOf + 1;
        return substring2;
    }
}
