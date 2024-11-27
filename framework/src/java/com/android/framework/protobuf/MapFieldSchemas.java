package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class MapFieldSchemas {
    private static final com.android.framework.protobuf.MapFieldSchema FULL_SCHEMA = loadSchemaForFullRuntime();
    private static final com.android.framework.protobuf.MapFieldSchema LITE_SCHEMA = new com.android.framework.protobuf.MapFieldSchemaLite();

    MapFieldSchemas() {
    }

    static com.android.framework.protobuf.MapFieldSchema full() {
        return FULL_SCHEMA;
    }

    static com.android.framework.protobuf.MapFieldSchema lite() {
        return LITE_SCHEMA;
    }

    private static com.android.framework.protobuf.MapFieldSchema loadSchemaForFullRuntime() {
        try {
            return (com.android.framework.protobuf.MapFieldSchema) java.lang.Class.forName("com.android.framework.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new java.lang.Class[0]).newInstance(new java.lang.Object[0]);
        } catch (java.lang.Exception e) {
            return null;
        }
    }
}
