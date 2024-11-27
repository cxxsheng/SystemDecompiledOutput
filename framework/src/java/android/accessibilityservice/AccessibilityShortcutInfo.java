package android.accessibilityservice;

/* loaded from: classes.dex */
public final class AccessibilityShortcutInfo {
    public static final java.lang.String META_DATA = "android.accessibilityshortcut.target";
    private static final java.lang.String TAG_ACCESSIBILITY_SHORTCUT = "accessibility-shortcut-target";
    private final android.content.pm.ActivityInfo mActivityInfo;
    private final int mAnimatedImageRes;
    private final android.content.ComponentName mComponentName;
    private final int mDescriptionResId;
    private final int mHtmlDescriptionRes;
    private final int mIntroResId;
    private java.lang.String mSettingsActivityName;
    private final int mSummaryResId;
    private java.lang.String mTileServiceName;

    public AccessibilityShortcutInfo(android.content.Context context, android.content.pm.ActivityInfo activityInfo) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        this.mComponentName = activityInfo.getComponentName();
        this.mActivityInfo = activityInfo;
        try {
            android.content.res.XmlResourceParser loadXmlMetaData = this.mActivityInfo.loadXmlMetaData(packageManager, META_DATA);
            try {
                if (loadXmlMetaData == null) {
                    throw new org.xmlpull.v1.XmlPullParserException("Meta-data accessibility-shortcut-target does not exist");
                }
                for (int i = 0; i != 1 && i != 2; i = loadXmlMetaData.next()) {
                }
                if (!TAG_ACCESSIBILITY_SHORTCUT.equals(loadXmlMetaData.getName())) {
                    throw new org.xmlpull.v1.XmlPullParserException("Meta-data does not start withaccessibility-shortcut-target tag");
                }
                android.content.res.TypedArray obtainAttributes = packageManager.getResourcesForApplication(this.mActivityInfo.applicationInfo).obtainAttributes(android.util.Xml.asAttributeSet(loadXmlMetaData), com.android.internal.R.styleable.AccessibilityShortcutTarget);
                this.mDescriptionResId = obtainAttributes.getResourceId(0, 0);
                this.mSummaryResId = obtainAttributes.getResourceId(1, 0);
                this.mAnimatedImageRes = obtainAttributes.getResourceId(3, 0);
                this.mHtmlDescriptionRes = obtainAttributes.getResourceId(4, 0);
                this.mSettingsActivityName = obtainAttributes.getString(2);
                this.mTileServiceName = obtainAttributes.getString(5);
                this.mIntroResId = obtainAttributes.getResourceId(6, 0);
                obtainAttributes.recycle();
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
            } finally {
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new org.xmlpull.v1.XmlPullParserException("Unable to create context for: " + this.mActivityInfo.packageName);
        }
    }

    public android.content.pm.ActivityInfo getActivityInfo() {
        return this.mActivityInfo;
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public java.lang.String loadSummary(android.content.pm.PackageManager packageManager) {
        return loadResourceString(packageManager, this.mActivityInfo, this.mSummaryResId);
    }

    public java.lang.String loadIntro(android.content.pm.PackageManager packageManager) {
        return loadResourceString(packageManager, this.mActivityInfo, this.mIntroResId);
    }

    public java.lang.String loadDescription(android.content.pm.PackageManager packageManager) {
        return loadResourceString(packageManager, this.mActivityInfo, this.mDescriptionResId);
    }

    public int getAnimatedImageRes() {
        return this.mAnimatedImageRes;
    }

    public android.graphics.drawable.Drawable loadAnimatedImage(android.content.Context context) {
        if (this.mAnimatedImageRes == 0) {
            return null;
        }
        return android.accessibilityservice.util.AccessibilityUtils.loadSafeAnimatedImage(context, this.mActivityInfo.applicationInfo, this.mAnimatedImageRes);
    }

    public java.lang.String loadHtmlDescription(android.content.pm.PackageManager packageManager) {
        java.lang.String loadResourceString = loadResourceString(packageManager, this.mActivityInfo, this.mHtmlDescriptionRes);
        if (loadResourceString != null) {
            return android.accessibilityservice.util.AccessibilityUtils.getFilteredHtmlText(loadResourceString);
        }
        return null;
    }

    public java.lang.String getSettingsActivityName() {
        return this.mSettingsActivityName;
    }

    public java.lang.String getTileServiceName() {
        return this.mTileServiceName;
    }

    private java.lang.String loadResourceString(android.content.pm.PackageManager packageManager, android.content.pm.ActivityInfo activityInfo, int i) {
        java.lang.CharSequence text;
        if (i == 0 || (text = packageManager.getText(activityInfo.packageName, i, activityInfo.applicationInfo)) == null) {
            return null;
        }
        return text.toString().trim();
    }

    public int hashCode() {
        return (this.mComponentName == null ? 0 : this.mComponentName.hashCode()) + 31;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.accessibilityservice.AccessibilityShortcutInfo accessibilityShortcutInfo = (android.accessibilityservice.AccessibilityShortcutInfo) obj;
        if (this.mComponentName == null) {
            if (accessibilityShortcutInfo.mComponentName != null) {
                return false;
            }
        } else if (!this.mComponentName.equals(accessibilityShortcutInfo.mComponentName)) {
            return false;
        }
        return true;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("AccessibilityShortcutInfo[");
        sb.append("activityInfo: ").append(this.mActivityInfo);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
