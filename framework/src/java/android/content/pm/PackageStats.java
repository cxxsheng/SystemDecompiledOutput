package android.content.pm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class PackageStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.PackageStats> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageStats>() { // from class: android.content.pm.PackageStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.PackageStats createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.PackageStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.PackageStats[] newArray(int i) {
            return new android.content.pm.PackageStats[i];
        }
    };
    public long apkSize;
    public long cacheSize;
    public long codeSize;
    public long curProfSize;
    public long dataSize;
    public long dexoptSize;
    public long dmSize;
    public long externalCacheSize;
    public long externalCodeSize;
    public long externalDataSize;
    public long externalMediaSize;
    public long externalObbSize;
    public long libSize;
    public java.lang.String packageName;
    public long refProfSize;
    public int userHandle;

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("PackageStats{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(" ");
        sb.append(this.packageName);
        if (this.codeSize != 0) {
            sb.append(" code=");
            sb.append(this.codeSize);
        }
        if (this.dataSize != 0) {
            sb.append(" data=");
            sb.append(this.dataSize);
        }
        if (this.cacheSize != 0) {
            sb.append(" cache=");
            sb.append(this.cacheSize);
        }
        if (this.apkSize != 0) {
            sb.append(" apk=");
            sb.append(this.apkSize);
        }
        if (this.libSize != 0) {
            sb.append(" lib=");
            sb.append(this.libSize);
        }
        if (this.dmSize != 0) {
            sb.append(" dm=");
            sb.append(this.dmSize);
        }
        if (this.dexoptSize != 0) {
            sb.append(" dexopt=");
            sb.append(this.dexoptSize);
        }
        if (this.curProfSize != 0) {
            sb.append(" curProf=");
            sb.append(this.curProfSize);
        }
        if (this.refProfSize != 0) {
            sb.append(" refProf=");
            sb.append(this.refProfSize);
        }
        if (this.externalCodeSize != 0) {
            sb.append(" extCode=");
            sb.append(this.externalCodeSize);
        }
        if (this.externalDataSize != 0) {
            sb.append(" extData=");
            sb.append(this.externalDataSize);
        }
        if (this.externalCacheSize != 0) {
            sb.append(" extCache=");
            sb.append(this.externalCacheSize);
        }
        if (this.externalMediaSize != 0) {
            sb.append(" media=");
            sb.append(this.externalMediaSize);
        }
        if (this.externalObbSize != 0) {
            sb.append(" obb=");
            sb.append(this.externalObbSize);
        }
        sb.append("}");
        return sb.toString();
    }

    public PackageStats(java.lang.String str) {
        this.packageName = str;
        this.userHandle = android.os.UserHandle.myUserId();
    }

    public PackageStats(java.lang.String str, int i) {
        this.packageName = str;
        this.userHandle = i;
    }

    public PackageStats(android.os.Parcel parcel) {
        this.packageName = parcel.readString();
        this.userHandle = parcel.readInt();
        this.codeSize = parcel.readLong();
        this.dataSize = parcel.readLong();
        this.cacheSize = parcel.readLong();
        this.apkSize = parcel.readLong();
        this.libSize = parcel.readLong();
        this.dmSize = parcel.readLong();
        this.dexoptSize = parcel.readLong();
        this.curProfSize = parcel.readLong();
        this.refProfSize = parcel.readLong();
        this.externalCodeSize = parcel.readLong();
        this.externalDataSize = parcel.readLong();
        this.externalCacheSize = parcel.readLong();
        this.externalMediaSize = parcel.readLong();
        this.externalObbSize = parcel.readLong();
    }

    public PackageStats(android.content.pm.PackageStats packageStats) {
        this.packageName = packageStats.packageName;
        this.userHandle = packageStats.userHandle;
        this.codeSize = packageStats.codeSize;
        this.dataSize = packageStats.dataSize;
        this.cacheSize = packageStats.cacheSize;
        this.apkSize = packageStats.apkSize;
        this.libSize = packageStats.libSize;
        this.dmSize = packageStats.dmSize;
        this.dexoptSize = packageStats.dexoptSize;
        this.curProfSize = packageStats.curProfSize;
        this.refProfSize = packageStats.refProfSize;
        this.externalCodeSize = packageStats.externalCodeSize;
        this.externalDataSize = packageStats.externalDataSize;
        this.externalCacheSize = packageStats.externalCacheSize;
        this.externalMediaSize = packageStats.externalMediaSize;
        this.externalObbSize = packageStats.externalObbSize;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeInt(this.userHandle);
        parcel.writeLong(this.codeSize);
        parcel.writeLong(this.dataSize);
        parcel.writeLong(this.cacheSize);
        parcel.writeLong(this.apkSize);
        parcel.writeLong(this.libSize);
        parcel.writeLong(this.dmSize);
        parcel.writeLong(this.dexoptSize);
        parcel.writeLong(this.curProfSize);
        parcel.writeLong(this.refProfSize);
        parcel.writeLong(this.externalCodeSize);
        parcel.writeLong(this.externalDataSize);
        parcel.writeLong(this.externalCacheSize);
        parcel.writeLong(this.externalMediaSize);
        parcel.writeLong(this.externalObbSize);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.content.pm.PackageStats)) {
            return false;
        }
        android.content.pm.PackageStats packageStats = (android.content.pm.PackageStats) obj;
        return android.text.TextUtils.equals(this.packageName, packageStats.packageName) && this.userHandle == packageStats.userHandle && this.codeSize == packageStats.codeSize && this.dataSize == packageStats.dataSize && this.cacheSize == packageStats.cacheSize && this.apkSize == packageStats.apkSize && this.libSize == packageStats.libSize && this.dmSize == packageStats.dmSize && this.dexoptSize == packageStats.dexoptSize && this.curProfSize == packageStats.curProfSize && this.refProfSize == packageStats.refProfSize && this.externalCodeSize == packageStats.externalCodeSize && this.externalDataSize == packageStats.externalDataSize && this.externalCacheSize == packageStats.externalCacheSize && this.externalMediaSize == packageStats.externalMediaSize && this.externalObbSize == packageStats.externalObbSize;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.packageName, java.lang.Integer.valueOf(this.userHandle), java.lang.Long.valueOf(this.codeSize), java.lang.Long.valueOf(this.dataSize), java.lang.Long.valueOf(this.apkSize), java.lang.Long.valueOf(this.libSize), java.lang.Long.valueOf(this.dmSize), java.lang.Long.valueOf(this.dexoptSize), java.lang.Long.valueOf(this.curProfSize), java.lang.Long.valueOf(this.refProfSize), java.lang.Long.valueOf(this.cacheSize), java.lang.Long.valueOf(this.externalCodeSize), java.lang.Long.valueOf(this.externalDataSize), java.lang.Long.valueOf(this.externalCacheSize), java.lang.Long.valueOf(this.externalMediaSize), java.lang.Long.valueOf(this.externalObbSize));
    }
}
