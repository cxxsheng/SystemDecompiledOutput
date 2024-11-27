package android.filterfw.core;

/* loaded from: classes.dex */
public class FilterGraph {
    public static final int AUTOBRANCH_OFF = 0;
    public static final int AUTOBRANCH_SYNCED = 1;
    public static final int AUTOBRANCH_UNSYNCED = 2;
    public static final int TYPECHECK_DYNAMIC = 1;
    public static final int TYPECHECK_OFF = 0;
    public static final int TYPECHECK_STRICT = 2;
    private java.util.HashSet<android.filterfw.core.Filter> mFilters = new java.util.HashSet<>();
    private java.util.HashMap<java.lang.String, android.filterfw.core.Filter> mNameMap = new java.util.HashMap<>();
    private java.util.HashMap<android.filterfw.core.OutputPort, java.util.LinkedList<android.filterfw.core.InputPort>> mPreconnections = new java.util.HashMap<>();
    private boolean mIsReady = false;
    private int mAutoBranchMode = 0;
    private int mTypeCheckMode = 2;
    private boolean mDiscardUnconnectedOutputs = false;
    private java.lang.String TAG = "FilterGraph";
    private boolean mLogVerbose = android.util.Log.isLoggable(this.TAG, 2);

    public boolean addFilter(android.filterfw.core.Filter filter) {
        if (!containsFilter(filter)) {
            this.mFilters.add(filter);
            this.mNameMap.put(filter.getName(), filter);
            return true;
        }
        return false;
    }

    public boolean containsFilter(android.filterfw.core.Filter filter) {
        return this.mFilters.contains(filter);
    }

    public android.filterfw.core.Filter getFilter(java.lang.String str) {
        return this.mNameMap.get(str);
    }

    public void connect(android.filterfw.core.Filter filter, java.lang.String str, android.filterfw.core.Filter filter2, java.lang.String str2) {
        if (filter == null || filter2 == null) {
            throw new java.lang.IllegalArgumentException("Passing null Filter in connect()!");
        }
        if (!containsFilter(filter) || !containsFilter(filter2)) {
            throw new java.lang.RuntimeException("Attempting to connect filter not in graph!");
        }
        android.filterfw.core.OutputPort outputPort = filter.getOutputPort(str);
        android.filterfw.core.InputPort inputPort = filter2.getInputPort(str2);
        if (outputPort == null) {
            throw new java.lang.RuntimeException("Unknown output port '" + str + "' on Filter " + filter + "!");
        }
        if (inputPort == null) {
            throw new java.lang.RuntimeException("Unknown input port '" + str2 + "' on Filter " + filter2 + "!");
        }
        preconnect(outputPort, inputPort);
    }

    public void connect(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        android.filterfw.core.Filter filter = getFilter(str);
        android.filterfw.core.Filter filter2 = getFilter(str3);
        if (filter == null) {
            throw new java.lang.RuntimeException("Attempting to connect unknown source filter '" + str + "'!");
        }
        if (filter2 == null) {
            throw new java.lang.RuntimeException("Attempting to connect unknown target filter '" + str3 + "'!");
        }
        connect(filter, str2, filter2, str4);
    }

    public java.util.Set<android.filterfw.core.Filter> getFilters() {
        return this.mFilters;
    }

    public void beginProcessing() {
        if (this.mLogVerbose) {
            android.util.Log.v(this.TAG, "Opening all filter connections...");
        }
        java.util.Iterator<android.filterfw.core.Filter> it = this.mFilters.iterator();
        while (it.hasNext()) {
            it.next().openOutputs();
        }
        this.mIsReady = true;
    }

    public void flushFrames() {
        java.util.Iterator<android.filterfw.core.Filter> it = this.mFilters.iterator();
        while (it.hasNext()) {
            it.next().clearOutputs();
        }
    }

    public void closeFilters(android.filterfw.core.FilterContext filterContext) {
        if (this.mLogVerbose) {
            android.util.Log.v(this.TAG, "Closing all filters...");
        }
        java.util.Iterator<android.filterfw.core.Filter> it = this.mFilters.iterator();
        while (it.hasNext()) {
            it.next().performClose(filterContext);
        }
        this.mIsReady = false;
    }

    public boolean isReady() {
        return this.mIsReady;
    }

