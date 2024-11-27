package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class ScriptIntrinsicBlend extends android.renderscript.ScriptIntrinsic {
    ScriptIntrinsicBlend(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
    }

    public static android.renderscript.ScriptIntrinsicBlend create(android.renderscript.RenderScript renderScript, android.renderscript.Element element) {
        return new android.renderscript.ScriptIntrinsicBlend(renderScript.nScriptIntrinsicCreate(7, element.getID(renderScript)), renderScript);
    }

    private void blend(int i, android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        if (!allocation.getElement().isCompatible(android.renderscript.Element.U8_4(this.mRS))) {
            throw new android.renderscript.RSIllegalArgumentException("Input is not of expected format.");
        }
        if (!allocation2.getElement().isCompatible(android.renderscript.Element.U8_4(this.mRS))) {
            throw new android.renderscript.RSIllegalArgumentException("Output is not of expected format.");
        }
        forEach(i, allocation, allocation2, (android.renderscript.FieldPacker) null, launchOptions);
    }

    public void forEachClear(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachClear(allocation, allocation2, null);
    }

    public void forEachClear(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(0, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDClear() {
        return createKernelID(0, 3, null, null);
    }

    public void forEachSrc(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachSrc(allocation, allocation2, null);
    }

    public void forEachSrc(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(1, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDSrc() {
        return createKernelID(1, 3, null, null);
    }

    public void forEachDst(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
    }

    public void forEachDst(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
    }

    public android.renderscript.Script.KernelID getKernelIDDst() {
        return createKernelID(2, 3, null, null);
    }

    public void forEachSrcOver(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachSrcOver(allocation, allocation2, null);
    }

    public void forEachSrcOver(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(3, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDSrcOver() {
        return createKernelID(3, 3, null, null);
    }

    public void forEachDstOver(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachDstOver(allocation, allocation2, null);
    }

    public void forEachDstOver(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(4, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDDstOver() {
        return createKernelID(4, 3, null, null);
    }

    public void forEachSrcIn(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachSrcIn(allocation, allocation2, null);
    }

    public void forEachSrcIn(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(5, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDSrcIn() {
        return createKernelID(5, 3, null, null);
    }

    public void forEachDstIn(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachDstIn(allocation, allocation2, null);
    }

    public void forEachDstIn(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(6, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDDstIn() {
        return createKernelID(6, 3, null, null);
    }

    public void forEachSrcOut(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachSrcOut(allocation, allocation2, null);
    }

    public void forEachSrcOut(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(7, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDSrcOut() {
        return createKernelID(7, 3, null, null);
    }

    public void forEachDstOut(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachDstOut(allocation, allocation2, null);
    }

    public void forEachDstOut(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(8, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDDstOut() {
        return createKernelID(8, 3, null, null);
    }

    public void forEachSrcAtop(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachSrcAtop(allocation, allocation2, null);
    }

    public void forEachSrcAtop(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(9, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDSrcAtop() {
        return createKernelID(9, 3, null, null);
    }

    public void forEachDstAtop(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachDstAtop(allocation, allocation2, null);
    }

    public void forEachDstAtop(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(10, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDDstAtop() {
        return createKernelID(10, 3, null, null);
    }

    public void forEachXor(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachXor(allocation, allocation2, null);
    }

    public void forEachXor(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(11, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDXor() {
        return createKernelID(11, 3, null, null);
    }

    public void forEachMultiply(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachMultiply(allocation, allocation2, null);
    }

    public void forEachMultiply(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(14, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDMultiply() {
        return createKernelID(14, 3, null, null);
    }

    public void forEachAdd(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachAdd(allocation, allocation2, null);
    }

    public void forEachAdd(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(34, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDAdd() {
        return createKernelID(34, 3, null, null);
    }

    public void forEachSubtract(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2) {
        forEachSubtract(allocation, allocation2, null);
    }

    public void forEachSubtract(android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.Script.LaunchOptions launchOptions) {
        blend(35, allocation, allocation2, launchOptions);
    }

    public android.renderscript.Script.KernelID getKernelIDSubtract() {
        return createKernelID(35, 3, null, null);
    }
}
