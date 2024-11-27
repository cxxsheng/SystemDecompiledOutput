package com.android.internal.os;

/* loaded from: classes4.dex */
class AndroidPrintStream extends com.android.internal.os.LoggingPrintStream {
    private final int priority;
    private final java.lang.String tag;

    public AndroidPrintStream(int i, java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("tag");
        }
        this.priority = i;
        this.tag = str;
    }

    @Override // com.android.internal.os.LoggingPrintStream
    protected void log(java.lang.String str) {
        android.util.Log.println(this.priority, this.tag, str);
    }
}