    public void setAutoBranchMode(int i) {
        this.mAutoBranchMode = i;
    }

    public void setDiscardUnconnectedOutputs(boolean z) {
        this.mDiscardUnconnectedOutputs = z;
    }

    public void setTypeCheckMode(int i) {
        this.mTypeCheckMode = i;
    }

    public void tearDown(android.filterfw.core.FilterContext filterContext) {
        if (!this.mFilters.isEmpty()) {
            flushFrames();
            java.util.Iterator<android.filterfw.core.Filter> it = this.mFilters.iterator();
            while (it.hasNext()) {
                it.next().performTearDown(filterContext);
            }
            this.mFilters.clear();
            this.mNameMap.clear();
            this.mIsReady = false;
        }
    }

    private boolean readyForProcessing(android.filterfw.core.Filter filter, java.util.Set<android.filterfw.core.Filter> set) {
        if (set.contains(filter)) {
            return false;
        }
        java.util.Iterator<android.filterfw.core.InputPort> it = filter.getInputPorts().iterator();
        while (it.hasNext()) {
            android.filterfw.core.Filter sourceFilter = it.next().getSourceFilter();
            if (sourceFilter != null && !set.contains(sourceFilter)) {
                return false;
            }
        }
        return true;
    }

    private void runTypeCheck() {
        java.util.Stack stack = new java.util.Stack();
        java.util.HashSet hashSet = new java.util.HashSet();
        stack.addAll(getSourceFilters());
        while (!stack.empty()) {
            android.filterfw.core.Filter filter = (android.filterfw.core.Filter) stack.pop();
            hashSet.add(filter);
            updateOutputs(filter);
            if (this.mLogVerbose) {
                android.util.Log.v(this.TAG, "Running type check on " + filter + android.telecom.Logging.Session.TRUNCATE_STRING);
            }
            runTypeCheckOn(filter);
            java.util.Iterator<android.filterfw.core.OutputPort> it = filter.getOutputPorts().iterator();
            while (it.hasNext()) {
                android.filterfw.core.Filter targetFilter = it.next().getTargetFilter();
                if (targetFilter != null && readyForProcessing(targetFilter, hashSet)) {
                    stack.push(targetFilter);
                }
            }
        }
        if (hashSet.size() != getFilters().size()) {
            throw new java.lang.RuntimeException("Could not schedule all filters! Is your graph malformed?");
        }
    }

    private void updateOutputs(android.filterfw.core.Filter filter) {
        for (android.filterfw.core.OutputPort outputPort : filter.getOutputPorts()) {
            android.filterfw.core.InputPort basePort = outputPort.getBasePort();
            if (basePort != null) {
                android.filterfw.core.FrameFormat outputFormat = filter.getOutputFormat(outputPort.getName(), basePort.getSourceFormat());
                if (outputFormat == null) {
                    throw new java.lang.RuntimeException("Filter did not return an output format for " + outputPort + "!");
                }
                outputPort.setPortFormat(outputFormat);
            }
        }
    }

    private void runTypeCheckOn(android.filterfw.core.Filter filter) {
        for (android.filterfw.core.InputPort inputPort : filter.getInputPorts()) {
            if (this.mLogVerbose) {
                android.util.Log.v(this.TAG, "Type checking port " + inputPort);
            }
            android.filterfw.core.FrameFormat sourceFormat = inputPort.getSourceFormat();
            android.filterfw.core.FrameFormat portFormat = inputPort.getPortFormat();
            if (sourceFormat != null && portFormat != null) {
                if (this.mLogVerbose) {
                    android.util.Log.v(this.TAG, "Checking " + sourceFormat + " against " + portFormat + android.media.MediaMetrics.SEPARATOR);
                }
                boolean z = true;
                switch (this.mTypeCheckMode) {
                    case 0:
                        inputPort.setChecksType(false);
                        break;
                    case 1:
                        boolean mayBeCompatibleWith = sourceFormat.mayBeCompatibleWith(portFormat);
                        inputPort.setChecksType(true);
                        z = mayBeCompatibleWith;
                        break;
                    case 2:
                        z = sourceFormat.isCompatibleWith(portFormat);
                        inputPort.setChecksType(false);
                        break;
                }
                if (!z) {
                    throw new java.lang.RuntimeException("Type mismatch: Filter " + filter + " expects a format of type " + portFormat + " but got a format of type " + sourceFormat + "!");
                }
            }
        }
    }

