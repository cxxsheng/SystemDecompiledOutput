package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Script extends android.renderscript.BaseObj {
    private final android.util.SparseArray<android.renderscript.Script.FieldID> mFIDs;
    private final android.util.SparseArray<android.renderscript.Script.InvokeID> mIIDs;
    long[] mInIdsBuffer;
    private final android.util.SparseArray<android.renderscript.Script.KernelID> mKIDs;

    public static final class KernelID extends android.renderscript.BaseObj {
        android.renderscript.Script mScript;
        int mSig;
        int mSlot;

        KernelID(long j, android.renderscript.RenderScript renderScript, android.renderscript.Script script, int i, int i2) {
            super(j, renderScript);
            this.mScript = script;
            this.mSlot = i;
            this.mSig = i2;
        }
    }

    protected android.renderscript.Script.KernelID createKernelID(int i, int i2, android.renderscript.Element element, android.renderscript.Element element2) {
        android.renderscript.Script.KernelID kernelID = this.mKIDs.get(i);
        if (kernelID != null) {
            return kernelID;
        }
        long nScriptKernelIDCreate = this.mRS.nScriptKernelIDCreate(getID(this.mRS), i, i2);
        if (nScriptKernelIDCreate == 0) {
            throw new android.renderscript.RSDriverException("Failed to create KernelID");
        }
        android.renderscript.Script.KernelID kernelID2 = new android.renderscript.Script.KernelID(nScriptKernelIDCreate, this.mRS, this, i, i2);
        this.mKIDs.put(i, kernelID2);
        return kernelID2;
    }

    public static final class InvokeID extends android.renderscript.BaseObj {
        android.renderscript.Script mScript;
        int mSlot;

        InvokeID(long j, android.renderscript.RenderScript renderScript, android.renderscript.Script script, int i) {
            super(j, renderScript);
            this.mScript = script;
            this.mSlot = i;
        }
    }

    protected android.renderscript.Script.InvokeID createInvokeID(int i) {
        android.renderscript.Script.InvokeID invokeID = this.mIIDs.get(i);
        if (invokeID != null) {
            return invokeID;
        }
        long nScriptInvokeIDCreate = this.mRS.nScriptInvokeIDCreate(getID(this.mRS), i);
        if (nScriptInvokeIDCreate == 0) {
            throw new android.renderscript.RSDriverException("Failed to create KernelID");
        }
        android.renderscript.Script.InvokeID invokeID2 = new android.renderscript.Script.InvokeID(nScriptInvokeIDCreate, this.mRS, this, i);
        this.mIIDs.put(i, invokeID2);
        return invokeID2;
    }

    public static final class FieldID extends android.renderscript.BaseObj {
        android.renderscript.Script mScript;
        int mSlot;

        FieldID(long j, android.renderscript.RenderScript renderScript, android.renderscript.Script script, int i) {
            super(j, renderScript);
            this.mScript = script;
            this.mSlot = i;
        }
    }

    protected android.renderscript.Script.FieldID createFieldID(int i, android.renderscript.Element element) {
        android.renderscript.Script.FieldID fieldID = this.mFIDs.get(i);
        if (fieldID != null) {
            return fieldID;
        }
        long nScriptFieldIDCreate = this.mRS.nScriptFieldIDCreate(getID(this.mRS), i);
        if (nScriptFieldIDCreate == 0) {
            throw new android.renderscript.RSDriverException("Failed to create FieldID");
        }
        android.renderscript.Script.FieldID fieldID2 = new android.renderscript.Script.FieldID(nScriptFieldIDCreate, this.mRS, this, i);
        this.mFIDs.put(i, fieldID2);
        return fieldID2;
    }

    protected void invoke(int i) {
        this.mRS.nScriptInvoke(getID(this.mRS), i);
    }

    protected void invoke(int i, android.renderscript.FieldPacker fieldPacker) {
        if (fieldPacker != null) {
            this.mRS.nScriptInvokeV(getID(this.mRS), i, fieldPacker.getData());
        } else {
            this.mRS.nScriptInvoke(getID(this.mRS), i);
        }
    }

    protected void forEach(int i, android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.FieldPacker fieldPacker) {
        forEach(i, allocation, allocation2, fieldPacker, (android.renderscript.Script.LaunchOptions) null);
    }

    protected void forEach(int i, android.renderscript.Allocation allocation, android.renderscript.Allocation allocation2, android.renderscript.FieldPacker fieldPacker, android.renderscript.Script.LaunchOptions launchOptions) {
        long[] jArr;
        long j;
        byte[] bArr;
        int[] iArr;
        this.mRS.validate();
        this.mRS.validateObject(allocation);
        this.mRS.validateObject(allocation2);
        if (allocation == null && allocation2 == null && launchOptions == null) {
            throw new android.renderscript.RSIllegalArgumentException("At least one of input allocation, output allocation, or LaunchOptions is required to be non-null.");
        }
        if (allocation == null) {
            jArr = null;
        } else {
            long[] jArr2 = this.mInIdsBuffer;
            jArr2[0] = allocation.getID(this.mRS);
            jArr = jArr2;
        }
        if (allocation2 == null) {
            j = 0;
        } else {
            j = allocation2.getID(this.mRS);
        }
        if (fieldPacker == null) {
            bArr = null;
        } else {
            bArr = fieldPacker.getData();
        }
        if (launchOptions == null) {
            iArr = null;
        } else {
            iArr = new int[]{launchOptions.xstart, launchOptions.xend, launchOptions.ystart, launchOptions.yend, launchOptions.zstart, launchOptions.zend};
        }
        this.mRS.nScriptForEach(getID(this.mRS), i, jArr, j, bArr, iArr);
    }

    protected void forEach(int i, android.renderscript.Allocation[] allocationArr, android.renderscript.Allocation allocation, android.renderscript.FieldPacker fieldPacker) {
        forEach(i, allocationArr, allocation, fieldPacker, (android.renderscript.Script.LaunchOptions) null);
    }

    protected void forEach(int i, android.renderscript.Allocation[] allocationArr, android.renderscript.Allocation allocation, android.renderscript.FieldPacker fieldPacker, android.renderscript.Script.LaunchOptions launchOptions) {
        long[] jArr;
        long j;
        byte[] bArr;
        int[] iArr;
        this.mRS.validate();
        if (allocationArr != null) {
            for (android.renderscript.Allocation allocation2 : allocationArr) {
                this.mRS.validateObject(allocation2);
            }
        }
        this.mRS.validateObject(allocation);
        if (allocationArr == null && allocation == null) {
            throw new android.renderscript.RSIllegalArgumentException("At least one of ain or aout is required to be non-null.");
        }
        if (allocationArr != null) {
            long[] jArr2 = new long[allocationArr.length];
            for (int i2 = 0; i2 < allocationArr.length; i2++) {
                jArr2[i2] = allocationArr[i2].getID(this.mRS);
            }
            jArr = jArr2;
        } else {
            jArr = null;
        }
        if (allocation == null) {
            j = 0;
        } else {
            j = allocation.getID(this.mRS);
        }
        if (fieldPacker == null) {
            bArr = null;
        } else {
            bArr = fieldPacker.getData();
        }
        if (launchOptions == null) {
            iArr = null;
        } else {
            iArr = new int[]{launchOptions.xstart, launchOptions.xend, launchOptions.ystart, launchOptions.yend, launchOptions.zstart, launchOptions.zend};
        }
        this.mRS.nScriptForEach(getID(this.mRS), i, jArr, j, bArr, iArr);
    }

    protected void reduce(int i, android.renderscript.Allocation[] allocationArr, android.renderscript.Allocation allocation, android.renderscript.Script.LaunchOptions launchOptions) {
        int[] iArr;
        this.mRS.validate();
        if (allocationArr == null || allocationArr.length < 1) {
            throw new android.renderscript.RSIllegalArgumentException("At least one input is required.");
        }
        if (allocation == null) {
            throw new android.renderscript.RSIllegalArgumentException("aout is required to be non-null.");
        }
        for (android.renderscript.Allocation allocation2 : allocationArr) {
            this.mRS.validateObject(allocation2);
        }
        long[] jArr = new long[allocationArr.length];
        for (int i2 = 0; i2 < allocationArr.length; i2++) {
            jArr[i2] = allocationArr[i2].getID(this.mRS);
        }
        long id = allocation.getID(this.mRS);
        if (launchOptions == null) {
            iArr = null;
        } else {
            iArr = new int[]{launchOptions.xstart, launchOptions.xend, launchOptions.ystart, launchOptions.yend, launchOptions.zstart, launchOptions.zend};
        }
        this.mRS.nScriptReduce(getID(this.mRS), i, jArr, id, iArr);
    }

    Script(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
        this.mKIDs = new android.util.SparseArray<>();
        this.mIIDs = new android.util.SparseArray<>();
        this.mFIDs = new android.util.SparseArray<>();
        this.mInIdsBuffer = new long[1];
        this.guard.open("destroy");
    }

    public void bindAllocation(android.renderscript.Allocation allocation, int i) {
        this.mRS.validate();
        this.mRS.validateObject(allocation);
        if (allocation != null) {
            if (this.mRS.getApplicationContext().getApplicationInfo().targetSdkVersion >= 20) {
                android.renderscript.Type type = allocation.mType;
                if (type.hasMipmaps() || type.hasFaces() || type.getY() != 0 || type.getZ() != 0) {
                    throw new android.renderscript.RSIllegalArgumentException("API 20+ only allows simple 1D allocations to be used with bind.");
                }
            }
            this.mRS.nScriptBindAllocation(getID(this.mRS), allocation.getID(this.mRS), i);
            return;
        }
        this.mRS.nScriptBindAllocation(getID(this.mRS), 0L, i);
    }

    public void setVar(int i, float f) {
        this.mRS.nScriptSetVarF(getID(this.mRS), i, f);
    }

    public float getVarF(int i) {
        return this.mRS.nScriptGetVarF(getID(this.mRS), i);
    }

    public void setVar(int i, double d) {
        this.mRS.nScriptSetVarD(getID(this.mRS), i, d);
    }

    public double getVarD(int i) {
        return this.mRS.nScriptGetVarD(getID(this.mRS), i);
    }

    public void setVar(int i, int i2) {
        this.mRS.nScriptSetVarI(getID(this.mRS), i, i2);
    }

    public int getVarI(int i) {
        return this.mRS.nScriptGetVarI(getID(this.mRS), i);
    }

    public void setVar(int i, long j) {
        this.mRS.nScriptSetVarJ(getID(this.mRS), i, j);
    }

    public long getVarJ(int i) {
        return this.mRS.nScriptGetVarJ(getID(this.mRS), i);
    }

    public void setVar(int i, boolean z) {
        this.mRS.nScriptSetVarI(getID(this.mRS), i, z ? 1 : 0);
    }

    public boolean getVarB(int i) {
        return this.mRS.nScriptGetVarI(getID(this.mRS), i) > 0;
    }

    public void setVar(int i, android.renderscript.BaseObj baseObj) {
        this.mRS.validate();
        this.mRS.validateObject(baseObj);
        this.mRS.nScriptSetVarObj(getID(this.mRS), i, baseObj == null ? 0L : baseObj.getID(this.mRS));
    }

    public void setVar(int i, android.renderscript.FieldPacker fieldPacker) {
        this.mRS.nScriptSetVarV(getID(this.mRS), i, fieldPacker.getData());
    }

    public void setVar(int i, android.renderscript.FieldPacker fieldPacker, android.renderscript.Element element, int[] iArr) {
        this.mRS.nScriptSetVarVE(getID(this.mRS), i, fieldPacker.getData(), element.getID(this.mRS), iArr);
    }

    public void getVarV(int i, android.renderscript.FieldPacker fieldPacker) {
        this.mRS.nScriptGetVarV(getID(this.mRS), i, fieldPacker.getData());
    }

    public void setTimeZone(java.lang.String str) {
        this.mRS.validate();
        try {
            this.mRS.nScriptSetTimeZone(getID(this.mRS), str.getBytes(android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8));
        } catch (java.io.UnsupportedEncodingException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public static class Builder {
        android.renderscript.RenderScript mRS;

        Builder(android.renderscript.RenderScript renderScript) {
            this.mRS = renderScript;
        }
    }

    public static class FieldBase {
        protected android.renderscript.Allocation mAllocation;
        protected android.renderscript.Element mElement;

        protected void init(android.renderscript.RenderScript renderScript, int i) {
            this.mAllocation = android.renderscript.Allocation.createSized(renderScript, this.mElement, i, 1);
        }

        protected void init(android.renderscript.RenderScript renderScript, int i, int i2) {
            this.mAllocation = android.renderscript.Allocation.createSized(renderScript, this.mElement, i, i2 | 1);
        }

        protected FieldBase() {
        }

        public android.renderscript.Element getElement() {
            return this.mElement;
        }

        public android.renderscript.Type getType() {
            return this.mAllocation.getType();
        }

        public android.renderscript.Allocation getAllocation() {
            return this.mAllocation;
        }

        public void updateAllocation() {
        }
    }

    public static final class LaunchOptions {
        private int strategy;
        private int xstart = 0;
        private int ystart = 0;
        private int xend = 0;
        private int yend = 0;
        private int zstart = 0;
        private int zend = 0;

        public android.renderscript.Script.LaunchOptions setX(int i, int i2) {
            if (i < 0 || i2 <= i) {
                throw new android.renderscript.RSIllegalArgumentException("Invalid dimensions");
            }
            this.xstart = i;
            this.xend = i2;
            return this;
        }

        public android.renderscript.Script.LaunchOptions setY(int i, int i2) {
            if (i < 0 || i2 <= i) {
                throw new android.renderscript.RSIllegalArgumentException("Invalid dimensions");
            }
            this.ystart = i;
            this.yend = i2;
            return this;
        }

        public android.renderscript.Script.LaunchOptions setZ(int i, int i2) {
            if (i < 0 || i2 <= i) {
                throw new android.renderscript.RSIllegalArgumentException("Invalid dimensions");
            }
            this.zstart = i;
            this.zend = i2;
            return this;
        }

        public int getXStart() {
            return this.xstart;
        }

        public int getXEnd() {
            return this.xend;
        }

        public int getYStart() {
            return this.ystart;
        }

        public int getYEnd() {
            return this.yend;
        }

        public int getZStart() {
            return this.zstart;
        }

        public int getZEnd() {
            return this.zend;
        }
    }
}
