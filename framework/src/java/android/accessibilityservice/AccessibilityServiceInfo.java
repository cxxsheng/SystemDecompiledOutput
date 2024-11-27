package android.accessibilityservice;

/* loaded from: classes.dex */
public class AccessibilityServiceInfo implements android.os.Parcelable {
    public static final int CAPABILITY_CAN_CONTROL_MAGNIFICATION = 16;
    public static final int CAPABILITY_CAN_PERFORM_GESTURES = 32;

    @java.lang.Deprecated
    public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
    public static final int CAPABILITY_CAN_REQUEST_FILTER_KEY_EVENTS = 8;
    public static final int CAPABILITY_CAN_REQUEST_FINGERPRINT_GESTURES = 64;
    public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
    public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;
    public static final int CAPABILITY_CAN_TAKE_SCREENSHOT = 128;
    public static final android.os.Parcelable.Creator<android.accessibilityservice.AccessibilityServiceInfo> CREATOR = new android.os.Parcelable.Creator<android.accessibilityservice.AccessibilityServiceInfo>() { // from class: android.accessibilityservice.AccessibilityServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accessibilityservice.AccessibilityServiceInfo createFromParcel(android.os.Parcel parcel) {
            android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = new android.accessibilityservice.AccessibilityServiceInfo();
            accessibilityServiceInfo.initFromParcel(parcel);
            return accessibilityServiceInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.accessibilityservice.AccessibilityServiceInfo[] newArray(int i) {
            return new android.accessibilityservice.AccessibilityServiceInfo[i];
        }
    };
    public static final int DEFAULT = 1;
    public static final int FEEDBACK_ALL_MASK = -1;
    public static final int FEEDBACK_AUDIBLE = 4;
    public static final int FEEDBACK_BRAILLE = 32;
    public static final int FEEDBACK_GENERIC = 16;
    public static final int FEEDBACK_HAPTIC = 2;
    public static final int FEEDBACK_SPOKEN = 1;
    public static final int FEEDBACK_VISUAL = 8;
    public static final int FLAG_ENABLE_ACCESSIBILITY_VOLUME = 128;
    public static final int FLAG_FORCE_DIRECT_BOOT_AWARE = 65536;
    public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
    public static final int FLAG_INPUT_METHOD_EDITOR = 32768;
    public static final int FLAG_REPORT_VIEW_IDS = 16;
    public static final int FLAG_REQUEST_2_FINGER_PASSTHROUGH = 8192;
    public static final int FLAG_REQUEST_ACCESSIBILITY_BUTTON = 256;

    @java.lang.Deprecated
    public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
    public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
    public static final int FLAG_REQUEST_FINGERPRINT_GESTURES = 512;
    public static final int FLAG_REQUEST_MULTI_FINGER_GESTURES = 4096;
    public static final int FLAG_REQUEST_SHORTCUT_WARNING_DIALOG_SPOKEN_FEEDBACK = 1024;
    public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;
    public static final int FLAG_RETRIEVE_INTERACTIVE_WINDOWS = 64;
    public static final int FLAG_SEND_MOTION_EVENTS = 16384;
    public static final int FLAG_SERVICE_HANDLES_DOUBLE_TAP = 2048;
    private static final long REQUEST_ACCESSIBILITY_BUTTON_CHANGE = 136293963;
    private static final java.lang.String TAG_ACCESSIBILITY_SERVICE = "accessibility-service";
    private static android.util.SparseArray<android.accessibilityservice.AccessibilityServiceInfo.CapabilityInfo> sAvailableCapabilityInfos;
    public boolean crashed;
    public int eventTypes;
    public int feedbackType;
    public int flags;
    private int mAnimatedImageRes;
    private int mCapabilities;
    private android.content.ComponentName mComponentName;
    private int mDescriptionResId;
    private final android.accessibilityservice.AccessibilityServiceInfo.DynamicPropertyDefaults mDynamicPropertyDefaults;
    private int mHtmlDescriptionRes;
    private int mInteractiveUiTimeout;
    private int mIntroResId;
    private boolean mIsAccessibilityTool;
    private int mMotionEventSources;
    private int mNonInteractiveUiTimeout;
    private java.lang.String mNonLocalizedDescription;
    private java.lang.String mNonLocalizedSummary;
    private int mObservedMotionEventSources;
    private android.content.pm.ResolveInfo mResolveInfo;
    private java.lang.String mSettingsActivityName;
    private int mSummaryResId;
    private java.lang.String mTileServiceName;
    public long notificationTimeout;
    public java.lang.String[] packageNames;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FeedbackType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MotionEventSources {
    }

