package android.hardware.gnss;

/* loaded from: classes2.dex */
public class CorrelationVector implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.gnss.CorrelationVector> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.CorrelationVector>() { // from class: android.hardware.gnss.CorrelationVector.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.CorrelationVector createFromParcel(android.os.Parcel parcel) {
            android.hardware.gnss.CorrelationVector correlationVector = new android.hardware.gnss.CorrelationVector();
            correlationVector.readFromParcel(parcel);
            return correlationVector;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.CorrelationVector[] newArray(int i) {
            return new android.hardware.gnss.CorrelationVector[i];
        }
    };
    public int[] magnitude;
    public double frequencyOffsetMps = 0.0d;
    public double samplingWidthM = 0.0d;
    public double samplingStartM = 0.0d;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeDouble(this.frequencyOffsetMps);
        parcel.writeDouble(this.samplingWidthM);
        parcel.writeDouble(this.samplingStartM);
        parcel.writeIntArray(this.magnitude);
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
            this.frequencyOffsetMps = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.samplingWidthM = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.samplingStartM = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.magnitude = parcel.createIntArray();
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
