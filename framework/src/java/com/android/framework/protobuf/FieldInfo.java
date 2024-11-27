package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class FieldInfo implements java.lang.Comparable<com.android.framework.protobuf.FieldInfo> {
    private final java.lang.reflect.Field cachedSizeField;
    private final boolean enforceUtf8;
    private final com.android.framework.protobuf.Internal.EnumVerifier enumVerifier;
    private final java.lang.reflect.Field field;
    private final int fieldNumber;
    private final java.lang.Object mapDefaultEntry;
    private final java.lang.Class<?> messageClass;
    private final com.android.framework.protobuf.OneofInfo oneof;
    private final java.lang.Class<?> oneofStoredType;
    private final java.lang.reflect.Field presenceField;
    private final int presenceMask;
    private final boolean required;
    private final com.android.framework.protobuf.FieldType type;

    public static com.android.framework.protobuf.FieldInfo forField(java.lang.reflect.Field field, int i, com.android.framework.protobuf.FieldType fieldType, boolean z) {
        checkFieldNumber(i);
        com.android.framework.protobuf.Internal.checkNotNull(field, "field");
        com.android.framework.protobuf.Internal.checkNotNull(fieldType, "fieldType");
        if (fieldType == com.android.framework.protobuf.FieldType.MESSAGE_LIST || fieldType == com.android.framework.protobuf.FieldType.GROUP_LIST) {
            throw new java.lang.IllegalStateException("Shouldn't be called for repeated message fields.");
        }
        return new com.android.framework.protobuf.FieldInfo(field, i, fieldType, null, null, 0, false, z, null, null, null, null, null);
    }

    public static com.android.framework.protobuf.FieldInfo forPackedField(java.lang.reflect.Field field, int i, com.android.framework.protobuf.FieldType fieldType, java.lang.reflect.Field field2) {
        checkFieldNumber(i);
        com.android.framework.protobuf.Internal.checkNotNull(field, "field");
        com.android.framework.protobuf.Internal.checkNotNull(fieldType, "fieldType");
        if (fieldType == com.android.framework.protobuf.FieldType.MESSAGE_LIST || fieldType == com.android.framework.protobuf.FieldType.GROUP_LIST) {
            throw new java.lang.IllegalStateException("Shouldn't be called for repeated message fields.");
        }
        return new com.android.framework.protobuf.FieldInfo(field, i, fieldType, null, null, 0, false, false, null, null, null, null, field2);
    }

    public static com.android.framework.protobuf.FieldInfo forRepeatedMessageField(java.lang.reflect.Field field, int i, com.android.framework.protobuf.FieldType fieldType, java.lang.Class<?> cls) {
        checkFieldNumber(i);
        com.android.framework.protobuf.Internal.checkNotNull(field, "field");
        com.android.framework.protobuf.Internal.checkNotNull(fieldType, "fieldType");
        com.android.framework.protobuf.Internal.checkNotNull(cls, "messageClass");
        return new com.android.framework.protobuf.FieldInfo(field, i, fieldType, cls, null, 0, false, false, null, null, null, null, null);
    }

    public static com.android.framework.protobuf.FieldInfo forFieldWithEnumVerifier(java.lang.reflect.Field field, int i, com.android.framework.protobuf.FieldType fieldType, com.android.framework.protobuf.Internal.EnumVerifier enumVerifier) {
        checkFieldNumber(i);
        com.android.framework.protobuf.Internal.checkNotNull(field, "field");
        return new com.android.framework.protobuf.FieldInfo(field, i, fieldType, null, null, 0, false, false, null, null, null, enumVerifier, null);
    }

    public static com.android.framework.protobuf.FieldInfo forPackedFieldWithEnumVerifier(java.lang.reflect.Field field, int i, com.android.framework.protobuf.FieldType fieldType, com.android.framework.protobuf.Internal.EnumVerifier enumVerifier, java.lang.reflect.Field field2) {
        checkFieldNumber(i);
        com.android.framework.protobuf.Internal.checkNotNull(field, "field");
        return new com.android.framework.protobuf.FieldInfo(field, i, fieldType, null, null, 0, false, false, null, null, null, enumVerifier, field2);
    }

    public static com.android.framework.protobuf.FieldInfo forProto2OptionalField(java.lang.reflect.Field field, int i, com.android.framework.protobuf.FieldType fieldType, java.lang.reflect.Field field2, int i2, boolean z, com.android.framework.protobuf.Internal.EnumVerifier enumVerifier) {
        checkFieldNumber(i);
        com.android.framework.protobuf.Internal.checkNotNull(field, "field");
        com.android.framework.protobuf.Internal.checkNotNull(fieldType, "fieldType");
        com.android.framework.protobuf.Internal.checkNotNull(field2, "presenceField");
        if (field2 != null && !isExactlyOneBitSet(i2)) {
            throw new java.lang.IllegalArgumentException("presenceMask must have exactly one bit set: " + i2);
        }
        return new com.android.framework.protobuf.FieldInfo(field, i, fieldType, null, field2, i2, false, z, null, null, null, enumVerifier, null);
    }

    public static com.android.framework.protobuf.FieldInfo forOneofMemberField(int i, com.android.framework.protobuf.FieldType fieldType, com.android.framework.protobuf.OneofInfo oneofInfo, java.lang.Class<?> cls, boolean z, com.android.framework.protobuf.Internal.EnumVerifier enumVerifier) {
        checkFieldNumber(i);
        com.android.framework.protobuf.Internal.checkNotNull(fieldType, "fieldType");
        com.android.framework.protobuf.Internal.checkNotNull(oneofInfo, "oneof");
        com.android.framework.protobuf.Internal.checkNotNull(cls, "oneofStoredType");
        if (!fieldType.isScalar()) {
            throw new java.lang.IllegalArgumentException("Oneof is only supported for scalar fields. Field " + i + " is of type " + fieldType);
        }
        return new com.android.framework.protobuf.FieldInfo(null, i, fieldType, null, null, 0, false, z, oneofInfo, cls, null, enumVerifier, null);
    }

    private static void checkFieldNumber(int i) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("fieldNumber must be positive: " + i);
        }
    }

    public static com.android.framework.protobuf.FieldInfo forProto2RequiredField(java.lang.reflect.Field field, int i, com.android.framework.protobuf.FieldType fieldType, java.lang.reflect.Field field2, int i2, boolean z, com.android.framework.protobuf.Internal.EnumVerifier enumVerifier) {
        checkFieldNumber(i);
        com.android.framework.protobuf.Internal.checkNotNull(field, "field");
        com.android.framework.protobuf.Internal.checkNotNull(fieldType, "fieldType");
        com.android.framework.protobuf.Internal.checkNotNull(field2, "presenceField");
        if (field2 != null && !isExactlyOneBitSet(i2)) {
            throw new java.lang.IllegalArgumentException("presenceMask must have exactly one bit set: " + i2);
        }
        return new com.android.framework.protobuf.FieldInfo(field, i, fieldType, null, field2, i2, true, z, null, null, null, enumVerifier, null);
    }

    public static com.android.framework.protobuf.FieldInfo forMapField(java.lang.reflect.Field field, int i, java.lang.Object obj, com.android.framework.protobuf.Internal.EnumVerifier enumVerifier) {
        com.android.framework.protobuf.Internal.checkNotNull(obj, "mapDefaultEntry");
        checkFieldNumber(i);
        com.android.framework.protobuf.Internal.checkNotNull(field, "field");
        return new com.android.framework.protobuf.FieldInfo(field, i, com.android.framework.protobuf.FieldType.MAP, null, null, 0, false, true, null, null, obj, enumVerifier, null);
    }

    private FieldInfo(java.lang.reflect.Field field, int i, com.android.framework.protobuf.FieldType fieldType, java.lang.Class<?> cls, java.lang.reflect.Field field2, int i2, boolean z, boolean z2, com.android.framework.protobuf.OneofInfo oneofInfo, java.lang.Class<?> cls2, java.lang.Object obj, com.android.framework.protobuf.Internal.EnumVerifier enumVerifier, java.lang.reflect.Field field3) {
        this.field = field;
        this.type = fieldType;
        this.messageClass = cls;
        this.fieldNumber = i;
        this.presenceField = field2;
        this.presenceMask = i2;
        this.required = z;
        this.enforceUtf8 = z2;
        this.oneof = oneofInfo;
        this.oneofStoredType = cls2;
        this.mapDefaultEntry = obj;
        this.enumVerifier = enumVerifier;
        this.cachedSizeField = field3;
    }

    public int getFieldNumber() {
        return this.fieldNumber;
    }

    public java.lang.reflect.Field getField() {
        return this.field;
    }

    public com.android.framework.protobuf.FieldType getType() {
        return this.type;
    }

    public com.android.framework.protobuf.OneofInfo getOneof() {
        return this.oneof;
    }

    public java.lang.Class<?> getOneofStoredType() {
        return this.oneofStoredType;
    }

    public com.android.framework.protobuf.Internal.EnumVerifier getEnumVerifier() {
        return this.enumVerifier;
    }

    @Override // java.lang.Comparable
    public int compareTo(com.android.framework.protobuf.FieldInfo fieldInfo) {
        return this.fieldNumber - fieldInfo.fieldNumber;
    }

    public java.lang.Class<?> getListElementType() {
        return this.messageClass;
    }

    public java.lang.reflect.Field getPresenceField() {
        return this.presenceField;
    }

    public java.lang.Object getMapDefaultEntry() {
        return this.mapDefaultEntry;
    }

    public int getPresenceMask() {
        return this.presenceMask;
    }

    public boolean isRequired() {
        return this.required;
    }

    public boolean isEnforceUtf8() {
        return this.enforceUtf8;
    }

    public java.lang.reflect.Field getCachedSizeField() {
        return this.cachedSizeField;
    }

    public java.lang.Class<?> getMessageFieldClass() {
        switch (this.type) {
            case MESSAGE:
            case GROUP:
                return this.field != null ? this.field.getType() : this.oneofStoredType;
            case MESSAGE_LIST:
            case GROUP_LIST:
                return this.messageClass;
            default:
                return null;
        }
    }

    public static com.android.framework.protobuf.FieldInfo.Builder newBuilder() {
        return new com.android.framework.protobuf.FieldInfo.Builder();
    }

    public static final class Builder {
        private java.lang.reflect.Field cachedSizeField;
        private boolean enforceUtf8;
        private com.android.framework.protobuf.Internal.EnumVerifier enumVerifier;
        private java.lang.reflect.Field field;
        private int fieldNumber;
        private java.lang.Object mapDefaultEntry;
        private com.android.framework.protobuf.OneofInfo oneof;
        private java.lang.Class<?> oneofStoredType;
        private java.lang.reflect.Field presenceField;
        private int presenceMask;
        private boolean required;
        private com.android.framework.protobuf.FieldType type;

        private Builder() {
        }

        public com.android.framework.protobuf.FieldInfo.Builder withField(java.lang.reflect.Field field) {
            if (this.oneof != null) {
                throw new java.lang.IllegalStateException("Cannot set field when building a oneof.");
            }
            this.field = field;
            return this;
        }

        public com.android.framework.protobuf.FieldInfo.Builder withType(com.android.framework.protobuf.FieldType fieldType) {
            this.type = fieldType;
            return this;
        }

        public com.android.framework.protobuf.FieldInfo.Builder withFieldNumber(int i) {
            this.fieldNumber = i;
            return this;
        }

        public com.android.framework.protobuf.FieldInfo.Builder withPresence(java.lang.reflect.Field field, int i) {
            this.presenceField = (java.lang.reflect.Field) com.android.framework.protobuf.Internal.checkNotNull(field, "presenceField");
            this.presenceMask = i;
            return this;
        }

        public com.android.framework.protobuf.FieldInfo.Builder withOneof(com.android.framework.protobuf.OneofInfo oneofInfo, java.lang.Class<?> cls) {
            if (this.field != null || this.presenceField != null) {
                throw new java.lang.IllegalStateException("Cannot set oneof when field or presenceField have been provided");
            }
            this.oneof = oneofInfo;
            this.oneofStoredType = cls;
            return this;
        }

        public com.android.framework.protobuf.FieldInfo.Builder withRequired(boolean z) {
            this.required = z;
            return this;
        }

        public com.android.framework.protobuf.FieldInfo.Builder withMapDefaultEntry(java.lang.Object obj) {
            this.mapDefaultEntry = obj;
            return this;
        }

        public com.android.framework.protobuf.FieldInfo.Builder withEnforceUtf8(boolean z) {
            this.enforceUtf8 = z;
            return this;
        }

        public com.android.framework.protobuf.FieldInfo.Builder withEnumVerifier(com.android.framework.protobuf.Internal.EnumVerifier enumVerifier) {
            this.enumVerifier = enumVerifier;
            return this;
        }

        public com.android.framework.protobuf.FieldInfo.Builder withCachedSizeField(java.lang.reflect.Field field) {
            this.cachedSizeField = field;
            return this;
        }

        public com.android.framework.protobuf.FieldInfo build() {
            if (this.oneof != null) {
                return com.android.framework.protobuf.FieldInfo.forOneofMemberField(this.fieldNumber, this.type, this.oneof, this.oneofStoredType, this.enforceUtf8, this.enumVerifier);
            }
            if (this.mapDefaultEntry != null) {
                return com.android.framework.protobuf.FieldInfo.forMapField(this.field, this.fieldNumber, this.mapDefaultEntry, this.enumVerifier);
            }
            if (this.presenceField != null) {
                if (this.required) {
                    return com.android.framework.protobuf.FieldInfo.forProto2RequiredField(this.field, this.fieldNumber, this.type, this.presenceField, this.presenceMask, this.enforceUtf8, this.enumVerifier);
                }
                return com.android.framework.protobuf.FieldInfo.forProto2OptionalField(this.field, this.fieldNumber, this.type, this.presenceField, this.presenceMask, this.enforceUtf8, this.enumVerifier);
            }
            if (this.enumVerifier != null) {
                if (this.cachedSizeField == null) {
                    return com.android.framework.protobuf.FieldInfo.forFieldWithEnumVerifier(this.field, this.fieldNumber, this.type, this.enumVerifier);
                }
                return com.android.framework.protobuf.FieldInfo.forPackedFieldWithEnumVerifier(this.field, this.fieldNumber, this.type, this.enumVerifier, this.cachedSizeField);
            }
            if (this.cachedSizeField == null) {
                return com.android.framework.protobuf.FieldInfo.forField(this.field, this.fieldNumber, this.type, this.enforceUtf8);
            }
            return com.android.framework.protobuf.FieldInfo.forPackedField(this.field, this.fieldNumber, this.type, this.cachedSizeField);
        }
    }

    private static boolean isExactlyOneBitSet(int i) {
        return i != 0 && (i & (i + (-1))) == 0;
    }
}
