package android.security.authorization;

/* loaded from: classes3.dex */
public class AuthorizationTokens implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.authorization.AuthorizationTokens> CREATOR = new android.os.Parcelable.Creator<android.security.authorization.AuthorizationTokens>() { // from class: android.security.authorization.AuthorizationTokens.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.authorization.AuthorizationTokens createFromParcel(android.os.Parcel parcel) {
            android.security.authorization.AuthorizationTokens authorizationTokens = new android.security.authorization.AuthorizationTokens();
            authorizationTokens.readFromParcel(parcel);
            return authorizationTokens;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.authorization.AuthorizationTokens[] newArray(int i) {
            return new android.security.authorization.AuthorizationTokens[i];
        }
    };
    public android.hardware.security.keymint.HardwareAuthToken authToken;
    public android.hardware.security.secureclock.TimeStampToken timestampToken;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.authToken, i);
        parcel.writeTypedObject(this.timestampToken, i);
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
            this.authToken = (android.hardware.security.keymint.HardwareAuthToken) parcel.readTypedObject(android.hardware.security.keymint.HardwareAuthToken.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.timestampToken = (android.hardware.security.secureclock.TimeStampToken) parcel.readTypedObject(android.hardware.security.secureclock.TimeStampToken.CREATOR);
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
        return describeContents(this.authToken) | 0 | describeContents(this.timestampToken);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
