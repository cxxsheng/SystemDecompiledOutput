package android.os.storage;

/* loaded from: classes3.dex */
public class VolumeInfo implements android.os.Parcelable {
    public static final java.lang.String ACTION_VOLUME_STATE_CHANGED = "android.os.storage.action.VOLUME_STATE_CHANGED";
    public static final android.os.Parcelable.Creator<android.os.storage.VolumeInfo> CREATOR;
    private static final java.lang.String DOCUMENT_AUTHORITY = "com.android.externalstorage.documents";
    private static final java.lang.String DOCUMENT_ROOT_PRIMARY_EMULATED = "primary";
    public static final java.lang.String EXTRA_VOLUME_ID = "android.os.storage.extra.VOLUME_ID";
    public static final java.lang.String EXTRA_VOLUME_STATE = "android.os.storage.extra.VOLUME_STATE";
    public static final java.lang.String ID_EMULATED_INTERNAL = "emulated";
    public static final java.lang.String ID_PRIVATE_INTERNAL = "private";
    public static final int MOUNT_FLAG_PRIMARY = 1;
    public static final int MOUNT_FLAG_VISIBLE_FOR_READ = 2;
    public static final int MOUNT_FLAG_VISIBLE_FOR_WRITE = 4;
    public static final int STATE_BAD_REMOVAL = 8;
    public static final int STATE_CHECKING = 1;
    public static final int STATE_EJECTING = 5;
    public static final int STATE_FORMATTING = 4;
    public static final int STATE_MOUNTED = 2;
    public static final int STATE_MOUNTED_READ_ONLY = 3;
    public static final int STATE_REMOVED = 7;
    public static final int STATE_UNMOUNTABLE = 6;
    public static final int STATE_UNMOUNTED = 0;
    public static final int TYPE_ASEC = 3;
    public static final int TYPE_EMULATED = 2;
    public static final int TYPE_OBB = 4;
    public static final int TYPE_PRIVATE = 1;
    public static final int TYPE_PUBLIC = 0;
    public static final int TYPE_STUB = 5;
    public final android.os.storage.DiskInfo disk;
    public java.lang.String fsLabel;
    public java.lang.String fsType;
    public java.lang.String fsUuid;
    public final java.lang.String id;
    public java.lang.String internalPath;
    public int mountFlags;
    public int mountUserId;
    public final java.lang.String partGuid;
    public java.lang.String path;
    public int state;
    public final int type;
    private static android.util.SparseArray<java.lang.String> sStateToEnvironment = new android.util.SparseArray<>();
    private static android.util.ArrayMap<java.lang.String, java.lang.String> sEnvironmentToBroadcast = new android.util.ArrayMap<>();
    private static android.util.SparseIntArray sStateToDescrip = new android.util.SparseIntArray();
    private static final java.util.Comparator<android.os.storage.VolumeInfo> sDescriptionComparator = new java.util.Comparator<android.os.storage.VolumeInfo>() { // from class: android.os.storage.VolumeInfo.1
        @Override // java.util.Comparator
        public int compare(android.os.storage.VolumeInfo volumeInfo, android.os.storage.VolumeInfo volumeInfo2) {
            if (android.os.storage.VolumeInfo.ID_PRIVATE_INTERNAL.equals(volumeInfo.getId())) {
                return -1;
            }
            if (volumeInfo.getDescription() == null) {
                return 1;
            }
            if (volumeInfo2.getDescription() == null) {
                return -1;
            }
            return volumeInfo.getDescription().compareTo(volumeInfo2.getDescription());
        }
    };

