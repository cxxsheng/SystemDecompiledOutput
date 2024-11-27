package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class NewInstanceSchemaLite implements com.android.framework.protobuf.NewInstanceSchema {
    NewInstanceSchemaLite() {
    }

    @Override // com.android.framework.protobuf.NewInstanceSchema
    public java.lang.Object newInstance(java.lang.Object obj) {
        return ((com.android.framework.protobuf.GeneratedMessageLite) obj).newMutableInstance();
    }
}
