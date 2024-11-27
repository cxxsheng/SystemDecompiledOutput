package android.app.blob;

/* loaded from: classes.dex */
public final class BlobInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.blob.BlobInfo> CREATOR = new android.os.Parcelable.Creator<android.app.blob.BlobInfo>() { // from class: android.app.blob.BlobInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.blob.BlobInfo createFromParcel(android.os.Parcel parcel) {
            return new android.app.blob.BlobInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.blob.BlobInfo[] newArray(int i) {
            return new android.app.blob.BlobInfo[i];
        }
    };
    private final long mExpiryTimeMs;
    private final long mId;
    private final java.lang.CharSequence mLabel;
    private final java.util.List<android.app.blob.LeaseInfo> mLeaseInfos;
    private final long mSizeBytes;

    public BlobInfo(long j, long j2, java.lang.CharSequence charSequence, long j3, java.util.List<android.app.blob.LeaseInfo> list) {
        this.mId = j;
        this.mExpiryTimeMs = j2;
        this.mLabel = charSequence;
        this.mSizeBytes = j3;
        this.mLeaseInfos = list;
    }

    private BlobInfo(android.os.Parcel parcel) {
        this.mId = parcel.readLong();
        this.mExpiryTimeMs = parcel.readLong();
        this.mLabel = parcel.readCharSequence();
        this.mSizeBytes = parcel.readLong();
        this.mLeaseInfos = parcel.readArrayList(null);
    }

    public long getId() {
        return this.mId;
    }

    public long getExpiryTimeMs() {
        return this.mExpiryTimeMs;
    }

    public java.lang.CharSequence getLabel() {
        return this.mLabel;
    }

    public long getSizeBytes() {
        return this.mSizeBytes;
    }

    public java.util.List<android.app.blob.LeaseInfo> getLeases() {
        return java.util.Collections.unmodifiableList(this.mLeaseInfos);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mId);
        parcel.writeLong(this.mExpiryTimeMs);
        parcel.writeCharSequence(this.mLabel);
        parcel.writeLong(this.mSizeBytes);
        parcel.writeList(this.mLeaseInfos);
    }

    public java.lang.String toString() {
        return toShortString();
    }

    private java.lang.String toShortString() {
        return "BlobInfo {id: " + this.mId + ",expiryMs: " + this.mExpiryTimeMs + ",label: " + ((java.lang.Object) this.mLabel) + ",size: " + formatBlobSize(this.mSizeBytes) + ",leases: " + android.app.blob.LeaseInfo.toShortString(this.mLeaseInfos) + ",}";
    }

    private static java.lang.String formatBlobSize(long j) {
        return android.text.format.Formatter.formatFileSize(android.app.AppGlobals.getInitialApplication(), j, 8);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
