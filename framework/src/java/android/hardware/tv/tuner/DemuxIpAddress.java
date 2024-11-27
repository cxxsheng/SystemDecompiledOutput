package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public class DemuxIpAddress implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxIpAddress> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxIpAddress>() { // from class: android.hardware.tv.tuner.DemuxIpAddress.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxIpAddress createFromParcel(android.os.Parcel parcel) {
            android.hardware.tv.tuner.DemuxIpAddress demuxIpAddress = new android.hardware.tv.tuner.DemuxIpAddress();
            demuxIpAddress.readFromParcel(parcel);
            return demuxIpAddress;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxIpAddress[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxIpAddress[i];
        }
    };
    public android.hardware.tv.tuner.DemuxIpAddressIpAddress dstIpAddress;
    public android.hardware.tv.tuner.DemuxIpAddressIpAddress srcIpAddress;
    public int srcPort = 0;
    public int dstPort = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.srcIpAddress, i);
        parcel.writeTypedObject(this.dstIpAddress, i);
        parcel.writeInt(this.srcPort);
        parcel.writeInt(this.dstPort);
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
            this.srcIpAddress = (android.hardware.tv.tuner.DemuxIpAddressIpAddress) parcel.readTypedObject(android.hardware.tv.tuner.DemuxIpAddressIpAddress.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.dstIpAddress = (android.hardware.tv.tuner.DemuxIpAddressIpAddress) parcel.readTypedObject(android.hardware.tv.tuner.DemuxIpAddressIpAddress.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.srcPort = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.dstPort = parcel.readInt();
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
        return describeContents(this.srcIpAddress) | 0 | describeContents(this.dstIpAddress);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
