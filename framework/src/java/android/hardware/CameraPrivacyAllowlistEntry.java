package android.hardware;

/* loaded from: classes.dex */
public class CameraPrivacyAllowlistEntry implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.CameraPrivacyAllowlistEntry> CREATOR = new android.os.Parcelable.Creator<android.hardware.CameraPrivacyAllowlistEntry>() { // from class: android.hardware.CameraPrivacyAllowlistEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.CameraPrivacyAllowlistEntry createFromParcel(android.os.Parcel parcel) {
            android.hardware.CameraPrivacyAllowlistEntry cameraPrivacyAllowlistEntry = new android.hardware.CameraPrivacyAllowlistEntry();
            cameraPrivacyAllowlistEntry.readFromParcel(parcel);
            return cameraPrivacyAllowlistEntry;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.CameraPrivacyAllowlistEntry[] newArray(int i) {
            return new android.hardware.CameraPrivacyAllowlistEntry[i];
        }
    };
    public boolean isMandatory = false;
    public java.lang.String packageName;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.packageName);
        parcel.writeBoolean(this.isMandatory);
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
            } else {
                this.isMandatory = parcel.readBoolean();
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
