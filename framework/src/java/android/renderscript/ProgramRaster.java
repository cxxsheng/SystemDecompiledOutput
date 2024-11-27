package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class ProgramRaster extends android.renderscript.BaseObj {
    android.renderscript.ProgramRaster.CullMode mCullMode;
    boolean mPointSprite;

    public enum CullMode {
        BACK(0),
        FRONT(1),
        NONE(2);

        int mID;

        CullMode(int i) {
            this.mID = i;
        }
    }

    ProgramRaster(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
        this.mPointSprite = false;
        this.mCullMode = android.renderscript.ProgramRaster.CullMode.BACK;
    }

    public boolean isPointSpriteEnabled() {
        return this.mPointSprite;
    }

    public android.renderscript.ProgramRaster.CullMode getCullMode() {
        return this.mCullMode;
    }

    public static android.renderscript.ProgramRaster CULL_BACK(android.renderscript.RenderScript renderScript) {
        if (renderScript.mProgramRaster_CULL_BACK == null) {
            android.renderscript.ProgramRaster.Builder builder = new android.renderscript.ProgramRaster.Builder(renderScript);
            builder.setCullMode(android.renderscript.ProgramRaster.CullMode.BACK);
            renderScript.mProgramRaster_CULL_BACK = builder.create();
        }
        return renderScript.mProgramRaster_CULL_BACK;
    }

    public static android.renderscript.ProgramRaster CULL_FRONT(android.renderscript.RenderScript renderScript) {
        if (renderScript.mProgramRaster_CULL_FRONT == null) {
            android.renderscript.ProgramRaster.Builder builder = new android.renderscript.ProgramRaster.Builder(renderScript);
            builder.setCullMode(android.renderscript.ProgramRaster.CullMode.FRONT);
            renderScript.mProgramRaster_CULL_FRONT = builder.create();
        }
        return renderScript.mProgramRaster_CULL_FRONT;
    }

    public static android.renderscript.ProgramRaster CULL_NONE(android.renderscript.RenderScript renderScript) {
        if (renderScript.mProgramRaster_CULL_NONE == null) {
            android.renderscript.ProgramRaster.Builder builder = new android.renderscript.ProgramRaster.Builder(renderScript);
            builder.setCullMode(android.renderscript.ProgramRaster.CullMode.NONE);
            renderScript.mProgramRaster_CULL_NONE = builder.create();
        }
        return renderScript.mProgramRaster_CULL_NONE;
    }

    public static class Builder {
        android.renderscript.RenderScript mRS;
        boolean mPointSprite = false;
        android.renderscript.ProgramRaster.CullMode mCullMode = android.renderscript.ProgramRaster.CullMode.BACK;

        public Builder(android.renderscript.RenderScript renderScript) {
            this.mRS = renderScript;
        }

        public android.renderscript.ProgramRaster.Builder setPointSpriteEnabled(boolean z) {
            this.mPointSprite = z;
            return this;
        }

        public android.renderscript.ProgramRaster.Builder setCullMode(android.renderscript.ProgramRaster.CullMode cullMode) {
            this.mCullMode = cullMode;
            return this;
        }

        public android.renderscript.ProgramRaster create() {
            this.mRS.validate();
            android.renderscript.ProgramRaster programRaster = new android.renderscript.ProgramRaster(this.mRS.nProgramRasterCreate(this.mPointSprite, this.mCullMode.mID), this.mRS);
            programRaster.mPointSprite = this.mPointSprite;
            programRaster.mCullMode = this.mCullMode;
            return programRaster;
        }
    }
}
