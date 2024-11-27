package android.text.style;

/* loaded from: classes3.dex */
public class QuoteSpan implements android.text.style.LeadingMarginSpan, android.text.ParcelableSpan {
    public static final int STANDARD_COLOR = -16776961;
    public static final int STANDARD_GAP_WIDTH_PX = 2;
    public static final int STANDARD_STRIPE_WIDTH_PX = 2;
    private final int mColor;
    private final int mGapWidth;
    private final int mStripeWidth;

    public QuoteSpan() {
        this(-16776961, 2, 2);
    }

    public QuoteSpan(int i) {
        this(i, 2, 2);
    }

    public QuoteSpan(int i, int i2, int i3) {
        this.mColor = i;
        this.mStripeWidth = i2;
        this.mGapWidth = i3;
    }

    public QuoteSpan(android.os.Parcel parcel) {
        this.mColor = parcel.readInt();
        this.mStripeWidth = parcel.readInt();
        this.mGapWidth = parcel.readInt();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 9;
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
        parcel.writeInt(this.mStripeWidth);
        parcel.writeInt(this.mGapWidth);
    }

    public int getColor() {
        return this.mColor;
    }

    public int getStripeWidth() {
        return this.mStripeWidth;
    }

    public int getGapWidth() {
        return this.mGapWidth;
    }

    @Override // android.text.style.LeadingMarginSpan
    public int getLeadingMargin(boolean z) {
        return this.mStripeWidth + this.mGapWidth;
    }

    @Override // android.text.style.LeadingMarginSpan
    public void drawLeadingMargin(android.graphics.Canvas canvas, android.graphics.Paint paint, int i, int i2, int i3, int i4, int i5, java.lang.CharSequence charSequence, int i6, int i7, boolean z, android.text.Layout layout) {
        android.graphics.Paint.Style style = paint.getStyle();
        int color = paint.getColor();
        paint.setStyle(android.graphics.Paint.Style.FILL);
        paint.setColor(this.mColor);
        canvas.drawRect(i, i3, (this.mStripeWidth * i2) + i, i5, paint);
        paint.setStyle(style);
        paint.setColor(color);
    }

    public java.lang.String toString() {
        return "QuoteSpan{color=" + java.lang.String.format("#%08X", java.lang.Integer.valueOf(getColor())) + ", stripeWidth=" + getStripeWidth() + ", gapWidth=" + getGapWidth() + '}';
    }
}
