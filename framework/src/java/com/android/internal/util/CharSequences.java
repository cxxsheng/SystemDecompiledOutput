package com.android.internal.util;

/* loaded from: classes5.dex */
public class CharSequences {
    public static java.lang.CharSequence forAsciiBytes(final byte[] bArr) {
        return new java.lang.CharSequence() { // from class: com.android.internal.util.CharSequences.1
            @Override // java.lang.CharSequence
            public char charAt(int i) {
                return (char) bArr[i];
            }

            @Override // java.lang.CharSequence
            public int length() {
                return bArr.length;
            }

            @Override // java.lang.CharSequence
            public java.lang.CharSequence subSequence(int i, int i2) {
                return com.android.internal.util.CharSequences.forAsciiBytes(bArr, i, i2);
            }

            @Override // java.lang.CharSequence
            public java.lang.String toString() {
                return new java.lang.String(bArr);
            }
        };
    }

    public static java.lang.CharSequence forAsciiBytes(final byte[] bArr, final int i, final int i2) {
        validate(i, i2, bArr.length);
        return new java.lang.CharSequence() { // from class: com.android.internal.util.CharSequences.2
            @Override // java.lang.CharSequence
            public char charAt(int i3) {
                return (char) bArr[i3 + i];
            }

            @Override // java.lang.CharSequence
            public int length() {
                return i2 - i;
            }

            @Override // java.lang.CharSequence
            public java.lang.CharSequence subSequence(int i3, int i4) {
                int i5 = i3 - i;
                int i6 = i4 - i;
                com.android.internal.util.CharSequences.validate(i5, i6, length());
                return com.android.internal.util.CharSequences.forAsciiBytes(bArr, i5, i6);
            }

            @Override // java.lang.CharSequence
            public java.lang.String toString() {
                return new java.lang.String(bArr, i, length());
            }
        };
    }

    static void validate(int i, int i2, int i3) {
        if (i < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (i2 < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (i2 > i3) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (i > i2) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public static boolean equals(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        if (charSequence.length() != charSequence2.length()) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static int compareToIgnoreCase(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        int length = charSequence.length();
        int length2 = charSequence2.length();
        int i = length < length2 ? length : length2;
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int i4 = i2 + 1;
            int i5 = i3 + 1;
            int lowerCase = java.lang.Character.toLowerCase(charSequence.charAt(i2)) - java.lang.Character.toLowerCase(charSequence2.charAt(i3));
            if (lowerCase == 0) {
                i2 = i4;
                i3 = i5;
            } else {
                return lowerCase;
            }
        }
        return length - length2;
    }
}
