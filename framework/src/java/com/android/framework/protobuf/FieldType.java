package com.android.framework.protobuf;

/* loaded from: classes4.dex */
public enum FieldType {
    DOUBLE(0, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.DOUBLE),
    FLOAT(1, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.FLOAT),
    INT64(2, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.LONG),
    UINT64(3, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.LONG),
    INT32(4, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.INT),
    FIXED64(5, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.LONG),
    FIXED32(6, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.INT),
    BOOL(7, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.BOOLEAN),
    STRING(8, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.STRING),
    MESSAGE(9, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.MESSAGE),
    BYTES(10, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.BYTE_STRING),
    UINT32(11, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.INT),
    ENUM(12, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.ENUM),
    SFIXED32(13, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.INT),
    SFIXED64(14, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.LONG),
    SINT32(15, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.INT),
    SINT64(16, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.LONG),
    GROUP(17, com.android.framework.protobuf.FieldType.Collection.SCALAR, com.android.framework.protobuf.JavaType.MESSAGE),
    DOUBLE_LIST(18, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.DOUBLE),
    FLOAT_LIST(19, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.FLOAT),
    INT64_LIST(20, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.LONG),
    UINT64_LIST(21, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.LONG),
    INT32_LIST(22, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.INT),
    FIXED64_LIST(23, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.LONG),
    FIXED32_LIST(24, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.INT),
    BOOL_LIST(25, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.BOOLEAN),
    STRING_LIST(26, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.STRING),
    MESSAGE_LIST(27, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.MESSAGE),
    BYTES_LIST(28, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.BYTE_STRING),
    UINT32_LIST(29, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.INT),
    ENUM_LIST(30, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.ENUM),
    SFIXED32_LIST(31, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.INT),
    SFIXED64_LIST(32, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.LONG),
    SINT32_LIST(33, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.INT),
    SINT64_LIST(34, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.LONG),
    DOUBLE_LIST_PACKED(35, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.DOUBLE),
    FLOAT_LIST_PACKED(36, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.FLOAT),
    INT64_LIST_PACKED(37, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.LONG),
    UINT64_LIST_PACKED(38, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.LONG),
    INT32_LIST_PACKED(39, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.INT),
    FIXED64_LIST_PACKED(40, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.LONG),
    FIXED32_LIST_PACKED(41, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.INT),
    BOOL_LIST_PACKED(42, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.BOOLEAN),
    UINT32_LIST_PACKED(43, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.INT),
    ENUM_LIST_PACKED(44, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.ENUM),
    SFIXED32_LIST_PACKED(45, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.INT),
    SFIXED64_LIST_PACKED(46, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.LONG),
    SINT32_LIST_PACKED(47, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.INT),
    SINT64_LIST_PACKED(48, com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR, com.android.framework.protobuf.JavaType.LONG),
    GROUP_LIST(49, com.android.framework.protobuf.FieldType.Collection.VECTOR, com.android.framework.protobuf.JavaType.MESSAGE),
    MAP(50, com.android.framework.protobuf.FieldType.Collection.MAP, com.android.framework.protobuf.JavaType.VOID);

    private static final java.lang.reflect.Type[] EMPTY_TYPES = new java.lang.reflect.Type[0];
    private static final com.android.framework.protobuf.FieldType[] VALUES;
    private final com.android.framework.protobuf.FieldType.Collection collection;
    private final java.lang.Class<?> elementType;
    private final int id;
    private final com.android.framework.protobuf.JavaType javaType;
    private final boolean primitiveScalar;

    static {
        com.android.framework.protobuf.FieldType[] values = values();
        VALUES = new com.android.framework.protobuf.FieldType[values.length];
        for (com.android.framework.protobuf.FieldType fieldType : values) {
            VALUES[fieldType.id] = fieldType;
        }
    }

    FieldType(int i, com.android.framework.protobuf.FieldType.Collection collection, com.android.framework.protobuf.JavaType javaType) {
        boolean z;
        this.id = i;
        this.collection = collection;
        this.javaType = javaType;
        switch (collection) {
            case MAP:
                this.elementType = javaType.getBoxedType();
                break;
            case VECTOR:
                this.elementType = javaType.getBoxedType();
                break;
            default:
                this.elementType = null;
                break;
        }
        if (collection == com.android.framework.protobuf.FieldType.Collection.SCALAR) {
            switch (javaType) {
                case BYTE_STRING:
                case MESSAGE:
                case STRING:
                    break;
                default:
                    z = true;
                    break;
            }
            this.primitiveScalar = z;
        }
        z = false;
        this.primitiveScalar = z;
    }

    public int id() {
        return this.id;
    }

    public com.android.framework.protobuf.JavaType getJavaType() {
        return this.javaType;
    }

    public boolean isPacked() {
        return com.android.framework.protobuf.FieldType.Collection.PACKED_VECTOR.equals(this.collection);
    }

