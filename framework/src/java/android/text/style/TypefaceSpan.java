package android.text.style;

/* loaded from: classes3.dex */
public class TypefaceSpan extends android.text.style.MetricAffectingSpan implements android.text.ParcelableSpan {
    private final java.lang.String mFamily;
    private final android.graphics.Typeface mTypeface;

    public TypefaceSpan(java.lang.String str) {
        this(str, null);
    }

    public TypefaceSpan(android.graphics.Typeface typeface) {
        this(null, typeface);
    }

    public TypefaceSpan(android.os.Parcel parcel) {
        this.mFamily = parcel.readString();
        this.mTypeface = android.graphics.LeakyTypefaceStorage.readTypefaceFromParcel(parcel);
    }

    private TypefaceSpan(java.lang.String str, android.graphics.Typeface typeface) {
        this.mFamily = str;
        this.mTypeface = typeface;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 13;
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
        parcel.writeString(this.mFamily);
        android.graphics.LeakyTypefaceStorage.writeTypefaceToParcel(this.mTypeface, parcel);
    }

    public java.lang.String getFamily() {
        return this.mFamily;
    }

    public android.graphics.Typeface getTypeface() {
        return this.mTypeface;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(android.text.TextPaint textPaint) {
        updateTypeface(textPaint);
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(android.text.TextPaint textPaint) {
        updateTypeface(textPaint);
    }

    private void updateTypeface(android.graphics.Paint paint) {
        if (this.mTypeface != null) {
            paint.setTypeface(this.mTypeface);
        } else if (this.mFamily != null) {
            applyFontFamily(paint, this.mFamily);
        }
    }

    private void applyFontFamily(android.graphics.Paint paint, java.lang.String str) {
        int style;
        android.graphics.Typeface typeface = paint.getTypeface();
        if (typeface == null) {
            style = 0;
        } else {
            style = typeface.getStyle();
        }
        android.graphics.Typeface create = android.graphics.Typeface.create(str, style);
        int i = style & (~create.getStyle());
        if ((i & 1) != 0) {
            paint.setFakeBoldText(true);
        }
        if ((i & 2) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.setTypeface(create);
    }

    public java.lang.String toString() {
        return "TypefaceSpan{family='" + getFamily() + android.text.format.DateFormat.QUOTE + ", typeface=" + getTypeface() + '}';
    }
}
