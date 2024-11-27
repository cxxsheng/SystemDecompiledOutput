package com.android.internal.util;

/* loaded from: classes5.dex */
public class LocalLog {
    private final java.lang.String mTag;
    private final int mMaxLines = 20;
    private final java.util.ArrayList<java.lang.String> mLines = new java.util.ArrayList<>(20);

    public LocalLog(java.lang.String str) {
        this.mTag = str;
    }

    public void w(java.lang.String str) {
        synchronized (this.mLines) {
            android.util.Slog.w(this.mTag, str);
            if (this.mLines.size() >= 20) {
                this.mLines.remove(0);
            }
            this.mLines.add(str);
        }
    }

    public boolean dump(android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String str) {
        synchronized (this.mLines) {
            if (this.mLines.size() <= 0) {
                return false;
            }
            if (str != null) {
                indentingPrintWriter.println(str);
                indentingPrintWriter.increaseIndent();
            }
            for (int i = 0; i < this.mLines.size(); i++) {
                indentingPrintWriter.println(this.mLines.get(i));
            }
            if (str != null) {
                indentingPrintWriter.decreaseIndent();
            }
            return true;
        }
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        synchronized (this.mLines) {
            for (int i = 0; i < this.mLines.size(); i++) {
                protoOutputStream.write(2237677961217L, this.mLines.get(i));
            }
        }
        protoOutputStream.end(start);
    }
}
