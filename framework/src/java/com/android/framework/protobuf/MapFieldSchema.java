package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
interface MapFieldSchema {
    java.util.Map<?, ?> forMapData(java.lang.Object obj);

    com.android.framework.protobuf.MapEntryLite.Metadata<?, ?> forMapMetadata(java.lang.Object obj);

    java.util.Map<?, ?> forMutableMapData(java.lang.Object obj);

    int getSerializedSize(int i, java.lang.Object obj, java.lang.Object obj2);

    boolean isImmutable(java.lang.Object obj);

    java.lang.Object mergeFrom(java.lang.Object obj, java.lang.Object obj2);

    java.lang.Object newMapField(java.lang.Object obj);

    java.lang.Object toImmutable(java.lang.Object obj);
}
