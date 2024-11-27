package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsicConvolve3x3 extends android.renderscript.ScriptIntrinsic {
    private android.renderscript.Allocation mInput;
    private final float[] mValues;

    private ScriptIntrinsicConvolve3x3(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
        this.mValues = new float[9];
    }

    public static android.renderscript.ScriptIntrinsicConvolve3x3 create(android.renderscript.RenderScript renderScript, android.renderscript.Element element) {
        float[] fArr = {0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        if (!element.isCompatible(android.renderscript.Element.U8(renderScript)) && !element.isCompatible(android.renderscript.Element.U8_2(renderScript)) && !element.isCompatible(android.renderscript.Element.U8_3(renderScript)) && !element.isCompatible(android.renderscript.Element.U8_4(renderScript)) && !element.isCompatible(android.renderscript.Element.F32(renderScript)) && !element.isCompatible(android.renderscript.Element.F32_2(renderScript)) && !element.isCompatible(android.renderscript.Element.F32_3(renderScript)) && !element.isCompatible(android.renderscript.Element.F32_4(renderScript))) {
            throw new android.renderscript.RSIllegalArgumentException("Unsupported element type.");
        }
        android.renderscript.ScriptIntrinsicConvolve3x3 scriptIntrinsicConvolve3x3 = new android.renderscript.ScriptIntrinsicConvolve3x3(renderScript.nScriptIntrinsicCreate(1, element.getID(renderScript)), renderScript);
        scriptIntrinsicConvolve3x3.setCoefficients(fArr);
        return scriptIntrinsicConvolve3x3;
    }

    public void setInput(android.renderscript.Allocation allocation) {
        this.mInput = allocation;
        setVar(1, allocation);
    }

    public void setCoefficients(float[] fArr) {
        android.renderscript.FieldPacker fieldPacker = new android.renderscript.FieldPacker(36);
        for (int i = 0; i < this.mValues.length; i++) {
            this.mValues[i] = fArr[i];
            fieldPacker.addF32(this.mValues[i]);
        }
        setVar(0, fieldPacker);
    }

    public void forEach(android.renderscript.Allocation allocation) {
        forEach(0, (android.renderscript.Allocation) null, allocation, (android.renderscript.FieldPacker) null);
    }

    public void forEach(android.renderscript.Allocation allocation, android.renderscript.Script.LaunchOptions launchOptions) {
        forEach(0, (android.renderscript.Allocation) null, allocation, (android.renderscript.FieldPacker) null, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelID() {
        return createKernelID(0, 2, null, null);
    }

    public android.renderscript.Script.FieldID getFieldID_Input() {
        return createFieldID(1, null);
    }
}
