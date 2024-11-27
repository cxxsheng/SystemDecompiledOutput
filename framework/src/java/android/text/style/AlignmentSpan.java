package android.text.style;

/* loaded from: classes3.dex */
public interface AlignmentSpan extends android.text.style.ParagraphStyle {
    android.text.Layout.Alignment getAlignment();

    public static class Standard implements android.text.style.AlignmentSpan, android.text.ParcelableSpan {
        private final android.text.Layout.Alignment mAlignment;

        public Standard(android.text.Layout.Alignment alignment) {
            this.mAlignment = alignment;
        }

        public Standard(android.os.Parcel parcel) {
            this.mAlignment = android.text.Layout.Alignment.valueOf(parcel.readString());
        }

        @Override // android.text.ParcelableSpan
        public int getSpanTypeId() {
            return getSpanTypeIdInternal();
        }

        @Override // android.text.ParcelableSpan
        public int getSpanTypeIdInternal() {
            return 1;
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
            parcel.writeString(this.mAlignment.name());
        }

        @Override // android.text.style.AlignmentSpan
        public android.text.Layout.Alignment getAlignment() {
            return this.mAlignment;
        }

        public java.lang.String toString() {
            return "AlignmentSpan.Standard{alignment=" + getAlignment() + '}';
        }
    }
}
