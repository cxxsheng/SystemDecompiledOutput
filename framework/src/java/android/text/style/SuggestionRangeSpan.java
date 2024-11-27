package android.text.style;

/* loaded from: classes3.dex */
public final class SuggestionRangeSpan extends android.text.style.CharacterStyle implements android.text.ParcelableSpan {
    public static final android.os.Parcelable.Creator<android.text.style.SuggestionRangeSpan> CREATOR = new android.os.Parcelable.Creator<android.text.style.SuggestionRangeSpan>() { // from class: android.text.style.SuggestionRangeSpan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.text.style.SuggestionRangeSpan createFromParcel(android.os.Parcel parcel) {
            return new android.text.style.SuggestionRangeSpan(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.text.style.SuggestionRangeSpan[] newArray(int i) {
            return new android.text.style.SuggestionRangeSpan[i];
        }
    };
    private int mBackgroundColor;

    public SuggestionRangeSpan() {
        this.mBackgroundColor = 0;
    }

    public SuggestionRangeSpan(android.os.Parcel parcel) {
        this.mBackgroundColor = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mBackgroundColor);
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 21;
    }

    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(android.text.TextPaint textPaint) {
        textPaint.bgColor = this.mBackgroundColor;
    }
}
