package android.app;

/* loaded from: classes.dex */
public final class RemoteInput implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.RemoteInput> CREATOR = new android.os.Parcelable.Creator<android.app.RemoteInput>() { // from class: android.app.RemoteInput.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RemoteInput createFromParcel(android.os.Parcel parcel) {
            return new android.app.RemoteInput(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RemoteInput[] newArray(int i) {
            return new android.app.RemoteInput[i];
        }
    };
    private static final int DEFAULT_FLAGS = 1;
    public static final int EDIT_CHOICES_BEFORE_SENDING_AUTO = 0;
    public static final int EDIT_CHOICES_BEFORE_SENDING_DISABLED = 1;
    public static final int EDIT_CHOICES_BEFORE_SENDING_ENABLED = 2;
    private static final java.lang.String EXTRA_DATA_TYPE_RESULTS_DATA = "android.remoteinput.dataTypeResultsData";
    public static final java.lang.String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
    private static final java.lang.String EXTRA_RESULTS_SOURCE = "android.remoteinput.resultsSource";
    private static final int FLAG_ALLOW_FREE_FORM_INPUT = 1;
    public static final java.lang.String RESULTS_CLIP_LABEL = "android.remoteinput.results";
    public static final int SOURCE_CHOICE = 1;
    public static final int SOURCE_FREE_FORM_INPUT = 0;
    private final android.util.ArraySet<java.lang.String> mAllowedDataTypes;
    private final java.lang.CharSequence[] mChoices;
    private final int mEditChoicesBeforeSending;
    private final android.os.Bundle mExtras;
    private final int mFlags;
    private final java.lang.CharSequence mLabel;
    private final java.lang.String mResultKey;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EditChoicesBeforeSending {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Source {
    }

    private RemoteInput(java.lang.String str, java.lang.CharSequence charSequence, java.lang.CharSequence[] charSequenceArr, int i, int i2, android.os.Bundle bundle, android.util.ArraySet<java.lang.String> arraySet) {
        this.mResultKey = str;
        this.mLabel = charSequence;
        this.mChoices = charSequenceArr;
        this.mFlags = i;
        this.mEditChoicesBeforeSending = i2;
        this.mExtras = bundle;
        this.mAllowedDataTypes = arraySet;
        if (getEditChoicesBeforeSending() == 2 && !getAllowFreeFormInput()) {
            throw new java.lang.IllegalArgumentException("setEditChoicesBeforeSending requires setAllowFreeFormInput");
        }
    }

    public java.lang.String getResultKey() {
        return this.mResultKey;
    }

    public java.lang.CharSequence getLabel() {
        return this.mLabel;
    }

    public java.lang.CharSequence[] getChoices() {
        return this.mChoices;
    }

    public java.util.Set<java.lang.String> getAllowedDataTypes() {
        return this.mAllowedDataTypes;
    }

    public boolean isDataOnly() {
        return !getAllowFreeFormInput() && (getChoices() == null || getChoices().length == 0) && !getAllowedDataTypes().isEmpty();
    }

    public boolean getAllowFreeFormInput() {
        return (this.mFlags & 1) != 0;
    }

    public int getEditChoicesBeforeSending() {
        return this.mEditChoicesBeforeSending;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public static final class Builder {
        private java.lang.CharSequence[] mChoices;
        private java.lang.CharSequence mLabel;
        private final java.lang.String mResultKey;
        private final android.util.ArraySet<java.lang.String> mAllowedDataTypes = new android.util.ArraySet<>();
        private final android.os.Bundle mExtras = new android.os.Bundle();
        private int mFlags = 1;
        private int mEditChoicesBeforeSending = 0;

        public Builder(java.lang.String str) {
            if (str == null) {
                throw new java.lang.IllegalArgumentException("Result key can't be null");
            }
            this.mResultKey = str;
        }

        public android.app.RemoteInput.Builder setLabel(java.lang.CharSequence charSequence) {
            this.mLabel = android.app.Notification.safeCharSequence(charSequence);
            return this;
        }

        public android.app.RemoteInput.Builder setChoices(java.lang.CharSequence[] charSequenceArr) {
            if (charSequenceArr == null) {
                this.mChoices = null;
            } else {
                this.mChoices = new java.lang.CharSequence[charSequenceArr.length];
                for (int i = 0; i < charSequenceArr.length; i++) {
                    this.mChoices[i] = android.app.Notification.safeCharSequence(charSequenceArr[i]);
                }
            }
            return this;
        }

        public android.app.RemoteInput.Builder setAllowDataType(java.lang.String str, boolean z) {
            if (z) {
                this.mAllowedDataTypes.add(str);
            } else {
                this.mAllowedDataTypes.remove(str);
            }
            return this;
        }

        public android.app.RemoteInput.Builder setAllowFreeFormInput(boolean z) {
            setFlag(1, z);
            return this;
        }

        public android.app.RemoteInput.Builder setEditChoicesBeforeSending(int i) {
            this.mEditChoicesBeforeSending = i;
            return this;
        }

        public android.app.RemoteInput.Builder addExtras(android.os.Bundle bundle) {
            if (bundle != null) {
                this.mExtras.putAll(bundle);
            }
            return this;
        }

        public android.os.Bundle getExtras() {
            return this.mExtras;
        }

        private void setFlag(int i, boolean z) {
            if (z) {
                this.mFlags = i | this.mFlags;
            } else {
                this.mFlags = (~i) & this.mFlags;
            }
        }

        public android.app.RemoteInput build() {
            return new android.app.RemoteInput(this.mResultKey, this.mLabel, this.mChoices, this.mFlags, this.mEditChoicesBeforeSending, this.mExtras, this.mAllowedDataTypes);
        }
    }

    private RemoteInput(android.os.Parcel parcel) {
        this.mResultKey = parcel.readString();
        this.mLabel = parcel.readCharSequence();
        this.mChoices = parcel.readCharSequenceArray();
        this.mFlags = parcel.readInt();
        this.mEditChoicesBeforeSending = parcel.readInt();
        this.mExtras = parcel.readBundle();
        this.mAllowedDataTypes = parcel.readArraySet(null);
    }

    public static java.util.Map<java.lang.String, android.net.Uri> getDataResultsFromIntent(android.content.Intent intent, java.lang.String str) {
        java.lang.String substring;
        java.lang.String string;
        android.content.Intent clipDataIntentFromIntent = getClipDataIntentFromIntent(intent);
        if (clipDataIntentFromIntent == null) {
            return null;
        }
        java.util.HashMap hashMap = new java.util.HashMap();
        for (java.lang.String str2 : clipDataIntentFromIntent.getExtras().keySet()) {
            if (str2.startsWith(EXTRA_DATA_TYPE_RESULTS_DATA) && (substring = str2.substring(EXTRA_DATA_TYPE_RESULTS_DATA.length())) != null && !substring.isEmpty() && (string = clipDataIntentFromIntent.getBundleExtra(str2).getString(str)) != null && !string.isEmpty()) {
                hashMap.put(substring, android.net.Uri.parse(string));
            }
        }
        if (hashMap.isEmpty()) {
            return null;
        }
        return hashMap;
    }

    public static android.os.Bundle getResultsFromIntent(android.content.Intent intent) {
        android.content.Intent clipDataIntentFromIntent = getClipDataIntentFromIntent(intent);
        if (clipDataIntentFromIntent == null) {
            return null;
        }
        return (android.os.Bundle) clipDataIntentFromIntent.getParcelableExtra(EXTRA_RESULTS_DATA, android.os.Bundle.class);
    }

    public static void addResultsToIntent(android.app.RemoteInput[] remoteInputArr, android.content.Intent intent, android.os.Bundle bundle) {
        android.content.Intent clipDataIntentFromIntent = getClipDataIntentFromIntent(intent);
        if (clipDataIntentFromIntent == null) {
            clipDataIntentFromIntent = new android.content.Intent();
        }
        android.os.Bundle bundleExtra = clipDataIntentFromIntent.getBundleExtra(EXTRA_RESULTS_DATA);
        if (bundleExtra == null) {
            bundleExtra = new android.os.Bundle();
        }
        for (android.app.RemoteInput remoteInput : remoteInputArr) {
            java.lang.Object obj = bundle.get(remoteInput.getResultKey());
            if (obj instanceof java.lang.CharSequence) {
                bundleExtra.putCharSequence(remoteInput.getResultKey(), (java.lang.CharSequence) obj);
            }
        }
        clipDataIntentFromIntent.putExtra(EXTRA_RESULTS_DATA, bundleExtra);
        intent.setClipData(android.content.ClipData.newIntent(RESULTS_CLIP_LABEL, clipDataIntentFromIntent));
    }

    public static void addDataResultToIntent(android.app.RemoteInput remoteInput, android.content.Intent intent, java.util.Map<java.lang.String, android.net.Uri> map) {
        android.content.Intent clipDataIntentFromIntent = getClipDataIntentFromIntent(intent);
        if (clipDataIntentFromIntent == null) {
            clipDataIntentFromIntent = new android.content.Intent();
        }
        for (java.util.Map.Entry<java.lang.String, android.net.Uri> entry : map.entrySet()) {
            java.lang.String key = entry.getKey();
            android.net.Uri value = entry.getValue();
            if (key != null) {
                android.os.Bundle bundleExtra = clipDataIntentFromIntent.getBundleExtra(getExtraResultsKeyForData(key));
                if (bundleExtra == null) {
                    bundleExtra = new android.os.Bundle();
                }
                bundleExtra.putString(remoteInput.getResultKey(), value.toString());
                clipDataIntentFromIntent.putExtra(getExtraResultsKeyForData(key), bundleExtra);
            }
        }
        intent.setClipData(android.content.ClipData.newIntent(RESULTS_CLIP_LABEL, clipDataIntentFromIntent));
    }

    public static void setResultsSource(android.content.Intent intent, int i) {
        android.content.Intent clipDataIntentFromIntent = getClipDataIntentFromIntent(intent);
        if (clipDataIntentFromIntent == null) {
            clipDataIntentFromIntent = new android.content.Intent();
        }
        clipDataIntentFromIntent.putExtra(EXTRA_RESULTS_SOURCE, i);
        intent.setClipData(android.content.ClipData.newIntent(RESULTS_CLIP_LABEL, clipDataIntentFromIntent));
    }

    public static int getResultsSource(android.content.Intent intent) {
        android.content.Intent clipDataIntentFromIntent = getClipDataIntentFromIntent(intent);
        if (clipDataIntentFromIntent == null) {
            return 0;
        }
        return clipDataIntentFromIntent.getExtras().getInt(EXTRA_RESULTS_SOURCE, 0);
    }

    private static java.lang.String getExtraResultsKeyForData(java.lang.String str) {
        return EXTRA_DATA_TYPE_RESULTS_DATA + str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mResultKey);
        parcel.writeCharSequence(this.mLabel);
        parcel.writeCharSequenceArray(this.mChoices);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mEditChoicesBeforeSending);
        parcel.writeBundle(this.mExtras);
        parcel.writeArraySet(this.mAllowedDataTypes);
    }

    private static android.content.Intent getClipDataIntentFromIntent(android.content.Intent intent) {
        android.content.ClipData clipData = intent.getClipData();
        if (clipData == null) {
            return null;
        }
        android.content.ClipDescription description = clipData.getDescription();
        if (!description.hasMimeType(android.content.ClipDescription.MIMETYPE_TEXT_INTENT) || !description.getLabel().equals(RESULTS_CLIP_LABEL)) {
            return null;
        }
        return clipData.getItemAt(0).getIntent();
    }
}
