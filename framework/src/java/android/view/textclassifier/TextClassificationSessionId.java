package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class TextClassificationSessionId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.textclassifier.TextClassificationSessionId> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextClassificationSessionId>() { // from class: android.view.textclassifier.TextClassificationSessionId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextClassificationSessionId createFromParcel(android.os.Parcel parcel) {
            return new android.view.textclassifier.TextClassificationSessionId(parcel.readString(), parcel.readStrongBinder());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextClassificationSessionId[] newArray(int i) {
            return new android.view.textclassifier.TextClassificationSessionId[i];
        }
    };
    private final android.os.IBinder mToken;
    private final java.lang.String mValue;

    public TextClassificationSessionId() {
        this(java.util.UUID.randomUUID().toString(), new android.os.Binder());
    }

    public TextClassificationSessionId(java.lang.String str, android.os.IBinder iBinder) {
        this.mValue = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mToken = (android.os.IBinder) java.util.Objects.requireNonNull(iBinder);
    }

    public android.os.IBinder getToken() {
        return this.mToken;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.textclassifier.TextClassificationSessionId textClassificationSessionId = (android.view.textclassifier.TextClassificationSessionId) obj;
        if (java.util.Objects.equals(this.mValue, textClassificationSessionId.mValue) && java.util.Objects.equals(this.mToken, textClassificationSessionId.mToken)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mValue, this.mToken);
    }

    public java.lang.String toString() {
        return java.lang.String.format(java.util.Locale.US, "TextClassificationSessionId {%s}", this.mValue);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mValue);
        parcel.writeStrongBinder(this.mToken);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String getValue() {
        return this.mValue;
    }
}
