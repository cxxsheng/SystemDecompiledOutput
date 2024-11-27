package android.hardware.radio.data;

/* loaded from: classes2.dex */
public class SetupDataCallResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.SetupDataCallResult> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.SetupDataCallResult>() { // from class: android.hardware.radio.data.SetupDataCallResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.SetupDataCallResult createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.data.SetupDataCallResult setupDataCallResult = new android.hardware.radio.data.SetupDataCallResult();
            setupDataCallResult.readFromParcel(parcel);
            return setupDataCallResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.SetupDataCallResult[] newArray(int i) {
            return new android.hardware.radio.data.SetupDataCallResult[i];
        }
    };
    public static final int DATA_CONNECTION_STATUS_ACTIVE = 2;
    public static final int DATA_CONNECTION_STATUS_DORMANT = 1;
    public static final int DATA_CONNECTION_STATUS_INACTIVE = 0;
    public static final byte HANDOVER_FAILURE_MODE_DO_FALLBACK = 1;
    public static final byte HANDOVER_FAILURE_MODE_LEGACY = 0;
    public static final byte HANDOVER_FAILURE_MODE_NO_FALLBACK_RETRY_HANDOVER = 2;
    public static final byte HANDOVER_FAILURE_MODE_NO_FALLBACK_RETRY_SETUP_NORMAL = 3;
    public android.hardware.radio.data.LinkAddress[] addresses;
    public int cause;
    public android.hardware.radio.data.Qos defaultQos;
    public java.lang.String[] dnses;
    public java.lang.String[] gateways;
    public java.lang.String ifname;
    public java.lang.String[] pcscf;
    public android.hardware.radio.data.QosSession[] qosSessions;
    public android.hardware.radio.data.SliceInfo sliceInfo;
    public android.hardware.radio.data.TrafficDescriptor[] trafficDescriptors;
    public int type;
    public long suggestedRetryTime = 0;
    public int cid = 0;
    public int active = 0;
    public int mtuV4 = 0;
    public int mtuV6 = 0;
    public byte handoverFailureMode = 0;
    public int pduSessionId = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.cause);
        parcel.writeLong(this.suggestedRetryTime);
        parcel.writeInt(this.cid);
        parcel.writeInt(this.active);
        parcel.writeInt(this.type);
        parcel.writeString(this.ifname);
        parcel.writeTypedArray(this.addresses, i);
        parcel.writeStringArray(this.dnses);
        parcel.writeStringArray(this.gateways);
        parcel.writeStringArray(this.pcscf);
        parcel.writeInt(this.mtuV4);
        parcel.writeInt(this.mtuV6);
        parcel.writeTypedObject(this.defaultQos, i);
        parcel.writeTypedArray(this.qosSessions, i);
        parcel.writeByte(this.handoverFailureMode);
        parcel.writeInt(this.pduSessionId);
        parcel.writeTypedObject(this.sliceInfo, i);
        parcel.writeTypedArray(this.trafficDescriptors, i);
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
            this.cause = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.suggestedRetryTime = parcel.readLong();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.cid = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.active = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.type = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.ifname = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.addresses = (android.hardware.radio.data.LinkAddress[]) parcel.createTypedArray(android.hardware.radio.data.LinkAddress.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.dnses = parcel.createStringArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.gateways = parcel.createStringArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.pcscf = parcel.createStringArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.mtuV4 = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.mtuV6 = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.defaultQos = (android.hardware.radio.data.Qos) parcel.readTypedObject(android.hardware.radio.data.Qos.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.qosSessions = (android.hardware.radio.data.QosSession[]) parcel.createTypedArray(android.hardware.radio.data.QosSession.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.handoverFailureMode = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.pduSessionId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sliceInfo = (android.hardware.radio.data.SliceInfo) parcel.readTypedObject(android.hardware.radio.data.SliceInfo.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.trafficDescriptors = (android.hardware.radio.data.TrafficDescriptor[]) parcel.createTypedArray(android.hardware.radio.data.TrafficDescriptor.CREATOR);
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
        stringJoiner.add("cause: " + android.hardware.radio.data.DataCallFailCause$$.toString(this.cause));
        stringJoiner.add("suggestedRetryTime: " + this.suggestedRetryTime);
        stringJoiner.add("cid: " + this.cid);
        stringJoiner.add("active: " + this.active);
        stringJoiner.add("type: " + android.hardware.radio.data.PdpProtocolType$$.toString(this.type));
        stringJoiner.add("ifname: " + java.util.Objects.toString(this.ifname));
        stringJoiner.add("addresses: " + java.util.Arrays.toString(this.addresses));
        stringJoiner.add("dnses: " + java.util.Arrays.toString(this.dnses));
        stringJoiner.add("gateways: " + java.util.Arrays.toString(this.gateways));
        stringJoiner.add("pcscf: " + java.util.Arrays.toString(this.pcscf));
        stringJoiner.add("mtuV4: " + this.mtuV4);
        stringJoiner.add("mtuV6: " + this.mtuV6);
        stringJoiner.add("defaultQos: " + java.util.Objects.toString(this.defaultQos));
        stringJoiner.add("qosSessions: " + java.util.Arrays.toString(this.qosSessions));
        stringJoiner.add("handoverFailureMode: " + ((int) this.handoverFailureMode));
        stringJoiner.add("pduSessionId: " + this.pduSessionId);
        stringJoiner.add("sliceInfo: " + java.util.Objects.toString(this.sliceInfo));
        stringJoiner.add("trafficDescriptors: " + java.util.Arrays.toString(this.trafficDescriptors));
        return "SetupDataCallResult" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.addresses) | 0 | describeContents(this.defaultQos) | describeContents(this.qosSessions) | describeContents(this.sliceInfo) | describeContents(this.trafficDescriptors);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
