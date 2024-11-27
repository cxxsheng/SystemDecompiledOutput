package android.content.om;

/* loaded from: classes.dex */
public class FabricatedOverlay {
    final android.os.FabricatedOverlayInternal mOverlay;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StringTypeOverlayResource {
    }

    public android.content.om.OverlayIdentifier getIdentifier() {
        return new android.content.om.OverlayIdentifier(this.mOverlay.packageName, android.text.TextUtils.nullIfEmpty(this.mOverlay.overlayName));
    }

    public static final class Builder {
        private final java.lang.String mName;
        private final java.lang.String mOwningPackage;
        private final java.lang.String mTargetPackage;
        private java.lang.String mTargetOverlayable = "";
        private final java.util.ArrayList<android.os.FabricatedOverlayInternalEntry> mEntries = new java.util.ArrayList<>();

        public Builder(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            com.android.internal.util.Preconditions.checkStringNotEmpty(str, "'owningPackage' must not be empty nor null");
            com.android.internal.util.Preconditions.checkStringNotEmpty(str2, "'name'' must not be empty nor null");
            com.android.internal.util.Preconditions.checkStringNotEmpty(str3, "'targetPackage' must not be empty nor null");
            this.mOwningPackage = str;
            this.mName = str2;
            this.mTargetPackage = str3;
        }

        public android.content.om.FabricatedOverlay.Builder setTargetOverlayable(java.lang.String str) {
            this.mTargetOverlayable = android.text.TextUtils.emptyIfNull(str);
            return this;
        }

        @java.lang.Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public android.content.om.FabricatedOverlay.Builder setResourceValue(java.lang.String str, int i, int i2) {
            return setResourceValue(str, i, i2, (java.lang.String) null);
        }

        @java.lang.Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public android.content.om.FabricatedOverlay.Builder setResourceValue(java.lang.String str, int i, int i2, java.lang.String str2) {
            android.content.om.FabricatedOverlay.ensureValidResourceName(str);
            this.mEntries.add(android.content.om.FabricatedOverlay.generateFabricatedOverlayInternalEntry(str, i, i2, str2));
            return this;
        }

        @java.lang.Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public android.content.om.FabricatedOverlay.Builder setResourceValue(java.lang.String str, int i, java.lang.String str2) {
            return setResourceValue(str, i, str2, (java.lang.String) null);
        }

        @java.lang.Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public android.content.om.FabricatedOverlay.Builder setResourceValue(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) {
            android.content.om.FabricatedOverlay.ensureValidResourceName(str);
            this.mEntries.add(android.content.om.FabricatedOverlay.generateFabricatedOverlayInternalEntry(str, i, str2, str3));
            return this;
        }

        @java.lang.Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public android.content.om.FabricatedOverlay.Builder setResourceValue(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String str2) {
            android.content.om.FabricatedOverlay.ensureValidResourceName(str);
            this.mEntries.add(android.content.om.FabricatedOverlay.generateFabricatedOverlayInternalEntry(str, parcelFileDescriptor, str2, false));
            return this;
        }

        @java.lang.Deprecated(since = "Please use FabricatedOverlay#setResourceValue instead")
        public android.content.om.FabricatedOverlay.Builder setResourceValue(java.lang.String str, android.content.res.AssetFileDescriptor assetFileDescriptor, java.lang.String str2) {
            android.content.om.FabricatedOverlay.ensureValidResourceName(str);
            this.mEntries.add(android.content.om.FabricatedOverlay.generateFabricatedOverlayInternalEntry(str, assetFileDescriptor, str2));
            return this;
        }

