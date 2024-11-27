package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class MessageSchema<T> implements com.android.framework.protobuf.Schema<T> {
    private static final int ENFORCE_UTF8_MASK = 536870912;
    private static final int FIELD_TYPE_MASK = 267386880;
    private static final int INTS_PER_FIELD = 3;
    private static final int NO_PRESENCE_SENTINEL = 1048575;
    private static final int OFFSET_BITS = 20;
    private static final int OFFSET_MASK = 1048575;
    static final int ONEOF_TYPE_OFFSET = 51;
    private static final int REQUIRED_MASK = 268435456;
    private final int[] buffer;
    private final int checkInitializedCount;
    private final com.android.framework.protobuf.MessageLite defaultInstance;
    private final com.android.framework.protobuf.ExtensionSchema<?> extensionSchema;
    private final boolean hasExtensions;
    private final int[] intArray;
    private final com.android.framework.protobuf.ListFieldSchema listFieldSchema;
    private final boolean lite;
    private final com.android.framework.protobuf.MapFieldSchema mapFieldSchema;
    private final int maxFieldNumber;
    private final int minFieldNumber;
    private final com.android.framework.protobuf.NewInstanceSchema newInstanceSchema;
    private final java.lang.Object[] objects;
    private final boolean proto3;
    private final int repeatedFieldOffsetStart;
    private final com.android.framework.protobuf.UnknownFieldSchema<?, ?> unknownFieldSchema;
    private final boolean useCachedSizeField;
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final sun.misc.Unsafe UNSAFE = com.android.framework.protobuf.UnsafeUtil.getUnsafe();

    private MessageSchema(int[] iArr, java.lang.Object[] objArr, int i, int i2, com.android.framework.protobuf.MessageLite messageLite, boolean z, boolean z2, int[] iArr2, int i3, int i4, com.android.framework.protobuf.NewInstanceSchema newInstanceSchema, com.android.framework.protobuf.ListFieldSchema listFieldSchema, com.android.framework.protobuf.UnknownFieldSchema<?, ?> unknownFieldSchema, com.android.framework.protobuf.ExtensionSchema<?> extensionSchema, com.android.framework.protobuf.MapFieldSchema mapFieldSchema) {
        this.buffer = iArr;
        this.objects = objArr;
        this.minFieldNumber = i;
        this.maxFieldNumber = i2;
        this.lite = messageLite instanceof com.android.framework.protobuf.GeneratedMessageLite;
        this.proto3 = z;
        this.hasExtensions = extensionSchema != null && extensionSchema.hasExtensions(messageLite);
        this.useCachedSizeField = z2;
        this.intArray = iArr2;
        this.checkInitializedCount = i3;
        this.repeatedFieldOffsetStart = i4;
        this.newInstanceSchema = newInstanceSchema;
        this.listFieldSchema = listFieldSchema;
        this.unknownFieldSchema = unknownFieldSchema;
        this.extensionSchema = extensionSchema;
        this.defaultInstance = messageLite;
        this.mapFieldSchema = mapFieldSchema;
    }

    static <T> com.android.framework.protobuf.MessageSchema<T> newSchema(java.lang.Class<T> cls, com.android.framework.protobuf.MessageInfo messageInfo, com.android.framework.protobuf.NewInstanceSchema newInstanceSchema, com.android.framework.protobuf.ListFieldSchema listFieldSchema, com.android.framework.protobuf.UnknownFieldSchema<?, ?> unknownFieldSchema, com.android.framework.protobuf.ExtensionSchema<?> extensionSchema, com.android.framework.protobuf.MapFieldSchema mapFieldSchema) {
        if (messageInfo instanceof com.android.framework.protobuf.RawMessageInfo) {
            return newSchemaForRawMessageInfo((com.android.framework.protobuf.RawMessageInfo) messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
        }
        return newSchemaForMessageInfo((com.android.framework.protobuf.StructuralMessageInfo) messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
    }

    static <T> com.android.framework.protobuf.MessageSchema<T> newSchemaForRawMessageInfo(com.android.framework.protobuf.RawMessageInfo rawMessageInfo, com.android.framework.protobuf.NewInstanceSchema newInstanceSchema, com.android.framework.protobuf.ListFieldSchema listFieldSchema, com.android.framework.protobuf.UnknownFieldSchema<?, ?> unknownFieldSchema, com.android.framework.protobuf.ExtensionSchema<?> extensionSchema, com.android.framework.protobuf.MapFieldSchema mapFieldSchema) {
        int i;
        int charAt;
        int charAt2;
        int charAt3;
        int charAt4;
        int charAt5;
        int[] iArr;
        int i2;
        int i3;
        int i4;
        char charAt6;
        int i5;
        char charAt7;
        int i6;
        char charAt8;
        int i7;
        char charAt9;
        int i8;
        char charAt10;
        int i9;
        char charAt11;
        int i10;
        char charAt12;
        int i11;
        char charAt13;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        boolean z;
        int objectFieldOffset;
        java.lang.String str;
        boolean z2;
        int i17;
        int i18;
        int i19;
        java.lang.reflect.Field reflectField;
        char charAt14;
        java.lang.reflect.Field reflectField2;
        java.lang.reflect.Field reflectField3;
        int i20;
        char charAt15;
        int i21;
        char charAt16;
        int i22;
        char charAt17;
        int i23;
        char charAt18;
        boolean z3 = rawMessageInfo.getSyntax() == com.android.framework.protobuf.ProtoSyntax.PROTO3;
        java.lang.String stringInfo = rawMessageInfo.getStringInfo();
        int length = stringInfo.length();
        char c = 55296;
        if (stringInfo.charAt(0) < 55296) {
            i = 1;
        } else {
            int i24 = 1;
            while (true) {
                i = i24 + 1;
                if (stringInfo.charAt(i24) < 55296) {
                    break;
                }
                i24 = i;
            }
        }
        int i25 = i + 1;
        int charAt19 = stringInfo.charAt(i);
        if (charAt19 >= 55296) {
            int i26 = charAt19 & 8191;
            int i27 = 13;
            while (true) {
                i23 = i25 + 1;
                charAt18 = stringInfo.charAt(i25);
                if (charAt18 < 55296) {
                    break;
                }
                i26 |= (charAt18 & 8191) << i27;
                i27 += 13;
                i25 = i23;
            }
            charAt19 = i26 | (charAt18 << i27);
            i25 = i23;
        }
        if (charAt19 == 0) {
            charAt = 0;
            charAt2 = 0;
            charAt3 = 0;
            charAt4 = 0;
            charAt5 = 0;
            i2 = 0;
            iArr = EMPTY_INT_ARRAY;
            i3 = 0;
        } else {
            int i28 = i25 + 1;
            int charAt20 = stringInfo.charAt(i25);
            if (charAt20 >= 55296) {
                int i29 = charAt20 & 8191;
                int i30 = 13;
                while (true) {
                    i11 = i28 + 1;
                    charAt13 = stringInfo.charAt(i28);
                    if (charAt13 < 55296) {
                        break;
                    }
                    i29 |= (charAt13 & 8191) << i30;
                    i30 += 13;
                    i28 = i11;
                }
                charAt20 = i29 | (charAt13 << i30);
                i28 = i11;
            }
            int i31 = i28 + 1;
            int charAt21 = stringInfo.charAt(i28);
            if (charAt21 >= 55296) {
                int i32 = charAt21 & 8191;
                int i33 = 13;
                while (true) {
                    i10 = i31 + 1;
                    charAt12 = stringInfo.charAt(i31);
                    if (charAt12 < 55296) {
                        break;
                    }
                    i32 |= (charAt12 & 8191) << i33;
                    i33 += 13;
                    i31 = i10;
                }
                charAt21 = i32 | (charAt12 << i33);
                i31 = i10;
            }
            int i34 = i31 + 1;
            charAt = stringInfo.charAt(i31);
            if (charAt >= 55296) {
                int i35 = charAt & 8191;
                int i36 = 13;
                while (true) {
                    i9 = i34 + 1;
                    charAt11 = stringInfo.charAt(i34);
                    if (charAt11 < 55296) {
                        break;
                    }
                    i35 |= (charAt11 & 8191) << i36;
                    i36 += 13;
                    i34 = i9;
                }
                charAt = i35 | (charAt11 << i36);
                i34 = i9;
            }
            int i37 = i34 + 1;
            charAt2 = stringInfo.charAt(i34);
            if (charAt2 >= 55296) {
                int i38 = charAt2 & 8191;
                int i39 = 13;
                while (true) {
                    i8 = i37 + 1;
                    charAt10 = stringInfo.charAt(i37);
                    if (charAt10 < 55296) {
                        break;
                    }
                    i38 |= (charAt10 & 8191) << i39;
                    i39 += 13;
                    i37 = i8;
                }
                charAt2 = i38 | (charAt10 << i39);
                i37 = i8;
            }
            int i40 = i37 + 1;
            charAt3 = stringInfo.charAt(i37);
            if (charAt3 >= 55296) {
                int i41 = charAt3 & 8191;
                int i42 = 13;
                while (true) {
                    i7 = i40 + 1;
                    charAt9 = stringInfo.charAt(i40);
                    if (charAt9 < 55296) {
                        break;
                    }
                    i41 |= (charAt9 & 8191) << i42;
                    i42 += 13;
                    i40 = i7;
                }
                charAt3 = i41 | (charAt9 << i42);
                i40 = i7;
            }
            int i43 = i40 + 1;
            charAt4 = stringInfo.charAt(i40);
            if (charAt4 >= 55296) {
                int i44 = charAt4 & 8191;
                int i45 = 13;
                while (true) {
                    i6 = i43 + 1;
                    charAt8 = stringInfo.charAt(i43);
                    if (charAt8 < 55296) {
                        break;
                    }
                    i44 |= (charAt8 & 8191) << i45;
                    i45 += 13;
                    i43 = i6;
                }
                charAt4 = i44 | (charAt8 << i45);
                i43 = i6;
            }
            int i46 = i43 + 1;
            int charAt22 = stringInfo.charAt(i43);
            if (charAt22 >= 55296) {
                int i47 = charAt22 & 8191;
                int i48 = 13;
                while (true) {
                    i5 = i46 + 1;
                    charAt7 = stringInfo.charAt(i46);
                    if (charAt7 < 55296) {
                        break;
                    }
                    i47 |= (charAt7 & 8191) << i48;
                    i48 += 13;
                    i46 = i5;
                }
                charAt22 = i47 | (charAt7 << i48);
                i46 = i5;
            }
            int i49 = i46 + 1;
            charAt5 = stringInfo.charAt(i46);
            if (charAt5 >= 55296) {
                int i50 = charAt5 & 8191;
                int i51 = 13;
                while (true) {
                    i4 = i49 + 1;
                    charAt6 = stringInfo.charAt(i49);
                    if (charAt6 < 55296) {
                        break;
                    }
                    i50 |= (charAt6 & 8191) << i51;
                    i51 += 13;
                    i49 = i4;
                }
                charAt5 = i50 | (charAt6 << i51);
                i49 = i4;
            }
            iArr = new int[charAt5 + charAt4 + charAt22];
            i2 = (charAt20 * 2) + charAt21;
            i3 = charAt20;
            i25 = i49;
        }
        sun.misc.Unsafe unsafe = UNSAFE;
        java.lang.Object[] objects = rawMessageInfo.getObjects();
        java.lang.Class<?> cls = rawMessageInfo.getDefaultInstance().getClass();
        int[] iArr2 = new int[charAt3 * 3];
        java.lang.Object[] objArr = new java.lang.Object[charAt3 * 2];
        int i52 = charAt5 + charAt4;
        int i53 = charAt5;
        int i54 = i52;
        int i55 = 0;
        int i56 = 0;
        while (i25 < length) {
            int i57 = i25 + 1;
            int charAt23 = stringInfo.charAt(i25);
            if (charAt23 < c) {
                i12 = i57;
            } else {
                int i58 = charAt23 & 8191;
                int i59 = i57;
                int i60 = 13;
                while (true) {
                    i22 = i59 + 1;
                    charAt17 = stringInfo.charAt(i59);
                    if (charAt17 < c) {
                        break;
                    }
                    i58 |= (charAt17 & 8191) << i60;
                    i60 += 13;
                    i59 = i22;
                }
                charAt23 = i58 | (charAt17 << i60);
                i12 = i22;
            }
            int i61 = i12 + 1;
            int charAt24 = stringInfo.charAt(i12);
            if (charAt24 < c) {
                i13 = length;
                i14 = i61;
            } else {
                int i62 = charAt24 & 8191;
                int i63 = i61;
                int i64 = 13;
                while (true) {
                    i21 = i63 + 1;
                    charAt16 = stringInfo.charAt(i63);
                    i13 = length;
                    if (charAt16 < 55296) {
                        break;
                    }
                    i62 |= (charAt16 & 8191) << i64;
                    i64 += 13;
                    i63 = i21;
                    length = i13;
                }
                charAt24 = i62 | (charAt16 << i64);
                i14 = i21;
            }
            int i65 = charAt24 & 255;
            int i66 = charAt5;
            if ((charAt24 & 1024) != 0) {
                iArr[i55] = i56;
                i55++;
            }
            int i67 = i55;
            if (i65 >= 51) {
                int i68 = i14 + 1;
                int charAt25 = stringInfo.charAt(i14);
                char c2 = 55296;
                if (charAt25 >= 55296) {
                    int i69 = charAt25 & 8191;
                    int i70 = 13;
                    while (true) {
                        i20 = i68 + 1;
                        charAt15 = stringInfo.charAt(i68);
                        if (charAt15 < c2) {
                            break;
                        }
                        i69 |= (charAt15 & 8191) << i70;
                        i70 += 13;
                        i68 = i20;
                        c2 = 55296;
                    }
                    charAt25 = i69 | (charAt15 << i70);
                    i68 = i20;
                }
                int i71 = i65 - 51;
                int i72 = i68;
                if (i71 == 9 || i71 == 17) {
                    objArr[((i56 / 3) * 2) + 1] = objects[i2];
                    i2++;
                } else if (i71 == 12 && !z3) {
                    objArr[((i56 / 3) * 2) + 1] = objects[i2];
                    i2++;
                }
                int i73 = charAt25 * 2;
                java.lang.Object obj = objects[i73];
                if (obj instanceof java.lang.reflect.Field) {
                    reflectField2 = (java.lang.reflect.Field) obj;
                } else {
                    reflectField2 = reflectField(cls, (java.lang.String) obj);
                    objects[i73] = reflectField2;
                }
                i15 = charAt;
                i16 = charAt2;
                int objectFieldOffset2 = (int) unsafe.objectFieldOffset(reflectField2);
                int i74 = i73 + 1;
                java.lang.Object obj2 = objects[i74];
                if (obj2 instanceof java.lang.reflect.Field) {
                    reflectField3 = (java.lang.reflect.Field) obj2;
                } else {
                    reflectField3 = reflectField(cls, (java.lang.String) obj2);
                    objects[i74] = reflectField3;
                }
                str = stringInfo;
                i17 = (int) unsafe.objectFieldOffset(reflectField3);
                z2 = z3;
                i18 = i72;
                objectFieldOffset = objectFieldOffset2;
                i19 = 0;
            } else {
                i15 = charAt;
                i16 = charAt2;
                int i75 = i2 + 1;
                java.lang.reflect.Field reflectField4 = reflectField(cls, (java.lang.String) objects[i2]);
                if (i65 == 9 || i65 == 17) {
                    z = true;
                    objArr[((i56 / 3) * 2) + 1] = reflectField4.getType();
                } else if (i65 == 27 || i65 == 49) {
                    objArr[((i56 / 3) * 2) + 1] = objects[i75];
                    i75++;
                    z = true;
                } else if (i65 == 12 || i65 == 30 || i65 == 44) {
                    if (z3) {
                        z = true;
                    } else {
                        objArr[((i56 / 3) * 2) + 1] = objects[i75];
                        i75++;
                        z = true;
                    }
                } else if (i65 != 50) {
                    z = true;
                } else {
                    int i76 = i53 + 1;
                    iArr[i53] = i56;
                    int i77 = (i56 / 3) * 2;
                    int i78 = i75 + 1;
                    objArr[i77] = objects[i75];
                    if ((charAt24 & 2048) == 0) {
                        i53 = i76;
                        i75 = i78;
                        z = true;
                    } else {
                        i75 = i78 + 1;
                        objArr[i77 + 1] = objects[i78];
                        i53 = i76;
                        z = true;
                    }
                }
                int i79 = i75;
                objectFieldOffset = (int) unsafe.objectFieldOffset(reflectField4);
                if (!((charAt24 & 4096) == 4096 ? z : false) || i65 > 17) {
                    str = stringInfo;
                    z2 = z3;
                    i17 = 1048575;
                    i18 = i14;
                    i19 = 0;
                } else {
                    int i80 = i14 + 1;
                    int charAt26 = stringInfo.charAt(i14);
                    if (charAt26 < 55296) {
                        i18 = i80;
                    } else {
                        int i81 = charAt26 & 8191;
                        int i82 = 13;
                        while (true) {
                            i18 = i80 + 1;
                            charAt14 = stringInfo.charAt(i80);
                            if (charAt14 < 55296) {
                                break;
                            }
                            i81 |= (charAt14 & 8191) << i82;
                            i82 += 13;
                            i80 = i18;
                        }
                        charAt26 = i81 | (charAt14 << i82);
                    }
                    int i83 = (i3 * 2) + (charAt26 / 32);
                    java.lang.Object obj3 = objects[i83];
                    str = stringInfo;
                    if (obj3 instanceof java.lang.reflect.Field) {
                        reflectField = (java.lang.reflect.Field) obj3;
                    } else {
                        reflectField = reflectField(cls, (java.lang.String) obj3);
                        objects[i83] = reflectField;
                    }
                    z2 = z3;
                    i17 = (int) unsafe.objectFieldOffset(reflectField);
                    i19 = charAt26 % 32;
                }
                if (i65 >= 18 && i65 <= 49) {
                    iArr[i54] = objectFieldOffset;
                    i54++;
                    i2 = i79;
                } else {
                    i2 = i79;
                }
            }
            int i84 = i56 + 1;
            iArr2[i56] = charAt23;
            int i85 = i84 + 1;
            iArr2[i84] = ((charAt24 & 256) != 0 ? 268435456 : 0) | ((charAt24 & 512) != 0 ? 536870912 : 0) | (i65 << 20) | objectFieldOffset;
            i56 = i85 + 1;
            iArr2[i85] = (i19 << 20) | i17;
            z3 = z2;
            charAt = i15;
            charAt5 = i66;
            length = i13;
            i25 = i18;
            i55 = i67;
            stringInfo = str;
            charAt2 = i16;
            c = 55296;
        }
        return new com.android.framework.protobuf.MessageSchema<>(iArr2, objArr, charAt, charAt2, rawMessageInfo.getDefaultInstance(), z3, false, iArr, charAt5, i52, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
    }

    private static java.lang.reflect.Field reflectField(java.lang.Class<?> cls, java.lang.String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (java.lang.NoSuchFieldException e) {
            java.lang.reflect.Field[] declaredFields = cls.getDeclaredFields();
            for (java.lang.reflect.Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new java.lang.RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + java.util.Arrays.toString(declaredFields));
        }
    }

    static <T> com.android.framework.protobuf.MessageSchema<T> newSchemaForMessageInfo(com.android.framework.protobuf.StructuralMessageInfo structuralMessageInfo, com.android.framework.protobuf.NewInstanceSchema newInstanceSchema, com.android.framework.protobuf.ListFieldSchema listFieldSchema, com.android.framework.protobuf.UnknownFieldSchema<?, ?> unknownFieldSchema, com.android.framework.protobuf.ExtensionSchema<?> extensionSchema, com.android.framework.protobuf.MapFieldSchema mapFieldSchema) {
        int fieldNumber;
        int fieldNumber2;
        int i;
        boolean z = structuralMessageInfo.getSyntax() == com.android.framework.protobuf.ProtoSyntax.PROTO3;
        com.android.framework.protobuf.FieldInfo[] fields = structuralMessageInfo.getFields();
        if (fields.length == 0) {
            fieldNumber = 0;
            fieldNumber2 = 0;
        } else {
            fieldNumber = fields[0].getFieldNumber();
            fieldNumber2 = fields[fields.length - 1].getFieldNumber();
        }
        int length = fields.length;
        int[] iArr = new int[length * 3];
        java.lang.Object[] objArr = new java.lang.Object[length * 2];
        int i2 = 0;
        int i3 = 0;
        for (com.android.framework.protobuf.FieldInfo fieldInfo : fields) {
            if (fieldInfo.getType() == com.android.framework.protobuf.FieldType.MAP) {
                i2++;
            } else if (fieldInfo.getType().id() >= 18 && fieldInfo.getType().id() <= 49) {
                i3++;
            }
        }
        int[] iArr2 = i2 > 0 ? new int[i2] : null;
        int[] iArr3 = i3 > 0 ? new int[i3] : null;
        int[] checkInitialized = structuralMessageInfo.getCheckInitialized();
        if (checkInitialized == null) {
            checkInitialized = EMPTY_INT_ARRAY;
        }
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i4 < fields.length) {
            com.android.framework.protobuf.FieldInfo fieldInfo2 = fields[i4];
            int fieldNumber3 = fieldInfo2.getFieldNumber();
            storeFieldData(fieldInfo2, iArr, i5, objArr);
            if (i6 < checkInitialized.length && checkInitialized[i6] == fieldNumber3) {
                checkInitialized[i6] = i5;
                i6++;
            }
            if (fieldInfo2.getType() == com.android.framework.protobuf.FieldType.MAP) {
                iArr2[i7] = i5;
                i7++;
                i = i5;
            } else if (fieldInfo2.getType().id() < 18 || fieldInfo2.getType().id() > 49) {
                i = i5;
            } else {
                i = i5;
                iArr3[i8] = (int) com.android.framework.protobuf.UnsafeUtil.objectFieldOffset(fieldInfo2.getField());
                i8++;
            }
            i4++;
            i5 = i + 3;
        }
        if (iArr2 == null) {
            iArr2 = EMPTY_INT_ARRAY;
        }
        if (iArr3 == null) {
            iArr3 = EMPTY_INT_ARRAY;
        }
        int[] iArr4 = new int[checkInitialized.length + iArr2.length + iArr3.length];
        java.lang.System.arraycopy(checkInitialized, 0, iArr4, 0, checkInitialized.length);
        java.lang.System.arraycopy(iArr2, 0, iArr4, checkInitialized.length, iArr2.length);
        java.lang.System.arraycopy(iArr3, 0, iArr4, checkInitialized.length + iArr2.length, iArr3.length);
        return new com.android.framework.protobuf.MessageSchema<>(iArr, objArr, fieldNumber, fieldNumber2, structuralMessageInfo.getDefaultInstance(), z, true, iArr4, checkInitialized.length, checkInitialized.length + iArr2.length, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
    }

    private static void storeFieldData(com.android.framework.protobuf.FieldInfo fieldInfo, int[] iArr, int i, java.lang.Object[] objArr) {
        int objectFieldOffset;
        int id;
        int objectFieldOffset2;
        int i2;
        com.android.framework.protobuf.OneofInfo oneof = fieldInfo.getOneof();
        if (oneof != null) {
            id = fieldInfo.getType().id() + 51;
            objectFieldOffset = (int) com.android.framework.protobuf.UnsafeUtil.objectFieldOffset(oneof.getValueField());
            objectFieldOffset2 = (int) com.android.framework.protobuf.UnsafeUtil.objectFieldOffset(oneof.getCaseField());
            i2 = 0;
        } else {
            com.android.framework.protobuf.FieldType type = fieldInfo.getType();
            objectFieldOffset = (int) com.android.framework.protobuf.UnsafeUtil.objectFieldOffset(fieldInfo.getField());
            id = type.id();
            if (!type.isList() && !type.isMap()) {
                java.lang.reflect.Field presenceField = fieldInfo.getPresenceField();
                if (presenceField == null) {
                    objectFieldOffset2 = 1048575;
                } else {
                    objectFieldOffset2 = (int) com.android.framework.protobuf.UnsafeUtil.objectFieldOffset(presenceField);
                }
                i2 = java.lang.Integer.numberOfTrailingZeros(fieldInfo.getPresenceMask());
            } else if (fieldInfo.getCachedSizeField() == null) {
                objectFieldOffset2 = 0;
                i2 = 0;
            } else {
                objectFieldOffset2 = (int) com.android.framework.protobuf.UnsafeUtil.objectFieldOffset(fieldInfo.getCachedSizeField());
                i2 = 0;
            }
        }
        iArr[i] = fieldInfo.getFieldNumber();
        iArr[i + 1] = (fieldInfo.isRequired() ? 268435456 : 0) | (fieldInfo.isEnforceUtf8() ? 536870912 : 0) | (id << 20) | objectFieldOffset;
        iArr[i + 2] = objectFieldOffset2 | (i2 << 20);
        java.lang.Class<?> messageFieldClass = fieldInfo.getMessageFieldClass();
        if (fieldInfo.getMapDefaultEntry() != null) {
            int i3 = (i / 3) * 2;
            objArr[i3] = fieldInfo.getMapDefaultEntry();
            if (messageFieldClass != null) {
                objArr[i3 + 1] = messageFieldClass;
                return;
            } else {
                if (fieldInfo.getEnumVerifier() != null) {
                    objArr[i3 + 1] = fieldInfo.getEnumVerifier();
                    return;
                }
                return;
            }
        }
        if (messageFieldClass != null) {
            objArr[((i / 3) * 2) + 1] = messageFieldClass;
        } else if (fieldInfo.getEnumVerifier() != null) {
            objArr[((i / 3) * 2) + 1] = fieldInfo.getEnumVerifier();
        }
    }

    @Override // com.android.framework.protobuf.Schema
    public T newInstance() {
        return (T) this.newInstanceSchema.newInstance(this.defaultInstance);
    }

    @Override // com.android.framework.protobuf.Schema
    public boolean equals(T t, T t2) {
        int length = this.buffer.length;
        for (int i = 0; i < length; i += 3) {
            if (!equals(t, t2, i)) {
                return false;
            }
        }
        if (!this.unknownFieldSchema.getFromMessage(t).equals(this.unknownFieldSchema.getFromMessage(t2))) {
            return false;
        }
        if (this.hasExtensions) {
            return this.extensionSchema.getExtensions(t).equals(this.extensionSchema.getExtensions(t2));
        }
        return true;
    }

    private boolean equals(T t, T t2, int i) {
        int typeAndOffsetAt = typeAndOffsetAt(i);
        long offset = offset(typeAndOffsetAt);
        switch (type(typeAndOffsetAt)) {
            case 0:
                if (arePresentForEquals(t, t2, i) && java.lang.Double.doubleToLongBits(com.android.framework.protobuf.UnsafeUtil.getDouble(t, offset)) == java.lang.Double.doubleToLongBits(com.android.framework.protobuf.UnsafeUtil.getDouble(t2, offset))) {
                    break;
                }
                break;
            case 1:
                if (arePresentForEquals(t, t2, i) && java.lang.Float.floatToIntBits(com.android.framework.protobuf.UnsafeUtil.getFloat(t, offset)) == java.lang.Float.floatToIntBits(com.android.framework.protobuf.UnsafeUtil.getFloat(t2, offset))) {
                    break;
                }
                break;
            case 2:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.UnsafeUtil.getLong(t, offset) == com.android.framework.protobuf.UnsafeUtil.getLong(t2, offset)) {
                    break;
                }
                break;
            case 3:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.UnsafeUtil.getLong(t, offset) == com.android.framework.protobuf.UnsafeUtil.getLong(t2, offset)) {
                    break;
                }
                break;
            case 4:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.UnsafeUtil.getInt(t, offset) == com.android.framework.protobuf.UnsafeUtil.getInt(t2, offset)) {
                    break;
                }
                break;
            case 5:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.UnsafeUtil.getLong(t, offset) == com.android.framework.protobuf.UnsafeUtil.getLong(t2, offset)) {
                    break;
                }
                break;
            case 6:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.UnsafeUtil.getInt(t, offset) == com.android.framework.protobuf.UnsafeUtil.getInt(t2, offset)) {
                    break;
                }
                break;
            case 7:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.UnsafeUtil.getBoolean(t, offset) == com.android.framework.protobuf.UnsafeUtil.getBoolean(t2, offset)) {
                    break;
                }
                break;
            case 8:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.SchemaUtil.safeEquals(com.android.framework.protobuf.UnsafeUtil.getObject(t, offset), com.android.framework.protobuf.UnsafeUtil.getObject(t2, offset))) {
                    break;
                }
                break;
            case 9:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.SchemaUtil.safeEquals(com.android.framework.protobuf.UnsafeUtil.getObject(t, offset), com.android.framework.protobuf.UnsafeUtil.getObject(t2, offset))) {
                    break;
                }
                break;
            case 10:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.SchemaUtil.safeEquals(com.android.framework.protobuf.UnsafeUtil.getObject(t, offset), com.android.framework.protobuf.UnsafeUtil.getObject(t2, offset))) {
                    break;
                }
                break;
            case 11:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.UnsafeUtil.getInt(t, offset) == com.android.framework.protobuf.UnsafeUtil.getInt(t2, offset)) {
                    break;
                }
                break;
            case 12:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.UnsafeUtil.getInt(t, offset) == com.android.framework.protobuf.UnsafeUtil.getInt(t2, offset)) {
                    break;
                }
                break;
            case 13:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.UnsafeUtil.getInt(t, offset) == com.android.framework.protobuf.UnsafeUtil.getInt(t2, offset)) {
                    break;
                }
                break;
            case 14:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.UnsafeUtil.getLong(t, offset) == com.android.framework.protobuf.UnsafeUtil.getLong(t2, offset)) {
                    break;
                }
                break;
            case 15:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.UnsafeUtil.getInt(t, offset) == com.android.framework.protobuf.UnsafeUtil.getInt(t2, offset)) {
                    break;
                }
                break;
            case 16:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.UnsafeUtil.getLong(t, offset) == com.android.framework.protobuf.UnsafeUtil.getLong(t2, offset)) {
                    break;
                }
                break;
            case 17:
                if (arePresentForEquals(t, t2, i) && com.android.framework.protobuf.SchemaUtil.safeEquals(com.android.framework.protobuf.UnsafeUtil.getObject(t, offset), com.android.framework.protobuf.UnsafeUtil.getObject(t2, offset))) {
                    break;
                }
                break;
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
                if (isOneofCaseEqual(t, t2, i) && com.android.framework.protobuf.SchemaUtil.safeEquals(com.android.framework.protobuf.UnsafeUtil.getObject(t, offset), com.android.framework.protobuf.UnsafeUtil.getObject(t2, offset))) {
                    break;
                }
                break;
        }
        return true;
    }

    @Override // com.android.framework.protobuf.Schema
    public int hashCode(T t) {
        int length = this.buffer.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2 += 3) {
            int typeAndOffsetAt = typeAndOffsetAt(i2);
            int numberAt = numberAt(i2);
            long offset = offset(typeAndOffsetAt);
            switch (type(typeAndOffsetAt)) {
                case 0:
                    i = (i * 53) + com.android.framework.protobuf.Internal.hashLong(java.lang.Double.doubleToLongBits(com.android.framework.protobuf.UnsafeUtil.getDouble(t, offset)));
                    break;
                case 1:
                    i = (i * 53) + java.lang.Float.floatToIntBits(com.android.framework.protobuf.UnsafeUtil.getFloat(t, offset));
                    break;
                case 2:
                    i = (i * 53) + com.android.framework.protobuf.Internal.hashLong(com.android.framework.protobuf.UnsafeUtil.getLong(t, offset));
                    break;
                case 3:
                    i = (i * 53) + com.android.framework.protobuf.Internal.hashLong(com.android.framework.protobuf.UnsafeUtil.getLong(t, offset));
                    break;
                case 4:
                    i = (i * 53) + com.android.framework.protobuf.UnsafeUtil.getInt(t, offset);
                    break;
                case 5:
                    i = (i * 53) + com.android.framework.protobuf.Internal.hashLong(com.android.framework.protobuf.UnsafeUtil.getLong(t, offset));
                    break;
                case 6:
                    i = (i * 53) + com.android.framework.protobuf.UnsafeUtil.getInt(t, offset);
                    break;
                case 7:
                    i = (i * 53) + com.android.framework.protobuf.Internal.hashBoolean(com.android.framework.protobuf.UnsafeUtil.getBoolean(t, offset));
                    break;
                case 8:
                    i = (i * 53) + ((java.lang.String) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset)).hashCode();
                    break;
                case 9:
                    java.lang.Object object = com.android.framework.protobuf.UnsafeUtil.getObject(t, offset);
                    i = (i * 53) + (object != null ? object.hashCode() : 37);
                    break;
                case 10:
                    i = (i * 53) + com.android.framework.protobuf.UnsafeUtil.getObject(t, offset).hashCode();
                    break;
                case 11:
                    i = (i * 53) + com.android.framework.protobuf.UnsafeUtil.getInt(t, offset);
                    break;
                case 12:
                    i = (i * 53) + com.android.framework.protobuf.UnsafeUtil.getInt(t, offset);
                    break;
                case 13:
                    i = (i * 53) + com.android.framework.protobuf.UnsafeUtil.getInt(t, offset);
                    break;
                case 14:
                    i = (i * 53) + com.android.framework.protobuf.Internal.hashLong(com.android.framework.protobuf.UnsafeUtil.getLong(t, offset));
                    break;
                case 15:
                    i = (i * 53) + com.android.framework.protobuf.UnsafeUtil.getInt(t, offset);
                    break;
                case 16:
                    i = (i * 53) + com.android.framework.protobuf.Internal.hashLong(com.android.framework.protobuf.UnsafeUtil.getLong(t, offset));
                    break;
                case 17:
                    java.lang.Object object2 = com.android.framework.protobuf.UnsafeUtil.getObject(t, offset);
                    i = (i * 53) + (object2 != null ? object2.hashCode() : 37);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = (i * 53) + com.android.framework.protobuf.UnsafeUtil.getObject(t, offset).hashCode();
                    break;
                case 50:
                    i = (i * 53) + com.android.framework.protobuf.UnsafeUtil.getObject(t, offset).hashCode();
                    break;
                case 51:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + com.android.framework.protobuf.Internal.hashLong(java.lang.Double.doubleToLongBits(oneofDoubleAt(t, offset)));
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + java.lang.Float.floatToIntBits(oneofFloatAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + com.android.framework.protobuf.Internal.hashLong(oneofLongAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + com.android.framework.protobuf.Internal.hashLong(oneofLongAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + oneofIntAt(t, offset);
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + com.android.framework.protobuf.Internal.hashLong(oneofLongAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + oneofIntAt(t, offset);
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + com.android.framework.protobuf.Internal.hashBoolean(oneofBooleanAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + ((java.lang.String) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset)).hashCode();
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + com.android.framework.protobuf.UnsafeUtil.getObject(t, offset).hashCode();
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + com.android.framework.protobuf.UnsafeUtil.getObject(t, offset).hashCode();
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + oneofIntAt(t, offset);
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + oneofIntAt(t, offset);
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + oneofIntAt(t, offset);
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + com.android.framework.protobuf.Internal.hashLong(oneofLongAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + oneofIntAt(t, offset);
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + com.android.framework.protobuf.Internal.hashLong(oneofLongAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (isOneofPresent(t, numberAt, i2)) {
                        i = (i * 53) + com.android.framework.protobuf.UnsafeUtil.getObject(t, offset).hashCode();
                        break;
                    } else {
                        break;
                    }
            }
        }
        int hashCode = (i * 53) + this.unknownFieldSchema.getFromMessage(t).hashCode();
        if (this.hasExtensions) {
            return (hashCode * 53) + this.extensionSchema.getExtensions(t).hashCode();
        }
        return hashCode;
    }

    @Override // com.android.framework.protobuf.Schema
    public void mergeFrom(T t, T t2) {
        checkMutable(t);
        if (t2 == null) {
            throw new java.lang.NullPointerException();
        }
        for (int i = 0; i < this.buffer.length; i += 3) {
            mergeSingleField(t, t2, i);
        }
        com.android.framework.protobuf.SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, t, t2);
        if (this.hasExtensions) {
            com.android.framework.protobuf.SchemaUtil.mergeExtensions(this.extensionSchema, t, t2);
        }
    }

    private void mergeSingleField(T t, T t2, int i) {
        int typeAndOffsetAt = typeAndOffsetAt(i);
        long offset = offset(typeAndOffsetAt);
        int numberAt = numberAt(i);
        switch (type(typeAndOffsetAt)) {
            case 0:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putDouble(t, offset, com.android.framework.protobuf.UnsafeUtil.getDouble(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 1:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putFloat(t, offset, com.android.framework.protobuf.UnsafeUtil.getFloat(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 2:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putLong(t, offset, com.android.framework.protobuf.UnsafeUtil.getLong(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 3:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putLong(t, offset, com.android.framework.protobuf.UnsafeUtil.getLong(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 4:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putInt(t, offset, com.android.framework.protobuf.UnsafeUtil.getInt(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 5:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putLong(t, offset, com.android.framework.protobuf.UnsafeUtil.getLong(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 6:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putInt(t, offset, com.android.framework.protobuf.UnsafeUtil.getInt(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 7:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putBoolean(t, offset, com.android.framework.protobuf.UnsafeUtil.getBoolean(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 8:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putObject(t, offset, com.android.framework.protobuf.UnsafeUtil.getObject(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 9:
                mergeMessage(t, t2, i);
                break;
            case 10:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putObject(t, offset, com.android.framework.protobuf.UnsafeUtil.getObject(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 11:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putInt(t, offset, com.android.framework.protobuf.UnsafeUtil.getInt(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 12:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putInt(t, offset, com.android.framework.protobuf.UnsafeUtil.getInt(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 13:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putInt(t, offset, com.android.framework.protobuf.UnsafeUtil.getInt(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 14:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putLong(t, offset, com.android.framework.protobuf.UnsafeUtil.getLong(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 15:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putInt(t, offset, com.android.framework.protobuf.UnsafeUtil.getInt(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 16:
                if (isFieldPresent(t2, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putLong(t, offset, com.android.framework.protobuf.UnsafeUtil.getLong(t2, offset));
                    setFieldPresent(t, i);
                    break;
                }
                break;
            case 17:
                mergeMessage(t, t2, i);
                break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
                this.listFieldSchema.mergeListsAt(t, t2, offset);
                break;
            case 50:
                com.android.framework.protobuf.SchemaUtil.mergeMap(this.mapFieldSchema, t, t2, offset);
                break;
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
                if (isOneofPresent(t2, numberAt, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putObject(t, offset, com.android.framework.protobuf.UnsafeUtil.getObject(t2, offset));
                    setOneofPresent(t, numberAt, i);
                    break;
                }
                break;
            case 60:
                mergeOneofMessage(t, t2, i);
                break;
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
                if (isOneofPresent(t2, numberAt, i)) {
                    com.android.framework.protobuf.UnsafeUtil.putObject(t, offset, com.android.framework.protobuf.UnsafeUtil.getObject(t2, offset));
                    setOneofPresent(t, numberAt, i);
                    break;
                }
                break;
            case 68:
                mergeOneofMessage(t, t2, i);
                break;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void mergeMessage(T t, T t2, int i) {
        if (!isFieldPresent(t2, i)) {
            return;
        }
        long offset = offset(typeAndOffsetAt(i));
        java.lang.Object object = UNSAFE.getObject(t2, offset);
        if (object == null) {
            throw new java.lang.IllegalStateException("Source subfield " + numberAt(i) + " is present but null: " + t2);
        }
        com.android.framework.protobuf.Schema messageFieldSchema = getMessageFieldSchema(i);
        if (!isFieldPresent(t, i)) {
            if (!isMutable(object)) {
                UNSAFE.putObject(t, offset, object);
            } else {
                java.lang.Object newInstance = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(newInstance, object);
                UNSAFE.putObject(t, offset, newInstance);
            }
            setFieldPresent(t, i);
            return;
        }
        java.lang.Object object2 = UNSAFE.getObject(t, offset);
        if (!isMutable(object2)) {
            java.lang.Object newInstance2 = messageFieldSchema.newInstance();
            messageFieldSchema.mergeFrom(newInstance2, object2);
            UNSAFE.putObject(t, offset, newInstance2);
            object2 = newInstance2;
        }
        messageFieldSchema.mergeFrom(object2, object);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void mergeOneofMessage(T t, T t2, int i) {
        int numberAt = numberAt(i);
        if (!isOneofPresent(t2, numberAt, i)) {
            return;
        }
        long offset = offset(typeAndOffsetAt(i));
        java.lang.Object object = UNSAFE.getObject(t2, offset);
        if (object == null) {
            throw new java.lang.IllegalStateException("Source subfield " + numberAt(i) + " is present but null: " + t2);
        }
        com.android.framework.protobuf.Schema messageFieldSchema = getMessageFieldSchema(i);
        if (!isOneofPresent(t, numberAt, i)) {
            if (!isMutable(object)) {
                UNSAFE.putObject(t, offset, object);
            } else {
                java.lang.Object newInstance = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(newInstance, object);
                UNSAFE.putObject(t, offset, newInstance);
            }
            setOneofPresent(t, numberAt, i);
            return;
        }
        java.lang.Object object2 = UNSAFE.getObject(t, offset);
        if (!isMutable(object2)) {
            java.lang.Object newInstance2 = messageFieldSchema.newInstance();
            messageFieldSchema.mergeFrom(newInstance2, object2);
            UNSAFE.putObject(t, offset, newInstance2);
            object2 = newInstance2;
        }
        messageFieldSchema.mergeFrom(object2, object);
    }

    @Override // com.android.framework.protobuf.Schema
    public int getSerializedSize(T t) {
        return this.proto3 ? getSerializedSizeProto3(t) : getSerializedSizeProto2(t);
    }

    private int getSerializedSizeProto2(T t) {
        int i;
        int i2;
        sun.misc.Unsafe unsafe = UNSAFE;
        int i3 = 1048575;
        int i4 = 1048575;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i5 < this.buffer.length) {
            int typeAndOffsetAt = typeAndOffsetAt(i5);
            int numberAt = numberAt(i5);
            int type = type(typeAndOffsetAt);
            if (type > 17) {
                if (this.useCachedSizeField && type >= com.android.framework.protobuf.FieldType.DOUBLE_LIST_PACKED.id() && type <= com.android.framework.protobuf.FieldType.SINT64_LIST_PACKED.id()) {
                    i = this.buffer[i5 + 2] & i3;
                    i2 = 0;
                } else {
                    i = 0;
                    i2 = 0;
                }
            } else {
                i = this.buffer[i5 + 2];
                int i8 = i & i3;
                i2 = 1 << (i >>> 20);
                if (i8 != i4) {
                    i7 = unsafe.getInt(t, i8);
                    i4 = i8;
                }
            }
            long offset = offset(typeAndOffsetAt);
            switch (type) {
                case 0:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeDoubleSize(numberAt, 0.0d);
                        break;
                    }
                case 1:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeFloatSize(numberAt, 0.0f);
                        break;
                    }
                case 2:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeInt64Size(numberAt, unsafe.getLong(t, offset));
                        break;
                    }
                case 3:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeUInt64Size(numberAt, unsafe.getLong(t, offset));
                        break;
                    }
                case 4:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeInt32Size(numberAt, unsafe.getInt(t, offset));
                        break;
                    }
                case 5:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeFixed64Size(numberAt, 0L);
                        break;
                    }
                case 6:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeFixed32Size(numberAt, 0);
                        break;
                    }
                case 7:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeBoolSize(numberAt, true);
                        break;
                    }
                case 8:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        java.lang.Object object = unsafe.getObject(t, offset);
                        if (object instanceof com.android.framework.protobuf.ByteString) {
                            i6 += com.android.framework.protobuf.CodedOutputStream.computeBytesSize(numberAt, (com.android.framework.protobuf.ByteString) object);
                        } else {
                            i6 += com.android.framework.protobuf.CodedOutputStream.computeStringSize(numberAt, (java.lang.String) object);
                        }
                        break;
                    }
                case 9:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.SchemaUtil.computeSizeMessage(numberAt, unsafe.getObject(t, offset), getMessageFieldSchema(i5));
                        break;
                    }
                case 10:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeBytesSize(numberAt, (com.android.framework.protobuf.ByteString) unsafe.getObject(t, offset));
                        break;
                    }
                case 11:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeUInt32Size(numberAt, unsafe.getInt(t, offset));
                        break;
                    }
                case 12:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeEnumSize(numberAt, unsafe.getInt(t, offset));
                        break;
                    }
                case 13:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeSFixed32Size(numberAt, 0);
                        break;
                    }
                case 14:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeSFixed64Size(numberAt, 0L);
                        break;
                    }
                case 15:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeSInt32Size(numberAt, unsafe.getInt(t, offset));
                        break;
                    }
                case 16:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeSInt64Size(numberAt, unsafe.getLong(t, offset));
                        break;
                    }
                case 17:
                    if ((i7 & i2) == 0) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeGroupSize(numberAt, (com.android.framework.protobuf.MessageLite) unsafe.getObject(t, offset), getMessageFieldSchema(i5));
                        break;
                    }
                case 18:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeFixed64List(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 19:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeFixed32List(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 20:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeInt64List(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 21:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeUInt64List(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 22:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeInt32List(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 23:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeFixed64List(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 24:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeFixed32List(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 25:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeBoolList(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 26:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeStringList(numberAt, (java.util.List) unsafe.getObject(t, offset));
                    break;
                case 27:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeMessageList(numberAt, (java.util.List) unsafe.getObject(t, offset), getMessageFieldSchema(i5));
                    break;
                case 28:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeByteStringList(numberAt, (java.util.List) unsafe.getObject(t, offset));
                    break;
                case 29:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeUInt32List(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 30:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeEnumList(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 31:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeFixed32List(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 32:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeFixed64List(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 33:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeSInt32List(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 34:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeSInt64List(numberAt, (java.util.List) unsafe.getObject(t, offset), false);
                    break;
                case 35:
                    int computeSizeFixed64ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeFixed64ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeFixed64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeFixed64ListNoTag);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag) + computeSizeFixed64ListNoTag;
                        break;
                    } else {
                        break;
                    }
                case 36:
                    int computeSizeFixed32ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeFixed32ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeFixed32ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeFixed32ListNoTag);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed32ListNoTag) + computeSizeFixed32ListNoTag;
                        break;
                    } else {
                        break;
                    }
                case 37:
                    int computeSizeInt64ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeInt64ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeInt64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeInt64ListNoTag);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeInt64ListNoTag) + computeSizeInt64ListNoTag;
                        break;
                    } else {
                        break;
                    }
                case 38:
                    int computeSizeUInt64ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeUInt64ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeUInt64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeUInt64ListNoTag);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeUInt64ListNoTag) + computeSizeUInt64ListNoTag;
                        break;
                    } else {
                        break;
                    }
                case 39:
                    int computeSizeInt32ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeInt32ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeInt32ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeInt32ListNoTag);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeInt32ListNoTag) + computeSizeInt32ListNoTag;
                        break;
                    } else {
                        break;
                    }
                case 40:
                    int computeSizeFixed64ListNoTag2 = com.android.framework.protobuf.SchemaUtil.computeSizeFixed64ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeFixed64ListNoTag2 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeFixed64ListNoTag2);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2) + computeSizeFixed64ListNoTag2;
                        break;
                    } else {
                        break;
                    }
                case 41:
                    int computeSizeFixed32ListNoTag2 = com.android.framework.protobuf.SchemaUtil.computeSizeFixed32ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeFixed32ListNoTag2 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeFixed32ListNoTag2);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed32ListNoTag2) + computeSizeFixed32ListNoTag2;
                        break;
                    } else {
                        break;
                    }
                case 42:
                    int computeSizeBoolListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeBoolListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeBoolListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeBoolListNoTag);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeBoolListNoTag) + computeSizeBoolListNoTag;
                        break;
                    } else {
                        break;
                    }
                case 43:
                    int computeSizeUInt32ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeUInt32ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeUInt32ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeUInt32ListNoTag);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeUInt32ListNoTag) + computeSizeUInt32ListNoTag;
                        break;
                    } else {
                        break;
                    }
                case 44:
                    int computeSizeEnumListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeEnumListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeEnumListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeEnumListNoTag);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeEnumListNoTag) + computeSizeEnumListNoTag;
                        break;
                    } else {
                        break;
                    }
                case 45:
                    int computeSizeFixed32ListNoTag3 = com.android.framework.protobuf.SchemaUtil.computeSizeFixed32ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeFixed32ListNoTag3 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeFixed32ListNoTag3);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed32ListNoTag3) + computeSizeFixed32ListNoTag3;
                        break;
                    } else {
                        break;
                    }
                case 46:
                    int computeSizeFixed64ListNoTag3 = com.android.framework.protobuf.SchemaUtil.computeSizeFixed64ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeFixed64ListNoTag3 > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeFixed64ListNoTag3);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag3) + computeSizeFixed64ListNoTag3;
                        break;
                    } else {
                        break;
                    }
                case 47:
                    int computeSizeSInt32ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeSInt32ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeSInt32ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeSInt32ListNoTag);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeSInt32ListNoTag) + computeSizeSInt32ListNoTag;
                        break;
                    } else {
                        break;
                    }
                case 48:
                    int computeSizeSInt64ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeSInt64ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeSInt64ListNoTag > 0) {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeSInt64ListNoTag);
                        }
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeSInt64ListNoTag) + computeSizeSInt64ListNoTag;
                        break;
                    } else {
                        break;
                    }
                case 49:
                    i6 += com.android.framework.protobuf.SchemaUtil.computeSizeGroupList(numberAt, (java.util.List) unsafe.getObject(t, offset), getMessageFieldSchema(i5));
                    break;
                case 50:
                    i6 += this.mapFieldSchema.getSerializedSize(numberAt, unsafe.getObject(t, offset), getMapFieldDefaultEntry(i5));
                    break;
                case 51:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeDoubleSize(numberAt, 0.0d);
                        break;
                    }
                case 52:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeFloatSize(numberAt, 0.0f);
                        break;
                    }
                case 53:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeInt64Size(numberAt, oneofLongAt(t, offset));
                        break;
                    }
                case 54:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeUInt64Size(numberAt, oneofLongAt(t, offset));
                        break;
                    }
                case 55:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeInt32Size(numberAt, oneofIntAt(t, offset));
                        break;
                    }
                case 56:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeFixed64Size(numberAt, 0L);
                        break;
                    }
                case 57:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeFixed32Size(numberAt, 0);
                        break;
                    }
                case 58:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeBoolSize(numberAt, true);
                        break;
                    }
                case 59:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        java.lang.Object object2 = unsafe.getObject(t, offset);
                        if (object2 instanceof com.android.framework.protobuf.ByteString) {
                            i6 += com.android.framework.protobuf.CodedOutputStream.computeBytesSize(numberAt, (com.android.framework.protobuf.ByteString) object2);
                        } else {
                            i6 += com.android.framework.protobuf.CodedOutputStream.computeStringSize(numberAt, (java.lang.String) object2);
                        }
                        break;
                    }
                case 60:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.SchemaUtil.computeSizeMessage(numberAt, unsafe.getObject(t, offset), getMessageFieldSchema(i5));
                        break;
                    }
                case 61:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeBytesSize(numberAt, (com.android.framework.protobuf.ByteString) unsafe.getObject(t, offset));
                        break;
                    }
                case 62:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeUInt32Size(numberAt, oneofIntAt(t, offset));
                        break;
                    }
                case 63:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeEnumSize(numberAt, oneofIntAt(t, offset));
                        break;
                    }
                case 64:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeSFixed32Size(numberAt, 0);
                        break;
                    }
                case 65:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeSFixed64Size(numberAt, 0L);
                        break;
                    }
                case 66:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeSInt32Size(numberAt, oneofIntAt(t, offset));
                        break;
                    }
                case 67:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeSInt64Size(numberAt, oneofLongAt(t, offset));
                        break;
                    }
                case 68:
                    if (!isOneofPresent(t, numberAt, i5)) {
                        break;
                    } else {
                        i6 += com.android.framework.protobuf.CodedOutputStream.computeGroupSize(numberAt, (com.android.framework.protobuf.MessageLite) unsafe.getObject(t, offset), getMessageFieldSchema(i5));
                        break;
                    }
            }
            i5 += 3;
            i3 = 1048575;
        }
        int unknownFieldsSerializedSize = i6 + getUnknownFieldsSerializedSize(this.unknownFieldSchema, t);
        if (this.hasExtensions) {
            return unknownFieldsSerializedSize + this.extensionSchema.getExtensions(t).getSerializedSize();
        }
        return unknownFieldsSerializedSize;
    }

    private int getSerializedSizeProto3(T t) {
        int i;
        sun.misc.Unsafe unsafe = UNSAFE;
        int i2 = 0;
        for (int i3 = 0; i3 < this.buffer.length; i3 += 3) {
            int typeAndOffsetAt = typeAndOffsetAt(i3);
            int type = type(typeAndOffsetAt);
            int numberAt = numberAt(i3);
            long offset = offset(typeAndOffsetAt);
            if (type >= com.android.framework.protobuf.FieldType.DOUBLE_LIST_PACKED.id() && type <= com.android.framework.protobuf.FieldType.SINT64_LIST_PACKED.id()) {
                i = this.buffer[i3 + 2] & 1048575;
            } else {
                i = 0;
            }
            switch (type) {
                case 0:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeDoubleSize(numberAt, 0.0d);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeFloatSize(numberAt, 0.0f);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeInt64Size(numberAt, com.android.framework.protobuf.UnsafeUtil.getLong(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeUInt64Size(numberAt, com.android.framework.protobuf.UnsafeUtil.getLong(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeInt32Size(numberAt, com.android.framework.protobuf.UnsafeUtil.getInt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeFixed64Size(numberAt, 0L);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeFixed32Size(numberAt, 0);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeBoolSize(numberAt, true);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (isFieldPresent(t, i3)) {
                        java.lang.Object object = com.android.framework.protobuf.UnsafeUtil.getObject(t, offset);
                        if (object instanceof com.android.framework.protobuf.ByteString) {
                            i2 += com.android.framework.protobuf.CodedOutputStream.computeBytesSize(numberAt, (com.android.framework.protobuf.ByteString) object);
                            break;
                        } else {
                            i2 += com.android.framework.protobuf.CodedOutputStream.computeStringSize(numberAt, (java.lang.String) object);
                            break;
                        }
                    } else {
                        break;
                    }
                case 9:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.SchemaUtil.computeSizeMessage(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset), getMessageFieldSchema(i3));
                        break;
                    } else {
                        break;
                    }
                case 10:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeBytesSize(numberAt, (com.android.framework.protobuf.ByteString) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeUInt32Size(numberAt, com.android.framework.protobuf.UnsafeUtil.getInt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeEnumSize(numberAt, com.android.framework.protobuf.UnsafeUtil.getInt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeSFixed32Size(numberAt, 0);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeSFixed64Size(numberAt, 0L);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeSInt32Size(numberAt, com.android.framework.protobuf.UnsafeUtil.getInt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeSInt64Size(numberAt, com.android.framework.protobuf.UnsafeUtil.getLong(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 17:
                    if (isFieldPresent(t, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeGroupSize(numberAt, (com.android.framework.protobuf.MessageLite) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset), getMessageFieldSchema(i3));
                        break;
                    } else {
                        break;
                    }
                case 18:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeFixed64List(numberAt, listAt(t, offset), false);
                    break;
                case 19:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeFixed32List(numberAt, listAt(t, offset), false);
                    break;
                case 20:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeInt64List(numberAt, listAt(t, offset), false);
                    break;
                case 21:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeUInt64List(numberAt, listAt(t, offset), false);
                    break;
                case 22:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeInt32List(numberAt, listAt(t, offset), false);
                    break;
                case 23:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeFixed64List(numberAt, listAt(t, offset), false);
                    break;
                case 24:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeFixed32List(numberAt, listAt(t, offset), false);
                    break;
                case 25:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeBoolList(numberAt, listAt(t, offset), false);
                    break;
                case 26:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeStringList(numberAt, listAt(t, offset));
                    break;
                case 27:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeMessageList(numberAt, listAt(t, offset), getMessageFieldSchema(i3));
                    break;
                case 28:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeByteStringList(numberAt, listAt(t, offset));
                    break;
                case 29:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeUInt32List(numberAt, listAt(t, offset), false);
                    break;
                case 30:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeEnumList(numberAt, listAt(t, offset), false);
                    break;
                case 31:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeFixed32List(numberAt, listAt(t, offset), false);
                    break;
                case 32:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeFixed64List(numberAt, listAt(t, offset), false);
                    break;
                case 33:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeSInt32List(numberAt, listAt(t, offset), false);
                    break;
                case 34:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeSInt64List(numberAt, listAt(t, offset), false);
                    break;
                case 35:
                    int computeSizeFixed64ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeFixed64ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeFixed64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeFixed64ListNoTag);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag) + computeSizeFixed64ListNoTag;
                        break;
                    }
                case 36:
                    int computeSizeFixed32ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeFixed32ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeFixed32ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeFixed32ListNoTag);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed32ListNoTag) + computeSizeFixed32ListNoTag;
                        break;
                    }
                case 37:
                    int computeSizeInt64ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeInt64ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeInt64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeInt64ListNoTag);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeInt64ListNoTag) + computeSizeInt64ListNoTag;
                        break;
                    }
                case 38:
                    int computeSizeUInt64ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeUInt64ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeUInt64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeUInt64ListNoTag);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeUInt64ListNoTag) + computeSizeUInt64ListNoTag;
                        break;
                    }
                case 39:
                    int computeSizeInt32ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeInt32ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeInt32ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeInt32ListNoTag);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeInt32ListNoTag) + computeSizeInt32ListNoTag;
                        break;
                    }
                case 40:
                    int computeSizeFixed64ListNoTag2 = com.android.framework.protobuf.SchemaUtil.computeSizeFixed64ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeFixed64ListNoTag2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeFixed64ListNoTag2);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag2) + computeSizeFixed64ListNoTag2;
                        break;
                    }
                case 41:
                    int computeSizeFixed32ListNoTag2 = com.android.framework.protobuf.SchemaUtil.computeSizeFixed32ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeFixed32ListNoTag2 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeFixed32ListNoTag2);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed32ListNoTag2) + computeSizeFixed32ListNoTag2;
                        break;
                    }
                case 42:
                    int computeSizeBoolListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeBoolListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeBoolListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeBoolListNoTag);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeBoolListNoTag) + computeSizeBoolListNoTag;
                        break;
                    }
                case 43:
                    int computeSizeUInt32ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeUInt32ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeUInt32ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeUInt32ListNoTag);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeUInt32ListNoTag) + computeSizeUInt32ListNoTag;
                        break;
                    }
                case 44:
                    int computeSizeEnumListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeEnumListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeEnumListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeEnumListNoTag);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeEnumListNoTag) + computeSizeEnumListNoTag;
                        break;
                    }
                case 45:
                    int computeSizeFixed32ListNoTag3 = com.android.framework.protobuf.SchemaUtil.computeSizeFixed32ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeFixed32ListNoTag3 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeFixed32ListNoTag3);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed32ListNoTag3) + computeSizeFixed32ListNoTag3;
                        break;
                    }
                case 46:
                    int computeSizeFixed64ListNoTag3 = com.android.framework.protobuf.SchemaUtil.computeSizeFixed64ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeFixed64ListNoTag3 <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeFixed64ListNoTag3);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeFixed64ListNoTag3) + computeSizeFixed64ListNoTag3;
                        break;
                    }
                case 47:
                    int computeSizeSInt32ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeSInt32ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeSInt32ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeSInt32ListNoTag);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeSInt32ListNoTag) + computeSizeSInt32ListNoTag;
                        break;
                    }
                case 48:
                    int computeSizeSInt64ListNoTag = com.android.framework.protobuf.SchemaUtil.computeSizeSInt64ListNoTag((java.util.List) unsafe.getObject(t, offset));
                    if (computeSizeSInt64ListNoTag <= 0) {
                        break;
                    } else {
                        if (this.useCachedSizeField) {
                            unsafe.putInt(t, i, computeSizeSInt64ListNoTag);
                        }
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeTagSize(numberAt) + com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(computeSizeSInt64ListNoTag) + computeSizeSInt64ListNoTag;
                        break;
                    }
                case 49:
                    i2 += com.android.framework.protobuf.SchemaUtil.computeSizeGroupList(numberAt, listAt(t, offset), getMessageFieldSchema(i3));
                    break;
                case 50:
                    i2 += this.mapFieldSchema.getSerializedSize(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset), getMapFieldDefaultEntry(i3));
                    break;
                case 51:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeDoubleSize(numberAt, 0.0d);
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeFloatSize(numberAt, 0.0f);
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeInt64Size(numberAt, oneofLongAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeUInt64Size(numberAt, oneofLongAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeInt32Size(numberAt, oneofIntAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeFixed64Size(numberAt, 0L);
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeFixed32Size(numberAt, 0);
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeBoolSize(numberAt, true);
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (isOneofPresent(t, numberAt, i3)) {
                        java.lang.Object object2 = com.android.framework.protobuf.UnsafeUtil.getObject(t, offset);
                        if (object2 instanceof com.android.framework.protobuf.ByteString) {
                            i2 += com.android.framework.protobuf.CodedOutputStream.computeBytesSize(numberAt, (com.android.framework.protobuf.ByteString) object2);
                            break;
                        } else {
                            i2 += com.android.framework.protobuf.CodedOutputStream.computeStringSize(numberAt, (java.lang.String) object2);
                            break;
                        }
                    } else {
                        break;
                    }
                case 60:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.SchemaUtil.computeSizeMessage(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset), getMessageFieldSchema(i3));
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeBytesSize(numberAt, (com.android.framework.protobuf.ByteString) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeUInt32Size(numberAt, oneofIntAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeEnumSize(numberAt, oneofIntAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeSFixed32Size(numberAt, 0);
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeSFixed64Size(numberAt, 0L);
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeSInt32Size(numberAt, oneofIntAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeSInt64Size(numberAt, oneofLongAt(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (isOneofPresent(t, numberAt, i3)) {
                        i2 += com.android.framework.protobuf.CodedOutputStream.computeGroupSize(numberAt, (com.android.framework.protobuf.MessageLite) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset), getMessageFieldSchema(i3));
                        break;
                    } else {
                        break;
                    }
            }
        }
        return i2 + getUnknownFieldsSerializedSize(this.unknownFieldSchema, t);
    }

    private <UT, UB> int getUnknownFieldsSerializedSize(com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema, T t) {
        return unknownFieldSchema.getSerializedSize(unknownFieldSchema.getFromMessage(t));
    }

    private static java.util.List<?> listAt(java.lang.Object obj, long j) {
        return (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(obj, j);
    }

    @Override // com.android.framework.protobuf.Schema
    public void writeTo(T t, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (writer.fieldOrder() == com.android.framework.protobuf.Writer.FieldOrder.DESCENDING) {
            writeFieldsInDescendingOrder(t, writer);
        } else if (this.proto3) {
            writeFieldsInAscendingOrderProto3(t, writer);
        } else {
            writeFieldsInAscendingOrderProto2(t, writer);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:301:0x0502  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void writeFieldsInAscendingOrderProto2(T t, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        java.util.Iterator<java.util.Map.Entry<?, java.lang.Object>> it;
        java.util.Map.Entry<?, ?> entry;
        int length;
        int i;
        int i2;
        if (this.hasExtensions) {
            com.android.framework.protobuf.FieldSet<?> extensions = this.extensionSchema.getExtensions(t);
            if (!extensions.isEmpty()) {
                it = extensions.iterator();
                entry = (java.util.Map.Entry) it.next();
                length = this.buffer.length;
                sun.misc.Unsafe unsafe = UNSAFE;
                int i3 = 1048575;
                int i4 = 1048575;
                i = 0;
                int i5 = 0;
                while (i < length) {
                    int typeAndOffsetAt = typeAndOffsetAt(i);
                    int numberAt = numberAt(i);
                    int type = type(typeAndOffsetAt);
                    if (type > 17) {
                        i2 = 0;
                    } else {
                        int i6 = this.buffer[i + 2];
                        int i7 = i6 & i3;
                        if (i7 != i4) {
                            i5 = unsafe.getInt(t, i7);
                            i4 = i7;
                        }
                        i2 = 1 << (i6 >>> 20);
                    }
                    while (entry != null && this.extensionSchema.extensionNumber(entry) <= numberAt) {
                        this.extensionSchema.serializeExtension(writer, entry);
                        entry = it.hasNext() ? (java.util.Map.Entry) it.next() : null;
                    }
                    long offset = offset(typeAndOffsetAt);
                    switch (type) {
                        case 0:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeDouble(numberAt, doubleAt(t, offset));
                                break;
                            }
                        case 1:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeFloat(numberAt, floatAt(t, offset));
                                break;
                            }
                        case 2:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeInt64(numberAt, unsafe.getLong(t, offset));
                                break;
                            }
                        case 3:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeUInt64(numberAt, unsafe.getLong(t, offset));
                                break;
                            }
                        case 4:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeInt32(numberAt, unsafe.getInt(t, offset));
                                break;
                            }
                        case 5:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeFixed64(numberAt, unsafe.getLong(t, offset));
                                break;
                            }
                        case 6:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeFixed32(numberAt, unsafe.getInt(t, offset));
                                break;
                            }
                        case 7:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeBool(numberAt, booleanAt(t, offset));
                                break;
                            }
                        case 8:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writeString(numberAt, unsafe.getObject(t, offset), writer);
                                break;
                            }
                        case 9:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeMessage(numberAt, unsafe.getObject(t, offset), getMessageFieldSchema(i));
                                break;
                            }
                        case 10:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeBytes(numberAt, (com.android.framework.protobuf.ByteString) unsafe.getObject(t, offset));
                                break;
                            }
                        case 11:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeUInt32(numberAt, unsafe.getInt(t, offset));
                                break;
                            }
                        case 12:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeEnum(numberAt, unsafe.getInt(t, offset));
                                break;
                            }
                        case 13:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeSFixed32(numberAt, unsafe.getInt(t, offset));
                                break;
                            }
                        case 14:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeSFixed64(numberAt, unsafe.getLong(t, offset));
                                break;
                            }
                        case 15:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeSInt32(numberAt, unsafe.getInt(t, offset));
                                break;
                            }
                        case 16:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeSInt64(numberAt, unsafe.getLong(t, offset));
                                break;
                            }
                        case 17:
                            if ((i2 & i5) == 0) {
                                break;
                            } else {
                                writer.writeGroup(numberAt, unsafe.getObject(t, offset), getMessageFieldSchema(i));
                                break;
                            }
                        case 18:
                            com.android.framework.protobuf.SchemaUtil.writeDoubleList(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 19:
                            com.android.framework.protobuf.SchemaUtil.writeFloatList(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 20:
                            com.android.framework.protobuf.SchemaUtil.writeInt64List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 21:
                            com.android.framework.protobuf.SchemaUtil.writeUInt64List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 22:
                            com.android.framework.protobuf.SchemaUtil.writeInt32List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 23:
                            com.android.framework.protobuf.SchemaUtil.writeFixed64List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 24:
                            com.android.framework.protobuf.SchemaUtil.writeFixed32List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 25:
                            com.android.framework.protobuf.SchemaUtil.writeBoolList(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 26:
                            com.android.framework.protobuf.SchemaUtil.writeStringList(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer);
                            break;
                        case 27:
                            com.android.framework.protobuf.SchemaUtil.writeMessageList(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, getMessageFieldSchema(i));
                            break;
                        case 28:
                            com.android.framework.protobuf.SchemaUtil.writeBytesList(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer);
                            break;
                        case 29:
                            com.android.framework.protobuf.SchemaUtil.writeUInt32List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 30:
                            com.android.framework.protobuf.SchemaUtil.writeEnumList(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 31:
                            com.android.framework.protobuf.SchemaUtil.writeSFixed32List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 32:
                            com.android.framework.protobuf.SchemaUtil.writeSFixed64List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 33:
                            com.android.framework.protobuf.SchemaUtil.writeSInt32List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 34:
                            com.android.framework.protobuf.SchemaUtil.writeSInt64List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, false);
                            break;
                        case 35:
                            com.android.framework.protobuf.SchemaUtil.writeDoubleList(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 36:
                            com.android.framework.protobuf.SchemaUtil.writeFloatList(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 37:
                            com.android.framework.protobuf.SchemaUtil.writeInt64List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 38:
                            com.android.framework.protobuf.SchemaUtil.writeUInt64List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 39:
                            com.android.framework.protobuf.SchemaUtil.writeInt32List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 40:
                            com.android.framework.protobuf.SchemaUtil.writeFixed64List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 41:
                            com.android.framework.protobuf.SchemaUtil.writeFixed32List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 42:
                            com.android.framework.protobuf.SchemaUtil.writeBoolList(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 43:
                            com.android.framework.protobuf.SchemaUtil.writeUInt32List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 44:
                            com.android.framework.protobuf.SchemaUtil.writeEnumList(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 45:
                            com.android.framework.protobuf.SchemaUtil.writeSFixed32List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 46:
                            com.android.framework.protobuf.SchemaUtil.writeSFixed64List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 47:
                            com.android.framework.protobuf.SchemaUtil.writeSInt32List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 48:
                            com.android.framework.protobuf.SchemaUtil.writeSInt64List(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, true);
                            break;
                        case 49:
                            com.android.framework.protobuf.SchemaUtil.writeGroupList(numberAt(i), (java.util.List) unsafe.getObject(t, offset), writer, getMessageFieldSchema(i));
                            break;
                        case 50:
                            writeMapHelper(writer, numberAt, unsafe.getObject(t, offset), i);
                            break;
                        case 51:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeDouble(numberAt, oneofDoubleAt(t, offset));
                                break;
                            }
                        case 52:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeFloat(numberAt, oneofFloatAt(t, offset));
                                break;
                            }
                        case 53:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeInt64(numberAt, oneofLongAt(t, offset));
                                break;
                            }
                        case 54:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeUInt64(numberAt, oneofLongAt(t, offset));
                                break;
                            }
                        case 55:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeInt32(numberAt, oneofIntAt(t, offset));
                                break;
                            }
                        case 56:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeFixed64(numberAt, oneofLongAt(t, offset));
                                break;
                            }
                        case 57:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeFixed32(numberAt, oneofIntAt(t, offset));
                                break;
                            }
                        case 58:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeBool(numberAt, oneofBooleanAt(t, offset));
                                break;
                            }
                        case 59:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writeString(numberAt, unsafe.getObject(t, offset), writer);
                                break;
                            }
                        case 60:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeMessage(numberAt, unsafe.getObject(t, offset), getMessageFieldSchema(i));
                                break;
                            }
                        case 61:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeBytes(numberAt, (com.android.framework.protobuf.ByteString) unsafe.getObject(t, offset));
                                break;
                            }
                        case 62:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeUInt32(numberAt, oneofIntAt(t, offset));
                                break;
                            }
                        case 63:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeEnum(numberAt, oneofIntAt(t, offset));
                                break;
                            }
                        case 64:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeSFixed32(numberAt, oneofIntAt(t, offset));
                                break;
                            }
                        case 65:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeSFixed64(numberAt, oneofLongAt(t, offset));
                                break;
                            }
                        case 66:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeSInt32(numberAt, oneofIntAt(t, offset));
                                break;
                            }
                        case 67:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeSInt64(numberAt, oneofLongAt(t, offset));
                                break;
                            }
                        case 68:
                            if (!isOneofPresent(t, numberAt, i)) {
                                break;
                            } else {
                                writer.writeGroup(numberAt, unsafe.getObject(t, offset), getMessageFieldSchema(i));
                                break;
                            }
                    }
                    i += 3;
                    i3 = 1048575;
                }
                while (entry != null) {
                    this.extensionSchema.serializeExtension(writer, entry);
                    entry = it.hasNext() ? (java.util.Map.Entry) it.next() : null;
                }
                writeUnknownInMessageTo(this.unknownFieldSchema, t, writer);
            }
        }
        it = null;
        entry = null;
        length = this.buffer.length;
        sun.misc.Unsafe unsafe2 = UNSAFE;
        int i32 = 1048575;
        int i42 = 1048575;
        i = 0;
        int i52 = 0;
        while (i < length) {
        }
        while (entry != null) {
        }
        writeUnknownInMessageTo(this.unknownFieldSchema, t, writer);
    }

    /* JADX WARN: Removed duplicated region for block: B:275:0x05ae  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void writeFieldsInAscendingOrderProto3(T t, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        java.util.Iterator<java.util.Map.Entry<?, java.lang.Object>> it;
        java.util.Map.Entry<?, ?> entry;
        int length;
        int i;
        if (this.hasExtensions) {
            com.android.framework.protobuf.FieldSet<?> extensions = this.extensionSchema.getExtensions(t);
            if (!extensions.isEmpty()) {
                it = extensions.iterator();
                entry = (java.util.Map.Entry) it.next();
                length = this.buffer.length;
                for (i = 0; i < length; i += 3) {
                    int typeAndOffsetAt = typeAndOffsetAt(i);
                    int numberAt = numberAt(i);
                    while (entry != null && this.extensionSchema.extensionNumber(entry) <= numberAt) {
                        this.extensionSchema.serializeExtension(writer, entry);
                        entry = it.hasNext() ? (java.util.Map.Entry) it.next() : null;
                    }
                    switch (type(typeAndOffsetAt)) {
                        case 0:
                            if (isFieldPresent(t, i)) {
                                writer.writeDouble(numberAt, doubleAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 1:
                            if (isFieldPresent(t, i)) {
                                writer.writeFloat(numberAt, floatAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 2:
                            if (isFieldPresent(t, i)) {
                                writer.writeInt64(numberAt, longAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 3:
                            if (isFieldPresent(t, i)) {
                                writer.writeUInt64(numberAt, longAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 4:
                            if (isFieldPresent(t, i)) {
                                writer.writeInt32(numberAt, intAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 5:
                            if (isFieldPresent(t, i)) {
                                writer.writeFixed64(numberAt, longAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 6:
                            if (isFieldPresent(t, i)) {
                                writer.writeFixed32(numberAt, intAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 7:
                            if (isFieldPresent(t, i)) {
                                writer.writeBool(numberAt, booleanAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 8:
                            if (isFieldPresent(t, i)) {
                                writeString(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer);
                                break;
                            } else {
                                break;
                            }
                        case 9:
                            if (isFieldPresent(t, i)) {
                                writer.writeMessage(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), getMessageFieldSchema(i));
                                break;
                            } else {
                                break;
                            }
                        case 10:
                            if (isFieldPresent(t, i)) {
                                writer.writeBytes(numberAt, (com.android.framework.protobuf.ByteString) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 11:
                            if (isFieldPresent(t, i)) {
                                writer.writeUInt32(numberAt, intAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 12:
                            if (isFieldPresent(t, i)) {
                                writer.writeEnum(numberAt, intAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 13:
                            if (isFieldPresent(t, i)) {
                                writer.writeSFixed32(numberAt, intAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 14:
                            if (isFieldPresent(t, i)) {
                                writer.writeSFixed64(numberAt, longAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 15:
                            if (isFieldPresent(t, i)) {
                                writer.writeSInt32(numberAt, intAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 16:
                            if (isFieldPresent(t, i)) {
                                writer.writeSInt64(numberAt, longAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 17:
                            if (isFieldPresent(t, i)) {
                                writer.writeGroup(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), getMessageFieldSchema(i));
                                break;
                            } else {
                                break;
                            }
                        case 18:
                            com.android.framework.protobuf.SchemaUtil.writeDoubleList(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 19:
                            com.android.framework.protobuf.SchemaUtil.writeFloatList(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 20:
                            com.android.framework.protobuf.SchemaUtil.writeInt64List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 21:
                            com.android.framework.protobuf.SchemaUtil.writeUInt64List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 22:
                            com.android.framework.protobuf.SchemaUtil.writeInt32List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 23:
                            com.android.framework.protobuf.SchemaUtil.writeFixed64List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 24:
                            com.android.framework.protobuf.SchemaUtil.writeFixed32List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 25:
                            com.android.framework.protobuf.SchemaUtil.writeBoolList(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 26:
                            com.android.framework.protobuf.SchemaUtil.writeStringList(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer);
                            break;
                        case 27:
                            com.android.framework.protobuf.SchemaUtil.writeMessageList(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, getMessageFieldSchema(i));
                            break;
                        case 28:
                            com.android.framework.protobuf.SchemaUtil.writeBytesList(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer);
                            break;
                        case 29:
                            com.android.framework.protobuf.SchemaUtil.writeUInt32List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 30:
                            com.android.framework.protobuf.SchemaUtil.writeEnumList(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 31:
                            com.android.framework.protobuf.SchemaUtil.writeSFixed32List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 32:
                            com.android.framework.protobuf.SchemaUtil.writeSFixed64List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 33:
                            com.android.framework.protobuf.SchemaUtil.writeSInt32List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 34:
                            com.android.framework.protobuf.SchemaUtil.writeSInt64List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 35:
                            com.android.framework.protobuf.SchemaUtil.writeDoubleList(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 36:
                            com.android.framework.protobuf.SchemaUtil.writeFloatList(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 37:
                            com.android.framework.protobuf.SchemaUtil.writeInt64List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 38:
                            com.android.framework.protobuf.SchemaUtil.writeUInt64List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 39:
                            com.android.framework.protobuf.SchemaUtil.writeInt32List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 40:
                            com.android.framework.protobuf.SchemaUtil.writeFixed64List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 41:
                            com.android.framework.protobuf.SchemaUtil.writeFixed32List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 42:
                            com.android.framework.protobuf.SchemaUtil.writeBoolList(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 43:
                            com.android.framework.protobuf.SchemaUtil.writeUInt32List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 44:
                            com.android.framework.protobuf.SchemaUtil.writeEnumList(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 45:
                            com.android.framework.protobuf.SchemaUtil.writeSFixed32List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 46:
                            com.android.framework.protobuf.SchemaUtil.writeSFixed64List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 47:
                            com.android.framework.protobuf.SchemaUtil.writeSInt32List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 48:
                            com.android.framework.protobuf.SchemaUtil.writeSInt64List(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 49:
                            com.android.framework.protobuf.SchemaUtil.writeGroupList(numberAt(i), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, getMessageFieldSchema(i));
                            break;
                        case 50:
                            writeMapHelper(writer, numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), i);
                            break;
                        case 51:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeDouble(numberAt, oneofDoubleAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 52:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeFloat(numberAt, oneofFloatAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 53:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeInt64(numberAt, oneofLongAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 54:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeUInt64(numberAt, oneofLongAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 55:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeInt32(numberAt, oneofIntAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 56:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeFixed64(numberAt, oneofLongAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 57:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeFixed32(numberAt, oneofIntAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 58:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeBool(numberAt, oneofBooleanAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 59:
                            if (isOneofPresent(t, numberAt, i)) {
                                writeString(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer);
                                break;
                            } else {
                                break;
                            }
                        case 60:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeMessage(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), getMessageFieldSchema(i));
                                break;
                            } else {
                                break;
                            }
                        case 61:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeBytes(numberAt, (com.android.framework.protobuf.ByteString) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 62:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeUInt32(numberAt, oneofIntAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 63:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeEnum(numberAt, oneofIntAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 64:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeSFixed32(numberAt, oneofIntAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 65:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeSFixed64(numberAt, oneofLongAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 66:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeSInt32(numberAt, oneofIntAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 67:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeSInt64(numberAt, oneofLongAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 68:
                            if (isOneofPresent(t, numberAt, i)) {
                                writer.writeGroup(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), getMessageFieldSchema(i));
                                break;
                            } else {
                                break;
                            }
                    }
                }
                while (entry != null) {
                    this.extensionSchema.serializeExtension(writer, entry);
                    entry = it.hasNext() ? (java.util.Map.Entry) it.next() : null;
                }
                writeUnknownInMessageTo(this.unknownFieldSchema, t, writer);
            }
        }
        it = null;
        entry = null;
        length = this.buffer.length;
        while (i < length) {
        }
        while (entry != null) {
        }
        writeUnknownInMessageTo(this.unknownFieldSchema, t, writer);
    }

    /* JADX WARN: Removed duplicated region for block: B:275:0x05b4  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void writeFieldsInDescendingOrder(T t, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        java.util.Iterator<java.util.Map.Entry<?, java.lang.Object>> it;
        java.util.Map.Entry<?, ?> entry;
        int length;
        writeUnknownInMessageTo(this.unknownFieldSchema, t, writer);
        if (this.hasExtensions) {
            com.android.framework.protobuf.FieldSet<?> extensions = this.extensionSchema.getExtensions(t);
            if (!extensions.isEmpty()) {
                it = extensions.descendingIterator();
                entry = (java.util.Map.Entry) it.next();
                for (length = this.buffer.length - 3; length >= 0; length -= 3) {
                    int typeAndOffsetAt = typeAndOffsetAt(length);
                    int numberAt = numberAt(length);
                    while (entry != null && this.extensionSchema.extensionNumber(entry) > numberAt) {
                        this.extensionSchema.serializeExtension(writer, entry);
                        entry = it.hasNext() ? (java.util.Map.Entry) it.next() : null;
                    }
                    switch (type(typeAndOffsetAt)) {
                        case 0:
                            if (isFieldPresent(t, length)) {
                                writer.writeDouble(numberAt, doubleAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 1:
                            if (isFieldPresent(t, length)) {
                                writer.writeFloat(numberAt, floatAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 2:
                            if (isFieldPresent(t, length)) {
                                writer.writeInt64(numberAt, longAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 3:
                            if (isFieldPresent(t, length)) {
                                writer.writeUInt64(numberAt, longAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 4:
                            if (isFieldPresent(t, length)) {
                                writer.writeInt32(numberAt, intAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 5:
                            if (isFieldPresent(t, length)) {
                                writer.writeFixed64(numberAt, longAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 6:
                            if (isFieldPresent(t, length)) {
                                writer.writeFixed32(numberAt, intAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 7:
                            if (isFieldPresent(t, length)) {
                                writer.writeBool(numberAt, booleanAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 8:
                            if (isFieldPresent(t, length)) {
                                writeString(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer);
                                break;
                            } else {
                                break;
                            }
                        case 9:
                            if (isFieldPresent(t, length)) {
                                writer.writeMessage(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), getMessageFieldSchema(length));
                                break;
                            } else {
                                break;
                            }
                        case 10:
                            if (isFieldPresent(t, length)) {
                                writer.writeBytes(numberAt, (com.android.framework.protobuf.ByteString) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 11:
                            if (isFieldPresent(t, length)) {
                                writer.writeUInt32(numberAt, intAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 12:
                            if (isFieldPresent(t, length)) {
                                writer.writeEnum(numberAt, intAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 13:
                            if (isFieldPresent(t, length)) {
                                writer.writeSFixed32(numberAt, intAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 14:
                            if (isFieldPresent(t, length)) {
                                writer.writeSFixed64(numberAt, longAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 15:
                            if (isFieldPresent(t, length)) {
                                writer.writeSInt32(numberAt, intAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 16:
                            if (isFieldPresent(t, length)) {
                                writer.writeSInt64(numberAt, longAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 17:
                            if (isFieldPresent(t, length)) {
                                writer.writeGroup(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), getMessageFieldSchema(length));
                                break;
                            } else {
                                break;
                            }
                        case 18:
                            com.android.framework.protobuf.SchemaUtil.writeDoubleList(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 19:
                            com.android.framework.protobuf.SchemaUtil.writeFloatList(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 20:
                            com.android.framework.protobuf.SchemaUtil.writeInt64List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 21:
                            com.android.framework.protobuf.SchemaUtil.writeUInt64List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 22:
                            com.android.framework.protobuf.SchemaUtil.writeInt32List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 23:
                            com.android.framework.protobuf.SchemaUtil.writeFixed64List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 24:
                            com.android.framework.protobuf.SchemaUtil.writeFixed32List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 25:
                            com.android.framework.protobuf.SchemaUtil.writeBoolList(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 26:
                            com.android.framework.protobuf.SchemaUtil.writeStringList(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer);
                            break;
                        case 27:
                            com.android.framework.protobuf.SchemaUtil.writeMessageList(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, getMessageFieldSchema(length));
                            break;
                        case 28:
                            com.android.framework.protobuf.SchemaUtil.writeBytesList(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer);
                            break;
                        case 29:
                            com.android.framework.protobuf.SchemaUtil.writeUInt32List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 30:
                            com.android.framework.protobuf.SchemaUtil.writeEnumList(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 31:
                            com.android.framework.protobuf.SchemaUtil.writeSFixed32List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 32:
                            com.android.framework.protobuf.SchemaUtil.writeSFixed64List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 33:
                            com.android.framework.protobuf.SchemaUtil.writeSInt32List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 34:
                            com.android.framework.protobuf.SchemaUtil.writeSInt64List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, false);
                            break;
                        case 35:
                            com.android.framework.protobuf.SchemaUtil.writeDoubleList(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 36:
                            com.android.framework.protobuf.SchemaUtil.writeFloatList(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 37:
                            com.android.framework.protobuf.SchemaUtil.writeInt64List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 38:
                            com.android.framework.protobuf.SchemaUtil.writeUInt64List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 39:
                            com.android.framework.protobuf.SchemaUtil.writeInt32List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 40:
                            com.android.framework.protobuf.SchemaUtil.writeFixed64List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 41:
                            com.android.framework.protobuf.SchemaUtil.writeFixed32List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 42:
                            com.android.framework.protobuf.SchemaUtil.writeBoolList(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 43:
                            com.android.framework.protobuf.SchemaUtil.writeUInt32List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 44:
                            com.android.framework.protobuf.SchemaUtil.writeEnumList(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 45:
                            com.android.framework.protobuf.SchemaUtil.writeSFixed32List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 46:
                            com.android.framework.protobuf.SchemaUtil.writeSFixed64List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 47:
                            com.android.framework.protobuf.SchemaUtil.writeSInt32List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 48:
                            com.android.framework.protobuf.SchemaUtil.writeSInt64List(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, true);
                            break;
                        case 49:
                            com.android.framework.protobuf.SchemaUtil.writeGroupList(numberAt(length), (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer, getMessageFieldSchema(length));
                            break;
                        case 50:
                            writeMapHelper(writer, numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), length);
                            break;
                        case 51:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeDouble(numberAt, oneofDoubleAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 52:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeFloat(numberAt, oneofFloatAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 53:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeInt64(numberAt, oneofLongAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 54:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeUInt64(numberAt, oneofLongAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 55:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeInt32(numberAt, oneofIntAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 56:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeFixed64(numberAt, oneofLongAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 57:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeFixed32(numberAt, oneofIntAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 58:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeBool(numberAt, oneofBooleanAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 59:
                            if (isOneofPresent(t, numberAt, length)) {
                                writeString(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), writer);
                                break;
                            } else {
                                break;
                            }
                        case 60:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeMessage(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), getMessageFieldSchema(length));
                                break;
                            } else {
                                break;
                            }
                        case 61:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeBytes(numberAt, (com.android.framework.protobuf.ByteString) com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 62:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeUInt32(numberAt, oneofIntAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 63:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeEnum(numberAt, oneofIntAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 64:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeSFixed32(numberAt, oneofIntAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 65:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeSFixed64(numberAt, oneofLongAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 66:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeSInt32(numberAt, oneofIntAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 67:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeSInt64(numberAt, oneofLongAt(t, offset(typeAndOffsetAt)));
                                break;
                            } else {
                                break;
                            }
                        case 68:
                            if (isOneofPresent(t, numberAt, length)) {
                                writer.writeGroup(numberAt, com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(typeAndOffsetAt)), getMessageFieldSchema(length));
                                break;
                            } else {
                                break;
                            }
                    }
                }
                while (entry != null) {
                    this.extensionSchema.serializeExtension(writer, entry);
                    entry = it.hasNext() ? (java.util.Map.Entry) it.next() : null;
                }
            }
        }
        it = null;
        entry = null;
        while (length >= 0) {
        }
        while (entry != null) {
        }
    }

    private <K, V> void writeMapHelper(com.android.framework.protobuf.Writer writer, int i, java.lang.Object obj, int i2) throws java.io.IOException {
        if (obj != null) {
            writer.writeMap(i, this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(i2)), this.mapFieldSchema.forMapData(obj));
        }
    }

    private <UT, UB> void writeUnknownInMessageTo(com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema, T t, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        unknownFieldSchema.writeTo(unknownFieldSchema.getFromMessage(t), writer);
    }

    @Override // com.android.framework.protobuf.Schema
    public void mergeFrom(T t, com.android.framework.protobuf.Reader reader, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        if (extensionRegistryLite == null) {
            throw new java.lang.NullPointerException();
        }
        checkMutable(t);
        mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, t, reader, extensionRegistryLite);
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    private <UT, UB, ET extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(com.android.framework.protobuf.UnknownFieldSchema<UT, UB> r19, com.android.framework.protobuf.ExtensionSchema<ET> r20, T r21, com.android.framework.protobuf.Reader r22, com.android.framework.protobuf.ExtensionRegistryLite r23) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 2050
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.framework.protobuf.MessageSchema.mergeFromHelper(com.android.framework.protobuf.UnknownFieldSchema, com.android.framework.protobuf.ExtensionSchema, java.lang.Object, com.android.framework.protobuf.Reader, com.android.framework.protobuf.ExtensionRegistryLite):void");
    }

    static com.android.framework.protobuf.UnknownFieldSetLite getMutableUnknownFields(java.lang.Object obj) {
        com.android.framework.protobuf.GeneratedMessageLite generatedMessageLite = (com.android.framework.protobuf.GeneratedMessageLite) obj;
        com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite = generatedMessageLite.unknownFields;
        if (unknownFieldSetLite == com.android.framework.protobuf.UnknownFieldSetLite.getDefaultInstance()) {
            com.android.framework.protobuf.UnknownFieldSetLite newInstance = com.android.framework.protobuf.UnknownFieldSetLite.newInstance();
            generatedMessageLite.unknownFields = newInstance;
            return newInstance;
        }
        return unknownFieldSetLite;
    }

    /* renamed from: com.android.framework.protobuf.MessageSchema$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$FieldType = new int[com.android.framework.protobuf.WireFormat.FieldType.values().length];

        static {
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.BOOL.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.BYTES.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.DOUBLE.ordinal()] = 3;
            } catch (java.lang.NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.FIXED32.ordinal()] = 4;
            } catch (java.lang.NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SFIXED32.ordinal()] = 5;
            } catch (java.lang.NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.FIXED64.ordinal()] = 6;
            } catch (java.lang.NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SFIXED64.ordinal()] = 7;
            } catch (java.lang.NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.FLOAT.ordinal()] = 8;
            } catch (java.lang.NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.ENUM.ordinal()] = 9;
            } catch (java.lang.NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.INT32.ordinal()] = 10;
            } catch (java.lang.NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.UINT32.ordinal()] = 11;
            } catch (java.lang.NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.INT64.ordinal()] = 12;
            } catch (java.lang.NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.UINT64.ordinal()] = 13;
            } catch (java.lang.NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.MESSAGE.ordinal()] = 14;
            } catch (java.lang.NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SINT32.ordinal()] = 15;
            } catch (java.lang.NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.SINT64.ordinal()] = 16;
            } catch (java.lang.NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[com.android.framework.protobuf.WireFormat.FieldType.STRING.ordinal()] = 17;
            } catch (java.lang.NoSuchFieldError e17) {
            }
        }
    }

    private int decodeMapEntryValue(byte[] bArr, int i, int i2, com.android.framework.protobuf.WireFormat.FieldType fieldType, java.lang.Class<?> cls, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        switch (com.android.framework.protobuf.MessageSchema.AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[fieldType.ordinal()]) {
            case 1:
                int decodeVarint64 = com.android.framework.protobuf.ArrayDecoders.decodeVarint64(bArr, i, registers);
                registers.object1 = java.lang.Boolean.valueOf(registers.long1 != 0);
                return decodeVarint64;
            case 2:
                return com.android.framework.protobuf.ArrayDecoders.decodeBytes(bArr, i, registers);
            case 3:
                registers.object1 = java.lang.Double.valueOf(com.android.framework.protobuf.ArrayDecoders.decodeDouble(bArr, i));
                return i + 8;
            case 4:
            case 5:
                registers.object1 = java.lang.Integer.valueOf(com.android.framework.protobuf.ArrayDecoders.decodeFixed32(bArr, i));
                return i + 4;
            case 6:
            case 7:
                registers.object1 = java.lang.Long.valueOf(com.android.framework.protobuf.ArrayDecoders.decodeFixed64(bArr, i));
                return i + 8;
            case 8:
                registers.object1 = java.lang.Float.valueOf(com.android.framework.protobuf.ArrayDecoders.decodeFloat(bArr, i));
                return i + 4;
            case 9:
            case 10:
            case 11:
                int decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr, i, registers);
                registers.object1 = java.lang.Integer.valueOf(registers.int1);
                return decodeVarint32;
            case 12:
            case 13:
                int decodeVarint642 = com.android.framework.protobuf.ArrayDecoders.decodeVarint64(bArr, i, registers);
                registers.object1 = java.lang.Long.valueOf(registers.long1);
                return decodeVarint642;
            case 14:
                return com.android.framework.protobuf.ArrayDecoders.decodeMessageField(com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) cls), bArr, i, i2, registers);
            case 15:
                int decodeVarint322 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr, i, registers);
                registers.object1 = java.lang.Integer.valueOf(com.android.framework.protobuf.CodedInputStream.decodeZigZag32(registers.int1));
                return decodeVarint322;
            case 16:
                int decodeVarint643 = com.android.framework.protobuf.ArrayDecoders.decodeVarint64(bArr, i, registers);
                registers.object1 = java.lang.Long.valueOf(com.android.framework.protobuf.CodedInputStream.decodeZigZag64(registers.long1));
                return decodeVarint643;
            case 17:
                return com.android.framework.protobuf.ArrayDecoders.decodeStringRequireUtf8(bArr, i, registers);
            default:
                throw new java.lang.RuntimeException("unsupported field type.");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r19v0, types: [java.util.Map, java.util.Map<K, V>] */
    /* JADX WARN: Type inference failed for: r1v10, types: [int] */
    private <K, V> int decodeMapEntry(byte[] bArr, int i, int i2, com.android.framework.protobuf.MapEntryLite.Metadata<K, V> metadata, java.util.Map<K, V> map, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        int i3;
        int decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr, i, registers);
        int i4 = registers.int1;
        if (i4 < 0 || i4 > i2 - decodeVarint32) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.truncatedMessage();
        }
        int i5 = decodeVarint32 + i4;
        java.lang.Object obj = metadata.defaultKey;
        java.lang.Object obj2 = metadata.defaultValue;
        while (decodeVarint32 < i5) {
            int i6 = decodeVarint32 + 1;
            byte b = bArr[decodeVarint32];
            if (b >= 0) {
                i3 = i6;
            } else {
                i3 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(b, bArr, i6, registers);
                b = registers.int1;
            }
            int i7 = b & 7;
            switch (b >>> 3) {
                case 1:
                    if (i7 == metadata.keyType.getWireType()) {
                        decodeVarint32 = decodeMapEntryValue(bArr, i3, i2, metadata.keyType, null, registers);
                        obj = registers.object1;
                        break;
                    } else {
                        decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.skipField(b, bArr, i3, i2, registers);
                        break;
                    }
                case 2:
                    if (i7 == metadata.valueType.getWireType()) {
                        decodeVarint32 = decodeMapEntryValue(bArr, i3, i2, metadata.valueType, metadata.defaultValue.getClass(), registers);
                        obj2 = registers.object1;
                        break;
                    } else {
                        decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.skipField(b, bArr, i3, i2, registers);
                        break;
                    }
                default:
                    decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.skipField(b, bArr, i3, i2, registers);
                    break;
            }
        }
        if (decodeVarint32 != i5) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
        }
        map.put(obj, obj2);
        return i5;
    }

    private int parseRepeatedField(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        int decodeVarint32List;
        com.android.framework.protobuf.Internal.ProtobufList protobufList = (com.android.framework.protobuf.Internal.ProtobufList) UNSAFE.getObject(t, j2);
        if (!protobufList.isModifiable()) {
            int size = protobufList.size();
            protobufList = protobufList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
            UNSAFE.putObject(t, j2, protobufList);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i5 == 2) {
                    return com.android.framework.protobuf.ArrayDecoders.decodePackedDoubleList(bArr, i, protobufList, registers);
                }
                if (i5 == 1) {
                    return com.android.framework.protobuf.ArrayDecoders.decodeDoubleList(i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 19:
            case 36:
                if (i5 == 2) {
                    return com.android.framework.protobuf.ArrayDecoders.decodePackedFloatList(bArr, i, protobufList, registers);
                }
                if (i5 == 5) {
                    return com.android.framework.protobuf.ArrayDecoders.decodeFloatList(i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i5 == 2) {
                    return com.android.framework.protobuf.ArrayDecoders.decodePackedVarint64List(bArr, i, protobufList, registers);
                }
                if (i5 == 0) {
                    return com.android.framework.protobuf.ArrayDecoders.decodeVarint64List(i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i5 == 2) {
                    return com.android.framework.protobuf.ArrayDecoders.decodePackedVarint32List(bArr, i, protobufList, registers);
                }
                if (i5 == 0) {
                    return com.android.framework.protobuf.ArrayDecoders.decodeVarint32List(i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i5 == 2) {
                    return com.android.framework.protobuf.ArrayDecoders.decodePackedFixed64List(bArr, i, protobufList, registers);
                }
                if (i5 == 1) {
                    return com.android.framework.protobuf.ArrayDecoders.decodeFixed64List(i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i5 == 2) {
                    return com.android.framework.protobuf.ArrayDecoders.decodePackedFixed32List(bArr, i, protobufList, registers);
                }
                if (i5 == 5) {
                    return com.android.framework.protobuf.ArrayDecoders.decodeFixed32List(i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 25:
            case 42:
                if (i5 == 2) {
                    return com.android.framework.protobuf.ArrayDecoders.decodePackedBoolList(bArr, i, protobufList, registers);
                }
                if (i5 == 0) {
                    return com.android.framework.protobuf.ArrayDecoders.decodeBoolList(i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 26:
                if (i5 == 2) {
                    if ((j & 536870912) == 0) {
                        return com.android.framework.protobuf.ArrayDecoders.decodeStringList(i3, bArr, i, i2, protobufList, registers);
                    }
                    return com.android.framework.protobuf.ArrayDecoders.decodeStringListRequireUtf8(i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 27:
                if (i5 == 2) {
                    return com.android.framework.protobuf.ArrayDecoders.decodeMessageList(getMessageFieldSchema(i6), i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 28:
                if (i5 == 2) {
                    return com.android.framework.protobuf.ArrayDecoders.decodeBytesList(i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 30:
            case 44:
                if (i5 == 2) {
                    decodeVarint32List = com.android.framework.protobuf.ArrayDecoders.decodePackedVarint32List(bArr, i, protobufList, registers);
                } else if (i5 == 0) {
                    decodeVarint32List = com.android.framework.protobuf.ArrayDecoders.decodeVarint32List(i3, bArr, i, i2, protobufList, registers);
                }
                com.android.framework.protobuf.SchemaUtil.filterUnknownEnumList((java.lang.Object) t, i4, (java.util.List<java.lang.Integer>) protobufList, getEnumFieldVerifier(i6), (java.lang.Object) null, (com.android.framework.protobuf.UnknownFieldSchema<UT, java.lang.Object>) this.unknownFieldSchema);
                return decodeVarint32List;
            case 33:
            case 47:
                if (i5 == 2) {
                    return com.android.framework.protobuf.ArrayDecoders.decodePackedSInt32List(bArr, i, protobufList, registers);
                }
                if (i5 == 0) {
                    return com.android.framework.protobuf.ArrayDecoders.decodeSInt32List(i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 34:
            case 48:
                if (i5 == 2) {
                    return com.android.framework.protobuf.ArrayDecoders.decodePackedSInt64List(bArr, i, protobufList, registers);
                }
                if (i5 == 0) {
                    return com.android.framework.protobuf.ArrayDecoders.decodeSInt64List(i3, bArr, i, i2, protobufList, registers);
                }
                break;
            case 49:
                if (i5 == 3) {
                    return com.android.framework.protobuf.ArrayDecoders.decodeGroupList(getMessageFieldSchema(i6), i3, bArr, i, i2, protobufList, registers);
                }
                break;
        }
        return i;
    }

    private <K, V> int parseMapField(T t, byte[] bArr, int i, int i2, int i3, long j, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        sun.misc.Unsafe unsafe = UNSAFE;
        java.lang.Object mapFieldDefaultEntry = getMapFieldDefaultEntry(i3);
        java.lang.Object object = unsafe.getObject(t, j);
        if (this.mapFieldSchema.isImmutable(object)) {
            java.lang.Object newMapField = this.mapFieldSchema.newMapField(mapFieldDefaultEntry);
            this.mapFieldSchema.mergeFrom(newMapField, object);
            unsafe.putObject(t, j, newMapField);
            object = newMapField;
        }
        return decodeMapEntry(bArr, i, i2, this.mapFieldSchema.forMapMetadata(mapFieldDefaultEntry), this.mapFieldSchema.forMutableMapData(object), registers);
    }

    private int parseOneofField(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        sun.misc.Unsafe unsafe = UNSAFE;
        long j2 = this.buffer[i8 + 2] & 1048575;
        switch (i7) {
            case 51:
                if (i5 == 1) {
                    unsafe.putObject(t, j, java.lang.Double.valueOf(com.android.framework.protobuf.ArrayDecoders.decodeDouble(bArr, i)));
                    int i9 = i + 8;
                    unsafe.putInt(t, j2, i4);
                    return i9;
                }
                break;
            case 52:
                if (i5 == 5) {
                    unsafe.putObject(t, j, java.lang.Float.valueOf(com.android.framework.protobuf.ArrayDecoders.decodeFloat(bArr, i)));
                    int i10 = i + 4;
                    unsafe.putInt(t, j2, i4);
                    return i10;
                }
                break;
            case 53:
            case 54:
                if (i5 == 0) {
                    int decodeVarint64 = com.android.framework.protobuf.ArrayDecoders.decodeVarint64(bArr, i, registers);
                    unsafe.putObject(t, j, java.lang.Long.valueOf(registers.long1));
                    unsafe.putInt(t, j2, i4);
                    return decodeVarint64;
                }
                break;
            case 55:
            case 62:
                if (i5 == 0) {
                    int decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr, i, registers);
                    unsafe.putObject(t, j, java.lang.Integer.valueOf(registers.int1));
                    unsafe.putInt(t, j2, i4);
                    return decodeVarint32;
                }
                break;
            case 56:
            case 65:
                if (i5 == 1) {
                    unsafe.putObject(t, j, java.lang.Long.valueOf(com.android.framework.protobuf.ArrayDecoders.decodeFixed64(bArr, i)));
                    int i11 = i + 8;
                    unsafe.putInt(t, j2, i4);
                    return i11;
                }
                break;
            case 57:
            case 64:
                if (i5 == 5) {
                    unsafe.putObject(t, j, java.lang.Integer.valueOf(com.android.framework.protobuf.ArrayDecoders.decodeFixed32(bArr, i)));
                    int i12 = i + 4;
                    unsafe.putInt(t, j2, i4);
                    return i12;
                }
                break;
            case 58:
                if (i5 == 0) {
                    int decodeVarint642 = com.android.framework.protobuf.ArrayDecoders.decodeVarint64(bArr, i, registers);
                    unsafe.putObject(t, j, java.lang.Boolean.valueOf(registers.long1 != 0));
                    unsafe.putInt(t, j2, i4);
                    return decodeVarint642;
                }
                break;
            case 59:
                if (i5 == 2) {
                    int decodeVarint322 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr, i, registers);
                    int i13 = registers.int1;
                    if (i13 == 0) {
                        unsafe.putObject(t, j, "");
                    } else {
                        if ((i6 & 536870912) != 0 && !com.android.framework.protobuf.Utf8.isValidUtf8(bArr, decodeVarint322, decodeVarint322 + i13)) {
                            throw com.android.framework.protobuf.InvalidProtocolBufferException.invalidUtf8();
                        }
                        unsafe.putObject(t, j, new java.lang.String(bArr, decodeVarint322, i13, com.android.framework.protobuf.Internal.UTF_8));
                        decodeVarint322 += i13;
                    }
                    unsafe.putInt(t, j2, i4);
                    return decodeVarint322;
                }
                break;
            case 60:
                if (i5 == 2) {
                    java.lang.Object mutableOneofMessageFieldForMerge = mutableOneofMessageFieldForMerge(t, i4, i8);
                    int mergeMessageField = com.android.framework.protobuf.ArrayDecoders.mergeMessageField(mutableOneofMessageFieldForMerge, getMessageFieldSchema(i8), bArr, i, i2, registers);
                    storeOneofMessageField(t, i4, i8, mutableOneofMessageFieldForMerge);
                    return mergeMessageField;
                }
                break;
            case 61:
                if (i5 == 2) {
                    int decodeBytes = com.android.framework.protobuf.ArrayDecoders.decodeBytes(bArr, i, registers);
                    unsafe.putObject(t, j, registers.object1);
                    unsafe.putInt(t, j2, i4);
                    return decodeBytes;
                }
                break;
            case 63:
                if (i5 == 0) {
                    int decodeVarint323 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr, i, registers);
                    int i14 = registers.int1;
                    com.android.framework.protobuf.Internal.EnumVerifier enumFieldVerifier = getEnumFieldVerifier(i8);
                    if (enumFieldVerifier == null || enumFieldVerifier.isInRange(i14)) {
                        unsafe.putObject(t, j, java.lang.Integer.valueOf(i14));
                        unsafe.putInt(t, j2, i4);
                    } else {
                        getMutableUnknownFields(t).storeField(i3, java.lang.Long.valueOf(i14));
                    }
                    return decodeVarint323;
                }
                break;
            case 66:
                if (i5 == 0) {
                    int decodeVarint324 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr, i, registers);
                    unsafe.putObject(t, j, java.lang.Integer.valueOf(com.android.framework.protobuf.CodedInputStream.decodeZigZag32(registers.int1)));
                    unsafe.putInt(t, j2, i4);
                    return decodeVarint324;
                }
                break;
            case 67:
                if (i5 == 0) {
                    int decodeVarint643 = com.android.framework.protobuf.ArrayDecoders.decodeVarint64(bArr, i, registers);
                    unsafe.putObject(t, j, java.lang.Long.valueOf(com.android.framework.protobuf.CodedInputStream.decodeZigZag64(registers.long1)));
                    unsafe.putInt(t, j2, i4);
                    return decodeVarint643;
                }
                break;
            case 68:
                if (i5 == 3) {
                    java.lang.Object mutableOneofMessageFieldForMerge2 = mutableOneofMessageFieldForMerge(t, i4, i8);
                    int mergeGroupField = com.android.framework.protobuf.ArrayDecoders.mergeGroupField(mutableOneofMessageFieldForMerge2, getMessageFieldSchema(i8), bArr, i, i2, (i3 & (-8)) | 4, registers);
                    storeOneofMessageField(t, i4, i8, mutableOneofMessageFieldForMerge2);
                    return mergeGroupField;
                }
                break;
        }
        return i;
    }

    private com.android.framework.protobuf.Schema getMessageFieldSchema(int i) {
        int i2 = (i / 3) * 2;
        com.android.framework.protobuf.Schema schema = (com.android.framework.protobuf.Schema) this.objects[i2];
        if (schema != null) {
            return schema;
        }
        com.android.framework.protobuf.Schema<T> schemaFor = com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) this.objects[i2 + 1]);
        this.objects[i2] = schemaFor;
        return schemaFor;
    }

    private java.lang.Object getMapFieldDefaultEntry(int i) {
        return this.objects[(i / 3) * 2];
    }

    private com.android.framework.protobuf.Internal.EnumVerifier getEnumFieldVerifier(int i) {
        return (com.android.framework.protobuf.Internal.EnumVerifier) this.objects[((i / 3) * 2) + 1];
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:110:0x009b. Please report as an issue. */
    int parseProto2Message(T t, byte[] bArr, int i, int i2, int i3, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        sun.misc.Unsafe unsafe;
        int i4;
        com.android.framework.protobuf.MessageSchema<T> messageSchema;
        int i5;
        int i6;
        int i7;
        T t2;
        int i8;
        int positionForFieldNumber;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        com.android.framework.protobuf.ArrayDecoders.Registers registers2;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        com.android.framework.protobuf.MessageSchema<T> messageSchema2 = this;
        T t3 = t;
        byte[] bArr2 = bArr;
        int i22 = i2;
        int i23 = i3;
        com.android.framework.protobuf.ArrayDecoders.Registers registers3 = registers;
        checkMutable(t);
        sun.misc.Unsafe unsafe2 = UNSAFE;
        int i24 = i;
        int i25 = 0;
        int i26 = 0;
        int i27 = 0;
        int i28 = -1;
        int i29 = 1048575;
        while (true) {
            if (i24 < i22) {
                int i30 = i24 + 1;
                byte b = bArr2[i24];
                if (b >= 0) {
                    i8 = b;
                } else {
                    int decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(b, bArr2, i30, registers3);
                    i8 = registers3.int1;
                    i30 = decodeVarint32;
                }
                int i31 = i8 >>> 3;
                int i32 = i8 & 7;
                if (i31 > i28) {
                    positionForFieldNumber = messageSchema2.positionForFieldNumber(i31, i25 / 3);
                } else {
                    positionForFieldNumber = messageSchema2.positionForFieldNumber(i31);
                }
                if (positionForFieldNumber == -1) {
                    i9 = i31;
                    i10 = i30;
                    i11 = i8;
                    i12 = i27;
                    i13 = i29;
                    unsafe = unsafe2;
                    i14 = 0;
                } else {
                    int i33 = messageSchema2.buffer[positionForFieldNumber + 1];
                    int type = type(i33);
                    long offset = offset(i33);
                    int i34 = i8;
                    if (type <= 17) {
                        int i35 = messageSchema2.buffer[positionForFieldNumber + 2];
                        int i36 = 1 << (i35 >>> 20);
                        int i37 = i35 & 1048575;
                        if (i37 == i29) {
                            i15 = i36;
                            i16 = i27;
                            i17 = i29;
                        } else {
                            if (i29 == 1048575) {
                                i15 = i36;
                            } else {
                                i15 = i36;
                                unsafe2.putInt(t3, i29, i27);
                            }
                            i17 = i37;
                            i16 = unsafe2.getInt(t3, i37);
                        }
                        switch (type) {
                            case 0:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 == 1) {
                                    com.android.framework.protobuf.UnsafeUtil.putDouble(t3, offset, com.android.framework.protobuf.ArrayDecoders.decodeDouble(bArr, i30));
                                    i24 = i30 + 8;
                                    i27 = i16 | i15;
                                    i23 = i3;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i9;
                                    i29 = i18;
                                    bArr2 = bArr;
                                } else {
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                }
                            case 1:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 == 5) {
                                    com.android.framework.protobuf.UnsafeUtil.putFloat(t3, offset, com.android.framework.protobuf.ArrayDecoders.decodeFloat(bArr, i30));
                                    i24 = i30 + 4;
                                    i27 = i16 | i15;
                                    i23 = i3;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i9;
                                    i29 = i18;
                                    bArr2 = bArr;
                                } else {
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                }
                            case 2:
                            case 3:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 == 0) {
                                    int decodeVarint64 = com.android.framework.protobuf.ArrayDecoders.decodeVarint64(bArr, i30, registers3);
                                    unsafe2.putLong(t, offset, registers3.long1);
                                    i27 = i16 | i15;
                                    i23 = i3;
                                    i25 = i20;
                                    i24 = decodeVarint64;
                                    i26 = i19;
                                    i28 = i9;
                                    i29 = i18;
                                    bArr2 = bArr;
                                } else {
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                }
                            case 4:
                            case 11:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 == 0) {
                                    i24 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr, i30, registers3);
                                    unsafe2.putInt(t3, offset, registers3.int1);
                                    i27 = i16 | i15;
                                    i23 = i3;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i9;
                                    i29 = i18;
                                    bArr2 = bArr;
                                } else {
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                }
                            case 5:
                            case 14:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 == 1) {
                                    unsafe2.putLong(t, offset, com.android.framework.protobuf.ArrayDecoders.decodeFixed64(bArr, i30));
                                    i24 = i30 + 8;
                                    i27 = i16 | i15;
                                    i23 = i3;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i9;
                                    i29 = i18;
                                    bArr2 = bArr;
                                } else {
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                }
                            case 6:
                            case 13:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 == 5) {
                                    unsafe2.putInt(t3, offset, com.android.framework.protobuf.ArrayDecoders.decodeFixed32(bArr, i30));
                                    i24 = i30 + 4;
                                    i27 = i16 | i15;
                                    i23 = i3;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i9;
                                    i29 = i18;
                                    bArr2 = bArr;
                                } else {
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                }
                            case 7:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 == 0) {
                                    i24 = com.android.framework.protobuf.ArrayDecoders.decodeVarint64(bArr, i30, registers3);
                                    com.android.framework.protobuf.UnsafeUtil.putBoolean(t3, offset, registers3.long1 != 0);
                                    i27 = i16 | i15;
                                    i23 = i3;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i9;
                                    i29 = i18;
                                    bArr2 = bArr;
                                } else {
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                }
                            case 8:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 == 2) {
                                    if ((536870912 & i33) == 0) {
                                        i24 = com.android.framework.protobuf.ArrayDecoders.decodeString(bArr, i30, registers3);
                                    } else {
                                        i24 = com.android.framework.protobuf.ArrayDecoders.decodeStringRequireUtf8(bArr, i30, registers3);
                                    }
                                    unsafe2.putObject(t3, offset, registers3.object1);
                                    i27 = i16 | i15;
                                    i23 = i3;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i9;
                                    i29 = i18;
                                    bArr2 = bArr;
                                } else {
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                }
                            case 9:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 == 2) {
                                    java.lang.Object mutableMessageFieldForMerge = messageSchema2.mutableMessageFieldForMerge(t3, i20);
                                    i24 = com.android.framework.protobuf.ArrayDecoders.mergeMessageField(mutableMessageFieldForMerge, messageSchema2.getMessageFieldSchema(i20), bArr, i30, i2, registers);
                                    messageSchema2.storeMessageField(t3, i20, mutableMessageFieldForMerge);
                                    i27 = i16 | i15;
                                    i23 = i3;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i9;
                                    i29 = i18;
                                    bArr2 = bArr;
                                } else {
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                }
                            case 10:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 == 2) {
                                    i24 = com.android.framework.protobuf.ArrayDecoders.decodeBytes(bArr, i30, registers3);
                                    unsafe2.putObject(t3, offset, registers3.object1);
                                    i27 = i16 | i15;
                                    i23 = i3;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i9;
                                    i29 = i18;
                                    bArr2 = bArr;
                                } else {
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                }
                            case 12:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 == 0) {
                                    i24 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr, i30, registers3);
                                    int i38 = registers3.int1;
                                    com.android.framework.protobuf.Internal.EnumVerifier enumFieldVerifier = messageSchema2.getEnumFieldVerifier(i20);
                                    if (enumFieldVerifier == null || enumFieldVerifier.isInRange(i38)) {
                                        unsafe2.putInt(t3, offset, i38);
                                        i27 = i16 | i15;
                                        i23 = i3;
                                        i25 = i20;
                                        i26 = i19;
                                        i28 = i9;
                                        i29 = i18;
                                        bArr2 = bArr;
                                    } else {
                                        getMutableUnknownFields(t).storeField(i19, java.lang.Long.valueOf(i38));
                                        i25 = i20;
                                        i27 = i16;
                                        i26 = i19;
                                        i28 = i9;
                                        i29 = i18;
                                        i23 = i3;
                                        bArr2 = bArr;
                                    }
                                } else {
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                }
                                break;
                            case 15:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 == 0) {
                                    i24 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr, i30, registers3);
                                    unsafe2.putInt(t3, offset, com.android.framework.protobuf.CodedInputStream.decodeZigZag32(registers3.int1));
                                    i27 = i16 | i15;
                                    i23 = i3;
                                    i25 = i20;
                                    i26 = i19;
                                    i28 = i9;
                                    i29 = i18;
                                    bArr2 = bArr;
                                } else {
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                }
                            case 16:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                if (i32 == 0) {
                                    int decodeVarint642 = com.android.framework.protobuf.ArrayDecoders.decodeVarint64(bArr, i30, registers3);
                                    unsafe2.putLong(t, offset, com.android.framework.protobuf.CodedInputStream.decodeZigZag64(registers3.long1));
                                    i27 = i16 | i15;
                                    i23 = i3;
                                    i25 = i20;
                                    i24 = decodeVarint642;
                                    i26 = i19;
                                    i28 = i9;
                                    i29 = i18;
                                    bArr2 = bArr;
                                } else {
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                }
                            case 17:
                                if (i32 != 3) {
                                    i9 = i31;
                                    i18 = i17;
                                    i19 = i34;
                                    i20 = positionForFieldNumber;
                                    i13 = i18;
                                    i10 = i30;
                                    i14 = i20;
                                    unsafe = unsafe2;
                                    i12 = i16;
                                    i11 = i19;
                                    break;
                                } else {
                                    java.lang.Object mutableMessageFieldForMerge2 = messageSchema2.mutableMessageFieldForMerge(t3, positionForFieldNumber);
                                    int i39 = positionForFieldNumber;
                                    i24 = com.android.framework.protobuf.ArrayDecoders.mergeGroupField(mutableMessageFieldForMerge2, messageSchema2.getMessageFieldSchema(positionForFieldNumber), bArr, i30, i2, (i31 << 3) | 4, registers);
                                    messageSchema2.storeMessageField(t3, i39, mutableMessageFieldForMerge2);
                                    i27 = i16 | i15;
                                    i29 = i17;
                                    i23 = i3;
                                    i25 = i39;
                                    i26 = i34;
                                    i28 = i31;
                                    bArr2 = bArr;
                                }
                            default:
                                i9 = i31;
                                i20 = positionForFieldNumber;
                                i18 = i17;
                                i19 = i34;
                                i13 = i18;
                                i10 = i30;
                                i14 = i20;
                                unsafe = unsafe2;
                                i12 = i16;
                                i11 = i19;
                                break;
                        }
                    } else {
                        i9 = i31;
                        int i40 = positionForFieldNumber;
                        i13 = i29;
                        i12 = i27;
                        if (type == 27) {
                            if (i32 != 2) {
                                i21 = i30;
                                unsafe = unsafe2;
                                i14 = i40;
                                i11 = i34;
                                i10 = i21;
                            } else {
                                com.android.framework.protobuf.Internal.ProtobufList protobufList = (com.android.framework.protobuf.Internal.ProtobufList) unsafe2.getObject(t3, offset);
                                if (!protobufList.isModifiable()) {
                                    int size = protobufList.size();
                                    protobufList = protobufList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
                                    unsafe2.putObject(t3, offset, protobufList);
                                }
                                i24 = com.android.framework.protobuf.ArrayDecoders.decodeMessageList(messageSchema2.getMessageFieldSchema(i40), i34, bArr, i30, i2, protobufList, registers);
                                i25 = i40;
                                i26 = i34;
                                i29 = i13;
                                i27 = i12;
                                i28 = i9;
                                bArr2 = bArr;
                                i23 = i3;
                            }
                        } else if (type <= 49) {
                            int i41 = i30;
                            unsafe = unsafe2;
                            i14 = i40;
                            i11 = i34;
                            i24 = parseRepeatedField(t, bArr, i30, i2, i34, i9, i32, i40, i33, type, offset, registers);
                            if (i24 != i41) {
                                messageSchema2 = this;
                                t3 = t;
                                bArr2 = bArr;
                                i22 = i2;
                                i23 = i3;
                                registers3 = registers;
                                i29 = i13;
                                i27 = i12;
                                i25 = i14;
                                i26 = i11;
                                i28 = i9;
                                unsafe2 = unsafe;
                            } else {
                                i10 = i24;
                            }
                        } else {
                            i21 = i30;
                            unsafe = unsafe2;
                            i14 = i40;
                            i11 = i34;
                            if (type == 50) {
                                if (i32 == 2) {
                                    i24 = parseMapField(t, bArr, i21, i2, i14, offset, registers);
                                    if (i24 != i21) {
                                        messageSchema2 = this;
                                        t3 = t;
                                        bArr2 = bArr;
                                        i22 = i2;
                                        i23 = i3;
                                        registers3 = registers;
                                        i29 = i13;
                                        i27 = i12;
                                        i25 = i14;
                                        i26 = i11;
                                        i28 = i9;
                                        unsafe2 = unsafe;
                                    } else {
                                        i10 = i24;
                                    }
                                } else {
                                    i10 = i21;
                                }
                            } else {
                                i24 = parseOneofField(t, bArr, i21, i2, i11, i9, i32, i33, type, offset, i14, registers);
                                if (i24 == i21) {
                                    i10 = i24;
                                } else {
                                    messageSchema2 = this;
                                    t3 = t;
                                    bArr2 = bArr;
                                    i22 = i2;
                                    i23 = i3;
                                    registers3 = registers;
                                    i29 = i13;
                                    i27 = i12;
                                    i25 = i14;
                                    i26 = i11;
                                    i28 = i9;
                                    unsafe2 = unsafe;
                                }
                            }
                        }
                    }
                }
                i4 = i3;
                i6 = i11;
                if (i6 == i4 && i4 != 0) {
                    messageSchema = this;
                    i5 = i10;
                    i7 = i13;
                    i27 = i12;
                } else {
                    if (!this.hasExtensions) {
                        registers2 = registers;
                    } else {
                        registers2 = registers;
                        if (registers2.extensionRegistry != com.android.framework.protobuf.ExtensionRegistryLite.getEmptyRegistry()) {
                            i24 = com.android.framework.protobuf.ArrayDecoders.decodeExtensionOrUnknownField(i6, bArr, i10, i2, t, this.defaultInstance, this.unknownFieldSchema, registers);
                            t3 = t;
                            bArr2 = bArr;
                            i22 = i2;
                            i26 = i6;
                            messageSchema2 = this;
                            registers3 = registers2;
                            i29 = i13;
                            i27 = i12;
                            i25 = i14;
                            i28 = i9;
                            unsafe2 = unsafe;
                            i23 = i4;
                        }
                    }
                    i24 = com.android.framework.protobuf.ArrayDecoders.decodeUnknownField(i6, bArr, i10, i2, getMutableUnknownFields(t), registers);
                    t3 = t;
                    bArr2 = bArr;
                    i22 = i2;
                    i26 = i6;
                    messageSchema2 = this;
                    registers3 = registers2;
                    i29 = i13;
                    i27 = i12;
                    i25 = i14;
                    i28 = i9;
                    unsafe2 = unsafe;
                    i23 = i4;
                }
            } else {
                int i42 = i29;
                unsafe = unsafe2;
                i4 = i23;
                messageSchema = messageSchema2;
                i5 = i24;
                i6 = i26;
                i7 = i42;
            }
        }
        if (i7 == 1048575) {
            t2 = t;
        } else {
            t2 = t;
            unsafe.putInt(t2, i7, i27);
        }
        com.android.framework.protobuf.UnknownFieldSetLite unknownFieldSetLite = null;
        for (int i43 = messageSchema.checkInitializedCount; i43 < messageSchema.repeatedFieldOffsetStart; i43++) {
            unknownFieldSetLite = (com.android.framework.protobuf.UnknownFieldSetLite) filterMapUnknownEnumValues(t, messageSchema.intArray[i43], unknownFieldSetLite, messageSchema.unknownFieldSchema, t);
        }
        if (unknownFieldSetLite != null) {
            messageSchema.unknownFieldSchema.setBuilderToMessage(t2, unknownFieldSetLite);
        }
        if (i4 == 0) {
            if (i5 != i2) {
                throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
            }
        } else if (i5 > i2 || i6 != i4) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
        }
        return i5;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private java.lang.Object mutableMessageFieldForMerge(T t, int i) {
        com.android.framework.protobuf.Schema messageFieldSchema = getMessageFieldSchema(i);
        long offset = offset(typeAndOffsetAt(i));
        if (!isFieldPresent(t, i)) {
            return messageFieldSchema.newInstance();
        }
        java.lang.Object object = UNSAFE.getObject(t, offset);
        if (isMutable(object)) {
            return object;
        }
        java.lang.Object newInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(newInstance, object);
        }
        return newInstance;
    }

    private void storeMessageField(T t, int i, java.lang.Object obj) {
        UNSAFE.putObject(t, offset(typeAndOffsetAt(i)), obj);
        setFieldPresent(t, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private java.lang.Object mutableOneofMessageFieldForMerge(T t, int i, int i2) {
        com.android.framework.protobuf.Schema messageFieldSchema = getMessageFieldSchema(i2);
        if (!isOneofPresent(t, i, i2)) {
            return messageFieldSchema.newInstance();
        }
        java.lang.Object object = UNSAFE.getObject(t, offset(typeAndOffsetAt(i2)));
        if (isMutable(object)) {
            return object;
        }
        java.lang.Object newInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(newInstance, object);
        }
        return newInstance;
    }

    private void storeOneofMessageField(T t, int i, int i2, java.lang.Object obj) {
        UNSAFE.putObject(t, offset(typeAndOffsetAt(i2)), obj);
        setOneofPresent(t, i, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v12, types: [int] */
    private int parseProto3Message(T t, byte[] bArr, int i, int i2, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        byte b;
        int i3;
        int positionForFieldNumber;
        int i4;
        int i5;
        int i6;
        sun.misc.Unsafe unsafe;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        com.android.framework.protobuf.MessageSchema<T> messageSchema = this;
        T t2 = t;
        byte[] bArr2 = bArr;
        int i14 = i2;
        com.android.framework.protobuf.ArrayDecoders.Registers registers2 = registers;
        checkMutable(t);
        sun.misc.Unsafe unsafe2 = UNSAFE;
        int i15 = -1;
        int i16 = i;
        int i17 = -1;
        int i18 = 0;
        int i19 = 0;
        int i20 = 1048575;
        while (i16 < i14) {
            int i21 = i16 + 1;
            byte b2 = bArr2[i16];
            if (b2 >= 0) {
                b = b2;
                i3 = i21;
            } else {
                i3 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(b2, bArr2, i21, registers2);
                b = registers2.int1;
            }
            int i22 = b >>> 3;
            int i23 = b & 7;
            if (i22 > i17) {
                positionForFieldNumber = messageSchema.positionForFieldNumber(i22, i18 / 3);
            } else {
                positionForFieldNumber = messageSchema.positionForFieldNumber(i22);
            }
            if (positionForFieldNumber == i15) {
                i4 = i3;
                i5 = i22;
                i6 = i15;
                unsafe = unsafe2;
                i7 = 0;
            } else {
                int i24 = messageSchema.buffer[positionForFieldNumber + 1];
                int type = type(i24);
                long offset = offset(i24);
                if (type <= 17) {
                    int i25 = messageSchema.buffer[positionForFieldNumber + 2];
                    int i26 = 1 << (i25 >>> 20);
                    int i27 = i25 & 1048575;
                    if (i27 != i20) {
                        if (i20 != 1048575) {
                            unsafe2.putInt(t2, i20, i19);
                        }
                        if (i27 != 1048575) {
                            i19 = unsafe2.getInt(t2, i27);
                        }
                        i20 = i27;
                    }
                    switch (type) {
                        case 0:
                            i5 = i22;
                            registers2 = registers;
                            i8 = positionForFieldNumber;
                            i9 = i3;
                            i10 = i19;
                            if (i23 == 1) {
                                com.android.framework.protobuf.UnsafeUtil.putDouble(t2, offset, com.android.framework.protobuf.ArrayDecoders.decodeDouble(bArr2, i9));
                                i16 = i9 + 8;
                                i19 = i10 | i26;
                                i18 = i8;
                                i17 = i5;
                                i15 = -1;
                                break;
                            } else {
                                i19 = i10;
                                i7 = i8;
                                unsafe = unsafe2;
                                i4 = i9;
                                i6 = -1;
                                break;
                            }
                        case 1:
                            i5 = i22;
                            registers2 = registers;
                            i9 = i3;
                            i10 = i19;
                            i8 = positionForFieldNumber;
                            if (i23 == 5) {
                                com.android.framework.protobuf.UnsafeUtil.putFloat(t2, offset, com.android.framework.protobuf.ArrayDecoders.decodeFloat(bArr2, i9));
                                i16 = i9 + 4;
                                i19 = i10 | i26;
                                i18 = i8;
                                i17 = i5;
                                i15 = -1;
                                break;
                            } else {
                                i19 = i10;
                                i7 = i8;
                                unsafe = unsafe2;
                                i4 = i9;
                                i6 = -1;
                                break;
                            }
                        case 2:
                        case 3:
                            i5 = i22;
                            registers2 = registers;
                            i8 = positionForFieldNumber;
                            i9 = i3;
                            i10 = i19;
                            if (i23 == 0) {
                                int decodeVarint64 = com.android.framework.protobuf.ArrayDecoders.decodeVarint64(bArr2, i9, registers2);
                                unsafe2.putLong(t, offset, registers2.long1);
                                i19 = i10 | i26;
                                i18 = i8;
                                i16 = decodeVarint64;
                                i17 = i5;
                                i15 = -1;
                                break;
                            } else {
                                i19 = i10;
                                i7 = i8;
                                unsafe = unsafe2;
                                i4 = i9;
                                i6 = -1;
                                break;
                            }
                        case 4:
                        case 11:
                            i5 = i22;
                            registers2 = registers;
                            i8 = positionForFieldNumber;
                            i9 = i3;
                            i10 = i19;
                            if (i23 == 0) {
                                i16 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr2, i9, registers2);
                                unsafe2.putInt(t2, offset, registers2.int1);
                                i19 = i10 | i26;
                                i18 = i8;
                                i17 = i5;
                                i15 = -1;
                                break;
                            } else {
                                i19 = i10;
                                i7 = i8;
                                unsafe = unsafe2;
                                i4 = i9;
                                i6 = -1;
                                break;
                            }
                        case 5:
                        case 14:
                            i5 = i22;
                            registers2 = registers;
                            i8 = positionForFieldNumber;
                            i10 = i19;
                            if (i23 == 1) {
                                unsafe2.putLong(t, offset, com.android.framework.protobuf.ArrayDecoders.decodeFixed64(bArr2, i3));
                                i16 = i3 + 8;
                                i19 = i10 | i26;
                                i18 = i8;
                                i17 = i5;
                                i15 = -1;
                                break;
                            } else {
                                i9 = i3;
                                i19 = i10;
                                i7 = i8;
                                unsafe = unsafe2;
                                i4 = i9;
                                i6 = -1;
                                break;
                            }
                        case 6:
                        case 13:
                            i5 = i22;
                            registers2 = registers;
                            i10 = i19;
                            i8 = positionForFieldNumber;
                            if (i23 == 5) {
                                unsafe2.putInt(t2, offset, com.android.framework.protobuf.ArrayDecoders.decodeFixed32(bArr2, i3));
                                i16 = i3 + 4;
                                i19 = i10 | i26;
                                i18 = i8;
                                i17 = i5;
                                i15 = -1;
                                break;
                            } else {
                                i9 = i3;
                                i19 = i10;
                                i7 = i8;
                                unsafe = unsafe2;
                                i4 = i9;
                                i6 = -1;
                                break;
                            }
                        case 7:
                            i5 = i22;
                            registers2 = registers;
                            i8 = positionForFieldNumber;
                            boolean z = true;
                            i10 = i19;
                            if (i23 != 0) {
                                i9 = i3;
                                i19 = i10;
                                i7 = i8;
                                unsafe = unsafe2;
                                i4 = i9;
                                i6 = -1;
                                break;
                            } else {
                                int decodeVarint642 = com.android.framework.protobuf.ArrayDecoders.decodeVarint64(bArr2, i3, registers2);
                                if (registers2.long1 == 0) {
                                    z = false;
                                }
                                com.android.framework.protobuf.UnsafeUtil.putBoolean(t2, offset, z);
                                i19 = i10 | i26;
                                i16 = decodeVarint642;
                                i18 = i8;
                                i17 = i5;
                                i15 = -1;
                                break;
                            }
                        case 8:
                            i5 = i22;
                            registers2 = registers;
                            i8 = positionForFieldNumber;
                            i10 = i19;
                            if (i23 != 2) {
                                i9 = i3;
                                i19 = i10;
                                i7 = i8;
                                unsafe = unsafe2;
                                i4 = i9;
                                i6 = -1;
                                break;
                            } else {
                                if ((536870912 & i24) == 0) {
                                    i16 = com.android.framework.protobuf.ArrayDecoders.decodeString(bArr2, i3, registers2);
                                } else {
                                    i16 = com.android.framework.protobuf.ArrayDecoders.decodeStringRequireUtf8(bArr2, i3, registers2);
                                }
                                unsafe2.putObject(t2, offset, registers2.object1);
                                i19 = i10 | i26;
                                i18 = i8;
                                i17 = i5;
                                i15 = -1;
                                break;
                            }
                        case 9:
                            i5 = i22;
                            registers2 = registers;
                            i8 = positionForFieldNumber;
                            if (i23 != 2) {
                                i9 = i3;
                                i10 = i19;
                                i19 = i10;
                                i7 = i8;
                                unsafe = unsafe2;
                                i4 = i9;
                                i6 = -1;
                                break;
                            } else {
                                java.lang.Object mutableMessageFieldForMerge = messageSchema.mutableMessageFieldForMerge(t2, i8);
                                i16 = com.android.framework.protobuf.ArrayDecoders.mergeMessageField(mutableMessageFieldForMerge, messageSchema.getMessageFieldSchema(i8), bArr, i3, i2, registers);
                                messageSchema.storeMessageField(t2, i8, mutableMessageFieldForMerge);
                                i19 |= i26;
                                i18 = i8;
                                i17 = i5;
                                i15 = -1;
                                break;
                            }
                        case 10:
                            i5 = i22;
                            registers2 = registers;
                            i8 = positionForFieldNumber;
                            if (i23 != 2) {
                                i9 = i3;
                                i10 = i19;
                                i19 = i10;
                                i7 = i8;
                                unsafe = unsafe2;
                                i4 = i9;
                                i6 = -1;
                                break;
                            } else {
                                int decodeBytes = com.android.framework.protobuf.ArrayDecoders.decodeBytes(bArr2, i3, registers2);
                                unsafe2.putObject(t2, offset, registers2.object1);
                                i19 |= i26;
                                i16 = decodeBytes;
                                i18 = i8;
                                i17 = i5;
                                i15 = -1;
                                break;
                            }
                        case 12:
                            i5 = i22;
                            registers2 = registers;
                            i8 = positionForFieldNumber;
                            if (i23 != 0) {
                                i9 = i3;
                                i10 = i19;
                                i19 = i10;
                                i7 = i8;
                                unsafe = unsafe2;
                                i4 = i9;
                                i6 = -1;
                                break;
                            } else {
                                int decodeVarint32 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr2, i3, registers2);
                                unsafe2.putInt(t2, offset, registers2.int1);
                                i19 |= i26;
                                i16 = decodeVarint32;
                                i18 = i8;
                                i17 = i5;
                                i15 = -1;
                                break;
                            }
                        case 15:
                            i5 = i22;
                            registers2 = registers;
                            i8 = positionForFieldNumber;
                            if (i23 != 0) {
                                i9 = i3;
                                i10 = i19;
                                i19 = i10;
                                i7 = i8;
                                unsafe = unsafe2;
                                i4 = i9;
                                i6 = -1;
                                break;
                            } else {
                                i16 = com.android.framework.protobuf.ArrayDecoders.decodeVarint32(bArr2, i3, registers2);
                                unsafe2.putInt(t2, offset, com.android.framework.protobuf.CodedInputStream.decodeZigZag32(registers2.int1));
                                i19 |= i26;
                                i18 = i8;
                                i17 = i5;
                                i15 = -1;
                                break;
                            }
                        case 16:
                            if (i23 != 0) {
                                i5 = i22;
                                i8 = positionForFieldNumber;
                                i9 = i3;
                                i10 = i19;
                                i19 = i10;
                                i7 = i8;
                                unsafe = unsafe2;
                                i4 = i9;
                                i6 = -1;
                                break;
                            } else {
                                registers2 = registers;
                                int decodeVarint643 = com.android.framework.protobuf.ArrayDecoders.decodeVarint64(bArr2, i3, registers2);
                                unsafe2.putLong(t, offset, com.android.framework.protobuf.CodedInputStream.decodeZigZag64(registers2.long1));
                                i19 |= i26;
                                i18 = positionForFieldNumber;
                                i16 = decodeVarint643;
                                i17 = i22;
                                i15 = -1;
                                break;
                            }
                        default:
                            i5 = i22;
                            i8 = positionForFieldNumber;
                            i9 = i3;
                            i10 = i19;
                            i19 = i10;
                            i7 = i8;
                            unsafe = unsafe2;
                            i4 = i9;
                            i6 = -1;
                            break;
                    }
                } else {
                    i5 = i22;
                    int i28 = positionForFieldNumber;
                    int i29 = i19;
                    registers2 = registers;
                    int i30 = i3;
                    if (type == 27) {
                        if (i23 != 2) {
                            i11 = i29;
                            i12 = i20;
                            i7 = i28;
                            unsafe = unsafe2;
                            i13 = i30;
                            i6 = -1;
                            i4 = i13;
                            i20 = i12;
                            i19 = i11;
                        } else {
                            com.android.framework.protobuf.Internal.ProtobufList protobufList = (com.android.framework.protobuf.Internal.ProtobufList) unsafe2.getObject(t2, offset);
                            if (!protobufList.isModifiable()) {
                                int size = protobufList.size();
                                protobufList = protobufList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
                                unsafe2.putObject(t2, offset, protobufList);
                            }
                            i16 = com.android.framework.protobuf.ArrayDecoders.decodeMessageList(messageSchema.getMessageFieldSchema(i28), b, bArr, i30, i2, protobufList, registers);
                            i18 = i28;
                            i19 = i29;
                            i17 = i5;
                            i15 = -1;
                        }
                    } else if (type > 49) {
                        i11 = i29;
                        i12 = i20;
                        i7 = i28;
                        unsafe = unsafe2;
                        i13 = i30;
                        i6 = -1;
                        if (type == 50) {
                            if (i23 == 2) {
                                i16 = parseMapField(t, bArr, i13, i2, i7, offset, registers);
                                if (i16 != i13) {
                                    messageSchema = this;
                                    t2 = t;
                                    bArr2 = bArr;
                                    i14 = i2;
                                    registers2 = registers;
                                    i17 = i5;
                                    i18 = i7;
                                    i20 = i12;
                                    i19 = i11;
                                    i15 = -1;
                                    unsafe2 = unsafe;
                                } else {
                                    i4 = i16;
                                    i20 = i12;
                                    i19 = i11;
                                }
                            } else {
                                i4 = i13;
                                i20 = i12;
                                i19 = i11;
                            }
                        } else {
                            i16 = parseOneofField(t, bArr, i13, i2, b, i5, i23, i24, type, offset, i7, registers);
                            if (i16 == i13) {
                                i4 = i16;
                                i20 = i12;
                                i19 = i11;
                            } else {
                                messageSchema = this;
                                t2 = t;
                                bArr2 = bArr;
                                i14 = i2;
                                registers2 = registers;
                                i17 = i5;
                                i18 = i7;
                                i20 = i12;
                                i19 = i11;
                                i15 = -1;
                                unsafe2 = unsafe;
                            }
                        }
                    } else {
                        int i31 = i20;
                        i7 = i28;
                        i6 = -1;
                        unsafe = unsafe2;
                        i16 = parseRepeatedField(t, bArr, i30, i2, b, i5, i23, i28, i24, type, offset, registers);
                        if (i16 != i30) {
                            messageSchema = this;
                            t2 = t;
                            bArr2 = bArr;
                            i14 = i2;
                            registers2 = registers;
                            i17 = i5;
                            i18 = i7;
                            i20 = i31;
                            i19 = i29;
                            i15 = -1;
                            unsafe2 = unsafe;
                        } else {
                            i4 = i16;
                            i20 = i31;
                            i19 = i29;
                        }
                    }
                }
            }
            i16 = com.android.framework.protobuf.ArrayDecoders.decodeUnknownField(b, bArr, i4, i2, getMutableUnknownFields(t), registers);
            messageSchema = this;
            t2 = t;
            bArr2 = bArr;
            i14 = i2;
            registers2 = registers;
            i17 = i5;
            i18 = i7;
            i15 = i6;
            unsafe2 = unsafe;
        }
        int i32 = i19;
        sun.misc.Unsafe unsafe3 = unsafe2;
        if (i20 != 1048575) {
            unsafe3.putInt(t, i20, i32);
        }
        if (i16 != i2) {
            throw com.android.framework.protobuf.InvalidProtocolBufferException.parseFailure();
        }
        return i16;
    }

    @Override // com.android.framework.protobuf.Schema
    public void mergeFrom(T t, byte[] bArr, int i, int i2, com.android.framework.protobuf.ArrayDecoders.Registers registers) throws java.io.IOException {
        if (this.proto3) {
            parseProto3Message(t, bArr, i, i2, registers);
        } else {
            parseProto2Message(t, bArr, i, i2, 0, registers);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.framework.protobuf.Schema
    public void makeImmutable(T t) {
        if (!isMutable(t)) {
            return;
        }
        if (t instanceof com.android.framework.protobuf.GeneratedMessageLite) {
            com.android.framework.protobuf.GeneratedMessageLite generatedMessageLite = (com.android.framework.protobuf.GeneratedMessageLite) t;
            generatedMessageLite.clearMemoizedSerializedSize();
            generatedMessageLite.clearMemoizedHashCode();
            generatedMessageLite.markImmutable();
        }
        int length = this.buffer.length;
        for (int i = 0; i < length; i += 3) {
            int typeAndOffsetAt = typeAndOffsetAt(i);
            long offset = offset(typeAndOffsetAt);
            switch (type(typeAndOffsetAt)) {
                case 9:
                case 17:
                    if (isFieldPresent(t, i)) {
                        getMessageFieldSchema(i).makeImmutable(UNSAFE.getObject(t, offset));
                        break;
                    } else {
                        break;
                    }
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.listFieldSchema.makeImmutableListAt(t, offset);
                    break;
                case 50:
                    java.lang.Object object = UNSAFE.getObject(t, offset);
                    if (object != null) {
                        UNSAFE.putObject(t, offset, this.mapFieldSchema.toImmutable(object));
                        break;
                    } else {
                        break;
                    }
            }
        }
        this.unknownFieldSchema.makeImmutable(t);
        if (this.hasExtensions) {
            this.extensionSchema.makeImmutable(t);
        }
    }

    private final <K, V> void mergeMap(java.lang.Object obj, int i, java.lang.Object obj2, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite, com.android.framework.protobuf.Reader reader) throws java.io.IOException {
        long offset = offset(typeAndOffsetAt(i));
        java.lang.Object object = com.android.framework.protobuf.UnsafeUtil.getObject(obj, offset);
        if (object == null) {
            object = this.mapFieldSchema.newMapField(obj2);
            com.android.framework.protobuf.UnsafeUtil.putObject(obj, offset, object);
        } else if (this.mapFieldSchema.isImmutable(object)) {
            java.lang.Object newMapField = this.mapFieldSchema.newMapField(obj2);
            this.mapFieldSchema.mergeFrom(newMapField, object);
            com.android.framework.protobuf.UnsafeUtil.putObject(obj, offset, newMapField);
            object = newMapField;
        }
        reader.readMap(this.mapFieldSchema.forMutableMapData(object), this.mapFieldSchema.forMapMetadata(obj2), extensionRegistryLite);
    }

    private <UT, UB> UB filterMapUnknownEnumValues(java.lang.Object obj, int i, UB ub, com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema, java.lang.Object obj2) {
        int numberAt = numberAt(i);
        java.lang.Object object = com.android.framework.protobuf.UnsafeUtil.getObject(obj, offset(typeAndOffsetAt(i)));
        if (object == null) {
            return ub;
        }
        com.android.framework.protobuf.Internal.EnumVerifier enumFieldVerifier = getEnumFieldVerifier(i);
        if (enumFieldVerifier == null) {
            return ub;
        }
        return (UB) filterUnknownEnumMap(i, numberAt, this.mapFieldSchema.forMutableMapData(object), enumFieldVerifier, ub, unknownFieldSchema, obj2);
    }

    private <K, V, UT, UB> UB filterUnknownEnumMap(int i, int i2, java.util.Map<K, V> map, com.android.framework.protobuf.Internal.EnumVerifier enumVerifier, UB ub, com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema, java.lang.Object obj) {
        com.android.framework.protobuf.MapEntryLite.Metadata<?, ?> forMapMetadata = this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(i));
        java.util.Iterator<java.util.Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            java.util.Map.Entry<K, V> next = it.next();
            if (!enumVerifier.isInRange(((java.lang.Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = unknownFieldSchema.getBuilderFromMessage(obj);
                }
                com.android.framework.protobuf.ByteString.CodedBuilder newCodedBuilder = com.android.framework.protobuf.ByteString.newCodedBuilder(com.android.framework.protobuf.MapEntryLite.computeSerializedSize(forMapMetadata, next.getKey(), next.getValue()));
                try {
                    com.android.framework.protobuf.MapEntryLite.writeTo(newCodedBuilder.getCodedOutput(), forMapMetadata, next.getKey(), next.getValue());
                    unknownFieldSchema.addLengthDelimited(ub, i2, newCodedBuilder.build());
                    it.remove();
                } catch (java.io.IOException e) {
                    throw new java.lang.RuntimeException(e);
                }
            }
        }
        return ub;
    }

    @Override // com.android.framework.protobuf.Schema
    public final boolean isInitialized(T t) {
        int i;
        int i2;
        int i3 = 1048575;
        int i4 = 0;
        int i5 = 0;
        while (i5 < this.checkInitializedCount) {
            int i6 = this.intArray[i5];
            int numberAt = numberAt(i6);
            int typeAndOffsetAt = typeAndOffsetAt(i6);
            int i7 = this.buffer[i6 + 2];
            int i8 = i7 & 1048575;
            int i9 = 1 << (i7 >>> 20);
            if (i8 == i3) {
                i = i3;
                i2 = i4;
            } else if (i8 == 1048575) {
                i2 = i4;
                i = i8;
            } else {
                i2 = UNSAFE.getInt(t, i8);
                i = i8;
            }
            if (isRequired(typeAndOffsetAt) && !isFieldPresent(t, i6, i, i2, i9)) {
                return false;
            }
            switch (type(typeAndOffsetAt)) {
                case 9:
                case 17:
                    if (isFieldPresent(t, i6, i, i2, i9) && !isInitialized(t, typeAndOffsetAt, getMessageFieldSchema(i6))) {
                        return false;
                    }
                    break;
                case 27:
                case 49:
                    if (!isListInitialized(t, typeAndOffsetAt, i6)) {
                        return false;
                    }
                    break;
                case 50:
                    if (!isMapInitialized(t, typeAndOffsetAt, i6)) {
                        return false;
                    }
                    break;
                case 60:
                case 68:
                    if (isOneofPresent(t, numberAt, i6) && !isInitialized(t, typeAndOffsetAt, getMessageFieldSchema(i6))) {
                        return false;
                    }
                    break;
            }
            i5++;
            i3 = i;
            i4 = i2;
        }
        return !this.hasExtensions || this.extensionSchema.getExtensions(t).isInitialized();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isInitialized(java.lang.Object obj, int i, com.android.framework.protobuf.Schema schema) {
        return schema.isInitialized(com.android.framework.protobuf.UnsafeUtil.getObject(obj, offset(i)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <N> boolean isListInitialized(java.lang.Object obj, int i, int i2) {
        java.util.List list = (java.util.List) com.android.framework.protobuf.UnsafeUtil.getObject(obj, offset(i));
        if (list.isEmpty()) {
            return true;
        }
        com.android.framework.protobuf.Schema messageFieldSchema = getMessageFieldSchema(i2);
        for (int i3 = 0; i3 < list.size(); i3++) {
            if (!messageFieldSchema.isInitialized(list.get(i3))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8, types: [com.android.framework.protobuf.Schema] */
    private boolean isMapInitialized(T t, int i, int i2) {
        java.util.Map<?, ?> forMapData = this.mapFieldSchema.forMapData(com.android.framework.protobuf.UnsafeUtil.getObject(t, offset(i)));
        if (forMapData.isEmpty()) {
            return true;
        }
        if (this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(i2)).valueType.getJavaType() != com.android.framework.protobuf.WireFormat.JavaType.MESSAGE) {
            return true;
        }
        ?? r5 = 0;
        for (java.lang.Object obj : forMapData.values()) {
            r5 = r5;
            if (r5 == 0) {
                r5 = com.android.framework.protobuf.Protobuf.getInstance().schemaFor((java.lang.Class) obj.getClass());
            }
            if (!r5.isInitialized(obj)) {
                return false;
            }
        }
        return true;
    }

    private void writeString(int i, java.lang.Object obj, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (obj instanceof java.lang.String) {
            writer.writeString(i, (java.lang.String) obj);
        } else {
            writer.writeBytes(i, (com.android.framework.protobuf.ByteString) obj);
        }
    }

    private void readString(java.lang.Object obj, int i, com.android.framework.protobuf.Reader reader) throws java.io.IOException {
        if (isEnforceUtf8(i)) {
            com.android.framework.protobuf.UnsafeUtil.putObject(obj, offset(i), reader.readStringRequireUtf8());
        } else if (this.lite) {
            com.android.framework.protobuf.UnsafeUtil.putObject(obj, offset(i), reader.readString());
        } else {
            com.android.framework.protobuf.UnsafeUtil.putObject(obj, offset(i), reader.readBytes());
        }
    }

    private void readStringList(java.lang.Object obj, int i, com.android.framework.protobuf.Reader reader) throws java.io.IOException {
        if (isEnforceUtf8(i)) {
            reader.readStringListRequireUtf8(this.listFieldSchema.mutableListAt(obj, offset(i)));
        } else {
            reader.readStringList(this.listFieldSchema.mutableListAt(obj, offset(i)));
        }
    }

    private <E> void readMessageList(java.lang.Object obj, int i, com.android.framework.protobuf.Reader reader, com.android.framework.protobuf.Schema<E> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        reader.readMessageList(this.listFieldSchema.mutableListAt(obj, offset(i)), schema, extensionRegistryLite);
    }

    private <E> void readGroupList(java.lang.Object obj, long j, com.android.framework.protobuf.Reader reader, com.android.framework.protobuf.Schema<E> schema, com.android.framework.protobuf.ExtensionRegistryLite extensionRegistryLite) throws java.io.IOException {
        reader.readGroupList(this.listFieldSchema.mutableListAt(obj, j), schema, extensionRegistryLite);
    }

    private int numberAt(int i) {
        return this.buffer[i];
    }

    private int typeAndOffsetAt(int i) {
        return this.buffer[i + 1];
    }

    private int presenceMaskAndOffsetAt(int i) {
        return this.buffer[i + 2];
    }

    private static int type(int i) {
        return (i & FIELD_TYPE_MASK) >>> 20;
    }

    private static boolean isRequired(int i) {
        return (i & 268435456) != 0;
    }

    private static boolean isEnforceUtf8(int i) {
        return (i & 536870912) != 0;
    }

    private static long offset(int i) {
        return i & 1048575;
    }

    private static boolean isMutable(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof com.android.framework.protobuf.GeneratedMessageLite) {
            return ((com.android.framework.protobuf.GeneratedMessageLite) obj).isMutable();
        }
        return true;
    }

    private static void checkMutable(java.lang.Object obj) {
        if (!isMutable(obj)) {
            throw new java.lang.IllegalArgumentException("Mutating immutable message: " + obj);
        }
    }

    private static <T> double doubleAt(T t, long j) {
        return com.android.framework.protobuf.UnsafeUtil.getDouble(t, j);
    }

    private static <T> float floatAt(T t, long j) {
        return com.android.framework.protobuf.UnsafeUtil.getFloat(t, j);
    }

    private static <T> int intAt(T t, long j) {
        return com.android.framework.protobuf.UnsafeUtil.getInt(t, j);
    }

    private static <T> long longAt(T t, long j) {
        return com.android.framework.protobuf.UnsafeUtil.getLong(t, j);
    }

    private static <T> boolean booleanAt(T t, long j) {
        return com.android.framework.protobuf.UnsafeUtil.getBoolean(t, j);
    }

    private static <T> double oneofDoubleAt(T t, long j) {
        return ((java.lang.Double) com.android.framework.protobuf.UnsafeUtil.getObject(t, j)).doubleValue();
    }

    private static <T> float oneofFloatAt(T t, long j) {
        return ((java.lang.Float) com.android.framework.protobuf.UnsafeUtil.getObject(t, j)).floatValue();
    }

    private static <T> int oneofIntAt(T t, long j) {
        return ((java.lang.Integer) com.android.framework.protobuf.UnsafeUtil.getObject(t, j)).intValue();
    }

    private static <T> long oneofLongAt(T t, long j) {
        return ((java.lang.Long) com.android.framework.protobuf.UnsafeUtil.getObject(t, j)).longValue();
    }

    private static <T> boolean oneofBooleanAt(T t, long j) {
        return ((java.lang.Boolean) com.android.framework.protobuf.UnsafeUtil.getObject(t, j)).booleanValue();
    }

    private boolean arePresentForEquals(T t, T t2, int i) {
        return isFieldPresent(t, i) == isFieldPresent(t2, i);
    }

    private boolean isFieldPresent(T t, int i, int i2, int i3, int i4) {
        if (i2 == 1048575) {
            return isFieldPresent(t, i);
        }
        return (i3 & i4) != 0;
    }

    private boolean isFieldPresent(T t, int i) {
        int presenceMaskAndOffsetAt = presenceMaskAndOffsetAt(i);
        long j = 1048575 & presenceMaskAndOffsetAt;
        if (j != 1048575) {
            return (com.android.framework.protobuf.UnsafeUtil.getInt(t, j) & (1 << (presenceMaskAndOffsetAt >>> 20))) != 0;
        }
        int typeAndOffsetAt = typeAndOffsetAt(i);
        long offset = offset(typeAndOffsetAt);
        switch (type(typeAndOffsetAt)) {
            case 0:
                return java.lang.Double.doubleToRawLongBits(com.android.framework.protobuf.UnsafeUtil.getDouble(t, offset)) != 0;
            case 1:
                return java.lang.Float.floatToRawIntBits(com.android.framework.protobuf.UnsafeUtil.getFloat(t, offset)) != 0;
            case 2:
                return com.android.framework.protobuf.UnsafeUtil.getLong(t, offset) != 0;
            case 3:
                return com.android.framework.protobuf.UnsafeUtil.getLong(t, offset) != 0;
            case 4:
                return com.android.framework.protobuf.UnsafeUtil.getInt(t, offset) != 0;
            case 5:
                return com.android.framework.protobuf.UnsafeUtil.getLong(t, offset) != 0;
            case 6:
                return com.android.framework.protobuf.UnsafeUtil.getInt(t, offset) != 0;
            case 7:
                return com.android.framework.protobuf.UnsafeUtil.getBoolean(t, offset);
            case 8:
                java.lang.Object object = com.android.framework.protobuf.UnsafeUtil.getObject(t, offset);
                if (object instanceof java.lang.String) {
                    return !((java.lang.String) object).isEmpty();
                }
                if (object instanceof com.android.framework.protobuf.ByteString) {
                    return !com.android.framework.protobuf.ByteString.EMPTY.equals(object);
                }
                throw new java.lang.IllegalArgumentException();
            case 9:
                return com.android.framework.protobuf.UnsafeUtil.getObject(t, offset) != null;
            case 10:
                return !com.android.framework.protobuf.ByteString.EMPTY.equals(com.android.framework.protobuf.UnsafeUtil.getObject(t, offset));
            case 11:
                return com.android.framework.protobuf.UnsafeUtil.getInt(t, offset) != 0;
            case 12:
                return com.android.framework.protobuf.UnsafeUtil.getInt(t, offset) != 0;
            case 13:
                return com.android.framework.protobuf.UnsafeUtil.getInt(t, offset) != 0;
            case 14:
                return com.android.framework.protobuf.UnsafeUtil.getLong(t, offset) != 0;
            case 15:
                return com.android.framework.protobuf.UnsafeUtil.getInt(t, offset) != 0;
            case 16:
                return com.android.framework.protobuf.UnsafeUtil.getLong(t, offset) != 0;
            case 17:
                return com.android.framework.protobuf.UnsafeUtil.getObject(t, offset) != null;
            default:
                throw new java.lang.IllegalArgumentException();
        }
    }

    private void setFieldPresent(T t, int i) {
        int presenceMaskAndOffsetAt = presenceMaskAndOffsetAt(i);
        long j = 1048575 & presenceMaskAndOffsetAt;
        if (j == 1048575) {
            return;
        }
        com.android.framework.protobuf.UnsafeUtil.putInt(t, j, (1 << (presenceMaskAndOffsetAt >>> 20)) | com.android.framework.protobuf.UnsafeUtil.getInt(t, j));
    }

    private boolean isOneofPresent(T t, int i, int i2) {
        return com.android.framework.protobuf.UnsafeUtil.getInt(t, (long) (presenceMaskAndOffsetAt(i2) & 1048575)) == i;
    }

    private boolean isOneofCaseEqual(T t, T t2, int i) {
        long presenceMaskAndOffsetAt = presenceMaskAndOffsetAt(i) & 1048575;
        return com.android.framework.protobuf.UnsafeUtil.getInt(t, presenceMaskAndOffsetAt) == com.android.framework.protobuf.UnsafeUtil.getInt(t2, presenceMaskAndOffsetAt);
    }

    private void setOneofPresent(T t, int i, int i2) {
        com.android.framework.protobuf.UnsafeUtil.putInt(t, presenceMaskAndOffsetAt(i2) & 1048575, i);
    }

    private int positionForFieldNumber(int i) {
        if (i >= this.minFieldNumber && i <= this.maxFieldNumber) {
            return slowPositionForFieldNumber(i, 0);
        }
        return -1;
    }

    private int positionForFieldNumber(int i, int i2) {
        if (i >= this.minFieldNumber && i <= this.maxFieldNumber) {
            return slowPositionForFieldNumber(i, i2);
        }
        return -1;
    }

    private int slowPositionForFieldNumber(int i, int i2) {
        int length = (this.buffer.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int numberAt = numberAt(i4);
            if (i == numberAt) {
                return i4;
            }
            if (i < numberAt) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }

    int getSchemaSize() {
        return this.buffer.length * 3;
    }
}
