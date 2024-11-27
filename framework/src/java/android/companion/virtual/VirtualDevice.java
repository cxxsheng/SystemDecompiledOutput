package android.companion.virtual;

/* loaded from: classes.dex */
public final class VirtualDevice implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.companion.virtual.VirtualDevice> CREATOR = new android.os.Parcelable.Creator<android.companion.virtual.VirtualDevice>() { // from class: android.companion.virtual.VirtualDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.VirtualDevice createFromParcel(android.os.Parcel parcel) {
            return new android.companion.virtual.VirtualDevice(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.VirtualDevice[] newArray(int i) {
            return new android.companion.virtual.VirtualDevice[i];
        }
    };
    private final java.lang.CharSequence mDisplayName;
    private final int mId;
    private final java.lang.String mName;
    private final java.lang.String mPersistentId;
    private final android.companion.virtual.IVirtualDevice mVirtualDevice;

    public VirtualDevice(android.companion.virtual.IVirtualDevice iVirtualDevice, int i, java.lang.String str, java.lang.String str2) {
        this(iVirtualDevice, i, str, str2, null);
    }

    public VirtualDevice(android.companion.virtual.IVirtualDevice iVirtualDevice, int i, java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("VirtualDevice ID must be greater than 0");
        }
        this.mVirtualDevice = iVirtualDevice;
        this.mId = i;
        this.mPersistentId = str;
        this.mName = str2;
        this.mDisplayName = charSequence;
    }

    private VirtualDevice(android.os.Parcel parcel) {
        this.mVirtualDevice = android.companion.virtual.IVirtualDevice.Stub.asInterface(parcel.readStrongBinder());
        this.mId = parcel.readInt();
        this.mPersistentId = parcel.readString8();
        this.mName = parcel.readString8();
        this.mDisplayName = parcel.readCharSequence();
    }

    public int getDeviceId() {
        return this.mId;
    }

    public java.lang.String getPersistentDeviceId() {
        return this.mPersistentId;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public int[] getDisplayIds() {
        try {
            return this.mVirtualDevice.getDisplayIds();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasCustomSensorSupport() {
        try {
            return this.mVirtualDevice.getDevicePolicy(0) == 1;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean hasCustomAudioInputSupport() {
        try {
            return this.mVirtualDevice.getDevicePolicy(1) == 1;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean hasCustomCameraSupport() {
        try {
            return this.mVirtualDevice.getDevicePolicy(5) == 1;
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
        parcel.writeStrongBinder(this.mVirtualDevice.asBinder());
        parcel.writeInt(this.mId);
        parcel.writeString8(this.mPersistentId);
        parcel.writeString8(this.mName);
        parcel.writeCharSequence(this.mDisplayName);
    }

    public java.lang.String toString() {
        return "VirtualDevice( mId=" + this.mId + " mPersistentId=" + this.mPersistentId + " mName=" + this.mName + " mDisplayName=" + ((java.lang.Object) this.mDisplayName) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
