package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class ScriptIntrinsic extends android.renderscript.Script {
    ScriptIntrinsic(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
        if (j == 0) {
            throw new android.renderscript.RSRuntimeException("Loading of ScriptIntrinsic failed.");
        }
    }
}
