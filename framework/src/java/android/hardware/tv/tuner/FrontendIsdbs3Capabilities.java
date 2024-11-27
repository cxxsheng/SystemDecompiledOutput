package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public class FrontendIsdbs3Capabilities implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendIsdbs3Capabilities> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendIsdbs3Capabilities>() { // from class: android.hardware.tv.tuner.FrontendIsdbs3Capabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendIsdbs3Capabilities createFromParcel(android.os.Parcel parcel) {
            android.hardware.tv.tuner.FrontendIsdbs3Capabilities frontendIsdbs3Capabilities = new android.hardware.tv.tuner.FrontendIsdbs3Capabilities();
            frontendIsdbs3Capabilities.readFromParcel(parcel);
            return frontendIsdbs3Capabilities;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendIsdbs3Capabilities[] newArray(int i) {
            return new android.hardware.tv.tuner.FrontendIsdbs3Capabilities[i];
        }
    };
    public int modulationCap = 0;
    public int coderateCap = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.modulationCap);
        parcel.writeInt(this.coderateCap);
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
            this.modulationCap = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.coderateCap = parcel.readInt();
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
