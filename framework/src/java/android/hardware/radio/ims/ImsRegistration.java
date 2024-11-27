package android.hardware.radio.ims;

/* loaded from: classes2.dex */
public class ImsRegistration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.ims.ImsRegistration> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.ims.ImsRegistration>() { // from class: android.hardware.radio.ims.ImsRegistration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.ims.ImsRegistration createFromParcel(android.os.Parcel parcel) {
            android.hardware.radio.ims.ImsRegistration imsRegistration = new android.hardware.radio.ims.ImsRegistration();
            imsRegistration.readFromParcel(parcel);
            return imsRegistration;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.ims.ImsRegistration[] newArray(int i) {
            return new android.hardware.radio.ims.ImsRegistration[i];
        }
    };
    public static final int IMS_MMTEL_CAPABILITY_NONE = 0;
    public static final int IMS_MMTEL_CAPABILITY_SMS = 4;
    public static final int IMS_MMTEL_CAPABILITY_VIDEO = 2;
    public static final int IMS_MMTEL_CAPABILITY_VOICE = 1;
    public static final int IMS_RCS_CAPABILITIES = 8;
    public int accessNetworkType;
    public int capabilities = 0;
    public int regState;
    public int suggestedAction;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.regState);
        parcel.writeInt(this.accessNetworkType);
        parcel.writeInt(this.suggestedAction);
        parcel.writeInt(this.capabilities);
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
            this.accessNetworkType = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.suggestedAction = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.capabilities = parcel.readInt();
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
        stringJoiner.add("regState: " + android.hardware.radio.ims.ImsRegistrationState$$.toString(this.regState));
        stringJoiner.add("accessNetworkType: " + android.hardware.radio.AccessNetwork$$.toString(this.accessNetworkType));
        stringJoiner.add("suggestedAction: " + android.hardware.radio.ims.SuggestedAction$$.toString(this.suggestedAction));
        stringJoiner.add("capabilities: " + this.capabilities);
        return "ImsRegistration" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
