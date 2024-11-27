package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class CheckBoxPreference extends android.preference.TwoStatePreference {
    public CheckBoxPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CheckBoxPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.CheckBoxPreference, i, i2);
        setSummaryOn(obtainStyledAttributes.getString(0));
        setSummaryOff(obtainStyledAttributes.getString(1));
        setDisableDependentsState(obtainStyledAttributes.getBoolean(2, false));
        obtainStyledAttributes.recycle();
    }

    public CheckBoxPreference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842895);
    }

    public CheckBoxPreference(android.content.Context context) {
        this(context, null);
    }

    @Override // android.preference.Preference
    protected void onBindView(android.view.View view) {
        super.onBindView(view);
        android.view.KeyEvent.Callback findViewById = view.findViewById(16908289);
        if (findViewById != null && (findViewById instanceof android.widget.Checkable)) {
            ((android.widget.Checkable) findViewById).setChecked(this.mChecked);
        }
        syncSummaryView(view);
    }
}
