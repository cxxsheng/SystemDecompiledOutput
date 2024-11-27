package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class ProgramVertex extends android.renderscript.Program {
    ProgramVertex(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
    }

    public int getInputCount() {
        if (this.mInputs != null) {
            return this.mInputs.length;
        }
        return 0;
    }

    public android.renderscript.Element getInput(int i) {
        if (i < 0 || i >= this.mInputs.length) {
            throw new java.lang.IllegalArgumentException("Slot ID out of range.");
        }
        return this.mInputs[i];
    }

    public static class Builder extends android.renderscript.Program.BaseProgramBuilder {
        public Builder(android.renderscript.RenderScript renderScript) {
            super(renderScript);
        }

        public android.renderscript.ProgramVertex.Builder addInput(android.renderscript.Element element) throws java.lang.IllegalStateException {
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

        public android.renderscript.ProgramVertex create() {
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
            android.renderscript.ProgramVertex programVertex = new android.renderscript.ProgramVertex(this.mRS.nProgramVertexCreate(this.mShader, strArr, jArr), this.mRS);
            initProgram(programVertex);
            return programVertex;
        }
    }
}
