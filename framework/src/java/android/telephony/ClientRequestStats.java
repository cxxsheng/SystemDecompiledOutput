package android.telephony;

/* loaded from: classes3.dex */
public final class ClientRequestStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ClientRequestStats> CREATOR = new android.os.Parcelable.Creator<android.telephony.ClientRequestStats>() { // from class: android.telephony.ClientRequestStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ClientRequestStats createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ClientRequestStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ClientRequestStats[] newArray(int i) {
            return new android.telephony.ClientRequestStats[i];
        }
    };
    private static final int REQUEST_HISTOGRAM_BUCKET_COUNT = 5;
    private java.lang.String mCallingPackage;
    private long mCompletedRequestsCount;
    private long mCompletedRequestsWakelockTime;
    private long mPendingRequestsCount;
    private long mPendingRequestsWakelockTime;
    private android.util.SparseArray<android.telephony.TelephonyHistogram> mRequestHistograms;

    public ClientRequestStats(android.os.Parcel parcel) {
        this.mCompletedRequestsWakelockTime = 0L;
        this.mCompletedRequestsCount = 0L;
        this.mPendingRequestsWakelockTime = 0L;
        this.mPendingRequestsCount = 0L;
        this.mRequestHistograms = new android.util.SparseArray<>();
        readFromParcel(parcel);
    }

    public ClientRequestStats() {
        this.mCompletedRequestsWakelockTime = 0L;
        this.mCompletedRequestsCount = 0L;
        this.mPendingRequestsWakelockTime = 0L;
        this.mPendingRequestsCount = 0L;
        this.mRequestHistograms = new android.util.SparseArray<>();
    }

    public ClientRequestStats(android.telephony.ClientRequestStats clientRequestStats) {
        this.mCompletedRequestsWakelockTime = 0L;
        this.mCompletedRequestsCount = 0L;
        this.mPendingRequestsWakelockTime = 0L;
        this.mPendingRequestsCount = 0L;
        this.mRequestHistograms = new android.util.SparseArray<>();
        this.mCallingPackage = clientRequestStats.getCallingPackage();
        this.mCompletedRequestsCount = clientRequestStats.getCompletedRequestsCount();
        this.mCompletedRequestsWakelockTime = clientRequestStats.getCompletedRequestsWakelockTime();
        this.mPendingRequestsCount = clientRequestStats.getPendingRequestsCount();
        this.mPendingRequestsWakelockTime = clientRequestStats.getPendingRequestsWakelockTime();
        for (android.telephony.TelephonyHistogram telephonyHistogram : clientRequestStats.getRequestHistograms()) {
            this.mRequestHistograms.put(telephonyHistogram.getId(), telephonyHistogram);
        }
    }

    public java.lang.String getCallingPackage() {
        return this.mCallingPackage;
    }

    public void setCallingPackage(java.lang.String str) {
        this.mCallingPackage = str;
    }

    public long getCompletedRequestsWakelockTime() {
        return this.mCompletedRequestsWakelockTime;
    }

    public void addCompletedWakelockTime(long j) {
        this.mCompletedRequestsWakelockTime += j;
    }

    public long getPendingRequestsWakelockTime() {
        return this.mPendingRequestsWakelockTime;
    }

    public void setPendingRequestsWakelockTime(long j) {
        this.mPendingRequestsWakelockTime = j;
    }

    public long getCompletedRequestsCount() {
        return this.mCompletedRequestsCount;
    }

    public void incrementCompletedRequestsCount() {
        this.mCompletedRequestsCount++;
    }

    public long getPendingRequestsCount() {
        return this.mPendingRequestsCount;
    }

    public void setPendingRequestsCount(long j) {
        this.mPendingRequestsCount = j;
    }

    public java.util.List<android.telephony.TelephonyHistogram> getRequestHistograms() {
        java.util.ArrayList arrayList;
        synchronized (this.mRequestHistograms) {
            arrayList = new java.util.ArrayList(this.mRequestHistograms.size());
            for (int i = 0; i < this.mRequestHistograms.size(); i++) {
                arrayList.add(new android.telephony.TelephonyHistogram(this.mRequestHistograms.valueAt(i)));
            }
        }
        return arrayList;
    }

    public void updateRequestHistograms(int i, int i2) {
        synchronized (this.mRequestHistograms) {
            android.telephony.TelephonyHistogram telephonyHistogram = this.mRequestHistograms.get(i);
            if (telephonyHistogram == null) {
                telephonyHistogram = new android.telephony.TelephonyHistogram(1, i, 5);
                this.mRequestHistograms.put(i, telephonyHistogram);
            }
            telephonyHistogram.addTimeTaken(i2);
        }
    }

    public java.lang.String toString() {
        return "ClientRequestStats{mCallingPackage='" + this.mCallingPackage + android.text.format.DateFormat.QUOTE + ", mCompletedRequestsWakelockTime=" + this.mCompletedRequestsWakelockTime + ", mCompletedRequestsCount=" + this.mCompletedRequestsCount + ", mPendingRequestsWakelockTime=" + this.mPendingRequestsWakelockTime + ", mPendingRequestsCount=" + this.mPendingRequestsCount + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mCallingPackage = parcel.readString();
        this.mCompletedRequestsWakelockTime = parcel.readLong();
        this.mCompletedRequestsCount = parcel.readLong();
        this.mPendingRequestsWakelockTime = parcel.readLong();
        this.mPendingRequestsCount = parcel.readLong();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.telephony.TelephonyHistogram.CREATOR);
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            android.telephony.TelephonyHistogram telephonyHistogram = (android.telephony.TelephonyHistogram) it.next();
            this.mRequestHistograms.put(telephonyHistogram.getId(), telephonyHistogram);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mCallingPackage);
        parcel.writeLong(this.mCompletedRequestsWakelockTime);
        parcel.writeLong(this.mCompletedRequestsCount);
        parcel.writeLong(this.mPendingRequestsWakelockTime);
        parcel.writeLong(this.mPendingRequestsCount);
        parcel.writeTypedList(getRequestHistograms());
    }
}
