package android.view.autofill;

/* loaded from: classes4.dex */
public final class Helper {
    public static boolean sDebug = false;
    public static boolean sVerbose = false;

    public static void appendRedacted(java.lang.StringBuilder sb, java.lang.CharSequence charSequence) {
        sb.append(getRedacted(charSequence));
    }

    public static java.lang.String getRedacted(java.lang.CharSequence charSequence) {
        return charSequence == null ? "null" : charSequence.length() + "_chars";
    }

    public static void appendRedacted(java.lang.StringBuilder sb, java.lang.String[] strArr) {
        if (strArr == null) {
            sb.append("N/A");
            return;
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        for (java.lang.String str : strArr) {
            sb.append(" '");
            appendRedacted(sb, str);
            sb.append("'");
        }
        sb.append(" ]");
    }

    public static android.view.autofill.AutofillId[] toArray(java.util.Collection<android.view.autofill.AutofillId> collection) {
        if (collection == null) {
            return new android.view.autofill.AutofillId[0];
        }
        android.view.autofill.AutofillId[] autofillIdArr = new android.view.autofill.AutofillId[collection.size()];
        collection.toArray(autofillIdArr);
        return autofillIdArr;
    }

    public static <T> java.util.ArrayList<T> toList(java.util.Set<T> set) {
        if (set == null) {
            return null;
        }
        return new java.util.ArrayList<>(set);
    }

    private Helper() {
        throw new java.lang.UnsupportedOperationException("contains static members only");
    }
}
