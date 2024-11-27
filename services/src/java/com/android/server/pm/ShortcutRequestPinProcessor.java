package com.android.server.pm;

/* loaded from: classes2.dex */
class ShortcutRequestPinProcessor {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "ShortcutService";
    private final java.lang.Object mLock;
    private final com.android.server.pm.ShortcutService mService;

    private static abstract class PinItemRequestInner extends android.content.pm.IPinItemRequest.Stub {

        @com.android.internal.annotations.GuardedBy({"this"})
        private boolean mAccepted;
        private final int mLauncherUid;
        protected final com.android.server.pm.ShortcutRequestPinProcessor mProcessor;
        private final android.content.IntentSender mResultIntent;

        private PinItemRequestInner(com.android.server.pm.ShortcutRequestPinProcessor shortcutRequestPinProcessor, android.content.IntentSender intentSender, int i) {
            this.mProcessor = shortcutRequestPinProcessor;
            this.mResultIntent = intentSender;
            this.mLauncherUid = i;
        }

        public android.content.pm.ShortcutInfo getShortcutInfo() {
            return null;
        }

        public android.appwidget.AppWidgetProviderInfo getAppWidgetProviderInfo() {
            return null;
        }

        public android.os.Bundle getExtras() {
            return null;
        }

        private boolean isCallerValid() {
            return this.mProcessor.isCallerUid(this.mLauncherUid);
        }

        public boolean isValid() {
            boolean z;
            if (!isCallerValid()) {
                return false;
            }
            synchronized (this) {
                z = this.mAccepted ? false : true;
            }
            return z;
        }

        public boolean accept(android.os.Bundle bundle) {
            android.content.Intent putExtras;
            if (!isCallerValid()) {
                throw new java.lang.SecurityException("Calling uid mismatch");
            }
            if (bundle == null) {
                putExtras = null;
            } else {
                try {
                    bundle.size();
                    putExtras = new android.content.Intent().putExtras(bundle);
                } catch (java.lang.RuntimeException e) {
                    throw new java.lang.IllegalArgumentException("options cannot be unparceled", e);
                }
            }
            synchronized (this) {
                if (this.mAccepted) {
                    throw new java.lang.IllegalStateException("accept() called already");
                }
                this.mAccepted = true;
            }
            if (tryAccept()) {
                this.mProcessor.sendResultIntent(this.mResultIntent, putExtras);
                return true;
            }
            return false;
        }

        protected boolean tryAccept() {
            return true;
        }
    }

    private static class PinAppWidgetRequestInner extends com.android.server.pm.ShortcutRequestPinProcessor.PinItemRequestInner {
        final android.appwidget.AppWidgetProviderInfo mAppWidgetProviderInfo;
        final android.os.Bundle mExtras;

        private PinAppWidgetRequestInner(com.android.server.pm.ShortcutRequestPinProcessor shortcutRequestPinProcessor, android.content.IntentSender intentSender, int i, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo, android.os.Bundle bundle) {
            super(intentSender, i);
            this.mAppWidgetProviderInfo = appWidgetProviderInfo;
            this.mExtras = bundle;
        }

        @Override // com.android.server.pm.ShortcutRequestPinProcessor.PinItemRequestInner
        public android.appwidget.AppWidgetProviderInfo getAppWidgetProviderInfo() {
            return this.mAppWidgetProviderInfo;
        }

        @Override // com.android.server.pm.ShortcutRequestPinProcessor.PinItemRequestInner
        public android.os.Bundle getExtras() {
            return this.mExtras;
        }
    }

    private static class PinShortcutRequestInner extends com.android.server.pm.ShortcutRequestPinProcessor.PinItemRequestInner {
        public final java.lang.String launcherPackage;
        public final int launcherUserId;
        public final boolean preExisting;
        public final android.content.pm.ShortcutInfo shortcutForLauncher;
        public final android.content.pm.ShortcutInfo shortcutOriginal;

        private PinShortcutRequestInner(com.android.server.pm.ShortcutRequestPinProcessor shortcutRequestPinProcessor, android.content.pm.ShortcutInfo shortcutInfo, android.content.pm.ShortcutInfo shortcutInfo2, android.content.IntentSender intentSender, java.lang.String str, int i, int i2, boolean z) {
            super(intentSender, i2);
            this.shortcutOriginal = shortcutInfo;
            this.shortcutForLauncher = shortcutInfo2;
            this.launcherPackage = str;
            this.launcherUserId = i;
            this.preExisting = z;
        }

        @Override // com.android.server.pm.ShortcutRequestPinProcessor.PinItemRequestInner
        public android.content.pm.ShortcutInfo getShortcutInfo() {
            return this.shortcutForLauncher;
        }

        @Override // com.android.server.pm.ShortcutRequestPinProcessor.PinItemRequestInner
        protected boolean tryAccept() {
            return this.mProcessor.directPinShortcut(this);
        }
    }

    public ShortcutRequestPinProcessor(com.android.server.pm.ShortcutService shortcutService, java.lang.Object obj) {
        this.mService = shortcutService;
        this.mLock = obj;
    }

