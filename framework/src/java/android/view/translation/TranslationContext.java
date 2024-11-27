package android.view.translation;

/* loaded from: classes4.dex */
public final class TranslationContext implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.translation.TranslationContext> CREATOR = new android.os.Parcelable.Creator<android.view.translation.TranslationContext>() { // from class: android.view.translation.TranslationContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationContext[] newArray(int i) {
            return new android.view.translation.TranslationContext[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.translation.TranslationContext createFromParcel(android.os.Parcel parcel) {
            return new android.view.translation.TranslationContext(parcel);
        }
    };
    public static final int FLAG_DEFINITIONS = 4;
    public static final int FLAG_LOW_LATENCY = 1;
    public static final int FLAG_TRANSLITERATION = 2;
    private final android.app.assist.ActivityId mActivityId;
    private final android.view.translation.TranslationSpec mSourceSpec;
    private final android.view.translation.TranslationSpec mTargetSpec;
    private final int mTranslationFlags;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TranslationFlag {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int defaultTranslationFlags() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.assist.ActivityId defaultActivityId() {
        return null;
    }

    private void parcelActivityId(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mActivityId != null);
        if (this.mActivityId != null) {
            this.mActivityId.writeToParcel(parcel, i);
        }
    }

    private android.app.assist.ActivityId unparcelActivityId(android.os.Parcel parcel) {
        if (parcel.readBoolean()) {
            return new android.app.assist.ActivityId(parcel);
        }
        return null;
    }

    @android.annotation.SystemApi
    public android.app.assist.ActivityId getActivityId() {
        return this.mActivityId;
    }

    static abstract class BaseBuilder {
        BaseBuilder() {
        }
    }

    public static java.lang.String translationFlagToString(int i) {
        return com.android.internal.util.BitUtils.flagsToString(i, new java.util.function.IntFunction() { // from class: android.view.translation.TranslationContext$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i2) {
                return android.view.translation.TranslationContext.singleTranslationFlagToString(i2);
            }
        });
    }

    static java.lang.String singleTranslationFlagToString(int i) {
        switch (i) {
            case 1:
                return "FLAG_LOW_LATENCY";
            case 2:
                return "FLAG_TRANSLITERATION";
            case 3:
            default:
                return java.lang.Integer.toHexString(i);
            case 4:
                return "FLAG_DEFINITIONS";
        }
    }

    TranslationContext(android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, int i, android.app.assist.ActivityId activityId) {
        this.mSourceSpec = translationSpec;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSourceSpec);
        this.mTargetSpec = translationSpec2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTargetSpec);
        this.mTranslationFlags = i;
        com.android.internal.util.Preconditions.checkFlagsArgument(this.mTranslationFlags, 7);
        this.mActivityId = activityId;
    }

    public android.view.translation.TranslationSpec getSourceSpec() {
        return this.mSourceSpec;
    }

    public android.view.translation.TranslationSpec getTargetSpec() {
        return this.mTargetSpec;
    }

    public int getTranslationFlags() {
        return this.mTranslationFlags;
    }

    public java.lang.String toString() {
        return "TranslationContext { sourceSpec = " + this.mSourceSpec + ", targetSpec = " + this.mTargetSpec + ", translationFlags = " + translationFlagToString(this.mTranslationFlags) + ", activityId = " + this.mActivityId + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.mActivityId != null ? (byte) 8 : (byte) 0);
        parcel.writeTypedObject(this.mSourceSpec, i);
        parcel.writeTypedObject(this.mTargetSpec, i);
        parcel.writeInt(this.mTranslationFlags);
        parcelActivityId(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    TranslationContext(android.os.Parcel parcel) {
        parcel.readByte();
        android.view.translation.TranslationSpec translationSpec = (android.view.translation.TranslationSpec) parcel.readTypedObject(android.view.translation.TranslationSpec.CREATOR);
        android.view.translation.TranslationSpec translationSpec2 = (android.view.translation.TranslationSpec) parcel.readTypedObject(android.view.translation.TranslationSpec.CREATOR);
        int readInt = parcel.readInt();
        android.app.assist.ActivityId unparcelActivityId = unparcelActivityId(parcel);
        this.mSourceSpec = translationSpec;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSourceSpec);
        this.mTargetSpec = translationSpec2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTargetSpec);
        this.mTranslationFlags = readInt;
        com.android.internal.util.Preconditions.checkFlagsArgument(this.mTranslationFlags, 7);
        this.mActivityId = unparcelActivityId;
    }

    public static final class Builder extends android.view.translation.TranslationContext.BaseBuilder {
        private android.app.assist.ActivityId mActivityId;
        private long mBuilderFieldsSet = 0;
        private android.view.translation.TranslationSpec mSourceSpec;
        private android.view.translation.TranslationSpec mTargetSpec;
        private int mTranslationFlags;

        public Builder(android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2) {
            this.mSourceSpec = translationSpec;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSourceSpec);
            this.mTargetSpec = translationSpec2;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mTargetSpec);
        }

        public android.view.translation.TranslationContext.Builder setTranslationFlags(int i) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mTranslationFlags = i;
            return this;
        }

        public android.view.translation.TranslationContext.Builder setActivityId(android.app.assist.ActivityId activityId) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mActivityId = activityId;
            return this;
        }

        public android.view.translation.TranslationContext build() {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mTranslationFlags = android.view.translation.TranslationContext.defaultTranslationFlags();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mActivityId = android.view.translation.TranslationContext.defaultActivityId();
            }
            return new android.view.translation.TranslationContext(this.mSourceSpec, this.mTargetSpec, this.mTranslationFlags, this.mActivityId);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
