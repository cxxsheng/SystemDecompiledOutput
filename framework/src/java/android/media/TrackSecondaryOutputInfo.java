package android.media;

/* loaded from: classes2.dex */
public class TrackSecondaryOutputInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.TrackSecondaryOutputInfo> CREATOR = new android.os.Parcelable.Creator<android.media.TrackSecondaryOutputInfo>() { // from class: android.media.TrackSecondaryOutputInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.TrackSecondaryOutputInfo createFromParcel(android.os.Parcel parcel) {
            android.media.TrackSecondaryOutputInfo trackSecondaryOutputInfo = new android.media.TrackSecondaryOutputInfo();
            trackSecondaryOutputInfo.readFromParcel(parcel);
            return trackSecondaryOutputInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.TrackSecondaryOutputInfo[] newArray(int i) {
            return new android.media.TrackSecondaryOutputInfo[i];
        }
    };
    public int portId = 0;
    public int[] secondaryOutputIds;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.portId);
        parcel.writeIntArray(this.secondaryOutputIds);
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
            this.portId = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.secondaryOutputIds = parcel.createIntArray();
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
