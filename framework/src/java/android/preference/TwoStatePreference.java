package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class TwoStatePreference extends android.preference.Preference {
    boolean mChecked;
    private boolean mCheckedSet;
    private boolean mDisableDependentsState;
    private java.lang.CharSequence mSummaryOff;
    private java.lang.CharSequence mSummaryOn;

    public TwoStatePreference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public TwoStatePreference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TwoStatePreference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TwoStatePreference(android.content.Context context) {
        this(context, null);
    }

    @Override // android.preference.Preference
    protected void onClick() {
        super.onClick();
        boolean z = !isChecked();
        if (callChangeListener(java.lang.Boolean.valueOf(z))) {
            setChecked(z);
        }
    }

    public void setChecked(boolean z) {
        boolean z2 = this.mChecked != z;
        if (z2 || !this.mCheckedSet) {
            this.mChecked = z;
            this.mCheckedSet = true;
            persistBoolean(z);
            if (z2) {
                notifyDependencyChange(shouldDisableDependents());
                notifyChanged();
            }
        }
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    @Override // android.preference.Preference
    public boolean shouldDisableDependents() {
        return (this.mDisableDependentsState ? this.mChecked : !this.mChecked) || super.shouldDisableDependents();
    }

    public void setSummaryOn(java.lang.CharSequence charSequence) {
        this.mSummaryOn = charSequence;
        if (isChecked()) {
            notifyChanged();
        }
    }

    public void setSummaryOn(int i) {
        setSummaryOn(getContext().getString(i));
    }

    public java.lang.CharSequence getSummaryOn() {
        return this.mSummaryOn;
    }

    public void setSummaryOff(java.lang.CharSequence charSequence) {
        this.mSummaryOff = charSequence;
        if (!isChecked()) {
            notifyChanged();
        }
    }

    public void setSummaryOff(int i) {
        setSummaryOff(getContext().getString(i));
    }

    public java.lang.CharSequence getSummaryOff() {
        return this.mSummaryOff;
    }

    public boolean getDisableDependentsState() {
        return this.mDisableDependentsState;
    }

    public void setDisableDependentsState(boolean z) {
        this.mDisableDependentsState = z;
    }

    @Override // android.preference.Preference
    protected java.lang.Object onGetDefaultValue(android.content.res.TypedArray typedArray, int i) {
        return java.lang.Boolean.valueOf(typedArray.getBoolean(i, false));
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, java.lang.Object obj) {
        setChecked(z ? getPersistedBoolean(this.mChecked) : ((java.lang.Boolean) obj).booleanValue());
    }

    void syncSummaryView(android.view.View view) {
        boolean z;
        android.widget.TextView textView = (android.widget.TextView) view.findViewById(16908304);
        if (textView != null) {
            if (this.mChecked && !android.text.TextUtils.isEmpty(this.mSummaryOn)) {
                textView.lambda$setTextAsync$0(this.mSummaryOn);
                z = false;
            } else if (!this.mChecked && !android.text.TextUtils.isEmpty(this.mSummaryOff)) {
                textView.lambda$setTextAsync$0(this.mSummaryOff);
                z = false;
            } else {
                z = true;
            }
            if (z) {
                java.lang.CharSequence summary = getSummary();
                if (!android.text.TextUtils.isEmpty(summary)) {
                    textView.lambda$setTextAsync$0(summary);
                    z = false;
                }
            }
            int i = z ? 8 : 0;
            if (i != textView.getVisibility()) {
                textView.setVisibility(i);
            }
        }
    }

    @Override // android.preference.Preference
    protected android.os.Parcelable onSaveInstanceState() {
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        android.preference.TwoStatePreference.SavedState savedState = new android.preference.TwoStatePreference.SavedState(onSaveInstanceState);
        savedState.checked = isChecked();
        return savedState;
    }

    @Override // android.preference.Preference
    protected void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(android.preference.TwoStatePreference.SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        android.preference.TwoStatePreference.SavedState savedState = (android.preference.TwoStatePreference.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setChecked(savedState.checked);
    }

    static class SavedState extends android.preference.Preference.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.preference.TwoStatePreference.SavedState> CREATOR = new android.os.Parcelable.Creator<android.preference.TwoStatePreference.SavedState>() { // from class: android.preference.TwoStatePreference.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.TwoStatePreference.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.preference.TwoStatePreference.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.TwoStatePreference.SavedState[] newArray(int i) {
                return new android.preference.TwoStatePreference.SavedState[i];
            }
        };
        boolean checked;

        public SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.checked = parcel.readInt() == 1;
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.checked ? 1 : 0);
        }

        public SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }
    }
}
