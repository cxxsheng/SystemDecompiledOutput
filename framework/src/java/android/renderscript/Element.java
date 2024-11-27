package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Element extends android.renderscript.BaseObj {
    int[] mArraySizes;
    java.lang.String[] mElementNames;
    android.renderscript.Element[] mElements;
    android.renderscript.Element.DataKind mKind;
    boolean mNormalized;
    int[] mOffsetInBytes;
    int mSize;
    android.renderscript.Element.DataType mType;
    int mVectorSize;
    int[] mVisibleElementMap;

    private void updateVisibleSubElements() {
        if (this.mElements == null) {
            return;
        }
        int length = this.mElementNames.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (this.mElementNames[i2].charAt(0) != '#') {
                i++;
            }
        }
        this.mVisibleElementMap = new int[i];
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            if (this.mElementNames[i4].charAt(0) != '#') {
                this.mVisibleElementMap[i3] = i4;
                i3++;
            }
        }
    }

    public int getBytesSize() {
        return this.mSize;
    }

    public int getVectorSize() {
        return this.mVectorSize;
    }

    public enum DataType {
        NONE(0, 0),
        FLOAT_16(1, 2),
        FLOAT_32(2, 4),
        FLOAT_64(3, 8),
        SIGNED_8(4, 1),
        SIGNED_16(5, 2),
        SIGNED_32(6, 4),
        SIGNED_64(7, 8),
        UNSIGNED_8(8, 1),
        UNSIGNED_16(9, 2),
        UNSIGNED_32(10, 4),
        UNSIGNED_64(11, 8),
        BOOLEAN(12, 1),
        UNSIGNED_5_6_5(13, 2),
        UNSIGNED_5_5_5_1(14, 2),
        UNSIGNED_4_4_4_4(15, 2),
        MATRIX_4X4(16, 64),
        MATRIX_3X3(17, 36),
        MATRIX_2X2(18, 16),
        RS_ELEMENT(1000),
        RS_TYPE(1001),
        RS_ALLOCATION(1002),
        RS_SAMPLER(1003),
        RS_SCRIPT(1004),
        RS_MESH(1005),
        RS_PROGRAM_FRAGMENT(1006),
        RS_PROGRAM_VERTEX(1007),
        RS_PROGRAM_RASTER(1008),
        RS_PROGRAM_STORE(1009),
        RS_FONT(1010);

        int mID;
        int mSize;

        DataType(int i, int i2) {
            this.mID = i;
            this.mSize = i2;
        }

        DataType(int i) {
            this.mID = i;
            this.mSize = 4;
            if (android.renderscript.RenderScript.sPointerSize == 8) {
                this.mSize = 32;
            }
        }
    }

    public enum DataKind {
        USER(0),
        PIXEL_L(7),
        PIXEL_A(8),
        PIXEL_LA(9),
        PIXEL_RGB(10),
        PIXEL_RGBA(11),
        PIXEL_DEPTH(12),
        PIXEL_YUV(13);

        int mID;

        DataKind(int i) {
            this.mID = i;
        }
    }

    public boolean isComplex() {
        if (this.mElements == null) {
            return false;
        }
        for (int i = 0; i < this.mElements.length; i++) {
            if (this.mElements[i].mElements != null) {
                return true;
            }
        }
        return false;
    }

    public int getSubElementCount() {
        if (this.mVisibleElementMap == null) {
            return 0;
        }
        return this.mVisibleElementMap.length;
    }

    public android.renderscript.Element getSubElement(int i) {
        if (this.mVisibleElementMap == null) {
            throw new android.renderscript.RSIllegalArgumentException("Element contains no sub-elements");
        }
        if (i < 0 || i >= this.mVisibleElementMap.length) {
            throw new android.renderscript.RSIllegalArgumentException("Illegal sub-element index");
        }
        return this.mElements[this.mVisibleElementMap[i]];
    }

    public java.lang.String getSubElementName(int i) {
        if (this.mVisibleElementMap == null) {
            throw new android.renderscript.RSIllegalArgumentException("Element contains no sub-elements");
        }
        if (i < 0 || i >= this.mVisibleElementMap.length) {
            throw new android.renderscript.RSIllegalArgumentException("Illegal sub-element index");
        }
        return this.mElementNames[this.mVisibleElementMap[i]];
    }

    public int getSubElementArraySize(int i) {
        if (this.mVisibleElementMap == null) {
            throw new android.renderscript.RSIllegalArgumentException("Element contains no sub-elements");
        }
        if (i < 0 || i >= this.mVisibleElementMap.length) {
            throw new android.renderscript.RSIllegalArgumentException("Illegal sub-element index");
        }
        return this.mArraySizes[this.mVisibleElementMap[i]];
    }

    public int getSubElementOffsetBytes(int i) {
        if (this.mVisibleElementMap == null) {
            throw new android.renderscript.RSIllegalArgumentException("Element contains no sub-elements");
        }
        if (i < 0 || i >= this.mVisibleElementMap.length) {
            throw new android.renderscript.RSIllegalArgumentException("Illegal sub-element index");
        }
        return this.mOffsetInBytes[this.mVisibleElementMap[i]];
    }

    public android.renderscript.Element.DataType getDataType() {
        return this.mType;
    }

    public android.renderscript.Element.DataKind getDataKind() {
        return this.mKind;
    }

    public static android.renderscript.Element BOOLEAN(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_BOOLEAN == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_BOOLEAN == null) {
                    renderScript.mElement_BOOLEAN = createUser(renderScript, android.renderscript.Element.DataType.BOOLEAN);
                }
            }
        }
        return renderScript.mElement_BOOLEAN;
    }

    public static android.renderscript.Element U8(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_U8 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_U8 == null) {
                    renderScript.mElement_U8 = createUser(renderScript, android.renderscript.Element.DataType.UNSIGNED_8);
                }
            }
        }
        return renderScript.mElement_U8;
    }

    public static android.renderscript.Element I8(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_I8 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_I8 == null) {
                    renderScript.mElement_I8 = createUser(renderScript, android.renderscript.Element.DataType.SIGNED_8);
                }
            }
        }
        return renderScript.mElement_I8;
    }

    public static android.renderscript.Element U16(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_U16 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_U16 == null) {
                    renderScript.mElement_U16 = createUser(renderScript, android.renderscript.Element.DataType.UNSIGNED_16);
                }
            }
        }
        return renderScript.mElement_U16;
    }

    public static android.renderscript.Element I16(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_I16 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_I16 == null) {
                    renderScript.mElement_I16 = createUser(renderScript, android.renderscript.Element.DataType.SIGNED_16);
                }
            }
        }
        return renderScript.mElement_I16;
    }

    public static android.renderscript.Element U32(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_U32 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_U32 == null) {
                    renderScript.mElement_U32 = createUser(renderScript, android.renderscript.Element.DataType.UNSIGNED_32);
                }
            }
        }
        return renderScript.mElement_U32;
    }

    public static android.renderscript.Element I32(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_I32 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_I32 == null) {
                    renderScript.mElement_I32 = createUser(renderScript, android.renderscript.Element.DataType.SIGNED_32);
                }
            }
        }
        return renderScript.mElement_I32;
    }

    public static android.renderscript.Element U64(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_U64 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_U64 == null) {
                    renderScript.mElement_U64 = createUser(renderScript, android.renderscript.Element.DataType.UNSIGNED_64);
                }
            }
        }
        return renderScript.mElement_U64;
    }

    public static android.renderscript.Element I64(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_I64 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_I64 == null) {
                    renderScript.mElement_I64 = createUser(renderScript, android.renderscript.Element.DataType.SIGNED_64);
                }
            }
        }
        return renderScript.mElement_I64;
    }

    public static android.renderscript.Element F16(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_F16 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_F16 == null) {
                    renderScript.mElement_F16 = createUser(renderScript, android.renderscript.Element.DataType.FLOAT_16);
                }
            }
        }
        return renderScript.mElement_F16;
    }

    public static android.renderscript.Element F32(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_F32 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_F32 == null) {
                    renderScript.mElement_F32 = createUser(renderScript, android.renderscript.Element.DataType.FLOAT_32);
                }
            }
        }
        return renderScript.mElement_F32;
    }

    public static android.renderscript.Element F64(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_F64 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_F64 == null) {
                    renderScript.mElement_F64 = createUser(renderScript, android.renderscript.Element.DataType.FLOAT_64);
                }
            }
        }
        return renderScript.mElement_F64;
    }

    public static android.renderscript.Element ELEMENT(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_ELEMENT == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_ELEMENT == null) {
                    renderScript.mElement_ELEMENT = createUser(renderScript, android.renderscript.Element.DataType.RS_ELEMENT);
                }
            }
        }
        return renderScript.mElement_ELEMENT;
    }

    public static android.renderscript.Element TYPE(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_TYPE == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_TYPE == null) {
                    renderScript.mElement_TYPE = createUser(renderScript, android.renderscript.Element.DataType.RS_TYPE);
                }
            }
        }
        return renderScript.mElement_TYPE;
    }

    public static android.renderscript.Element ALLOCATION(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_ALLOCATION == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_ALLOCATION == null) {
                    renderScript.mElement_ALLOCATION = createUser(renderScript, android.renderscript.Element.DataType.RS_ALLOCATION);
                }
            }
        }
        return renderScript.mElement_ALLOCATION;
    }

    public static android.renderscript.Element SAMPLER(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_SAMPLER == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_SAMPLER == null) {
                    renderScript.mElement_SAMPLER = createUser(renderScript, android.renderscript.Element.DataType.RS_SAMPLER);
                }
            }
        }
        return renderScript.mElement_SAMPLER;
    }

    public static android.renderscript.Element SCRIPT(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_SCRIPT == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_SCRIPT == null) {
                    renderScript.mElement_SCRIPT = createUser(renderScript, android.renderscript.Element.DataType.RS_SCRIPT);
                }
            }
        }
        return renderScript.mElement_SCRIPT;
    }

    public static android.renderscript.Element MESH(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_MESH == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_MESH == null) {
                    renderScript.mElement_MESH = createUser(renderScript, android.renderscript.Element.DataType.RS_MESH);
                }
            }
        }
        return renderScript.mElement_MESH;
    }

    public static android.renderscript.Element PROGRAM_FRAGMENT(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_PROGRAM_FRAGMENT == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_PROGRAM_FRAGMENT == null) {
                    renderScript.mElement_PROGRAM_FRAGMENT = createUser(renderScript, android.renderscript.Element.DataType.RS_PROGRAM_FRAGMENT);
                }
            }
        }
        return renderScript.mElement_PROGRAM_FRAGMENT;
    }

    public static android.renderscript.Element PROGRAM_VERTEX(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_PROGRAM_VERTEX == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_PROGRAM_VERTEX == null) {
                    renderScript.mElement_PROGRAM_VERTEX = createUser(renderScript, android.renderscript.Element.DataType.RS_PROGRAM_VERTEX);
                }
            }
        }
        return renderScript.mElement_PROGRAM_VERTEX;
    }

    public static android.renderscript.Element PROGRAM_RASTER(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_PROGRAM_RASTER == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_PROGRAM_RASTER == null) {
                    renderScript.mElement_PROGRAM_RASTER = createUser(renderScript, android.renderscript.Element.DataType.RS_PROGRAM_RASTER);
                }
            }
        }
        return renderScript.mElement_PROGRAM_RASTER;
    }

    public static android.renderscript.Element PROGRAM_STORE(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_PROGRAM_STORE == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_PROGRAM_STORE == null) {
                    renderScript.mElement_PROGRAM_STORE = createUser(renderScript, android.renderscript.Element.DataType.RS_PROGRAM_STORE);
                }
            }
        }
        return renderScript.mElement_PROGRAM_STORE;
    }

    public static android.renderscript.Element FONT(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_FONT == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_FONT == null) {
                    renderScript.mElement_FONT = createUser(renderScript, android.renderscript.Element.DataType.RS_FONT);
                }
            }
        }
        return renderScript.mElement_FONT;
    }

    public static android.renderscript.Element A_8(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_A_8 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_A_8 == null) {
                    renderScript.mElement_A_8 = createPixel(renderScript, android.renderscript.Element.DataType.UNSIGNED_8, android.renderscript.Element.DataKind.PIXEL_A);
                }
            }
        }
        return renderScript.mElement_A_8;
    }

    public static android.renderscript.Element RGB_565(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_RGB_565 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_RGB_565 == null) {
                    renderScript.mElement_RGB_565 = createPixel(renderScript, android.renderscript.Element.DataType.UNSIGNED_5_6_5, android.renderscript.Element.DataKind.PIXEL_RGB);
                }
            }
        }
        return renderScript.mElement_RGB_565;
    }

    public static android.renderscript.Element RGB_888(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_RGB_888 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_RGB_888 == null) {
                    renderScript.mElement_RGB_888 = createPixel(renderScript, android.renderscript.Element.DataType.UNSIGNED_8, android.renderscript.Element.DataKind.PIXEL_RGB);
                }
            }
        }
        return renderScript.mElement_RGB_888;
    }

    public static android.renderscript.Element RGBA_5551(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_RGBA_5551 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_RGBA_5551 == null) {
                    renderScript.mElement_RGBA_5551 = createPixel(renderScript, android.renderscript.Element.DataType.UNSIGNED_5_5_5_1, android.renderscript.Element.DataKind.PIXEL_RGBA);
                }
            }
        }
        return renderScript.mElement_RGBA_5551;
    }

    public static android.renderscript.Element RGBA_4444(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_RGBA_4444 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_RGBA_4444 == null) {
                    renderScript.mElement_RGBA_4444 = createPixel(renderScript, android.renderscript.Element.DataType.UNSIGNED_4_4_4_4, android.renderscript.Element.DataKind.PIXEL_RGBA);
                }
            }
        }
        return renderScript.mElement_RGBA_4444;
    }

    public static android.renderscript.Element RGBA_8888(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_RGBA_8888 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_RGBA_8888 == null) {
                    renderScript.mElement_RGBA_8888 = createPixel(renderScript, android.renderscript.Element.DataType.UNSIGNED_8, android.renderscript.Element.DataKind.PIXEL_RGBA);
                }
            }
        }
        return renderScript.mElement_RGBA_8888;
    }

    public static android.renderscript.Element F16_2(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_HALF_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_HALF_2 == null) {
                    renderScript.mElement_HALF_2 = createVector(renderScript, android.renderscript.Element.DataType.FLOAT_16, 2);
                }
            }
        }
        return renderScript.mElement_HALF_2;
    }

    public static android.renderscript.Element F16_3(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_HALF_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_HALF_3 == null) {
                    renderScript.mElement_HALF_3 = createVector(renderScript, android.renderscript.Element.DataType.FLOAT_16, 3);
                }
            }
        }
        return renderScript.mElement_HALF_3;
    }

    public static android.renderscript.Element F16_4(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_HALF_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_HALF_4 == null) {
                    renderScript.mElement_HALF_4 = createVector(renderScript, android.renderscript.Element.DataType.FLOAT_16, 4);
                }
            }
        }
        return renderScript.mElement_HALF_4;
    }

    public static android.renderscript.Element F32_2(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_FLOAT_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_FLOAT_2 == null) {
                    renderScript.mElement_FLOAT_2 = createVector(renderScript, android.renderscript.Element.DataType.FLOAT_32, 2);
                }
            }
        }
        return renderScript.mElement_FLOAT_2;
    }

    public static android.renderscript.Element F32_3(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_FLOAT_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_FLOAT_3 == null) {
                    renderScript.mElement_FLOAT_3 = createVector(renderScript, android.renderscript.Element.DataType.FLOAT_32, 3);
                }
            }
        }
        return renderScript.mElement_FLOAT_3;
    }

    public static android.renderscript.Element F32_4(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_FLOAT_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_FLOAT_4 == null) {
                    renderScript.mElement_FLOAT_4 = createVector(renderScript, android.renderscript.Element.DataType.FLOAT_32, 4);
                }
            }
        }
        return renderScript.mElement_FLOAT_4;
    }

    public static android.renderscript.Element F64_2(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_DOUBLE_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_DOUBLE_2 == null) {
                    renderScript.mElement_DOUBLE_2 = createVector(renderScript, android.renderscript.Element.DataType.FLOAT_64, 2);
                }
            }
        }
        return renderScript.mElement_DOUBLE_2;
    }

    public static android.renderscript.Element F64_3(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_DOUBLE_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_DOUBLE_3 == null) {
                    renderScript.mElement_DOUBLE_3 = createVector(renderScript, android.renderscript.Element.DataType.FLOAT_64, 3);
                }
            }
        }
        return renderScript.mElement_DOUBLE_3;
    }

    public static android.renderscript.Element F64_4(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_DOUBLE_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_DOUBLE_4 == null) {
                    renderScript.mElement_DOUBLE_4 = createVector(renderScript, android.renderscript.Element.DataType.FLOAT_64, 4);
                }
            }
        }
        return renderScript.mElement_DOUBLE_4;
    }

    public static android.renderscript.Element U8_2(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_UCHAR_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_UCHAR_2 == null) {
                    renderScript.mElement_UCHAR_2 = createVector(renderScript, android.renderscript.Element.DataType.UNSIGNED_8, 2);
                }
            }
        }
        return renderScript.mElement_UCHAR_2;
    }

    public static android.renderscript.Element U8_3(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_UCHAR_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_UCHAR_3 == null) {
                    renderScript.mElement_UCHAR_3 = createVector(renderScript, android.renderscript.Element.DataType.UNSIGNED_8, 3);
                }
            }
        }
        return renderScript.mElement_UCHAR_3;
    }

    public static android.renderscript.Element U8_4(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_UCHAR_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_UCHAR_4 == null) {
                    renderScript.mElement_UCHAR_4 = createVector(renderScript, android.renderscript.Element.DataType.UNSIGNED_8, 4);
                }
            }
        }
        return renderScript.mElement_UCHAR_4;
    }

    public static android.renderscript.Element I8_2(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_CHAR_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_CHAR_2 == null) {
                    renderScript.mElement_CHAR_2 = createVector(renderScript, android.renderscript.Element.DataType.SIGNED_8, 2);
                }
            }
        }
        return renderScript.mElement_CHAR_2;
    }

    public static android.renderscript.Element I8_3(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_CHAR_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_CHAR_3 == null) {
                    renderScript.mElement_CHAR_3 = createVector(renderScript, android.renderscript.Element.DataType.SIGNED_8, 3);
                }
            }
        }
        return renderScript.mElement_CHAR_3;
    }

    public static android.renderscript.Element I8_4(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_CHAR_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_CHAR_4 == null) {
                    renderScript.mElement_CHAR_4 = createVector(renderScript, android.renderscript.Element.DataType.SIGNED_8, 4);
                }
            }
        }
        return renderScript.mElement_CHAR_4;
    }

    public static android.renderscript.Element U16_2(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_USHORT_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_USHORT_2 == null) {
                    renderScript.mElement_USHORT_2 = createVector(renderScript, android.renderscript.Element.DataType.UNSIGNED_16, 2);
                }
            }
        }
        return renderScript.mElement_USHORT_2;
    }

    public static android.renderscript.Element U16_3(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_USHORT_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_USHORT_3 == null) {
                    renderScript.mElement_USHORT_3 = createVector(renderScript, android.renderscript.Element.DataType.UNSIGNED_16, 3);
                }
            }
        }
        return renderScript.mElement_USHORT_3;
    }

    public static android.renderscript.Element U16_4(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_USHORT_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_USHORT_4 == null) {
                    renderScript.mElement_USHORT_4 = createVector(renderScript, android.renderscript.Element.DataType.UNSIGNED_16, 4);
                }
            }
        }
        return renderScript.mElement_USHORT_4;
    }

    public static android.renderscript.Element I16_2(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_SHORT_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_SHORT_2 == null) {
                    renderScript.mElement_SHORT_2 = createVector(renderScript, android.renderscript.Element.DataType.SIGNED_16, 2);
                }
            }
        }
        return renderScript.mElement_SHORT_2;
    }

    public static android.renderscript.Element I16_3(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_SHORT_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_SHORT_3 == null) {
                    renderScript.mElement_SHORT_3 = createVector(renderScript, android.renderscript.Element.DataType.SIGNED_16, 3);
                }
            }
        }
        return renderScript.mElement_SHORT_3;
    }

    public static android.renderscript.Element I16_4(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_SHORT_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_SHORT_4 == null) {
                    renderScript.mElement_SHORT_4 = createVector(renderScript, android.renderscript.Element.DataType.SIGNED_16, 4);
                }
            }
        }
        return renderScript.mElement_SHORT_4;
    }

    public static android.renderscript.Element U32_2(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_UINT_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_UINT_2 == null) {
                    renderScript.mElement_UINT_2 = createVector(renderScript, android.renderscript.Element.DataType.UNSIGNED_32, 2);
                }
            }
        }
        return renderScript.mElement_UINT_2;
    }

    public static android.renderscript.Element U32_3(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_UINT_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_UINT_3 == null) {
                    renderScript.mElement_UINT_3 = createVector(renderScript, android.renderscript.Element.DataType.UNSIGNED_32, 3);
                }
            }
        }
        return renderScript.mElement_UINT_3;
    }

    public static android.renderscript.Element U32_4(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_UINT_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_UINT_4 == null) {
                    renderScript.mElement_UINT_4 = createVector(renderScript, android.renderscript.Element.DataType.UNSIGNED_32, 4);
                }
            }
        }
        return renderScript.mElement_UINT_4;
    }

    public static android.renderscript.Element I32_2(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_INT_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_INT_2 == null) {
                    renderScript.mElement_INT_2 = createVector(renderScript, android.renderscript.Element.DataType.SIGNED_32, 2);
                }
            }
        }
        return renderScript.mElement_INT_2;
    }

    public static android.renderscript.Element I32_3(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_INT_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_INT_3 == null) {
                    renderScript.mElement_INT_3 = createVector(renderScript, android.renderscript.Element.DataType.SIGNED_32, 3);
                }
            }
        }
        return renderScript.mElement_INT_3;
    }

    public static android.renderscript.Element I32_4(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_INT_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_INT_4 == null) {
                    renderScript.mElement_INT_4 = createVector(renderScript, android.renderscript.Element.DataType.SIGNED_32, 4);
                }
            }
        }
        return renderScript.mElement_INT_4;
    }

    public static android.renderscript.Element U64_2(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_ULONG_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_ULONG_2 == null) {
                    renderScript.mElement_ULONG_2 = createVector(renderScript, android.renderscript.Element.DataType.UNSIGNED_64, 2);
                }
            }
        }
        return renderScript.mElement_ULONG_2;
    }

    public static android.renderscript.Element U64_3(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_ULONG_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_ULONG_3 == null) {
                    renderScript.mElement_ULONG_3 = createVector(renderScript, android.renderscript.Element.DataType.UNSIGNED_64, 3);
                }
            }
        }
        return renderScript.mElement_ULONG_3;
    }

    public static android.renderscript.Element U64_4(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_ULONG_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_ULONG_4 == null) {
                    renderScript.mElement_ULONG_4 = createVector(renderScript, android.renderscript.Element.DataType.UNSIGNED_64, 4);
                }
            }
        }
        return renderScript.mElement_ULONG_4;
    }

    public static android.renderscript.Element I64_2(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_LONG_2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_LONG_2 == null) {
                    renderScript.mElement_LONG_2 = createVector(renderScript, android.renderscript.Element.DataType.SIGNED_64, 2);
                }
            }
        }
        return renderScript.mElement_LONG_2;
    }

    public static android.renderscript.Element I64_3(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_LONG_3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_LONG_3 == null) {
                    renderScript.mElement_LONG_3 = createVector(renderScript, android.renderscript.Element.DataType.SIGNED_64, 3);
                }
            }
        }
        return renderScript.mElement_LONG_3;
    }

    public static android.renderscript.Element I64_4(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_LONG_4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_LONG_4 == null) {
                    renderScript.mElement_LONG_4 = createVector(renderScript, android.renderscript.Element.DataType.SIGNED_64, 4);
                }
            }
        }
        return renderScript.mElement_LONG_4;
    }

    public static android.renderscript.Element YUV(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_YUV == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_YUV == null) {
                    renderScript.mElement_YUV = createPixel(renderScript, android.renderscript.Element.DataType.UNSIGNED_8, android.renderscript.Element.DataKind.PIXEL_YUV);
                }
            }
        }
        return renderScript.mElement_YUV;
    }

    public static android.renderscript.Element MATRIX_4X4(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_MATRIX_4X4 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_MATRIX_4X4 == null) {
                    renderScript.mElement_MATRIX_4X4 = createUser(renderScript, android.renderscript.Element.DataType.MATRIX_4X4);
                }
            }
        }
        return renderScript.mElement_MATRIX_4X4;
    }

    public static android.renderscript.Element MATRIX4X4(android.renderscript.RenderScript renderScript) {
        return MATRIX_4X4(renderScript);
    }

    public static android.renderscript.Element MATRIX_3X3(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_MATRIX_3X3 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_MATRIX_3X3 == null) {
                    renderScript.mElement_MATRIX_3X3 = createUser(renderScript, android.renderscript.Element.DataType.MATRIX_3X3);
                }
            }
        }
        return renderScript.mElement_MATRIX_3X3;
    }

    public static android.renderscript.Element MATRIX_2X2(android.renderscript.RenderScript renderScript) {
        if (renderScript.mElement_MATRIX_2X2 == null) {
            synchronized (renderScript) {
                if (renderScript.mElement_MATRIX_2X2 == null) {
                    renderScript.mElement_MATRIX_2X2 = createUser(renderScript, android.renderscript.Element.DataType.MATRIX_2X2);
                }
            }
        }
        return renderScript.mElement_MATRIX_2X2;
    }

    Element(long j, android.renderscript.RenderScript renderScript, android.renderscript.Element[] elementArr, java.lang.String[] strArr, int[] iArr) {
        super(j, renderScript);
        this.mSize = 0;
        this.mVectorSize = 1;
        this.mElements = elementArr;
        this.mElementNames = strArr;
        this.mArraySizes = iArr;
        this.mType = android.renderscript.Element.DataType.NONE;
        this.mKind = android.renderscript.Element.DataKind.USER;
        this.mOffsetInBytes = new int[this.mElements.length];
        for (int i = 0; i < this.mElements.length; i++) {
            this.mOffsetInBytes[i] = this.mSize;
            this.mSize += this.mElements[i].mSize * this.mArraySizes[i];
        }
        updateVisibleSubElements();
    }

    Element(long j, android.renderscript.RenderScript renderScript, android.renderscript.Element.DataType dataType, android.renderscript.Element.DataKind dataKind, boolean z, int i) {
        super(j, renderScript);
        if (dataType != android.renderscript.Element.DataType.UNSIGNED_5_6_5 && dataType != android.renderscript.Element.DataType.UNSIGNED_4_4_4_4 && dataType != android.renderscript.Element.DataType.UNSIGNED_5_5_5_1) {
            if (i == 3) {
                this.mSize = dataType.mSize * 4;
            } else {
                this.mSize = dataType.mSize * i;
            }
        } else {
            this.mSize = dataType.mSize;
        }
        this.mType = dataType;
        this.mKind = dataKind;
        this.mNormalized = z;
        this.mVectorSize = i;
    }

    Element(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
    }

    @Override // android.renderscript.BaseObj
    void updateFromNative() {
        super.updateFromNative();
        int[] iArr = new int[5];
        this.mRS.nElementGetNativeData(getID(this.mRS), iArr);
        this.mNormalized = iArr[2] == 1;
        this.mVectorSize = iArr[3];
        this.mSize = 0;
        for (android.renderscript.Element.DataType dataType : android.renderscript.Element.DataType.values()) {
            if (dataType.mID == iArr[0]) {
                this.mType = dataType;
                this.mSize = this.mType.mSize * this.mVectorSize;
            }
        }
        for (android.renderscript.Element.DataKind dataKind : android.renderscript.Element.DataKind.values()) {
            if (dataKind.mID == iArr[1]) {
                this.mKind = dataKind;
            }
        }
        int i = iArr[4];
        if (i > 0) {
            this.mElements = new android.renderscript.Element[i];
            this.mElementNames = new java.lang.String[i];
            this.mArraySizes = new int[i];
            this.mOffsetInBytes = new int[i];
            long[] jArr = new long[i];
            this.mRS.nElementGetSubElements(getID(this.mRS), jArr, this.mElementNames, this.mArraySizes);
            for (int i2 = 0; i2 < i; i2++) {
                this.mElements[i2] = new android.renderscript.Element(jArr[i2], this.mRS);
                this.mElements[i2].updateFromNative();
                this.mOffsetInBytes[i2] = this.mSize;
                this.mSize += this.mElements[i2].mSize * this.mArraySizes[i2];
            }
        }
        updateVisibleSubElements();
    }

    static android.renderscript.Element createUser(android.renderscript.RenderScript renderScript, android.renderscript.Element.DataType dataType) {
        android.renderscript.Element.DataKind dataKind = android.renderscript.Element.DataKind.USER;
        return new android.renderscript.Element(renderScript.nElementCreate(dataType.mID, dataKind.mID, false, 1), renderScript, dataType, dataKind, false, 1);
    }

    public static android.renderscript.Element createVector(android.renderscript.RenderScript renderScript, android.renderscript.Element.DataType dataType, int i) {
        if (i < 2 || i > 4) {
            throw new android.renderscript.RSIllegalArgumentException("Vector size out of range 2-4.");
        }
        switch (dataType) {
            case FLOAT_16:
            case FLOAT_32:
            case FLOAT_64:
            case SIGNED_8:
            case SIGNED_16:
            case SIGNED_32:
            case SIGNED_64:
            case UNSIGNED_8:
            case UNSIGNED_16:
            case UNSIGNED_32:
            case UNSIGNED_64:
            case BOOLEAN:
                android.renderscript.Element.DataKind dataKind = android.renderscript.Element.DataKind.USER;
                return new android.renderscript.Element(renderScript.nElementCreate(dataType.mID, dataKind.mID, false, i), renderScript, dataType, dataKind, false, i);
            default:
                throw new android.renderscript.RSIllegalArgumentException("Cannot create vector of non-primitive type.");
        }
    }

    public static android.renderscript.Element createPixel(android.renderscript.RenderScript renderScript, android.renderscript.Element.DataType dataType, android.renderscript.Element.DataKind dataKind) {
        int i;
        if (dataKind != android.renderscript.Element.DataKind.PIXEL_L && dataKind != android.renderscript.Element.DataKind.PIXEL_A && dataKind != android.renderscript.Element.DataKind.PIXEL_LA && dataKind != android.renderscript.Element.DataKind.PIXEL_RGB && dataKind != android.renderscript.Element.DataKind.PIXEL_RGBA && dataKind != android.renderscript.Element.DataKind.PIXEL_DEPTH && dataKind != android.renderscript.Element.DataKind.PIXEL_YUV) {
            throw new android.renderscript.RSIllegalArgumentException("Unsupported DataKind");
        }
        if (dataType != android.renderscript.Element.DataType.UNSIGNED_8 && dataType != android.renderscript.Element.DataType.UNSIGNED_16 && dataType != android.renderscript.Element.DataType.UNSIGNED_5_6_5 && dataType != android.renderscript.Element.DataType.UNSIGNED_4_4_4_4 && dataType != android.renderscript.Element.DataType.UNSIGNED_5_5_5_1) {
            throw new android.renderscript.RSIllegalArgumentException("Unsupported DataType");
        }
        if (dataType == android.renderscript.Element.DataType.UNSIGNED_5_6_5 && dataKind != android.renderscript.Element.DataKind.PIXEL_RGB) {
            throw new android.renderscript.RSIllegalArgumentException("Bad kind and type combo");
        }
        if (dataType == android.renderscript.Element.DataType.UNSIGNED_5_5_5_1 && dataKind != android.renderscript.Element.DataKind.PIXEL_RGBA) {
            throw new android.renderscript.RSIllegalArgumentException("Bad kind and type combo");
        }
        if (dataType == android.renderscript.Element.DataType.UNSIGNED_4_4_4_4 && dataKind != android.renderscript.Element.DataKind.PIXEL_RGBA) {
            throw new android.renderscript.RSIllegalArgumentException("Bad kind and type combo");
        }
        if (dataType == android.renderscript.Element.DataType.UNSIGNED_16 && dataKind != android.renderscript.Element.DataKind.PIXEL_DEPTH) {
            throw new android.renderscript.RSIllegalArgumentException("Bad kind and type combo");
        }
        switch (dataKind) {
            case PIXEL_LA:
                i = 2;
                break;
            case PIXEL_RGB:
                i = 3;
                break;
            case PIXEL_RGBA:
                i = 4;
                break;
            case PIXEL_DEPTH:
                i = 2;
                break;
            default:
                i = 1;
                break;
        }
        return new android.renderscript.Element(renderScript.nElementCreate(dataType.mID, dataKind.mID, true, i), renderScript, dataType, dataKind, true, i);
    }

    public boolean isCompatible(android.renderscript.Element element) {
        if (equals(element)) {
            return true;
        }
        return this.mSize == element.mSize && this.mType != android.renderscript.Element.DataType.NONE && this.mType == element.mType && this.mVectorSize == element.mVectorSize;
    }

    public static class Builder {
        android.renderscript.RenderScript mRS;
        int mSkipPadding;
        int mCount = 0;
        android.renderscript.Element[] mElements = new android.renderscript.Element[8];
        java.lang.String[] mElementNames = new java.lang.String[8];
        int[] mArraySizes = new int[8];

        public Builder(android.renderscript.RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public android.renderscript.Element.Builder add(android.renderscript.Element element, java.lang.String str, int i) {
            if (i < 1) {
                throw new android.renderscript.RSIllegalArgumentException("Array size cannot be less than 1.");
            }
            if (this.mSkipPadding != 0 && str.startsWith("#padding_")) {
                this.mSkipPadding = 0;
                return this;
            }
            if (element.mVectorSize == 3) {
                this.mSkipPadding = 1;
            } else {
                this.mSkipPadding = 0;
            }
            if (this.mCount == this.mElements.length) {
                android.renderscript.Element[] elementArr = new android.renderscript.Element[this.mCount + 8];
                java.lang.String[] strArr = new java.lang.String[this.mCount + 8];
                int[] iArr = new int[this.mCount + 8];
                java.lang.System.arraycopy(this.mElements, 0, elementArr, 0, this.mCount);
                java.lang.System.arraycopy(this.mElementNames, 0, strArr, 0, this.mCount);
                java.lang.System.arraycopy(this.mArraySizes, 0, iArr, 0, this.mCount);
                this.mElements = elementArr;
                this.mElementNames = strArr;
                this.mArraySizes = iArr;
            }
            this.mElements[this.mCount] = element;
            this.mElementNames[this.mCount] = str;
            this.mArraySizes[this.mCount] = i;
            this.mCount++;
            return this;
        }

        public android.renderscript.Element.Builder add(android.renderscript.Element element, java.lang.String str) {
            return add(element, str, 1);
        }

        public android.renderscript.Element create() {
            this.mRS.validate();
            int i = this.mCount;
            android.renderscript.Element[] elementArr = new android.renderscript.Element[i];
            java.lang.String[] strArr = new java.lang.String[this.mCount];
            int[] iArr = new int[this.mCount];
            java.lang.System.arraycopy(this.mElements, 0, elementArr, 0, this.mCount);
            java.lang.System.arraycopy(this.mElementNames, 0, strArr, 0, this.mCount);
            java.lang.System.arraycopy(this.mArraySizes, 0, iArr, 0, this.mCount);
            long[] jArr = new long[i];
            for (int i2 = 0; i2 < i; i2++) {
                jArr[i2] = elementArr[i2].getID(this.mRS);
            }
            return new android.renderscript.Element(this.mRS.nElementCreate2(jArr, strArr, iArr), this.mRS, elementArr, strArr, iArr);
        }
    }
}
