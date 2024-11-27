package com.android.server.accessibility;

/* loaded from: classes.dex */
class AccessibilityUserState {
    private static final java.lang.String LOG_TAG = com.android.server.accessibility.AccessibilityUserState.class.getSimpleName();
    private boolean mAccessibilityFocusOnlyInActiveWindow;
    private boolean mBindInstantServiceAllowed;
    private android.content.Context mContext;
    private int mFocusColor;
    private final int mFocusColorDefaultValue;
    private int mFocusStrokeWidth;
    private final int mFocusStrokeWidthDefaultValue;
    private boolean mIsAudioDescriptionByDefaultRequested;
    private boolean mIsAutoclickEnabled;
    private boolean mIsFilterKeyEventsEnabled;
    private boolean mIsMagnificationSingleFingerTripleTapEnabled;
    private boolean mIsPerformGesturesEnabled;
    private boolean mIsTextHighContrastEnabled;
    private boolean mIsTouchExplorationEnabled;
    private boolean mMagnificationTwoFingerTripleTapEnabled;
    private boolean mRequestMultiFingerGestures;
    private boolean mRequestTwoFingerPassthrough;
    private boolean mSendMotionEventsEnabled;
    private android.content.ComponentName mServiceChangingSoftKeyboardMode;
    private boolean mServiceHandlesDoubleTap;
    private final com.android.server.accessibility.AccessibilityUserState.ServiceInfoChangeListener mServiceInfoChangeListener;
    private final boolean mSupportWindowMagnification;
    private java.lang.String mTargetAssignedToAccessibilityButton;
    final int mUserId;
    private int mUserInteractiveUiTimeout;
    private int mUserNonInteractiveUiTimeout;
    final android.os.RemoteCallbackList<android.view.accessibility.IAccessibilityManagerClient> mUserClients = new android.os.RemoteCallbackList<>();
    final java.util.ArrayList<com.android.server.accessibility.AccessibilityServiceConnection> mBoundServices = new java.util.ArrayList<>();
    final java.util.Map<android.content.ComponentName, com.android.server.accessibility.AccessibilityServiceConnection> mComponentNameToServiceMap = new java.util.HashMap();
    final java.util.List<android.accessibilityservice.AccessibilityServiceInfo> mInstalledServices = new java.util.ArrayList();
    final java.util.List<android.accessibilityservice.AccessibilityShortcutInfo> mInstalledShortcuts = new java.util.ArrayList();
    final java.util.Set<android.content.ComponentName> mBindingServices = new java.util.HashSet();
    final java.util.Set<android.content.ComponentName> mCrashedServices = new java.util.HashSet();
    final java.util.Set<android.content.ComponentName> mEnabledServices = new java.util.HashSet();
    final java.util.Set<android.content.ComponentName> mTouchExplorationGrantedServices = new java.util.HashSet();
    final android.util.ArraySet<java.lang.String> mAccessibilityShortcutKeyTargets = new android.util.ArraySet<>();
    final android.util.ArraySet<java.lang.String> mAccessibilityButtonTargets = new android.util.ArraySet<>();
    private android.util.SparseArray<java.lang.Boolean> mServiceDetectsGestures = new android.util.SparseArray<>(0);
    private int mNonInteractiveUiTimeout = 0;
    private int mInteractiveUiTimeout = 0;
    private int mLastSentClientState = -1;
    private final android.util.SparseIntArray mMagnificationModes = new android.util.SparseIntArray();
    private int mMagnificationCapabilities = 1;
    private boolean mMagnificationFollowTypingEnabled = true;
    private boolean mAlwaysOnMagnificationEnabled = false;
    private int mSoftKeyboardShowMode = 0;

    interface ServiceInfoChangeListener {
        void onServiceInfoChangedLocked(com.android.server.accessibility.AccessibilityUserState accessibilityUserState);
    }

    boolean isValidMagnificationModeLocked(int i) {
        int magnificationModeLocked = getMagnificationModeLocked(i);
        return (this.mSupportWindowMagnification || magnificationModeLocked != 2) && (magnificationModeLocked & this.mMagnificationCapabilities) != 0;
    }

