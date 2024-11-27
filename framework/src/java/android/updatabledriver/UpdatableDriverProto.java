package android.updatabledriver;

/* loaded from: classes3.dex */
public final class UpdatableDriverProto {

    public interface DenylistOrBuilder extends com.android.framework.protobuf.MessageLiteOrBuilder {
        java.lang.String getPackageNames(int i);

        com.android.framework.protobuf.ByteString getPackageNamesBytes(int i);

        int getPackageNamesCount();

        java.util.List<java.lang.String> getPackageNamesList();

        long getVersionCode();

        boolean hasVersionCode();
    }

    public interface DenylistsOrBuilder extends com.android.framework.protobuf.MessageLiteOrBuilder {
        android.updatabledriver.UpdatableDriverProto.Denylist getDenylists(int i);

        int getDenylistsCount();

        java.util.List<android.updatabledriver.UpdatableDriverProto.Denylist> getDenylistsList();
    }

    private UpdatableDriverProto() {
    }

    public static void registerAllExtensions(com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) {
    }

    public static final class Denylist extends com.android.framework.protobuf.GeneratedMessageLite<android.updatabledriver.UpdatableDriverProto.Denylist, android.updatabledriver.UpdatableDriverProto.Denylist.Builder> implements android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder {
        private static final android.updatabledriver.UpdatableDriverProto.Denylist DEFAULT_INSTANCE;
        public static final int PACKAGE_NAMES_FIELD_NUMBER = 2;
        private static volatile com.android.framework.protobuf.Parser<android.updatabledriver.UpdatableDriverProto.Denylist> PARSER = null;
        public static final int VERSION_CODE_FIELD_NUMBER = 1;
        private int bitField0_;
        private com.android.framework.protobuf.Internal.ProtobufList<java.lang.String> packageNames_ = com.android.framework.protobuf.GeneratedMessageLite.emptyProtobufList();
        private long versionCode_;

