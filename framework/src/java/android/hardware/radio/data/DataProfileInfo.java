package android.hardware.radio.data;

/* loaded from: classes2.dex */
public class DataProfileInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.DataProfileInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.DataProfileInfo>() { // from class: android.hardware.radio.data.DataProfileInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.DataProfileInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.data.DataProfileInfo dataProfileInfo = new android.hardware.radio.data.DataProfileInfo();
            dataProfileInfo.readFromParcel(parcel);
            return dataProfileInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.DataProfileInfo[] newArray(int i) {
            return new android.hardware.radio.data.DataProfileInfo[i];
        }
    };
    public static final int ID_CBS = 4;
    public static final int ID_DEFAULT = 0;
    public static final int ID_FOTA = 3;
    public static final int ID_IMS = 2;
    public static final int ID_INVALID = -1;
    public static final int ID_OEM_BASE = 1000;
    public static final int ID_TETHERED = 1;
    public static final int INFRASTRUCTURE_CELLULAR = 1;
    public static final int INFRASTRUCTURE_SATELLITE = 2;
    public static final int INFRASTRUCTURE_UNKNOWN = 0;
    public static final int TYPE_3GPP = 1;
    public static final int TYPE_3GPP2 = 2;
    public static final int TYPE_COMMON = 0;
    public java.lang.String apn;
    public int authType;
    public java.lang.String password;
    public int protocol;
    public int roamingProtocol;
    public android.hardware.radio.data.TrafficDescriptor trafficDescriptor;
    public java.lang.String user;
    public int profileId = 0;
    public int type = 0;
    public int maxConnsTime = 0;
    public int maxConns = 0;
    public int waitTime = 0;
    public boolean enabled = false;
    public int supportedApnTypesBitmap = 0;
    public int bearerBitmap = 0;
    public int mtuV4 = 0;
    public int mtuV6 = 0;
    public boolean preferred = false;
    public boolean persistent = false;
    public boolean alwaysOn = false;
    public int infrastructureBitmap = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.profileId);
        parcel.writeString(this.apn);
        parcel.writeInt(this.protocol);
        parcel.writeInt(this.roamingProtocol);
        parcel.writeInt(this.authType);
        parcel.writeString(this.user);
        parcel.writeString(this.password);
        parcel.writeInt(this.type);
        parcel.writeInt(this.maxConnsTime);
        parcel.writeInt(this.maxConns);
        parcel.writeInt(this.waitTime);
        parcel.writeBoolean(this.enabled);
        parcel.writeInt(this.supportedApnTypesBitmap);
        parcel.writeInt(this.bearerBitmap);
        parcel.writeInt(this.mtuV4);
        parcel.writeInt(this.mtuV6);
        parcel.writeBoolean(this.preferred);
        parcel.writeBoolean(this.persistent);
        parcel.writeBoolean(this.alwaysOn);
        parcel.writeTypedObject(this.trafficDescriptor, i);
        parcel.writeInt(this.infrastructureBitmap);
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
            this.profileId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.apn = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.protocol = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.roamingProtocol = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.authType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.user = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.password = parcel.readString();
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
            this.maxConnsTime = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.maxConns = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.waitTime = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.enabled = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.supportedApnTypesBitmap = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.bearerBitmap = parcel.readInt();
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
            this.preferred = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.persistent = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.alwaysOn = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.trafficDescriptor = (android.hardware.radio.data.TrafficDescriptor) parcel.readTypedObject(android.hardware.radio.data.TrafficDescriptor.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.infrastructureBitmap = parcel.readInt();
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
        stringJoiner.add("profileId: " + this.profileId);
        stringJoiner.add("apn: " + java.util.Objects.toString(this.apn));
        stringJoiner.add("protocol: " + android.hardware.radio.data.PdpProtocolType$$.toString(this.protocol));
        stringJoiner.add("roamingProtocol: " + android.hardware.radio.data.PdpProtocolType$$.toString(this.roamingProtocol));
        stringJoiner.add("authType: " + android.hardware.radio.data.ApnAuthType$$.toString(this.authType));
        stringJoiner.add("user: " + java.util.Objects.toString(this.user));
        stringJoiner.add("password: " + java.util.Objects.toString(this.password));
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("maxConnsTime: " + this.maxConnsTime);
        stringJoiner.add("maxConns: " + this.maxConns);
        stringJoiner.add("waitTime: " + this.waitTime);
        stringJoiner.add("enabled: " + this.enabled);
        stringJoiner.add("supportedApnTypesBitmap: " + this.supportedApnTypesBitmap);
        stringJoiner.add("bearerBitmap: " + this.bearerBitmap);
        stringJoiner.add("mtuV4: " + this.mtuV4);
        stringJoiner.add("mtuV6: " + this.mtuV6);
        stringJoiner.add("preferred: " + this.preferred);
        stringJoiner.add("persistent: " + this.persistent);
        stringJoiner.add("alwaysOn: " + this.alwaysOn);
        stringJoiner.add("trafficDescriptor: " + java.util.Objects.toString(this.trafficDescriptor));
        stringJoiner.add("infrastructureBitmap: " + this.infrastructureBitmap);
        return "DataProfileInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.trafficDescriptor) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
