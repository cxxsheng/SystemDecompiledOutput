package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class MultiSelectListPreference extends android.preference.DialogPreference {
    private java.lang.CharSequence[] mEntries;
    private java.lang.CharSequence[] mEntryValues;
    private java.util.Set<java.lang.String> mNewValues;
    private boolean mPreferenceChanged;
    private java.util.Set<java.lang.String> mValues;

    public MultiSelectListPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mValues = new java.util.HashSet();
        this.mNewValues = new java.util.HashSet();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.MultiSelectListPreference, i, i2);
        this.mEntries = obtainStyledAttributes.getTextArray(0);
        this.mEntryValues = obtainStyledAttributes.getTextArray(1);
        obtainStyledAttributes.recycle();
    }

    public MultiSelectListPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MultiSelectListPreference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842897);
    }

    public MultiSelectListPreference(android.content.Context context) {
        this(context, null);
    }

    public void setEntries(java.lang.CharSequence[] charSequenceArr) {
        this.mEntries = charSequenceArr;
    }

    public void setEntries(int i) {
        setEntries(getContext().getResources().getTextArray(i));
    }

    public java.lang.CharSequence[] getEntries() {
        return this.mEntries;
    }

    public void setEntryValues(java.lang.CharSequence[] charSequenceArr) {
        this.mEntryValues = charSequenceArr;
    }

    public void setEntryValues(int i) {
        setEntryValues(getContext().getResources().getTextArray(i));
    }

    public java.lang.CharSequence[] getEntryValues() {
        return this.mEntryValues;
    }

    public void setValues(java.util.Set<java.lang.String> set) {
        this.mValues.clear();
        this.mValues.addAll(set);
        persistStringSet(set);
    }

    public java.util.Set<java.lang.String> getValues() {
        return this.mValues;
    }

    public int findIndexOfValue(java.lang.String str) {
        if (str != null && this.mEntryValues != null) {
            for (int length = this.mEntryValues.length - 1; length >= 0; length--) {
                if (this.mEntryValues[length].equals(str)) {
                    return length;
                }
            }
            return -1;
        }
        return -1;
    }

    @Override // android.preference.DialogPreference
    protected void onPrepareDialogBuilder(android.app.AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        if (this.mEntries == null || this.mEntryValues == null) {
            throw new java.lang.IllegalStateException("MultiSelectListPreference requires an entries array and an entryValues array.");
        }
        builder.setMultiChoiceItems(this.mEntries, getSelectedItems(), new android.content.DialogInterface.OnMultiChoiceClickListener() { // from class: android.preference.MultiSelectListPreference.1
            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
            public void onClick(android.content.DialogInterface dialogInterface, int i, boolean z) {
                if (z) {
                    android.preference.MultiSelectListPreference multiSelectListPreference = android.preference.MultiSelectListPreference.this;
                    multiSelectListPreference.mPreferenceChanged = android.preference.MultiSelectListPreference.this.mNewValues.add(android.preference.MultiSelectListPreference.this.mEntryValues[i].toString()) | multiSelectListPreference.mPreferenceChanged;
                } else {
                    android.preference.MultiSelectListPreference multiSelectListPreference2 = android.preference.MultiSelectListPreference.this;
                    multiSelectListPreference2.mPreferenceChanged = android.preference.MultiSelectListPreference.this.mNewValues.remove(android.preference.MultiSelectListPreference.this.mEntryValues[i].toString()) | multiSelectListPreference2.mPreferenceChanged;
                }
            }
        });
        this.mNewValues.clear();
        this.mNewValues.addAll(this.mValues);
    }

    private boolean[] getSelectedItems() {
        java.lang.CharSequence[] charSequenceArr = this.mEntryValues;
        int length = charSequenceArr.length;
        java.util.Set<java.lang.String> set = this.mValues;
        boolean[] zArr = new boolean[length];
        for (int i = 0; i < length; i++) {
            zArr[i] = set.contains(charSequenceArr[i].toString());
        }
        return zArr;
    }

    @Override // android.preference.DialogPreference
    protected void onDialogClosed(boolean z) {
        super.onDialogClosed(z);
        if (z && this.mPreferenceChanged) {
            java.util.Set<java.lang.String> set = this.mNewValues;
            if (callChangeListener(set)) {
                setValues(set);
            }
        }
        this.mPreferenceChanged = false;
    }

    @Override // android.preference.Preference
    protected java.lang.Object onGetDefaultValue(android.content.res.TypedArray typedArray, int i) {
        java.lang.CharSequence[] textArray = typedArray.getTextArray(i);
        java.util.HashSet hashSet = new java.util.HashSet();
        for (java.lang.CharSequence charSequence : textArray) {
            hashSet.add(charSequence.toString());
        }
        return hashSet;
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, java.lang.Object obj) {
        setValues(z ? getPersistedStringSet(this.mValues) : (java.util.Set) obj);
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected android.os.Parcelable onSaveInstanceState() {
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        android.preference.MultiSelectListPreference.SavedState savedState = new android.preference.MultiSelectListPreference.SavedState(onSaveInstanceState);
        savedState.values = getValues();
        return savedState;
    }

    private static class SavedState extends android.preference.Preference.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.preference.MultiSelectListPreference.SavedState> CREATOR = new android.os.Parcelable.Creator<android.preference.MultiSelectListPreference.SavedState>() { // from class: android.preference.MultiSelectListPreference.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.MultiSelectListPreference.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.preference.MultiSelectListPreference.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.MultiSelectListPreference.SavedState[] newArray(int i) {
                return new android.preference.MultiSelectListPreference.SavedState[i];
            }
        };
        java.util.Set<java.lang.String> values;

        public SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.values = new java.util.HashSet();
            for (java.lang.String str : parcel.readStringArray()) {
                this.values.add(str);
            }
        }

        public SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeStringArray((java.lang.String[]) this.values.toArray(new java.lang.String[0]));
        }
    }
}
