package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsic3DLUT extends android.renderscript.ScriptIntrinsic {
    private android.renderscript.Element mElement;
    private android.renderscript.Allocation mLUT;

    private ScriptIntrinsic3DLUT(long j, android.renderscript.RenderScript renderScript, android.renderscript.Element element) {
        super(j, renderScript);
        this.mElement = element;
    }

    public static android.renderscript.ScriptIntrinsic3DLUT create(android.renderscript.RenderScript renderScript, android.renderscript.Element element) {
        long nScriptIntrinsicCreate = renderScript.nScriptIntrinsicCreate(8, element.getID(renderScript));
        if (!element.isCompatible(android.renderscript.Element.U8_4(renderScript))) {
            throw new android.renderscript.RSIllegalArgumentException("Element must be compatible with uchar4.");
        }
        return new android.renderscript.ScriptIntrinsic3DLUT(nScriptIntrinsicCreate, renderScript, element);
    }

    public void setLUT(android.renderscript.Allocation allocation) {
        android.renderscript.Type type = allocation.getType();
        if (type.getZ() == 0) {
            throw new android.renderscript.RSIllegalArgumentException("LUT must be 3d.");
        }
        if (!type.getElement().isCompatible(this.mElement)) {
            throw new android.renderscript.RSIllegalArgumentException("LUT element type must match.");
        }
        this.mLUT = allocation;
        setVar(0, this.mLUT);
    }

    public void forEach(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEach(allocation, allocation2, null);
    }

    public void forEach(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        forEach(0, allocation, allocation2, (android.renderscript.FieldPacker) null, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelID() {
        return createKernelID(0, 3, null, null);
    }
}
