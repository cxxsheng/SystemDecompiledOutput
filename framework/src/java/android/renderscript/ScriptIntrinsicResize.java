package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsicResize extends android.renderscript.ScriptIntrinsic {
    private android.renderscript.Allocation mInput;

    private ScriptIntrinsicResize(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
    }

    public static android.renderscript.ScriptIntrinsicResize create(android.renderscript.RenderScript renderScript) {
        return new android.renderscript.ScriptIntrinsicResize(renderScript.nScriptIntrinsicCreate(12, 0L), renderScript);
    }

    public void setInput(android.renderscript.Allocation allocation) {
        android.renderscript.Element element = allocation.getElement();
        if (!element.isCompatible(android.renderscript.Element.U8(this.mRS)) && !element.isCompatible(android.renderscript.Element.U8_2(this.mRS)) && !element.isCompatible(android.renderscript.Element.U8_3(this.mRS)) && !element.isCompatible(android.renderscript.Element.U8_4(this.mRS)) && !element.isCompatible(android.renderscript.Element.F32(this.mRS)) && !element.isCompatible(android.renderscript.Element.F32_2(this.mRS)) && !element.isCompatible(android.renderscript.Element.F32_3(this.mRS)) && !element.isCompatible(android.renderscript.Element.F32_4(this.mRS))) {
            throw new android.renderscript.RSIllegalArgumentException("Unsupported element type.");
        }
        this.mInput = allocation;
        setVar(0, allocation);
    }

    public android.renderscript.Script.FieldID getFieldID_Input() {
        return createFieldID(0, null);
    }

    public void forEach_bicubic(android.renderscript.Allocation allocation) {
        if (allocation == this.mInput) {
            throw new android.renderscript.RSIllegalArgumentException("Output cannot be same as Input.");
        }
        forEach_bicubic(allocation, null);
    }

    public void forEach_bicubic(android.renderscript.Allocation allocation, android.renderscript.Script.LaunchOptions launchOptions) {
        forEach(0, (android.renderscript.Allocation) null, allocation, (android.renderscript.FieldPacker) null, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelID_bicubic() {
        return createKernelID(0, 2, null, null);
    }
}
