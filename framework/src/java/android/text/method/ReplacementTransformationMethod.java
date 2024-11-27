package android.text.method;

/* loaded from: classes3.dex */
public abstract class ReplacementTransformationMethod implements android.text.method.TransformationMethod {
    protected abstract char[] getOriginal();

    protected abstract char[] getReplacement();

    @Override // android.text.method.TransformationMethod
    public java.lang.CharSequence getTransformation(java.lang.CharSequence charSequence, android.view.View view) {
        char[] original = getOriginal();
        char[] replacement = getReplacement();
        if (!(charSequence instanceof android.text.Editable)) {
            int length = original.length;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = true;
                    break;
                }
                if (android.text.TextUtils.indexOf(charSequence, original[i]) >= 0) {
                    break;
                }
                i++;
            }
            if (z) {
                return charSequence;
            }
            if (!(charSequence instanceof android.text.Spannable)) {
                if (charSequence instanceof android.text.Spanned) {
                    return new android.text.SpannedString(new android.text.method.ReplacementTransformationMethod.SpannedReplacementCharSequence((android.text.Spanned) charSequence, original, replacement));
                }
                return new android.text.method.ReplacementTransformationMethod.ReplacementCharSequence(charSequence, original, replacement).toString();
            }
        }
        if (charSequence instanceof android.text.Spanned) {
            return new android.text.method.ReplacementTransformationMethod.SpannedReplacementCharSequence((android.text.Spanned) charSequence, original, replacement);
        }
        return new android.text.method.ReplacementTransformationMethod.ReplacementCharSequence(charSequence, original, replacement);
    }

    @Override // android.text.method.TransformationMethod
    public void onFocusChanged(android.view.View view, java.lang.CharSequence charSequence, boolean z, int i, android.graphics.Rect rect) {
    }

    private static class ReplacementCharSequence implements java.lang.CharSequence, android.text.GetChars {
        private char[] mOriginal;
        private char[] mReplacement;
        private java.lang.CharSequence mSource;

        public ReplacementCharSequence(java.lang.CharSequence charSequence, char[] cArr, char[] cArr2) {
            this.mSource = charSequence;
            this.mOriginal = cArr;
            this.mReplacement = cArr2;
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mSource.length();
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            char charAt = this.mSource.charAt(i);
            int length = this.mOriginal.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (charAt == this.mOriginal[i2]) {
                    charAt = this.mReplacement[i2];
                }
            }
            return charAt;
        }

        @Override // java.lang.CharSequence
        public java.lang.CharSequence subSequence(int i, int i2) {
            char[] cArr = new char[i2 - i];
            getChars(i, i2, cArr, 0);
            return new java.lang.String(cArr);
        }

        @Override // java.lang.CharSequence
        public java.lang.String toString() {
            char[] cArr = new char[length()];
            getChars(0, length(), cArr, 0);
            return new java.lang.String(cArr);
        }

        @Override // android.text.GetChars
        public void getChars(int i, int i2, char[] cArr, int i3) {
            android.text.TextUtils.getChars(this.mSource, i, i2, cArr, i3);
            int i4 = (i2 - i) + i3;
            int length = this.mOriginal.length;
            while (i3 < i4) {
                char c = cArr[i3];
                for (int i5 = 0; i5 < length; i5++) {
                    if (c == this.mOriginal[i5]) {
                        cArr[i3] = this.mReplacement[i5];
                    }
                }
                i3++;
            }
        }
    }

    private static class SpannedReplacementCharSequence extends android.text.method.ReplacementTransformationMethod.ReplacementCharSequence implements android.text.Spanned {
        private android.text.Spanned mSpanned;

        public SpannedReplacementCharSequence(android.text.Spanned spanned, char[] cArr, char[] cArr2) {
            super(spanned, cArr, cArr2);
            this.mSpanned = spanned;
        }

        @Override // android.text.method.ReplacementTransformationMethod.ReplacementCharSequence, java.lang.CharSequence
        public java.lang.CharSequence subSequence(int i, int i2) {
            return new android.text.SpannedString(this).subSequence(i, i2);
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
}
