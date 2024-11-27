package android.hardware.radio.sim;

/* loaded from: classes2.dex */
public class CarrierInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.sim.CarrierInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.sim.CarrierInfo>() { // from class: android.hardware.radio.sim.CarrierInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.sim.CarrierInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.sim.CarrierInfo carrierInfo = new android.hardware.radio.sim.CarrierInfo();
            carrierInfo.readFromParcel(parcel);
            return carrierInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.sim.CarrierInfo[] newArray(int i) {
            return new android.hardware.radio.sim.CarrierInfo[i];
        }
    };
    public java.util.List<android.hardware.radio.sim.Plmn> ehplmn;
    public java.lang.String gid1;
    public java.lang.String gid2;
    public java.lang.String iccid;
    public java.lang.String impi;
    public java.lang.String imsiPrefix;
    public java.lang.String mcc;
    public java.lang.String mnc;
    public java.lang.String spn;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
        parcel.writeString(this.spn);
        parcel.writeString(this.gid1);
        parcel.writeString(this.gid2);
        parcel.writeString(this.imsiPrefix);
        parcel.writeTypedList(this.ehplmn, i);
        parcel.writeString(this.iccid);
        parcel.writeString(this.impi);
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
            this.mcc = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.mnc = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.spn = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.gid1 = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.gid2 = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.imsiPrefix = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.ehplmn = parcel.createTypedArrayList(android.hardware.radio.sim.Plmn.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.iccid = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.impi = parcel.readString();
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
        stringJoiner.add("mcc: " + java.util.Objects.toString(this.mcc));
        stringJoiner.add("mnc: " + java.util.Objects.toString(this.mnc));
        stringJoiner.add("spn: " + java.util.Objects.toString(this.spn));
        stringJoiner.add("gid1: " + java.util.Objects.toString(this.gid1));
        stringJoiner.add("gid2: " + java.util.Objects.toString(this.gid2));
        stringJoiner.add("imsiPrefix: " + java.util.Objects.toString(this.imsiPrefix));
        stringJoiner.add("ehplmn: " + java.util.Objects.toString(this.ehplmn));
        stringJoiner.add("iccid: " + java.util.Objects.toString(this.iccid));
        stringJoiner.add("impi: " + java.util.Objects.toString(this.impi));
        return "CarrierInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.ehplmn) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        int i = 0;
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.util.Collection) {
            java.util.Iterator it = ((java.util.Collection) obj).iterator();
            while (it.hasNext()) {
                i |= describeContents(it.next());
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
