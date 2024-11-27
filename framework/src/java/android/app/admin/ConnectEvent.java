package android.app.admin;

/* loaded from: classes.dex */
public final class ConnectEvent extends android.app.admin.NetworkEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.ConnectEvent> CREATOR = new android.os.Parcelable.Creator<android.app.admin.ConnectEvent>() { // from class: android.app.admin.ConnectEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.ConnectEvent createFromParcel(android.os.Parcel parcel) {
            if (parcel.readInt() != 2) {
                return null;
            }
            return new android.app.admin.ConnectEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.ConnectEvent[] newArray(int i) {
            return new android.app.admin.ConnectEvent[i];
        }
    };
    private final java.lang.String mIpAddress;
    private final int mPort;

    public ConnectEvent(java.lang.String str, int i, java.lang.String str2, long j) {
        super(str2, j);
        this.mIpAddress = str;
        this.mPort = i;
    }

    private ConnectEvent(android.os.Parcel parcel) {
        this.mIpAddress = parcel.readString();
        this.mPort = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mTimestamp = parcel.readLong();
        this.mId = parcel.readLong();
    }

    public java.net.InetAddress getInetAddress() {
        try {
            return java.net.InetAddress.getByName(this.mIpAddress);
        } catch (java.net.UnknownHostException e) {
            return java.net.InetAddress.getLoopbackAddress();
        }
    }

    public int getPort() {
        return this.mPort;
    }

    public java.lang.String toString() {
        return java.lang.String.format("ConnectEvent(%d, %s, %d, %d, %s)", java.lang.Long.valueOf(this.mId), this.mIpAddress, java.lang.Integer.valueOf(this.mPort), java.lang.Long.valueOf(this.mTimestamp), this.mPackageName);
    }

    @Override // android.app.admin.NetworkEvent, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.app.admin.NetworkEvent, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(2);
        parcel.writeString(this.mIpAddress);
        parcel.writeInt(this.mPort);
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mTimestamp);
        parcel.writeLong(this.mId);
    }
}
