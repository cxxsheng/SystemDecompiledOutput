package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class EncodeException extends java.lang.Exception {
    public static final int ERROR_EXCEED_SIZE = 1;
    public static final int ERROR_UNENCODABLE = 0;
    private int mError;

    public EncodeException() {
        this.mError = 0;
    }

    public EncodeException(java.lang.String str) {
        super(str);
        this.mError = 0;
    }

    public EncodeException(java.lang.String str, int i) {
        super(str);
        this.mError = 0;
        this.mError = i;
    }

    public EncodeException(char c) {
        super("Unencodable char: '" + c + "'");
        this.mError = 0;
    }

    public int getError() {
        return this.mError;
    }
}
