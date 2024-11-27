package android.content.pm;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class UserProperties implements android.os.Parcelable {
    private static final java.lang.String ATTR_ALLOW_STOPPING_USER_WITH_DELAYED_LOCKING = "allowStoppingUserWithDelayedLocking";
    private static final java.lang.String ATTR_ALWAYS_VISIBLE = "alwaysVisible";
    private static final java.lang.String ATTR_AUTH_ALWAYS_REQUIRED_TO_DISABLE_QUIET_MODE = "authAlwaysRequiredToDisableQuietMode";
    private static final java.lang.String ATTR_CREDENTIAL_SHAREABLE_WITH_PARENT = "credentialShareableWithParent";
    private static final java.lang.String ATTR_CROSS_PROFILE_CONTENT_SHARING_STRATEGY = "crossProfileContentSharingStrategy";
    private static final java.lang.String ATTR_CROSS_PROFILE_INTENT_FILTER_ACCESS_CONTROL = "crossProfileIntentFilterAccessControl";
    private static final java.lang.String ATTR_CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY = "crossProfileIntentResolutionStrategy";
    private static final java.lang.String ATTR_DELETE_APP_WITH_PARENT = "deleteAppWithParent";
    private static final java.lang.String ATTR_INHERIT_DEVICE_POLICY = "inheritDevicePolicy";
    private static final java.lang.String ATTR_MEDIA_SHARED_WITH_PARENT = "mediaSharedWithParent";
    private static final java.lang.String ATTR_PROFILE_API_VISIBILITY = "profileApiVisibility";
    private static final java.lang.String ATTR_SHOW_IN_LAUNCHER = "showInLauncher";
    private static final java.lang.String ATTR_SHOW_IN_QUIET_MODE = "showInQuietMode";
    private static final java.lang.String ATTR_SHOW_IN_SETTINGS = "showInSettings";
    private static final java.lang.String ATTR_SHOW_IN_SHARING_SURFACES = "showInSharingSurfaces";
    private static final java.lang.String ATTR_START_WITH_PARENT = "startWithParent";
    private static final java.lang.String ATTR_UPDATE_CROSS_PROFILE_INTENT_FILTERS_ON_OTA = "updateCrossProfileIntentFiltersOnOTA";
    private static final java.lang.String ATTR_USE_PARENTS_CONTACTS = "useParentsContacts";
    public static final int CROSS_PROFILE_CONTENT_SHARING_DELEGATE_FROM_PARENT = 1;
    public static final int CROSS_PROFILE_CONTENT_SHARING_NO_DELEGATION = 0;
    public static final int CROSS_PROFILE_CONTENT_SHARING_UNKNOWN = -1;
    public static final int CROSS_PROFILE_INTENT_FILTER_ACCESS_LEVEL_ALL = 0;
    public static final int CROSS_PROFILE_INTENT_FILTER_ACCESS_LEVEL_SYSTEM = 10;
    public static final int CROSS_PROFILE_INTENT_FILTER_ACCESS_LEVEL_SYSTEM_ADD_ONLY = 20;
    public static final int CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY_DEFAULT = 0;
    public static final int CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY_NO_FILTERING = 1;
    private static final int INDEX_ALLOW_STOPPING_USER_WITH_DELAYED_LOCKING = 16;
    private static final int INDEX_ALWAYS_VISIBLE = 11;
    private static final int INDEX_AUTH_ALWAYS_REQUIRED_TO_DISABLE_QUIET_MODE = 13;
    private static final int INDEX_CREDENTIAL_SHAREABLE_WITH_PARENT = 9;
    private static final int INDEX_CROSS_PROFILE_CONTENT_SHARING_STRATEGY = 15;
    private static final int INDEX_CROSS_PROFILE_INTENT_FILTER_ACCESS_CONTROL = 6;
    private static final int INDEX_CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY = 7;
    private static final int INDEX_DELETE_APP_WITH_PARENT = 10;
    private static final int INDEX_INHERIT_DEVICE_POLICY = 3;
    private static final int INDEX_ITEMS_RESTRICTED_ON_HOME_SCREEN = 18;
    private static final int INDEX_MEDIA_SHARED_WITH_PARENT = 8;
    private static final int INDEX_PROFILE_API_VISIBILITY = 17;
    private static final int INDEX_SHOW_IN_LAUNCHER = 0;
    private static final int INDEX_SHOW_IN_QUIET_MODE = 12;
    private static final int INDEX_SHOW_IN_SETTINGS = 2;
    private static final int INDEX_SHOW_IN_SHARING_SURFACES = 14;
    private static final int INDEX_START_WITH_PARENT = 1;
    private static final int INDEX_UPDATE_CROSS_PROFILE_INTENT_FILTERS_ON_OTA = 5;
    private static final int INDEX_USE_PARENTS_CONTACTS = 4;
    public static final int INHERIT_DEVICE_POLICY_FROM_PARENT = 1;
    public static final int INHERIT_DEVICE_POLICY_NO = 0;
    private static final java.lang.String ITEMS_RESTRICTED_ON_HOME_SCREEN = "itemsRestrictedOnHomeScreen";
    public static final int PROFILE_API_VISIBILITY_HIDDEN = 1;
    public static final int PROFILE_API_VISIBILITY_UNKNOWN = -1;
    public static final int PROFILE_API_VISIBILITY_VISIBLE = 0;
    public static final int SHOW_IN_LAUNCHER_NO = 2;
    public static final int SHOW_IN_LAUNCHER_SEPARATE = 1;
    public static final int SHOW_IN_LAUNCHER_UNKNOWN = -1;
    public static final int SHOW_IN_LAUNCHER_WITH_PARENT = 0;
    public static final int SHOW_IN_QUIET_MODE_DEFAULT = 2;
    public static final int SHOW_IN_QUIET_MODE_HIDDEN = 1;
    public static final int SHOW_IN_QUIET_MODE_PAUSED = 0;
    public static final int SHOW_IN_QUIET_MODE_UNKNOWN = -1;
    public static final int SHOW_IN_SETTINGS_NO = 2;
    public static final int SHOW_IN_SETTINGS_SEPARATE = 1;
    public static final int SHOW_IN_SETTINGS_UNKNOWN = -1;
    public static final int SHOW_IN_SETTINGS_WITH_PARENT = 0;
    public static final int SHOW_IN_SHARING_SURFACES_NO = 2;
    public static final int SHOW_IN_SHARING_SURFACES_SEPARATE = 1;
    public static final int SHOW_IN_SHARING_SURFACES_UNKNOWN = -1;
    public static final int SHOW_IN_SHARING_SURFACES_WITH_PARENT = 0;
    private boolean mAllowStoppingUserWithDelayedLocking;
    private boolean mAlwaysVisible;
    private boolean mAuthAlwaysRequiredToDisableQuietMode;
    private boolean mCredentialShareableWithParent;
    private int mCrossProfileContentSharingStrategy;
    private int mCrossProfileIntentFilterAccessControl;
    private int mCrossProfileIntentResolutionStrategy;
    private final android.content.pm.UserProperties mDefaultProperties;
    private boolean mDeleteAppWithParent;
    private int mInheritDevicePolicy;
    private boolean mItemsRestrictedOnHomeScreen;
    private boolean mMediaSharedWithParent;
    private int mProfileApiVisibility;
    private long mPropertiesPresent;
    private int mShowInLauncher;
    private int mShowInQuietMode;
    private int mShowInSettings;
    private int mShowInSharingSurfaces;
    private boolean mStartWithParent;
    private boolean mUpdateCrossProfileIntentFiltersOnOTA;
    private boolean mUseParentsContacts;
    private static final java.lang.String LOG_TAG = android.content.pm.UserProperties.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.content.pm.UserProperties> CREATOR = new android.os.Parcelable.Creator<android.content.pm.UserProperties>() { // from class: android.content.pm.UserProperties.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.UserProperties createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.UserProperties(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.UserProperties[] newArray(int i) {
            return new android.content.pm.UserProperties[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CrossProfileContentSharingStrategy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CrossProfileIntentFilterAccessControlLevel {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CrossProfileIntentResolutionStrategy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InheritDevicePolicy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProfileApiVisibility {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface PropertyIndex {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShowInLauncher {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShowInQuietMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShowInSettings {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShowInSharingSurfaces {
    }

    public UserProperties(android.content.pm.UserProperties userProperties) {
        this.mPropertiesPresent = 0L;
        this.mDefaultProperties = userProperties;
        this.mPropertiesPresent = 0L;
    }

    public UserProperties(android.content.pm.UserProperties userProperties, boolean z, boolean z2, boolean z3) {
        this.mPropertiesPresent = 0L;
        if (userProperties.mDefaultProperties == null) {
            throw new java.lang.IllegalArgumentException("Attempting to copy a non-original UserProperties.");
        }
        this.mDefaultProperties = null;
        if (z) {
            setStartWithParent(userProperties.getStartWithParent());
            setInheritDevicePolicy(userProperties.getInheritDevicePolicy());
            setUpdateCrossProfileIntentFiltersOnOTA(userProperties.getUpdateCrossProfileIntentFiltersOnOTA());
            setCrossProfileIntentFilterAccessControl(userProperties.getCrossProfileIntentFilterAccessControl());
            setCrossProfileIntentResolutionStrategy(userProperties.getCrossProfileIntentResolutionStrategy());
            setDeleteAppWithParent(userProperties.getDeleteAppWithParent());
            setAlwaysVisible(userProperties.getAlwaysVisible());
            setAllowStoppingUserWithDelayedLocking(userProperties.getAllowStoppingUserWithDelayedLocking());
        }
        if (z2) {
            setShowInSettings(userProperties.getShowInSettings());
            setUseParentsContacts(userProperties.getUseParentsContacts());
            setAuthAlwaysRequiredToDisableQuietMode(userProperties.isAuthAlwaysRequiredToDisableQuietMode());
        }
        setShowInLauncher(userProperties.getShowInLauncher());
        setMediaSharedWithParent(userProperties.isMediaSharedWithParent());
        setCredentialShareableWithParent(userProperties.isCredentialShareableWithParent());
        setShowInQuietMode(userProperties.getShowInQuietMode());
        setShowInSharingSurfaces(userProperties.getShowInSharingSurfaces());
        setCrossProfileContentSharingStrategy(userProperties.getCrossProfileContentSharingStrategy());
        setProfileApiVisibility(userProperties.getProfileApiVisibility());
        setItemsRestrictedOnHomeScreen(userProperties.areItemsRestrictedOnHomeScreen());
    }

    private boolean isPresent(long j) {
        return ((1 << ((int) j)) & this.mPropertiesPresent) != 0;
    }

    private void setPresent(long j) {
        this.mPropertiesPresent = (1 << ((int) j)) | this.mPropertiesPresent;
    }

    public long getPropertiesPresent() {
        return this.mPropertiesPresent;
    }

    public int getShowInLauncher() {
        if (isPresent(0L)) {
            return this.mShowInLauncher;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mShowInLauncher;
        }
        throw new java.lang.SecurityException("You don't have permission to query showInLauncher");
    }

    public void setShowInLauncher(int i) {
        this.mShowInLauncher = i;
        setPresent(0L);
    }

    public int getShowInSettings() {
        if (isPresent(2L)) {
            return this.mShowInSettings;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mShowInSettings;
        }
        throw new java.lang.SecurityException("You don't have permission to query mShowInSettings");
    }

    public void setShowInSettings(int i) {
        this.mShowInSettings = i;
        setPresent(2L);
    }

    public int getShowInQuietMode() {
        if (isPresent(12L)) {
            return this.mShowInQuietMode;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mShowInQuietMode;
        }
        throw new java.lang.SecurityException("You don't have permission to query ShowInQuietMode");
    }

    public void setShowInQuietMode(int i) {
        this.mShowInQuietMode = i;
        setPresent(12L);
    }

    public int getShowInSharingSurfaces() {
        if (isPresent(14L)) {
            return this.mShowInSharingSurfaces;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mShowInSharingSurfaces;
        }
        throw new java.lang.SecurityException("You don't have permission to query ShowInSharingSurfaces");
    }

    public void setShowInSharingSurfaces(int i) {
        this.mShowInSharingSurfaces = i;
        setPresent(14L);
    }

    public boolean getStartWithParent() {
        if (isPresent(1L)) {
            return this.mStartWithParent;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mStartWithParent;
        }
        throw new java.lang.SecurityException("You don't have permission to query startWithParent");
    }

    public void setStartWithParent(boolean z) {
        this.mStartWithParent = z;
        setPresent(1L);
    }

    public boolean getDeleteAppWithParent() {
        if (isPresent(10L)) {
            return this.mDeleteAppWithParent;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mDeleteAppWithParent;
        }
        throw new java.lang.SecurityException("You don't have permission to query deleteAppWithParent");
    }

    public void setDeleteAppWithParent(boolean z) {
        this.mDeleteAppWithParent = z;
        setPresent(10L);
    }

    public boolean getAlwaysVisible() {
        if (isPresent(11L)) {
            return this.mAlwaysVisible;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mAlwaysVisible;
        }
        throw new java.lang.SecurityException("You don't have permission to query alwaysVisible");
    }

    public void setAlwaysVisible(boolean z) {
        this.mAlwaysVisible = z;
        setPresent(11L);
    }

    public int getInheritDevicePolicy() {
        if (isPresent(3L)) {
            return this.mInheritDevicePolicy;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mInheritDevicePolicy;
        }
        throw new java.lang.SecurityException("You don't have permission to query inheritDevicePolicy");
    }

    public void setInheritDevicePolicy(int i) {
        this.mInheritDevicePolicy = i;
        setPresent(3L);
    }

    public boolean getUseParentsContacts() {
        if (isPresent(4L)) {
            return this.mUseParentsContacts;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mUseParentsContacts;
        }
        throw new java.lang.SecurityException("You don't have permission to query useParentsContacts");
    }

    public void setUseParentsContacts(boolean z) {
        this.mUseParentsContacts = z;
        setPresent(4L);
    }

    public boolean getUpdateCrossProfileIntentFiltersOnOTA() {
        if (isPresent(5L)) {
            return this.mUpdateCrossProfileIntentFiltersOnOTA;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mUpdateCrossProfileIntentFiltersOnOTA;
        }
        throw new java.lang.SecurityException("You don't have permission to query updateCrossProfileIntentFiltersOnOTA");
    }

    public void setUpdateCrossProfileIntentFiltersOnOTA(boolean z) {
        this.mUpdateCrossProfileIntentFiltersOnOTA = z;
        setPresent(5L);
    }

    public boolean isMediaSharedWithParent() {
        if (isPresent(8L)) {
            return this.mMediaSharedWithParent;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mMediaSharedWithParent;
        }
        throw new java.lang.SecurityException("You don't have permission to query mediaSharedWithParent");
    }

    public void setMediaSharedWithParent(boolean z) {
        this.mMediaSharedWithParent = z;
        setPresent(8L);
    }

    public boolean isCredentialShareableWithParent() {
        if (isPresent(9L)) {
            return this.mCredentialShareableWithParent;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mCredentialShareableWithParent;
        }
        throw new java.lang.SecurityException("You don't have permission to query credentialShareableWithParent");
    }

    public void setCredentialShareableWithParent(boolean z) {
        this.mCredentialShareableWithParent = z;
        setPresent(9L);
    }

    public boolean isAuthAlwaysRequiredToDisableQuietMode() {
        if (isPresent(13L)) {
            return this.mAuthAlwaysRequiredToDisableQuietMode;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mAuthAlwaysRequiredToDisableQuietMode;
        }
        throw new java.lang.SecurityException("You don't have permission to query authAlwaysRequiredToDisableQuietMode");
    }

    public void setAuthAlwaysRequiredToDisableQuietMode(boolean z) {
        this.mAuthAlwaysRequiredToDisableQuietMode = z;
        setPresent(13L);
    }

    public boolean getAllowStoppingUserWithDelayedLocking() {
        if (isPresent(16L)) {
            return this.mAllowStoppingUserWithDelayedLocking;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mAllowStoppingUserWithDelayedLocking;
        }
        throw new java.lang.SecurityException("You don't have permission to query allowStoppingUserWithDelayedLocking");
    }

    public void setAllowStoppingUserWithDelayedLocking(boolean z) {
        this.mAllowStoppingUserWithDelayedLocking = z;
        setPresent(16L);
    }

    public int getCrossProfileIntentFilterAccessControl() {
        if (isPresent(6L)) {
            return this.mCrossProfileIntentFilterAccessControl;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mCrossProfileIntentFilterAccessControl;
        }
        throw new java.lang.SecurityException("You don't have permission to query crossProfileIntentFilterAccessControl");
    }

    public void setCrossProfileIntentFilterAccessControl(int i) {
        this.mCrossProfileIntentFilterAccessControl = i;
        setPresent(6L);
    }

    public int getCrossProfileIntentResolutionStrategy() {
        if (isPresent(7L)) {
            return this.mCrossProfileIntentResolutionStrategy;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mCrossProfileIntentResolutionStrategy;
        }
        throw new java.lang.SecurityException("You don't have permission to query crossProfileIntentResolutionStrategy");
    }

    public void setCrossProfileIntentResolutionStrategy(int i) {
        this.mCrossProfileIntentResolutionStrategy = i;
        setPresent(7L);
    }

    public int getCrossProfileContentSharingStrategy() {
        if (isPresent(15L)) {
            return this.mCrossProfileContentSharingStrategy;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mCrossProfileContentSharingStrategy;
        }
        throw new java.lang.SecurityException("You don't have permission to query crossProfileContentSharingStrategy");
    }

    public void setCrossProfileContentSharingStrategy(int i) {
        this.mCrossProfileContentSharingStrategy = i;
        setPresent(15L);
    }

    public int getProfileApiVisibility() {
        if (isPresent(17L)) {
            return this.mProfileApiVisibility;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mProfileApiVisibility;
        }
        throw new java.lang.SecurityException("You don't have permission to query profileApiVisibility");
    }

    public void setProfileApiVisibility(int i) {
        this.mProfileApiVisibility = i;
        setPresent(17L);
    }

    public boolean areItemsRestrictedOnHomeScreen() {
        if (isPresent(18L)) {
            return this.mItemsRestrictedOnHomeScreen;
        }
        if (this.mDefaultProperties != null) {
            return this.mDefaultProperties.mItemsRestrictedOnHomeScreen;
        }
        throw new java.lang.SecurityException("You don't have permission to query mItemsRestrictedOnHomeScreen");
    }

    public void setItemsRestrictedOnHomeScreen(boolean z) {
        this.mItemsRestrictedOnHomeScreen = z;
        setPresent(18L);
    }

    public java.lang.String toString() {
        return "UserProperties{mPropertiesPresent=" + java.lang.Long.toBinaryString(this.mPropertiesPresent) + ", mShowInLauncher=" + getShowInLauncher() + ", mStartWithParent=" + getStartWithParent() + ", mShowInSettings=" + getShowInSettings() + ", mInheritDevicePolicy=" + getInheritDevicePolicy() + ", mUseParentsContacts=" + getUseParentsContacts() + ", mUpdateCrossProfileIntentFiltersOnOTA=" + getUpdateCrossProfileIntentFiltersOnOTA() + ", mCrossProfileIntentFilterAccessControl=" + getCrossProfileIntentFilterAccessControl() + ", mCrossProfileIntentResolutionStrategy=" + getCrossProfileIntentResolutionStrategy() + ", mMediaSharedWithParent=" + isMediaSharedWithParent() + ", mCredentialShareableWithParent=" + isCredentialShareableWithParent() + ", mAuthAlwaysRequiredToDisableQuietMode=" + isAuthAlwaysRequiredToDisableQuietMode() + ", mAllowStoppingUserWithDelayedLocking=" + getAllowStoppingUserWithDelayedLocking() + ", mDeleteAppWithParent=" + getDeleteAppWithParent() + ", mAlwaysVisible=" + getAlwaysVisible() + ", mCrossProfileContentSharingStrategy=" + getCrossProfileContentSharingStrategy() + ", mProfileApiVisibility=" + getProfileApiVisibility() + ", mItemsRestrictedOnHomeScreen=" + areItemsRestrictedOnHomeScreen() + "}";
    }

    public void println(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "UserProperties:");
        printWriter.println(str + "    mPropertiesPresent=" + java.lang.Long.toBinaryString(this.mPropertiesPresent));
        printWriter.println(str + "    mShowInLauncher=" + getShowInLauncher());
        printWriter.println(str + "    mStartWithParent=" + getStartWithParent());
        printWriter.println(str + "    mShowInSettings=" + getShowInSettings());
        printWriter.println(str + "    mInheritDevicePolicy=" + getInheritDevicePolicy());
        printWriter.println(str + "    mUseParentsContacts=" + getUseParentsContacts());
        printWriter.println(str + "    mUpdateCrossProfileIntentFiltersOnOTA=" + getUpdateCrossProfileIntentFiltersOnOTA());
        printWriter.println(str + "    mCrossProfileIntentFilterAccessControl=" + getCrossProfileIntentFilterAccessControl());
        printWriter.println(str + "    mCrossProfileIntentResolutionStrategy=" + getCrossProfileIntentResolutionStrategy());
        printWriter.println(str + "    mMediaSharedWithParent=" + isMediaSharedWithParent());
        printWriter.println(str + "    mCredentialShareableWithParent=" + isCredentialShareableWithParent());
        printWriter.println(str + "    mAuthAlwaysRequiredToDisableQuietMode=" + isAuthAlwaysRequiredToDisableQuietMode());
        printWriter.println(str + "    mAllowStoppingUserWithDelayedLocking=" + getAllowStoppingUserWithDelayedLocking());
        printWriter.println(str + "    mDeleteAppWithParent=" + getDeleteAppWithParent());
        printWriter.println(str + "    mAlwaysVisible=" + getAlwaysVisible());
        printWriter.println(str + "    mCrossProfileContentSharingStrategy=" + getCrossProfileContentSharingStrategy());
        printWriter.println(str + "    mProfileApiVisibility=" + getProfileApiVisibility());
        printWriter.println(str + "    mItemsRestrictedOnHomeScreen=" + areItemsRestrictedOnHomeScreen());
    }

    public UserProperties(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.content.pm.UserProperties userProperties) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        this(userProperties);
        updateFromXml(typedXmlPullParser);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void updateFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        char c;
        int attributeCount = typedXmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            java.lang.String attributeName = typedXmlPullParser.getAttributeName(i);
            switch (attributeName.hashCode()) {
                case -1740726433:
                    if (attributeName.equals(ATTR_CROSS_PROFILE_CONTENT_SHARING_STRATEGY)) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case -1612179643:
                    if (attributeName.equals(ATTR_SHOW_IN_SETTINGS)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1572579485:
                    if (attributeName.equals(ATTR_ALWAYS_VISIBLE)) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -1093353053:
                    if (attributeName.equals(ATTR_PROFILE_API_VISIBILITY)) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case -1041116634:
                    if (attributeName.equals(ATTR_DELETE_APP_WITH_PARENT)) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -934956400:
                    if (attributeName.equals(ATTR_UPDATE_CROSS_PROFILE_INTENT_FILTERS_ON_OTA)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -842277572:
                    if (attributeName.equals(ATTR_CROSS_PROFILE_INTENT_FILTER_ACCESS_CONTROL)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -627117223:
                    if (attributeName.equals(ATTR_MEDIA_SHARED_WITH_PARENT)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -552376349:
                    if (attributeName.equals(ATTR_ALLOW_STOPPING_USER_WITH_DELAYED_LOCKING)) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -317094126:
                    if (attributeName.equals(ATTR_START_WITH_PARENT)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -159094078:
                    if (attributeName.equals(ATTR_SHOW_IN_LAUNCHER)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 428661202:
                    if (attributeName.equals(ATTR_CREDENTIAL_SHAREABLE_WITH_PARENT)) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 490625987:
                    if (attributeName.equals(ATTR_INHERIT_DEVICE_POLICY)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 585305077:
                    if (attributeName.equals(ATTR_USE_PARENTS_CONTACTS)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 931602880:
                    if (attributeName.equals(ATTR_AUTH_ALWAYS_REQUIRED_TO_DISABLE_QUIET_MODE)) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 1102355712:
                    if (attributeName.equals(ATTR_SHOW_IN_SHARING_SURFACES)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1298238789:
                    if (attributeName.equals(ITEMS_RESTRICTED_ON_HOME_SCREEN)) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case 1404281621:
                    if (attributeName.equals(ATTR_SHOW_IN_QUIET_MODE)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 2082796132:
                    if (attributeName.equals(ATTR_CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    setShowInLauncher(typedXmlPullParser.getAttributeInt(i));
                    break;
                case 1:
                    setStartWithParent(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case 2:
                    setShowInSettings(typedXmlPullParser.getAttributeInt(i));
                    break;
                case 3:
                    setShowInQuietMode(typedXmlPullParser.getAttributeInt(i));
                    break;
                case 4:
                    setShowInSharingSurfaces(typedXmlPullParser.getAttributeInt(i));
                    break;
                case 5:
                    setInheritDevicePolicy(typedXmlPullParser.getAttributeInt(i));
                    break;
                case 6:
                    setUseParentsContacts(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case 7:
                    setUpdateCrossProfileIntentFiltersOnOTA(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case '\b':
                    setCrossProfileIntentFilterAccessControl(typedXmlPullParser.getAttributeInt(i));
                    break;
                case '\t':
                    setCrossProfileIntentResolutionStrategy(typedXmlPullParser.getAttributeInt(i));
                    break;
                case '\n':
                    setMediaSharedWithParent(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case 11:
                    setCredentialShareableWithParent(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case '\f':
                    setAuthAlwaysRequiredToDisableQuietMode(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case '\r':
                    setAllowStoppingUserWithDelayedLocking(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case 14:
                    setDeleteAppWithParent(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case 15:
                    setAlwaysVisible(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                case 16:
                    setCrossProfileContentSharingStrategy(typedXmlPullParser.getAttributeInt(i));
                    break;
                case 17:
                    setProfileApiVisibility(typedXmlPullParser.getAttributeInt(i));
                    break;
                case 18:
                    setItemsRestrictedOnHomeScreen(typedXmlPullParser.getAttributeBoolean(i));
                    break;
                default:
                    android.util.Slog.w(LOG_TAG, "Skipping unknown property " + attributeName);
                    break;
            }
        }
    }

    public void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        if (isPresent(0L)) {
            typedXmlSerializer.attributeInt(null, ATTR_SHOW_IN_LAUNCHER, this.mShowInLauncher);
        }
        if (isPresent(1L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_START_WITH_PARENT, this.mStartWithParent);
        }
        if (isPresent(2L)) {
            typedXmlSerializer.attributeInt(null, ATTR_SHOW_IN_SETTINGS, this.mShowInSettings);
        }
        if (isPresent(12L)) {
            typedXmlSerializer.attributeInt(null, ATTR_SHOW_IN_QUIET_MODE, this.mShowInQuietMode);
        }
        if (isPresent(14L)) {
            typedXmlSerializer.attributeInt(null, ATTR_SHOW_IN_SHARING_SURFACES, this.mShowInSharingSurfaces);
        }
        if (isPresent(3L)) {
            typedXmlSerializer.attributeInt(null, ATTR_INHERIT_DEVICE_POLICY, this.mInheritDevicePolicy);
        }
        if (isPresent(4L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_USE_PARENTS_CONTACTS, this.mUseParentsContacts);
        }
        if (isPresent(5L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_UPDATE_CROSS_PROFILE_INTENT_FILTERS_ON_OTA, this.mUpdateCrossProfileIntentFiltersOnOTA);
        }
        if (isPresent(6L)) {
            typedXmlSerializer.attributeInt(null, ATTR_CROSS_PROFILE_INTENT_FILTER_ACCESS_CONTROL, this.mCrossProfileIntentFilterAccessControl);
        }
        if (isPresent(7L)) {
            typedXmlSerializer.attributeInt(null, ATTR_CROSS_PROFILE_INTENT_RESOLUTION_STRATEGY, this.mCrossProfileIntentResolutionStrategy);
        }
        if (isPresent(8L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_MEDIA_SHARED_WITH_PARENT, this.mMediaSharedWithParent);
        }
        if (isPresent(9L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_CREDENTIAL_SHAREABLE_WITH_PARENT, this.mCredentialShareableWithParent);
        }
        if (isPresent(13L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_AUTH_ALWAYS_REQUIRED_TO_DISABLE_QUIET_MODE, this.mAuthAlwaysRequiredToDisableQuietMode);
        }
        if (isPresent(16L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_ALLOW_STOPPING_USER_WITH_DELAYED_LOCKING, this.mAllowStoppingUserWithDelayedLocking);
        }
        if (isPresent(10L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_DELETE_APP_WITH_PARENT, this.mDeleteAppWithParent);
        }
        if (isPresent(11L)) {
            typedXmlSerializer.attributeBoolean(null, ATTR_ALWAYS_VISIBLE, this.mAlwaysVisible);
        }
        if (isPresent(15L)) {
            typedXmlSerializer.attributeInt(null, ATTR_CROSS_PROFILE_CONTENT_SHARING_STRATEGY, this.mCrossProfileContentSharingStrategy);
        }
        if (isPresent(17L)) {
            typedXmlSerializer.attributeInt(null, ATTR_PROFILE_API_VISIBILITY, this.mProfileApiVisibility);
        }
        if (isPresent(18L)) {
            typedXmlSerializer.attributeBoolean(null, ITEMS_RESTRICTED_ON_HOME_SCREEN, this.mItemsRestrictedOnHomeScreen);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mPropertiesPresent);
        parcel.writeInt(this.mShowInLauncher);
        parcel.writeBoolean(this.mStartWithParent);
        parcel.writeInt(this.mShowInSettings);
        parcel.writeInt(this.mShowInQuietMode);
        parcel.writeInt(this.mShowInSharingSurfaces);
        parcel.writeInt(this.mInheritDevicePolicy);
        parcel.writeBoolean(this.mUseParentsContacts);
        parcel.writeBoolean(this.mUpdateCrossProfileIntentFiltersOnOTA);
        parcel.writeInt(this.mCrossProfileIntentFilterAccessControl);
        parcel.writeInt(this.mCrossProfileIntentResolutionStrategy);
        parcel.writeBoolean(this.mMediaSharedWithParent);
        parcel.writeBoolean(this.mCredentialShareableWithParent);
        parcel.writeBoolean(this.mAuthAlwaysRequiredToDisableQuietMode);
        parcel.writeBoolean(this.mAllowStoppingUserWithDelayedLocking);
        parcel.writeBoolean(this.mDeleteAppWithParent);
        parcel.writeBoolean(this.mAlwaysVisible);
        parcel.writeInt(this.mCrossProfileContentSharingStrategy);
        parcel.writeInt(this.mProfileApiVisibility);
        parcel.writeBoolean(this.mItemsRestrictedOnHomeScreen);
    }

    private UserProperties(android.os.Parcel parcel) {
        this.mPropertiesPresent = 0L;
        this.mDefaultProperties = null;
        this.mPropertiesPresent = parcel.readLong();
        this.mShowInLauncher = parcel.readInt();
        this.mStartWithParent = parcel.readBoolean();
        this.mShowInSettings = parcel.readInt();
        this.mShowInQuietMode = parcel.readInt();
        this.mShowInSharingSurfaces = parcel.readInt();
        this.mInheritDevicePolicy = parcel.readInt();
        this.mUseParentsContacts = parcel.readBoolean();
        this.mUpdateCrossProfileIntentFiltersOnOTA = parcel.readBoolean();
        this.mCrossProfileIntentFilterAccessControl = parcel.readInt();
        this.mCrossProfileIntentResolutionStrategy = parcel.readInt();
        this.mMediaSharedWithParent = parcel.readBoolean();
        this.mCredentialShareableWithParent = parcel.readBoolean();
        this.mAuthAlwaysRequiredToDisableQuietMode = parcel.readBoolean();
        this.mAllowStoppingUserWithDelayedLocking = parcel.readBoolean();
        this.mDeleteAppWithParent = parcel.readBoolean();
        this.mAlwaysVisible = parcel.readBoolean();
        this.mCrossProfileContentSharingStrategy = parcel.readInt();
        this.mProfileApiVisibility = parcel.readInt();
        this.mItemsRestrictedOnHomeScreen = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private int mShowInLauncher = 0;
        private boolean mStartWithParent = false;
        private int mShowInSettings = 0;
        private int mShowInQuietMode = 0;
        private int mShowInSharingSurfaces = 1;
        private int mInheritDevicePolicy = 0;
        private boolean mUseParentsContacts = false;
        private boolean mUpdateCrossProfileIntentFiltersOnOTA = false;
        private int mCrossProfileIntentFilterAccessControl = 0;
        private int mCrossProfileIntentResolutionStrategy = 0;
        private boolean mMediaSharedWithParent = false;
        private boolean mCredentialShareableWithParent = false;
        private boolean mAuthAlwaysRequiredToDisableQuietMode = false;
        private boolean mAllowStoppingUserWithDelayedLocking = false;
        private boolean mDeleteAppWithParent = false;
        private boolean mAlwaysVisible = false;
        private int mCrossProfileContentSharingStrategy = 0;
        private int mProfileApiVisibility = 0;
        private boolean mItemsRestrictedOnHomeScreen = false;

        public android.content.pm.UserProperties.Builder setShowInLauncher(int i) {
            this.mShowInLauncher = i;
            return this;
        }

        public android.content.pm.UserProperties.Builder setStartWithParent(boolean z) {
            this.mStartWithParent = z;
            return this;
        }

        public android.content.pm.UserProperties.Builder setShowInSettings(int i) {
            this.mShowInSettings = i;
            return this;
        }

        public android.content.pm.UserProperties.Builder setShowInQuietMode(int i) {
            this.mShowInQuietMode = i;
            return this;
        }

        public android.content.pm.UserProperties.Builder setShowInSharingSurfaces(int i) {
            this.mShowInSharingSurfaces = i;
            return this;
        }

        public android.content.pm.UserProperties.Builder setInheritDevicePolicy(int i) {
            this.mInheritDevicePolicy = i;
            return this;
        }

        public android.content.pm.UserProperties.Builder setUseParentsContacts(boolean z) {
            this.mUseParentsContacts = z;
            return this;
        }

        public android.content.pm.UserProperties.Builder setUpdateCrossProfileIntentFiltersOnOTA(boolean z) {
            this.mUpdateCrossProfileIntentFiltersOnOTA = z;
            return this;
        }

        public android.content.pm.UserProperties.Builder setCrossProfileIntentFilterAccessControl(int i) {
            this.mCrossProfileIntentFilterAccessControl = i;
            return this;
        }

        public android.content.pm.UserProperties.Builder setCrossProfileIntentResolutionStrategy(int i) {
            this.mCrossProfileIntentResolutionStrategy = i;
            return this;
        }

        public android.content.pm.UserProperties.Builder setMediaSharedWithParent(boolean z) {
            this.mMediaSharedWithParent = z;
            return this;
        }

        public android.content.pm.UserProperties.Builder setCredentialShareableWithParent(boolean z) {
            this.mCredentialShareableWithParent = z;
            return this;
        }

        public android.content.pm.UserProperties.Builder setAuthAlwaysRequiredToDisableQuietMode(boolean z) {
            this.mAuthAlwaysRequiredToDisableQuietMode = z;
            return this;
        }

        public android.content.pm.UserProperties.Builder setAllowStoppingUserWithDelayedLocking(boolean z) {
            this.mAllowStoppingUserWithDelayedLocking = z;
            return this;
        }

        public android.content.pm.UserProperties.Builder setDeleteAppWithParent(boolean z) {
            this.mDeleteAppWithParent = z;
            return this;
        }

        public android.content.pm.UserProperties.Builder setAlwaysVisible(boolean z) {
            this.mAlwaysVisible = z;
            return this;
        }

        public android.content.pm.UserProperties.Builder setCrossProfileContentSharingStrategy(int i) {
            this.mCrossProfileContentSharingStrategy = i;
            return this;
        }

        public android.content.pm.UserProperties.Builder setProfileApiVisibility(int i) {
            this.mProfileApiVisibility = i;
            return this;
        }

        public android.content.pm.UserProperties.Builder setItemsRestrictedOnHomeScreen(boolean z) {
            this.mItemsRestrictedOnHomeScreen = z;
            return this;
        }

        public android.content.pm.UserProperties build() {
            return new android.content.pm.UserProperties(this.mShowInLauncher, this.mStartWithParent, this.mShowInSettings, this.mShowInQuietMode, this.mShowInSharingSurfaces, this.mInheritDevicePolicy, this.mUseParentsContacts, this.mUpdateCrossProfileIntentFiltersOnOTA, this.mCrossProfileIntentFilterAccessControl, this.mCrossProfileIntentResolutionStrategy, this.mMediaSharedWithParent, this.mCredentialShareableWithParent, this.mAuthAlwaysRequiredToDisableQuietMode, this.mAllowStoppingUserWithDelayedLocking, this.mDeleteAppWithParent, this.mAlwaysVisible, this.mCrossProfileContentSharingStrategy, this.mProfileApiVisibility, this.mItemsRestrictedOnHomeScreen);
        }
    }

    private UserProperties(int i, boolean z, int i2, int i3, int i4, int i5, boolean z2, boolean z3, int i6, int i7, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, int i8, int i9, boolean z10) {
        this.mPropertiesPresent = 0L;
        this.mDefaultProperties = null;
        setShowInLauncher(i);
        setStartWithParent(z);
        setShowInSettings(i2);
        setShowInQuietMode(i3);
        setShowInSharingSurfaces(i4);
        setInheritDevicePolicy(i5);
        setUseParentsContacts(z2);
        setUpdateCrossProfileIntentFiltersOnOTA(z3);
        setCrossProfileIntentFilterAccessControl(i6);
        setCrossProfileIntentResolutionStrategy(i7);
        setMediaSharedWithParent(z4);
        setCredentialShareableWithParent(z5);
        setAuthAlwaysRequiredToDisableQuietMode(z6);
        setAllowStoppingUserWithDelayedLocking(z7);
        setDeleteAppWithParent(z8);
        setAlwaysVisible(z9);
        setCrossProfileContentSharingStrategy(i8);
        setProfileApiVisibility(i9);
        setItemsRestrictedOnHomeScreen(z10);
    }
}
