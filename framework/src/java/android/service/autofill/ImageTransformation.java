package android.service.autofill;

/* loaded from: classes3.dex */
public final class ImageTransformation extends android.service.autofill.InternalTransformation implements android.service.autofill.Transformation, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.ImageTransformation> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.ImageTransformation>() { // from class: android.service.autofill.ImageTransformation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.ImageTransformation createFromParcel(android.os.Parcel parcel) {
            android.service.autofill.ImageTransformation.Builder builder;
            android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) parcel.readParcelable(null, android.view.autofill.AutofillId.class);
            java.util.regex.Pattern[] patternArr = (java.util.regex.Pattern[]) parcel.readSerializable();
            int[] createIntArray = parcel.createIntArray();
            java.lang.CharSequence[] readCharSequenceArray = parcel.readCharSequenceArray();
            java.lang.CharSequence charSequence = readCharSequenceArray[0];
            if (charSequence != null) {
                builder = new android.service.autofill.ImageTransformation.Builder(autofillId, patternArr[0], createIntArray[0], charSequence);
            } else {
                builder = new android.service.autofill.ImageTransformation.Builder(autofillId, patternArr[0], createIntArray[0]);
            }
            int length = patternArr.length;
            for (int i = 1; i < length; i++) {
                if (readCharSequenceArray[i] != null) {
                    builder.addOption(patternArr[i], createIntArray[i], readCharSequenceArray[i]);
                } else {
                    builder.addOption(patternArr[i], createIntArray[i]);
                }
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.ImageTransformation[] newArray(int i) {
            return new android.service.autofill.ImageTransformation[i];
        }
    };
    private static final java.lang.String TAG = "ImageTransformation";
    private final android.view.autofill.AutofillId mId;
    private final java.util.ArrayList<android.service.autofill.ImageTransformation.Option> mOptions;

    private ImageTransformation(android.service.autofill.ImageTransformation.Builder builder) {
        this.mId = builder.mId;
        this.mOptions = builder.mOptions;
    }

    @Override // android.service.autofill.InternalTransformation
    public void apply(android.service.autofill.ValueFinder valueFinder, android.widget.RemoteViews remoteViews, int i) throws java.lang.Exception {
        java.lang.String findByAutofillId = valueFinder.findByAutofillId(this.mId);
        if (findByAutofillId == null) {
            android.util.Log.w(TAG, "No view for id " + this.mId);
            return;
        }
        int size = this.mOptions.size();
        if (android.view.autofill.Helper.sDebug) {
            android.util.Log.d(TAG, size + " multiple options on id " + i + " to compare against");
        }
        for (int i2 = 0; i2 < size; i2++) {
            android.service.autofill.ImageTransformation.Option option = this.mOptions.get(i2);
            try {
                if (option.pattern.matcher(findByAutofillId).matches()) {
                    android.util.Log.d(TAG, "Found match at " + i2 + ": " + option);
                    remoteViews.setImageViewResource(i, option.resId);
                    if (option.contentDescription != null) {
                        remoteViews.setContentDescription(i, option.contentDescription);
                        return;
                    }
                    return;
                }
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "Error matching regex #" + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + option.pattern + ") on id " + option.resId + ": " + e.getClass());
                throw e;
            }
        }
        if (android.view.autofill.Helper.sDebug) {
            android.util.Log.d(TAG, "No match for " + findByAutofillId);
        }
    }

    public static class Builder {
        private boolean mDestroyed;
        private final android.view.autofill.AutofillId mId;
        private final java.util.ArrayList<android.service.autofill.ImageTransformation.Option> mOptions = new java.util.ArrayList<>();

        @java.lang.Deprecated
        public Builder(android.view.autofill.AutofillId autofillId, java.util.regex.Pattern pattern, int i) {
            this.mId = (android.view.autofill.AutofillId) java.util.Objects.requireNonNull(autofillId);
            addOption(pattern, i);
        }

        public Builder(android.view.autofill.AutofillId autofillId, java.util.regex.Pattern pattern, int i, java.lang.CharSequence charSequence) {
            this.mId = (android.view.autofill.AutofillId) java.util.Objects.requireNonNull(autofillId);
            addOption(pattern, i, charSequence);
        }

        @java.lang.Deprecated
        public android.service.autofill.ImageTransformation.Builder addOption(java.util.regex.Pattern pattern, int i) {
            addOptionInternal(pattern, i, null);
            return this;
        }

        public android.service.autofill.ImageTransformation.Builder addOption(java.util.regex.Pattern pattern, int i, java.lang.CharSequence charSequence) {
            addOptionInternal(pattern, i, (java.lang.CharSequence) java.util.Objects.requireNonNull(charSequence));
            return this;
        }

        private void addOptionInternal(java.util.regex.Pattern pattern, int i, java.lang.CharSequence charSequence) {
            throwIfDestroyed();
            java.util.Objects.requireNonNull(pattern);
            com.android.internal.util.Preconditions.checkArgument(i != 0);
            this.mOptions.add(new android.service.autofill.ImageTransformation.Option(pattern, i, charSequence));
        }

        public android.service.autofill.ImageTransformation build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new android.service.autofill.ImageTransformation(this);
        }

        private void throwIfDestroyed() {
            com.android.internal.util.Preconditions.checkState(!this.mDestroyed, "Already called build()");
        }
    }

    public java.lang.String toString() {
        return !android.view.autofill.Helper.sDebug ? super.toString() : "ImageTransformation: [id=" + this.mId + ", options=" + this.mOptions + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.Serializable, java.util.regex.Pattern[]] */
    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mId, i);
        int size = this.mOptions.size();
        ?? r0 = new java.util.regex.Pattern[size];
        int[] iArr = new int[size];
        java.lang.String[] strArr = new java.lang.String[size];
        for (int i2 = 0; i2 < size; i2++) {
            android.service.autofill.ImageTransformation.Option option = this.mOptions.get(i2);
            r0[i2] = option.pattern;
            iArr[i2] = option.resId;
            strArr[i2] = option.contentDescription;
        }
        parcel.writeSerializable(r0);
        parcel.writeIntArray(iArr);
        parcel.writeCharSequenceArray(strArr);
    }

    private static final class Option {
        public final java.lang.CharSequence contentDescription;
        public final java.util.regex.Pattern pattern;
        public final int resId;

        Option(java.util.regex.Pattern pattern, int i, java.lang.CharSequence charSequence) {
            this.pattern = pattern;
            this.resId = i;
            this.contentDescription = android.text.TextUtils.trimNoCopySpans(charSequence);
        }
    }
}
