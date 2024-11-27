package android.frameworks.location.altitude;

/* loaded from: classes.dex */
public class GetGeoidHeightResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.frameworks.location.altitude.GetGeoidHeightResponse> CREATOR = new android.os.Parcelable.Creator<android.frameworks.location.altitude.GetGeoidHeightResponse>() { // from class: android.frameworks.location.altitude.GetGeoidHeightResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.frameworks.location.altitude.GetGeoidHeightResponse createFromParcel(android.os.Parcel parcel) {
            android.frameworks.location.altitude.GetGeoidHeightResponse getGeoidHeightResponse = new android.frameworks.location.altitude.GetGeoidHeightResponse();
            getGeoidHeightResponse.readFromParcel(parcel);
            return getGeoidHeightResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.frameworks.location.altitude.GetGeoidHeightResponse[] newArray(int i) {
            return new android.frameworks.location.altitude.GetGeoidHeightResponse[i];
        }
    };
    public double geoidHeightMeters = 0.0d;
    public float geoidHeightErrorMeters = 0.0f;
    public double expirationDistanceMeters = 0.0d;
    public float additionalGeoidHeightErrorMeters = 0.0f;
    public boolean success = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeDouble(this.geoidHeightMeters);
        parcel.writeFloat(this.geoidHeightErrorMeters);
        parcel.writeDouble(this.expirationDistanceMeters);
        parcel.writeFloat(this.additionalGeoidHeightErrorMeters);
        parcel.writeBoolean(this.success);
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
            this.geoidHeightMeters = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.geoidHeightErrorMeters = parcel.readFloat();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.expirationDistanceMeters = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.additionalGeoidHeightErrorMeters = parcel.readFloat();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.success = parcel.readBoolean();
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
