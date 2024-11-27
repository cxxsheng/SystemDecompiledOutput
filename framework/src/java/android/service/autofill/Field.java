package android.service.autofill;

/* loaded from: classes3.dex */
public final class Field {
    private android.service.autofill.Dataset.DatasetFieldFilter mFilter;
    private android.service.autofill.Presentations mPresentations;
    private android.view.autofill.AutofillValue mValue;

    Field(android.view.autofill.AutofillValue autofillValue, android.service.autofill.Dataset.DatasetFieldFilter datasetFieldFilter, android.service.autofill.Presentations presentations) {
        this.mValue = autofillValue;
        this.mFilter = datasetFieldFilter;
        this.mPresentations = presentations;
    }

    public android.view.autofill.AutofillValue getValue() {
        return this.mValue;
    }

    public android.service.autofill.Dataset.DatasetFieldFilter getDatasetFieldFilter() {
        return this.mFilter;
    }

    public java.util.regex.Pattern getFilter() {
        if (this.mFilter == null) {
            return null;
        }
        return this.mFilter.pattern;
    }

    public android.service.autofill.Presentations getPresentations() {
        return this.mPresentations;
    }

    public static final class Builder {
        private android.view.autofill.AutofillValue mValue = null;
        private android.service.autofill.Dataset.DatasetFieldFilter mFilter = null;
        private android.service.autofill.Presentations mPresentations = null;
        private boolean mDestroyed = false;

        public android.service.autofill.Field.Builder setValue(android.view.autofill.AutofillValue autofillValue) {
            checkNotUsed();
            this.mValue = autofillValue;
            return this;
        }

        public android.service.autofill.Field.Builder setFilter(java.util.regex.Pattern pattern) {
            checkNotUsed();
            this.mFilter = new android.service.autofill.Dataset.DatasetFieldFilter(pattern);
            return this;
        }

        public android.service.autofill.Field.Builder setPresentations(android.service.autofill.Presentations presentations) {
            checkNotUsed();
            this.mPresentations = presentations;
            return this;
        }

        public android.service.autofill.Field build() {
            checkNotUsed();
            this.mDestroyed = true;
            return new android.service.autofill.Field(this.mValue, this.mFilter, this.mPresentations);
        }

        private void checkNotUsed() {
            if (this.mDestroyed) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