    AccessibilityUserState(int i, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.accessibility.AccessibilityUserState.ServiceInfoChangeListener serviceInfoChangeListener) {
        boolean z = false;
        this.mUserId = i;
        this.mContext = context;
        this.mServiceInfoChangeListener = serviceInfoChangeListener;
        this.mFocusStrokeWidthDefaultValue = this.mContext.getResources().getDimensionPixelSize(android.R.dimen.accessibility_focus_highlight_stroke_width);
        this.mFocusColorDefaultValue = this.mContext.getResources().getColor(android.R.color.accent_device_default_dark);
        this.mFocusStrokeWidth = this.mFocusStrokeWidthDefaultValue;
        this.mFocusColor = this.mFocusColorDefaultValue;
        if (this.mContext.getResources().getBoolean(android.R.bool.config_lockscreenWeatherEnabledByDefault) && this.mContext.getPackageManager().hasSystemFeature("android.software.window_magnification")) {
            z = true;
        }
        this.mSupportWindowMagnification = z;
    }

    boolean isHandlingAccessibilityEventsLocked() {
        return (this.mBoundServices.isEmpty() && this.mBindingServices.isEmpty()) ? false : true;
    }

    void onSwitchToAnotherUserLocked() {
        unbindAllServicesLocked();
        this.mBoundServices.clear();
        this.mBindingServices.clear();
        this.mCrashedServices.clear();
        this.mLastSentClientState = -1;
        this.mNonInteractiveUiTimeout = 0;
        this.mInteractiveUiTimeout = 0;
        this.mEnabledServices.clear();
        this.mTouchExplorationGrantedServices.clear();
        this.mAccessibilityShortcutKeyTargets.clear();
        this.mAccessibilityButtonTargets.clear();
        this.mTargetAssignedToAccessibilityButton = null;
        this.mIsTouchExplorationEnabled = false;
        this.mServiceHandlesDoubleTap = false;
        this.mRequestMultiFingerGestures = false;
        this.mRequestTwoFingerPassthrough = false;
        this.mSendMotionEventsEnabled = false;
        this.mIsMagnificationSingleFingerTripleTapEnabled = false;
        this.mMagnificationTwoFingerTripleTapEnabled = false;
        this.mIsAutoclickEnabled = false;
        this.mUserNonInteractiveUiTimeout = 0;
        this.mUserInteractiveUiTimeout = 0;
        this.mMagnificationModes.clear();
        this.mFocusStrokeWidth = this.mFocusStrokeWidthDefaultValue;
        this.mFocusColor = this.mFocusColorDefaultValue;
        this.mMagnificationFollowTypingEnabled = true;
        this.mAlwaysOnMagnificationEnabled = false;
    }

    void addServiceLocked(com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection) {
        if (!this.mBoundServices.contains(accessibilityServiceConnection)) {
            com.android.server.accessibility.Flags.addWindowTokenWithoutLock();
            accessibilityServiceConnection.addWindowTokensForAllDisplays();
            this.mBoundServices.add(accessibilityServiceConnection);
            this.mComponentNameToServiceMap.put(accessibilityServiceConnection.getComponentName(), accessibilityServiceConnection);
            this.mServiceInfoChangeListener.onServiceInfoChangedLocked(this);
        }
    }

    void removeServiceLocked(com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection) {
        this.mBoundServices.remove(accessibilityServiceConnection);
        accessibilityServiceConnection.onRemoved();
        if (this.mServiceChangingSoftKeyboardMode != null && this.mServiceChangingSoftKeyboardMode.equals(accessibilityServiceConnection.getServiceInfo().getComponentName())) {
            setSoftKeyboardModeLocked(0, null);
        }
        this.mComponentNameToServiceMap.clear();
        for (int i = 0; i < this.mBoundServices.size(); i++) {
            com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection2 = this.mBoundServices.get(i);
            this.mComponentNameToServiceMap.put(accessibilityServiceConnection2.getComponentName(), accessibilityServiceConnection2);
        }
        this.mServiceInfoChangeListener.onServiceInfoChangedLocked(this);
    }

    void serviceDisconnectedLocked(com.android.server.accessibility.AccessibilityServiceConnection accessibilityServiceConnection) {
        removeServiceLocked(accessibilityServiceConnection);
        this.mCrashedServices.add(accessibilityServiceConnection.getComponentName());
    }

