package android.hardware.gnss;

/* loaded from: classes2.dex */
public class ElapsedRealtime implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.gnss.ElapsedRealtime> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.ElapsedRealtime>() { // from class: android.hardware.gnss.ElapsedRealtime.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.ElapsedRealtime createFromParcel(android.os.Parcel parcel) {
            android.hardware.gnss.ElapsedRealtime elapsedRealtime = new android.hardware.gnss.ElapsedRealtime();
            elapsedRealtime.readFromParcel(parcel);
            return elapsedRealtime;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.gnss.ElapsedRealtime[] newArray(int i) {
            return new android.hardware.gnss.ElapsedRealtime[i];
        }
    };
    public static final int HAS_TIMESTAMP_NS = 1;
    public static final int HAS_TIME_UNCERTAINTY_NS = 2;
    public int flags = 0;
    public long timestampNs = 0;
    public double timeUncertaintyNs = 0.0d;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.flags);
        parcel.writeLong(this.timestampNs);
        parcel.writeDouble(this.timeUncertaintyNs);
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
            this.flags = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.timestampNs = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.timeUncertaintyNs = parcel.readDouble();
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
