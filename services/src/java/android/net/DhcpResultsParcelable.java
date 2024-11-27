package android.net;

/* loaded from: classes.dex */
public class DhcpResultsParcelable implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.DhcpResultsParcelable> CREATOR = new android.os.Parcelable.Creator<android.net.DhcpResultsParcelable>() { // from class: android.net.DhcpResultsParcelable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.DhcpResultsParcelable createFromParcel(android.os.Parcel parcel) {
            android.net.DhcpResultsParcelable dhcpResultsParcelable = new android.net.DhcpResultsParcelable();
            dhcpResultsParcelable.readFromParcel(parcel);
            return dhcpResultsParcelable;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.DhcpResultsParcelable[] newArray(int i) {
            return new android.net.DhcpResultsParcelable[i];
        }
    };
    public android.net.StaticIpConfiguration baseConfiguration;
    public java.lang.String captivePortalApiUrl;
    public int leaseDuration = 0;
    public int mtu = 0;
    public java.lang.String serverAddress;
    public java.lang.String serverHostName;
    public java.lang.String vendorInfo;

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.baseConfiguration, i);
        parcel.writeInt(this.leaseDuration);
        parcel.writeInt(this.mtu);
        parcel.writeString(this.serverAddress);
        parcel.writeString(this.vendorInfo);
        parcel.writeString(this.serverHostName);
        parcel.writeString(this.captivePortalApiUrl);
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
            this.baseConfiguration = (android.net.StaticIpConfiguration) parcel.readTypedObject(android.net.StaticIpConfiguration.CREATOR);
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.leaseDuration = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.mtu = parcel.readInt();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.serverAddress = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.vendorInfo = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                return;
            }
            this.serverHostName = parcel.readString();
            if (parcel.dataPosition() - dataPosition >= readInt) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } else {
                this.captivePortalApiUrl = parcel.readString();
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
        stringJoiner.add("baseConfiguration: " + java.util.Objects.toString(this.baseConfiguration));
        stringJoiner.add("leaseDuration: " + this.leaseDuration);
        stringJoiner.add("mtu: " + this.mtu);
        stringJoiner.add("serverAddress: " + java.util.Objects.toString(this.serverAddress));
        stringJoiner.add("vendorInfo: " + java.util.Objects.toString(this.vendorInfo));
        stringJoiner.add("serverHostName: " + java.util.Objects.toString(this.serverHostName));
        stringJoiner.add("captivePortalApiUrl: " + java.util.Objects.toString(this.captivePortalApiUrl));
        return "DhcpResultsParcelable" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.baseConfiguration) | 0;
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }
}
