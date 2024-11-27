package android.service.credentials;

/* loaded from: classes3.dex */
public final class CredentialEntry implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.credentials.CredentialEntry> CREATOR = new android.os.Parcelable.Creator<android.service.credentials.CredentialEntry>() { // from class: android.service.credentials.CredentialEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.CredentialEntry createFromParcel(android.os.Parcel parcel) {
            return new android.service.credentials.CredentialEntry(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.CredentialEntry[] newArray(int i) {
            return new android.service.credentials.CredentialEntry[i];
        }
    };
    private final java.lang.String mBeginGetCredentialOptionId;
    private final android.app.slice.Slice mSlice;
    private final java.lang.String mType;

    public CredentialEntry(java.lang.String str, java.lang.String str2, android.app.slice.Slice slice) {
        this.mBeginGetCredentialOptionId = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "beginGetCredentialOptionId must not be null, or empty");
        this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str2, "type must not be null, or empty");
        this.mSlice = (android.app.slice.Slice) java.util.Objects.requireNonNull(slice, "slice must not be null");
    }

    public CredentialEntry(android.service.credentials.BeginGetCredentialOption beginGetCredentialOption, android.app.slice.Slice slice) {
        java.util.Objects.requireNonNull(beginGetCredentialOption, "beginGetCredentialOption must not be null");
        this.mBeginGetCredentialOptionId = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(beginGetCredentialOption.getId(), "Id in beginGetCredentialOption must not be null");
        this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(beginGetCredentialOption.getType(), "type in beginGetCredentialOption must not be null");
        this.mSlice = (android.app.slice.Slice) java.util.Objects.requireNonNull(slice, "slice must not be null");
    }

    public CredentialEntry(java.lang.String str, android.app.slice.Slice slice) {
        this.mBeginGetCredentialOptionId = null;
        this.mType = (java.lang.String) java.util.Objects.requireNonNull(str, "type must not be null");
        this.mSlice = (android.app.slice.Slice) java.util.Objects.requireNonNull(slice, "slice must not be null");
    }

    private CredentialEntry(android.os.Parcel parcel) {
        java.util.Objects.requireNonNull(parcel, "parcel must not be null");
        this.mType = parcel.readString8();
        this.mSlice = (android.app.slice.Slice) parcel.readTypedObject(android.app.slice.Slice.CREATOR);
        this.mBeginGetCredentialOptionId = parcel.readString8();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mType);
        parcel.writeTypedObject(this.mSlice, i);
        parcel.writeString8(this.mBeginGetCredentialOptionId);
    }

    public java.lang.String getBeginGetCredentialOptionId() {
        return this.mBeginGetCredentialOptionId;
    }

    public java.lang.String getType() {
        return this.mType;
    }

    public android.app.slice.Slice getSlice() {
        return this.mSlice;
    }
}
