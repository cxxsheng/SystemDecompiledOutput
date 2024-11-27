package android.filterfw.core;

/* loaded from: classes.dex */
public abstract class Filter {
    static final int STATUS_ERROR = 6;
    static final int STATUS_FINISHED = 5;
    static final int STATUS_PREINIT = 0;
    static final int STATUS_PREPARED = 2;
    static final int STATUS_PROCESSING = 3;
    static final int STATUS_RELEASED = 7;
    static final int STATUS_SLEEPING = 4;
    static final int STATUS_UNPREPARED = 1;
    private static final java.lang.String TAG = "Filter";
    private long mCurrentTimestamp;
    private java.util.HashMap<java.lang.String, android.filterfw.core.InputPort> mInputPorts;
    private java.lang.String mName;
    private java.util.HashMap<java.lang.String, android.filterfw.core.OutputPort> mOutputPorts;
    private int mSleepDelay;
    private int mStatus;
    private int mInputCount = -1;
    private int mOutputCount = -1;
    private boolean mIsOpen = false;
    private java.util.HashSet<android.filterfw.core.Frame> mFramesToRelease = new java.util.HashSet<>();
    private java.util.HashMap<java.lang.String, android.filterfw.core.Frame> mFramesToSet = new java.util.HashMap<>();
    private boolean mLogVerbose = android.util.Log.isLoggable(TAG, 2);

    public abstract void process(android.filterfw.core.FilterContext filterContext);

    public abstract void setupPorts();

    public Filter(java.lang.String str) {
        this.mStatus = 0;
        this.mName = str;
        this.mStatus = 0;
    }

    public static final boolean isAvailable(java.lang.String str) {
        try {
            if (!android.filterfw.core.Filter.class.isAssignableFrom(java.lang.Thread.currentThread().getContextClassLoader().loadClass(str))) {
                return false;
            }
            return true;
        } catch (java.lang.ClassNotFoundException e) {
            return false;
        }
    }

    public final void initWithValueMap(android.filterfw.core.KeyValueMap keyValueMap) {
        initFinalPorts(keyValueMap);
        initRemainingPorts(keyValueMap);
        this.mStatus = 1;
    }

    public final void initWithAssignmentString(java.lang.String str) {
        try {
            initWithValueMap(new android.filterfw.io.TextGraphReader().readKeyValueAssignments(str));
        } catch (android.filterfw.io.GraphIOException e) {
            throw new java.lang.IllegalArgumentException(e.getMessage());
        }
    }

    public final void initWithAssignmentList(java.lang.Object... objArr) {
        android.filterfw.core.KeyValueMap keyValueMap = new android.filterfw.core.KeyValueMap();
        keyValueMap.setKeyValues(objArr);
        initWithValueMap(keyValueMap);
    }

    public final void init() throws android.filterfw.core.ProtocolException {
        initWithValueMap(new android.filterfw.core.KeyValueMap());
    }

    public java.lang.String getFilterClassName() {
        return getClass().getSimpleName();
    }

    public final java.lang.String getName() {
        return this.mName;
    }

    public boolean isOpen() {
        return this.mIsOpen;
    }

    public void setInputFrame(java.lang.String str, android.filterfw.core.Frame frame) {
        android.filterfw.core.InputPort inputPort = getInputPort(str);
        if (!inputPort.isOpen()) {
            inputPort.open();
        }
        inputPort.setFrame(frame);
    }

    public final void setInputValue(java.lang.String str, java.lang.Object obj) {
        setInputFrame(str, wrapInputValue(str, obj));
    }

    protected void prepare(android.filterfw.core.FilterContext filterContext) {
    }

    protected void parametersUpdated(java.util.Set<java.lang.String> set) {
    }

    protected void delayNextProcess(int i) {
        this.mSleepDelay = i;
        this.mStatus = 4;
    }

    public android.filterfw.core.FrameFormat getOutputFormat(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        return null;
    }

    public final android.filterfw.core.FrameFormat getInputFormat(java.lang.String str) {
        return getInputPort(str).getSourceFormat();
    }

    public void open(android.filterfw.core.FilterContext filterContext) {
    }

