package android.text.style;

/* loaded from: classes3.dex */
public final class LineBreakConfigSpan implements android.text.ParcelableSpan {
    private final android.graphics.text.LineBreakConfig mLineBreakConfig;
    private static final android.graphics.text.LineBreakConfig sNoHyphenationConfig = new android.graphics.text.LineBreakConfig.Builder().setHyphenation(0).build();
    private static final android.graphics.text.LineBreakConfig sNoBreakConfig = new android.graphics.text.LineBreakConfig.Builder().setLineBreakStyle(4).build();
    public static final android.os.Parcelable.Creator<android.text.style.LineBreakConfigSpan> CREATOR = new android.os.Parcelable.Creator<android.text.style.LineBreakConfigSpan>() { // from class: android.text.style.LineBreakConfigSpan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.text.style.LineBreakConfigSpan createFromParcel(android.os.Parcel parcel) {
            return new android.text.style.LineBreakConfigSpan((android.graphics.text.LineBreakConfig) parcel.readParcelable(android.graphics.text.LineBreakConfig.class.getClassLoader(), android.graphics.text.LineBreakConfig.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.text.style.LineBreakConfigSpan[] newArray(int i) {
            return new android.text.style.LineBreakConfigSpan[i];
        }
    };

    public LineBreakConfigSpan(android.graphics.text.LineBreakConfig lineBreakConfig) {
        this.mLineBreakConfig = lineBreakConfig;
    }

    public android.graphics.text.LineBreakConfig getLineBreakConfig() {
        return this.mLineBreakConfig;
    }

    public static android.text.style.LineBreakConfigSpan createNoBreakSpan() {
        return new android.text.style.LineBreakConfigSpan(sNoBreakConfig);
    }

    public static android.text.style.LineBreakConfigSpan createNoHyphenationSpan() {
        return new android.text.style.LineBreakConfigSpan(sNoHyphenationConfig);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof android.text.style.LineBreakConfigSpan) {
            return java.util.Objects.equals(this.mLineBreakConfig, ((android.text.style.LineBreakConfigSpan) obj).mLineBreakConfig);
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mLineBreakConfig);
    }

    public java.lang.String toString() {
        return "LineBreakConfigSpan{mLineBreakConfig=" + this.mLineBreakConfig + '}';
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
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 30;
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mLineBreakConfig, i);
    }
}
