package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public class Properties implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.broadcastradio.Properties> CREATOR = new android.os.Parcelable.Creator<android.hardware.broadcastradio.Properties>() { // from class: android.hardware.broadcastradio.Properties.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.Properties createFromParcel(android.os.Parcel parcel) {
            android.hardware.broadcastradio.Properties properties = new android.hardware.broadcastradio.Properties();
            properties.readFromParcel(parcel);
            return properties;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.broadcastradio.Properties[] newArray(int i) {
            return new android.hardware.broadcastradio.Properties[i];
        }
    };
    public java.lang.String maker;
    public java.lang.String product;
    public java.lang.String serial;
    public int[] supportedIdentifierTypes;
    public android.hardware.broadcastradio.VendorKeyValue[] vendorInfo;
    public java.lang.String version;

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.maker);
        parcel.writeString(this.product);
        parcel.writeString(this.version);
        parcel.writeString(this.serial);
        parcel.writeIntArray(this.supportedIdentifierTypes);
        parcel.writeTypedArray(this.vendorInfo, i);
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
            this.maker = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.product = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.version = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.serial = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.supportedIdentifierTypes = parcel.createIntArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.vendorInfo = (android.hardware.broadcastradio.VendorKeyValue[]) parcel.createTypedArray(android.hardware.broadcastradio.VendorKeyValue.CREATOR);
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
        stringJoiner.add("maker: " + java.util.Objects.toString(this.maker));
        stringJoiner.add("product: " + java.util.Objects.toString(this.product));
        stringJoiner.add("version: " + java.util.Objects.toString(this.version));
        stringJoiner.add("serial: " + java.util.Objects.toString(this.serial));
        stringJoiner.add("supportedIdentifierTypes: " + android.hardware.broadcastradio.IdentifierType$$.arrayToString(this.supportedIdentifierTypes));
        stringJoiner.add("vendorInfo: " + java.util.Arrays.toString(this.vendorInfo));
        return "Properties" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.hardware.broadcastradio.Properties)) {
            return false;
        }
        android.hardware.broadcastradio.Properties properties = (android.hardware.broadcastradio.Properties) obj;
        if (java.util.Objects.deepEquals(this.maker, properties.maker) && java.util.Objects.deepEquals(this.product, properties.product) && java.util.Objects.deepEquals(this.version, properties.version) && java.util.Objects.deepEquals(this.serial, properties.serial) && java.util.Objects.deepEquals(this.supportedIdentifierTypes, properties.supportedIdentifierTypes) && java.util.Objects.deepEquals(this.vendorInfo, properties.vendorInfo)) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.maker, this.product, this.version, this.serial, this.supportedIdentifierTypes, this.vendorInfo).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.vendorInfo) | 0;
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
