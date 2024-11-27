package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class RegStateResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.RegStateResult> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.RegStateResult>() { // from class: android.hardware.radio.network.RegStateResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.RegStateResult createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.RegStateResult regStateResult = new android.hardware.radio.network.RegStateResult();
            regStateResult.readFromParcel(parcel);
            return regStateResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.RegStateResult[] newArray(int i) {
            return new android.hardware.radio.network.RegStateResult[i];
        }
    };
    public android.hardware.radio.network.AccessTechnologySpecificInfo accessTechnologySpecificInfo;
    public android.hardware.radio.network.CellIdentity cellIdentity;
    public int rat;
    public int reasonForDenial;
    public int regState;
    public java.lang.String registeredPlmn;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.regState);
        parcel.writeInt(this.rat);
        parcel.writeInt(this.reasonForDenial);
        parcel.writeTypedObject(this.cellIdentity, i);
        parcel.writeString(this.registeredPlmn);
        parcel.writeTypedObject(this.accessTechnologySpecificInfo, i);
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
            this.regState = parcel.readInt();
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
            this.reasonForDenial = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.cellIdentity = (android.hardware.radio.network.CellIdentity) parcel.readTypedObject(android.hardware.radio.network.CellIdentity.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.registeredPlmn = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.accessTechnologySpecificInfo = (android.hardware.radio.network.AccessTechnologySpecificInfo) parcel.readTypedObject(android.hardware.radio.network.AccessTechnologySpecificInfo.CREATOR);
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
        stringJoiner.add("regState: " + android.hardware.radio.network.RegState$$.toString(this.regState));
        stringJoiner.add("rat: " + android.hardware.radio.RadioTechnology$$.toString(this.rat));
        stringJoiner.add("reasonForDenial: " + android.hardware.radio.network.RegistrationFailCause$$.toString(this.reasonForDenial));
        stringJoiner.add("cellIdentity: " + java.util.Objects.toString(this.cellIdentity));
        stringJoiner.add("registeredPlmn: " + java.util.Objects.toString(this.registeredPlmn));
        stringJoiner.add("accessTechnologySpecificInfo: " + java.util.Objects.toString(this.accessTechnologySpecificInfo));
        return "RegStateResult" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.cellIdentity) | 0 | describeContents(this.accessTechnologySpecificInfo);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
