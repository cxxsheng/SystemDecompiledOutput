package android.text;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class AlteredCharSequence implements java.lang.CharSequence, android.text.GetChars {
    private char[] mChars;
    private int mEnd;
    private java.lang.CharSequence mSource;
    private int mStart;

    public static android.text.AlteredCharSequence make(java.lang.CharSequence charSequence, char[] cArr, int i, int i2) {
        if (charSequence instanceof android.text.Spanned) {
            return new android.text.AlteredCharSequence.AlteredSpanned(charSequence, cArr, i, i2);
        }
        return new android.text.AlteredCharSequence(charSequence, cArr, i, i2);
    }

    private AlteredCharSequence(java.lang.CharSequence charSequence, char[] cArr, int i, int i2) {
        this.mSource = charSequence;
        this.mChars = cArr;
        this.mStart = i;
        this.mEnd = i2;
    }

    void update(char[] cArr, int i, int i2) {
        this.mChars = cArr;
        this.mStart = i;
        this.mEnd = i2;
    }

    private static class AlteredSpanned extends android.text.AlteredCharSequence implements android.text.Spanned {
        private android.text.Spanned mSpanned;

        private AlteredSpanned(java.lang.CharSequence charSequence, char[] cArr, int i, int i2) {
            super(charSequence, cArr, i, i2);
            this.mSpanned = (android.text.Spanned) charSequence;
        }

        @Override // android.text.Spanned
        public <T> T[] getSpans(int i, int i2, java.lang.Class<T> cls) {
            return (T[]) this.mSpanned.getSpans(i, i2, cls);
        }

        @Override // android.text.Spanned
        public int getSpanStart(java.lang.Object obj) {
            return this.mSpanned.getSpanStart(obj);
        }

        @Override // android.text.Spanned
        public int getSpanEnd(java.lang.Object obj) {
            return this.mSpanned.getSpanEnd(obj);
        }

        @Override // android.text.Spanned
        public int getSpanFlags(java.lang.Object obj) {
            return this.mSpanned.getSpanFlags(obj);
        }

        @Override // android.text.Spanned
        public int nextSpanTransition(int i, int i2, java.lang.Class cls) {
            return this.mSpanned.nextSpanTransition(i, i2, cls);
        }
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        if (i >= this.mStart && i < this.mEnd) {
            return this.mChars[i - this.mStart];
        }
        return this.mSource.charAt(i);
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.mSource.length();
    }

    @Override // java.lang.CharSequence
    public java.lang.CharSequence subSequence(int i, int i2) {
        return make(this.mSource.subSequence(i, i2), this.mChars, this.mStart - i, this.mEnd - i);
    }

    @Override // android.text.GetChars
    public void getChars(int i, int i2, char[] cArr, int i3) {
        android.text.TextUtils.getChars(this.mSource, i, i2, cArr, i3);
        int max = java.lang.Math.max(this.mStart, i);
        int min = java.lang.Math.min(this.mEnd, i2);
        if (max > min) {
            java.lang.System.arraycopy(this.mChars, max - this.mStart, cArr, i3, min - max);
        }
    }

    @Override // java.lang.CharSequence
    public java.lang.String toString() {
        int length = length();
        char[] cArr = new char[length];
        getChars(0, length, cArr, 0);
        return java.lang.String.valueOf(cArr);
    }
}
