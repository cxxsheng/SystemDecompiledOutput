package android.telephony.data;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class EpsBearerQosSessionAttributes implements android.os.Parcelable, android.net.QosSessionAttributes {
    private final long mGuaranteedDownlinkBitRate;
    private final long mGuaranteedUplinkBitRate;
    private final long mMaxDownlinkBitRate;
    private final long mMaxUplinkBitRate;
    private final int mQci;
    private final java.util.List<java.net.InetSocketAddress> mRemoteAddresses;
    private static final java.lang.String TAG = android.telephony.data.EpsBearerQosSessionAttributes.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.telephony.data.EpsBearerQosSessionAttributes> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.EpsBearerQosSessionAttributes>() { // from class: android.telephony.data.EpsBearerQosSessionAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.EpsBearerQosSessionAttributes createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.data.EpsBearerQosSessionAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.EpsBearerQosSessionAttributes[] newArray(int i) {
            return new android.telephony.data.EpsBearerQosSessionAttributes[i];
        }
    };

    public int getQosIdentifier() {
        return this.mQci;
    }

    public long getGuaranteedUplinkBitRateKbps() {
        return this.mGuaranteedUplinkBitRate;
    }

    public long getGuaranteedDownlinkBitRateKbps() {
        return this.mGuaranteedDownlinkBitRate;
    }

    public long getMaxUplinkBitRateKbps() {
        return this.mMaxUplinkBitRate;
    }

    public long getMaxDownlinkBitRateKbps() {
        return this.mMaxDownlinkBitRate;
    }

    public java.util.List<java.net.InetSocketAddress> getRemoteAddresses() {
        return this.mRemoteAddresses;
    }

    public EpsBearerQosSessionAttributes(int i, long j, long j2, long j3, long j4, java.util.List<java.net.InetSocketAddress> list) {
        java.util.Objects.requireNonNull(list, "remoteAddress must be non-null");
        this.mQci = i;
        this.mMaxDownlinkBitRate = j;
        this.mMaxUplinkBitRate = j2;
        this.mGuaranteedDownlinkBitRate = j3;
        this.mGuaranteedUplinkBitRate = j4;
        this.mRemoteAddresses = java.util.Collections.unmodifiableList(copySocketAddresses(list));
    }

    private static java.util.List<java.net.InetSocketAddress> copySocketAddresses(java.util.List<java.net.InetSocketAddress> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.net.InetSocketAddress inetSocketAddress : list) {
            if (inetSocketAddress != null && inetSocketAddress.getAddress() != null) {
                arrayList.add(inetSocketAddress);
            }
        }
        return arrayList;
    }

    private EpsBearerQosSessionAttributes(android.os.Parcel parcel) {
        this.mQci = parcel.readInt();
        this.mMaxDownlinkBitRate = parcel.readLong();
        this.mMaxUplinkBitRate = parcel.readLong();
        this.mGuaranteedDownlinkBitRate = parcel.readLong();
        this.mGuaranteedUplinkBitRate = parcel.readLong();
        int readInt = parcel.readInt();
        java.util.ArrayList arrayList = new java.util.ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            try {
                arrayList.add(new java.net.InetSocketAddress(java.net.InetAddress.getByAddress(parcel.createByteArray()), parcel.readInt()));
            } catch (java.net.UnknownHostException e) {
                android.util.Log.e(TAG, "unable to unparcel remote address at index: " + i, e);
            }
        }
        this.mRemoteAddresses = java.util.Collections.unmodifiableList(arrayList);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mQci);
        parcel.writeLong(this.mMaxDownlinkBitRate);
        parcel.writeLong(this.mMaxUplinkBitRate);
        parcel.writeLong(this.mGuaranteedDownlinkBitRate);
        parcel.writeLong(this.mGuaranteedUplinkBitRate);
        int size = this.mRemoteAddresses.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            java.net.InetSocketAddress inetSocketAddress = this.mRemoteAddresses.get(i2);
            parcel.writeByteArray(inetSocketAddress.getAddress().getAddress());
            parcel.writeInt(inetSocketAddress.getPort());
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.data.EpsBearerQosSessionAttributes epsBearerQosSessionAttributes = (android.telephony.data.EpsBearerQosSessionAttributes) obj;
        if (this.mQci == epsBearerQosSessionAttributes.mQci && this.mMaxUplinkBitRate == epsBearerQosSessionAttributes.mMaxUplinkBitRate && this.mMaxDownlinkBitRate == epsBearerQosSessionAttributes.mMaxDownlinkBitRate && this.mGuaranteedUplinkBitRate == epsBearerQosSessionAttributes.mGuaranteedUplinkBitRate && this.mGuaranteedDownlinkBitRate == epsBearerQosSessionAttributes.mGuaranteedDownlinkBitRate && this.mRemoteAddresses.size() == epsBearerQosSessionAttributes.mRemoteAddresses.size() && this.mRemoteAddresses.containsAll(epsBearerQosSessionAttributes.mRemoteAddresses)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mQci), java.lang.Long.valueOf(this.mMaxUplinkBitRate), java.lang.Long.valueOf(this.mMaxDownlinkBitRate), java.lang.Long.valueOf(this.mGuaranteedUplinkBitRate), java.lang.Long.valueOf(this.mGuaranteedDownlinkBitRate), this.mRemoteAddresses);
    }
}
