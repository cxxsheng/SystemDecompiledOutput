package android.security.identity;

/* loaded from: classes3.dex */
public class SecurityHardwareInfoParcel implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.identity.SecurityHardwareInfoParcel> CREATOR = new android.os.Parcelable.Creator<android.security.identity.SecurityHardwareInfoParcel>() { // from class: android.security.identity.SecurityHardwareInfoParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.identity.SecurityHardwareInfoParcel createFromParcel(android.os.Parcel parcel) {
            android.security.identity.SecurityHardwareInfoParcel securityHardwareInfoParcel = new android.security.identity.SecurityHardwareInfoParcel();
            securityHardwareInfoParcel.readFromParcel(parcel);
            return securityHardwareInfoParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.identity.SecurityHardwareInfoParcel[] newArray(int i) {
            return new android.security.identity.SecurityHardwareInfoParcel[i];
        }
    };
    public boolean directAccess = false;
    public java.lang.String[] supportedDocTypes;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.directAccess);
        parcel.writeStringArray(this.supportedDocTypes);
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
            this.directAccess = parcel.readBoolean();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.supportedDocTypes = parcel.createStringArray();
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