        public android.content.om.FabricatedOverlay build() {
            return new android.content.om.FabricatedOverlay(android.content.om.FabricatedOverlay.generateFabricatedOverlayInternal(this.mOwningPackage, this.mName, this.mTargetPackage, this.mTargetOverlayable, this.mEntries));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.FabricatedOverlayInternal generateFabricatedOverlayInternal(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.util.ArrayList<android.os.FabricatedOverlayInternalEntry> arrayList) {
        android.os.FabricatedOverlayInternal fabricatedOverlayInternal = new android.os.FabricatedOverlayInternal();
        fabricatedOverlayInternal.packageName = str;
        fabricatedOverlayInternal.overlayName = str2;
        fabricatedOverlayInternal.targetPackageName = str3;
        fabricatedOverlayInternal.targetOverlayable = android.text.TextUtils.emptyIfNull(str4);
        fabricatedOverlayInternal.entries = new java.util.ArrayList();
        fabricatedOverlayInternal.entries.addAll(arrayList);
        return fabricatedOverlayInternal;
    }

    private FabricatedOverlay(android.os.FabricatedOverlayInternal fabricatedOverlayInternal) {
        this.mOverlay = fabricatedOverlayInternal;
    }

    public FabricatedOverlay(java.lang.String str, java.lang.String str2) {
        this(generateFabricatedOverlayInternal("", com.android.internal.content.om.OverlayManagerImpl.checkOverlayNameValid(str), (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str2, "'targetPackage' must not be empty nor null"), null, new java.util.ArrayList()));
    }

    public void setOwningPackage(java.lang.String str) {
        this.mOverlay.packageName = str;
    }

    public void setTargetOverlayable(java.lang.String str) {
        this.mOverlay.targetOverlayable = android.text.TextUtils.emptyIfNull(str);
    }

    public java.lang.String getTargetOverlayable() {
        return this.mOverlay.targetOverlayable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String ensureValidResourceName(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        int indexOf = str.indexOf(47);
        int indexOf2 = str.indexOf(58);
        com.android.internal.util.Preconditions.checkArgument(indexOf >= 0 && indexOf2 != 0 && indexOf - indexOf2 > 2, "\"%s\" is invalid resource name", str);
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(java.lang.String str, int i, int i2, java.lang.String str2) {
        android.os.FabricatedOverlayInternalEntry fabricatedOverlayInternalEntry = new android.os.FabricatedOverlayInternalEntry();
        fabricatedOverlayInternalEntry.resourceName = str;
        fabricatedOverlayInternalEntry.dataType = com.android.internal.util.Preconditions.checkArgumentInRange(i, 16, 31, "dataType");
        fabricatedOverlayInternalEntry.data = i2;
        fabricatedOverlayInternalEntry.configuration = str2;
        return fabricatedOverlayInternalEntry;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) {
        android.os.FabricatedOverlayInternalEntry fabricatedOverlayInternalEntry = new android.os.FabricatedOverlayInternalEntry();
        fabricatedOverlayInternalEntry.resourceName = str;
        fabricatedOverlayInternalEntry.dataType = com.android.internal.util.Preconditions.checkArgumentInRange(i, 3, 6, "dataType");
        fabricatedOverlayInternalEntry.stringData = (java.lang.String) java.util.Objects.requireNonNull(str2);
        fabricatedOverlayInternalEntry.configuration = str3;
        return fabricatedOverlayInternalEntry;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String str2, boolean z) {
        android.os.FabricatedOverlayInternalEntry fabricatedOverlayInternalEntry = new android.os.FabricatedOverlayInternalEntry();
        fabricatedOverlayInternalEntry.resourceName = str;
        fabricatedOverlayInternalEntry.binaryData = (android.os.ParcelFileDescriptor) java.util.Objects.requireNonNull(parcelFileDescriptor);
        fabricatedOverlayInternalEntry.configuration = str2;
        fabricatedOverlayInternalEntry.binaryDataOffset = 0L;
        fabricatedOverlayInternalEntry.binaryDataSize = parcelFileDescriptor.getStatSize();
        fabricatedOverlayInternalEntry.isNinePatch = z;
        return fabricatedOverlayInternalEntry;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.FabricatedOverlayInternalEntry generateFabricatedOverlayInternalEntry(java.lang.String str, android.content.res.AssetFileDescriptor assetFileDescriptor, java.lang.String str2) {
        android.os.FabricatedOverlayInternalEntry fabricatedOverlayInternalEntry = new android.os.FabricatedOverlayInternalEntry();
        fabricatedOverlayInternalEntry.resourceName = str;
        fabricatedOverlayInternalEntry.binaryData = (android.os.ParcelFileDescriptor) java.util.Objects.requireNonNull(assetFileDescriptor.getParcelFileDescriptor());
        fabricatedOverlayInternalEntry.binaryDataOffset = assetFileDescriptor.getStartOffset();
        fabricatedOverlayInternalEntry.binaryDataSize = assetFileDescriptor.getLength();
        fabricatedOverlayInternalEntry.configuration = str2;
        return fabricatedOverlayInternalEntry;
    }

    public void setResourceValue(java.lang.String str, int i, int i2, java.lang.String str2) {
        ensureValidResourceName(str);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(str, i, i2, str2));
    }

    public void setResourceValue(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) {
        ensureValidResourceName(str);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(str, i, str2, str3));
    }

    public void setResourceValue(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String str2) {
        ensureValidResourceName(str);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(str, parcelFileDescriptor, str2, false));
    }

    public void setNinePatchResourceValue(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String str2) {
        ensureValidResourceName(str);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(str, parcelFileDescriptor, str2, true));
    }

    public void setResourceValue(java.lang.String str, android.content.res.AssetFileDescriptor assetFileDescriptor, java.lang.String str2) {
        ensureValidResourceName(str);
        this.mOverlay.entries.add(generateFabricatedOverlayInternalEntry(str, assetFileDescriptor, str2));
    }
}
