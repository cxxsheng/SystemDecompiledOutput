package android.util.apk;

/* loaded from: classes3.dex */
public final class SourceStampVerificationResult {
    private final java.security.cert.Certificate mCertificate;
    private final java.util.List<? extends java.security.cert.Certificate> mCertificateLineage;
    private final boolean mPresent;
    private final boolean mVerified;

    private SourceStampVerificationResult(boolean z, boolean z2, java.security.cert.Certificate certificate, java.util.List<? extends java.security.cert.Certificate> list) {
        this.mPresent = z;
        this.mVerified = z2;
        this.mCertificate = certificate;
        this.mCertificateLineage = list;
    }

    public boolean isPresent() {
        return this.mPresent;
    }

    public boolean isVerified() {
        return this.mVerified;
    }

    public java.security.cert.Certificate getCertificate() {
        return this.mCertificate;
    }

    public java.util.List<? extends java.security.cert.Certificate> getCertificateLineage() {
        return this.mCertificateLineage;
    }

    public static android.util.apk.SourceStampVerificationResult notPresent() {
        return new android.util.apk.SourceStampVerificationResult(false, false, null, java.util.Collections.emptyList());
    }

    public static android.util.apk.SourceStampVerificationResult verified(java.security.cert.Certificate certificate, java.util.List<? extends java.security.cert.Certificate> list) {
        return new android.util.apk.SourceStampVerificationResult(true, true, certificate, list);
    }

    public static android.util.apk.SourceStampVerificationResult notVerified() {
        return new android.util.apk.SourceStampVerificationResult(true, false, null, java.util.Collections.emptyList());
    }
}
