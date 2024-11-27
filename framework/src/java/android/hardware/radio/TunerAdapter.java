package android.hardware.radio;

/* loaded from: classes2.dex */
final class TunerAdapter extends android.hardware.radio.RadioTuner {
    private static final java.lang.String TAG = "BroadcastRadio.TunerAdapter";
    private int mBand;
    private final android.hardware.radio.TunerCallbackAdapter mCallback;
    private boolean mIsClosed;
    private java.util.Map<java.lang.String, java.lang.String> mLegacyListFilter;
    private android.hardware.radio.ProgramList mLegacyListProxy;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.hardware.radio.ITuner mTuner;

    TunerAdapter(android.hardware.radio.ITuner iTuner, android.hardware.radio.TunerCallbackAdapter tunerCallbackAdapter, int i) {
        this.mTuner = (android.hardware.radio.ITuner) java.util.Objects.requireNonNull(iTuner, "Tuner cannot be null");
        this.mCallback = (android.hardware.radio.TunerCallbackAdapter) java.util.Objects.requireNonNull(tunerCallbackAdapter, "Callback cannot be null");
        this.mBand = i;
    }

    @Override // android.hardware.radio.RadioTuner
    public void close() {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                android.util.Log.v(TAG, "Tuner is already closed");
                return;
            }
            this.mIsClosed = true;
            if (this.mLegacyListProxy != null) {
                this.mLegacyListProxy.close();
                this.mLegacyListProxy = null;
            }
            this.mCallback.close();
            try {
                this.mTuner.close();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Exception trying to close tuner", e);
            }
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int setConfiguration(android.hardware.radio.RadioManager.BandConfig bandConfig) {
        if (bandConfig == null) {
            return -22;
        }
        try {
            this.mTuner.setConfiguration(bandConfig);
            synchronized (this.mLock) {
                this.mBand = bandConfig.getType();
            }
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Service died", e);
            return -32;
        } catch (java.lang.IllegalArgumentException e2) {
            android.util.Log.e(TAG, "Can't set configuration", e2);
            return -22;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int getConfiguration(android.hardware.radio.RadioManager.BandConfig[] bandConfigArr) {
        if (bandConfigArr == null || bandConfigArr.length != 1) {
            throw new java.lang.IllegalArgumentException("The argument must be an array of length 1");
        }
        try {
            bandConfigArr[0] = this.mTuner.getConfiguration();
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Service died", e);
            return -32;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int setMute(boolean z) {
        try {
            this.mTuner.setMuted(z);
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Service died", e);
            return -32;
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.e(TAG, "Can't set muted", e2);
            return Integer.MIN_VALUE;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean getMute() {
        try {
            return this.mTuner.isMuted();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Service died", e);
            return true;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int step(int i, boolean z) {
        try {
            android.hardware.radio.ITuner iTuner = this.mTuner;
            boolean z2 = true;
            if (i != 1) {
                z2 = false;
            }
            iTuner.step(z2, z);
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Service died", e);
            return -32;
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.e(TAG, "Can't step", e2);
            return -38;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int scan(int i, boolean z) {
        try {
            android.hardware.radio.ITuner iTuner = this.mTuner;
            boolean z2 = true;
            if (i != 1) {
                z2 = false;
            }
            iTuner.seek(z2, z);
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Service died", e);
            return -32;
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.e(TAG, "Can't scan", e2);
            return -38;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int seek(int i, boolean z) {
        try {
            android.hardware.radio.ITuner iTuner = this.mTuner;
            boolean z2 = true;
            if (i != 1) {
                z2 = false;
            }
            iTuner.seek(z2, z);
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Service died", e);
            return -32;
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.e(TAG, "Can't seek", e2);
            return -38;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int tune(int i, int i2) {
        int i3;
        try {
            synchronized (this.mLock) {
                i3 = this.mBand;
            }
            this.mTuner.tune(android.hardware.radio.ProgramSelector.createAmFmSelector(i3, i, i2));
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Service died", e);
            return -32;
        } catch (java.lang.IllegalArgumentException e2) {
            android.util.Log.e(TAG, "Can't tune", e2);
            return -22;
        } catch (java.lang.IllegalStateException e3) {
            android.util.Log.e(TAG, "Can't tune", e3);
            return -38;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public void tune(android.hardware.radio.ProgramSelector programSelector) {
        try {
            this.mTuner.tune(programSelector);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int cancel() {
        try {
            this.mTuner.cancel();
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Service died", e);
            return -32;
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.e(TAG, "Can't cancel", e2);
            return -38;
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public void cancelAnnouncement() {
        try {
            this.mTuner.cancelAnnouncement();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public int getProgramInformation(android.hardware.radio.RadioManager.ProgramInfo[] programInfoArr) {
        if (programInfoArr == null || programInfoArr.length != 1) {
            android.util.Log.e(TAG, "The argument must be an array of length 1");
            return -22;
        }
        android.hardware.radio.RadioManager.ProgramInfo currentProgramInformation = this.mCallback.getCurrentProgramInformation();
        if (currentProgramInformation == null) {
            android.util.Log.w(TAG, "Didn't get program info yet");
            return -38;
        }
        programInfoArr[0] = currentProgramInformation;
        return 0;
    }

    @Override // android.hardware.radio.RadioTuner
    public android.graphics.Bitmap getMetadataImage(int i) {
        if (i == 0) {
            throw new java.lang.IllegalArgumentException("Invalid metadata image id 0");
        }
        try {
            android.graphics.Bitmap image = this.mTuner.getImage(i);
            if (image == null) {
                throw new java.lang.IllegalArgumentException("Metadata image with id " + i + " is not available");
            }
            return image;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean startBackgroundScan() {
        try {
            return this.mTuner.startBackgroundScan();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public java.util.List<android.hardware.radio.RadioManager.ProgramInfo> getProgramList(java.util.Map<java.lang.String, java.lang.String> map) {
        synchronized (this.mLock) {
            if (this.mLegacyListProxy == null || !java.util.Objects.equals(this.mLegacyListFilter, map)) {
                android.util.Log.i(TAG, "Program list filter has changed, requesting new list");
                this.mLegacyListProxy = new android.hardware.radio.ProgramList();
                this.mLegacyListFilter = map;
                this.mCallback.clearLastCompleteList();
                this.mCallback.setProgramListObserver(this.mLegacyListProxy, new android.hardware.radio.ProgramList.OnCloseListener() { // from class: android.hardware.radio.TunerAdapter$$ExternalSyntheticLambda0
                    @Override // android.hardware.radio.ProgramList.OnCloseListener
                    public final void onClose() {
                        android.util.Log.i(android.hardware.radio.TunerAdapter.TAG, "Empty closeListener in programListObserver");
                    }
                });
            }
        }
        try {
            this.mTuner.startProgramListUpdates(new android.hardware.radio.ProgramList.Filter(map));
            java.util.List<android.hardware.radio.RadioManager.ProgramInfo> lastCompleteList = this.mCallback.getLastCompleteList();
            if (lastCompleteList == null) {
                throw new java.lang.IllegalStateException("Program list is not ready yet");
            }
            return lastCompleteList;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public android.hardware.radio.ProgramList getDynamicProgramList(android.hardware.radio.ProgramList.Filter filter) {
        synchronized (this.mLock) {
            if (this.mLegacyListProxy != null) {
                this.mLegacyListProxy.close();
                this.mLegacyListProxy = null;
            }
            this.mLegacyListFilter = null;
        }
        android.hardware.radio.ProgramList programList = new android.hardware.radio.ProgramList();
        this.mCallback.setProgramListObserver(programList, new android.hardware.radio.ProgramList.OnCloseListener() { // from class: android.hardware.radio.TunerAdapter$$ExternalSyntheticLambda1
            @Override // android.hardware.radio.ProgramList.OnCloseListener
            public final void onClose() {
                android.hardware.radio.TunerAdapter.this.lambda$getDynamicProgramList$1();
            }
        });
        try {
            this.mTuner.startProgramListUpdates(filter);
            return programList;
        } catch (android.os.RemoteException e) {
            this.mCallback.setProgramListObserver(null, new android.hardware.radio.ProgramList.OnCloseListener() { // from class: android.hardware.radio.TunerAdapter$$ExternalSyntheticLambda2
                @Override // android.hardware.radio.ProgramList.OnCloseListener
                public final void onClose() {
                    android.util.Log.i(android.hardware.radio.TunerAdapter.TAG, "Empty closeListener in programListObserver");
                }
            });
            throw new java.lang.RuntimeException("Service died", e);
        } catch (java.lang.UnsupportedOperationException e2) {
            android.util.Log.i(TAG, "Program list is not supported with this hardware");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDynamicProgramList$1() {
        try {
            this.mTuner.stopProgramListUpdates();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Couldn't stop program list updates", e);
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.e(TAG, "Tuner may already be closed", e2);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean isAnalogForced() {
        try {
            return isConfigFlagSet(2);
        } catch (java.lang.UnsupportedOperationException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public void setAnalogForced(boolean z) {
        try {
            setConfigFlag(2, z);
        } catch (java.lang.UnsupportedOperationException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean isConfigFlagSupported(int i) {
        try {
            return this.mTuner.isConfigFlagSupported(i);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean isConfigFlagSet(int i) {
        try {
            return this.mTuner.isConfigFlagSet(convertForceAnalogConfigFlag(i));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public void setConfigFlag(int i, boolean z) {
        try {
            this.mTuner.setConfigFlag(convertForceAnalogConfigFlag(i), z);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public java.util.Map<java.lang.String, java.lang.String> setParameters(java.util.Map<java.lang.String, java.lang.String> map) {
        try {
            return this.mTuner.setParameters((java.util.Map) java.util.Objects.requireNonNull(map, "Parameters cannot be null"));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public java.util.Map<java.lang.String, java.lang.String> getParameters(java.util.List<java.lang.String> list) {
        try {
            return this.mTuner.getParameters((java.util.List) java.util.Objects.requireNonNull(list, "Keys cannot be null"));
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Service died", e);
        }
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean isAntennaConnected() {
        return this.mCallback.isAntennaConnected();
    }

    @Override // android.hardware.radio.RadioTuner
    public boolean hasControl() {
        try {
            return !this.mTuner.isClosed();
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    private int convertForceAnalogConfigFlag(int i) throws android.os.RemoteException {
        if (android.hardware.radio.Flags.hdRadioImproved() && i == 2 && this.mTuner.isConfigFlagSupported(10)) {
            return 10;
        }
        return i;
    }
}
