package android.text.style;

/* loaded from: classes3.dex */
public class BulletSpan implements android.text.style.LeadingMarginSpan, android.text.ParcelableSpan {
    private static final int STANDARD_BULLET_RADIUS = 4;
    private static final int STANDARD_COLOR = 0;
    public static final int STANDARD_GAP_WIDTH = 2;
    private final int mBulletRadius;
    private final int mColor;
    private final int mGapWidth;
    private final boolean mWantColor;

    public BulletSpan() {
        this(2, 0, false, 4);
    }

    public BulletSpan(int i) {
        this(i, 0, false, 4);
    }

    public BulletSpan(int i, int i2) {
        this(i, i2, true, 4);
    }

    public BulletSpan(int i, int i2, int i3) {
        this(i, i2, true, i3);
    }

    private BulletSpan(int i, int i2, boolean z, int i3) {
        this.mGapWidth = i;
        this.mBulletRadius = i3;
        this.mColor = i2;
        this.mWantColor = z;
    }

    public BulletSpan(android.os.Parcel parcel) {
        this.mGapWidth = parcel.readInt();
        this.mWantColor = parcel.readInt() != 0;
        this.mColor = parcel.readInt();
        this.mBulletRadius = parcel.readInt();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 8;
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
        parcel.writeInt(this.mGapWidth);
        parcel.writeInt(this.mWantColor ? 1 : 0);
        parcel.writeInt(this.mColor);
        parcel.writeInt(this.mBulletRadius);
    }

    @Override // android.text.style.LeadingMarginSpan
    public int getLeadingMargin(boolean z) {
        return (this.mBulletRadius * 2) + this.mGapWidth;
    }

    public int getGapWidth() {
        return this.mGapWidth;
    }

    public int getBulletRadius() {
        return this.mBulletRadius;
    }

    public int getColor() {
        return this.mColor;
    }

    @Override // android.text.style.LeadingMarginSpan
    public void drawLeadingMargin(android.graphics.Canvas canvas, android.graphics.Paint paint, int i, int i2, int i3, int i4, int i5, java.lang.CharSequence charSequence, int i6, int i7, boolean z, android.text.Layout layout) {
        int i8;
        if (((android.text.Spanned) charSequence).getSpanStart(this) == i6) {
            android.graphics.Paint.Style style = paint.getStyle();
            if (!this.mWantColor) {
                i8 = 0;
            } else {
                i8 = paint.getColor();
                paint.setColor(this.mColor);
            }
            paint.setStyle(android.graphics.Paint.Style.FILL);
            if (layout != null) {
                i5 -= layout.getLineExtra(layout.getLineForOffset(i6));
            }
            canvas.drawCircle(i + (i2 * this.mBulletRadius), (i3 + i5) / 2.0f, this.mBulletRadius, paint);
            if (this.mWantColor) {
                paint.setColor(i8);
            }
            paint.setStyle(style);
        }
    }

    public java.lang.String toString() {
        return "BulletSpan{gapWidth=" + getGapWidth() + ", bulletRadius=" + getBulletRadius() + ", color=" + java.lang.String.format("%08X", java.lang.Integer.valueOf(getColor())) + '}';
    }
}
