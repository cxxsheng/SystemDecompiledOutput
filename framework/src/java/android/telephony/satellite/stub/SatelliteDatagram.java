package android.telephony.satellite.stub;

/* loaded from: classes3.dex */
public class SatelliteDatagram implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.satellite.stub.SatelliteDatagram> CREATOR = new android.os.Parcelable.Creator<android.telephony.satellite.stub.SatelliteDatagram>() { // from class: android.telephony.satellite.stub.SatelliteDatagram.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.stub.SatelliteDatagram createFromParcel(android.os.Parcel parcel) {
            android.telephony.satellite.stub.SatelliteDatagram satelliteDatagram = new android.telephony.satellite.stub.SatelliteDatagram();
            satelliteDatagram.readFromParcel(parcel);
            return satelliteDatagram;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.stub.SatelliteDatagram[] newArray(int i) {
            return new android.telephony.satellite.stub.SatelliteDatagram[i];
        }
    };
    public byte[] data;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.data);
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
                this.data = parcel.createByteArray();
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