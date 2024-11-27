package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class MultiCheckPreference extends android.preference.DialogPreference {
    private java.lang.CharSequence[] mEntries;
    private java.lang.String[] mEntryValues;
    private boolean[] mOrigValues;
    private boolean[] mSetValues;
    private java.lang.String mSummary;

    public MultiCheckPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ListPreference, i, i2);
        this.mEntries = obtainStyledAttributes.getTextArray(0);
        if (this.mEntries != null) {
            setEntries(this.mEntries);
        }
        setEntryValuesCS(obtainStyledAttributes.getTextArray(1));
        obtainStyledAttributes.recycle();
        android.content.res.TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Preference, 0, 0);
        this.mSummary = obtainStyledAttributes2.getString(7);
        obtainStyledAttributes2.recycle();
    }

    public MultiCheckPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MultiCheckPreference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842897);
    }

    public MultiCheckPreference(android.content.Context context) {
        this(context, null);
    }

    public void setEntries(java.lang.CharSequence[] charSequenceArr) {
        this.mEntries = charSequenceArr;
        this.mSetValues = new boolean[charSequenceArr.length];
        this.mOrigValues = new boolean[charSequenceArr.length];
    }

    public void setEntries(int i) {
        setEntries(getContext().getResources().getTextArray(i));
    }

    public java.lang.CharSequence[] getEntries() {
        return this.mEntries;
    }

    public void setEntryValues(java.lang.String[] strArr) {
        this.mEntryValues = strArr;
        java.util.Arrays.fill(this.mSetValues, false);
        java.util.Arrays.fill(this.mOrigValues, false);
    }

    public void setEntryValues(int i) {
        setEntryValuesCS(getContext().getResources().getTextArray(i));
    }

    private void setEntryValuesCS(java.lang.CharSequence[] charSequenceArr) {
        setValues(null);
        if (charSequenceArr != null) {
            this.mEntryValues = new java.lang.String[charSequenceArr.length];
            for (int i = 0; i < charSequenceArr.length; i++) {
                this.mEntryValues[i] = charSequenceArr[i].toString();
            }
        }
    }

    public java.lang.String[] getEntryValues() {
        return this.mEntryValues;
    }

    public boolean getValue(int i) {
        return this.mSetValues[i];
    }

    public void setValue(int i, boolean z) {
        this.mSetValues[i] = z;
    }

    public void setValues(boolean[] zArr) {
        if (this.mSetValues != null) {
            java.util.Arrays.fill(this.mSetValues, false);
            java.util.Arrays.fill(this.mOrigValues, false);
            if (zArr != null) {
                java.lang.System.arraycopy(zArr, 0, this.mSetValues, 0, zArr.length < this.mSetValues.length ? zArr.length : this.mSetValues.length);
            }
        }
    }

    @Override // android.preference.Preference
    public java.lang.CharSequence getSummary() {
        if (this.mSummary == null) {
            return super.getSummary();
        }
        return this.mSummary;
    }

    @Override // android.preference.Preference
    public void setSummary(java.lang.CharSequence charSequence) {
        super.setSummary(charSequence);
        if (charSequence == null && this.mSummary != null) {
            this.mSummary = null;
        } else if (charSequence != null && !charSequence.equals(this.mSummary)) {
            this.mSummary = charSequence.toString();
        }
    }

    public boolean[] getValues() {
        return this.mSetValues;
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
            throw new java.lang.IllegalStateException("ListPreference requires an entries array and an entryValues array.");
        }
        this.mOrigValues = java.util.Arrays.copyOf(this.mSetValues, this.mSetValues.length);
        builder.setMultiChoiceItems(this.mEntries, this.mSetValues, new android.content.DialogInterface.OnMultiChoiceClickListener() { // from class: android.preference.MultiCheckPreference.1
            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
            public void onClick(android.content.DialogInterface dialogInterface, int i, boolean z) {
                android.preference.MultiCheckPreference.this.mSetValues[i] = z;
            }
        });
    }

    @Override // android.preference.DialogPreference
    protected void onDialogClosed(boolean z) {
        super.onDialogClosed(z);
        if (z && callChangeListener(getValues())) {
            return;
        }
        java.lang.System.arraycopy(this.mOrigValues, 0, this.mSetValues, 0, this.mSetValues.length);
    }

    @Override // android.preference.Preference
    protected java.lang.Object onGetDefaultValue(android.content.res.TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, java.lang.Object obj) {
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected android.os.Parcelable onSaveInstanceState() {
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        android.preference.MultiCheckPreference.SavedState savedState = new android.preference.MultiCheckPreference.SavedState(onSaveInstanceState);
        savedState.values = getValues();
        return savedState;
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(android.preference.MultiCheckPreference.SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        android.preference.MultiCheckPreference.SavedState savedState = (android.preference.MultiCheckPreference.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setValues(savedState.values);
    }

    private static class SavedState extends android.preference.Preference.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.preference.MultiCheckPreference.SavedState> CREATOR = new android.os.Parcelable.Creator<android.preference.MultiCheckPreference.SavedState>() { // from class: android.preference.MultiCheckPreference.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.MultiCheckPreference.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.preference.MultiCheckPreference.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.MultiCheckPreference.SavedState[] newArray(int i) {
                return new android.preference.MultiCheckPreference.SavedState[i];
            }
        };
        boolean[] values;

        public SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.values = parcel.createBooleanArray();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeBooleanArray(this.values);
        }

        public SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }
    }
}
