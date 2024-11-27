package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class StructuralMessageInfo implements com.android.framework.protobuf.MessageInfo {
    private final int[] checkInitialized;
    private final com.android.framework.protobuf.MessageLite defaultInstance;
    private final com.android.framework.protobuf.FieldInfo[] fields;
    private final boolean messageSetWireFormat;
    private final com.android.framework.protobuf.ProtoSyntax syntax;

    StructuralMessageInfo(com.android.framework.protobuf.ProtoSyntax protoSyntax, boolean z, int[] iArr, com.android.framework.protobuf.FieldInfo[] fieldInfoArr, java.lang.Object obj) {
        this.syntax = protoSyntax;
        this.messageSetWireFormat = z;
        this.checkInitialized = iArr;
        this.fields = fieldInfoArr;
        this.defaultInstance = (com.android.framework.protobuf.MessageLite) com.android.framework.protobuf.Internal.checkNotNull(obj, "defaultInstance");
    }

    @Override // com.android.framework.protobuf.MessageInfo
    public com.android.framework.protobuf.ProtoSyntax getSyntax() {
        return this.syntax;
    }

    @Override // com.android.framework.protobuf.MessageInfo
    public boolean isMessageSetWireFormat() {
        return this.messageSetWireFormat;
    }

    public int[] getCheckInitialized() {
        return this.checkInitialized;
    }

    public com.android.framework.protobuf.FieldInfo[] getFields() {
        return this.fields;
    }

    @Override // com.android.framework.protobuf.MessageInfo
    public com.android.framework.protobuf.MessageLite getDefaultInstance() {
        return this.defaultInstance;
    }

    public static com.android.framework.protobuf.StructuralMessageInfo.Builder newBuilder() {
        return new com.android.framework.protobuf.StructuralMessageInfo.Builder();
    }

    public static com.android.framework.protobuf.StructuralMessageInfo.Builder newBuilder(int i) {
        return new com.android.framework.protobuf.StructuralMessageInfo.Builder(i);
    }

    public static final class Builder {
        private int[] checkInitialized;
        private java.lang.Object defaultInstance;
        private final java.util.List<com.android.framework.protobuf.FieldInfo> fields;
        private boolean messageSetWireFormat;
        private com.android.framework.protobuf.ProtoSyntax syntax;
        private boolean wasBuilt;

        public Builder() {
            this.checkInitialized = null;
            this.fields = new java.util.ArrayList();
        }

        public Builder(int i) {
            this.checkInitialized = null;
            this.fields = new java.util.ArrayList(i);
        }

        public void withDefaultInstance(java.lang.Object obj) {
            this.defaultInstance = obj;
        }

        public void withSyntax(com.android.framework.protobuf.ProtoSyntax protoSyntax) {
            this.syntax = (com.android.framework.protobuf.ProtoSyntax) com.android.framework.protobuf.Internal.checkNotNull(protoSyntax, "syntax");
        }

        public void withMessageSetWireFormat(boolean z) {
            this.messageSetWireFormat = z;
        }

        public void withCheckInitialized(int[] iArr) {
            this.checkInitialized = iArr;
        }

        public void withField(com.android.framework.protobuf.FieldInfo fieldInfo) {
            if (this.wasBuilt) {
                throw new java.lang.IllegalStateException("Builder can only build once");
            }
            this.fields.add(fieldInfo);
        }

        public com.android.framework.protobuf.StructuralMessageInfo build() {
            if (this.wasBuilt) {
                throw new java.lang.IllegalStateException("Builder can only build once");
            }
            if (this.syntax == null) {
                throw new java.lang.IllegalStateException("Must specify a proto syntax");
            }
            this.wasBuilt = true;
            java.util.Collections.sort(this.fields);
            return new com.android.framework.protobuf.StructuralMessageInfo(this.syntax, this.messageSetWireFormat, this.checkInitialized, (com.android.framework.protobuf.FieldInfo[]) this.fields.toArray(new com.android.framework.protobuf.FieldInfo[0]), this.defaultInstance);
        }
    }
}
