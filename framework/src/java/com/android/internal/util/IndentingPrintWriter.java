package com.android.internal.util;

@java.lang.Deprecated
/* loaded from: classes5.dex */
public class IndentingPrintWriter extends android.util.IndentingPrintWriter {
    public IndentingPrintWriter(java.io.Writer writer, java.lang.String str) {
        super(writer, str, -1);
    }

    public IndentingPrintWriter(java.io.Writer writer, java.lang.String str, int i) {
        super(writer, str, i);
    }

    public IndentingPrintWriter(java.io.Writer writer, java.lang.String str, java.lang.String str2, int i) {
        super(writer, str, str2, i);
    }

    @Override // android.util.IndentingPrintWriter
    public com.android.internal.util.IndentingPrintWriter setIndent(java.lang.String str) {
        super.setIndent(str);
        return this;
    }

    @Override // android.util.IndentingPrintWriter
    public com.android.internal.util.IndentingPrintWriter setIndent(int i) {
        super.setIndent(i);
        return this;
    }

    @Override // android.util.IndentingPrintWriter
    public com.android.internal.util.IndentingPrintWriter increaseIndent() {
        super.increaseIndent();
        return this;
    }

    @Override // android.util.IndentingPrintWriter
    public com.android.internal.util.IndentingPrintWriter decreaseIndent() {
        super.decreaseIndent();
        return this;
    }

    public com.android.internal.util.IndentingPrintWriter printPair(java.lang.String str, java.lang.Object obj) {
        super.print(str, obj);
        return this;
    }

    public com.android.internal.util.IndentingPrintWriter printPair(java.lang.String str, java.lang.Object[] objArr) {
        super.print(str, objArr);
        return this;
    }

    public com.android.internal.util.IndentingPrintWriter printHexPair(java.lang.String str, int i) {
        super.printHexInt(str, i);
        return this;
    }
}
