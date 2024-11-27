package com.android.server.autofill.ui;

/* loaded from: classes.dex */
public final class AutoFillUI {
    private static final java.lang.String TAG = "AutofillUI";

    @android.annotation.Nullable
    private com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback mCallback;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.Nullable
    private java.lang.Runnable mCreateFillUiRunnable;

    @android.annotation.Nullable
    private com.android.server.autofill.ui.DialogFillUi mFillDialog;

    @android.annotation.Nullable
    private com.android.server.autofill.ui.FillUi mFillUi;

    @android.annotation.NonNull
    private final com.android.server.autofill.ui.OverlayControl mOverlayControl;

    @android.annotation.Nullable
    private com.android.server.autofill.ui.SaveUi mSaveUi;

    @android.annotation.Nullable
    private com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback mSaveUiCallback;
    private final android.os.Handler mHandler = com.android.server.UiThread.getHandler();
    private final com.android.internal.logging.MetricsLogger mMetricsLogger = new com.android.internal.logging.MetricsLogger();

    @android.annotation.NonNull
    private final com.android.server.UiModeManagerInternal mUiModeMgr = (com.android.server.UiModeManagerInternal) com.android.server.LocalServices.getService(com.android.server.UiModeManagerInternal.class);

    public interface AutoFillUiCallback {
        void authenticate(int i, int i2, @android.annotation.NonNull android.content.IntentSender intentSender, @android.annotation.Nullable android.os.Bundle bundle, int i3);

        void cancelSave();

        void cancelSession();

        void dispatchUnhandledKey(android.view.autofill.AutofillId autofillId, android.view.KeyEvent keyEvent);

        void fill(int i, int i2, @android.annotation.NonNull android.service.autofill.Dataset dataset, int i3);

        void onShown(int i);

        void requestFallbackFromFillDialog();

        void requestHideFillUi(android.view.autofill.AutofillId autofillId);

        void requestHideFillUiWhenDestroyed(android.view.autofill.AutofillId autofillId);

        void requestShowFillUi(android.view.autofill.AutofillId autofillId, int i, int i2, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter);

        void requestShowSoftInput(android.view.autofill.AutofillId autofillId);

        void save();

        void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent);

