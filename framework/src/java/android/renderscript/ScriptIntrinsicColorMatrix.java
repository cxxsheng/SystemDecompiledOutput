package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsicColorMatrix extends android.renderscript.ScriptIntrinsic {
    private final android.renderscript.Float4 mAdd;
    private final android.renderscript.Matrix4f mMatrix;

    private ScriptIntrinsicColorMatrix(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
        this.mMatrix = new android.renderscript.Matrix4f();
        this.mAdd = new android.renderscript.Float4();
    }

    @java.lang.Deprecated
    public static android.renderscript.ScriptIntrinsicColorMatrix create(android.renderscript.RenderScript renderScript, android.renderscript.Element element) {
        return create(renderScript);
    }

    public static android.renderscript.ScriptIntrinsicColorMatrix create(android.renderscript.RenderScript renderScript) {
        return new android.renderscript.ScriptIntrinsicColorMatrix(renderScript.nScriptIntrinsicCreate(2, 0L), renderScript);
    }

    private void setMatrix() {
        android.renderscript.FieldPacker fieldPacker = new android.renderscript.FieldPacker(64);
        fieldPacker.addMatrix(this.mMatrix);
        setVar(0, fieldPacker);
    }

    public void setColorMatrix(android.renderscript.Matrix4f matrix4f) {
        this.mMatrix.load(matrix4f);
        setMatrix();
    }

    public void setColorMatrix(android.renderscript.Matrix3f matrix3f) {
        this.mMatrix.load(matrix3f);
        setMatrix();
    }

    public void setAdd(android.renderscript.Float4 float4) {
        this.mAdd.x = float4.x;
        this.mAdd.y = float4.y;
        this.mAdd.z = float4.z;
        this.mAdd.w = float4.w;
        android.renderscript.FieldPacker fieldPacker = new android.renderscript.FieldPacker(16);
        fieldPacker.addF32(float4.x);
        fieldPacker.addF32(float4.y);
        fieldPacker.addF32(float4.z);
        fieldPacker.addF32(float4.w);
        setVar(1, fieldPacker);
    }

    public void setAdd(float f, float f2, float f3, float f4) {
        this.mAdd.x = f;
        this.mAdd.y = f2;
        this.mAdd.z = f3;
        this.mAdd.w = f4;
        android.renderscript.FieldPacker fieldPacker = new android.renderscript.FieldPacker(16);
        fieldPacker.addF32(this.mAdd.x);
        fieldPacker.addF32(this.mAdd.y);
        fieldPacker.addF32(this.mAdd.z);
        fieldPacker.addF32(this.mAdd.w);
        setVar(1, fieldPacker);
    }

    public void setGreyscale() {
        this.mMatrix.loadIdentity();
        this.mMatrix.set(0, 0, 0.299f);
        this.mMatrix.set(1, 0, 0.587f);
        this.mMatrix.set(2, 0, 0.114f);
        this.mMatrix.set(0, 1, 0.299f);
        this.mMatrix.set(1, 1, 0.587f);
        this.mMatrix.set(2, 1, 0.114f);
        this.mMatrix.set(0, 2, 0.299f);
        this.mMatrix.set(1, 2, 0.587f);
        this.mMatrix.set(2, 2, 0.114f);
        setMatrix();
    }

    public void setYUVtoRGB() {
        this.mMatrix.loadIdentity();
        this.mMatrix.set(0, 0, 1.0f);
        this.mMatrix.set(1, 0, 0.0f);
        this.mMatrix.set(2, 0, 1.13983f);
        this.mMatrix.set(0, 1, 1.0f);
        this.mMatrix.set(1, 1, -0.39465f);
        this.mMatrix.set(2, 1, -0.5806f);
        this.mMatrix.set(0, 2, 1.0f);
        this.mMatrix.set(1, 2, 2.03211f);
        this.mMatrix.set(2, 2, 0.0f);
        setMatrix();
    }

    public void setRGBtoYUV() {
        this.mMatrix.loadIdentity();
        this.mMatrix.set(0, 0, 0.299f);
        this.mMatrix.set(1, 0, 0.587f);
        this.mMatrix.set(2, 0, 0.114f);
        this.mMatrix.set(0, 1, -0.14713f);
        this.mMatrix.set(1, 1, -0.28886f);
        this.mMatrix.set(2, 1, 0.436f);
        this.mMatrix.set(0, 2, 0.615f);
        this.mMatrix.set(1, 2, -0.51499f);
        this.mMatrix.set(2, 2, -0.10001f);
        setMatrix();
    }

    public void forEach(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEach(allocation, allocation2, null);
    }

    public void forEach(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        if (!allocation.getElement().isCompatible(android.renderscript.Element.U8(this.mRS)) && !allocation.getElement().isCompatible(android.renderscript.Element.U8_2(this.mRS)) && !allocation.getElement().isCompatible(android.renderscript.Element.U8_3(this.mRS)) && !allocation.getElement().isCompatible(android.renderscript.Element.U8_4(this.mRS)) && !allocation.getElement().isCompatible(android.renderscript.Element.F32(this.mRS)) && !allocation.getElement().isCompatible(android.renderscript.Element.F32_2(this.mRS)) && !allocation.getElement().isCompatible(android.renderscript.Element.F32_3(this.mRS)) && !allocation.getElement().isCompatible(android.renderscript.Element.F32_4(this.mRS))) {
            throw new android.renderscript.RSIllegalArgumentException("Unsupported element type.");
        }
        if (!allocation2.getElement().isCompatible(android.renderscript.Element.U8(this.mRS)) && !allocation2.getElement().isCompatible(android.renderscript.Element.U8_2(this.mRS)) && !allocation2.getElement().isCompatible(android.renderscript.Element.U8_3(this.mRS)) && !allocation2.getElement().isCompatible(android.renderscript.Element.U8_4(this.mRS)) && !allocation2.getElement().isCompatible(android.renderscript.Element.F32(this.mRS)) && !allocation2.getElement().isCompatible(android.renderscript.Element.F32_2(this.mRS)) && !allocation2.getElement().isCompatible(android.renderscript.Element.F32_3(this.mRS)) && !allocation2.getElement().isCompatible(android.renderscript.Element.F32_4(this.mRS))) {
            throw new android.renderscript.RSIllegalArgumentException("Unsupported element type.");
        }
        forEach(0, allocation, allocation2, (android.renderscript.FieldPacker) null, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelID() {
        return createKernelID(0, 3, null, null);
    }
}
