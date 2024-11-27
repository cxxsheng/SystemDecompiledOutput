package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
interface MessageInfoFactory {
    boolean isSupported(java.lang.Class<?> cls);

    com.android.framework.protobuf.MessageInfo messageInfoFor(java.lang.Class<?> cls);
}
