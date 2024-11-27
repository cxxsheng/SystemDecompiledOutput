package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class LinkCapacityEstimate implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.LinkCapacityEstimate> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.LinkCapacityEstimate>() { // from class: android.hardware.radio.network.LinkCapacityEstimate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.LinkCapacityEstimate createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.LinkCapacityEstimate linkCapacityEstimate = new android.hardware.radio.network.LinkCapacityEstimate();
            linkCapacityEstimate.readFromParcel(parcel);
            return linkCapacityEstimate;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.LinkCapacityEstimate[] newArray(int i) {
            return new android.hardware.radio.network.LinkCapacityEstimate[i];
        }
    };
    public int downlinkCapacityKbps = 0;
    public int uplinkCapacityKbps = 0;
    public int secondaryDownlinkCapacityKbps = 0;
    public int secondaryUplinkCapacityKbps = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.downlinkCapacityKbps);
        parcel.writeInt(this.uplinkCapacityKbps);
        parcel.writeInt(this.secondaryDownlinkCapacityKbps);
        parcel.writeInt(this.secondaryUplinkCapacityKbps);
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
            this.downlinkCapacityKbps = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.uplinkCapacityKbps = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.secondaryDownlinkCapacityKbps = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.secondaryUplinkCapacityKbps = parcel.readInt();
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
        stringJoiner.add("downlinkCapacityKbps: " + this.downlinkCapacityKbps);
        stringJoiner.add("uplinkCapacityKbps: " + this.uplinkCapacityKbps);
        stringJoiner.add("secondaryDownlinkCapacityKbps: " + this.secondaryDownlinkCapacityKbps);
        stringJoiner.add("secondaryUplinkCapacityKbps: " + this.secondaryUplinkCapacityKbps);
        return "LinkCapacityEstimate" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
