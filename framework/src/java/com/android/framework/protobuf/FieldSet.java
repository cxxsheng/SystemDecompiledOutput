package com.android.framework.protobuf;

/* loaded from: classes4.dex */
final class FieldSet<T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> {
    private static final int DEFAULT_FIELD_MAP_ARRAY_SIZE = 16;
    private static final com.android.framework.protobuf.FieldSet DEFAULT_INSTANCE = new com.android.framework.protobuf.FieldSet(true);
    private final com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object> fields;
    private boolean hasLazyField;
    private boolean isImmutable;

    public interface FieldDescriptorLite<T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> extends java.lang.Comparable<T> {
        com.android.framework.protobuf.Internal.EnumLiteMap<?> getEnumType();

        com.android.framework.protobuf.WireFormat.JavaType getLiteJavaType();

        com.android.framework.protobuf.WireFormat.FieldType getLiteType();

        int getNumber();

        com.android.framework.protobuf.MessageLite.Builder internalMergeFrom(com.android.framework.protobuf.MessageLite.Builder builder, com.android.framework.protobuf.MessageLite messageLite);

        boolean isPacked();

        boolean isRepeated();
    }

    /* synthetic */ FieldSet(com.android.framework.protobuf.SmallSortedMap smallSortedMap, com.android.framework.protobuf.FieldSet.AnonymousClass1 anonymousClass1) {
        this(smallSortedMap);
    }

    private FieldSet() {
        this.fields = com.android.framework.protobuf.SmallSortedMap.newFieldMap(16);
    }

    private FieldSet(boolean z) {
        this(com.android.framework.protobuf.SmallSortedMap.newFieldMap(0));
        makeImmutable();
    }

    private FieldSet(com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object> smallSortedMap) {
        this.fields = smallSortedMap;
        makeImmutable();
    }

    public static <T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> com.android.framework.protobuf.FieldSet<T> newFieldSet() {
        return new com.android.framework.protobuf.FieldSet<>();
    }

    public static <T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> com.android.framework.protobuf.FieldSet<T> emptySet() {
        return DEFAULT_INSTANCE;
    }

    public static <T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> com.android.framework.protobuf.FieldSet.Builder<T> newBuilder() {
        return new com.android.framework.protobuf.FieldSet.Builder<>((com.android.framework.protobuf.FieldSet.AnonymousClass1) null);
    }

    boolean isEmpty() {
        return this.fields.isEmpty();
    }

