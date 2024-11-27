package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
class PreferenceInflater extends android.preference.GenericInflater<android.preference.Preference, android.preference.PreferenceGroup> {
    private static final java.lang.String EXTRA_TAG_NAME = "extra";
    private static final java.lang.String INTENT_TAG_NAME = "intent";
    private static final java.lang.String TAG = "PreferenceInflater";
    private android.preference.PreferenceManager mPreferenceManager;

    public PreferenceInflater(android.content.Context context, android.preference.PreferenceManager preferenceManager) {
        super(context);
        init(preferenceManager);
    }

    PreferenceInflater(android.preference.GenericInflater<android.preference.Preference, android.preference.PreferenceGroup> genericInflater, android.preference.PreferenceManager preferenceManager, android.content.Context context) {
        super(genericInflater, context);
        init(preferenceManager);
    }

    @Override // android.preference.GenericInflater
    public android.preference.GenericInflater<android.preference.Preference, android.preference.PreferenceGroup> cloneInContext(android.content.Context context) {
        return new android.preference.PreferenceInflater(this, this.mPreferenceManager, context);
    }

    private void init(android.preference.PreferenceManager preferenceManager) {
        this.mPreferenceManager = preferenceManager;
        setDefaultPackage("android.preference.");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.preference.GenericInflater
    public boolean onCreateCustomFromTag(org.xmlpull.v1.XmlPullParser xmlPullParser, android.preference.Preference preference, android.util.AttributeSet attributeSet) throws org.xmlpull.v1.XmlPullParserException {
        java.lang.String name = xmlPullParser.getName();
        if (name.equals("intent")) {
            try {
                android.content.Intent parseIntent = android.content.Intent.parseIntent(getContext().getResources(), xmlPullParser, attributeSet);
                if (parseIntent != null) {
                    preference.setIntent(parseIntent);
                }
                return true;
            } catch (java.io.IOException e) {
                org.xmlpull.v1.XmlPullParserException xmlPullParserException = new org.xmlpull.v1.XmlPullParserException("Error parsing preference");
                xmlPullParserException.initCause(e);
                throw xmlPullParserException;
            }
        }
        if (name.equals(EXTRA_TAG_NAME)) {
            getContext().getResources().parseBundleExtra(EXTRA_TAG_NAME, attributeSet, preference.getExtras());
            try {
                com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                return true;
            } catch (java.io.IOException e2) {
                org.xmlpull.v1.XmlPullParserException xmlPullParserException2 = new org.xmlpull.v1.XmlPullParserException("Error parsing preference");
                xmlPullParserException2.initCause(e2);
                throw xmlPullParserException2;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.preference.GenericInflater
    public android.preference.PreferenceGroup onMergeRoots(android.preference.PreferenceGroup preferenceGroup, boolean z, android.preference.PreferenceGroup preferenceGroup2) {
        if (preferenceGroup == null) {
            preferenceGroup2.onAttachedToHierarchy(this.mPreferenceManager);
            return preferenceGroup2;
        }
        return preferenceGroup;
    }
}
