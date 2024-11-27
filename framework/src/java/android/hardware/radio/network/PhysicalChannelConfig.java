package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class PhysicalChannelConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.PhysicalChannelConfig> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.PhysicalChannelConfig>() { // from class: android.hardware.radio.network.PhysicalChannelConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.PhysicalChannelConfig createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.PhysicalChannelConfig physicalChannelConfig = new android.hardware.radio.network.PhysicalChannelConfig();
            physicalChannelConfig.readFromParcel(parcel);
            return physicalChannelConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.PhysicalChannelConfig[] newArray(int i) {
            return new android.hardware.radio.network.PhysicalChannelConfig[i];
        }
    };
    public android.hardware.radio.network.PhysicalChannelConfigBand band;
    public int[] contextIds;
    public int rat;
    public int status;
    public int downlinkChannelNumber = 0;
    public int uplinkChannelNumber = 0;
    public int cellBandwidthDownlinkKhz = 0;
    public int cellBandwidthUplinkKhz = 0;
    public int physicalCellId = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.status);
        parcel.writeInt(this.rat);
        parcel.writeInt(this.downlinkChannelNumber);
        parcel.writeInt(this.uplinkChannelNumber);
        parcel.writeInt(this.cellBandwidthDownlinkKhz);
        parcel.writeInt(this.cellBandwidthUplinkKhz);
        parcel.writeIntArray(this.contextIds);
        parcel.writeInt(this.physicalCellId);
        parcel.writeTypedObject(this.band, i);
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
            this.status = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.rat = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.downlinkChannelNumber = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.uplinkChannelNumber = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.cellBandwidthDownlinkKhz = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.cellBandwidthUplinkKhz = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.contextIds = parcel.createIntArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.physicalCellId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.band = (android.hardware.radio.network.PhysicalChannelConfigBand) parcel.readTypedObject(android.hardware.radio.network.PhysicalChannelConfigBand.CREATOR);
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
        stringJoiner.add("status: " + android.hardware.radio.network.CellConnectionStatus$$.toString(this.status));
        stringJoiner.add("rat: " + android.hardware.radio.RadioTechnology$$.toString(this.rat));
        stringJoiner.add("downlinkChannelNumber: " + this.downlinkChannelNumber);
        stringJoiner.add("uplinkChannelNumber: " + this.uplinkChannelNumber);
        stringJoiner.add("cellBandwidthDownlinkKhz: " + this.cellBandwidthDownlinkKhz);
        stringJoiner.add("cellBandwidthUplinkKhz: " + this.cellBandwidthUplinkKhz);
        stringJoiner.add("contextIds: " + java.util.Arrays.toString(this.contextIds));
        stringJoiner.add("physicalCellId: " + this.physicalCellId);
        stringJoiner.add("band: " + java.util.Objects.toString(this.band));
        return "PhysicalChannelConfig" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.band) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
