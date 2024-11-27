package android.text.method;

/* loaded from: classes3.dex */
public class AllCapsTransformationMethod implements android.text.method.TransformationMethod2 {
    private static final java.lang.String TAG = "AllCapsTransformationMethod";
    private boolean mEnabled;
    private java.util.Locale mLocale;

    public AllCapsTransformationMethod(android.content.Context context) {
        this.mLocale = context.getResources().getConfiguration().getLocales().get(0);
    }

    @Override // android.text.method.TransformationMethod
    public java.lang.CharSequence getTransformation(java.lang.CharSequence charSequence, android.view.View view) {
        if (!this.mEnabled) {
            android.util.Log.w(TAG, "Caller did not enable length changes; not transforming text");
            return charSequence;
        }
        java.util.Locale locale = null;
        if (charSequence == null) {
            return null;
        }
        if (view instanceof android.widget.TextView) {
            locale = ((android.widget.TextView) view).getTextLocale();
        }
        if (locale == null) {
            locale = this.mLocale;
        }
        return android.text.TextUtils.toUpperCase(locale, charSequence, charSequence instanceof android.text.Spanned);
    }

    @Override // android.text.method.TransformationMethod
    public void onFocusChanged(android.view.View view, java.lang.CharSequence charSequence, boolean z, int i, android.graphics.Rect rect) {
    }

    @Override // android.text.method.TransformationMethod2
    public void setLengthChangesAllowed(boolean z) {
        this.mEnabled = z;
    }
}
