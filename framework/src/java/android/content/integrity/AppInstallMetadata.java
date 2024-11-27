package android.content.integrity;

/* loaded from: classes.dex */
public final class AppInstallMetadata {
    private final java.util.Map<java.lang.String, java.lang.String> mAllowedInstallersAndCertificates;
    private final java.util.List<java.lang.String> mAppCertificateLineage;
    private final java.util.List<java.lang.String> mAppCertificates;
    private final java.util.List<java.lang.String> mInstallerCertificates;
    private final java.lang.String mInstallerName;
    private final boolean mIsPreInstalled;
    private final boolean mIsStampPresent;
    private final boolean mIsStampTrusted;
    private final boolean mIsStampVerified;
    private final java.lang.String mPackageName;
    private final java.lang.String mStampCertificateHash;
    private final long mVersionCode;

    private AppInstallMetadata(android.content.integrity.AppInstallMetadata.Builder builder) {
        this.mPackageName = builder.mPackageName;
        this.mAppCertificates = builder.mAppCertificates;
        this.mAppCertificateLineage = builder.mAppCertificateLineage;
        this.mInstallerName = builder.mInstallerName;
        this.mInstallerCertificates = builder.mInstallerCertificates;
        this.mVersionCode = builder.mVersionCode;
        this.mIsPreInstalled = builder.mIsPreInstalled;
        this.mIsStampPresent = builder.mIsStampPresent;
        this.mIsStampVerified = builder.mIsStampVerified;
        this.mIsStampTrusted = builder.mIsStampTrusted;
        this.mStampCertificateHash = builder.mStampCertificateHash;
        this.mAllowedInstallersAndCertificates = builder.mAllowedInstallersAndCertificates;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.util.List<java.lang.String> getAppCertificates() {
        return this.mAppCertificates;
    }

    public java.util.List<java.lang.String> getAppCertificateLineage() {
        return this.mAppCertificateLineage;
    }

    public java.lang.String getInstallerName() {
        return this.mInstallerName;
    }

    public java.util.List<java.lang.String> getInstallerCertificates() {
        return this.mInstallerCertificates;
    }

    public long getVersionCode() {
        return this.mVersionCode;
    }

    public boolean isPreInstalled() {
        return this.mIsPreInstalled;
    }

    public boolean isStampPresent() {
        return this.mIsStampPresent;
    }

    public boolean isStampVerified() {
        return this.mIsStampVerified;
    }

    public boolean isStampTrusted() {
        return this.mIsStampTrusted;
    }

    public java.lang.String getStampCertificateHash() {
        return this.mStampCertificateHash;
    }

    public java.util.Map<java.lang.String, java.lang.String> getAllowedInstallersAndCertificates() {
        return this.mAllowedInstallersAndCertificates;
    }

    public java.lang.String toString() {
        return java.lang.String.format("AppInstallMetadata { PackageName = %s, AppCerts = %s, AppCertsLineage = %s, InstallerName = %s, InstallerCerts = %s, VersionCode = %d, PreInstalled = %b, StampPresent = %b, StampVerified = %b, StampTrusted = %b, StampCert = %s }", this.mPackageName, this.mAppCertificates, this.mAppCertificateLineage, this.mInstallerName == null ? "null" : this.mInstallerName, this.mInstallerCertificates == null ? "null" : this.mInstallerCertificates, java.lang.Long.valueOf(this.mVersionCode), java.lang.Boolean.valueOf(this.mIsPreInstalled), java.lang.Boolean.valueOf(this.mIsStampPresent), java.lang.Boolean.valueOf(this.mIsStampVerified), java.lang.Boolean.valueOf(this.mIsStampTrusted), this.mStampCertificateHash != null ? this.mStampCertificateHash : "null");
    }

    public static final class Builder {
        private java.util.Map<java.lang.String, java.lang.String> mAllowedInstallersAndCertificates = new java.util.HashMap();
        private java.util.List<java.lang.String> mAppCertificateLineage;
        private java.util.List<java.lang.String> mAppCertificates;
        private java.util.List<java.lang.String> mInstallerCertificates;
        private java.lang.String mInstallerName;
        private boolean mIsPreInstalled;
        private boolean mIsStampPresent;
        private boolean mIsStampTrusted;
        private boolean mIsStampVerified;
        private java.lang.String mPackageName;
        private java.lang.String mStampCertificateHash;
        private long mVersionCode;

        public android.content.integrity.AppInstallMetadata.Builder setAllowedInstallersAndCert(java.util.Map<java.lang.String, java.lang.String> map) {
            this.mAllowedInstallersAndCertificates = map;
            return this;
        }

        public android.content.integrity.AppInstallMetadata.Builder setPackageName(java.lang.String str) {
            this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str);
            return this;
        }

        public android.content.integrity.AppInstallMetadata.Builder setAppCertificates(java.util.List<java.lang.String> list) {
            this.mAppCertificates = (java.util.List) java.util.Objects.requireNonNull(list);
            return this;
        }

        public android.content.integrity.AppInstallMetadata.Builder setAppCertificateLineage(java.util.List<java.lang.String> list) {
            this.mAppCertificateLineage = (java.util.List) java.util.Objects.requireNonNull(list);
            return this;
        }

        public android.content.integrity.AppInstallMetadata.Builder setInstallerName(java.lang.String str) {
            this.mInstallerName = (java.lang.String) java.util.Objects.requireNonNull(str);
            return this;
        }

        public android.content.integrity.AppInstallMetadata.Builder setInstallerCertificates(java.util.List<java.lang.String> list) {
            this.mInstallerCertificates = (java.util.List) java.util.Objects.requireNonNull(list);
            return this;
        }

        public android.content.integrity.AppInstallMetadata.Builder setVersionCode(long j) {
            this.mVersionCode = j;
            return this;
        }

        public android.content.integrity.AppInstallMetadata.Builder setIsPreInstalled(boolean z) {
            this.mIsPreInstalled = z;
            return this;
        }

        public android.content.integrity.AppInstallMetadata.Builder setIsStampPresent(boolean z) {
            this.mIsStampPresent = z;
            return this;
        }

        public android.content.integrity.AppInstallMetadata.Builder setIsStampVerified(boolean z) {
            this.mIsStampVerified = z;
            return this;
        }

        public android.content.integrity.AppInstallMetadata.Builder setIsStampTrusted(boolean z) {
            this.mIsStampTrusted = z;
            return this;
        }

        public android.content.integrity.AppInstallMetadata.Builder setStampCertificateHash(java.lang.String str) {
            this.mStampCertificateHash = (java.lang.String) java.util.Objects.requireNonNull(str);
            return this;
        }

        public android.content.integrity.AppInstallMetadata build() {
            java.util.Objects.requireNonNull(this.mPackageName);
            java.util.Objects.requireNonNull(this.mAppCertificates);
            java.util.Objects.requireNonNull(this.mAppCertificateLineage);
            return new android.content.integrity.AppInstallMetadata(this);
        }
    }
}