    boolean setSoftKeyboardModeLocked(int i, @android.annotation.Nullable android.content.ComponentName componentName) {
        if (i != 0 && i != 1 && i != 2) {
            android.util.Slog.w(LOG_TAG, "Invalid soft keyboard mode");
            return false;
        }
        if (this.mSoftKeyboardShowMode == i) {
            return true;
        }
        if (i == 2) {
            if (hasUserOverriddenHardKeyboardSetting()) {
                return false;
            }
            if (getSoftKeyboardValueFromSettings() != 2) {
                setOriginalHardKeyboardValue(getSecureIntForUser("show_ime_with_hard_keyboard", 0, this.mUserId) != 0);
            }
            putSecureIntForUser("show_ime_with_hard_keyboard", 1, this.mUserId);
        } else if (this.mSoftKeyboardShowMode == 2) {
            putSecureIntForUser("show_ime_with_hard_keyboard", getOriginalHardKeyboardValue() ? 1 : 0, this.mUserId);
        }
        saveSoftKeyboardValueToSettings(i);
        this.mSoftKeyboardShowMode = i;
        this.mServiceChangingSoftKeyboardMode = componentName;
        for (int size = this.mBoundServices.size() - 1; size >= 0; size--) {
            this.mBoundServices.get(size).notifySoftKeyboardShowModeChangedLocked(this.mSoftKeyboardShowMode);
        }
        return true;
    }

    int getSoftKeyboardShowModeLocked() {
        return this.mSoftKeyboardShowMode;
    }

