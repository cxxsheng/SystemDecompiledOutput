package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public abstract class AbstractMessageLite<MessageType extends com.android.framework.protobuf.AbstractMessageLite<MessageType, BuilderType>, BuilderType extends com.android.framework.protobuf.AbstractMessageLite.Builder<MessageType, BuilderType>> implements com.android.framework.protobuf.MessageLite {
    protected int memoizedHashCode = 0;

    protected interface InternalOneOfEnum {
        int getNumber();
    }

    @Override // com.android.framework.protobuf.MessageLite
    public com.android.framework.protobuf.ByteString toByteString() {
        try {
            com.android.framework.protobuf.ByteString.CodedBuilder newCodedBuilder = com.android.framework.protobuf.ByteString.newCodedBuilder(getSerializedSize());
            writeTo(newCodedBuilder.getCodedOutput());
            return newCodedBuilder.build();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException(getSerializingExceptionMessage("ByteString"), e);
        }
    }

    @Override // com.android.framework.protobuf.MessageLite
    public byte[] toByteArray() {
        try {
            byte[] bArr = new byte[getSerializedSize()];
            com.android.framework.protobuf.CodedOutputStream newInstance = com.android.framework.protobuf.CodedOutputStream.newInstance(bArr);
            writeTo(newInstance);
            newInstance.checkNoSpaceLeft();
            return bArr;
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException(getSerializingExceptionMessage("byte array"), e);
        }
    }

    @Override // com.android.framework.protobuf.MessageLite
    public void writeTo(java.io.OutputStream outputStream) throws java.io.IOException {
        com.android.framework.protobuf.CodedOutputStream newInstance = com.android.framework.protobuf.CodedOutputStream.newInstance(outputStream, com.android.framework.protobuf.CodedOutputStream.computePreferredBufferSize(getSerializedSize()));
        writeTo(newInstance);
        newInstance.flush();
    }

    @Override // com.android.framework.protobuf.MessageLite
    public void writeDelimitedTo(java.io.OutputStream outputStream) throws java.io.IOException {
        int serializedSize = getSerializedSize();
        com.android.framework.protobuf.CodedOutputStream newInstance = com.android.framework.protobuf.CodedOutputStream.newInstance(outputStream, com.android.framework.protobuf.CodedOutputStream.computePreferredBufferSize(com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(serializedSize) + serializedSize));
        newInstance.writeUInt32NoTag(serializedSize);
        writeTo(newInstance);
        newInstance.flush();
    }

    int getMemoizedSerializedSize() {
        throw new java.lang.UnsupportedOperationException();
    }

    void setMemoizedSerializedSize(int i) {
        throw new java.lang.UnsupportedOperationException();
    }

    int getSerializedSize(com.android.framework.protobuf.Schema schema) {
        int memoizedSerializedSize = getMemoizedSerializedSize();
        if (memoizedSerializedSize == -1) {
            int serializedSize = schema.getSerializedSize(this);
            setMemoizedSerializedSize(serializedSize);
            return serializedSize;
        }
        return memoizedSerializedSize;
    }

    com.android.framework.protobuf.UninitializedMessageException newUninitializedMessageException() {
        return new com.android.framework.protobuf.UninitializedMessageException(this);
    }

    private java.lang.String getSerializingExceptionMessage(java.lang.String str) {
        return "Serializing " + getClass().getName() + " to a " + str + " threw an IOException (should never happen).";
    }

    protected static void checkByteStringIsUtf8(com.android.framework.protobuf.ByteString byteString) throws java.lang.IllegalArgumentException {
        if (!byteString.isValidUtf8()) {
            throw new java.lang.IllegalArgumentException("Byte string is not UTF-8.");
        }
    }

    @java.lang.Deprecated
    protected static <T> void addAll(java.lang.Iterable<T> iterable, java.util.Collection<? super T> collection) {
        com.android.framework.protobuf.AbstractMessageLite.Builder.addAll((java.lang.Iterable) iterable, (java.util.List) collection);
    }

    protected static <T> void addAll(java.lang.Iterable<T> iterable, java.util.List<? super T> list) {
        com.android.framework.protobuf.AbstractMessageLite.Builder.addAll((java.lang.Iterable) iterable, (java.util.List) list);
    }

    public static abstract class Builder<MessageType extends com.android.framework.protobuf.AbstractMessageLite<MessageType, BuilderType>, BuilderType extends com.android.framework.protobuf.AbstractMessageLite.Builder<MessageType, BuilderType>> implements com.android.framework.protobuf.MessageLite.Builder {
        @Override // 
        /* renamed from: clone */
        public abstract BuilderType mo6591clone();

        protected abstract BuilderType internalMergeFrom(MessageType messagetype);

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public abstract BuilderType mergeFrom(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException;

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(com.android.framework.protobuf.CodedInputStream codedInputStream) throws java.io.IOException {
            return mergeFrom(codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(com.android.framework.protobuf.ByteString byteString) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            try {
                com.android.framework.protobuf.CodedInputStream newCodedInput = byteString.newCodedInput();
                mergeFrom(newCodedInput);
                newCodedInput.checkLastTagWas(0);
                return this;
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                throw e;
            } catch (java.io.IOException e2) {
                throw new java.lang.RuntimeException(getReadingExceptionMessage("ByteString"), e2);
            }
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            try {
                com.android.framework.protobuf.CodedInputStream newCodedInput = byteString.newCodedInput();
                mergeFrom(newCodedInput, extensionRegistryLite);
                newCodedInput.checkLastTagWas(0);
                return this;
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                throw e;
            } catch (java.io.IOException e2) {
                throw new java.lang.RuntimeException(getReadingExceptionMessage("ByteString"), e2);
            }
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(byte[] bArr) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return mergeFrom(bArr, 0, bArr.length);
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(byte[] bArr, int i, int i2) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            try {
                com.android.framework.protobuf.CodedInputStream newInstance = com.android.framework.protobuf.CodedInputStream.newInstance(bArr, i, i2);
                mergeFrom(newInstance);
                newInstance.checkLastTagWas(0);
                return this;
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                throw e;
            } catch (java.io.IOException e2) {
                throw new java.lang.RuntimeException(getReadingExceptionMessage("byte array"), e2);
            }
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(byte[] bArr, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            return mergeFrom(bArr, 0, bArr.length, extensionRegistryLite);
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(byte[] bArr, int i, int i2, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws com.android.framework.protobuf.InvalidProtocolBufferException {
            try {
                com.android.framework.protobuf.CodedInputStream newInstance = com.android.framework.protobuf.CodedInputStream.newInstance(bArr, i, i2);
                mergeFrom(newInstance, extensionRegistryLite);
                newInstance.checkLastTagWas(0);
                return this;
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                throw e;
            } catch (java.io.IOException e2) {
                throw new java.lang.RuntimeException(getReadingExceptionMessage("byte array"), e2);
            }
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(java.io.InputStream inputStream) throws java.io.IOException {
            com.android.framework.protobuf.CodedInputStream newInstance = com.android.framework.protobuf.CodedInputStream.newInstance(inputStream);
            mergeFrom(newInstance);
            newInstance.checkLastTagWas(0);
            return this;
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            com.android.framework.protobuf.CodedInputStream newInstance = com.android.framework.protobuf.CodedInputStream.newInstance(inputStream);
            mergeFrom(newInstance, extensionRegistryLite);
            newInstance.checkLastTagWas(0);
            return this;
        }

        static final class LimitedInputStream extends java.io.FilterInputStream {
            private int limit;

            LimitedInputStream(java.io.InputStream inputStream, int i) {
                super(inputStream);
                this.limit = i;
            }

            @Override // java.io.FilterInputStream, java.io.InputStream
            public int available() throws java.io.IOException {
                return java.lang.Math.min(super.available(), this.limit);
            }

            @Override // java.io.FilterInputStream, java.io.InputStream
            public int read() throws java.io.IOException {
                if (this.limit <= 0) {
                    return -1;
                }
                int read = super.read();
                if (read >= 0) {
                    this.limit--;
                }
                return read;
            }

            @Override // java.io.FilterInputStream, java.io.InputStream
            public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
                if (this.limit <= 0) {
                    return -1;
                }
                int read = super.read(bArr, i, java.lang.Math.min(i2, this.limit));
                if (read >= 0) {
                    this.limit -= read;
                }
                return read;
            }

            @Override // java.io.FilterInputStream, java.io.InputStream
            public long skip(long j) throws java.io.IOException {
                int skip = (int) super.skip(java.lang.Math.min(j, this.limit));
                if (skip >= 0) {
                    this.limit -= skip;
                }
                return skip;
            }
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public boolean mergeDelimitedFrom(java.io.InputStream inputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
            int read = inputStream.read();
            if (read == -1) {
                return false;
            }
            mergeFrom((java.io.InputStream) new com.android.framework.protobuf.AbstractMessageLite.Builder.LimitedInputStream(inputStream, com.android.framework.protobuf.CodedInputStream.readRawVarint32(read, inputStream)), extensionRegistryLite);
            return true;
        }

        @Override // com.android.framework.protobuf.MessageLite.Builder
        public boolean mergeDelimitedFrom(java.io.InputStream inputStream) throws java.io.IOException {
            return mergeDelimitedFrom(inputStream, com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry());
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.framework.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(com.android.framework.protobuf.MessageLite messageLite) {
            if (!getDefaultInstanceForType().getClass().isInstance(messageLite)) {
                throw new java.lang.IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
            }
            return (BuilderType) internalMergeFrom((com.android.framework.protobuf.AbstractMessageLite) messageLite);
        }

        private java.lang.String getReadingExceptionMessage(java.lang.String str) {
            return "Reading " + getClass().getName() + " from a " + str + " threw an IOException (should never happen).";
        }

        private static <T> void addAllCheckingNulls(java.lang.Iterable<T> iterable, java.util.List<? super T> list) {
            if ((list instanceof java.util.ArrayList) && (iterable instanceof java.util.Collection)) {
                ((java.util.ArrayList) list).ensureCapacity(list.size() + ((java.util.Collection) iterable).size());
            }
            int size = list.size();
            for (T t : iterable) {
                if (t == null) {
                    java.lang.String str = "Element at index " + (list.size() - size) + " is null.";
                    for (int size2 = list.size() - 1; size2 >= size; size2--) {
                        list.remove(size2);
                    }
                    throw new java.lang.NullPointerException(str);
                }
                list.add(t);
            }
        }

        protected static com.android.framework.protobuf.UninitializedMessageException newUninitializedMessageException(com.android.framework.protobuf.MessageLite messageLite) {
            return new com.android.framework.protobuf.UninitializedMessageException(messageLite);
        }

        @java.lang.Deprecated
        protected static <T> void addAll(java.lang.Iterable<T> iterable, java.util.Collection<? super T> collection) {
            addAll((java.lang.Iterable) iterable, (java.util.List) collection);
        }

        protected static <T> void addAll(java.lang.Iterable<T> iterable, java.util.List<? super T> list) {
            com.android.framework.protobuf.Internal.checkNotNull(iterable);
            if (iterable instanceof com.android.framework.protobuf.LazyStringList) {
                java.util.List<?> underlyingElements = ((com.android.framework.protobuf.LazyStringList) iterable).getUnderlyingElements();
                com.android.framework.protobuf.LazyStringList lazyStringList = (com.android.framework.protobuf.LazyStringList) list;
                int size = list.size();
                for (java.lang.Object obj : underlyingElements) {
                    if (obj == null) {
                        java.lang.String str = "Element at index " + (lazyStringList.size() - size) + " is null.";
                        for (int size2 = lazyStringList.size() - 1; size2 >= size; size2--) {
                            lazyStringList.remove(size2);
                        }
                        throw new java.lang.NullPointerException(str);
                    }
                    if (obj instanceof com.android.framework.protobuf.ByteString) {
                        lazyStringList.add((com.android.framework.protobuf.ByteString) obj);
                    } else {
                        lazyStringList.add((java.lang.String) obj);
                    }
                }
                return;
            }
            if (iterable instanceof com.android.framework.protobuf.PrimitiveNonBoxingCollection) {
                list.addAll((java.util.Collection) iterable);
            } else {
                addAllCheckingNulls(iterable, list);
            }
        }
    }
}