    public void makeImmutable() {
        if (this.isImmutable) {
            return;
        }
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            java.util.Map.Entry<T, java.lang.Object> arrayEntryAt = this.fields.getArrayEntryAt(i);
            if (arrayEntryAt.getValue() instanceof com.android.framework.protobuf.GeneratedMessageLite) {
                ((com.android.framework.protobuf.GeneratedMessageLite) arrayEntryAt.getValue()).makeImmutable();
            }
        }
        this.fields.makeImmutable();
        this.isImmutable = true;
    }

    public boolean isImmutable() {
        return this.isImmutable;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.framework.protobuf.FieldSet)) {
            return false;
        }
        return this.fields.equals(((com.android.framework.protobuf.FieldSet) obj).fields);
    }

    public int hashCode() {
        return this.fields.hashCode();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public com.android.framework.protobuf.FieldSet<T> m6592clone() {
        com.android.framework.protobuf.FieldSet<T> newFieldSet = newFieldSet();
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            java.util.Map.Entry<T, java.lang.Object> arrayEntryAt = this.fields.getArrayEntryAt(i);
            newFieldSet.setField(arrayEntryAt.getKey(), arrayEntryAt.getValue());
        }
        for (java.util.Map.Entry<T, java.lang.Object> entry : this.fields.getOverflowEntries()) {
            newFieldSet.setField(entry.getKey(), entry.getValue());
        }
        newFieldSet.hasLazyField = this.hasLazyField;
        return newFieldSet;
    }

    public void clear() {
        this.fields.clear();
        this.hasLazyField = false;
    }

    public java.util.Map<T, java.lang.Object> getAllFields() {
        if (!this.hasLazyField) {
            return this.fields.isImmutable() ? this.fields : java.util.Collections.unmodifiableMap(this.fields);
        }
        com.android.framework.protobuf.SmallSortedMap cloneAllFieldsMap = cloneAllFieldsMap(this.fields, false);
        if (this.fields.isImmutable()) {
            cloneAllFieldsMap.makeImmutable();
        }
        return cloneAllFieldsMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object> cloneAllFieldsMap(com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object> smallSortedMap, boolean z) {
        com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object> newFieldMap = com.android.framework.protobuf.SmallSortedMap.newFieldMap(16);
        for (int i = 0; i < smallSortedMap.getNumArrayEntries(); i++) {
            cloneFieldEntry(newFieldMap, smallSortedMap.getArrayEntryAt(i), z);
        }
        java.util.Iterator<java.util.Map.Entry<T, java.lang.Object>> it = smallSortedMap.getOverflowEntries().iterator();
        while (it.hasNext()) {
            cloneFieldEntry(newFieldMap, it.next(), z);
        }
        return newFieldMap;
    }

    private static <T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> void cloneFieldEntry(java.util.Map<T, java.lang.Object> map, java.util.Map.Entry<T, java.lang.Object> entry, boolean z) {
        T key = entry.getKey();
        java.lang.Object value = entry.getValue();
        if (value instanceof com.android.framework.protobuf.LazyField) {
            map.put(key, ((com.android.framework.protobuf.LazyField) value).getValue());
        } else if (z && (value instanceof java.util.List)) {
            map.put(key, new java.util.ArrayList((java.util.List) value));
        } else {
            map.put(key, value);
        }
    }

    public java.util.Iterator<java.util.Map.Entry<T, java.lang.Object>> iterator() {
        if (this.hasLazyField) {
            return new com.android.framework.protobuf.LazyField.LazyIterator(this.fields.entrySet().iterator());
        }
        return this.fields.entrySet().iterator();
    }

    java.util.Iterator<java.util.Map.Entry<T, java.lang.Object>> descendingIterator() {
        if (this.hasLazyField) {
            return new com.android.framework.protobuf.LazyField.LazyIterator(this.fields.descendingEntrySet().iterator());
        }
        return this.fields.descendingEntrySet().iterator();
    }

    public boolean hasField(T t) {
        if (t.isRepeated()) {
            throw new java.lang.IllegalArgumentException("hasField() can only be called on non-repeated fields.");
        }
        return this.fields.get(t) != null;
    }

    public java.lang.Object getField(T t) {
        java.lang.Object obj = this.fields.get(t);
        if (obj instanceof com.android.framework.protobuf.LazyField) {
            return ((com.android.framework.protobuf.LazyField) obj).getValue();
        }
        return obj;
    }

    public void setField(T t, java.lang.Object obj) {
        if (t.isRepeated()) {
            if (!(obj instanceof java.util.List)) {
                throw new java.lang.IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.addAll((java.util.List) obj);
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                verifyType(t, it.next());
            }
            obj = arrayList;
        } else {
            verifyType(t, obj);
        }
        if (obj instanceof com.android.framework.protobuf.LazyField) {
            this.hasLazyField = true;
        }
        this.fields.put((com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object>) t, (T) obj);
    }

    public void clearField(T t) {
        this.fields.remove(t);
        if (this.fields.isEmpty()) {
            this.hasLazyField = false;
        }
    }

    public int getRepeatedFieldCount(T t) {
        if (!t.isRepeated()) {
            throw new java.lang.IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
        }
        java.lang.Object field = getField(t);
        if (field == null) {
            return 0;
        }
        return ((java.util.List) field).size();
    }

    public java.lang.Object getRepeatedField(T t, int i) {
        if (!t.isRepeated()) {
            throw new java.lang.IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
        }
        java.lang.Object field = getField(t);
        if (field == null) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return ((java.util.List) field).get(i);
    }

    public void setRepeatedField(T t, int i, java.lang.Object obj) {
        if (!t.isRepeated()) {
            throw new java.lang.IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
        }
        java.lang.Object field = getField(t);
        if (field == null) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        verifyType(t, obj);
        ((java.util.List) field).set(i, obj);
    }

    public void addRepeatedField(T t, java.lang.Object obj) {
        java.util.List list;
        if (!t.isRepeated()) {
            throw new java.lang.IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
        }
        verifyType(t, obj);
        java.lang.Object field = getField(t);
        if (field == null) {
            list = new java.util.ArrayList();
            this.fields.put((com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object>) t, (T) list);
        } else {
            list = (java.util.List) field;
        }
        list.add(obj);
    }

    private void verifyType(T t, java.lang.Object obj) {
        if (!isValidType(t.getLiteType(), obj)) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", java.lang.Integer.valueOf(t.getNumber()), t.getLiteType().getJavaType(), obj.getClass().getName()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidType(com.android.framework.protobuf.WireFormat.FieldType fieldType, java.lang.Object obj) {
        com.android.framework.protobuf.Internal.checkNotNull(obj);
        switch (fieldType.getJavaType()) {
            case BYTE_STRING:
                if (!(obj instanceof com.android.framework.protobuf.ByteString) && !(obj instanceof byte[])) {
                    break;
                }
                break;
            case ENUM:
                if (!(obj instanceof java.lang.Integer) && !(obj instanceof com.android.framework.protobuf.Internal.EnumLite)) {
                    break;
                }
                break;
            case MESSAGE:
                if (!(obj instanceof com.android.framework.protobuf.MessageLite) && !(obj instanceof com.android.framework.protobuf.LazyField)) {
                    break;
                }
                break;
        }
        return false;
    }

    public boolean isInitialized() {
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            if (!isInitialized(this.fields.getArrayEntryAt(i))) {
                return false;
            }
        }
        java.util.Iterator<java.util.Map.Entry<T, java.lang.Object>> it = this.fields.getOverflowEntries().iterator();
        while (it.hasNext()) {
            if (!isInitialized(it.next())) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> boolean isInitialized(java.util.Map.Entry<T, java.lang.Object> entry) {
        T key = entry.getKey();
        if (key.getLiteJavaType() == com.android.framework.protobuf.WireFormat.JavaType.MESSAGE) {
            if (key.isRepeated()) {
                java.util.Iterator it = ((java.util.List) entry.getValue()).iterator();
                while (it.hasNext()) {
                    if (!isMessageFieldValueInitialized(it.next())) {
                        return false;
                    }
                }
                return true;
            }
            return isMessageFieldValueInitialized(entry.getValue());
        }
        return true;
    }

    private static boolean isMessageFieldValueInitialized(java.lang.Object obj) {
        if (obj instanceof com.android.framework.protobuf.MessageLiteOrBuilder) {
            return ((com.android.framework.protobuf.MessageLiteOrBuilder) obj).isInitialized();
        }
        if (obj instanceof com.android.framework.protobuf.LazyField) {
            return true;
        }
        throw new java.lang.IllegalArgumentException("Wrong object type used with protocol message reflection.");
    }

    static int getWireFormatForFieldType(com.android.framework.protobuf.WireFormat.FieldType fieldType, boolean z) {
        if (z) {
            return 2;
        }
        return fieldType.getWireType();
    }

    public void mergeFrom(com.android.framework.protobuf.FieldSet<T> fieldSet) {
        for (int i = 0; i < fieldSet.fields.getNumArrayEntries(); i++) {
            mergeFromField(fieldSet.fields.getArrayEntryAt(i));
        }
        java.util.Iterator<java.util.Map.Entry<T, java.lang.Object>> it = fieldSet.fields.getOverflowEntries().iterator();
        while (it.hasNext()) {
            mergeFromField(it.next());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.Object cloneIfMutable(java.lang.Object obj) {
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            byte[] bArr2 = new byte[bArr.length];
            java.lang.System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            return bArr2;
        }
        return obj;
    }

    private void mergeFromField(java.util.Map.Entry<T, java.lang.Object> entry) {
        T key = entry.getKey();
        java.lang.Object value = entry.getValue();
        if (value instanceof com.android.framework.protobuf.LazyField) {
            value = ((com.android.framework.protobuf.LazyField) value).getValue();
        }
        if (key.isRepeated()) {
            java.lang.Object field = getField(key);
            if (field == null) {
                field = new java.util.ArrayList();
            }
            java.util.Iterator it = ((java.util.List) value).iterator();
            while (it.hasNext()) {
                ((java.util.List) field).add(cloneIfMutable(it.next()));
            }
            this.fields.put((com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object>) key, (T) field);
            return;
        }
        if (key.getLiteJavaType() == com.android.framework.protobuf.WireFormat.JavaType.MESSAGE) {
            java.lang.Object field2 = getField(key);
            if (field2 == null) {
                this.fields.put((com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object>) key, (T) cloneIfMutable(value));
                return;
            } else {
                this.fields.put((com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object>) key, (T) key.internalMergeFrom(((com.android.framework.protobuf.MessageLite) field2).toBuilder(), (com.android.framework.protobuf.MessageLite) value).build());
                return;
            }
        }
        this.fields.put((com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object>) key, (T) cloneIfMutable(value));
    }

    public static java.lang.Object readPrimitiveField(com.android.framework.protobuf.CodedInputStream codedInputStream, com.android.framework.protobuf.WireFormat.FieldType fieldType, boolean z) throws java.io.IOException {
        if (z) {
            return com.android.framework.protobuf.WireFormat.readPrimitiveField(codedInputStream, fieldType, com.android.framework.protobuf.WireFormat.Utf8Validation.STRICT);
        }
        return com.android.framework.protobuf.WireFormat.readPrimitiveField(codedInputStream, fieldType, com.android.framework.protobuf.WireFormat.Utf8Validation.LOOSE);
    }

    public void writeTo(com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            java.util.Map.Entry<T, java.lang.Object> arrayEntryAt = this.fields.getArrayEntryAt(i);
            writeField(arrayEntryAt.getKey(), arrayEntryAt.getValue(), codedOutputStream);
        }
        for (java.util.Map.Entry<T, java.lang.Object> entry : this.fields.getOverflowEntries()) {
            writeField(entry.getKey(), entry.getValue(), codedOutputStream);
        }
    }

    public void writeMessageSetTo(com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            writeMessageSetTo(this.fields.getArrayEntryAt(i), codedOutputStream);
        }
        java.util.Iterator<java.util.Map.Entry<T, java.lang.Object>> it = this.fields.getOverflowEntries().iterator();
        while (it.hasNext()) {
            writeMessageSetTo(it.next(), codedOutputStream);
        }
    }

    private void writeMessageSetTo(java.util.Map.Entry<T, java.lang.Object> entry, com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
        T key = entry.getKey();
        if (key.getLiteJavaType() == com.android.framework.protobuf.WireFormat.JavaType.MESSAGE && !key.isRepeated() && !key.isPacked()) {
            java.lang.Object value = entry.getValue();
            if (value instanceof com.android.framework.protobuf.LazyField) {
                value = ((com.android.framework.protobuf.LazyField) value).getValue();
            }
            codedOutputStream.writeMessageSetExtension(entry.getKey().getNumber(), (com.android.framework.protobuf.MessageLite) value);
            return;
        }
        writeField(key, entry.getValue(), codedOutputStream);
    }

    static void writeElement(com.android.framework.protobuf.CodedOutputStream codedOutputStream, com.android.framework.protobuf.WireFormat.FieldType fieldType, int i, java.lang.Object obj) throws java.io.IOException {
        if (fieldType == com.android.framework.protobuf.WireFormat.FieldType.GROUP) {
            codedOutputStream.writeGroup(i, (com.android.framework.protobuf.MessageLite) obj);
        } else {
            codedOutputStream.writeTag(i, getWireFormatForFieldType(fieldType, false));
            writeElementNoTag(codedOutputStream, fieldType, obj);
        }
    }

    /* renamed from: com.android.framework.protobuf.FieldSet$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$FieldType = new int[com.android.framework.protobuf.WireFormat.FieldType.values().length];

        static {
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.DOUBLE.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.FLOAT.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.INT64.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.UINT64.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.INT32.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.FIXED64.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.FIXED32.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.BOOL.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.GROUP.ordinal()] = 9;
            } catch (java.lang.NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.MESSAGE.ordinal()] = 10;
            } catch (java.lang.NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.STRING.ordinal()] = 11;
            } catch (java.lang.NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.BYTES.ordinal()] = 12;
            } catch (java.lang.NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.UINT32.ordinal()] = 13;
            } catch (java.lang.NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SFIXED32.ordinal()] = 14;
            } catch (java.lang.NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SFIXED64.ordinal()] = 15;
            } catch (java.lang.NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SINT32.ordinal()] = 16;
            } catch (java.lang.NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SINT64.ordinal()] = 17;
            } catch (java.lang.NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.ENUM.ordinal()] = 18;
            } catch (java.lang.NoSuchFieldError e18) {
            }
            $SwitchMap$com$google$protobuf$WireFormat$JavaType = new int[com.android.framework.protobuf.WireFormat.JavaType.values().length];
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[com.android.framework.protobuf.WireFormat.JavaType.INT.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[com.android.framework.protobuf.WireFormat.JavaType.LONG.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[com.android.framework.protobuf.WireFormat.JavaType.FLOAT.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[com.android.framework.protobuf.WireFormat.JavaType.DOUBLE.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[com.android.framework.protobuf.WireFormat.JavaType.BOOLEAN.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[com.android.framework.protobuf.WireFormat.JavaType.STRING.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[com.android.framework.protobuf.WireFormat.JavaType.BYTE_STRING.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[com.android.framework.protobuf.WireFormat.JavaType.ENUM.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[com.android.framework.protobuf.WireFormat.JavaType.MESSAGE.ordinal()] = 9;
            } catch (java.lang.NoSuchFieldError e27) {
            }
        }
    }

    static void writeElementNoTag(com.android.framework.protobuf.CodedOutputStream codedOutputStream, com.android.framework.protobuf.WireFormat.FieldType fieldType, java.lang.Object obj) throws java.io.IOException {
        switch (com.android.framework.protobuf.FieldSet.AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[fieldType.ordinal()]) {
            case 1:
                codedOutputStream.writeDoubleNoTag(((java.lang.Double) obj).doubleValue());
                break;
            case 2:
                codedOutputStream.writeFloatNoTag(((java.lang.Float) obj).floatValue());
                break;
            case 3:
                codedOutputStream.writeInt64NoTag(((java.lang.Long) obj).longValue());
                break;
            case 4:
                codedOutputStream.writeUInt64NoTag(((java.lang.Long) obj).longValue());
                break;
            case 5:
                codedOutputStream.writeInt32NoTag(((java.lang.Integer) obj).intValue());
                break;
            case 6:
                codedOutputStream.writeFixed64NoTag(((java.lang.Long) obj).longValue());
                break;
            case 7:
                codedOutputStream.writeFixed32NoTag(((java.lang.Integer) obj).intValue());
                break;
            case 8:
                codedOutputStream.writeBoolNoTag(((java.lang.Boolean) obj).booleanValue());
                break;
            case 9:
                codedOutputStream.writeGroupNoTag((com.android.framework.protobuf.MessageLite) obj);
                break;
            case 10:
                codedOutputStream.writeMessageNoTag((com.android.framework.protobuf.MessageLite) obj);
                break;
            case 11:
                if (obj instanceof com.android.framework.protobuf.ByteString) {
                    codedOutputStream.writeBytesNoTag((com.android.framework.protobuf.ByteString) obj);
                    break;
                } else {
                    codedOutputStream.writeStringNoTag((java.lang.String) obj);
                    break;
                }
            case 12:
                if (obj instanceof com.android.framework.protobuf.ByteString) {
                    codedOutputStream.writeBytesNoTag((com.android.framework.protobuf.ByteString) obj);
                    break;
                } else {
                    codedOutputStream.writeByteArrayNoTag((byte[]) obj);
                    break;
                }
            case 13:
                codedOutputStream.writeUInt32NoTag(((java.lang.Integer) obj).intValue());
                break;
            case 14:
                codedOutputStream.writeSFixed32NoTag(((java.lang.Integer) obj).intValue());
                break;
            case 15:
                codedOutputStream.writeSFixed64NoTag(((java.lang.Long) obj).longValue());
                break;
            case 16:
                codedOutputStream.writeSInt32NoTag(((java.lang.Integer) obj).intValue());
                break;
            case 17:
                codedOutputStream.writeSInt64NoTag(((java.lang.Long) obj).longValue());
                break;
            case 18:
                if (obj instanceof com.android.framework.protobuf.Internal.EnumLite) {
                    codedOutputStream.writeEnumNoTag(((com.android.framework.protobuf.Internal.EnumLite) obj).getNumber());
                    break;
                } else {
                    codedOutputStream.writeEnumNoTag(((java.lang.Integer) obj).intValue());
                    break;
                }
        }
    }

    public static void writeField(com.android.framework.protobuf.FieldSet.FieldDescriptorLite<?> fieldDescriptorLite, java.lang.Object obj, com.android.framework.protobuf.CodedOutputStream codedOutputStream) throws java.io.IOException {
        com.android.framework.protobuf.WireFormat.FieldType liteType = fieldDescriptorLite.getLiteType();
        int number = fieldDescriptorLite.getNumber();
        if (fieldDescriptorLite.isRepeated()) {
            java.util.List list = (java.util.List) obj;
            if (fieldDescriptorLite.isPacked()) {
                codedOutputStream.writeTag(number, 2);
                java.util.Iterator it = list.iterator();
                int i = 0;
                while (it.hasNext()) {
                    i += computeElementSizeNoTag(liteType, it.next());
                }
                codedOutputStream.writeUInt32NoTag(i);
                java.util.Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    writeElementNoTag(codedOutputStream, liteType, it2.next());
                }
                return;
            }
            java.util.Iterator it3 = list.iterator();
            while (it3.hasNext()) {
                writeElement(codedOutputStream, liteType, number, it3.next());
            }
            return;
        }
        if (obj instanceof com.android.framework.protobuf.LazyField) {
            writeElement(codedOutputStream, liteType, number, ((com.android.framework.protobuf.LazyField) obj).getValue());
        } else {
            writeElement(codedOutputStream, liteType, number, obj);
        }
    }

    public int getSerializedSize() {
        int i = 0;
        for (int i2 = 0; i2 < this.fields.getNumArrayEntries(); i2++) {
            java.util.Map.Entry<T, java.lang.Object> arrayEntryAt = this.fields.getArrayEntryAt(i2);
            i += computeFieldSize(arrayEntryAt.getKey(), arrayEntryAt.getValue());
        }
        for (java.util.Map.Entry<T, java.lang.Object> entry : this.fields.getOverflowEntries()) {
            i += computeFieldSize(entry.getKey(), entry.getValue());
        }
        return i;
    }

    public int getMessageSetSerializedSize() {
        int i = 0;
        for (int i2 = 0; i2 < this.fields.getNumArrayEntries(); i2++) {
            i += getMessageSetSerializedSize(this.fields.getArrayEntryAt(i2));
        }
        java.util.Iterator<java.util.Map.Entry<T, java.lang.Object>> it = this.fields.getOverflowEntries().iterator();
        while (it.hasNext()) {
            i += getMessageSetSerializedSize(it.next());
        }
        return i;
    }

    private int getMessageSetSerializedSize(java.util.Map.Entry<T, java.lang.Object> entry) {
        T key = entry.getKey();
        java.lang.Object value = entry.getValue();
        if (key.getLiteJavaType() == com.android.framework.protobuf.WireFormat.JavaType.MESSAGE && !key.isRepeated() && !key.isPacked()) {
            if (value instanceof com.android.framework.protobuf.LazyField) {
                return com.android.framework.protobuf.CodedOutputStream.computeLazyFieldMessageSetExtensionSize(entry.getKey().getNumber(), (com.android.framework.protobuf.LazyField) value);
            }
            return com.android.framework.protobuf.CodedOutputStream.computeMessageSetExtensionSize(entry.getKey().getNumber(), (com.android.framework.protobuf.MessageLite) value);
        }
        return computeFieldSize(key, value);
    }

    static int computeElementSize(com.android.framework.protobuf.WireFormat.FieldType fieldType, int i, java.lang.Object obj) {
        int computeTagSize = com.android.framework.protobuf.CodedOutputStream.computeTagSize(i);
        if (fieldType == com.android.framework.protobuf.WireFormat.FieldType.GROUP) {
            computeTagSize *= 2;
        }
        return computeTagSize + computeElementSizeNoTag(fieldType, obj);
    }

    static int computeElementSizeNoTag(com.android.framework.protobuf.WireFormat.FieldType fieldType, java.lang.Object obj) {
        switch (com.android.framework.protobuf.FieldSet.AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[fieldType.ordinal()]) {
            case 1:
                return com.android.framework.protobuf.CodedOutputStream.computeDoubleSizeNoTag(((java.lang.Double) obj).doubleValue());
            case 2:
                return com.android.framework.protobuf.CodedOutputStream.computeFloatSizeNoTag(((java.lang.Float) obj).floatValue());
            case 3:
                return com.android.framework.protobuf.CodedOutputStream.computeInt64SizeNoTag(((java.lang.Long) obj).longValue());
            case 4:
                return com.android.framework.protobuf.CodedOutputStream.computeUInt64SizeNoTag(((java.lang.Long) obj).longValue());
            case 5:
                return com.android.framework.protobuf.CodedOutputStream.computeInt32SizeNoTag(((java.lang.Integer) obj).intValue());
            case 6:
                return com.android.framework.protobuf.CodedOutputStream.computeFixed64SizeNoTag(((java.lang.Long) obj).longValue());
            case 7:
                return com.android.framework.protobuf.CodedOutputStream.computeFixed32SizeNoTag(((java.lang.Integer) obj).intValue());
            case 8:
                return com.android.framework.protobuf.CodedOutputStream.computeBoolSizeNoTag(((java.lang.Boolean) obj).booleanValue());
            case 9:
                return com.android.framework.protobuf.CodedOutputStream.computeGroupSizeNoTag((com.android.framework.protobuf.MessageLite) obj);
            case 10:
                if (obj instanceof com.android.framework.protobuf.LazyField) {
                    return com.android.framework.protobuf.CodedOutputStream.computeLazyFieldSizeNoTag((com.android.framework.protobuf.LazyField) obj);
                }
                return com.android.framework.protobuf.CodedOutputStream.computeMessageSizeNoTag((com.android.framework.protobuf.MessageLite) obj);
            case 11:
                if (obj instanceof com.android.framework.protobuf.ByteString) {
                    return com.android.framework.protobuf.CodedOutputStream.computeBytesSizeNoTag((com.android.framework.protobuf.ByteString) obj);
                }
                return com.android.framework.protobuf.CodedOutputStream.computeStringSizeNoTag((java.lang.String) obj);
            case 12:
                if (obj instanceof com.android.framework.protobuf.ByteString) {
                    return com.android.framework.protobuf.CodedOutputStream.computeBytesSizeNoTag((com.android.framework.protobuf.ByteString) obj);
                }
                return com.android.framework.protobuf.CodedOutputStream.computeByteArraySizeNoTag((byte[]) obj);
            case 13:
                return com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(((java.lang.Integer) obj).intValue());
            case 14:
                return com.android.framework.protobuf.CodedOutputStream.computeSFixed32SizeNoTag(((java.lang.Integer) obj).intValue());
            case 15:
                return com.android.framework.protobuf.CodedOutputStream.computeSFixed64SizeNoTag(((java.lang.Long) obj).longValue());
            case 16:
                return com.android.framework.protobuf.CodedOutputStream.computeSInt32SizeNoTag(((java.lang.Integer) obj).intValue());
            case 17:
                return com.android.framework.protobuf.CodedOutputStream.computeSInt64SizeNoTag(((java.lang.Long) obj).longValue());
            case 18:
                if (obj instanceof com.android.framework.protobuf.Internal.EnumLite) {
                    return com.android.framework.protobuf.CodedOutputStream.computeEnumSizeNoTag(((com.android.framework.protobuf.Internal.EnumLite) obj).getNumber());
                }
                return com.android.framework.protobuf.CodedOutputStream.computeEnumSizeNoTag(((java.lang.Integer) obj).intValue());
            default:
                throw new java.lang.RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int computeFieldSize(com.android.framework.protobuf.FieldSet.FieldDescriptorLite<?> fieldDescriptorLite, java.lang.Object obj) {
        com.android.framework.protobuf.WireFormat.FieldType liteType = fieldDescriptorLite.getLiteType();
        int number = fieldDescriptorLite.getNumber();
        if (fieldDescriptorLite.isRepeated()) {
            int i = 0;
            if (fieldDescriptorLite.isPacked()) {
                java.util.Iterator it = ((java.util.List) obj).iterator();
                while (it.hasNext()) {
                    i += computeElementSizeNoTag(liteType, it.next());
                }
                return com.android.framework.protobuf.CodedOutputStream.computeTagSize(number) + i + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(i);
            }
            java.util.Iterator it2 = ((java.util.List) obj).iterator();
            while (it2.hasNext()) {
                i += computeElementSize(liteType, number, it2.next());
            }
            return i;
        }
        return computeElementSize(liteType, number, obj);
    }

    static final class Builder<T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> {
        private com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object> fields;
        private boolean hasLazyField;
        private boolean hasNestedBuilders;
        private boolean isMutable;

        /* synthetic */ Builder(com.android.framework.protobuf.FieldSet.AnonymousClass1 anonymousClass1) {
            this();
        }

        private Builder() {
            this(com.android.framework.protobuf.SmallSortedMap.newFieldMap(16));
        }

        private Builder(com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object> smallSortedMap) {
            this.fields = smallSortedMap;
            this.isMutable = true;
        }

        public com.android.framework.protobuf.FieldSet<T> build() {
            return buildImpl(false);
        }

        public com.android.framework.protobuf.FieldSet<T> buildPartial() {
            return buildImpl(true);
        }

        private com.android.framework.protobuf.FieldSet<T> buildImpl(boolean z) {
            if (this.fields.isEmpty()) {
                return com.android.framework.protobuf.FieldSet.emptySet();
            }
            this.isMutable = false;
            com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object> smallSortedMap = this.fields;
            if (this.hasNestedBuilders) {
                smallSortedMap = com.android.framework.protobuf.FieldSet.cloneAllFieldsMap(this.fields, false);
                replaceBuilders(smallSortedMap, z);
            }
            com.android.framework.protobuf.FieldSet<T> fieldSet = new com.android.framework.protobuf.FieldSet<>(smallSortedMap, null);
            ((com.android.framework.protobuf.FieldSet) fieldSet).hasLazyField = this.hasLazyField;
            return fieldSet;
        }

        private static <T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> void replaceBuilders(com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object> smallSortedMap, boolean z) {
            for (int i = 0; i < smallSortedMap.getNumArrayEntries(); i++) {
                replaceBuilders(smallSortedMap.getArrayEntryAt(i), z);
            }
            java.util.Iterator<java.util.Map.Entry<T, java.lang.Object>> it = smallSortedMap.getOverflowEntries().iterator();
            while (it.hasNext()) {
                replaceBuilders(it.next(), z);
            }
        }

        private static <T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> void replaceBuilders(java.util.Map.Entry<T, java.lang.Object> entry, boolean z) {
            entry.setValue(replaceBuilders(entry.getKey(), entry.getValue(), z));
        }

        private static <T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> java.lang.Object replaceBuilders(T t, java.lang.Object obj, boolean z) {
            if (obj == null) {
                return obj;
            }
            if (t.getLiteJavaType() == com.android.framework.protobuf.WireFormat.JavaType.MESSAGE) {
                if (t.isRepeated()) {
                    if (!(obj instanceof java.util.List)) {
                        throw new java.lang.IllegalStateException("Repeated field should contains a List but actually contains type: " + obj.getClass());
                    }
                    java.util.List list = (java.util.List) obj;
                    for (int i = 0; i < list.size(); i++) {
                        java.lang.Object obj2 = list.get(i);
                        java.lang.Object replaceBuilder = replaceBuilder(obj2, z);
                        if (replaceBuilder != obj2) {
                            if (list == obj) {
                                list = new java.util.ArrayList(list);
                            }
                            list.set(i, replaceBuilder);
                        }
                    }
                    return list;
                }
                return replaceBuilder(obj, z);
            }
            return obj;
        }

        private static java.lang.Object replaceBuilder(java.lang.Object obj, boolean z) {
            if (!(obj instanceof com.android.framework.protobuf.MessageLite.Builder)) {
                return obj;
            }
            com.android.framework.protobuf.MessageLite.Builder builder = (com.android.framework.protobuf.MessageLite.Builder) obj;
            if (z) {
                return builder.buildPartial();
            }
            return builder.build();
        }

        public static <T extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<T>> com.android.framework.protobuf.FieldSet.Builder<T> fromFieldSet(com.android.framework.protobuf.FieldSet<T> fieldSet) {
            com.android.framework.protobuf.FieldSet.Builder<T> builder = new com.android.framework.protobuf.FieldSet.Builder<>(com.android.framework.protobuf.FieldSet.cloneAllFieldsMap(((com.android.framework.protobuf.FieldSet) fieldSet).fields, true));
            ((com.android.framework.protobuf.FieldSet.Builder) builder).hasLazyField = ((com.android.framework.protobuf.FieldSet) fieldSet).hasLazyField;
            return builder;
        }

        public java.util.Map<T, java.lang.Object> getAllFields() {
            if (!this.hasLazyField) {
                return this.fields.isImmutable() ? this.fields : java.util.Collections.unmodifiableMap(this.fields);
            }
            com.android.framework.protobuf.SmallSortedMap cloneAllFieldsMap = com.android.framework.protobuf.FieldSet.cloneAllFieldsMap(this.fields, false);
            if (this.fields.isImmutable()) {
                cloneAllFieldsMap.makeImmutable();
            } else {
                replaceBuilders(cloneAllFieldsMap, true);
            }
            return cloneAllFieldsMap;
        }

        public boolean hasField(T t) {
            if (t.isRepeated()) {
                throw new java.lang.IllegalArgumentException("hasField() can only be called on non-repeated fields.");
            }
            return this.fields.get(t) != null;
        }

        public java.lang.Object getField(T t) {
            return replaceBuilders(t, getFieldAllowBuilders(t), true);
        }

        java.lang.Object getFieldAllowBuilders(T t) {
            java.lang.Object obj = this.fields.get(t);
            if (obj instanceof com.android.framework.protobuf.LazyField) {
                return ((com.android.framework.protobuf.LazyField) obj).getValue();
            }
            return obj;
        }

        private void ensureIsMutable() {
            if (!this.isMutable) {
                this.fields = com.android.framework.protobuf.FieldSet.cloneAllFieldsMap(this.fields, true);
                this.isMutable = true;
            }
        }

        public void setField(T t, java.lang.Object obj) {
            ensureIsMutable();
            if (t.isRepeated()) {
                if (!(obj instanceof java.util.List)) {
                    throw new java.lang.IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
                java.util.ArrayList arrayList = new java.util.ArrayList((java.util.List) obj);
                for (java.lang.Object obj2 : arrayList) {
                    verifyType(t, obj2);
                    this.hasNestedBuilders = this.hasNestedBuilders || (obj2 instanceof com.android.framework.protobuf.MessageLite.Builder);
                }
                obj = arrayList;
            } else {
                verifyType(t, obj);
            }
            if (obj instanceof com.android.framework.protobuf.LazyField) {
                this.hasLazyField = true;
            }
            this.hasNestedBuilders = this.hasNestedBuilders || (obj instanceof com.android.framework.protobuf.MessageLite.Builder);
            this.fields.put((com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object>) t, (T) obj);
        }

        public void clearField(T t) {
            ensureIsMutable();
            this.fields.remove(t);
            if (this.fields.isEmpty()) {
                this.hasLazyField = false;
            }
        }

        public int getRepeatedFieldCount(T t) {
            if (!t.isRepeated()) {
                throw new java.lang.IllegalArgumentException("getRepeatedFieldCount() can only be called on repeated fields.");
            }
            java.lang.Object fieldAllowBuilders = getFieldAllowBuilders(t);
            if (fieldAllowBuilders == null) {
                return 0;
            }
            return ((java.util.List) fieldAllowBuilders).size();
        }

        public java.lang.Object getRepeatedField(T t, int i) {
            if (this.hasNestedBuilders) {
                ensureIsMutable();
            }
            return replaceBuilder(getRepeatedFieldAllowBuilders(t, i), true);
        }

        java.lang.Object getRepeatedFieldAllowBuilders(T t, int i) {
            if (!t.isRepeated()) {
                throw new java.lang.IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
            }
            java.lang.Object fieldAllowBuilders = getFieldAllowBuilders(t);
            if (fieldAllowBuilders == null) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            return ((java.util.List) fieldAllowBuilders).get(i);
        }

        public void setRepeatedField(T t, int i, java.lang.Object obj) {
            ensureIsMutable();
            if (!t.isRepeated()) {
                throw new java.lang.IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
            }
            this.hasNestedBuilders = this.hasNestedBuilders || (obj instanceof com.android.framework.protobuf.MessageLite.Builder);
            java.lang.Object fieldAllowBuilders = getFieldAllowBuilders(t);
            if (fieldAllowBuilders == null) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            verifyType(t, obj);
            ((java.util.List) fieldAllowBuilders).set(i, obj);
        }

        public void addRepeatedField(T t, java.lang.Object obj) {
            java.util.List list;
            ensureIsMutable();
            if (!t.isRepeated()) {
                throw new java.lang.IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
            }
            this.hasNestedBuilders = this.hasNestedBuilders || (obj instanceof com.android.framework.protobuf.MessageLite.Builder);
            verifyType(t, obj);
            java.lang.Object fieldAllowBuilders = getFieldAllowBuilders(t);
            if (fieldAllowBuilders == null) {
                list = new java.util.ArrayList();
                this.fields.put((com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object>) t, (T) list);
            } else {
                list = (java.util.List) fieldAllowBuilders;
            }
            list.add(obj);
        }

        private void verifyType(T t, java.lang.Object obj) {
            if (!com.android.framework.protobuf.FieldSet.isValidType(t.getLiteType(), obj)) {
                if (t.getLiteType().getJavaType() == com.android.framework.protobuf.WireFormat.JavaType.MESSAGE && (obj instanceof com.android.framework.protobuf.MessageLite.Builder)) {
                } else {
                    throw new java.lang.IllegalArgumentException(java.lang.String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", java.lang.Integer.valueOf(t.getNumber()), t.getLiteType().getJavaType(), obj.getClass().getName()));
                }
            }
        }

        public boolean isInitialized() {
            for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
                if (!com.android.framework.protobuf.FieldSet.isInitialized(this.fields.getArrayEntryAt(i))) {
                    return false;
                }
            }
            java.util.Iterator<java.util.Map.Entry<T, java.lang.Object>> it = this.fields.getOverflowEntries().iterator();
            while (it.hasNext()) {
                if (!com.android.framework.protobuf.FieldSet.isInitialized(it.next())) {
                    return false;
                }
            }
            return true;
        }

        public void mergeFrom(com.android.framework.protobuf.FieldSet<T> fieldSet) {
            ensureIsMutable();
            for (int i = 0; i < ((com.android.framework.protobuf.FieldSet) fieldSet).fields.getNumArrayEntries(); i++) {
                mergeFromField(((com.android.framework.protobuf.FieldSet) fieldSet).fields.getArrayEntryAt(i));
            }
            java.util.Iterator it = ((com.android.framework.protobuf.FieldSet) fieldSet).fields.getOverflowEntries().iterator();
            while (it.hasNext()) {
                mergeFromField((java.util.Map.Entry) it.next());
            }
        }

        private void mergeFromField(java.util.Map.Entry<T, java.lang.Object> entry) {
            T key = entry.getKey();
            java.lang.Object value = entry.getValue();
            if (value instanceof com.android.framework.protobuf.LazyField) {
                value = ((com.android.framework.protobuf.LazyField) value).getValue();
            }
            if (key.isRepeated()) {
                java.util.List list = (java.util.List) getFieldAllowBuilders(key);
                if (list == null) {
                    list = new java.util.ArrayList();
                    this.fields.put((com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object>) key, (T) list);
                }
                java.util.Iterator it = ((java.util.List) value).iterator();
                while (it.hasNext()) {
                    list.add(com.android.framework.protobuf.FieldSet.cloneIfMutable(it.next()));
                }
                return;
            }
            if (key.getLiteJavaType() != com.android.framework.protobuf.WireFormat.JavaType.MESSAGE) {
                this.fields.put((com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object>) key, (T) com.android.framework.protobuf.FieldSet.cloneIfMutable(value));
                return;
            }
            java.lang.Object fieldAllowBuilders = getFieldAllowBuilders(key);
            if (fieldAllowBuilders == null) {
                this.fields.put((com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object>) key, (T) com.android.framework.protobuf.FieldSet.cloneIfMutable(value));
            } else if (fieldAllowBuilders instanceof com.android.framework.protobuf.MessageLite.Builder) {
                key.internalMergeFrom((com.android.framework.protobuf.MessageLite.Builder) fieldAllowBuilders, (com.android.framework.protobuf.MessageLite) value);
            } else {
                this.fields.put((com.android.framework.protobuf.SmallSortedMap<T, java.lang.Object>) key, (T) key.internalMergeFrom(((com.android.framework.protobuf.MessageLite) fieldAllowBuilders).toBuilder(), (com.android.framework.protobuf.MessageLite) value).build());
            }
        }
    }
}
