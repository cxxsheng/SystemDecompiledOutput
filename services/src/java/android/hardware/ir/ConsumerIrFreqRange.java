package android.hardware.ir;

/* loaded from: classes.dex */
public class ConsumerIrFreqRange implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.ir.ConsumerIrFreqRange> CREATOR = new android.os.Parcelable.Creator<android.hardware.ir.ConsumerIrFreqRange>() { // from class: android.hardware.ir.ConsumerIrFreqRange.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.ir.ConsumerIrFreqRange createFromParcel(android.os.Parcel parcel) {
            android.hardware.ir.ConsumerIrFreqRange consumerIrFreqRange = new android.hardware.ir.ConsumerIrFreqRange();
            consumerIrFreqRange.readFromParcel(parcel);
            return consumerIrFreqRange;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.ir.ConsumerIrFreqRange[] newArray(int i) {
            return new android.hardware.ir.ConsumerIrFreqRange[i];
        }
    };
    public int minHz = 0;
    public int maxHz = 0;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.minHz);
        parcel.writeInt(this.maxHz);
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
            this.minHz = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.maxHz = parcel.readInt();
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
