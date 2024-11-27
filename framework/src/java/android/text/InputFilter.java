package android.text;

/* loaded from: classes3.dex */
public interface InputFilter {
    java.lang.CharSequence filter(java.lang.CharSequence charSequence, int i, int i2, android.text.Spanned spanned, int i3, int i4);

    public static class AllCaps implements android.text.InputFilter {
        private final java.util.Locale mLocale;

        public AllCaps() {
            this.mLocale = null;
        }

        public AllCaps(java.util.Locale locale) {
            com.android.internal.util.Preconditions.checkNotNull(locale);
            this.mLocale = locale;
        }

        @Override // android.text.InputFilter
        public java.lang.CharSequence filter(java.lang.CharSequence charSequence, int i, int i2, android.text.Spanned spanned, int i3, int i4) {
            boolean z;
            java.lang.CharSequence upperCase;
            android.text.InputFilter.AllCaps.CharSequenceWrapper charSequenceWrapper = new android.text.InputFilter.AllCaps.CharSequenceWrapper(charSequence, i, i2);
            int i5 = i2 - i;
            boolean z2 = false;
            int i6 = 0;
            while (i6 < i5) {
                int codePointAt = java.lang.Character.codePointAt(charSequenceWrapper, i6);
                if (!java.lang.Character.isLowerCase(codePointAt) && !java.lang.Character.isTitleCase(codePointAt)) {
                    i6 += java.lang.Character.charCount(codePointAt);
                } else {
                    z2 = true;
                    break;
                }
            }
            if (z2 && (upperCase = android.text.TextUtils.toUpperCase(this.mLocale, charSequenceWrapper, (z = charSequence instanceof android.text.Spanned))) != charSequenceWrapper) {
                return z ? new android.text.SpannableString(upperCase) : upperCase.toString();
            }
            return null;
        }

        private static class CharSequenceWrapper implements java.lang.CharSequence, android.text.Spanned {
            private final int mEnd;
            private final int mLength;
            private final java.lang.CharSequence mSource;
            private final int mStart;

            CharSequenceWrapper(java.lang.CharSequence charSequence, int i, int i2) {
                this.mSource = charSequence;
                this.mStart = i;
                this.mEnd = i2;
                this.mLength = i2 - i;
            }

            @Override // java.lang.CharSequence
            public int length() {
                return this.mLength;
            }

            @Override // java.lang.CharSequence
            public char charAt(int i) {
                if (i < 0 || i >= this.mLength) {
                    throw new java.lang.IndexOutOfBoundsException();
                }
                return this.mSource.charAt(this.mStart + i);
            }

            @Override // java.lang.CharSequence
            public java.lang.CharSequence subSequence(int i, int i2) {
                if (i < 0 || i2 < 0 || i2 > this.mLength || i > i2) {
                    throw new java.lang.IndexOutOfBoundsException();
                }
                return new android.text.InputFilter.AllCaps.CharSequenceWrapper(this.mSource, this.mStart + i, this.mStart + i2);
            }

            @Override // java.lang.CharSequence
            public java.lang.String toString() {
                return this.mSource.subSequence(this.mStart, this.mEnd).toString();
            }

            @Override // android.text.Spanned
            public <T> T[] getSpans(int i, int i2, java.lang.Class<T> cls) {
                return (T[]) ((android.text.Spanned) this.mSource).getSpans(this.mStart + i, this.mStart + i2, cls);
            }

            @Override // android.text.Spanned
            public int getSpanStart(java.lang.Object obj) {
                return ((android.text.Spanned) this.mSource).getSpanStart(obj) - this.mStart;
            }

            @Override // android.text.Spanned
            public int getSpanEnd(java.lang.Object obj) {
                return ((android.text.Spanned) this.mSource).getSpanEnd(obj) - this.mStart;
            }

            @Override // android.text.Spanned
            public int getSpanFlags(java.lang.Object obj) {
                return ((android.text.Spanned) this.mSource).getSpanFlags(obj);
            }

            @Override // android.text.Spanned
            public int nextSpanTransition(int i, int i2, java.lang.Class cls) {
                return ((android.text.Spanned) this.mSource).nextSpanTransition(this.mStart + i, this.mStart + i2, cls) - this.mStart;
            }
        }
    }

    public static class LengthFilter implements android.text.InputFilter {
        private final int mMax;

        public LengthFilter(int i) {
            this.mMax = i;
        }

        @Override // android.text.InputFilter
        public java.lang.CharSequence filter(java.lang.CharSequence charSequence, int i, int i2, android.text.Spanned spanned, int i3, int i4) {
            int length = this.mMax - (spanned.length() - (i4 - i3));
            if (length <= 0) {
                return "";
            }
            if (length >= i2 - i) {
                return null;
            }
            int i5 = length + i;
            if (java.lang.Character.isHighSurrogate(charSequence.charAt(i5 - 1)) && i5 - 1 == i) {
                return "";
            }
            return charSequence.subSequence(i, i5);
        }

        public int getMax() {
            return this.mMax;
        }
    }
}