    public boolean isRequestPinItemSupported(int i, int i2) {
        return getRequestPinConfirmationActivity(i, i2) != null;
    }

    public boolean requestPinItemLocked(android.content.pm.ShortcutInfo shortcutInfo, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo, android.os.Bundle bundle, int i, android.content.IntentSender intentSender) {
        android.content.pm.LauncherApps.PinItemRequest pinItemRequest;
        int i2 = shortcutInfo != null ? 1 : 2;
        android.util.Pair<android.content.ComponentName, java.lang.Integer> requestPinConfirmationActivity = getRequestPinConfirmationActivity(i, i2);
        if (requestPinConfirmationActivity == null) {
            android.util.Log.w(TAG, "Launcher doesn't support requestPinnedShortcut(). Shortcut not created.");
            return false;
        }
        int intValue = ((java.lang.Integer) requestPinConfirmationActivity.second).intValue();
        this.mService.throwIfUserLockedL(intValue);
        if (shortcutInfo != null) {
            pinItemRequest = requestPinShortcutLocked(shortcutInfo, intentSender, ((android.content.ComponentName) requestPinConfirmationActivity.first).getPackageName(), ((java.lang.Integer) requestPinConfirmationActivity.second).intValue());
        } else {
            pinItemRequest = new android.content.pm.LauncherApps.PinItemRequest(new com.android.server.pm.ShortcutRequestPinProcessor.PinAppWidgetRequestInner(intentSender, this.mService.injectGetPackageUid(((android.content.ComponentName) requestPinConfirmationActivity.first).getPackageName(), intValue), appWidgetProviderInfo, bundle), 2);
        }
        return startRequestConfirmActivity((android.content.ComponentName) requestPinConfirmationActivity.first, intValue, pinItemRequest, i2);
    }

    public android.content.Intent createShortcutResultIntent(@android.annotation.NonNull android.content.pm.ShortcutInfo shortcutInfo, int i) {
        int parentOrSelfUserId = this.mService.getParentOrSelfUserId(i);
        java.lang.String defaultLauncher = this.mService.getDefaultLauncher(parentOrSelfUserId);
        if (defaultLauncher == null) {
            android.util.Log.e(TAG, "Default launcher not found.");
            return null;
        }
        this.mService.throwIfUserLockedL(parentOrSelfUserId);
        return new android.content.Intent().putExtra("android.content.pm.extra.PIN_ITEM_REQUEST", requestPinShortcutLocked(shortcutInfo, null, defaultLauncher, parentOrSelfUserId));
    }

    @android.annotation.NonNull
    private android.content.pm.LauncherApps.PinItemRequest requestPinShortcutLocked(android.content.pm.ShortcutInfo shortcutInfo, android.content.IntentSender intentSender, java.lang.String str, int i) {
        android.content.pm.ShortcutInfo clone;
        android.content.IntentSender intentSender2;
        android.content.pm.ShortcutInfo findShortcutById = this.mService.getPackageShortcutsForPublisherLocked(shortcutInfo.getPackage(), shortcutInfo.getUserId()).findShortcutById(shortcutInfo.getId());
        boolean z = findShortcutById != null;
        if (z) {
            findShortcutById.isVisibleToPublisher();
        }
        if (z) {
            validateExistingShortcut(findShortcutById);
            boolean hasPinned = this.mService.getLauncherShortcutsLocked(str, findShortcutById.getUserId(), i).hasPinned(findShortcutById);
            if (!hasPinned) {
                intentSender2 = intentSender;
            } else {
                intentSender2 = null;
                sendResultIntent(intentSender, null);
            }
            android.content.pm.ShortcutInfo clone2 = findShortcutById.clone(27);
            if (!hasPinned) {
                clone2.clearFlags(2);
            }
            clone = clone2;
        } else {
            if (shortcutInfo.getActivity() == null) {
                shortcutInfo.setActivity(this.mService.injectGetDefaultMainActivity(shortcutInfo.getPackage(), shortcutInfo.getUserId()));
            }
            this.mService.validateShortcutForPinRequest(shortcutInfo);
            shortcutInfo.resolveResourceStrings(this.mService.injectGetResourcesForApplicationAsUser(shortcutInfo.getPackage(), shortcutInfo.getUserId()));
            clone = shortcutInfo.clone(26);
            intentSender2 = intentSender;
        }
        return new android.content.pm.LauncherApps.PinItemRequest(new com.android.server.pm.ShortcutRequestPinProcessor.PinShortcutRequestInner(shortcutInfo, clone, intentSender2, str, i, this.mService.injectGetPackageUid(str, i), z), 1);
    }

    private void validateExistingShortcut(android.content.pm.ShortcutInfo shortcutInfo) {
        com.android.internal.util.Preconditions.checkArgument(shortcutInfo.isEnabled(), "Shortcut ID=" + shortcutInfo + " already exists but disabled.");
    }

