package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class ProgramFragment extends android.renderscript.Program {
    ProgramFragment(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
    }

    public static class Builder extends android.renderscript.Program.BaseProgramBuilder {
        public Builder(android.renderscript.RenderScript renderScript) {
            super(renderScript);
        }

        public android.renderscript.ProgramFragment create() {
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
            android.renderscript.ProgramFragment programFragment = new android.renderscript.ProgramFragment(this.mRS.nProgramFragmentCreate(this.mShader, strArr, jArr), this.mRS);
            initProgram(programFragment);
            return programFragment;
        }
    }
}
