package android.text.style;

/* loaded from: classes3.dex */
public class AccessibilityReplacementSpan extends android.text.style.ReplacementSpan implements android.text.ParcelableSpan {
    public static final android.os.Parcelable.Creator<android.text.style.AccessibilityReplacementSpan> CREATOR = new android.os.Parcelable.Creator<android.text.style.AccessibilityReplacementSpan>() { // from class: android.text.style.AccessibilityReplacementSpan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.text.style.AccessibilityReplacementSpan createFromParcel(android.os.Parcel parcel) {
            return new android.text.style.AccessibilityReplacementSpan(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.text.style.AccessibilityReplacementSpan[] newArray(int i) {
            return new android.text.style.AccessibilityReplacementSpan[i];
        }
    };

    public AccessibilityReplacementSpan(java.lang.CharSequence charSequence) {
        setContentDescription(charSequence);
    }

    public AccessibilityReplacementSpan(android.os.Parcel parcel) {
        setContentDescription(parcel.readCharSequence());
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 29;
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
        parcel.writeCharSequence(getContentDescription());
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(android.graphics.Paint paint, java.lang.CharSequence charSequence, int i, int i2, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        return 0;
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(android.graphics.Canvas canvas, java.lang.CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, android.graphics.Paint paint) {
    }
}
