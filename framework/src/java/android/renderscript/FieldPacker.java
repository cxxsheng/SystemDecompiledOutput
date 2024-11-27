package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class FieldPacker {
    private java.util.BitSet mAlignment;
    private byte[] mData;
    private int mLen;
    private int mPos;

    public FieldPacker(int i) {
        this.mPos = 0;
        this.mLen = i;
        this.mData = new byte[i];
        this.mAlignment = new java.util.BitSet();
    }

    public FieldPacker(byte[] bArr) {
        this.mPos = bArr.length;
        this.mLen = bArr.length;
        this.mData = bArr;
        this.mAlignment = new java.util.BitSet();
    }

    static android.renderscript.FieldPacker createFromArray(java.lang.Object[] objArr) {
        android.renderscript.FieldPacker fieldPacker = new android.renderscript.FieldPacker(android.renderscript.RenderScript.sPointerSize * 8);
        for (java.lang.Object obj : objArr) {
            fieldPacker.addSafely(obj);
        }
        fieldPacker.resize(fieldPacker.mPos);
        return fieldPacker;
    }

    public void align(int i) {
        if (i > 0) {
            int i2 = i - 1;
            if ((i & i2) == 0) {
                while ((this.mPos & i2) != 0) {
                    this.mAlignment.flip(this.mPos);
                    byte[] bArr = this.mData;
                    int i3 = this.mPos;
                    this.mPos = i3 + 1;
                    bArr[i3] = 0;
                }
                return;
            }
        }
        throw new android.renderscript.RSIllegalArgumentException("argument must be a non-negative non-zero power of 2: " + i);
    }

    public void subalign(int i) {
        int i2 = i - 1;
        if ((i & i2) != 0) {
            throw new android.renderscript.RSIllegalArgumentException("argument must be a non-negative non-zero power of 2: " + i);
        }
        while ((this.mPos & i2) != 0) {
            this.mPos--;
        }
        if (this.mPos > 0) {
            while (this.mAlignment.get(this.mPos - 1)) {
                this.mPos--;
                this.mAlignment.flip(this.mPos);
            }
        }
    }

    public void reset() {
        this.mPos = 0;
    }

    public void reset(int i) {
        if (i < 0 || i > this.mLen) {
            throw new android.renderscript.RSIllegalArgumentException("out of range argument: " + i);
        }
        this.mPos = i;
    }

    public void skip(int i) {
        int i2 = this.mPos + i;
        if (i2 < 0 || i2 > this.mLen) {
            throw new android.renderscript.RSIllegalArgumentException("out of range argument: " + i);
        }
        this.mPos = i2;
    }

    public void addI8(byte b) {
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = b;
    }

    public byte subI8() {
        subalign(1);
        byte[] bArr = this.mData;
        int i = this.mPos - 1;
        this.mPos = i;
        return bArr[i];
    }

    public void addI16(short s) {
        align(2);
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) (s & 255);
        byte[] bArr2 = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr2[i2] = (byte) (s >> 8);
    }

    public short subI16() {
        subalign(2);
        byte[] bArr = this.mData;
        int i = this.mPos - 1;
        this.mPos = i;
        short s = (short) ((bArr[i] & 255) << 8);
        byte[] bArr2 = this.mData;
        int i2 = this.mPos - 1;
        this.mPos = i2;
        return (short) (s | ((short) (bArr2[i2] & 255)));
    }

    public void addI32(int i) {
        align(4);
        byte[] bArr = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr[i2] = (byte) (i & 255);
        byte[] bArr2 = this.mData;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        bArr2[i3] = (byte) ((i >> 8) & 255);
        byte[] bArr3 = this.mData;
        int i4 = this.mPos;
        this.mPos = i4 + 1;
        bArr3[i4] = (byte) ((i >> 16) & 255);
        byte[] bArr4 = this.mData;
        int i5 = this.mPos;
        this.mPos = i5 + 1;
        bArr4[i5] = (byte) ((i >> 24) & 255);
    }

    public int subI32() {
        subalign(4);
        byte[] bArr = this.mData;
        int i = this.mPos - 1;
        this.mPos = i;
        int i2 = (bArr[i] & 255) << 24;
        byte[] bArr2 = this.mData;
        int i3 = this.mPos - 1;
        this.mPos = i3;
        int i4 = i2 | ((bArr2[i3] & 255) << 16);
        byte[] bArr3 = this.mData;
        int i5 = this.mPos - 1;
        this.mPos = i5;
        int i6 = i4 | ((bArr3[i5] & 255) << 8);
        byte[] bArr4 = this.mData;
        int i7 = this.mPos - 1;
        this.mPos = i7;
        return i6 | (bArr4[i7] & 255);
    }

    public void addI64(long j) {
        align(8);
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) (j & 255);
        byte[] bArr2 = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr2[i2] = (byte) ((j >> 8) & 255);
        byte[] bArr3 = this.mData;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        bArr3[i3] = (byte) ((j >> 16) & 255);
        byte[] bArr4 = this.mData;
        int i4 = this.mPos;
        this.mPos = i4 + 1;
        bArr4[i4] = (byte) ((j >> 24) & 255);
        byte[] bArr5 = this.mData;
        int i5 = this.mPos;
        this.mPos = i5 + 1;
        bArr5[i5] = (byte) ((j >> 32) & 255);
        byte[] bArr6 = this.mData;
        int i6 = this.mPos;
        this.mPos = i6 + 1;
        bArr6[i6] = (byte) ((j >> 40) & 255);
        byte[] bArr7 = this.mData;
        int i7 = this.mPos;
        this.mPos = i7 + 1;
        bArr7[i7] = (byte) ((j >> 48) & 255);
        byte[] bArr8 = this.mData;
        int i8 = this.mPos;
        this.mPos = i8 + 1;
        bArr8[i8] = (byte) ((j >> 56) & 255);
    }

    public long subI64() {
        subalign(8);
        byte[] bArr = this.mData;
        this.mPos = this.mPos - 1;
        byte[] bArr2 = this.mData;
        this.mPos = this.mPos - 1;
        long j = ((bArr[r2] & 255) << 56) | 0 | ((bArr2[r6] & 255) << 48);
        byte[] bArr3 = this.mData;
        this.mPos = this.mPos - 1;
        long j2 = j | ((bArr3[r6] & 255) << 40);
        byte[] bArr4 = this.mData;
        this.mPos = this.mPos - 1;
        long j3 = j2 | ((bArr4[r6] & 255) << 32);
        byte[] bArr5 = this.mData;
        this.mPos = this.mPos - 1;
        long j4 = j3 | ((bArr5[r6] & 255) << 24);
        byte[] bArr6 = this.mData;
        this.mPos = this.mPos - 1;
        long j5 = j4 | ((bArr6[r6] & 255) << 16);
        byte[] bArr7 = this.mData;
        this.mPos = this.mPos - 1;
        long j6 = j5 | ((bArr7[r6] & 255) << 8);
        byte[] bArr8 = this.mData;
        this.mPos = this.mPos - 1;
        return j6 | (bArr8[r5] & 255);
    }

    public void addU8(short s) {
        if (s < 0 || s > 255) {
            android.util.Log.e("rs", "FieldPacker.addU8( " + ((int) s) + " )");
            throw new java.lang.IllegalArgumentException("Saving value out of range for type");
        }
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) s;
    }

    public void addU16(int i) {
        if (i < 0 || i > 65535) {
            android.util.Log.e("rs", "FieldPacker.addU16( " + i + " )");
            throw new java.lang.IllegalArgumentException("Saving value out of range for type");
        }
        align(2);
        byte[] bArr = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr[i2] = (byte) (i & 255);
        byte[] bArr2 = this.mData;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        bArr2[i3] = (byte) (i >> 8);
    }

    public void addU32(long j) {
        if (j < 0 || j > 4294967295L) {
            android.util.Log.e("rs", "FieldPacker.addU32( " + j + " )");
            throw new java.lang.IllegalArgumentException("Saving value out of range for type");
        }
        align(4);
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) (j & 255);
        byte[] bArr2 = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr2[i2] = (byte) ((j >> 8) & 255);
        byte[] bArr3 = this.mData;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        bArr3[i3] = (byte) ((j >> 16) & 255);
        byte[] bArr4 = this.mData;
        int i4 = this.mPos;
        this.mPos = i4 + 1;
        bArr4[i4] = (byte) ((j >> 24) & 255);
    }

    public void addU64(long j) {
        if (j < 0) {
            android.util.Log.e("rs", "FieldPacker.addU64( " + j + " )");
            throw new java.lang.IllegalArgumentException("Saving value out of range for type");
        }
        align(8);
        byte[] bArr = this.mData;
        int i = this.mPos;
        this.mPos = i + 1;
        bArr[i] = (byte) (j & 255);
        byte[] bArr2 = this.mData;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        bArr2[i2] = (byte) ((j >> 8) & 255);
        byte[] bArr3 = this.mData;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        bArr3[i3] = (byte) ((j >> 16) & 255);
        byte[] bArr4 = this.mData;
        int i4 = this.mPos;
        this.mPos = i4 + 1;
        bArr4[i4] = (byte) ((j >> 24) & 255);
        byte[] bArr5 = this.mData;
        int i5 = this.mPos;
        this.mPos = i5 + 1;
        bArr5[i5] = (byte) ((j >> 32) & 255);
        byte[] bArr6 = this.mData;
        int i6 = this.mPos;
        this.mPos = i6 + 1;
        bArr6[i6] = (byte) ((j >> 40) & 255);
        byte[] bArr7 = this.mData;
        int i7 = this.mPos;
        this.mPos = i7 + 1;
        bArr7[i7] = (byte) ((j >> 48) & 255);
        byte[] bArr8 = this.mData;
        int i8 = this.mPos;
        this.mPos = i8 + 1;
        bArr8[i8] = (byte) ((j >> 56) & 255);
    }

    public void addF32(float f) {
        addI32(java.lang.Float.floatToRawIntBits(f));
    }

    public float subF32() {
        return java.lang.Float.intBitsToFloat(subI32());
    }

    public void addF64(double d) {
        addI64(java.lang.Double.doubleToRawLongBits(d));
    }

    public double subF64() {
        return java.lang.Double.longBitsToDouble(subI64());
    }

    public void addObj(android.renderscript.BaseObj baseObj) {
        if (baseObj != null) {
            if (android.renderscript.RenderScript.sPointerSize == 8) {
                addI64(baseObj.getID(null));
                addI64(0L);
                addI64(0L);
                addI64(0L);
                return;
            }
            addI32((int) baseObj.getID(null));
            return;
        }
        if (android.renderscript.RenderScript.sPointerSize == 8) {
            addI64(0L);
            addI64(0L);
            addI64(0L);
            addI64(0L);
            return;
        }
        addI32(0);
    }

    public void addF32(android.renderscript.Float2 float2) {
        addF32(float2.x);
        addF32(float2.y);
    }

    public void addF32(android.renderscript.Float3 float3) {
        addF32(float3.x);
        addF32(float3.y);
        addF32(float3.z);
    }

    public void addF32(android.renderscript.Float4 float4) {
        addF32(float4.x);
        addF32(float4.y);
        addF32(float4.z);
        addF32(float4.w);
    }

    public void addF64(android.renderscript.Double2 double2) {
        addF64(double2.x);
        addF64(double2.y);
    }

    public void addF64(android.renderscript.Double3 double3) {
        addF64(double3.x);
        addF64(double3.y);
        addF64(double3.z);
    }

    public void addF64(android.renderscript.Double4 double4) {
        addF64(double4.x);
        addF64(double4.y);
        addF64(double4.z);
        addF64(double4.w);
    }

    public void addI8(android.renderscript.Byte2 byte2) {
        addI8(byte2.x);
        addI8(byte2.y);
    }

    public void addI8(android.renderscript.Byte3 byte3) {
        addI8(byte3.x);
        addI8(byte3.y);
        addI8(byte3.z);
    }

    public void addI8(android.renderscript.Byte4 byte4) {
        addI8(byte4.x);
        addI8(byte4.y);
        addI8(byte4.z);
        addI8(byte4.w);
    }

    public void addU8(android.renderscript.Short2 short2) {
        addU8(short2.x);
        addU8(short2.y);
    }

    public void addU8(android.renderscript.Short3 short3) {
        addU8(short3.x);
        addU8(short3.y);
        addU8(short3.z);
    }

    public void addU8(android.renderscript.Short4 short4) {
        addU8(short4.x);
        addU8(short4.y);
        addU8(short4.z);
        addU8(short4.w);
    }

    public void addI16(android.renderscript.Short2 short2) {
        addI16(short2.x);
        addI16(short2.y);
    }

    public void addI16(android.renderscript.Short3 short3) {
        addI16(short3.x);
        addI16(short3.y);
        addI16(short3.z);
    }

    public void addI16(android.renderscript.Short4 short4) {
        addI16(short4.x);
        addI16(short4.y);
        addI16(short4.z);
        addI16(short4.w);
    }

    public void addU16(android.renderscript.Int2 int2) {
        addU16(int2.x);
        addU16(int2.y);
    }

    public void addU16(android.renderscript.Int3 int3) {
        addU16(int3.x);
        addU16(int3.y);
        addU16(int3.z);
    }

    public void addU16(android.renderscript.Int4 int4) {
        addU16(int4.x);
        addU16(int4.y);
        addU16(int4.z);
        addU16(int4.w);
    }

    public void addI32(android.renderscript.Int2 int2) {
        addI32(int2.x);
        addI32(int2.y);
    }

    public void addI32(android.renderscript.Int3 int3) {
        addI32(int3.x);
        addI32(int3.y);
        addI32(int3.z);
    }

    public void addI32(android.renderscript.Int4 int4) {
        addI32(int4.x);
        addI32(int4.y);
        addI32(int4.z);
        addI32(int4.w);
    }

    public void addU32(android.renderscript.Long2 long2) {
        addU32(long2.x);
        addU32(long2.y);
    }

    public void addU32(android.renderscript.Long3 long3) {
        addU32(long3.x);
        addU32(long3.y);
        addU32(long3.z);
    }

    public void addU32(android.renderscript.Long4 long4) {
        addU32(long4.x);
        addU32(long4.y);
        addU32(long4.z);
        addU32(long4.w);
    }

    public void addI64(android.renderscript.Long2 long2) {
        addI64(long2.x);
        addI64(long2.y);
    }

    public void addI64(android.renderscript.Long3 long3) {
        addI64(long3.x);
        addI64(long3.y);
        addI64(long3.z);
    }

    public void addI64(android.renderscript.Long4 long4) {
        addI64(long4.x);
        addI64(long4.y);
        addI64(long4.z);
        addI64(long4.w);
    }

    public void addU64(android.renderscript.Long2 long2) {
        addU64(long2.x);
        addU64(long2.y);
    }

    public void addU64(android.renderscript.Long3 long3) {
        addU64(long3.x);
        addU64(long3.y);
        addU64(long3.z);
    }

    public void addU64(android.renderscript.Long4 long4) {
        addU64(long4.x);
        addU64(long4.y);
        addU64(long4.z);
        addU64(long4.w);
    }

    public android.renderscript.Float2 subFloat2() {
        android.renderscript.Float2 float2 = new android.renderscript.Float2();
        float2.y = subF32();
        float2.x = subF32();
        return float2;
    }

    public android.renderscript.Float3 subFloat3() {
        android.renderscript.Float3 float3 = new android.renderscript.Float3();
        float3.z = subF32();
        float3.y = subF32();
        float3.x = subF32();
        return float3;
    }

    public android.renderscript.Float4 subFloat4() {
        android.renderscript.Float4 float4 = new android.renderscript.Float4();
        float4.w = subF32();
        float4.z = subF32();
        float4.y = subF32();
        float4.x = subF32();
        return float4;
    }

    public android.renderscript.Double2 subDouble2() {
        android.renderscript.Double2 double2 = new android.renderscript.Double2();
        double2.y = subF64();
        double2.x = subF64();
        return double2;
    }

    public android.renderscript.Double3 subDouble3() {
        android.renderscript.Double3 double3 = new android.renderscript.Double3();
        double3.z = subF64();
        double3.y = subF64();
        double3.x = subF64();
        return double3;
    }

    public android.renderscript.Double4 subDouble4() {
        android.renderscript.Double4 double4 = new android.renderscript.Double4();
        double4.w = subF64();
        double4.z = subF64();
        double4.y = subF64();
        double4.x = subF64();
        return double4;
    }

    public android.renderscript.Byte2 subByte2() {
        android.renderscript.Byte2 byte2 = new android.renderscript.Byte2();
        byte2.y = subI8();
        byte2.x = subI8();
        return byte2;
    }

    public android.renderscript.Byte3 subByte3() {
        android.renderscript.Byte3 byte3 = new android.renderscript.Byte3();
        byte3.z = subI8();
        byte3.y = subI8();
        byte3.x = subI8();
        return byte3;
    }

    public android.renderscript.Byte4 subByte4() {
        android.renderscript.Byte4 byte4 = new android.renderscript.Byte4();
        byte4.w = subI8();
        byte4.z = subI8();
        byte4.y = subI8();
        byte4.x = subI8();
        return byte4;
    }

    public android.renderscript.Short2 subShort2() {
        android.renderscript.Short2 short2 = new android.renderscript.Short2();
        short2.y = subI16();
        short2.x = subI16();
        return short2;
    }

    public android.renderscript.Short3 subShort3() {
        android.renderscript.Short3 short3 = new android.renderscript.Short3();
        short3.z = subI16();
        short3.y = subI16();
        short3.x = subI16();
        return short3;
    }

    public android.renderscript.Short4 subShort4() {
        android.renderscript.Short4 short4 = new android.renderscript.Short4();
        short4.w = subI16();
        short4.z = subI16();
        short4.y = subI16();
        short4.x = subI16();
        return short4;
    }

    public android.renderscript.Int2 subInt2() {
        android.renderscript.Int2 int2 = new android.renderscript.Int2();
        int2.y = subI32();
        int2.x = subI32();
        return int2;
    }

    public android.renderscript.Int3 subInt3() {
        android.renderscript.Int3 int3 = new android.renderscript.Int3();
        int3.z = subI32();
        int3.y = subI32();
        int3.x = subI32();
        return int3;
    }

    public android.renderscript.Int4 subInt4() {
        android.renderscript.Int4 int4 = new android.renderscript.Int4();
        int4.w = subI32();
        int4.z = subI32();
        int4.y = subI32();
        int4.x = subI32();
        return int4;
    }

    public android.renderscript.Long2 subLong2() {
        android.renderscript.Long2 long2 = new android.renderscript.Long2();
        long2.y = subI64();
        long2.x = subI64();
        return long2;
    }

    public android.renderscript.Long3 subLong3() {
        android.renderscript.Long3 long3 = new android.renderscript.Long3();
        long3.z = subI64();
        long3.y = subI64();
        long3.x = subI64();
        return long3;
    }

    public android.renderscript.Long4 subLong4() {
        android.renderscript.Long4 long4 = new android.renderscript.Long4();
        long4.w = subI64();
        long4.z = subI64();
        long4.y = subI64();
        long4.x = subI64();
        return long4;
    }

    public void addMatrix(android.renderscript.Matrix4f matrix4f) {
        for (int i = 0; i < matrix4f.mMat.length; i++) {
            addF32(matrix4f.mMat[i]);
        }
    }

    public android.renderscript.Matrix4f subMatrix4f() {
        android.renderscript.Matrix4f matrix4f = new android.renderscript.Matrix4f();
        for (int length = matrix4f.mMat.length - 1; length >= 0; length--) {
            matrix4f.mMat[length] = subF32();
        }
        return matrix4f;
    }

    public void addMatrix(android.renderscript.Matrix3f matrix3f) {
        for (int i = 0; i < matrix3f.mMat.length; i++) {
            addF32(matrix3f.mMat[i]);
        }
    }

    public android.renderscript.Matrix3f subMatrix3f() {
        android.renderscript.Matrix3f matrix3f = new android.renderscript.Matrix3f();
        for (int length = matrix3f.mMat.length - 1; length >= 0; length--) {
            matrix3f.mMat[length] = subF32();
        }
        return matrix3f;
    }

    public void addMatrix(android.renderscript.Matrix2f matrix2f) {
        for (int i = 0; i < matrix2f.mMat.length; i++) {
            addF32(matrix2f.mMat[i]);
        }
    }

    public android.renderscript.Matrix2f subMatrix2f() {
        android.renderscript.Matrix2f matrix2f = new android.renderscript.Matrix2f();
        for (int length = matrix2f.mMat.length - 1; length >= 0; length--) {
            matrix2f.mMat[length] = subF32();
        }
        return matrix2f;
    }

    public void addBoolean(boolean z) {
        addI8(z ? (byte) 1 : (byte) 0);
    }

    public boolean subBoolean() {
        if (subI8() == 1) {
            return true;
        }
        return false;
    }

    public final byte[] getData() {
        return this.mData;
    }

    public int getPos() {
        return this.mPos;
    }

    private void add(java.lang.Object obj) {
        if (obj instanceof java.lang.Boolean) {
            addBoolean(((java.lang.Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof java.lang.Byte) {
            addI8(((java.lang.Byte) obj).byteValue());
            return;
        }
        if (obj instanceof java.lang.Short) {
            addI16(((java.lang.Short) obj).shortValue());
            return;
        }
        if (obj instanceof java.lang.Integer) {
            addI32(((java.lang.Integer) obj).intValue());
            return;
        }
        if (obj instanceof java.lang.Long) {
            addI64(((java.lang.Long) obj).longValue());
            return;
        }
        if (obj instanceof java.lang.Float) {
            addF32(((java.lang.Float) obj).floatValue());
            return;
        }
        if (obj instanceof java.lang.Double) {
            addF64(((java.lang.Double) obj).doubleValue());
            return;
        }
        if (obj instanceof android.renderscript.Byte2) {
            addI8((android.renderscript.Byte2) obj);
            return;
        }
        if (obj instanceof android.renderscript.Byte3) {
            addI8((android.renderscript.Byte3) obj);
            return;
        }
        if (obj instanceof android.renderscript.Byte4) {
            addI8((android.renderscript.Byte4) obj);
            return;
        }
        if (obj instanceof android.renderscript.Short2) {
            addI16((android.renderscript.Short2) obj);
            return;
        }
        if (obj instanceof android.renderscript.Short3) {
            addI16((android.renderscript.Short3) obj);
            return;
        }
        if (obj instanceof android.renderscript.Short4) {
            addI16((android.renderscript.Short4) obj);
            return;
        }
        if (obj instanceof android.renderscript.Int2) {
            addI32((android.renderscript.Int2) obj);
            return;
        }
        if (obj instanceof android.renderscript.Int3) {
            addI32((android.renderscript.Int3) obj);
            return;
        }
        if (obj instanceof android.renderscript.Int4) {
            addI32((android.renderscript.Int4) obj);
            return;
        }
        if (obj instanceof android.renderscript.Long2) {
            addI64((android.renderscript.Long2) obj);
            return;
        }
        if (obj instanceof android.renderscript.Long3) {
            addI64((android.renderscript.Long3) obj);
            return;
        }
        if (obj instanceof android.renderscript.Long4) {
            addI64((android.renderscript.Long4) obj);
            return;
        }
        if (obj instanceof android.renderscript.Float2) {
            addF32((android.renderscript.Float2) obj);
            return;
        }
        if (obj instanceof android.renderscript.Float3) {
            addF32((android.renderscript.Float3) obj);
            return;
        }
        if (obj instanceof android.renderscript.Float4) {
            addF32((android.renderscript.Float4) obj);
            return;
        }
        if (obj instanceof android.renderscript.Double2) {
            addF64((android.renderscript.Double2) obj);
            return;
        }
        if (obj instanceof android.renderscript.Double3) {
            addF64((android.renderscript.Double3) obj);
            return;
        }
        if (obj instanceof android.renderscript.Double4) {
            addF64((android.renderscript.Double4) obj);
            return;
        }
        if (obj instanceof android.renderscript.Matrix2f) {
            addMatrix((android.renderscript.Matrix2f) obj);
            return;
        }
        if (obj instanceof android.renderscript.Matrix3f) {
            addMatrix((android.renderscript.Matrix3f) obj);
        } else if (obj instanceof android.renderscript.Matrix4f) {
            addMatrix((android.renderscript.Matrix4f) obj);
        } else if (obj instanceof android.renderscript.BaseObj) {
            addObj((android.renderscript.BaseObj) obj);
        }
    }

    private boolean resize(int i) {
        if (i == this.mLen) {
            return false;
        }
        byte[] bArr = new byte[i];
        java.lang.System.arraycopy(this.mData, 0, bArr, 0, this.mPos);
        this.mData = bArr;
        this.mLen = i;
        return true;
    }

    private void addSafely(java.lang.Object obj) {
        boolean z;
        int i = this.mPos;
        do {
            try {
                add(obj);
                z = false;
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                this.mPos = i;
                resize(this.mLen * 2);
                z = true;
            }
        } while (z);
    }
}
