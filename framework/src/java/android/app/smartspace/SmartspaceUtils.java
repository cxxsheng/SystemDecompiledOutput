package android.app.smartspace;

/* loaded from: classes.dex */
public final class SmartspaceUtils {
    private SmartspaceUtils() {
    }

    public static boolean isEmpty(android.app.smartspace.uitemplatedata.Text text) {
        return text == null || android.text.TextUtils.isEmpty(text.getText());
    }

    public static boolean isEqual(android.app.smartspace.uitemplatedata.Text text, android.app.smartspace.uitemplatedata.Text text2) {
        if (text == null && text2 == null) {
            return true;
        }
        if (text == null || text2 == null) {
            return false;
        }
        return text.equals(text2);
    }

    public static boolean isEqual(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        if (charSequence == null && charSequence2 == null) {
            return true;
        }
        if (charSequence == null || charSequence2 == null) {
            return false;
        }
        return charSequence.toString().contentEquals(charSequence2);
    }
}
