package android.media.tv.tunerresourcemanager;

/* loaded from: classes2.dex */
public class TunerDescramblerRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.tunerresourcemanager.TunerDescramblerRequest> CREATOR = new android.os.Parcelable.Creator<android.media.tv.tunerresourcemanager.TunerDescramblerRequest>() { // from class: android.media.tv.tunerresourcemanager.TunerDescramblerRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.tunerresourcemanager.TunerDescramblerRequest createFromParcel(android.os.Parcel parcel) {
            android.media.tv.tunerresourcemanager.TunerDescramblerRequest tunerDescramblerRequest = new android.media.tv.tunerresourcemanager.TunerDescramblerRequest();
            tunerDescramblerRequest.readFromParcel(parcel);
            return tunerDescramblerRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.tunerresourcemanager.TunerDescramblerRequest[] newArray(int i) {
            return new android.media.tv.tunerresourcemanager.TunerDescramblerRequest[i];
        }
    };
    public int clientId = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.clientId);
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
                this.clientId = parcel.readInt();
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