        private Denylist() {
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
        public boolean hasVersionCode() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
        public long getVersionCode() {
            return this.versionCode_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setVersionCode(long j) {
            this.bitField0_ |= 1;
            this.versionCode_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearVersionCode() {
            this.bitField0_ &= -2;
            this.versionCode_ = 0L;
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
        public java.util.List<java.lang.String> getPackageNamesList() {
            return this.packageNames_;
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
        public int getPackageNamesCount() {
            return this.packageNames_.size();
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
        public java.lang.String getPackageNames(int i) {
            return this.packageNames_.get(i);
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
        public com.android.framework.protobuf.ByteString getPackageNamesBytes(int i) {
            return com.android.framework.protobuf.ByteString.copyFromUtf8(this.packageNames_.get(i));
        }

        private void ensurePackageNamesIsMutable() {
            com.android.framework.protobuf.Internal.ProtobufList<java.lang.String> protobufList = this.packageNames_;
            if (!protobufList.isModifiable()) {
                this.packageNames_ = com.android.framework.protobuf.GeneratedMessageLite.mutableCopy(protobufList);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPackageNames(int i, java.lang.String str) {
            str.getClass();
            ensurePackageNamesIsMutable();
            this.packageNames_.set(i, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addPackageNames(java.lang.String str) {
            str.getClass();
            ensurePackageNamesIsMutable();
            this.packageNames_.add(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllPackageNames(java.lang.Iterable<java.lang.String> iterable) {
            ensurePackageNamesIsMutable();
            com.android.framework.protobuf.AbstractMessageLite.addAll((java.lang.Iterable) iterable, (java.util.List) this.packageNames_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPackageNames() {
            this.packageNames_ = com.android.framework.protobuf.GeneratedMessageLite.emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addPackageNamesBytes(com.android.framework.protobuf.ByteString byteString) {
            ensurePackageNamesIsMutable();
            this.packageNames_.add(byteString.toStringUtf8());
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist parseFrom(java.nio.ByteBuffer byteBuffer) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (android.updatabledriver.UpdatableDriverProto.Denylist) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist parseFrom(java.nio.ByteBuffer byteBuffer, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (android.updatabledriver.UpdatableDriverProto.Denylist) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist parseFrom(com.android.framework.protobuf.ByteString byteString) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (android.updatabledriver.UpdatableDriverProto.Denylist) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist parseFrom(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (android.updatabledriver.UpdatableDriverProto.Denylist) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist parseFrom(byte[] bArr) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (android.updatabledriver.UpdatableDriverProto.Denylist) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist parseFrom(byte[] bArr, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (android.updatabledriver.UpdatableDriverProto.Denylist) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist parseFrom(java.io.InputStream inputStream) throws java.io.IOException {
            return (android.updatabledriver.UpdatableDriverProto.Denylist) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist parseFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            return (android.updatabledriver.UpdatableDriverProto.Denylist) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist parseDelimitedFrom(java.io.InputStream inputStream) throws java.io.IOException {
            return (android.updatabledriver.UpdatableDriverProto.Denylist) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist parseDelimitedFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            return (android.updatabledriver.UpdatableDriverProto.Denylist) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist parseFrom(com.android.framework.protobuf.CodedInputStream codedInputStream) throws java.io.IOException {
            return (android.updatabledriver.UpdatableDriverProto.Denylist) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist parseFrom(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            return (android.updatabledriver.UpdatableDriverProto.Denylist) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist.Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist.Builder newBuilder(android.updatabledriver.UpdatableDriverProto.Denylist denylist) {
            return DEFAULT_INSTANCE.createBuilder(denylist);
        }

        public static final class Builder extends com.android.framework.protobuf.GeneratedMessageLite.Builder<android.updatabledriver.UpdatableDriverProto.Denylist, android.updatabledriver.UpdatableDriverProto.Denylist.Builder> implements android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder {
            private Builder() {
                super(android.updatabledriver.UpdatableDriverProto.Denylist.DEFAULT_INSTANCE);
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
            public boolean hasVersionCode() {
                return ((android.updatabledriver.UpdatableDriverProto.Denylist) this.instance).hasVersionCode();
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
            public long getVersionCode() {
                return ((android.updatabledriver.UpdatableDriverProto.Denylist) this.instance).getVersionCode();
            }

            public android.updatabledriver.UpdatableDriverProto.Denylist.Builder setVersionCode(long j) {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylist) this.instance).setVersionCode(j);
                return this;
            }

            public android.updatabledriver.UpdatableDriverProto.Denylist.Builder clearVersionCode() {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylist) this.instance).clearVersionCode();
                return this;
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
            public java.util.List<java.lang.String> getPackageNamesList() {
                return java.util.Collections.unmodifiableList(((android.updatabledriver.UpdatableDriverProto.Denylist) this.instance).getPackageNamesList());
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
            public int getPackageNamesCount() {
                return ((android.updatabledriver.UpdatableDriverProto.Denylist) this.instance).getPackageNamesCount();
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
            public java.lang.String getPackageNames(int i) {
                return ((android.updatabledriver.UpdatableDriverProto.Denylist) this.instance).getPackageNames(i);
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
            public com.android.framework.protobuf.ByteString getPackageNamesBytes(int i) {
                return ((android.updatabledriver.UpdatableDriverProto.Denylist) this.instance).getPackageNamesBytes(i);
            }

            public android.updatabledriver.UpdatableDriverProto.Denylist.Builder setPackageNames(int i, java.lang.String str) {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylist) this.instance).setPackageNames(i, str);
                return this;
            }

            public android.updatabledriver.UpdatableDriverProto.Denylist.Builder addPackageNames(java.lang.String str) {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylist) this.instance).addPackageNames(str);
                return this;
            }

            public android.updatabledriver.UpdatableDriverProto.Denylist.Builder addAllPackageNames(java.lang.Iterable<java.lang.String> iterable) {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylist) this.instance).addAllPackageNames(iterable);
                return this;
            }

            public android.updatabledriver.UpdatableDriverProto.Denylist.Builder clearPackageNames() {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylist) this.instance).clearPackageNames();
                return this;
            }

            public android.updatabledriver.UpdatableDriverProto.Denylist.Builder addPackageNamesBytes(com.android.framework.protobuf.ByteString byteString) {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylist) this.instance).addPackageNamesBytes(byteString);
                return this;
            }
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite
        protected final java.lang.Object dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke methodToInvoke, java.lang.Object obj, java.lang.Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new android.updatabledriver.UpdatableDriverProto.Denylist();
                case NEW_BUILDER:
                    return new android.updatabledriver.UpdatableDriverProto.Denylist.Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001ဂ\u0000\u0002\u001a", new java.lang.Object[]{"bitField0_", "versionCode_", "packageNames_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    com.android.framework.protobuf.Parser<android.updatabledriver.UpdatableDriverProto.Denylist> parser = PARSER;
                    if (parser == null) {
                        synchronized (android.updatabledriver.UpdatableDriverProto.Denylist.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new com.android.framework.protobuf.GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new java.lang.UnsupportedOperationException();
            }
        }

        static {
            android.updatabledriver.UpdatableDriverProto.Denylist denylist = new android.updatabledriver.UpdatableDriverProto.Denylist();
            DEFAULT_INSTANCE = denylist;
            com.android.framework.protobuf.GeneratedMessageLite.registerDefaultInstance(android.updatabledriver.UpdatableDriverProto.Denylist.class, denylist);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylist getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static com.android.framework.protobuf.Parser<android.updatabledriver.UpdatableDriverProto.Denylist> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class Denylists extends com.android.framework.protobuf.GeneratedMessageLite<android.updatabledriver.UpdatableDriverProto.Denylists, android.updatabledriver.UpdatableDriverProto.Denylists.Builder> implements android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder {
        private static final android.updatabledriver.UpdatableDriverProto.Denylists DEFAULT_INSTANCE;
        public static final int DENYLISTS_FIELD_NUMBER = 1;
        private static volatile com.android.framework.protobuf.Parser<android.updatabledriver.UpdatableDriverProto.Denylists> PARSER;
        private com.android.framework.protobuf.Internal.ProtobufList<android.updatabledriver.UpdatableDriverProto.Denylist> denylists_ = emptyProtobufList();

        private Denylists() {
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder
        public java.util.List<android.updatabledriver.UpdatableDriverProto.Denylist> getDenylistsList() {
            return this.denylists_;
        }

        public java.util.List<? extends android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder> getDenylistsOrBuilderList() {
            return this.denylists_;
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder
        public int getDenylistsCount() {
            return this.denylists_.size();
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder
        public android.updatabledriver.UpdatableDriverProto.Denylist getDenylists(int i) {
            return this.denylists_.get(i);
        }

        public android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder getDenylistsOrBuilder(int i) {
            return this.denylists_.get(i);
        }

        private void ensureDenylistsIsMutable() {
            com.android.framework.protobuf.Internal.ProtobufList<android.updatabledriver.UpdatableDriverProto.Denylist> protobufList = this.denylists_;
            if (!protobufList.isModifiable()) {
                this.denylists_ = com.android.framework.protobuf.GeneratedMessageLite.mutableCopy(protobufList);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDenylists(int i, android.updatabledriver.UpdatableDriverProto.Denylist denylist) {
            denylist.getClass();
            ensureDenylistsIsMutable();
            this.denylists_.set(i, denylist);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDenylists(android.updatabledriver.UpdatableDriverProto.Denylist denylist) {
            denylist.getClass();
            ensureDenylistsIsMutable();
            this.denylists_.add(denylist);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDenylists(int i, android.updatabledriver.UpdatableDriverProto.Denylist denylist) {
            denylist.getClass();
            ensureDenylistsIsMutable();
            this.denylists_.add(i, denylist);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllDenylists(java.lang.Iterable<? extends android.updatabledriver.UpdatableDriverProto.Denylist> iterable) {
            ensureDenylistsIsMutable();
            com.android.framework.protobuf.AbstractMessageLite.addAll((java.lang.Iterable) iterable, (java.util.List) this.denylists_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDenylists() {
            this.denylists_ = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeDenylists(int i) {
            ensureDenylistsIsMutable();
            this.denylists_.remove(i);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists parseFrom(java.nio.ByteBuffer byteBuffer) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (android.updatabledriver.UpdatableDriverProto.Denylists) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists parseFrom(java.nio.ByteBuffer byteBuffer, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (android.updatabledriver.UpdatableDriverProto.Denylists) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists parseFrom(com.android.framework.protobuf.ByteString byteString) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (android.updatabledriver.UpdatableDriverProto.Denylists) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists parseFrom(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (android.updatabledriver.UpdatableDriverProto.Denylists) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists parseFrom(byte[] bArr) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (android.updatabledriver.UpdatableDriverProto.Denylists) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists parseFrom(byte[] bArr, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return (android.updatabledriver.UpdatableDriverProto.Denylists) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists parseFrom(java.io.InputStream inputStream) throws java.io.IOException {
            return (android.updatabledriver.UpdatableDriverProto.Denylists) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists parseFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            return (android.updatabledriver.UpdatableDriverProto.Denylists) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists parseDelimitedFrom(java.io.InputStream inputStream) throws java.io.IOException {
            return (android.updatabledriver.UpdatableDriverProto.Denylists) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists parseDelimitedFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            return (android.updatabledriver.UpdatableDriverProto.Denylists) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists parseFrom(com.android.framework.protobuf.CodedInputStream codedInputStream) throws java.io.IOException {
            return (android.updatabledriver.UpdatableDriverProto.Denylists) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists parseFrom(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            return (android.updatabledriver.UpdatableDriverProto.Denylists) com.android.framework.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists.Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists.Builder newBuilder(android.updatabledriver.UpdatableDriverProto.Denylists denylists) {
            return DEFAULT_INSTANCE.createBuilder(denylists);
        }

        public static final class Builder extends com.android.framework.protobuf.GeneratedMessageLite.Builder<android.updatabledriver.UpdatableDriverProto.Denylists, android.updatabledriver.UpdatableDriverProto.Denylists.Builder> implements android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder {
            private Builder() {
                super(android.updatabledriver.UpdatableDriverProto.Denylists.DEFAULT_INSTANCE);
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder
            public java.util.List<android.updatabledriver.UpdatableDriverProto.Denylist> getDenylistsList() {
                return java.util.Collections.unmodifiableList(((android.updatabledriver.UpdatableDriverProto.Denylists) this.instance).getDenylistsList());
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder
            public int getDenylistsCount() {
                return ((android.updatabledriver.UpdatableDriverProto.Denylists) this.instance).getDenylistsCount();
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder
            public android.updatabledriver.UpdatableDriverProto.Denylist getDenylists(int i) {
                return ((android.updatabledriver.UpdatableDriverProto.Denylists) this.instance).getDenylists(i);
            }

            public android.updatabledriver.UpdatableDriverProto.Denylists.Builder setDenylists(int i, android.updatabledriver.UpdatableDriverProto.Denylist denylist) {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylists) this.instance).setDenylists(i, denylist);
                return this;
            }

            public android.updatabledriver.UpdatableDriverProto.Denylists.Builder setDenylists(int i, android.updatabledriver.UpdatableDriverProto.Denylist.Builder builder) {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylists) this.instance).setDenylists(i, builder.build());
                return this;
            }

            public android.updatabledriver.UpdatableDriverProto.Denylists.Builder addDenylists(android.updatabledriver.UpdatableDriverProto.Denylist denylist) {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylists) this.instance).addDenylists(denylist);
                return this;
            }

            public android.updatabledriver.UpdatableDriverProto.Denylists.Builder addDenylists(int i, android.updatabledriver.UpdatableDriverProto.Denylist denylist) {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylists) this.instance).addDenylists(i, denylist);
                return this;
            }

            public android.updatabledriver.UpdatableDriverProto.Denylists.Builder addDenylists(android.updatabledriver.UpdatableDriverProto.Denylist.Builder builder) {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylists) this.instance).addDenylists(builder.build());
                return this;
            }

            public android.updatabledriver.UpdatableDriverProto.Denylists.Builder addDenylists(int i, android.updatabledriver.UpdatableDriverProto.Denylist.Builder builder) {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylists) this.instance).addDenylists(i, builder.build());
                return this;
            }

            public android.updatabledriver.UpdatableDriverProto.Denylists.Builder addAllDenylists(java.lang.Iterable<? extends android.updatabledriver.UpdatableDriverProto.Denylist> iterable) {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylists) this.instance).addAllDenylists(iterable);
                return this;
            }

            public android.updatabledriver.UpdatableDriverProto.Denylists.Builder clearDenylists() {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylists) this.instance).clearDenylists();
                return this;
            }

            public android.updatabledriver.UpdatableDriverProto.Denylists.Builder removeDenylists(int i) {
                copyOnWrite();
                ((android.updatabledriver.UpdatableDriverProto.Denylists) this.instance).removeDenylists(i);
                return this;
            }
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite
        protected final java.lang.Object dynamicMethod(com.android.framework.protobuf.GeneratedMessageLite.MethodToInvoke methodToInvoke, java.lang.Object obj, java.lang.Object obj2) {
            switch (methodToInvoke) {
                case NEW_MUTABLE_INSTANCE:
                    return new android.updatabledriver.UpdatableDriverProto.Denylists();
                case NEW_BUILDER:
                    return new android.updatabledriver.UpdatableDriverProto.Denylists.Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new java.lang.Object[]{"denylists_", android.updatabledriver.UpdatableDriverProto.Denylist.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    com.android.framework.protobuf.Parser<android.updatabledriver.UpdatableDriverProto.Denylists> parser = PARSER;
                    if (parser == null) {
                        synchronized (android.updatabledriver.UpdatableDriverProto.Denylists.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new com.android.framework.protobuf.GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new java.lang.UnsupportedOperationException();
            }
        }

        static {
            android.updatabledriver.UpdatableDriverProto.Denylists denylists = new android.updatabledriver.UpdatableDriverProto.Denylists();
            DEFAULT_INSTANCE = denylists;
            com.android.framework.protobuf.GeneratedMessageLite.registerDefaultInstance(android.updatabledriver.UpdatableDriverProto.Denylists.class, denylists);
        }

        public static android.updatabledriver.UpdatableDriverProto.Denylists getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static com.android.framework.protobuf.Parser<android.updatabledriver.UpdatableDriverProto.Denylists> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
