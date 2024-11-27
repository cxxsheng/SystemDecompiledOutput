package android.filterfw.core;

/* loaded from: classes.dex */
public class FrameFormat {
    public static final int BYTES_PER_SAMPLE_UNSPECIFIED = 1;
    protected static final int SIZE_UNKNOWN = -1;
    public static final int SIZE_UNSPECIFIED = 0;
    public static final int TARGET_GPU = 3;
    public static final int TARGET_NATIVE = 2;
    public static final int TARGET_RS = 5;
    public static final int TARGET_SIMPLE = 1;
    public static final int TARGET_UNSPECIFIED = 0;
    public static final int TARGET_VERTEXBUFFER = 4;
    public static final int TYPE_BIT = 1;
    public static final int TYPE_BYTE = 2;
    public static final int TYPE_DOUBLE = 6;
    public static final int TYPE_FLOAT = 5;
    public static final int TYPE_INT16 = 3;
    public static final int TYPE_INT32 = 4;
    public static final int TYPE_OBJECT = 8;
    public static final int TYPE_POINTER = 7;
    public static final int TYPE_UNSPECIFIED = 0;
    protected int mBaseType;
    protected int mBytesPerSample;
    protected int[] mDimensions;
    protected android.filterfw.core.KeyValueMap mMetaData;
    protected java.lang.Class mObjectClass;
    protected int mSize;
    protected int mTarget;

    protected FrameFormat() {
        this.mBaseType = 0;
        this.mBytesPerSample = 1;
        this.mSize = -1;
        this.mTarget = 0;
    }

    public FrameFormat(int i, int i2) {
        this.mBaseType = 0;
        this.mBytesPerSample = 1;
        this.mSize = -1;
        this.mTarget = 0;
        this.mBaseType = i;
        this.mTarget = i2;
        initDefaults();
    }

    public static android.filterfw.core.FrameFormat unspecified() {
        return new android.filterfw.core.FrameFormat(0, 0);
    }

    public int getBaseType() {
        return this.mBaseType;
    }

    public boolean isBinaryDataType() {
        return this.mBaseType >= 1 && this.mBaseType <= 6;
    }

    public int getBytesPerSample() {
        return this.mBytesPerSample;
    }

    public int getValuesPerSample() {
        return this.mBytesPerSample / bytesPerSampleOf(this.mBaseType);
    }

    public int getTarget() {
        return this.mTarget;
    }

    public int[] getDimensions() {
        return this.mDimensions;
    }

    public int getDimension(int i) {
        return this.mDimensions[i];
    }

    public int getDimensionCount() {
        if (this.mDimensions == null) {
            return 0;
        }
        return this.mDimensions.length;
    }

    public boolean hasMetaKey(java.lang.String str) {
        if (this.mMetaData != null) {
            return this.mMetaData.containsKey(str);
        }
        return false;
    }

    public boolean hasMetaKey(java.lang.String str, java.lang.Class cls) {
        if (this.mMetaData != null && this.mMetaData.containsKey(str)) {
            if (!cls.isAssignableFrom(this.mMetaData.get(str).getClass())) {
                throw new java.lang.RuntimeException("FrameFormat meta-key '" + str + "' is of type " + this.mMetaData.get(str).getClass() + " but expected to be of type " + cls + "!");
            }
            return true;
        }
        return false;
    }

    public java.lang.Object getMetaValue(java.lang.String str) {
        if (this.mMetaData != null) {
            return this.mMetaData.get(str);
        }
        return null;
    }

    public int getNumberOfDimensions() {
        if (this.mDimensions != null) {
            return this.mDimensions.length;
        }
        return 0;
    }

    public int getLength() {
        if (this.mDimensions == null || this.mDimensions.length < 1) {
            return -1;
        }
        return this.mDimensions[0];
    }

    public int getWidth() {
        return getLength();
    }

