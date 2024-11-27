package android.security.identity;

/* loaded from: classes3.dex */
public class AccessControlProfile {
    private android.security.identity.AccessControlProfileId mAccessControlProfileId;
    private java.security.cert.X509Certificate mReaderCertificate;
    private boolean mUserAuthenticationRequired;
    private long mUserAuthenticationTimeout;

    private AccessControlProfile() {
        this.mAccessControlProfileId = new android.security.identity.AccessControlProfileId(0);
        this.mReaderCertificate = null;
        this.mUserAuthenticationRequired = true;
        this.mUserAuthenticationTimeout = 0L;
    }

    android.security.identity.AccessControlProfileId getAccessControlProfileId() {
        return this.mAccessControlProfileId;
    }

    long getUserAuthenticationTimeout() {
        return this.mUserAuthenticationTimeout;
    }

    boolean isUserAuthenticationRequired() {
        return this.mUserAuthenticationRequired;
    }

    java.security.cert.X509Certificate getReaderCertificate() {
        return this.mReaderCertificate;
    }

    public static final class Builder {
        private android.security.identity.AccessControlProfile mProfile = new android.security.identity.AccessControlProfile();

        public Builder(android.security.identity.AccessControlProfileId accessControlProfileId) {
            this.mProfile.mAccessControlProfileId = accessControlProfileId;
        }

        public android.security.identity.AccessControlProfile.Builder setUserAuthenticationRequired(boolean z) {
            this.mProfile.mUserAuthenticationRequired = z;
            return this;
        }

        public android.security.identity.AccessControlProfile.Builder setUserAuthenticationTimeout(long j) {
            this.mProfile.mUserAuthenticationTimeout = j;
            return this;
        }

        public android.security.identity.AccessControlProfile.Builder setReaderCertificate(java.security.cert.X509Certificate x509Certificate) {
            this.mProfile.mReaderCertificate = x509Certificate;
            return this;
        }

        public android.security.identity.AccessControlProfile build() {
            return this.mProfile;
        }
    }
}
