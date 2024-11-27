package android.hardware.location;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public class NanoAppFilter implements android.os.Parcelable {
    public static final int APP_ANY = -1;
    public static final android.os.Parcelable.Creator<android.hardware.location.NanoAppFilter> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.NanoAppFilter>() { // from class: android.hardware.location.NanoAppFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoAppFilter createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.location.NanoAppFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoAppFilter[] newArray(int i) {
            return new android.hardware.location.NanoAppFilter[i];
        }
    };
    public static final int FLAGS_VERSION_ANY = -1;
    public static final int FLAGS_VERSION_GREAT_THAN = 2;
    public static final int FLAGS_VERSION_LESS_THAN = 4;
    public static final int FLAGS_VERSION_STRICTLY_EQUAL = 8;
    public static final int HUB_ANY = -1;
    private static final java.lang.String TAG = "NanoAppFilter";
    public static final int VENDOR_ANY = -1;
    private long mAppId;
    private long mAppIdVendorMask;
    private int mAppVersion;
    private int mContextHubId;
    private int mVersionRestrictionMask;

    private NanoAppFilter(android.os.Parcel parcel) {
        this.mContextHubId = -1;
        this.mAppId = parcel.readLong();
        this.mAppVersion = parcel.readInt();
        this.mVersionRestrictionMask = parcel.readInt();
        this.mAppIdVendorMask = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mAppId);
        parcel.writeInt(this.mAppVersion);
        parcel.writeInt(this.mVersionRestrictionMask);
        parcel.writeLong(this.mAppIdVendorMask);
    }

    public NanoAppFilter(long j, int i, int i2, long j2) {
        this.mContextHubId = -1;
        this.mAppId = j;
        this.mAppVersion = i;
        this.mVersionRestrictionMask = i2;
        this.mAppIdVendorMask = j2;
    }

    private boolean versionsMatch(int i, int i2, int i3) {
        return true;
    }

    public boolean testMatch(android.hardware.location.NanoAppInstanceInfo nanoAppInstanceInfo) {
        return (this.mContextHubId == -1 || nanoAppInstanceInfo.getContexthubId() == this.mContextHubId) && (this.mAppId == -1 || nanoAppInstanceInfo.getAppId() == this.mAppId) && versionsMatch(this.mVersionRestrictionMask, this.mAppVersion, nanoAppInstanceInfo.getAppVersion());
    }

    public java.lang.String toString() {
        return "nanoAppId: 0x" + java.lang.Long.toHexString(this.mAppId) + ", nanoAppVersion: 0x" + java.lang.Integer.toHexString(this.mAppVersion) + ", versionMask: " + this.mVersionRestrictionMask + ", vendorMask: " + this.mAppIdVendorMask;
    }
}
