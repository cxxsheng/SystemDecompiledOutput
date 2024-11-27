package com.android.server.translation;

/* loaded from: classes2.dex */
final class TranslationManagerServiceImpl extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.translation.TranslationManagerServiceImpl, com.android.server.translation.TranslationManagerService> implements android.os.IBinder.DeathRecipient {

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<android.os.IBinder, com.android.server.translation.TranslationManagerServiceImpl.ActiveTranslation> mActiveTranslations;
    private final com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManagerInternal;
    private final android.os.RemoteCallbackList<android.os.IRemoteCallback> mCallbacks;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.ref.WeakReference<com.android.server.wm.ActivityTaskManagerInternal.ActivityTokens> mLastActivityTokens;
    private final com.android.server.translation.TranslationManagerServiceImpl.TranslationServiceRemoteCallback mRemoteServiceCallback;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.translation.RemoteTranslationService mRemoteTranslationService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.content.pm.ServiceInfo mRemoteTranslationServiceInfo;
    private final android.os.RemoteCallbackList<android.os.IRemoteCallback> mTranslationCapabilityCallbacks;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.service.translation.TranslationServiceInfo mTranslationServiceInfo;
    private final android.util.ArraySet<android.os.IBinder> mWaitingFinishedCallbackActivities;
    private static final java.lang.String TAG = "TranslationManagerServiceImpl";

