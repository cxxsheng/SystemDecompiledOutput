package com.android.internal.org.bouncycastle.util;

/* loaded from: classes4.dex */
public final class Strings {
    private static java.lang.String LINE_SEPARATOR;

    static {
        try {
            LINE_SEPARATOR = (java.lang.String) java.security.AccessController.doPrivileged(new java.security.PrivilegedAction<java.lang.String>() { // from class: com.android.internal.org.bouncycastle.util.Strings.1
                @Override // java.security.PrivilegedAction
                public java.lang.String run() {
                    return java.lang.System.getProperty("line.separator");
                }
            });
        } catch (java.lang.Exception e) {
            try {
                LINE_SEPARATOR = java.lang.String.format("%n", new java.lang.Object[0]);
            } catch (java.lang.Exception e2) {
                LINE_SEPARATOR = "\n";
            }
        }
    }

    public static java.lang.String fromUTF8ByteArray(byte[] bArr) {
        char[] cArr = new char[bArr.length];
        int transcodeToUTF16 = com.android.internal.org.bouncycastle.util.encoders.UTF8.transcodeToUTF16(bArr, cArr);
        if (transcodeToUTF16 < 0) {
            throw new java.lang.IllegalArgumentException("Invalid UTF-8 input");
        }
        return new java.lang.String(cArr, 0, transcodeToUTF16);
    }

    public static byte[] toUTF8ByteArray(java.lang.String str) {
        return toUTF8ByteArray(str.toCharArray());
    }

    public static byte[] toUTF8ByteArray(char[] cArr) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            toUTF8ByteArray(cArr, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalStateException("cannot encode string to byte array!");
        }
    }

    public static void toUTF8ByteArray(char[] cArr, java.io.OutputStream outputStream) throws java.io.IOException {
        int i = 0;
        while (i < cArr.length) {
            char c = cArr[i];
            if (c < 128) {
                outputStream.write(c);
            } else if (c < 2048) {
                outputStream.write((c >> 6) | 192);
                outputStream.write((c & '?') | 128);
            } else if (c >= 55296 && c <= 57343) {
                i++;
                if (i >= cArr.length) {
                    throw new java.lang.IllegalStateException("invalid UTF-16 codepoint");
                }
                char c2 = cArr[i];
                if (c > 56319) {
                    throw new java.lang.IllegalStateException("invalid UTF-16 codepoint");
                }
                int i2 = (((c & 1023) << 10) | (c2 & 1023)) + 65536;
                outputStream.write((i2 >> 18) | 240);
                outputStream.write(((i2 >> 12) & 63) | 128);
                outputStream.write(((i2 >> 6) & 63) | 128);
                outputStream.write((i2 & 63) | 128);
            } else {
                outputStream.write((c >> '\f') | 224);
                outputStream.write(((c >> 6) & 63) | 128);
                outputStream.write((c & '?') | 128);
            }
            i++;
        }
    }

    public static java.lang.String toUpperCase(java.lang.String str) {
        char[] charArray = str.toCharArray();
        boolean z = false;
        for (int i = 0; i != charArray.length; i++) {
            char c = charArray[i];
            if ('a' <= c && 'z' >= c) {
                charArray[i] = (char) ((c - 'a') + 65);
                z = true;
            }
        }
        if (z) {
            return new java.lang.String(charArray);
        }
        return str;
    }

    public static java.lang.String toLowerCase(java.lang.String str) {
        char[] charArray = str.toCharArray();
        boolean z = false;
        for (int i = 0; i != charArray.length; i++) {
            char c = charArray[i];
            if ('A' <= c && 'Z' >= c) {
                charArray[i] = (char) ((c - 'A') + 97);
                z = true;
            }
        }
        if (z) {
            return new java.lang.String(charArray);
        }
        return str;
    }

    public static byte[] toByteArray(char[] cArr) {
        int length = cArr.length;
        byte[] bArr = new byte[length];
        for (int i = 0; i != length; i++) {
            bArr[i] = (byte) cArr[i];
        }
        return bArr;
    }

    public static byte[] toByteArray(java.lang.String str) {
        int length = str.length();
        byte[] bArr = new byte[length];
        for (int i = 0; i != length; i++) {
            bArr[i] = (byte) str.charAt(i);
        }
        return bArr;
    }

    public static int toByteArray(java.lang.String str, byte[] bArr, int i) {
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            bArr[i + i2] = (byte) str.charAt(i2);
        }
        return length;
    }

    public static java.lang.String fromByteArray(byte[] bArr) {
        return new java.lang.String(asCharArray(bArr));
    }

    public static char[] asCharArray(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[length];
        for (int i = 0; i != length; i++) {
            cArr[i] = (char) (bArr[i] & 255);
        }
        return cArr;
    }

    public static java.lang.String[] split(java.lang.String str, char c) {
        int i;
        java.util.Vector vector = new java.util.Vector();
        boolean z = true;
        while (true) {
            if (!z) {
                break;
            }
            int indexOf = str.indexOf(c);
            if (indexOf > 0) {
                vector.addElement(str.substring(0, indexOf));
                str = str.substring(indexOf + 1);
            } else {
                vector.addElement(str);
                z = false;
            }
        }
        int size = vector.size();
        java.lang.String[] strArr = new java.lang.String[size];
        for (i = 0; i != size; i++) {
            strArr[i] = (java.lang.String) vector.elementAt(i);
        }
        return strArr;
    }

    public static com.android.internal.org.bouncycastle.util.StringList newList() {
        return new com.android.internal.org.bouncycastle.util.Strings.StringListImpl();
    }

    public static java.lang.String lineSeparator() {
        return LINE_SEPARATOR;
    }

    private static class StringListImpl extends java.util.ArrayList<java.lang.String> implements com.android.internal.org.bouncycastle.util.StringList {
        private StringListImpl() {
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.List, com.android.internal.org.bouncycastle.util.StringList
        public /* bridge */ /* synthetic */ java.lang.String get(int i) {
            return (java.lang.String) super.get(i);
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean add(java.lang.String str) {
            return super.add((com.android.internal.org.bouncycastle.util.Strings.StringListImpl) str);
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
        public java.lang.String set(int i, java.lang.String str) {
            return (java.lang.String) super.set(i, (int) str);
        }

        @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
        public void add(int i, java.lang.String str) {
            super.add(i, (int) str);
        }

        @Override // com.android.internal.org.bouncycastle.util.StringList
        public java.lang.String[] toStringArray() {
            int size = size();
            java.lang.String[] strArr = new java.lang.String[size];
            for (int i = 0; i != size; i++) {
                strArr[i] = (java.lang.String) get(i);
            }
            return strArr;
        }

        @Override // com.android.internal.org.bouncycastle.util.StringList
        public java.lang.String[] toStringArray(int i, int i2) {
            java.lang.String[] strArr = new java.lang.String[i2 - i];
            for (int i3 = i; i3 != size() && i3 != i2; i3++) {
                strArr[i3 - i] = (java.lang.String) get(i3);
            }
            return strArr;
        }
    }
}