    private static class DynamicPropertyDefaults {
        private final int mEventTypesDefault;
        private final int mFeedbackTypeDefault;
        private final int mFlagsDefault;
        private final int mInteractiveUiTimeoutDefault;
        private final int mMotionEventSourcesDefault;
        private final int mNonInteractiveUiTimeoutDefault;
        private final long mNotificationTimeoutDefault;
        private final int mObservedMotionEventSourcesDefault;
        private final java.util.List<java.lang.String> mPackageNamesDefault;

        DynamicPropertyDefaults(android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
            this.mEventTypesDefault = accessibilityServiceInfo.eventTypes;
            if (accessibilityServiceInfo.packageNames != null) {
                this.mPackageNamesDefault = java.util.List.of((java.lang.Object[]) accessibilityServiceInfo.packageNames);
            } else {
                this.mPackageNamesDefault = null;
            }
            this.mFeedbackTypeDefault = accessibilityServiceInfo.feedbackType;
            this.mNotificationTimeoutDefault = accessibilityServiceInfo.notificationTimeout;
            this.mNonInteractiveUiTimeoutDefault = accessibilityServiceInfo.mNonInteractiveUiTimeout;
            this.mInteractiveUiTimeoutDefault = accessibilityServiceInfo.mInteractiveUiTimeout;
            this.mFlagsDefault = accessibilityServiceInfo.flags;
            this.mMotionEventSourcesDefault = accessibilityServiceInfo.mMotionEventSources;
            this.mObservedMotionEventSourcesDefault = accessibilityServiceInfo.mObservedMotionEventSources;
        }
    }

    public AccessibilityServiceInfo() {
        this.mIsAccessibilityTool = false;
        this.mMotionEventSources = 0;
        this.mObservedMotionEventSources = 0;
        this.mDynamicPropertyDefaults = new android.accessibilityservice.AccessibilityServiceInfo.DynamicPropertyDefaults(this);
    }