    private boolean startRequestConfirmActivity(android.content.ComponentName componentName, int i, android.content.pm.LauncherApps.PinItemRequest pinItemRequest, int i2) {
        android.content.Intent intent = new android.content.Intent(i2 == 1 ? "android.content.pm.action.CONFIRM_PIN_SHORTCUT" : "android.content.pm.action.CONFIRM_PIN_APPWIDGET");
        intent.setComponent(componentName);
        intent.putExtra("android.content.pm.extra.PIN_ITEM_REQUEST", pinItemRequest);
        intent.addFlags(268468224);
        long injectClearCallingIdentity = this.mService.injectClearCallingIdentity();
        try {
            try {
                this.mService.mContext.startActivityAsUser(intent, android.os.UserHandle.of(i));
                return true;
            } catch (java.lang.RuntimeException e) {
                android.util.Log.e(TAG, "Unable to start activity " + componentName, e);
                this.mService.injectRestoreCallingIdentity(injectClearCallingIdentity);
                return false;
            }
        } finally {
            this.mService.injectRestoreCallingIdentity(injectClearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.util.Pair<android.content.ComponentName, java.lang.Integer> getRequestPinConfirmationActivity(int i, int i2) {
        if (!this.mService.areShortcutsSupportedOnHomeScreen(i)) {
            return null;
        }
        int parentOrSelfUserId = this.mService.getParentOrSelfUserId(i);
        java.lang.String defaultLauncher = this.mService.getDefaultLauncher(parentOrSelfUserId);
        if (defaultLauncher == null) {
            android.util.Log.e(TAG, "Default launcher not found.");
            return null;
        }
        android.content.ComponentName injectGetPinConfirmationActivity = this.mService.injectGetPinConfirmationActivity(defaultLauncher, parentOrSelfUserId, i2);
        if (injectGetPinConfirmationActivity == null) {
            return null;
        }
        return android.util.Pair.create(injectGetPinConfirmationActivity, java.lang.Integer.valueOf(parentOrSelfUserId));
    }

    public void sendResultIntent(@android.annotation.Nullable android.content.IntentSender intentSender, @android.annotation.Nullable android.content.Intent intent) {
        this.mService.injectSendIntentSender(intentSender, intent);
    }

    public boolean isCallerUid(int i) {
        return i == this.mService.injectBinderCallingUid();
    }

    public boolean directPinShortcut(com.android.server.pm.ShortcutRequestPinProcessor.PinShortcutRequestInner pinShortcutRequestInner) {
        android.content.pm.ShortcutInfo shortcutInfo = pinShortcutRequestInner.shortcutOriginal;
        int userId = shortcutInfo.getUserId();
        java.lang.String str = shortcutInfo.getPackage();
        int i = pinShortcutRequestInner.launcherUserId;
        java.lang.String str2 = pinShortcutRequestInner.launcherPackage;
        java.lang.String id = shortcutInfo.getId();
        synchronized (this.mLock) {
            try {
                if (this.mService.isUserUnlockedL(userId) && this.mService.isUserUnlockedL(pinShortcutRequestInner.launcherUserId)) {
                    com.android.server.pm.ShortcutLauncher launcherShortcutsLocked = this.mService.getLauncherShortcutsLocked(str2, userId, i);
                    launcherShortcutsLocked.attemptToRestoreIfNeededAndSave();
                    if (launcherShortcutsLocked.hasPinned(shortcutInfo)) {
                        return true;
                    }
                    com.android.server.pm.ShortcutPackage packageShortcutsForPublisherLocked = this.mService.getPackageShortcutsForPublisherLocked(str, userId);
                    android.content.pm.ShortcutInfo findShortcutById = packageShortcutsForPublisherLocked.findShortcutById(id);
                    try {
                        if (findShortcutById == null) {
                            this.mService.validateShortcutForPinRequest(shortcutInfo);
                        } else {
                            validateExistingShortcut(findShortcutById);
                        }
                        if (findShortcutById == null) {
                            if (shortcutInfo.getActivity() == null) {
                                shortcutInfo.setActivity(this.mService.getDummyMainActivity(str));
                            }
                            packageShortcutsForPublisherLocked.addOrReplaceDynamicShortcut(shortcutInfo);
                        }
                        launcherShortcutsLocked.addPinnedShortcut(str, userId, id, true);
                        if (findShortcutById == null) {
                            packageShortcutsForPublisherLocked.deleteDynamicWithId(id, false, false);
                        }
                        packageShortcutsForPublisherLocked.adjustRanks();
                        java.util.List<android.content.pm.ShortcutInfo> singletonList = java.util.Collections.singletonList(packageShortcutsForPublisherLocked.findShortcutById(id));
                        this.mService.verifyStates();
                        this.mService.packageShortcutsChanged(packageShortcutsForPublisherLocked, singletonList, null);
                        return true;
                    } catch (java.lang.RuntimeException e) {
                        android.util.Log.w(TAG, "Unable to pin shortcut: " + e.getMessage());
                        return false;
                    }
                }
                android.util.Log.w(TAG, "User is locked now.");
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
