package android.view.autofill;

/* loaded from: classes4.dex */
public final class VirtualViewFillInfo {
    private java.lang.String[] mAutofillHints;

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String[] defaultAutofillHints() {
        return null;
    }

    VirtualViewFillInfo(java.lang.String[] strArr) {
        this.mAutofillHints = strArr;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.annotation.SuppressLint.class, (java.lang.annotation.Annotation) null, this.mAutofillHints, "value", "NullableCollection");
    }

    public java.lang.String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    public static final class Builder {
        private java.lang.String[] mAutofillHints;
        private long mBuilderFieldsSet = 0;

        public android.view.autofill.VirtualViewFillInfo.Builder setAutofillHints(java.lang.String... strArr) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mAutofillHints = strArr;
            return this;
        }

        public android.view.autofill.VirtualViewFillInfo build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mAutofillHints = android.view.autofill.VirtualViewFillInfo.defaultAutofillHints();
            }
            return new android.view.autofill.VirtualViewFillInfo(this.mAutofillHints);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 2) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
