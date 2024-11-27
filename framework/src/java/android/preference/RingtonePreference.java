package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class RingtonePreference extends android.preference.Preference implements android.preference.PreferenceManager.OnActivityResultListener {
    private static final java.lang.String TAG = "RingtonePreference";
    private int mRequestCode;
    private int mRingtoneType;
    private boolean mShowDefault;
    private boolean mShowSilent;

    public RingtonePreference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.RingtonePreference, i, i2);
        this.mRingtoneType = obtainStyledAttributes.getInt(0, 1);
        this.mShowDefault = obtainStyledAttributes.getBoolean(1, true);
        this.mShowSilent = obtainStyledAttributes.getBoolean(2, true);
        obtainStyledAttributes.recycle();
    }

    public RingtonePreference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RingtonePreference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842899);
    }

    public RingtonePreference(android.content.Context context) {
        this(context, null);
    }

    public int getRingtoneType() {
        return this.mRingtoneType;
    }

    public void setRingtoneType(int i) {
        this.mRingtoneType = i;
    }

    public boolean getShowDefault() {
        return this.mShowDefault;
    }

    public void setShowDefault(boolean z) {
        this.mShowDefault = z;
    }

    public boolean getShowSilent() {
        return this.mShowSilent;
    }

    public void setShowSilent(boolean z) {
        this.mShowSilent = z;
    }

    @Override // android.preference.Preference
    protected void onClick() {
        android.content.Intent intent = new android.content.Intent(android.media.RingtoneManager.ACTION_RINGTONE_PICKER);
        onPrepareRingtonePickerIntent(intent);
        android.preference.PreferenceFragment fragment = getPreferenceManager().getFragment();
        if (fragment != null) {
            fragment.startActivityForResult(intent, this.mRequestCode);
        } else {
            getPreferenceManager().getActivity().startActivityForResult(intent, this.mRequestCode);
        }
    }

    protected void onPrepareRingtonePickerIntent(android.content.Intent intent) {
        intent.putExtra(android.media.RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, onRestoreRingtone());
        intent.putExtra(android.media.RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, this.mShowDefault);
        if (this.mShowDefault) {
            intent.putExtra(android.media.RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, android.media.RingtoneManager.getDefaultUri(getRingtoneType()));
        }
        intent.putExtra(android.media.RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, this.mShowSilent);
        intent.putExtra(android.media.RingtoneManager.EXTRA_RINGTONE_TYPE, this.mRingtoneType);
        intent.putExtra(android.media.RingtoneManager.EXTRA_RINGTONE_TITLE, getTitle());
        intent.putExtra(android.media.RingtoneManager.EXTRA_RINGTONE_AUDIO_ATTRIBUTES_FLAGS, 64);
    }

    protected void onSaveRingtone(android.net.Uri uri) {
        persistString(uri != null ? uri.toString() : "");
    }

    protected android.net.Uri onRestoreRingtone() {
        java.lang.String persistedString = getPersistedString(null);
        if (android.text.TextUtils.isEmpty(persistedString)) {
            return null;
        }
        return android.net.Uri.parse(persistedString);
    }

    @Override // android.preference.Preference
    protected java.lang.Object onGetDefaultValue(android.content.res.TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, java.lang.Object obj) {
        java.lang.String str = (java.lang.String) obj;
        if (!z && !android.text.TextUtils.isEmpty(str)) {
            onSaveRingtone(android.net.Uri.parse(str));
        }
    }

    @Override // android.preference.Preference
    protected void onAttachedToHierarchy(android.preference.PreferenceManager preferenceManager) {
        super.onAttachedToHierarchy(preferenceManager);
        preferenceManager.registerOnActivityResultListener(this);
        this.mRequestCode = preferenceManager.getNextRequestCode();
    }

    @Override // android.preference.PreferenceManager.OnActivityResultListener
    public boolean onActivityResult(int i, int i2, android.content.Intent intent) {
        if (i == this.mRequestCode) {
            if (intent != null) {
                android.net.Uri uri = (android.net.Uri) intent.getParcelableExtra(android.media.RingtoneManager.EXTRA_RINGTONE_PICKED_URI, android.net.Uri.class);
                if (callChangeListener(uri != null ? uri.toString() : "")) {
                    onSaveRingtone(uri);
                    return true;
                }
                return true;
            }
            return true;
        }
        return false;
    }
}
