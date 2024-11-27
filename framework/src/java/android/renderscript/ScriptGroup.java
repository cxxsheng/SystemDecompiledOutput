package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class ScriptGroup extends android.renderscript.BaseObj {
    private static final java.lang.String TAG = "ScriptGroup";
    private java.util.List<android.renderscript.ScriptGroup.Closure> mClosures;
    android.renderscript.ScriptGroup.IO[] mInputs;
    private java.util.List<android.renderscript.ScriptGroup.Input> mInputs2;
    private java.lang.String mName;
    android.renderscript.ScriptGroup.IO[] mOutputs;
    private android.renderscript.ScriptGroup.Future[] mOutputs2;

    static class IO {
        android.renderscript.Allocation mAllocation;
        android.renderscript.Script.KernelID mKID;

        IO(android.renderscript.Script.KernelID kernelID) {
            this.mKID = kernelID;
        }
    }

    static class ConnectLine {
        android.renderscript.Type mAllocationType;
        android.renderscript.Script.KernelID mFrom;
        android.renderscript.Script.FieldID mToF;
        android.renderscript.Script.KernelID mToK;

        ConnectLine(android.renderscript.Type type, android.renderscript.Script.KernelID kernelID, android.renderscript.Script.KernelID kernelID2) {
            this.mFrom = kernelID;
            this.mToK = kernelID2;
            this.mAllocationType = type;
        }

        ConnectLine(android.renderscript.Type type, android.renderscript.Script.KernelID kernelID, android.renderscript.Script.FieldID fieldID) {
            this.mFrom = kernelID;
            this.mToF = fieldID;
            this.mAllocationType = type;
        }
    }

    static class Node {
        int dagNumber;
        android.renderscript.ScriptGroup.Node mNext;
        android.renderscript.Script mScript;
        java.util.ArrayList<android.renderscript.Script.KernelID> mKernels = new java.util.ArrayList<>();
        java.util.ArrayList<android.renderscript.ScriptGroup.ConnectLine> mInputs = new java.util.ArrayList<>();
        java.util.ArrayList<android.renderscript.ScriptGroup.ConnectLine> mOutputs = new java.util.ArrayList<>();

        Node(android.renderscript.Script script) {
            this.mScript = script;
        }
    }

    public static final class Closure extends android.renderscript.BaseObj {
        private static final java.lang.String TAG = "Closure";
        private java.lang.Object[] mArgs;
        private java.util.Map<android.renderscript.Script.FieldID, java.lang.Object> mBindings;
        private android.renderscript.FieldPacker mFP;
        private java.util.Map<android.renderscript.Script.FieldID, android.renderscript.ScriptGroup.Future> mGlobalFuture;
        private android.renderscript.ScriptGroup.Future mReturnFuture;
        private android.renderscript.Allocation mReturnValue;

        Closure(long j, android.renderscript.RenderScript renderScript) {
            super(j, renderScript);
        }

        Closure(android.renderscript.RenderScript renderScript, android.renderscript.Script.KernelID kernelID, android.renderscript.Type type, java.lang.Object[] objArr, java.util.Map<android.renderscript.Script.FieldID, java.lang.Object> map) {
            super(0L, renderScript);
            this.mArgs = objArr;
            this.mReturnValue = android.renderscript.Allocation.createTyped(renderScript, type);
            this.mBindings = map;
            this.mGlobalFuture = new java.util.HashMap();
            int length = objArr.length + map.size();
            long[] jArr = new long[length];
            long[] jArr2 = new long[length];
            int[] iArr = new int[length];
            long[] jArr3 = new long[length];
            long[] jArr4 = new long[length];
            int i = 0;
            while (i < objArr.length) {
                jArr[i] = 0;
                long[] jArr5 = jArr4;
                long[] jArr6 = jArr3;
                retrieveValueAndDependenceInfo(renderScript, i, null, objArr[i], jArr2, iArr, jArr6, jArr5);
                i++;
                jArr2 = jArr2;
                jArr3 = jArr6;
                jArr4 = jArr5;
                iArr = iArr;
            }
            int i2 = i;
            long[] jArr7 = jArr4;
            long[] jArr8 = jArr3;
            int[] iArr2 = iArr;
            long[] jArr9 = jArr2;
            for (java.util.Map.Entry<android.renderscript.Script.FieldID, java.lang.Object> entry : map.entrySet()) {
                java.lang.Object value = entry.getValue();
                android.renderscript.Script.FieldID key = entry.getKey();
                jArr[i2] = key.getID(renderScript);
                retrieveValueAndDependenceInfo(renderScript, i2, key, value, jArr9, iArr2, jArr8, jArr7);
                i2++;
            }
            setID(renderScript.nClosureCreate(kernelID.getID(renderScript), this.mReturnValue.getID(renderScript), jArr, jArr9, iArr2, jArr8, jArr7));
            this.guard.open("destroy");
        }

        Closure(android.renderscript.RenderScript renderScript, android.renderscript.Script.InvokeID invokeID, java.lang.Object[] objArr, java.util.Map<android.renderscript.Script.FieldID, java.lang.Object> map) {
            super(0L, renderScript);
            this.mFP = android.renderscript.FieldPacker.createFromArray(objArr);
            this.mArgs = objArr;
            this.mBindings = map;
            this.mGlobalFuture = new java.util.HashMap();
            int size = map.size();
            long[] jArr = new long[size];
            long[] jArr2 = new long[size];
            int[] iArr = new int[size];
            long[] jArr3 = new long[size];
            long[] jArr4 = new long[size];
            int i = 0;
            for (java.util.Map.Entry<android.renderscript.Script.FieldID, java.lang.Object> entry : map.entrySet()) {
                java.lang.Object value = entry.getValue();
                android.renderscript.Script.FieldID key = entry.getKey();
                jArr[i] = key.getID(renderScript);
                retrieveValueAndDependenceInfo(renderScript, i, key, value, jArr2, iArr, jArr3, jArr4);
                i++;
            }
            setID(renderScript.nInvokeClosureCreate(invokeID.getID(renderScript), this.mFP.getData(), jArr, jArr2, iArr));
            this.guard.open("destroy");
        }

        @Override // android.renderscript.BaseObj
        public void destroy() {
            super.destroy();
            if (this.mReturnValue != null) {
                this.mReturnValue.destroy();
            }
        }

        @Override // android.renderscript.BaseObj
        protected void finalize() throws java.lang.Throwable {
            this.mReturnValue = null;
            super.finalize();
        }

        private void retrieveValueAndDependenceInfo(android.renderscript.RenderScript renderScript, int i, android.renderscript.Script.FieldID fieldID, java.lang.Object obj, long[] jArr, int[] iArr, long[] jArr2, long[] jArr3) {
            if (obj instanceof android.renderscript.ScriptGroup.Future) {
                android.renderscript.ScriptGroup.Future future = (android.renderscript.ScriptGroup.Future) obj;
                java.lang.Object value = future.getValue();
                jArr2[i] = future.getClosure().getID(renderScript);
                android.renderscript.Script.FieldID fieldID2 = future.getFieldID();
                jArr3[i] = fieldID2 != null ? fieldID2.getID(renderScript) : 0L;
                obj = value;
            } else {
                jArr2[i] = 0;
                jArr3[i] = 0;
            }
            if (obj instanceof android.renderscript.ScriptGroup.Input) {
                android.renderscript.ScriptGroup.Input input = (android.renderscript.ScriptGroup.Input) obj;
                if (i < this.mArgs.length) {
                    input.addReference(this, i);
                } else {
                    input.addReference(this, fieldID);
                }
                jArr[i] = 0;
                iArr[i] = 0;
                return;
            }
            android.renderscript.ScriptGroup.Closure.ValueAndSize valueAndSize = new android.renderscript.ScriptGroup.Closure.ValueAndSize(renderScript, obj);
            jArr[i] = valueAndSize.value;
            iArr[i] = valueAndSize.size;
        }

        public android.renderscript.ScriptGroup.Future getReturn() {
            if (this.mReturnFuture == null) {
                this.mReturnFuture = new android.renderscript.ScriptGroup.Future(this, null, this.mReturnValue);
            }
            return this.mReturnFuture;
        }

        public android.renderscript.ScriptGroup.Future getGlobal(android.renderscript.Script.FieldID fieldID) {
            android.renderscript.ScriptGroup.Future future = this.mGlobalFuture.get(fieldID);
            if (future == null) {
                java.lang.Object obj = this.mBindings.get(fieldID);
                if (obj instanceof android.renderscript.ScriptGroup.Future) {
                    obj = ((android.renderscript.ScriptGroup.Future) obj).getValue();
                }
                android.renderscript.ScriptGroup.Future future2 = new android.renderscript.ScriptGroup.Future(this, fieldID, obj);
                this.mGlobalFuture.put(fieldID, future2);
                return future2;
            }
            return future;
        }

        void setArg(int i, java.lang.Object obj) {
            if (obj instanceof android.renderscript.ScriptGroup.Future) {
                obj = ((android.renderscript.ScriptGroup.Future) obj).getValue();
            }
            this.mArgs[i] = obj;
            android.renderscript.ScriptGroup.Closure.ValueAndSize valueAndSize = new android.renderscript.ScriptGroup.Closure.ValueAndSize(this.mRS, obj);
            this.mRS.nClosureSetArg(getID(this.mRS), i, valueAndSize.value, valueAndSize.size);
        }

        void setGlobal(android.renderscript.Script.FieldID fieldID, java.lang.Object obj) {
            if (obj instanceof android.renderscript.ScriptGroup.Future) {
                obj = ((android.renderscript.ScriptGroup.Future) obj).getValue();
            }
            this.mBindings.put(fieldID, obj);
            android.renderscript.ScriptGroup.Closure.ValueAndSize valueAndSize = new android.renderscript.ScriptGroup.Closure.ValueAndSize(this.mRS, obj);
            this.mRS.nClosureSetGlobal(getID(this.mRS), fieldID.getID(this.mRS), valueAndSize.value, valueAndSize.size);
        }

        private static final class ValueAndSize {
            public int size;
            public long value;

            public ValueAndSize(android.renderscript.RenderScript renderScript, java.lang.Object obj) {
                if (obj instanceof android.renderscript.Allocation) {
                    this.value = ((android.renderscript.Allocation) obj).getID(renderScript);
                    this.size = -1;
                    return;
                }
                if (obj instanceof java.lang.Boolean) {
                    this.value = ((java.lang.Boolean) obj).booleanValue() ? 1L : 0L;
                    this.size = 4;
                    return;
                }
                if (obj instanceof java.lang.Integer) {
                    this.value = ((java.lang.Integer) obj).longValue();
                    this.size = 4;
                    return;
                }
                if (obj instanceof java.lang.Long) {
                    this.value = ((java.lang.Long) obj).longValue();
                    this.size = 8;
                } else if (obj instanceof java.lang.Float) {
                    this.value = java.lang.Float.floatToRawIntBits(((java.lang.Float) obj).floatValue());
                    this.size = 4;
                } else if (obj instanceof java.lang.Double) {
                    this.value = java.lang.Double.doubleToRawLongBits(((java.lang.Double) obj).doubleValue());
                    this.size = 8;
                }
            }
        }
    }

    public static final class Future {
        android.renderscript.ScriptGroup.Closure mClosure;
        android.renderscript.Script.FieldID mFieldID;
        java.lang.Object mValue;

        Future(android.renderscript.ScriptGroup.Closure closure, android.renderscript.Script.FieldID fieldID, java.lang.Object obj) {
            this.mClosure = closure;
            this.mFieldID = fieldID;
            this.mValue = obj;
        }

        android.renderscript.ScriptGroup.Closure getClosure() {
            return this.mClosure;
        }

        android.renderscript.Script.FieldID getFieldID() {
            return this.mFieldID;
        }

        java.lang.Object getValue() {
            return this.mValue;
        }
    }

    public static final class Input {
        java.lang.Object mValue;
        java.util.List<android.util.Pair<android.renderscript.ScriptGroup.Closure, android.renderscript.Script.FieldID>> mFieldID = new java.util.ArrayList();
        java.util.List<android.util.Pair<android.renderscript.ScriptGroup.Closure, java.lang.Integer>> mArgIndex = new java.util.ArrayList();

        Input() {
        }

        void addReference(android.renderscript.ScriptGroup.Closure closure, int i) {
            this.mArgIndex.add(android.util.Pair.create(closure, java.lang.Integer.valueOf(i)));
        }

        void addReference(android.renderscript.ScriptGroup.Closure closure, android.renderscript.Script.FieldID fieldID) {
            this.mFieldID.add(android.util.Pair.create(closure, fieldID));
        }

        void set(java.lang.Object obj) {
            this.mValue = obj;
            for (android.util.Pair<android.renderscript.ScriptGroup.Closure, java.lang.Integer> pair : this.mArgIndex) {
                pair.first.setArg(pair.second.intValue(), obj);
            }
            for (android.util.Pair<android.renderscript.ScriptGroup.Closure, android.renderscript.Script.FieldID> pair2 : this.mFieldID) {
                pair2.first.setGlobal(pair2.second, obj);
            }
        }

        java.lang.Object get() {
            return this.mValue;
        }
    }

    ScriptGroup(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
        this.guard.open("destroy");
    }

    ScriptGroup(android.renderscript.RenderScript renderScript, java.lang.String str, java.util.List<android.renderscript.ScriptGroup.Closure> list, java.util.List<android.renderscript.ScriptGroup.Input> list2, android.renderscript.ScriptGroup.Future[] futureArr) {
        super(0L, renderScript);
        this.mName = str;
        this.mClosures = list;
        this.mInputs2 = list2;
        this.mOutputs2 = futureArr;
        int size = list.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = list.get(i).getID(renderScript);
        }
        setID(renderScript.nScriptGroup2Create(str, android.renderscript.RenderScript.getCachePath(), jArr));
        this.guard.open("destroy");
    }

    public java.lang.Object[] execute(java.lang.Object... objArr) {
        if (objArr.length < this.mInputs2.size()) {
            android.util.Log.e(TAG, toString() + " receives " + objArr.length + " inputs, less than expected " + this.mInputs2.size());
            return null;
        }
        if (objArr.length > this.mInputs2.size()) {
            android.util.Log.i(TAG, toString() + " receives " + objArr.length + " inputs, more than expected " + this.mInputs2.size());
        }
        int i = 0;
        for (int i2 = 0; i2 < this.mInputs2.size(); i2++) {
            java.lang.Object obj = objArr[i2];
            if ((obj instanceof android.renderscript.ScriptGroup.Future) || (obj instanceof android.renderscript.ScriptGroup.Input)) {
                android.util.Log.e(TAG, toString() + ": input " + i2 + " is a future or unbound value");
                return null;
            }
            this.mInputs2.get(i2).set(obj);
        }
        this.mRS.nScriptGroup2Execute(getID(this.mRS));
        java.lang.Object[] objArr2 = new java.lang.Object[this.mOutputs2.length];
        android.renderscript.ScriptGroup.Future[] futureArr = this.mOutputs2;
        int length = futureArr.length;
        int i3 = 0;
        while (i < length) {
            java.lang.Object value = futureArr[i].getValue();
            if (value instanceof android.renderscript.ScriptGroup.Input) {
                value = ((android.renderscript.ScriptGroup.Input) value).get();
            }
            objArr2[i3] = value;
            i++;
            i3++;
        }
        return objArr2;
    }

    public void setInput(android.renderscript.Script.KernelID kernelID, android.renderscript.Allocation allocation) {
        for (int i = 0; i < this.mInputs.length; i++) {
            if (this.mInputs[i].mKID == kernelID) {
                this.mInputs[i].mAllocation = allocation;
                this.mRS.nScriptGroupSetInput(getID(this.mRS), kernelID.getID(this.mRS), this.mRS.safeID(allocation));
                return;
            }
        }
        throw new android.renderscript.RSIllegalArgumentException("Script not found");
    }

    public void setOutput(android.renderscript.Script.KernelID kernelID, android.renderscript.Allocation allocation) {
        for (int i = 0; i < this.mOutputs.length; i++) {
            if (this.mOutputs[i].mKID == kernelID) {
                this.mOutputs[i].mAllocation = allocation;
                this.mRS.nScriptGroupSetOutput(getID(this.mRS), kernelID.getID(this.mRS), this.mRS.safeID(allocation));
                return;
            }
        }
        throw new android.renderscript.RSIllegalArgumentException("Script not found");
    }

    public void execute() {
        this.mRS.nScriptGroupExecute(getID(this.mRS));
    }

    public static final class Builder {
        private int mKernelCount;
        private android.renderscript.RenderScript mRS;
        private java.util.ArrayList<android.renderscript.ScriptGroup.Node> mNodes = new java.util.ArrayList<>();
        private java.util.ArrayList<android.renderscript.ScriptGroup.ConnectLine> mLines = new java.util.ArrayList<>();

        public Builder(android.renderscript.RenderScript renderScript) {
            this.mRS = renderScript;
        }

        private void validateCycle(android.renderscript.ScriptGroup.Node node, android.renderscript.ScriptGroup.Node node2) {
            for (int i = 0; i < node.mOutputs.size(); i++) {
                android.renderscript.ScriptGroup.ConnectLine connectLine = node.mOutputs.get(i);
                if (connectLine.mToK != null) {
                    android.renderscript.ScriptGroup.Node findNode = findNode(connectLine.mToK.mScript);
                    if (findNode.equals(node2)) {
                        throw new android.renderscript.RSInvalidStateException("Loops in group not allowed.");
                    }
                    validateCycle(findNode, node2);
                }
                if (connectLine.mToF != null) {
                    android.renderscript.ScriptGroup.Node findNode2 = findNode(connectLine.mToF.mScript);
                    if (findNode2.equals(node2)) {
                        throw new android.renderscript.RSInvalidStateException("Loops in group not allowed.");
                    }
                    validateCycle(findNode2, node2);
                }
            }
        }

        private void mergeDAGs(int i, int i2) {
            for (int i3 = 0; i3 < this.mNodes.size(); i3++) {
                if (this.mNodes.get(i3).dagNumber == i2) {
                    this.mNodes.get(i3).dagNumber = i;
                }
            }
        }

        private void validateDAGRecurse(android.renderscript.ScriptGroup.Node node, int i) {
            if (node.dagNumber != 0 && node.dagNumber != i) {
                mergeDAGs(node.dagNumber, i);
                return;
            }
            node.dagNumber = i;
            for (int i2 = 0; i2 < node.mOutputs.size(); i2++) {
                android.renderscript.ScriptGroup.ConnectLine connectLine = node.mOutputs.get(i2);
                if (connectLine.mToK != null) {
                    validateDAGRecurse(findNode(connectLine.mToK.mScript), i);
                }
                if (connectLine.mToF != null) {
                    validateDAGRecurse(findNode(connectLine.mToF.mScript), i);
                }
            }
        }

        private void validateDAG() {
            for (int i = 0; i < this.mNodes.size(); i++) {
                android.renderscript.ScriptGroup.Node node = this.mNodes.get(i);
                if (node.mInputs.size() == 0) {
                    if (node.mOutputs.size() == 0 && this.mNodes.size() > 1) {
                        throw new android.renderscript.RSInvalidStateException("Groups cannot contain unconnected scripts");
                    }
                    validateDAGRecurse(node, i + 1);
                }
            }
            int i2 = this.mNodes.get(0).dagNumber;
            for (int i3 = 0; i3 < this.mNodes.size(); i3++) {
                if (this.mNodes.get(i3).dagNumber != i2) {
                    throw new android.renderscript.RSInvalidStateException("Multiple DAGs in group not allowed.");
                }
            }
        }

        private android.renderscript.ScriptGroup.Node findNode(android.renderscript.Script script) {
            for (int i = 0; i < this.mNodes.size(); i++) {
                if (script == this.mNodes.get(i).mScript) {
                    return this.mNodes.get(i);
                }
            }
            return null;
        }

        private android.renderscript.ScriptGroup.Node findNode(android.renderscript.Script.KernelID kernelID) {
            for (int i = 0; i < this.mNodes.size(); i++) {
                android.renderscript.ScriptGroup.Node node = this.mNodes.get(i);
                for (int i2 = 0; i2 < node.mKernels.size(); i2++) {
                    if (kernelID == node.mKernels.get(i2)) {
                        return node;
                    }
                }
            }
            return null;
        }

        public android.renderscript.ScriptGroup.Builder addKernel(android.renderscript.Script.KernelID kernelID) {
            if (this.mLines.size() != 0) {
                throw new android.renderscript.RSInvalidStateException("Kernels may not be added once connections exist.");
            }
            if (findNode(kernelID) != null) {
                return this;
            }
            this.mKernelCount++;
            android.renderscript.ScriptGroup.Node findNode = findNode(kernelID.mScript);
            if (findNode == null) {
                findNode = new android.renderscript.ScriptGroup.Node(kernelID.mScript);
                this.mNodes.add(findNode);
            }
            findNode.mKernels.add(kernelID);
            return this;
        }

        public android.renderscript.ScriptGroup.Builder addConnection(android.renderscript.Type type, android.renderscript.Script.KernelID kernelID, android.renderscript.Script.FieldID fieldID) {
            android.renderscript.ScriptGroup.Node findNode = findNode(kernelID);
            if (findNode == null) {
                throw new android.renderscript.RSInvalidStateException("From script not found.");
            }
            android.renderscript.ScriptGroup.Node findNode2 = findNode(fieldID.mScript);
            if (findNode2 == null) {
                throw new android.renderscript.RSInvalidStateException("To script not found.");
            }
            android.renderscript.ScriptGroup.ConnectLine connectLine = new android.renderscript.ScriptGroup.ConnectLine(type, kernelID, fieldID);
            this.mLines.add(new android.renderscript.ScriptGroup.ConnectLine(type, kernelID, fieldID));
            findNode.mOutputs.add(connectLine);
            findNode2.mInputs.add(connectLine);
            validateCycle(findNode, findNode);
            return this;
        }

        public android.renderscript.ScriptGroup.Builder addConnection(android.renderscript.Type type, android.renderscript.Script.KernelID kernelID, android.renderscript.Script.KernelID kernelID2) {
            android.renderscript.ScriptGroup.Node findNode = findNode(kernelID);
            if (findNode == null) {
                throw new android.renderscript.RSInvalidStateException("From script not found.");
            }
            android.renderscript.ScriptGroup.Node findNode2 = findNode(kernelID2);
            if (findNode2 == null) {
                throw new android.renderscript.RSInvalidStateException("To script not found.");
            }
            android.renderscript.ScriptGroup.ConnectLine connectLine = new android.renderscript.ScriptGroup.ConnectLine(type, kernelID, kernelID2);
            this.mLines.add(new android.renderscript.ScriptGroup.ConnectLine(type, kernelID, kernelID2));
            findNode.mOutputs.add(connectLine);
            findNode2.mInputs.add(connectLine);
            validateCycle(findNode, findNode);
            return this;
        }

        public android.renderscript.ScriptGroup create() {
            if (this.mNodes.size() == 0) {
                throw new android.renderscript.RSInvalidStateException("Empty script groups are not allowed");
            }
            for (int i = 0; i < this.mNodes.size(); i++) {
                this.mNodes.get(i).dagNumber = 0;
            }
            validateDAG();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            long[] jArr = new long[this.mKernelCount];
            int i2 = 0;
            for (int i3 = 0; i3 < this.mNodes.size(); i3++) {
                android.renderscript.ScriptGroup.Node node = this.mNodes.get(i3);
                int i4 = 0;
                while (i4 < node.mKernels.size()) {
                    android.renderscript.Script.KernelID kernelID = node.mKernels.get(i4);
                    int i5 = i2 + 1;
                    jArr[i2] = kernelID.getID(this.mRS);
                    boolean z = false;
                    for (int i6 = 0; i6 < node.mInputs.size(); i6++) {
                        if (node.mInputs.get(i6).mToK == kernelID) {
                            z = true;
                        }
                    }
                    boolean z2 = false;
                    for (int i7 = 0; i7 < node.mOutputs.size(); i7++) {
                        if (node.mOutputs.get(i7).mFrom == kernelID) {
                            z2 = true;
                        }
                    }
                    if (!z) {
                        arrayList.add(new android.renderscript.ScriptGroup.IO(kernelID));
                    }
                    if (!z2) {
                        arrayList2.add(new android.renderscript.ScriptGroup.IO(kernelID));
                    }
                    i4++;
                    i2 = i5;
                }
            }
            if (i2 != this.mKernelCount) {
                throw new android.renderscript.RSRuntimeException("Count mismatch, should not happen.");
            }
            long[] jArr2 = new long[this.mLines.size()];
            long[] jArr3 = new long[this.mLines.size()];
            long[] jArr4 = new long[this.mLines.size()];
            long[] jArr5 = new long[this.mLines.size()];
            for (int i8 = 0; i8 < this.mLines.size(); i8++) {
                android.renderscript.ScriptGroup.ConnectLine connectLine = this.mLines.get(i8);
                jArr2[i8] = connectLine.mFrom.getID(this.mRS);
                if (connectLine.mToK != null) {
                    jArr3[i8] = connectLine.mToK.getID(this.mRS);
                }
                if (connectLine.mToF != null) {
                    jArr4[i8] = connectLine.mToF.getID(this.mRS);
                }
                jArr5[i8] = connectLine.mAllocationType.getID(this.mRS);
            }
            long nScriptGroupCreate = this.mRS.nScriptGroupCreate(jArr, jArr2, jArr3, jArr4, jArr5);
            if (nScriptGroupCreate == 0) {
                throw new android.renderscript.RSRuntimeException("Object creation error, should not happen.");
            }
            android.renderscript.ScriptGroup scriptGroup = new android.renderscript.ScriptGroup(nScriptGroupCreate, this.mRS);
            scriptGroup.mOutputs = new android.renderscript.ScriptGroup.IO[arrayList2.size()];
            for (int i9 = 0; i9 < arrayList2.size(); i9++) {
                scriptGroup.mOutputs[i9] = (android.renderscript.ScriptGroup.IO) arrayList2.get(i9);
            }
            scriptGroup.mInputs = new android.renderscript.ScriptGroup.IO[arrayList.size()];
            for (int i10 = 0; i10 < arrayList.size(); i10++) {
                scriptGroup.mInputs[i10] = (android.renderscript.ScriptGroup.IO) arrayList.get(i10);
            }
            return scriptGroup;
        }
    }

    public static final class Binding {
        private final android.renderscript.Script.FieldID mField;
        private final java.lang.Object mValue;

        public Binding(android.renderscript.Script.FieldID fieldID, java.lang.Object obj) {
            this.mField = fieldID;
            this.mValue = obj;
        }

        android.renderscript.Script.FieldID getField() {
            return this.mField;
        }

        java.lang.Object getValue() {
            return this.mValue;
        }
    }

    public static final class Builder2 {
        private static final java.lang.String TAG = "ScriptGroup.Builder2";
        java.util.List<android.renderscript.ScriptGroup.Closure> mClosures = new java.util.ArrayList();
        java.util.List<android.renderscript.ScriptGroup.Input> mInputs = new java.util.ArrayList();
        android.renderscript.RenderScript mRS;

        public Builder2(android.renderscript.RenderScript renderScript) {
            this.mRS = renderScript;
        }

        private android.renderscript.ScriptGroup.Closure addKernelInternal(android.renderscript.Script.KernelID kernelID, android.renderscript.Type type, java.lang.Object[] objArr, java.util.Map<android.renderscript.Script.FieldID, java.lang.Object> map) {
            android.renderscript.ScriptGroup.Closure closure = new android.renderscript.ScriptGroup.Closure(this.mRS, kernelID, type, objArr, map);
            this.mClosures.add(closure);
            return closure;
        }

        private android.renderscript.ScriptGroup.Closure addInvokeInternal(android.renderscript.Script.InvokeID invokeID, java.lang.Object[] objArr, java.util.Map<android.renderscript.Script.FieldID, java.lang.Object> map) {
            android.renderscript.ScriptGroup.Closure closure = new android.renderscript.ScriptGroup.Closure(this.mRS, invokeID, objArr, map);
            this.mClosures.add(closure);
            return closure;
        }

        public android.renderscript.ScriptGroup.Input addInput() {
            android.renderscript.ScriptGroup.Input input = new android.renderscript.ScriptGroup.Input();
            this.mInputs.add(input);
            return input;
        }

        public android.renderscript.ScriptGroup.Closure addKernel(android.renderscript.Script.KernelID kernelID, android.renderscript.Type type, java.lang.Object... objArr) {
            java.util.ArrayList<java.lang.Object> arrayList = new java.util.ArrayList<>();
            java.util.HashMap hashMap = new java.util.HashMap();
            if (!seperateArgsAndBindings(objArr, arrayList, hashMap)) {
                return null;
            }
            return addKernelInternal(kernelID, type, arrayList.toArray(), hashMap);
        }

        public android.renderscript.ScriptGroup.Closure addInvoke(android.renderscript.Script.InvokeID invokeID, java.lang.Object... objArr) {
            java.util.ArrayList<java.lang.Object> arrayList = new java.util.ArrayList<>();
            java.util.HashMap hashMap = new java.util.HashMap();
            if (!seperateArgsAndBindings(objArr, arrayList, hashMap)) {
                return null;
            }
            return addInvokeInternal(invokeID, arrayList.toArray(), hashMap);
        }

        public android.renderscript.ScriptGroup create(java.lang.String str, android.renderscript.ScriptGroup.Future... futureArr) {
            if (str == null || str.isEmpty() || str.length() > 100 || !str.equals(str.replaceAll("[^a-zA-Z0-9-]", android.telecom.Logging.Session.SESSION_SEPARATION_CHAR_CHILD))) {
                throw new android.renderscript.RSIllegalArgumentException("invalid script group name");
            }
            android.renderscript.ScriptGroup scriptGroup = new android.renderscript.ScriptGroup(this.mRS, str, this.mClosures, this.mInputs, futureArr);
            this.mClosures = new java.util.ArrayList();
            this.mInputs = new java.util.ArrayList();
            return scriptGroup;
        }

        private boolean seperateArgsAndBindings(java.lang.Object[] objArr, java.util.ArrayList<java.lang.Object> arrayList, java.util.Map<android.renderscript.Script.FieldID, java.lang.Object> map) {
            int i = 0;
            while (i < objArr.length && !(objArr[i] instanceof android.renderscript.ScriptGroup.Binding)) {
                arrayList.add(objArr[i]);
                i++;
            }
            while (i < objArr.length) {
                if (!(objArr[i] instanceof android.renderscript.ScriptGroup.Binding)) {
                    return false;
                }
                android.renderscript.ScriptGroup.Binding binding = (android.renderscript.ScriptGroup.Binding) objArr[i];
                map.put(binding.getField(), binding.getValue());
                i++;
            }
            return true;
        }
    }

    @Override // android.renderscript.BaseObj
    public void destroy() {
        super.destroy();
        if (this.mClosures != null) {
            java.util.Iterator<android.renderscript.ScriptGroup.Closure> it = this.mClosures.iterator();
            while (it.hasNext()) {
                it.next().destroy();
            }
        }
    }
}
