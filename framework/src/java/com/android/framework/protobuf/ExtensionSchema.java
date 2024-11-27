package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
abstract class ExtensionSchema<T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> {
    abstract int extensionNumber(java.util.Map.Entry<?, ?> entry);

    abstract java.lang.Object findExtensionByNumber(com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.MessageLite messageLite, int i);

    abstract com.android.framework.protobuf.FieldSet<T> getExtensions(java.lang.Object obj);

    abstract com.android.framework.protobuf.FieldSet<T> getMutableExtensions(java.lang.Object obj);

    abstract boolean hasExtensions(com.android.framework.protobuf.MessageLite messageLite);

    abstract void makeImmutable(java.lang.Object obj);

    abstract <UT, UB> UB parseExtension(java.lang.Object obj, com.android.framework.protobuf.Reader reader, java.lang.Object obj2, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.FieldSet<T> fieldSet, UB ub, com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema) throws java.io.IOException;

    abstract void parseLengthPrefixedMessageSetItem(com.android.framework.protobuf.Reader reader, java.lang.Object obj, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.FieldSet<T> fieldSet) throws java.io.IOException;

    abstract void parseMessageSetItem(com.android.framework.protobuf.ByteString byteString, java.lang.Object obj, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.FieldSet<T> fieldSet) throws java.io.IOException;

    abstract void serializeExtension(com.android.framework.protobuf.Writer writer, java.util.Map.Entry<?, ?> entry) throws java.io.IOException;

    abstract void setExtensions(java.lang.Object obj, com.android.framework.protobuf.FieldSet<T> fieldSet);

    ExtensionSchema() {
    }
}
