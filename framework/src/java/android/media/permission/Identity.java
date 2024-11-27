package android.media.permission;

/* loaded from: classes2.dex */
public class Identity implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.permission.Identity> CREATOR = new android.os.Parcelable.Creator<android.media.permission.Identity>() { // from class: android.media.permission.Identity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.permission.Identity createFromParcel(android.os.Parcel parcel) {
            android.media.permission.Identity identity = new android.media.permission.Identity();
            identity.readFromParcel(parcel);
            return identity;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.permission.Identity[] newArray(int i) {
            return new android.media.permission.Identity[i];
        }
    };
    public java.lang.String attributionTag;
    public java.lang.String packageName;
    public int uid = -1;
    public int pid = -1;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.uid);
        parcel.writeInt(this.pid);
        parcel.writeString(this.packageName);
        parcel.writeString(this.attributionTag);
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
            this.uid = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.pid = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.packageName = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.attributionTag = parcel.readString();
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
        stringJoiner.add("uid: " + this.uid);
        stringJoiner.add("pid: " + this.pid);
        stringJoiner.add("packageName: " + java.util.Objects.toString(this.packageName));
        stringJoiner.add("attributionTag: " + java.util.Objects.toString(this.attributionTag));
        return "Identity" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.permission.Identity)) {
            return false;
        }
        android.media.permission.Identity identity = (android.media.permission.Identity) obj;
        if (java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.uid), java.lang.Integer.valueOf(identity.uid)) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.pid), java.lang.Integer.valueOf(identity.pid)) && java.util.Objects.deepEquals(this.packageName, identity.packageName) && java.util.Objects.deepEquals(this.attributionTag, identity.attributionTag)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this.uid), java.lang.Integer.valueOf(this.pid), this.packageName, this.attributionTag).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