    private void checkConnections() {
    }

    private void discardUnconnectedOutputs() {
        java.util.LinkedList linkedList = new java.util.LinkedList();
        java.util.Iterator<android.filterfw.core.Filter> it = this.mFilters.iterator();
        while (it.hasNext()) {
            android.filterfw.core.Filter next = it.next();
            int i = 0;
            for (android.filterfw.core.OutputPort outputPort : next.getOutputPorts()) {
                if (!outputPort.isConnected()) {
                    if (this.mLogVerbose) {
                        android.util.Log.v(this.TAG, "Autoconnecting unconnected " + outputPort + " to Null filter.");
                    }
                    android.filterpacks.base.NullFilter nullFilter = new android.filterpacks.base.NullFilter(next.getName() + "ToNull" + i);
                    nullFilter.init();
                    linkedList.add(nullFilter);
                    outputPort.connectTo(nullFilter.getInputPort("frame"));
                    i++;
                }
            }
        }
        java.util.Iterator it2 = linkedList.iterator();
        while (it2.hasNext()) {
            addFilter((android.filterfw.core.Filter) it2.next());
        }
    }

    private void removeFilter(android.filterfw.core.Filter filter) {
        this.mFilters.remove(filter);
        this.mNameMap.remove(filter.getName());
    }

    private void preconnect(android.filterfw.core.OutputPort outputPort, android.filterfw.core.InputPort inputPort) {
        java.util.LinkedList<android.filterfw.core.InputPort> linkedList = this.mPreconnections.get(outputPort);
        if (linkedList == null) {
            linkedList = new java.util.LinkedList<>();
            this.mPreconnections.put(outputPort, linkedList);
        }
        linkedList.add(inputPort);
    }

    private void connectPorts() {
        int i = 1;
        for (java.util.Map.Entry<android.filterfw.core.OutputPort, java.util.LinkedList<android.filterfw.core.InputPort>> entry : this.mPreconnections.entrySet()) {
            android.filterfw.core.OutputPort key = entry.getKey();
            java.util.LinkedList<android.filterfw.core.InputPort> value = entry.getValue();
            if (value.size() == 1) {
                key.connectTo(value.get(0));
            } else {
                if (this.mAutoBranchMode == 0) {
                    throw new java.lang.RuntimeException("Attempting to connect " + key + " to multiple filter ports! Enable auto-branching to allow this.");
                }
                if (this.mLogVerbose) {
                    android.util.Log.v(this.TAG, "Creating branch for " + key + "!");
                }
                if (this.mAutoBranchMode == 1) {
                    int i2 = i + 1;
                    android.filterpacks.base.FrameBranch frameBranch = new android.filterpacks.base.FrameBranch("branch" + i);
                    new android.filterfw.core.KeyValueMap();
                    frameBranch.initWithAssignmentList("outputs", java.lang.Integer.valueOf(value.size()));
                    addFilter(frameBranch);
                    key.connectTo(frameBranch.getInputPort("in"));
                    java.util.Iterator<android.filterfw.core.InputPort> it = value.iterator();
                    java.util.Iterator<android.filterfw.core.OutputPort> it2 = frameBranch.getOutputPorts().iterator();
                    while (it2.hasNext()) {
                        it2.next().connectTo(it.next());
                    }
                    i = i2;
                } else {
                    throw new java.lang.RuntimeException("TODO: Unsynced branches not implemented yet!");
                }
            }
        }
        this.mPreconnections.clear();
    }

    private java.util.HashSet<android.filterfw.core.Filter> getSourceFilters() {
        java.util.HashSet<android.filterfw.core.Filter> hashSet = new java.util.HashSet<>();
        for (android.filterfw.core.Filter filter : getFilters()) {
            if (filter.getNumberOfConnectedInputs() == 0) {
                if (this.mLogVerbose) {
                    android.util.Log.v(this.TAG, "Found source filter: " + filter);
                }
                hashSet.add(filter);
            }
        }
        return hashSet;
    }

    void setupFilters() {
        if (this.mDiscardUnconnectedOutputs) {
            discardUnconnectedOutputs();
        }
        connectPorts();
        checkConnections();
        runTypeCheck();
    }
}
