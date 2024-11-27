package android.net.ipmemorystore;

/* loaded from: classes.dex */
public class SameL3NetworkResponseParcelable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.ipmemorystore.SameL3NetworkResponseParcelable> CREATOR = new android.os.Parcelable.Creator<android.net.ipmemorystore.SameL3NetworkResponseParcelable>() { // from class: android.net.ipmemorystore.SameL3NetworkResponseParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.ipmemorystore.SameL3NetworkResponseParcelable createFromParcel(android.os.Parcel parcel) {
            android.net.ipmemorystore.SameL3NetworkResponseParcelable sameL3NetworkResponseParcelable = new android.net.ipmemorystore.SameL3NetworkResponseParcelable();
            sameL3NetworkResponseParcelable.readFromParcel(parcel);
            return sameL3NetworkResponseParcelable;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.ipmemorystore.SameL3NetworkResponseParcelable[] newArray(int i) {
            return new android.net.ipmemorystore.SameL3NetworkResponseParcelable[i];
        }
    };
    public float confidence = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    public java.lang.String l2Key1;
    public java.lang.String l2Key2;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.l2Key1);
        parcel.writeString(this.l2Key2);
        parcel.writeFloat(this.confidence);
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
            this.l2Key1 = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.l2Key2 = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.confidence = parcel.readFloat();
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

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "{", "}");
        stringJoiner.add("l2Key1: " + java.util.Objects.toString(this.l2Key1));
        stringJoiner.add("l2Key2: " + java.util.Objects.toString(this.l2Key2));
        stringJoiner.add("confidence: " + this.confidence);
        return "SameL3NetworkResponseParcelable" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
