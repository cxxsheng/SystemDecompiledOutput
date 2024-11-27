package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class MultiStateStats {
    private static final java.lang.String TAG = "MultiStateStats";
    private static final java.lang.String XML_TAG_STATS = "stats";
    private int mCompositeState;
    private final com.android.internal.os.LongArrayMultiStateCounter mCounter;
    private final com.android.server.power.stats.MultiStateStats.Factory mFactory;
    private boolean mTracking;

    public static class States {
        final java.lang.String[] mLabels;
        final java.lang.String mName;
        final boolean mTracked;

        public States(java.lang.String str, boolean z, java.lang.String... strArr) {
            this.mName = str;
            this.mTracked = z;
            this.mLabels = strArr;
        }

        public boolean isTracked() {
            return this.mTracked;
        }

        public java.lang.String getName() {
            return this.mName;
        }

        public java.lang.String[] getLabels() {
            return this.mLabels;
        }

        public static void forEachTrackedStateCombination(com.android.server.power.stats.MultiStateStats.States[] statesArr, java.util.function.Consumer<int[]> consumer) {
            forEachTrackedStateCombination(consumer, statesArr, new int[statesArr.length], 0);
        }

        private static void forEachTrackedStateCombination(java.util.function.Consumer<int[]> consumer, com.android.server.power.stats.MultiStateStats.States[] statesArr, int[] iArr, int i) {
            if (i < iArr.length) {
                if (!statesArr[i].mTracked) {
                    forEachTrackedStateCombination(consumer, statesArr, iArr, i + 1);
                    return;
                }
                for (int i2 = 0; i2 < statesArr[i].mLabels.length; i2++) {
                    iArr[i] = i2;
                    forEachTrackedStateCombination(consumer, statesArr, iArr, i + 1);
                }
                return;
            }
            consumer.accept(iArr);
        }
    }

    public static class Factory {
        private static final int INVALID_SERIAL_STATE = -1;
        final int[] mCompositeToSerialState;
        final int mDimensionCount;
        final int mSerialStateCount;
        private final int[] mStateBitFieldMasks;
        private final short[] mStateBitFieldShifts;
        final com.android.server.power.stats.MultiStateStats.States[] mStates;

        public Factory(int i, com.android.server.power.stats.MultiStateStats.States... statesArr) {
            this.mDimensionCount = i;
            this.mStates = statesArr;
            int i2 = 1;
            for (com.android.server.power.stats.MultiStateStats.States states : this.mStates) {
                if (states.mTracked) {
                    i2 *= states.mLabels.length;
                }
            }
            this.mSerialStateCount = i2;
            this.mStateBitFieldMasks = new int[this.mStates.length];
            this.mStateBitFieldShifts = new short[this.mStates.length];
            int i3 = 0;
            for (int i4 = 0; i4 < this.mStates.length; i4++) {
                this.mStateBitFieldShifts[i4] = (short) i3;
                if (this.mStates[i4].mLabels.length < 2) {
                    throw new java.lang.IllegalArgumentException("Invalid state: " + java.util.Arrays.toString(this.mStates[i4].mLabels) + ". Should have at least two values.");
                }
                int numberOfLeadingZeros = 32 - java.lang.Integer.numberOfLeadingZeros(this.mStates[i4].mLabels.length - 1);
                this.mStateBitFieldMasks[i4] = ((1 << numberOfLeadingZeros) - 1) << i3;
                i3 += numberOfLeadingZeros;
            }
            if (i3 >= 31) {
                throw new java.lang.IllegalArgumentException("Too many states: " + i3 + " bits are required to represent the composite state, but only 31 are available");
            }
            int i5 = -1;
            for (int i6 = 0; i6 < this.mStates.length; i6++) {
                if (!this.mStates[i6].mTracked) {
                    i5 &= ~this.mStateBitFieldMasks[i6];
                }
            }
            this.mCompositeToSerialState = new int[1 << i3];
            java.util.Arrays.fill(this.mCompositeToSerialState, -1);
            int i7 = 0;
            for (int i8 = 0; i8 < this.mCompositeToSerialState.length; i8++) {
                if (isValidCompositeState(i8)) {
                    int i9 = i8 & i5;
                    if (this.mCompositeToSerialState[i9] != -1) {
                        this.mCompositeToSerialState[i8] = this.mCompositeToSerialState[i9];
                    } else {
                        this.mCompositeToSerialState[i8] = i7;
                        i7++;
                    }
                }
            }
        }

        private boolean isValidCompositeState(int i) {
            for (int i2 = 0; i2 < this.mStates.length; i2++) {
                if (extractStateFromComposite(i, i2) >= this.mStates[i2].mLabels.length) {
                    return false;
                }
            }
            return true;
        }

        private int extractStateFromComposite(int i, int i2) {
            return (i & this.mStateBitFieldMasks[i2]) >>> this.mStateBitFieldShifts[i2];
        }

        int setStateInComposite(int i, int i2, int i3) {
            return (i & (~this.mStateBitFieldMasks[i2])) | (i3 << this.mStateBitFieldShifts[i2]);
        }

        int setStateInComposite(int i, java.lang.String str, java.lang.String str2) {
            for (int i2 = 0; i2 < this.mStates.length; i2++) {
                com.android.server.power.stats.MultiStateStats.States states = this.mStates[i2];
                if (states.mName.equals(str)) {
                    for (int i3 = 0; i3 < states.mLabels.length; i3++) {
                        if (states.mLabels[i3].equals(str2)) {
                            return setStateInComposite(i, i2, i3);
                        }
                    }
                    android.util.Slog.e(com.android.server.power.stats.MultiStateStats.TAG, "Unexpected label '" + str2 + "' for state: " + str);
                    return -1;
                }
            }
            android.util.Slog.e(com.android.server.power.stats.MultiStateStats.TAG, "Unsupported state: " + str);
            return -1;
        }

        public com.android.server.power.stats.MultiStateStats create() {
            return new com.android.server.power.stats.MultiStateStats(this, this.mDimensionCount);
        }

        @com.android.internal.annotations.VisibleForTesting
        public int getSerialStateCount() {
            return this.mSerialStateCount;
        }

        @com.android.internal.annotations.VisibleForTesting
        public int getSerialState(int[] iArr) {
            com.android.internal.util.Preconditions.checkArgument(iArr.length == this.mStates.length);
            int i = 0;
            for (int i2 = 0; i2 < iArr.length; i2++) {
                i = setStateInComposite(i, i2, iArr[i2]);
            }
            int i3 = this.mCompositeToSerialState[i];
            if (i3 == -1) {
                throw new java.lang.IllegalArgumentException("State values out of bounds: " + java.util.Arrays.toString(iArr));
            }
            return i3;
        }

        int getSerialState(int i) {
            return this.mCompositeToSerialState[i];
        }
    }

    public MultiStateStats(com.android.server.power.stats.MultiStateStats.Factory factory, int i) {
        this.mFactory = factory;
        this.mCounter = new com.android.internal.os.LongArrayMultiStateCounter(factory.mSerialStateCount, i);
    }

    public void setState(int i, int i2, long j) {
        if (!this.mTracking) {
            this.mCounter.updateValues(new long[this.mCounter.getArrayLength()], j);
            this.mTracking = true;
        }
        this.mCompositeState = this.mFactory.setStateInComposite(this.mCompositeState, i, i2);
        this.mCounter.setState(this.mFactory.mCompositeToSerialState[this.mCompositeState], j);
    }

    public void increment(long[] jArr, long j) {
        this.mCounter.incrementValues(jArr, j);
        this.mTracking = true;
    }

    public void getStats(long[] jArr, int[] iArr) {
        this.mCounter.getCounts(jArr, this.mFactory.getSerialState(iArr));
    }

    public void setStats(int[] iArr, long[] jArr) {
        this.mCounter.setValues(this.mFactory.getSerialState(iArr), jArr);
    }

    public void reset() {
        this.mCounter.reset();
        this.mTracking = false;
    }

    public java.lang.String toString() {
        return this.mCounter.toString();
    }

    public void writeXml(final com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        final long[] jArr = new long[this.mCounter.getArrayLength()];
        try {
            com.android.server.power.stats.MultiStateStats.States.forEachTrackedStateCombination(this.mFactory.mStates, new java.util.function.Consumer() { // from class: com.android.server.power.stats.MultiStateStats$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.power.stats.MultiStateStats.this.lambda$writeXml$0(typedXmlSerializer, jArr, (int[]) obj);
                }
            });
        } catch (java.lang.RuntimeException e) {
            if (e.getCause() instanceof java.io.IOException) {
                throw ((java.io.IOException) e.getCause());
            }
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$writeXml$0(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, long[] jArr, int[] iArr) {
        try {
            writeXmlForStates(typedXmlSerializer, iArr, jArr);
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private void writeXmlForStates(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, int[] iArr, long[] jArr) throws java.io.IOException {
        boolean z;
        this.mCounter.getCounts(jArr, this.mFactory.getSerialState(iArr));
        int length = jArr.length;
        int i = 0;
        while (true) {
            if (i < length) {
                if (jArr[i] == 0) {
                    i++;
                } else {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        if (!z) {
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_STATS);
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (this.mFactory.mStates[i2].mTracked && iArr[i2] != 0) {
                typedXmlSerializer.attribute((java.lang.String) null, this.mFactory.mStates[i2].mName, this.mFactory.mStates[i2].mLabels[iArr[i2]]);
            }
        }
        for (int i3 = 0; i3 < jArr.length; i3++) {
            if (jArr[i3] != 0) {
                typedXmlSerializer.attributeLong((java.lang.String) null, "_" + i3, jArr[i3]);
            }
        }
        typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_STATS);
    }

    public boolean readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String name = typedXmlPullParser.getName();
        int arrayLength = this.mCounter.getArrayLength();
        long[] jArr = new long[arrayLength];
        int eventType = typedXmlPullParser.getEventType();
        while (eventType != 1 && (eventType != 3 || !typedXmlPullParser.getName().equals(name))) {
            if (eventType == 2 && typedXmlPullParser.getName().equals(XML_TAG_STATS)) {
                java.util.Arrays.fill(jArr, 0L);
                int attributeCount = typedXmlPullParser.getAttributeCount();
                int i = 0;
                for (int i2 = 0; i2 < attributeCount; i2++) {
                    java.lang.String attributeName = typedXmlPullParser.getAttributeName(i2);
                    if (attributeName.startsWith("_")) {
                        try {
                            int parseInt = java.lang.Integer.parseInt(attributeName.substring(1));
                            if (parseInt < 0 || parseInt >= arrayLength) {
                                android.util.Slog.e(TAG, "State index out of bounds: " + parseInt + " length: " + arrayLength);
                                return false;
                            }
                            jArr[parseInt] = typedXmlPullParser.getAttributeLong(i2);
                        } catch (java.lang.NumberFormatException e) {
                            throw new org.xmlpull.v1.XmlPullParserException("Unexpected index syntax: " + attributeName, typedXmlPullParser, e);
                        }
                    } else {
                        i = this.mFactory.setStateInComposite(i, attributeName, typedXmlPullParser.getAttributeValue(i2));
                        if (i == -1) {
                            return false;
                        }
                    }
                }
                this.mCounter.setValues(this.mFactory.getSerialState(i), jArr);
            }
            eventType = typedXmlPullParser.next();
        }
        return true;
    }

    public void dump(final java.io.PrintWriter printWriter, final java.util.function.Function<long[], java.lang.String> function) {
        final long[] jArr = new long[this.mCounter.getArrayLength()];
        com.android.server.power.stats.MultiStateStats.States.forEachTrackedStateCombination(this.mFactory.mStates, new java.util.function.Consumer() { // from class: com.android.server.power.stats.MultiStateStats$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.power.stats.MultiStateStats.this.lambda$dump$1(jArr, function, printWriter, (int[]) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$1(long[] jArr, java.util.function.Function function, java.io.PrintWriter printWriter, int[] iArr) {
        boolean z;
        this.mCounter.getCounts(jArr, this.mFactory.getSerialState(iArr));
        int length = jArr.length;
        int i = 0;
        while (true) {
            if (i < length) {
                if (jArr[i] == 0) {
                    i++;
                } else {
                    z = true;
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        if (!z) {
            return;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (this.mFactory.mStates[i2].mTracked) {
                if (sb.length() != 0) {
                    sb.append(" ");
                }
                sb.append(this.mFactory.mStates[i2].mLabels[iArr[i2]]);
            }
        }
        sb.append(" ");
        sb.append((java.lang.String) function.apply(jArr));
        printWriter.println(sb);
    }
}
