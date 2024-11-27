package android.text.style;

/* loaded from: classes3.dex */
public interface LineHeightSpan extends android.text.style.ParagraphStyle, android.text.style.WrapTogetherSpan {

    public interface WithDensity extends android.text.style.LineHeightSpan {
        void chooseHeight(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, android.graphics.Paint.FontMetricsInt fontMetricsInt, android.text.TextPaint textPaint);
    }

    void chooseHeight(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, android.graphics.Paint.FontMetricsInt fontMetricsInt);

    public static class Standard implements android.text.style.LineHeightSpan, android.text.ParcelableSpan {
        private final int mHeight;

        public Standard(int i) {
            com.android.internal.util.Preconditions.checkArgument(i > 0, "Height: %d must be positive", java.lang.Integer.valueOf(i));
            this.mHeight = i;
        }

        public Standard(android.os.Parcel parcel) {
            this.mHeight = parcel.readInt();
        }

        public int getHeight() {
            return this.mHeight;
        }

        @Override // android.text.ParcelableSpan
        public int getSpanTypeId() {
            return getSpanTypeIdInternal();
        }

        @Override // android.text.ParcelableSpan
        public int getSpanTypeIdInternal() {
            return 28;
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
            parcel.writeInt(this.mHeight);
        }

        @Override // android.text.style.LineHeightSpan
        public void chooseHeight(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
            int i5 = fontMetricsInt.descent - fontMetricsInt.ascent;
            if (i5 <= 0) {
                return;
            }
            fontMetricsInt.descent = java.lang.Math.round(fontMetricsInt.descent * ((this.mHeight * 1.0f) / i5));
            fontMetricsInt.ascent = fontMetricsInt.descent - this.mHeight;
        }
    }
}
