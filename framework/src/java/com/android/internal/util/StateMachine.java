package com.android.internal.util;

/* loaded from: classes5.dex */
public class StateMachine {
    public static final boolean HANDLED = true;
    public static final boolean NOT_HANDLED = false;
    private static final int SM_INIT_CMD = -2;
    private static final int SM_QUIT_CMD = -1;
    private java.lang.String mName;
    private com.android.internal.util.StateMachine.SmHandler mSmHandler;
    private android.os.HandlerThread mSmThread;

    public static class LogRec {
        private com.android.internal.util.IState mDstState;
        private java.lang.String mInfo;
        private com.android.internal.util.IState mOrgState;
        private com.android.internal.util.StateMachine mSm;
        private com.android.internal.util.IState mState;
        private long mTime;
        private int mWhat;

        LogRec(com.android.internal.util.StateMachine stateMachine, android.os.Message message, java.lang.String str, com.android.internal.util.IState iState, com.android.internal.util.IState iState2, com.android.internal.util.IState iState3) {
            update(stateMachine, message, str, iState, iState2, iState3);
        }

        public void update(com.android.internal.util.StateMachine stateMachine, android.os.Message message, java.lang.String str, com.android.internal.util.IState iState, com.android.internal.util.IState iState2, com.android.internal.util.IState iState3) {
            this.mSm = stateMachine;
            this.mTime = java.lang.System.currentTimeMillis();
            this.mWhat = message != null ? message.what : 0;
            this.mInfo = str;
            this.mState = iState;
            this.mOrgState = iState2;
            this.mDstState = iState3;
        }

        public long getTime() {
            return this.mTime;
        }

        public long getWhat() {
            return this.mWhat;
        }

        public java.lang.String getInfo() {
            return this.mInfo;
        }

        public com.android.internal.util.IState getState() {
            return this.mState;
        }

        public com.android.internal.util.IState getDestState() {
            return this.mDstState;
        }

        public com.android.internal.util.IState getOriginalState() {
            return this.mOrgState;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("time=");
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTimeInMillis(this.mTime);
            sb.append(java.lang.String.format("%tm-%td %tH:%tM:%tS.%tL", calendar, calendar, calendar, calendar, calendar, calendar));
            sb.append(" processed=");
            sb.append(this.mState == null ? "<null>" : this.mState.getName());
            sb.append(" org=");
            sb.append(this.mOrgState == null ? "<null>" : this.mOrgState.getName());
            sb.append(" dest=");
            sb.append(this.mDstState != null ? this.mDstState.getName() : "<null>");
            sb.append(" what=");
            java.lang.String whatToString = this.mSm != null ? this.mSm.getWhatToString(this.mWhat) : "";
            if (android.text.TextUtils.isEmpty(whatToString)) {
                sb.append(this.mWhat);
                sb.append("(0x");
                sb.append(java.lang.Integer.toHexString(this.mWhat));
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            } else {
                sb.append(whatToString);
            }
            if (!android.text.TextUtils.isEmpty(this.mInfo)) {
                sb.append(" ");
                sb.append(this.mInfo);
            }
            return sb.toString();
        }
    }

    private static class LogRecords {
        private static final int DEFAULT_SIZE = 20;
        private int mCount;
        private boolean mLogOnlyTransitions;
        private java.util.Vector<com.android.internal.util.StateMachine.LogRec> mLogRecVector;
        private int mMaxSize;
        private int mOldestIndex;

        private LogRecords() {
            this.mLogRecVector = new java.util.Vector<>();
            this.mMaxSize = 20;
            this.mOldestIndex = 0;
            this.mCount = 0;
            this.mLogOnlyTransitions = false;
        }

        synchronized void setSize(int i) {
            this.mMaxSize = i;
            this.mOldestIndex = 0;
            this.mCount = 0;
            this.mLogRecVector.clear();
        }

        synchronized void setLogOnlyTransitions(boolean z) {
            this.mLogOnlyTransitions = z;
        }

