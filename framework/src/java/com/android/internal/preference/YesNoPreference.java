package com.android.internal.preference;

/* loaded from: classes5.dex */
public class YesNoPreference extends android.preference.DialogPreference {
    private boolean mWasPositiveResult;

    public YesNoPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public YesNoPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public YesNoPreference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842896);
    }

    public YesNoPreference(android.content.Context context) {
        this(context, null);
    }

    @Override // android.preference.DialogPreference
    protected void onDialogClosed(boolean z) {
        super.onDialogClosed(z);
        if (callChangeListener(java.lang.Boolean.valueOf(z))) {
            setValue(z);
        }
    }

    public void setValue(boolean z) {
        this.mWasPositiveResult = z;
        persistBoolean(z);
        notifyDependencyChange(!z);
    }

    public boolean getValue() {
        return this.mWasPositiveResult;
    }

    @Override // android.preference.Preference
    protected java.lang.Object onGetDefaultValue(android.content.res.TypedArray typedArray, int i) {
        return java.lang.Boolean.valueOf(typedArray.getBoolean(i, false));
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, java.lang.Object obj) {
        setValue(z ? getPersistedBoolean(this.mWasPositiveResult) : ((java.lang.Boolean) obj).booleanValue());
    }

    @Override // android.preference.Preference
    public boolean shouldDisableDependents() {
        return !this.mWasPositiveResult || super.shouldDisableDependents();
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected android.os.Parcelable onSaveInstanceState() {
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        com.android.internal.preference.YesNoPreference.SavedState savedState = new com.android.internal.preference.YesNoPreference.SavedState(onSaveInstanceState);
        savedState.wasPositiveResult = getValue();
        return savedState;
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (!parcelable.getClass().equals(com.android.internal.preference.YesNoPreference.SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        com.android.internal.preference.YesNoPreference.SavedState savedState = (com.android.internal.preference.YesNoPreference.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setValue(savedState.wasPositiveResult);
    }

    private static class SavedState extends android.preference.Preference.BaseSavedState {
        public static final android.os.Parcelable.Creator<com.android.internal.preference.YesNoPreference.SavedState> CREATOR = new android.os.Parcelable.Creator<com.android.internal.preference.YesNoPreference.SavedState>() { // from class: com.android.internal.preference.YesNoPreference.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.preference.YesNoPreference.SavedState createFromParcel(android.os.Parcel parcel) {
                return new com.android.internal.preference.YesNoPreference.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.preference.YesNoPreference.SavedState[] newArray(int i) {
                return new com.android.internal.preference.YesNoPreference.SavedState[i];
            }
        };
        boolean wasPositiveResult;

        public SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.wasPositiveResult = parcel.readInt() == 1;
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.wasPositiveResult ? 1 : 0);
        }

        public SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }
    }
}
