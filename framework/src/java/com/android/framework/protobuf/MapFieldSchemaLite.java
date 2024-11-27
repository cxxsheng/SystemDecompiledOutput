package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
class MapFieldSchemaLite implements com.android.framework.protobuf.MapFieldSchema {
    MapFieldSchemaLite() {
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public java.util.Map<?, ?> forMutableMapData(java.lang.Object obj) {
        return (com.android.framework.protobuf.MapFieldLite) obj;
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public com.android.framework.protobuf.MapEntryLite.Metadata<?, ?> forMapMetadata(java.lang.Object obj) {
        return ((com.android.framework.protobuf.MapEntryLite) obj).getMetadata();
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public java.util.Map<?, ?> forMapData(java.lang.Object obj) {
        return (com.android.framework.protobuf.MapFieldLite) obj;
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public boolean isImmutable(java.lang.Object obj) {
        return !((com.android.framework.protobuf.MapFieldLite) obj).isMutable();
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public java.lang.Object toImmutable(java.lang.Object obj) {
        ((com.android.framework.protobuf.MapFieldLite) obj).makeImmutable();
        return obj;
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public java.lang.Object newMapField(java.lang.Object obj) {
        return com.android.framework.protobuf.MapFieldLite.emptyMapField().mutableCopy();
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public java.lang.Object mergeFrom(java.lang.Object obj, java.lang.Object obj2) {
        return mergeFromLite(obj, obj2);
    }

    private static <K, V> com.android.framework.protobuf.MapFieldLite<K, V> mergeFromLite(java.lang.Object obj, java.lang.Object obj2) {
        com.android.framework.protobuf.MapFieldLite<K, V> mapFieldLite = (com.android.framework.protobuf.MapFieldLite) obj;
        com.android.framework.protobuf.MapFieldLite<K, V> mapFieldLite2 = (com.android.framework.protobuf.MapFieldLite) obj2;
        if (!mapFieldLite2.isEmpty()) {
            if (!mapFieldLite.isMutable()) {
                mapFieldLite = mapFieldLite.mutableCopy();
            }
            mapFieldLite.mergeFrom(mapFieldLite2);
        }
        return mapFieldLite;
    }

    @Override // com.android.framework.protobuf.MapFieldSchema
    public int getSerializedSize(int i, java.lang.Object obj, java.lang.Object obj2) {
        return getSerializedSizeLite(i, obj, obj2);
    }

    private static <K, V> int getSerializedSizeLite(int i, java.lang.Object obj, java.lang.Object obj2) {
        com.android.framework.protobuf.MapFieldLite mapFieldLite = (com.android.framework.protobuf.MapFieldLite) obj;
        com.android.framework.protobuf.MapEntryLite mapEntryLite = (com.android.framework.protobuf.MapEntryLite) obj2;
        int i2 = 0;
        if (mapFieldLite.isEmpty()) {
            return 0;
        }
        for (java.util.Map.Entry<K, V> entry : mapFieldLite.entrySet()) {
            i2 += mapEntryLite.computeMessageSize(i, entry.getKey(), entry.getValue());
        }
        return i2;
    }
}
