package android.text.style;

/* loaded from: classes3.dex */
public interface LineBackgroundSpan extends android.text.style.ParagraphStyle {
    void drawBackground(android.graphics.Canvas canvas, android.graphics.Paint paint, int i, int i2, int i3, int i4, int i5, java.lang.CharSequence charSequence, int i6, int i7, int i8);

    public static class Standard implements android.text.style.LineBackgroundSpan, android.text.ParcelableSpan {
        private final int mColor;

        public Standard(int i) {
            this.mColor = i;
        }

        public Standard(android.os.Parcel parcel) {
            this.mColor = parcel.readInt();
        }

        @Override // android.text.ParcelableSpan
        public int getSpanTypeId() {
            return getSpanTypeIdInternal();
        }

        @Override // android.text.ParcelableSpan
        public int getSpanTypeIdInternal() {
            return 27;
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

        public final int getColor() {
            return this.mColor;
        }

        @Override // android.text.style.LineBackgroundSpan
        public void drawBackground(android.graphics.Canvas canvas, android.graphics.Paint paint, int i, int i2, int i3, int i4, int i5, java.lang.CharSequence charSequence, int i6, int i7, int i8) {
            int color = paint.getColor();
            paint.setColor(this.mColor);
            canvas.drawRect(i, i3, i2, i5, paint);
            paint.setColor(color);
        }
    }
}
