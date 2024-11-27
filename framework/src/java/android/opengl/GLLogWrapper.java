package android.opengl;

/* loaded from: classes3.dex */
class GLLogWrapper extends android.opengl.GLWrapperBase {
    private static final int FORMAT_FIXED = 2;
    private static final int FORMAT_FLOAT = 1;
    private static final int FORMAT_INT = 0;
    private int mArgCount;
    boolean mColorArrayEnabled;
    private android.opengl.GLLogWrapper.PointerInfo mColorPointer;
    private java.io.Writer mLog;
    private boolean mLogArgumentNames;
    boolean mNormalArrayEnabled;
    private android.opengl.GLLogWrapper.PointerInfo mNormalPointer;
    java.lang.StringBuilder mStringBuilder;
    private android.opengl.GLLogWrapper.PointerInfo mTexCoordPointer;
    boolean mTextureCoordArrayEnabled;
    boolean mVertexArrayEnabled;
    private android.opengl.GLLogWrapper.PointerInfo mVertexPointer;

    public GLLogWrapper(javax.microedition.khronos.opengles.GL gl, java.io.Writer writer, boolean z) {
        super(gl);
        this.mColorPointer = new android.opengl.GLLogWrapper.PointerInfo();
        this.mNormalPointer = new android.opengl.GLLogWrapper.PointerInfo();
        this.mTexCoordPointer = new android.opengl.GLLogWrapper.PointerInfo();
        this.mVertexPointer = new android.opengl.GLLogWrapper.PointerInfo();
        this.mLog = writer;
        this.mLogArgumentNames = z;
    }

    private void checkError() {
        int glGetError = this.mgl.glGetError();
        if (glGetError != 0) {
            logLine("glError: " + java.lang.Integer.toString(glGetError));
        }
    }

    private void logLine(java.lang.String str) {
        log(str + '\n');
    }

    private void log(java.lang.String str) {
        try {
            this.mLog.write(str);
        } catch (java.io.IOException e) {
        }
    }

    private void begin(java.lang.String str) {
        log(str + '(');
        this.mArgCount = 0;
    }

    private void arg(java.lang.String str, java.lang.String str2) {
        int i = this.mArgCount;
        this.mArgCount = i + 1;
        if (i > 0) {
            log(", ");
        }
        if (this.mLogArgumentNames) {
            log(str + "=");
        }
        log(str2);
    }

    private void end() {
        log(");\n");
        flush();
    }

    private void flush() {
        try {
            this.mLog.flush();
        } catch (java.io.IOException e) {
            this.mLog = null;
        }
    }

    private void arg(java.lang.String str, boolean z) {
        arg(str, java.lang.Boolean.toString(z));
    }

    private void arg(java.lang.String str, int i) {
        arg(str, java.lang.Integer.toString(i));
    }

    private void arg(java.lang.String str, float f) {
        arg(str, java.lang.Float.toString(f));
    }

    private void returns(java.lang.String str) {
        log(") returns " + str + ";\n");
        flush();
    }

    private void returns(int i) {
        returns(java.lang.Integer.toString(i));
    }

    private void arg(java.lang.String str, int i, int[] iArr, int i2) {
        arg(str, toString(i, 0, iArr, i2));
    }

    private void arg(java.lang.String str, int i, short[] sArr, int i2) {
        arg(str, toString(i, sArr, i2));
    }

    private void arg(java.lang.String str, int i, float[] fArr, int i2) {
        arg(str, toString(i, fArr, i2));
    }

    private void formattedAppend(java.lang.StringBuilder sb, int i, int i2) {
        switch (i2) {
            case 0:
                sb.append(i);
                break;
            case 1:
                sb.append(java.lang.Float.intBitsToFloat(i));
                break;
            case 2:
                sb.append(i / 65536.0f);
                break;
        }
    }

