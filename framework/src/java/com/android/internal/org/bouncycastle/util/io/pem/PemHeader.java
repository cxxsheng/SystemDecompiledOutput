package com.android.internal.org.bouncycastle.util.io.pem;

/* loaded from: classes4.dex */
public class PemHeader {
    private java.lang.String name;
    private java.lang.String value;

    public PemHeader(java.lang.String str, java.lang.String str2) {
        this.name = str;
        this.value = str2;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public java.lang.String getValue() {
        return this.value;
    }

    public int hashCode() {
        return getHashCode(this.name) + (getHashCode(this.value) * 31);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.util.io.pem.PemHeader)) {
            return false;
        }
        com.android.internal.org.bouncycastle.util.io.pem.PemHeader pemHeader = (com.android.internal.org.bouncycastle.util.io.pem.PemHeader) obj;
        return pemHeader == this || (isEqual(this.name, pemHeader.name) && isEqual(this.value, pemHeader.value));
    }

    private int getHashCode(java.lang.String str) {
        if (str == null) {
            return 1;
        }
        return str.hashCode();
    }

    private boolean isEqual(java.lang.String str, java.lang.String str2) {
        if (str == str2) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return str.equals(str2);
    }
}
