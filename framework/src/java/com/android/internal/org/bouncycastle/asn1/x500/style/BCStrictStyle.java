package com.android.internal.org.bouncycastle.asn1.x500.style;

/* loaded from: classes4.dex */
public class BCStrictStyle extends com.android.internal.org.bouncycastle.asn1.x500.style.BCStyle {
    public static final com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle INSTANCE = new com.android.internal.org.bouncycastle.asn1.x500.style.BCStrictStyle();

    @Override // com.android.internal.org.bouncycastle.asn1.x500.style.AbstractX500NameStyle, com.android.internal.org.bouncycastle.asn1.x500.X500NameStyle
    public boolean areEqual(com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name, com.android.internal.org.bouncycastle.asn1.x500.X500Name x500Name2) {
        com.android.internal.org.bouncycastle.asn1.x500.RDN[] rDNs = x500Name.getRDNs();
        com.android.internal.org.bouncycastle.asn1.x500.RDN[] rDNs2 = x500Name2.getRDNs();
        if (rDNs.length != rDNs2.length) {
            return false;
        }
        for (int i = 0; i != rDNs.length; i++) {
            if (!rdnAreEqual(rDNs[i], rDNs2[i])) {
                return false;
            }
        }
        return true;
    }
}
