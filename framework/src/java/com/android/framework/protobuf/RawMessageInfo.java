package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class RawMessageInfo implements com.android.framework.protobuf.MessageInfo {
    private final com.android.framework.protobuf.MessageLite defaultInstance;
    private final int flags;
    private final java.lang.String info;
    private final java.lang.Object[] objects;

    RawMessageInfo(com.android.framework.protobuf.MessageLite messageLite, java.lang.String str, java.lang.Object[] objArr) {
        this.defaultInstance = messageLite;
        this.info = str;
        this.objects = objArr;
        char charAt = str.charAt(0);
        if (charAt < 55296) {
            this.flags = charAt;
            return;
        }
        int i = charAt & 8191;
        int i2 = 13;
        int i3 = 1;
        while (true) {
            int i4 = i3 + 1;
            char charAt2 = str.charAt(i3);
            if (charAt2 >= 55296) {
                i |= (charAt2 & 8191) << i2;
                i2 += 13;
                i3 = i4;
            } else {
                this.flags = i | (charAt2 << i2);
                return;
            }
        }
    }

    java.lang.String getStringInfo() {
        return this.info;
    }

    java.lang.Object[] getObjects() {
        return this.objects;
    }

    @Override // com.android.framework.protobuf.MessageInfo
    public com.android.framework.protobuf.MessageLite getDefaultInstance() {
        return this.defaultInstance;
    }

    @Override // com.android.framework.protobuf.MessageInfo
    public com.android.framework.protobuf.ProtoSyntax getSyntax() {
        return (this.flags & 1) == 1 ? com.android.framework.protobuf.ProtoSyntax.PROTO2 : com.android.framework.protobuf.ProtoSyntax.PROTO3;
    }

    @Override // com.android.framework.protobuf.MessageInfo
    public boolean isMessageSetWireFormat() {
        return (this.flags & 2) == 2;
    }
}
