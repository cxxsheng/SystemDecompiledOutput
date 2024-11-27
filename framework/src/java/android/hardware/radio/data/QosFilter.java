package android.hardware.radio.data;

/* loaded from: classes2.dex */
public class QosFilter implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.QosFilter> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.QosFilter>() { // from class: android.hardware.radio.data.QosFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.QosFilter createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.data.QosFilter qosFilter = new android.hardware.radio.data.QosFilter();
            qosFilter.readFromParcel(parcel);
            return qosFilter;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.QosFilter[] newArray(int i) {
            return new android.hardware.radio.data.QosFilter[i];
        }
    };
    public static final byte DIRECTION_BIDIRECTIONAL = 2;
    public static final byte DIRECTION_DOWNLINK = 0;
    public static final byte DIRECTION_UPLINK = 1;
    public static final byte PROTOCOL_AH = 51;
    public static final byte PROTOCOL_ESP = 50;
    public static final byte PROTOCOL_TCP = 6;
    public static final byte PROTOCOL_UDP = 17;
    public static final byte PROTOCOL_UNSPECIFIED = -1;
    public android.hardware.radio.data.QosFilterIpv6FlowLabel flowLabel;
    public java.lang.String[] localAddresses;
    public android.hardware.radio.data.PortRange localPort;
    public java.lang.String[] remoteAddresses;
    public android.hardware.radio.data.PortRange remotePort;
    public android.hardware.radio.data.QosFilterIpsecSpi spi;
    public android.hardware.radio.data.QosFilterTypeOfService tos;
    public byte protocol = 0;
    public byte direction = 0;
    public int precedence = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStringArray(this.localAddresses);
        parcel.writeStringArray(this.remoteAddresses);
        parcel.writeTypedObject(this.localPort, i);
        parcel.writeTypedObject(this.remotePort, i);
        parcel.writeByte(this.protocol);
        parcel.writeTypedObject(this.tos, i);
        parcel.writeTypedObject(this.flowLabel, i);
        parcel.writeTypedObject(this.spi, i);
        parcel.writeByte(this.direction);
        parcel.writeInt(this.precedence);
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
            this.localAddresses = parcel.createStringArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.remoteAddresses = parcel.createStringArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.localPort = (android.hardware.radio.data.PortRange) parcel.readTypedObject(android.hardware.radio.data.PortRange.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.remotePort = (android.hardware.radio.data.PortRange) parcel.readTypedObject(android.hardware.radio.data.PortRange.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.protocol = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.tos = (android.hardware.radio.data.QosFilterTypeOfService) parcel.readTypedObject(android.hardware.radio.data.QosFilterTypeOfService.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.flowLabel = (android.hardware.radio.data.QosFilterIpv6FlowLabel) parcel.readTypedObject(android.hardware.radio.data.QosFilterIpv6FlowLabel.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.spi = (android.hardware.radio.data.QosFilterIpsecSpi) parcel.readTypedObject(android.hardware.radio.data.QosFilterIpsecSpi.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.direction = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.precedence = parcel.readInt();
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
        stringJoiner.add("localAddresses: " + java.util.Arrays.toString(this.localAddresses));
        stringJoiner.add("remoteAddresses: " + java.util.Arrays.toString(this.remoteAddresses));
        stringJoiner.add("localPort: " + java.util.Objects.toString(this.localPort));
        stringJoiner.add("remotePort: " + java.util.Objects.toString(this.remotePort));
        stringJoiner.add("protocol: " + ((int) this.protocol));
        stringJoiner.add("tos: " + java.util.Objects.toString(this.tos));
        stringJoiner.add("flowLabel: " + java.util.Objects.toString(this.flowLabel));
        stringJoiner.add("spi: " + java.util.Objects.toString(this.spi));
        stringJoiner.add("direction: " + ((int) this.direction));
        stringJoiner.add("precedence: " + this.precedence);
        return "QosFilter" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.localPort) | 0 | describeContents(this.remotePort) | describeContents(this.tos) | describeContents(this.flowLabel) | describeContents(this.spi);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
