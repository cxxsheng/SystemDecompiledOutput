package android.service.autofill.augmented;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class FillController {
    private static final java.lang.String TAG = android.service.autofill.augmented.FillController.class.getSimpleName();
    private final android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy mProxy;

    FillController(android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy autofillProxy) {
        this.mProxy = autofillProxy;
    }

    public void autofill(java.util.List<android.util.Pair<android.view.autofill.AutofillId, android.view.autofill.AutofillValue>> list) {
        java.util.Objects.requireNonNull(list);
        if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
            android.util.Log.d(TAG, "autofill() with " + list.size() + " values");
        }
        try {
            this.mProxy.autofill(list);
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
        android.service.autofill.augmented.FillWindow fillWindow = this.mProxy.getFillWindow();
        if (fillWindow != null) {
            fillWindow.destroy();
        }
    }
}