    void reconcileSoftKeyboardModeWithSettingsLocked() {
        boolean z = getSecureIntForUser("show_ime_with_hard_keyboard", 0, this.mUserId) != 0;
        if (this.mSoftKeyboardShowMode == 2 && !z) {
            setSoftKeyboardModeLocked(0, null);
            setUserOverridesHardKeyboardSetting();
        }
        if (getSoftKeyboardValueFromSettings() != this.mSoftKeyboardShowMode) {
            android.util.Slog.e(LOG_TAG, "Show IME setting inconsistent with internal state. Overwriting");
            setSoftKeyboardModeLocked(0, null);
            putSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId);
        }
    }

    boolean getBindInstantServiceAllowedLocked() {
        return this.mBindInstantServiceAllowed;
    }

    void setBindInstantServiceAllowedLocked(boolean z) {
        this.mBindInstantServiceAllowed = z;
    }

    java.util.Set<android.content.ComponentName> getBindingServicesLocked() {
        return this.mBindingServices;
    }

    java.util.Set<android.content.ComponentName> getCrashedServicesLocked() {
        return this.mCrashedServices;
    }

    java.util.Set<android.content.ComponentName> getEnabledServicesLocked() {
        return this.mEnabledServices;
    }

    void removeDisabledServicesFromTemporaryStatesLocked() {
        int size = this.mInstalledServices.size();
        for (int i = 0; i < size; i++) {
            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(this.mInstalledServices.get(i).getId());
            if (!this.mEnabledServices.contains(unflattenFromString)) {
                this.mCrashedServices.remove(unflattenFromString);
                this.mBindingServices.remove(unflattenFromString);
            }
        }
    }

    java.util.List<com.android.server.accessibility.AccessibilityServiceConnection> getBoundServicesLocked() {
        return this.mBoundServices;
    }

    int getClientStateLocked(boolean z, int i) {
        int i2 = 1;
        boolean z2 = z || isHandlingAccessibilityEventsLocked();
        if (!z2) {
            i2 = 0;
        }
        if (z2 && this.mIsTouchExplorationEnabled) {
            i2 = i2 | 2 | 8 | 16;
        }
        if (this.mIsTextHighContrastEnabled) {
            i2 |= 4;
        }
        if (this.mIsAudioDescriptionByDefaultRequested) {
            i2 |= 4096;
        }
        return i2 | i;
    }

    private void setUserOverridesHardKeyboardSetting() {
        putSecureIntForUser("accessibility_soft_keyboard_mode", getSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId) | 1073741824, this.mUserId);
    }

    private boolean hasUserOverriddenHardKeyboardSetting() {
        return (getSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId) & 1073741824) != 0;
    }

    private void setOriginalHardKeyboardValue(boolean z) {
        putSecureIntForUser("accessibility_soft_keyboard_mode", (getSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId) & (-536870913)) | (z ? 536870912 : 0), this.mUserId);
    }

    private void saveSoftKeyboardValueToSettings(int i) {
        putSecureIntForUser("accessibility_soft_keyboard_mode", i | (getSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId) & (-4)), this.mUserId);
    }

    private int getSoftKeyboardValueFromSettings() {
        return getSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId) & 3;
    }

    private boolean getOriginalHardKeyboardValue() {
        return (getSecureIntForUser("accessibility_soft_keyboard_mode", 0, this.mUserId) & 536870912) != 0;
    }

    private void unbindAllServicesLocked() {
        java.util.ArrayList<com.android.server.accessibility.AccessibilityServiceConnection> arrayList = this.mBoundServices;
        for (int size = arrayList.size(); size > 0; size--) {
            arrayList.get(0).unbindLocked();
        }
    }

    private int getSecureIntForUser(java.lang.String str, int i, int i2) {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), str, i, i2);
    }

    private void putSecureIntForUser(java.lang.String str, int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), str, i, i2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.append("User state[");
        printWriter.println();
        printWriter.append("     attributes:{id=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mUserId));
        printWriter.append(", touchExplorationEnabled=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mIsTouchExplorationEnabled));
        printWriter.append(", serviceHandlesDoubleTap=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mServiceHandlesDoubleTap));
        printWriter.append(", requestMultiFingerGestures=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mRequestMultiFingerGestures));
        printWriter.append(", requestTwoFingerPassthrough=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mRequestTwoFingerPassthrough));
        printWriter.append(", sendMotionEventsEnabled").append((java.lang.CharSequence) java.lang.String.valueOf(this.mSendMotionEventsEnabled));
        printWriter.append(", displayMagnificationEnabled=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mIsMagnificationSingleFingerTripleTapEnabled));
        printWriter.append(", autoclickEnabled=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mIsAutoclickEnabled));
        printWriter.append(", nonInteractiveUiTimeout=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mNonInteractiveUiTimeout));
        printWriter.append(", interactiveUiTimeout=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mInteractiveUiTimeout));
        printWriter.append(", installedServiceCount=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mInstalledServices.size()));
        printWriter.append(", magnificationModes=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mMagnificationModes));
        printWriter.append(", magnificationCapabilities=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mMagnificationCapabilities));
        printWriter.append(", audioDescriptionByDefaultEnabled=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mIsAudioDescriptionByDefaultRequested));
        printWriter.append(", magnificationFollowTypingEnabled=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mMagnificationFollowTypingEnabled));
        printWriter.append(", alwaysOnMagnificationEnabled=").append((java.lang.CharSequence) java.lang.String.valueOf(this.mAlwaysOnMagnificationEnabled));
        printWriter.append("}");
        printWriter.println();
        printWriter.append("     shortcut key:{");
        int size = this.mAccessibilityShortcutKeyTargets.size();
        int i = 0;
        while (i < size) {
            printWriter.append((java.lang.CharSequence) this.mAccessibilityShortcutKeyTargets.valueAt(i));
            i++;
            if (i < size) {
                printWriter.append(", ");
            }
        }
        printWriter.println("}");
        printWriter.append("     button:{");
        int size2 = this.mAccessibilityButtonTargets.size();
        int i2 = 0;
        while (i2 < size2) {
            printWriter.append((java.lang.CharSequence) this.mAccessibilityButtonTargets.valueAt(i2));
            i2++;
            if (i2 < size2) {
                printWriter.append(", ");
            }
        }
        printWriter.println("}");
        printWriter.append("     button target:{").append((java.lang.CharSequence) this.mTargetAssignedToAccessibilityButton);
        printWriter.println("}");
        printWriter.append("     Bound services:{");
        int size3 = this.mBoundServices.size();
        for (int i3 = 0; i3 < size3; i3++) {
            if (i3 > 0) {
                printWriter.append(", ");
                printWriter.println();
                printWriter.append("                     ");
            }
            this.mBoundServices.get(i3).dump(fileDescriptor, printWriter, strArr);
        }
        printWriter.println("}");
        printWriter.append("     Enabled services:{");
        java.util.Iterator<android.content.ComponentName> it = this.mEnabledServices.iterator();
        if (it.hasNext()) {
            printWriter.append((java.lang.CharSequence) it.next().toShortString());
            while (it.hasNext()) {
                android.content.ComponentName next = it.next();
                printWriter.append(", ");
                printWriter.append((java.lang.CharSequence) next.toShortString());
            }
        }
        printWriter.println("}");
        printWriter.append("     Binding services:{");
        java.util.Iterator<android.content.ComponentName> it2 = this.mBindingServices.iterator();
        if (it2.hasNext()) {
            printWriter.append((java.lang.CharSequence) it2.next().toShortString());
            while (it2.hasNext()) {
                android.content.ComponentName next2 = it2.next();
                printWriter.append(", ");
                printWriter.append((java.lang.CharSequence) next2.toShortString());
            }
        }
        printWriter.println("}");
        printWriter.append("     Crashed services:{");
        java.util.Iterator<android.content.ComponentName> it3 = this.mCrashedServices.iterator();
        if (it3.hasNext()) {
            printWriter.append((java.lang.CharSequence) it3.next().toShortString());
            while (it3.hasNext()) {
                android.content.ComponentName next3 = it3.next();
                printWriter.append(", ");
                printWriter.append((java.lang.CharSequence) next3.toShortString());
            }
        }
        printWriter.println("}");
        printWriter.println("     Client list info:{");
        this.mUserClients.dump(printWriter, "          Client list ");
        printWriter.println("          Registered clients:{");
        for (int i4 = 0; i4 < this.mUserClients.getRegisteredCallbackCount(); i4++) {
            printWriter.append((java.lang.CharSequence) java.util.Arrays.toString(((com.android.server.accessibility.AccessibilityManagerService.Client) this.mUserClients.getRegisteredCallbackCookie(i4)).mPackageNames));
        }
        printWriter.println("}]");
    }

    public boolean isAutoclickEnabledLocked() {
        return this.mIsAutoclickEnabled;
    }

    public void setAutoclickEnabledLocked(boolean z) {
        this.mIsAutoclickEnabled = z;
    }

    public boolean isMagnificationSingleFingerTripleTapEnabledLocked() {
        return this.mIsMagnificationSingleFingerTripleTapEnabled;
    }

    public void setMagnificationSingleFingerTripleTapEnabledLocked(boolean z) {
        this.mIsMagnificationSingleFingerTripleTapEnabled = z;
    }

    public boolean isMagnificationTwoFingerTripleTapEnabledLocked() {
        return this.mMagnificationTwoFingerTripleTapEnabled;
    }

    public void setMagnificationTwoFingerTripleTapEnabledLocked(boolean z) {
        this.mMagnificationTwoFingerTripleTapEnabled = z;
    }

    public boolean isFilterKeyEventsEnabledLocked() {
        return this.mIsFilterKeyEventsEnabled;
    }

    public void setFilterKeyEventsEnabledLocked(boolean z) {
        this.mIsFilterKeyEventsEnabled = z;
    }

    public int getInteractiveUiTimeoutLocked() {
        return this.mInteractiveUiTimeout;
    }

    public void setInteractiveUiTimeoutLocked(int i) {
        this.mInteractiveUiTimeout = i;
    }

    public int getLastSentClientStateLocked() {
        return this.mLastSentClientState;
    }

    public void setLastSentClientStateLocked(int i) {
        this.mLastSentClientState = i;
    }

    public boolean isShortcutMagnificationEnabledLocked() {
        return this.mAccessibilityShortcutKeyTargets.contains("com.android.server.accessibility.MagnificationController") || this.mAccessibilityButtonTargets.contains("com.android.server.accessibility.MagnificationController");
    }

    public int getMagnificationModeLocked(int i) {
        int i2 = this.mMagnificationModes.get(i, 0);
        if (i2 == 0) {
            setMagnificationModeLocked(i, 1);
            return 1;
        }
        return i2;
    }

    int getMagnificationCapabilitiesLocked() {
        return this.mMagnificationCapabilities;
    }

    public void setMagnificationCapabilitiesLocked(int i) {
        this.mMagnificationCapabilities = i;
    }

    public void setMagnificationFollowTypingEnabled(boolean z) {
        this.mMagnificationFollowTypingEnabled = z;
    }

    public boolean isMagnificationFollowTypingEnabled() {
        return this.mMagnificationFollowTypingEnabled;
    }

    public void setAlwaysOnMagnificationEnabled(boolean z) {
        this.mAlwaysOnMagnificationEnabled = z;
    }

    public boolean isAlwaysOnMagnificationEnabled() {
        return this.mAlwaysOnMagnificationEnabled;
    }

    public void setMagnificationModeLocked(int i, int i2) {
        this.mMagnificationModes.put(i, i2);
    }

    public void disableShortcutMagnificationLocked() {
        this.mAccessibilityShortcutKeyTargets.remove("com.android.server.accessibility.MagnificationController");
        this.mAccessibilityButtonTargets.remove("com.android.server.accessibility.MagnificationController");
    }

    public android.util.ArraySet<java.lang.String> getShortcutTargetsLocked(int i) {
        if (i == 1) {
            return this.mAccessibilityShortcutKeyTargets;
        }
        if (i == 0) {
            return this.mAccessibilityButtonTargets;
        }
        return null;
    }

    public boolean isShortcutTargetInstalledLocked(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return false;
        }
        if ("com.android.server.accessibility.MagnificationController".equals(str)) {
            return true;
        }
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
        if (unflattenFromString == null) {
            return false;
        }
        if (com.android.internal.accessibility.AccessibilityShortcutController.getFrameworkShortcutFeaturesMap().containsKey(unflattenFromString) || getInstalledServiceInfoLocked(unflattenFromString) != null) {
            return true;
        }
        for (int i = 0; i < this.mInstalledShortcuts.size(); i++) {
            if (this.mInstalledShortcuts.get(i).getComponentName().equals(unflattenFromString)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeShortcutTargetLocked(int i, final android.content.ComponentName componentName) {
        return getShortcutTargetsLocked(i).removeIf(new java.util.function.Predicate() { // from class: com.android.server.accessibility.AccessibilityUserState$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeShortcutTargetLocked$0;
                lambda$removeShortcutTargetLocked$0 = com.android.server.accessibility.AccessibilityUserState.lambda$removeShortcutTargetLocked$0(componentName, (java.lang.String) obj);
                return lambda$removeShortcutTargetLocked$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeShortcutTargetLocked$0(android.content.ComponentName componentName, java.lang.String str) {
        android.content.ComponentName unflattenFromString;
        if (str == null || (unflattenFromString = android.content.ComponentName.unflattenFromString(str)) == null) {
            return false;
        }
        return unflattenFromString.equals(componentName);
    }

    public android.accessibilityservice.AccessibilityServiceInfo getInstalledServiceInfoLocked(android.content.ComponentName componentName) {
        for (int i = 0; i < this.mInstalledServices.size(); i++) {
            android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = this.mInstalledServices.get(i);
            if (accessibilityServiceInfo.getComponentName().equals(componentName)) {
                return accessibilityServiceInfo;
            }
        }
        return null;
    }

    public com.android.server.accessibility.AccessibilityServiceConnection getServiceConnectionLocked(android.content.ComponentName componentName) {
        return this.mComponentNameToServiceMap.get(componentName);
    }

    public int getNonInteractiveUiTimeoutLocked() {
        return this.mNonInteractiveUiTimeout;
    }

    public void setNonInteractiveUiTimeoutLocked(int i) {
        this.mNonInteractiveUiTimeout = i;
    }

    public boolean isPerformGesturesEnabledLocked() {
        return this.mIsPerformGesturesEnabled;
    }

    public void setPerformGesturesEnabledLocked(boolean z) {
        this.mIsPerformGesturesEnabled = z;
    }

    public boolean isAccessibilityFocusOnlyInActiveWindow() {
        return this.mAccessibilityFocusOnlyInActiveWindow;
    }

    public void setAccessibilityFocusOnlyInActiveWindow(boolean z) {
        this.mAccessibilityFocusOnlyInActiveWindow = z;
    }

    public android.content.ComponentName getServiceChangingSoftKeyboardModeLocked() {
        return this.mServiceChangingSoftKeyboardMode;
    }

    public void setServiceChangingSoftKeyboardModeLocked(android.content.ComponentName componentName) {
        this.mServiceChangingSoftKeyboardMode = componentName;
    }

    public boolean isTextHighContrastEnabledLocked() {
        return this.mIsTextHighContrastEnabled;
    }

    public void setTextHighContrastEnabledLocked(boolean z) {
        this.mIsTextHighContrastEnabled = z;
    }

    public boolean isAudioDescriptionByDefaultEnabledLocked() {
        return this.mIsAudioDescriptionByDefaultRequested;
    }

    public void setAudioDescriptionByDefaultEnabledLocked(boolean z) {
        this.mIsAudioDescriptionByDefaultRequested = z;
    }

    public boolean isTouchExplorationEnabledLocked() {
        return this.mIsTouchExplorationEnabled;
    }

    public void setTouchExplorationEnabledLocked(boolean z) {
        this.mIsTouchExplorationEnabled = z;
    }

    public boolean isServiceHandlesDoubleTapEnabledLocked() {
        return this.mServiceHandlesDoubleTap;
    }

    public void setServiceHandlesDoubleTapLocked(boolean z) {
        this.mServiceHandlesDoubleTap = z;
    }

    public boolean isMultiFingerGesturesEnabledLocked() {
        return this.mRequestMultiFingerGestures;
    }

    public void setMultiFingerGesturesLocked(boolean z) {
        this.mRequestMultiFingerGestures = z;
    }

    public boolean isTwoFingerPassthroughEnabledLocked() {
        return this.mRequestTwoFingerPassthrough;
    }

    public void setTwoFingerPassthroughLocked(boolean z) {
        this.mRequestTwoFingerPassthrough = z;
    }

    public boolean isSendMotionEventsEnabled() {
        return this.mSendMotionEventsEnabled;
    }

    public void setSendMotionEventsEnabled(boolean z) {
        this.mSendMotionEventsEnabled = z;
    }

    public int getUserInteractiveUiTimeoutLocked() {
        return this.mUserInteractiveUiTimeout;
    }

    public void setUserInteractiveUiTimeoutLocked(int i) {
        this.mUserInteractiveUiTimeout = i;
    }

    public int getUserNonInteractiveUiTimeoutLocked() {
        return this.mUserNonInteractiveUiTimeout;
    }

    public void setUserNonInteractiveUiTimeoutLocked(int i) {
        this.mUserNonInteractiveUiTimeout = i;
    }

    public java.lang.String getTargetAssignedToAccessibilityButton() {
        return this.mTargetAssignedToAccessibilityButton;
    }

    public void setTargetAssignedToAccessibilityButton(java.lang.String str) {
        this.mTargetAssignedToAccessibilityButton = str;
    }

    public static boolean doesShortcutTargetsStringContain(java.util.Collection<java.lang.String> collection, java.lang.String str) {
        if (collection == null || str == null) {
            return false;
        }
        if (collection.contains(str)) {
            return true;
        }
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
        if (unflattenFromString == null) {
            return false;
        }
        for (java.lang.String str2 : collection) {
            if (!android.text.TextUtils.isEmpty(str2) && unflattenFromString.equals(android.content.ComponentName.unflattenFromString(str2))) {
                return true;
            }
        }
        return false;
    }

    public int getFocusStrokeWidthLocked() {
        return this.mFocusStrokeWidth;
    }

    public int getFocusColorLocked() {
        return this.mFocusColor;
    }

    public void setFocusAppearanceLocked(int i, int i2) {
        this.mFocusStrokeWidth = i;
        this.mFocusColor = i2;
    }

    public void setServiceDetectsGesturesEnabled(int i, boolean z) {
        this.mServiceDetectsGestures.put(i, java.lang.Boolean.valueOf(z));
    }

    public void resetServiceDetectsGestures() {
        this.mServiceDetectsGestures.clear();
    }

    public boolean isServiceDetectsGesturesEnabled(int i) {
        if (this.mServiceDetectsGestures.contains(i)) {
            return this.mServiceDetectsGestures.get(i).booleanValue();
        }
        return false;
    }
}
