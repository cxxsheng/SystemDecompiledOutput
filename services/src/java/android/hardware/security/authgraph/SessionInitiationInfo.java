package android.hardware.security.authgraph;

/* loaded from: classes.dex */
public class SessionInitiationInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.security.authgraph.SessionInitiationInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.security.authgraph.SessionInitiationInfo>() { // from class: android.hardware.security.authgraph.SessionInitiationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.authgraph.SessionInitiationInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.security.authgraph.SessionInitiationInfo sessionInitiationInfo = new android.hardware.security.authgraph.SessionInitiationInfo();
            sessionInitiationInfo.readFromParcel(parcel);
            return sessionInitiationInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.authgraph.SessionInitiationInfo[] newArray(int i) {
            return new android.hardware.security.authgraph.SessionInitiationInfo[i];
        }
    };
    public android.hardware.security.authgraph.Identity identity;
    public android.hardware.security.authgraph.Key key;
    public byte[] nonce;
    public int version = 0;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.key, i);
        parcel.writeTypedObject(this.identity, i);
        parcel.writeByteArray(this.nonce);
        parcel.writeInt(this.version);
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
            this.key = (android.hardware.security.authgraph.Key) parcel.readTypedObject(android.hardware.security.authgraph.Key.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.identity = (android.hardware.security.authgraph.Identity) parcel.readTypedObject(android.hardware.security.authgraph.Identity.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.nonce = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.version = parcel.readInt();
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
        return describeContents(this.key) | 0 | describeContents(this.identity);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
