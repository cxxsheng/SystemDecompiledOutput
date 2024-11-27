package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class NewInstanceSchemas {
    private static final com.android.framework.protobuf.NewInstanceSchema FULL_SCHEMA = loadSchemaForFullRuntime();
    private static final com.android.framework.protobuf.NewInstanceSchema LITE_SCHEMA = new com.android.framework.protobuf.NewInstanceSchemaLite();

    NewInstanceSchemas() {
    }

    static com.android.framework.protobuf.NewInstanceSchema full() {
        return FULL_SCHEMA;
    }

    static com.android.framework.protobuf.NewInstanceSchema lite() {
        return LITE_SCHEMA;
    }

    private static com.android.framework.protobuf.NewInstanceSchema loadSchemaForFullRuntime() {
        try {
            return (com.android.framework.protobuf.NewInstanceSchema) java.lang.Class.forName("com.android.framework.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new java.lang.Class[0]).newInstance(new java.lang.Object[0]);
        } catch (java.lang.Exception e) {
            return null;
        }
    }
}
