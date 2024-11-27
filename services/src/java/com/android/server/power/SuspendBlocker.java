package com.android.server.power;

/* loaded from: classes2.dex */
interface SuspendBlocker {
    void acquire();

    void acquire(java.lang.String str);

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j);

    void release();

    void release(java.lang.String str);
}
