package com.android.server.sensorprivacy;

/* loaded from: classes2.dex */
class PersistedState {
    private static final int CURRENT_PERSISTENCE_VERSION = 2;
    private static final int CURRENT_VERSION = 2;
    private static final java.lang.String LOG_TAG = com.android.server.sensorprivacy.PersistedState.class.getSimpleName();
    private static final java.lang.String XML_ATTRIBUTE_LAST_CHANGE = "last-change";
    private static final java.lang.String XML_ATTRIBUTE_PERSISTENCE_VERSION = "persistence-version";
    private static final java.lang.String XML_ATTRIBUTE_SENSOR = "sensor";
    private static final java.lang.String XML_ATTRIBUTE_STATE_TYPE = "state-type";
    private static final java.lang.String XML_ATTRIBUTE_TOGGLE_TYPE = "toggle-type";
    private static final java.lang.String XML_ATTRIBUTE_USER_ID = "user-id";
    private static final java.lang.String XML_ATTRIBUTE_VERSION = "version";
    private static final java.lang.String XML_TAG_SENSOR_PRIVACY = "sensor-privacy";
    private static final java.lang.String XML_TAG_SENSOR_STATE = "sensor-state";
    private final android.util.AtomicFile mAtomicFile;
    private android.util.ArrayMap<com.android.server.sensorprivacy.PersistedState.TypeUserSensor, com.android.server.sensorprivacy.SensorState> mStates = new android.util.ArrayMap<>();

    static com.android.server.sensorprivacy.PersistedState fromFile(java.lang.String str) {
        return new com.android.server.sensorprivacy.PersistedState(str);
    }

