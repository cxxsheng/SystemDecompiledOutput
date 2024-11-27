package android.printservice.recommendation;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class RecommendationInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.printservice.recommendation.RecommendationInfo> CREATOR = new android.os.Parcelable.Creator<android.printservice.recommendation.RecommendationInfo>() { // from class: android.printservice.recommendation.RecommendationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.printservice.recommendation.RecommendationInfo createFromParcel(android.os.Parcel parcel) {
            return new android.printservice.recommendation.RecommendationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.printservice.recommendation.RecommendationInfo[] newArray(int i) {
            return new android.printservice.recommendation.RecommendationInfo[i];
        }
    };
    private final java.util.List<java.net.InetAddress> mDiscoveredPrinters;
    private final java.lang.CharSequence mName;
    private final java.lang.CharSequence mPackageName;
    private final boolean mRecommendsMultiVendorService;

    public RecommendationInfo(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.util.List<java.net.InetAddress> list, boolean z) {
        this.mPackageName = com.android.internal.util.Preconditions.checkStringNotEmpty(charSequence);
        this.mName = com.android.internal.util.Preconditions.checkStringNotEmpty(charSequence2);
        this.mDiscoveredPrinters = (java.util.List) com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "discoveredPrinters");
        this.mRecommendsMultiVendorService = z;
    }

    @java.lang.Deprecated
    public RecommendationInfo(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, int i, boolean z) {
        throw new java.lang.IllegalArgumentException("This constructor has been deprecated");
    }

    private static java.util.ArrayList<java.net.InetAddress> readDiscoveredPrinters(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        java.util.ArrayList<java.net.InetAddress> arrayList = new java.util.ArrayList<>(readInt);
        for (int i = 0; i < readInt; i++) {
            try {
                arrayList.add(java.net.InetAddress.getByAddress(parcel.readBlob()));
            } catch (java.net.UnknownHostException e) {
                throw new java.lang.IllegalArgumentException(e);
            }
        }
        return arrayList;
    }

    private RecommendationInfo(android.os.Parcel parcel) {
        this(parcel.readCharSequence(), parcel.readCharSequence(), readDiscoveredPrinters(parcel), parcel.readByte() != 0);
    }

    public java.lang.CharSequence getPackageName() {
        return this.mPackageName;
    }

    public boolean recommendsMultiVendorService() {
        return this.mRecommendsMultiVendorService;
    }

    public java.util.List<java.net.InetAddress> getDiscoveredPrinters() {
        return this.mDiscoveredPrinters;
    }

    public int getNumDiscoveredPrinters() {
        return this.mDiscoveredPrinters.size();
    }

    public java.lang.CharSequence getName() {
        return this.mName;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeCharSequence(this.mPackageName);
        parcel.writeCharSequence(this.mName);
        parcel.writeInt(this.mDiscoveredPrinters.size());
        java.util.Iterator<java.net.InetAddress> it = this.mDiscoveredPrinters.iterator();
        while (it.hasNext()) {
            parcel.writeBlob(it.next().getAddress());
        }
        parcel.writeByte(this.mRecommendsMultiVendorService ? (byte) 1 : (byte) 0);
    }
}
