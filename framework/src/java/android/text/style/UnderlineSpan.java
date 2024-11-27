package android.text.style;

/* loaded from: classes3.dex */
public class UnderlineSpan extends android.text.style.CharacterStyle implements android.text.style.UpdateAppearance, android.text.ParcelableSpan {
    public UnderlineSpan() {
    }

    public UnderlineSpan(android.os.Parcel parcel) {
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 6;
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
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(android.text.TextPaint textPaint) {
        textPaint.setUnderlineText(true);
    }

    public java.lang.String toString() {
        return "UnderlineSpan{}";
    }
}