    private PersistedState(java.lang.String str) {
        this.mAtomicFile = new android.util.AtomicFile(new java.io.File(android.os.Environment.getDataSystemDirectory(), str));
        readState();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0065 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v26 */
    /* JADX WARN: Type inference failed for: r3v27 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readState() {
        java.lang.Object obj;
        boolean z;
        boolean z2;
        com.android.server.sensorprivacy.PersistedState.PVersion2 pVersion2;
        android.util.AtomicFile atomicFile;
        java.io.IOException e;
        android.util.AtomicFile atomicFile2 = this.mAtomicFile;
        if (!atomicFile2.exists()) {
            android.util.AtomicFile atomicFile3 = new android.util.AtomicFile(new java.io.File(android.os.Environment.getDataSystemDirectory(), "sensor_privacy.xml"));
            if (atomicFile3.exists()) {
                try {
                    java.io.FileInputStream openRead = atomicFile3.openRead();
                    try {
                        com.android.internal.util.XmlUtils.beginDocument(android.util.Xml.resolvePullParser(openRead), XML_TAG_SENSOR_PRIVACY);
                        if (openRead != null) {
                            try {
                                openRead.close();
                            } catch (java.io.IOException e2) {
                                e = e2;
                                atomicFile = atomicFile3;
                                android.util.Log.e(LOG_TAG, "Caught an exception reading the state from storage: ", e);
                                atomicFile3.delete();
                                atomicFile2 = atomicFile;
                                int i = 2;
                                boolean z3 = false;
                                boolean z4 = false;
                                boolean z5 = false;
                                if (atomicFile2.exists()) {
                                }
                                if (obj == null) {
                                }
                                z = obj instanceof com.android.server.sensorprivacy.PersistedState.PVersion0;
                                ?? r3 = obj;
                                if (z) {
                                }
                                z2 = r3 instanceof com.android.server.sensorprivacy.PersistedState.PVersion1;
                                pVersion2 = r3;
                                if (z2) {
                                }
                                if (pVersion2 instanceof com.android.server.sensorprivacy.PersistedState.PVersion2) {
                                }
                            } catch (org.xmlpull.v1.XmlPullParserException e3) {
                                atomicFile2 = atomicFile3;
                            }
                        }
                        atomicFile2 = atomicFile3;
                    } catch (java.lang.Throwable th) {
                        if (openRead != null) {
                            try {
                                openRead.close();
                            } catch (java.lang.Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (java.io.IOException e4) {
                    atomicFile = atomicFile2;
                    e = e4;
                    android.util.Log.e(LOG_TAG, "Caught an exception reading the state from storage: ", e);
                    atomicFile3.delete();
                    atomicFile2 = atomicFile;
                    int i2 = 2;
                    boolean z32 = false;
                    boolean z42 = false;
                    boolean z52 = false;
                    if (atomicFile2.exists()) {
                    }
                    if (obj == null) {
                    }
                    z = obj instanceof com.android.server.sensorprivacy.PersistedState.PVersion0;
                    ?? r32 = obj;
                    if (z) {
                    }
                    z2 = r32 instanceof com.android.server.sensorprivacy.PersistedState.PVersion1;
                    pVersion2 = r32;
                    if (z2) {
                    }
                    if (pVersion2 instanceof com.android.server.sensorprivacy.PersistedState.PVersion2) {
                    }
                } catch (org.xmlpull.v1.XmlPullParserException e5) {
                }
            }
        }
        int i22 = 2;
        boolean z322 = false;
        boolean z422 = false;
        boolean z522 = false;
        if (atomicFile2.exists()) {
            try {
                java.io.FileInputStream openRead2 = atomicFile2.openRead();
                try {
                    com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead2);
                    com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, XML_TAG_SENSOR_PRIVACY);
                    int i3 = 0;
                    int attributeInt = resolvePullParser.getAttributeInt((java.lang.String) null, XML_ATTRIBUTE_PERSISTENCE_VERSION, 0);
                    if (attributeInt == 0) {
                        com.android.server.sensorprivacy.PersistedState.PVersion0 pVersion0 = new com.android.server.sensorprivacy.PersistedState.PVersion0(i3);
                        readPVersion0(resolvePullParser, pVersion0);
                        obj = pVersion0;
                    } else if (attributeInt == 1) {
                        com.android.server.sensorprivacy.PersistedState.PVersion1 pVersion1 = new com.android.server.sensorprivacy.PersistedState.PVersion1(resolvePullParser.getAttributeInt((java.lang.String) null, XML_ATTRIBUTE_VERSION, 1));
                        readPVersion1(resolvePullParser, pVersion1);
                        obj = pVersion1;
                    } else if (attributeInt == 2) {
                        com.android.server.sensorprivacy.PersistedState.PVersion2 pVersion22 = new com.android.server.sensorprivacy.PersistedState.PVersion2(resolvePullParser.getAttributeInt((java.lang.String) null, XML_ATTRIBUTE_VERSION, 2));
                        readPVersion2(resolvePullParser, pVersion22);
                        obj = pVersion22;
                    } else {
                        android.util.Log.e(LOG_TAG, "Unknown persistence version: " + attributeInt + ". Deleting.", new java.lang.RuntimeException());
                        atomicFile2.delete();
                        obj = null;
                    }
                    if (openRead2 != null) {
                        openRead2.close();
                    }
                } catch (java.lang.Throwable th3) {
                    if (openRead2 != null) {
                        try {
                            openRead2.close();
                        } catch (java.lang.Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                    }
                    throw th3;
                }
            } catch (java.io.IOException | java.lang.RuntimeException | org.xmlpull.v1.XmlPullParserException e6) {
                android.util.Log.e(LOG_TAG, "Caught an exception reading the state from storage: ", e6);
                atomicFile2.delete();
                obj = null;
            }
        } else {
            obj = null;
        }
        if (obj == null) {
            obj = new com.android.server.sensorprivacy.PersistedState.PVersion2(i22);
        }
        z = obj instanceof com.android.server.sensorprivacy.PersistedState.PVersion0;
        ?? r322 = obj;
        if (z) {
            r322 = com.android.server.sensorprivacy.PersistedState.PVersion1.fromPVersion0((com.android.server.sensorprivacy.PersistedState.PVersion0) obj);
        }
        z2 = r322 instanceof com.android.server.sensorprivacy.PersistedState.PVersion1;
        pVersion2 = r322;
        if (z2) {
            pVersion2 = com.android.server.sensorprivacy.PersistedState.PVersion2.fromPVersion1((com.android.server.sensorprivacy.PersistedState.PVersion1) r322);
        }
        if (pVersion2 instanceof com.android.server.sensorprivacy.PersistedState.PVersion2) {
            this.mStates = pVersion2.mStates;
        } else {
            android.util.Log.e(LOG_TAG, "State not successfully upgraded.");
            this.mStates = new android.util.ArrayMap<>();
        }
    }

    private static void readPVersion0(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.sensorprivacy.PersistedState.PVersion0 pVersion0) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.internal.util.XmlUtils.nextElement(typedXmlPullParser);
        while (typedXmlPullParser.getEventType() != 1) {
            if ("individual-sensor-privacy".equals(typedXmlPullParser.getName())) {
                pVersion0.addState(com.android.internal.util.XmlUtils.readIntAttribute(typedXmlPullParser, XML_ATTRIBUTE_SENSOR), com.android.internal.util.XmlUtils.readBooleanAttribute(typedXmlPullParser, com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED));
                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
            } else {
                com.android.internal.util.XmlUtils.nextElement(typedXmlPullParser);
            }
        }
    }

    private static void readPVersion1(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.sensorprivacy.PersistedState.PVersion1 pVersion1) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        while (typedXmlPullParser.getEventType() != 1) {
            com.android.internal.util.XmlUtils.nextElement(typedXmlPullParser);
            if ("user".equals(typedXmlPullParser.getName())) {
                int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "id");
                int depth = typedXmlPullParser.getDepth();
                while (com.android.internal.util.XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                    if ("individual-sensor-privacy".equals(typedXmlPullParser.getName())) {
                        pVersion1.addState(attributeInt, typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTRIBUTE_SENSOR), typedXmlPullParser.getAttributeBoolean((java.lang.String) null, com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED));
                    }
                }
            }
        }
    }

    private static void readPVersion2(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.server.sensorprivacy.PersistedState.PVersion2 pVersion2) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        while (typedXmlPullParser.getEventType() != 1) {
            com.android.internal.util.XmlUtils.nextElement(typedXmlPullParser);
            if (XML_TAG_SENSOR_STATE.equals(typedXmlPullParser.getName())) {
                pVersion2.addState(typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTRIBUTE_TOGGLE_TYPE), typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTRIBUTE_USER_ID), typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTRIBUTE_SENSOR), typedXmlPullParser.getAttributeInt((java.lang.String) null, XML_ATTRIBUTE_STATE_TYPE), typedXmlPullParser.getAttributeLong((java.lang.String) null, XML_ATTRIBUTE_LAST_CHANGE));
            } else {
                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
    }

    public com.android.server.sensorprivacy.SensorState getState(int i, int i2, int i3) {
        return this.mStates.get(new com.android.server.sensorprivacy.PersistedState.TypeUserSensor(i, i2, i3));
    }

    public com.android.server.sensorprivacy.SensorState setState(int i, int i2, int i3, com.android.server.sensorprivacy.SensorState sensorState) {
        return this.mStates.put(new com.android.server.sensorprivacy.PersistedState.TypeUserSensor(i, i2, i3), sensorState);
    }

    private static class TypeUserSensor {
        int mSensor;
        int mType;
        int mUserId;

        TypeUserSensor(int i, int i2, int i3) {
            this.mType = i;
            this.mUserId = i2;
            this.mSensor = i3;
        }

        TypeUserSensor(com.android.server.sensorprivacy.PersistedState.TypeUserSensor typeUserSensor) {
            this(typeUserSensor.mType, typeUserSensor.mUserId, typeUserSensor.mSensor);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.sensorprivacy.PersistedState.TypeUserSensor)) {
                return false;
            }
            com.android.server.sensorprivacy.PersistedState.TypeUserSensor typeUserSensor = (com.android.server.sensorprivacy.PersistedState.TypeUserSensor) obj;
            return this.mType == typeUserSensor.mType && this.mUserId == typeUserSensor.mUserId && this.mSensor == typeUserSensor.mSensor;
        }

        public int hashCode() {
            return (((this.mType * 31) + this.mUserId) * 31) + this.mSensor;
        }
    }

    void schedulePersist() {
        int size = this.mStates.size();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (int i = 0; i < size; i++) {
            arrayMap.put(new com.android.server.sensorprivacy.PersistedState.TypeUserSensor(this.mStates.keyAt(i)), new com.android.server.sensorprivacy.SensorState(this.mStates.valueAt(i)));
        }
        com.android.server.IoThread.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.BiConsumer() { // from class: com.android.server.sensorprivacy.PersistedState$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                ((com.android.server.sensorprivacy.PersistedState) obj).persist((android.util.ArrayMap) obj2);
            }
        }, this, arrayMap));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void persist(android.util.ArrayMap<com.android.server.sensorprivacy.PersistedState.TypeUserSensor, com.android.server.sensorprivacy.SensorState> arrayMap) {
        java.io.FileOutputStream startWrite;
        java.io.FileOutputStream fileOutputStream = null;
        try {
            startWrite = this.mAtomicFile.startWrite();
        } catch (java.io.IOException e) {
            e = e;
        }
        try {
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.startTag((java.lang.String) null, XML_TAG_SENSOR_PRIVACY);
            resolveSerializer.attributeInt((java.lang.String) null, XML_ATTRIBUTE_PERSISTENCE_VERSION, 2);
            resolveSerializer.attributeInt((java.lang.String) null, XML_ATTRIBUTE_VERSION, 2);
            for (int i = 0; i < arrayMap.size(); i++) {
                com.android.server.sensorprivacy.PersistedState.TypeUserSensor keyAt = arrayMap.keyAt(i);
                com.android.server.sensorprivacy.SensorState valueAt = arrayMap.valueAt(i);
                if (keyAt.mType == 1) {
                    resolveSerializer.startTag((java.lang.String) null, XML_TAG_SENSOR_STATE);
                    resolveSerializer.attributeInt((java.lang.String) null, XML_ATTRIBUTE_TOGGLE_TYPE, keyAt.mType);
                    resolveSerializer.attributeInt((java.lang.String) null, XML_ATTRIBUTE_USER_ID, keyAt.mUserId);
                    resolveSerializer.attributeInt((java.lang.String) null, XML_ATTRIBUTE_SENSOR, keyAt.mSensor);
                    resolveSerializer.attributeInt((java.lang.String) null, XML_ATTRIBUTE_STATE_TYPE, valueAt.getState());
                    resolveSerializer.attributeLong((java.lang.String) null, XML_ATTRIBUTE_LAST_CHANGE, valueAt.getLastChange());
                    resolveSerializer.endTag((java.lang.String) null, XML_TAG_SENSOR_STATE);
                }
            }
            resolveSerializer.endTag((java.lang.String) null, XML_TAG_SENSOR_PRIVACY);
            resolveSerializer.endDocument();
            this.mAtomicFile.finishWrite(startWrite);
        } catch (java.io.IOException e2) {
            e = e2;
            fileOutputStream = startWrite;
            android.util.Log.e(LOG_TAG, "Caught an exception persisting the sensor privacy state: ", e);
            this.mAtomicFile.failWrite(fileOutputStream);
        }
    }

    void dump(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream) {
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        int size = this.mStates.size();
        for (int i = 0; i < size; i++) {
            int i2 = this.mStates.keyAt(i).mType;
            int i3 = this.mStates.keyAt(i).mUserId;
            int i4 = this.mStates.keyAt(i).mSensor;
            android.util.SparseArray sparseArray2 = (android.util.SparseArray) sparseArray.get(i3);
            if (sparseArray2 == null) {
                sparseArray2 = new android.util.SparseArray();
                sparseArray.put(i3, sparseArray2);
            }
            sparseArray2.put(i4, new android.util.Pair(java.lang.Integer.valueOf(i2), this.mStates.valueAt(i)));
        }
        dualDumpOutputStream.write("storage_implementation", 1138166333444L, com.android.server.sensorprivacy.SensorPrivacyStateControllerImpl.class.getName());
        int size2 = sparseArray.size();
        for (int i5 = 0; i5 < size2; i5++) {
            int keyAt = sparseArray.keyAt(i5);
            long start = dualDumpOutputStream.start(com.android.server.voiceinteraction.DatabaseHelper.SoundModelContract.KEY_USERS, 2246267895811L);
            long j = 1120986464257L;
            dualDumpOutputStream.write("user_id", 1120986464257L, keyAt);
            android.util.SparseArray sparseArray3 = (android.util.SparseArray) sparseArray.valueAt(i5);
            int i6 = 0;
            for (int size3 = sparseArray3.size(); i6 < size3; size3 = size3) {
                int keyAt2 = sparseArray3.keyAt(i6);
                int intValue = ((java.lang.Integer) ((android.util.Pair) sparseArray3.valueAt(i6)).first).intValue();
                com.android.server.sensorprivacy.SensorState sensorState = (com.android.server.sensorprivacy.SensorState) ((android.util.Pair) sparseArray3.valueAt(i6)).second;
                long start2 = dualDumpOutputStream.start("sensors", 2246267895812L);
                dualDumpOutputStream.write(XML_ATTRIBUTE_SENSOR, j, keyAt2);
                long start3 = dualDumpOutputStream.start("toggles", 2246267895810L);
                dualDumpOutputStream.write("toggle_type", 1159641169924L, intValue);
                dualDumpOutputStream.write("state_type", 1159641169925L, sensorState.getState());
                dualDumpOutputStream.write("last_change", 1112396529667L, sensorState.getLastChange());
                dualDumpOutputStream.end(start3);
                dualDumpOutputStream.end(start2);
                i6++;
                j = 1120986464257L;
                size2 = size2;
            }
            dualDumpOutputStream.end(start);
        }
    }

    void forEachKnownState(com.android.internal.util.function.QuadConsumer<java.lang.Integer, java.lang.Integer, java.lang.Integer, com.android.server.sensorprivacy.SensorState> quadConsumer) {
        int size = this.mStates.size();
        for (int i = 0; i < size; i++) {
            com.android.server.sensorprivacy.PersistedState.TypeUserSensor keyAt = this.mStates.keyAt(i);
            quadConsumer.accept(java.lang.Integer.valueOf(keyAt.mType), java.lang.Integer.valueOf(keyAt.mUserId), java.lang.Integer.valueOf(keyAt.mSensor), this.mStates.valueAt(i));
        }
    }

    private static class PVersion0 {
        private android.util.SparseArray<com.android.server.sensorprivacy.SensorState> mIndividualEnabled;

        private PVersion0(int i) {
            this.mIndividualEnabled = new android.util.SparseArray<>();
            if (i != 0) {
                throw new java.lang.RuntimeException("Only version 0 supported");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addState(int i, boolean z) {
            this.mIndividualEnabled.put(i, new com.android.server.sensorprivacy.SensorState(z));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void upgrade() {
        }
    }

    private static class PVersion1 {
        private android.util.SparseArray<android.util.SparseArray<com.android.server.sensorprivacy.SensorState>> mIndividualEnabled;

        private PVersion1(int i) {
            this.mIndividualEnabled = new android.util.SparseArray<>();
            if (i != 1) {
                throw new java.lang.RuntimeException("Only version 1 supported");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static com.android.server.sensorprivacy.PersistedState.PVersion1 fromPVersion0(com.android.server.sensorprivacy.PersistedState.PVersion0 pVersion0) {
            pVersion0.upgrade();
            com.android.server.sensorprivacy.PersistedState.PVersion1 pVersion1 = new com.android.server.sensorprivacy.PersistedState.PVersion1(1);
            int[] iArr = {0};
            try {
                iArr = ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserIds();
            } catch (java.lang.Exception e) {
                android.util.Log.e(com.android.server.sensorprivacy.PersistedState.LOG_TAG, "Unable to get users.", e);
            }
            for (int i : iArr) {
                for (int i2 = 0; i2 < pVersion0.mIndividualEnabled.size(); i2++) {
                    pVersion1.addState(i, pVersion0.mIndividualEnabled.keyAt(i2), ((com.android.server.sensorprivacy.SensorState) pVersion0.mIndividualEnabled.valueAt(i2)).isEnabled());
                }
            }
            return pVersion1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addState(int i, int i2, boolean z) {
            android.util.SparseArray<com.android.server.sensorprivacy.SensorState> sparseArray = this.mIndividualEnabled.get(i, new android.util.SparseArray<>());
            this.mIndividualEnabled.put(i, sparseArray);
            sparseArray.put(i2, new com.android.server.sensorprivacy.SensorState(z));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void upgrade() {
        }
    }

    private static class PVersion2 {
        private android.util.ArrayMap<com.android.server.sensorprivacy.PersistedState.TypeUserSensor, com.android.server.sensorprivacy.SensorState> mStates;

        private PVersion2(int i) {
            this.mStates = new android.util.ArrayMap<>();
            if (i != 2) {
                throw new java.lang.RuntimeException("Only version 2 supported");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static com.android.server.sensorprivacy.PersistedState.PVersion2 fromPVersion1(com.android.server.sensorprivacy.PersistedState.PVersion1 pVersion1) {
            pVersion1.upgrade();
            com.android.server.sensorprivacy.PersistedState.PVersion2 pVersion2 = new com.android.server.sensorprivacy.PersistedState.PVersion2(2);
            android.util.SparseArray sparseArray = pVersion1.mIndividualEnabled;
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                int keyAt = sparseArray.keyAt(i);
                android.util.SparseArray sparseArray2 = (android.util.SparseArray) sparseArray.valueAt(i);
                int size2 = sparseArray2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    int keyAt2 = sparseArray2.keyAt(i2);
                    com.android.server.sensorprivacy.SensorState sensorState = (com.android.server.sensorprivacy.SensorState) sparseArray2.valueAt(i2);
                    pVersion2.addState(1, keyAt, keyAt2, sensorState.getState(), sensorState.getLastChange());
                }
            }
            return pVersion2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addState(int i, int i2, int i3, int i4, long j) {
            this.mStates.put(new com.android.server.sensorprivacy.PersistedState.TypeUserSensor(i, i2, i3), new com.android.server.sensorprivacy.SensorState(i4, j));
        }
    }

    public void resetForTesting() {
        this.mStates = new android.util.ArrayMap<>();
    }
}
