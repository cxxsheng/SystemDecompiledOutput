package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class ProgramFragmentFixedFunction extends android.renderscript.ProgramFragment {
    ProgramFragmentFixedFunction(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
    }

    static class InternalBuilder extends android.renderscript.Program.BaseProgramBuilder {
        public InternalBuilder(android.renderscript.RenderScript renderScript) {
            super(renderScript);
        }

        public android.renderscript.ProgramFragmentFixedFunction create() {
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
            android.renderscript.ProgramFragmentFixedFunction programFragmentFixedFunction = new android.renderscript.ProgramFragmentFixedFunction(this.mRS.nProgramFragmentCreate(this.mShader, strArr, jArr), this.mRS);
            initProgram(programFragmentFixedFunction);
            return programFragmentFixedFunction;
        }
    }

    public static class Builder {
        public static final int MAX_TEXTURE = 2;
        int mNumTextures;
        android.renderscript.RenderScript mRS;
        java.lang.String mShader;
        boolean mVaryingColorEnable;
        android.renderscript.ProgramFragmentFixedFunction.Builder.Slot[] mSlots = new android.renderscript.ProgramFragmentFixedFunction.Builder.Slot[2];
        boolean mPointSpriteEnable = false;

        public enum EnvMode {
            REPLACE(1),
            MODULATE(2),
            DECAL(3);

            int mID;

            EnvMode(int i) {
                this.mID = i;
            }
        }

        public enum Format {
            ALPHA(1),
            LUMINANCE_ALPHA(2),
            RGB(3),
            RGBA(4);

            int mID;

            Format(int i) {
                this.mID = i;
            }
        }

        private class Slot {
            android.renderscript.ProgramFragmentFixedFunction.Builder.EnvMode env;
            android.renderscript.ProgramFragmentFixedFunction.Builder.Format format;

            Slot(android.renderscript.ProgramFragmentFixedFunction.Builder.EnvMode envMode, android.renderscript.ProgramFragmentFixedFunction.Builder.Format format) {
                this.env = envMode;
                this.format = format;
            }
        }

        private void buildShaderString() {
            this.mShader = "//rs_shader_internal\n";
            this.mShader += "varying lowp vec4 varColor;\n";
            this.mShader += "varying vec2 varTex0;\n";
            this.mShader += "void main() {\n";
            if (this.mVaryingColorEnable) {
                this.mShader += "  lowp vec4 col = varColor;\n";
            } else {
                this.mShader += "  lowp vec4 col = UNI_Color;\n";
            }
            if (this.mNumTextures != 0) {
                if (this.mPointSpriteEnable) {
                    this.mShader += "  vec2 t0 = gl_PointCoord;\n";
                } else {
                    this.mShader += "  vec2 t0 = varTex0.xy;\n";
                }
            }
            for (int i = 0; i < this.mNumTextures; i++) {
                switch (this.mSlots[i].env) {
                    case REPLACE:
                        switch (this.mSlots[i].format) {
                            case ALPHA:
                                this.mShader += "  col.a = texture2D(UNI_Tex0, t0).a;\n";
                                break;
                            case LUMINANCE_ALPHA:
                                this.mShader += "  col.rgba = texture2D(UNI_Tex0, t0).rgba;\n";
                                break;
                            case RGB:
                                this.mShader += "  col.rgb = texture2D(UNI_Tex0, t0).rgb;\n";
                                break;
                            case RGBA:
                                this.mShader += "  col.rgba = texture2D(UNI_Tex0, t0).rgba;\n";
                                break;
                        }
                    case MODULATE:
                        switch (this.mSlots[i].format) {
                            case ALPHA:
                                this.mShader += "  col.a *= texture2D(UNI_Tex0, t0).a;\n";
                                break;
                            case LUMINANCE_ALPHA:
                                this.mShader += "  col.rgba *= texture2D(UNI_Tex0, t0).rgba;\n";
                                break;
                            case RGB:
                                this.mShader += "  col.rgb *= texture2D(UNI_Tex0, t0).rgb;\n";
                                break;
                            case RGBA:
                                this.mShader += "  col.rgba *= texture2D(UNI_Tex0, t0).rgba;\n";
                                break;
                        }
                    case DECAL:
                        this.mShader += "  col = texture2D(UNI_Tex0, t0);\n";
                        break;
                }
            }
            this.mShader += "  gl_FragColor = col;\n";
            this.mShader += "}\n";
        }

        public Builder(android.renderscript.RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public android.renderscript.ProgramFragmentFixedFunction.Builder setTexture(android.renderscript.ProgramFragmentFixedFunction.Builder.EnvMode envMode, android.renderscript.ProgramFragmentFixedFunction.Builder.Format format, int i) throws java.lang.IllegalArgumentException {
            if (i < 0 || i >= 2) {
                throw new java.lang.IllegalArgumentException("MAX_TEXTURE exceeded.");
            }
            this.mSlots[i] = new android.renderscript.ProgramFragmentFixedFunction.Builder.Slot(envMode, format);
            return this;
        }

        public android.renderscript.ProgramFragmentFixedFunction.Builder setPointSpriteTexCoordinateReplacement(boolean z) {
            this.mPointSpriteEnable = z;
            return this;
        }

        public android.renderscript.ProgramFragmentFixedFunction.Builder setVaryingColor(boolean z) {
            this.mVaryingColorEnable = z;
            return this;
        }

        public android.renderscript.ProgramFragmentFixedFunction create() {
            android.renderscript.Type type;
            android.renderscript.ProgramFragmentFixedFunction.InternalBuilder internalBuilder = new android.renderscript.ProgramFragmentFixedFunction.InternalBuilder(this.mRS);
            this.mNumTextures = 0;
            for (int i = 0; i < 2; i++) {
                if (this.mSlots[i] != null) {
                    this.mNumTextures++;
                }
            }
            buildShaderString();
            internalBuilder.setShader(this.mShader);
            if (this.mVaryingColorEnable) {
                type = null;
            } else {
                android.renderscript.Element.Builder builder = new android.renderscript.Element.Builder(this.mRS);
                builder.add(android.renderscript.Element.F32_4(this.mRS), "Color");
                android.renderscript.Type.Builder builder2 = new android.renderscript.Type.Builder(this.mRS, builder.create());
                builder2.setX(1);
                type = builder2.create();
                internalBuilder.addConstant(type);
            }
            for (int i2 = 0; i2 < this.mNumTextures; i2++) {
                internalBuilder.addTexture(android.renderscript.Program.TextureType.TEXTURE_2D);
            }
            android.renderscript.ProgramFragmentFixedFunction create = internalBuilder.create();
            create.mTextureCount = 2;
            if (!this.mVaryingColorEnable) {
                android.renderscript.Allocation createTyped = android.renderscript.Allocation.createTyped(this.mRS, type);
                android.renderscript.FieldPacker fieldPacker = new android.renderscript.FieldPacker(16);
                fieldPacker.addF32(new android.renderscript.Float4(1.0f, 1.0f, 1.0f, 1.0f));
                createTyped.setFromFieldPacker(0, fieldPacker);
                create.bindConstants(createTyped, 0);
            }
            return create;
        }
    }
}
