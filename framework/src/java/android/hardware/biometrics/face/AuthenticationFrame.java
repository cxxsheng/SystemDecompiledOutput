package android.hardware.biometrics.face;

/* loaded from: classes.dex */
public class AuthenticationFrame implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.biometrics.face.AuthenticationFrame> CREATOR = new android.os.Parcelable.Creator<android.hardware.biometrics.face.AuthenticationFrame>() { // from class: android.hardware.biometrics.face.AuthenticationFrame.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.face.AuthenticationFrame createFromParcel(android.os.Parcel parcel) {
            android.hardware.biometrics.face.AuthenticationFrame authenticationFrame = new android.hardware.biometrics.face.AuthenticationFrame();
            authenticationFrame.readFromParcel(parcel);
            return authenticationFrame;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.biometrics.face.AuthenticationFrame[] newArray(int i) {
            return new android.hardware.biometrics.face.AuthenticationFrame[i];
        }
    };
    public android.hardware.biometrics.face.BaseFrame data;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.data, i);
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
            } else {
                this.data = (android.hardware.biometrics.face.BaseFrame) parcel.readTypedObject(android.hardware.biometrics.face.BaseFrame.CREATOR);
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
        return describeContents(this.data) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
