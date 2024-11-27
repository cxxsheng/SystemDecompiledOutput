package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsicYuvToRGB extends android.renderscript.ScriptIntrinsic {
    private android.renderscript.Allocation mInput;

    ScriptIntrinsicYuvToRGB(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
    }

    public static android.renderscript.ScriptIntrinsicYuvToRGB create(android.renderscript.RenderScript renderScript, android.renderscript.Element element) {
        return new android.renderscript.ScriptIntrinsicYuvToRGB(renderScript.nScriptIntrinsicCreate(6, element.getID(renderScript)), renderScript);
    }

    public void setInput(android.renderscript.Allocation allocation) {
        this.mInput = allocation;
        setVar(0, allocation);
    }

    public void forEach(android.renderscript.Allocation allocation) {
        forEach(0, (android.renderscript.Allocation) null, allocation, (android.renderscript.FieldPacker) null);
    }

    public android.renderscript.Script.KernelID getKernelID() {
        return createKernelID(0, 2, null, null);
    }

    public android.renderscript.Script.FieldID getFieldID_Input() {
        return createFieldID(0, null);
    }
}
