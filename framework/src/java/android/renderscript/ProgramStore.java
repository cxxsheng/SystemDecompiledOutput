package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class ProgramStore extends android.renderscript.BaseObj {
    android.renderscript.ProgramStore.BlendDstFunc mBlendDst;
    android.renderscript.ProgramStore.BlendSrcFunc mBlendSrc;
    boolean mColorMaskA;
    boolean mColorMaskB;
    boolean mColorMaskG;
    boolean mColorMaskR;
    android.renderscript.ProgramStore.DepthFunc mDepthFunc;
    boolean mDepthMask;
    boolean mDither;

    public enum DepthFunc {
        ALWAYS(0),
        LESS(1),
        LESS_OR_EQUAL(2),
        GREATER(3),
        GREATER_OR_EQUAL(4),
        EQUAL(5),
        NOT_EQUAL(6);

        int mID;

        DepthFunc(int i) {
            this.mID = i;
        }
    }

    public enum BlendSrcFunc {
        ZERO(0),
        ONE(1),
        DST_COLOR(2),
        ONE_MINUS_DST_COLOR(3),
        SRC_ALPHA(4),
        ONE_MINUS_SRC_ALPHA(5),
        DST_ALPHA(6),
        ONE_MINUS_DST_ALPHA(7),
        SRC_ALPHA_SATURATE(8);

        int mID;

        BlendSrcFunc(int i) {
            this.mID = i;
        }
    }

    public enum BlendDstFunc {
        ZERO(0),
        ONE(1),
        SRC_COLOR(2),
        ONE_MINUS_SRC_COLOR(3),
        SRC_ALPHA(4),
        ONE_MINUS_SRC_ALPHA(5),
        DST_ALPHA(6),
        ONE_MINUS_DST_ALPHA(7);

        int mID;

        BlendDstFunc(int i) {
            this.mID = i;
        }
    }

    ProgramStore(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
    }

    public android.renderscript.ProgramStore.DepthFunc getDepthFunc() {
        return this.mDepthFunc;
    }

    public boolean isDepthMaskEnabled() {
        return this.mDepthMask;
    }

    public boolean isColorMaskRedEnabled() {
        return this.mColorMaskR;
    }

    public boolean isColorMaskGreenEnabled() {
        return this.mColorMaskG;
    }

    public boolean isColorMaskBlueEnabled() {
        return this.mColorMaskB;
    }

    public boolean isColorMaskAlphaEnabled() {
        return this.mColorMaskA;
    }

    public android.renderscript.ProgramStore.BlendSrcFunc getBlendSrcFunc() {
        return this.mBlendSrc;
    }

    public android.renderscript.ProgramStore.BlendDstFunc getBlendDstFunc() {
        return this.mBlendDst;
    }

    public boolean isDitherEnabled() {
        return this.mDither;
    }

    public static android.renderscript.ProgramStore BLEND_NONE_DEPTH_TEST(android.renderscript.RenderScript renderScript) {
        if (renderScript.mProgramStore_BLEND_NONE_DEPTH_TEST == null) {
            android.renderscript.ProgramStore.Builder builder = new android.renderscript.ProgramStore.Builder(renderScript);
            builder.setDepthFunc(android.renderscript.ProgramStore.DepthFunc.LESS);
            builder.setBlendFunc(android.renderscript.ProgramStore.BlendSrcFunc.ONE, android.renderscript.ProgramStore.BlendDstFunc.ZERO);
            builder.setDitherEnabled(false);
            builder.setDepthMaskEnabled(true);
            renderScript.mProgramStore_BLEND_NONE_DEPTH_TEST = builder.create();
        }
        return renderScript.mProgramStore_BLEND_NONE_DEPTH_TEST;
    }

    public static android.renderscript.ProgramStore BLEND_NONE_DEPTH_NONE(android.renderscript.RenderScript renderScript) {
        if (renderScript.mProgramStore_BLEND_NONE_DEPTH_NO_DEPTH == null) {
            android.renderscript.ProgramStore.Builder builder = new android.renderscript.ProgramStore.Builder(renderScript);
            builder.setDepthFunc(android.renderscript.ProgramStore.DepthFunc.ALWAYS);
            builder.setBlendFunc(android.renderscript.ProgramStore.BlendSrcFunc.ONE, android.renderscript.ProgramStore.BlendDstFunc.ZERO);
            builder.setDitherEnabled(false);
            builder.setDepthMaskEnabled(false);
            renderScript.mProgramStore_BLEND_NONE_DEPTH_NO_DEPTH = builder.create();
        }
        return renderScript.mProgramStore_BLEND_NONE_DEPTH_NO_DEPTH;
    }

    public static android.renderscript.ProgramStore BLEND_ALPHA_DEPTH_TEST(android.renderscript.RenderScript renderScript) {
        if (renderScript.mProgramStore_BLEND_ALPHA_DEPTH_TEST == null) {
            android.renderscript.ProgramStore.Builder builder = new android.renderscript.ProgramStore.Builder(renderScript);
            builder.setDepthFunc(android.renderscript.ProgramStore.DepthFunc.LESS);
            builder.setBlendFunc(android.renderscript.ProgramStore.BlendSrcFunc.SRC_ALPHA, android.renderscript.ProgramStore.BlendDstFunc.ONE_MINUS_SRC_ALPHA);
            builder.setDitherEnabled(false);
            builder.setDepthMaskEnabled(true);
            renderScript.mProgramStore_BLEND_ALPHA_DEPTH_TEST = builder.create();
        }
        return renderScript.mProgramStore_BLEND_ALPHA_DEPTH_TEST;
    }

    public static android.renderscript.ProgramStore BLEND_ALPHA_DEPTH_NONE(android.renderscript.RenderScript renderScript) {
        if (renderScript.mProgramStore_BLEND_ALPHA_DEPTH_NO_DEPTH == null) {
            android.renderscript.ProgramStore.Builder builder = new android.renderscript.ProgramStore.Builder(renderScript);
            builder.setDepthFunc(android.renderscript.ProgramStore.DepthFunc.ALWAYS);
            builder.setBlendFunc(android.renderscript.ProgramStore.BlendSrcFunc.SRC_ALPHA, android.renderscript.ProgramStore.BlendDstFunc.ONE_MINUS_SRC_ALPHA);
            builder.setDitherEnabled(false);
            builder.setDepthMaskEnabled(false);
            renderScript.mProgramStore_BLEND_ALPHA_DEPTH_NO_DEPTH = builder.create();
        }
        return renderScript.mProgramStore_BLEND_ALPHA_DEPTH_NO_DEPTH;
    }

    public static class Builder {
        boolean mDither;
        android.renderscript.RenderScript mRS;
        android.renderscript.ProgramStore.DepthFunc mDepthFunc = android.renderscript.ProgramStore.DepthFunc.ALWAYS;
        boolean mDepthMask = false;
        boolean mColorMaskR = true;
        boolean mColorMaskG = true;
        boolean mColorMaskB = true;
        boolean mColorMaskA = true;
        android.renderscript.ProgramStore.BlendSrcFunc mBlendSrc = android.renderscript.ProgramStore.BlendSrcFunc.ONE;
        android.renderscript.ProgramStore.BlendDstFunc mBlendDst = android.renderscript.ProgramStore.BlendDstFunc.ZERO;

        public Builder(android.renderscript.RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public android.renderscript.ProgramStore.Builder setDepthFunc(android.renderscript.ProgramStore.DepthFunc depthFunc) {
            this.mDepthFunc = depthFunc;
            return this;
        }

        public android.renderscript.ProgramStore.Builder setDepthMaskEnabled(boolean z) {
            this.mDepthMask = z;
            return this;
        }

        public android.renderscript.ProgramStore.Builder setColorMaskEnabled(boolean z, boolean z2, boolean z3, boolean z4) {
            this.mColorMaskR = z;
            this.mColorMaskG = z2;
            this.mColorMaskB = z3;
            this.mColorMaskA = z4;
            return this;
        }

        public android.renderscript.ProgramStore.Builder setBlendFunc(android.renderscript.ProgramStore.BlendSrcFunc blendSrcFunc, android.renderscript.ProgramStore.BlendDstFunc blendDstFunc) {
            this.mBlendSrc = blendSrcFunc;
            this.mBlendDst = blendDstFunc;
            return this;
        }

        public android.renderscript.ProgramStore.Builder setDitherEnabled(boolean z) {
            this.mDither = z;
            return this;
        }

        public android.renderscript.ProgramStore create() {
            this.mRS.validate();
            android.renderscript.ProgramStore programStore = new android.renderscript.ProgramStore(this.mRS.nProgramStoreCreate(this.mColorMaskR, this.mColorMaskG, this.mColorMaskB, this.mColorMaskA, this.mDepthMask, this.mDither, this.mBlendSrc.mID, this.mBlendDst.mID, this.mDepthFunc.mID), this.mRS);
            programStore.mDepthFunc = this.mDepthFunc;
            programStore.mDepthMask = this.mDepthMask;
            programStore.mColorMaskR = this.mColorMaskR;
            programStore.mColorMaskG = this.mColorMaskG;
            programStore.mColorMaskB = this.mColorMaskB;
            programStore.mColorMaskA = this.mColorMaskA;
            programStore.mBlendSrc = this.mBlendSrc;
            programStore.mBlendDst = this.mBlendDst;
            programStore.mDither = this.mDither;
            return programStore;
        }
    }
}
