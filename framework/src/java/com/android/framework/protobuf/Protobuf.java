package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class Protobuf {
    private static final com.android.framework.protobuf.Protobuf INSTANCE = new com.android.framework.protobuf.Protobuf();
    private final java.util.concurrent.ConcurrentMap<java.lang.Class<?>, com.android.framework.protobuf.Schema<?>> schemaCache = new java.util.concurrent.ConcurrentHashMap();
    private final com.android.framework.protobuf.SchemaFactory schemaFactory = new com.android.framework.protobuf.ManifestSchemaFactory();

    public static com.android.framework.protobuf.Protobuf getInstance() {
        return INSTANCE;
    }

    public <T> void writeTo(T t, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        schemaFor((com.android.framework.protobuf.Protobuf) t).writeTo(t, writer);
    }

    public <T> void mergeFrom(T t, com.android.framework.protobuf.Reader reader) throws java.io.IOException {
        mergeFrom(t, reader, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry());
    }

    public <T> void mergeFrom(T t, com.android.framework.protobuf.Reader reader, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        schemaFor((com.android.framework.protobuf.Protobuf) t).mergeFrom(t, reader, extensionRegistryLite);
    }

    public <T> void makeImmutable(T t) {
        schemaFor((com.android.framework.protobuf.Protobuf) t).makeImmutable(t);
    }

    <T> boolean isInitialized(T t) {
        return schemaFor((com.android.framework.protobuf.Protobuf) t).isInitialized(t);
    }

    public <T> com.android.framework.protobuf.Schema<T> schemaFor(java.lang.Class<T> cls) {
        com.android.framework.protobuf.Internal.checkNotNull(cls, "messageType");
        com.android.framework.protobuf.Schema<T> schema = (com.android.framework.protobuf.Schema) this.schemaCache.get(cls);
        if (schema == null) {
            com.android.framework.protobuf.Schema<T> createSchema = this.schemaFactory.createSchema(cls);
            com.android.framework.protobuf.Schema<T> schema2 = (com.android.framework.protobuf.Schema<T>) registerSchema(cls, createSchema);
            if (schema2 != null) {
                return schema2;
            }
            return createSchema;
        }
        return schema;
    }

    public <T> com.android.framework.protobuf.Schema<T> schemaFor(T t) {
        return schemaFor((java.lang.Class) t.getClass());
    }

    public com.android.framework.protobuf.Schema<?> registerSchema(java.lang.Class<?> cls, com.android.framework.protobuf.Schema<?> schema) {
        com.android.framework.protobuf.Internal.checkNotNull(cls, "messageType");
        com.android.framework.protobuf.Internal.checkNotNull(schema, "schema");
        return this.schemaCache.putIfAbsent(cls, schema);
    }

    public com.android.framework.protobuf.Schema<?> registerSchemaOverride(java.lang.Class<?> cls, com.android.framework.protobuf.Schema<?> schema) {
        com.android.framework.protobuf.Internal.checkNotNull(cls, "messageType");
        com.android.framework.protobuf.Internal.checkNotNull(schema, "schema");
        return this.schemaCache.put(cls, schema);
    }

    private Protobuf() {
    }

    int getTotalSchemaSize() {
        int i = 0;
        for (com.android.framework.protobuf.Schema<?> schema : this.schemaCache.values()) {
            if (schema instanceof com.android.framework.protobuf.MessageSchema) {
                i += ((com.android.framework.protobuf.MessageSchema) schema).getSchemaSize();
            }
        }
        return i;
    }
}
