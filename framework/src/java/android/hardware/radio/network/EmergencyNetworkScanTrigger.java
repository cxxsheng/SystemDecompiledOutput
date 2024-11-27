package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class EmergencyNetworkScanTrigger implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.EmergencyNetworkScanTrigger> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.EmergencyNetworkScanTrigger>() { // from class: android.hardware.radio.network.EmergencyNetworkScanTrigger.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.EmergencyNetworkScanTrigger createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.EmergencyNetworkScanTrigger emergencyNetworkScanTrigger = new android.hardware.radio.network.EmergencyNetworkScanTrigger();
            emergencyNetworkScanTrigger.readFromParcel(parcel);
            return emergencyNetworkScanTrigger;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.EmergencyNetworkScanTrigger[] newArray(int i) {
            return new android.hardware.radio.network.EmergencyNetworkScanTrigger[i];
        }
    };
    public int[] accessNetwork;
    public int scanType;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeIntArray(this.accessNetwork);
        parcel.writeInt(this.scanType);
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
            this.accessNetwork = parcel.createIntArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.scanType = parcel.readInt();
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

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("accessNetwork: " + android.hardware.radio.AccessNetwork$$.arrayToString(this.accessNetwork));
        stringJoiner.add("scanType: " + android.hardware.radio.network.EmergencyScanType$$.toString(this.scanType));
        return "EmergencyNetworkScanTrigger" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
