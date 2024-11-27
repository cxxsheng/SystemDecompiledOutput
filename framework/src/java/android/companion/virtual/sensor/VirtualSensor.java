package android.companion.virtual.sensor;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class VirtualSensor implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.companion.virtual.sensor.VirtualSensor> CREATOR = new android.os.Parcelable.Creator<android.companion.virtual.sensor.VirtualSensor>() { // from class: android.companion.virtual.sensor.VirtualSensor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.sensor.VirtualSensor createFromParcel(android.os.Parcel parcel) {
            return new android.companion.virtual.sensor.VirtualSensor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.sensor.VirtualSensor[] newArray(int i) {
            return new android.companion.virtual.sensor.VirtualSensor[i];
        }
    };
    private final int mHandle;
    private final java.lang.String mName;
    private final android.os.IBinder mToken;
    private final int mType;
    private final android.companion.virtual.IVirtualDevice mVirtualDevice;

    public VirtualSensor(int i, int i2, java.lang.String str, android.companion.virtual.IVirtualDevice iVirtualDevice, android.os.IBinder iBinder) {
        this.mHandle = i;
        this.mType = i2;
        this.mName = str;
        this.mVirtualDevice = iVirtualDevice;
        this.mToken = iBinder;
    }

    public VirtualSensor(int i, int i2, java.lang.String str) {
        this(i, i2, str, null, null);
    }

    private VirtualSensor(android.os.Parcel parcel) {
        this.mHandle = parcel.readInt();
        this.mType = parcel.readInt();
        this.mName = parcel.readString8();
        this.mVirtualDevice = android.companion.virtual.IVirtualDevice.Stub.asInterface(parcel.readStrongBinder());
        this.mToken = parcel.readStrongBinder();
    }

    public int getHandle() {
        return this.mHandle;
    }

    public int getType() {
        return this.mType;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public int getDeviceId() {
        try {
            return this.mVirtualDevice.getDeviceId();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mHandle);
        parcel.writeInt(this.mType);
        parcel.writeString8(this.mName);
        parcel.writeStrongBinder(this.mVirtualDevice.asBinder());
        parcel.writeStrongBinder(this.mToken);
    }

    public java.lang.String toString() {
        return "VirtualSensor{ mType=" + this.mType + ", mName='" + this.mName + "' }";
    }

    public void sendEvent(android.companion.virtual.sensor.VirtualSensorEvent virtualSensorEvent) {
        try {
            this.mVirtualDevice.sendSensorEvent(this.mToken, virtualSensorEvent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
