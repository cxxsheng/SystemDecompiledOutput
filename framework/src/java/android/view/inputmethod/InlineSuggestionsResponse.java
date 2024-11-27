package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class InlineSuggestionsResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.InlineSuggestionsResponse> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.InlineSuggestionsResponse>() { // from class: android.view.inputmethod.InlineSuggestionsResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InlineSuggestionsResponse[] newArray(int i) {
            return new android.view.inputmethod.InlineSuggestionsResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InlineSuggestionsResponse createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.InlineSuggestionsResponse(parcel);
        }
    };
    private final java.util.List<android.view.inputmethod.InlineSuggestion> mInlineSuggestions;

    public static android.view.inputmethod.InlineSuggestionsResponse newInlineSuggestionsResponse(java.util.List<android.view.inputmethod.InlineSuggestion> list) {
        return new android.view.inputmethod.InlineSuggestionsResponse(list);
    }

    public InlineSuggestionsResponse(java.util.List<android.view.inputmethod.InlineSuggestion> list) {
        this.mInlineSuggestions = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInlineSuggestions);
    }

    public java.util.List<android.view.inputmethod.InlineSuggestion> getInlineSuggestions() {
        return this.mInlineSuggestions;
    }

    public java.lang.String toString() {
        return "InlineSuggestionsResponse { inlineSuggestions = " + this.mInlineSuggestions + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(this.mInlineSuggestions, ((android.view.inputmethod.InlineSuggestionsResponse) obj).mInlineSuggestions);
    }

    public int hashCode() {
        return 31 + java.util.Objects.hashCode(this.mInlineSuggestions);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelableList(this.mInlineSuggestions, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    InlineSuggestionsResponse(android.os.Parcel parcel) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readParcelableList(arrayList, android.view.inputmethod.InlineSuggestion.class.getClassLoader(), android.view.inputmethod.InlineSuggestion.class);
        this.mInlineSuggestions = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInlineSuggestions);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
