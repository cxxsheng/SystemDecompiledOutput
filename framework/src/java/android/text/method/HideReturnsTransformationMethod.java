package android.text.method;

/* loaded from: classes3.dex */
public class HideReturnsTransformationMethod extends android.text.method.ReplacementTransformationMethod {
    private static char[] ORIGINAL = {'\r'};
    private static char[] REPLACEMENT = {65279};
    private static android.text.method.HideReturnsTransformationMethod sInstance;

    @Override // android.text.method.ReplacementTransformationMethod
    protected char[] getOriginal() {
        return ORIGINAL;
    }

    @Override // android.text.method.ReplacementTransformationMethod
    protected char[] getReplacement() {
        return REPLACEMENT;
    }

    public static android.text.method.HideReturnsTransformationMethod getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        sInstance = new android.text.method.HideReturnsTransformationMethod();
        return sInstance;
    }
}