    public boolean isPrimitiveScalar() {
        return this.primitiveScalar;
    }

    public boolean isScalar() {
        return this.collection == com.android.framework.protobuf.FieldType.Collection.SCALAR;
    }

    public boolean isList() {
        return this.collection.isList();
    }

    public boolean isMap() {
        return this.collection == com.android.framework.protobuf.FieldType.Collection.MAP;
    }

    public boolean isValidForField(java.lang.reflect.Field field) {
        if (com.android.framework.protobuf.FieldType.Collection.VECTOR.equals(this.collection)) {
            return isValidForList(field);
        }
        return this.javaType.getType().isAssignableFrom(field.getType());
    }

    private boolean isValidForList(java.lang.reflect.Field field) {
        java.lang.Class<?> type = field.getType();
        if (!this.javaType.getType().isAssignableFrom(type)) {
            return false;
        }
        java.lang.reflect.Type[] typeArr = EMPTY_TYPES;
        if (field.getGenericType() instanceof java.lang.reflect.ParameterizedType) {
            typeArr = ((java.lang.reflect.ParameterizedType) field.getGenericType()).getActualTypeArguments();
        }
        java.lang.reflect.Type listParameter = getListParameter(type, typeArr);
        if (!(listParameter instanceof java.lang.Class)) {
            return true;
        }
        return this.elementType.isAssignableFrom((java.lang.Class) listParameter);
    }

    public static com.android.framework.protobuf.FieldType forId(int i) {
        if (i < 0 || i >= VALUES.length) {
            return null;
        }
        return VALUES[i];
    }

    private static java.lang.reflect.Type getGenericSuperList(java.lang.Class<?> cls) {
        for (java.lang.reflect.Type type : cls.getGenericInterfaces()) {
            if ((type instanceof java.lang.reflect.ParameterizedType) && java.util.List.class.isAssignableFrom((java.lang.Class) ((java.lang.reflect.ParameterizedType) type).getRawType())) {
                return type;
            }
        }
        java.lang.reflect.Type genericSuperclass = cls.getGenericSuperclass();
        if ((genericSuperclass instanceof java.lang.reflect.ParameterizedType) && java.util.List.class.isAssignableFrom((java.lang.Class) ((java.lang.reflect.ParameterizedType) genericSuperclass).getRawType())) {
            return genericSuperclass;
        }
        return null;
    }

    private static java.lang.reflect.Type getListParameter(java.lang.Class<?> cls, java.lang.reflect.Type[] typeArr) {
        boolean z;
        while (true) {
            int i = 0;
            if (cls != java.util.List.class) {
                java.lang.reflect.Type genericSuperList = getGenericSuperList(cls);
                if (genericSuperList instanceof java.lang.reflect.ParameterizedType) {
                    java.lang.reflect.ParameterizedType parameterizedType = (java.lang.reflect.ParameterizedType) genericSuperList;
                    java.lang.reflect.Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    for (int i2 = 0; i2 < actualTypeArguments.length; i2++) {
                        java.lang.reflect.Type type = actualTypeArguments[i2];
                        if (type instanceof java.lang.reflect.TypeVariable) {
                            java.lang.reflect.TypeVariable<java.lang.Class<?>>[] typeParameters = cls.getTypeParameters();
                            if (typeArr.length != typeParameters.length) {
                                throw new java.lang.RuntimeException("Type array mismatch");
                            }
                            int i3 = 0;
                            while (true) {
                                if (i3 >= typeParameters.length) {
                                    z = false;
                                    break;
                                }
                                if (type != typeParameters[i3]) {
                                    i3++;
                                } else {
                                    actualTypeArguments[i2] = typeArr[i3];
                                    z = true;
                                    break;
                                }
                            }
                            if (!z) {
                                throw new java.lang.RuntimeException("Unable to find replacement for " + type);
                            }
                        }
                    }
                    cls = (java.lang.Class) parameterizedType.getRawType();
                    typeArr = actualTypeArguments;
                } else {
                    typeArr = EMPTY_TYPES;
                    java.lang.Class<?>[] interfaces = cls.getInterfaces();
                    int length = interfaces.length;
                    while (true) {
                        if (i < length) {
                            java.lang.Class<?> cls2 = interfaces[i];
                            if (!java.util.List.class.isAssignableFrom(cls2)) {
                                i++;
                            } else {
                                cls = cls2;
                                break;
                            }
                        } else {
                            cls = cls.getSuperclass();
                            break;
                        }
                    }
                }
            } else {
                if (typeArr.length != 1) {
                    throw new java.lang.RuntimeException("Unable to identify parameter type for List<T>");
                }
                return typeArr[0];
            }
        }
    }

    enum Collection {
        SCALAR(false),
        VECTOR(true),
        PACKED_VECTOR(true),
        MAP(false);

        private final boolean isList;

        Collection(boolean z) {
            this.isList = z;
        }

        public boolean isList() {
            return this.isList;
        }
    }
}
