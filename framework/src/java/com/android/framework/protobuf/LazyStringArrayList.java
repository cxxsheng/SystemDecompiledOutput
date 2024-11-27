package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public class LazyStringArrayList extends com.android.framework.protobuf.AbstractProtobufList<java.lang.String> implements com.android.framework.protobuf.LazyStringList, java.util.RandomAccess {
    public static final com.android.framework.protobuf.LazyStringList EMPTY;
    private static final com.android.framework.protobuf.LazyStringArrayList EMPTY_LIST = new com.android.framework.protobuf.LazyStringArrayList();
    private final java.util.List<java.lang.Object> list;

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ boolean add(java.lang.Object obj) {
        return super.add((com.android.framework.protobuf.LazyStringArrayList) obj);
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ boolean equals(java.lang.Object obj) {
        return super.equals(obj);
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, com.android.framework.protobuf.Internal.ProtobufList
    public /* bridge */ /* synthetic */ boolean isModifiable() {
        return super.isModifiable();
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ boolean remove(java.lang.Object obj) {
        return super.remove(obj);
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ boolean removeAll(java.util.Collection collection) {
        return super.removeAll(collection);
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ boolean retainAll(java.util.Collection collection) {
        return super.retainAll(collection);
    }

    static {
        EMPTY_LIST.makeImmutable();
        EMPTY = EMPTY_LIST;
    }

    static com.android.framework.protobuf.LazyStringArrayList emptyList() {
        return EMPTY_LIST;
    }

    public LazyStringArrayList() {
        this(10);
    }

    public LazyStringArrayList(int i) {
        this((java.util.ArrayList<java.lang.Object>) new java.util.ArrayList(i));
    }

    public LazyStringArrayList(com.android.framework.protobuf.LazyStringList lazyStringList) {
        this.list = new java.util.ArrayList(lazyStringList.size());
        addAll(lazyStringList);
    }

    public LazyStringArrayList(java.util.List<java.lang.String> list) {
        this((java.util.ArrayList<java.lang.Object>) new java.util.ArrayList(list));
    }

    private LazyStringArrayList(java.util.ArrayList<java.lang.Object> arrayList) {
        this.list = arrayList;
    }

    @Override // com.android.framework.protobuf.Internal.ProtobufList, com.android.framework.protobuf.Internal.BooleanList
    /* renamed from: mutableCopyWithCapacity */
    public com.android.framework.protobuf.LazyStringArrayList mutableCopyWithCapacity2(int i) {
        if (i < size()) {
            throw new java.lang.IllegalArgumentException();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(i);
        arrayList.addAll(this.list);
        return new com.android.framework.protobuf.LazyStringArrayList((java.util.ArrayList<java.lang.Object>) arrayList);
    }

    @Override // java.util.AbstractList, java.util.List
    public java.lang.String get(int i) {
        java.lang.Object obj = this.list.get(i);
        if (obj instanceof java.lang.String) {
            return (java.lang.String) obj;
        }
        if (obj instanceof com.android.framework.protobuf.ByteString) {
            com.android.framework.protobuf.ByteString byteString = (com.android.framework.protobuf.ByteString) obj;
            java.lang.String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.list.set(i, stringUtf8);
            }
            return stringUtf8;
        }
        byte[] bArr = (byte[]) obj;
        java.lang.String stringUtf82 = com.android.framework.protobuf.Internal.toStringUtf8(bArr);
        if (com.android.framework.protobuf.Internal.isValidUtf8(bArr)) {
            this.list.set(i, stringUtf82);
        }
        return stringUtf82;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.list.size();
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public java.lang.String set(int i, java.lang.String str) {
        ensureIsMutable();
        return asString(this.list.set(i, str));
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public void add(int i, java.lang.String str) {
        ensureIsMutable();
        this.list.add(i, str);
        this.modCount++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void add(int i, com.android.framework.protobuf.ByteString byteString) {
        ensureIsMutable();
        this.list.add(i, byteString);
        this.modCount++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void add(int i, byte[] bArr) {
        ensureIsMutable();
        this.list.add(i, bArr);
        this.modCount++;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(java.util.Collection<? extends java.lang.String> collection) {
        return addAll(size(), collection);
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public boolean addAll(int i, java.util.Collection<? extends java.lang.String> collection) {
        ensureIsMutable();
        if (collection instanceof com.android.framework.protobuf.LazyStringList) {
            collection = ((com.android.framework.protobuf.LazyStringList) collection).getUnderlyingElements();
        }
        boolean addAll = this.list.addAll(i, collection);
        this.modCount++;
        return addAll;
    }

    @Override // com.android.framework.protobuf.LazyStringList
    public boolean addAllByteString(java.util.Collection<? extends com.android.framework.protobuf.ByteString> collection) {
        ensureIsMutable();
        boolean addAll = this.list.addAll(collection);
        this.modCount++;
        return addAll;
    }

    @Override // com.android.framework.protobuf.LazyStringList
    public boolean addAllByteArray(java.util.Collection<byte[]> collection) {
        ensureIsMutable();
        boolean addAll = this.list.addAll(collection);
        this.modCount++;
        return addAll;
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public java.lang.String remove(int i) {
        ensureIsMutable();
        java.lang.Object remove = this.list.remove(i);
        this.modCount++;
        return asString(remove);
    }

    @Override // com.android.framework.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        ensureIsMutable();
        this.list.clear();
        this.modCount++;
    }

    @Override // com.android.framework.protobuf.LazyStringList
    public void add(com.android.framework.protobuf.ByteString byteString) {
        ensureIsMutable();
        this.list.add(byteString);
        this.modCount++;
    }

    @Override // com.android.framework.protobuf.LazyStringList
    public void add(byte[] bArr) {
        ensureIsMutable();
        this.list.add(bArr);
        this.modCount++;
    }

    @Override // com.android.framework.protobuf.LazyStringList
    public java.lang.Object getRaw(int i) {
        return this.list.get(i);
    }

    @Override // com.android.framework.protobuf.LazyStringList
    public com.android.framework.protobuf.ByteString getByteString(int i) {
        java.lang.Object obj = this.list.get(i);
        com.android.framework.protobuf.ByteString asByteString = asByteString(obj);
        if (asByteString != obj) {
            this.list.set(i, asByteString);
        }
        return asByteString;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.framework.protobuf.LazyStringList
    public byte[] getByteArray(int i) {
        java.lang.Object obj = this.list.get(i);
        byte[] asByteArray = asByteArray(obj);
        if (asByteArray != obj) {
            this.list.set(i, asByteArray);
        }
        return asByteArray;
    }

    @Override // com.android.framework.protobuf.LazyStringList
    public void set(int i, com.android.framework.protobuf.ByteString byteString) {
        setAndReturn(i, byteString);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.Object setAndReturn(int i, com.android.framework.protobuf.ByteString byteString) {
        ensureIsMutable();
        return this.list.set(i, byteString);
    }

    @Override // com.android.framework.protobuf.LazyStringList
    public void set(int i, byte[] bArr) {
        setAndReturn(i, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.Object setAndReturn(int i, byte[] bArr) {
        ensureIsMutable();
        return this.list.set(i, bArr);
    }

    private static java.lang.String asString(java.lang.Object obj) {
        if (obj instanceof java.lang.String) {
            return (java.lang.String) obj;
        }
        if (obj instanceof com.android.framework.protobuf.ByteString) {
            return ((com.android.framework.protobuf.ByteString) obj).toStringUtf8();
        }
        return com.android.framework.protobuf.Internal.toStringUtf8((byte[]) obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.framework.protobuf.ByteString asByteString(java.lang.Object obj) {
        if (obj instanceof com.android.framework.protobuf.ByteString) {
            return (com.android.framework.protobuf.ByteString) obj;
        }
        if (obj instanceof java.lang.String) {
            return com.android.framework.protobuf.ByteString.copyFromUtf8((java.lang.String) obj);
        }
        return com.android.framework.protobuf.ByteString.copyFrom((byte[]) obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] asByteArray(java.lang.Object obj) {
        if (obj instanceof byte[]) {
            return (byte[]) obj;
        }
        if (obj instanceof java.lang.String) {
            return com.android.framework.protobuf.Internal.toByteArray((java.lang.String) obj);
        }
        return ((com.android.framework.protobuf.ByteString) obj).toByteArray();
    }

    @Override // com.android.framework.protobuf.LazyStringList
    public java.util.List<?> getUnderlyingElements() {
        return java.util.Collections.unmodifiableList(this.list);
    }

    @Override // com.android.framework.protobuf.LazyStringList
    public void mergeFrom(com.android.framework.protobuf.LazyStringList lazyStringList) {
        ensureIsMutable();
        for (java.lang.Object obj : lazyStringList.getUnderlyingElements()) {
            if (obj instanceof byte[]) {
                byte[] bArr = (byte[]) obj;
                this.list.add(java.util.Arrays.copyOf(bArr, bArr.length));
            } else {
                this.list.add(obj);
            }
        }
    }

    private static class ByteArrayListView extends java.util.AbstractList<byte[]> implements java.util.RandomAccess {
        private final com.android.framework.protobuf.LazyStringArrayList list;

        ByteArrayListView(com.android.framework.protobuf.LazyStringArrayList lazyStringArrayList) {
            this.list = lazyStringArrayList;
        }

        @Override // java.util.AbstractList, java.util.List
        public byte[] get(int i) {
            return this.list.getByteArray(i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.list.size();
        }

        @Override // java.util.AbstractList, java.util.List
        public byte[] set(int i, byte[] bArr) {
            java.lang.Object andReturn = this.list.setAndReturn(i, bArr);
            this.modCount++;
            return com.android.framework.protobuf.LazyStringArrayList.asByteArray(andReturn);
        }

        @Override // java.util.AbstractList, java.util.List
        public void add(int i, byte[] bArr) {
            this.list.add(i, bArr);
            this.modCount++;
        }

        @Override // java.util.AbstractList, java.util.List
        public byte[] remove(int i) {
            java.lang.String remove = this.list.remove(i);
            this.modCount++;
            return com.android.framework.protobuf.LazyStringArrayList.asByteArray(remove);
        }
    }

    @Override // com.android.framework.protobuf.LazyStringList
    public java.util.List<byte[]> asByteArrayList() {
        return new com.android.framework.protobuf.LazyStringArrayList.ByteArrayListView(this);
    }

    private static class ByteStringListView extends java.util.AbstractList<com.android.framework.protobuf.ByteString> implements java.util.RandomAccess {
        private final com.android.framework.protobuf.LazyStringArrayList list;

        ByteStringListView(com.android.framework.protobuf.LazyStringArrayList lazyStringArrayList) {
            this.list = lazyStringArrayList;
        }

        @Override // java.util.AbstractList, java.util.List
        public com.android.framework.protobuf.ByteString get(int i) {
            return this.list.getByteString(i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.list.size();
        }

        @Override // java.util.AbstractList, java.util.List
        public com.android.framework.protobuf.ByteString set(int i, com.android.framework.protobuf.ByteString byteString) {
            java.lang.Object andReturn = this.list.setAndReturn(i, byteString);
            this.modCount++;
            return com.android.framework.protobuf.LazyStringArrayList.asByteString(andReturn);
        }

        @Override // java.util.AbstractList, java.util.List
        public void add(int i, com.android.framework.protobuf.ByteString byteString) {
            this.list.add(i, byteString);
            this.modCount++;
        }

        @Override // java.util.AbstractList, java.util.List
        public com.android.framework.protobuf.ByteString remove(int i) {
            java.lang.String remove = this.list.remove(i);
            this.modCount++;
            return com.android.framework.protobuf.LazyStringArrayList.asByteString(remove);
        }
    }

    @Override // com.android.framework.protobuf.ProtocolStringList
    public java.util.List<com.android.framework.protobuf.ByteString> asByteStringList() {
        return new com.android.framework.protobuf.LazyStringArrayList.ByteStringListView(this);
    }

    @Override // com.android.framework.protobuf.LazyStringList
    public com.android.framework.protobuf.LazyStringList getUnmodifiableView() {
        if (isModifiable()) {
            return new com.android.framework.protobuf.UnmodifiableLazyStringList(this);
        }
        return this;
    }
}