    static {
        sStateToEnvironment.put(0, android.os.Environment.MEDIA_UNMOUNTED);
        sStateToEnvironment.put(1, android.os.Environment.MEDIA_CHECKING);
        sStateToEnvironment.put(2, android.os.Environment.MEDIA_MOUNTED);
        sStateToEnvironment.put(3, android.os.Environment.MEDIA_MOUNTED_READ_ONLY);
        sStateToEnvironment.put(4, android.os.Environment.MEDIA_UNMOUNTED);
        sStateToEnvironment.put(5, android.os.Environment.MEDIA_EJECTING);
        sStateToEnvironment.put(6, android.os.Environment.MEDIA_UNMOUNTABLE);
        sStateToEnvironment.put(7, android.os.Environment.MEDIA_REMOVED);
        sStateToEnvironment.put(8, android.os.Environment.MEDIA_BAD_REMOVAL);
        sEnvironmentToBroadcast.put(android.os.Environment.MEDIA_UNMOUNTED, android.content.Intent.ACTION_MEDIA_UNMOUNTED);
        sEnvironmentToBroadcast.put(android.os.Environment.MEDIA_CHECKING, android.content.Intent.ACTION_MEDIA_CHECKING);
        sEnvironmentToBroadcast.put(android.os.Environment.MEDIA_MOUNTED, android.content.Intent.ACTION_MEDIA_MOUNTED);
        sEnvironmentToBroadcast.put(android.os.Environment.MEDIA_MOUNTED_READ_ONLY, android.content.Intent.ACTION_MEDIA_MOUNTED);
        sEnvironmentToBroadcast.put(android.os.Environment.MEDIA_EJECTING, android.content.Intent.ACTION_MEDIA_EJECT);
        sEnvironmentToBroadcast.put(android.os.Environment.MEDIA_UNMOUNTABLE, android.content.Intent.ACTION_MEDIA_UNMOUNTABLE);
        sEnvironmentToBroadcast.put(android.os.Environment.MEDIA_REMOVED, android.content.Intent.ACTION_MEDIA_REMOVED);
        sEnvironmentToBroadcast.put(android.os.Environment.MEDIA_BAD_REMOVAL, android.content.Intent.ACTION_MEDIA_BAD_REMOVAL);
        sStateToDescrip.put(0, com.android.internal.R.string.ext_media_status_unmounted);
        sStateToDescrip.put(1, com.android.internal.R.string.ext_media_status_checking);
        sStateToDescrip.put(2, com.android.internal.R.string.ext_media_status_mounted);
        sStateToDescrip.put(3, com.android.internal.R.string.ext_media_status_mounted_ro);
        sStateToDescrip.put(4, com.android.internal.R.string.ext_media_status_formatting);
        sStateToDescrip.put(5, com.android.internal.R.string.ext_media_status_ejecting);
        sStateToDescrip.put(6, com.android.internal.R.string.ext_media_status_unmountable);
        sStateToDescrip.put(7, com.android.internal.R.string.ext_media_status_removed);
        sStateToDescrip.put(8, com.android.internal.R.string.ext_media_status_bad_removal);
        CREATOR = new android.os.Parcelable.Creator<android.os.storage.VolumeInfo>() { // from class: android.os.storage.VolumeInfo.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.storage.VolumeInfo createFromParcel(android.os.Parcel parcel) {
                return new android.os.storage.VolumeInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.storage.VolumeInfo[] newArray(int i) {
                return new android.os.storage.VolumeInfo[i];
            }
        };
    }

