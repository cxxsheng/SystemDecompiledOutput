package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class ListPreference extends android.preference.DialogPreference {
    private int mClickedDialogEntryIndex;
    private java.lang.CharSequence[] mEntries;
    private java.lang.CharSequence[] mEntryValues;
    private java.lang.String mSummary;
    private java.lang.String mValue;
    private boolean mValueSet;

    public ListPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ListPreference, i, i2);
        this.mEntries = obtainStyledAttributes.getTextArray(0);
        this.mEntryValues = obtainStyledAttributes.getTextArray(1);
        obtainStyledAttributes.recycle();
        android.content.res.TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Preference, i, i2);
        this.mSummary = obtainStyledAttributes2.getString(7);
        obtainStyledAttributes2.recycle();
    }

    public ListPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ListPreference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842897);
    }

    public ListPreference(android.content.Context context) {
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

    public void setValue(java.lang.String str) {
        boolean z = !android.text.TextUtils.equals(this.mValue, str);
        if (z || !this.mValueSet) {
            this.mValue = str;
            this.mValueSet = true;
            persistString(str);
            if (z) {
                notifyChanged();
            }
        }
    }

    @Override // android.preference.Preference
    public java.lang.CharSequence getSummary() {
        java.lang.CharSequence entry = getEntry();
        if (this.mSummary == null) {
            return super.getSummary();
        }
        java.lang.String str = this.mSummary;
        if (entry == null) {
            entry = "";
        }
        return java.lang.String.format(str, entry);
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

    public void setValueIndex(int i) {
        if (this.mEntryValues != null) {
            setValue(this.mEntryValues[i].toString());
        }
    }

    public java.lang.String getValue() {
        return this.mValue;
    }

    public java.lang.CharSequence getEntry() {
        int valueIndex = getValueIndex();
        if (valueIndex < 0 || this.mEntries == null) {
            return null;
        }
        return this.mEntries[valueIndex];
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

    private int getValueIndex() {
        return findIndexOfValue(this.mValue);
    }

    @Override // android.preference.DialogPreference
    protected void onPrepareDialogBuilder(android.app.AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        if (this.mEntries == null || this.mEntryValues == null) {
            throw new java.lang.IllegalStateException("ListPreference requires an entries array and an entryValues array.");
        }
        this.mClickedDialogEntryIndex = getValueIndex();
        builder.setSingleChoiceItems(this.mEntries, this.mClickedDialogEntryIndex, new android.content.DialogInterface.OnClickListener() { // from class: android.preference.ListPreference.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(android.content.DialogInterface dialogInterface, int i) {
                android.preference.ListPreference.this.mClickedDialogEntryIndex = i;
                android.preference.ListPreference.this.onClick(dialogInterface, -1);
                android.preference.ListPreference.this.postDismiss();
            }
        });
        builder.setPositiveButton((java.lang.CharSequence) null, (android.content.DialogInterface.OnClickListener) null);
    }

    @Override // android.preference.DialogPreference
    protected void onDialogClosed(boolean z) {
        super.onDialogClosed(z);
        if (z && this.mClickedDialogEntryIndex >= 0 && this.mEntryValues != null) {
            java.lang.String charSequence = this.mEntryValues[this.mClickedDialogEntryIndex].toString();
            if (callChangeListener(charSequence)) {
                setValue(charSequence);
            }
        }
    }

    @Override // android.preference.Preference
    protected java.lang.Object onGetDefaultValue(android.content.res.TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, java.lang.Object obj) {
        setValue(z ? getPersistedString(this.mValue) : (java.lang.String) obj);
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected android.os.Parcelable onSaveInstanceState() {
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        android.preference.ListPreference.SavedState savedState = new android.preference.ListPreference.SavedState(onSaveInstanceState);
        savedState.value = getValue();
        return savedState;
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(android.preference.ListPreference.SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        android.preference.ListPreference.SavedState savedState = (android.preference.ListPreference.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setValue(savedState.value);
    }

    private static class SavedState extends android.preference.Preference.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.preference.ListPreference.SavedState> CREATOR = new android.os.Parcelable.Creator<android.preference.ListPreference.SavedState>() { // from class: android.preference.ListPreference.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.ListPreference.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.preference.ListPreference.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.ListPreference.SavedState[] newArray(int i) {
                return new android.preference.ListPreference.SavedState[i];
            }
        };
        java.lang.String value;

        public SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.value = parcel.readString();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.value);
        }

        public SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }
    }
}
