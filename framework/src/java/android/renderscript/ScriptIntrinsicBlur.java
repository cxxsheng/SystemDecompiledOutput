package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsicBlur extends android.renderscript.ScriptIntrinsic {
    private android.renderscript.Allocation mInput;
    private final float[] mValues;

    private ScriptIntrinsicBlur(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
        this.mValues = new float[9];
    }

    public static android.renderscript.ScriptIntrinsicBlur create(android.renderscript.RenderScript renderScript, android.renderscript.Element element) {
        if (!element.isCompatible(android.renderscript.Element.U8_4(renderScript)) && !element.isCompatible(android.renderscript.Element.U8(renderScript))) {
            throw new android.renderscript.RSIllegalArgumentException("Unsupported element type.");
        }
        android.renderscript.ScriptIntrinsicBlur scriptIntrinsicBlur = new android.renderscript.ScriptIntrinsicBlur(renderScript.nScriptIntrinsicCreate(5, element.getID(renderScript)), renderScript);
        scriptIntrinsicBlur.setRadius(5.0f);
        return scriptIntrinsicBlur;
    }

    public void setInput(android.renderscript.Allocation allocation) {
        if (allocation.getType().getY() == 0) {
            throw new android.renderscript.RSIllegalArgumentException("Input set to a 1D Allocation");
        }
        android.renderscript.Element element = allocation.getElement();
        if (!element.isCompatible(android.renderscript.Element.U8_4(this.mRS)) && !element.isCompatible(android.renderscript.Element.U8(this.mRS))) {
            throw new android.renderscript.RSIllegalArgumentException("Unsupported element type.");
        }
        this.mInput = allocation;
        setVar(1, allocation);
    }

    public void setRadius(float f) {
        if (f <= 0.0f || f > 25.0f) {
            throw new android.renderscript.RSIllegalArgumentException("Radius out of range (0 < r <= 25).");
        }
        setVar(0, f);
    }

    public void forEach(android.renderscript.Allocation allocation) {
        if (allocation.getType().getY() == 0) {
            throw new android.renderscript.RSIllegalArgumentException("Output is a 1D Allocation");
        }
        forEach(0, (android.renderscript.Allocation) null, allocation, (android.renderscript.FieldPacker) null);
    }

    public void forEach(android.renderscript.Allocation allocation, android.renderscript.Script.LaunchOptions launchOptions) {
        if (allocation.getType().getY() == 0) {
            throw new android.renderscript.RSIllegalArgumentException("Output is a 1D Allocation");
        }
        forEach(0, (android.renderscript.Allocation) null, allocation, (android.renderscript.FieldPacker) null, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelID() {
        return createKernelID(0, 2, null, null);
    }

    public android.renderscript.Script.FieldID getFieldID_Input() {
        return createFieldID(1, null);
    }
}
