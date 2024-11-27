package android.text.method;

/* loaded from: classes3.dex */
public class TranslationTransformationMethod implements android.text.method.TransformationMethod2 {
    private static final java.util.regex.Pattern PATTERN_WHITESPACE = java.util.regex.Pattern.compile("\\s+");
    private static final java.lang.String TAG = "TranslationTransformationMethod";
    private boolean mAllowLengthChanges;
    private android.text.method.TransformationMethod mOriginalTranslationMethod;
    private final android.view.translation.ViewTranslationResponse mTranslationResponse;

    public TranslationTransformationMethod(android.view.translation.ViewTranslationResponse viewTranslationResponse, android.text.method.TransformationMethod transformationMethod) {
        this.mTranslationResponse = viewTranslationResponse;
        this.mOriginalTranslationMethod = transformationMethod;
    }

    public android.text.method.TransformationMethod getOriginalTransformationMethod() {
        return this.mOriginalTranslationMethod;
    }

    public android.view.translation.ViewTranslationResponse getViewTranslationResponse() {
        return this.mTranslationResponse;
    }

    @Override // android.text.method.TransformationMethod
    public java.lang.CharSequence getTransformation(java.lang.CharSequence charSequence, android.view.View view) {
        java.lang.CharSequence charSequence2;
        if (!this.mAllowLengthChanges) {
            android.util.Log.w(TAG, "Caller did not enable length changes; not transforming to translated text");
            return charSequence;
        }
        android.view.translation.TranslationResponseValue value = this.mTranslationResponse.getValue(android.view.translation.ViewTranslationRequest.ID_TEXT);
        if (value.getStatusCode() == 0) {
            charSequence2 = value.getText();
        } else {
            charSequence2 = "";
        }
        if (android.text.TextUtils.isEmpty(charSequence2) || isWhitespace(charSequence2.toString())) {
            return charSequence;
        }
        return charSequence2;
    }

    @Override // android.text.method.TransformationMethod
    public void onFocusChanged(android.view.View view, java.lang.CharSequence charSequence, boolean z, int i, android.graphics.Rect rect) {
    }

    @Override // android.text.method.TransformationMethod2
    public void setLengthChangesAllowed(boolean z) {
        this.mAllowLengthChanges = z;
    }

    private boolean isWhitespace(java.lang.String str) {
        return PATTERN_WHITESPACE.matcher(str.substring(0, str.length())).matches();
    }
}
