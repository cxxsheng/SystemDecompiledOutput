package com.android.internal.org.bouncycastle.asn1.misc;

/* loaded from: classes4.dex */
public class VerisignCzagExtension extends com.android.internal.org.bouncycastle.asn1.DERIA5String {
    public VerisignCzagExtension(com.android.internal.org.bouncycastle.asn1.DERIA5String dERIA5String) {
        super(dERIA5String.getString());
    }

    @Override // com.android.internal.org.bouncycastle.asn1.DERIA5String
    public java.lang.String toString() {
        return "VerisignCzagExtension: " + getString();
    }
}
