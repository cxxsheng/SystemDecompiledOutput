package android.frameworks.location.altitude;

/* loaded from: classes.dex */
public class GetGeoidHeightRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.frameworks.location.altitude.GetGeoidHeightRequest> CREATOR = new android.os.Parcelable.Creator<android.frameworks.location.altitude.GetGeoidHeightRequest>() { // from class: android.frameworks.location.altitude.GetGeoidHeightRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.frameworks.location.altitude.GetGeoidHeightRequest createFromParcel(android.os.Parcel parcel) {
            android.frameworks.location.altitude.GetGeoidHeightRequest getGeoidHeightRequest = new android.frameworks.location.altitude.GetGeoidHeightRequest();
            getGeoidHeightRequest.readFromParcel(parcel);
            return getGeoidHeightRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.frameworks.location.altitude.GetGeoidHeightRequest[] newArray(int i) {
            return new android.frameworks.location.altitude.GetGeoidHeightRequest[i];
        }
    };
    public double latitudeDegrees = 0.0d;
    public double longitudeDegrees = 0.0d;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeDouble(this.latitudeDegrees);
        parcel.writeDouble(this.longitudeDegrees);
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
            this.latitudeDegrees = parcel.readDouble();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.longitudeDegrees = parcel.readDouble();
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