    public final int getSleepDelay() {
        return 250;
    }

    public void close(android.filterfw.core.FilterContext filterContext) {
    }

    public void tearDown(android.filterfw.core.FilterContext filterContext) {
    }

    public final int getNumberOfConnectedInputs() {
        java.util.Iterator<android.filterfw.core.InputPort> it = this.mInputPorts.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isConnected()) {
                i++;
            }
        }
        return i;
    }

    public final int getNumberOfConnectedOutputs() {
        java.util.Iterator<android.filterfw.core.OutputPort> it = this.mOutputPorts.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isConnected()) {
                i++;
            }
        }
        return i;
    }

    public final int getNumberOfInputs() {
        if (this.mOutputPorts == null) {
            return 0;
        }
        return this.mInputPorts.size();
    }

    public final int getNumberOfOutputs() {
        if (this.mInputPorts == null) {
            return 0;
        }
        return this.mOutputPorts.size();
    }

    public final android.filterfw.core.InputPort getInputPort(java.lang.String str) {
        if (this.mInputPorts == null) {
            throw new java.lang.NullPointerException("Attempting to access input port '" + str + "' of " + this + " before Filter has been initialized!");
        }
        android.filterfw.core.InputPort inputPort = this.mInputPorts.get(str);
        if (inputPort == null) {
            throw new java.lang.IllegalArgumentException("Unknown input port '" + str + "' on filter " + this + "!");
        }
        return inputPort;
    }

    public final android.filterfw.core.OutputPort getOutputPort(java.lang.String str) {
        if (this.mInputPorts == null) {
            throw new java.lang.NullPointerException("Attempting to access output port '" + str + "' of " + this + " before Filter has been initialized!");
        }
        android.filterfw.core.OutputPort outputPort = this.mOutputPorts.get(str);
        if (outputPort == null) {
            throw new java.lang.IllegalArgumentException("Unknown output port '" + str + "' on filter " + this + "!");
        }
        return outputPort;
    }

    protected final void pushOutput(java.lang.String str, android.filterfw.core.Frame frame) {
        if (frame.getTimestamp() == -2) {
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Default-setting output Frame timestamp on port " + str + " to " + this.mCurrentTimestamp);
            }
            frame.setTimestamp(this.mCurrentTimestamp);
        }
        getOutputPort(str).pushFrame(frame);
    }

    protected final android.filterfw.core.Frame pullInput(java.lang.String str) {
        android.filterfw.core.Frame pullFrame = getInputPort(str).pullFrame();
        if (this.mCurrentTimestamp == -1) {
            this.mCurrentTimestamp = pullFrame.getTimestamp();
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Default-setting current timestamp from input port " + str + " to " + this.mCurrentTimestamp);
            }
        }
        this.mFramesToRelease.add(pullFrame);
        return pullFrame;
    }

    public void fieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
    }

    protected void transferInputPortFrame(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        getInputPort(str).transfer(filterContext);
    }

    protected void initProgramInputs(android.filterfw.core.Program program, android.filterfw.core.FilterContext filterContext) {
        if (program != null) {
            for (android.filterfw.core.InputPort inputPort : this.mInputPorts.values()) {
                if (inputPort.getTarget() == program) {
                    inputPort.transfer(filterContext);
                }
            }
        }
    }

    protected void addInputPort(java.lang.String str) {
        addMaskedInputPort(str, null);
    }

    protected void addMaskedInputPort(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        android.filterfw.core.StreamPort streamPort = new android.filterfw.core.StreamPort(this, str);
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Filter " + this + " adding " + streamPort);
        }
        this.mInputPorts.put(str, streamPort);
        streamPort.setPortFormat(frameFormat);
    }

    protected void addOutputPort(java.lang.String str, android.filterfw.core.FrameFormat frameFormat) {
        android.filterfw.core.OutputPort outputPort = new android.filterfw.core.OutputPort(this, str);
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Filter " + this + " adding " + outputPort);
        }
        outputPort.setPortFormat(frameFormat);
        this.mOutputPorts.put(str, outputPort);
    }

    protected void addOutputBasedOnInput(java.lang.String str, java.lang.String str2) {
        android.filterfw.core.OutputPort outputPort = new android.filterfw.core.OutputPort(this, str);
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Filter " + this + " adding " + outputPort);
        }
        outputPort.setBasePort(getInputPort(str2));
        this.mOutputPorts.put(str, outputPort);
    }

    protected void addFieldPort(java.lang.String str, java.lang.reflect.Field field, boolean z, boolean z2) {
        android.filterfw.core.InputPort fieldPort;
        field.setAccessible(true);
        if (z2) {
            fieldPort = new android.filterfw.core.FinalPort(this, str, field, z);
        } else {
            fieldPort = new android.filterfw.core.FieldPort(this, str, field, z);
        }
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Filter " + this + " adding " + fieldPort);
        }
        fieldPort.setPortFormat(android.filterfw.format.ObjectFormat.fromClass(field.getType(), 1));
        this.mInputPorts.put(str, fieldPort);
    }

    protected void addProgramPort(java.lang.String str, java.lang.String str2, java.lang.reflect.Field field, java.lang.Class cls, boolean z) {
        field.setAccessible(true);
        android.filterfw.core.ProgramPort programPort = new android.filterfw.core.ProgramPort(this, str, str2, field, z);
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Filter " + this + " adding " + programPort);
        }
        programPort.setPortFormat(android.filterfw.format.ObjectFormat.fromClass(cls, 1));
        this.mInputPorts.put(str, programPort);
    }

    protected void closeOutputPort(java.lang.String str) {
        getOutputPort(str).close();
    }

    protected void setWaitsOnInputPort(java.lang.String str, boolean z) {
        getInputPort(str).setBlocking(z);
    }

    protected void setWaitsOnOutputPort(java.lang.String str, boolean z) {
        getOutputPort(str).setBlocking(z);
    }

    public java.lang.String toString() {
        return "'" + getName() + "' (" + getFilterClassName() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    final java.util.Collection<android.filterfw.core.InputPort> getInputPorts() {
        return this.mInputPorts.values();
    }

    final java.util.Collection<android.filterfw.core.OutputPort> getOutputPorts() {
        return this.mOutputPorts.values();
    }

    final synchronized int getStatus() {
        return this.mStatus;
    }

    final synchronized void unsetStatus(int i) {
        this.mStatus = (~i) & this.mStatus;
    }

    final synchronized void performOpen(android.filterfw.core.FilterContext filterContext) {
        if (!this.mIsOpen) {
            if (this.mStatus == 1) {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Preparing " + this);
                }
                prepare(filterContext);
                this.mStatus = 2;
            }
            if (this.mStatus == 2) {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Opening " + this);
                }
                open(filterContext);
                this.mStatus = 3;
            }
            if (this.mStatus != 3) {
                throw new java.lang.RuntimeException("Filter " + this + " was brought into invalid state during opening (state: " + this.mStatus + ")!");
            }
            this.mIsOpen = true;
        }
    }

    final synchronized void performProcess(android.filterfw.core.FilterContext filterContext) {
        if (this.mStatus == 7) {
            throw new java.lang.RuntimeException("Filter " + this + " is already torn down!");
        }
        transferInputFrames(filterContext);
        if (this.mStatus < 3) {
            performOpen(filterContext);
        }
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Processing " + this);
        }
        this.mCurrentTimestamp = -1L;
        process(filterContext);
        releasePulledFrames(filterContext);
        if (filterMustClose()) {
            performClose(filterContext);
        }
    }

    final synchronized void performClose(android.filterfw.core.FilterContext filterContext) {
        if (this.mIsOpen) {
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Closing " + this);
            }
            this.mIsOpen = false;
            this.mStatus = 2;
            close(filterContext);
            closePorts();
        }
    }

    final synchronized void performTearDown(android.filterfw.core.FilterContext filterContext) {
        performClose(filterContext);
        if (this.mStatus != 7) {
            tearDown(filterContext);
            this.mStatus = 7;
        }
    }

    final synchronized boolean canProcess() {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Checking if can process: " + this + " (" + this.mStatus + ").");
        }
        boolean z = false;
        if (this.mStatus > 3) {
            return false;
        }
        if (inputConditionsMet()) {
            if (outputConditionsMet()) {
                z = true;
            }
        }
        return z;
    }

    final void openOutputs() {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Opening all output ports on " + this + "!");
        }
        for (android.filterfw.core.OutputPort outputPort : this.mOutputPorts.values()) {
            if (!outputPort.isOpen()) {
                outputPort.open();
            }
        }
    }

    final void clearInputs() {
        java.util.Iterator<android.filterfw.core.InputPort> it = this.mInputPorts.values().iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    final void clearOutputs() {
        java.util.Iterator<android.filterfw.core.OutputPort> it = this.mOutputPorts.values().iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    final void notifyFieldPortValueUpdated(java.lang.String str, android.filterfw.core.FilterContext filterContext) {
        if (this.mStatus == 3 || this.mStatus == 2) {
            fieldPortValueUpdated(str, filterContext);
        }
    }

    final synchronized void pushInputFrame(java.lang.String str, android.filterfw.core.Frame frame) {
        android.filterfw.core.InputPort inputPort = getInputPort(str);
        if (!inputPort.isOpen()) {
            inputPort.open();
        }
        inputPort.pushFrame(frame);
    }

    final synchronized void pushInputValue(java.lang.String str, java.lang.Object obj) {
        pushInputFrame(str, wrapInputValue(str, obj));
    }

    private final void initFinalPorts(android.filterfw.core.KeyValueMap keyValueMap) {
        this.mInputPorts = new java.util.HashMap<>();
        this.mOutputPorts = new java.util.HashMap<>();
        addAndSetFinalPorts(keyValueMap);
    }

    private final void initRemainingPorts(android.filterfw.core.KeyValueMap keyValueMap) {
        addAnnotatedPorts();
        setupPorts();
        setInitialInputValues(keyValueMap);
    }

    private final void addAndSetFinalPorts(android.filterfw.core.KeyValueMap keyValueMap) {
        for (java.lang.reflect.Field field : getClass().getDeclaredFields()) {
            java.lang.annotation.Annotation annotation = field.getAnnotation(android.filterfw.core.GenerateFinalPort.class);
            if (annotation != null) {
                android.filterfw.core.GenerateFinalPort generateFinalPort = (android.filterfw.core.GenerateFinalPort) annotation;
                java.lang.String name = generateFinalPort.name().isEmpty() ? field.getName() : generateFinalPort.name();
                addFieldPort(name, field, generateFinalPort.hasDefault(), true);
                if (keyValueMap.containsKey(name)) {
                    setImmediateInputValue(name, keyValueMap.get(name));
                    keyValueMap.remove(name);
                } else if (!generateFinalPort.hasDefault()) {
                    throw new java.lang.RuntimeException("No value specified for final input port '" + name + "' of filter " + this + "!");
                }
            }
        }
    }

    private final void addAnnotatedPorts() {
        for (java.lang.reflect.Field field : getClass().getDeclaredFields()) {
            java.lang.annotation.Annotation annotation = field.getAnnotation(android.filterfw.core.GenerateFieldPort.class);
            if (annotation != null) {
                addFieldGenerator((android.filterfw.core.GenerateFieldPort) annotation, field);
            } else {
                java.lang.annotation.Annotation annotation2 = field.getAnnotation(android.filterfw.core.GenerateProgramPort.class);
                if (annotation2 != null) {
                    addProgramGenerator((android.filterfw.core.GenerateProgramPort) annotation2, field);
                } else {
                    java.lang.annotation.Annotation annotation3 = field.getAnnotation(android.filterfw.core.GenerateProgramPorts.class);
                    if (annotation3 != null) {
                        for (android.filterfw.core.GenerateProgramPort generateProgramPort : ((android.filterfw.core.GenerateProgramPorts) annotation3).value()) {
                            addProgramGenerator(generateProgramPort, field);
                        }
                    }
                }
            }
        }
    }

    private final void addFieldGenerator(android.filterfw.core.GenerateFieldPort generateFieldPort, java.lang.reflect.Field field) {
        addFieldPort(generateFieldPort.name().isEmpty() ? field.getName() : generateFieldPort.name(), field, generateFieldPort.hasDefault(), false);
    }

    private final void addProgramGenerator(android.filterfw.core.GenerateProgramPort generateProgramPort, java.lang.reflect.Field field) {
        java.lang.String name = generateProgramPort.name();
        addProgramPort(name, generateProgramPort.variableName().isEmpty() ? name : generateProgramPort.variableName(), field, generateProgramPort.type(), generateProgramPort.hasDefault());
    }

    private final void setInitialInputValues(android.filterfw.core.KeyValueMap keyValueMap) {
        for (java.util.Map.Entry<java.lang.String, java.lang.Object> entry : keyValueMap.entrySet()) {
            setInputValue(entry.getKey(), entry.getValue());
        }
    }

    private final void setImmediateInputValue(java.lang.String str, java.lang.Object obj) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Setting immediate value " + obj + " for port " + str + "!");
        }
        android.filterfw.core.InputPort inputPort = getInputPort(str);
        inputPort.open();
        inputPort.setFrame(android.filterfw.core.SimpleFrame.wrapObject(obj, null));
    }

    private final void transferInputFrames(android.filterfw.core.FilterContext filterContext) {
        java.util.Iterator<android.filterfw.core.InputPort> it = this.mInputPorts.values().iterator();
        while (it.hasNext()) {
            it.next().transfer(filterContext);
        }
    }

    private final android.filterfw.core.Frame wrapInputValue(java.lang.String str, java.lang.Object obj) {
        android.filterfw.core.Frame simpleFrame;
        android.filterfw.core.MutableFrameFormat fromObject = android.filterfw.format.ObjectFormat.fromObject(obj, 1);
        if (obj == null) {
            android.filterfw.core.FrameFormat portFormat = getInputPort(str).getPortFormat();
            fromObject.setObjectClass(portFormat == null ? null : portFormat.getObjectClass());
        }
        if (((obj instanceof java.lang.Number) || (obj instanceof java.lang.Boolean) || (obj instanceof java.lang.String) || !(obj instanceof java.io.Serializable)) ? false : true) {
            simpleFrame = new android.filterfw.core.SerializedFrame(fromObject, null);
        } else {
            simpleFrame = new android.filterfw.core.SimpleFrame(fromObject, null);
        }
        simpleFrame.setObjectValue(obj);
        return simpleFrame;
    }

    private final void releasePulledFrames(android.filterfw.core.FilterContext filterContext) {
        java.util.Iterator<android.filterfw.core.Frame> it = this.mFramesToRelease.iterator();
        while (it.hasNext()) {
            filterContext.getFrameManager().releaseFrame(it.next());
        }
        this.mFramesToRelease.clear();
    }

    private final boolean inputConditionsMet() {
        for (android.filterfw.core.InputPort inputPort : this.mInputPorts.values()) {
            if (!inputPort.isReady()) {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Input condition not met: " + inputPort + "!");
                    return false;
                }
                return false;
            }
        }
        return true;
    }

    private final boolean outputConditionsMet() {
        for (android.filterfw.core.OutputPort outputPort : this.mOutputPorts.values()) {
            if (!outputPort.isReady()) {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Output condition not met: " + outputPort + "!");
                    return false;
                }
                return false;
            }
        }
        return true;
    }

    private final void closePorts() {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Closing all ports on " + this + "!");
        }
        java.util.Iterator<android.filterfw.core.InputPort> it = this.mInputPorts.values().iterator();
        while (it.hasNext()) {
            it.next().close();
        }
        java.util.Iterator<android.filterfw.core.OutputPort> it2 = this.mOutputPorts.values().iterator();
        while (it2.hasNext()) {
            it2.next().close();
        }
    }

    private final boolean filterMustClose() {
        for (android.filterfw.core.InputPort inputPort : this.mInputPorts.values()) {
            if (inputPort.filterMustClose()) {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Filter " + this + " must close due to port " + inputPort);
                }
                return true;
            }
        }
        for (android.filterfw.core.OutputPort outputPort : this.mOutputPorts.values()) {
            if (outputPort.filterMustClose()) {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Filter " + this + " must close due to port " + outputPort);
                }
                return true;
            }
        }
        return false;
    }
}
