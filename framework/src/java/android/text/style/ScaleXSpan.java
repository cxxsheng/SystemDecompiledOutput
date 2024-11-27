package android.text.style;

/* loaded from: classes3.dex */
public class ScaleXSpan extends android.text.style.MetricAffectingSpan implements android.text.ParcelableSpan {
    private final float mProportion;

    public ScaleXSpan(float f) {
        this.mProportion = f;
    }

    public ScaleXSpan(android.os.Parcel parcel) {
        this.mProportion = parcel.readFloat();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 4;
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
        parcel.writeFloat(this.mProportion);
    }

    public float getScaleX() {
        return this.mProportion;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(android.text.TextPaint textPaint) {
        textPaint.setTextScaleX(textPaint.getTextScaleX() * this.mProportion);
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(android.text.TextPaint textPaint) {
        textPaint.setTextScaleX(textPaint.getTextScaleX() * this.mProportion);
    }

    public java.lang.String toString() {
        return "ScaleXSpan{scaleX=" + getScaleX() + '}';
    }
}
