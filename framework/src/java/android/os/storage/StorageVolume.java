package android.os.storage;

/* loaded from: classes3.dex */
public final class StorageVolume implements android.os.Parcelable {
    private static final java.lang.String ACTION_OPEN_EXTERNAL_DIRECTORY = "android.os.storage.action.OPEN_EXTERNAL_DIRECTORY";
    public static final android.os.Parcelable.Creator<android.os.storage.StorageVolume> CREATOR = new android.os.Parcelable.Creator<android.os.storage.StorageVolume>() { // from class: android.os.storage.StorageVolume.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.storage.StorageVolume createFromParcel(android.os.Parcel parcel) {
            return new android.os.storage.StorageVolume(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.storage.StorageVolume[] newArray(int i) {
            return new android.os.storage.StorageVolume[i];
        }
    };
    public static final java.lang.String EXTRA_DIRECTORY_NAME = "android.os.storage.extra.DIRECTORY_NAME";
    public static final java.lang.String EXTRA_STORAGE_VOLUME = "android.os.storage.extra.STORAGE_VOLUME";
    public static final int STORAGE_ID_INVALID = 0;
    public static final int STORAGE_ID_PRIMARY = 65537;
    private final boolean mAllowMassStorage;
    private final java.lang.String mDescription;
    private final boolean mEmulated;
    private final boolean mExternallyManaged;
    private final java.lang.String mFsUuid;
    private final java.lang.String mId;
    private final java.io.File mInternalPath;
    private final long mMaxFileSize;
    private final android.os.UserHandle mOwner;
    private final java.io.File mPath;
    private final boolean mPrimary;
    private final boolean mRemovable;
    private final java.lang.String mState;
    private final java.util.UUID mUuid;

    public StorageVolume(java.lang.String str, java.io.File file, java.io.File file2, java.lang.String str2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, long j, android.os.UserHandle userHandle, java.util.UUID uuid, java.lang.String str3, java.lang.String str4) {
        this.mId = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str);
        this.mPath = (java.io.File) com.android.internal.util.Preconditions.checkNotNull(file);
        this.mInternalPath = (java.io.File) com.android.internal.util.Preconditions.checkNotNull(file2);
        this.mDescription = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str2);
        this.mPrimary = z;
        this.mRemovable = z2;
        this.mEmulated = z3;
        this.mExternallyManaged = z4;
        this.mAllowMassStorage = z5;
        this.mMaxFileSize = j;
        this.mOwner = (android.os.UserHandle) com.android.internal.util.Preconditions.checkNotNull(userHandle);
        this.mUuid = uuid;
        this.mFsUuid = str3;
        this.mState = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str4);
    }

    private StorageVolume(android.os.Parcel parcel) {
        this.mId = parcel.readString8();
        this.mPath = new java.io.File(parcel.readString8());
        this.mInternalPath = new java.io.File(parcel.readString8());
        this.mDescription = parcel.readString8();
        this.mPrimary = parcel.readInt() != 0;
        this.mRemovable = parcel.readInt() != 0;
        this.mEmulated = parcel.readInt() != 0;
        this.mExternallyManaged = parcel.readInt() != 0;
        this.mAllowMassStorage = parcel.readInt() != 0;
        this.mMaxFileSize = parcel.readLong();
        this.mOwner = (android.os.UserHandle) parcel.readParcelable(null, android.os.UserHandle.class);
        if (parcel.readInt() != 0) {
            this.mUuid = android.os.storage.StorageManager.convert(parcel.readString8());
        } else {
            this.mUuid = null;
        }
        this.mFsUuid = parcel.readString8();
        this.mState = parcel.readString8();
    }

    @android.annotation.SystemApi
    public java.lang.String getId() {
        return this.mId;
    }

    public java.lang.String getPath() {
        return this.mPath.toString();
    }

    public java.lang.String getInternalPath() {
        return this.mInternalPath.toString();
    }

    public java.io.File getPathFile() {
        return this.mPath;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public java.io.File getDirectory() {
        char c;
        java.lang.String str = this.mState;
        switch (str.hashCode()) {
            case 1242932856:
                if (str.equals(android.os.Environment.MEDIA_MOUNTED)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1299749220:
                if (str.equals(android.os.Environment.MEDIA_MOUNTED_READ_ONLY)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                return this.mPath;
            default:
                return null;
        }
    }

    public java.lang.String getDescription(android.content.Context context) {
        return this.mDescription;
    }

    public boolean isPrimary() {
        return this.mPrimary;
    }

    public boolean isRemovable() {
        return this.mRemovable;
    }

    public boolean isEmulated() {
        return this.mEmulated;
    }

    @android.annotation.SystemApi
    public boolean isExternallyManaged() {
        return this.mExternallyManaged;
    }

    public boolean allowMassStorage() {
        return this.mAllowMassStorage;
    }

    public long getMaxFileSize() {
        return this.mMaxFileSize;
    }

    public android.os.UserHandle getOwner() {
        return this.mOwner;
    }

    public java.util.UUID getStorageUuid() {
        return this.mUuid;
    }

    public java.lang.String getUuid() {
        return this.mFsUuid;
    }

    public java.lang.String getMediaStoreVolumeName() {
        if (isPrimary()) {
            return "external_primary";
        }
        return getNormalizedUuid();
    }

    public static java.lang.String normalizeUuid(java.lang.String str) {
        if (str != null) {
            return str.toLowerCase(java.util.Locale.US);
        }
        return null;
    }

    public java.lang.String getNormalizedUuid() {
        return normalizeUuid(this.mFsUuid);
    }

    public int getFatVolumeId() {
        if (this.mFsUuid == null || this.mFsUuid.length() != 9) {
            return -1;
        }
        try {
            return (int) java.lang.Long.parseLong(this.mFsUuid.replace(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE, ""), 16);
        } catch (java.lang.NumberFormatException e) {
            return -1;
        }
    }

    public java.lang.String getUserLabel() {
        return this.mDescription;
    }

    public java.lang.String getState() {
        return this.mState;
    }

    @java.lang.Deprecated
    public android.content.Intent createAccessIntent(java.lang.String str) {
        if (!isPrimary() || str != null) {
            if (str != null && !android.os.Environment.isStandardDirectory(str)) {
                return null;
            }
            android.content.Intent intent = new android.content.Intent(ACTION_OPEN_EXTERNAL_DIRECTORY);
            intent.putExtra(EXTRA_STORAGE_VOLUME, this);
            intent.putExtra(EXTRA_DIRECTORY_NAME, str);
            return intent;
        }
        return null;
    }

    public android.content.Intent createOpenDocumentTreeIntent() {
        java.lang.String str;
        if (isEmulated()) {
            str = "primary";
        } else {
            str = this.mFsUuid;
        }
        return new android.content.Intent(android.content.Intent.ACTION_OPEN_DOCUMENT_TREE).putExtra(android.provider.DocumentsContract.EXTRA_INITIAL_URI, android.provider.DocumentsContract.buildRootUri(android.provider.DocumentsContract.EXTERNAL_STORAGE_PROVIDER_AUTHORITY, str)).putExtra(android.provider.DocumentsContract.EXTRA_SHOW_ADVANCED, true);
    }

    public boolean equals(java.lang.Object obj) {
        if ((obj instanceof android.os.storage.StorageVolume) && this.mPath != null) {
            return this.mPath.equals(((android.os.storage.StorageVolume) obj).mPath);
        }
        return false;
    }

    public int hashCode() {
        return this.mPath.hashCode();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder("StorageVolume: ").append(this.mDescription);
        if (this.mFsUuid != null) {
            append.append(" (").append(this.mFsUuid).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return append.toString();
    }

    public java.lang.String dump() {
        java.io.CharArrayWriter charArrayWriter = new java.io.CharArrayWriter();
        dump(new com.android.internal.util.IndentingPrintWriter(charArrayWriter, "    ", 80));
        return charArrayWriter.toString();
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("StorageVolume:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("mId", this.mId);
        indentingPrintWriter.printPair("mPath", this.mPath);
        indentingPrintWriter.printPair("mInternalPath", this.mInternalPath);
        indentingPrintWriter.printPair("mDescription", this.mDescription);
        indentingPrintWriter.printPair("mPrimary", java.lang.Boolean.valueOf(this.mPrimary));
        indentingPrintWriter.printPair("mRemovable", java.lang.Boolean.valueOf(this.mRemovable));
        indentingPrintWriter.printPair("mEmulated", java.lang.Boolean.valueOf(this.mEmulated));
        indentingPrintWriter.printPair("mExternallyManaged", java.lang.Boolean.valueOf(this.mExternallyManaged));
        indentingPrintWriter.printPair("mAllowMassStorage", java.lang.Boolean.valueOf(this.mAllowMassStorage));
        indentingPrintWriter.printPair("mMaxFileSize", java.lang.Long.valueOf(this.mMaxFileSize));
        indentingPrintWriter.printPair("mOwner", this.mOwner);
        indentingPrintWriter.printPair("mFsUuid", this.mFsUuid);
        indentingPrintWriter.printPair("mState", this.mState);
        indentingPrintWriter.decreaseIndent();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mId);
        parcel.writeString8(this.mPath.toString());
        parcel.writeString8(this.mInternalPath.toString());
        parcel.writeString8(this.mDescription);
        parcel.writeInt(this.mPrimary ? 1 : 0);
        parcel.writeInt(this.mRemovable ? 1 : 0);
        parcel.writeInt(this.mEmulated ? 1 : 0);
        parcel.writeInt(this.mExternallyManaged ? 1 : 0);
        parcel.writeInt(this.mAllowMassStorage ? 1 : 0);
        parcel.writeLong(this.mMaxFileSize);
        parcel.writeParcelable(this.mOwner, i);
        if (this.mUuid != null) {
            parcel.writeInt(1);
            parcel.writeString8(android.os.storage.StorageManager.convert(this.mUuid));
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString8(this.mFsUuid);
        parcel.writeString8(this.mState);
    }

    public static final class Builder {
        private java.lang.String mDescription;
        private boolean mEmulated;
        private java.lang.String mId;
        private android.os.UserHandle mOwner;
        private java.io.File mPath;
        private boolean mPrimary;
        private boolean mRemovable;
        private java.lang.String mState;
        private java.util.UUID mStorageUuid;
        private java.lang.String mUuid;

        public Builder(java.lang.String str, java.io.File file, java.lang.String str2, android.os.UserHandle userHandle, java.lang.String str3) {
            this.mId = str;
            this.mPath = file;
            this.mDescription = str2;
            this.mOwner = userHandle;
            this.mState = str3;
        }

        public android.os.storage.StorageVolume.Builder setStorageUuid(java.util.UUID uuid) {
            this.mStorageUuid = uuid;
            return this;
        }

        public android.os.storage.StorageVolume.Builder setUuid(java.lang.String str) {
            this.mUuid = str;
            return this;
        }

        public android.os.storage.StorageVolume.Builder setPrimary(boolean z) {
            this.mPrimary = z;
            return this;
        }

        public android.os.storage.StorageVolume.Builder setRemovable(boolean z) {
            this.mRemovable = z;
            return this;
        }

        public android.os.storage.StorageVolume.Builder setEmulated(boolean z) {
            this.mEmulated = z;
            return this;
        }

        public android.os.storage.StorageVolume build() {
            return new android.os.storage.StorageVolume(this.mId, this.mPath, this.mPath, this.mDescription, this.mPrimary, this.mRemovable, this.mEmulated, false, false, 0L, this.mOwner, this.mStorageUuid, this.mUuid, this.mState);
        }
    }
}
