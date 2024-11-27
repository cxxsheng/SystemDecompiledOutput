package android.telephony;

/* loaded from: classes3.dex */
public final class NetworkScanRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.NetworkScanRequest> CREATOR = new android.os.Parcelable.Creator<android.telephony.NetworkScanRequest>() { // from class: android.telephony.NetworkScanRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.NetworkScanRequest createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.NetworkScanRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.NetworkScanRequest[] newArray(int i) {
            return new android.telephony.NetworkScanRequest[i];
        }
    };
    public static final int MAX_BANDS = 8;
    public static final int MAX_CHANNELS = 32;
    public static final int MAX_INCREMENTAL_PERIODICITY_SEC = 10;
    public static final int MAX_MCC_MNC_LIST_SIZE = 20;
    public static final int MAX_RADIO_ACCESS_NETWORKS = 8;
    public static final int MAX_SEARCH_MAX_SEC = 3600;
    public static final int MAX_SEARCH_PERIODICITY_SEC = 300;
    public static final int MIN_INCREMENTAL_PERIODICITY_SEC = 1;
    public static final int MIN_SEARCH_MAX_SEC = 60;
    public static final int MIN_SEARCH_PERIODICITY_SEC = 5;
    public static final int SCAN_TYPE_ONE_SHOT = 0;
    public static final int SCAN_TYPE_PERIODIC = 1;
    private boolean mIncrementalResults;
    private int mIncrementalResultsPeriodicity;
    private int mMaxSearchTime;
    private java.util.ArrayList<java.lang.String> mMccMncs;
    private int mScanType;
    private int mSearchPeriodicity;
    private android.telephony.RadioAccessSpecifier[] mSpecifiers;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScanType {
    }

    public NetworkScanRequest(int i, android.telephony.RadioAccessSpecifier[] radioAccessSpecifierArr, int i2, int i3, boolean z, int i4, java.util.ArrayList<java.lang.String> arrayList) {
        this.mScanType = i;
        if (radioAccessSpecifierArr != null) {
            this.mSpecifiers = (android.telephony.RadioAccessSpecifier[]) radioAccessSpecifierArr.clone();
        } else {
            this.mSpecifiers = null;
        }
        this.mSearchPeriodicity = i2;
        this.mMaxSearchTime = i3;
        this.mIncrementalResults = z;
        this.mIncrementalResultsPeriodicity = i4;
        if (arrayList != null) {
            this.mMccMncs = (java.util.ArrayList) arrayList.clone();
        } else {
            this.mMccMncs = new java.util.ArrayList<>();
        }
    }

    public int getScanType() {
        return this.mScanType;
    }

    public int getSearchPeriodicity() {
        return this.mSearchPeriodicity;
    }

    public int getMaxSearchTime() {
        return this.mMaxSearchTime;
    }

    public boolean getIncrementalResults() {
        return this.mIncrementalResults;
    }

    public int getIncrementalResultsPeriodicity() {
        return this.mIncrementalResultsPeriodicity;
    }

    public android.telephony.RadioAccessSpecifier[] getSpecifiers() {
        if (this.mSpecifiers == null) {
            return null;
        }
        return (android.telephony.RadioAccessSpecifier[]) this.mSpecifiers.clone();
    }

    public java.util.ArrayList<java.lang.String> getPlmns() {
        return (java.util.ArrayList) this.mMccMncs.clone();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mScanType);
        parcel.writeParcelableArray(this.mSpecifiers, i);
        parcel.writeInt(this.mSearchPeriodicity);
        parcel.writeInt(this.mMaxSearchTime);
        parcel.writeBoolean(this.mIncrementalResults);
        parcel.writeInt(this.mIncrementalResultsPeriodicity);
        parcel.writeStringList(this.mMccMncs);
    }

    private NetworkScanRequest(android.os.Parcel parcel) {
        this.mScanType = parcel.readInt();
        android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) parcel.readParcelableArray(java.lang.Object.class.getClassLoader(), android.telephony.RadioAccessSpecifier.class);
        if (parcelableArr != null) {
            this.mSpecifiers = new android.telephony.RadioAccessSpecifier[parcelableArr.length];
            for (int i = 0; i < parcelableArr.length; i++) {
                this.mSpecifiers[i] = (android.telephony.RadioAccessSpecifier) parcelableArr[i];
            }
        } else {
            this.mSpecifiers = null;
        }
        this.mSearchPeriodicity = parcel.readInt();
        this.mMaxSearchTime = parcel.readInt();
        this.mIncrementalResults = parcel.readBoolean();
        this.mIncrementalResultsPeriodicity = parcel.readInt();
        this.mMccMncs = new java.util.ArrayList<>();
        parcel.readStringList(this.mMccMncs);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.NetworkScanRequest)) {
            return false;
        }
        android.telephony.NetworkScanRequest networkScanRequest = (android.telephony.NetworkScanRequest) obj;
        return this.mScanType == networkScanRequest.mScanType && java.util.Arrays.equals(this.mSpecifiers, networkScanRequest.mSpecifiers) && this.mSearchPeriodicity == networkScanRequest.mSearchPeriodicity && this.mMaxSearchTime == networkScanRequest.mMaxSearchTime && this.mIncrementalResults == networkScanRequest.mIncrementalResults && this.mIncrementalResultsPeriodicity == networkScanRequest.mIncrementalResultsPeriodicity && java.util.Objects.equals(this.mMccMncs, networkScanRequest.mMccMncs);
    }

    public int hashCode() {
        return (this.mScanType * 31) + (java.util.Arrays.hashCode(this.mSpecifiers) * 37) + (this.mSearchPeriodicity * 41) + (this.mMaxSearchTime * 43) + ((!this.mIncrementalResults ? 0 : 1) * 47) + (this.mIncrementalResultsPeriodicity * 53) + (this.mMccMncs.hashCode() * 59);
    }
}
