package android.view.inputmethod;

/* loaded from: classes4.dex */
public class ExtractedText implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.ExtractedText> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.ExtractedText>() { // from class: android.view.inputmethod.ExtractedText.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.ExtractedText createFromParcel(android.os.Parcel parcel) {
            android.view.inputmethod.ExtractedText extractedText = new android.view.inputmethod.ExtractedText();
            extractedText.text = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            extractedText.startOffset = parcel.readInt();
            extractedText.partialStartOffset = parcel.readInt();
            extractedText.partialEndOffset = parcel.readInt();
            extractedText.selectionStart = parcel.readInt();
            extractedText.selectionEnd = parcel.readInt();
            extractedText.flags = parcel.readInt();
            extractedText.hint = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            return extractedText;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.ExtractedText[] newArray(int i) {
            return new android.view.inputmethod.ExtractedText[i];
        }
    };
    public static final int FLAG_SELECTING = 2;
    public static final int FLAG_SINGLE_LINE = 1;
    public int flags;
    public java.lang.CharSequence hint;
    public int partialEndOffset;
    public int partialStartOffset;
    public int selectionEnd;
    public int selectionStart;
    public int startOffset;
    public java.lang.CharSequence text;

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.text.TextUtils.writeToParcel(this.text, parcel, i);
        parcel.writeInt(this.startOffset);
        parcel.writeInt(this.partialStartOffset);
        parcel.writeInt(this.partialEndOffset);
        parcel.writeInt(this.selectionStart);
        parcel.writeInt(this.selectionEnd);
        parcel.writeInt(this.flags);
        android.text.TextUtils.writeToParcel(this.hint, parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
