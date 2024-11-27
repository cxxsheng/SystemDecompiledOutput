package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class PKIXNameConstraintValidator {
    com.android.internal.org.bouncycastle.asn1.x509.PKIXNameConstraintValidator validator = new com.android.internal.org.bouncycastle.asn1.x509.PKIXNameConstraintValidator();

    public int hashCode() {
        return this.validator.hashCode();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidator)) {
            return false;
        }
        return this.validator.equals(((com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidator) obj).validator);
    }

    public void checkPermittedDN(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) throws com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException {
        try {
            this.validator.checkPermittedDN(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(aSN1Sequence));
        } catch (com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException e) {
            throw new com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException(e.getMessage(), e);
        }
    }

    public void checkExcludedDN(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) throws com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException {
        try {
            this.validator.checkExcludedDN(com.android.internal.org.bouncycastle.asn1.x500.X500Name.getInstance(aSN1Sequence));
        } catch (com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException e) {
            throw new com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException(e.getMessage(), e);
        }
    }

    public void checkPermitted(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName) throws com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException {
        try {
            this.validator.checkPermitted(generalName);
        } catch (com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException e) {
            throw new com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException(e.getMessage(), e);
        }
    }

    public void checkExcluded(com.android.internal.org.bouncycastle.asn1.x509.GeneralName generalName) throws com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException {
        try {
            this.validator.checkExcluded(generalName);
        } catch (com.android.internal.org.bouncycastle.asn1.x509.NameConstraintValidatorException e) {
            throw new com.android.internal.org.bouncycastle.jce.provider.PKIXNameConstraintValidatorException(e.getMessage(), e);
        }
    }

    public void intersectPermittedSubtree(com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree generalSubtree) {
        this.validator.intersectPermittedSubtree(generalSubtree);
    }

    public void intersectPermittedSubtree(com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree[] generalSubtreeArr) {
        this.validator.intersectPermittedSubtree(generalSubtreeArr);
    }

    public void intersectEmptyPermittedSubtree(int i) {
        this.validator.intersectEmptyPermittedSubtree(i);
    }

    public void addExcludedSubtree(com.android.internal.org.bouncycastle.asn1.x509.GeneralSubtree generalSubtree) {
        this.validator.addExcludedSubtree(generalSubtree);
    }

    public java.lang.String toString() {
        return this.validator.toString();
    }
}
