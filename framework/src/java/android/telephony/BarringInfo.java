package android.telephony;

/* loaded from: classes3.dex */
public final class BarringInfo implements android.os.Parcelable {
    public static final int BARRING_SERVICE_TYPE_CS_FALLBACK = 5;
    public static final int BARRING_SERVICE_TYPE_CS_SERVICE = 0;
    public static final int BARRING_SERVICE_TYPE_CS_VOICE = 2;
    public static final int BARRING_SERVICE_TYPE_EMERGENCY = 8;
    public static final int BARRING_SERVICE_TYPE_MMTEL_VIDEO = 7;
    public static final int BARRING_SERVICE_TYPE_MMTEL_VOICE = 6;
    public static final int BARRING_SERVICE_TYPE_MO_DATA = 4;
    public static final int BARRING_SERVICE_TYPE_MO_SIGNALLING = 3;
    public static final int BARRING_SERVICE_TYPE_PS_SERVICE = 1;
    public static final int BARRING_SERVICE_TYPE_SMS = 9;
    private android.util.SparseArray<android.telephony.BarringInfo.BarringServiceInfo> mBarringServiceInfos;
    private android.telephony.CellIdentity mCellIdentity;
    private static final android.telephony.BarringInfo.BarringServiceInfo BARRING_SERVICE_INFO_UNKNOWN = new android.telephony.BarringInfo.BarringServiceInfo(-1);
    private static final android.telephony.BarringInfo.BarringServiceInfo BARRING_SERVICE_INFO_UNBARRED = new android.telephony.BarringInfo.BarringServiceInfo(0);
    public static final android.os.Parcelable.Creator<android.telephony.BarringInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.BarringInfo>() { // from class: android.telephony.BarringInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.BarringInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.BarringInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.BarringInfo[] newArray(int i) {
            return new android.telephony.BarringInfo[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BarringServiceType {
    }

    public static final class BarringServiceInfo implements android.os.Parcelable {
        public static final int BARRING_TYPE_CONDITIONAL = 1;
        public static final int BARRING_TYPE_NONE = 0;
        public static final int BARRING_TYPE_UNCONDITIONAL = 2;
        public static final int BARRING_TYPE_UNKNOWN = -1;
        public static final android.os.Parcelable.Creator<android.telephony.BarringInfo.BarringServiceInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.BarringInfo.BarringServiceInfo>() { // from class: android.telephony.BarringInfo.BarringServiceInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.BarringInfo.BarringServiceInfo createFromParcel(android.os.Parcel parcel) {
                return new android.telephony.BarringInfo.BarringServiceInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.BarringInfo.BarringServiceInfo[] newArray(int i) {
                return new android.telephony.BarringInfo.BarringServiceInfo[i];
            }
        };
        private final int mBarringType;
        private final int mConditionalBarringFactor;
        private final int mConditionalBarringTimeSeconds;
        private final boolean mIsConditionallyBarred;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface BarringType {
        }

        public BarringServiceInfo(int i) {
            this(i, false, 0, 0);
        }

        public BarringServiceInfo(int i, boolean z, int i2, int i3) {
            this.mBarringType = i;
            this.mIsConditionallyBarred = z;
            this.mConditionalBarringFactor = i2;
            this.mConditionalBarringTimeSeconds = i3;
        }

        public int getBarringType() {
            return this.mBarringType;
        }

        public boolean isConditionallyBarred() {
            return this.mIsConditionallyBarred;
        }

        public int getConditionalBarringFactor() {
            return this.mConditionalBarringFactor;
        }

        public int getConditionalBarringTimeSeconds() {
            return this.mConditionalBarringTimeSeconds;
        }

        public boolean isBarred() {
            if (this.mBarringType != 2) {
                return this.mBarringType == 1 && this.mIsConditionallyBarred;
            }
            return true;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mBarringType), java.lang.Boolean.valueOf(this.mIsConditionallyBarred), java.lang.Integer.valueOf(this.mConditionalBarringFactor), java.lang.Integer.valueOf(this.mConditionalBarringTimeSeconds));
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.telephony.BarringInfo.BarringServiceInfo)) {
                return false;
            }
            android.telephony.BarringInfo.BarringServiceInfo barringServiceInfo = (android.telephony.BarringInfo.BarringServiceInfo) obj;
            return this.mBarringType == barringServiceInfo.mBarringType && this.mIsConditionallyBarred == barringServiceInfo.mIsConditionallyBarred && this.mConditionalBarringFactor == barringServiceInfo.mConditionalBarringFactor && this.mConditionalBarringTimeSeconds == barringServiceInfo.mConditionalBarringTimeSeconds;
        }

        private static java.lang.String barringTypeToString(int i) {
            switch (i) {
                case -1:
                    return "UNKNOWN";
                case 0:
                    return android.security.keystore.KeyProperties.DIGEST_NONE;
                case 1:
                    return "CONDITIONAL";
                case 2:
                    return "UNCONDITIONAL";
                default:
                    return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            }
        }

        public java.lang.String toString() {
            return "BarringServiceInfo {mBarringType=" + barringTypeToString(this.mBarringType) + ", mIsConditionallyBarred=" + this.mIsConditionallyBarred + ", mConditionalBarringFactor=" + this.mConditionalBarringFactor + ", mConditionalBarringTimeSeconds=" + this.mConditionalBarringTimeSeconds + "}";
        }

        public BarringServiceInfo(android.os.Parcel parcel) {
            this.mBarringType = parcel.readInt();
            this.mIsConditionallyBarred = parcel.readBoolean();
            this.mConditionalBarringFactor = parcel.readInt();
            this.mConditionalBarringTimeSeconds = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mBarringType);
            parcel.writeBoolean(this.mIsConditionallyBarred);
            parcel.writeInt(this.mConditionalBarringFactor);
            parcel.writeInt(this.mConditionalBarringTimeSeconds);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    @android.annotation.SystemApi
    public BarringInfo() {
        this.mBarringServiceInfos = new android.util.SparseArray<>();
    }

    public BarringInfo(android.telephony.CellIdentity cellIdentity, android.util.SparseArray<android.telephony.BarringInfo.BarringServiceInfo> sparseArray) {
        this.mCellIdentity = cellIdentity;
        this.mBarringServiceInfos = sparseArray;
    }

    public android.telephony.BarringInfo.BarringServiceInfo getBarringServiceInfo(int i) {
        android.telephony.BarringInfo.BarringServiceInfo barringServiceInfo = this.mBarringServiceInfos.get(i);
        if (barringServiceInfo != null) {
            return barringServiceInfo;
        }
        return this.mBarringServiceInfos.size() > 0 ? BARRING_SERVICE_INFO_UNBARRED : BARRING_SERVICE_INFO_UNKNOWN;
    }

    @android.annotation.SystemApi
    public android.telephony.BarringInfo createLocationInfoSanitizedCopy() {
        return this.mCellIdentity == null ? this : new android.telephony.BarringInfo(this.mCellIdentity.sanitizeLocationInfo(), this.mBarringServiceInfos);
    }

    public BarringInfo(android.os.Parcel parcel) {
        this.mCellIdentity = (android.telephony.CellIdentity) parcel.readParcelable(android.telephony.CellIdentity.class.getClassLoader(), android.telephony.CellIdentity.class);
        this.mBarringServiceInfos = parcel.readSparseArray(android.telephony.BarringInfo.BarringServiceInfo.class.getClassLoader(), android.telephony.BarringInfo.BarringServiceInfo.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mCellIdentity, i);
        parcel.writeSparseArray(this.mBarringServiceInfos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        int hashCode = this.mCellIdentity != null ? this.mCellIdentity.hashCode() : 7;
        for (int i = 0; i < this.mBarringServiceInfos.size(); i++) {
            hashCode = hashCode + (this.mBarringServiceInfos.keyAt(i) * 15) + (this.mBarringServiceInfos.valueAt(i).hashCode() * 31);
        }
        return hashCode;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.BarringInfo)) {
            return false;
        }
        android.telephony.BarringInfo barringInfo = (android.telephony.BarringInfo) obj;
        if (hashCode() != barringInfo.hashCode() || this.mBarringServiceInfos.size() != barringInfo.mBarringServiceInfos.size()) {
            return false;
        }
        for (int i = 0; i < this.mBarringServiceInfos.size(); i++) {
            if (this.mBarringServiceInfos.keyAt(i) != barringInfo.mBarringServiceInfos.keyAt(i) || !java.util.Objects.equals(this.mBarringServiceInfos.valueAt(i), barringInfo.mBarringServiceInfos.valueAt(i))) {
                return false;
            }
        }
        return true;
    }

    public java.lang.String toString() {
        return "BarringInfo {mCellIdentity=" + this.mCellIdentity + ", mBarringServiceInfos=" + this.mBarringServiceInfos + "}";
    }
}
