package com.android.internal.protolog.common;

/* loaded from: classes5.dex */
public class ProtoLog {
    public static boolean REQUIRE_PROTOLOGTOOL = true;

    public static void d(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, java.lang.String str, java.lang.Object... objArr) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new java.lang.UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }

    public static void v(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, java.lang.String str, java.lang.Object... objArr) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new java.lang.UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }

    public static void i(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, java.lang.String str, java.lang.Object... objArr) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new java.lang.UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }

    public static void w(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, java.lang.String str, java.lang.Object... objArr) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new java.lang.UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }

    public static void e(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, java.lang.String str, java.lang.Object... objArr) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new java.lang.UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }

    public static void wtf(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup, java.lang.String str, java.lang.Object... objArr) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new java.lang.UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
    }

    public static boolean isEnabled(com.android.internal.protolog.common.IProtoLogGroup iProtoLogGroup) {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new java.lang.UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
        return false;
    }

    public static com.android.internal.protolog.common.IProtoLog getSingleInstance() {
        if (REQUIRE_PROTOLOGTOOL) {
            throw new java.lang.UnsupportedOperationException("ProtoLog calls MUST be processed with ProtoLogTool");
        }
        return null;
    }
}
