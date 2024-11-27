package android.os.storage;

/* loaded from: classes3.dex */
public class VolumeRecord implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.storage.VolumeRecord> CREATOR = new android.os.Parcelable.Creator<android.os.storage.VolumeRecord>() { // from class: android.os.storage.VolumeRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.storage.VolumeRecord createFromParcel(android.os.Parcel parcel) {
            return new android.os.storage.VolumeRecord(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.storage.VolumeRecord[] newArray(int i) {
            return new android.os.storage.VolumeRecord[i];
        }
    };
    public static final java.lang.String EXTRA_FS_UUID = "android.os.storage.extra.FS_UUID";
    public static final int USER_FLAG_INITED = 1;
    public static final int USER_FLAG_SNOOZED = 2;
    public long createdMillis;
    public final java.lang.String fsUuid;
    public long lastBenchMillis;
    public long lastSeenMillis;
    public long lastTrimMillis;
    public java.lang.String nickname;
    public java.lang.String partGuid;
    public final int type;
    public int userFlags;

    public VolumeRecord(int i, java.lang.String str) {
        this.type = i;
        this.fsUuid = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str);
    }

    public VolumeRecord(android.os.Parcel parcel) {
        this.type = parcel.readInt();
        this.fsUuid = parcel.readString();
        this.partGuid = parcel.readString();
        this.nickname = parcel.readString();
        this.userFlags = parcel.readInt();
        this.createdMillis = parcel.readLong();
        this.lastSeenMillis = parcel.readLong();
        this.lastTrimMillis = parcel.readLong();
        this.lastBenchMillis = parcel.readLong();
    }

    public int getType() {
        return this.type;
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

    public java.lang.String getNickname() {
        return this.nickname;
    }

    public boolean isInited() {
        return (this.userFlags & 1) != 0;
    }

    public boolean isSnoozed() {
        return (this.userFlags & 2) != 0;
    }

    public android.os.storage.StorageVolume buildStorageVolume(android.content.Context context) {
        java.lang.String str;
        java.lang.String str2 = "unknown:" + this.fsUuid;
        java.io.File file = new java.io.File("/dev/null");
        java.io.File file2 = new java.io.File("/dev/null");
        android.os.UserHandle userHandle = new android.os.UserHandle(-10000);
        java.lang.String str3 = this.nickname;
        if (str3 != null) {
            str = str3;
        } else {
            str = context.getString(17039374);
        }
        return new android.os.storage.StorageVolume(str2, file, file2, str, false, true, false, false, false, 0L, userHandle, null, this.fsUuid, "unknown");
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("VolumeRecord:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("type", android.util.DebugUtils.valueToString(android.os.storage.VolumeInfo.class, "TYPE_", this.type));
        indentingPrintWriter.printPair("fsUuid", this.fsUuid);
        indentingPrintWriter.printPair("partGuid", this.partGuid);
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("nickname", this.nickname);
        indentingPrintWriter.printPair("userFlags", android.util.DebugUtils.flagsToString(android.os.storage.VolumeRecord.class, "USER_FLAG_", this.userFlags));
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("createdMillis", android.util.TimeUtils.formatForLogging(this.createdMillis));
        indentingPrintWriter.printPair("lastSeenMillis", android.util.TimeUtils.formatForLogging(this.lastSeenMillis));
        indentingPrintWriter.printPair("lastTrimMillis", android.util.TimeUtils.formatForLogging(this.lastTrimMillis));
        indentingPrintWriter.printPair("lastBenchMillis", android.util.TimeUtils.formatForLogging(this.lastBenchMillis));
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.os.storage.VolumeRecord m3200clone() {
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
        if (obj instanceof android.os.storage.VolumeRecord) {
            return java.util.Objects.equals(this.fsUuid, ((android.os.storage.VolumeRecord) obj).fsUuid);
        }
        return false;
    }

    public int hashCode() {
        return this.fsUuid.hashCode();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.type);
        parcel.writeString(this.fsUuid);
        parcel.writeString(this.partGuid);
        parcel.writeString(this.nickname);
        parcel.writeInt(this.userFlags);
        parcel.writeLong(this.createdMillis);
        parcel.writeLong(this.lastSeenMillis);
        parcel.writeLong(this.lastTrimMillis);
        parcel.writeLong(this.lastBenchMillis);
    }
}
