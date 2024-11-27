package android.service.autofill;

/* loaded from: classes3.dex */
public final class InlinePresentation implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.InlinePresentation> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.InlinePresentation>() { // from class: android.service.autofill.InlinePresentation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.InlinePresentation[] newArray(int i) {
            return new android.service.autofill.InlinePresentation[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.InlinePresentation createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.InlinePresentation(parcel);
        }
    };
    private final android.widget.inline.InlinePresentationSpec mInlinePresentationSpec;
    private final boolean mPinned;
    private final android.app.slice.Slice mSlice;

    public java.lang.String[] getAutofillHints() {
        java.util.List<java.lang.String> hints = this.mSlice.getHints();
        return (java.lang.String[]) hints.toArray(new java.lang.String[hints.size()]);
    }

    public static android.service.autofill.InlinePresentation createTooltipPresentation(android.app.slice.Slice slice, android.widget.inline.InlinePresentationSpec inlinePresentationSpec) {
        return new android.service.autofill.InlinePresentation(slice, inlinePresentationSpec, false);
    }

    public InlinePresentation(android.app.slice.Slice slice, android.widget.inline.InlinePresentationSpec inlinePresentationSpec, boolean z) {
        this.mSlice = slice;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSlice);
        this.mInlinePresentationSpec = inlinePresentationSpec;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInlinePresentationSpec);
        this.mPinned = z;
    }

    public android.app.slice.Slice getSlice() {
        return this.mSlice;
    }

    public android.widget.inline.InlinePresentationSpec getInlinePresentationSpec() {
        return this.mInlinePresentationSpec;
    }

    public boolean isPinned() {
        return this.mPinned;
    }

    public java.lang.String toString() {
        return "InlinePresentation { slice = " + this.mSlice + ", inlinePresentationSpec = " + this.mInlinePresentationSpec + ", pinned = " + this.mPinned + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.autofill.InlinePresentation inlinePresentation = (android.service.autofill.InlinePresentation) obj;
        if (java.util.Objects.equals(this.mSlice, inlinePresentation.mSlice) && java.util.Objects.equals(this.mInlinePresentationSpec, inlinePresentation.mInlinePresentationSpec) && this.mPinned == inlinePresentation.mPinned) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((java.util.Objects.hashCode(this.mSlice) + 31) * 31) + java.util.Objects.hashCode(this.mInlinePresentationSpec)) * 31) + java.lang.Boolean.hashCode(this.mPinned);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mPinned ? (byte) 4 : (byte) 0);
        parcel.writeTypedObject(this.mSlice, i);
        parcel.writeTypedObject(this.mInlinePresentationSpec, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    InlinePresentation(android.os.Parcel parcel) {
        boolean z = (parcel.readByte() & 4) != 0;
        android.app.slice.Slice slice = (android.app.slice.Slice) parcel.readTypedObject(android.app.slice.Slice.CREATOR);
        android.widget.inline.InlinePresentationSpec inlinePresentationSpec = (android.widget.inline.InlinePresentationSpec) parcel.readTypedObject(android.widget.inline.InlinePresentationSpec.CREATOR);
        this.mSlice = slice;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSlice);
        this.mInlinePresentationSpec = inlinePresentationSpec;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInlinePresentationSpec);
        this.mPinned = z;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
