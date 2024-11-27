package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class ScriptC extends android.renderscript.Script {
    private static final long RENDERSCRIPT_SCRIPTC_DEPRECATION_CHANGE_ID = 297019750;
    private static final java.lang.String TAG = "ScriptC";

    protected ScriptC(int i, android.renderscript.RenderScript renderScript) {
        super(i, renderScript);
    }

    protected ScriptC(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
    }

    protected ScriptC(android.renderscript.RenderScript renderScript, android.content.res.Resources resources, int i) {
        super(0L, renderScript);
        long internalCreate = internalCreate(renderScript, resources, i);
        if (internalCreate == 0) {
            throw new android.renderscript.RSRuntimeException("Loading of ScriptC script failed.");
        }
        setID(internalCreate);
    }

    protected ScriptC(android.renderscript.RenderScript renderScript, java.lang.String str, byte[] bArr, byte[] bArr2) {
        super(0L, renderScript);
        long internalStringCreate;
        if (android.renderscript.RenderScript.sPointerSize == 4) {
            internalStringCreate = internalStringCreate(renderScript, str, bArr);
        } else {
            internalStringCreate = internalStringCreate(renderScript, str, bArr2);
        }
        if (internalStringCreate == 0) {
            throw new android.renderscript.RSRuntimeException("Loading of ScriptC script failed.");
        }
        setID(internalStringCreate);
    }

    private static void throwExceptionIfScriptCUnsupported() {
        try {
            java.lang.System.loadLibrary("RS");
            android.util.Slog.w(TAG, "ScriptC scripts are not supported when targeting an API Level >= 36. Please refer to https://developer.android.com/guide/topics/renderscript/migration-guide for proposed alternatives.");
            if (android.app.compat.CompatChanges.isChangeEnabled(RENDERSCRIPT_SCRIPTC_DEPRECATION_CHANGE_ID)) {
                throw new java.lang.UnsupportedOperationException("ScriptC scripts are not supported when targeting an API Level >= 36. Please refer to https://developer.android.com/guide/topics/renderscript/migration-guide for proposed alternatives.");
            }
        } catch (java.lang.UnsatisfiedLinkError e) {
            throw new java.lang.UnsupportedOperationException("This device does not have an ABI that supports ScriptC.");
        }
    }

    private static synchronized long internalCreate(android.renderscript.RenderScript renderScript, android.content.res.Resources resources, int i) {
        long nScriptCCreate;
        synchronized (android.renderscript.ScriptC.class) {
            throwExceptionIfScriptCUnsupported();
            java.io.InputStream openRawResource = resources.openRawResource(i);
            try {
                try {
                    byte[] bArr = new byte[1024];
                    int i2 = 0;
                    while (true) {
                        int length = bArr.length - i2;
                        if (length == 0) {
                            int length2 = bArr.length * 2;
                            byte[] bArr2 = new byte[length2];
                            java.lang.System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                            length = length2 - i2;
                            bArr = bArr2;
                        }
                        int read = openRawResource.read(bArr, i2, length);
                        if (read > 0) {
                            i2 += read;
                        } else {
                            nScriptCCreate = renderScript.nScriptCCreate(resources.getResourceEntryName(i), android.renderscript.RenderScript.getCachePath(), bArr, i2);
                        }
                    }
                } finally {
                    openRawResource.close();
                }
            } catch (java.io.IOException e) {
                throw new android.content.res.Resources.NotFoundException();
            }
        }
        return nScriptCCreate;
    }

    private static synchronized long internalStringCreate(android.renderscript.RenderScript renderScript, java.lang.String str, byte[] bArr) {
        long nScriptCCreate;
        synchronized (android.renderscript.ScriptC.class) {
            throwExceptionIfScriptCUnsupported();
            nScriptCCreate = renderScript.nScriptCCreate(str, android.renderscript.RenderScript.getCachePath(), bArr, bArr.length);
        }
        return nScriptCCreate;
    }
}