        synchronized boolean logOnlyTransitions() {
            return this.mLogOnlyTransitions;
        }

        synchronized int size() {
            return this.mLogRecVector.size();
        }

        synchronized int count() {
            return this.mCount;
        }

        synchronized void cleanup() {
            this.mLogRecVector.clear();
        }

        synchronized com.android.internal.util.StateMachine.LogRec get(int i) {
            int i2 = this.mOldestIndex + i;
            if (i2 >= this.mMaxSize) {
                i2 -= this.mMaxSize;
            }
            if (i2 >= size()) {
                return null;
            }
            return this.mLogRecVector.get(i2);
        }

        synchronized void add(com.android.internal.util.StateMachine stateMachine, android.os.Message message, java.lang.String str, com.android.internal.util.IState iState, com.android.internal.util.IState iState2, com.android.internal.util.IState iState3) {
            this.mCount++;
            if (this.mLogRecVector.size() < this.mMaxSize) {
                this.mLogRecVector.add(new com.android.internal.util.StateMachine.LogRec(stateMachine, message, str, iState, iState2, iState3));
            } else {
                com.android.internal.util.StateMachine.LogRec logRec = this.mLogRecVector.get(this.mOldestIndex);
                this.mOldestIndex++;
                if (this.mOldestIndex >= this.mMaxSize) {
                    this.mOldestIndex = 0;
                }
                logRec.update(stateMachine, message, str, iState, iState2, iState3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SmHandler extends android.os.Handler {
        private static final java.lang.Object mSmHandlerObj = new java.lang.Object();
        private boolean mDbg;
        private final java.util.ArrayList<android.os.Message> mDeferredMessages;
        private com.android.internal.util.State mDestState;
        private final com.android.internal.util.StateMachine.SmHandler.HaltingState mHaltingState;
        private boolean mHasQuit;
        private com.android.internal.util.State mInitialState;
        private boolean mIsConstructionCompleted;
        private final com.android.internal.util.StateMachine.LogRecords mLogRecords;
        private android.os.Message mMsg;
        private final com.android.internal.util.StateMachine.SmHandler.QuittingState mQuittingState;
        private com.android.internal.util.StateMachine mSm;
        private final java.util.HashMap<com.android.internal.util.State, com.android.internal.util.StateMachine.SmHandler.StateInfo> mStateInfo;
        private com.android.internal.util.StateMachine.SmHandler.StateInfo[] mStateStack;
        private int mStateStackTopIndex;
        private com.android.internal.util.StateMachine.SmHandler.StateInfo[] mTempStateStack;
        private int mTempStateStackCount;
        private boolean mTransitionInProgress;

        /* JADX INFO: Access modifiers changed from: private */
        static class StateInfo {
            boolean active = false;
            final com.android.internal.util.StateMachine.SmHandler.StateInfo parentStateInfo;
            final com.android.internal.util.State state;

            StateInfo(com.android.internal.util.State state, com.android.internal.util.StateMachine.SmHandler.StateInfo stateInfo) {
                this.state = state;
                this.parentStateInfo = stateInfo;
            }

            public java.lang.String toString() {
                return "state=" + this.state.getName() + ",active=" + this.active + ",parent=" + (this.parentStateInfo == null ? "null" : this.parentStateInfo.state.getName());
            }
        }

        private class HaltingState extends com.android.internal.util.State {
            private HaltingState() {
            }

            @Override // com.android.internal.util.State, com.android.internal.util.IState
            public boolean processMessage(android.os.Message message) {
                com.android.internal.util.StateMachine.SmHandler.this.mSm.haltedProcessMessage(message);
                return true;
            }
        }

        private static class QuittingState extends com.android.internal.util.State {
            private QuittingState() {
            }

            @Override // com.android.internal.util.State, com.android.internal.util.IState
            public boolean processMessage(android.os.Message message) {
                return false;
            }
        }

        @Override // android.os.Handler
        public final void handleMessage(android.os.Message message) {
            com.android.internal.util.State processMsg;
            if (!this.mHasQuit) {
                if (this.mSm != null && message.what != -2 && message.what != -1) {
                    this.mSm.onPreHandleMessage(message);
                }
                if (this.mDbg) {
                    this.mSm.log("handleMessage: E msg.what=" + message.what);
                }
                this.mMsg = message;
                if (this.mIsConstructionCompleted || this.mMsg.what == -1) {
                    processMsg = processMsg(message);
                } else if (this.mMsg.what == -2 && this.mMsg.obj == mSmHandlerObj) {
                    this.mIsConstructionCompleted = true;
                    invokeEnterMethods(0);
                    processMsg = null;
                } else {
                    throw new java.lang.RuntimeException("StateMachine.handleMessage: The start method not called, received msg: " + message);
                }
                performTransitions(processMsg, message);
                if (this.mDbg && this.mSm != null) {
                    this.mSm.log("handleMessage: X");
                }
                if (this.mSm != null && message.what != -2 && message.what != -1) {
                    this.mSm.onPostHandleMessage(message);
                }
            }
        }

        private void performTransitions(com.android.internal.util.State state, android.os.Message message) {
            com.android.internal.util.State state2 = this.mStateStack[this.mStateStackTopIndex].state;
            boolean z = this.mSm.recordLogRec(this.mMsg) && message.obj != mSmHandlerObj;
            if (this.mLogRecords.logOnlyTransitions()) {
                if (this.mDestState != null) {
                    this.mLogRecords.add(this.mSm, this.mMsg, this.mSm.getLogRecString(this.mMsg), state, state2, this.mDestState);
                }
            } else if (z) {
                this.mLogRecords.add(this.mSm, this.mMsg, this.mSm.getLogRecString(this.mMsg), state, state2, this.mDestState);
            }
            com.android.internal.util.State state3 = this.mDestState;
            if (state3 != null) {
                while (true) {
                    if (this.mDbg) {
                        this.mSm.log("handleMessage: new destination call exit/enter");
                    }
                    com.android.internal.util.StateMachine.SmHandler.StateInfo stateInfo = setupTempStateStackWithStatesToEnter(state3);
                    this.mTransitionInProgress = true;
                    invokeExitMethods(stateInfo);
                    invokeEnterMethods(moveTempStateStackToStateStack());
                    moveDeferredMessageAtFrontOfQueue();
                    if (state3 == this.mDestState) {
                        break;
                    } else {
                        state3 = this.mDestState;
                    }
                }
                this.mDestState = null;
            }
            if (state3 != null) {
                if (state3 == this.mQuittingState) {
                    this.mSm.onQuitting();
                    cleanupAfterQuitting();
                } else if (state3 == this.mHaltingState) {
                    this.mSm.onHalting();
                }
            }
        }

        private final void cleanupAfterQuitting() {
            if (this.mSm.mSmThread != null) {
                getLooper().quit();
                this.mSm.mSmThread = null;
            }
            this.mSm.mSmHandler = null;
            this.mSm = null;
            this.mMsg = null;
            this.mLogRecords.cleanup();
            this.mStateStack = null;
            this.mTempStateStack = null;
            this.mStateInfo.clear();
            this.mInitialState = null;
            this.mDestState = null;
            this.mDeferredMessages.clear();
            this.mHasQuit = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void completeConstruction() {
            if (this.mDbg) {
                this.mSm.log("completeConstruction: E");
            }
            int i = 0;
            for (com.android.internal.util.StateMachine.SmHandler.StateInfo stateInfo : this.mStateInfo.values()) {
                int i2 = 0;
                while (stateInfo != null) {
                    stateInfo = stateInfo.parentStateInfo;
                    i2++;
                }
                if (i < i2) {
                    i = i2;
                }
            }
            if (this.mDbg) {
                this.mSm.log("completeConstruction: maxDepth=" + i);
            }
            this.mStateStack = new com.android.internal.util.StateMachine.SmHandler.StateInfo[i];
            this.mTempStateStack = new com.android.internal.util.StateMachine.SmHandler.StateInfo[i];
            setupInitialStateStack();
            sendMessageAtFrontOfQueue(obtainMessage(-2, mSmHandlerObj));
            if (this.mDbg) {
                this.mSm.log("completeConstruction: X");
            }
        }

        private final com.android.internal.util.State processMsg(android.os.Message message) {
            com.android.internal.util.StateMachine.SmHandler.StateInfo stateInfo = this.mStateStack[this.mStateStackTopIndex];
            if (this.mDbg) {
                this.mSm.log("processMsg: " + stateInfo.state.getName());
            }
            if (isQuit(message)) {
                transitionTo(this.mQuittingState);
            } else {
                while (true) {
                    if (stateInfo.state.processMessage(message)) {
                        break;
                    }
                    stateInfo = stateInfo.parentStateInfo;
                    if (stateInfo == null) {
                        this.mSm.unhandledMessage(message);
                        break;
                    }
                    if (this.mDbg) {
                        this.mSm.log("processMsg: " + stateInfo.state.getName());
                    }
                }
            }
            if (stateInfo != null) {
                return stateInfo.state;
            }
            return null;
        }

        private final void invokeExitMethods(com.android.internal.util.StateMachine.SmHandler.StateInfo stateInfo) {
            while (this.mStateStackTopIndex >= 0 && this.mStateStack[this.mStateStackTopIndex] != stateInfo) {
                com.android.internal.util.State state = this.mStateStack[this.mStateStackTopIndex].state;
                if (this.mDbg) {
                    this.mSm.log("invokeExitMethods: " + state.getName());
                }
                state.exit();
                this.mStateStack[this.mStateStackTopIndex].active = false;
                this.mStateStackTopIndex--;
            }
        }

        private final void invokeEnterMethods(int i) {
            for (int i2 = i; i2 <= this.mStateStackTopIndex; i2++) {
                if (i == this.mStateStackTopIndex) {
                    this.mTransitionInProgress = false;
                }
                if (this.mDbg) {
                    this.mSm.log("invokeEnterMethods: " + this.mStateStack[i2].state.getName());
                }
                this.mStateStack[i2].state.enter();
                this.mStateStack[i2].active = true;
            }
            this.mTransitionInProgress = false;
        }

        private final void moveDeferredMessageAtFrontOfQueue() {
            for (int size = this.mDeferredMessages.size() - 1; size >= 0; size--) {
                android.os.Message message = this.mDeferredMessages.get(size);
                if (this.mDbg) {
                    this.mSm.log("moveDeferredMessageAtFrontOfQueue; what=" + message.what);
                }
                sendMessageAtFrontOfQueue(message);
            }
            this.mDeferredMessages.clear();
        }

        private final int moveTempStateStackToStateStack() {
            int i = this.mStateStackTopIndex + 1;
            int i2 = i;
            for (int i3 = this.mTempStateStackCount - 1; i3 >= 0; i3--) {
                if (this.mDbg) {
                    this.mSm.log("moveTempStackToStateStack: i=" + i3 + ",j=" + i2);
                }
                this.mStateStack[i2] = this.mTempStateStack[i3];
                i2++;
            }
            this.mStateStackTopIndex = i2 - 1;
            if (this.mDbg) {
                this.mSm.log("moveTempStackToStateStack: X mStateStackTop=" + this.mStateStackTopIndex + ",startingIndex=" + i + ",Top=" + this.mStateStack[this.mStateStackTopIndex].state.getName());
            }
            return i;
        }

        private final com.android.internal.util.StateMachine.SmHandler.StateInfo setupTempStateStackWithStatesToEnter(com.android.internal.util.State state) {
            this.mTempStateStackCount = 0;
            com.android.internal.util.StateMachine.SmHandler.StateInfo stateInfo = this.mStateInfo.get(state);
            do {
                com.android.internal.util.StateMachine.SmHandler.StateInfo[] stateInfoArr = this.mTempStateStack;
                int i = this.mTempStateStackCount;
                this.mTempStateStackCount = i + 1;
                stateInfoArr[i] = stateInfo;
                stateInfo = stateInfo.parentStateInfo;
                if (stateInfo == null) {
                    break;
                }
            } while (!stateInfo.active);
            if (this.mDbg) {
                this.mSm.log("setupTempStateStackWithStatesToEnter: X mTempStateStackCount=" + this.mTempStateStackCount + ",curStateInfo: " + stateInfo);
            }
            return stateInfo;
        }

        private final void setupInitialStateStack() {
            if (this.mDbg) {
                this.mSm.log("setupInitialStateStack: E mInitialState=" + this.mInitialState.getName());
            }
            com.android.internal.util.StateMachine.SmHandler.StateInfo stateInfo = this.mStateInfo.get(this.mInitialState);
            int i = 0;
            while (true) {
                this.mTempStateStackCount = i;
                if (stateInfo != null) {
                    this.mTempStateStack[this.mTempStateStackCount] = stateInfo;
                    stateInfo = stateInfo.parentStateInfo;
                    i = this.mTempStateStackCount + 1;
                } else {
                    this.mStateStackTopIndex = -1;
                    moveTempStateStackToStateStack();
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final android.os.Message getCurrentMessage() {
            return this.mMsg;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final com.android.internal.util.IState getCurrentState() {
            return this.mStateStack[this.mStateStackTopIndex].state;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final com.android.internal.util.StateMachine.SmHandler.StateInfo addState(com.android.internal.util.State state, com.android.internal.util.State state2) {
            if (this.mDbg) {
                this.mSm.log("addStateInternal: E state=" + state.getName() + ",parent=" + (state2 == null ? "" : state2.getName()));
            }
            com.android.internal.util.StateMachine.SmHandler.StateInfo stateInfo = null;
            if (state2 != null) {
                com.android.internal.util.StateMachine.SmHandler.StateInfo stateInfo2 = this.mStateInfo.get(state2);
                if (stateInfo2 != null) {
                    stateInfo = stateInfo2;
                } else {
                    stateInfo = addState(state2, null);
                }
            }
            com.android.internal.util.StateMachine.SmHandler.StateInfo stateInfo3 = this.mStateInfo.get(state);
            if (stateInfo3 == null) {
                stateInfo3 = new com.android.internal.util.StateMachine.SmHandler.StateInfo(state, stateInfo);
                this.mStateInfo.put(state, stateInfo3);
            }
            if (stateInfo3.parentStateInfo != null && stateInfo3.parentStateInfo != stateInfo) {
                throw new java.lang.RuntimeException("state already added");
            }
            if (this.mDbg) {
                this.mSm.log("addStateInternal: X stateInfo: " + stateInfo3);
            }
            return stateInfo3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeState(com.android.internal.util.State state) {
            final com.android.internal.util.StateMachine.SmHandler.StateInfo stateInfo = this.mStateInfo.get(state);
            if (stateInfo == null || stateInfo.active || this.mStateInfo.values().stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.internal.util.StateMachine$SmHandler$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return com.android.internal.util.StateMachine.SmHandler.lambda$removeState$0(com.android.internal.util.StateMachine.SmHandler.StateInfo.this, (com.android.internal.util.StateMachine.SmHandler.StateInfo) obj);
                }
            })) {
                return;
            }
            this.mStateInfo.remove(state);
        }

        static /* synthetic */ boolean lambda$removeState$0(com.android.internal.util.StateMachine.SmHandler.StateInfo stateInfo, com.android.internal.util.StateMachine.SmHandler.StateInfo stateInfo2) {
            return stateInfo2.parentStateInfo == stateInfo;
        }

        private SmHandler(android.os.Looper looper, com.android.internal.util.StateMachine stateMachine) {
            super(looper);
            this.mHasQuit = false;
            this.mDbg = false;
            this.mLogRecords = new com.android.internal.util.StateMachine.LogRecords();
            this.mStateStackTopIndex = -1;
            this.mHaltingState = new com.android.internal.util.StateMachine.SmHandler.HaltingState();
            this.mQuittingState = new com.android.internal.util.StateMachine.SmHandler.QuittingState();
            this.mStateInfo = new java.util.HashMap<>();
            this.mTransitionInProgress = false;
            this.mDeferredMessages = new java.util.ArrayList<>();
            this.mSm = stateMachine;
            addState(this.mHaltingState, null);
            addState(this.mQuittingState, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void setInitialState(com.android.internal.util.State state) {
            if (this.mDbg) {
                this.mSm.log("setInitialState: initialState=" + state.getName());
            }
            this.mInitialState = state;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void transitionTo(com.android.internal.util.IState iState) {
            if (this.mTransitionInProgress) {
                android.util.Log.wtf(this.mSm.mName, "transitionTo called while transition already in progress to " + this.mDestState + ", new target state=" + iState);
            }
            this.mDestState = (com.android.internal.util.State) iState;
            if (this.mDbg) {
                this.mSm.log("transitionTo: destState=" + this.mDestState.getName());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void deferMessage(android.os.Message message) {
            if (this.mDbg) {
                this.mSm.log("deferMessage: msg=" + message.what);
            }
            android.os.Message obtainMessage = obtainMessage();
            obtainMessage.copyFrom(message);
            this.mDeferredMessages.add(obtainMessage);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void quit() {
            if (this.mDbg) {
                this.mSm.log("quit:");
            }
            sendMessage(obtainMessage(-1, mSmHandlerObj));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void quitNow() {
            if (this.mDbg) {
                this.mSm.log("quitNow:");
            }
            sendMessageAtFrontOfQueue(obtainMessage(-1, mSmHandlerObj));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isQuit(android.os.Message message) {
            return message.what == -1 && message.obj == mSmHandlerObj;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isDbg() {
            return this.mDbg;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void setDbg(boolean z) {
            this.mDbg = z;
        }
    }

    private void initStateMachine(java.lang.String str, android.os.Looper looper) {
        this.mName = str;
        this.mSmHandler = new com.android.internal.util.StateMachine.SmHandler(looper, this);
    }

    protected StateMachine(java.lang.String str) {
        this.mSmThread = new android.os.HandlerThread(str);
        this.mSmThread.start();
        initStateMachine(str, this.mSmThread.getLooper());
    }

    protected StateMachine(java.lang.String str, android.os.Looper looper) {
        initStateMachine(str, looper);
    }

    protected StateMachine(java.lang.String str, android.os.Handler handler) {
        initStateMachine(str, handler.getLooper());
    }

    protected void onPreHandleMessage(android.os.Message message) {
    }

    protected void onPostHandleMessage(android.os.Message message) {
    }

    public final void addState(com.android.internal.util.State state, com.android.internal.util.State state2) {
        this.mSmHandler.addState(state, state2);
    }

    public final void addState(com.android.internal.util.State state) {
        this.mSmHandler.addState(state, null);
    }

    public final void removeState(com.android.internal.util.State state) {
        this.mSmHandler.removeState(state);
    }

    public final void setInitialState(com.android.internal.util.State state) {
        this.mSmHandler.setInitialState(state);
    }

    public final android.os.Message getCurrentMessage() {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return null;
        }
        return smHandler.getCurrentMessage();
    }

    public final com.android.internal.util.IState getCurrentState() {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return null;
        }
        return smHandler.getCurrentState();
    }

    public final void transitionTo(com.android.internal.util.IState iState) {
        this.mSmHandler.transitionTo(iState);
    }

    public final void transitionToHaltingState() {
        this.mSmHandler.transitionTo(this.mSmHandler.mHaltingState);
    }

    public final void deferMessage(android.os.Message message) {
        this.mSmHandler.deferMessage(message);
    }

    protected void unhandledMessage(android.os.Message message) {
        if (this.mSmHandler.mDbg) {
            loge(" - unhandledMessage: msg.what=" + message.what);
        }
    }

    protected void haltedProcessMessage(android.os.Message message) {
    }

    protected void onHalting() {
    }

    protected void onQuitting() {
    }

    public final java.lang.String getName() {
        return this.mName;
    }

    public final void setLogRecSize(int i) {
        this.mSmHandler.mLogRecords.setSize(i);
    }

    public final void setLogOnlyTransitions(boolean z) {
        this.mSmHandler.mLogRecords.setLogOnlyTransitions(z);
    }

    public final int getLogRecSize() {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return 0;
        }
        return smHandler.mLogRecords.size();
    }

    public final int getLogRecMaxSize() {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return 0;
        }
        return smHandler.mLogRecords.mMaxSize;
    }

    public final int getLogRecCount() {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return 0;
        }
        return smHandler.mLogRecords.count();
    }

    public final com.android.internal.util.StateMachine.LogRec getLogRec(int i) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return null;
        }
        return smHandler.mLogRecords.get(i);
    }

    public final java.util.Collection<com.android.internal.util.StateMachine.LogRec> copyLogRecs() {
        java.util.Vector vector = new java.util.Vector();
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            java.util.Iterator it = smHandler.mLogRecords.mLogRecVector.iterator();
            while (it.hasNext()) {
                vector.add((com.android.internal.util.StateMachine.LogRec) it.next());
            }
        }
        return vector;
    }

    public void addLogRec(java.lang.String str) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.mLogRecords.add(this, smHandler.getCurrentMessage(), str, smHandler.getCurrentState(), smHandler.mStateStack[smHandler.mStateStackTopIndex].state, smHandler.mDestState);
    }

    protected boolean recordLogRec(android.os.Message message) {
        return true;
    }

    protected java.lang.String getLogRecString(android.os.Message message) {
        return "";
    }

    protected java.lang.String getWhatToString(int i) {
        return null;
    }

    public final android.os.Handler getHandler() {
        return this.mSmHandler;
    }

    public final android.os.Message obtainMessage() {
        return android.os.Message.obtain(this.mSmHandler);
    }

    public final android.os.Message obtainMessage(int i) {
        return android.os.Message.obtain(this.mSmHandler, i);
    }

    public final android.os.Message obtainMessage(int i, java.lang.Object obj) {
        return android.os.Message.obtain(this.mSmHandler, i, obj);
    }

    public final android.os.Message obtainMessage(int i, int i2) {
        return android.os.Message.obtain(this.mSmHandler, i, i2, 0);
    }

    public final android.os.Message obtainMessage(int i, int i2, int i3) {
        return android.os.Message.obtain(this.mSmHandler, i, i2, i3);
    }

    public final android.os.Message obtainMessage(int i, int i2, int i3, java.lang.Object obj) {
        return android.os.Message.obtain(this.mSmHandler, i, i2, i3, obj);
    }

    public void sendMessage(int i) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(obtainMessage(i));
    }

    public void sendMessage(int i, java.lang.Object obj) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(obtainMessage(i, obj));
    }

    public void sendMessage(int i, int i2) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(obtainMessage(i, i2));
    }

    public void sendMessage(int i, int i2, int i3) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(obtainMessage(i, i2, i3));
    }

    public void sendMessage(int i, int i2, int i3, java.lang.Object obj) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(obtainMessage(i, i2, i3, obj));
    }

    public void sendMessage(android.os.Message message) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessage(message);
    }

    public void sendMessageDelayed(int i, long j) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(obtainMessage(i), j);
    }

    public void sendMessageDelayed(int i, java.lang.Object obj, long j) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(obtainMessage(i, obj), j);
    }

