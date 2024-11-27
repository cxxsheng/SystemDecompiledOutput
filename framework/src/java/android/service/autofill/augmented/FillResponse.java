package android.service.autofill.augmented;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class FillResponse {
    private android.os.Bundle mClientState;
    private android.service.autofill.augmented.FillWindow mFillWindow;
    private java.util.List<android.service.autofill.Dataset> mInlineSuggestions;

    /* JADX INFO: Access modifiers changed from: private */
    public static android.service.autofill.augmented.FillWindow defaultFillWindow() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<android.service.autofill.Dataset> defaultInlineSuggestions() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.Bundle defaultClientState() {
        return null;
    }

    static abstract class BaseBuilder {
        abstract android.service.autofill.augmented.FillResponse.Builder addInlineSuggestion(android.service.autofill.Dataset dataset);

        BaseBuilder() {
        }
    }

    FillResponse(android.service.autofill.augmented.FillWindow fillWindow, java.util.List<android.service.autofill.Dataset> list, android.os.Bundle bundle) {
        this.mFillWindow = fillWindow;
        this.mInlineSuggestions = list;
        this.mClientState = bundle;
    }

    public android.service.autofill.augmented.FillWindow getFillWindow() {
        return this.mFillWindow;
    }

    public java.util.List<android.service.autofill.Dataset> getInlineSuggestions() {
        return this.mInlineSuggestions;
    }

    public android.os.Bundle getClientState() {
        return this.mClientState;
    }

    public static final class Builder extends android.service.autofill.augmented.FillResponse.BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private android.os.Bundle mClientState;
        private android.service.autofill.augmented.FillWindow mFillWindow;
        private java.util.List<android.service.autofill.Dataset> mInlineSuggestions;

        public android.service.autofill.augmented.FillResponse.Builder setFillWindow(android.service.autofill.augmented.FillWindow fillWindow) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mFillWindow = fillWindow;
            return this;
        }

        public android.service.autofill.augmented.FillResponse.Builder setInlineSuggestions(java.util.List<android.service.autofill.Dataset> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mInlineSuggestions = list;
            return this;
        }

        @Override // android.service.autofill.augmented.FillResponse.BaseBuilder
        android.service.autofill.augmented.FillResponse.Builder addInlineSuggestion(android.service.autofill.Dataset dataset) {
            if (this.mInlineSuggestions == null) {
                setInlineSuggestions(new java.util.ArrayList());
            }
            this.mInlineSuggestions.add(dataset);
            return this;
        }

        public android.service.autofill.augmented.FillResponse.Builder setClientState(android.os.Bundle bundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mClientState = bundle;
            return this;
        }

        public android.service.autofill.augmented.FillResponse build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mFillWindow = android.service.autofill.augmented.FillResponse.defaultFillWindow();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mInlineSuggestions = android.service.autofill.augmented.FillResponse.defaultInlineSuggestions();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mClientState = android.service.autofill.augmented.FillResponse.defaultClientState();
            }
            return new android.service.autofill.augmented.FillResponse(this.mFillWindow, this.mInlineSuggestions, this.mClientState);
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