    public int getHeight() {
        if (this.mDimensions == null || this.mDimensions.length < 2) {
            return -1;
        }
        return this.mDimensions[1];
    }

    public int getDepth() {
        if (this.mDimensions == null || this.mDimensions.length < 3) {
            return -1;
        }
        return this.mDimensions[2];
    }

    public int getSize() {
        if (this.mSize == -1) {
            this.mSize = calcSize(this.mDimensions);
        }
        return this.mSize;
    }

    public java.lang.Class getObjectClass() {
        return this.mObjectClass;
    }

    public android.filterfw.core.MutableFrameFormat mutableCopy() {
        android.filterfw.core.MutableFrameFormat mutableFrameFormat = new android.filterfw.core.MutableFrameFormat();
        mutableFrameFormat.setBaseType(getBaseType());
        mutableFrameFormat.setTarget(getTarget());
        mutableFrameFormat.setBytesPerSample(getBytesPerSample());
        mutableFrameFormat.setDimensions(getDimensions());
        mutableFrameFormat.setObjectClass(getObjectClass());
        mutableFrameFormat.mMetaData = this.mMetaData == null ? null : (android.filterfw.core.KeyValueMap) this.mMetaData.clone();
        return mutableFrameFormat;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.filterfw.core.FrameFormat)) {
            return false;
        }
        android.filterfw.core.FrameFormat frameFormat = (android.filterfw.core.FrameFormat) obj;
        return frameFormat.mBaseType == this.mBaseType && frameFormat.mTarget == this.mTarget && frameFormat.mBytesPerSample == this.mBytesPerSample && java.util.Arrays.equals(frameFormat.mDimensions, this.mDimensions) && frameFormat.mMetaData.equals(this.mMetaData);
    }

    public int hashCode() {
        return ((this.mBaseType ^ 4211) ^ this.mBytesPerSample) ^ getSize();
    }

    public boolean isCompatibleWith(android.filterfw.core.FrameFormat frameFormat) {
        if (frameFormat.getBaseType() != 0 && getBaseType() != frameFormat.getBaseType()) {
            return false;
        }
        if (frameFormat.getTarget() != 0 && getTarget() != frameFormat.getTarget()) {
            return false;
        }
        if (frameFormat.getBytesPerSample() != 1 && getBytesPerSample() != frameFormat.getBytesPerSample()) {
            return false;
        }
        if (frameFormat.getDimensionCount() > 0 && getDimensionCount() != frameFormat.getDimensionCount()) {
            return false;
        }
        for (int i = 0; i < frameFormat.getDimensionCount(); i++) {
            int dimension = frameFormat.getDimension(i);
            if (dimension != 0 && getDimension(i) != dimension) {
                return false;
            }
        }
        if (frameFormat.getObjectClass() != null && (getObjectClass() == null || !frameFormat.getObjectClass().isAssignableFrom(getObjectClass()))) {
            return false;
        }
        if (frameFormat.mMetaData != null) {
            for (java.lang.String str : frameFormat.mMetaData.keySet()) {
                if (this.mMetaData == null || !this.mMetaData.containsKey(str) || !this.mMetaData.get(str).equals(frameFormat.mMetaData.get(str))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean mayBeCompatibleWith(android.filterfw.core.FrameFormat frameFormat) {
        if (frameFormat.getBaseType() != 0 && getBaseType() != 0 && getBaseType() != frameFormat.getBaseType()) {
            return false;
        }
        if (frameFormat.getTarget() != 0 && getTarget() != 0 && getTarget() != frameFormat.getTarget()) {
            return false;
        }
        if (frameFormat.getBytesPerSample() != 1 && getBytesPerSample() != 1 && getBytesPerSample() != frameFormat.getBytesPerSample()) {
            return false;
        }
        if (frameFormat.getDimensionCount() > 0 && getDimensionCount() > 0 && getDimensionCount() != frameFormat.getDimensionCount()) {
            return false;
        }
        for (int i = 0; i < frameFormat.getDimensionCount(); i++) {
            int dimension = frameFormat.getDimension(i);
            if (dimension != 0 && getDimension(i) != 0 && getDimension(i) != dimension) {
                return false;
            }
        }
        if (frameFormat.getObjectClass() != null && getObjectClass() != null && !frameFormat.getObjectClass().isAssignableFrom(getObjectClass())) {
            return false;
        }
        if (frameFormat.mMetaData != null && this.mMetaData != null) {
            for (java.lang.String str : frameFormat.mMetaData.keySet()) {
                if (this.mMetaData.containsKey(str) && !this.mMetaData.get(str).equals(frameFormat.mMetaData.get(str))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int bytesPerSampleOf(int i) {
        switch (i) {
        }
        return 1;
    }

    public static java.lang.String dimensionsToString(int[] iArr) {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        if (iArr != null) {
            int length = iArr.length;
            for (int i = 0; i < length; i++) {
                if (iArr[i] == 0) {
                    stringBuffer.append("[]");
                } else {
                    stringBuffer.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + java.lang.String.valueOf(iArr[i]) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
                }
            }
        }
        return stringBuffer.toString();
    }

    public static java.lang.String baseTypeToString(int i) {
        switch (i) {
        }
        return android.app.slice.SliceItem.FORMAT_INT;
    }

    public static java.lang.String targetToString(int i) {
        switch (i) {
            case 0:
                return "unspecified";
            case 1:
                return "simple";
            case 2:
                return "native";
            case 3:
                return "gpu";
            case 4:
                return "vbo";
            case 5:
                return "renderscript";
            default:
                return "unknown";
        }
    }

    public static java.lang.String metaDataToString(android.filterfw.core.KeyValueMap keyValueMap) {
        if (keyValueMap == null) {
            return "";
        }
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("{ ");
        for (java.util.Map.Entry<java.lang.String, java.lang.Object> entry : keyValueMap.entrySet()) {
            stringBuffer.append(entry.getKey() + ": " + entry.getValue() + " ");
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public static int readTargetString(java.lang.String str) {
        if (str.equalsIgnoreCase("CPU") || str.equalsIgnoreCase("NATIVE")) {
            return 2;
        }
        if (str.equalsIgnoreCase("GPU")) {
            return 3;
        }
        if (str.equalsIgnoreCase("SIMPLE")) {
            return 1;
        }
        if (str.equalsIgnoreCase("VERTEXBUFFER")) {
            return 4;
        }
        if (str.equalsIgnoreCase("UNSPECIFIED")) {
            return 0;
        }
        throw new java.lang.RuntimeException("Unknown target type '" + str + "'!");
    }

    public java.lang.String toString() {
        int valuesPerSample = getValuesPerSample();
        return (this.mTarget == 0 ? "" : targetToString(this.mTarget) + " ") + baseTypeToString(this.mBaseType) + (valuesPerSample == 1 ? "" : java.lang.String.valueOf(valuesPerSample)) + dimensionsToString(this.mDimensions) + (this.mObjectClass != null ? " class(" + this.mObjectClass.getSimpleName() + ") " : "") + metaDataToString(this.mMetaData);
    }

    private void initDefaults() {
        this.mBytesPerSample = bytesPerSampleOf(this.mBaseType);
    }

    int calcSize(int[] iArr) {
        if (iArr == null || iArr.length <= 0) {
            return 0;
        }
        int bytesPerSample = getBytesPerSample();
        for (int i : iArr) {
            bytesPerSample *= i;
        }
        return bytesPerSample;
    }

    boolean isReplaceableBy(android.filterfw.core.FrameFormat frameFormat) {
        return this.mTarget == frameFormat.mTarget && getSize() == frameFormat.getSize() && java.util.Arrays.equals(frameFormat.mDimensions, this.mDimensions);
    }
}
