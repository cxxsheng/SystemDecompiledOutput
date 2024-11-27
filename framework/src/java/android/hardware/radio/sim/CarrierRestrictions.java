package android.hardware.radio.sim;

/* loaded from: classes2.dex */
public class CarrierRestrictions implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.sim.CarrierRestrictions> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.sim.CarrierRestrictions>() { // from class: android.hardware.radio.sim.CarrierRestrictions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.sim.CarrierRestrictions createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.sim.CarrierRestrictions carrierRestrictions = new android.hardware.radio.sim.CarrierRestrictions();
            carrierRestrictions.readFromParcel(parcel);
            return carrierRestrictions;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.sim.CarrierRestrictions[] newArray(int i) {
            return new android.hardware.radio.sim.CarrierRestrictions[i];
        }
    };

    @java.lang.Deprecated
    public android.hardware.radio.sim.Carrier[] allowedCarriers;

    @java.lang.Deprecated
    public android.hardware.radio.sim.Carrier[] excludedCarriers;
    public int status;
    public boolean allowedCarriersPrioritized = false;
    public android.hardware.radio.sim.CarrierInfo[] allowedCarrierInfoList = new android.hardware.radio.sim.CarrierInfo[0];
    public android.hardware.radio.sim.CarrierInfo[] excludedCarrierInfoList = new android.hardware.radio.sim.CarrierInfo[0];

    public @interface CarrierRestrictionStatus {
        public static final int NOT_RESTRICTED = 1;
        public static final int RESTRICTED = 2;
        public static final int UNKNOWN = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.allowedCarriers, i);
        parcel.writeTypedArray(this.excludedCarriers, i);
        parcel.writeBoolean(this.allowedCarriersPrioritized);
        parcel.writeInt(this.status);
        parcel.writeTypedArray(this.allowedCarrierInfoList, i);
        parcel.writeTypedArray(this.excludedCarrierInfoList, i);
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
            this.allowedCarriers = (android.hardware.radio.sim.Carrier[]) parcel.createTypedArray(android.hardware.radio.sim.Carrier.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.excludedCarriers = (android.hardware.radio.sim.Carrier[]) parcel.createTypedArray(android.hardware.radio.sim.Carrier.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.allowedCarriersPrioritized = parcel.readBoolean();
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
            this.allowedCarrierInfoList = (android.hardware.radio.sim.CarrierInfo[]) parcel.createTypedArray(android.hardware.radio.sim.CarrierInfo.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.excludedCarrierInfoList = (android.hardware.radio.sim.CarrierInfo[]) parcel.createTypedArray(android.hardware.radio.sim.CarrierInfo.CREATOR);
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
        stringJoiner.add("allowedCarriers: " + java.util.Arrays.toString(this.allowedCarriers));
        stringJoiner.add("excludedCarriers: " + java.util.Arrays.toString(this.excludedCarriers));
        stringJoiner.add("allowedCarriersPrioritized: " + this.allowedCarriersPrioritized);
        stringJoiner.add("status: " + this.status);
        stringJoiner.add("allowedCarrierInfoList: " + java.util.Arrays.toString(this.allowedCarrierInfoList));
        stringJoiner.add("excludedCarrierInfoList: " + java.util.Arrays.toString(this.excludedCarrierInfoList));
        return "CarrierRestrictions" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.allowedCarriers) | 0 | describeContents(this.excludedCarriers) | describeContents(this.allowedCarrierInfoList) | describeContents(this.excludedCarrierInfoList);
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
