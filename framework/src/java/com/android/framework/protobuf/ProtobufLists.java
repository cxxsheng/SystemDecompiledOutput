package com.android.framework.protobuf;

@com.android.framework.protobuf.CheckReturnValue
/* loaded from: classes4.dex */
final class ProtobufLists {
    private ProtobufLists() {
    }

    public static <E> com.android.framework.protobuf.Internal.ProtobufList<E> emptyProtobufList() {
        return com.android.framework.protobuf.ProtobufArrayList.emptyList();
    }

    public static <E> com.android.framework.protobuf.Internal.ProtobufList<E> mutableCopy(com.android.framework.protobuf.Internal.ProtobufList<E> protobufList) {
        int size = protobufList.size();
        return protobufList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    public static com.android.framework.protobuf.Internal.BooleanList emptyBooleanList() {
        return com.android.framework.protobuf.BooleanArrayList.emptyList();
    }

    public static com.android.framework.protobuf.Internal.BooleanList newBooleanList() {
        return new com.android.framework.protobuf.BooleanArrayList();
    }

    public static com.android.framework.protobuf.Internal.IntList emptyIntList() {
        return com.android.framework.protobuf.IntArrayList.emptyList();
    }

    public static com.android.framework.protobuf.Internal.IntList newIntList() {
        return new com.android.framework.protobuf.IntArrayList();
    }

    public static com.android.framework.protobuf.Internal.LongList emptyLongList() {
        return com.android.framework.protobuf.LongArrayList.emptyList();
    }

    public static com.android.framework.protobuf.Internal.LongList newLongList() {
        return new com.android.framework.protobuf.LongArrayList();
    }

    public static com.android.framework.protobuf.Internal.FloatList emptyFloatList() {
        return com.android.framework.protobuf.FloatArrayList.emptyList();
    }

    public static com.android.framework.protobuf.Internal.FloatList newFloatList() {
        return new com.android.framework.protobuf.FloatArrayList();
    }

    public static com.android.framework.protobuf.Internal.DoubleList emptyDoubleList() {
        return com.android.framework.protobuf.DoubleArrayList.emptyList();
    }

    public static com.android.framework.protobuf.Internal.DoubleList newDoubleList() {
        return new com.android.framework.protobuf.DoubleArrayList();
    }
}
