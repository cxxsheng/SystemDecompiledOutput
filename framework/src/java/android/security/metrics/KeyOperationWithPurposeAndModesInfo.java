package android.security.metrics;

/* loaded from: classes3.dex */
public class KeyOperationWithPurposeAndModesInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.metrics.KeyOperationWithPurposeAndModesInfo> CREATOR = new android.os.Parcelable.Creator<android.security.metrics.KeyOperationWithPurposeAndModesInfo>() { // from class: android.security.metrics.KeyOperationWithPurposeAndModesInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.metrics.KeyOperationWithPurposeAndModesInfo createFromParcel(android.os.Parcel parcel) {
            android.security.metrics.KeyOperationWithPurposeAndModesInfo keyOperationWithPurposeAndModesInfo = new android.security.metrics.KeyOperationWithPurposeAndModesInfo();
            keyOperationWithPurposeAndModesInfo.readFromParcel(parcel);
            return keyOperationWithPurposeAndModesInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.metrics.KeyOperationWithPurposeAndModesInfo[] newArray(int i) {
            return new android.security.metrics.KeyOperationWithPurposeAndModesInfo[i];
        }
    };
    public int purpose;
    public int padding_mode_bitmap = 0;
    public int digest_bitmap = 0;
    public int block_mode_bitmap = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.purpose);
        parcel.writeInt(this.padding_mode_bitmap);
        parcel.writeInt(this.digest_bitmap);
        parcel.writeInt(this.block_mode_bitmap);
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
            this.purpose = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.padding_mode_bitmap = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.digest_bitmap = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.block_mode_bitmap = parcel.readInt();
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
