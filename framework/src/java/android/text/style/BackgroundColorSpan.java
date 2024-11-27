package android.text.style;

/* loaded from: classes3.dex */
public class BackgroundColorSpan extends android.text.style.CharacterStyle implements android.text.style.UpdateAppearance, android.text.ParcelableSpan {
    private final int mColor;

    public BackgroundColorSpan(int i) {
        this.mColor = i;
    }

    public BackgroundColorSpan(android.os.Parcel parcel) {
        this.mColor = parcel.readInt();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 12;
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
        parcel.writeInt(this.mColor);
    }

    public int getBackgroundColor() {
        return this.mColor;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(android.text.TextPaint textPaint) {
        textPaint.bgColor = this.mColor;
    }

    public java.lang.String toString() {
        return "BackgroundColorSpan{color=#" + java.lang.String.format("%08X", java.lang.Integer.valueOf(getBackgroundColor())) + '}';
    }
}
