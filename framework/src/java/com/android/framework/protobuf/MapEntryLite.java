package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public class MapEntryLite<K, V> {
    private static final int KEY_FIELD_NUMBER = 1;
    private static final int VALUE_FIELD_NUMBER = 2;
    private final K key;
    private final com.android.framework.protobuf.MapEntryLite.Metadata<K, V> metadata;
    private final V value;

    static class Metadata<K, V> {
        public final K defaultKey;
        public final V defaultValue;
        public final com.android.framework.protobuf.WireFormat.FieldType keyType;
        public final com.android.framework.protobuf.WireFormat.FieldType valueType;

        public Metadata(com.android.framework.protobuf.WireFormat.FieldType fieldType, K k, com.android.framework.protobuf.WireFormat.FieldType fieldType2, V v) {
            this.keyType = fieldType;
            this.defaultKey = k;
            this.valueType = fieldType2;
            this.defaultValue = v;
        }
    }

    private MapEntryLite(com.android.framework.protobuf.WireFormat.FieldType fieldType, K k, com.android.framework.protobuf.WireFormat.FieldType fieldType2, V v) {
        this.metadata = new com.android.framework.protobuf.MapEntryLite.Metadata<>(fieldType, k, fieldType2, v);
        this.key = k;
        this.value = v;
    }

    private MapEntryLite(com.android.framework.protobuf.MapEntryLite.Metadata<K, V> metadata, K k, V v) {
        this.metadata = metadata;
        this.key = k;
        this.value = v;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public static <K, V> com.android.framework.protobuf.MapEntryLite<K, V> newDefaultInstance(com.android.framework.protobuf.WireFormat.FieldType fieldType, K k, com.android.framework.protobuf.WireFormat.FieldType fieldType2, V v) {
        return new com.android.framework.protobuf.MapEntryLite<>(fieldType, k, fieldType2, v);
    }

    static <K, V> void writeTo(com.android.framework.protobuf.CodedOutputStream codedOutputStream, com.android.framework.protobuf.MapEntryLite.Metadata<K, V> metadata, K k, V v) throws java.io.IOException {
        com.android.framework.protobuf.FieldSet.writeElement(codedOutputStream, metadata.keyType, 1, k);
        com.android.framework.protobuf.FieldSet.writeElement(codedOutputStream, metadata.valueType, 2, v);
    }

    static <K, V> int computeSerializedSize(com.android.framework.protobuf.MapEntryLite.Metadata<K, V> metadata, K k, V v) {
        return com.android.framework.protobuf.FieldSet.computeElementSize(metadata.keyType, 1, k) + com.android.framework.protobuf.FieldSet.computeElementSize(metadata.valueType, 2, v);
    }

    static <T> T parseField(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.WireFormat.FieldType fieldType, T t) throws java.io.IOException {
        switch (fieldType) {
            case com.android.framework.protobuf.WireFormat.FieldType.MESSAGE:
                com.android.framework.protobuf.MessageLite.Builder builder = ((com.android.framework.protobuf.MessageLite) t).toBuilder();
                codedInputStream.readMessage(builder, extensionRegistryLite);
                return (T) builder.buildPartial();
            case com.android.framework.protobuf.WireFormat.FieldType.ENUM:
                return (T) java.lang.Integer.valueOf(codedInputStream.readEnum());
            case com.android.framework.protobuf.WireFormat.FieldType.GROUP:
                throw new java.lang.RuntimeException("Groups are not allowed in maps.");
            default:
                return (T) com.android.framework.protobuf.FieldSet.readPrimitiveField(codedInputStream, fieldType, true);
        }
    }

    public void serializeTo(com.android.framework.protobuf.CodedOutputStream codedOutputStream, int i, K k, V v) throws java.io.IOException {
        codedOutputStream.writeTag(i, 2);
        codedOutputStream.writeUInt32NoTag(computeSerializedSize(this.metadata, k, v));
        writeTo(codedOutputStream, this.metadata, k, v);
    }

    public int computeMessageSize(int i, K k, V v) {
        return com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) + com.android.framework.protobuf.CodedOutputStream.computeLengthDelimitedFieldSize(computeSerializedSize(this.metadata, k, v));
    }

    public java.util.Map.Entry<K, V> parseEntry(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        return parseEntry(byteString.newCodedInput(), this.metadata, extensionRegistryLite);
    }

    static <K, V> java.util.Map.Entry<K, V> parseEntry(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.MapEntryLite.Metadata<K, V> metadata, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        java.lang.Object obj = metadata.defaultKey;
        java.lang.Object obj2 = metadata.defaultValue;
        while (true) {
            int readTag = codedInputStream.readTag();
            if (readTag == 0) {
                break;
            }
            if (readTag == com.android.framework.protobuf.WireFormat.makeTag(1, metadata.keyType.getWireType())) {
                obj = parseField(codedInputStream, extensionRegistryLite, metadata.keyType, obj);
            } else if (readTag == com.android.framework.protobuf.WireFormat.makeTag(2, metadata.valueType.getWireType())) {
                obj2 = parseField(codedInputStream, extensionRegistryLite, metadata.valueType, obj2);
            } else if (!codedInputStream.skipField(readTag)) {
                break;
            }
        }
        return new java.util.AbstractMap.SimpleImmutableEntry(obj, obj2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void parseInto(com.android.framework.protobuf.MapFieldLite<K, V> mapFieldLite, com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        int pushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
        java.lang.Object obj = this.metadata.defaultKey;
        java.lang.Object obj2 = this.metadata.defaultValue;
        while (true) {
            int readTag = codedInputStream.readTag();
            if (readTag == 0) {
                break;
            }
            if (readTag == com.android.framework.protobuf.WireFormat.makeTag(1, this.metadata.keyType.getWireType())) {
                obj = parseField(codedInputStream, extensionRegistryLite, this.metadata.keyType, obj);
            } else if (readTag == com.android.framework.protobuf.WireFormat.makeTag(2, this.metadata.valueType.getWireType())) {
                obj2 = parseField(codedInputStream, extensionRegistryLite, this.metadata.valueType, obj2);
            } else if (!codedInputStream.skipField(readTag)) {
                break;
            }
        }
        codedInputStream.checkLastTagWas(0);
        codedInputStream.popLimit(pushLimit);
        mapFieldLite.put(obj, obj2);
    }

    com.android.framework.protobuf.MapEntryLite.Metadata<K, V> getMetadata() {
        return this.metadata;
    }
}
