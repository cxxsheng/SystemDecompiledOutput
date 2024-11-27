package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class ProgramVertexFixedFunction extends android.renderscript.ProgramVertex {
    ProgramVertexFixedFunction(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
    }

    public void bindConstants(android.renderscript.ProgramVertexFixedFunction.Constants constants) {
        this.mRS.validate();
        bindConstants(constants.getAllocation(), 0);
    }

    static class InternalBuilder extends android.renderscript.Program.BaseProgramBuilder {
        public InternalBuilder(android.renderscript.RenderScript renderScript) {
            super(renderScript);
        }

        public android.renderscript.ProgramVertexFixedFunction.InternalBuilder addInput(android.renderscript.Element element) throws java.lang.IllegalStateException {
            if (this.mInputCount >= 8) {
                throw new android.renderscript.RSIllegalArgumentException("Max input count exceeded.");
            }
            if (element.isComplex()) {
                throw new android.renderscript.RSIllegalArgumentException("Complex elements not allowed.");
            }
            android.renderscript.Element[] elementArr = this.mInputs;
            int i = this.mInputCount;
            this.mInputCount = i + 1;
            elementArr[i] = element;
            return this;
        }

        public android.renderscript.ProgramVertexFixedFunction create() {
            this.mRS.validate();
            long[] jArr = new long[(this.mInputCount + this.mOutputCount + this.mConstantCount + this.mTextureCount) * 2];
            java.lang.String[] strArr = new java.lang.String[this.mTextureCount];
            int i = 0;
            for (int i2 = 0; i2 < this.mInputCount; i2++) {
                int i3 = i + 1;
                jArr[i] = android.renderscript.Program.ProgramParam.INPUT.mID;
                i = i3 + 1;
                jArr[i3] = this.mInputs[i2].getID(this.mRS);
            }
            for (int i4 = 0; i4 < this.mOutputCount; i4++) {
                int i5 = i + 1;
                jArr[i] = android.renderscript.Program.ProgramParam.OUTPUT.mID;
                i = i5 + 1;
                jArr[i5] = this.mOutputs[i4].getID(this.mRS);
            }
            for (int i6 = 0; i6 < this.mConstantCount; i6++) {
                int i7 = i + 1;
                jArr[i] = android.renderscript.Program.ProgramParam.CONSTANT.mID;
                i = i7 + 1;
                jArr[i7] = this.mConstants[i6].getID(this.mRS);
            }
            for (int i8 = 0; i8 < this.mTextureCount; i8++) {
                int i9 = i + 1;
                jArr[i] = android.renderscript.Program.ProgramParam.TEXTURE_TYPE.mID;
                i = i9 + 1;
                jArr[i9] = this.mTextureTypes[i8].mID;
                strArr[i8] = this.mTextureNames[i8];
            }
            android.renderscript.ProgramVertexFixedFunction programVertexFixedFunction = new android.renderscript.ProgramVertexFixedFunction(this.mRS.nProgramVertexCreate(this.mShader, strArr, jArr), this.mRS);
            initProgram(programVertexFixedFunction);
            return programVertexFixedFunction;
        }
    }

    public static class Builder {
        android.renderscript.RenderScript mRS;
        java.lang.String mShader;
        boolean mTextureMatrixEnable;

        public Builder(android.renderscript.RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public android.renderscript.ProgramVertexFixedFunction.Builder setTextureMatrixEnable(boolean z) {
            this.mTextureMatrixEnable = z;
            return this;
        }

        static android.renderscript.Type getConstantInputType(android.renderscript.RenderScript renderScript) {
            android.renderscript.Element.Builder builder = new android.renderscript.Element.Builder(renderScript);
            builder.add(android.renderscript.Element.MATRIX4X4(renderScript), "MV");
            builder.add(android.renderscript.Element.MATRIX4X4(renderScript), android.hardware.gnss.GnssSignalType.CODE_TYPE_P);
            builder.add(android.renderscript.Element.MATRIX4X4(renderScript), "TexMatrix");
            builder.add(android.renderscript.Element.MATRIX4X4(renderScript), "MVP");
            android.renderscript.Type.Builder builder2 = new android.renderscript.Type.Builder(renderScript, builder.create());
            builder2.setX(1);
            return builder2.create();
        }

        private void buildShaderString() {
            this.mShader = "//rs_shader_internal\n";
            this.mShader += "varying vec4 varColor;\n";
            this.mShader += "varying vec2 varTex0;\n";
            this.mShader += "void main() {\n";
            this.mShader += "  gl_Position = UNI_MVP * ATTRIB_position;\n";
            this.mShader += "  gl_PointSize = 1.0;\n";
            this.mShader += "  varColor = ATTRIB_color;\n";
            if (this.mTextureMatrixEnable) {
                this.mShader += "  varTex0 = (UNI_TexMatrix * vec4(ATTRIB_texture0, 0.0, 1.0)).xy;\n";
            } else {
                this.mShader += "  varTex0 = ATTRIB_texture0;\n";
            }
            this.mShader += "}\n";
        }

        public android.renderscript.ProgramVertexFixedFunction create() {
            buildShaderString();
            android.renderscript.ProgramVertexFixedFunction.InternalBuilder internalBuilder = new android.renderscript.ProgramVertexFixedFunction.InternalBuilder(this.mRS);
            internalBuilder.setShader(this.mShader);
            internalBuilder.addConstant(getConstantInputType(this.mRS));
            android.renderscript.Element.Builder builder = new android.renderscript.Element.Builder(this.mRS);
            builder.add(android.renderscript.Element.F32_4(this.mRS), "position");
            builder.add(android.renderscript.Element.F32_4(this.mRS), "color");
            builder.add(android.renderscript.Element.F32_3(this.mRS), android.graphics.FontListParser.STYLE_NORMAL);
            builder.add(android.renderscript.Element.F32_2(this.mRS), "texture0");
            internalBuilder.addInput(builder.create());
            return internalBuilder.create();
        }
    }

    public static class Constants {
        static final int MODELVIEW_OFFSET = 0;
        static final int PROJECTION_OFFSET = 16;
        static final int TEXTURE_OFFSET = 32;
        android.renderscript.Allocation mAlloc;
        private android.renderscript.FieldPacker mIOBuffer;
        android.renderscript.Matrix4f mModel;
        android.renderscript.Matrix4f mProjection;
        android.renderscript.Matrix4f mTexture;

        android.renderscript.Allocation getAllocation() {
            return this.mAlloc;
        }

        public Constants(android.renderscript.RenderScript renderScript) {
            android.renderscript.Type constantInputType = android.renderscript.ProgramVertexFixedFunction.Builder.getConstantInputType(renderScript);
            this.mAlloc = android.renderscript.Allocation.createTyped(renderScript, constantInputType);
            this.mIOBuffer = new android.renderscript.FieldPacker(constantInputType.getElement().getBytesSize() * constantInputType.getCount());
            this.mModel = new android.renderscript.Matrix4f();
            this.mProjection = new android.renderscript.Matrix4f();
            this.mTexture = new android.renderscript.Matrix4f();
            setModelview(new android.renderscript.Matrix4f());
            setProjection(new android.renderscript.Matrix4f());
            setTexture(new android.renderscript.Matrix4f());
        }

        public void destroy() {
            this.mAlloc.destroy();
            this.mAlloc = null;
        }

        private void addToBuffer(int i, android.renderscript.Matrix4f matrix4f) {
            this.mIOBuffer.reset(i);
            for (int i2 = 0; i2 < 16; i2++) {
                this.mIOBuffer.addF32(matrix4f.mMat[i2]);
            }
            this.mIOBuffer.reset(this.mIOBuffer.getData().length);
            this.mAlloc.setFromFieldPacker(0, this.mIOBuffer);
        }

        public void setModelview(android.renderscript.Matrix4f matrix4f) {
            this.mModel.load(matrix4f);
            addToBuffer(0, matrix4f);
        }

        public void setProjection(android.renderscript.Matrix4f matrix4f) {
            this.mProjection.load(matrix4f);
            addToBuffer(64, matrix4f);
        }

        public void setTexture(android.renderscript.Matrix4f matrix4f) {
            this.mTexture.load(matrix4f);
            addToBuffer(128, matrix4f);
        }
    }
}
