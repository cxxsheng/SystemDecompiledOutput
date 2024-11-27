package android.telephony;

/* loaded from: classes3.dex */
public final class AvailableNetworkInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.AvailableNetworkInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.AvailableNetworkInfo>() { // from class: android.telephony.AvailableNetworkInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.AvailableNetworkInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.AvailableNetworkInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.AvailableNetworkInfo[] newArray(int i) {
            return new android.telephony.AvailableNetworkInfo[i];
        }
    };
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = 3;
    public static final int PRIORITY_MED = 2;

    @java.lang.Deprecated
    private java.util.ArrayList<java.lang.Integer> mBands;
    private java.util.ArrayList<java.lang.String> mMccMncs;
    private int mPriority;
    private java.util.ArrayList<android.telephony.RadioAccessSpecifier> mRadioAccessSpecifiers;
    private int mSubId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AvailableNetworkInfoPriority {
    }

    public int getSubId() {
        return this.mSubId;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public java.util.List<java.lang.String> getMccMncs() {
        return (java.util.List) this.mMccMncs.clone();
    }

    public java.util.List<java.lang.Integer> getBands() {
        return (java.util.List) this.mBands.clone();
    }

    public java.util.List<android.telephony.RadioAccessSpecifier> getRadioAccessSpecifiers() {
        return (java.util.List) this.mRadioAccessSpecifiers.clone();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSubId);
        parcel.writeInt(this.mPriority);
        parcel.writeStringList(this.mMccMncs);
        parcel.writeList(this.mBands);
        parcel.writeList(this.mRadioAccessSpecifiers);
    }

    private AvailableNetworkInfo(android.os.Parcel parcel) {
        this.mSubId = parcel.readInt();
        this.mPriority = parcel.readInt();
        this.mMccMncs = new java.util.ArrayList<>();
        parcel.readStringList(this.mMccMncs);
        this.mBands = new java.util.ArrayList<>();
        parcel.readList(this.mBands, java.lang.Integer.class.getClassLoader(), java.lang.Integer.class);
        this.mRadioAccessSpecifiers = new java.util.ArrayList<>();
        parcel.readList(this.mRadioAccessSpecifiers, android.telephony.RadioAccessSpecifier.class.getClassLoader(), android.telephony.RadioAccessSpecifier.class);
    }

    public AvailableNetworkInfo(int i, int i2, java.util.List<java.lang.String> list, java.util.List<java.lang.Integer> list2) {
        this(i, i2, list, list2, new java.util.ArrayList());
    }

    private AvailableNetworkInfo(int i, int i2, java.util.List<java.lang.String> list, java.util.List<java.lang.Integer> list2, java.util.List<android.telephony.RadioAccessSpecifier> list3) {
        this.mSubId = i;
        this.mPriority = i2;
        this.mMccMncs = new java.util.ArrayList<>(list);
        this.mBands = new java.util.ArrayList<>(list2);
        this.mRadioAccessSpecifiers = new java.util.ArrayList<>(list3);
    }

    public boolean equals(java.lang.Object obj) {
        try {
            android.telephony.AvailableNetworkInfo availableNetworkInfo = (android.telephony.AvailableNetworkInfo) obj;
            return obj != null && this.mSubId == availableNetworkInfo.mSubId && this.mPriority == availableNetworkInfo.mPriority && this.mMccMncs != null && this.mMccMncs.equals(availableNetworkInfo.mMccMncs) && this.mBands.equals(availableNetworkInfo.mBands) && this.mRadioAccessSpecifiers.equals(availableNetworkInfo.getRadioAccessSpecifiers());
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mSubId), java.lang.Integer.valueOf(this.mPriority), this.mMccMncs, this.mBands, this.mRadioAccessSpecifiers);
    }

    public java.lang.String toString() {
        return "AvailableNetworkInfo: mSubId: " + this.mSubId + " mPriority: " + this.mPriority + " mMccMncs: " + java.util.Arrays.toString(this.mMccMncs.toArray()) + " mBands: " + java.util.Arrays.toString(this.mBands.toArray()) + " mRadioAccessSpecifiers: " + java.util.Arrays.toString(this.mRadioAccessSpecifiers.toArray());
    }

    public static final class Builder {
        private int mSubId;
        private int mPriority = 3;
        private java.util.ArrayList<java.lang.String> mMccMncs = new java.util.ArrayList<>();
        private java.util.ArrayList<android.telephony.RadioAccessSpecifier> mRadioAccessSpecifiers = new java.util.ArrayList<>();

        public Builder(int i) {
            this.mSubId = Integer.MIN_VALUE;
            this.mSubId = i;
        }

        public android.telephony.AvailableNetworkInfo.Builder setPriority(int i) {
            if (i > 3 || i < 1) {
                throw new java.lang.IllegalArgumentException("A valid priority must be set");
            }
            this.mPriority = i;
            return this;
        }

        public android.telephony.AvailableNetworkInfo.Builder setMccMncs(java.util.List<java.lang.String> list) {
            java.util.Objects.requireNonNull(list, "A non-null List of mccmncs must be set. An empty List is still accepted. Please read documentation in AvailableNetworkInfo to see consequences of an empty List.");
            this.mMccMncs = new java.util.ArrayList<>(list);
            return this;
        }

        public android.telephony.AvailableNetworkInfo.Builder setRadioAccessSpecifiers(java.util.List<android.telephony.RadioAccessSpecifier> list) {
            java.util.Objects.requireNonNull(list, "A non-null List of RadioAccessSpecifiers must be set. An empty List is still accepted. Please read documentation in AvailableNetworkInfo to see consequences of an empty List.");
            this.mRadioAccessSpecifiers = new java.util.ArrayList<>(list);
            return this;
        }

        public android.telephony.AvailableNetworkInfo build() {
            if (this.mSubId == Integer.MIN_VALUE) {
                throw new java.lang.IllegalArgumentException("A valid subId must be set");
            }
            return new android.telephony.AvailableNetworkInfo(this.mSubId, this.mPriority, this.mMccMncs, new java.util.ArrayList(), this.mRadioAccessSpecifiers);
        }
    }
}
