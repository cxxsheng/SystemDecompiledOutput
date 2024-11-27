package android.companion.virtualcamera;

/* loaded from: classes.dex */
public class VirtualCameraConfiguration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.companion.virtualcamera.VirtualCameraConfiguration> CREATOR = new android.os.Parcelable.Creator<android.companion.virtualcamera.VirtualCameraConfiguration>() { // from class: android.companion.virtualcamera.VirtualCameraConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtualcamera.VirtualCameraConfiguration createFromParcel(android.os.Parcel parcel) {
            android.companion.virtualcamera.VirtualCameraConfiguration virtualCameraConfiguration = new android.companion.virtualcamera.VirtualCameraConfiguration();
            virtualCameraConfiguration.readFromParcel(parcel);
            return virtualCameraConfiguration;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtualcamera.VirtualCameraConfiguration[] newArray(int i) {
            return new android.companion.virtualcamera.VirtualCameraConfiguration[i];
        }
    };
    public int lensFacing;
    public int sensorOrientation = 0;
    public android.companion.virtualcamera.SupportedStreamConfiguration[] supportedStreamConfigs;
    public android.companion.virtualcamera.IVirtualCameraCallback virtualCameraCallback;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.supportedStreamConfigs, i);
        parcel.writeStrongInterface(this.virtualCameraCallback);
        parcel.writeInt(this.sensorOrientation);
        parcel.writeInt(this.lensFacing);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new android.os.BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.supportedStreamConfigs = (android.companion.virtualcamera.SupportedStreamConfiguration[]) parcel.createTypedArray(android.companion.virtualcamera.SupportedStreamConfiguration.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.virtualCameraCallback = android.companion.virtualcamera.IVirtualCameraCallback.Stub.asInterface(parcel.readStrongBinder());
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sensorOrientation = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.lensFacing = parcel.readInt();
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            }
        } catch (java.lang.Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new android.os.BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.supportedStreamConfigs) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
