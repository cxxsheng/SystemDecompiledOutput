package android.telephony.satellite.stub;

/* loaded from: classes3.dex */
public class NtnSignalStrength implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.satellite.stub.NtnSignalStrength> CREATOR = new android.os.Parcelable.Creator<android.telephony.satellite.stub.NtnSignalStrength>() { // from class: android.telephony.satellite.stub.NtnSignalStrength.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.stub.NtnSignalStrength createFromParcel(android.os.Parcel parcel) {
            android.telephony.satellite.stub.NtnSignalStrength ntnSignalStrength = new android.telephony.satellite.stub.NtnSignalStrength();
            ntnSignalStrength.readFromParcel(parcel);
            return ntnSignalStrength;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.stub.NtnSignalStrength[] newArray(int i) {
            return new android.telephony.satellite.stub.NtnSignalStrength[i];
        }
    };
    public int signalStrengthLevel;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.signalStrengthLevel);
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
                this.signalStrengthLevel = parcel.readInt();
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
