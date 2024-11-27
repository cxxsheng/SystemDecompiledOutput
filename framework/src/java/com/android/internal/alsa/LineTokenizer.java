package com.android.internal.alsa;

/* loaded from: classes4.dex */
public class LineTokenizer {
    public static final int kTokenNotFound = -1;
    private final java.lang.String mDelimiters;

    public LineTokenizer(java.lang.String str) {
        this.mDelimiters = str;
    }

    int nextToken(java.lang.String str, int i) {
        int length = str.length();
        while (i < length && this.mDelimiters.indexOf(str.charAt(i)) != -1) {
            i++;
        }
        if (i < length) {
            return i;
        }
        return -1;
    }

    int nextDelimiter(java.lang.String str, int i) {
        int length = str.length();
        while (i < length && this.mDelimiters.indexOf(str.charAt(i)) == -1) {
            i++;
        }
        if (i < length) {
            return i;
        }
        return -1;
    }
}
