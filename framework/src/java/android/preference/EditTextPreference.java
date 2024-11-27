package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class EditTextPreference extends android.preference.DialogPreference {
    private android.widget.EditText mEditText;
    private java.lang.String mText;
    private boolean mTextSet;

    public EditTextPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mEditText = new android.widget.EditText(context, attributeSet);
        this.mEditText.setId(16908291);
        this.mEditText.setEnabled(true);
    }

    public EditTextPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public EditTextPreference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842898);
    }

    public EditTextPreference(android.content.Context context) {
        this(context, null);
    }

    public void setText(java.lang.String str) {
        boolean z = !android.text.TextUtils.equals(this.mText, str);
        if (z || !this.mTextSet) {
            this.mText = str;
            this.mTextSet = true;
            persistString(str);
            if (z) {
                notifyDependencyChange(shouldDisableDependents());
                notifyChanged();
            }
        }
    }

    public java.lang.String getText() {
        return this.mText;
    }

    @Override // android.preference.DialogPreference
    protected void onBindDialogView(android.view.View view) {
        super.onBindDialogView(view);
        android.widget.EditText editText = this.mEditText;
        editText.lambda$setTextAsync$0(getText());
        android.view.ViewParent parent = editText.getParent();
        if (parent != view) {
            if (parent != null) {
                ((android.view.ViewGroup) parent).removeView(editText);
            }
            onAddEditTextToDialogView(view, editText);
        }
    }

    @Override // android.preference.DialogPreference
    protected void showDialog(android.os.Bundle bundle) {
        super.showDialog(bundle);
        this.mEditText.requestFocus();
        this.mEditText.getWindowInsetsController().show(android.view.WindowInsets.Type.ime());
    }

    protected void onAddEditTextToDialogView(android.view.View view, android.widget.EditText editText) {
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) view.findViewById(com.android.internal.R.id.edittext_container);
        if (viewGroup != null) {
            viewGroup.addView(editText, -1, -2);
        }
    }

    @Override // android.preference.DialogPreference
    protected void onDialogClosed(boolean z) {
        super.onDialogClosed(z);
        if (z) {
            java.lang.String obj = this.mEditText.getText().toString();
            if (callChangeListener(obj)) {
                setText(obj);
            }
        }
    }

    @Override // android.preference.Preference
    protected java.lang.Object onGetDefaultValue(android.content.res.TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, java.lang.Object obj) {
        setText(z ? getPersistedString(this.mText) : (java.lang.String) obj);
    }

    @Override // android.preference.Preference
    public boolean shouldDisableDependents() {
        return android.text.TextUtils.isEmpty(this.mText) || super.shouldDisableDependents();
    }

    public android.widget.EditText getEditText() {
        return this.mEditText;
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected android.os.Parcelable onSaveInstanceState() {
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        android.preference.EditTextPreference.SavedState savedState = new android.preference.EditTextPreference.SavedState(onSaveInstanceState);
        savedState.text = getText();
        return savedState;
    }

    @Override // android.preference.DialogPreference, android.preference.Preference
    protected void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(android.preference.EditTextPreference.SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        android.preference.EditTextPreference.SavedState savedState = (android.preference.EditTextPreference.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setText(savedState.text);
    }

    private static class SavedState extends android.preference.Preference.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.preference.EditTextPreference.SavedState> CREATOR = new android.os.Parcelable.Creator<android.preference.EditTextPreference.SavedState>() { // from class: android.preference.EditTextPreference.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.EditTextPreference.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.preference.EditTextPreference.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.EditTextPreference.SavedState[] newArray(int i) {
                return new android.preference.EditTextPreference.SavedState[i];
            }
        };
        java.lang.String text;

        public SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.text = parcel.readString();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.text);
        }

        public SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }
    }
}
