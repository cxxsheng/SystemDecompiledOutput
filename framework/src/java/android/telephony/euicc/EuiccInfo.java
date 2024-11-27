package android.telephony.euicc;

/* loaded from: classes3.dex */
public final class EuiccInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.euicc.EuiccInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.euicc.EuiccInfo>() { // from class: android.telephony.euicc.EuiccInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.euicc.EuiccInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.euicc.EuiccInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.euicc.EuiccInfo[] newArray(int i) {
            return new android.telephony.euicc.EuiccInfo[i];
        }
    };
    private final java.lang.String osVersion;

    public java.lang.String getOsVersion() {
        return this.osVersion;
    }

    public EuiccInfo(java.lang.String str) {
        this.osVersion = str;
    }

    private EuiccInfo(android.os.Parcel parcel) {
        this.osVersion = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.osVersion);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
