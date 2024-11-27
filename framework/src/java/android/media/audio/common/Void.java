package android.media.audio.common;

/* loaded from: classes2.dex */
public class Void implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.Void> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.Void>() { // from class: android.media.audio.common.Void.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.Void createFromParcel(android.os.Parcel parcel) {
            android.media.audio.common.Void r0 = new android.media.audio.common.Void();
            r0.readFromParcel(parcel);
            return r0;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.Void[] newArray(int i) {
            return new android.media.audio.common.Void[i];
        }
    };

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        if (readInt < 4) {
            try {
                throw new android.os.BadParcelableException("Parcelable too small");
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
        if (dataPosition > Integer.MAX_VALUE - readInt) {
            throw new android.os.BadParcelableException("Overflow in the size of parcelable");
        }
        parcel.setDataPosition(dataPosition + readInt);
    }

    public java.lang.String toString() {
        return "Void" + new java.util.StringJoiner(", ", "{", "}").toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.Void)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(new java.lang.Object[0]).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
