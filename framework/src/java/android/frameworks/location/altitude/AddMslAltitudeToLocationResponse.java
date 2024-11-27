package android.frameworks.location.altitude;

/* loaded from: classes.dex */
public class AddMslAltitudeToLocationResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.frameworks.location.altitude.AddMslAltitudeToLocationResponse> CREATOR = new android.os.Parcelable.Creator<android.frameworks.location.altitude.AddMslAltitudeToLocationResponse>() { // from class: android.frameworks.location.altitude.AddMslAltitudeToLocationResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.frameworks.location.altitude.AddMslAltitudeToLocationResponse createFromParcel(android.os.Parcel parcel) {
            android.frameworks.location.altitude.AddMslAltitudeToLocationResponse addMslAltitudeToLocationResponse = new android.frameworks.location.altitude.AddMslAltitudeToLocationResponse();
            addMslAltitudeToLocationResponse.readFromParcel(parcel);
            return addMslAltitudeToLocationResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.frameworks.location.altitude.AddMslAltitudeToLocationResponse[] newArray(int i) {
            return new android.frameworks.location.altitude.AddMslAltitudeToLocationResponse[i];
        }
    };
    public double mslAltitudeMeters = 0.0d;
    public float mslAltitudeAccuracyMeters = 0.0f;
    public boolean success = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeDouble(this.mslAltitudeMeters);
        parcel.writeFloat(this.mslAltitudeAccuracyMeters);
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
            this.mslAltitudeMeters = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.mslAltitudeAccuracyMeters = parcel.readFloat();
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
