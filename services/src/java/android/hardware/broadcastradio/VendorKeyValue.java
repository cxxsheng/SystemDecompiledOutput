package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public class VendorKeyValue implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.broadcastradio.VendorKeyValue> CREATOR = new android.os.Parcelable.Creator<android.hardware.broadcastradio.VendorKeyValue>() { // from class: android.hardware.broadcastradio.VendorKeyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.VendorKeyValue createFromParcel(android.os.Parcel parcel) {
            android.hardware.broadcastradio.VendorKeyValue vendorKeyValue = new android.hardware.broadcastradio.VendorKeyValue();
            vendorKeyValue.readFromParcel(parcel);
            return vendorKeyValue;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.VendorKeyValue[] newArray(int i) {
            return new android.hardware.broadcastradio.VendorKeyValue[i];
        }
    };
    public java.lang.String key;
    public java.lang.String value;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.key);
        parcel.writeString(this.value);
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
            this.key = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.value = parcel.readString();
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
        stringJoiner.add("key: " + java.util.Objects.toString(this.key));
        stringJoiner.add("value: " + java.util.Objects.toString(this.value));
        return "VendorKeyValue" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.broadcastradio.VendorKeyValue)) {
            return false;
        }
        android.hardware.broadcastradio.VendorKeyValue vendorKeyValue = (android.hardware.broadcastradio.VendorKeyValue) obj;
        if (java.util.Objects.deepEquals(this.key, vendorKeyValue.key) && java.util.Objects.deepEquals(this.value, vendorKeyValue.value)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.key, this.value).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
