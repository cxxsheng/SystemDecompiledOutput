package android.telephony.satellite;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class SatelliteDatagram implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.satellite.SatelliteDatagram> CREATOR = new android.os.Parcelable.Creator<android.telephony.satellite.SatelliteDatagram>() { // from class: android.telephony.satellite.SatelliteDatagram.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.SatelliteDatagram createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.satellite.SatelliteDatagram(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.satellite.SatelliteDatagram[] newArray(int i) {
            return new android.telephony.satellite.SatelliteDatagram[i];
        }
    };
    private byte[] mData;

    public SatelliteDatagram(byte[] bArr) {
        this.mData = bArr;
    }

    private SatelliteDatagram(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByteArray(this.mData);
    }

    public byte[] getSatelliteDatagram() {
        return this.mData;
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mData = parcel.createByteArray();
    }
}
