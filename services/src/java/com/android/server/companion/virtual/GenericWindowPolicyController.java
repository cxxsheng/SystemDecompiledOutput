package com.android.server.companion.virtual;

/* loaded from: classes.dex */
public class GenericWindowPolicyController extends android.window.DisplayWindowPolicyController {
    public static final long ALLOW_SECURE_ACTIVITY_DISPLAY_ON_REMOTE_DEVICE = 201712607;
    private static final android.content.ComponentName BLOCKED_APP_STREAMING_COMPONENT = new android.content.ComponentName(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, com.android.internal.app.BlockedAppStreamingActivity.class.getName());
    private static final java.lang.String TAG = "GenericWindowPolicyController";

    @android.annotation.Nullable
    private final com.android.server.companion.virtual.GenericWindowPolicyController.ActivityBlockedCallback mActivityBlockedCallback;

    @com.android.internal.annotations.GuardedBy({"mGenericWindowPolicyControllerLock"})
    private boolean mActivityLaunchAllowedByDefault;

    @android.annotation.Nullable
    private final android.companion.virtual.VirtualDeviceManager.ActivityListener mActivityListener;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mGenericWindowPolicyControllerLock"})
    private final java.util.Set<android.content.ComponentName> mActivityPolicyExemptions;

    @android.annotation.NonNull
    private final android.util.ArraySet<android.os.UserHandle> mAllowedUsers;

    @android.annotation.NonNull
    private final android.content.AttributionSource mAttributionSource;
    private final boolean mCrossTaskNavigationAllowedByDefault;

    @android.annotation.NonNull
    private final android.util.ArraySet<android.content.ComponentName> mCrossTaskNavigationExemptions;

    @android.annotation.Nullable
    private final android.content.ComponentName mCustomHomeComponent;

    @android.annotation.NonNull
    private final java.util.Set<java.lang.String> mDisplayCategories;

    @android.annotation.Nullable
    private final com.android.server.companion.virtual.GenericWindowPolicyController.IntentListenerCallback mIntentListenerCallback;

    @android.annotation.Nullable
    private final android.content.ComponentName mPermissionDialogComponent;

    @android.annotation.Nullable
    private final com.android.server.companion.virtual.GenericWindowPolicyController.PipBlockedCallback mPipBlockedCallback;

    @android.annotation.Nullable
    private final com.android.server.companion.virtual.GenericWindowPolicyController.SecureWindowCallback mSecureWindowCallback;

    @com.android.internal.annotations.GuardedBy({"mGenericWindowPolicyControllerLock"})
    private boolean mShowTasksInHostDeviceRecents;
    private final java.lang.Object mGenericWindowPolicyControllerLock = new java.lang.Object();
    private int mDisplayId = -1;
    private boolean mIsMirrorDisplay = false;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mGenericWindowPolicyControllerLock"})
    private final android.util.ArraySet<java.lang.Integer> mRunningUids = new android.util.ArraySet<>();
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mGenericWindowPolicyControllerLock"})
    private final android.util.ArraySet<com.android.server.companion.virtual.GenericWindowPolicyController.RunningAppsChangedListener> mRunningAppsChangedListeners = new android.util.ArraySet<>();

    public interface ActivityBlockedCallback {
        void onActivityBlocked(int i, android.content.pm.ActivityInfo activityInfo);
    }

    public interface IntentListenerCallback {
        boolean shouldInterceptIntent(android.content.Intent intent);
    }

    public interface PipBlockedCallback {
        void onEnteringPipBlocked(int i);
    }

    public interface RunningAppsChangedListener {
        void onRunningAppsChanged(android.util.ArraySet<java.lang.Integer> arraySet);
    }

    public interface SecureWindowCallback {
        void onSecureWindowShown(int i, int i2);
    }

