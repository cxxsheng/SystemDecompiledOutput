package android.net;

/* loaded from: classes2.dex */
public class InterfaceConfiguration implements android.os.Parcelable {
    private static final java.lang.String FLAG_DOWN = "down";
    private static final java.lang.String FLAG_UP = "up";
    private android.net.LinkAddress mAddr;
    private java.util.HashSet<java.lang.String> mFlags = new java.util.HashSet<>();
    private java.lang.String mHwAddr;
    private static final java.lang.String[] EMPTY_STRING_ARRAY = new java.lang.String[0];
    public static final android.os.Parcelable.Creator<android.net.InterfaceConfiguration> CREATOR = new android.os.Parcelable.Creator<android.net.InterfaceConfiguration>() { // from class: android.net.InterfaceConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.InterfaceConfiguration createFromParcel(android.os.Parcel parcel) {
            android.net.InterfaceConfiguration interfaceConfiguration = new android.net.InterfaceConfiguration();
            interfaceConfiguration.mHwAddr = parcel.readString();
            if (parcel.readByte() == 1) {
                interfaceConfiguration.mAddr = (android.net.LinkAddress) parcel.readParcelable(null);
            }
            int readInt = parcel.readInt();
            for (int i = 0; i < readInt; i++) {
                interfaceConfiguration.mFlags.add(parcel.readString());
            }
            return interfaceConfiguration;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.InterfaceConfiguration[] newArray(int i) {
            return new android.net.InterfaceConfiguration[i];
        }
    };

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("mHwAddr=").append(this.mHwAddr);
        sb.append(" mAddr=").append(java.lang.String.valueOf(this.mAddr));
        sb.append(" mFlags=").append(getFlags());
        return sb.toString();
    }

    public java.lang.Iterable<java.lang.String> getFlags() {
        return this.mFlags;
    }

    public boolean hasFlag(java.lang.String str) {
        validateFlag(str);
        return this.mFlags.contains(str);
    }

    public void clearFlag(java.lang.String str) {
        validateFlag(str);
        this.mFlags.remove(str);
    }

    public void setFlag(java.lang.String str) {
        validateFlag(str);
        this.mFlags.add(str);
    }

    public void setInterfaceUp() {
        this.mFlags.remove("down");
        this.mFlags.add("up");
    }

    public void setInterfaceDown() {
        this.mFlags.remove("up");
        this.mFlags.add("down");
    }

    public void ignoreInterfaceUpDownStatus() {
        this.mFlags.remove("up");
        this.mFlags.remove("down");
    }

    public android.net.LinkAddress getLinkAddress() {
        return this.mAddr;
    }

    public void setLinkAddress(android.net.LinkAddress linkAddress) {
        this.mAddr = linkAddress;
    }

    public java.lang.String getHardwareAddress() {
        return this.mHwAddr;
    }

    public void setHardwareAddress(java.lang.String str) {
        this.mHwAddr = str;
    }

    public boolean isActive() {
        try {
            if (isUp()) {
                for (byte b : this.mAddr.getAddress().getAddress()) {
                    if (b != 0) {
                        return true;
                    }
                }
            }
            return false;
        } catch (java.lang.NullPointerException e) {
            return false;
        }
    }

    public boolean isUp() {
        return hasFlag("up");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mHwAddr);
        if (this.mAddr != null) {
            parcel.writeByte((byte) 1);
            parcel.writeParcelable(this.mAddr, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.mFlags.size());
        java.util.Iterator<java.lang.String> it = this.mFlags.iterator();
        while (it.hasNext()) {
            parcel.writeString(it.next());
        }
    }

    private static void validateFlag(java.lang.String str) {
        if (str.indexOf(32) >= 0) {
            throw new java.lang.IllegalArgumentException("flag contains space: " + str);
        }
    }
}
