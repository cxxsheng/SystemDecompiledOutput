package android.filterfw.core;

/* loaded from: classes.dex */
public class NativeProgram extends android.filterfw.core.Program {
    private boolean mHasGetValueFunction;
    private boolean mHasInitFunction;
    private boolean mHasResetFunction;
    private boolean mHasSetValueFunction;
    private boolean mHasTeardownFunction;
    private boolean mTornDown = false;
    private int nativeProgramId;

    private native boolean allocate();

    private native boolean bindGetValueFunction(java.lang.String str);

    private native boolean bindInitFunction(java.lang.String str);

    private native boolean bindProcessFunction(java.lang.String str);

    private native boolean bindResetFunction(java.lang.String str);

    private native boolean bindSetValueFunction(java.lang.String str);

    private native boolean bindTeardownFunction(java.lang.String str);

    private native java.lang.String callNativeGetValue(java.lang.String str);

    private native boolean callNativeInit();

    private native boolean callNativeProcess(android.filterfw.core.NativeFrame[] nativeFrameArr, android.filterfw.core.NativeFrame nativeFrame);

    private native boolean callNativeReset();

    private native boolean callNativeSetValue(java.lang.String str, java.lang.String str2);

    private native boolean callNativeTeardown();

    private native boolean deallocate();

    private native boolean nativeInit();

    private native boolean openNativeLibrary(java.lang.String str);

    public NativeProgram(java.lang.String str, java.lang.String str2) {
        this.mHasInitFunction = false;
        this.mHasTeardownFunction = false;
        this.mHasSetValueFunction = false;
        this.mHasGetValueFunction = false;
        this.mHasResetFunction = false;
        allocate();
        java.lang.String str3 = com.android.internal.content.NativeLibraryHelper.LIB_DIR_NAME + str + ".so";
        if (!openNativeLibrary(str3)) {
            throw new java.lang.RuntimeException("Could not find native library named '" + str3 + "' required for native program!");
        }
        java.lang.String str4 = str2 + "_process";
        if (!bindProcessFunction(str4)) {
            throw new java.lang.RuntimeException("Could not find native program function name " + str4 + " in library " + str3 + "! This function is required!");
        }
        this.mHasInitFunction = bindInitFunction(str2 + "_init");
        this.mHasTeardownFunction = bindTeardownFunction(str2 + "_teardown");
        this.mHasSetValueFunction = bindSetValueFunction(str2 + "_setvalue");
        this.mHasGetValueFunction = bindGetValueFunction(str2 + "_getvalue");
        this.mHasResetFunction = bindResetFunction(str2 + "_reset");
        if (this.mHasInitFunction && !callNativeInit()) {
            throw new java.lang.RuntimeException("Could not initialize NativeProgram!");
        }
    }

    public void tearDown() {
        if (this.mTornDown) {
            return;
        }
        if (this.mHasTeardownFunction && !callNativeTeardown()) {
            throw new java.lang.RuntimeException("Could not tear down NativeProgram!");
        }
        deallocate();
        this.mTornDown = true;
    }

    @Override // android.filterfw.core.Program
    public void reset() {
        if (this.mHasResetFunction && !callNativeReset()) {
            throw new java.lang.RuntimeException("Could not reset NativeProgram!");
        }
    }

    protected void finalize() throws java.lang.Throwable {
        tearDown();
    }

    @Override // android.filterfw.core.Program
    public void process(android.filterfw.core.Frame[] frameArr, android.filterfw.core.Frame frame) {
        if (this.mTornDown) {
            throw new java.lang.RuntimeException("NativeProgram already torn down!");
        }
        android.filterfw.core.NativeFrame[] nativeFrameArr = new android.filterfw.core.NativeFrame[frameArr.length];
        for (int i = 0; i < frameArr.length; i++) {
            if (frameArr[i] == null || (frameArr[i] instanceof android.filterfw.core.NativeFrame)) {
                nativeFrameArr[i] = (android.filterfw.core.NativeFrame) frameArr[i];
            } else {
                throw new java.lang.RuntimeException("NativeProgram got non-native frame as input " + i + "!");
            }
        }
        if (frame != null && !(frame instanceof android.filterfw.core.NativeFrame)) {
            throw new java.lang.RuntimeException("NativeProgram got non-native output frame!");
        }
        if (!callNativeProcess(nativeFrameArr, (android.filterfw.core.NativeFrame) frame)) {
            throw new java.lang.RuntimeException("Calling native process() caused error!");
        }
    }

    @Override // android.filterfw.core.Program
    public void setHostValue(java.lang.String str, java.lang.Object obj) {
        if (this.mTornDown) {
            throw new java.lang.RuntimeException("NativeProgram already torn down!");
        }
        if (!this.mHasSetValueFunction) {
            throw new java.lang.RuntimeException("Attempting to set native variable, but native code does not define native setvalue function!");
        }
        if (!callNativeSetValue(str, obj.toString())) {
            throw new java.lang.RuntimeException("Error setting native value for variable '" + str + "'!");
        }
    }

    @Override // android.filterfw.core.Program
    public java.lang.Object getHostValue(java.lang.String str) {
        if (this.mTornDown) {
            throw new java.lang.RuntimeException("NativeProgram already torn down!");
        }
        if (!this.mHasGetValueFunction) {
            throw new java.lang.RuntimeException("Attempting to get native variable, but native code does not define native getvalue function!");
        }
        return callNativeGetValue(str);
    }

    static {
        java.lang.System.loadLibrary("filterfw");
    }
}
