package android.speech;

/* loaded from: classes3.dex */
public final class AlternativeSpans implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.speech.AlternativeSpans> CREATOR = new android.os.Parcelable.Creator<android.speech.AlternativeSpans>() { // from class: android.speech.AlternativeSpans.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.speech.AlternativeSpans[] newArray(int i) {
            return new android.speech.AlternativeSpans[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.speech.AlternativeSpans createFromParcel(android.os.Parcel parcel) {
            return new android.speech.AlternativeSpans(parcel);
        }
    };
    private final java.util.List<android.speech.AlternativeSpan> mSpans;

    public AlternativeSpans(java.util.List<android.speech.AlternativeSpan> list) {
        this.mSpans = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSpans);
    }

    public java.util.List<android.speech.AlternativeSpan> getSpans() {
        return this.mSpans;
    }

    public java.lang.String toString() {
        return "AlternativeSpans { spans = " + this.mSpans + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(this.mSpans, ((android.speech.AlternativeSpans) obj).mSpans);
    }

    public int hashCode() {
        return 31 + java.util.Objects.hashCode(this.mSpans);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelableList(this.mSpans, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AlternativeSpans(android.os.Parcel parcel) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readParcelableList(arrayList, android.speech.AlternativeSpan.class.getClassLoader(), android.speech.AlternativeSpan.class);
        this.mSpans = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSpans);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