    private java.lang.String toString(int i, int i2, int[] iArr, int i3) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{\n");
        int length = iArr.length;
        for (int i4 = 0; i4 < i; i4++) {
            int i5 = i3 + i4;
            sb.append(" [" + i5 + "] = ");
            if (i5 < 0 || i5 >= length) {
                sb.append("out of bounds");
            } else {
                formattedAppend(sb, iArr[i5], i2);
            }
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private java.lang.String toString(int i, short[] sArr, int i2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{\n");
        int length = sArr.length;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = i2 + i3;
            sb.append(" [" + i4 + "] = ");
            if (i4 < 0 || i4 >= length) {
                sb.append("out of bounds");
            } else {
                sb.append((int) sArr[i4]);
            }
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private java.lang.String toString(int i, float[] fArr, int i2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{\n");
        int length = fArr.length;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = i2 + i3;
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + i4 + "] = ");
            if (i4 < 0 || i4 >= length) {
                sb.append("out of bounds");
            } else {
                sb.append(fArr[i4]);
            }
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private java.lang.String toString(int i, java.nio.FloatBuffer floatBuffer) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{\n");
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(" [" + i2 + "] = " + floatBuffer.get(i2) + '\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private java.lang.String toString(int i, int i2, java.nio.IntBuffer intBuffer) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{\n");
        for (int i3 = 0; i3 < i; i3++) {
            sb.append(" [" + i3 + "] = ");
            formattedAppend(sb, intBuffer.get(i3), i2);
            sb.append('\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private java.lang.String toString(int i, java.nio.ShortBuffer shortBuffer) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("{\n");
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(" [" + i2 + "] = " + ((int) shortBuffer.get(i2)) + '\n');
        }
        sb.append("}");
        return sb.toString();
    }

    private void arg(java.lang.String str, int i, java.nio.FloatBuffer floatBuffer) {
        arg(str, toString(i, floatBuffer));
    }

    private void arg(java.lang.String str, int i, java.nio.IntBuffer intBuffer) {
        arg(str, toString(i, 0, intBuffer));
    }

    private void arg(java.lang.String str, int i, java.nio.ShortBuffer shortBuffer) {
        arg(str, toString(i, shortBuffer));
    }

    private void argPointer(int i, int i2, int i3, java.nio.Buffer buffer) {
        arg("size", i);
        arg("type", getPointerTypeName(i2));
        arg(android.media.MediaFormat.KEY_STRIDE, i3);
        arg("pointer", buffer.toString());
    }

    private static java.lang.String getHex(int i) {
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static java.lang.String getErrorString(int i) {
        switch (i) {
            case 0:
                return "GL_NO_ERROR";
            case 1280:
                return "GL_INVALID_ENUM";
            case 1281:
                return "GL_INVALID_VALUE";
            case 1282:
                return "GL_INVALID_OPERATION";
            case 1283:
                return "GL_STACK_OVERFLOW";
            case 1284:
                return "GL_STACK_UNDERFLOW";
            case 1285:
                return "GL_OUT_OF_MEMORY";
            default:
                return getHex(i);
        }
    }

    private java.lang.String getClearBufferMask(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if ((i & 256) != 0) {
            sb.append("GL_DEPTH_BUFFER_BIT");
            i &= -257;
        }
        if ((i & 1024) != 0) {
            if (sb.length() > 0) {
                sb.append(" | ");
            }
            sb.append("GL_STENCIL_BUFFER_BIT");
            i &= -1025;
        }
        if ((i & 16384) != 0) {
            if (sb.length() > 0) {
                sb.append(" | ");
            }
            sb.append("GL_COLOR_BUFFER_BIT");
            i &= -16385;
        }
        if (i != 0) {
            if (sb.length() > 0) {
                sb.append(" | ");
            }
            sb.append(getHex(i));
        }
        return sb.toString();
    }

    private java.lang.String getFactor(int i) {
        switch (i) {
            case 0:
                return "GL_ZERO";
            case 1:
                return "GL_ONE";
            case 768:
                return "GL_SRC_COLOR";
            case 769:
                return "GL_ONE_MINUS_SRC_COLOR";
            case 770:
                return "GL_SRC_ALPHA";
            case 771:
                return "GL_ONE_MINUS_SRC_ALPHA";
            case 772:
                return "GL_DST_ALPHA";
            case 773:
                return "GL_ONE_MINUS_DST_ALPHA";
            case 774:
                return "GL_DST_COLOR";
            case 775:
                return "GL_ONE_MINUS_DST_COLOR";
            case 776:
                return "GL_SRC_ALPHA_SATURATE";
            default:
                return getHex(i);
        }
    }

    private java.lang.String getShadeModel(int i) {
        switch (i) {
            case 7424:
                return "GL_FLAT";
            case 7425:
                return "GL_SMOOTH";
            default:
                return getHex(i);
        }
    }

    private java.lang.String getTextureTarget(int i) {
        switch (i) {
            case 3553:
                return "GL_TEXTURE_2D";
            default:
                return getHex(i);
        }
    }

    private java.lang.String getTextureEnvTarget(int i) {
        switch (i) {
            case 8960:
                return "GL_TEXTURE_ENV";
            default:
                return getHex(i);
        }
    }

    private java.lang.String getTextureEnvPName(int i) {
        switch (i) {
            case 8704:
                return "GL_TEXTURE_ENV_MODE";
            case 8705:
                return "GL_TEXTURE_ENV_COLOR";
            default:
                return getHex(i);
        }
    }

    private int getTextureEnvParamCount(int i) {
        switch (i) {
            case 8704:
                return 1;
            case 8705:
                return 4;
            default:
                return 0;
        }
    }

    private java.lang.String getTextureEnvParamName(float f) {
        int i = (int) f;
        if (f == i) {
            switch (i) {
                case 260:
                    return "GL_ADD";
                case 3042:
                    return "GL_BLEND";
                case 7681:
                    return "GL_REPLACE";
                case 8448:
                    return "GL_MODULATE";
                case 8449:
                    return "GL_DECAL";
                case 34160:
                    return "GL_COMBINE";
                default:
                    return getHex(i);
            }
        }
        return java.lang.Float.toString(f);
    }

    private java.lang.String getMatrixMode(int i) {
        switch (i) {
            case 5888:
                return "GL_MODELVIEW";
            case 5889:
                return "GL_PROJECTION";
            case 5890:
                return "GL_TEXTURE";
            default:
                return getHex(i);
        }
    }

    private java.lang.String getClientState(int i) {
        switch (i) {
            case 32884:
                return "GL_VERTEX_ARRAY";
            case 32885:
                return "GL_NORMAL_ARRAY";
            case 32886:
                return "GL_COLOR_ARRAY";
            case 32887:
            default:
                return getHex(i);
            case 32888:
                return "GL_TEXTURE_COORD_ARRAY";
        }
    }

    private java.lang.String getCap(int i) {
        switch (i) {
            case 2832:
                return "GL_POINT_SMOOTH";
            case 2848:
                return "GL_LINE_SMOOTH";
            case 2884:
                return "GL_CULL_FACE";
            case 2896:
                return "GL_LIGHTING";
            case 2903:
                return "GL_COLOR_MATERIAL";
            case 2912:
                return "GL_FOG";
            case 2929:
                return "GL_DEPTH_TEST";
            case 2960:
                return "GL_STENCIL_TEST";
            case 2977:
                return "GL_NORMALIZE";
            case 3008:
                return "GL_ALPHA_TEST";
            case 3024:
                return "GL_DITHER";
            case 3042:
                return "GL_BLEND";
            case 3058:
                return "GL_COLOR_LOGIC_OP";
            case 3089:
                return "GL_SCISSOR_TEST";
            case 3553:
                return "GL_TEXTURE_2D";
            case 16384:
                return "GL_LIGHT0";
            case 16385:
                return "GL_LIGHT1";
            case 16386:
                return "GL_LIGHT2";
            case 16387:
                return "GL_LIGHT3";
            case 16388:
                return "GL_LIGHT4";
            case 16389:
                return "GL_LIGHT5";
            case 16390:
                return "GL_LIGHT6";
            case 16391:
                return "GL_LIGHT7";
            case 32826:
                return "GL_RESCALE_NORMAL";
            case 32884:
                return "GL_VERTEX_ARRAY";
            case 32885:
                return "GL_NORMAL_ARRAY";
            case 32886:
                return "GL_COLOR_ARRAY";
            case 32888:
                return "GL_TEXTURE_COORD_ARRAY";
            case 32925:
                return "GL_MULTISAMPLE";
            case 32926:
                return "GL_SAMPLE_ALPHA_TO_COVERAGE";
            case 32927:
                return "GL_SAMPLE_ALPHA_TO_ONE";
            case 32928:
                return "GL_SAMPLE_COVERAGE";
            default:
                return getHex(i);
        }
    }

    private java.lang.String getTexturePName(int i) {
        switch (i) {
            case 10240:
                return "GL_TEXTURE_MAG_FILTER";
            case 10241:
                return "GL_TEXTURE_MIN_FILTER";
            case 10242:
                return "GL_TEXTURE_WRAP_S";
            case 10243:
                return "GL_TEXTURE_WRAP_T";
            case 33169:
                return "GL_GENERATE_MIPMAP";
            case 35741:
                return "GL_TEXTURE_CROP_RECT_OES";
            default:
                return getHex(i);
        }
    }

    private java.lang.String getTextureParamName(float f) {
        int i = (int) f;
        if (f == i) {
            switch (i) {
                case 9728:
                    return "GL_NEAREST";
                case 9729:
                    return "GL_LINEAR";
                case 9984:
                    return "GL_NEAREST_MIPMAP_NEAREST";
                case 9985:
                    return "GL_LINEAR_MIPMAP_NEAREST";
                case 9986:
                    return "GL_NEAREST_MIPMAP_LINEAR";
                case 9987:
                    return "GL_LINEAR_MIPMAP_LINEAR";
                case 10497:
                    return "GL_REPEAT";
                case 33071:
                    return "GL_CLAMP_TO_EDGE";
                default:
                    return getHex(i);
            }
        }
        return java.lang.Float.toString(f);
    }

    private java.lang.String getFogPName(int i) {
        switch (i) {
            case 2914:
                return "GL_FOG_DENSITY";
            case 2915:
                return "GL_FOG_START";
            case 2916:
                return "GL_FOG_END";
            case 2917:
                return "GL_FOG_MODE";
            case 2918:
                return "GL_FOG_COLOR";
            default:
                return getHex(i);
        }
    }

    private int getFogParamCount(int i) {
        switch (i) {
        }
        return 1;
    }

    private java.lang.String getBeginMode(int i) {
        switch (i) {
            case 0:
                return "GL_POINTS";
            case 1:
                return "GL_LINES";
            case 2:
                return "GL_LINE_LOOP";
            case 3:
                return "GL_LINE_STRIP";
            case 4:
                return "GL_TRIANGLES";
            case 5:
                return "GL_TRIANGLE_STRIP";
            case 6:
                return "GL_TRIANGLE_FAN";
            default:
                return getHex(i);
        }
    }

    private java.lang.String getIndexType(int i) {
        switch (i) {
            case 5121:
                return "GL_UNSIGNED_BYTE";
            case 5122:
            default:
                return getHex(i);
            case 5123:
                return "GL_UNSIGNED_SHORT";
        }
    }

    private java.lang.String getIntegerStateName(int i) {
        switch (i) {
            case 2834:
                return "GL_SMOOTH_POINT_SIZE_RANGE";
            case 2850:
                return "GL_SMOOTH_LINE_WIDTH_RANGE";
            case 3377:
                return "GL_MAX_LIGHTS";
            case 3379:
                return "GL_MAX_TEXTURE_SIZE";
            case 3382:
                return "GL_MAX_MODELVIEW_STACK_DEPTH";
            case 3384:
                return "GL_MAX_PROJECTION_STACK_DEPTH";
            case 3385:
                return "GL_MAX_TEXTURE_STACK_DEPTH";
            case 3386:
                return "GL_MAX_VIEWPORT_DIMS";
            case 3408:
                return "GL_SUBPIXEL_BITS";
            case 3410:
                return "GL_RED_BITS";
            case 3411:
                return "GL_GREEN_BITS";
            case 3412:
                return "GL_BLUE_BITS";
            case 3413:
                return "GL_ALPHA_BITS";
            case 3414:
                return "GL_DEPTH_BITS";
            case 3415:
                return "GL_STENCIL_BITS";
            case 33000:
                return "GL_MAX_ELEMENTS_VERTICES";
            case 33001:
                return "GL_MAX_ELEMENTS_INDICES";
            case 33901:
                return "GL_ALIASED_POINT_SIZE_RANGE";
            case 33902:
                return "GL_ALIASED_LINE_WIDTH_RANGE";
            case 34018:
                return "GL_MAX_TEXTURE_UNITS";
            case 34466:
                return "GL_NUM_COMPRESSED_TEXTURE_FORMATS";
            case 34467:
                return "GL_COMPRESSED_TEXTURE_FORMATS";
            case 35213:
                return "GL_MODELVIEW_MATRIX_FLOAT_AS_INT_BITS_OES";
            case 35214:
                return "GL_PROJECTION_MATRIX_FLOAT_AS_INT_BITS_OES";
            case 35215:
                return "GL_TEXTURE_MATRIX_FLOAT_AS_INT_BITS_OES";
            default:
                return getHex(i);
        }
    }

    private int getIntegerStateSize(int i) {
        switch (i) {
            case 2834:
                return 2;
            case 2850:
                return 2;
            case 3377:
                return 1;
            case 3379:
                return 1;
            case 3382:
                return 1;
            case 3384:
                return 1;
            case 3385:
                return 1;
            case 3386:
                return 2;
            case 3408:
                return 1;
            case 3410:
                return 1;
            case 3411:
                return 1;
            case 3412:
                return 1;
            case 3413:
                return 1;
            case 3414:
                return 1;
            case 3415:
                return 1;
            case 33000:
                return 1;
            case 33001:
                return 1;
            case 33901:
                return 2;
            case 33902:
                return 2;
            case 34018:
                return 1;
            case 34466:
                return 1;
            case 34467:
                int[] iArr = new int[1];
                this.mgl.glGetIntegerv(34466, iArr, 0);
                return iArr[0];
            case 35213:
            case 35214:
            case 35215:
                return 16;
            default:
                return 0;
        }
    }

    private int getIntegerStateFormat(int i) {
        switch (i) {
            case 35213:
            case 35214:
            case 35215:
                return 1;
            default:
                return 0;
        }
    }

    private java.lang.String getHintTarget(int i) {
        switch (i) {
            case 3152:
                return "GL_PERSPECTIVE_CORRECTION_HINT";
            case 3153:
                return "GL_POINT_SMOOTH_HINT";
            case 3154:
                return "GL_LINE_SMOOTH_HINT";
            case 3155:
                return "GL_POLYGON_SMOOTH_HINT";
            case 3156:
                return "GL_FOG_HINT";
            case 33170:
                return "GL_GENERATE_MIPMAP_HINT";
            default:
                return getHex(i);
        }
    }

    private java.lang.String getHintMode(int i) {
        switch (i) {
            case 4352:
                return "GL_DONT_CARE";
            case 4353:
                return "GL_FASTEST";
            case 4354:
                return "GL_NICEST";
            default:
                return getHex(i);
        }
    }

    private java.lang.String getFaceName(int i) {
        switch (i) {
            case 1032:
                return "GL_FRONT_AND_BACK";
            default:
                return getHex(i);
        }
    }

    private java.lang.String getMaterialPName(int i) {
        switch (i) {
            case 4608:
                return "GL_AMBIENT";
            case 4609:
                return "GL_DIFFUSE";
            case 4610:
                return "GL_SPECULAR";
            case 5632:
                return "GL_EMISSION";
            case 5633:
                return "GL_SHININESS";
            case 5634:
                return "GL_AMBIENT_AND_DIFFUSE";
            default:
                return getHex(i);
        }
    }

    private int getMaterialParamCount(int i) {
        switch (i) {
            case 4608:
                break;
            case 4609:
                break;
            case 4610:
                break;
            case 5632:
                break;
            case 5633:
                break;
            case 5634:
                break;
        }
        return 4;
    }

    private java.lang.String getLightName(int i) {
        if (i >= 16384 && i <= 16391) {
            return "GL_LIGHT" + java.lang.Integer.toString(i);
        }
        return getHex(i);
    }

    private java.lang.String getLightPName(int i) {
        switch (i) {
            case 4608:
                return "GL_AMBIENT";
            case 4609:
                return "GL_DIFFUSE";
            case 4610:
                return "GL_SPECULAR";
            case 4611:
                return "GL_POSITION";
            case 4612:
                return "GL_SPOT_DIRECTION";
            case 4613:
                return "GL_SPOT_EXPONENT";
            case 4614:
                return "GL_SPOT_CUTOFF";
            case 4615:
                return "GL_CONSTANT_ATTENUATION";
            case 4616:
                return "GL_LINEAR_ATTENUATION";
            case 4617:
                return "GL_QUADRATIC_ATTENUATION";
            default:
                return getHex(i);
        }
    }

    private int getLightParamCount(int i) {
        switch (i) {
        }
        return 1;
    }

    private java.lang.String getLightModelPName(int i) {
        switch (i) {
            case 2898:
                return "GL_LIGHT_MODEL_TWO_SIDE";
            case 2899:
                return "GL_LIGHT_MODEL_AMBIENT";
            default:
                return getHex(i);
        }
    }

    private int getLightModelParamCount(int i) {
        switch (i) {
            case 2898:
                return 1;
            case 2899:
                return 4;
            default:
                return 0;
        }
    }

    private java.lang.String getPointerTypeName(int i) {
        switch (i) {
            case 5120:
                return "GL_BYTE";
            case 5121:
                return "GL_UNSIGNED_BYTE";
            case 5122:
                return "GL_SHORT";
            case 5126:
                return "GL_FLOAT";
            case 5132:
                return "GL_FIXED";
            default:
                return getHex(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.nio.ByteBuffer toByteBuffer(int i, java.nio.Buffer buffer) {
        java.nio.ByteBuffer order;
        int i2 = 0;
        boolean z = i < 0;
        if (buffer instanceof java.nio.ByteBuffer) {
            java.nio.ByteBuffer byteBuffer = (java.nio.ByteBuffer) buffer;
            int position = byteBuffer.position();
            if (z) {
                i = byteBuffer.limit() - position;
            }
            order = java.nio.ByteBuffer.allocate(i).order(byteBuffer.order());
            while (i2 < i) {
                order.put(byteBuffer.get());
                i2++;
            }
            byteBuffer.position(position);
        } else if (buffer instanceof java.nio.CharBuffer) {
            java.nio.CharBuffer charBuffer = (java.nio.CharBuffer) buffer;
            int position2 = charBuffer.position();
            if (z) {
                i = (charBuffer.limit() - position2) * 2;
            }
            order = java.nio.ByteBuffer.allocate(i).order(charBuffer.order());
            java.nio.CharBuffer asCharBuffer = order.asCharBuffer();
            while (i2 < i / 2) {
                asCharBuffer.put(charBuffer.get());
                i2++;
            }
            charBuffer.position(position2);
        } else if (buffer instanceof java.nio.ShortBuffer) {
            java.nio.ShortBuffer shortBuffer = (java.nio.ShortBuffer) buffer;
            int position3 = shortBuffer.position();
            if (z) {
                i = (shortBuffer.limit() - position3) * 2;
            }
            order = java.nio.ByteBuffer.allocate(i).order(shortBuffer.order());
            java.nio.ShortBuffer asShortBuffer = order.asShortBuffer();
            while (i2 < i / 2) {
                asShortBuffer.put(shortBuffer.get());
                i2++;
            }
            shortBuffer.position(position3);
        } else if (buffer instanceof java.nio.IntBuffer) {
            java.nio.IntBuffer intBuffer = (java.nio.IntBuffer) buffer;
            int position4 = intBuffer.position();
            if (z) {
                i = (intBuffer.limit() - position4) * 4;
            }
            order = java.nio.ByteBuffer.allocate(i).order(intBuffer.order());
            java.nio.IntBuffer asIntBuffer = order.asIntBuffer();
            while (i2 < i / 4) {
                asIntBuffer.put(intBuffer.get());
                i2++;
            }
            intBuffer.position(position4);
        } else if (buffer instanceof java.nio.FloatBuffer) {
            java.nio.FloatBuffer floatBuffer = (java.nio.FloatBuffer) buffer;
            int position5 = floatBuffer.position();
            if (z) {
                i = (floatBuffer.limit() - position5) * 4;
            }
            order = java.nio.ByteBuffer.allocate(i).order(floatBuffer.order());
            java.nio.FloatBuffer asFloatBuffer = order.asFloatBuffer();
            while (i2 < i / 4) {
                asFloatBuffer.put(floatBuffer.get());
                i2++;
            }
            floatBuffer.position(position5);
        } else if (buffer instanceof java.nio.DoubleBuffer) {
            java.nio.DoubleBuffer doubleBuffer = (java.nio.DoubleBuffer) buffer;
            int position6 = doubleBuffer.position();
            if (z) {
                i = (doubleBuffer.limit() - position6) * 8;
            }
            order = java.nio.ByteBuffer.allocate(i).order(doubleBuffer.order());
            java.nio.DoubleBuffer asDoubleBuffer = order.asDoubleBuffer();
            while (i2 < i / 8) {
                asDoubleBuffer.put(doubleBuffer.get());
                i2++;
            }
            doubleBuffer.position(position6);
        } else if (buffer instanceof java.nio.LongBuffer) {
            java.nio.LongBuffer longBuffer = (java.nio.LongBuffer) buffer;
            int position7 = longBuffer.position();
            if (z) {
                i = (longBuffer.limit() - position7) * 8;
            }
            order = java.nio.ByteBuffer.allocate(i).order(longBuffer.order());
            java.nio.LongBuffer asLongBuffer = order.asLongBuffer();
            while (i2 < i / 8) {
                asLongBuffer.put(longBuffer.get());
                i2++;
            }
            longBuffer.position(position7);
        } else {
            throw new java.lang.RuntimeException("Unimplemented Buffer subclass.");
        }
        order.rewind();
        order.order(java.nio.ByteOrder.nativeOrder());
        return order;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0042, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private char[] toCharIndices(int i, int i2, java.nio.Buffer buffer) {
        java.nio.CharBuffer asCharBuffer;
        char[] cArr = new char[i];
        switch (i2) {
            case 5121:
                java.nio.ByteBuffer byteBuffer = toByteBuffer(i, buffer);
                byte[] array = byteBuffer.array();
                int arrayOffset = byteBuffer.arrayOffset();
                for (int i3 = 0; i3 < i; i3++) {
                    cArr[i3] = (char) (array[arrayOffset + i3] & 255);
                }
                break;
            case 5123:
                if (buffer instanceof java.nio.CharBuffer) {
                    asCharBuffer = (java.nio.CharBuffer) buffer;
                } else {
                    asCharBuffer = toByteBuffer(i * 2, buffer).asCharBuffer();
                }
                int position = asCharBuffer.position();
                asCharBuffer.position(0);
                asCharBuffer.get(cArr);
                asCharBuffer.position(position);
                break;
        }
    }

    private void doArrayElement(java.lang.StringBuilder sb, boolean z, java.lang.String str, android.opengl.GLLogWrapper.PointerInfo pointerInfo, int i) {
        if (!z) {
            return;
        }
        sb.append(" ");
        sb.append(str + ":{");
        if (pointerInfo == null || pointerInfo.mTempByteBuffer == null) {
            sb.append("undefined }");
            return;
        }
        if (pointerInfo.mStride < 0) {
            sb.append("invalid stride");
            return;
        }
        int stride = pointerInfo.getStride();
        java.nio.ByteBuffer byteBuffer = pointerInfo.mTempByteBuffer;
        int i2 = pointerInfo.mSize;
        int i3 = pointerInfo.mType;
        int sizeof = pointerInfo.sizeof(i3);
        int i4 = stride * i;
        for (int i5 = 0; i5 < i2; i5++) {
            if (i5 > 0) {
                sb.append(", ");
            }
            switch (i3) {
                case 5120:
                    sb.append(java.lang.Integer.toString(byteBuffer.get(i4)));
                    break;
                case 5121:
                    sb.append(java.lang.Integer.toString(byteBuffer.get(i4) & 255));
                    break;
                case 5122:
                    sb.append(java.lang.Integer.toString(byteBuffer.asShortBuffer().get(i4 / 2)));
                    break;
                case 5126:
                    sb.append(java.lang.Float.toString(byteBuffer.asFloatBuffer().get(i4 / 4)));
                    break;
                case 5132:
                    sb.append(java.lang.Integer.toString(byteBuffer.asIntBuffer().get(i4 / 4)));
                    break;
                default:
                    sb.append("?");
                    break;
            }
            i4 += sizeof;
        }
        sb.append("}");
    }

    private void doElement(java.lang.StringBuilder sb, int i, int i2) {
        sb.append(" [" + i + " : " + i2 + "] =");
        doArrayElement(sb, this.mVertexArrayEnabled, "v", this.mVertexPointer, i2);
        doArrayElement(sb, this.mNormalArrayEnabled, "n", this.mNormalPointer, i2);
        doArrayElement(sb, this.mColorArrayEnabled, "c", this.mColorPointer, i2);
        doArrayElement(sb, this.mTextureCoordArrayEnabled, "t", this.mTexCoordPointer, i2);
        sb.append("\n");
    }

    private void bindArrays() {
        if (this.mColorArrayEnabled) {
            this.mColorPointer.bindByteBuffer();
        }
        if (this.mNormalArrayEnabled) {
            this.mNormalPointer.bindByteBuffer();
        }
        if (this.mTextureCoordArrayEnabled) {
            this.mTexCoordPointer.bindByteBuffer();
        }
        if (this.mVertexArrayEnabled) {
            this.mVertexPointer.bindByteBuffer();
        }
    }

    private void unbindArrays() {
        if (this.mColorArrayEnabled) {
            this.mColorPointer.unbindByteBuffer();
        }
        if (this.mNormalArrayEnabled) {
            this.mNormalPointer.unbindByteBuffer();
        }
        if (this.mTextureCoordArrayEnabled) {
            this.mTexCoordPointer.unbindByteBuffer();
        }
        if (this.mVertexArrayEnabled) {
            this.mVertexPointer.unbindByteBuffer();
        }
    }

    private void startLogIndices() {
        this.mStringBuilder = new java.lang.StringBuilder();
        this.mStringBuilder.append("\n");
        bindArrays();
    }

    private void endLogIndices() {
        log(this.mStringBuilder.toString());
        unbindArrays();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glActiveTexture(int i) {
        begin("glActiveTexture");
        arg("texture", i);
        end();
        this.mgl.glActiveTexture(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glAlphaFunc(int i, float f) {
        begin("glAlphaFunc");
        arg("func", i);
        arg("ref", f);
        end();
        this.mgl.glAlphaFunc(i, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glAlphaFuncx(int i, int i2) {
        begin("glAlphaFuncx");
        arg("func", i);
        arg("ref", i2);
        end();
        this.mgl.glAlphaFuncx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBindTexture(int i, int i2) {
        begin("glBindTexture");
        arg("target", getTextureTarget(i));
        arg("texture", i2);
        end();
        this.mgl.glBindTexture(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glBlendFunc(int i, int i2) {
        begin("glBlendFunc");
        arg("sfactor", getFactor(i));
        arg("dfactor", getFactor(i2));
        end();
        this.mgl.glBlendFunc(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClear(int i) {
        begin("glClear");
        arg("mask", getClearBufferMask(i));
        end();
        this.mgl.glClear(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearColor(float f, float f2, float f3, float f4) {
        begin("glClearColor");
        arg("red", f);
        arg("green", f2);
        arg("blue", f3);
        arg("alpha", f4);
        end();
        this.mgl.glClearColor(f, f2, f3, f4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearColorx(int i, int i2, int i3, int i4) {
        begin("glClearColor");
        arg("red", i);
        arg("green", i2);
        arg("blue", i3);
        arg("alpha", i4);
        end();
        this.mgl.glClearColorx(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearDepthf(float f) {
        begin("glClearDepthf");
        arg("depth", f);
        end();
        this.mgl.glClearDepthf(f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearDepthx(int i) {
        begin("glClearDepthx");
        arg("depth", i);
        end();
        this.mgl.glClearDepthx(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClearStencil(int i) {
        begin("glClearStencil");
        arg(android.app.blob.XmlTags.TAG_SESSION, i);
        end();
        this.mgl.glClearStencil(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glClientActiveTexture(int i) {
        begin("glClientActiveTexture");
        arg("texture", i);
        end();
        this.mgl.glClientActiveTexture(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glColor4f(float f, float f2, float f3, float f4) {
        begin("glColor4f");
        arg("red", f);
        arg("green", f2);
        arg("blue", f3);
        arg("alpha", f4);
        end();
        this.mgl.glColor4f(f, f2, f3, f4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glColor4x(int i, int i2, int i3, int i4) {
        begin("glColor4x");
        arg("red", i);
        arg("green", i2);
        arg("blue", i3);
        arg("alpha", i4);
        end();
        this.mgl.glColor4x(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glColorMask(boolean z, boolean z2, boolean z3, boolean z4) {
        begin("glColorMask");
        arg("red", z);
        arg("green", z2);
        arg("blue", z3);
        arg("alpha", z4);
        end();
        this.mgl.glColorMask(z, z2, z3, z4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glColorPointer(int i, int i2, int i3, java.nio.Buffer buffer) {
        begin("glColorPointer");
        argPointer(i, i2, i3, buffer);
        end();
        this.mColorPointer = new android.opengl.GLLogWrapper.PointerInfo(i, i2, i3, buffer);
        this.mgl.glColorPointer(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glCompressedTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, java.nio.Buffer buffer) {
        begin("glCompressedTexImage2D");
        arg("target", getTextureTarget(i));
        arg("level", i2);
        arg("internalformat", i3);
        arg("width", i4);
        arg("height", i5);
        arg("border", i6);
        arg("imageSize", i7);
        arg("data", buffer.toString());
        end();
        this.mgl.glCompressedTexImage2D(i, i2, i3, i4, i5, i6, i7, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glCompressedTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, java.nio.Buffer buffer) {
        begin("glCompressedTexSubImage2D");
        arg("target", getTextureTarget(i));
        arg("level", i2);
        arg("xoffset", i3);
        arg("yoffset", i4);
        arg("width", i5);
        arg("height", i6);
        arg(android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT, i7);
        arg("imageSize", i8);
        arg("data", buffer.toString());
        end();
        this.mgl.glCompressedTexSubImage2D(i, i2, i3, i4, i5, i6, i7, i8, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glCopyTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        begin("glCopyTexImage2D");
        arg("target", getTextureTarget(i));
        arg("level", i2);
        arg("internalformat", i3);
        arg("x", i4);
        arg("y", i5);
        arg("width", i6);
        arg("height", i7);
        arg("border", i8);
        end();
        this.mgl.glCopyTexImage2D(i, i2, i3, i4, i5, i6, i7, i8);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glCopyTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        begin("glCopyTexSubImage2D");
        arg("target", getTextureTarget(i));
        arg("level", i2);
        arg("xoffset", i3);
        arg("yoffset", i4);
        arg("x", i5);
        arg("y", i6);
        arg("width", i7);
        arg("height", i8);
        end();
        this.mgl.glCopyTexSubImage2D(i, i2, i3, i4, i5, i6, i7, i8);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glCullFace(int i) {
        begin("glCullFace");
        arg("mode", i);
        end();
        this.mgl.glCullFace(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDeleteTextures(int i, int[] iArr, int i2) {
        begin("glDeleteTextures");
        arg("n", i);
        arg("textures", i, iArr, i2);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl.glDeleteTextures(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDeleteTextures(int i, java.nio.IntBuffer intBuffer) {
        begin("glDeleteTextures");
        arg("n", i);
        arg("textures", i, intBuffer);
        end();
        this.mgl.glDeleteTextures(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDepthFunc(int i) {
        begin("glDepthFunc");
        arg("func", i);
        end();
        this.mgl.glDepthFunc(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDepthMask(boolean z) {
        begin("glDepthMask");
        arg("flag", z);
        end();
        this.mgl.glDepthMask(z);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDepthRangef(float f, float f2) {
        begin("glDepthRangef");
        arg("near", f);
        arg("far", f2);
        end();
        this.mgl.glDepthRangef(f, f2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDepthRangex(int i, int i2) {
        begin("glDepthRangex");
        arg("near", i);
        arg("far", i2);
        end();
        this.mgl.glDepthRangex(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDisable(int i) {
        begin("glDisable");
        arg("cap", getCap(i));
        end();
        this.mgl.glDisable(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDisableClientState(int i) {
        begin("glDisableClientState");
        arg("array", getClientState(i));
        end();
        switch (i) {
            case 32884:
                this.mVertexArrayEnabled = false;
                break;
            case 32885:
                this.mNormalArrayEnabled = false;
                break;
            case 32886:
                this.mColorArrayEnabled = false;
                break;
            case 32888:
                this.mTextureCoordArrayEnabled = false;
                break;
        }
        this.mgl.glDisableClientState(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDrawArrays(int i, int i2, int i3) {
        begin("glDrawArrays");
        arg("mode", i);
        arg("first", i2);
        arg("count", i3);
        startLogIndices();
        for (int i4 = 0; i4 < i3; i4++) {
            doElement(this.mStringBuilder, i4, i2 + i4);
        }
        endLogIndices();
        end();
        this.mgl.glDrawArrays(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glDrawElements(int i, int i2, int i3, java.nio.Buffer buffer) {
        begin("glDrawElements");
        arg("mode", getBeginMode(i));
        arg("count", i2);
        arg("type", getIndexType(i3));
        char[] charIndices = toCharIndices(i2, i3, buffer);
        int length = charIndices.length;
        startLogIndices();
        for (int i4 = 0; i4 < length; i4++) {
            doElement(this.mStringBuilder, i4, charIndices[i4]);
        }
        endLogIndices();
        end();
        this.mgl.glDrawElements(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11Ext, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glEnable(int i) {
        begin("glEnable");
        arg("cap", getCap(i));
        end();
        this.mgl.glEnable(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11Ext
    public void glEnableClientState(int i) {
        begin("glEnableClientState");
        arg("array", getClientState(i));
        end();
        switch (i) {
            case 32884:
                this.mVertexArrayEnabled = true;
                break;
            case 32885:
                this.mNormalArrayEnabled = true;
                break;
            case 32886:
                this.mColorArrayEnabled = true;
                break;
            case 32888:
                this.mTextureCoordArrayEnabled = true;
                break;
        }
        this.mgl.glEnableClientState(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFinish() {
        begin("glFinish");
        end();
        this.mgl.glFinish();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFlush() {
        begin("glFlush");
        end();
        this.mgl.glFlush();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogf(int i, float f) {
        begin("glFogf");
        arg("pname", i);
        arg("param", f);
        end();
        this.mgl.glFogf(i, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogfv(int i, float[] fArr, int i2) {
        begin("glFogfv");
        arg("pname", getFogPName(i));
        arg("params", getFogParamCount(i), fArr, i2);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl.glFogfv(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogfv(int i, java.nio.FloatBuffer floatBuffer) {
        begin("glFogfv");
        arg("pname", getFogPName(i));
        arg("params", getFogParamCount(i), floatBuffer);
        end();
        this.mgl.glFogfv(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogx(int i, int i2) {
        begin("glFogx");
        arg("pname", getFogPName(i));
        arg("param", i2);
        end();
        this.mgl.glFogx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogxv(int i, int[] iArr, int i2) {
        begin("glFogxv");
        arg("pname", getFogPName(i));
        arg("params", getFogParamCount(i), iArr, i2);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl.glFogxv(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFogxv(int i, java.nio.IntBuffer intBuffer) {
        begin("glFogxv");
        arg("pname", getFogPName(i));
        arg("params", getFogParamCount(i), intBuffer);
        end();
        this.mgl.glFogxv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFrontFace(int i) {
        begin("glFrontFace");
        arg("mode", i);
        end();
        this.mgl.glFrontFace(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFrustumf(float f, float f2, float f3, float f4, float f5, float f6) {
        begin("glFrustumf");
        arg(android.inputmethodservice.navigationbar.NavigationBarInflaterView.LEFT, f);
        arg(android.inputmethodservice.navigationbar.NavigationBarInflaterView.RIGHT, f2);
        arg("bottom", f3);
        arg("top", f4);
        arg("near", f5);
        arg("far", f6);
        end();
        this.mgl.glFrustumf(f, f2, f3, f4, f5, f6);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glFrustumx(int i, int i2, int i3, int i4, int i5, int i6) {
        begin("glFrustumx");
        arg(android.inputmethodservice.navigationbar.NavigationBarInflaterView.LEFT, i);
        arg(android.inputmethodservice.navigationbar.NavigationBarInflaterView.RIGHT, i2);
        arg("bottom", i3);
        arg("top", i4);
        arg("near", i5);
        arg("far", i6);
        end();
        this.mgl.glFrustumx(i, i2, i3, i4, i5, i6);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glGenTextures(int i, int[] iArr, int i2) {
        begin("glGenTextures");
        arg("n", i);
        arg("textures", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        this.mgl.glGenTextures(i, iArr, i2);
        returns(toString(i, 0, iArr, i2));
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glGenTextures(int i, java.nio.IntBuffer intBuffer) {
        begin("glGenTextures");
        arg("n", i);
        arg("textures", intBuffer.toString());
        this.mgl.glGenTextures(i, intBuffer);
        returns(toString(i, 0, intBuffer));
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public int glGetError() {
        begin("glGetError");
        int glGetError = this.mgl.glGetError();
        returns(glGetError);
        return glGetError;
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetIntegerv(int i, int[] iArr, int i2) {
        begin("glGetIntegerv");
        arg("pname", getIntegerStateName(i));
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        this.mgl.glGetIntegerv(i, iArr, i2);
        returns(toString(getIntegerStateSize(i), getIntegerStateFormat(i), iArr, i2));
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetIntegerv(int i, java.nio.IntBuffer intBuffer) {
        begin("glGetIntegerv");
        arg("pname", getIntegerStateName(i));
        arg("params", intBuffer.toString());
        this.mgl.glGetIntegerv(i, intBuffer);
        returns(toString(getIntegerStateSize(i), getIntegerStateFormat(i), intBuffer));
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public java.lang.String glGetString(int i) {
        begin("glGetString");
        arg("name", i);
        java.lang.String glGetString = this.mgl.glGetString(i);
        returns(glGetString);
        checkError();
        return glGetString;
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glHint(int i, int i2) {
        begin("glHint");
        arg("target", getHintTarget(i));
        arg("mode", getHintMode(i2));
        end();
        this.mgl.glHint(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelf(int i, float f) {
        begin("glLightModelf");
        arg("pname", getLightModelPName(i));
        arg("param", f);
        end();
        this.mgl.glLightModelf(i, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelfv(int i, float[] fArr, int i2) {
        begin("glLightModelfv");
        arg("pname", getLightModelPName(i));
        arg("params", getLightModelParamCount(i), fArr, i2);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl.glLightModelfv(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelfv(int i, java.nio.FloatBuffer floatBuffer) {
        begin("glLightModelfv");
        arg("pname", getLightModelPName(i));
        arg("params", getLightModelParamCount(i), floatBuffer);
        end();
        this.mgl.glLightModelfv(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelx(int i, int i2) {
        begin("glLightModelx");
        arg("pname", getLightModelPName(i));
        arg("param", i2);
        end();
        this.mgl.glLightModelx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelxv(int i, int[] iArr, int i2) {
        begin("glLightModelxv");
        arg("pname", getLightModelPName(i));
        arg("params", getLightModelParamCount(i), iArr, i2);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl.glLightModelxv(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightModelxv(int i, java.nio.IntBuffer intBuffer) {
        begin("glLightModelfv");
        arg("pname", getLightModelPName(i));
        arg("params", getLightModelParamCount(i), intBuffer);
        end();
        this.mgl.glLightModelxv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightf(int i, int i2, float f) {
        begin("glLightf");
        arg("light", getLightName(i));
        arg("pname", getLightPName(i2));
        arg("param", f);
        end();
        this.mgl.glLightf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightfv(int i, int i2, float[] fArr, int i3) {
        begin("glLightfv");
        arg("light", getLightName(i));
        arg("pname", getLightPName(i2));
        arg("params", getLightParamCount(i2), fArr, i3);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl.glLightfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightfv(int i, int i2, java.nio.FloatBuffer floatBuffer) {
        begin("glLightfv");
        arg("light", getLightName(i));
        arg("pname", getLightPName(i2));
        arg("params", getLightParamCount(i2), floatBuffer);
        end();
        this.mgl.glLightfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightx(int i, int i2, int i3) {
        begin("glLightx");
        arg("light", getLightName(i));
        arg("pname", getLightPName(i2));
        arg("param", i3);
        end();
        this.mgl.glLightx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightxv(int i, int i2, int[] iArr, int i3) {
        begin("glLightxv");
        arg("light", getLightName(i));
        arg("pname", getLightPName(i2));
        arg("params", getLightParamCount(i2), iArr, i3);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl.glLightxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLightxv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glLightxv");
        arg("light", getLightName(i));
        arg("pname", getLightPName(i2));
        arg("params", getLightParamCount(i2), intBuffer);
        end();
        this.mgl.glLightxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLineWidth(float f) {
        begin("glLineWidth");
        arg("width", f);
        end();
        this.mgl.glLineWidth(f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLineWidthx(int i) {
        begin("glLineWidthx");
        arg("width", i);
        end();
        this.mgl.glLineWidthx(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadIdentity() {
        begin("glLoadIdentity");
        end();
        this.mgl.glLoadIdentity();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadMatrixf(float[] fArr, int i) {
        begin("glLoadMatrixf");
        arg("m", 16, fArr, i);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl.glLoadMatrixf(fArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadMatrixf(java.nio.FloatBuffer floatBuffer) {
        begin("glLoadMatrixf");
        arg("m", 16, floatBuffer);
        end();
        this.mgl.glLoadMatrixf(floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadMatrixx(int[] iArr, int i) {
        begin("glLoadMatrixx");
        arg("m", 16, iArr, i);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl.glLoadMatrixx(iArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLoadMatrixx(java.nio.IntBuffer intBuffer) {
        begin("glLoadMatrixx");
        arg("m", 16, intBuffer);
        end();
        this.mgl.glLoadMatrixx(intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glLogicOp(int i) {
        begin("glLogicOp");
        arg("opcode", i);
        end();
        this.mgl.glLogicOp(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialf(int i, int i2, float f) {
        begin("glMaterialf");
        arg(android.content.Context.FACE_SERVICE, getFaceName(i));
        arg("pname", getMaterialPName(i2));
        arg("param", f);
        end();
        this.mgl.glMaterialf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialfv(int i, int i2, float[] fArr, int i3) {
        begin("glMaterialfv");
        arg(android.content.Context.FACE_SERVICE, getFaceName(i));
        arg("pname", getMaterialPName(i2));
        arg("params", getMaterialParamCount(i2), fArr, i3);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl.glMaterialfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialfv(int i, int i2, java.nio.FloatBuffer floatBuffer) {
        begin("glMaterialfv");
        arg(android.content.Context.FACE_SERVICE, getFaceName(i));
        arg("pname", getMaterialPName(i2));
        arg("params", getMaterialParamCount(i2), floatBuffer);
        end();
        this.mgl.glMaterialfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialx(int i, int i2, int i3) {
        begin("glMaterialx");
        arg(android.content.Context.FACE_SERVICE, getFaceName(i));
        arg("pname", getMaterialPName(i2));
        arg("param", i3);
        end();
        this.mgl.glMaterialx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialxv(int i, int i2, int[] iArr, int i3) {
        begin("glMaterialxv");
        arg(android.content.Context.FACE_SERVICE, getFaceName(i));
        arg("pname", getMaterialPName(i2));
        arg("params", getMaterialParamCount(i2), iArr, i3);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl.glMaterialxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMaterialxv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glMaterialxv");
        arg(android.content.Context.FACE_SERVICE, getFaceName(i));
        arg("pname", getMaterialPName(i2));
        arg("params", getMaterialParamCount(i2), intBuffer);
        end();
        this.mgl.glMaterialxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMatrixMode(int i) {
        begin("glMatrixMode");
        arg("mode", getMatrixMode(i));
        end();
        this.mgl.glMatrixMode(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultMatrixf(float[] fArr, int i) {
        begin("glMultMatrixf");
        arg("m", 16, fArr, i);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl.glMultMatrixf(fArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultMatrixf(java.nio.FloatBuffer floatBuffer) {
        begin("glMultMatrixf");
        arg("m", 16, floatBuffer);
        end();
        this.mgl.glMultMatrixf(floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultMatrixx(int[] iArr, int i) {
        begin("glMultMatrixx");
        arg("m", 16, iArr, i);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl.glMultMatrixx(iArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultMatrixx(java.nio.IntBuffer intBuffer) {
        begin("glMultMatrixx");
        arg("m", 16, intBuffer);
        end();
        this.mgl.glMultMatrixx(intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultiTexCoord4f(int i, float f, float f2, float f3, float f4) {
        begin("glMultiTexCoord4f");
        arg("target", i);
        arg(android.app.blob.XmlTags.TAG_SESSION, f);
        arg("t", f2);
        arg("r", f3);
        arg("q", f4);
        end();
        this.mgl.glMultiTexCoord4f(i, f, f2, f3, f4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glMultiTexCoord4x(int i, int i2, int i3, int i4, int i5) {
        begin("glMultiTexCoord4x");
        arg("target", i);
        arg(android.app.blob.XmlTags.TAG_SESSION, i2);
        arg("t", i3);
        arg("r", i4);
        arg("q", i5);
        end();
        this.mgl.glMultiTexCoord4x(i, i2, i3, i4, i5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glNormal3f(float f, float f2, float f3) {
        begin("glNormal3f");
        arg("nx", f);
        arg("ny", f2);
        arg("nz", f3);
        end();
        this.mgl.glNormal3f(f, f2, f3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glNormal3x(int i, int i2, int i3) {
        begin("glNormal3x");
        arg("nx", i);
        arg("ny", i2);
        arg("nz", i3);
        end();
        this.mgl.glNormal3x(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glNormalPointer(int i, int i2, java.nio.Buffer buffer) {
        begin("glNormalPointer");
        arg("type", i);
        arg(android.media.MediaFormat.KEY_STRIDE, i2);
        arg("pointer", buffer.toString());
        end();
        this.mNormalPointer = new android.opengl.GLLogWrapper.PointerInfo(3, i, i2, buffer);
        this.mgl.glNormalPointer(i, i2, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glOrthof(float f, float f2, float f3, float f4, float f5, float f6) {
        begin("glOrthof");
        arg(android.inputmethodservice.navigationbar.NavigationBarInflaterView.LEFT, f);
        arg(android.inputmethodservice.navigationbar.NavigationBarInflaterView.RIGHT, f2);
        arg("bottom", f3);
        arg("top", f4);
        arg("near", f5);
        arg("far", f6);
        end();
        this.mgl.glOrthof(f, f2, f3, f4, f5, f6);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glOrthox(int i, int i2, int i3, int i4, int i5, int i6) {
        begin("glOrthox");
        arg(android.inputmethodservice.navigationbar.NavigationBarInflaterView.LEFT, i);
        arg(android.inputmethodservice.navigationbar.NavigationBarInflaterView.RIGHT, i2);
        arg("bottom", i3);
        arg("top", i4);
        arg("near", i5);
        arg("far", i6);
        end();
        this.mgl.glOrthox(i, i2, i3, i4, i5, i6);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPixelStorei(int i, int i2) {
        begin("glPixelStorei");
        arg("pname", i);
        arg("param", i2);
        end();
        this.mgl.glPixelStorei(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPointSize(float f) {
        begin("glPointSize");
        arg("size", f);
        end();
        this.mgl.glPointSize(f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPointSizex(int i) {
        begin("glPointSizex");
        arg("size", i);
        end();
        this.mgl.glPointSizex(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPolygonOffset(float f, float f2) {
        begin("glPolygonOffset");
        arg("factor", f);
        arg("units", f2);
        end();
        this.mgl.glPolygonOffset(f, f2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPolygonOffsetx(int i, int i2) {
        begin("glPolygonOffsetx");
        arg("factor", i);
        arg("units", i2);
        end();
        this.mgl.glPolygonOffsetx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPopMatrix() {
        begin("glPopMatrix");
        end();
        this.mgl.glPopMatrix();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glPushMatrix() {
        begin("glPushMatrix");
        end();
        this.mgl.glPushMatrix();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glReadPixels(int i, int i2, int i3, int i4, int i5, int i6, java.nio.Buffer buffer) {
        begin("glReadPixels");
        arg("x", i);
        arg("y", i2);
        arg("width", i3);
        arg("height", i4);
        arg(android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT, i5);
        arg("type", i6);
        arg("pixels", buffer.toString());
        end();
        this.mgl.glReadPixels(i, i2, i3, i4, i5, i6, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glRotatef(float f, float f2, float f3, float f4) {
        begin("glRotatef");
        arg("angle", f);
        arg("x", f2);
        arg("y", f3);
        arg("z", f4);
        end();
        this.mgl.glRotatef(f, f2, f3, f4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glRotatex(int i, int i2, int i3, int i4) {
        begin("glRotatex");
        arg("angle", i);
        arg("x", i2);
        arg("y", i3);
        arg("z", i4);
        end();
        this.mgl.glRotatex(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glSampleCoverage(float f, boolean z) {
        begin("glSampleCoveragex");
        arg("value", f);
        arg("invert", z);
        end();
        this.mgl.glSampleCoverage(f, z);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glSampleCoveragex(int i, boolean z) {
        begin("glSampleCoveragex");
        arg("value", i);
        arg("invert", z);
        end();
        this.mgl.glSampleCoveragex(i, z);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glScalef(float f, float f2, float f3) {
        begin("glScalef");
        arg("x", f);
        arg("y", f2);
        arg("z", f3);
        end();
        this.mgl.glScalef(f, f2, f3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glScalex(int i, int i2, int i3) {
        begin("glScalex");
        arg("x", i);
        arg("y", i2);
        arg("z", i3);
        end();
        this.mgl.glScalex(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glScissor(int i, int i2, int i3, int i4) {
        begin("glScissor");
        arg("x", i);
        arg("y", i2);
        arg("width", i3);
        arg("height", i4);
        end();
        this.mgl.glScissor(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glShadeModel(int i) {
        begin("glShadeModel");
        arg("mode", getShadeModel(i));
        end();
        this.mgl.glShadeModel(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glStencilFunc(int i, int i2, int i3) {
        begin("glStencilFunc");
        arg("func", i);
        arg("ref", i2);
        arg("mask", i3);
        end();
        this.mgl.glStencilFunc(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glStencilMask(int i) {
        begin("glStencilMask");
        arg("mask", i);
        end();
        this.mgl.glStencilMask(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glStencilOp(int i, int i2, int i3) {
        begin("glStencilOp");
        arg("fail", i);
        arg("zfail", i2);
        arg("zpass", i3);
        end();
        this.mgl.glStencilOp(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTexCoordPointer(int i, int i2, int i3, java.nio.Buffer buffer) {
        begin("glTexCoordPointer");
        argPointer(i, i2, i3, buffer);
        end();
        this.mTexCoordPointer = new android.opengl.GLLogWrapper.PointerInfo(i, i2, i3, buffer);
        this.mgl.glTexCoordPointer(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvf(int i, int i2, float f) {
        begin("glTexEnvf");
        arg("target", getTextureEnvTarget(i));
        arg("pname", getTextureEnvPName(i2));
        arg("param", getTextureEnvParamName(f));
        end();
        this.mgl.glTexEnvf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvfv(int i, int i2, float[] fArr, int i3) {
        begin("glTexEnvfv");
        arg("target", getTextureEnvTarget(i));
        arg("pname", getTextureEnvPName(i2));
        arg("params", getTextureEnvParamCount(i2), fArr, i3);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl.glTexEnvfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvfv(int i, int i2, java.nio.FloatBuffer floatBuffer) {
        begin("glTexEnvfv");
        arg("target", getTextureEnvTarget(i));
        arg("pname", getTextureEnvPName(i2));
        arg("params", getTextureEnvParamCount(i2), floatBuffer);
        end();
        this.mgl.glTexEnvfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvx(int i, int i2, int i3) {
        begin("glTexEnvx");
        arg("target", getTextureEnvTarget(i));
        arg("pname", getTextureEnvPName(i2));
        arg("param", i3);
        end();
        this.mgl.glTexEnvx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvxv(int i, int i2, int[] iArr, int i3) {
        begin("glTexEnvxv");
        arg("target", getTextureEnvTarget(i));
        arg("pname", getTextureEnvPName(i2));
        arg("params", getTextureEnvParamCount(i2), iArr, i3);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl.glTexEnvxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexEnvxv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glTexEnvxv");
        arg("target", getTextureEnvTarget(i));
        arg("pname", getTextureEnvPName(i2));
        arg("params", getTextureEnvParamCount(i2), intBuffer);
        end();
        this.mgl.glTexEnvxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, java.nio.Buffer buffer) {
        begin("glTexImage2D");
        arg("target", i);
        arg("level", i2);
        arg("internalformat", i3);
        arg("width", i4);
        arg("height", i5);
        arg("border", i6);
        arg(android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT, i7);
        arg("type", i8);
        arg("pixels", buffer.toString());
        end();
        this.mgl.glTexImage2D(i, i2, i3, i4, i5, i6, i7, i8, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10, javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexParameterf(int i, int i2, float f) {
        begin("glTexParameterf");
        arg("target", getTextureTarget(i));
        arg("pname", getTexturePName(i2));
        arg("param", getTextureParamName(f));
        end();
        this.mgl.glTexParameterf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTexParameterx(int i, int i2, int i3) {
        begin("glTexParameterx");
        arg("target", getTextureTarget(i));
        arg("pname", getTexturePName(i2));
        arg("param", i3);
        end();
        this.mgl.glTexParameterx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameteriv(int i, int i2, int[] iArr, int i3) {
        begin("glTexParameteriv");
        arg("target", getTextureTarget(i));
        arg("pname", getTexturePName(i2));
        arg("params", 4, iArr, i3);
        end();
        this.mgl11.glTexParameteriv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameteriv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glTexParameteriv");
        arg("target", getTextureTarget(i));
        arg("pname", getTexturePName(i2));
        arg("params", 4, intBuffer);
        end();
        this.mgl11.glTexParameteriv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTexSubImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, java.nio.Buffer buffer) {
        begin("glTexSubImage2D");
        arg("target", getTextureTarget(i));
        arg("level", i2);
        arg("xoffset", i3);
        arg("yoffset", i4);
        arg("width", i5);
        arg("height", i6);
        arg(android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT, i7);
        arg("type", i8);
        arg("pixels", buffer.toString());
        end();
        this.mgl.glTexSubImage2D(i, i2, i3, i4, i5, i6, i7, i8, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTranslatef(float f, float f2, float f3) {
        begin("glTranslatef");
        arg("x", f);
        arg("y", f2);
        arg("z", f3);
        end();
        this.mgl.glTranslatef(f, f2, f3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glTranslatex(int i, int i2, int i3) {
        begin("glTranslatex");
        arg("x", i);
        arg("y", i2);
        arg("z", i3);
        end();
        this.mgl.glTranslatex(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glVertexPointer(int i, int i2, int i3, java.nio.Buffer buffer) {
        begin("glVertexPointer");
        argPointer(i, i2, i3, buffer);
        end();
        this.mVertexPointer = new android.opengl.GLLogWrapper.PointerInfo(i, i2, i3, buffer);
        this.mgl.glVertexPointer(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10
    public void glViewport(int i, int i2, int i3, int i4) {
        begin("glViewport");
        arg("x", i);
        arg("y", i2);
        arg("width", i3);
        arg("height", i4);
        end();
        this.mgl.glViewport(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glClipPlanef(int i, float[] fArr, int i2) {
        begin("glClipPlanef");
        arg("plane", i);
        arg("equation", 4, fArr, i2);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glClipPlanef(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glClipPlanef(int i, java.nio.FloatBuffer floatBuffer) {
        begin("glClipPlanef");
        arg("plane", i);
        arg("equation", 4, floatBuffer);
        end();
        this.mgl11.glClipPlanef(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glClipPlanex(int i, int[] iArr, int i2) {
        begin("glClipPlanex");
        arg("plane", i);
        arg("equation", 4, iArr, i2);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glClipPlanex(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glClipPlanex(int i, java.nio.IntBuffer intBuffer) {
        begin("glClipPlanef");
        arg("plane", i);
        arg("equation", 4, intBuffer);
        end();
        this.mgl11.glClipPlanex(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexfOES(float f, float f2, float f3, float f4, float f5) {
        begin("glDrawTexfOES");
        arg("x", f);
        arg("y", f2);
        arg("z", f3);
        arg("width", f4);
        arg("height", f5);
        end();
        this.mgl11Ext.glDrawTexfOES(f, f2, f3, f4, f5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexfvOES(float[] fArr, int i) {
        begin("glDrawTexfvOES");
        arg("coords", 5, fArr, i);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl11Ext.glDrawTexfvOES(fArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexfvOES(java.nio.FloatBuffer floatBuffer) {
        begin("glDrawTexfvOES");
        arg("coords", 5, floatBuffer);
        end();
        this.mgl11Ext.glDrawTexfvOES(floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexiOES(int i, int i2, int i3, int i4, int i5) {
        begin("glDrawTexiOES");
        arg("x", i);
        arg("y", i2);
        arg("z", i3);
        arg("width", i4);
        arg("height", i5);
        end();
        this.mgl11Ext.glDrawTexiOES(i, i2, i3, i4, i5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexivOES(int[] iArr, int i) {
        begin("glDrawTexivOES");
        arg("coords", 5, iArr, i);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl11Ext.glDrawTexivOES(iArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexivOES(java.nio.IntBuffer intBuffer) {
        begin("glDrawTexivOES");
        arg("coords", 5, intBuffer);
        end();
        this.mgl11Ext.glDrawTexivOES(intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexsOES(short s, short s2, short s3, short s4, short s5) {
        begin("glDrawTexsOES");
        arg("x", (int) s);
        arg("y", (int) s2);
        arg("z", (int) s3);
        arg("width", (int) s4);
        arg("height", (int) s5);
        end();
        this.mgl11Ext.glDrawTexsOES(s, s2, s3, s4, s5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexsvOES(short[] sArr, int i) {
        begin("glDrawTexsvOES");
        arg("coords", 5, sArr, i);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl11Ext.glDrawTexsvOES(sArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexsvOES(java.nio.ShortBuffer shortBuffer) {
        begin("glDrawTexsvOES");
        arg("coords", 5, shortBuffer);
        end();
        this.mgl11Ext.glDrawTexsvOES(shortBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexxOES(int i, int i2, int i3, int i4, int i5) {
        begin("glDrawTexxOES");
        arg("x", i);
        arg("y", i2);
        arg("z", i3);
        arg("width", i4);
        arg("height", i5);
        end();
        this.mgl11Ext.glDrawTexxOES(i, i2, i3, i4, i5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexxvOES(int[] iArr, int i) {
        begin("glDrawTexxvOES");
        arg("coords", 5, iArr, i);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i);
        end();
        this.mgl11Ext.glDrawTexxvOES(iArr, i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glDrawTexxvOES(java.nio.IntBuffer intBuffer) {
        begin("glDrawTexxvOES");
        arg("coords", 5, intBuffer);
        end();
        this.mgl11Ext.glDrawTexxvOES(intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL10Ext
    public int glQueryMatrixxOES(int[] iArr, int i, int[] iArr2, int i2) {
        begin("glQueryMatrixxOES");
        arg("mantissa", java.util.Arrays.toString(iArr));
        arg("exponent", java.util.Arrays.toString(iArr2));
        end();
        int glQueryMatrixxOES = this.mgl10Ext.glQueryMatrixxOES(iArr, i, iArr2, i2);
        returns(toString(16, 2, iArr, i));
        returns(toString(16, 0, iArr2, i2));
        checkError();
        return glQueryMatrixxOES;
    }

    @Override // javax.microedition.khronos.opengles.GL10Ext
    public int glQueryMatrixxOES(java.nio.IntBuffer intBuffer, java.nio.IntBuffer intBuffer2) {
        begin("glQueryMatrixxOES");
        arg("mantissa", intBuffer.toString());
        arg("exponent", intBuffer2.toString());
        end();
        int glQueryMatrixxOES = this.mgl10Ext.glQueryMatrixxOES(intBuffer, intBuffer2);
        returns(toString(16, 2, intBuffer));
        returns(toString(16, 0, intBuffer2));
        checkError();
        return glQueryMatrixxOES;
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glBindBuffer(int i, int i2) {
        begin("glBindBuffer");
        arg("target", i);
        arg("buffer", i2);
        end();
        this.mgl11.glBindBuffer(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glBufferData(int i, int i2, java.nio.Buffer buffer, int i3) {
        begin("glBufferData");
        arg("target", i);
        arg("size", i2);
        arg("data", buffer.toString());
        arg("usage", i3);
        end();
        this.mgl11.glBufferData(i, i2, buffer, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glBufferSubData(int i, int i2, int i3, java.nio.Buffer buffer) {
        begin("glBufferSubData");
        arg("target", i);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        arg("size", i3);
        arg("data", buffer.toString());
        end();
        this.mgl11.glBufferSubData(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glColor4ub(byte b, byte b2, byte b3, byte b4) {
        begin("glColor4ub");
        arg("red", (int) b);
        arg("green", (int) b2);
        arg("blue", (int) b3);
        arg("alpha", (int) b4);
        end();
        this.mgl11.glColor4ub(b, b2, b3, b4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glDeleteBuffers(int i, int[] iArr, int i2) {
        begin("glDeleteBuffers");
        arg("n", i);
        arg("buffers", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glDeleteBuffers(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glDeleteBuffers(int i, java.nio.IntBuffer intBuffer) {
        begin("glDeleteBuffers");
        arg("n", i);
        arg("buffers", intBuffer.toString());
        end();
        this.mgl11.glDeleteBuffers(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGenBuffers(int i, int[] iArr, int i2) {
        begin("glGenBuffers");
        arg("n", i);
        arg("buffers", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glGenBuffers(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGenBuffers(int i, java.nio.IntBuffer intBuffer) {
        begin("glGenBuffers");
        arg("n", i);
        arg("buffers", intBuffer.toString());
        end();
        this.mgl11.glGenBuffers(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetBooleanv(int i, boolean[] zArr, int i2) {
        begin("glGetBooleanv");
        arg("pname", i);
        arg("params", java.util.Arrays.toString(zArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glGetBooleanv(i, zArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetBooleanv(int i, java.nio.IntBuffer intBuffer) {
        begin("glGetBooleanv");
        arg("pname", i);
        arg("params", intBuffer.toString());
        end();
        this.mgl11.glGetBooleanv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetBufferParameteriv(int i, int i2, int[] iArr, int i3) {
        begin("glGetBufferParameteriv");
        arg("target", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetBufferParameteriv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetBufferParameteriv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glGetBufferParameteriv");
        arg("target", i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11.glGetBufferParameteriv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetClipPlanef(int i, float[] fArr, int i2) {
        begin("glGetClipPlanef");
        arg("pname", i);
        arg("eqn", java.util.Arrays.toString(fArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glGetClipPlanef(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetClipPlanef(int i, java.nio.FloatBuffer floatBuffer) {
        begin("glGetClipPlanef");
        arg("pname", i);
        arg("eqn", floatBuffer.toString());
        end();
        this.mgl11.glGetClipPlanef(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetClipPlanex(int i, int[] iArr, int i2) {
        begin("glGetClipPlanex");
        arg("pname", i);
        arg("eqn", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glGetClipPlanex(i, iArr, i2);
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetClipPlanex(int i, java.nio.IntBuffer intBuffer) {
        begin("glGetClipPlanex");
        arg("pname", i);
        arg("eqn", intBuffer.toString());
        end();
        this.mgl11.glGetClipPlanex(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetFixedv(int i, int[] iArr, int i2) {
        begin("glGetFixedv");
        arg("pname", i);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glGetFixedv(i, iArr, i2);
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetFixedv(int i, java.nio.IntBuffer intBuffer) {
        begin("glGetFixedv");
        arg("pname", i);
        arg("params", intBuffer.toString());
        end();
        this.mgl11.glGetFixedv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetFloatv(int i, float[] fArr, int i2) {
        begin("glGetFloatv");
        arg("pname", i);
        arg("params", java.util.Arrays.toString(fArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glGetFloatv(i, fArr, i2);
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetFloatv(int i, java.nio.FloatBuffer floatBuffer) {
        begin("glGetFloatv");
        arg("pname", i);
        arg("params", floatBuffer.toString());
        end();
        this.mgl11.glGetFloatv(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetLightfv(int i, int i2, float[] fArr, int i3) {
        begin("glGetLightfv");
        arg("light", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(fArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetLightfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetLightfv(int i, int i2, java.nio.FloatBuffer floatBuffer) {
        begin("glGetLightfv");
        arg("light", i);
        arg("pname", i2);
        arg("params", floatBuffer.toString());
        end();
        this.mgl11.glGetLightfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetLightxv(int i, int i2, int[] iArr, int i3) {
        begin("glGetLightxv");
        arg("light", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetLightxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetLightxv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glGetLightxv");
        arg("light", i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11.glGetLightxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetMaterialfv(int i, int i2, float[] fArr, int i3) {
        begin("glGetMaterialfv");
        arg(android.content.Context.FACE_SERVICE, i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(fArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetMaterialfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetMaterialfv(int i, int i2, java.nio.FloatBuffer floatBuffer) {
        begin("glGetMaterialfv");
        arg(android.content.Context.FACE_SERVICE, i);
        arg("pname", i2);
        arg("params", floatBuffer.toString());
        end();
        this.mgl11.glGetMaterialfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetMaterialxv(int i, int i2, int[] iArr, int i3) {
        begin("glGetMaterialxv");
        arg(android.content.Context.FACE_SERVICE, i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetMaterialxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetMaterialxv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glGetMaterialxv");
        arg(android.content.Context.FACE_SERVICE, i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11.glGetMaterialxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexEnviv(int i, int i2, int[] iArr, int i3) {
        begin("glGetTexEnviv");
        arg("env", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetTexEnviv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexEnviv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glGetTexEnviv");
        arg("env", i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11.glGetTexEnviv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexEnvxv(int i, int i2, int[] iArr, int i3) {
        begin("glGetTexEnviv");
        arg("env", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetTexEnviv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexEnvxv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glGetTexEnviv");
        arg("env", i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11.glGetTexEnvxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameterfv(int i, int i2, float[] fArr, int i3) {
        begin("glGetTexParameterfv");
        arg("target", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(fArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetTexParameterfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameterfv(int i, int i2, java.nio.FloatBuffer floatBuffer) {
        begin("glGetTexParameterfv");
        arg("target", i);
        arg("pname", i2);
        arg("params", floatBuffer.toString());
        end();
        this.mgl11.glGetTexParameterfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameteriv(int i, int i2, int[] iArr, int i3) {
        begin("glGetTexParameteriv");
        arg("target", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetTexEnviv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameteriv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glGetTexParameteriv");
        arg("target", i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11.glGetTexParameteriv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameterxv(int i, int i2, int[] iArr, int i3) {
        begin("glGetTexParameterxv");
        arg("target", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glGetTexParameterxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetTexParameterxv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glGetTexParameterxv");
        arg("target", i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11.glGetTexParameterxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public boolean glIsBuffer(int i) {
        begin("glIsBuffer");
        arg("buffer", i);
        end();
        boolean glIsBuffer = this.mgl11.glIsBuffer(i);
        checkError();
        return glIsBuffer;
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public boolean glIsEnabled(int i) {
        begin("glIsEnabled");
        arg("cap", i);
        end();
        boolean glIsEnabled = this.mgl11.glIsEnabled(i);
        checkError();
        return glIsEnabled;
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public boolean glIsTexture(int i) {
        begin("glIsTexture");
        arg("texture", i);
        end();
        boolean glIsTexture = this.mgl11.glIsTexture(i);
        checkError();
        return glIsTexture;
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterf(int i, float f) {
        begin("glPointParameterf");
        arg("pname", i);
        arg("param", f);
        end();
        this.mgl11.glPointParameterf(i, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterfv(int i, float[] fArr, int i2) {
        begin("glPointParameterfv");
        arg("pname", i);
        arg("params", java.util.Arrays.toString(fArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glPointParameterfv(i, fArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterfv(int i, java.nio.FloatBuffer floatBuffer) {
        begin("glPointParameterfv");
        arg("pname", i);
        arg("params", floatBuffer.toString());
        end();
        this.mgl11.glPointParameterfv(i, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterx(int i, int i2) {
        begin("glPointParameterfv");
        arg("pname", i);
        arg("param", i2);
        end();
        this.mgl11.glPointParameterx(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterxv(int i, int[] iArr, int i2) {
        begin("glPointParameterxv");
        arg("pname", i);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11.glPointParameterxv(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointParameterxv(int i, java.nio.IntBuffer intBuffer) {
        begin("glPointParameterxv");
        arg("pname", i);
        arg("params", intBuffer.toString());
        end();
        this.mgl11.glPointParameterxv(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glPointSizePointerOES(int i, int i2, java.nio.Buffer buffer) {
        begin("glPointSizePointerOES");
        arg("type", i);
        arg(android.media.MediaFormat.KEY_STRIDE, i2);
        arg("params", buffer.toString());
        end();
        this.mgl11.glPointSizePointerOES(i, i2, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexEnvi(int i, int i2, int i3) {
        begin("glTexEnvi");
        arg("target", i);
        arg("pname", i2);
        arg("param", i3);
        end();
        this.mgl11.glTexEnvi(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexEnviv(int i, int i2, int[] iArr, int i3) {
        begin("glTexEnviv");
        arg("target", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glTexEnviv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexEnviv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glTexEnviv");
        arg("target", i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11.glTexEnviv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11, javax.microedition.khronos.opengles.GL11Ext
    public void glTexParameterfv(int i, int i2, float[] fArr, int i3) {
        begin("glTexParameterfv");
        arg("target", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(fArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glTexParameterfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameterfv(int i, int i2, java.nio.FloatBuffer floatBuffer) {
        begin("glTexParameterfv");
        arg("target", i);
        arg("pname", i2);
        arg("params", floatBuffer.toString());
        end();
        this.mgl11.glTexParameterfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameteri(int i, int i2, int i3) {
        begin("glTexParameterxv");
        arg("target", i);
        arg("pname", i2);
        arg("param", i3);
        end();
        this.mgl11.glTexParameteri(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameterxv(int i, int i2, int[] iArr, int i3) {
        begin("glTexParameterxv");
        arg("target", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glTexParameterxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexParameterxv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glTexParameterxv");
        arg("target", i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11.glTexParameterxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glColorPointer(int i, int i2, int i3, int i4) {
        begin("glColorPointer");
        arg("size", i);
        arg("type", i2);
        arg(android.media.MediaFormat.KEY_STRIDE, i3);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11.glColorPointer(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glDrawElements(int i, int i2, int i3, int i4) {
        begin("glDrawElements");
        arg("mode", i);
        arg("count", i2);
        arg("type", i3);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11.glDrawElements(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glGetPointerv(int i, java.nio.Buffer[] bufferArr) {
        begin("glGetPointerv");
        arg("pname", i);
        arg("params", java.util.Arrays.toString(bufferArr));
        end();
        this.mgl11.glGetPointerv(i, bufferArr);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glNormalPointer(int i, int i2, int i3) {
        begin("glNormalPointer");
        arg("type", i);
        arg(android.media.MediaFormat.KEY_STRIDE, i2);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11.glNormalPointer(i, i2, i3);
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glTexCoordPointer(int i, int i2, int i3, int i4) {
        begin("glTexCoordPointer");
        arg("size", i);
        arg("type", i2);
        arg(android.media.MediaFormat.KEY_STRIDE, i3);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11.glTexCoordPointer(i, i2, i3, i4);
    }

    @Override // javax.microedition.khronos.opengles.GL11
    public void glVertexPointer(int i, int i2, int i3, int i4) {
        begin("glVertexPointer");
        arg("size", i);
        arg("type", i2);
        arg(android.media.MediaFormat.KEY_STRIDE, i3);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11.glVertexPointer(i, i2, i3, i4);
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glCurrentPaletteMatrixOES(int i) {
        begin("glCurrentPaletteMatrixOES");
        arg("matrixpaletteindex", i);
        end();
        this.mgl11Ext.glCurrentPaletteMatrixOES(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glLoadPaletteFromModelViewMatrixOES() {
        begin("glLoadPaletteFromModelViewMatrixOES");
        end();
        this.mgl11Ext.glLoadPaletteFromModelViewMatrixOES();
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glMatrixIndexPointerOES(int i, int i2, int i3, java.nio.Buffer buffer) {
        begin("glMatrixIndexPointerOES");
        argPointer(i, i2, i3, buffer);
        end();
        this.mgl11Ext.glMatrixIndexPointerOES(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glMatrixIndexPointerOES(int i, int i2, int i3, int i4) {
        begin("glMatrixIndexPointerOES");
        arg("size", i);
        arg("type", i2);
        arg(android.media.MediaFormat.KEY_STRIDE, i3);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11Ext.glMatrixIndexPointerOES(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glWeightPointerOES(int i, int i2, int i3, java.nio.Buffer buffer) {
        begin("glWeightPointerOES");
        argPointer(i, i2, i3, buffer);
        end();
        this.mgl11Ext.glWeightPointerOES(i, i2, i3, buffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11Ext
    public void glWeightPointerOES(int i, int i2, int i3, int i4) {
        begin("glWeightPointerOES");
        arg("size", i);
        arg("type", i2);
        arg(android.media.MediaFormat.KEY_STRIDE, i3);
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11Ext.glWeightPointerOES(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBindFramebufferOES(int i, int i2) {
        begin("glBindFramebufferOES");
        arg("target", i);
        arg("framebuffer", i2);
        end();
        this.mgl11ExtensionPack.glBindFramebufferOES(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBindRenderbufferOES(int i, int i2) {
        begin("glBindRenderbufferOES");
        arg("target", i);
        arg("renderbuffer", i2);
        end();
        this.mgl11ExtensionPack.glBindRenderbufferOES(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBlendEquation(int i) {
        begin("glBlendEquation");
        arg("mode", i);
        end();
        this.mgl11ExtensionPack.glBlendEquation(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBlendEquationSeparate(int i, int i2) {
        begin("glBlendEquationSeparate");
        arg("modeRGB", i);
        arg("modeAlpha", i2);
        end();
        this.mgl11ExtensionPack.glBlendEquationSeparate(i, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glBlendFuncSeparate(int i, int i2, int i3, int i4) {
        begin("glBlendFuncSeparate");
        arg("srcRGB", i);
        arg("dstRGB", i2);
        arg("srcAlpha", i3);
        arg("dstAlpha", i4);
        end();
        this.mgl11ExtensionPack.glBlendFuncSeparate(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public int glCheckFramebufferStatusOES(int i) {
        begin("glCheckFramebufferStatusOES");
        arg("target", i);
        end();
        int glCheckFramebufferStatusOES = this.mgl11ExtensionPack.glCheckFramebufferStatusOES(i);
        checkError();
        return glCheckFramebufferStatusOES;
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glDeleteFramebuffersOES(int i, int[] iArr, int i2) {
        begin("glDeleteFramebuffersOES");
        arg("n", i);
        arg("framebuffers", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11ExtensionPack.glDeleteFramebuffersOES(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glDeleteFramebuffersOES(int i, java.nio.IntBuffer intBuffer) {
        begin("glDeleteFramebuffersOES");
        arg("n", i);
        arg("framebuffers", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glDeleteFramebuffersOES(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glDeleteRenderbuffersOES(int i, int[] iArr, int i2) {
        begin("glDeleteRenderbuffersOES");
        arg("n", i);
        arg("renderbuffers", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11ExtensionPack.glDeleteRenderbuffersOES(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glDeleteRenderbuffersOES(int i, java.nio.IntBuffer intBuffer) {
        begin("glDeleteRenderbuffersOES");
        arg("n", i);
        arg("renderbuffers", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glDeleteRenderbuffersOES(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glFramebufferRenderbufferOES(int i, int i2, int i3, int i4) {
        begin("glFramebufferRenderbufferOES");
        arg("target", i);
        arg("attachment", i2);
        arg("renderbuffertarget", i3);
        arg("renderbuffer", i4);
        end();
        this.mgl11ExtensionPack.glFramebufferRenderbufferOES(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glFramebufferTexture2DOES(int i, int i2, int i3, int i4, int i5) {
        begin("glFramebufferTexture2DOES");
        arg("target", i);
        arg("attachment", i2);
        arg("textarget", i3);
        arg("texture", i4);
        arg("level", i5);
        end();
        this.mgl11ExtensionPack.glFramebufferTexture2DOES(i, i2, i3, i4, i5);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenerateMipmapOES(int i) {
        begin("glGenerateMipmapOES");
        arg("target", i);
        end();
        this.mgl11ExtensionPack.glGenerateMipmapOES(i);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenFramebuffersOES(int i, int[] iArr, int i2) {
        begin("glGenFramebuffersOES");
        arg("n", i);
        arg("framebuffers", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11ExtensionPack.glGenFramebuffersOES(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenFramebuffersOES(int i, java.nio.IntBuffer intBuffer) {
        begin("glGenFramebuffersOES");
        arg("n", i);
        arg("framebuffers", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGenFramebuffersOES(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenRenderbuffersOES(int i, int[] iArr, int i2) {
        begin("glGenRenderbuffersOES");
        arg("n", i);
        arg("renderbuffers", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i2);
        end();
        this.mgl11ExtensionPack.glGenRenderbuffersOES(i, iArr, i2);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGenRenderbuffersOES(int i, java.nio.IntBuffer intBuffer) {
        begin("glGenRenderbuffersOES");
        arg("n", i);
        arg("renderbuffers", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGenRenderbuffersOES(i, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetFramebufferAttachmentParameterivOES(int i, int i2, int i3, int[] iArr, int i4) {
        begin("glGetFramebufferAttachmentParameterivOES");
        arg("target", i);
        arg("attachment", i2);
        arg("pname", i3);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i4);
        end();
        this.mgl11ExtensionPack.glGetFramebufferAttachmentParameterivOES(i, i2, i3, iArr, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetFramebufferAttachmentParameterivOES(int i, int i2, int i3, java.nio.IntBuffer intBuffer) {
        begin("glGetFramebufferAttachmentParameterivOES");
        arg("target", i);
        arg("attachment", i2);
        arg("pname", i3);
        arg("params", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGetFramebufferAttachmentParameterivOES(i, i2, i3, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetRenderbufferParameterivOES(int i, int i2, int[] iArr, int i3) {
        begin("glGetRenderbufferParameterivOES");
        arg("target", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glGetRenderbufferParameterivOES(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetRenderbufferParameterivOES(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glGetRenderbufferParameterivOES");
        arg("target", i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGetRenderbufferParameterivOES(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGenfv(int i, int i2, float[] fArr, int i3) {
        begin("glGetTexGenfv");
        arg("coord", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(fArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glGetTexGenfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGenfv(int i, int i2, java.nio.FloatBuffer floatBuffer) {
        begin("glGetTexGenfv");
        arg("coord", i);
        arg("pname", i2);
        arg("params", floatBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGetTexGenfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGeniv(int i, int i2, int[] iArr, int i3) {
        begin("glGetTexGeniv");
        arg("coord", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glGetTexGeniv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGeniv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glGetTexGeniv");
        arg("coord", i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGetTexGeniv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGenxv(int i, int i2, int[] iArr, int i3) {
        begin("glGetTexGenxv");
        arg("coord", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glGetTexGenxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glGetTexGenxv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glGetTexGenxv");
        arg("coord", i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glGetTexGenxv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public boolean glIsFramebufferOES(int i) {
        begin("glIsFramebufferOES");
        arg("framebuffer", i);
        end();
        boolean glIsFramebufferOES = this.mgl11ExtensionPack.glIsFramebufferOES(i);
        checkError();
        return glIsFramebufferOES;
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public boolean glIsRenderbufferOES(int i) {
        begin("glIsRenderbufferOES");
        arg("renderbuffer", i);
        end();
        this.mgl11ExtensionPack.glIsRenderbufferOES(i);
        checkError();
        return false;
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glRenderbufferStorageOES(int i, int i2, int i3, int i4) {
        begin("glRenderbufferStorageOES");
        arg("target", i);
        arg("internalformat", i2);
        arg("width", i3);
        arg("height", i4);
        end();
        this.mgl11ExtensionPack.glRenderbufferStorageOES(i, i2, i3, i4);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenf(int i, int i2, float f) {
        begin("glTexGenf");
        arg("coord", i);
        arg("pname", i2);
        arg("param", f);
        end();
        this.mgl11ExtensionPack.glTexGenf(i, i2, f);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenfv(int i, int i2, float[] fArr, int i3) {
        begin("glTexGenfv");
        arg("coord", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(fArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glTexGenfv(i, i2, fArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenfv(int i, int i2, java.nio.FloatBuffer floatBuffer) {
        begin("glTexGenfv");
        arg("coord", i);
        arg("pname", i2);
        arg("params", floatBuffer.toString());
        end();
        this.mgl11ExtensionPack.glTexGenfv(i, i2, floatBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGeni(int i, int i2, int i3) {
        begin("glTexGeni");
        arg("coord", i);
        arg("pname", i2);
        arg("param", i3);
        end();
        this.mgl11ExtensionPack.glTexGeni(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGeniv(int i, int i2, int[] iArr, int i3) {
        begin("glTexGeniv");
        arg("coord", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glTexGeniv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGeniv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glTexGeniv");
        arg("coord", i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glTexGeniv(i, i2, intBuffer);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenx(int i, int i2, int i3) {
        begin("glTexGenx");
        arg("coord", i);
        arg("pname", i2);
        arg("param", i3);
        end();
        this.mgl11ExtensionPack.glTexGenx(i, i2, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenxv(int i, int i2, int[] iArr, int i3) {
        begin("glTexGenxv");
        arg("coord", i);
        arg("pname", i2);
        arg("params", java.util.Arrays.toString(iArr));
        arg(android.provider.CallLog.Calls.OFFSET_PARAM_KEY, i3);
        end();
        this.mgl11ExtensionPack.glTexGenxv(i, i2, iArr, i3);
        checkError();
    }

    @Override // javax.microedition.khronos.opengles.GL11ExtensionPack
    public void glTexGenxv(int i, int i2, java.nio.IntBuffer intBuffer) {
        begin("glTexGenxv");
        arg("coord", i);
        arg("pname", i2);
        arg("params", intBuffer.toString());
        end();
        this.mgl11ExtensionPack.glTexGenxv(i, i2, intBuffer);
        checkError();
    }

    private class PointerInfo {
        public java.nio.Buffer mPointer;
        public int mSize;
        public int mStride;
        public java.nio.ByteBuffer mTempByteBuffer;
        public int mType;

        public PointerInfo() {
        }

        public PointerInfo(int i, int i2, int i3, java.nio.Buffer buffer) {
            this.mSize = i;
            this.mType = i2;
            this.mStride = i3;
            this.mPointer = buffer;
        }

        public int sizeof(int i) {
            switch (i) {
                case 5120:
                    break;
                case 5121:
                    break;
                case 5122:
                    break;
                case 5126:
                    break;
                case 5132:
                    break;
            }
            return 4;
        }

        public int getStride() {
            return this.mStride > 0 ? this.mStride : sizeof(this.mType) * this.mSize;
        }

        public void bindByteBuffer() {
            this.mTempByteBuffer = this.mPointer == null ? null : android.opengl.GLLogWrapper.this.toByteBuffer(-1, this.mPointer);
        }

        public void unbindByteBuffer() {
            this.mTempByteBuffer = null;
        }
    }
}
