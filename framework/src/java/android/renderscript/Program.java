package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Program extends android.renderscript.BaseObj {
    static final int MAX_CONSTANT = 8;
    static final int MAX_INPUT = 8;
    static final int MAX_OUTPUT = 8;
    static final int MAX_TEXTURE = 8;
    android.renderscript.Type[] mConstants;
    android.renderscript.Element[] mInputs;
    android.renderscript.Element[] mOutputs;
    java.lang.String mShader;
    int mTextureCount;
    java.lang.String[] mTextureNames;
    android.renderscript.Program.TextureType[] mTextures;

    public enum TextureType {
        TEXTURE_2D(0),
        TEXTURE_CUBE(1);

        int mID;

        TextureType(int i) {
            this.mID = i;
        }
    }

    enum ProgramParam {
        INPUT(0),
        OUTPUT(1),
        CONSTANT(2),
        TEXTURE_TYPE(3);

        int mID;

        ProgramParam(int i) {
            this.mID = i;
        }
    }

    Program(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
        this.guard.open("destroy");
    }

    public int getConstantCount() {
        if (this.mConstants != null) {
            return this.mConstants.length;
        }
        return 0;
    }

    public android.renderscript.Type getConstant(int i) {
        if (i < 0 || i >= this.mConstants.length) {
            throw new java.lang.IllegalArgumentException("Slot ID out of range.");
        }
        return this.mConstants[i];
    }

    public int getTextureCount() {
        return this.mTextureCount;
    }

    public android.renderscript.Program.TextureType getTextureType(int i) {
        if (i < 0 || i >= this.mTextureCount) {
            throw new java.lang.IllegalArgumentException("Slot ID out of range.");
        }
        return this.mTextures[i];
    }

    public java.lang.String getTextureName(int i) {
        if (i < 0 || i >= this.mTextureCount) {
            throw new java.lang.IllegalArgumentException("Slot ID out of range.");
        }
        return this.mTextureNames[i];
    }

    public void bindConstants(android.renderscript.Allocation allocation, int i) {
        if (i < 0 || i >= this.mConstants.length) {
            throw new java.lang.IllegalArgumentException("Slot ID out of range.");
        }
        if (allocation != null && allocation.getType().getID(this.mRS) != this.mConstants[i].getID(this.mRS)) {
            throw new java.lang.IllegalArgumentException("Allocation type does not match slot type.");
        }
        this.mRS.nProgramBindConstants(getID(this.mRS), i, allocation != null ? allocation.getID(this.mRS) : 0L);
    }

    public void bindTexture(android.renderscript.Allocation allocation, int i) throws java.lang.IllegalArgumentException {
        this.mRS.validate();
        if (i < 0 || i >= this.mTextureCount) {
            throw new java.lang.IllegalArgumentException("Slot ID out of range.");
        }
        if (allocation != null && allocation.getType().hasFaces() && this.mTextures[i] != android.renderscript.Program.TextureType.TEXTURE_CUBE) {
            throw new java.lang.IllegalArgumentException("Cannot bind cubemap to 2d texture slot");
        }
        this.mRS.nProgramBindTexture(getID(this.mRS), i, allocation != null ? allocation.getID(this.mRS) : 0L);
    }

    public void bindSampler(android.renderscript.Sampler sampler, int i) throws java.lang.IllegalArgumentException {
        this.mRS.validate();
        if (i < 0 || i >= this.mTextureCount) {
            throw new java.lang.IllegalArgumentException("Slot ID out of range.");
        }
        this.mRS.nProgramBindSampler(getID(this.mRS), i, sampler != null ? sampler.getID(this.mRS) : 0L);
    }

    public static class BaseProgramBuilder {
        android.renderscript.RenderScript mRS;
        java.lang.String mShader;
        android.renderscript.Type[] mTextures;
        android.renderscript.Element[] mInputs = new android.renderscript.Element[8];
        android.renderscript.Element[] mOutputs = new android.renderscript.Element[8];
        android.renderscript.Type[] mConstants = new android.renderscript.Type[8];
        int mInputCount = 0;
        int mOutputCount = 0;
        int mConstantCount = 0;
        int mTextureCount = 0;
        android.renderscript.Program.TextureType[] mTextureTypes = new android.renderscript.Program.TextureType[8];
        java.lang.String[] mTextureNames = new java.lang.String[8];

        protected BaseProgramBuilder(android.renderscript.RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public android.renderscript.Program.BaseProgramBuilder setShader(java.lang.String str) {
            this.mShader = str;
            return this;
        }

        public android.renderscript.Program.BaseProgramBuilder setShader(android.content.res.Resources resources, int i) {
            java.io.InputStream openRawResource = resources.openRawResource(i);
            try {
                try {
                    byte[] bArr = new byte[1024];
                    int i2 = 0;
                    while (true) {
                        int length = bArr.length - i2;
                        if (length == 0) {
                            int length2 = bArr.length * 2;
                            byte[] bArr2 = new byte[length2];
                            java.lang.System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                            length = length2 - i2;
                            bArr = bArr2;
                        }
                        int read = openRawResource.read(bArr, i2, length);
                        if (read <= 0) {
                            break;
                        }
                        i2 += read;
                    }
                    try {
                        this.mShader = new java.lang.String(bArr, 0, i2, android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
                    } catch (java.io.UnsupportedEncodingException e) {
                        android.util.Log.e("RenderScript shader creation", "Could not decode shader string");
                    }
                    return this;
                } finally {
                    openRawResource.close();
                }
            } catch (java.io.IOException e2) {
                throw new android.content.res.Resources.NotFoundException();
            }
        }

        public int getCurrentConstantIndex() {
            return this.mConstantCount - 1;
        }

        public int getCurrentTextureIndex() {
            return this.mTextureCount - 1;
        }

        public android.renderscript.Program.BaseProgramBuilder addConstant(android.renderscript.Type type) throws java.lang.IllegalStateException {
            if (this.mConstantCount >= 8) {
                throw new android.renderscript.RSIllegalArgumentException("Max input count exceeded.");
            }
            if (type.getElement().isComplex()) {
                throw new android.renderscript.RSIllegalArgumentException("Complex elements not allowed.");
            }
            this.mConstants[this.mConstantCount] = type;
            this.mConstantCount++;
            return this;
        }

        public android.renderscript.Program.BaseProgramBuilder addTexture(android.renderscript.Program.TextureType textureType) throws java.lang.IllegalArgumentException {
            addTexture(textureType, "Tex" + this.mTextureCount);
            return this;
        }

        public android.renderscript.Program.BaseProgramBuilder addTexture(android.renderscript.Program.TextureType textureType, java.lang.String str) throws java.lang.IllegalArgumentException {
            if (this.mTextureCount >= 8) {
                throw new java.lang.IllegalArgumentException("Max texture count exceeded.");
            }
            this.mTextureTypes[this.mTextureCount] = textureType;
            this.mTextureNames[this.mTextureCount] = str;
            this.mTextureCount++;
            return this;
        }

        protected void initProgram(android.renderscript.Program program) {
            program.mInputs = new android.renderscript.Element[this.mInputCount];
            java.lang.System.arraycopy(this.mInputs, 0, program.mInputs, 0, this.mInputCount);
            program.mOutputs = new android.renderscript.Element[this.mOutputCount];
            java.lang.System.arraycopy(this.mOutputs, 0, program.mOutputs, 0, this.mOutputCount);
            program.mConstants = new android.renderscript.Type[this.mConstantCount];
            java.lang.System.arraycopy(this.mConstants, 0, program.mConstants, 0, this.mConstantCount);
            program.mTextureCount = this.mTextureCount;
            program.mTextures = new android.renderscript.Program.TextureType[this.mTextureCount];
            java.lang.System.arraycopy(this.mTextureTypes, 0, program.mTextures, 0, this.mTextureCount);
            program.mTextureNames = new java.lang.String[this.mTextureCount];
            java.lang.System.arraycopy(this.mTextureNames, 0, program.mTextureNames, 0, this.mTextureCount);
        }
    }
}
