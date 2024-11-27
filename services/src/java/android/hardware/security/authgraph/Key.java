package android.hardware.security.authgraph;

/* loaded from: classes.dex */
public class Key implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.security.authgraph.Key> CREATOR = new android.os.Parcelable.Creator<android.hardware.security.authgraph.Key>() { // from class: android.hardware.security.authgraph.Key.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.authgraph.Key createFromParcel(android.os.Parcel parcel) {
            android.hardware.security.authgraph.Key key = new android.hardware.security.authgraph.Key();
            key.readFromParcel(parcel);
            return key;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.authgraph.Key[] newArray(int i) {
            return new android.hardware.security.authgraph.Key[i];
        }
    };
    public android.hardware.security.authgraph.Arc arcFromPBK;
    public android.hardware.security.authgraph.PubKey pubKey;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.pubKey, i);
        parcel.writeTypedObject(this.arcFromPBK, i);
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
            this.pubKey = (android.hardware.security.authgraph.PubKey) parcel.readTypedObject(android.hardware.security.authgraph.PubKey.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.arcFromPBK = (android.hardware.security.authgraph.Arc) parcel.readTypedObject(android.hardware.security.authgraph.Arc.CREATOR);
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
        return describeContents(this.pubKey) | 0 | describeContents(this.arcFromPBK);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
