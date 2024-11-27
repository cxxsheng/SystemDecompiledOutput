package android.hardware.radio.network;

/* loaded from: classes2.dex */
public class EutranRegistrationInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.EutranRegistrationInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.EutranRegistrationInfo>() { // from class: android.hardware.radio.network.EutranRegistrationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.EutranRegistrationInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.network.EutranRegistrationInfo eutranRegistrationInfo = new android.hardware.radio.network.EutranRegistrationInfo();
            eutranRegistrationInfo.readFromParcel(parcel);
            return eutranRegistrationInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.EutranRegistrationInfo[] newArray(int i) {
            return new android.hardware.radio.network.EutranRegistrationInfo[i];
        }
    };
    public static final int EXTRA_CSFB_NOT_PREFERRED = 1;
    public static final int EXTRA_SMS_ONLY = 2;
    public int extraInfo = 0;
    public byte lteAttachResultType;
    public android.hardware.radio.network.LteVopsInfo lteVopsInfo;
    public android.hardware.radio.network.NrIndicators nrIndicators;

    public @interface AttachResultType {
        public static final byte COMBINED = 2;
        public static final byte EPS_ONLY = 1;
        public static final byte NONE = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.lteVopsInfo, i);
        parcel.writeTypedObject(this.nrIndicators, i);
        parcel.writeByte(this.lteAttachResultType);
        parcel.writeInt(this.extraInfo);
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
            this.lteVopsInfo = (android.hardware.radio.network.LteVopsInfo) parcel.readTypedObject(android.hardware.radio.network.LteVopsInfo.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.nrIndicators = (android.hardware.radio.network.NrIndicators) parcel.readTypedObject(android.hardware.radio.network.NrIndicators.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.lteAttachResultType = parcel.readByte();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.extraInfo = parcel.readInt();
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
        stringJoiner.add("lteVopsInfo: " + java.util.Objects.toString(this.lteVopsInfo));
        stringJoiner.add("nrIndicators: " + java.util.Objects.toString(this.nrIndicators));
        stringJoiner.add("lteAttachResultType: " + ((int) this.lteAttachResultType));
        stringJoiner.add("extraInfo: " + this.extraInfo);
        return "EutranRegistrationInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.lteVopsInfo) | 0 | describeContents(this.nrIndicators);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
