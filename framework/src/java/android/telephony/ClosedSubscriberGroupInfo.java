package android.telephony;

/* loaded from: classes3.dex */
public final class ClosedSubscriberGroupInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ClosedSubscriberGroupInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.ClosedSubscriberGroupInfo>() { // from class: android.telephony.ClosedSubscriberGroupInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ClosedSubscriberGroupInfo createFromParcel(android.os.Parcel parcel) {
            return android.telephony.ClosedSubscriberGroupInfo.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ClosedSubscriberGroupInfo[] newArray(int i) {
            return new android.telephony.ClosedSubscriberGroupInfo[i];
        }
    };
    private static final java.lang.String TAG = "ClosedSubscriberGroupInfo";
    private final int mCsgIdentity;
    private final boolean mCsgIndicator;
    private final java.lang.String mHomeNodebName;

    public ClosedSubscriberGroupInfo(boolean z, java.lang.String str, int i) {
        this.mCsgIndicator = z;
        this.mHomeNodebName = str == null ? "" : str;
        this.mCsgIdentity = i;
    }

    public boolean getCsgIndicator() {
        return this.mCsgIndicator;
    }

    public java.lang.String getHomeNodebName() {
        return this.mHomeNodebName;
    }

    public int getCsgIdentity() {
        return this.mCsgIdentity;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mCsgIndicator), this.mHomeNodebName, java.lang.Integer.valueOf(this.mCsgIdentity));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.ClosedSubscriberGroupInfo)) {
            return false;
        }
        android.telephony.ClosedSubscriberGroupInfo closedSubscriberGroupInfo = (android.telephony.ClosedSubscriberGroupInfo) obj;
        return this.mCsgIndicator == closedSubscriberGroupInfo.mCsgIndicator && closedSubscriberGroupInfo.mHomeNodebName.equals(this.mHomeNodebName) && this.mCsgIdentity == closedSubscriberGroupInfo.mCsgIdentity;
    }

    public java.lang.String toString() {
        return "ClosedSubscriberGroupInfo:{ mCsgIndicator = " + this.mCsgIndicator + " mHomeNodebName = " + this.mHomeNodebName + " mCsgIdentity = " + this.mCsgIdentity;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mCsgIndicator);
        parcel.writeString(this.mHomeNodebName);
        parcel.writeInt(this.mCsgIdentity);
    }

    private ClosedSubscriberGroupInfo(android.os.Parcel parcel) {
        this(parcel.readBoolean(), parcel.readString(), parcel.readInt());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected static android.telephony.ClosedSubscriberGroupInfo createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.ClosedSubscriberGroupInfo(parcel);
    }
}
