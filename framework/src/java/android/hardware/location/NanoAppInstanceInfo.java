package android.hardware.location;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public class NanoAppInstanceInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.location.NanoAppInstanceInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.NanoAppInstanceInfo>() { // from class: android.hardware.location.NanoAppInstanceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoAppInstanceInfo createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.location.NanoAppInstanceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoAppInstanceInfo[] newArray(int i) {
            return new android.hardware.location.NanoAppInstanceInfo[i];
        }
    };
    private long mAppId;
    private int mAppVersion;
    private int mContexthubId;
    private int mHandle;
    private java.lang.String mName;
    private int mNeededExecMemBytes;
    private int mNeededReadMemBytes;
    private int[] mNeededSensors;
    private int mNeededWriteMemBytes;
    private int[] mOutputEvents;
    private java.lang.String mPublisher;

    public NanoAppInstanceInfo() {
        this.mPublisher = "Unknown";
        this.mName = "Unknown";
        this.mNeededReadMemBytes = 0;
        this.mNeededWriteMemBytes = 0;
        this.mNeededExecMemBytes = 0;
        this.mNeededSensors = libcore.util.EmptyArray.INT;
        this.mOutputEvents = libcore.util.EmptyArray.INT;
    }

    public NanoAppInstanceInfo(int i, long j, int i2, int i3) {
        this.mPublisher = "Unknown";
        this.mName = "Unknown";
        this.mNeededReadMemBytes = 0;
        this.mNeededWriteMemBytes = 0;
        this.mNeededExecMemBytes = 0;
        this.mNeededSensors = libcore.util.EmptyArray.INT;
        this.mOutputEvents = libcore.util.EmptyArray.INT;
        this.mHandle = i;
        this.mAppId = j;
        this.mAppVersion = i2;
        this.mContexthubId = i3;
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

    public int getContexthubId() {
        return this.mContexthubId;
    }

    public int getHandle() {
        return this.mHandle;
    }

    private NanoAppInstanceInfo(android.os.Parcel parcel) {
        this.mPublisher = "Unknown";
        this.mName = "Unknown";
        this.mNeededReadMemBytes = 0;
        this.mNeededWriteMemBytes = 0;
        this.mNeededExecMemBytes = 0;
        this.mNeededSensors = libcore.util.EmptyArray.INT;
        this.mOutputEvents = libcore.util.EmptyArray.INT;
        this.mPublisher = parcel.readString();
        this.mName = parcel.readString();
        this.mHandle = parcel.readInt();
        this.mAppId = parcel.readLong();
        this.mAppVersion = parcel.readInt();
        this.mContexthubId = parcel.readInt();
        this.mNeededReadMemBytes = parcel.readInt();
        this.mNeededWriteMemBytes = parcel.readInt();
        this.mNeededExecMemBytes = parcel.readInt();
        this.mNeededSensors = new int[parcel.readInt()];
        parcel.readIntArray(this.mNeededSensors);
        this.mOutputEvents = new int[parcel.readInt()];
        parcel.readIntArray(this.mOutputEvents);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPublisher);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mHandle);
        parcel.writeLong(this.mAppId);
        parcel.writeInt(this.mAppVersion);
        parcel.writeInt(this.mContexthubId);
        parcel.writeInt(this.mNeededReadMemBytes);
        parcel.writeInt(this.mNeededWriteMemBytes);
        parcel.writeInt(this.mNeededExecMemBytes);
        parcel.writeInt(this.mNeededSensors.length);
        parcel.writeIntArray(this.mNeededSensors);
        parcel.writeInt(this.mOutputEvents.length);
        parcel.writeIntArray(this.mOutputEvents);
    }

    public java.lang.String toString() {
        return (("handle : " + this.mHandle) + ", Id : 0x" + java.lang.Long.toHexString(this.mAppId)) + ", Version : 0x" + java.lang.Integer.toHexString(this.mAppVersion);
    }
}