    public GenericWindowPolicyController(int i, int i2, android.content.AttributionSource attributionSource, @android.annotation.NonNull android.util.ArraySet<android.os.UserHandle> arraySet, boolean z, @android.annotation.NonNull java.util.Set<android.content.ComponentName> set, boolean z2, @android.annotation.NonNull java.util.Set<android.content.ComponentName> set2, @android.annotation.Nullable android.content.ComponentName componentName, @android.annotation.Nullable android.companion.virtual.VirtualDeviceManager.ActivityListener activityListener, @android.annotation.Nullable com.android.server.companion.virtual.GenericWindowPolicyController.PipBlockedCallback pipBlockedCallback, @android.annotation.Nullable com.android.server.companion.virtual.GenericWindowPolicyController.ActivityBlockedCallback activityBlockedCallback, @android.annotation.Nullable com.android.server.companion.virtual.GenericWindowPolicyController.SecureWindowCallback secureWindowCallback, @android.annotation.Nullable com.android.server.companion.virtual.GenericWindowPolicyController.IntentListenerCallback intentListenerCallback, @android.annotation.NonNull java.util.Set<java.lang.String> set3, boolean z3, @android.annotation.Nullable android.content.ComponentName componentName2) {
        this.mAttributionSource = attributionSource;
        this.mAllowedUsers = arraySet;
        this.mActivityLaunchAllowedByDefault = z;
        this.mActivityPolicyExemptions = set;
        this.mCrossTaskNavigationAllowedByDefault = z2;
        this.mCrossTaskNavigationExemptions = new android.util.ArraySet<>(set2);
        this.mPermissionDialogComponent = componentName;
        this.mActivityBlockedCallback = activityBlockedCallback;
        setInterestedWindowFlags(i, i2);
        this.mActivityListener = activityListener;
        this.mPipBlockedCallback = pipBlockedCallback;
        this.mSecureWindowCallback = secureWindowCallback;
        this.mIntentListenerCallback = intentListenerCallback;
        this.mDisplayCategories = set3;
        this.mShowTasksInHostDeviceRecents = z3;
        this.mCustomHomeComponent = componentName2;
    }

    void setDisplayId(int i, boolean z) {
        this.mDisplayId = i;
        this.mIsMirrorDisplay = z;
    }

    public void setShowInHostDeviceRecents(boolean z) {
        synchronized (this.mGenericWindowPolicyControllerLock) {
            this.mShowTasksInHostDeviceRecents = z;
        }
    }

