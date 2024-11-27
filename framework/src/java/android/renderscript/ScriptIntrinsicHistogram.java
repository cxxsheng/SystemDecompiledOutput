package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsicHistogram extends android.renderscript.ScriptIntrinsic {
    private android.renderscript.Allocation mOut;

    private ScriptIntrinsicHistogram(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
    }

    public static android.renderscript.ScriptIntrinsicHistogram create(android.renderscript.RenderScript renderScript, android.renderscript.Element element) {
        if (!element.isCompatible(android.renderscript.Element.U8_4(renderScript)) && !element.isCompatible(android.renderscript.Element.U8_3(renderScript)) && !element.isCompatible(android.renderscript.Element.U8_2(renderScript)) && !element.isCompatible(android.renderscript.Element.U8(renderScript))) {
            throw new android.renderscript.RSIllegalArgumentException("Unsupported element type.");
        }
        return new android.renderscript.ScriptIntrinsicHistogram(renderScript.nScriptIntrinsicCreate(9, element.getID(renderScript)), renderScript);
    }

    public void forEach(android.renderscript.Allocation allocation) {
        forEach(allocation, null);
    }

    public void forEach(android.renderscript.Allocation allocation, android.renderscript.Script.LaunchOptions launchOptions) {
        if (allocation.getType().getElement().getVectorSize() < this.mOut.getType().getElement().getVectorSize()) {
            throw new android.renderscript.RSIllegalArgumentException("Input vector size must be >= output vector size.");
        }
        if (!allocation.getType().getElement().isCompatible(android.renderscript.Element.U8(this.mRS)) && !allocation.getType().getElement().isCompatible(android.renderscript.Element.U8_2(this.mRS)) && !allocation.getType().getElement().isCompatible(android.renderscript.Element.U8_3(this.mRS)) && !allocation.getType().getElement().isCompatible(android.renderscript.Element.U8_4(this.mRS))) {
            throw new android.renderscript.RSIllegalArgumentException("Input type must be U8, U8_1, U8_2 or U8_4.");
        }
        forEach(0, allocation, (android.renderscript.Allocation) null, (android.renderscript.FieldPacker) null, launchOptions);
    }

    public void setDotCoefficients(float f, float f2, float f3, float f4) {
        if (f < 0.0f || f2 < 0.0f || f3 < 0.0f || f4 < 0.0f) {
            throw new android.renderscript.RSIllegalArgumentException("Coefficient may not be negative.");
        }
        if (f + f2 + f3 + f4 > 1.0f) {
            throw new android.renderscript.RSIllegalArgumentException("Sum of coefficients must be 1.0 or less.");
        }
        android.renderscript.FieldPacker fieldPacker = new android.renderscript.FieldPacker(16);
        fieldPacker.addF32(f);
        fieldPacker.addF32(f2);
        fieldPacker.addF32(f3);
        fieldPacker.addF32(f4);
        setVar(0, fieldPacker);
    }

    public void setOutput(android.renderscript.Allocation allocation) {
        this.mOut = allocation;
        if (this.mOut.getType().getElement() != android.renderscript.Element.U32(this.mRS) && this.mOut.getType().getElement() != android.renderscript.Element.U32_2(this.mRS) && this.mOut.getType().getElement() != android.renderscript.Element.U32_3(this.mRS) && this.mOut.getType().getElement() != android.renderscript.Element.U32_4(this.mRS) && this.mOut.getType().getElement() != android.renderscript.Element.I32(this.mRS) && this.mOut.getType().getElement() != android.renderscript.Element.I32_2(this.mRS) && this.mOut.getType().getElement() != android.renderscript.Element.I32_3(this.mRS) && this.mOut.getType().getElement() != android.renderscript.Element.I32_4(this.mRS)) {
            throw new android.renderscript.RSIllegalArgumentException("Output type must be U32 or I32.");
        }
        if (this.mOut.getType().getX() != 256 || this.mOut.getType().getY() != 0 || this.mOut.getType().hasMipmaps() || this.mOut.getType().getYuv() != 0) {
            throw new android.renderscript.RSIllegalArgumentException("Output must be 1D, 256 elements.");
        }
        setVar(1, allocation);
    }

    public void forEach_Dot(android.renderscript.Allocation allocation) {
        forEach_Dot(allocation, null);
    }

    public void forEach_Dot(android.renderscript.Allocation allocation, android.renderscript.Script.LaunchOptions launchOptions) {
        if (this.mOut.getType().getElement().getVectorSize() != 1) {
            throw new android.renderscript.RSIllegalArgumentException("Output vector size must be one.");
        }
        if (!allocation.getType().getElement().isCompatible(android.renderscript.Element.U8(this.mRS)) && !allocation.getType().getElement().isCompatible(android.renderscript.Element.U8_2(this.mRS)) && !allocation.getType().getElement().isCompatible(android.renderscript.Element.U8_3(this.mRS)) && !allocation.getType().getElement().isCompatible(android.renderscript.Element.U8_4(this.mRS))) {
            throw new android.renderscript.RSIllegalArgumentException("Input type must be U8, U8_1, U8_2 or U8_4.");
        }
        forEach(1, allocation, (android.renderscript.Allocation) null, (android.renderscript.FieldPacker) null, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelID_Separate() {
        return createKernelID(0, 3, null, null);
    }

    public android.renderscript.Script.FieldID getFieldID_Input() {
        return createFieldID(1, null);
    }
}
