package com.android.server.textclassifier;

/* loaded from: classes2.dex */
public final class TextClassificationManagerService extends android.service.textclassifier.ITextClassifierService.Stub {
    private static final boolean DEBUG = false;
    private static final java.lang.String LOG_TAG = "TextClassificationManagerService";
    private static final android.service.textclassifier.ITextClassifierCallback NO_OP_CALLBACK = new android.service.textclassifier.ITextClassifierCallback() { // from class: com.android.server.textclassifier.TextClassificationManagerService.1
        public void onSuccess(android.os.Bundle bundle) {
        }

        public void onFailure() {
        }

        public android.os.IBinder asBinder() {
            return null;
        }
    };
    private final android.content.Context mContext;

    @android.annotation.Nullable
    private final java.lang.String mDefaultTextClassifierPackage;
    private final java.lang.Object mLock;
    private final com.android.server.textclassifier.TextClassificationManagerService.SessionCache mSessionCache;
    private final android.view.textclassifier.TextClassificationConstants mSettings;
    private final com.android.server.textclassifier.TextClassificationManagerService.TextClassifierSettingsListener mSettingsListener;

    @android.annotation.Nullable
    private final java.lang.String mSystemTextClassifierPackage;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.util.SparseArray<com.android.server.textclassifier.TextClassificationManagerService.UserState> mUserStates;

    public static final class Lifecycle extends com.android.server.SystemService {
        private final com.android.server.textclassifier.TextClassificationManagerService mManagerService;

