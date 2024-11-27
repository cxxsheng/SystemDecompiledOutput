package com.android.server.blob;

/* loaded from: classes.dex */
class BlobAccessMode {
    public static final int ACCESS_TYPE_ALLOWLIST = 8;
    public static final int ACCESS_TYPE_PRIVATE = 1;
    public static final int ACCESS_TYPE_PUBLIC = 2;
    public static final int ACCESS_TYPE_SAME_SIGNATURE = 4;
    private int mAccessType = 1;
    private final android.util.ArraySet<com.android.server.blob.BlobAccessMode.PackageIdentifier> mAllowedPackages = new android.util.ArraySet<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface AccessType {
    }

    BlobAccessMode() {
    }

    void allow(com.android.server.blob.BlobAccessMode blobAccessMode) {
        if ((blobAccessMode.mAccessType & 8) != 0) {
            this.mAllowedPackages.addAll((android.util.ArraySet<? extends com.android.server.blob.BlobAccessMode.PackageIdentifier>) blobAccessMode.mAllowedPackages);
        }
        this.mAccessType = blobAccessMode.mAccessType | this.mAccessType;
    }

    void allowPublicAccess() {
        this.mAccessType |= 2;
    }

    void allowSameSignatureAccess() {
        this.mAccessType |= 4;
    }

    void allowPackageAccess(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr) {
        this.mAccessType |= 8;
        this.mAllowedPackages.add(com.android.server.blob.BlobAccessMode.PackageIdentifier.create(str, bArr));
    }

    boolean isPublicAccessAllowed() {
        return (this.mAccessType & 2) != 0;
    }

    boolean isSameSignatureAccessAllowed() {
        return (this.mAccessType & 4) != 0;
    }

    boolean isPackageAccessAllowed(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr) {
        if ((this.mAccessType & 8) == 0) {
            return false;
        }
        return this.mAllowedPackages.contains(com.android.server.blob.BlobAccessMode.PackageIdentifier.create(str, bArr));
    }

    boolean isAccessAllowedForCaller(android.content.Context context, @android.annotation.NonNull java.lang.String str, int i, int i2) {
        if ((this.mAccessType & 2) != 0) {
            return true;
        }
        if ((this.mAccessType & 4) != 0 && checkSignatures(i, i2)) {
            return true;
        }
        if ((this.mAccessType & 8) != 0) {
            android.content.pm.PackageManager packageManager = context.createContextAsUser(android.os.UserHandle.of(android.os.UserHandle.getUserId(i)), 0).getPackageManager();
            for (int i3 = 0; i3 < this.mAllowedPackages.size(); i3++) {
                com.android.server.blob.BlobAccessMode.PackageIdentifier valueAt = this.mAllowedPackages.valueAt(i3);
                if (valueAt.packageName.equals(str) && packageManager.hasSigningCertificate(str, valueAt.certificate, 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkSignatures(int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).checkUidSignaturesForAllUsers(i, i2) == 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    int getAccessType() {
        return this.mAccessType;
    }

    int getAllowedPackagesCount() {
        return this.mAllowedPackages.size();
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("accessType: " + android.util.DebugUtils.flagsToString(com.android.server.blob.BlobAccessMode.class, "ACCESS_TYPE_", this.mAccessType));
        indentingPrintWriter.print("Explicitly allowed pkgs:");
        if (this.mAllowedPackages.isEmpty()) {
            indentingPrintWriter.println(" (Empty)");
            return;
        }
        indentingPrintWriter.increaseIndent();
        int size = this.mAllowedPackages.size();
        for (int i = 0; i < size; i++) {
            indentingPrintWriter.println(this.mAllowedPackages.valueAt(i).toString());
        }
        indentingPrintWriter.decreaseIndent();
    }

    void writeToXml(@android.annotation.NonNull org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        com.android.internal.util.XmlUtils.writeIntAttribute(xmlSerializer, "t", this.mAccessType);
        int size = this.mAllowedPackages.size();
        for (int i = 0; i < size; i++) {
            xmlSerializer.startTag(null, "wl");
            com.android.server.blob.BlobAccessMode.PackageIdentifier valueAt = this.mAllowedPackages.valueAt(i);
            com.android.internal.util.XmlUtils.writeStringAttribute(xmlSerializer, "p", valueAt.packageName);
            com.android.internal.util.XmlUtils.writeByteArrayAttribute(xmlSerializer, "ct", valueAt.certificate);
            xmlSerializer.endTag(null, "wl");
        }
    }

    @android.annotation.NonNull
    static com.android.server.blob.BlobAccessMode createFromXml(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.blob.BlobAccessMode blobAccessMode = new com.android.server.blob.BlobAccessMode();
        blobAccessMode.mAccessType = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "t");
        int depth = xmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            if ("wl".equals(xmlPullParser.getName())) {
                blobAccessMode.allowPackageAccess(com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, "p"), com.android.internal.util.XmlUtils.readByteArrayAttribute(xmlPullParser, "ct"));
            }
        }
        return blobAccessMode;
    }

    private static final class PackageIdentifier {
        public final byte[] certificate;
        public final java.lang.String packageName;

        private PackageIdentifier(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr) {
            this.packageName = str;
            this.certificate = bArr;
        }

        public static com.android.server.blob.BlobAccessMode.PackageIdentifier create(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull byte[] bArr) {
            return new com.android.server.blob.BlobAccessMode.PackageIdentifier(str, bArr);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof com.android.server.blob.BlobAccessMode.PackageIdentifier)) {
                return false;
            }
            com.android.server.blob.BlobAccessMode.PackageIdentifier packageIdentifier = (com.android.server.blob.BlobAccessMode.PackageIdentifier) obj;
            if (this.packageName.equals(packageIdentifier.packageName) && java.util.Arrays.equals(this.certificate, packageIdentifier.certificate)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.packageName, java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.certificate)));
        }

        public java.lang.String toString() {
            return "[" + this.packageName + ", " + android.util.Base64.encodeToString(this.certificate, 2) + "]";
        }
    }
}
