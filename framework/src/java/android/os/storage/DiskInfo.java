package android.os.storage;

/* loaded from: classes3.dex */
public class DiskInfo implements android.os.Parcelable {
    public static final java.lang.String ACTION_DISK_SCANNED = "android.os.storage.action.DISK_SCANNED";
    public static final android.os.Parcelable.Creator<android.os.storage.DiskInfo> CREATOR = new android.os.Parcelable.Creator<android.os.storage.DiskInfo>() { // from class: android.os.storage.DiskInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.storage.DiskInfo createFromParcel(android.os.Parcel parcel) {
            return new android.os.storage.DiskInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.storage.DiskInfo[] newArray(int i) {
            return new android.os.storage.DiskInfo[i];
        }
    };
    public static final java.lang.String EXTRA_DISK_ID = "android.os.storage.extra.DISK_ID";
    public static final java.lang.String EXTRA_VOLUME_COUNT = "android.os.storage.extra.VOLUME_COUNT";
    public static final int FLAG_ADOPTABLE = 1;
    public static final int FLAG_DEFAULT_PRIMARY = 2;
    public static final int FLAG_SD = 4;
    public static final int FLAG_STUB_VISIBLE = 64;
    public static final int FLAG_USB = 8;
    public final int flags;
    public final java.lang.String id;
    public java.lang.String label;
    public long size;
    public java.lang.String sysPath;
    public int volumeCount;

    public DiskInfo(java.lang.String str, int i) {
        this.id = (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str);
        this.flags = i;
    }

    public DiskInfo(android.os.Parcel parcel) {
        this.id = parcel.readString();
        this.flags = parcel.readInt();
        this.size = parcel.readLong();
        this.label = parcel.readString();
        this.volumeCount = parcel.readInt();
        this.sysPath = parcel.readString();
    }

    public java.lang.String getId() {
        return this.id;
    }

    private boolean isInteresting(java.lang.String str) {
        return (android.text.TextUtils.isEmpty(str) || str.equalsIgnoreCase("ata") || str.toLowerCase().contains("generic") || str.toLowerCase().startsWith("usb") || str.toLowerCase().startsWith("multiple")) ? false : true;
    }

    public java.lang.String getDescription() {
        android.content.res.Resources system = android.content.res.Resources.getSystem();
        if ((this.flags & 4) != 0) {
            if (isInteresting(this.label)) {
                return system.getString(com.android.internal.R.string.storage_sd_card_label, this.label);
            }
            return system.getString(com.android.internal.R.string.storage_sd_card);
        }
        if ((this.flags & 8) != 0) {
            if (isInteresting(this.label)) {
                return system.getString(com.android.internal.R.string.storage_usb_drive_label, this.label);
            }
            return system.getString(com.android.internal.R.string.storage_usb_drive);
        }
        return null;
    }

    public java.lang.String getShortDescription() {
        android.content.res.Resources system = android.content.res.Resources.getSystem();
        if (isSd()) {
            return system.getString(com.android.internal.R.string.storage_sd_card);
        }
        if (isUsb()) {
            return system.getString(com.android.internal.R.string.storage_usb_drive);
        }
        return null;
    }

    public boolean isAdoptable() {
        return (this.flags & 1) != 0;
    }

    public boolean isDefaultPrimary() {
        return (this.flags & 2) != 0;
    }

    public boolean isSd() {
        return (this.flags & 4) != 0;
    }

    public boolean isUsb() {
        return (this.flags & 8) != 0;
    }

    public boolean isStubVisible() {
        return (this.flags & 64) != 0;
    }

    public java.lang.String toString() {
        java.io.CharArrayWriter charArrayWriter = new java.io.CharArrayWriter();
        dump(new com.android.internal.util.IndentingPrintWriter(charArrayWriter, "    ", 80));
        return charArrayWriter.toString();
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("DiskInfo{" + this.id + "}:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("flags", android.util.DebugUtils.flagsToString(getClass(), "FLAG_", this.flags));
        indentingPrintWriter.printPair("size", java.lang.Long.valueOf(this.size));
        indentingPrintWriter.printPair("label", this.label);
        indentingPrintWriter.println();
        indentingPrintWriter.printPair("sysPath", this.sysPath);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.os.storage.DiskInfo m3193clone() {
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
        if (obj instanceof android.os.storage.DiskInfo) {
            return java.util.Objects.equals(this.id, ((android.os.storage.DiskInfo) obj).id);
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
        parcel.writeString(this.id);
        parcel.writeInt(this.flags);
        parcel.writeLong(this.size);
        parcel.writeString(this.label);
        parcel.writeInt(this.volumeCount);
        parcel.writeString(this.sysPath);
    }
}
