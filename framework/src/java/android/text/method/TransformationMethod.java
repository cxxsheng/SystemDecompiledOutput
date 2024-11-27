package android.text.method;

/* loaded from: classes3.dex */
public interface TransformationMethod {
    java.lang.CharSequence getTransformation(java.lang.CharSequence charSequence, android.view.View view);

    void onFocusChanged(android.view.View view, java.lang.CharSequence charSequence, boolean z, int i, android.graphics.Rect rect);
}