        void startIntentSenderAndFinishSession(android.content.IntentSender intentSender);
    }

    public AutoFillUI(@android.annotation.NonNull android.content.Context context) {
        this.mContext = context;
        this.mOverlayControl = new com.android.server.autofill.ui.OverlayControl(context);
    }

    public void setCallback(@android.annotation.NonNull final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.AutoFillUI.this.lambda$setCallback$0(autoFillUiCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCallback$0(com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        if (this.mCallback != autoFillUiCallback) {
            if (this.mCallback != null) {
                if (isSaveUiShowing()) {
                    hideFillUiUiThread(autoFillUiCallback, true);
                } else {
                    lambda$hideAll$10(this.mCallback);
                }
            }
            this.mCallback = autoFillUiCallback;
        }
    }

    public void clearCallback(@android.annotation.NonNull final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.AutoFillUI.this.lambda$clearCallback$1(autoFillUiCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearCallback$1(com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        if (this.mCallback == autoFillUiCallback) {
            lambda$hideAll$10(autoFillUiCallback);
            this.mCallback = null;
        }
    }

    public void showError(int i, @android.annotation.NonNull com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        showError(this.mContext.getString(i), autoFillUiCallback);
    }

    public void showError(@android.annotation.Nullable final java.lang.CharSequence charSequence, @android.annotation.NonNull final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        android.util.Slog.w(TAG, "showError(): " + ((java.lang.Object) charSequence));
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.AutoFillUI.this.lambda$showError$2(autoFillUiCallback, charSequence);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showError$2(com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback, java.lang.CharSequence charSequence) {
        if (this.mCallback != autoFillUiCallback) {
            return;
        }
        lambda$hideAll$10(autoFillUiCallback);
        if (!android.text.TextUtils.isEmpty(charSequence)) {
            android.widget.Toast.makeText(this.mContext, charSequence, 1).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideFillUi$3(com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        hideFillUiUiThread(autoFillUiCallback, true);
    }

    public void hideFillUi(@android.annotation.NonNull final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.AutoFillUI.this.lambda$hideFillUi$3(autoFillUiCallback);
            }
        });
    }

    public void hideFillDialog(@android.annotation.NonNull final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.AutoFillUI.this.lambda$hideFillDialog$4(autoFillUiCallback);
            }
        });
    }

    public void filterFillUi(@android.annotation.Nullable final java.lang.String str, @android.annotation.NonNull final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.AutoFillUI.this.lambda$filterFillUi$5(autoFillUiCallback, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$filterFillUi$5(com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback, java.lang.String str) {
        if (autoFillUiCallback == this.mCallback && this.mFillUi != null) {
            this.mFillUi.setFilterText(str);
        }
    }

    public void showFillUi(@android.annotation.NonNull final android.view.autofill.AutofillId autofillId, @android.annotation.NonNull final android.service.autofill.FillResponse fillResponse, @android.annotation.Nullable final java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull final java.lang.CharSequence charSequence, @android.annotation.NonNull final android.graphics.drawable.Drawable drawable, @android.annotation.NonNull final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback, @android.annotation.NonNull final android.content.Context context, int i, boolean z, final int i2) {
        if (com.android.server.autofill.Helper.sDebug) {
            com.android.server.utils.Slogf.d(TAG, "showFillUi(): id=%s, filter=%d chars, displayId=%d", autofillId, java.lang.Integer.valueOf(str == null ? 0 : str.length()), java.lang.Integer.valueOf(context.getDisplayId()));
        }
        final android.metrics.LogMaker addTaggedData = com.android.server.autofill.Helper.newLogMaker(910, componentName, str2, i, z).addTaggedData(911, java.lang.Integer.valueOf(str == null ? 0 : str.length())).addTaggedData(909, java.lang.Integer.valueOf(fillResponse.getDatasets() != null ? fillResponse.getDatasets().size() : 0));
        java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.AutoFillUI.this.lambda$showFillUi$6(autoFillUiCallback, context, fillResponse, autofillId, str, charSequence, drawable, i2, addTaggedData);
            }
        };
        if (isSaveUiShowing()) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "postpone fill UI request..");
            }
            this.mCreateFillUiRunnable = runnable;
            return;
        }
        this.mHandler.post(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFillUi$6(final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback, android.content.Context context, final android.service.autofill.FillResponse fillResponse, final android.view.autofill.AutofillId autofillId, java.lang.String str, java.lang.CharSequence charSequence, android.graphics.drawable.Drawable drawable, int i, final android.metrics.LogMaker logMaker) {
        if (autoFillUiCallback != this.mCallback) {
            return;
        }
        lambda$hideAll$10(autoFillUiCallback);
        this.mFillUi = new com.android.server.autofill.ui.FillUi(context, fillResponse, autofillId, str, this.mOverlayControl, charSequence, drawable, this.mUiModeMgr.isNightMode(), i, new com.android.server.autofill.ui.FillUi.Callback() { // from class: com.android.server.autofill.ui.AutoFillUI.1
            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void onResponsePicked(android.service.autofill.FillResponse fillResponse2) {
                logMaker.setType(3);
                com.android.server.autofill.ui.AutoFillUI.this.hideFillUiUiThread(autoFillUiCallback, true);
                if (com.android.server.autofill.ui.AutoFillUI.this.mCallback != null) {
                    com.android.server.autofill.ui.AutoFillUI.this.mCallback.authenticate(fillResponse2.getRequestId(), com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL, fillResponse2.getAuthentication(), fillResponse2.getClientState(), 1);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void onShown() {
                if (com.android.server.autofill.ui.AutoFillUI.this.mCallback != null) {
                    com.android.server.autofill.ui.AutoFillUI.this.mCallback.onShown(1);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void onDatasetPicked(android.service.autofill.Dataset dataset) {
                logMaker.setType(4);
                com.android.server.autofill.ui.AutoFillUI.this.hideFillUiUiThread(autoFillUiCallback, true);
                if (com.android.server.autofill.ui.AutoFillUI.this.mCallback != null) {
                    com.android.server.autofill.ui.AutoFillUI.this.mCallback.fill(fillResponse.getRequestId(), fillResponse.getDatasets().indexOf(dataset), dataset, 1);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void onCanceled() {
                logMaker.setType(5);
                com.android.server.autofill.ui.AutoFillUI.this.hideFillUiUiThread(autoFillUiCallback, true);
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void onDestroy() {
                if (logMaker.getType() == 0) {
                    logMaker.setType(2);
                }
                com.android.server.autofill.ui.AutoFillUI.this.mMetricsLogger.write(logMaker);
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void requestShowFillUi(int i2, int i3, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) {
                if (com.android.server.autofill.ui.AutoFillUI.this.mCallback != null) {
                    com.android.server.autofill.ui.AutoFillUI.this.mCallback.requestShowFillUi(autofillId, i2, i3, iAutofillWindowPresenter);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void requestHideFillUi() {
                if (com.android.server.autofill.ui.AutoFillUI.this.mCallback != null) {
                    com.android.server.autofill.ui.AutoFillUI.this.mCallback.requestHideFillUi(autofillId);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void requestHideFillUiWhenDestroyed() {
                if (com.android.server.autofill.ui.AutoFillUI.this.mCallback != null) {
                    com.android.server.autofill.ui.AutoFillUI.this.mCallback.requestHideFillUiWhenDestroyed(autofillId);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void startIntentSender(android.content.IntentSender intentSender) {
                if (com.android.server.autofill.ui.AutoFillUI.this.mCallback != null) {
                    com.android.server.autofill.ui.AutoFillUI.this.mCallback.startIntentSenderAndFinishSession(intentSender);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void dispatchUnhandledKey(android.view.KeyEvent keyEvent) {
                if (com.android.server.autofill.ui.AutoFillUI.this.mCallback != null) {
                    com.android.server.autofill.ui.AutoFillUI.this.mCallback.dispatchUnhandledKey(autofillId, keyEvent);
                }
            }

            @Override // com.android.server.autofill.ui.FillUi.Callback
            public void cancelSession() {
                if (com.android.server.autofill.ui.AutoFillUI.this.mCallback != null) {
                    com.android.server.autofill.ui.AutoFillUI.this.mCallback.cancelSession();
                }
            }
        });
    }

    public void showSaveUi(@android.annotation.NonNull final java.lang.CharSequence charSequence, @android.annotation.NonNull final android.graphics.drawable.Drawable drawable, @android.annotation.Nullable final java.lang.String str, @android.annotation.NonNull final android.service.autofill.SaveInfo saveInfo, @android.annotation.NonNull final android.service.autofill.ValueFinder valueFinder, @android.annotation.NonNull final android.content.ComponentName componentName, @android.annotation.NonNull final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback, @android.annotation.NonNull final android.content.Context context, @android.annotation.NonNull final com.android.server.autofill.ui.PendingUi pendingUi, final boolean z, final boolean z2, final boolean z3, @android.annotation.Nullable final com.android.server.autofill.SaveEventLogger saveEventLogger) {
        if (com.android.server.autofill.Helper.sVerbose) {
            com.android.server.utils.Slogf.v(TAG, "showSaveUi(update=%b) for %s and display %d: %s", java.lang.Boolean.valueOf(z), componentName.toShortString(), java.lang.Integer.valueOf(context.getDisplayId()), saveInfo);
        }
        final android.metrics.LogMaker addTaggedData = com.android.server.autofill.Helper.newLogMaker(916, componentName, str, pendingUi.sessionId, z2).addTaggedData(917, java.lang.Integer.valueOf((saveInfo.getRequiredIds() == null ? 0 : saveInfo.getRequiredIds().length) + 0 + (saveInfo.getOptionalIds() != null ? saveInfo.getOptionalIds().length : 0)));
        if (z) {
            addTaggedData.addTaggedData(1555, 1);
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.AutoFillUI.this.lambda$showSaveUi$7(autoFillUiCallback, context, pendingUi, charSequence, drawable, str, componentName, saveInfo, valueFinder, addTaggedData, saveEventLogger, z, z2, z3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSaveUi$7(final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback, android.content.Context context, final com.android.server.autofill.ui.PendingUi pendingUi, java.lang.CharSequence charSequence, android.graphics.drawable.Drawable drawable, java.lang.String str, android.content.ComponentName componentName, android.service.autofill.SaveInfo saveInfo, android.service.autofill.ValueFinder valueFinder, final android.metrics.LogMaker logMaker, final com.android.server.autofill.SaveEventLogger saveEventLogger, boolean z, boolean z2, boolean z3) {
        if (autoFillUiCallback != this.mCallback) {
            return;
        }
        lambda$hideAll$10(autoFillUiCallback);
        this.mSaveUiCallback = autoFillUiCallback;
        this.mSaveUi = new com.android.server.autofill.ui.SaveUi(context, pendingUi, charSequence, drawable, str, componentName, saveInfo, valueFinder, this.mOverlayControl, new com.android.server.autofill.ui.SaveUi.OnSaveListener() { // from class: com.android.server.autofill.ui.AutoFillUI.2
            @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
            public void onSave() {
                logMaker.setType(4);
                if (saveEventLogger != null) {
                    saveEventLogger.maybeSetSaveButtonClicked(true);
                }
                com.android.server.autofill.ui.AutoFillUI.this.hideSaveUiUiThread(autoFillUiCallback);
                autoFillUiCallback.save();
                com.android.server.autofill.ui.AutoFillUI.this.destroySaveUiUiThread(pendingUi, true);
            }

            @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
            public void onCancel(android.content.IntentSender intentSender) {
                logMaker.setType(5);
                if (saveEventLogger != null) {
                    saveEventLogger.maybeSetCancelButtonClicked(true);
                }
                com.android.server.autofill.ui.AutoFillUI.this.hideSaveUiUiThread(autoFillUiCallback);
                if (intentSender != null) {
                    try {
                        intentSender.sendIntent(com.android.server.autofill.ui.AutoFillUI.this.mContext, 0, null, null, null);
                    } catch (android.content.IntentSender.SendIntentException e) {
                        android.util.Slog.e(com.android.server.autofill.ui.AutoFillUI.TAG, "Error starting negative action listener: " + intentSender, e);
                    }
                }
                autoFillUiCallback.cancelSave();
                com.android.server.autofill.ui.AutoFillUI.this.destroySaveUiUiThread(pendingUi, true);
            }

            @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
            public void onDestroy() {
                if (logMaker.getType() == 0) {
                    logMaker.setType(2);
                    autoFillUiCallback.cancelSave();
                }
                com.android.server.autofill.ui.AutoFillUI.this.mMetricsLogger.write(logMaker);
                if (saveEventLogger != null) {
                    saveEventLogger.maybeSetDialogDismissed(true);
                }
            }

            @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
            public void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent) {
                autoFillUiCallback.startIntentSender(intentSender, intent);
            }
        }, this.mUiModeMgr.isNightMode(), z, z2, z3);
    }

    public void showFillDialog(@android.annotation.NonNull final android.view.autofill.AutofillId autofillId, @android.annotation.NonNull final android.service.autofill.FillResponse fillResponse, @android.annotation.Nullable final java.lang.String str, @android.annotation.Nullable final java.lang.String str2, @android.annotation.NonNull final android.content.ComponentName componentName, @android.annotation.Nullable final android.graphics.drawable.Drawable drawable, @android.annotation.NonNull final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback, int i, boolean z, @android.annotation.Nullable final com.android.server.autofill.PresentationStatsEventLogger presentationStatsEventLogger) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "showFillDialog for " + componentName.toShortString() + ": " + fillResponse);
        }
        final android.metrics.LogMaker addTaggedData = com.android.server.autofill.Helper.newLogMaker(910, componentName, str2, i, z).addTaggedData(911, java.lang.Integer.valueOf(str == null ? 0 : str.length())).addTaggedData(909, java.lang.Integer.valueOf(fillResponse.getDatasets() != null ? fillResponse.getDatasets().size() : 0));
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.AutoFillUI.this.lambda$showFillDialog$8(autoFillUiCallback, fillResponse, autofillId, str, drawable, str2, componentName, presentationStatsEventLogger, addTaggedData);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFillDialog$8(final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback, final android.service.autofill.FillResponse fillResponse, final android.view.autofill.AutofillId autofillId, java.lang.String str, android.graphics.drawable.Drawable drawable, java.lang.String str2, android.content.ComponentName componentName, final com.android.server.autofill.PresentationStatsEventLogger presentationStatsEventLogger, final android.metrics.LogMaker logMaker) {
        if (autoFillUiCallback != this.mCallback) {
            return;
        }
        lambda$hideAll$10(autoFillUiCallback);
        this.mFillDialog = new com.android.server.autofill.ui.DialogFillUi(this.mContext, fillResponse, autofillId, str, drawable, str2, componentName, this.mOverlayControl, this.mUiModeMgr.isNightMode(), new com.android.server.autofill.ui.DialogFillUi.UiCallback() { // from class: com.android.server.autofill.ui.AutoFillUI.3
            @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
            public void onResponsePicked(android.service.autofill.FillResponse fillResponse2) {
                log(3);
                com.android.server.autofill.ui.AutoFillUI.this.lambda$hideFillDialog$4(autoFillUiCallback);
                if (com.android.server.autofill.ui.AutoFillUI.this.mCallback != null) {
                    com.android.server.autofill.ui.AutoFillUI.this.mCallback.authenticate(fillResponse2.getRequestId(), com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL, fillResponse2.getAuthentication(), fillResponse2.getClientState(), 3);
                }
            }

            @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
            public void onShown() {
                autoFillUiCallback.onShown(3);
            }

            @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
            public void onDatasetPicked(android.service.autofill.Dataset dataset) {
                log(4);
                if (presentationStatsEventLogger != null) {
                    presentationStatsEventLogger.maybeSetPositiveCtaButtonClicked(true);
                }
                com.android.server.autofill.ui.AutoFillUI.this.lambda$hideFillDialog$4(autoFillUiCallback);
                if (com.android.server.autofill.ui.AutoFillUI.this.mCallback != null) {
                    com.android.server.autofill.ui.AutoFillUI.this.mCallback.fill(fillResponse.getRequestId(), fillResponse.getDatasets().indexOf(dataset), dataset, 3);
                }
            }

            @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
            public void onDismissed() {
                log(5);
                if (presentationStatsEventLogger != null) {
                    presentationStatsEventLogger.maybeSetDialogDismissed(true);
                }
                com.android.server.autofill.ui.AutoFillUI.this.lambda$hideFillDialog$4(autoFillUiCallback);
                autoFillUiCallback.requestShowSoftInput(autofillId);
                autoFillUiCallback.requestFallbackFromFillDialog();
            }

            @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
            public void onCanceled() {
                log(2);
                if (presentationStatsEventLogger != null) {
                    presentationStatsEventLogger.maybeSetNegativeCtaButtonClicked(true);
                }
                com.android.server.autofill.ui.AutoFillUI.this.lambda$hideFillDialog$4(autoFillUiCallback);
                autoFillUiCallback.requestShowSoftInput(autofillId);
                autoFillUiCallback.requestFallbackFromFillDialog();
            }

            @Override // com.android.server.autofill.ui.DialogFillUi.UiCallback
            public void startIntentSender(android.content.IntentSender intentSender) {
                com.android.server.autofill.ui.AutoFillUI.this.mCallback.startIntentSenderAndFinishSession(intentSender);
            }

            private void log(int i) {
                logMaker.setType(i);
                com.android.server.autofill.ui.AutoFillUI.this.mMetricsLogger.write(logMaker);
            }
        });
    }

    public void onPendingSaveUi(final int i, @android.annotation.NonNull final android.os.IBinder iBinder) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.AutoFillUI.this.lambda$onPendingSaveUi$9(i, iBinder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPendingSaveUi$9(int i, android.os.IBinder iBinder) {
        if (this.mSaveUi != null) {
            this.mSaveUi.onPendingUi(i, iBinder);
            return;
        }
        android.util.Slog.w(TAG, "onPendingSaveUi(" + i + "): no save ui");
    }

    public void hideAll(@android.annotation.Nullable final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.AutoFillUI.this.lambda$hideAll$10(autoFillUiCallback);
            }
        });
    }

    public void destroyAll(@android.annotation.Nullable final com.android.server.autofill.ui.PendingUi pendingUi, @android.annotation.Nullable final com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback, final boolean z) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.autofill.ui.AutoFillUI$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.AutoFillUI.this.lambda$destroyAll$11(pendingUi, autoFillUiCallback, z);
            }
        });
    }

    public boolean isSaveUiShowing() {
        if (this.mSaveUi == null) {
            return false;
        }
        return this.mSaveUi.isShowing();
    }

    public boolean isFillDialogShowing() {
        if (this.mFillDialog == null) {
            return false;
        }
        return this.mFillDialog.isShowing();
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("Autofill UI");
        printWriter.print("  ");
        printWriter.print("Night mode: ");
        printWriter.println(this.mUiModeMgr.isNightMode());
        if (this.mFillUi != null) {
            printWriter.print("  ");
            printWriter.println("showsFillUi: true");
            this.mFillUi.dump(printWriter, "    ");
        } else {
            printWriter.print("  ");
            printWriter.println("showsFillUi: false");
        }
        if (this.mSaveUi != null) {
            printWriter.print("  ");
            printWriter.println("showsSaveUi: true");
            this.mSaveUi.dump(printWriter, "    ");
        } else {
            printWriter.print("  ");
            printWriter.println("showsSaveUi: false");
        }
        if (this.mFillDialog != null) {
            printWriter.print("  ");
            printWriter.println("showsFillDialog: true");
            this.mFillDialog.dump(printWriter, "    ");
        } else {
            printWriter.print("  ");
            printWriter.println("showsFillDialog: false");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideFillUiUiThread(@android.annotation.Nullable com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback, boolean z) {
        if (this.mFillUi != null) {
            if (autoFillUiCallback == null || autoFillUiCallback == this.mCallback) {
                this.mFillUi.destroy(z);
                this.mFillUi = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public com.android.server.autofill.ui.PendingUi hideSaveUiUiThread(@android.annotation.Nullable com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "hideSaveUiUiThread(): mSaveUi=" + this.mSaveUi + ", callback=" + autoFillUiCallback + ", mCallback=" + this.mCallback);
        }
        if (this.mSaveUi != null && this.mSaveUiCallback == autoFillUiCallback) {
            return this.mSaveUi.hide();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: hideFillDialogUiThread, reason: merged with bridge method [inline-methods] */
    public void lambda$hideFillDialog$4(@android.annotation.Nullable com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        if (this.mFillDialog != null) {
            if (autoFillUiCallback == null || autoFillUiCallback == this.mCallback) {
                this.mFillDialog.destroy();
                this.mFillDialog = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroySaveUiUiThread(@android.annotation.Nullable com.android.server.autofill.ui.PendingUi pendingUi, boolean z) {
        if (this.mSaveUi == null) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "destroySaveUiUiThread(): already destroyed");
                return;
            }
            return;
        }
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "destroySaveUiUiThread(): " + pendingUi);
        }
        this.mSaveUi.destroy();
        this.mSaveUi = null;
        this.mSaveUiCallback = null;
        if (pendingUi != null && z) {
            try {
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(TAG, "destroySaveUiUiThread(): notifying client");
                }
                pendingUi.client.setSaveUiState(pendingUi.sessionId, false);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Error notifying client to set save UI state to hidden: " + e);
            }
        }
        if (this.mCreateFillUiRunnable != null) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "start the pending fill UI request..");
            }
            this.mHandler.post(this.mCreateFillUiRunnable);
            this.mCreateFillUiRunnable = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: destroyAllUiThread, reason: merged with bridge method [inline-methods] */
    public void lambda$destroyAll$11(@android.annotation.Nullable com.android.server.autofill.ui.PendingUi pendingUi, @android.annotation.Nullable com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback, boolean z) {
        hideFillUiUiThread(autoFillUiCallback, z);
        lambda$hideFillDialog$4(autoFillUiCallback);
        destroySaveUiUiThread(pendingUi, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: hideAllUiThread, reason: merged with bridge method [inline-methods] */
    public void lambda$hideAll$10(@android.annotation.Nullable com.android.server.autofill.ui.AutoFillUI.AutoFillUiCallback autoFillUiCallback) {
        hideFillUiUiThread(autoFillUiCallback, true);
        lambda$hideFillDialog$4(autoFillUiCallback);
        com.android.server.autofill.ui.PendingUi hideSaveUiUiThread = hideSaveUiUiThread(autoFillUiCallback);
        if (hideSaveUiUiThread != null && hideSaveUiUiThread.getState() == 4) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "hideAllUiThread(): destroying Save UI because pending restoration is finished");
            }
            destroySaveUiUiThread(hideSaveUiUiThread, true);
        }
    }
}
