package com.android.internal.org.bouncycastle.asn1.misc;

/* loaded from: classes4.dex */
public class NetscapeRevocationURL extends com.android.internal.org.bouncycastle.asn1.DERIA5String {
    public NetscapeRevocationURL(com.android.internal.org.bouncycastle.asn1.DERIA5String dERIA5String) {
        super(dERIA5String.getString());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.DERIA5String
    public java.lang.String toString() {
        return "NetscapeRevocationURL: " + getString();
    }
}
