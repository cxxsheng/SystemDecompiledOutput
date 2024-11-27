package com.android.server.autofill;

/* loaded from: classes.dex */
final class SecondaryProviderHandler implements com.android.server.autofill.RemoteFillService.FillServiceCallbacks {
    private static final java.lang.String TAG = "SecondaryProviderHandler";
    private final com.android.server.autofill.SecondaryProviderHandler.SecondaryProviderCallback mCallback;
    private int mLastFlag;
    private final com.android.server.autofill.RemoteFillService mRemoteFillService;

    interface SecondaryProviderCallback {
        void onSecondaryFillResponse(@android.annotation.Nullable android.service.autofill.FillResponse fillResponse, int i);
    }

    SecondaryProviderHandler(@android.annotation.NonNull android.content.Context context, int i, boolean z, com.android.server.autofill.SecondaryProviderHandler.SecondaryProviderCallback secondaryProviderCallback, android.content.ComponentName componentName) {
        this.mRemoteFillService = new com.android.server.autofill.RemoteFillService(context, componentName, i, this, z);
        this.mCallback = secondaryProviderCallback;
        android.util.Slog.v(TAG, "Creating a secondary provider handler with component name, " + componentName);
    }

    public void onServiceDied(com.android.server.autofill.RemoteFillService remoteFillService) {
        this.mRemoteFillService.destroy();
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public void onFillRequestSuccess(int i, @android.annotation.Nullable android.service.autofill.FillResponse fillResponse, @android.annotation.NonNull java.lang.String str, int i2) {
        android.util.Slog.v(TAG, "Received a fill response: " + fillResponse);
        this.mCallback.onSecondaryFillResponse(fillResponse, this.mLastFlag);
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public void onFillRequestFailure(int i, @android.annotation.Nullable java.lang.CharSequence charSequence) {
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public void onFillRequestTimeout(int i) {
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public void onSaveRequestSuccess(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable android.content.IntentSender intentSender) {
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public void onSaveRequestFailure(@android.annotation.Nullable java.lang.CharSequence charSequence, @android.annotation.NonNull java.lang.String str) {
    }

    @Override // com.android.server.autofill.RemoteFillService.FillServiceCallbacks
    public void onConvertCredentialRequestSuccess(@android.annotation.NonNull android.service.autofill.ConvertCredentialResponse convertCredentialResponse) {
    }

    public void onFillRequest(android.service.autofill.FillRequest fillRequest, android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, int i, int i2, android.view.autofill.IAutoFillManagerClient iAutoFillManagerClient) {
        android.util.Slog.v(TAG, "Requesting fill response to secondary provider.");
        this.mLastFlag = i;
        if (this.mRemoteFillService != null && this.mRemoteFillService.isCredentialAutofillService()) {
            android.util.Slog.v(TAG, "About to call CredAutofill service as secondary provider");
            this.mRemoteFillService.onFillCredentialRequest(addSessionIdAndRequestIdToClientState(fillRequest, inlineSuggestionsRequest, i2), iAutoFillManagerClient);
            return;
        }
        this.mRemoteFillService.onFillRequest(fillRequest);
    }

    private android.service.autofill.FillRequest addSessionIdAndRequestIdToClientState(android.service.autofill.FillRequest fillRequest, android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, int i) {
        if (fillRequest.getClientState() == null) {
            fillRequest = new android.service.autofill.FillRequest(fillRequest.getId(), fillRequest.getFillContexts(), fillRequest.getHints(), new android.os.Bundle(), fillRequest.getFlags(), inlineSuggestionsRequest, fillRequest.getDelayedFillIntentSender());
        }
        fillRequest.getClientState().putInt("autofill_session_id", i);
        fillRequest.getClientState().putInt("autofill_request_id", fillRequest.getId());
        return fillRequest;
    }

    public void destroy() {
        this.mRemoteFillService.destroy();
    }
}
