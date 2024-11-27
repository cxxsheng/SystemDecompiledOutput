package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class CorrectionInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.CorrectionInfo> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.CorrectionInfo>() { // from class: android.view.inputmethod.CorrectionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.CorrectionInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.CorrectionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.CorrectionInfo[] newArray(int i) {
            return new android.view.inputmethod.CorrectionInfo[i];
        }
    };
    private final java.lang.CharSequence mNewText;
    private final int mOffset;
    private final java.lang.CharSequence mOldText;

    public CorrectionInfo(int i, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        this.mOffset = i;
        this.mOldText = charSequence;
        this.mNewText = charSequence2;
    }

    private CorrectionInfo(android.os.Parcel parcel) {
        this.mOffset = parcel.readInt();
        this.mOldText = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mNewText = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }

    public int getOffset() {
        return this.mOffset;
    }

    public java.lang.CharSequence getOldText() {
        return this.mOldText;
    }

    public java.lang.CharSequence getNewText() {
        return this.mNewText;
    }

    public java.lang.String toString() {
        return "CorrectionInfo{#" + this.mOffset + " \"" + ((java.lang.Object) this.mOldText) + "\" -> \"" + ((java.lang.Object) this.mNewText) + "\"}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mOffset);
        android.text.TextUtils.writeToParcel(this.mOldText, parcel, i);
        android.text.TextUtils.writeToParcel(this.mNewText, parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
