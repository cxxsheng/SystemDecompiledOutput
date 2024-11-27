package android.text.style;

/* loaded from: classes3.dex */
public interface LeadingMarginSpan extends android.text.style.ParagraphStyle {

    public interface LeadingMarginSpan2 extends android.text.style.LeadingMarginSpan, android.text.style.WrapTogetherSpan {
        int getLeadingMarginLineCount();
    }

    void drawLeadingMargin(android.graphics.Canvas canvas, android.graphics.Paint paint, int i, int i2, int i3, int i4, int i5, java.lang.CharSequence charSequence, int i6, int i7, boolean z, android.text.Layout layout);

    int getLeadingMargin(boolean z);

    public static class Standard implements android.text.style.LeadingMarginSpan, android.text.ParcelableSpan {
        private final int mFirst;
        private final int mRest;

        public Standard(int i, int i2) {
            this.mFirst = i;
            this.mRest = i2;
        }

        public Standard(int i) {
            this(i, i);
        }

        public Standard(android.os.Parcel parcel) {
            this.mFirst = parcel.readInt();
            this.mRest = parcel.readInt();
        }

        @Override // android.text.ParcelableSpan
        public int getSpanTypeId() {
            return getSpanTypeIdInternal();
        }

        @Override // android.text.ParcelableSpan
        public int getSpanTypeIdInternal() {
            return 10;
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
            parcel.writeInt(this.mFirst);
            parcel.writeInt(this.mRest);
        }

        @Override // android.text.style.LeadingMarginSpan
        public int getLeadingMargin(boolean z) {
            return z ? this.mFirst : this.mRest;
        }

        @Override // android.text.style.LeadingMarginSpan
        public void drawLeadingMargin(android.graphics.Canvas canvas, android.graphics.Paint paint, int i, int i2, int i3, int i4, int i5, java.lang.CharSequence charSequence, int i6, int i7, boolean z, android.text.Layout layout) {
        }
    }
}
