package com.android.server.usb;

/* loaded from: classes2.dex */
final class DualOutputStreamDumpSink implements com.android.server.utils.EventLogger.DumpSink {
    private final com.android.internal.util.dump.DualDumpOutputStream mDumpOutputStream;
    private final long mId;

    DualOutputStreamDumpSink(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, long j) {
        this.mDumpOutputStream = dualDumpOutputStream;
        this.mId = j;
    }

    @Override // com.android.server.utils.EventLogger.DumpSink
    public void sink(java.lang.String str, java.util.List<com.android.server.utils.EventLogger.Event> list) {
        this.mDumpOutputStream.write("USB Event Log", this.mId, str);
        java.util.Iterator<com.android.server.utils.EventLogger.Event> it = list.iterator();
        while (it.hasNext()) {
            this.mDumpOutputStream.write("USB Event", this.mId, it.next().toString());
        }
    }
}