    public AccessibilityServiceInfo(android.content.pm.ResolveInfo resolveInfo, android.content.Context context) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        this.mIsAccessibilityTool = false;
        this.mMotionEventSources = 0;
        this.mObservedMotionEventSources = 0;
        android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        this.mComponentName = new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name);
        this.mResolveInfo = resolveInfo;
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                android.content.pm.PackageManager packageManager = context.getPackageManager();
                android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, android.accessibilityservice.AccessibilityService.SERVICE_META_DATA);
                if (loadXmlMetaData == null) {
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                    this.mDynamicPropertyDefaults = new android.accessibilityservice.AccessibilityServiceInfo.DynamicPropertyDefaults(this);
                    return;
                }
                for (int i = 0; i != 1 && i != 2; i = loadXmlMetaData.next()) {
                }
                if (!TAG_ACCESSIBILITY_SERVICE.equals(loadXmlMetaData.getName())) {
                    throw new org.xmlpull.v1.XmlPullParserException("Meta-data does not start withaccessibility-service tag");
                }
                android.content.res.TypedArray obtainAttributes = packageManager.getResourcesForApplication(serviceInfo.applicationInfo).obtainAttributes(android.util.Xml.asAttributeSet(loadXmlMetaData), com.android.internal.R.styleable.AccessibilityService);
                this.eventTypes = obtainAttributes.getInt(3, 0);
                java.lang.String string = obtainAttributes.getString(4);
                if (string != null) {
                    this.packageNames = string.split("(\\s)*,(\\s)*");
                }
                this.feedbackType = obtainAttributes.getInt(5, 0);
                this.notificationTimeout = obtainAttributes.getInt(6, 0);
                this.mNonInteractiveUiTimeout = obtainAttributes.getInt(15, 0);
                this.mInteractiveUiTimeout = obtainAttributes.getInt(16, 0);
                this.flags = obtainAttributes.getInt(7, 0);
                this.mSettingsActivityName = obtainAttributes.getString(2);
                if (obtainAttributes.getBoolean(8, false)) {
                    this.mCapabilities |= 1;
                }
                if (obtainAttributes.getBoolean(9, false)) {
                    this.mCapabilities = 2 | this.mCapabilities;
                }
                if (obtainAttributes.getBoolean(11, false)) {
                    this.mCapabilities |= 8;
                }
                if (obtainAttributes.getBoolean(12, false)) {
                    this.mCapabilities = 16 | this.mCapabilities;
                }
                if (obtainAttributes.getBoolean(13, false)) {
                    this.mCapabilities |= 32;
                }
                if (obtainAttributes.getBoolean(14, false)) {
                    this.mCapabilities |= 64;
                }
                if (obtainAttributes.getBoolean(19, false)) {
                    this.mCapabilities |= 128;
                }
                android.util.TypedValue peekValue = obtainAttributes.peekValue(0);
                if (peekValue != null) {
                    this.mDescriptionResId = peekValue.resourceId;
                    java.lang.CharSequence coerceToString = peekValue.coerceToString();
                    if (coerceToString != null) {
                        this.mNonLocalizedDescription = coerceToString.toString().trim();
                    }
                }
                android.util.TypedValue peekValue2 = obtainAttributes.peekValue(1);
                if (peekValue2 != null) {
                    this.mSummaryResId = peekValue2.resourceId;
                    java.lang.CharSequence coerceToString2 = peekValue2.coerceToString();
                    if (coerceToString2 != null) {
                        this.mNonLocalizedSummary = coerceToString2.toString().trim();
                    }
                }
                android.util.TypedValue peekValue3 = obtainAttributes.peekValue(17);
                if (peekValue3 != null) {
                    this.mAnimatedImageRes = peekValue3.resourceId;
                }
                android.util.TypedValue peekValue4 = obtainAttributes.peekValue(18);
                if (peekValue4 != null) {
                    this.mHtmlDescriptionRes = peekValue4.resourceId;
                }
                this.mIsAccessibilityTool = obtainAttributes.getBoolean(20, false);
                this.mTileServiceName = obtainAttributes.getString(21);
                android.util.TypedValue peekValue5 = obtainAttributes.peekValue(22);
                if (peekValue5 != null) {
                    this.mIntroResId = peekValue5.resourceId;
                }
                obtainAttributes.recycle();
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
                this.mDynamicPropertyDefaults = new android.accessibilityservice.AccessibilityServiceInfo.DynamicPropertyDefaults(this);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                throw new org.xmlpull.v1.XmlPullParserException("Unable to create context for: " + serviceInfo.packageName);
            }
        } catch (java.lang.Throwable th) {
            if (0 != 0) {
                xmlResourceParser.close();
            }
            this.mDynamicPropertyDefaults = new android.accessibilityservice.AccessibilityServiceInfo.DynamicPropertyDefaults(this);
            throw th;
        }
    }

    public void resetDynamicallyConfigurableProperties() {
        this.eventTypes = this.mDynamicPropertyDefaults.mEventTypesDefault;
        if (this.mDynamicPropertyDefaults.mPackageNamesDefault == null) {
            this.packageNames = null;
        } else {
            this.packageNames = (java.lang.String[]) this.mDynamicPropertyDefaults.mPackageNamesDefault.toArray(new java.lang.String[0]);
        }
        this.feedbackType = this.mDynamicPropertyDefaults.mFeedbackTypeDefault;
        this.notificationTimeout = this.mDynamicPropertyDefaults.mNotificationTimeoutDefault;
        this.mNonInteractiveUiTimeout = this.mDynamicPropertyDefaults.mNonInteractiveUiTimeoutDefault;
        this.mInteractiveUiTimeout = this.mDynamicPropertyDefaults.mInteractiveUiTimeoutDefault;
        this.flags = this.mDynamicPropertyDefaults.mFlagsDefault;
        this.mMotionEventSources = this.mDynamicPropertyDefaults.mMotionEventSourcesDefault;
        if (android.view.accessibility.Flags.motionEventObserving()) {
            this.mObservedMotionEventSources = this.mDynamicPropertyDefaults.mObservedMotionEventSourcesDefault;
        }
    }

    public void updateDynamicallyConfigurableProperties(com.android.internal.compat.IPlatformCompat iPlatformCompat, android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo) {
        if (isRequestAccessibilityButtonChangeEnabled(iPlatformCompat)) {
            accessibilityServiceInfo.flags &= -257;
            accessibilityServiceInfo.flags |= this.flags & 256;
        }
        this.eventTypes = accessibilityServiceInfo.eventTypes;
        this.packageNames = accessibilityServiceInfo.packageNames;
        this.feedbackType = accessibilityServiceInfo.feedbackType;
        this.notificationTimeout = accessibilityServiceInfo.notificationTimeout;
        this.mNonInteractiveUiTimeout = accessibilityServiceInfo.mNonInteractiveUiTimeout;
        this.mInteractiveUiTimeout = accessibilityServiceInfo.mInteractiveUiTimeout;
        this.flags = accessibilityServiceInfo.flags;
        this.mMotionEventSources = accessibilityServiceInfo.mMotionEventSources;
        if (android.view.accessibility.Flags.motionEventObserving()) {
            setObservedMotionEventSources(accessibilityServiceInfo.mObservedMotionEventSources);
        }
    }

    private boolean isRequestAccessibilityButtonChangeEnabled(com.android.internal.compat.IPlatformCompat iPlatformCompat) {
        if (this.mResolveInfo == null) {
            return true;
        }
        if (iPlatformCompat != null) {
            try {
                return iPlatformCompat.isChangeEnabled(REQUEST_ACCESSIBILITY_BUTTON_CHANGE, this.mResolveInfo.serviceInfo.applicationInfo);
            } catch (android.os.RemoteException e) {
            }
        }
        return this.mResolveInfo.serviceInfo.applicationInfo.targetSdkVersion > 29;
    }

    public void setComponentName(android.content.ComponentName componentName) {
        this.mComponentName = componentName;
    }

    public void setResolveInfo(android.content.pm.ResolveInfo resolveInfo) {
        this.mResolveInfo = resolveInfo;
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    public java.lang.String getId() {
        if (this.mComponentName == null) {
            return null;
        }
        return this.mComponentName.flattenToShortString();
    }

    public android.content.pm.ResolveInfo getResolveInfo() {
        return this.mResolveInfo;
    }

    public java.lang.String getSettingsActivityName() {
        return this.mSettingsActivityName;
    }

    public java.lang.String getTileServiceName() {
        return this.mTileServiceName;
    }

    public int getAnimatedImageRes() {
        return this.mAnimatedImageRes;
    }

    public android.graphics.drawable.Drawable loadAnimatedImage(android.content.Context context) {
        if (this.mAnimatedImageRes == 0) {
            return null;
        }
        return android.accessibilityservice.util.AccessibilityUtils.loadSafeAnimatedImage(context, this.mResolveInfo.serviceInfo.applicationInfo, this.mAnimatedImageRes);
    }

    public boolean getCanRetrieveWindowContent() {
        return (this.mCapabilities & 1) != 0;
    }

    public int getCapabilities() {
        return this.mCapabilities;
    }

    public void setCapabilities(int i) {
        this.mCapabilities = i;
    }

    public int getMotionEventSources() {
        return this.mMotionEventSources;
    }

    public void setMotionEventSources(int i) {
        this.mMotionEventSources = i;
        this.mObservedMotionEventSources = 0;
    }

    public void setObservedMotionEventSources(int i) {
        if (((~this.mMotionEventSources) & i) != 0) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Requested motion event sources for listening = 0x%x but requested motion event sources for observing = 0x%x.", java.lang.Integer.valueOf(this.mMotionEventSources), java.lang.Integer.valueOf(i)));
        }
        this.mObservedMotionEventSources = i;
    }

    public int getObservedMotionEventSources() {
        return this.mObservedMotionEventSources;
    }

    public java.lang.CharSequence loadSummary(android.content.pm.PackageManager packageManager) {
        if (this.mSummaryResId == 0) {
            return this.mNonLocalizedSummary;
        }
        android.content.pm.ServiceInfo serviceInfo = this.mResolveInfo.serviceInfo;
        java.lang.CharSequence text = packageManager.getText(serviceInfo.packageName, this.mSummaryResId, serviceInfo.applicationInfo);
        if (text != null) {
            return text.toString().trim();
        }
        return null;
    }

    public java.lang.CharSequence loadIntro(android.content.pm.PackageManager packageManager) {
        if (this.mIntroResId == 0) {
            return null;
        }
        android.content.pm.ServiceInfo serviceInfo = this.mResolveInfo.serviceInfo;
        java.lang.CharSequence text = packageManager.getText(serviceInfo.packageName, this.mIntroResId, serviceInfo.applicationInfo);
        if (text != null) {
            return text.toString().trim();
        }
        return null;
    }

    public java.lang.String getDescription() {
        return this.mNonLocalizedDescription;
    }

    public java.lang.String loadDescription(android.content.pm.PackageManager packageManager) {
        if (this.mDescriptionResId == 0) {
            return this.mNonLocalizedDescription;
        }
        android.content.pm.ServiceInfo serviceInfo = this.mResolveInfo.serviceInfo;
        java.lang.CharSequence text = packageManager.getText(serviceInfo.packageName, this.mDescriptionResId, serviceInfo.applicationInfo);
        if (text != null) {
            return text.toString().trim();
        }
        return null;
    }

    public java.lang.String loadHtmlDescription(android.content.pm.PackageManager packageManager) {
        if (this.mHtmlDescriptionRes == 0) {
            return null;
        }
        android.content.pm.ServiceInfo serviceInfo = this.mResolveInfo.serviceInfo;
        java.lang.CharSequence text = packageManager.getText(serviceInfo.packageName, this.mHtmlDescriptionRes, serviceInfo.applicationInfo);
        if (text != null) {
            return android.accessibilityservice.util.AccessibilityUtils.getFilteredHtmlText(text.toString().trim());
        }
        return null;
    }

    public void setNonInteractiveUiTimeoutMillis(int i) {
        this.mNonInteractiveUiTimeout = i;
    }

    public int getNonInteractiveUiTimeoutMillis() {
        return this.mNonInteractiveUiTimeout;
    }

    public void setInteractiveUiTimeoutMillis(int i) {
        this.mInteractiveUiTimeout = i;
    }

    public int getInteractiveUiTimeoutMillis() {
        return this.mInteractiveUiTimeout;
    }

    public boolean isDirectBootAware() {
        return (this.flags & 65536) != 0 || this.mResolveInfo.serviceInfo.directBootAware;
    }

    @android.annotation.SystemApi
    public void setAccessibilityTool(boolean z) {
        this.mIsAccessibilityTool = z;
    }

    public boolean isAccessibilityTool() {
        return this.mIsAccessibilityTool;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final boolean isWithinParcelableSize() {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        writeToParcel(obtain, 0);
        boolean z = obtain.dataSize() <= 65536;
        obtain.recycle();
        return z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.eventTypes);
        parcel.writeStringArray(this.packageNames);
        parcel.writeInt(this.feedbackType);
        parcel.writeLong(this.notificationTimeout);
        parcel.writeInt(this.mNonInteractiveUiTimeout);
        parcel.writeInt(this.mInteractiveUiTimeout);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.crashed ? 1 : 0);
        parcel.writeParcelable(this.mComponentName, i);
        parcel.writeParcelable(this.mResolveInfo, 0);
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeInt(this.mCapabilities);
        parcel.writeInt(this.mSummaryResId);
        parcel.writeString(this.mNonLocalizedSummary);
        parcel.writeInt(this.mDescriptionResId);
        parcel.writeInt(this.mAnimatedImageRes);
        parcel.writeInt(this.mHtmlDescriptionRes);
        parcel.writeString(this.mNonLocalizedDescription);
        parcel.writeBoolean(this.mIsAccessibilityTool);
        parcel.writeString(this.mTileServiceName);
        parcel.writeInt(this.mIntroResId);
        parcel.writeInt(this.mMotionEventSources);
        parcel.writeInt(this.mObservedMotionEventSources);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFromParcel(android.os.Parcel parcel) {
        this.eventTypes = parcel.readInt();
        this.packageNames = parcel.readStringArray();
        this.feedbackType = parcel.readInt();
        this.notificationTimeout = parcel.readLong();
        this.mNonInteractiveUiTimeout = parcel.readInt();
        this.mInteractiveUiTimeout = parcel.readInt();
        this.flags = parcel.readInt();
        this.crashed = parcel.readInt() != 0;
        this.mComponentName = (android.content.ComponentName) parcel.readParcelable(getClass().getClassLoader(), android.content.ComponentName.class);
        this.mResolveInfo = (android.content.pm.ResolveInfo) parcel.readParcelable(null, android.content.pm.ResolveInfo.class);
        this.mSettingsActivityName = parcel.readString();
        this.mCapabilities = parcel.readInt();
        this.mSummaryResId = parcel.readInt();
        this.mNonLocalizedSummary = parcel.readString();
        this.mDescriptionResId = parcel.readInt();
        this.mAnimatedImageRes = parcel.readInt();
        this.mHtmlDescriptionRes = parcel.readInt();
        this.mNonLocalizedDescription = parcel.readString();
        this.mIsAccessibilityTool = parcel.readBoolean();
        this.mTileServiceName = parcel.readString();
        this.mIntroResId = parcel.readInt();
        this.mMotionEventSources = parcel.readInt();
        setObservedMotionEventSources(parcel.readInt());
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
        android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = (android.accessibilityservice.AccessibilityServiceInfo) obj;
        if (this.mComponentName == null) {
            if (accessibilityServiceInfo.mComponentName != null) {
                return false;
            }
        } else if (!this.mComponentName.equals(accessibilityServiceInfo.mComponentName)) {
            return false;
        }
        return true;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        appendEventTypes(sb, this.eventTypes);
        sb.append(", ");
        appendPackageNames(sb, this.packageNames);
        sb.append(", ");
        appendFeedbackTypes(sb, this.feedbackType);
        sb.append(", ");
        sb.append("notificationTimeout: ").append(this.notificationTimeout);
        sb.append(", ");
        sb.append("nonInteractiveUiTimeout: ").append(this.mNonInteractiveUiTimeout);
        sb.append(", ");
        sb.append("interactiveUiTimeout: ").append(this.mInteractiveUiTimeout);
        sb.append(", ");
        appendFlags(sb, this.flags);
        sb.append(", ");
        sb.append("id: ").append(getId());
        sb.append(", ");
        sb.append("resolveInfo: ").append(this.mResolveInfo);
        sb.append(", ");
        sb.append("settingsActivityName: ").append(this.mSettingsActivityName);
        sb.append(", ");
        sb.append("tileServiceName: ").append(this.mTileServiceName);
        sb.append(", ");
        sb.append("summary: ").append(this.mNonLocalizedSummary);
        sb.append(", ");
        sb.append("isAccessibilityTool: ").append(this.mIsAccessibilityTool);
        sb.append(", ");
        appendCapabilities(sb, this.mCapabilities);
        return sb.toString();
    }

    private static void appendFeedbackTypes(java.lang.StringBuilder sb, int i) {
        sb.append("feedbackTypes:");
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        while (i != 0) {
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i);
            sb.append(feedbackTypeToString(numberOfTrailingZeros));
            i &= ~numberOfTrailingZeros;
            if (i != 0) {
                sb.append(", ");
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
    }

    private static void appendPackageNames(java.lang.StringBuilder sb, java.lang.String[] strArr) {
        sb.append("packageNames:");
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        if (strArr != null) {
            int length = strArr.length;
            for (int i = 0; i < length; i++) {
                sb.append(strArr[i]);
                if (i < length - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
    }

    private static void appendEventTypes(java.lang.StringBuilder sb, int i) {
        sb.append("eventTypes:");
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        while (i != 0) {
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i);
            sb.append(android.view.accessibility.AccessibilityEvent.eventTypeToString(numberOfTrailingZeros));
            i &= ~numberOfTrailingZeros;
            if (i != 0) {
                sb.append(", ");
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
    }

    private static void appendFlags(java.lang.StringBuilder sb, int i) {
        sb.append("flags:");
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        while (i != 0) {
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i);
            sb.append(flagToString(numberOfTrailingZeros));
            i &= ~numberOfTrailingZeros;
            if (i != 0) {
                sb.append(", ");
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
    }

    private static void appendCapabilities(java.lang.StringBuilder sb, int i) {
        sb.append("capabilities:");
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        while (i != 0) {
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i);
            sb.append(capabilityToString(numberOfTrailingZeros));
            i &= ~numberOfTrailingZeros;
            if (i != 0) {
                sb.append(", ");
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
    }

    public static java.lang.String feedbackTypeToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        while (i != 0) {
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i);
            i &= ~numberOfTrailingZeros;
            switch (numberOfTrailingZeros) {
                case 1:
                    if (sb.length() > 1) {
                        sb.append(", ");
                    }
                    sb.append("FEEDBACK_SPOKEN");
                    break;
                case 2:
                    if (sb.length() > 1) {
                        sb.append(", ");
                    }
                    sb.append("FEEDBACK_HAPTIC");
                    break;
                case 4:
                    if (sb.length() > 1) {
                        sb.append(", ");
                    }
                    sb.append("FEEDBACK_AUDIBLE");
                    break;
                case 8:
                    if (sb.length() > 1) {
                        sb.append(", ");
                    }
                    sb.append("FEEDBACK_VISUAL");
                    break;
                case 16:
                    if (sb.length() > 1) {
                        sb.append(", ");
                    }
                    sb.append("FEEDBACK_GENERIC");
                    break;
                case 32:
                    if (sb.length() > 1) {
                        sb.append(", ");
                    }
                    sb.append("FEEDBACK_BRAILLE");
                    break;
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public static java.lang.String flagToString(int i) {
        switch (i) {
            case 1:
                return "DEFAULT";
            case 2:
                return "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS";
            case 4:
                return "FLAG_REQUEST_TOUCH_EXPLORATION_MODE";
            case 8:
                return "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
            case 16:
                return "FLAG_REPORT_VIEW_IDS";
            case 32:
                return "FLAG_REQUEST_FILTER_KEY_EVENTS";
            case 64:
                return "FLAG_RETRIEVE_INTERACTIVE_WINDOWS";
            case 128:
                return "FLAG_ENABLE_ACCESSIBILITY_VOLUME";
            case 256:
                return "FLAG_REQUEST_ACCESSIBILITY_BUTTON";
            case 512:
                return "FLAG_REQUEST_FINGERPRINT_GESTURES";
            case 1024:
                return "FLAG_REQUEST_SHORTCUT_WARNING_DIALOG_SPOKEN_FEEDBACK";
            case 2048:
                return "FLAG_SERVICE_HANDLES_DOUBLE_TAP";
            case 4096:
                return "FLAG_REQUEST_MULTI_FINGER_GESTURES";
            case 8192:
                return "FLAG_REQUEST_2_FINGER_PASSTHROUGH";
            case 16384:
                return "FLAG_SEND_MOTION_EVENTS";
            case 32768:
                return "FLAG_INPUT_METHOD_EDITOR";
            default:
                return null;
        }
    }

    public static java.lang.String capabilityToString(int i) {
        switch (i) {
            case 1:
                return "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT";
            case 2:
                return "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION";
            case 8:
                return "CAPABILITY_CAN_REQUEST_FILTER_KEY_EVENTS";
            case 16:
                return "CAPABILITY_CAN_CONTROL_MAGNIFICATION";
            case 32:
                return "CAPABILITY_CAN_PERFORM_GESTURES";
            case 64:
                return "CAPABILITY_CAN_REQUEST_FINGERPRINT_GESTURES";
            case 128:
                return "CAPABILITY_CAN_TAKE_SCREENSHOT";
            default:
                return "UNKNOWN";
        }
    }

    public java.util.List<android.accessibilityservice.AccessibilityServiceInfo.CapabilityInfo> getCapabilityInfos() {
        return getCapabilityInfos(null);
    }

    public java.util.List<android.accessibilityservice.AccessibilityServiceInfo.CapabilityInfo> getCapabilityInfos(android.content.Context context) {
        if (this.mCapabilities == 0) {
            return java.util.Collections.emptyList();
        }
        int i = this.mCapabilities;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.util.SparseArray<android.accessibilityservice.AccessibilityServiceInfo.CapabilityInfo> capabilityInfoSparseArray = getCapabilityInfoSparseArray(context);
        while (i != 0) {
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i);
            i &= ~numberOfTrailingZeros;
            android.accessibilityservice.AccessibilityServiceInfo.CapabilityInfo capabilityInfo = capabilityInfoSparseArray.get(numberOfTrailingZeros);
            if (capabilityInfo != null) {
                arrayList.add(capabilityInfo);
            }
        }
        return arrayList;
    }

    private static android.util.SparseArray<android.accessibilityservice.AccessibilityServiceInfo.CapabilityInfo> getCapabilityInfoSparseArray(android.content.Context context) {
        if (sAvailableCapabilityInfos == null) {
            sAvailableCapabilityInfos = new android.util.SparseArray<>();
            sAvailableCapabilityInfos.put(1, new android.accessibilityservice.AccessibilityServiceInfo.CapabilityInfo(1, com.android.internal.R.string.capability_title_canRetrieveWindowContent, com.android.internal.R.string.capability_desc_canRetrieveWindowContent));
            sAvailableCapabilityInfos.put(2, new android.accessibilityservice.AccessibilityServiceInfo.CapabilityInfo(2, com.android.internal.R.string.capability_title_canRequestTouchExploration, com.android.internal.R.string.capability_desc_canRequestTouchExploration));
            sAvailableCapabilityInfos.put(8, new android.accessibilityservice.AccessibilityServiceInfo.CapabilityInfo(8, com.android.internal.R.string.capability_title_canRequestFilterKeyEvents, com.android.internal.R.string.capability_desc_canRequestFilterKeyEvents));
            sAvailableCapabilityInfos.put(16, new android.accessibilityservice.AccessibilityServiceInfo.CapabilityInfo(16, com.android.internal.R.string.capability_title_canControlMagnification, com.android.internal.R.string.capability_desc_canControlMagnification));
            sAvailableCapabilityInfos.put(32, new android.accessibilityservice.AccessibilityServiceInfo.CapabilityInfo(32, com.android.internal.R.string.capability_title_canPerformGestures, com.android.internal.R.string.capability_desc_canPerformGestures));
            sAvailableCapabilityInfos.put(128, new android.accessibilityservice.AccessibilityServiceInfo.CapabilityInfo(128, com.android.internal.R.string.capability_title_canTakeScreenshot, com.android.internal.R.string.capability_desc_canTakeScreenshot));
            if (context == null || fingerprintAvailable(context)) {
                sAvailableCapabilityInfos.put(64, new android.accessibilityservice.AccessibilityServiceInfo.CapabilityInfo(64, com.android.internal.R.string.capability_title_canCaptureFingerprintGestures, com.android.internal.R.string.capability_desc_canCaptureFingerprintGestures));
            }
        }
        return sAvailableCapabilityInfos;
    }

    private static boolean fingerprintAvailable(android.content.Context context) {
        return context.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_FINGERPRINT) && ((android.hardware.fingerprint.FingerprintManager) context.getSystemService(android.hardware.fingerprint.FingerprintManager.class)).isHardwareDetected();
    }

    public static final class CapabilityInfo {
        public final int capability;
        public final int descResId;
        public final int titleResId;

        public CapabilityInfo(int i, int i2, int i3) {
            this.capability = i;
            this.titleResId = i2;
            this.descResId = i3;
        }
    }
}
