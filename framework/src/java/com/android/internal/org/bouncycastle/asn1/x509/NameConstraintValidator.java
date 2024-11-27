package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes4.dex */
public interface NameConstraintValidator {
    void addExcludedSubtree(com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree generalSubtree);

    void checkExcluded(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException;

    void checkPermitted(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName) throws com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException;

    void intersectEmptyPermittedSubtree(int i);

    void intersectPermittedSubtree(com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree generalSubtree);

    void intersectPermittedSubtree(com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] generalSubtreeArr);
}
