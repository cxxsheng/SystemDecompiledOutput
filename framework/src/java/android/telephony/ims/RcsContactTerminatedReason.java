package android.telephony.ims;

/* loaded from: classes3.dex */
public final class RcsContactTerminatedReason implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.RcsContactTerminatedReason> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.RcsContactTerminatedReason>() { // from class: android.telephony.ims.RcsContactTerminatedReason.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.RcsContactTerminatedReason createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.RcsContactTerminatedReason(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.RcsContactTerminatedReason[] newArray(int i) {
            return new android.telephony.ims.RcsContactTerminatedReason[i];
        }
    };
    private final android.net.Uri mContactUri;
    private final java.lang.String mReason;

    public RcsContactTerminatedReason(android.net.Uri uri, java.lang.String str) {
        this.mContactUri = uri;
        this.mReason = str;
    }

    private RcsContactTerminatedReason(android.os.Parcel parcel) {
        this.mContactUri = (android.net.Uri) parcel.readParcelable(android.net.Uri.class.getClassLoader(), android.net.Uri.class);
        this.mReason = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mContactUri, i);
        parcel.writeString(this.mReason);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.net.Uri getContactUri() {
        return this.mContactUri;
    }

    public java.lang.String getReason() {
        return this.mReason;
    }
}
