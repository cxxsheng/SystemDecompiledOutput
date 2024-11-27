package android.widget.inline;

/* loaded from: classes4.dex */
public final class InlinePresentationSpec implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.widget.inline.InlinePresentationSpec> CREATOR = new android.os.Parcelable.Creator<android.widget.inline.InlinePresentationSpec>() { // from class: android.widget.inline.InlinePresentationSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.widget.inline.InlinePresentationSpec[] newArray(int i) {
            return new android.widget.inline.InlinePresentationSpec[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.widget.inline.InlinePresentationSpec createFromParcel(android.os.Parcel parcel) {
            return new android.widget.inline.InlinePresentationSpec(parcel);
        }
    };
    private final android.util.Size mMaxSize;
    private final android.util.Size mMinSize;
    private final android.os.Bundle mStyle;

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.Bundle defaultStyle() {
        return android.os.Bundle.EMPTY;
    }

    private boolean styleEquals(android.os.Bundle bundle) {
        return com.android.internal.widget.InlinePresentationStyleUtils.bundleEquals(this.mStyle, bundle);
    }

    public void filterContentTypes() {
        com.android.internal.widget.InlinePresentationStyleUtils.filterContentTypes(this.mStyle);
    }

    static abstract class BaseBuilder {
        BaseBuilder() {
        }
    }

    InlinePresentationSpec(android.util.Size size, android.util.Size size2, android.os.Bundle bundle) {
        this.mMinSize = size;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMinSize);
        this.mMaxSize = size2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMaxSize);
        this.mStyle = bundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mStyle);
    }

    public android.util.Size getMinSize() {
        return this.mMinSize;
    }

    public android.util.Size getMaxSize() {
        return this.mMaxSize;
    }

    public android.os.Bundle getStyle() {
        return this.mStyle;
    }

    public java.lang.String toString() {
        return "InlinePresentationSpec { minSize = " + this.mMinSize + ", maxSize = " + this.mMaxSize + ", style = " + this.mStyle + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.widget.inline.InlinePresentationSpec inlinePresentationSpec = (android.widget.inline.InlinePresentationSpec) obj;
        if (java.util.Objects.equals(this.mMinSize, inlinePresentationSpec.mMinSize) && java.util.Objects.equals(this.mMaxSize, inlinePresentationSpec.mMaxSize) && styleEquals(inlinePresentationSpec.mStyle)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((java.util.Objects.hashCode(this.mMinSize) + 31) * 31) + java.util.Objects.hashCode(this.mMaxSize)) * 31) + java.util.Objects.hashCode(this.mStyle);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeSize(this.mMinSize);
        parcel.writeSize(this.mMaxSize);
        parcel.writeBundle(this.mStyle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    InlinePresentationSpec(android.os.Parcel parcel) {
        android.util.Size readSize = parcel.readSize();
        android.util.Size readSize2 = parcel.readSize();
        android.os.Bundle readBundle = parcel.readBundle();
        this.mMinSize = readSize;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMinSize);
        this.mMaxSize = readSize2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMaxSize);
        this.mStyle = readBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mStyle);
    }

    public static final class Builder extends android.widget.inline.InlinePresentationSpec.BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private android.util.Size mMaxSize;
        private android.util.Size mMinSize;
        private android.os.Bundle mStyle;

        public Builder(android.util.Size size, android.util.Size size2) {
            this.mMinSize = size;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMinSize);
            this.mMaxSize = size2;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mMaxSize);
        }

        public android.widget.inline.InlinePresentationSpec.Builder setStyle(android.os.Bundle bundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mStyle = bundle;
            return this;
        }

        public android.widget.inline.InlinePresentationSpec build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mStyle = android.widget.inline.InlinePresentationSpec.defaultStyle();
            }
            return new android.widget.inline.InlinePresentationSpec(this.mMinSize, this.mMaxSize, this.mStyle);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 8) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
