package android.text.style;

/* loaded from: classes3.dex */
public class SubscriptSpan extends android.text.style.MetricAffectingSpan implements android.text.ParcelableSpan {
    public SubscriptSpan() {
    }

    public SubscriptSpan(android.os.Parcel parcel) {
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 15;
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
        textPaint.baselineShift -= (int) (textPaint.ascent() / 2.0f);
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(android.text.TextPaint textPaint) {
        textPaint.baselineShift -= (int) (textPaint.ascent() / 2.0f);
    }

    public java.lang.String toString() {
        return "SubscriptSpan{}";
    }
}
