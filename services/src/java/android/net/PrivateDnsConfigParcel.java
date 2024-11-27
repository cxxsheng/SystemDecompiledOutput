package android.net;

/* loaded from: classes.dex */
public class PrivateDnsConfigParcel implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.PrivateDnsConfigParcel> CREATOR = new android.os.Parcelable.Creator<android.net.PrivateDnsConfigParcel>() { // from class: android.net.PrivateDnsConfigParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.PrivateDnsConfigParcel createFromParcel(android.os.Parcel parcel) {
            android.net.PrivateDnsConfigParcel privateDnsConfigParcel = new android.net.PrivateDnsConfigParcel();
            privateDnsConfigParcel.readFromParcel(parcel);
            return privateDnsConfigParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.PrivateDnsConfigParcel[] newArray(int i) {
            return new android.net.PrivateDnsConfigParcel[i];
        }
    };
    public java.lang.String hostname;
    public java.lang.String[] ips;
    public int privateDnsMode = -1;
    public java.lang.String dohName = "";
    public java.lang.String[] dohIps = new java.lang.String[0];
    public java.lang.String dohPath = "";
    public int dohPort = -1;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.hostname);
        parcel.writeStringArray(this.ips);
        parcel.writeInt(this.privateDnsMode);
        parcel.writeString(this.dohName);
        parcel.writeStringArray(this.dohIps);
        parcel.writeString(this.dohPath);
        parcel.writeInt(this.dohPort);
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
            this.hostname = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.ips = parcel.createStringArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.privateDnsMode = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.dohName = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.dohIps = parcel.createStringArray();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.dohPath = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.dohPort = parcel.readInt();
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
        stringJoiner.add("hostname: " + java.util.Objects.toString(this.hostname));
        stringJoiner.add("ips: " + java.util.Arrays.toString(this.ips));
        stringJoiner.add("privateDnsMode: " + this.privateDnsMode);
        stringJoiner.add("dohName: " + java.util.Objects.toString(this.dohName));
        stringJoiner.add("dohIps: " + java.util.Arrays.toString(this.dohIps));
        stringJoiner.add("dohPath: " + java.util.Objects.toString(this.dohPath));
        stringJoiner.add("dohPort: " + this.dohPort);
        return "PrivateDnsConfigParcel" + stringJoiner.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.net.PrivateDnsConfigParcel)) {
            return false;
        }
        android.net.PrivateDnsConfigParcel privateDnsConfigParcel = (android.net.PrivateDnsConfigParcel) obj;
        if (java.util.Objects.deepEquals(this.hostname, privateDnsConfigParcel.hostname) && java.util.Objects.deepEquals(this.ips, privateDnsConfigParcel.ips) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.privateDnsMode), java.lang.Integer.valueOf(privateDnsConfigParcel.privateDnsMode)) && java.util.Objects.deepEquals(this.dohName, privateDnsConfigParcel.dohName) && java.util.Objects.deepEquals(this.dohIps, privateDnsConfigParcel.dohIps) && java.util.Objects.deepEquals(this.dohPath, privateDnsConfigParcel.dohPath) && java.util.Objects.deepEquals(java.lang.Integer.valueOf(this.dohPort), java.lang.Integer.valueOf(privateDnsConfigParcel.dohPort))) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(this.hostname, this.ips, java.lang.Integer.valueOf(this.privateDnsMode), this.dohName, this.dohIps, this.dohPath, java.lang.Integer.valueOf(this.dohPort)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
