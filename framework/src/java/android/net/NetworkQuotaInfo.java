package android.net;

@java.lang.Deprecated
/* loaded from: classes2.dex */
public class NetworkQuotaInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.NetworkQuotaInfo> CREATOR = new android.os.Parcelable.Creator<android.net.NetworkQuotaInfo>() { // from class: android.net.NetworkQuotaInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.NetworkQuotaInfo createFromParcel(android.os.Parcel parcel) {
            return new android.net.NetworkQuotaInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.NetworkQuotaInfo[] newArray(int i) {
            return new android.net.NetworkQuotaInfo[i];
        }
    };
    public static final long NO_LIMIT = -1;

    public NetworkQuotaInfo() {
    }

    public NetworkQuotaInfo(android.os.Parcel parcel) {
    }

    public long getEstimatedBytes() {
        return 0L;
    }

    public long getSoftLimitBytes() {
        return -1L;
    }

    public long getHardLimitBytes() {
        return -1L;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
    }
}