    @android.annotation.SuppressLint({"IsLoggableTagLength"})
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    protected TranslationManagerServiceImpl(@android.annotation.NonNull com.android.server.translation.TranslationManagerService translationManagerService, @android.annotation.NonNull java.lang.Object obj, int i, boolean z) {
        super(translationManagerService, obj, i);
        this.mRemoteServiceCallback = new com.android.server.translation.TranslationManagerServiceImpl.TranslationServiceRemoteCallback();
        this.mTranslationCapabilityCallbacks = new android.os.RemoteCallbackList<>();
        this.mWaitingFinishedCallbackActivities = new android.util.ArraySet<>();
        this.mActiveTranslations = new android.util.ArrayMap<>();
        this.mCallbacks = new android.os.RemoteCallbackList<>();
        updateRemoteServiceLocked();
        this.mActivityTaskManagerInternal = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        this.mTranslationServiceInfo = new android.service.translation.TranslationServiceInfo(getContext(), componentName, isTemporaryServiceSetLocked(), this.mUserId);
        this.mRemoteTranslationServiceInfo = this.mTranslationServiceInfo.getServiceInfo();
        return this.mTranslationServiceInfo.getServiceInfo();
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected boolean updateLocked(boolean z) {
        boolean updateLocked = super.updateLocked(z);
        updateRemoteServiceLocked();
        return updateLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateRemoteServiceLocked() {
        if (this.mRemoteTranslationService != null) {
            if (((com.android.server.translation.TranslationManagerService) this.mMaster).debug) {
                android.util.Slog.d(TAG, "updateRemoteService(): destroying old remote service");
            }
            this.mRemoteTranslationService.unbind();
            this.mRemoteTranslationService = null;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private com.android.server.translation.RemoteTranslationService ensureRemoteServiceLocked() {
        if (this.mRemoteTranslationService == null) {
            java.lang.String componentNameLocked = getComponentNameLocked();
            if (componentNameLocked == null) {
                if (((com.android.server.translation.TranslationManagerService) this.mMaster).verbose) {
                    android.util.Slog.v(TAG, "ensureRemoteServiceLocked(): no service component name.");
                }
                return null;
            }
            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(componentNameLocked);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                boolean isServiceAvailableForUser = isServiceAvailableForUser(unflattenFromString);
                if (((com.android.server.translation.TranslationManagerService) this.mMaster).verbose) {
                    android.util.Slog.v(TAG, "ensureRemoteServiceLocked(): isServiceAvailableForUser=" + isServiceAvailableForUser);
                }
                if (!isServiceAvailableForUser) {
                    android.util.Slog.w(TAG, "ensureRemoteServiceLocked(): " + unflattenFromString + " is not available,");
                    return null;
                }
                this.mRemoteTranslationService = new com.android.server.translation.RemoteTranslationService(getContext(), unflattenFromString, this.mUserId, false, this.mRemoteServiceCallback);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        return this.mRemoteTranslationService;
    }

    private boolean isServiceAvailableForUser(android.content.ComponentName componentName) {
        android.content.pm.ResolveInfo resolveServiceAsUser = getContext().getPackageManager().resolveServiceAsUser(new android.content.Intent("android.service.translation.TranslationService").setComponent(componentName), 132, this.mUserId);
        return (resolveServiceAsUser == null || resolveServiceAsUser.serviceInfo == null) ? false : true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onTranslationCapabilitiesRequestLocked(int i, int i2, @android.annotation.NonNull android.os.ResultReceiver resultReceiver) {
        com.android.server.translation.RemoteTranslationService ensureRemoteServiceLocked = ensureRemoteServiceLocked();
        if (ensureRemoteServiceLocked != null) {
            ensureRemoteServiceLocked.onTranslationCapabilitiesRequest(i, i2, resultReceiver);
        } else {
            android.util.Slog.v(TAG, "onTranslationCapabilitiesRequestLocked(): no remote service.");
            resultReceiver.send(2, null);
        }
    }

    public void registerTranslationCapabilityCallback(android.os.IRemoteCallback iRemoteCallback, int i) {
        this.mTranslationCapabilityCallbacks.register(iRemoteCallback, java.lang.Integer.valueOf(i));
        ensureRemoteServiceLocked();
    }

    public void unregisterTranslationCapabilityCallback(android.os.IRemoteCallback iRemoteCallback) {
        this.mTranslationCapabilityCallbacks.unregister(iRemoteCallback);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    void onSessionCreatedLocked(@android.annotation.NonNull android.view.translation.TranslationContext translationContext, int i, com.android.internal.os.IResultReceiver iResultReceiver) throws android.os.RemoteException {
        com.android.server.translation.RemoteTranslationService ensureRemoteServiceLocked = ensureRemoteServiceLocked();
        if (ensureRemoteServiceLocked != null) {
            ensureRemoteServiceLocked.onSessionCreated(translationContext, i, iResultReceiver);
        } else {
            android.util.Slog.v(TAG, "onSessionCreatedLocked(): no remote service.");
            iResultReceiver.send(2, (android.os.Bundle) null);
        }
    }

    private int getAppUidByComponentName(android.content.Context context, android.content.ComponentName componentName, int i) {
        if (componentName == null) {
            return -1;
        }
        try {
            return context.getPackageManager().getApplicationInfoAsUser(componentName.getPackageName(), 0, i).uid;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.d(TAG, "Cannot find packageManager for" + componentName);
            return -1;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onTranslationFinishedLocked(boolean z, android.os.IBinder iBinder, android.content.ComponentName componentName) {
        int appUidByComponentName = getAppUidByComponentName(getContext(), componentName, getUserId());
        java.lang.String packageName = componentName.getPackageName();
        if (z || this.mWaitingFinishedCallbackActivities.contains(iBinder)) {
            invokeCallbacks(3, null, null, packageName, appUidByComponentName);
            this.mWaitingFinishedCallbackActivities.remove(iBinder);
            this.mActiveTranslations.remove(iBinder);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateUiTranslationStateLocked(int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.os.IBinder iBinder, int i2, android.view.translation.UiTranslationSpec uiTranslationSpec) {
        com.android.server.wm.ActivityTaskManagerInternal.ActivityTokens attachedNonFinishingActivityForTask = this.mActivityTaskManagerInternal.getAttachedNonFinishingActivityForTask(i2, iBinder);
        if (attachedNonFinishingActivityForTask != null) {
            this.mLastActivityTokens = new java.lang.ref.WeakReference<>(attachedNonFinishingActivityForTask);
            if (i == 3) {
                this.mWaitingFinishedCallbackActivities.add(iBinder);
            }
            android.os.IBinder activityToken = attachedNonFinishingActivityForTask.getActivityToken();
            try {
                attachedNonFinishingActivityForTask.getApplicationThread().updateUiTranslationState(activityToken, i, translationSpec, translationSpec2, list, uiTranslationSpec);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Update UiTranslationState fail: " + e);
            }
            android.content.ComponentName activityName = this.mActivityTaskManagerInternal.getActivityName(activityToken);
            int appUidByComponentName = getAppUidByComponentName(getContext(), activityName, getUserId());
            java.lang.String packageName = activityName.getPackageName();
            invokeCallbacksIfNecessaryLocked(i, translationSpec, translationSpec2, packageName, iBinder, appUidByComponentName);
            updateActiveTranslationsLocked(i, translationSpec, translationSpec2, packageName, iBinder, appUidByComponentName);
            return;
        }
        android.util.Slog.w(TAG, "Unknown activity or it was finished to query for update translation state for token=" + iBinder + " taskId=" + i2 + " for state= " + i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateActiveTranslationsLocked(int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.lang.String str, android.os.IBinder iBinder, int i2) {
        com.android.server.translation.TranslationManagerServiceImpl.ActiveTranslation activeTranslation = this.mActiveTranslations.get(iBinder);
        switch (i) {
            case 0:
                if (activeTranslation == null) {
                    try {
                        iBinder.linkToDeath(this, 0);
                        this.mActiveTranslations.put(iBinder, new com.android.server.translation.TranslationManagerServiceImpl.ActiveTranslation(translationSpec, translationSpec2, i2, str));
                        break;
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Failed to call linkToDeath for translated app with uid=" + i2 + "; activity is already dead", e);
                        invokeCallbacks(3, translationSpec, translationSpec2, str, i2);
                        return;
                    }
                }
                break;
            case 1:
                if (activeTranslation != null) {
                    activeTranslation.isPaused = true;
                    break;
                }
                break;
            case 2:
                if (activeTranslation != null) {
                    activeTranslation.isPaused = false;
                    break;
                }
                break;
            case 3:
                if (activeTranslation != null) {
                    this.mActiveTranslations.remove(iBinder);
                    break;
                }
                break;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Updating to translation state=" + i + " for app with uid=" + i2 + " packageName=" + str);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void invokeCallbacksIfNecessaryLocked(int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.lang.String str, android.os.IBinder iBinder, int i2) {
        int i3;
        com.android.server.translation.TranslationManagerServiceImpl.ActiveTranslation activeTranslation = this.mActiveTranslations.get(iBinder);
        boolean z = false;
        if (activeTranslation == null) {
            if (i != 0) {
                android.util.Slog.w(TAG, "Updating to translation state=" + i + " for app with uid=" + i2 + " packageName=" + str + " but no active translation was found for it");
                i3 = i;
            }
            i3 = i;
            z = true;
        } else {
            switch (i) {
                case 0:
                    if (activeTranslation.sourceSpec.getLocale().equals(translationSpec.getLocale()) && activeTranslation.targetSpec.getLocale().equals(translationSpec2.getLocale())) {
                        if (activeTranslation.isPaused) {
                            i3 = 2;
                            z = true;
                            break;
                        } else {
                            i3 = i;
                            break;
                        }
                    }
                    i3 = i;
                    z = true;
                    break;
                case 1:
                    if (activeTranslation.isPaused) {
                        i3 = i;
                        break;
                    }
                    i3 = i;
                    z = true;
                    break;
                case 2:
                    if (!activeTranslation.isPaused) {
                        i3 = i;
                        break;
                    }
                    i3 = i;
                    z = true;
                    break;
                case 3:
                    i3 = i;
                    break;
                default:
                    i3 = i;
                    z = true;
                    break;
            }
        }
        if (z) {
            invokeCallbacks(i3, translationSpec, translationSpec2, str, i2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpLocked(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter) {
        if (this.mLastActivityTokens != null) {
            com.android.server.wm.ActivityTaskManagerInternal.ActivityTokens activityTokens = this.mLastActivityTokens.get();
            if (activityTokens == null) {
                return;
            }
            try {
                com.android.internal.os.TransferPipe transferPipe = new com.android.internal.os.TransferPipe();
                try {
                    activityTokens.getApplicationThread().dumpActivity(transferPipe.getWriteFd(), activityTokens.getActivityToken(), str, new java.lang.String[]{"--dump-dumpable", "UiTranslationController"});
                    transferPipe.go(fileDescriptor);
                    transferPipe.close();
                } finally {
                }
            } catch (android.os.RemoteException e) {
                printWriter.println(str + "Got a RemoteException while dumping the activity");
            } catch (java.io.IOException e2) {
                printWriter.println(str + "Failure while dumping the activity: " + e2);
            }
        } else {
            printWriter.print(str);
            printWriter.println("No requested UiTranslation Activity.");
        }
        int size = this.mWaitingFinishedCallbackActivities.size();
        if (size > 0) {
            printWriter.print(str);
            printWriter.print("number waiting finish callback activities: ");
            printWriter.println(size);
            java.util.Iterator<android.os.IBinder> it = this.mWaitingFinishedCallbackActivities.iterator();
            while (it.hasNext()) {
                android.os.IBinder next = it.next();
                printWriter.print(str);
                printWriter.print("shareableActivityToken: ");
                printWriter.println(next);
            }
        }
    }

    private void invokeCallbacks(int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.lang.String str, final int i2) {
        final android.os.Bundle createResultForCallback = createResultForCallback(i, translationSpec, translationSpec2, str);
        int registeredCallbackCount = this.mCallbacks.getRegisteredCallbackCount();
        if (DEBUG) {
            android.util.Slog.d(TAG, "Invoking " + registeredCallbackCount + " callbacks for translation state=" + i + " for app with uid=" + i2 + " packageName=" + str);
        }
        if (registeredCallbackCount == 0) {
            return;
        }
        final java.util.List<android.view.inputmethod.InputMethodInfo> enabledInputMethods = getEnabledInputMethods();
        this.mCallbacks.broadcast(new java.util.function.BiConsumer() { // from class: com.android.server.translation.TranslationManagerServiceImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.translation.TranslationManagerServiceImpl.this.lambda$invokeCallbacks$0(i2, createResultForCallback, enabledInputMethods, (android.os.IRemoteCallback) obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$invokeCallbacks$0(int i, android.os.Bundle bundle, java.util.List list, android.os.IRemoteCallback iRemoteCallback, java.lang.Object obj) {
        invokeCallback(((java.lang.Integer) obj).intValue(), i, iRemoteCallback, bundle, list);
    }

    private java.util.List<android.view.inputmethod.InputMethodInfo> getEnabledInputMethods() {
        return ((com.android.server.inputmethod.InputMethodManagerInternal) com.android.server.LocalServices.getService(com.android.server.inputmethod.InputMethodManagerInternal.class)).getEnabledInputMethodListAsUser(this.mUserId);
    }

    private android.os.Bundle createResultForCallback(int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.lang.String str) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("state", i);
        if (translationSpec != null) {
            bundle.putSerializable("source_locale", translationSpec.getLocale());
            bundle.putSerializable("target_locale", translationSpec2.getLocale());
        }
        bundle.putString("package_name", str);
        return bundle;
    }

    private void invokeCallback(int i, int i2, android.os.IRemoteCallback iRemoteCallback, android.os.Bundle bundle, java.util.List<android.view.inputmethod.InputMethodInfo> list) {
        boolean z;
        if (i == i2) {
            try {
                iRemoteCallback.sendResult(bundle);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed to invoke UiTranslationStateCallback: " + e);
                return;
            }
        }
        java.util.Iterator<android.view.inputmethod.InputMethodInfo> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            } else if (i == it.next().getServiceInfo().applicationInfo.uid) {
                z = true;
                break;
            }
        }
        if (!z) {
            return;
        }
        try {
            iRemoteCallback.sendResult(bundle);
        } catch (android.os.RemoteException e2) {
            android.util.Slog.w(TAG, "Failed to invoke UiTranslationStateCallback: " + e2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void registerUiTranslationStateCallbackLocked(android.os.IRemoteCallback iRemoteCallback, int i) {
        this.mCallbacks.register(iRemoteCallback, java.lang.Integer.valueOf(i));
        int size = this.mActiveTranslations.size();
        android.util.Slog.i(TAG, "New registered callback for sourceUid=" + i + " with currently " + size + " active translations");
        if (size == 0) {
            return;
        }
        java.util.List<android.view.inputmethod.InputMethodInfo> enabledInputMethods = getEnabledInputMethods();
        for (int i2 = 0; i2 < this.mActiveTranslations.size(); i2++) {
            com.android.server.translation.TranslationManagerServiceImpl.ActiveTranslation valueAt = this.mActiveTranslations.valueAt(i2);
            int i3 = valueAt.translatedAppUid;
            java.lang.String str = valueAt.packageName;
            if (DEBUG) {
                android.util.Slog.d(TAG, "Triggering callback for sourceUid=" + i + " for translated app with uid=" + i3 + "packageName=" + str + " isPaused=" + valueAt.isPaused);
            }
            invokeCallback(i, i3, iRemoteCallback, createResultForCallback(0, valueAt.sourceSpec, valueAt.targetSpec, str), enabledInputMethods);
            if (valueAt.isPaused) {
                invokeCallback(i, i3, iRemoteCallback, createResultForCallback(1, valueAt.sourceSpec, valueAt.targetSpec, str), enabledInputMethods);
            }
        }
    }

    public void unregisterUiTranslationStateCallback(android.os.IRemoteCallback iRemoteCallback) {
        this.mCallbacks.unregister(iRemoteCallback);
    }

    public android.content.ComponentName getServiceSettingsActivityLocked() {
        java.lang.String settingsActivity;
        if (this.mTranslationServiceInfo == null || (settingsActivity = this.mTranslationServiceInfo.getSettingsActivity()) == null) {
            return null;
        }
        return new android.content.ComponentName(this.mTranslationServiceInfo.getServiceInfo().packageName, settingsActivity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyClientsTranslationCapability(android.view.translation.TranslationCapability translationCapability) {
        final android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable("translation_capabilities", translationCapability);
        this.mTranslationCapabilityCallbacks.broadcast(new java.util.function.BiConsumer() { // from class: com.android.server.translation.TranslationManagerServiceImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.translation.TranslationManagerServiceImpl.lambda$notifyClientsTranslationCapability$1(bundle, (android.os.IRemoteCallback) obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$notifyClientsTranslationCapability$1(android.os.Bundle bundle, android.os.IRemoteCallback iRemoteCallback, java.lang.Object obj) {
        try {
            iRemoteCallback.sendResult(bundle);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to invoke UiTranslationStateCallback: " + e);
        }
    }

    private final class TranslationServiceRemoteCallback extends android.view.translation.ITranslationServiceCallback.Stub {
        private TranslationServiceRemoteCallback() {
        }

        public void updateTranslationCapability(android.view.translation.TranslationCapability translationCapability) {
            if (translationCapability == null) {
                android.util.Slog.wtf(com.android.server.translation.TranslationManagerServiceImpl.TAG, "received a null TranslationCapability from TranslationService.");
            } else {
                com.android.server.translation.TranslationManagerServiceImpl.this.notifyClientsTranslationCapability(translationCapability);
            }
        }
    }

    private static final class ActiveTranslation {
        public boolean isPaused;
        public final java.lang.String packageName;
        public final android.view.translation.TranslationSpec sourceSpec;
        public final android.view.translation.TranslationSpec targetSpec;
        public final int translatedAppUid;

        private ActiveTranslation(android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, int i, java.lang.String str) {
            this.isPaused = false;
            this.sourceSpec = translationSpec;
            this.targetSpec = translationSpec2;
            this.translatedAppUid = i;
            this.packageName = str;
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied(android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                this.mWaitingFinishedCallbackActivities.remove(iBinder);
                com.android.server.translation.TranslationManagerServiceImpl.ActiveTranslation remove = this.mActiveTranslations.remove(iBinder);
                if (remove != null) {
                    invokeCallbacks(3, remove.sourceSpec, remove.targetSpec, remove.packageName, remove.translatedAppUid);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
