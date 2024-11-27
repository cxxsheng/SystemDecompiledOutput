package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class CellIdentityGsm implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.CellIdentityGsm> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.CellIdentityGsm>() { // from class: android.hardware.radio.network.CellIdentityGsm.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellIdentityGsm createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.CellIdentityGsm cellIdentityGsm = new android.hardware.radio.network.CellIdentityGsm();
            cellIdentityGsm.readFromParcel(parcel);
            return cellIdentityGsm;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellIdentityGsm[] newArray(int i) {
            return new android.hardware.radio.network.CellIdentityGsm[i];
        }
    };
    public java.lang.String[] additionalPlmns;
    public java.lang.String mcc;
    public java.lang.String mnc;
    public android.hardware.radio.network.OperatorInfo operatorNames;
    public int lac = 0;
    public int cid = 0;
    public int arfcn = 0;
    public byte bsic = 0;

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
        parcel.writeInt(this.lac);
        parcel.writeInt(this.cid);
        parcel.writeInt(this.arfcn);
        parcel.writeByte(this.bsic);
        parcel.writeTypedObject(this.operatorNames, i);
        parcel.writeStringArray(this.additionalPlmns);
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
            this.lac = parcel.readInt();
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
            this.arfcn = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.bsic = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.operatorNames = (android.hardware.radio.network.OperatorInfo) parcel.readTypedObject(android.hardware.radio.network.OperatorInfo.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.additionalPlmns = parcel.createStringArray();
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
        stringJoiner.add("lac: " + this.lac);
        stringJoiner.add("cid: " + this.cid);
        stringJoiner.add("arfcn: " + this.arfcn);
        stringJoiner.add("bsic: " + ((int) this.bsic));
        stringJoiner.add("operatorNames: " + java.util.Objects.toString(this.operatorNames));
        stringJoiner.add("additionalPlmns: " + java.util.Arrays.toString(this.additionalPlmns));
        return "CellIdentityGsm" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.operatorNames) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
