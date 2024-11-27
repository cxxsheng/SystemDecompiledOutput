package android.service.autofill.augmented;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class FillCallback {
    private static final java.lang.String TAG = android.service.autofill.augmented.FillCallback.class.getSimpleName();
    private final android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy mProxy;

    FillCallback(android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy autofillProxy) {
        this.mProxy = autofillProxy;
    }

    public void onSuccess(android.service.autofill.augmented.FillResponse fillResponse) {
        if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
            android.util.Log.d(TAG, "onSuccess(): " + fillResponse);
        }
        boolean z = true;
        if (fillResponse == null) {
            this.mProxy.logEvent(1);
            this.mProxy.reportResult(null, null, false);
            return;
        }
        java.util.List<android.service.autofill.Dataset> inlineSuggestions = fillResponse.getInlineSuggestions();
        android.os.Bundle clientState = fillResponse.getClientState();
        android.service.autofill.augmented.FillWindow fillWindow = fillResponse.getFillWindow();
        if (inlineSuggestions != null && !inlineSuggestions.isEmpty()) {
            this.mProxy.logEvent(4);
        } else if (fillWindow != null) {
            fillWindow.show();
            this.mProxy.reportResult(inlineSuggestions, clientState, z);
        }
        z = false;
        this.mProxy.reportResult(inlineSuggestions, clientState, z);
    }
}
