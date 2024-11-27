package android.telephony.data;

/* loaded from: classes3.dex */
public final class TrafficDescriptor implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.data.TrafficDescriptor> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.TrafficDescriptor>() { // from class: android.telephony.data.TrafficDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.TrafficDescriptor createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.data.TrafficDescriptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.TrafficDescriptor[] newArray(int i) {
            return new android.telephony.data.TrafficDescriptor[i];
        }
    };
    private final java.lang.String mDnn;
    private final android.telephony.data.TrafficDescriptor.OsAppId mOsAppId;

    public static final class OsAppId {
        private final java.lang.String mAppId;
        private final int mDifferentiator;
        private final java.util.UUID mOsId;
        public static final java.util.UUID ANDROID_OS_ID = java.util.UUID.fromString("97a498e3-fc92-5c94-8986-0333d06e4e47");
        private static final java.util.Set<java.lang.String> ALLOWED_APP_IDS = java.util.Set.of("ENTERPRISE", "PRIORITIZE_LATENCY", "PRIORITIZE_BANDWIDTH", "CBS");

        public OsAppId(java.util.UUID uuid, java.lang.String str) {
            this(uuid, str, 1);
        }

        public OsAppId(java.util.UUID uuid, java.lang.String str, int i) {
            java.util.Objects.requireNonNull(uuid);
            java.util.Objects.requireNonNull(str);
            if (i < 1) {
                throw new java.lang.IllegalArgumentException("Invalid differentiator " + i);
            }
            this.mOsId = uuid;
            this.mAppId = str;
            this.mDifferentiator = i;
        }

        public OsAppId(byte[] bArr) {
            try {
                java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
                this.mOsId = new java.util.UUID(wrap.getLong(), wrap.getLong());
                int i = wrap.get();
                byte[] bArr2 = new byte[i];
                wrap.get(bArr2, 0, i);
                java.lang.String str = new java.lang.String(bArr2);
                java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("[^0-9]+([0-9]+)$").matcher(new java.lang.String(bArr2));
                if (matcher.find()) {
                    this.mDifferentiator = java.lang.Integer.parseInt(matcher.group(1));
                    this.mAppId = str.replace(matcher.group(1), "");
                } else {
                    this.mDifferentiator = 1;
                    this.mAppId = str;
                }
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException("Failed to decode " + (bArr != null ? new java.math.BigInteger(1, bArr).toString(16) : null));
            }
        }

        public java.util.UUID getOsId() {
            return this.mOsId;
        }

        public java.lang.String getAppId() {
            return this.mAppId;
        }

        public int getDifferentiator() {
            return this.mDifferentiator;
        }

        public byte[] getBytes() {
            byte[] bytes = (this.mAppId + (this.mDifferentiator > 1 ? java.lang.Integer.valueOf(this.mDifferentiator) : "")).getBytes();
            java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(bytes.length + 17);
            allocate.putLong(this.mOsId.getMostSignificantBits());
            allocate.putLong(this.mOsId.getLeastSignificantBits());
            allocate.put((byte) bytes.length);
            allocate.put(bytes);
            return allocate.array();
        }

        public java.lang.String toString() {
            return "[OsAppId: OS=" + this.mOsId + ", App=" + this.mAppId + ", differentiator=" + this.mDifferentiator + ", raw=" + new java.math.BigInteger(1, getBytes()).toString(16) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.telephony.data.TrafficDescriptor.OsAppId osAppId = (android.telephony.data.TrafficDescriptor.OsAppId) obj;
            if (this.mDifferentiator == osAppId.mDifferentiator && this.mOsId.equals(osAppId.mOsId) && this.mAppId.equals(osAppId.mAppId)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mOsId, this.mAppId, java.lang.Integer.valueOf(this.mDifferentiator));
        }
    }

    private TrafficDescriptor(android.os.Parcel parcel) {
        android.telephony.data.TrafficDescriptor.OsAppId osAppId;
        this.mDnn = parcel.readString();
        byte[] createByteArray = parcel.createByteArray();
        if (createByteArray == null) {
            osAppId = null;
        } else {
            osAppId = new android.telephony.data.TrafficDescriptor.OsAppId(createByteArray);
        }
        this.mOsAppId = osAppId;
        enforceAllowedIds();
    }

    public TrafficDescriptor(java.lang.String str, byte[] bArr) {
        android.telephony.data.TrafficDescriptor.OsAppId osAppId;
        this.mDnn = str;
        if (bArr == null) {
            osAppId = null;
        } else {
            osAppId = new android.telephony.data.TrafficDescriptor.OsAppId(bArr);
        }
        this.mOsAppId = osAppId;
        enforceAllowedIds();
    }

    private void enforceAllowedIds() {
        if (this.mOsAppId != null && !this.mOsAppId.getOsId().equals(android.telephony.data.TrafficDescriptor.OsAppId.ANDROID_OS_ID)) {
            throw new java.lang.IllegalArgumentException("OS id " + this.mOsAppId.getOsId() + " does not match " + android.telephony.data.TrafficDescriptor.OsAppId.ANDROID_OS_ID);
        }
        if (this.mOsAppId != null && !android.telephony.data.TrafficDescriptor.OsAppId.ALLOWED_APP_IDS.contains(this.mOsAppId.getAppId())) {
            throw new java.lang.IllegalArgumentException("Illegal app id " + this.mOsAppId.getAppId() + ". Only allowing one of the following " + android.telephony.data.TrafficDescriptor.OsAppId.ALLOWED_APP_IDS);
        }
    }

    public java.lang.String getDataNetworkName() {
        return this.mDnn;
    }

    public byte[] getOsAppId() {
        if (this.mOsAppId != null) {
            return this.mOsAppId.getBytes();
        }
        return null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "TrafficDescriptor={mDnn=" + this.mDnn + ", " + this.mOsAppId + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mDnn);
        parcel.writeByteArray(this.mOsAppId != null ? this.mOsAppId.getBytes() : null);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.data.TrafficDescriptor trafficDescriptor = (android.telephony.data.TrafficDescriptor) obj;
        if (java.util.Objects.equals(this.mDnn, trafficDescriptor.mDnn) && java.util.Objects.equals(this.mOsAppId, trafficDescriptor.mOsAppId)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mDnn, this.mOsAppId);
    }

    public static final class Builder {
        private java.lang.String mDnn = null;
        private byte[] mOsAppId = null;

        public android.telephony.data.TrafficDescriptor.Builder setDataNetworkName(java.lang.String str) {
            this.mDnn = str;
            return this;
        }

        public android.telephony.data.TrafficDescriptor.Builder setOsAppId(byte[] bArr) {
            this.mOsAppId = bArr;
            return this;
        }

        public android.telephony.data.TrafficDescriptor build() {
            if (this.mDnn == null && this.mOsAppId == null) {
                throw new java.lang.IllegalArgumentException("DNN and OS App ID are null");
            }
            return new android.telephony.data.TrafficDescriptor(this.mDnn, this.mOsAppId);
        }
    }
}
