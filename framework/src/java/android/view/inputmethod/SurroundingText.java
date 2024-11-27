package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class SurroundingText implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.SurroundingText> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.SurroundingText>() { // from class: android.view.inputmethod.SurroundingText.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.SurroundingText createFromParcel(android.os.Parcel parcel) {
            java.lang.CharSequence createFromParcel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            if (createFromParcel == null) {
                createFromParcel = "";
            }
            return new android.view.inputmethod.SurroundingText(createFromParcel, readInt, readInt2, readInt3);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.SurroundingText[] newArray(int i) {
            return new android.view.inputmethod.SurroundingText[i];
        }
    };
    private final int mOffset;
    private final int mSelectionEnd;
    private final int mSelectionStart;
    private final java.lang.CharSequence mText;

    public SurroundingText(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        this.mText = copyWithParcelableSpans(charSequence);
        this.mSelectionStart = i;
        this.mSelectionEnd = i2;
        this.mOffset = i3;
    }

    public java.lang.CharSequence getText() {
        return this.mText;
    }

    public int getSelectionStart() {
        return this.mSelectionStart;
    }

    public int getSelectionEnd() {
        return this.mSelectionEnd;
    }

    public int getOffset() {
        return this.mOffset;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.text.TextUtils.writeToParcel(this.mText, parcel, i);
        parcel.writeInt(this.mSelectionStart);
        parcel.writeInt(this.mSelectionEnd);
        parcel.writeInt(this.mOffset);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static java.lang.CharSequence copyWithParcelableSpans(java.lang.CharSequence charSequence) {
        android.os.Parcel parcel = null;
        if (charSequence == null) {
            return null;
        }
        try {
            parcel = android.os.Parcel.obtain();
            android.text.TextUtils.writeToParcel(charSequence, parcel, 0);
            parcel.setDataPosition(0);
            return android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        } finally {
            if (parcel != null) {
                parcel.recycle();
            }
        }
    }

    public boolean isEqualTo(android.view.inputmethod.SurroundingText surroundingText) {
        if (surroundingText == null) {
            return false;
        }
        if (this == surroundingText) {
            return true;
        }
        if (this.mSelectionStart != surroundingText.mSelectionStart || this.mSelectionEnd != surroundingText.mSelectionEnd || this.mOffset != surroundingText.mOffset || !android.text.TextUtils.equals(this.mText, surroundingText.mText)) {
            return false;
        }
        return true;
    }
}
