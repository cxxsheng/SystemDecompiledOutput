package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class PreferenceCategory extends android.preference.PreferenceGroup {
    private static final java.lang.String TAG = "PreferenceCategory";

    public PreferenceCategory(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public PreferenceCategory(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public PreferenceCategory(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842892);
    }

    public PreferenceCategory(android.content.Context context) {
        this(context, null);
    }

    @Override // android.preference.PreferenceGroup
    protected boolean onPrepareAddPreference(android.preference.Preference preference) {
        if (preference instanceof android.preference.PreferenceCategory) {
            throw new java.lang.IllegalArgumentException("Cannot add a PreferenceCategory directly to a PreferenceCategory");
        }
        return super.onPrepareAddPreference(preference);
    }

    @Override // android.preference.Preference
    public boolean isEnabled() {
        return false;
    }

    @Override // android.preference.Preference
    public boolean shouldDisableDependents() {
        return !super.isEnabled();
    }
}
