package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsicLUT extends android.renderscript.ScriptIntrinsic {
    private final byte[] mCache;
    private boolean mDirty;
    private final android.renderscript.Matrix4f mMatrix;
    private android.renderscript.Allocation mTables;

    private ScriptIntrinsicLUT(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
        this.mMatrix = new android.renderscript.Matrix4f();
        this.mCache = new byte[1024];
        this.mDirty = true;
        this.mTables = android.renderscript.Allocation.createSized(renderScript, android.renderscript.Element.U8(renderScript), 1024);
        for (int i = 0; i < 256; i++) {
            byte b = (byte) i;
            this.mCache[i] = b;
            this.mCache[i + 256] = b;
            this.mCache[i + 512] = b;
            this.mCache[i + 768] = b;
        }
        setVar(0, this.mTables);
    }

    public static android.renderscript.ScriptIntrinsicLUT create(android.renderscript.RenderScript renderScript, android.renderscript.Element element) {
        return new android.renderscript.ScriptIntrinsicLUT(renderScript.nScriptIntrinsicCreate(3, element.getID(renderScript)), renderScript);
    }

    @Override // android.renderscript.BaseObj
    public void destroy() {
        this.mTables.destroy();
        super.destroy();
    }

    private void validate(int i, int i2) {
        if (i < 0 || i > 255) {
            throw new android.renderscript.RSIllegalArgumentException("Index out of range (0-255).");
        }
        if (i2 < 0 || i2 > 255) {
            throw new android.renderscript.RSIllegalArgumentException("Value out of range (0-255).");
        }
    }

    public void setRed(int i, int i2) {
        validate(i, i2);
        this.mCache[i] = (byte) i2;
        this.mDirty = true;
    }

    public void setGreen(int i, int i2) {
        validate(i, i2);
        this.mCache[i + 256] = (byte) i2;
        this.mDirty = true;
    }

    public void setBlue(int i, int i2) {
        validate(i, i2);
        this.mCache[i + 512] = (byte) i2;
        this.mDirty = true;
    }

    public void setAlpha(int i, int i2) {
        validate(i, i2);
        this.mCache[i + 768] = (byte) i2;
        this.mDirty = true;
    }

    public void forEach(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEach(allocation, allocation2, null);
    }

    public void forEach(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        if (this.mDirty) {
            this.mDirty = false;
            this.mTables.copyFromUnchecked(this.mCache);
        }
        forEach(0, allocation, allocation2, (android.renderscript.FieldPacker) null, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelID() {
        return createKernelID(0, 3, null, null);
    }
}