    public VolumeInfo(java.lang.String str, int i, android.os.storage.DiskInfo diskInfo, java.lang.String str2) {
        this.mountFlags = 0;
        this.mountUserId = -10000;
        this.state = 0;
        this.id = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str);
        this.type = i;
        this.disk = diskInfo;
        this.partGuid = str2;
    }

    public VolumeInfo(android.os.Parcel parcel) {
        this.mountFlags = 0;
        this.mountUserId = -10000;
        this.state = 0;
        this.id = parcel.readString8();
        this.type = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.disk = android.os.storage.DiskInfo.CREATOR.createFromParcel(parcel);
        } else {
            this.disk = null;
        }
        this.partGuid = parcel.readString8();
        this.mountFlags = parcel.readInt();
        this.mountUserId = parcel.readInt();
        this.state = parcel.readInt();
        this.fsType = parcel.readString8();
        this.fsUuid = parcel.readString8();
        this.fsLabel = parcel.readString8();
        this.path = parcel.readString8();
        this.internalPath = parcel.readString8();
    }

    public VolumeInfo(android.os.storage.VolumeInfo volumeInfo) {
        this.mountFlags = 0;
        this.mountUserId = -10000;
        this.state = 0;
        this.id = volumeInfo.id;
        this.type = volumeInfo.type;
        this.disk = volumeInfo.disk;
        this.partGuid = volumeInfo.partGuid;
        this.mountFlags = volumeInfo.mountFlags;
        this.mountUserId = volumeInfo.mountUserId;
        this.state = volumeInfo.state;
        this.fsType = volumeInfo.fsType;
        this.fsUuid = volumeInfo.fsUuid;
        this.fsLabel = volumeInfo.fsLabel;
        this.path = volumeInfo.path;
        this.internalPath = volumeInfo.internalPath;
    }

    public static java.lang.String getEnvironmentForState(int i) {
        java.lang.String str = sStateToEnvironment.get(i);
        if (str != null) {
            return str;
        }
        return "unknown";
    }

    public static java.lang.String getBroadcastForEnvironment(java.lang.String str) {
        return sEnvironmentToBroadcast.get(str);
    }

    public static java.lang.String getBroadcastForState(int i) {
        return getBroadcastForEnvironment(getEnvironmentForState(i));
    }

    public static java.util.Comparator<android.os.storage.VolumeInfo> getDescriptionComparator() {
        return sDescriptionComparator;
    }

    public java.lang.String getId() {
        return this.id;
    }

    public android.os.storage.DiskInfo getDisk() {
        return this.disk;
    }

    public java.lang.String getDiskId() {
        if (this.disk != null) {
            return this.disk.id;
        }
        return null;
    }

    public int getType() {
        return this.type;
    }

    public int getState() {
        return this.state;
    }

    public int getStateDescription() {
        return sStateToDescrip.get(this.state, 0);
    }

    public java.lang.String getFsUuid() {
        return this.fsUuid;
    }

    public java.lang.String getNormalizedFsUuid() {
        if (this.fsUuid != null) {
            return this.fsUuid.toLowerCase(java.util.Locale.US);
        }
        return null;
    }

    public int getMountUserId() {
        return this.mountUserId;
    }

    public java.lang.String getDescription() {
        if (ID_PRIVATE_INTERNAL.equals(this.id) || this.id.startsWith("emulated;")) {
            return android.content.res.Resources.getSystem().getString(com.android.internal.R.string.storage_internal);
        }
        if (!android.text.TextUtils.isEmpty(this.fsLabel)) {
            return this.fsLabel;
        }
        return null;
    }

    public boolean isMountedReadable() {
        return this.state == 2 || this.state == 3;
    }

    public boolean isMountedWritable() {
        return this.state == 2;
    }

    public boolean isPrimary() {
        return (this.mountFlags & 1) != 0;
    }

    public boolean isPrimaryPhysical() {
        return isPrimary() && getType() == 0;
    }

    private boolean isVisibleForRead() {
        return (this.mountFlags & 2) != 0;
    }

    private boolean isVisibleForWrite() {
        return (this.mountFlags & 4) != 0;
    }

    public boolean isVisible() {
        return isVisibleForRead() || isVisibleForWrite();
    }

    private boolean isVolumeSupportedForUser(int i) {
        if (this.mountUserId != i) {
            return false;
        }
        return this.type == 0 || this.type == 5 || this.type == 2;
    }

    public boolean isVisibleForUser(int i) {
        return isVolumeSupportedForUser(i) && isVisible();
    }

    public boolean isPrimaryEmulatedForUser(int i) {
        return this.id.equals("emulated;" + i);
    }

    public boolean isVisibleForRead(int i) {
        return isVolumeSupportedForUser(i) && isVisibleForRead();
    }

    public boolean isVisibleForWrite(int i) {
        return isVolumeSupportedForUser(i) && isVisibleForWrite();
    }

    public java.io.File getPath() {
        if (this.path != null) {
            return new java.io.File(this.path);
        }
        return null;
    }

    public java.io.File getInternalPath() {
        if (this.internalPath != null) {
            return new java.io.File(this.internalPath);
        }
        return null;
    }

    public java.io.File getPathForUser(int i) {
        if (this.path == null) {
            return null;
        }
        if (this.type == 0 || this.type == 5) {
            return new java.io.File(this.path);
        }
        if (this.type == 2) {
            return new java.io.File(this.path, java.lang.Integer.toString(i));
        }
        return null;
    }

    public java.io.File getInternalPathForUser(int i) {
        if (this.path == null) {
            return null;
        }
        if (this.type == 0 || this.type == 5) {
            return new java.io.File(this.path.replace("/storage/", "/mnt/media_rw/"));
        }
        return getPathForUser(i);
    }

    public android.os.storage.StorageVolume buildStorageVolume(android.content.Context context, int i, boolean z) {
        java.lang.String environmentForState;
        java.io.File file;
        java.io.File file2;
        java.lang.String str;
        boolean z2;
        java.util.UUID uuid;
        long j;
        boolean z3;
        java.util.UUID uuid2;
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) context.getSystemService(android.os.storage.StorageManager.class);
        boolean z4 = this.type == 5;
        if (!z) {
            environmentForState = getEnvironmentForState(this.state);
        } else {
            environmentForState = android.os.Environment.MEDIA_UNMOUNTED;
        }
        java.lang.String str2 = environmentForState;
        java.io.File pathForUser = getPathForUser(i);
        if (pathForUser != null) {
            file = pathForUser;
        } else {
            file = new java.io.File("/dev/null");
        }
        java.io.File internalPathForUser = getInternalPathForUser(i);
        if (internalPathForUser != null) {
            file2 = internalPathForUser;
        } else {
            file2 = new java.io.File("/dev/null");
        }
        java.lang.String str3 = this.fsUuid;
        java.lang.String str4 = null;
        if (this.type != 2) {
            if (this.type == 0 || this.type == 5) {
                java.lang.String bestVolumeDescription = storageManager.getBestVolumeDescription(this);
                if (!"vfat".equals(this.fsType)) {
                    str = str3;
                    z2 = false;
                    uuid = null;
                    j = 0;
                    str4 = bestVolumeDescription;
                    z3 = true;
                } else {
                    str = str3;
                    z2 = false;
                    z3 = true;
                    j = 4294967295L;
                    uuid = null;
                    str4 = bestVolumeDescription;
                }
            } else {
                throw new java.lang.IllegalStateException("Unexpected volume type " + this.type);
            }
        } else {
            android.os.storage.VolumeInfo findPrivateForEmulated = storageManager.findPrivateForEmulated(this);
            if (findPrivateForEmulated != null) {
                str4 = storageManager.getBestVolumeDescription(findPrivateForEmulated);
                uuid2 = android.os.storage.StorageManager.convert(findPrivateForEmulated.fsUuid);
                str3 = findPrivateForEmulated.fsUuid;
            } else {
                uuid2 = android.os.storage.StorageManager.UUID_DEFAULT;
            }
            uuid = uuid2;
            str = str3;
            z2 = true;
            j = 0;
            z3 = isPrimaryEmulatedForUser(i) ? false : true;
        }
        if (str4 == null) {
            str4 = context.getString(17039374);
        }
        return new android.os.storage.StorageVolume(this.id, file, file2, str4, isPrimary(), z3, z2, z4, false, j, new android.os.UserHandle(i), uuid, str, str2);
    }

    public static int buildStableMtpStorageId(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            i = (i * 31) + str.charAt(i2);
        }
        int i3 = ((i << 16) ^ i) & (-65536);
        if (i3 == 0) {
            i3 = 131072;
        }
        int i4 = i3 != 65536 ? i3 : 131072;
        if (i4 == -65536) {
            i4 = -131072;
        }
        return i4 | 1;
    }

    public android.content.Intent buildBrowseIntent() {
        return buildBrowseIntentForUser(android.os.UserHandle.myUserId());
    }

    public android.content.Intent buildBrowseIntentForUser(int i) {
        android.net.Uri buildRootUri;
        if ((this.type == 0 || this.type == 5) && this.mountUserId == i) {
            buildRootUri = android.provider.DocumentsContract.buildRootUri("com.android.externalstorage.documents", this.fsUuid);
        } else if (this.type == 2 && isPrimary()) {
            buildRootUri = android.provider.DocumentsContract.buildRootUri("com.android.externalstorage.documents", "primary");
        } else {
            return null;
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.VIEW");
        intent.addCategory(android.content.Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(buildRootUri, android.provider.DocumentsContract.Root.MIME_TYPE_ITEM);
        intent.putExtra(android.provider.DocumentsContract.EXTRA_SHOW_ADVANCED, isPrimary());
        return intent;
    }

    public java.lang.String toString() {
        java.io.CharArrayWriter charArrayWriter = new java.io.CharArrayWriter();
        dump(new com.android.internal.util.IndentingPrintWriter(charArrayWriter, "    ", 80));
        return charArrayWriter.toString();
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("VolumeInfo{" + this.id + "}:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("type", android.util.DebugUtils.valueToString(getClass(), "TYPE_", this.type));
        indentingPrintWriter.printPair("diskId", getDiskId());
        indentingPrintWriter.printPair("partGuid", this.partGuid);
        indentingPrintWriter.printPair("mountFlags", android.util.DebugUtils.flagsToString(getClass(), "MOUNT_FLAG_", this.mountFlags));
        indentingPrintWriter.printPair("mountUserId", java.lang.Integer.valueOf(this.mountUserId));
        indentingPrintWriter.printPair("state", android.util.DebugUtils.valueToString(getClass(), "STATE_", this.state));
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("fsType", this.fsType);
        indentingPrintWriter.printPair("fsUuid", this.fsUuid);
        indentingPrintWriter.printPair("fsLabel", this.fsLabel);
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("path", this.path);
        indentingPrintWriter.printPair("internalPath", this.internalPath);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.os.storage.VolumeInfo m3199clone() {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            return CREATOR.createFromParcel(obtain);
        } finally {
            obtain.recycle();
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj instanceof android.os.storage.VolumeInfo) {
            return java.util.Objects.equals(this.id, ((android.os.storage.VolumeInfo) obj).id);
        }
        return false;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.id);
        parcel.writeInt(this.type);
        if (this.disk != null) {
            parcel.writeInt(1);
            this.disk.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString8(this.partGuid);
        parcel.writeInt(this.mountFlags);
        parcel.writeInt(this.mountUserId);
        parcel.writeInt(this.state);
        parcel.writeString8(this.fsType);
        parcel.writeString8(this.fsUuid);
        parcel.writeString8(this.fsLabel);
        parcel.writeString8(this.path);
        parcel.writeString8(this.internalPath);
    }
}
