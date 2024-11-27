package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public class LazyFieldLite {
    private static final com.android.framework.protobuf.ExtensionRegistryLite EMPTY_REGISTRY = com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry();
    private com.android.framework.protobuf.ByteString delayedBytes;
    private com.android.framework.protobuf.ExtensionRegistryLite extensionRegistry;
    private volatile com.android.framework.protobuf.ByteString memoizedBytes;
    protected volatile com.android.framework.protobuf.MessageLite value;

    public LazyFieldLite(com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.ByteString byteString) {
        checkArguments(extensionRegistryLite, byteString);
        this.extensionRegistry = extensionRegistryLite;
        this.delayedBytes = byteString;
    }

    public LazyFieldLite() {
    }

    public static com.android.framework.protobuf.LazyFieldLite fromValue(com.android.framework.protobuf.MessageLite messageLite) {
        com.android.framework.protobuf.LazyFieldLite lazyFieldLite = new com.android.framework.protobuf.LazyFieldLite();
        lazyFieldLite.setValue(messageLite);
        return lazyFieldLite;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.framework.protobuf.LazyFieldLite)) {
            return false;
        }
        com.android.framework.protobuf.LazyFieldLite lazyFieldLite = (com.android.framework.protobuf.LazyFieldLite) obj;
        com.android.framework.protobuf.MessageLite messageLite = this.value;
        com.android.framework.protobuf.MessageLite messageLite2 = lazyFieldLite.value;
        if (messageLite == null && messageLite2 == null) {
            return toByteString().equals(lazyFieldLite.toByteString());
        }
        if (messageLite != null && messageLite2 != null) {
            return messageLite.equals(messageLite2);
        }
        if (messageLite != null) {
            return messageLite.equals(lazyFieldLite.getValue(messageLite.getDefaultInstanceForType()));
        }
        return getValue(messageLite2.getDefaultInstanceForType()).equals(messageLite2);
    }

    public int hashCode() {
        return 1;
    }

    public boolean containsDefaultInstance() {
        return this.memoizedBytes == com.android.framework.protobuf.ByteString.EMPTY || (this.value == null && (this.delayedBytes == null || this.delayedBytes == com.android.framework.protobuf.ByteString.EMPTY));
    }

    public void clear() {
        this.delayedBytes = null;
        this.value = null;
        this.memoizedBytes = null;
    }

    public void set(com.android.framework.protobuf.LazyFieldLite lazyFieldLite) {
        this.delayedBytes = lazyFieldLite.delayedBytes;
        this.value = lazyFieldLite.value;
        this.memoizedBytes = lazyFieldLite.memoizedBytes;
        if (lazyFieldLite.extensionRegistry != null) {
            this.extensionRegistry = lazyFieldLite.extensionRegistry;
        }
    }

    public com.android.framework.protobuf.MessageLite getValue(com.android.framework.protobuf.MessageLite messageLite) {
        ensureInitialized(messageLite);
        return this.value;
    }

    public com.android.framework.protobuf.MessageLite setValue(com.android.framework.protobuf.MessageLite messageLite) {
        com.android.framework.protobuf.MessageLite messageLite2 = this.value;
        this.delayedBytes = null;
        this.memoizedBytes = null;
        this.value = messageLite;
        return messageLite2;
    }

    public void merge(com.android.framework.protobuf.LazyFieldLite lazyFieldLite) {
        if (lazyFieldLite.containsDefaultInstance()) {
            return;
        }
        if (containsDefaultInstance()) {
            set(lazyFieldLite);
            return;
        }
        if (this.extensionRegistry == null) {
            this.extensionRegistry = lazyFieldLite.extensionRegistry;
        }
        if (this.delayedBytes != null && lazyFieldLite.delayedBytes != null) {
            this.delayedBytes = this.delayedBytes.concat(lazyFieldLite.delayedBytes);
            return;
        }
        if (this.value == null && lazyFieldLite.value != null) {
            setValue(mergeValueAndBytes(lazyFieldLite.value, this.delayedBytes, this.extensionRegistry));
        } else if (this.value != null && lazyFieldLite.value == null) {
            setValue(mergeValueAndBytes(this.value, lazyFieldLite.delayedBytes, lazyFieldLite.extensionRegistry));
        } else {
            setValue(this.value.toBuilder().mergeFrom(lazyFieldLite.value).build());
        }
    }

    public void mergeFrom(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        if (containsDefaultInstance()) {
            setByteString(codedInputStream.readBytes(), extensionRegistryLite);
            return;
        }
        if (this.extensionRegistry == null) {
            this.extensionRegistry = extensionRegistryLite;
        }
        if (this.delayedBytes != null) {
            setByteString(this.delayedBytes.concat(codedInputStream.readBytes()), this.extensionRegistry);
        } else {
            try {
                setValue(this.value.toBuilder().mergeFrom(codedInputStream, extensionRegistryLite).build());
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
            }
        }
    }

    private static com.android.framework.protobuf.MessageLite mergeValueAndBytes(com.android.framework.protobuf.MessageLite messageLite, com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) {
        try {
            return messageLite.toBuilder().mergeFrom(byteString, extensionRegistryLite).build();
        } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
            return messageLite;
        }
    }

    public void setByteString(com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) {
        checkArguments(extensionRegistryLite, byteString);
        this.delayedBytes = byteString;
        this.extensionRegistry = extensionRegistryLite;
        this.value = null;
        this.memoizedBytes = null;
    }

    public int getSerializedSize() {
        if (this.memoizedBytes != null) {
            return this.memoizedBytes.size();
        }
        if (this.delayedBytes != null) {
            return this.delayedBytes.size();
        }
        if (this.value != null) {
            return this.value.getSerializedSize();
        }
        return 0;
    }

    public com.android.framework.protobuf.ByteString toByteString() {
        if (this.memoizedBytes != null) {
            return this.memoizedBytes;
        }
        if (this.delayedBytes != null) {
            return this.delayedBytes;
        }
        synchronized (this) {
            if (this.memoizedBytes != null) {
                return this.memoizedBytes;
            }
            if (this.value == null) {
                this.memoizedBytes = com.android.framework.protobuf.ByteString.EMPTY;
            } else {
                this.memoizedBytes = this.value.toByteString();
            }
            return this.memoizedBytes;
        }
    }

    void writeTo(com.android.framework.protobuf.Writer writer, int i) throws java.io.IOException {
        if (this.memoizedBytes != null) {
            writer.writeBytes(i, this.memoizedBytes);
            return;
        }
        if (this.delayedBytes != null) {
            writer.writeBytes(i, this.delayedBytes);
        } else if (this.value != null) {
            writer.writeMessage(i, this.value);
        } else {
            writer.writeBytes(i, com.android.framework.protobuf.ByteString.EMPTY);
        }
    }

    protected void ensureInitialized(com.android.framework.protobuf.MessageLite messageLite) {
        if (this.value != null) {
            return;
        }
        synchronized (this) {
            if (this.value != null) {
                return;
            }
            try {
                if (this.delayedBytes != null) {
                    this.value = messageLite.getParserForType().parseFrom(this.delayedBytes, this.extensionRegistry);
                    this.memoizedBytes = this.delayedBytes;
                } else {
                    this.value = messageLite;
                    this.memoizedBytes = com.android.framework.protobuf.ByteString.EMPTY;
                }
            } catch (com.android.framework.protobuf.InvalidProtocolBufferException e) {
                this.value = messageLite;
                this.memoizedBytes = com.android.framework.protobuf.ByteString.EMPTY;
            }
        }
    }

    private static void checkArguments(com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.ByteString byteString) {
        if (extensionRegistryLite == null) {
            throw new java.lang.NullPointerException("found null ExtensionRegistry");
        }
        if (byteString == null) {
            throw new java.lang.NullPointerException("found null ByteString");
        }
    }
}
