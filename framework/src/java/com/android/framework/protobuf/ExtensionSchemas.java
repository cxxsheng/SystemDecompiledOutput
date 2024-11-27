package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class ExtensionSchemas {
    private static final com.android.framework.protobuf.ExtensionSchema<?> LITE_SCHEMA = new com.android.framework.protobuf.ExtensionSchemaLite();
    private static final com.android.framework.protobuf.ExtensionSchema<?> FULL_SCHEMA = loadSchemaForFullRuntime();

    ExtensionSchemas() {
    }

    private static com.android.framework.protobuf.ExtensionSchema<?> loadSchemaForFullRuntime() {
        try {
            return (com.android.framework.protobuf.ExtensionSchema) java.lang.Class.forName("com.android.framework.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new java.lang.Class[0]).newInstance(new java.lang.Object[0]);
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    static com.android.framework.protobuf.ExtensionSchema<?> lite() {
        return LITE_SCHEMA;
    }

    static com.android.framework.protobuf.ExtensionSchema<?> full() {
        if (FULL_SCHEMA == null) {
            throw new java.lang.IllegalStateException("Protobuf runtime is not correctly loaded.");
        }
        return FULL_SCHEMA;
    }
}
