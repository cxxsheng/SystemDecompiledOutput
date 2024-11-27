package android.hardware.location;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class NanoAppState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.location.NanoAppState> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.NanoAppState>() { // from class: android.hardware.location.NanoAppState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoAppState createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.location.NanoAppState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoAppState[] newArray(int i) {
            return new android.hardware.location.NanoAppState[i];
        }
    };
    private boolean mIsEnabled;
    private long mNanoAppId;
    private java.util.List<java.lang.String> mNanoAppPermissions;
    private java.util.List<android.hardware.location.NanoAppRpcService> mNanoAppRpcServiceList;
    private int mNanoAppVersion;

    public NanoAppState(long j, int i, boolean z) {
        this.mNanoAppPermissions = new java.util.ArrayList();
        this.mNanoAppRpcServiceList = new java.util.ArrayList();
        this.mNanoAppId = j;
        this.mNanoAppVersion = i;
        this.mIsEnabled = z;
    }

    public NanoAppState(long j, int i, boolean z, java.util.List<java.lang.String> list) {
        this.mNanoAppPermissions = new java.util.ArrayList();
        this.mNanoAppRpcServiceList = new java.util.ArrayList();
        this.mNanoAppId = j;
        this.mNanoAppVersion = i;
        this.mIsEnabled = z;
        this.mNanoAppPermissions = java.util.Collections.unmodifiableList(list);
    }

    public NanoAppState(long j, int i, boolean z, java.util.List<java.lang.String> list, java.util.List<android.hardware.location.NanoAppRpcService> list2) {
        this.mNanoAppPermissions = new java.util.ArrayList();
        this.mNanoAppRpcServiceList = new java.util.ArrayList();
        this.mNanoAppId = j;
        this.mNanoAppVersion = i;
        this.mIsEnabled = z;
        this.mNanoAppPermissions = java.util.Collections.unmodifiableList(list);
        this.mNanoAppRpcServiceList = java.util.Collections.unmodifiableList(list2);
    }

    public long getNanoAppId() {
        return this.mNanoAppId;
    }

    public long getNanoAppVersion() {
        return this.mNanoAppVersion;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public java.util.List<java.lang.String> getNanoAppPermissions() {
        return this.mNanoAppPermissions;
    }

    public java.util.List<android.hardware.location.NanoAppRpcService> getRpcServices() {
        return this.mNanoAppRpcServiceList;
    }

    private NanoAppState(android.os.Parcel parcel) {
        this.mNanoAppPermissions = new java.util.ArrayList();
        this.mNanoAppRpcServiceList = new java.util.ArrayList();
        this.mNanoAppId = parcel.readLong();
        this.mNanoAppVersion = parcel.readInt();
        this.mIsEnabled = parcel.readInt() == 1;
        this.mNanoAppPermissions = new java.util.ArrayList();
        parcel.readStringList(this.mNanoAppPermissions);
        this.mNanoAppRpcServiceList = java.util.Collections.unmodifiableList(java.util.Arrays.asList((android.hardware.location.NanoAppRpcService[]) parcel.readParcelableArray(android.hardware.location.NanoAppRpcService.class.getClassLoader(), android.hardware.location.NanoAppRpcService.class)));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mNanoAppId);
        parcel.writeInt(this.mNanoAppVersion);
        parcel.writeInt(this.mIsEnabled ? 1 : 0);
        parcel.writeStringList(this.mNanoAppPermissions);
        parcel.writeParcelableArray((android.hardware.location.NanoAppRpcService[]) this.mNanoAppRpcServiceList.toArray(new android.hardware.location.NanoAppRpcService[0]), 0);
    }
}
