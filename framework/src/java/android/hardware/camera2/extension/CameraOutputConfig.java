package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public class CameraOutputConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.camera2.extension.CameraOutputConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.extension.CameraOutputConfig>() { // from class: android.hardware.camera2.extension.CameraOutputConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.extension.CameraOutputConfig createFromParcel(android.os.Parcel parcel) {
            android.hardware.camera2.extension.CameraOutputConfig cameraOutputConfig = new android.hardware.camera2.extension.CameraOutputConfig();
            cameraOutputConfig.readFromParcel(parcel);
            return cameraOutputConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.extension.CameraOutputConfig[] newArray(int i) {
            return new android.hardware.camera2.extension.CameraOutputConfig[i];
        }
    };
    public static final int TYPE_IMAGEREADER = 1;
    public static final int TYPE_MULTIRES_IMAGEREADER = 2;
    public static final int TYPE_SURFACE = 0;
    public android.hardware.camera2.extension.OutputConfigId outputId;
    public java.lang.String physicalCameraId;
    public java.util.List<android.hardware.camera2.extension.CameraOutputConfig> sharedSurfaceConfigs;
    public android.hardware.camera2.extension.Size size;
    public android.view.Surface surface;
    public int imageFormat = 0;
    public int capacity = 0;
    public long usage = 0;
    public int type = 0;
    public int surfaceGroupId = 0;
    public boolean isMultiResolutionOutput = false;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.size, i);
        parcel.writeTypedObject(this.surface, i);
        parcel.writeInt(this.imageFormat);
        parcel.writeInt(this.capacity);
        parcel.writeLong(this.usage);
        parcel.writeInt(this.type);
        parcel.writeTypedObject(this.outputId, i);
        parcel.writeInt(this.surfaceGroupId);
        parcel.writeString(this.physicalCameraId);
        parcel.writeTypedList(this.sharedSurfaceConfigs, i);
        parcel.writeBoolean(this.isMultiResolutionOutput);
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
            this.size = (android.hardware.camera2.extension.Size) parcel.readTypedObject(android.hardware.camera2.extension.Size.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.imageFormat = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.capacity = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.usage = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.type = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.outputId = (android.hardware.camera2.extension.OutputConfigId) parcel.readTypedObject(android.hardware.camera2.extension.OutputConfigId.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.surfaceGroupId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.physicalCameraId = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sharedSurfaceConfigs = parcel.createTypedArrayList(CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.isMultiResolutionOutput = parcel.readBoolean();
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
        return describeContents(this.size) | 0 | describeContents(this.surface) | describeContents(this.outputId) | describeContents(this.sharedSurfaceConfigs);
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
