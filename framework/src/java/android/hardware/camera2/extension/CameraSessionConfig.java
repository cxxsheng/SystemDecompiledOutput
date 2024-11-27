package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public class CameraSessionConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.camera2.extension.CameraSessionConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.extension.CameraSessionConfig>() { // from class: android.hardware.camera2.extension.CameraSessionConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.extension.CameraSessionConfig createFromParcel(android.os.Parcel parcel) {
            android.hardware.camera2.extension.CameraSessionConfig cameraSessionConfig = new android.hardware.camera2.extension.CameraSessionConfig();
            cameraSessionConfig.readFromParcel(parcel);
            return cameraSessionConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.extension.CameraSessionConfig[] newArray(int i) {
            return new android.hardware.camera2.extension.CameraSessionConfig[i];
        }
    };
    public java.util.List<android.hardware.camera2.extension.CameraOutputConfig> outputConfigs;
    public android.hardware.camera2.impl.CameraMetadataNative sessionParameter;
    public int sessionTemplateId = 0;
    public int sessionType = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedList(this.outputConfigs, i);
        parcel.writeTypedObject(this.sessionParameter, i);
        parcel.writeInt(this.sessionTemplateId);
        parcel.writeInt(this.sessionType);
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
            this.outputConfigs = parcel.createTypedArrayList(android.hardware.camera2.extension.CameraOutputConfig.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sessionParameter = (android.hardware.camera2.impl.CameraMetadataNative) parcel.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sessionTemplateId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.sessionType = parcel.readInt();
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
        return describeContents(this.outputConfigs) | 0 | describeContents(this.sessionParameter);
    }

    private int describeContents(java.lang.Object obj) {
        int i = 0;
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.util.Collection) {
            java.util.Iterator it = ((java.util.Collection) obj).iterator();
            while (it.hasNext()) {
                i |= describeContents(it.next());
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
