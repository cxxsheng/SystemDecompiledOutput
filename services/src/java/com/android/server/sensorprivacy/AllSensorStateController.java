package com.android.server.sensorprivacy;

/* loaded from: classes2.dex */
class AllSensorStateController {
    private static final java.lang.String LOG_TAG = com.android.server.sensorprivacy.AllSensorStateController.class.getSimpleName();
    private static final java.lang.String SENSOR_PRIVACY_XML_FILE = "sensor_privacy.xml";
    private static final java.lang.String XML_ATTRIBUTE_ENABLED = "enabled";
    private static final java.lang.String XML_TAG_SENSOR_PRIVACY = "all-sensor-privacy";
    private static final java.lang.String XML_TAG_SENSOR_PRIVACY_LEGACY = "sensor-privacy";
    private static com.android.server.sensorprivacy.AllSensorStateController sInstance;
    private final android.util.AtomicFile mAtomicFile = new android.util.AtomicFile(new java.io.File(android.os.Environment.getDataSystemDirectory(), SENSOR_PRIVACY_XML_FILE));
    private boolean mEnabled;
    private com.android.server.sensorprivacy.SensorPrivacyStateController.AllSensorPrivacyListener mListener;
    private android.os.Handler mListenerHandler;

    static com.android.server.sensorprivacy.AllSensorStateController getInstance() {
        if (sInstance == null) {
            sInstance = new com.android.server.sensorprivacy.AllSensorStateController();
        }
        return sInstance;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x003f, code lost:
    
        r7.mEnabled = com.android.internal.util.XmlUtils.readBooleanAttribute(r2, "enabled", false) | r7.mEnabled;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private AllSensorStateController() {
        if (!this.mAtomicFile.exists()) {
            return;
        }
        try {
            java.io.FileInputStream openRead = this.mAtomicFile.openRead();
            try {
                com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                while (true) {
                    if (resolvePullParser.getEventType() == 1) {
                        break;
                    }
                    java.lang.String name = resolvePullParser.getName();
                    if (XML_TAG_SENSOR_PRIVACY.equals(name)) {
                        break;
                    }
                    if (XML_TAG_SENSOR_PRIVACY_LEGACY.equals(name)) {
                        this.mEnabled |= com.android.internal.util.XmlUtils.readBooleanAttribute(resolvePullParser, "enabled", false);
                    }
                    if ("user".equals(name) && com.android.internal.util.XmlUtils.readIntAttribute(resolvePullParser, "id", -1) == 0) {
                        this.mEnabled |= com.android.internal.util.XmlUtils.readBooleanAttribute(resolvePullParser, "enabled");
                    }
                    com.android.internal.util.XmlUtils.nextElement(resolvePullParser);
                }
                if (openRead != null) {
                    openRead.close();
                }
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
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.e(LOG_TAG, "Caught an exception reading the state from storage: ", e);
            this.mEnabled = false;
        }
    }

    public boolean getAllSensorStateLocked() {
        return this.mEnabled;
    }

    public void setAllSensorStateLocked(boolean z) {
        if (this.mEnabled != z) {
            this.mEnabled = z;
            if (this.mListener != null && this.mListenerHandler != null) {
                android.os.Handler handler = this.mListenerHandler;
                final com.android.server.sensorprivacy.SensorPrivacyStateController.AllSensorPrivacyListener allSensorPrivacyListener = this.mListener;
                java.util.Objects.requireNonNull(allSensorPrivacyListener);
                handler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.sensorprivacy.AllSensorStateController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.sensorprivacy.SensorPrivacyStateController.AllSensorPrivacyListener.this.onAllSensorPrivacyChanged(((java.lang.Boolean) obj).booleanValue());
                    }
                }, java.lang.Boolean.valueOf(z)));
            }
        }
    }

    void setAllSensorPrivacyListenerLocked(android.os.Handler handler, com.android.server.sensorprivacy.SensorPrivacyStateController.AllSensorPrivacyListener allSensorPrivacyListener) {
        java.util.Objects.requireNonNull(handler);
        java.util.Objects.requireNonNull(allSensorPrivacyListener);
        if (this.mListener != null) {
            throw new java.lang.IllegalStateException("Listener is already set");
        }
        this.mListener = allSensorPrivacyListener;
        this.mListenerHandler = handler;
    }

    public void schedulePersistLocked() {
        com.android.server.IoThread.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.sensorprivacy.AllSensorStateController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.sensorprivacy.AllSensorStateController.this.persist(((java.lang.Boolean) obj).booleanValue());
            }
        }, java.lang.Boolean.valueOf(this.mEnabled)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void persist(boolean z) {
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
            resolveSerializer.attributeBoolean((java.lang.String) null, "enabled", z);
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

    void resetForTesting() {
        this.mListener = null;
        this.mListenerHandler = null;
        this.mEnabled = false;
    }

    void dumpLocked(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream) {
    }
}
