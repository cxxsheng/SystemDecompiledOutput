package android.view.translation;

/* loaded from: classes4.dex */
public final class UiTranslationManager {
    public static final java.lang.String EXTRA_PACKAGE_NAME = "package_name";
    public static final java.lang.String EXTRA_SOURCE_LOCALE = "source_locale";
    public static final java.lang.String EXTRA_STATE = "state";
    public static final java.lang.String EXTRA_TARGET_LOCALE = "target_locale";
    public static final java.lang.String LOG_TAG = "UiTranslation";
    public static final int STATE_UI_TRANSLATION_FINISHED = 3;
    public static final int STATE_UI_TRANSLATION_PAUSED = 1;
    public static final int STATE_UI_TRANSLATION_RESUMED = 2;
    public static final int STATE_UI_TRANSLATION_STARTED = 0;
    private static final java.lang.String TAG = "UiTranslationManager";
    private final java.util.Map<android.view.translation.UiTranslationStateCallback, android.os.IRemoteCallback> mCallbacks = new android.util.ArrayMap();
    private final android.content.Context mContext;
    private final android.view.translation.ITranslationManager mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UiTranslationState {
    }

    public UiTranslationManager(android.content.Context context, android.view.translation.ITranslationManager iTranslationManager) {
        this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
        this.mService = iTranslationManager;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void startTranslation(android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.app.assist.ActivityId activityId) {
        startTranslation(translationSpec, translationSpec2, list, activityId, new android.view.translation.UiTranslationSpec.Builder().setShouldPadContentForCompat(true).build());
    }

    @android.annotation.SystemApi
    public void startTranslation(android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.app.assist.ActivityId activityId, android.view.translation.UiTranslationSpec uiTranslationSpec) {
        java.util.Objects.requireNonNull(translationSpec);
        java.util.Objects.requireNonNull(translationSpec2);
        java.util.Objects.requireNonNull(list);
        java.util.Objects.requireNonNull(activityId);
        java.util.Objects.requireNonNull(activityId.getToken());
        java.util.Objects.requireNonNull(uiTranslationSpec);
        if (list.size() == 0) {
            throw new java.lang.IllegalArgumentException("Invalid empty views: " + list);
        }
        try {
            this.mService.updateUiTranslationState(0, translationSpec, translationSpec2, list, activityId.getToken(), activityId.getTaskId(), uiTranslationSpec, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void finishTranslation(android.app.assist.ActivityId activityId) {
        try {
            java.util.Objects.requireNonNull(activityId);
            java.util.Objects.requireNonNull(activityId.getToken());
            this.mService.updateUiTranslationState(3, null, null, null, activityId.getToken(), activityId.getTaskId(), null, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void pauseTranslation(android.app.assist.ActivityId activityId) {
        try {
            java.util.Objects.requireNonNull(activityId);
            java.util.Objects.requireNonNull(activityId.getToken());
            this.mService.updateUiTranslationState(1, null, null, null, activityId.getToken(), activityId.getTaskId(), null, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void resumeTranslation(android.app.assist.ActivityId activityId) {
        try {
            java.util.Objects.requireNonNull(activityId);
            java.util.Objects.requireNonNull(activityId.getToken());
            this.mService.updateUiTranslationState(2, null, null, null, activityId.getToken(), activityId.getTaskId(), null, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerUiTranslationStateCallback(java.util.concurrent.Executor executor, android.view.translation.UiTranslationStateCallback uiTranslationStateCallback) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(uiTranslationStateCallback);
        synchronized (this.mCallbacks) {
            if (this.mCallbacks.containsKey(uiTranslationStateCallback)) {
                android.util.Log.w(TAG, "registerUiTranslationStateCallback: callback already registered; ignoring.");
                return;
            }
            android.view.translation.UiTranslationManager.UiTranslationStateRemoteCallback uiTranslationStateRemoteCallback = new android.view.translation.UiTranslationManager.UiTranslationStateRemoteCallback(executor, uiTranslationStateCallback);
            try {
                this.mService.registerUiTranslationStateCallback(uiTranslationStateRemoteCallback, this.mContext.getUserId());
                this.mCallbacks.put(uiTranslationStateCallback, uiTranslationStateRemoteCallback);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterUiTranslationStateCallback(android.view.translation.UiTranslationStateCallback uiTranslationStateCallback) {
        java.util.Objects.requireNonNull(uiTranslationStateCallback);
        synchronized (this.mCallbacks) {
            android.os.IRemoteCallback iRemoteCallback = this.mCallbacks.get(uiTranslationStateCallback);
            if (iRemoteCallback == null) {
                android.util.Log.w(TAG, "unregisterUiTranslationStateCallback: callback not found; ignoring.");
                return;
            }
            try {
                this.mService.unregisterUiTranslationStateCallback(iRemoteCallback, this.mContext.getUserId());
                this.mCallbacks.remove(uiTranslationStateCallback);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void onTranslationFinished(boolean z, android.app.assist.ActivityId activityId, android.content.ComponentName componentName) {
        try {
            this.mService.onTranslationFinished(z, activityId.getToken(), componentName, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class UiTranslationStateRemoteCallback extends android.os.IRemoteCallback.Stub {
        private final android.view.translation.UiTranslationStateCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private android.icu.util.ULocale mSourceLocale;
        private android.icu.util.ULocale mTargetLocale;

        UiTranslationStateRemoteCallback(java.util.concurrent.Executor executor, android.view.translation.UiTranslationStateCallback uiTranslationStateCallback) {
            this.mExecutor = executor;
            this.mCallback = uiTranslationStateCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendResult$1(final android.os.Bundle bundle) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.view.translation.UiTranslationManager$UiTranslationStateRemoteCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.translation.UiTranslationManager.UiTranslationStateRemoteCallback.this.lambda$sendResult$0(bundle);
                }
            });
        }

        @Override // android.os.IRemoteCallback
        public void sendResult(final android.os.Bundle bundle) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.view.translation.UiTranslationManager$UiTranslationStateRemoteCallback$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.view.translation.UiTranslationManager.UiTranslationStateRemoteCallback.this.lambda$sendResult$1(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: onStateChange, reason: merged with bridge method [inline-methods] */
        public void lambda$sendResult$0(android.os.Bundle bundle) {
            int i = bundle.getInt("state");
            java.lang.String string = bundle.getString("package_name");
            switch (i) {
                case 0:
                    this.mSourceLocale = (android.icu.util.ULocale) bundle.getSerializable(android.view.translation.UiTranslationManager.EXTRA_SOURCE_LOCALE, android.icu.util.ULocale.class);
                    this.mTargetLocale = (android.icu.util.ULocale) bundle.getSerializable(android.view.translation.UiTranslationManager.EXTRA_TARGET_LOCALE, android.icu.util.ULocale.class);
                    this.mCallback.onStarted(this.mSourceLocale, this.mTargetLocale, string);
                    break;
                case 1:
                    this.mCallback.onPaused(string);
                    break;
                case 2:
                    this.mCallback.onResumed(this.mSourceLocale, this.mTargetLocale, string);
                    break;
                case 3:
                    this.mCallback.onFinished(string);
                    break;
                default:
                    android.util.Log.wtf(android.view.translation.UiTranslationManager.TAG, "Unexpected translation state:" + i);
                    break;
            }
        }
    }
}
