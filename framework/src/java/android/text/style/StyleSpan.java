package android.text.style;

/* loaded from: classes3.dex */
public class StyleSpan extends android.text.style.MetricAffectingSpan implements android.text.ParcelableSpan {
    private final int mFontWeightAdjustment;
    private final int mStyle;

    public StyleSpan(int i) {
        this(i, Integer.MAX_VALUE);
    }

    public StyleSpan(int i, int i2) {
        this.mStyle = i;
        this.mFontWeightAdjustment = i2;
    }

    public StyleSpan(android.os.Parcel parcel) {
        this.mStyle = parcel.readInt();
        this.mFontWeightAdjustment = parcel.readInt();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 7;
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
        parcel.writeInt(this.mStyle);
        parcel.writeInt(this.mFontWeightAdjustment);
    }

    public int getStyle() {
        return this.mStyle;
    }

    public int getFontWeightAdjustment() {
        return this.mFontWeightAdjustment;
    }

    @Override // android.text.style.CharacterStyle
    public void updateDrawState(android.text.TextPaint textPaint) {
        apply(textPaint, this.mStyle, this.mFontWeightAdjustment);
    }

    @Override // android.text.style.MetricAffectingSpan
    public void updateMeasureState(android.text.TextPaint textPaint) {
        apply(textPaint, this.mStyle, this.mFontWeightAdjustment);
    }

    private static void apply(android.graphics.Paint paint, int i, int i2) {
        int style;
        android.graphics.Typeface create;
        android.graphics.Typeface typeface = paint.getTypeface();
        boolean z = false;
        if (typeface == null) {
            style = 0;
        } else {
            style = typeface.getStyle();
        }
        int i3 = style | i;
        if (typeface == null) {
            create = android.graphics.Typeface.defaultFromStyle(i3);
        } else {
            create = android.graphics.Typeface.create(typeface, i3);
        }
        if ((i & 1) != 0 && i2 != 0 && i2 != Integer.MAX_VALUE) {
            int min = java.lang.Math.min(java.lang.Math.max(create.getWeight() + i2, 1), 1000);
            if ((i3 & 2) != 0) {
                z = true;
            }
            create = android.graphics.Typeface.create(create, min, z);
        }
        int i4 = (~create.getStyle()) & i3;
        if ((i4 & 1) != 0) {
            paint.setFakeBoldText(true);
        }
        if ((i4 & 2) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.setTypeface(create);
    }

    public java.lang.String toString() {
        return "StyleSpan{style=" + getStyle() + ", fontWeightAdjustment=" + getFontWeightAdjustment() + '}';
    }
}
