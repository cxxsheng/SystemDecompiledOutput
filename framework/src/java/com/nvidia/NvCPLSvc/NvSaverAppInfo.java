package com.nvidia.NvCPLSvc;

/* loaded from: classes5.dex */
public class NvSaverAppInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.nvidia.NvCPLSvc.NvSaverAppInfo> CREATOR = new android.os.Parcelable.Creator<com.nvidia.NvCPLSvc.NvSaverAppInfo>() { // from class: com.nvidia.NvCPLSvc.NvSaverAppInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.nvidia.NvCPLSvc.NvSaverAppInfo createFromParcel(android.os.Parcel parcel) {
            return new com.nvidia.NvCPLSvc.NvSaverAppInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.nvidia.NvCPLSvc.NvSaverAppInfo[] newArray(int i) {
            return new com.nvidia.NvCPLSvc.NvSaverAppInfo[i];
        }
    };
    public static final int NVSAVER_ACTIVITY_HIGH = 1;
    public static final int NVSAVER_ACTIVITY_LOW = 3;
    public static final int NVSAVER_ACTIVITY_MIDIUM = 2;
    public static final int NVSAVER_LIST_BLACKLIST = 3;
    public static final int NVSAVER_LIST_NONE = 1;
    public static final int NVSAVER_LIST_WHITELIST = 2;
    public static final int NV_APP_OPTIMIZE_LIST = 4;
    public int mAppList;
    public java.lang.String mPkgName;
    public long mTotalWakeupStatsTime;
    public int mUid;
    public long mWakeupStatsTime;
    public int mWakeupTimes;
    public int mWowWakeupTimes;
    private java.lang.String mAppLabel = null;
    private android.graphics.drawable.Drawable mAppIcon = null;
    private int mAppActivity = 0;
    private float mPowerSaver = 0.0f;

    public NvSaverAppInfo(android.os.Parcel parcel) {
        this.mUid = parcel.readInt();
        this.mAppList = parcel.readInt();
        this.mWakeupTimes = parcel.readInt();
        this.mWowWakeupTimes = parcel.readInt();
        this.mPkgName = parcel.readString();
        this.mWakeupStatsTime = parcel.readLong();
        this.mTotalWakeupStatsTime = parcel.readLong();
    }

    public NvSaverAppInfo(int i, int i2, int i3, int i4, java.lang.String str, long j, long j2) {
        this.mUid = i;
        this.mAppList = i2;
        this.mWakeupTimes = i3;
        this.mWowWakeupTimes = i4;
        this.mPkgName = str;
        this.mWakeupStatsTime = j;
        this.mTotalWakeupStatsTime = j2;
    }

    public java.lang.String getAppLabel() {
        return this.mAppLabel;
    }

    public void setAppLabel(java.lang.String str) {
        this.mAppLabel = str;
    }

    public android.graphics.drawable.Drawable getAppIcon() {
        return this.mAppIcon;
    }

    public void setAppIcon(android.graphics.drawable.Drawable drawable) {
        this.mAppIcon = drawable;
    }

    public int getAppActivity() {
        return this.mAppActivity;
    }

    public void setAppActivity(int i) {
        this.mAppActivity = i;
    }

    public java.lang.String getPkgName() {
        return this.mPkgName;
    }

    public void setPkgName(java.lang.String str) {
        this.mPkgName = str;
    }

    public int getUid() {
        return this.mUid;
    }

    public void setUid(int i) {
        this.mUid = i;
    }

    public int getWakeupTimes() {
        return this.mWakeupTimes;
    }

    public void setWakeupTimes(int i) {
        this.mWakeupTimes = i;
    }

    public int getWowWakeupTimes() {
        return this.mWowWakeupTimes;
    }

    public void setWowWakeupTimes(int i) {
        this.mWowWakeupTimes = i;
    }

    public long getTotalWakeupStatsTime() {
        return this.mTotalWakeupStatsTime;
    }

    public void setTotalWakeupStatsTime(long j) {
        this.mTotalWakeupStatsTime = j;
    }

    public long getWakeupStatsTime() {
        return this.mWakeupStatsTime;
    }

    public void setWakeupStatsTime(long j) {
        this.mWakeupStatsTime = j;
    }

    public int getAppList() {
        return this.mAppList;
    }

    public void setAppList(int i) {
        this.mAppList = i;
    }

    public float getPowerSaver() {
        return this.mPowerSaver;
    }

    public void setPowerSaver(float f) {
        this.mPowerSaver = f;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mUid);
        parcel.writeInt(this.mAppList);
        parcel.writeInt(this.mWakeupTimes);
        parcel.writeInt(this.mWowWakeupTimes);
        parcel.writeString(this.mPkgName);
        parcel.writeLong(this.mWakeupStatsTime);
        parcel.writeLong(this.mTotalWakeupStatsTime);
    }
}
