package android.service.autofill.augmented;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class FillRequest {
    private final android.view.inputmethod.InlineSuggestionsRequest mInlineSuggestionsRequest;
    private final android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy mProxy;

    public int getTaskId() {
        return this.mProxy.mTaskId;
    }

    public android.content.ComponentName getActivityComponent() {
        return this.mProxy.mComponentName;
    }

    public android.view.autofill.AutofillId getFocusedId() {
        return this.mProxy.getFocusedId();
    }

    public android.view.autofill.AutofillValue getFocusedValue() {
        return this.mProxy.getFocusedValue();
    }

    public android.app.assist.AssistStructure.ViewNode getFocusedViewNode() {
        return this.mProxy.getFocusedViewNode();
    }

    public android.service.autofill.augmented.PresentationParams getPresentationParams() {
        return this.mProxy.getSmartSuggestionParams();
    }

    java.lang.String proxyToString() {
        return "FillRequest[act=" + getActivityComponent().flattenToShortString() + ", id=" + this.mProxy.getFocusedId() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public FillRequest(android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy autofillProxy, android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest) {
        this.mProxy = autofillProxy;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mProxy);
        this.mInlineSuggestionsRequest = inlineSuggestionsRequest;
    }

    public android.view.inputmethod.InlineSuggestionsRequest getInlineSuggestionsRequest() {
        return this.mInlineSuggestionsRequest;
    }

    public java.lang.String toString() {
        return "FillRequest { proxy = " + proxyToString() + ", inlineSuggestionsRequest = " + this.mInlineSuggestionsRequest + " }";
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
