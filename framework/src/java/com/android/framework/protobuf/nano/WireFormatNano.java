package com.android.framework.protobuf.nano;

/* loaded from: classes4.dex */
public final class WireFormatNano {
    static final int TAG_TYPE_BITS = 3;
    static final int TAG_TYPE_MASK = 7;
    static final int WIRETYPE_END_GROUP = 4;
    static final int WIRETYPE_FIXED32 = 5;
    static final int WIRETYPE_FIXED64 = 1;
    static final int WIRETYPE_LENGTH_DELIMITED = 2;
    static final int WIRETYPE_START_GROUP = 3;
    static final int WIRETYPE_VARINT = 0;
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
    public static final java.lang.String[] EMPTY_STRING_ARRAY = new java.lang.String[0];
    public static final byte[][] EMPTY_BYTES_ARRAY = new byte[0][];
    public static final byte[] EMPTY_BYTES = new byte[0];

    private WireFormatNano() {
    }

    static int getTagWireType(int i) {
        return i & 7;
    }

    public static int getTagFieldNumber(int i) {
        return i >>> 3;
    }

    static int makeTag(int i, int i2) {
        return (i << 3) | i2;
    }

    public static boolean parseUnknownField(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano, int i) throws java.io.IOException {
        return codedInputByteBufferNano.skipField(i);
    }

    public static final int getRepeatedFieldArrayLength(com.android.framework.protobuf.nano.CodedInputByteBufferNano codedInputByteBufferNano, int i) throws java.io.IOException {
        int position = codedInputByteBufferNano.getPosition();
        codedInputByteBufferNano.skipField(i);
        int i2 = 1;
        while (codedInputByteBufferNano.readTag() == i) {
            codedInputByteBufferNano.skipField(i);
            i2++;
        }
        codedInputByteBufferNano.rewindToPosition(position);
        return i2;
    }
}
