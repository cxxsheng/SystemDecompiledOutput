package android.hardware.security.authgraph;

/* loaded from: classes.dex */
public class SessionIdSignature implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.security.authgraph.SessionIdSignature> CREATOR = new android.os.Parcelable.Creator<android.hardware.security.authgraph.SessionIdSignature>() { // from class: android.hardware.security.authgraph.SessionIdSignature.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.authgraph.SessionIdSignature createFromParcel(android.os.Parcel parcel) {
            android.hardware.security.authgraph.SessionIdSignature sessionIdSignature = new android.hardware.security.authgraph.SessionIdSignature();
            sessionIdSignature.readFromParcel(parcel);
            return sessionIdSignature;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.authgraph.SessionIdSignature[] newArray(int i) {
            return new android.hardware.security.authgraph.SessionIdSignature[i];
        }
    };
    public byte[] signature;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByteArray(this.signature);
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
                this.signature = parcel.createByteArray();
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
