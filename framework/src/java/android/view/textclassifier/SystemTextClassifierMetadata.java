package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class SystemTextClassifierMetadata implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.textclassifier.SystemTextClassifierMetadata> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.SystemTextClassifierMetadata>() { // from class: android.view.textclassifier.SystemTextClassifierMetadata.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.SystemTextClassifierMetadata createFromParcel(android.os.Parcel parcel) {
            return android.view.textclassifier.SystemTextClassifierMetadata.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.SystemTextClassifierMetadata[] newArray(int i) {
            return new android.view.textclassifier.SystemTextClassifierMetadata[i];
        }
    };
    private final java.lang.String mCallingPackageName;
    private final boolean mUseDefaultTextClassifier;
    private final int mUserId;

    public SystemTextClassifierMetadata(java.lang.String str, int i, boolean z) {
        java.util.Objects.requireNonNull(str);
        this.mCallingPackageName = str;
        this.mUserId = i;
        this.mUseDefaultTextClassifier = z;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public java.lang.String getCallingPackageName() {
        return this.mCallingPackageName;
    }

    public boolean useDefaultTextClassifier() {
        return this.mUseDefaultTextClassifier;
    }

    public java.lang.String toString() {
        return java.lang.String.format(java.util.Locale.US, "SystemTextClassifierMetadata {callingPackageName=%s, userId=%d, useDefaultTextClassifier=%b}", this.mCallingPackageName, java.lang.Integer.valueOf(this.mUserId), java.lang.Boolean.valueOf(this.mUseDefaultTextClassifier));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.view.textclassifier.SystemTextClassifierMetadata readFromParcel(android.os.Parcel parcel) {
        return new android.view.textclassifier.SystemTextClassifierMetadata(parcel.readString(), parcel.readInt(), parcel.readBoolean());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mCallingPackageName);
        parcel.writeInt(this.mUserId);
        parcel.writeBoolean(this.mUseDefaultTextClassifier);
    }
}
