package android.hardware.location;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public class NanoApp implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.location.NanoApp> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.NanoApp>() { // from class: android.hardware.location.NanoApp.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoApp createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.location.NanoApp(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoApp[] newArray(int i) {
            return new android.hardware.location.NanoApp[i];
        }
    };
    private final java.lang.String TAG;
    private final java.lang.String UNKNOWN;
    private byte[] mAppBinary;
    private long mAppId;
    private boolean mAppIdSet;
    private int mAppVersion;
    private java.lang.String mName;
    private int mNeededExecMemBytes;
    private int mNeededReadMemBytes;
    private int[] mNeededSensors;
    private int mNeededWriteMemBytes;
    private int[] mOutputEvents;
    private java.lang.String mPublisher;

    public NanoApp() {
        this(0L, (byte[]) null);
        this.mAppIdSet = false;
    }

    @java.lang.Deprecated
    public NanoApp(int i, byte[] bArr) {
        this.TAG = "NanoApp";
        this.UNKNOWN = "Unknown";
        android.util.Log.w("NanoApp", "NanoApp(int, byte[]) is deprecated, please use NanoApp(long, byte[]) instead.");
    }

    public NanoApp(long j, byte[] bArr) {
        this.TAG = "NanoApp";
        this.UNKNOWN = "Unknown";
        this.mPublisher = "Unknown";
        this.mName = "Unknown";
        this.mAppId = j;
        this.mAppIdSet = true;
        this.mAppVersion = 0;
        this.mNeededReadMemBytes = 0;
        this.mNeededWriteMemBytes = 0;
        this.mNeededExecMemBytes = 0;
        this.mNeededSensors = new int[0];
        this.mOutputEvents = new int[0];
        this.mAppBinary = bArr;
    }

    public void setPublisher(java.lang.String str) {
        this.mPublisher = str;
    }

    public void setName(java.lang.String str) {
        this.mName = str;
    }

    public void setAppId(long j) {
        this.mAppId = j;
        this.mAppIdSet = true;
    }

    public void setAppVersion(int i) {
        this.mAppVersion = i;
    }

    public void setNeededReadMemBytes(int i) {
        this.mNeededReadMemBytes = i;
    }

    public void setNeededWriteMemBytes(int i) {
        this.mNeededWriteMemBytes = i;
    }

    public void setNeededExecMemBytes(int i) {
        this.mNeededExecMemBytes = i;
    }

    public void setNeededSensors(int[] iArr) {
        java.util.Objects.requireNonNull(iArr, "neededSensors must not be null");
        this.mNeededSensors = iArr;
    }

    public void setOutputEvents(int[] iArr) {
        java.util.Objects.requireNonNull(iArr, "outputEvents must not be null");
        this.mOutputEvents = iArr;
    }

    public void setAppBinary(byte[] bArr) {
        java.util.Objects.requireNonNull(bArr, "appBinary must not be null");
        this.mAppBinary = bArr;
    }

    public java.lang.String getPublisher() {
        return this.mPublisher;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public long getAppId() {
        return this.mAppId;
    }

    public int getAppVersion() {
        return this.mAppVersion;
    }

    public int getNeededReadMemBytes() {
        return this.mNeededReadMemBytes;
    }

    public int getNeededWriteMemBytes() {
        return this.mNeededWriteMemBytes;
    }

    public int getNeededExecMemBytes() {
        return this.mNeededExecMemBytes;
    }

    public int[] getNeededSensors() {
        return this.mNeededSensors;
    }

    public int[] getOutputEvents() {
        return this.mOutputEvents;
    }

    public byte[] getAppBinary() {
        return this.mAppBinary;
    }

    private NanoApp(android.os.Parcel parcel) {
        this.TAG = "NanoApp";
        this.UNKNOWN = "Unknown";
        this.mPublisher = parcel.readString();
        this.mName = parcel.readString();
        this.mAppId = parcel.readLong();
        this.mAppVersion = parcel.readInt();
        this.mNeededReadMemBytes = parcel.readInt();
        this.mNeededWriteMemBytes = parcel.readInt();
        this.mNeededExecMemBytes = parcel.readInt();
        this.mNeededSensors = new int[parcel.readInt()];
        parcel.readIntArray(this.mNeededSensors);
        this.mOutputEvents = new int[parcel.readInt()];
        parcel.readIntArray(this.mOutputEvents);
        this.mAppBinary = new byte[parcel.readInt()];
        parcel.readByteArray(this.mAppBinary);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mAppBinary == null) {
            throw new java.lang.IllegalStateException("Must set non-null AppBinary for nanoapp " + this.mName);
        }
        if (!this.mAppIdSet) {
            throw new java.lang.IllegalStateException("Must set AppId for nanoapp " + this.mName);
        }
        parcel.writeString(this.mPublisher);
        parcel.writeString(this.mName);
        parcel.writeLong(this.mAppId);
        parcel.writeInt(this.mAppVersion);
        parcel.writeInt(this.mNeededReadMemBytes);
        parcel.writeInt(this.mNeededWriteMemBytes);
        parcel.writeInt(this.mNeededExecMemBytes);
        parcel.writeInt(this.mNeededSensors.length);
        parcel.writeIntArray(this.mNeededSensors);
        parcel.writeInt(this.mOutputEvents.length);
        parcel.writeIntArray(this.mOutputEvents);
        parcel.writeInt(this.mAppBinary.length);
        parcel.writeByteArray(this.mAppBinary);
    }

    public java.lang.String toString() {
        return ((("Id : " + this.mAppId) + ", Version : " + this.mAppVersion) + ", Name : " + this.mName) + ", Publisher : " + this.mPublisher;
    }
}
