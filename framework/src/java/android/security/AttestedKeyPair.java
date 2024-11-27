package android.security;

/* loaded from: classes3.dex */
public final class AttestedKeyPair {
    private final java.util.List<java.security.cert.Certificate> mAttestationRecord;
    private final java.security.KeyPair mKeyPair;

    public AttestedKeyPair(java.security.KeyPair keyPair, java.util.List<java.security.cert.Certificate> list) {
        this.mKeyPair = keyPair;
        this.mAttestationRecord = list;
    }

    public AttestedKeyPair(java.security.KeyPair keyPair, java.security.cert.Certificate[] certificateArr) {
        this.mKeyPair = keyPair;
        if (certificateArr == null) {
            this.mAttestationRecord = new java.util.ArrayList();
        } else {
            this.mAttestationRecord = java.util.Arrays.asList(certificateArr);
        }
    }

    public java.security.KeyPair getKeyPair() {
        return this.mKeyPair;
    }

    public java.util.List<java.security.cert.Certificate> getAttestationRecord() {
        return java.util.Collections.unmodifiableList(this.mAttestationRecord);
    }
}
