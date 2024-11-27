package android.app;

/* loaded from: classes.dex */
public class RemoteInputHistoryItem implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.RemoteInputHistoryItem> CREATOR = new android.os.Parcelable.Creator<android.app.RemoteInputHistoryItem>() { // from class: android.app.RemoteInputHistoryItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RemoteInputHistoryItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.RemoteInputHistoryItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RemoteInputHistoryItem[] newArray(int i) {
            return new android.app.RemoteInputHistoryItem[i];
        }
    };
    private java.lang.String mMimeType;
    private java.lang.CharSequence mText;
    private android.net.Uri mUri;

    public RemoteInputHistoryItem(java.lang.String str, android.net.Uri uri, java.lang.CharSequence charSequence) {
        this.mMimeType = str;
        this.mUri = uri;
        this.mText = android.app.Notification.safeCharSequence(charSequence);
    }

    public RemoteInputHistoryItem(java.lang.CharSequence charSequence) {
        this.mText = android.app.Notification.safeCharSequence(charSequence);
    }

    protected RemoteInputHistoryItem(android.os.Parcel parcel) {
        this.mText = parcel.readCharSequence();
        this.mMimeType = parcel.readStringNoHelper();
        this.mUri = (android.net.Uri) parcel.readParcelable(android.net.Uri.class.getClassLoader(), android.net.Uri.class);
    }

    public java.lang.CharSequence getText() {
        return this.mText;
    }

    public java.lang.String getMimeType() {
        return this.mMimeType;
    }

    public android.net.Uri getUri() {
        return this.mUri;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeCharSequence(this.mText);
        parcel.writeStringNoHelper(this.mMimeType);
        parcel.writeParcelable(this.mUri, i);
    }
}
