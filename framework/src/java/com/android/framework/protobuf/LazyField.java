package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public class LazyField extends com.android.framework.protobuf.LazyFieldLite {
    private final com.android.framework.protobuf.MessageLite defaultInstance;

    public LazyField(com.android.framework.protobuf.MessageLite messageLite, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.ByteString byteString) {
        super(extensionRegistryLite, byteString);
        this.defaultInstance = messageLite;
    }

    @Override // com.android.framework.protobuf.LazyFieldLite
    public boolean containsDefaultInstance() {
        return super.containsDefaultInstance() || this.value == this.defaultInstance;
    }

    public com.android.framework.protobuf.MessageLite getValue() {
        return getValue(this.defaultInstance);
    }

    @Override // com.android.framework.protobuf.LazyFieldLite
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override // com.android.framework.protobuf.LazyFieldLite
    public boolean equals(java.lang.Object obj) {
        return getValue().equals(obj);
    }

    public java.lang.String toString() {
        return getValue().toString();
    }

    static class LazyEntry<K> implements java.util.Map.Entry<K, java.lang.Object> {
        private java.util.Map.Entry<K, com.android.framework.protobuf.LazyField> entry;

        private LazyEntry(java.util.Map.Entry<K, com.android.framework.protobuf.LazyField> entry) {
            this.entry = entry;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.entry.getKey();
        }

        @Override // java.util.Map.Entry
        public java.lang.Object getValue() {
            com.android.framework.protobuf.LazyField value = this.entry.getValue();
            if (value == null) {
                return null;
            }
            return value.getValue();
        }

        public com.android.framework.protobuf.LazyField getField() {
            return this.entry.getValue();
        }

        @Override // java.util.Map.Entry
        public java.lang.Object setValue(java.lang.Object obj) {
            if (!(obj instanceof com.android.framework.protobuf.MessageLite)) {
                throw new java.lang.IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
            }
            return this.entry.getValue().setValue((com.android.framework.protobuf.MessageLite) obj);
        }
    }

    static class LazyIterator<K> implements java.util.Iterator<java.util.Map.Entry<K, java.lang.Object>> {
        private java.util.Iterator<java.util.Map.Entry<K, java.lang.Object>> iterator;

        public LazyIterator(java.util.Iterator<java.util.Map.Entry<K, java.lang.Object>> it) {
            this.iterator = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override // java.util.Iterator
        public java.util.Map.Entry<K, java.lang.Object> next() {
            java.util.Map.Entry<K, java.lang.Object> next = this.iterator.next();
            if (next.getValue() instanceof com.android.framework.protobuf.LazyField) {
                return new com.android.framework.protobuf.LazyField.LazyEntry(next);
            }
            return next;
        }

        @Override // java.util.Iterator
        public void remove() {
            this.iterator.remove();
        }
    }
}
