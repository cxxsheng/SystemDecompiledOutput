package android.view.translation;

/* loaded from: classes4.dex */
public final class TranslationRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.translation.TranslationRequest> CREATOR = new android.os.Parcelable.Creator<android.view.translation.TranslationRequest>() { // from class: android.view.translation.TranslationRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationRequest[] newArray(int i) {
            return new android.view.translation.TranslationRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationRequest createFromParcel(android.os.Parcel parcel) {
            return new android.view.translation.TranslationRequest(parcel);
        }
    };
    public static final int FLAG_DICTIONARY_RESULT = 2;
    public static final int FLAG_PARTIAL_RESPONSES = 8;
    public static final int FLAG_TRANSLATION_RESULT = 1;
    public static final int FLAG_TRANSLITERATION_RESULT = 4;
    private final int mFlags;
    private final java.util.List<android.view.translation.TranslationRequestValue> mTranslationRequestValues;
    private final java.util.List<android.view.translation.ViewTranslationRequest> mViewTranslationRequests;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RequestFlags {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultFlags() {
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<android.view.translation.TranslationRequestValue> defaultTranslationRequestValues() {
        return java.util.Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<android.view.translation.ViewTranslationRequest> defaultViewTranslationRequests() {
        return java.util.Collections.emptyList();
    }

    static abstract class BaseBuilder {
        @java.lang.Deprecated
        public abstract android.view.translation.TranslationRequest.Builder addTranslationRequestValue(android.view.translation.TranslationRequestValue translationRequestValue);

        @java.lang.Deprecated
        public abstract android.view.translation.TranslationRequest.Builder addViewTranslationRequest(android.view.translation.ViewTranslationRequest viewTranslationRequest);

        BaseBuilder() {
        }
    }

    public static java.lang.String requestFlagsToString(int i) {
        return com.android.internal.util.BitUtils.flagsToString(i, new java.util.function.IntFunction() { // from class: android.view.translation.TranslationRequest$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i2) {
                return android.view.translation.TranslationRequest.singleRequestFlagsToString(i2);
            }
        });
    }

    static java.lang.String singleRequestFlagsToString(int i) {
        switch (i) {
            case 1:
                return "FLAG_TRANSLATION_RESULT";
            case 2:
                return "FLAG_DICTIONARY_RESULT";
            case 4:
                return "FLAG_TRANSLITERATION_RESULT";
            case 8:
                return "FLAG_PARTIAL_RESPONSES";
            default:
                return java.lang.Integer.toHexString(i);
        }
    }

    TranslationRequest(int i, java.util.List<android.view.translation.TranslationRequestValue> list, java.util.List<android.view.translation.ViewTranslationRequest> list2) {
        this.mFlags = i;
        com.android.internal.util.Preconditions.checkFlagsArgument(this.mFlags, 15);
        this.mTranslationRequestValues = list;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTranslationRequestValues);
        this.mViewTranslationRequests = list2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mViewTranslationRequests);
    }

    public int getFlags() {
        return this.mFlags;
    }

    public java.util.List<android.view.translation.TranslationRequestValue> getTranslationRequestValues() {
        return this.mTranslationRequestValues;
    }

    public java.util.List<android.view.translation.ViewTranslationRequest> getViewTranslationRequests() {
        return this.mViewTranslationRequests;
    }

    public java.lang.String toString() {
        return "TranslationRequest { flags = " + requestFlagsToString(this.mFlags) + ", translationRequestValues = " + this.mTranslationRequestValues + ", viewTranslationRequests = " + this.mViewTranslationRequests + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mFlags);
        parcel.writeParcelableList(this.mTranslationRequestValues, i);
        parcel.writeParcelableList(this.mViewTranslationRequests, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    TranslationRequest(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readParcelableList(arrayList, android.view.translation.TranslationRequestValue.class.getClassLoader(), android.view.translation.TranslationRequestValue.class);
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        parcel.readParcelableList(arrayList2, android.view.translation.ViewTranslationRequest.class.getClassLoader(), android.view.translation.ViewTranslationRequest.class);
        this.mFlags = readInt;
        com.android.internal.util.Preconditions.checkFlagsArgument(this.mFlags, 15);
        this.mTranslationRequestValues = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTranslationRequestValues);
        this.mViewTranslationRequests = arrayList2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mViewTranslationRequests);
    }

    public static final class Builder extends android.view.translation.TranslationRequest.BaseBuilder {
        private long mBuilderFieldsSet = 0;
        private int mFlags;
        private java.util.List<android.view.translation.TranslationRequestValue> mTranslationRequestValues;
        private java.util.List<android.view.translation.ViewTranslationRequest> mViewTranslationRequests;

        public android.view.translation.TranslationRequest.Builder setFlags(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mFlags = i;
            return this;
        }

        public android.view.translation.TranslationRequest.Builder setTranslationRequestValues(java.util.List<android.view.translation.TranslationRequestValue> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mTranslationRequestValues = list;
            return this;
        }

        @Override // android.view.translation.TranslationRequest.BaseBuilder
        @java.lang.Deprecated
        public android.view.translation.TranslationRequest.Builder addTranslationRequestValue(android.view.translation.TranslationRequestValue translationRequestValue) {
            if (this.mTranslationRequestValues == null) {
                setTranslationRequestValues(new java.util.ArrayList());
            }
            this.mTranslationRequestValues.add(translationRequestValue);
            return this;
        }

        public android.view.translation.TranslationRequest.Builder setViewTranslationRequests(java.util.List<android.view.translation.ViewTranslationRequest> list) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mViewTranslationRequests = list;
            return this;
        }

        @Override // android.view.translation.TranslationRequest.BaseBuilder
        @java.lang.Deprecated
        public android.view.translation.TranslationRequest.Builder addViewTranslationRequest(android.view.translation.ViewTranslationRequest viewTranslationRequest) {
            if (this.mViewTranslationRequests == null) {
                setViewTranslationRequests(new java.util.ArrayList());
            }
            this.mViewTranslationRequests.add(viewTranslationRequest);
            return this;
        }

        public android.view.translation.TranslationRequest build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            if ((this.mBuilderFieldsSet & 1) == 0) {
                this.mFlags = android.view.translation.TranslationRequest.defaultFlags();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mTranslationRequestValues = android.view.translation.TranslationRequest.defaultTranslationRequestValues();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mViewTranslationRequests = android.view.translation.TranslationRequest.defaultViewTranslationRequests();
            }
            return new android.view.translation.TranslationRequest(this.mFlags, this.mTranslationRequestValues, this.mViewTranslationRequests);
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
