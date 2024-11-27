package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class InlineSuggestionsRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.InlineSuggestionsRequest> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.InlineSuggestionsRequest>() { // from class: android.view.inputmethod.InlineSuggestionsRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InlineSuggestionsRequest[] newArray(int i) {
            return new android.view.inputmethod.InlineSuggestionsRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InlineSuggestionsRequest createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.InlineSuggestionsRequest(parcel);
        }
    };
    private static final long IME_AUTOFILL_DEFAULT_SUPPORTED_LOCALES_IS_EMPTY = 169273070;
    public static final int SUGGESTION_COUNT_UNLIMITED = Integer.MAX_VALUE;
    private android.os.Bundle mExtras;
    private int mHostDisplayId;
    private android.os.IBinder mHostInputToken;
    private java.lang.String mHostPackageName;
    private final java.util.List<android.widget.inline.InlinePresentationSpec> mInlinePresentationSpecs;
    private android.widget.inline.InlinePresentationSpec mInlineTooltipPresentationSpec;
    private final int mMaxSuggestionCount;
    private android.os.LocaleList mSupportedLocales;

    public void setHostInputToken(android.os.IBinder iBinder) {
        this.mHostInputToken = iBinder;
    }

    private boolean extrasEquals(android.os.Bundle bundle) {
        return com.android.internal.widget.InlinePresentationStyleUtils.bundleEquals(this.mExtras, bundle);
    }

    private void parcelHostInputToken(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mHostInputToken);
    }

    private android.os.IBinder unparcelHostInputToken(android.os.Parcel parcel) {
        return parcel.readStrongBinder();
    }

    public void setHostDisplayId(int i) {
        this.mHostDisplayId = i;
    }

    private void onConstructed() {
        com.android.internal.util.Preconditions.checkState(!this.mInlinePresentationSpecs.isEmpty());
        com.android.internal.util.Preconditions.checkState(this.mMaxSuggestionCount >= this.mInlinePresentationSpecs.size());
    }

    public void filterContentTypes() {
        com.android.internal.widget.InlinePresentationStyleUtils.filterContentTypes(this.mExtras);
        for (int i = 0; i < this.mInlinePresentationSpecs.size(); i++) {
            this.mInlinePresentationSpecs.get(i).filterContentTypes();
        }
        if (this.mInlineTooltipPresentationSpec != null) {
            this.mInlineTooltipPresentationSpec.filterContentTypes();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultMaxSuggestionCount() {
        return Integer.MAX_VALUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String defaultHostPackageName() {
        return android.app.ActivityThread.currentPackageName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.widget.inline.InlinePresentationSpec defaultInlineTooltipPresentationSpec() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.LocaleList defaultSupportedLocales() {
        if (android.app.compat.CompatChanges.isChangeEnabled(IME_AUTOFILL_DEFAULT_SUPPORTED_LOCALES_IS_EMPTY)) {
            return android.os.LocaleList.getEmptyLocaleList();
        }
        return android.os.LocaleList.getDefault();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.IBinder defaultHostInputToken() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultHostDisplayId() {
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.Bundle defaultExtras() {
        return android.os.Bundle.EMPTY;
    }

    static abstract class BaseBuilder {
        abstract android.view.inputmethod.InlineSuggestionsRequest.Builder setHostDisplayId(int i);

        abstract android.view.inputmethod.InlineSuggestionsRequest.Builder setHostInputToken(android.os.IBinder iBinder);

        abstract android.view.inputmethod.InlineSuggestionsRequest.Builder setHostPackageName(java.lang.String str);

        abstract android.view.inputmethod.InlineSuggestionsRequest.Builder setInlinePresentationSpecs(java.util.List<android.widget.inline.InlinePresentationSpec> list);

        BaseBuilder() {
        }
    }

    InlineSuggestionsRequest(int i, java.util.List<android.widget.inline.InlinePresentationSpec> list, java.lang.String str, android.os.LocaleList localeList, android.os.Bundle bundle, android.os.IBinder iBinder, int i2, android.widget.inline.InlinePresentationSpec inlinePresentationSpec) {
        this.mMaxSuggestionCount = i;
        this.mInlinePresentationSpecs = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInlinePresentationSpecs);
        this.mHostPackageName = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHostPackageName);
        this.mSupportedLocales = localeList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSupportedLocales);
        this.mExtras = bundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mExtras);
        this.mHostInputToken = iBinder;
        this.mHostDisplayId = i2;
        this.mInlineTooltipPresentationSpec = inlinePresentationSpec;
        onConstructed();
    }

    public int getMaxSuggestionCount() {
        return this.mMaxSuggestionCount;
    }

    public java.util.List<android.widget.inline.InlinePresentationSpec> getInlinePresentationSpecs() {
        return this.mInlinePresentationSpecs;
    }

    public java.lang.String getHostPackageName() {
        return this.mHostPackageName;
    }

    public android.os.LocaleList getSupportedLocales() {
        return this.mSupportedLocales;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public android.os.IBinder getHostInputToken() {
        return this.mHostInputToken;
    }

    public int getHostDisplayId() {
        return this.mHostDisplayId;
    }

    public android.widget.inline.InlinePresentationSpec getInlineTooltipPresentationSpec() {
        return this.mInlineTooltipPresentationSpec;
    }

    public java.lang.String toString() {
        return "InlineSuggestionsRequest { maxSuggestionCount = " + this.mMaxSuggestionCount + ", inlinePresentationSpecs = " + this.mInlinePresentationSpecs + ", hostPackageName = " + this.mHostPackageName + ", supportedLocales = " + this.mSupportedLocales + ", extras = " + this.mExtras + ", hostInputToken = " + this.mHostInputToken + ", hostDisplayId = " + this.mHostDisplayId + ", inlineTooltipPresentationSpec = " + this.mInlineTooltipPresentationSpec + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest = (android.view.inputmethod.InlineSuggestionsRequest) obj;
        if (this.mMaxSuggestionCount == inlineSuggestionsRequest.mMaxSuggestionCount && java.util.Objects.equals(this.mInlinePresentationSpecs, inlineSuggestionsRequest.mInlinePresentationSpecs) && java.util.Objects.equals(this.mHostPackageName, inlineSuggestionsRequest.mHostPackageName) && java.util.Objects.equals(this.mSupportedLocales, inlineSuggestionsRequest.mSupportedLocales) && extrasEquals(inlineSuggestionsRequest.mExtras) && java.util.Objects.equals(this.mHostInputToken, inlineSuggestionsRequest.mHostInputToken) && this.mHostDisplayId == inlineSuggestionsRequest.mHostDisplayId && java.util.Objects.equals(this.mInlineTooltipPresentationSpec, inlineSuggestionsRequest.mInlineTooltipPresentationSpec)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((this.mMaxSuggestionCount + 31) * 31) + java.util.Objects.hashCode(this.mInlinePresentationSpecs)) * 31) + java.util.Objects.hashCode(this.mHostPackageName)) * 31) + java.util.Objects.hashCode(this.mSupportedLocales)) * 31) + java.util.Objects.hashCode(this.mExtras)) * 31) + java.util.Objects.hashCode(this.mHostInputToken)) * 31) + this.mHostDisplayId) * 31) + java.util.Objects.hashCode(this.mInlineTooltipPresentationSpec);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        int i2 = this.mHostInputToken != null ? 32 : 0;
        if (this.mInlineTooltipPresentationSpec != null) {
            i2 |= 128;
        }
        parcel.writeInt(i2);
        parcel.writeInt(this.mMaxSuggestionCount);
        parcel.writeParcelableList(this.mInlinePresentationSpecs, i);
        parcel.writeString(this.mHostPackageName);
        parcel.writeTypedObject(this.mSupportedLocales, i);
        parcel.writeBundle(this.mExtras);
        parcelHostInputToken(parcel, i);
        parcel.writeInt(this.mHostDisplayId);
        if (this.mInlineTooltipPresentationSpec != null) {
            parcel.writeTypedObject(this.mInlineTooltipPresentationSpec, i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    InlineSuggestionsRequest(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readParcelableList(arrayList, android.widget.inline.InlinePresentationSpec.class.getClassLoader());
        java.lang.String readString = parcel.readString();
        android.os.LocaleList localeList = (android.os.LocaleList) parcel.readTypedObject(android.os.LocaleList.CREATOR);
        android.os.Bundle readBundle = parcel.readBundle();
        android.os.IBinder unparcelHostInputToken = unparcelHostInputToken(parcel);
        int readInt3 = parcel.readInt();
        android.widget.inline.InlinePresentationSpec inlinePresentationSpec = (readInt & 128) == 0 ? null : (android.widget.inline.InlinePresentationSpec) parcel.readTypedObject(android.widget.inline.InlinePresentationSpec.CREATOR);
        this.mMaxSuggestionCount = readInt2;
        this.mInlinePresentationSpecs = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInlinePresentationSpecs);
        this.mHostPackageName = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mHostPackageName);
        this.mSupportedLocales = localeList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSupportedLocales);
        this.mExtras = readBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mExtras);
        this.mHostInputToken = unparcelHostInputToken;
        this.mHostDisplayId = readInt3;
        this.mInlineTooltipPresentationSpec = inlinePresentationSpec;
        onConstructed();
    }

    public static final class Builder extends android.view.inputmethod.InlineSuggestionsRequest.BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private android.os.Bundle mExtras;
        private int mHostDisplayId;
        private android.os.IBinder mHostInputToken;
        private java.lang.String mHostPackageName;
        private java.util.List<android.widget.inline.InlinePresentationSpec> mInlinePresentationSpecs;
        private android.widget.inline.InlinePresentationSpec mInlineTooltipPresentationSpec;
        private int mMaxSuggestionCount;
        private android.os.LocaleList mSupportedLocales;

        public Builder(java.util.List<android.widget.inline.InlinePresentationSpec> list) {
            this.mInlinePresentationSpecs = list;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mInlinePresentationSpecs);
        }

        public android.view.inputmethod.InlineSuggestionsRequest.Builder setMaxSuggestionCount(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mMaxSuggestionCount = i;
            return this;
        }

        @Override // android.view.inputmethod.InlineSuggestionsRequest.BaseBuilder
        public android.view.inputmethod.InlineSuggestionsRequest.Builder setInlinePresentationSpecs(java.util.List<android.widget.inline.InlinePresentationSpec> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mInlinePresentationSpecs = list;
            return this;
        }

        public android.view.inputmethod.InlineSuggestionsRequest.Builder addInlinePresentationSpecs(android.widget.inline.InlinePresentationSpec inlinePresentationSpec) {
            if (this.mInlinePresentationSpecs == null) {
                setInlinePresentationSpecs(new java.util.ArrayList());
            }
            this.mInlinePresentationSpecs.add(inlinePresentationSpec);
            return this;
        }

        @Override // android.view.inputmethod.InlineSuggestionsRequest.BaseBuilder
        android.view.inputmethod.InlineSuggestionsRequest.Builder setHostPackageName(java.lang.String str) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mHostPackageName = str;
            return this;
        }

        public android.view.inputmethod.InlineSuggestionsRequest.Builder setSupportedLocales(android.os.LocaleList localeList) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mSupportedLocales = localeList;
            return this;
        }

        public android.view.inputmethod.InlineSuggestionsRequest.Builder setExtras(android.os.Bundle bundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mExtras = bundle;
            return this;
        }

        @Override // android.view.inputmethod.InlineSuggestionsRequest.BaseBuilder
        android.view.inputmethod.InlineSuggestionsRequest.Builder setHostInputToken(android.os.IBinder iBinder) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mHostInputToken = iBinder;
            return this;
        }

        @Override // android.view.inputmethod.InlineSuggestionsRequest.BaseBuilder
        android.view.inputmethod.InlineSuggestionsRequest.Builder setHostDisplayId(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mHostDisplayId = i;
            return this;
        }

        public android.view.inputmethod.InlineSuggestionsRequest.Builder setInlineTooltipPresentationSpec(android.widget.inline.InlinePresentationSpec inlinePresentationSpec) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 128;
            this.mInlineTooltipPresentationSpec = inlinePresentationSpec;
            return this;
        }

        public android.view.inputmethod.InlineSuggestionsRequest build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 256;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mMaxSuggestionCount = android.view.inputmethod.InlineSuggestionsRequest.defaultMaxSuggestionCount();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mHostPackageName = android.view.inputmethod.InlineSuggestionsRequest.defaultHostPackageName();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mSupportedLocales = android.view.inputmethod.InlineSuggestionsRequest.defaultSupportedLocales();
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mExtras = android.view.inputmethod.InlineSuggestionsRequest.defaultExtras();
            }
            if ((this.mBuilderFieldsSet & 32) == 0) {
                this.mHostInputToken = android.view.inputmethod.InlineSuggestionsRequest.defaultHostInputToken();
            }
            if ((this.mBuilderFieldsSet & 64) == 0) {
                this.mHostDisplayId = android.view.inputmethod.InlineSuggestionsRequest.defaultHostDisplayId();
            }
            if ((this.mBuilderFieldsSet & 128) == 0) {
                this.mInlineTooltipPresentationSpec = android.view.inputmethod.InlineSuggestionsRequest.defaultInlineTooltipPresentationSpec();
            }
            return new android.view.inputmethod.InlineSuggestionsRequest(this.mMaxSuggestionCount, this.mInlinePresentationSpecs, this.mHostPackageName, this.mSupportedLocales, this.mExtras, this.mHostInputToken, this.mHostDisplayId, this.mInlineTooltipPresentationSpec);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 256) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
