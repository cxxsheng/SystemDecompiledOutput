package android.media;

/* loaded from: classes2.dex */
public class GetSpatializerResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.GetSpatializerResponse> CREATOR = new android.os.Parcelable.Creator<android.media.GetSpatializerResponse>() { // from class: android.media.GetSpatializerResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.GetSpatializerResponse createFromParcel(android.os.Parcel parcel) {
            android.media.GetSpatializerResponse getSpatializerResponse = new android.media.GetSpatializerResponse();
            getSpatializerResponse.readFromParcel(parcel);
            return getSpatializerResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.GetSpatializerResponse[] newArray(int i) {
            return new android.media.GetSpatializerResponse[i];
        }
    };
    public android.media.ISpatializer spatializer;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStrongInterface(this.spatializer);
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
                this.spatializer = android.media.ISpatializer.Stub.asInterface(parcel.readStrongBinder());
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
