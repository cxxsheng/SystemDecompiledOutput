package com.android.internal.pm.parsing;

/* loaded from: classes5.dex */
public class PackageParserException extends java.lang.Exception {
    public final int error;

    public PackageParserException(int i, java.lang.String str) {
        super(str);
        this.error = i;
    }

    public PackageParserException(int i, java.lang.String str, java.lang.Throwable th) {
        super(str, th);
        this.error = i;
    }
}
