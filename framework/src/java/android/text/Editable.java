package android.text;

/* loaded from: classes3.dex */
public interface Editable extends java.lang.CharSequence, android.text.GetChars, android.text.Spannable, java.lang.Appendable {
    @Override // java.lang.Appendable
    android.text.Editable append(char c);

    @Override // java.lang.Appendable
    android.text.Editable append(java.lang.CharSequence charSequence);

    @Override // java.lang.Appendable
    android.text.Editable append(java.lang.CharSequence charSequence, int i, int i2);

    void clear();

    void clearSpans();

    android.text.Editable delete(int i, int i2);

    android.text.InputFilter[] getFilters();

    android.text.Editable insert(int i, java.lang.CharSequence charSequence);

    android.text.Editable insert(int i, java.lang.CharSequence charSequence, int i2, int i3);

    android.text.Editable replace(int i, int i2, java.lang.CharSequence charSequence);

    android.text.Editable replace(int i, int i2, java.lang.CharSequence charSequence, int i3, int i4);

    void setFilters(android.text.InputFilter[] inputFilterArr);

    public static class Factory {
        private static android.text.Editable.Factory sInstance = new android.text.Editable.Factory();

        public static android.text.Editable.Factory getInstance() {
            return sInstance;
        }

        public android.text.Editable newEditable(java.lang.CharSequence charSequence) {
            return new android.text.SpannableStringBuilder(charSequence);
        }
    }
}
