package com.android.internal.util;

/* loaded from: classes5.dex */
public class ProgressReporter {
    private static final int STATE_FINISHED = 2;
    private static final int STATE_INIT = 0;
    private static final int STATE_STARTED = 1;
    private final int mId;
    private final android.os.RemoteCallbackList<android.os.IProgressListener> mListeners = new android.os.RemoteCallbackList<>();
    private int mState = 0;
    private int mProgress = 0;
    private android.os.Bundle mExtras = new android.os.Bundle();
    private int[] mSegmentRange = {0, 100};

    public ProgressReporter(int i) {
        this.mId = i;
    }

    public void addListener(android.os.IProgressListener iProgressListener) {
        if (iProgressListener == null) {
            return;
        }
        synchronized (this) {
            this.mListeners.register(iProgressListener);
            switch (this.mState) {
                case 1:
                    try {
                        iProgressListener.onStarted(this.mId, null);
                        iProgressListener.onProgress(this.mId, this.mProgress, this.mExtras);
                        break;
                    } catch (android.os.RemoteException e) {
                        break;
                    }
                case 2:
                    try {
                        iProgressListener.onFinished(this.mId, null);
                        break;
                    } catch (android.os.RemoteException e2) {
                        break;
                    }
            }
        }
    }

    public void setProgress(int i) {
        setProgress(i, 100, null);
    }

    public void setProgress(int i, java.lang.CharSequence charSequence) {
        setProgress(i, 100, charSequence);
    }

    public void setProgress(int i, int i2) {
        setProgress(i, i2, null);
    }

    public void setProgress(int i, int i2, java.lang.CharSequence charSequence) {
        synchronized (this) {
            if (this.mState != 1) {
                throw new java.lang.IllegalStateException("Must be started to change progress");
            }
            this.mProgress = this.mSegmentRange[0] + android.util.MathUtils.constrain((i * this.mSegmentRange[1]) / i2, 0, this.mSegmentRange[1]);
            if (charSequence != null) {
                this.mExtras.putCharSequence(android.content.Intent.EXTRA_TITLE, charSequence);
            }
            notifyProgress(this.mId, this.mProgress, this.mExtras);
        }
    }

    public int[] startSegment(int i) {
        int[] iArr;
        synchronized (this) {
            iArr = this.mSegmentRange;
            this.mSegmentRange = new int[]{this.mProgress, (i * this.mSegmentRange[1]) / 100};
        }
        return iArr;
    }

    public void endSegment(int[] iArr) {
        synchronized (this) {
            this.mProgress = this.mSegmentRange[0] + this.mSegmentRange[1];
            this.mSegmentRange = iArr;
        }
    }

    public int getProgress() {
        return this.mProgress;
    }

    int[] getSegmentRange() {
        return this.mSegmentRange;
    }

    public void start() {
        synchronized (this) {
            this.mState = 1;
            notifyStarted(this.mId, null);
            notifyProgress(this.mId, this.mProgress, this.mExtras);
        }
    }

    public void finish() {
        synchronized (this) {
            this.mState = 2;
            notifyFinished(this.mId, null);
            this.mListeners.kill();
        }
    }

    private void notifyStarted(int i, android.os.Bundle bundle) {
        for (int beginBroadcast = this.mListeners.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
            try {
                this.mListeners.getBroadcastItem(beginBroadcast).onStarted(i, bundle);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mListeners.finishBroadcast();
    }

    private void notifyProgress(int i, int i2, android.os.Bundle bundle) {
        for (int beginBroadcast = this.mListeners.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
            try {
                this.mListeners.getBroadcastItem(beginBroadcast).onProgress(i, i2, bundle);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mListeners.finishBroadcast();
    }

    private void notifyFinished(int i, android.os.Bundle bundle) {
        for (int beginBroadcast = this.mListeners.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
            try {
                this.mListeners.getBroadcastItem(beginBroadcast).onFinished(i, bundle);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mListeners.finishBroadcast();
    }
}
