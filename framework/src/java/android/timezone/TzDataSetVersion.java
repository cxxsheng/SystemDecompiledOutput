package android.timezone;

/* loaded from: classes3.dex */
public final class TzDataSetVersion {
    private final com.android.i18n.timezone.TzDataSetVersion mDelegate;

    public static int currentFormatMajorVersion() {
        return com.android.i18n.timezone.TzDataSetVersion.currentFormatMajorVersion();
    }

    public static int currentFormatMinorVersion() {
        return com.android.i18n.timezone.TzDataSetVersion.currentFormatMinorVersion();
    }

    public static boolean isCompatibleWithThisDevice(android.timezone.TzDataSetVersion tzDataSetVersion) {
        return com.android.i18n.timezone.TzDataSetVersion.isCompatibleWithThisDevice(tzDataSetVersion.mDelegate);
    }

    public static android.timezone.TzDataSetVersion read() throws java.io.IOException, android.timezone.TzDataSetVersion.TzDataSetException {
        try {
            return new android.timezone.TzDataSetVersion(com.android.i18n.timezone.TimeZoneDataFiles.readTimeZoneModuleVersion());
        } catch (com.android.i18n.timezone.TzDataSetVersion.TzDataSetException e) {
            throw new android.timezone.TzDataSetVersion.TzDataSetException(e.getMessage(), e);
        }
    }

    public static final class TzDataSetException extends java.lang.Exception {
        public TzDataSetException(java.lang.String str) {
            super(str);
        }

        public TzDataSetException(java.lang.String str, java.lang.Throwable th) {
            super(str, th);
        }
    }

    private TzDataSetVersion(com.android.i18n.timezone.TzDataSetVersion tzDataSetVersion) {
        this.mDelegate = (com.android.i18n.timezone.TzDataSetVersion) java.util.Objects.requireNonNull(tzDataSetVersion);
    }

    public int getFormatMajorVersion() {
        return this.mDelegate.getFormatMajorVersion();
    }

    public int getFormatMinorVersion() {
        return this.mDelegate.getFormatMinorVersion();
    }

    public java.lang.String getRulesVersion() {
        return this.mDelegate.getRulesVersion();
    }

    public int getRevision() {
        return this.mDelegate.getRevision();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mDelegate.equals(((android.timezone.TzDataSetVersion) obj).mDelegate);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mDelegate);
    }

    public java.lang.String toString() {
        return this.mDelegate.toString();
    }
}
