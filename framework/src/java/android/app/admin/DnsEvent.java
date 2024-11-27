package android.app.admin;

/* loaded from: classes.dex */
public final class DnsEvent extends android.app.admin.NetworkEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.DnsEvent> CREATOR = new android.os.Parcelable.Creator<android.app.admin.DnsEvent>() { // from class: android.app.admin.DnsEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.DnsEvent createFromParcel(android.os.Parcel parcel) {
            if (parcel.readInt() != 1) {
                return null;
            }
            return new android.app.admin.DnsEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.DnsEvent[] newArray(int i) {
            return new android.app.admin.DnsEvent[i];
        }
    };
    private final java.lang.String mHostname;
    private final java.lang.String[] mIpAddresses;
    private final int mIpAddressesCount;

    public DnsEvent(java.lang.String str, java.lang.String[] strArr, int i, java.lang.String str2, long j) {
        super(str2, j);
        this.mHostname = str;
        this.mIpAddresses = strArr;
        this.mIpAddressesCount = i;
    }

    private DnsEvent(android.os.Parcel parcel) {
        this.mHostname = parcel.readString();
        this.mIpAddresses = parcel.createStringArray();
        this.mIpAddressesCount = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mTimestamp = parcel.readLong();
        this.mId = parcel.readLong();
    }

    public java.lang.String getHostname() {
        return this.mHostname;
    }

    public java.util.List<java.net.InetAddress> getInetAddresses() {
        if (this.mIpAddresses == null || this.mIpAddresses.length == 0) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mIpAddresses.length);
        for (java.lang.String str : this.mIpAddresses) {
            try {
                arrayList.add(java.net.InetAddress.getByName(str));
            } catch (java.net.UnknownHostException e) {
            }
        }
        return arrayList;
    }

    public int getTotalResolvedAddressCount() {
        return this.mIpAddressesCount;
    }

    public java.lang.String toString() {
        return java.lang.String.format("DnsEvent(%d, %s, %s, %d, %d, %s)", java.lang.Long.valueOf(this.mId), this.mHostname, this.mIpAddresses == null ? android.security.keystore.KeyProperties.DIGEST_NONE : java.lang.String.join(" ", this.mIpAddresses), java.lang.Integer.valueOf(this.mIpAddressesCount), java.lang.Long.valueOf(this.mTimestamp), this.mPackageName);
    }

    @Override // android.app.admin.NetworkEvent, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.app.admin.NetworkEvent, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(1);
        parcel.writeString(this.mHostname);
        parcel.writeStringArray(this.mIpAddresses);
        parcel.writeInt(this.mIpAddressesCount);
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mTimestamp);
        parcel.writeLong(this.mId);
    }
}
