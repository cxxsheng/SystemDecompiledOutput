package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public class OutputConfigId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.camera2.extension.OutputConfigId> CREATOR = new android.os.Parcelable.Creator<android.hardware.camera2.extension.OutputConfigId>() { // from class: android.hardware.camera2.extension.OutputConfigId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.extension.OutputConfigId createFromParcel(android.os.Parcel parcel) {
            android.hardware.camera2.extension.OutputConfigId outputConfigId = new android.hardware.camera2.extension.OutputConfigId();
            outputConfigId.readFromParcel(parcel);
            return outputConfigId;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.camera2.extension.OutputConfigId[] newArray(int i) {
            return new android.hardware.camera2.extension.OutputConfigId[i];
        }
    };
    public int id = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
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
                this.id = parcel.readInt();
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
