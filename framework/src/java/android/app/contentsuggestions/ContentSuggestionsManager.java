package android.app.contentsuggestions;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class ContentSuggestionsManager {
    public static final java.lang.String EXTRA_BITMAP = "android.contentsuggestions.extra.BITMAP";
    private static final int SYNC_CALLS_TIMEOUT_MS = 5000;
    private static final java.lang.String TAG = android.app.contentsuggestions.ContentSuggestionsManager.class.getSimpleName();
    private final android.app.contentsuggestions.IContentSuggestionsManager mService;
    private final int mUser;

    public interface ClassificationsCallback {
        void onContentClassificationsAvailable(int i, java.util.List<android.app.contentsuggestions.ContentClassification> list);
    }

    public interface SelectionsCallback {
        void onContentSelectionsAvailable(int i, java.util.List<android.app.contentsuggestions.ContentSelection> list);
    }

    public ContentSuggestionsManager(int i, android.app.contentsuggestions.IContentSuggestionsManager iContentSuggestionsManager) {
        this.mService = iContentSuggestionsManager;
        this.mUser = i;
    }

    public void provideContextImage(android.graphics.Bitmap bitmap, android.os.Bundle bundle) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "provideContextImage called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            this.mService.provideContextBitmap(this.mUser, bitmap, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void provideContextImage(int i, android.os.Bundle bundle) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "provideContextImage called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            this.mService.provideContextImage(this.mUser, i, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void suggestContentSelections(android.app.contentsuggestions.SelectionsRequest selectionsRequest, java.util.concurrent.Executor executor, android.app.contentsuggestions.ContentSuggestionsManager.SelectionsCallback selectionsCallback) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "suggestContentSelections called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            this.mService.suggestContentSelections(this.mUser, selectionsRequest, new android.app.contentsuggestions.ContentSuggestionsManager.SelectionsCallbackWrapper(selectionsCallback, executor));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void classifyContentSelections(android.app.contentsuggestions.ClassificationsRequest classificationsRequest, java.util.concurrent.Executor executor, android.app.contentsuggestions.ContentSuggestionsManager.ClassificationsCallback classificationsCallback) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "classifyContentSelections called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            this.mService.classifyContentSelections(this.mUser, classificationsRequest, new android.app.contentsuggestions.ContentSuggestionsManager.ClassificationsCallbackWrapper(classificationsCallback, executor));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyInteraction(java.lang.String str, android.os.Bundle bundle) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "notifyInteraction called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            this.mService.notifyInteraction(this.mUser, str, bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isEnabled() {
        if (this.mService == null) {
            return false;
        }
        com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
        try {
            this.mService.isEnabled(this.mUser, syncResultReceiver);
            return syncResultReceiver.getIntResult() != 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            throw new java.lang.RuntimeException("Fail to get the enable status.");
        }
    }

    public void resetTemporaryService(int i) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "resetTemporaryService called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            this.mService.resetTemporaryService(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTemporaryService(int i, java.lang.String str, int i2) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setTemporaryService called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            this.mService.setTemporaryService(i, str, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDefaultServiceEnabled(int i, boolean z) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setDefaultServiceEnabled called, but no ContentSuggestionsManager configured");
            return;
        }
        try {
            this.mService.setDefaultServiceEnabled(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SelectionsCallbackWrapper extends android.app.contentsuggestions.ISelectionsCallback.Stub {
        private final android.app.contentsuggestions.ContentSuggestionsManager.SelectionsCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        SelectionsCallbackWrapper(android.app.contentsuggestions.ContentSuggestionsManager.SelectionsCallback selectionsCallback, java.util.concurrent.Executor executor) {
            this.mCallback = selectionsCallback;
            this.mExecutor = executor;
        }

        @Override // android.app.contentsuggestions.ISelectionsCallback
        public void onContentSelectionsAvailable(final int i, final java.util.List<android.app.contentsuggestions.ContentSelection> list) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.contentsuggestions.ContentSuggestionsManager$SelectionsCallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.contentsuggestions.ContentSuggestionsManager.SelectionsCallbackWrapper.this.lambda$onContentSelectionsAvailable$0(i, list);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onContentSelectionsAvailable$0(int i, java.util.List list) {
            this.mCallback.onContentSelectionsAvailable(i, list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class ClassificationsCallbackWrapper extends android.app.contentsuggestions.IClassificationsCallback.Stub {
        private final android.app.contentsuggestions.ContentSuggestionsManager.ClassificationsCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        ClassificationsCallbackWrapper(android.app.contentsuggestions.ContentSuggestionsManager.ClassificationsCallback classificationsCallback, java.util.concurrent.Executor executor) {
            this.mCallback = classificationsCallback;
            this.mExecutor = executor;
        }

        @Override // android.app.contentsuggestions.IClassificationsCallback
        public void onContentClassificationsAvailable(final int i, final java.util.List<android.app.contentsuggestions.ContentClassification> list) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.contentsuggestions.ContentSuggestionsManager$ClassificationsCallbackWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.contentsuggestions.ContentSuggestionsManager.ClassificationsCallbackWrapper.this.lambda$onContentClassificationsAvailable$0(i, list);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onContentClassificationsAvailable$0(int i, java.util.List list) {
            this.mCallback.onContentClassificationsAvailable(i, list);
        }
    }
}