        public Lifecycle(android.content.Context context) {
            super(context);
            this.mManagerService = new com.android.server.textclassifier.TextClassificationManagerService(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            try {
                publishBinderService("textclassification", this.mManagerService);
                this.mManagerService.startListenSettings();
                this.mManagerService.startTrackingPackageChanges();
            } catch (java.lang.Throwable th) {
                android.util.Slog.e(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, "Could not start the TextClassificationManagerService.", th);
            }
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            updatePackageStateForUser(targetUser.getUserIdentifier());
            processAnyPendingWork(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            updatePackageStateForUser(targetUser.getUserIdentifier());
            processAnyPendingWork(targetUser.getUserIdentifier());
        }

        private void processAnyPendingWork(int i) {
            synchronized (this.mManagerService.mLock) {
                this.mManagerService.getUserStateLocked(i).bindIfHasPendingRequestsLocked();
            }
        }

        private void updatePackageStateForUser(int i) {
            synchronized (this.mManagerService.mLock) {
                this.mManagerService.getUserStateLocked(i).updatePackageStateLocked();
            }
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            int userIdentifier = targetUser.getUserIdentifier();
            synchronized (this.mManagerService.mLock) {
                try {
                    com.android.server.textclassifier.TextClassificationManagerService.UserState peekUserStateLocked = this.mManagerService.peekUserStateLocked(userIdentifier);
                    if (peekUserStateLocked != null) {
                        peekUserStateLocked.cleanupServiceLocked();
                        this.mManagerService.mUserStates.remove(userIdentifier);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private TextClassificationManagerService(android.content.Context context) {
        this.mUserStates = new android.util.SparseArray<>();
        java.util.Objects.requireNonNull(context);
        this.mContext = context;
        this.mLock = new java.lang.Object();
        this.mSettings = new android.view.textclassifier.TextClassificationConstants();
        this.mSettingsListener = new com.android.server.textclassifier.TextClassificationManagerService.TextClassifierSettingsListener(this.mContext);
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        this.mDefaultTextClassifierPackage = packageManager.getDefaultTextClassifierPackageName();
        this.mSystemTextClassifierPackage = packageManager.getSystemTextClassifierPackageName();
        this.mSessionCache = new com.android.server.textclassifier.TextClassificationManagerService.SessionCache(this.mLock);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startListenSettings() {
        this.mSettingsListener.registerObserver();
    }

    void startTrackingPackageChanges() {
        new com.android.internal.content.PackageMonitor() { // from class: com.android.server.textclassifier.TextClassificationManagerService.2
            public void onPackageAdded(java.lang.String str, int i) {
                notifyPackageInstallStatusChange(str, true);
            }

            public void onPackageRemoved(java.lang.String str, int i) {
                notifyPackageInstallStatusChange(str, false);
            }

            public void onPackageModified(java.lang.String str) {
                int changingUserId = getChangingUserId();
                synchronized (com.android.server.textclassifier.TextClassificationManagerService.this.mLock) {
                    try {
                        com.android.server.textclassifier.TextClassificationManagerService.ServiceState serviceStateLocked = com.android.server.textclassifier.TextClassificationManagerService.this.getUserStateLocked(changingUserId).getServiceStateLocked(str);
                        if (serviceStateLocked != null) {
                            serviceStateLocked.onPackageModifiedLocked();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            private void notifyPackageInstallStatusChange(java.lang.String str, boolean z) {
                int changingUserId = getChangingUserId();
                synchronized (com.android.server.textclassifier.TextClassificationManagerService.this.mLock) {
                    try {
                        com.android.server.textclassifier.TextClassificationManagerService.ServiceState serviceStateLocked = com.android.server.textclassifier.TextClassificationManagerService.this.getUserStateLocked(changingUserId).getServiceStateLocked(str);
                        if (serviceStateLocked != null) {
                            serviceStateLocked.onPackageInstallStatusChangeLocked(z);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }.register(this.mContext, (android.os.Looper) null, android.os.UserHandle.ALL, true);
    }

    public void onConnectedStateChanged(int i) {
    }

    public void onSuggestSelection(@android.annotation.Nullable final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.TextSelection.Request request, final android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(request);
        java.util.Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda0
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.textclassifier.TextClassificationManagerService.lambda$onSuggestSelection$0(textClassificationSessionId, request, iTextClassifierCallback, (android.service.textclassifier.ITextClassifierService) obj);
            }
        }, "onSuggestSelection", iTextClassifierCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onSuggestSelection$0(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextSelection.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback, android.service.textclassifier.ITextClassifierService iTextClassifierService) throws java.lang.Exception {
        iTextClassifierService.onSuggestSelection(textClassificationSessionId, request, wrap(iTextClassifierCallback));
    }

    public void onClassifyText(@android.annotation.Nullable final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.TextClassification.Request request, final android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(request);
        java.util.Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda7
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.textclassifier.TextClassificationManagerService.lambda$onClassifyText$1(textClassificationSessionId, request, iTextClassifierCallback, (android.service.textclassifier.ITextClassifierService) obj);
            }
        }, "onClassifyText", iTextClassifierCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onClassifyText$1(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.TextClassification.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback, android.service.textclassifier.ITextClassifierService iTextClassifierService) throws java.lang.Exception {
        iTextClassifierService.onClassifyText(textClassificationSessionId, request, wrap(iTextClassifierCallback));
    }

    public void onGenerateLinks(@android.annotation.Nullable final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.TextLinks.Request request, final android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(request);
        java.util.Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda3
            public final void acceptOrThrow(java.lang.Object obj) {
                ((android.service.textclassifier.ITextClassifierService) obj).onGenerateLinks(textClassificationSessionId, request, iTextClassifierCallback);
            }
        }, "onGenerateLinks", iTextClassifierCallback);
    }

    public void onSelectionEvent(@android.annotation.Nullable final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.SelectionEvent selectionEvent) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(selectionEvent);
        java.util.Objects.requireNonNull(selectionEvent.getSystemTextClassifierMetadata());
        handleRequest(selectionEvent.getSystemTextClassifierMetadata(), true, true, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda5
            public final void acceptOrThrow(java.lang.Object obj) {
                ((android.service.textclassifier.ITextClassifierService) obj).onSelectionEvent(textClassificationSessionId, selectionEvent);
            }
        }, "onSelectionEvent", NO_OP_CALLBACK);
    }

    public void onTextClassifierEvent(@android.annotation.Nullable final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.TextClassifierEvent textClassifierEvent) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(textClassifierEvent);
        android.view.textclassifier.TextClassificationContext eventContext = textClassifierEvent.getEventContext();
        handleRequest(eventContext != null ? eventContext.getSystemTextClassifierMetadata() : null, true, true, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda2
            public final void acceptOrThrow(java.lang.Object obj) {
                ((android.service.textclassifier.ITextClassifierService) obj).onTextClassifierEvent(textClassificationSessionId, textClassifierEvent);
            }
        }, "onTextClassifierEvent", NO_OP_CALLBACK);
    }

    public void onDetectLanguage(@android.annotation.Nullable final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.TextLanguage.Request request, final android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(request);
        java.util.Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda10
            public final void acceptOrThrow(java.lang.Object obj) {
                ((android.service.textclassifier.ITextClassifierService) obj).onDetectLanguage(textClassificationSessionId, request, iTextClassifierCallback);
            }
        }, "onDetectLanguage", iTextClassifierCallback);
    }

    public void onSuggestConversationActions(@android.annotation.Nullable final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, final android.view.textclassifier.ConversationActions.Request request, final android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(request);
        java.util.Objects.requireNonNull(request.getSystemTextClassifierMetadata());
        handleRequest(request.getSystemTextClassifierMetadata(), true, true, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda11
            public final void acceptOrThrow(java.lang.Object obj) {
                com.android.server.textclassifier.TextClassificationManagerService.lambda$onSuggestConversationActions$6(textClassificationSessionId, request, iTextClassifierCallback, (android.service.textclassifier.ITextClassifierService) obj);
            }
        }, "onSuggestConversationActions", iTextClassifierCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onSuggestConversationActions$6(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.view.textclassifier.ConversationActions.Request request, android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback, android.service.textclassifier.ITextClassifierService iTextClassifierService) throws java.lang.Exception {
        iTextClassifierService.onSuggestConversationActions(textClassificationSessionId, request, wrap(iTextClassifierCallback));
    }

    public void onCreateTextClassificationSession(final android.view.textclassifier.TextClassificationContext textClassificationContext, final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(textClassificationSessionId);
        java.util.Objects.requireNonNull(textClassificationContext);
        java.util.Objects.requireNonNull(textClassificationContext.getSystemTextClassifierMetadata());
        synchronized (this.mLock) {
            this.mSessionCache.put(textClassificationSessionId, textClassificationContext);
        }
        handleRequest(textClassificationContext.getSystemTextClassifierMetadata(), true, false, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda4
            public final void acceptOrThrow(java.lang.Object obj) {
                ((android.service.textclassifier.ITextClassifierService) obj).onCreateTextClassificationSession(textClassificationContext, textClassificationSessionId);
            }
        }, "onCreateTextClassificationSession", NO_OP_CALLBACK);
    }

    public void onDestroyTextClassificationSession(final android.view.textclassifier.TextClassificationSessionId textClassificationSessionId) throws android.os.RemoteException {
        int callingUserId;
        java.util.Objects.requireNonNull(textClassificationSessionId);
        synchronized (this.mLock) {
            try {
                com.android.server.textclassifier.TextClassificationManagerService.StrippedTextClassificationContext strippedTextClassificationContext = this.mSessionCache.get(textClassificationSessionId.getToken());
                if (strippedTextClassificationContext != null) {
                    callingUserId = strippedTextClassificationContext.userId;
                } else {
                    callingUserId = android.os.UserHandle.getCallingUserId();
                }
                handleRequest(new android.view.textclassifier.SystemTextClassifierMetadata("", callingUserId, strippedTextClassificationContext == null || strippedTextClassificationContext.useDefaultTextClassifier), false, false, new com.android.internal.util.FunctionalUtils.ThrowingConsumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda6
                    public final void acceptOrThrow(java.lang.Object obj) {
                        com.android.server.textclassifier.TextClassificationManagerService.this.lambda$onDestroyTextClassificationSession$8(textClassificationSessionId, (android.service.textclassifier.ITextClassifierService) obj);
                    }
                }, "onDestroyTextClassificationSession", NO_OP_CALLBACK);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDestroyTextClassificationSession$8(android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, android.service.textclassifier.ITextClassifierService iTextClassifierService) throws java.lang.Exception {
        iTextClassifierService.onDestroyTextClassificationSession(textClassificationSessionId);
        this.mSessionCache.remove(textClassificationSessionId.getToken());
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.textclassifier.TextClassificationManagerService.UserState getUserStateLocked(int i) {
        com.android.server.textclassifier.TextClassificationManagerService.UserState userState = this.mUserStates.get(i);
        if (userState == null) {
            com.android.server.textclassifier.TextClassificationManagerService.UserState userState2 = new com.android.server.textclassifier.TextClassificationManagerService.UserState(i);
            this.mUserStates.put(i, userState2);
            return userState2;
        }
        return userState;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    com.android.server.textclassifier.TextClassificationManagerService.UserState peekUserStateLocked(int i) {
        return this.mUserStates.get(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int resolvePackageToUid(@android.annotation.Nullable java.lang.String str, int i) {
        if (str == null) {
            return -1;
        }
        try {
            return this.mContext.getPackageManager().getPackageUidAsUser(str, i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(LOG_TAG, "Could not get the UID for " + str);
            return -1;
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, LOG_TAG, printWriter)) {
            final com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda1
                public final void runOrThrow() {
                    com.android.server.textclassifier.TextClassificationManagerService.this.lambda$dump$9(indentingPrintWriter);
                }
            });
            indentingPrintWriter.printPair("context", this.mContext);
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("defaultTextClassifierPackage", this.mDefaultTextClassifierPackage);
            indentingPrintWriter.println();
            indentingPrintWriter.printPair("systemTextClassifierPackage", this.mSystemTextClassifierPackage);
            indentingPrintWriter.println();
            synchronized (this.mLock) {
                try {
                    int size = this.mUserStates.size();
                    indentingPrintWriter.print("Number user states: ");
                    indentingPrintWriter.println(size);
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            indentingPrintWriter.increaseIndent();
                            com.android.server.textclassifier.TextClassificationManagerService.UserState valueAt = this.mUserStates.valueAt(i);
                            indentingPrintWriter.printPair("User", java.lang.Integer.valueOf(this.mUserStates.keyAt(i)));
                            indentingPrintWriter.println();
                            valueAt.dump(indentingPrintWriter);
                            indentingPrintWriter.decreaseIndent();
                        }
                    }
                    indentingPrintWriter.println("Number of active sessions: " + this.mSessionCache.size());
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$9(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) throws java.lang.Exception {
        ((android.view.textclassifier.TextClassificationManager) this.mContext.getSystemService(android.view.textclassifier.TextClassificationManager.class)).dump(indentingPrintWriter);
    }

    private void handleRequest(@android.annotation.Nullable android.view.textclassifier.SystemTextClassifierMetadata systemTextClassifierMetadata, boolean z, boolean z2, @android.annotation.NonNull final com.android.internal.util.FunctionalUtils.ThrowingConsumer<android.service.textclassifier.ITextClassifierService> throwingConsumer, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull final android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) throws android.os.RemoteException {
        java.util.Objects.requireNonNull(throwingConsumer);
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(iTextClassifierCallback);
        int callingUserId = systemTextClassifierMetadata == null ? android.os.UserHandle.getCallingUserId() : systemTextClassifierMetadata.getUserId();
        java.lang.String callingPackageName = systemTextClassifierMetadata == null ? null : systemTextClassifierMetadata.getCallingPackageName();
        boolean useDefaultTextClassifier = systemTextClassifierMetadata == null ? true : systemTextClassifierMetadata.useDefaultTextClassifier();
        if (z) {
            try {
                validateCallingPackage(callingPackageName);
            } catch (java.lang.Exception e) {
                throw new android.os.RemoteException("Invalid request: " + e.getMessage(), e, true, true);
            }
        }
        validateUser(callingUserId);
        synchronized (this.mLock) {
            try {
                final com.android.server.textclassifier.TextClassificationManagerService.ServiceState serviceStateLocked = getUserStateLocked(callingUserId).getServiceStateLocked(useDefaultTextClassifier);
                if (serviceStateLocked == null) {
                    android.util.Slog.d(LOG_TAG, "No configured system TextClassifierService");
                    iTextClassifierCallback.onFailure();
                } else if (!serviceStateLocked.isInstalledLocked() || !serviceStateLocked.isEnabledLocked()) {
                    iTextClassifierCallback.onFailure();
                } else if (z2 && !serviceStateLocked.bindLocked()) {
                    android.util.Slog.d(LOG_TAG, "Unable to bind TextClassifierService at " + str);
                    iTextClassifierCallback.onFailure();
                } else if (serviceStateLocked.isBoundLocked()) {
                    if (!serviceStateLocked.checkRequestAcceptedLocked(android.os.Binder.getCallingUid(), str)) {
                        android.util.Slog.w(LOG_TAG, java.lang.String.format("UID %d is not allowed to see the %s request", java.lang.Integer.valueOf(android.os.Binder.getCallingUid()), str));
                        iTextClassifierCallback.onFailure();
                        return;
                    }
                    consumeServiceNoExceptLocked(throwingConsumer, serviceStateLocked.mService);
                } else {
                    com.android.server.textclassifier.FixedSizeQueue<com.android.server.textclassifier.TextClassificationManagerService.PendingRequest> fixedSizeQueue = serviceStateLocked.mPendingRequests;
                    com.android.internal.util.FunctionalUtils.ThrowingRunnable throwingRunnable = new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda8
                        public final void runOrThrow() {
                            com.android.server.textclassifier.TextClassificationManagerService.lambda$handleRequest$10(throwingConsumer, serviceStateLocked);
                        }
                    };
                    java.util.Objects.requireNonNull(iTextClassifierCallback);
                    fixedSizeQueue.add(new com.android.server.textclassifier.TextClassificationManagerService.PendingRequest(str, throwingRunnable, new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda9
                        public final void runOrThrow() {
                            iTextClassifierCallback.onFailure();
                        }
                    }, iTextClassifierCallback.asBinder(), this, serviceStateLocked, android.os.Binder.getCallingUid()));
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handleRequest$10(com.android.internal.util.FunctionalUtils.ThrowingConsumer throwingConsumer, com.android.server.textclassifier.TextClassificationManagerService.ServiceState serviceState) throws java.lang.Exception {
        consumeServiceNoExceptLocked(throwingConsumer, serviceState.mService);
    }

    private static void consumeServiceNoExceptLocked(@android.annotation.NonNull com.android.internal.util.FunctionalUtils.ThrowingConsumer<android.service.textclassifier.ITextClassifierService> throwingConsumer, @android.annotation.Nullable android.service.textclassifier.ITextClassifierService iTextClassifierService) {
        try {
            throwingConsumer.accept(iTextClassifierService);
        } catch (java.lang.Error | java.lang.RuntimeException e) {
            android.util.Slog.e(LOG_TAG, "Exception when consume textClassifierService: " + e);
        }
    }

    private static android.service.textclassifier.ITextClassifierCallback wrap(android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) {
        return new com.android.server.textclassifier.TextClassificationManagerService.CallbackWrapper(iTextClassifierCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTextClassifierServicePackageOverrideChanged(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                int size = this.mUserStates.size();
                for (int i = 0; i < size; i++) {
                    this.mUserStates.valueAt(i).onTextClassifierServicePackageOverrideChangedLocked(str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class PendingRequest implements android.os.IBinder.DeathRecipient {

        @android.annotation.Nullable
        private final android.os.IBinder mBinder;

        @android.annotation.Nullable
        private final java.lang.String mName;

        @android.annotation.NonNull
        private final java.lang.Runnable mOnServiceFailure;

        @android.annotation.NonNull
        private final java.lang.Runnable mRequest;

        @android.annotation.NonNull
        private final com.android.server.textclassifier.TextClassificationManagerService mService;

        @android.annotation.NonNull
        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final com.android.server.textclassifier.TextClassificationManagerService.ServiceState mServiceState;
        private final int mUid;

        PendingRequest(@android.annotation.Nullable java.lang.String str, @android.annotation.NonNull com.android.internal.util.FunctionalUtils.ThrowingRunnable throwingRunnable, @android.annotation.NonNull com.android.internal.util.FunctionalUtils.ThrowingRunnable throwingRunnable2, @android.annotation.Nullable android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.textclassifier.TextClassificationManagerService textClassificationManagerService, @android.annotation.NonNull com.android.server.textclassifier.TextClassificationManagerService.ServiceState serviceState, int i) {
            this.mName = str;
            java.util.Objects.requireNonNull(throwingRunnable);
            this.mRequest = com.android.server.textclassifier.TextClassificationManagerService.logOnFailure(throwingRunnable, "handling pending request");
            java.util.Objects.requireNonNull(throwingRunnable2);
            this.mOnServiceFailure = com.android.server.textclassifier.TextClassificationManagerService.logOnFailure(throwingRunnable2, "notifying callback of service failure");
            this.mBinder = iBinder;
            this.mService = textClassificationManagerService;
            java.util.Objects.requireNonNull(serviceState);
            this.mServiceState = serviceState;
            if (this.mBinder != null) {
                try {
                    this.mBinder.linkToDeath(this, 0);
                } catch (android.os.RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mUid = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (this.mService.mLock) {
                removeLocked();
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void removeLocked() {
            this.mServiceState.mPendingRequests.remove(this);
            if (this.mBinder != null) {
                this.mBinder.unlinkToDeath(this, 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.Runnable logOnFailure(@android.annotation.Nullable com.android.internal.util.FunctionalUtils.ThrowingRunnable throwingRunnable, final java.lang.String str) {
        if (throwingRunnable == null) {
            return null;
        }
        return com.android.internal.util.FunctionalUtils.handleExceptions(throwingRunnable, new java.util.function.Consumer() { // from class: com.android.server.textclassifier.TextClassificationManagerService$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.textclassifier.TextClassificationManagerService.lambda$logOnFailure$11(str, (java.lang.Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$logOnFailure$11(java.lang.String str, java.lang.Throwable th) {
        android.util.Slog.d(LOG_TAG, "Error " + str + ": " + th.getMessage());
    }

    private void validateCallingPackage(@android.annotation.Nullable java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        if (str != null) {
            int packageUidAsUser = this.mContext.getPackageManager().getPackageUidAsUser(str, android.os.UserHandle.getCallingUserId());
            int callingUid = android.os.Binder.getCallingUid();
            com.android.internal.util.Preconditions.checkArgument(callingUid == packageUidAsUser || callingUid == 1000, "Invalid package name. callingPackage=" + str + ", callingUid=" + callingUid);
        }
    }

    private void validateUser(int i) {
        com.android.internal.util.Preconditions.checkArgument(i != -10000, "Null userId");
        int callingUserId = android.os.UserHandle.getCallingUserId();
        if (callingUserId != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "Invalid userId. UserId=" + i + ", CallingUserId=" + callingUserId);
        }
    }

    static final class SessionCache {
        private static final int MAX_CACHE_SIZE = 100;

        @android.annotation.NonNull
        private final java.lang.Object mLock;

        @android.annotation.NonNull
        private final android.os.IBinder.DeathRecipient mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.textclassifier.TextClassificationManagerService.SessionCache.1
            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied(android.os.IBinder iBinder) {
                com.android.server.textclassifier.TextClassificationManagerService.SessionCache.this.remove(iBinder);
            }
        };

        @android.annotation.NonNull
        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.LruCache<android.os.IBinder, com.android.server.textclassifier.TextClassificationManagerService.StrippedTextClassificationContext> mCache = new android.util.LruCache<android.os.IBinder, com.android.server.textclassifier.TextClassificationManagerService.StrippedTextClassificationContext>(100) { // from class: com.android.server.textclassifier.TextClassificationManagerService.SessionCache.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.util.LruCache
            public void entryRemoved(boolean z, android.os.IBinder iBinder, com.android.server.textclassifier.TextClassificationManagerService.StrippedTextClassificationContext strippedTextClassificationContext, com.android.server.textclassifier.TextClassificationManagerService.StrippedTextClassificationContext strippedTextClassificationContext2) {
                if (z) {
                    iBinder.unlinkToDeath(com.android.server.textclassifier.TextClassificationManagerService.SessionCache.this.mDeathRecipient, 0);
                }
            }
        };

        SessionCache(@android.annotation.NonNull java.lang.Object obj) {
            java.util.Objects.requireNonNull(obj);
            this.mLock = obj;
        }

        void put(@android.annotation.NonNull android.view.textclassifier.TextClassificationSessionId textClassificationSessionId, @android.annotation.NonNull android.view.textclassifier.TextClassificationContext textClassificationContext) {
            synchronized (this.mLock) {
                this.mCache.put(textClassificationSessionId.getToken(), new com.android.server.textclassifier.TextClassificationManagerService.StrippedTextClassificationContext(textClassificationContext));
                try {
                    textClassificationSessionId.getToken().linkToDeath(this.mDeathRecipient, 0);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, "SessionCache: Failed to link to death", e);
                }
            }
        }

        @android.annotation.Nullable
        com.android.server.textclassifier.TextClassificationManagerService.StrippedTextClassificationContext get(@android.annotation.NonNull android.os.IBinder iBinder) {
            com.android.server.textclassifier.TextClassificationManagerService.StrippedTextClassificationContext strippedTextClassificationContext;
            java.util.Objects.requireNonNull(iBinder);
            synchronized (this.mLock) {
                strippedTextClassificationContext = this.mCache.get(iBinder);
            }
            return strippedTextClassificationContext;
        }

        void remove(@android.annotation.NonNull android.os.IBinder iBinder) {
            java.util.Objects.requireNonNull(iBinder);
            synchronized (this.mLock) {
                try {
                    iBinder.unlinkToDeath(this.mDeathRecipient, 0);
                } catch (java.util.NoSuchElementException e) {
                }
                this.mCache.remove(iBinder);
            }
        }

        int size() {
            int size;
            synchronized (this.mLock) {
                size = this.mCache.size();
            }
            return size;
        }
    }

    static class StrippedTextClassificationContext {
        public final boolean useDefaultTextClassifier;
        public final int userId;

        StrippedTextClassificationContext(android.view.textclassifier.TextClassificationContext textClassificationContext) {
            android.view.textclassifier.SystemTextClassifierMetadata systemTextClassifierMetadata = textClassificationContext.getSystemTextClassifierMetadata();
            this.userId = systemTextClassifierMetadata.getUserId();
            this.useDefaultTextClassifier = systemTextClassifierMetadata.useDefaultTextClassifier();
        }
    }

    private final class UserState {

        @android.annotation.Nullable
        private final com.android.server.textclassifier.TextClassificationManagerService.ServiceState mDefaultServiceState;

        @android.annotation.Nullable
        private final com.android.server.textclassifier.TextClassificationManagerService.ServiceState mSystemServiceState;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        private com.android.server.textclassifier.TextClassificationManagerService.ServiceState mUntrustedServiceState;
        final int mUserId;

        private UserState(int i) {
            com.android.server.textclassifier.TextClassificationManagerService.ServiceState serviceState;
            this.mUserId = i;
            if (android.text.TextUtils.isEmpty(com.android.server.textclassifier.TextClassificationManagerService.this.mDefaultTextClassifierPackage)) {
                serviceState = null;
            } else {
                serviceState = new com.android.server.textclassifier.TextClassificationManagerService.ServiceState(i, com.android.server.textclassifier.TextClassificationManagerService.this.mDefaultTextClassifierPackage, true);
            }
            this.mDefaultServiceState = serviceState;
            this.mSystemServiceState = android.text.TextUtils.isEmpty(com.android.server.textclassifier.TextClassificationManagerService.this.mSystemTextClassifierPackage) ? null : new com.android.server.textclassifier.TextClassificationManagerService.ServiceState(i, com.android.server.textclassifier.TextClassificationManagerService.this.mSystemTextClassifierPackage, true);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        com.android.server.textclassifier.TextClassificationManagerService.ServiceState getServiceStateLocked(boolean z) {
            if (z) {
                return this.mDefaultServiceState;
            }
            final android.view.textclassifier.TextClassificationConstants textClassificationConstants = com.android.server.textclassifier.TextClassificationManagerService.this.mSettings;
            java.util.Objects.requireNonNull(textClassificationConstants);
            java.lang.String str = (java.lang.String) android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingSupplier() { // from class: com.android.server.textclassifier.TextClassificationManagerService$UserState$$ExternalSyntheticLambda0
                public final java.lang.Object getOrThrow() {
                    return textClassificationConstants.getTextClassifierServicePackageOverride();
                }
            });
            if (android.text.TextUtils.isEmpty(str)) {
                return this.mSystemServiceState != null ? this.mSystemServiceState : this.mDefaultServiceState;
            }
            if (str.equals(com.android.server.textclassifier.TextClassificationManagerService.this.mDefaultTextClassifierPackage)) {
                return this.mDefaultServiceState;
            }
            if (str.equals(com.android.server.textclassifier.TextClassificationManagerService.this.mSystemTextClassifierPackage) && this.mSystemServiceState != null) {
                return this.mSystemServiceState;
            }
            if (this.mUntrustedServiceState == null) {
                this.mUntrustedServiceState = new com.android.server.textclassifier.TextClassificationManagerService.ServiceState(this.mUserId, str, false);
            }
            return this.mUntrustedServiceState;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void onTextClassifierServicePackageOverrideChangedLocked(java.lang.String str) {
            java.util.Iterator<com.android.server.textclassifier.TextClassificationManagerService.ServiceState> it = getAllServiceStatesLocked().iterator();
            while (it.hasNext()) {
                it.next().unbindIfBoundLocked();
            }
            this.mUntrustedServiceState = null;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void bindIfHasPendingRequestsLocked() {
            java.util.Iterator<com.android.server.textclassifier.TextClassificationManagerService.ServiceState> it = getAllServiceStatesLocked().iterator();
            while (it.hasNext()) {
                it.next().bindIfHasPendingRequestsLocked();
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void cleanupServiceLocked() {
            for (com.android.server.textclassifier.TextClassificationManagerService.ServiceState serviceState : getAllServiceStatesLocked()) {
                if (serviceState.mConnection != null) {
                    serviceState.mConnection.cleanupService();
                }
            }
        }

        @android.annotation.NonNull
        @com.android.internal.annotations.GuardedBy({"mLock"})
        private java.util.List<com.android.server.textclassifier.TextClassificationManagerService.ServiceState> getAllServiceStatesLocked() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (this.mDefaultServiceState != null) {
                arrayList.add(this.mDefaultServiceState);
            }
            if (this.mSystemServiceState != null) {
                arrayList.add(this.mSystemServiceState);
            }
            if (this.mUntrustedServiceState != null) {
                arrayList.add(this.mUntrustedServiceState);
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        public com.android.server.textclassifier.TextClassificationManagerService.ServiceState getServiceStateLocked(java.lang.String str) {
            for (com.android.server.textclassifier.TextClassificationManagerService.ServiceState serviceState : getAllServiceStatesLocked()) {
                if (serviceState.mPackageName.equals(str)) {
                    return serviceState;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void updatePackageStateLocked() {
            java.util.Iterator<com.android.server.textclassifier.TextClassificationManagerService.ServiceState> it = getAllServiceStatesLocked().iterator();
            while (it.hasNext()) {
                it.next().updatePackageStateLocked();
            }
        }

        void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            synchronized (com.android.server.textclassifier.TextClassificationManagerService.this.mLock) {
                indentingPrintWriter.increaseIndent();
                dump(indentingPrintWriter, this.mDefaultServiceState, "Default");
                dump(indentingPrintWriter, this.mSystemServiceState, "System");
                dump(indentingPrintWriter, this.mUntrustedServiceState, "Untrusted");
                indentingPrintWriter.decreaseIndent();
            }
        }

        private void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.Nullable com.android.server.textclassifier.TextClassificationManagerService.ServiceState serviceState, java.lang.String str) {
            synchronized (com.android.server.textclassifier.TextClassificationManagerService.this.mLock) {
                if (serviceState != null) {
                    try {
                        indentingPrintWriter.print(str + ": ");
                        serviceState.dump(indentingPrintWriter);
                        indentingPrintWriter.println();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class ServiceState {
        private static final int MAX_PENDING_REQUESTS = 20;
        final int mBindServiceFlags;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean mBinding;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        android.content.ComponentName mBoundComponentName;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        int mBoundServiceUid;

        @android.annotation.NonNull
        final com.android.server.textclassifier.TextClassificationManagerService.ServiceState.TextClassifierServiceConnection mConnection;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean mEnabled;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean mInstalled;
        final boolean mIsTrusted;

        @android.annotation.NonNull
        final java.lang.String mPackageName;

        @android.annotation.NonNull
        @com.android.internal.annotations.GuardedBy({"mLock"})
        final com.android.server.textclassifier.FixedSizeQueue<com.android.server.textclassifier.TextClassificationManagerService.PendingRequest> mPendingRequests;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        @android.annotation.Nullable
        android.service.textclassifier.ITextClassifierService mService;
        final int mUserId;

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$new$0(com.android.server.textclassifier.TextClassificationManagerService.PendingRequest pendingRequest) {
            android.util.Slog.w(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, java.lang.String.format("Pending request[%s] is dropped", pendingRequest.mName));
            pendingRequest.mOnServiceFailure.run();
        }

        private ServiceState(int i, @android.annotation.NonNull java.lang.String str, boolean z) {
            this.mPendingRequests = new com.android.server.textclassifier.FixedSizeQueue<>(20, new com.android.server.textclassifier.FixedSizeQueue.OnEntryEvictedListener() { // from class: com.android.server.textclassifier.TextClassificationManagerService$ServiceState$$ExternalSyntheticLambda0
                @Override // com.android.server.textclassifier.FixedSizeQueue.OnEntryEvictedListener
                public final void onEntryEvicted(java.lang.Object obj) {
                    com.android.server.textclassifier.TextClassificationManagerService.ServiceState.lambda$new$0((com.android.server.textclassifier.TextClassificationManagerService.PendingRequest) obj);
                }
            });
            this.mBoundComponentName = null;
            this.mBoundServiceUid = -1;
            this.mUserId = i;
            this.mPackageName = str;
            this.mConnection = new com.android.server.textclassifier.TextClassificationManagerService.ServiceState.TextClassifierServiceConnection(this.mUserId);
            this.mIsTrusted = z;
            this.mBindServiceFlags = createBindServiceFlags(str);
            this.mInstalled = isPackageInstalledForUser();
            this.mEnabled = isServiceEnabledForUser();
        }

        private int createBindServiceFlags(@android.annotation.NonNull java.lang.String str) {
            if (str.equals(com.android.server.textclassifier.TextClassificationManagerService.this.mDefaultTextClassifierPackage)) {
                return android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN;
            }
            return 69206017;
        }

        private boolean isPackageInstalledForUser() {
            try {
                return com.android.server.textclassifier.TextClassificationManagerService.this.mContext.getPackageManager().getPackageInfoAsUser(this.mPackageName, 0, this.mUserId) != null;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return false;
            }
        }

        private boolean isServiceEnabledForUser() {
            android.content.pm.PackageManager packageManager = com.android.server.textclassifier.TextClassificationManagerService.this.mContext.getPackageManager();
            android.content.Intent intent = new android.content.Intent("android.service.textclassifier.TextClassifierService");
            intent.setPackage(this.mPackageName);
            android.content.pm.ResolveInfo resolveServiceAsUser = packageManager.resolveServiceAsUser(intent, 4, this.mUserId);
            return (resolveServiceAsUser == null ? null : resolveServiceAsUser.serviceInfo) != null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void onPackageInstallStatusChangeLocked(boolean z) {
            this.mInstalled = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void onPackageModifiedLocked() {
            this.mEnabled = isServiceEnabledForUser();
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void updatePackageStateLocked() {
            this.mInstalled = isPackageInstalledForUser();
            this.mEnabled = isServiceEnabledForUser();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean isInstalledLocked() {
            return this.mInstalled;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean isEnabledLocked() {
            return this.mEnabled;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        boolean isBoundLocked() {
            return this.mService != null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void handlePendingRequestsLocked() {
            while (true) {
                com.android.server.textclassifier.TextClassificationManagerService.PendingRequest poll = this.mPendingRequests.poll();
                if (poll != null) {
                    if (isBoundLocked()) {
                        if (!checkRequestAcceptedLocked(poll.mUid, poll.mName)) {
                            android.util.Slog.w(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, java.lang.String.format("UID %d is not allowed to see the %s request", java.lang.Integer.valueOf(poll.mUid), poll.mName));
                            poll.mOnServiceFailure.run();
                        } else {
                            poll.mRequest.run();
                        }
                    } else {
                        android.util.Slog.d(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, "Unable to bind TextClassifierService for PendingRequest " + poll.mName);
                        poll.mOnServiceFailure.run();
                    }
                    if (poll.mBinder != null) {
                        poll.mBinder.unlinkToDeath(poll, 0);
                    }
                } else {
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public boolean bindIfHasPendingRequestsLocked() {
            return !this.mPendingRequests.isEmpty() && bindLocked();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void unbindIfBoundLocked() {
            if (isBoundLocked()) {
                android.util.Slog.v(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, "Unbinding " + this.mBoundComponentName + " for " + this.mUserId);
                com.android.server.textclassifier.TextClassificationManagerService.this.mContext.unbindService(this.mConnection);
                this.mConnection.cleanupService();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public boolean bindLocked() {
            if (isBoundLocked() || this.mBinding) {
                return true;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.content.ComponentName textClassifierServiceComponent = getTextClassifierServiceComponent();
                if (textClassifierServiceComponent == null) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
                android.content.Intent component = new android.content.Intent("android.service.textclassifier.TextClassifierService").setComponent(textClassifierServiceComponent);
                android.util.Slog.d(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, "Binding to " + component.getComponent());
                boolean bindServiceAsUser = com.android.server.textclassifier.TextClassificationManagerService.this.mContext.bindServiceAsUser(component, this.mConnection, this.mBindServiceFlags, android.os.UserHandle.of(this.mUserId));
                if (!bindServiceAsUser) {
                    android.util.Slog.e(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, "Could not bind to " + textClassifierServiceComponent);
                }
                this.mBinding = bindServiceAsUser;
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return bindServiceAsUser;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        @android.annotation.Nullable
        private android.content.ComponentName getTextClassifierServiceComponent() {
            return android.service.textclassifier.TextClassifierService.getServiceComponentName(com.android.server.textclassifier.TextClassificationManagerService.this.mContext, this.mPackageName, this.mIsTrusted ? 1048576 : 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.printPair("context", com.android.server.textclassifier.TextClassificationManagerService.this.mContext);
            indentingPrintWriter.printPair("userId", java.lang.Integer.valueOf(this.mUserId));
            synchronized (com.android.server.textclassifier.TextClassificationManagerService.this.mLock) {
                indentingPrintWriter.printPair(com.android.server.pm.verify.domain.DomainVerificationLegacySettings.ATTR_PACKAGE_NAME, this.mPackageName);
                indentingPrintWriter.printPair("installed", java.lang.Boolean.valueOf(this.mInstalled));
                indentingPrintWriter.printPair(com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED, java.lang.Boolean.valueOf(this.mEnabled));
                indentingPrintWriter.printPair("boundComponentName", this.mBoundComponentName);
                indentingPrintWriter.printPair("isTrusted", java.lang.Boolean.valueOf(this.mIsTrusted));
                indentingPrintWriter.printPair("bindServiceFlags", java.lang.Integer.valueOf(this.mBindServiceFlags));
                indentingPrintWriter.printPair("boundServiceUid", java.lang.Integer.valueOf(this.mBoundServiceUid));
                indentingPrintWriter.printPair("binding", java.lang.Boolean.valueOf(this.mBinding));
                indentingPrintWriter.printPair("numOfPendingRequests", java.lang.Integer.valueOf(this.mPendingRequests.size()));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public boolean checkRequestAcceptedLocked(int i, @android.annotation.NonNull java.lang.String str) {
            if (this.mIsTrusted || i == this.mBoundServiceUid) {
                return true;
            }
            android.util.Slog.w(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, java.lang.String.format("[%s] Non-default TextClassifierServices may only see text from the same uid.", str));
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void updateServiceInfoLocked(int i, @android.annotation.Nullable android.content.ComponentName componentName) {
            int resolvePackageToUid;
            this.mBoundComponentName = componentName;
            if (this.mBoundComponentName == null) {
                resolvePackageToUid = -1;
            } else {
                resolvePackageToUid = com.android.server.textclassifier.TextClassificationManagerService.this.resolvePackageToUid(this.mBoundComponentName.getPackageName(), i);
            }
            this.mBoundServiceUid = resolvePackageToUid;
        }

        private final class TextClassifierServiceConnection implements android.content.ServiceConnection {
            private final int mUserId;

            TextClassifierServiceConnection(int i) {
                this.mUserId = i;
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                android.service.textclassifier.ITextClassifierService asInterface = android.service.textclassifier.ITextClassifierService.Stub.asInterface(iBinder);
                try {
                    asInterface.onConnectedStateChanged(0);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, "error in onConnectedStateChanged");
                }
                init(asInterface, componentName);
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
                android.util.Slog.i(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, "onServiceDisconnected called with " + componentName);
                cleanupService();
            }

            @Override // android.content.ServiceConnection
            public void onBindingDied(android.content.ComponentName componentName) {
                android.util.Slog.i(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, "onBindingDied called with " + componentName);
                cleanupService();
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(android.content.ComponentName componentName) {
                android.util.Slog.i(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, "onNullBinding called with " + componentName);
                cleanupService();
            }

            void cleanupService() {
                init(null, null);
            }

            private void init(@android.annotation.Nullable android.service.textclassifier.ITextClassifierService iTextClassifierService, @android.annotation.Nullable android.content.ComponentName componentName) {
                synchronized (com.android.server.textclassifier.TextClassificationManagerService.this.mLock) {
                    com.android.server.textclassifier.TextClassificationManagerService.ServiceState.this.mService = iTextClassifierService;
                    com.android.server.textclassifier.TextClassificationManagerService.ServiceState.this.mBinding = false;
                    com.android.server.textclassifier.TextClassificationManagerService.ServiceState.this.updateServiceInfoLocked(this.mUserId, componentName);
                    com.android.server.textclassifier.TextClassificationManagerService.ServiceState.this.handlePendingRequestsLocked();
                }
            }
        }
    }

    private final class TextClassifierSettingsListener implements android.provider.DeviceConfig.OnPropertiesChangedListener {

        @android.annotation.NonNull
        private final android.content.Context mContext;

        @android.annotation.Nullable
        private java.lang.String mServicePackageOverride;

        TextClassifierSettingsListener(android.content.Context context) {
            this.mContext = context;
            this.mServicePackageOverride = com.android.server.textclassifier.TextClassificationManagerService.this.mSettings.getTextClassifierServicePackageOverride();
        }

        void registerObserver() {
            android.provider.DeviceConfig.addOnPropertiesChangedListener("textclassifier", this.mContext.getMainExecutor(), this);
        }

        public void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
            java.lang.String textClassifierServicePackageOverride = com.android.server.textclassifier.TextClassificationManagerService.this.mSettings.getTextClassifierServicePackageOverride();
            if (android.text.TextUtils.equals(textClassifierServicePackageOverride, this.mServicePackageOverride)) {
                return;
            }
            this.mServicePackageOverride = textClassifierServicePackageOverride;
            com.android.server.textclassifier.TextClassificationManagerService.this.onTextClassifierServicePackageOverrideChanged(textClassifierServicePackageOverride);
        }
    }

    private static final class CallbackWrapper extends android.service.textclassifier.ITextClassifierCallback.Stub {
        private final android.service.textclassifier.ITextClassifierCallback mWrapped;

        CallbackWrapper(android.service.textclassifier.ITextClassifierCallback iTextClassifierCallback) {
            java.util.Objects.requireNonNull(iTextClassifierCallback);
            this.mWrapped = iTextClassifierCallback;
        }

        public void onSuccess(android.os.Bundle bundle) {
            android.os.Parcelable response = android.service.textclassifier.TextClassifierService.getResponse(bundle);
            if (response instanceof android.view.textclassifier.TextClassification) {
                rewriteTextClassificationIcons(bundle);
            } else if (response instanceof android.view.textclassifier.ConversationActions) {
                rewriteConversationActionsIcons(bundle);
            } else if (response instanceof android.view.textclassifier.TextSelection) {
                rewriteTextSelectionIcons(bundle);
            }
            try {
                this.mWrapped.onSuccess(bundle);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, "Callback error", e);
            }
        }

        private static void rewriteTextSelectionIcons(android.os.Bundle bundle) {
            android.view.textclassifier.TextClassification rewriteTextClassificationIcons;
            android.view.textclassifier.TextSelection textSelection = (android.view.textclassifier.TextSelection) android.service.textclassifier.TextClassifierService.getResponse(bundle);
            if (textSelection.getTextClassification() == null || (rewriteTextClassificationIcons = rewriteTextClassificationIcons(textSelection.getTextClassification())) == null) {
                return;
            }
            android.service.textclassifier.TextClassifierService.putResponse(bundle, textSelection.toBuilder().setTextClassification(rewriteTextClassificationIcons).build());
        }

        @android.annotation.Nullable
        private static android.view.textclassifier.TextClassification rewriteTextClassificationIcons(android.view.textclassifier.TextClassification textClassification) {
            java.util.List<android.app.RemoteAction> actions = textClassification.getActions();
            int size = actions.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            boolean z = false;
            for (int i = 0; i < size; i++) {
                android.app.RemoteAction remoteAction = actions.get(i);
                if (shouldRewriteIcon(remoteAction)) {
                    remoteAction = validAction(remoteAction);
                    z = true;
                }
                arrayList.add(remoteAction);
            }
            if (z) {
                return textClassification.toBuilder().clearActions().addActions(arrayList).build();
            }
            return null;
        }

        private static void rewriteTextClassificationIcons(android.os.Bundle bundle) {
            android.view.textclassifier.TextClassification rewriteTextClassificationIcons = rewriteTextClassificationIcons((android.view.textclassifier.TextClassification) android.service.textclassifier.TextClassifierService.getResponse(bundle));
            if (rewriteTextClassificationIcons != null) {
                android.service.textclassifier.TextClassifierService.putResponse(bundle, rewriteTextClassificationIcons);
            }
        }

        private static void rewriteConversationActionsIcons(android.os.Bundle bundle) {
            android.view.textclassifier.ConversationActions conversationActions = (android.view.textclassifier.ConversationActions) android.service.textclassifier.TextClassifierService.getResponse(bundle);
            java.util.List<android.view.textclassifier.ConversationAction> conversationActions2 = conversationActions.getConversationActions();
            int size = conversationActions2.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            boolean z = false;
            for (int i = 0; i < size; i++) {
                android.view.textclassifier.ConversationAction conversationAction = conversationActions2.get(i);
                if (shouldRewriteIcon(conversationAction.getAction())) {
                    conversationAction = conversationAction.toBuilder().setAction(validAction(conversationAction.getAction())).build();
                    z = true;
                }
                arrayList.add(conversationAction);
            }
            if (z) {
                android.service.textclassifier.TextClassifierService.putResponse(bundle, new android.view.textclassifier.ConversationActions(arrayList, conversationActions.getId()));
            }
        }

        private static android.app.RemoteAction validAction(android.app.RemoteAction remoteAction) {
            android.app.RemoteAction remoteAction2 = new android.app.RemoteAction(changeIcon(remoteAction.getIcon()), remoteAction.getTitle(), remoteAction.getContentDescription(), remoteAction.getActionIntent());
            remoteAction2.setEnabled(remoteAction.isEnabled());
            remoteAction2.setShouldShowIcon(remoteAction.shouldShowIcon());
            return remoteAction2;
        }

        private static boolean shouldRewriteIcon(@android.annotation.Nullable android.app.RemoteAction remoteAction) {
            return remoteAction != null && remoteAction.getIcon().getType() == 2;
        }

        private static android.graphics.drawable.Icon changeIcon(android.graphics.drawable.Icon icon) {
            return android.graphics.drawable.Icon.createWithContentUri(com.android.server.textclassifier.IconsUriHelper.getInstance().getContentUri(icon.getResPackage(), icon.getResId()));
        }

        public void onFailure() {
            try {
                this.mWrapped.onFailure();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.textclassifier.TextClassificationManagerService.LOG_TAG, "Callback error", e);
            }
        }
    }
}
