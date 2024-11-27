package android.service.autofill;

/* loaded from: classes3.dex */
public final class SaveInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.SaveInfo> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.SaveInfo>() { // from class: android.service.autofill.SaveInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.SaveInfo createFromParcel(android.os.Parcel parcel) {
            android.service.autofill.SaveInfo.Builder builder;
            int readInt = parcel.readInt();
            android.view.autofill.AutofillId[] autofillIdArr = (android.view.autofill.AutofillId[]) parcel.readParcelableArray(null, android.view.autofill.AutofillId.class);
            if (autofillIdArr != null) {
                builder = new android.service.autofill.SaveInfo.Builder(readInt, autofillIdArr);
            } else {
                builder = new android.service.autofill.SaveInfo.Builder(readInt);
            }
            android.view.autofill.AutofillId[] autofillIdArr2 = (android.view.autofill.AutofillId[]) parcel.readParcelableArray(null, android.view.autofill.AutofillId.class);
            if (autofillIdArr2 != null) {
                builder.setOptionalIds(autofillIdArr2);
            }
            builder.setNegativeAction(parcel.readInt(), (android.content.IntentSender) parcel.readParcelable(null, android.content.IntentSender.class));
            builder.setPositiveAction(parcel.readInt());
            builder.setDescription(parcel.readCharSequence());
            android.service.autofill.CustomDescription customDescription = (android.service.autofill.CustomDescription) parcel.readParcelable(null, android.service.autofill.CustomDescription.class);
            if (customDescription != null) {
                builder.setCustomDescription(customDescription);
            }
            android.service.autofill.InternalValidator internalValidator = (android.service.autofill.InternalValidator) parcel.readParcelable(null, android.service.autofill.InternalValidator.class);
            if (internalValidator != null) {
                builder.setValidator(internalValidator);
            }
            android.service.autofill.InternalSanitizer[] internalSanitizerArr = (android.service.autofill.InternalSanitizer[]) parcel.readParcelableArray(null, android.service.autofill.InternalSanitizer.class);
            if (internalSanitizerArr != null) {
                for (android.service.autofill.InternalSanitizer internalSanitizer : internalSanitizerArr) {
                    builder.addSanitizer(internalSanitizer, (android.view.autofill.AutofillId[]) parcel.readParcelableArray(null, android.view.autofill.AutofillId.class));
                }
            }
            android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) parcel.readParcelable(null, android.view.autofill.AutofillId.class);
            if (autofillId != null) {
                builder.setTriggerId(autofillId);
            }
            builder.setFlags(parcel.readInt());
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.SaveInfo[] newArray(int i) {
            return new android.service.autofill.SaveInfo[i];
        }
    };
    public static final int FLAG_DELAY_SAVE = 4;
    public static final int FLAG_DONT_SAVE_ON_FINISH = 2;
    public static final int FLAG_SAVE_ON_ALL_VIEWS_INVISIBLE = 1;
    public static final int NEGATIVE_BUTTON_STYLE_CANCEL = 0;
    public static final int NEGATIVE_BUTTON_STYLE_NEVER = 2;
    public static final int NEGATIVE_BUTTON_STYLE_REJECT = 1;
    public static final int POSITIVE_BUTTON_STYLE_CONTINUE = 1;
    public static final int POSITIVE_BUTTON_STYLE_SAVE = 0;
    public static final int SAVE_DATA_TYPE_ADDRESS = 2;
    public static final int SAVE_DATA_TYPE_CREDIT_CARD = 4;
    public static final int SAVE_DATA_TYPE_DEBIT_CARD = 32;
    public static final int SAVE_DATA_TYPE_EMAIL_ADDRESS = 16;
    public static final int SAVE_DATA_TYPE_GENERIC = 0;
    public static final int SAVE_DATA_TYPE_GENERIC_CARD = 128;
    public static final int SAVE_DATA_TYPE_PASSWORD = 1;
    public static final int SAVE_DATA_TYPE_PAYMENT_CARD = 64;
    public static final int SAVE_DATA_TYPE_USERNAME = 8;
    private final android.service.autofill.CustomDescription mCustomDescription;
    private final java.lang.CharSequence mDescription;
    private final int mFlags;
    private final android.content.IntentSender mNegativeActionListener;
    private final int mNegativeButtonStyle;
    private final android.view.autofill.AutofillId[] mOptionalIds;
    private final int mPositiveButtonStyle;
    private final android.view.autofill.AutofillId[] mRequiredIds;
    private final android.service.autofill.InternalSanitizer[] mSanitizerKeys;
    private final android.view.autofill.AutofillId[][] mSanitizerValues;
    private final android.view.autofill.AutofillId mTriggerId;
    private final int mType;
    private final android.service.autofill.InternalValidator mValidator;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface NegativeButtonStyle {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface PositiveButtonStyle {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface SaveDataType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface SaveInfoFlags {
    }

    public static android.service.autofill.SaveInfo copy(android.service.autofill.SaveInfo saveInfo, android.view.autofill.AutofillId[] autofillIdArr) {
        return new android.service.autofill.SaveInfo(saveInfo.mType, saveInfo.mNegativeButtonStyle, saveInfo.mPositiveButtonStyle, saveInfo.mNegativeActionListener, saveInfo.mRequiredIds, android.service.autofill.AutofillServiceHelper.assertValid(autofillIdArr), saveInfo.mDescription, saveInfo.mFlags, saveInfo.mCustomDescription, saveInfo.mValidator, saveInfo.mSanitizerKeys, saveInfo.mSanitizerValues, saveInfo.mTriggerId);
    }

    private SaveInfo(int i, int i2, int i3, android.content.IntentSender intentSender, android.view.autofill.AutofillId[] autofillIdArr, android.view.autofill.AutofillId[] autofillIdArr2, java.lang.CharSequence charSequence, int i4, android.service.autofill.CustomDescription customDescription, android.service.autofill.InternalValidator internalValidator, android.service.autofill.InternalSanitizer[] internalSanitizerArr, android.view.autofill.AutofillId[][] autofillIdArr3, android.view.autofill.AutofillId autofillId) {
        this.mType = i;
        this.mNegativeButtonStyle = i2;
        this.mNegativeActionListener = intentSender;
        this.mPositiveButtonStyle = i3;
        this.mRequiredIds = autofillIdArr;
        this.mOptionalIds = autofillIdArr2;
        this.mDescription = charSequence;
        this.mFlags = i4;
        this.mCustomDescription = customDescription;
        this.mValidator = internalValidator;
        this.mSanitizerKeys = internalSanitizerArr;
        this.mSanitizerValues = autofillIdArr3;
        this.mTriggerId = autofillId;
    }

    private SaveInfo(android.service.autofill.SaveInfo.Builder builder) {
        this.mType = builder.mType;
        this.mNegativeButtonStyle = builder.mNegativeButtonStyle;
        this.mNegativeActionListener = builder.mNegativeActionListener;
        this.mPositiveButtonStyle = builder.mPositiveButtonStyle;
        this.mRequiredIds = builder.mRequiredIds;
        this.mOptionalIds = builder.mOptionalIds;
        this.mDescription = builder.mDescription;
        this.mFlags = builder.mFlags;
        this.mCustomDescription = builder.mCustomDescription;
        this.mValidator = builder.mValidator;
        if (builder.mSanitizers == null) {
            this.mSanitizerKeys = null;
            this.mSanitizerValues = null;
        } else {
            int size = builder.mSanitizers.size();
            this.mSanitizerKeys = new android.service.autofill.InternalSanitizer[size];
            this.mSanitizerValues = new android.view.autofill.AutofillId[size][];
            for (int i = 0; i < size; i++) {
                this.mSanitizerKeys[i] = (android.service.autofill.InternalSanitizer) builder.mSanitizers.keyAt(i);
                this.mSanitizerValues[i] = (android.view.autofill.AutofillId[]) builder.mSanitizers.valueAt(i);
            }
        }
        this.mTriggerId = builder.mTriggerId;
    }

    public int getNegativeActionStyle() {
        return this.mNegativeButtonStyle;
    }

    public android.content.IntentSender getNegativeActionListener() {
        return this.mNegativeActionListener;
    }

    public int getPositiveActionStyle() {
        return this.mPositiveButtonStyle;
    }

    public android.view.autofill.AutofillId[] getRequiredIds() {
        return this.mRequiredIds;
    }

    public android.view.autofill.AutofillId[] getOptionalIds() {
        return this.mOptionalIds;
    }

    public int getType() {
        return this.mType;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public java.lang.CharSequence getDescription() {
        return this.mDescription;
    }

    public android.service.autofill.CustomDescription getCustomDescription() {
        return this.mCustomDescription;
    }

    public android.service.autofill.InternalValidator getValidator() {
        return this.mValidator;
    }

    public android.service.autofill.InternalSanitizer[] getSanitizerKeys() {
        return this.mSanitizerKeys;
    }

    public android.view.autofill.AutofillId[][] getSanitizerValues() {
        return this.mSanitizerValues;
    }

    public android.view.autofill.AutofillId getTriggerId() {
        return this.mTriggerId;
    }

    public static final class Builder {
        private android.service.autofill.CustomDescription mCustomDescription;
        private java.lang.CharSequence mDescription;
        private boolean mDestroyed;
        private int mFlags;
        private android.content.IntentSender mNegativeActionListener;
        private int mNegativeButtonStyle;
        private android.view.autofill.AutofillId[] mOptionalIds;
        private int mPositiveButtonStyle;
        private final android.view.autofill.AutofillId[] mRequiredIds;
        private android.util.ArraySet<android.view.autofill.AutofillId> mSanitizerIds;
        private android.util.ArrayMap<android.service.autofill.InternalSanitizer, android.view.autofill.AutofillId[]> mSanitizers;
        private android.view.autofill.AutofillId mTriggerId;
        private final int mType;
        private android.service.autofill.InternalValidator mValidator;

        public Builder(int i, android.view.autofill.AutofillId[] autofillIdArr) {
            this.mNegativeButtonStyle = 0;
            this.mPositiveButtonStyle = 0;
            this.mType = i;
            this.mRequiredIds = android.service.autofill.AutofillServiceHelper.assertValid(autofillIdArr);
        }

        public Builder(int i) {
            this.mNegativeButtonStyle = 0;
            this.mPositiveButtonStyle = 0;
            this.mType = i;
            this.mRequiredIds = null;
        }

        public android.service.autofill.SaveInfo.Builder setFlags(int i) {
            throwIfDestroyed();
            this.mFlags = com.android.internal.util.Preconditions.checkFlagsArgument(i, 7);
            return this;
        }

        public android.service.autofill.SaveInfo.Builder setOptionalIds(android.view.autofill.AutofillId[] autofillIdArr) {
            throwIfDestroyed();
            this.mOptionalIds = android.service.autofill.AutofillServiceHelper.assertValid(autofillIdArr);
            return this;
        }

        public android.service.autofill.SaveInfo.Builder setDescription(java.lang.CharSequence charSequence) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkState(this.mCustomDescription == null, "Can call setDescription() or setCustomDescription(), but not both");
            this.mDescription = charSequence;
            return this;
        }

        public android.service.autofill.SaveInfo.Builder setCustomDescription(android.service.autofill.CustomDescription customDescription) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkState(this.mDescription == null, "Can call setDescription() or setCustomDescription(), but not both");
            this.mCustomDescription = customDescription;
            return this;
        }

        public android.service.autofill.SaveInfo.Builder setNegativeAction(int i, android.content.IntentSender intentSender) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, 2, "style");
            this.mNegativeButtonStyle = i;
            this.mNegativeActionListener = intentSender;
            return this;
        }

        public android.service.autofill.SaveInfo.Builder setPositiveAction(int i) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, 1, "style");
            this.mPositiveButtonStyle = i;
            return this;
        }

        public android.service.autofill.SaveInfo.Builder setValidator(android.service.autofill.Validator validator) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkArgument(validator instanceof android.service.autofill.InternalValidator, "not provided by Android System: %s", validator);
            this.mValidator = (android.service.autofill.InternalValidator) validator;
            return this;
        }

        public android.service.autofill.SaveInfo.Builder addSanitizer(android.service.autofill.Sanitizer sanitizer, android.view.autofill.AutofillId... autofillIdArr) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkArgument(!com.android.internal.util.ArrayUtils.isEmpty(autofillIdArr), "ids cannot be empty or null");
            com.android.internal.util.Preconditions.checkArgument(sanitizer instanceof android.service.autofill.InternalSanitizer, "not provided by Android System: %s", sanitizer);
            if (this.mSanitizers == null) {
                this.mSanitizers = new android.util.ArrayMap<>();
                this.mSanitizerIds = new android.util.ArraySet<>(autofillIdArr.length);
            }
            for (android.view.autofill.AutofillId autofillId : autofillIdArr) {
                com.android.internal.util.Preconditions.checkArgument(!this.mSanitizerIds.contains(autofillId), "already added %s", autofillId);
                this.mSanitizerIds.add(autofillId);
            }
            this.mSanitizers.put((android.service.autofill.InternalSanitizer) sanitizer, autofillIdArr);
            return this;
        }

        public android.service.autofill.SaveInfo.Builder setTriggerId(android.view.autofill.AutofillId autofillId) {
            throwIfDestroyed();
            this.mTriggerId = (android.view.autofill.AutofillId) java.util.Objects.requireNonNull(autofillId);
            return this;
        }

        public android.service.autofill.SaveInfo build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new android.service.autofill.SaveInfo(this);
        }

        private void throwIfDestroyed() {
            if (this.mDestroyed) {
                throw new java.lang.IllegalStateException("Already called #build()");
            }
        }
    }

    public java.lang.String toString() {
        if (!android.view.autofill.Helper.sDebug) {
            return super.toString();
        }
        java.lang.StringBuilder append = new java.lang.StringBuilder("SaveInfo: [type=").append(android.util.DebugUtils.flagsToString(android.service.autofill.SaveInfo.class, "SAVE_DATA_TYPE_", this.mType)).append(", requiredIds=").append(java.util.Arrays.toString(this.mRequiredIds)).append(", negative style=").append(android.util.DebugUtils.flagsToString(android.service.autofill.SaveInfo.class, "NEGATIVE_BUTTON_STYLE_", this.mNegativeButtonStyle)).append(", positive style=").append(android.util.DebugUtils.flagsToString(android.service.autofill.SaveInfo.class, "POSITIVE_BUTTON_STYLE_", this.mPositiveButtonStyle));
        if (this.mOptionalIds != null) {
            append.append(", optionalIds=").append(java.util.Arrays.toString(this.mOptionalIds));
        }
        if (this.mDescription != null) {
            append.append(", description=").append(this.mDescription);
        }
        if (this.mFlags != 0) {
            append.append(", flags=").append(this.mFlags);
        }
        if (this.mCustomDescription != null) {
            append.append(", customDescription=").append(this.mCustomDescription);
        }
        if (this.mValidator != null) {
            append.append(", validator=").append(this.mValidator);
        }
        if (this.mSanitizerKeys != null) {
            append.append(", sanitizerKeys=").append(this.mSanitizerKeys.length);
        }
        if (this.mSanitizerValues != null) {
            append.append(", sanitizerValues=").append(this.mSanitizerValues.length);
        }
        if (this.mTriggerId != null) {
            append.append(", triggerId=").append(this.mTriggerId);
        }
        return append.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeParcelableArray(this.mRequiredIds, i);
        parcel.writeParcelableArray(this.mOptionalIds, i);
        parcel.writeInt(this.mNegativeButtonStyle);
        parcel.writeParcelable(this.mNegativeActionListener, i);
        parcel.writeInt(this.mPositiveButtonStyle);
        parcel.writeCharSequence(this.mDescription);
        parcel.writeParcelable(this.mCustomDescription, i);
        parcel.writeParcelable(this.mValidator, i);
        parcel.writeParcelableArray(this.mSanitizerKeys, i);
        if (this.mSanitizerKeys != null) {
            for (int i2 = 0; i2 < this.mSanitizerValues.length; i2++) {
                parcel.writeParcelableArray(this.mSanitizerValues[i2], i);
            }
        }
        parcel.writeParcelable(this.mTriggerId, i);
        parcel.writeInt(this.mFlags);
    }
}
