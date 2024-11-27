package android.app.usage;

/* loaded from: classes.dex */
public final class StorageStats implements android.os.Parcelable {
    public static final int APP_DATA_TYPE_FILE_TYPE_APK = 3;
    public static final int APP_DATA_TYPE_FILE_TYPE_CURRENT_PROFILE = 2;
    public static final int APP_DATA_TYPE_FILE_TYPE_DEXOPT_ARTIFACT = 0;
    public static final int APP_DATA_TYPE_FILE_TYPE_DM = 4;
    public static final int APP_DATA_TYPE_FILE_TYPE_REFERENCE_PROFILE = 1;
    public static final int APP_DATA_TYPE_LIB = 5;
    public static final android.os.Parcelable.Creator<android.app.usage.StorageStats> CREATOR = new android.os.Parcelable.Creator<android.app.usage.StorageStats>() { // from class: android.app.usage.StorageStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.StorageStats createFromParcel(android.os.Parcel parcel) {
            return new android.app.usage.StorageStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.StorageStats[] newArray(int i) {
            return new android.app.usage.StorageStats[i];
        }
    };
    public long apkBytes;
    public long cacheBytes;
    public long codeBytes;
    public long curProfBytes;
    public long dataBytes;
    public long dexoptBytes;
    public long dmBytes;
    public long externalCacheBytes;
    public long libBytes;
    public long refProfBytes;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AppDataType {
    }

    public long getAppBytes() {
        return this.codeBytes;
    }

    public long getAppBytesByDataType(int i) {
        switch (i) {
            case 0:
                return this.dexoptBytes;
            case 1:
                return this.refProfBytes;
            case 2:
                return this.curProfBytes;
            case 3:
                return this.apkBytes;
            case 4:
                return this.dmBytes;
            case 5:
                return this.libBytes;
            default:
                return 0L;
        }
    }

    public long getDataBytes() {
        return this.dataBytes;
    }

    public long getCacheBytes() {
        return this.cacheBytes;
    }

    public long getExternalCacheBytes() {
        return this.externalCacheBytes;
    }

    public StorageStats() {
    }

    public StorageStats(android.os.Parcel parcel) {
        this.codeBytes = parcel.readLong();
        this.dataBytes = parcel.readLong();
        this.cacheBytes = parcel.readLong();
        this.dexoptBytes = parcel.readLong();
        this.refProfBytes = parcel.readLong();
        this.curProfBytes = parcel.readLong();
        this.apkBytes = parcel.readLong();
        this.libBytes = parcel.readLong();
        this.dmBytes = parcel.readLong();
        this.externalCacheBytes = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.codeBytes);
        parcel.writeLong(this.dataBytes);
        parcel.writeLong(this.cacheBytes);
        parcel.writeLong(this.dexoptBytes);
        parcel.writeLong(this.refProfBytes);
        parcel.writeLong(this.curProfBytes);
        parcel.writeLong(this.apkBytes);
        parcel.writeLong(this.libBytes);
        parcel.writeLong(this.dmBytes);
        parcel.writeLong(this.externalCacheBytes);
    }
}
