package android.text;

/* loaded from: classes3.dex */
public class SpannableString extends android.text.SpannableStringInternal implements java.lang.CharSequence, android.text.GetChars, android.text.Spannable {
    @Override // android.text.SpannableStringInternal
    public /* bridge */ /* synthetic */ boolean equals(java.lang.Object obj) {
        return super.equals(obj);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spanned
    public /* bridge */ /* synthetic */ int getSpanEnd(java.lang.Object obj) {
        return super.getSpanEnd(obj);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spanned
    public /* bridge */ /* synthetic */ int getSpanFlags(java.lang.Object obj) {
        return super.getSpanFlags(obj);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spanned
    public /* bridge */ /* synthetic */ int getSpanStart(java.lang.Object obj) {
        return super.getSpanStart(obj);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spanned
    public /* bridge */ /* synthetic */ java.lang.Object[] getSpans(int i, int i2, java.lang.Class cls) {
        return super.getSpans(i, i2, cls);
    }

    @Override // android.text.SpannableStringInternal
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // android.text.SpannableStringInternal, android.text.Spanned
    public /* bridge */ /* synthetic */ int nextSpanTransition(int i, int i2, java.lang.Class cls) {
        return super.nextSpanTransition(i, i2, cls);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spannable
    public /* bridge */ /* synthetic */ void removeSpan(java.lang.Object obj, int i) {
        super.removeSpan(obj, i);
    }

    public SpannableString(java.lang.CharSequence charSequence, boolean z) {
        super(charSequence, 0, charSequence.length(), z);
    }

    public SpannableString(java.lang.CharSequence charSequence) {
        this(charSequence, false);
    }

    private SpannableString(java.lang.CharSequence charSequence, int i, int i2) {
        super(charSequence, i, i2, false);
    }

    public static android.text.SpannableString valueOf(java.lang.CharSequence charSequence) {
        if (charSequence instanceof android.text.SpannableString) {
            return (android.text.SpannableString) charSequence;
        }
        return new android.text.SpannableString(charSequence);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spannable
    public void setSpan(java.lang.Object obj, int i, int i2, int i3) {
        super.setSpan(obj, i, i2, i3);
    }

    @Override // android.text.SpannableStringInternal, android.text.Spannable
    public void removeSpan(java.lang.Object obj) {
        super.removeSpan(obj);
    }

    @Override // java.lang.CharSequence
    public final java.lang.CharSequence subSequence(int i, int i2) {
        return new android.text.SpannableString(this, i, i2);
    }
}
