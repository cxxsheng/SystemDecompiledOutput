package android.text;

/* loaded from: classes3.dex */
public interface Spannable extends android.text.Spanned {
    void removeSpan(java.lang.Object obj);

    void setSpan(java.lang.Object obj, int i, int i2, int i3);

    default void removeSpan(java.lang.Object obj, int i) {
        removeSpan(obj);
    }

    public static class Factory {
        private static android.text.Spannable.Factory sInstance = new android.text.Spannable.Factory();

        public static android.text.Spannable.Factory getInstance() {
            return sInstance;
        }

        public android.text.Spannable newSpannable(java.lang.CharSequence charSequence) {
            return new android.text.SpannableString(charSequence);
        }
    }
}
