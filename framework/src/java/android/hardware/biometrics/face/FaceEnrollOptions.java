package android.hardware.biometrics.face;

/* loaded from: classes.dex */
public class FaceEnrollOptions implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.biometrics.face.FaceEnrollOptions> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.face.FaceEnrollOptions>() { // from class: android.hardware.biometrics.face.FaceEnrollOptions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.face.FaceEnrollOptions createFromParcel(android.os.Parcel parcel) {
            android.hardware.biometrics.face.FaceEnrollOptions faceEnrollOptions = new android.hardware.biometrics.face.FaceEnrollOptions();
            faceEnrollOptions.readFromParcel(parcel);
            return faceEnrollOptions;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.face.FaceEnrollOptions[] newArray(int i) {
            return new android.hardware.biometrics.face.FaceEnrollOptions[i];
        }
    };
    public android.hardware.biometrics.common.OperationContext context;
    public byte enrollmentType;
    public byte[] features;
    public android.hardware.keymaster.HardwareAuthToken hardwareAuthToken;

    @java.lang.Deprecated
    public android.hardware.common.NativeHandle nativeHandlePreview;
    public android.view.Surface surfacePreview;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.hardwareAuthToken, i);
        parcel.writeByte(this.enrollmentType);
        parcel.writeByteArray(this.features);
        parcel.writeTypedObject(this.nativeHandlePreview, i);
        parcel.writeTypedObject(this.surfacePreview, i);
        parcel.writeTypedObject(this.context, i);
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
            this.hardwareAuthToken = (android.hardware.keymaster.HardwareAuthToken) parcel.readTypedObject(android.hardware.keymaster.HardwareAuthToken.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.enrollmentType = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.features = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.nativeHandlePreview = (android.hardware.common.NativeHandle) parcel.readTypedObject(android.hardware.common.NativeHandle.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.surfacePreview = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.context = (android.hardware.biometrics.common.OperationContext) parcel.readTypedObject(android.hardware.biometrics.common.OperationContext.CREATOR);
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
        return describeContents(this.hardwareAuthToken) | 0 | describeContents(this.nativeHandlePreview) | describeContents(this.surfacePreview) | describeContents(this.context);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
