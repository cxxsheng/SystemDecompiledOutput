package android.hardware.location;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class MemoryRegion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.location.MemoryRegion> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.MemoryRegion>() { // from class: android.hardware.location.MemoryRegion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.MemoryRegion createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.location.MemoryRegion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.MemoryRegion[] newArray(int i) {
            return new android.hardware.location.MemoryRegion[i];
        }
    };
    private boolean mIsExecutable;
    private boolean mIsReadable;
    private boolean mIsWritable;
    private int mSizeBytes;
    private int mSizeBytesFree;

    public int getCapacityBytes() {
        return this.mSizeBytes;
    }

    public int getFreeCapacityBytes() {
        return this.mSizeBytesFree;
    }

    public boolean isReadable() {
        return this.mIsReadable;
    }

    public boolean isWritable() {
        return this.mIsWritable;
    }

    public boolean isExecutable() {
        return this.mIsExecutable;
    }

    public java.lang.String toString() {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        if (isReadable()) {
            str = "r";
        } else {
            str = "" + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        }
        if (isWritable()) {
            str2 = str + "w";
        } else {
            str2 = str + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        }
        if (isExecutable()) {
            str3 = str2 + "x";
        } else {
            str3 = str2 + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        }
        return "[ " + this.mSizeBytesFree + "/ " + this.mSizeBytes + " ] : " + str3;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.hardware.location.MemoryRegion)) {
            return false;
        }
        android.hardware.location.MemoryRegion memoryRegion = (android.hardware.location.MemoryRegion) obj;
        return memoryRegion.getCapacityBytes() == this.mSizeBytes && memoryRegion.getFreeCapacityBytes() == this.mSizeBytesFree && memoryRegion.isReadable() == this.mIsReadable && memoryRegion.isWritable() == this.mIsWritable && memoryRegion.isExecutable() == this.mIsExecutable;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSizeBytes);
        parcel.writeInt(this.mSizeBytesFree);
        parcel.writeInt(this.mIsReadable ? 1 : 0);
        parcel.writeInt(this.mIsWritable ? 1 : 0);
        parcel.writeInt(this.mIsExecutable ? 1 : 0);
    }

    public MemoryRegion(android.os.Parcel parcel) {
        this.mSizeBytes = parcel.readInt();
        this.mSizeBytesFree = parcel.readInt();
        this.mIsReadable = parcel.readInt() != 0;
        this.mIsWritable = parcel.readInt() != 0;
        this.mIsExecutable = parcel.readInt() != 0;
    }
}
