package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class CompletionInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.CompletionInfo> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.CompletionInfo>() { // from class: android.view.inputmethod.CompletionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.CompletionInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.CompletionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.CompletionInfo[] newArray(int i) {
            return new android.view.inputmethod.CompletionInfo[i];
        }
    };
    private final long mId;
    private final java.lang.CharSequence mLabel;
    private final int mPosition;
    private final java.lang.CharSequence mText;

    public CompletionInfo(long j, int i, java.lang.CharSequence charSequence) {
        this.mId = j;
        this.mPosition = i;
        this.mText = charSequence;
        this.mLabel = null;
    }

    public CompletionInfo(long j, int i, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        this.mId = j;
        this.mPosition = i;
        this.mText = charSequence;
        this.mLabel = charSequence2;
    }

    private CompletionInfo(android.os.Parcel parcel) {
        this.mId = parcel.readLong();
        this.mPosition = parcel.readInt();
        this.mText = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mLabel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }

    public long getId() {
        return this.mId;
    }

    public int getPosition() {
        return this.mPosition;
    }

    public java.lang.CharSequence getText() {
        return this.mText;
    }

    public java.lang.CharSequence getLabel() {
        return this.mLabel;
    }

    public java.lang.String toString() {
        return "CompletionInfo{#" + this.mPosition + " \"" + ((java.lang.Object) this.mText) + "\" id=" + this.mId + " label=" + ((java.lang.Object) this.mLabel) + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mId);
        parcel.writeInt(this.mPosition);
        android.text.TextUtils.writeToParcel(this.mText, parcel, i);
        android.text.TextUtils.writeToParcel(this.mLabel, parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