    void setActivityLaunchDefaultAllowed(boolean z) {
        synchronized (this.mGenericWindowPolicyControllerLock) {
            try {
                if (this.mActivityLaunchAllowedByDefault != z) {
                    this.mActivityPolicyExemptions.clear();
                }
                this.mActivityLaunchAllowedByDefault = z;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void addActivityPolicyExemption(@android.annotation.NonNull android.content.ComponentName componentName) {
        synchronized (this.mGenericWindowPolicyControllerLock) {
            this.mActivityPolicyExemptions.add(componentName);
        }
    }

    void removeActivityPolicyExemption(@android.annotation.NonNull android.content.ComponentName componentName) {
        synchronized (this.mGenericWindowPolicyControllerLock) {
            this.mActivityPolicyExemptions.remove(componentName);
        }
    }

    public void registerRunningAppsChangedListener(@android.annotation.NonNull com.android.server.companion.virtual.GenericWindowPolicyController.RunningAppsChangedListener runningAppsChangedListener) {
        synchronized (this.mGenericWindowPolicyControllerLock) {
            this.mRunningAppsChangedListeners.add(runningAppsChangedListener);
        }
    }

    public void unregisterRunningAppsChangedListener(@android.annotation.NonNull com.android.server.companion.virtual.GenericWindowPolicyController.RunningAppsChangedListener runningAppsChangedListener) {
        synchronized (this.mGenericWindowPolicyControllerLock) {
            this.mRunningAppsChangedListeners.remove(runningAppsChangedListener);
        }
    }

    public boolean canActivityBeLaunched(@android.annotation.NonNull android.content.pm.ActivityInfo activityInfo, @android.annotation.Nullable android.content.Intent intent, int i, int i2, boolean z) {
        if (!canContainActivity(activityInfo, i, i2, z)) {
            notifyActivityBlocked(activityInfo);
            return false;
        }
        if (this.mIntentListenerCallback != null && intent != null && this.mIntentListenerCallback.shouldInterceptIntent(intent)) {
            android.util.Slog.d(TAG, "Virtual device intercepting intent");
            return false;
        }
        return true;
    }

    public boolean canContainActivity(@android.annotation.NonNull android.content.pm.ActivityInfo activityInfo, int i, int i2, boolean z) {
        if (this.mIsMirrorDisplay) {
            android.util.Slog.d(TAG, "Mirror virtual displays cannot contain activities.");
            return false;
        }
        if (!isWindowingModeSupported(i)) {
            android.util.Slog.d(TAG, "Virtual device doesn't support windowing mode " + i);
            return false;
        }
        if ((activityInfo.flags & 65536) == 0) {
            android.util.Slog.d(TAG, "Virtual device requires android:canDisplayOnRemoteDevices=true");
            return false;
        }
        android.os.UserHandle userHandleForUid = android.os.UserHandle.getUserHandleForUid(activityInfo.applicationInfo.uid);
        android.content.ComponentName componentName = activityInfo.getComponentName();
        if (BLOCKED_APP_STREAMING_COMPONENT.equals(componentName) && userHandleForUid.isSystem()) {
            return true;
        }
        if (!this.mAllowedUsers.contains(userHandleForUid)) {
            android.util.Slog.d(TAG, "Virtual device launch disallowed from user " + userHandleForUid);
            return false;
        }
        if (!activityMatchesDisplayCategory(activityInfo)) {
            android.util.Slog.d(TAG, "The activity's required display category '" + activityInfo.requiredDisplayCategory + "' not found on virtual display with the following categories: " + this.mDisplayCategories);
            return false;
        }
        synchronized (this.mGenericWindowPolicyControllerLock) {
            try {
                if (!isAllowedByPolicy(this.mActivityLaunchAllowedByDefault, this.mActivityPolicyExemptions, componentName)) {
                    android.util.Slog.d(TAG, "Virtual device launch disallowed by policy: " + componentName);
                    return false;
                }
                if (!z || i2 == 0 || isAllowedByPolicy(this.mCrossTaskNavigationAllowedByDefault, this.mCrossTaskNavigationExemptions, componentName)) {
                    return this.mPermissionDialogComponent == null || !this.mPermissionDialogComponent.equals(componentName);
                }
                android.util.Slog.d(TAG, "Virtual device cross task navigation disallowed by policy: " + componentName);
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean keepActivityOnWindowFlagsChanged(final android.content.pm.ActivityInfo activityInfo, int i, int i2) {
        int i3 = i & 8192;
        if (i3 != 0 && this.mSecureWindowCallback != null) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.companion.virtual.GenericWindowPolicyController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.companion.virtual.GenericWindowPolicyController.this.lambda$keepActivityOnWindowFlagsChanged$0(activityInfo);
                }
            });
        }
        if (!android.app.compat.CompatChanges.isChangeEnabled(ALLOW_SECURE_ACTIVITY_DISPLAY_ON_REMOTE_DEVICE, activityInfo.packageName, android.os.UserHandle.getUserHandleForUid(activityInfo.applicationInfo.uid))) {
            if (i3 != 0 || (524288 & i2) != 0) {
                notifyActivityBlocked(activityInfo);
                return false;
            }
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$keepActivityOnWindowFlagsChanged$0(android.content.pm.ActivityInfo activityInfo) {
        this.mSecureWindowCallback.onSecureWindowShown(this.mDisplayId, activityInfo.applicationInfo.uid);
    }

    public void onTopActivityChanged(final android.content.ComponentName componentName, int i, final int i2) {
        if (this.mActivityListener != null && componentName != null) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.companion.virtual.GenericWindowPolicyController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.companion.virtual.GenericWindowPolicyController.this.lambda$onTopActivityChanged$1(componentName, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTopActivityChanged$1(android.content.ComponentName componentName, int i) {
        this.mActivityListener.onTopActivityChanged(this.mDisplayId, componentName, i);
    }

    public void onRunningAppsChanged(final android.util.ArraySet<java.lang.Integer> arraySet) {
        synchronized (this.mGenericWindowPolicyControllerLock) {
            try {
                this.mRunningUids.clear();
                this.mRunningUids.addAll((android.util.ArraySet<? extends java.lang.Integer>) arraySet);
                if (this.mActivityListener != null && this.mRunningUids.isEmpty()) {
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.companion.virtual.GenericWindowPolicyController$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.companion.virtual.GenericWindowPolicyController.this.lambda$onRunningAppsChanged$2();
                        }
                    });
                }
                if (!this.mRunningAppsChangedListeners.isEmpty()) {
                    final android.util.ArraySet arraySet2 = new android.util.ArraySet((android.util.ArraySet) this.mRunningAppsChangedListeners);
                    this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.companion.virtual.GenericWindowPolicyController$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.companion.virtual.GenericWindowPolicyController.lambda$onRunningAppsChanged$3(arraySet2, arraySet);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRunningAppsChanged$2() {
        this.mActivityListener.onDisplayEmpty(this.mDisplayId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onRunningAppsChanged$3(android.util.ArraySet arraySet, android.util.ArraySet arraySet2) {
        java.util.Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            ((com.android.server.companion.virtual.GenericWindowPolicyController.RunningAppsChangedListener) it.next()).onRunningAppsChanged(arraySet2);
        }
    }

    public boolean canShowTasksInHostDeviceRecents() {
        boolean z;
        synchronized (this.mGenericWindowPolicyControllerLock) {
            z = this.mShowTasksInHostDeviceRecents;
        }
        return z;
    }

    public boolean isEnteringPipAllowed(final int i) {
        if (super.isEnteringPipAllowed(i)) {
            return true;
        }
        if (this.mPipBlockedCallback != null) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.companion.virtual.GenericWindowPolicyController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.companion.virtual.GenericWindowPolicyController.this.lambda$isEnteringPipAllowed$4(i);
                }
            });
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$isEnteringPipAllowed$4(int i) {
        this.mPipBlockedCallback.onEnteringPipBlocked(i);
    }

    @android.annotation.Nullable
    public android.content.ComponentName getCustomHomeComponent() {
        return this.mCustomHomeComponent;
    }

    boolean containsUid(int i) {
        boolean contains;
        synchronized (this.mGenericWindowPolicyControllerLock) {
            contains = this.mRunningUids.contains(java.lang.Integer.valueOf(i));
        }
        return contains;
    }

    private boolean activityMatchesDisplayCategory(android.content.pm.ActivityInfo activityInfo) {
        return this.mDisplayCategories.isEmpty() ? activityInfo.requiredDisplayCategory == null : activityInfo.requiredDisplayCategory != null && this.mDisplayCategories.contains(activityInfo.requiredDisplayCategory);
    }

    private void notifyActivityBlocked(android.content.pm.ActivityInfo activityInfo) {
        if (!this.mIsMirrorDisplay && this.mActivityBlockedCallback != null) {
            this.mActivityBlockedCallback.onActivityBlocked(this.mDisplayId, activityInfo);
        }
        if (android.companion.virtualdevice.flags.Flags.metricsCollection()) {
            com.android.modules.expresslog.Counter.logIncrementWithUid("virtual_devices.value_activity_blocked_count", this.mAttributionSource.getUid());
        }
    }

    private static boolean isAllowedByPolicy(boolean z, java.util.Set<android.content.ComponentName> set, android.content.ComponentName componentName) {
        return z != set.contains(componentName);
    }

    @com.android.internal.annotations.VisibleForTesting
    int getRunningAppsChangedListenersSizeForTesting() {
        int size;
        synchronized (this.mGenericWindowPolicyControllerLock) {
            size = this.mRunningAppsChangedListeners.size();
        }
        return size;
    }
}
