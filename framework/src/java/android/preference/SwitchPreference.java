package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class SwitchPreference extends android.preference.TwoStatePreference {
    private final android.preference.SwitchPreference.Listener mListener;
    private java.lang.CharSequence mSwitchOff;
    private java.lang.CharSequence mSwitchOn;

    private class Listener implements android.widget.CompoundButton.OnCheckedChangeListener {
        private Listener() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(android.widget.CompoundButton compoundButton, boolean z) {
            if (!android.preference.SwitchPreference.this.callChangeListener(java.lang.Boolean.valueOf(z))) {
                compoundButton.setChecked(!z);
            } else {
                android.preference.SwitchPreference.this.setChecked(z);
            }
        }
    }

    public SwitchPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mListener = new android.preference.SwitchPreference.Listener();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.SwitchPreference, i, i2);
        setSummaryOn(obtainStyledAttributes.getString(0));
        setSummaryOff(obtainStyledAttributes.getString(1));
        setSwitchTextOn(obtainStyledAttributes.getString(3));
        setSwitchTextOff(obtainStyledAttributes.getString(4));
        setDisableDependentsState(obtainStyledAttributes.getBoolean(2, false));
        obtainStyledAttributes.recycle();
    }

    public SwitchPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SwitchPreference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16843629);
    }

    public SwitchPreference(android.content.Context context) {
        this(context, null);
    }

    @Override // android.preference.Preference
    protected void onBindView(android.view.View view) {
        super.onBindView(view);
        android.view.KeyEvent.Callback findViewById = view.findViewById(16908352);
        if (findViewById != null && (findViewById instanceof android.widget.Checkable)) {
            boolean z = findViewById instanceof android.widget.Switch;
            if (z) {
                ((android.widget.Switch) findViewById).setOnCheckedChangeListener(null);
            }
            ((android.widget.Checkable) findViewById).setChecked(this.mChecked);
            if (z) {
                android.widget.Switch r0 = (android.widget.Switch) findViewById;
                r0.setTextOn(this.mSwitchOn);
                r0.setTextOff(this.mSwitchOff);
                r0.setOnCheckedChangeListener(this.mListener);
            }
        }
        syncSummaryView(view);
    }

    public void setSwitchTextOn(java.lang.CharSequence charSequence) {
        this.mSwitchOn = charSequence;
        notifyChanged();
    }

    public void setSwitchTextOff(java.lang.CharSequence charSequence) {
        this.mSwitchOff = charSequence;
        notifyChanged();
    }

    public void setSwitchTextOn(int i) {
        setSwitchTextOn(getContext().getString(i));
    }

    public void setSwitchTextOff(int i) {
        setSwitchTextOff(getContext().getString(i));
    }

    public java.lang.CharSequence getSwitchTextOn() {
        return this.mSwitchOn;
    }

    public java.lang.CharSequence getSwitchTextOff() {
        return this.mSwitchOff;
    }
}
