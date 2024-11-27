package android.hardware;

/* loaded from: classes.dex */
public class CameraIdRemapping implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.CameraIdRemapping> CREATOR = new android.os.Parcelable.Creator<android.hardware.CameraIdRemapping>() { // from class: android.hardware.CameraIdRemapping.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.CameraIdRemapping createFromParcel(android.os.Parcel parcel) {
            android.hardware.CameraIdRemapping cameraIdRemapping = new android.hardware.CameraIdRemapping();
            cameraIdRemapping.readFromParcel(parcel);
            return cameraIdRemapping;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.CameraIdRemapping[] newArray(int i) {
            return new android.hardware.CameraIdRemapping[i];
        }
    };
    public java.util.List<android.hardware.CameraIdRemapping.PackageIdRemapping> packageIdRemappings;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedList(this.packageIdRemappings, i);
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
            } else {
                this.packageIdRemappings = parcel.createTypedArrayList(android.hardware.CameraIdRemapping.PackageIdRemapping.CREATOR);
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
        return describeContents(this.packageIdRemappings) | 0;
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

    public static class PackageIdRemapping implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.CameraIdRemapping.PackageIdRemapping> CREATOR = new android.os.Parcelable.Creator<android.hardware.CameraIdRemapping.PackageIdRemapping>() { // from class: android.hardware.CameraIdRemapping.PackageIdRemapping.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.CameraIdRemapping.PackageIdRemapping createFromParcel(android.os.Parcel parcel) {
                android.hardware.CameraIdRemapping.PackageIdRemapping packageIdRemapping = new android.hardware.CameraIdRemapping.PackageIdRemapping();
                packageIdRemapping.readFromParcel(parcel);
                return packageIdRemapping;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.CameraIdRemapping.PackageIdRemapping[] newArray(int i) {
                return new android.hardware.CameraIdRemapping.PackageIdRemapping[i];
            }
        };
        public java.util.List<java.lang.String> cameraIdsToReplace;
        public java.lang.String packageName;
        public java.util.List<java.lang.String> updatedCameraIds;

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.packageName);
            parcel.writeStringList(this.cameraIdsToReplace);
            parcel.writeStringList(this.updatedCameraIds);
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
                this.packageName = parcel.readString();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.cameraIdsToReplace = parcel.createStringArrayList();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.updatedCameraIds = parcel.createStringArrayList();
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
            return 0;
        }
    }
}
