package com.android.server.biometrics.sensors;

/* loaded from: classes.dex */
public abstract class BiometricUserState<T extends android.hardware.biometrics.BiometricAuthenticator.Identifier> {
    private static final java.lang.String ATTR_INVALIDATION = "authenticatorIdInvalidation_attr";
    private static final java.lang.String TAG = "UserState";
    private static final java.lang.String TAG_INVALIDATION = "authenticatorIdInvalidation_tag";
    protected final android.content.Context mContext;
    protected final java.io.File mFile;
    protected boolean mInvalidationInProgress;

    @com.android.internal.annotations.GuardedBy({"this"})
    protected final java.util.ArrayList<T> mBiometrics = new java.util.ArrayList<>();
    private final java.lang.Runnable mWriteStateRunnable = new java.lang.Runnable() { // from class: com.android.server.biometrics.sensors.BiometricUserState$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.biometrics.sensors.BiometricUserState.this.doWriteStateInternal();
        }
    };

    protected abstract void doWriteState(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.lang.Exception;

    protected abstract java.lang.String getBiometricsTag();

    protected abstract java.util.ArrayList<T> getCopy(java.util.ArrayList<T> arrayList);

    protected abstract int getNameTemplateResource();

    protected abstract void parseBiometricsLocked(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException;

    /* JADX INFO: Access modifiers changed from: private */
    public void doWriteStateInternal() {
        java.io.FileOutputStream startWrite;
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(this.mFile);
        java.io.FileOutputStream fileOutputStream = null;
        try {
            startWrite = atomicFile.startWrite();
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
            resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.startTag((java.lang.String) null, TAG_INVALIDATION);
            resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_INVALIDATION, this.mInvalidationInProgress);
            resolveSerializer.endTag((java.lang.String) null, TAG_INVALIDATION);
            doWriteState(resolveSerializer);
            resolveSerializer.endDocument();
            atomicFile.finishWrite(startWrite);
            libcore.io.IoUtils.closeQuietly(startWrite);
        } catch (java.lang.Throwable th2) {
            th = th2;
            fileOutputStream = startWrite;
            try {
                android.util.Slog.wtf(TAG, "Failed to write settings, restoring backup", th);
                atomicFile.failWrite(fileOutputStream);
                throw new java.lang.IllegalStateException("Failed to write to file: " + this.mFile.toString(), th);
            } catch (java.lang.Throwable th3) {
                libcore.io.IoUtils.closeQuietly(fileOutputStream);
                throw th3;
            }
        }
    }

    public BiometricUserState(android.content.Context context, int i, @android.annotation.NonNull java.lang.String str) {
        this.mFile = getFileForUser(i, str);
        this.mContext = context;
        synchronized (this) {
            readStateSyncLocked();
        }
    }

    public void setInvalidationInProgress(boolean z) {
        synchronized (this) {
            this.mInvalidationInProgress = z;
            scheduleWriteStateLocked();
        }
    }

    public boolean isInvalidationInProgress() {
        boolean z;
        synchronized (this) {
            z = this.mInvalidationInProgress;
        }
        return z;
    }

    public void addBiometric(T t) {
        synchronized (this) {
            this.mBiometrics.add(t);
            scheduleWriteStateLocked();
        }
    }

    public void removeBiometric(int i) {
        synchronized (this) {
            int i2 = 0;
            while (true) {
                try {
                    if (i2 >= this.mBiometrics.size()) {
                        break;
                    }
                    if (this.mBiometrics.get(i2).getBiometricId() != i) {
                        i2++;
                    } else {
                        this.mBiometrics.remove(i2);
                        scheduleWriteStateLocked();
                        break;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void renameBiometric(int i, java.lang.CharSequence charSequence) {
        synchronized (this) {
            int i2 = 0;
            while (true) {
                try {
                    if (i2 >= this.mBiometrics.size()) {
                        break;
                    }
                    if (this.mBiometrics.get(i2).getBiometricId() != i) {
                        i2++;
                    } else {
                        this.mBiometrics.get(i2).setName(charSequence);
                        scheduleWriteStateLocked();
                        break;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public java.util.List<T> getBiometrics() {
        java.util.ArrayList<T> copy;
        synchronized (this) {
            copy = getCopy(this.mBiometrics);
        }
        return copy;
    }

    public java.lang.String getUniqueName() {
        int i = 1;
        while (true) {
            java.lang.String string = this.mContext.getString(getNameTemplateResource(), java.lang.Integer.valueOf(i));
            if (isUnique(string)) {
                return string;
            }
            i++;
        }
    }

    private boolean isUnique(java.lang.String str) {
        java.util.Iterator<T> it = this.mBiometrics.iterator();
        while (it.hasNext()) {
            if (it.next().getName().equals(str)) {
                return false;
            }
        }
        return true;
    }

    private java.io.File getFileForUser(int i, @android.annotation.NonNull java.lang.String str) {
        return new java.io.File(android.os.Environment.getUserSystemDirectory(i), str);
    }

    private void scheduleWriteStateLocked() {
        android.os.AsyncTask.execute(this.mWriteStateRunnable);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void readStateSyncLocked() {
        if (!this.mFile.exists()) {
            return;
        }
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(this.mFile);
            try {
                try {
                    parseStateLocked(android.util.Xml.resolvePullParser(fileInputStream));
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    throw new java.lang.IllegalStateException("Failed parsing settings file: " + this.mFile, e);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(fileInputStream);
            }
        } catch (java.io.FileNotFoundException e2) {
            android.util.Slog.i(TAG, "No fingerprint state");
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void parseStateLocked(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    java.lang.String name = typedXmlPullParser.getName();
                    if (name.equals(getBiometricsTag())) {
                        parseBiometricsLocked(typedXmlPullParser);
                    } else if (name.equals(TAG_INVALIDATION)) {
                        this.mInvalidationInProgress = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_INVALIDATION);
                    }
                }
            } else {
                return;
            }
        }
    }
}
