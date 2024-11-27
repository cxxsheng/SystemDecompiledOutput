package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class SchemaUtil {
    private static final int DEFAULT_LOOK_UP_START_NUMBER = 40;
    private static final java.lang.Class<?> GENERATED_MESSAGE_CLASS = getGeneratedMessageClass();
    private static final com.android.framework.protobuf.UnknownFieldSchema<?, ?> PROTO2_UNKNOWN_FIELD_SET_SCHEMA = getUnknownFieldSetSchema(false);
    private static final com.android.framework.protobuf.UnknownFieldSchema<?, ?> PROTO3_UNKNOWN_FIELD_SET_SCHEMA = getUnknownFieldSetSchema(true);
    private static final com.android.framework.protobuf.UnknownFieldSchema<?, ?> UNKNOWN_FIELD_SET_LITE_SCHEMA = new com.android.framework.protobuf.UnknownFieldSetLiteSchema();

    private SchemaUtil() {
    }

    public static void requireGeneratedMessage(java.lang.Class<?> cls) {
        if (!com.android.framework.protobuf.GeneratedMessageLite.class.isAssignableFrom(cls) && GENERATED_MESSAGE_CLASS != null && !GENERATED_MESSAGE_CLASS.isAssignableFrom(cls)) {
            throw new java.lang.IllegalArgumentException("Message classes must extend GeneratedMessageV3 or GeneratedMessageLite");
        }
    }

    public static void writeDouble(int i, double d, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (java.lang.Double.doubleToRawLongBits(d) != 0) {
            writer.writeDouble(i, d);
        }
    }

    public static void writeFloat(int i, float f, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (java.lang.Float.floatToRawIntBits(f) != 0) {
            writer.writeFloat(i, f);
        }
    }

    public static void writeInt64(int i, long j, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (j != 0) {
            writer.writeInt64(i, j);
        }
    }

    public static void writeUInt64(int i, long j, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (j != 0) {
            writer.writeUInt64(i, j);
        }
    }

    public static void writeSInt64(int i, long j, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (j != 0) {
            writer.writeSInt64(i, j);
        }
    }

    public static void writeFixed64(int i, long j, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (j != 0) {
            writer.writeFixed64(i, j);
        }
    }

    public static void writeSFixed64(int i, long j, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (j != 0) {
            writer.writeSFixed64(i, j);
        }
    }

    public static void writeInt32(int i, int i2, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (i2 != 0) {
            writer.writeInt32(i, i2);
        }
    }

    public static void writeUInt32(int i, int i2, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (i2 != 0) {
            writer.writeUInt32(i, i2);
        }
    }

    public static void writeSInt32(int i, int i2, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (i2 != 0) {
            writer.writeSInt32(i, i2);
        }
    }

    public static void writeFixed32(int i, int i2, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (i2 != 0) {
            writer.writeFixed32(i, i2);
        }
    }

    public static void writeSFixed32(int i, int i2, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (i2 != 0) {
            writer.writeSFixed32(i, i2);
        }
    }

    public static void writeEnum(int i, int i2, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (i2 != 0) {
            writer.writeEnum(i, i2);
        }
    }

    public static void writeBool(int i, boolean z, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (z) {
            writer.writeBool(i, true);
        }
    }

    public static void writeString(int i, java.lang.Object obj, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (obj instanceof java.lang.String) {
            writeStringInternal(i, (java.lang.String) obj, writer);
        } else {
            writeBytes(i, (com.android.framework.protobuf.ByteString) obj, writer);
        }
    }

    private static void writeStringInternal(int i, java.lang.String str, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (str != null && !str.isEmpty()) {
            writer.writeString(i, str);
        }
    }

    public static void writeBytes(int i, com.android.framework.protobuf.ByteString byteString, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (byteString != null && !byteString.isEmpty()) {
            writer.writeBytes(i, byteString);
        }
    }

    public static void writeMessage(int i, java.lang.Object obj, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (obj != null) {
            writer.writeMessage(i, obj);
        }
    }

    public static void writeDoubleList(int i, java.util.List<java.lang.Double> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeDoubleList(i, list, z);
        }
    }

    public static void writeFloatList(int i, java.util.List<java.lang.Float> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeFloatList(i, list, z);
        }
    }

    public static void writeInt64List(int i, java.util.List<java.lang.Long> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeInt64List(i, list, z);
        }
    }

    public static void writeUInt64List(int i, java.util.List<java.lang.Long> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeUInt64List(i, list, z);
        }
    }

    public static void writeSInt64List(int i, java.util.List<java.lang.Long> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeSInt64List(i, list, z);
        }
    }

    public static void writeFixed64List(int i, java.util.List<java.lang.Long> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeFixed64List(i, list, z);
        }
    }

    public static void writeSFixed64List(int i, java.util.List<java.lang.Long> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeSFixed64List(i, list, z);
        }
    }

    public static void writeInt32List(int i, java.util.List<java.lang.Integer> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeInt32List(i, list, z);
        }
    }

    public static void writeUInt32List(int i, java.util.List<java.lang.Integer> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeUInt32List(i, list, z);
        }
    }

    public static void writeSInt32List(int i, java.util.List<java.lang.Integer> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeSInt32List(i, list, z);
        }
    }

    public static void writeFixed32List(int i, java.util.List<java.lang.Integer> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeFixed32List(i, list, z);
        }
    }

    public static void writeSFixed32List(int i, java.util.List<java.lang.Integer> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeSFixed32List(i, list, z);
        }
    }

    public static void writeEnumList(int i, java.util.List<java.lang.Integer> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeEnumList(i, list, z);
        }
    }

    public static void writeBoolList(int i, java.util.List<java.lang.Boolean> list, com.android.framework.protobuf.Writer writer, boolean z) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeBoolList(i, list, z);
        }
    }

    public static void writeStringList(int i, java.util.List<java.lang.String> list, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeStringList(i, list);
        }
    }

    public static void writeBytesList(int i, java.util.List<com.android.framework.protobuf.ByteString> list, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeBytesList(i, list);
        }
    }

    public static void writeMessageList(int i, java.util.List<?> list, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeMessageList(i, list);
        }
    }

    public static void writeMessageList(int i, java.util.List<?> list, com.android.framework.protobuf.Writer writer, com.android.framework.protobuf.Schema schema) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeMessageList(i, list, schema);
        }
    }

    public static void writeLazyFieldList(int i, java.util.List<?> list, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            java.util.Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                ((com.android.framework.protobuf.LazyFieldLite) it.next()).writeTo(writer, i);
            }
        }
    }

    public static void writeGroupList(int i, java.util.List<?> list, com.android.framework.protobuf.Writer writer) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeGroupList(i, list);
        }
    }

    public static void writeGroupList(int i, java.util.List<?> list, com.android.framework.protobuf.Writer writer, com.android.framework.protobuf.Schema schema) throws java.io.IOException {
        if (list != null && !list.isEmpty()) {
            writer.writeGroupList(i, list, schema);
        }
    }

    static int computeSizeInt64ListNoTag(java.util.List<java.lang.Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof com.android.framework.protobuf.LongArrayList) {
            com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) list;
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeInt64SizeNoTag(longArrayList.getLong(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeInt64SizeNoTag(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    static int computeSizeInt64List(int i, java.util.List<java.lang.Long> list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        int computeSizeInt64ListNoTag = computeSizeInt64ListNoTag(list);
        if (z) {
            return com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) + com.android.framework.protobuf.CodedOutputStream.computeLengthDelimitedFieldSize(computeSizeInt64ListNoTag);
        }
        return computeSizeInt64ListNoTag + (list.size() * com.android.framework.protobuf.CodedOutputStream.computeTagSize(i));
    }

    static int computeSizeUInt64ListNoTag(java.util.List<java.lang.Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof com.android.framework.protobuf.LongArrayList) {
            com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) list;
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeUInt64SizeNoTag(longArrayList.getLong(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeUInt64SizeNoTag(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    static int computeSizeUInt64List(int i, java.util.List<java.lang.Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeSizeUInt64ListNoTag = computeSizeUInt64ListNoTag(list);
        if (z) {
            return com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) + com.android.framework.protobuf.CodedOutputStream.computeLengthDelimitedFieldSize(computeSizeUInt64ListNoTag);
        }
        return computeSizeUInt64ListNoTag + (size * com.android.framework.protobuf.CodedOutputStream.computeTagSize(i));
    }

    static int computeSizeSInt64ListNoTag(java.util.List<java.lang.Long> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof com.android.framework.protobuf.LongArrayList) {
            com.android.framework.protobuf.LongArrayList longArrayList = (com.android.framework.protobuf.LongArrayList) list;
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeSInt64SizeNoTag(longArrayList.getLong(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeSInt64SizeNoTag(list.get(i2).longValue());
                i2++;
            }
        }
        return i;
    }

    static int computeSizeSInt64List(int i, java.util.List<java.lang.Long> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeSizeSInt64ListNoTag = computeSizeSInt64ListNoTag(list);
        if (z) {
            return com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) + com.android.framework.protobuf.CodedOutputStream.computeLengthDelimitedFieldSize(computeSizeSInt64ListNoTag);
        }
        return computeSizeSInt64ListNoTag + (size * com.android.framework.protobuf.CodedOutputStream.computeTagSize(i));
    }

    static int computeSizeEnumListNoTag(java.util.List<java.lang.Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof com.android.framework.protobuf.IntArrayList) {
            com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeEnumSizeNoTag(intArrayList.getInt(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeEnumSizeNoTag(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int computeSizeEnumList(int i, java.util.List<java.lang.Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeSizeEnumListNoTag = computeSizeEnumListNoTag(list);
        if (z) {
            return com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) + com.android.framework.protobuf.CodedOutputStream.computeLengthDelimitedFieldSize(computeSizeEnumListNoTag);
        }
        return computeSizeEnumListNoTag + (size * com.android.framework.protobuf.CodedOutputStream.computeTagSize(i));
    }

    static int computeSizeInt32ListNoTag(java.util.List<java.lang.Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof com.android.framework.protobuf.IntArrayList) {
            com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeInt32SizeNoTag(intArrayList.getInt(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeInt32SizeNoTag(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int computeSizeInt32List(int i, java.util.List<java.lang.Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeSizeInt32ListNoTag = computeSizeInt32ListNoTag(list);
        if (z) {
            return com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) + com.android.framework.protobuf.CodedOutputStream.computeLengthDelimitedFieldSize(computeSizeInt32ListNoTag);
        }
        return computeSizeInt32ListNoTag + (size * com.android.framework.protobuf.CodedOutputStream.computeTagSize(i));
    }

    static int computeSizeUInt32ListNoTag(java.util.List<java.lang.Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof com.android.framework.protobuf.IntArrayList) {
            com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(intArrayList.getInt(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeUInt32SizeNoTag(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int computeSizeUInt32List(int i, java.util.List<java.lang.Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeSizeUInt32ListNoTag = computeSizeUInt32ListNoTag(list);
        if (z) {
            return com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) + com.android.framework.protobuf.CodedOutputStream.computeLengthDelimitedFieldSize(computeSizeUInt32ListNoTag);
        }
        return computeSizeUInt32ListNoTag + (size * com.android.framework.protobuf.CodedOutputStream.computeTagSize(i));
    }

    static int computeSizeSInt32ListNoTag(java.util.List<java.lang.Integer> list) {
        int i;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof com.android.framework.protobuf.IntArrayList) {
            com.android.framework.protobuf.IntArrayList intArrayList = (com.android.framework.protobuf.IntArrayList) list;
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeSInt32SizeNoTag(intArrayList.getInt(i2));
                i2++;
            }
        } else {
            i = 0;
            while (i2 < size) {
                i += com.android.framework.protobuf.CodedOutputStream.computeSInt32SizeNoTag(list.get(i2).intValue());
                i2++;
            }
        }
        return i;
    }

    static int computeSizeSInt32List(int i, java.util.List<java.lang.Integer> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeSizeSInt32ListNoTag = computeSizeSInt32ListNoTag(list);
        if (z) {
            return com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) + com.android.framework.protobuf.CodedOutputStream.computeLengthDelimitedFieldSize(computeSizeSInt32ListNoTag);
        }
        return computeSizeSInt32ListNoTag + (size * com.android.framework.protobuf.CodedOutputStream.computeTagSize(i));
    }

    static int computeSizeFixed32ListNoTag(java.util.List<?> list) {
        return list.size() * 4;
    }

    static int computeSizeFixed32List(int i, java.util.List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        if (z) {
            return com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) + com.android.framework.protobuf.CodedOutputStream.computeLengthDelimitedFieldSize(size * 4);
        }
        return size * com.android.framework.protobuf.CodedOutputStream.computeFixed32Size(i, 0);
    }

    static int computeSizeFixed64ListNoTag(java.util.List<?> list) {
        return list.size() * 8;
    }

    static int computeSizeFixed64List(int i, java.util.List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        if (z) {
            return com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) + com.android.framework.protobuf.CodedOutputStream.computeLengthDelimitedFieldSize(size * 8);
        }
        return size * com.android.framework.protobuf.CodedOutputStream.computeFixed64Size(i, 0L);
    }

    static int computeSizeBoolListNoTag(java.util.List<?> list) {
        return list.size();
    }

    static int computeSizeBoolList(int i, java.util.List<?> list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        if (z) {
            return com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) + com.android.framework.protobuf.CodedOutputStream.computeLengthDelimitedFieldSize(size);
        }
        return size * com.android.framework.protobuf.CodedOutputStream.computeBoolSize(i, true);
    }

    static int computeSizeStringList(int i, java.util.List<?> list) {
        int computeStringSizeNoTag;
        int computeStringSizeNoTag2;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int computeTagSize = com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) * size;
        if (list instanceof com.android.framework.protobuf.LazyStringList) {
            com.android.framework.protobuf.LazyStringList lazyStringList = (com.android.framework.protobuf.LazyStringList) list;
            while (i2 < size) {
                java.lang.Object raw = lazyStringList.getRaw(i2);
                if (raw instanceof com.android.framework.protobuf.ByteString) {
                    computeStringSizeNoTag2 = com.android.framework.protobuf.CodedOutputStream.computeBytesSizeNoTag((com.android.framework.protobuf.ByteString) raw);
                } else {
                    computeStringSizeNoTag2 = com.android.framework.protobuf.CodedOutputStream.computeStringSizeNoTag((java.lang.String) raw);
                }
                computeTagSize += computeStringSizeNoTag2;
                i2++;
            }
        } else {
            while (i2 < size) {
                java.lang.Object obj = list.get(i2);
                if (obj instanceof com.android.framework.protobuf.ByteString) {
                    computeStringSizeNoTag = com.android.framework.protobuf.CodedOutputStream.computeBytesSizeNoTag((com.android.framework.protobuf.ByteString) obj);
                } else {
                    computeStringSizeNoTag = com.android.framework.protobuf.CodedOutputStream.computeStringSizeNoTag((java.lang.String) obj);
                }
                computeTagSize += computeStringSizeNoTag;
                i2++;
            }
        }
        return computeTagSize;
    }

    static int computeSizeMessage(int i, java.lang.Object obj, com.android.framework.protobuf.Schema schema) {
        if (obj instanceof com.android.framework.protobuf.LazyFieldLite) {
            return com.android.framework.protobuf.CodedOutputStream.computeLazyFieldSize(i, (com.android.framework.protobuf.LazyFieldLite) obj);
        }
        return com.android.framework.protobuf.CodedOutputStream.computeMessageSize(i, (com.android.framework.protobuf.MessageLite) obj, schema);
    }

    static int computeSizeMessageList(int i, java.util.List<?> list) {
        int computeMessageSizeNoTag;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeTagSize = com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            java.lang.Object obj = list.get(i2);
            if (obj instanceof com.android.framework.protobuf.LazyFieldLite) {
                computeMessageSizeNoTag = com.android.framework.protobuf.CodedOutputStream.computeLazyFieldSizeNoTag((com.android.framework.protobuf.LazyFieldLite) obj);
            } else {
                computeMessageSizeNoTag = com.android.framework.protobuf.CodedOutputStream.computeMessageSizeNoTag((com.android.framework.protobuf.MessageLite) obj);
            }
            computeTagSize += computeMessageSizeNoTag;
        }
        return computeTagSize;
    }

    static int computeSizeMessageList(int i, java.util.List<?> list, com.android.framework.protobuf.Schema schema) {
        int computeMessageSizeNoTag;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeTagSize = com.android.framework.protobuf.CodedOutputStream.computeTagSize(i) * size;
        for (int i2 = 0; i2 < size; i2++) {
            java.lang.Object obj = list.get(i2);
            if (obj instanceof com.android.framework.protobuf.LazyFieldLite) {
                computeMessageSizeNoTag = com.android.framework.protobuf.CodedOutputStream.computeLazyFieldSizeNoTag((com.android.framework.protobuf.LazyFieldLite) obj);
            } else {
                computeMessageSizeNoTag = com.android.framework.protobuf.CodedOutputStream.computeMessageSizeNoTag((com.android.framework.protobuf.MessageLite) obj, schema);
            }
            computeTagSize += computeMessageSizeNoTag;
        }
        return computeTagSize;
    }

    static int computeSizeByteStringList(int i, java.util.List<com.android.framework.protobuf.ByteString> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int computeTagSize = size * com.android.framework.protobuf.CodedOutputStream.computeTagSize(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            computeTagSize += com.android.framework.protobuf.CodedOutputStream.computeBytesSizeNoTag(list.get(i2));
        }
        return computeTagSize;
    }

    static int computeSizeGroupList(int i, java.util.List<com.android.framework.protobuf.MessageLite> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += com.android.framework.protobuf.CodedOutputStream.computeGroupSize(i, list.get(i3));
        }
        return i2;
    }

    static int computeSizeGroupList(int i, java.util.List<com.android.framework.protobuf.MessageLite> list, com.android.framework.protobuf.Schema schema) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += com.android.framework.protobuf.CodedOutputStream.computeGroupSize(i, list.get(i3), schema);
        }
        return i2;
    }

    public static boolean shouldUseTableSwitch(com.android.framework.protobuf.FieldInfo[] fieldInfoArr) {
        if (fieldInfoArr.length == 0) {
            return false;
        }
        return shouldUseTableSwitch(fieldInfoArr[0].getFieldNumber(), fieldInfoArr[fieldInfoArr.length - 1].getFieldNumber(), fieldInfoArr.length);
    }

    public static boolean shouldUseTableSwitch(int i, int i2, int i3) {
        if (i2 < 40) {
            return true;
        }
        long j = (i2 - i) + 1;
        long j2 = i3;
        return j + 9 <= ((2 * j2) + 3) + ((j2 + 3) * 3);
    }

    public static com.android.framework.protobuf.UnknownFieldSchema<?, ?> proto2UnknownFieldSetSchema() {
        return PROTO2_UNKNOWN_FIELD_SET_SCHEMA;
    }

    public static com.android.framework.protobuf.UnknownFieldSchema<?, ?> proto3UnknownFieldSetSchema() {
        return PROTO3_UNKNOWN_FIELD_SET_SCHEMA;
    }

    public static com.android.framework.protobuf.UnknownFieldSchema<?, ?> unknownFieldSetLiteSchema() {
        return UNKNOWN_FIELD_SET_LITE_SCHEMA;
    }

    private static com.android.framework.protobuf.UnknownFieldSchema<?, ?> getUnknownFieldSetSchema(boolean z) {
        try {
            java.lang.Class<?> unknownFieldSetSchemaClass = getUnknownFieldSetSchemaClass();
            if (unknownFieldSetSchemaClass == null) {
                return null;
            }
            return (com.android.framework.protobuf.UnknownFieldSchema) unknownFieldSetSchemaClass.getConstructor(java.lang.Boolean.TYPE).newInstance(java.lang.Boolean.valueOf(z));
        } catch (java.lang.Throwable th) {
            return null;
        }
    }

    private static java.lang.Class<?> getGeneratedMessageClass() {
        try {
            return java.lang.Class.forName("com.android.framework.protobuf.GeneratedMessageV3");
        } catch (java.lang.Throwable th) {
            return null;
        }
    }

    private static java.lang.Class<?> getUnknownFieldSetSchemaClass() {
        try {
            return java.lang.Class.forName("com.android.framework.protobuf.UnknownFieldSetSchema");
        } catch (java.lang.Throwable th) {
            return null;
        }
    }

    static java.lang.Object getMapDefaultEntry(java.lang.Class<?> cls, java.lang.String str) {
        try {
            java.lang.reflect.Field[] declaredFields = java.lang.Class.forName(cls.getName() + "$" + toCamelCase(str, true) + "DefaultEntryHolder").getDeclaredFields();
            if (declaredFields.length != 1) {
                throw new java.lang.IllegalStateException("Unable to look up map field default entry holder class for " + str + " in " + cls.getName());
            }
            return com.android.framework.protobuf.UnsafeUtil.getStaticObject(declaredFields[0]);
        } catch (java.lang.Throwable th) {
            throw new java.lang.RuntimeException(th);
        }
    }

    static java.lang.String toCamelCase(java.lang.String str, boolean z) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if ('a' <= charAt && charAt <= 'z') {
                if (z) {
                    sb.append((char) (charAt - ' '));
                } else {
                    sb.append(charAt);
                }
                z = false;
            } else if ('A' <= charAt && charAt <= 'Z') {
                if (i == 0 && !z) {
                    sb.append((char) (charAt + ' '));
                } else {
                    sb.append(charAt);
                }
                z = false;
            } else if ('0' <= charAt && charAt <= '9') {
                sb.append(charAt);
                z = true;
            } else {
                z = true;
            }
        }
        return sb.toString();
    }

    static boolean safeEquals(java.lang.Object obj, java.lang.Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    static <T> void mergeMap(com.android.framework.protobuf.MapFieldSchema mapFieldSchema, T t, T t2, long j) {
        com.android.framework.protobuf.UnsafeUtil.putObject(t, j, mapFieldSchema.mergeFrom(com.android.framework.protobuf.UnsafeUtil.getObject(t, j), com.android.framework.protobuf.UnsafeUtil.getObject(t2, j)));
    }

    static <T, FT extends com.android.framework.protobuf.FieldSet.FieldDescriptorLite<FT>> void mergeExtensions(com.android.framework.protobuf.ExtensionSchema<FT> extensionSchema, T t, T t2) {
        com.android.framework.protobuf.FieldSet<FT> extensions = extensionSchema.getExtensions(t2);
        if (!extensions.isEmpty()) {
            extensionSchema.getMutableExtensions(t).mergeFrom(extensions);
        }
    }

    static <T, UT, UB> void mergeUnknownFields(com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema, T t, T t2) {
        unknownFieldSchema.setToMessage(t, unknownFieldSchema.merge(unknownFieldSchema.getFromMessage(t), unknownFieldSchema.getFromMessage(t2)));
    }

    static <UT, UB> UB filterUnknownEnumList(java.lang.Object obj, int i, java.util.List<java.lang.Integer> list, com.android.framework.protobuf.Internal.EnumLiteMap<?> enumLiteMap, UB ub, com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema) {
        if (enumLiteMap == null) {
            return ub;
        }
        if (list instanceof java.util.RandomAccess) {
            int size = list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int intValue = list.get(i3).intValue();
                if (enumLiteMap.findValueByNumber(intValue) != null) {
                    if (i3 != i2) {
                        list.set(i2, java.lang.Integer.valueOf(intValue));
                    }
                    i2++;
                } else {
                    ub = (UB) storeUnknownEnum(obj, i, intValue, ub, unknownFieldSchema);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            java.util.Iterator<java.lang.Integer> it = list.iterator();
            while (it.hasNext()) {
                int intValue2 = it.next().intValue();
                if (enumLiteMap.findValueByNumber(intValue2) == null) {
                    ub = (UB) storeUnknownEnum(obj, i, intValue2, ub, unknownFieldSchema);
                    it.remove();
                }
            }
        }
        return ub;
    }

    static <UT, UB> UB filterUnknownEnumList(java.lang.Object obj, int i, java.util.List<java.lang.Integer> list, com.android.framework.protobuf.Internal.EnumVerifier enumVerifier, UB ub, com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema) {
        if (enumVerifier == null) {
            return ub;
        }
        if (list instanceof java.util.RandomAccess) {
            int size = list.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                int intValue = list.get(i3).intValue();
                if (enumVerifier.isInRange(intValue)) {
                    if (i3 != i2) {
                        list.set(i2, java.lang.Integer.valueOf(intValue));
                    }
                    i2++;
                } else {
                    ub = (UB) storeUnknownEnum(obj, i, intValue, ub, unknownFieldSchema);
                }
            }
            if (i2 != size) {
                list.subList(i2, size).clear();
            }
        } else {
            java.util.Iterator<java.lang.Integer> it = list.iterator();
            while (it.hasNext()) {
                int intValue2 = it.next().intValue();
                if (!enumVerifier.isInRange(intValue2)) {
                    ub = (UB) storeUnknownEnum(obj, i, intValue2, ub, unknownFieldSchema);
                    it.remove();
                }
            }
        }
        return ub;
    }

    static <UT, UB> UB storeUnknownEnum(java.lang.Object obj, int i, int i2, UB ub, com.android.framework.protobuf.UnknownFieldSchema<UT, UB> unknownFieldSchema) {
        if (ub == null) {
            ub = unknownFieldSchema.getBuilderFromMessage(obj);
        }
        unknownFieldSchema.addVarint(ub, i, i2);
        return ub;
    }
}
