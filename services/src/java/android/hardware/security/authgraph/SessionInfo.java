package android.hardware.security.authgraph;

/* loaded from: classes.dex */
public class SessionInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.security.authgraph.SessionInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.security.authgraph.SessionInfo>() { // from class: android.hardware.security.authgraph.SessionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.authgraph.SessionInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.security.authgraph.SessionInfo sessionInfo = new android.hardware.security.authgraph.SessionInfo();
            sessionInfo.readFromParcel(parcel);
            return sessionInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.security.authgraph.SessionInfo[] newArray(int i) {
            return new android.hardware.security.authgraph.SessionInfo[i];
        }
    };
    public byte[] sessionId;
    public android.hardware.security.authgraph.Arc[] sharedKeys;
    public android.hardware.security.authgraph.SessionIdSignature signature;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeFixedArray(this.sharedKeys, i, 2);
        parcel.writeByteArray(this.sessionId);
        parcel.writeTypedObject(this.signature, i);
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
            this.sharedKeys = (android.hardware.security.authgraph.Arc[]) parcel.createFixedArray(android.hardware.security.authgraph.Arc[].class, android.hardware.security.authgraph.Arc.CREATOR, 2);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.sessionId = parcel.createByteArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.signature = (android.hardware.security.authgraph.SessionIdSignature) parcel.readTypedObject(android.hardware.security.authgraph.SessionIdSignature.CREATOR);
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
        return describeContents(this.sharedKeys) | 0 | describeContents(this.signature);
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
