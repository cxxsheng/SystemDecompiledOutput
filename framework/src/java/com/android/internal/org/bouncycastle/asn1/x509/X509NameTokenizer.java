package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public class X509NameTokenizer {
    private java.lang.StringBuffer buf;
    private int index;
    private char separator;
    private java.lang.String value;

    public X509NameTokenizer(java.lang.String str) {
        this(str, ',');
    }

    public X509NameTokenizer(java.lang.String str, char c) {
        this.buf = new java.lang.StringBuffer();
        this.value = str;
        this.index = -1;
        this.separator = c;
    }

    public boolean hasMoreTokens() {
        return this.index != this.value.length();
    }

    public java.lang.String nextToken() {
        if (this.index == this.value.length()) {
            return null;
        }
        int i = this.index + 1;
        this.buf.setLength(0);
        boolean z = false;
        boolean z2 = false;
        while (i != this.value.length()) {
            char charAt = this.value.charAt(i);
            if (charAt == '\"') {
                if (!z) {
                    z2 = !z2;
                }
                this.buf.append(charAt);
                z = false;
            } else if (z || z2) {
                this.buf.append(charAt);
                z = false;
            } else if (charAt == '\\') {
                this.buf.append(charAt);
                z = true;
            } else {
                if (charAt == this.separator) {
                    break;
                }
                if (charAt == '#' && this.buf.charAt(this.buf.length() - 1) == '=') {
                    this.buf.append('\\');
                } else if (charAt == '+' && this.separator != '+') {
                    this.buf.append('\\');
                }
                this.buf.append(charAt);
            }
            i++;
        }
        this.index = i;
        return this.buf.toString();
    }
}