    public void sendMessageDelayed(int i, int i2, long j) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(obtainMessage(i, i2), j);
    }

    public void sendMessageDelayed(int i, int i2, int i3, long j) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(obtainMessage(i, i2, i3), j);
    }

    public void sendMessageDelayed(int i, int i2, int i3, java.lang.Object obj, long j) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(obtainMessage(i, i2, i3, obj), j);
    }

    public void sendMessageDelayed(android.os.Message message, long j) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageDelayed(message, j);
    }

    protected final void sendMessageAtFrontOfQueue(int i) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(obtainMessage(i));
    }

    protected final void sendMessageAtFrontOfQueue(int i, java.lang.Object obj) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(obtainMessage(i, obj));
    }

    protected final void sendMessageAtFrontOfQueue(int i, int i2) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(obtainMessage(i, i2));
    }

    protected final void sendMessageAtFrontOfQueue(int i, int i2, int i3) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(obtainMessage(i, i2, i3));
    }

    protected final void sendMessageAtFrontOfQueue(int i, int i2, int i3, java.lang.Object obj) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(obtainMessage(i, i2, i3, obj));
    }

    protected final void sendMessageAtFrontOfQueue(android.os.Message message) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.sendMessageAtFrontOfQueue(message);
    }

    protected final void removeMessages(int i) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.removeMessages(i);
    }

    protected final void removeDeferredMessages(int i) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        java.util.Iterator it = smHandler.mDeferredMessages.iterator();
        while (it.hasNext()) {
            if (((android.os.Message) it.next()).what == i) {
                it.remove();
            }
        }
    }

    protected final boolean hasDeferredMessages(int i) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return false;
        }
        java.util.Iterator it = smHandler.mDeferredMessages.iterator();
        while (it.hasNext()) {
            if (((android.os.Message) it.next()).what == i) {
                return true;
            }
        }
        return false;
    }

    protected final boolean hasMessages(int i) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return false;
        }
        return smHandler.hasMessages(i);
    }

    protected final boolean isQuit(android.os.Message message) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return message.what == -1;
        }
        return smHandler.isQuit(message);
    }

    public final void quit() {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.quit();
    }

    public final void quitNow() {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.quitNow();
    }

    public boolean isDbg() {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return false;
        }
        return smHandler.isDbg();
    }

    public void setDbg(boolean z) {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.setDbg(z);
    }

    public void start() {
        com.android.internal.util.StateMachine.SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return;
        }
        smHandler.completeConstruction();
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.println(getName() + ":");
        printWriter.println(" total records=" + getLogRecCount());
        for (int i = 0; i < getLogRecSize(); i++) {
            printWriter.println(" rec[" + i + "]: " + getLogRec(i));
            printWriter.flush();
        }
        com.android.internal.util.IState currentState = getCurrentState();
        printWriter.println("curState=" + (currentState == null ? "<QUIT>" : currentState.getName()));
    }

    public java.lang.String toString() {
        java.lang.String str;
        try {
            str = this.mSmHandler.getCurrentState().getName().toString();
        } catch (java.lang.ArrayIndexOutOfBoundsException | java.lang.NullPointerException e) {
            str = "null";
        }
        return "name=" + this.mName + " state=" + str;
    }

    protected void logAndAddLogRec(java.lang.String str) {
        addLogRec(str);
        log(str);
    }

    protected void log(java.lang.String str) {
        android.util.Log.d(this.mName, str);
    }

    protected void logd(java.lang.String str) {
        android.util.Log.d(this.mName, str);
    }

    protected void logv(java.lang.String str) {
        android.util.Log.v(this.mName, str);
    }

    protected void logi(java.lang.String str) {
        android.util.Log.i(this.mName, str);
    }

    protected void logw(java.lang.String str) {
        android.util.Log.w(this.mName, str);
    }

    protected void loge(java.lang.String str) {
        android.util.Log.e(this.mName, str);
    }

    protected void loge(java.lang.String str, java.lang.Throwable th) {
        android.util.Log.e(this.mName, str, th);
    }
}
