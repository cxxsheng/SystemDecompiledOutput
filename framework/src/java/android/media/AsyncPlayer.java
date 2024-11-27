package android.media;

/* loaded from: classes2.dex */
public class AsyncPlayer {
    private static final int PLAY = 1;
    private static final int STOP = 2;
    private static final boolean mDebug = false;
    private android.media.MediaPlayer mPlayer;
    private java.lang.String mTag;
    private android.media.AsyncPlayer.Thread mThread;
    private android.os.PowerManager.WakeLock mWakeLock;
    private final java.util.LinkedList<android.media.AsyncPlayer.Command> mCmdQueue = new java.util.LinkedList<>();
    private int mState = 2;

    private static final class Command {
        android.media.AudioAttributes attributes;
        int code;
        android.content.Context context;
        boolean looping;
        long requestTime;
        android.net.Uri uri;

        private Command() {
        }

        public java.lang.String toString() {
            return "{ code=" + this.code + " looping=" + this.looping + " attr=" + this.attributes + " uri=" + this.uri + " }";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSound(android.media.AsyncPlayer.Command command) {
        try {
            android.media.MediaPlayer mediaPlayer = new android.media.MediaPlayer();
            mediaPlayer.setAudioAttributes(command.attributes);
            mediaPlayer.setDataSource(command.context, command.uri);
            mediaPlayer.setLooping(command.looping);
            mediaPlayer.prepare();
            mediaPlayer.start();
            if (this.mPlayer != null) {
                this.mPlayer.release();
            }
            this.mPlayer = mediaPlayer;
            long uptimeMillis = android.os.SystemClock.uptimeMillis() - command.requestTime;
            if (uptimeMillis > 1000) {
                android.util.Log.w(this.mTag, "Notification sound delayed by " + uptimeMillis + "msecs");
            }
        } catch (java.lang.Exception e) {
            android.util.Log.w(this.mTag, "error loading sound for " + command.uri, e);
        }
    }

    private final class Thread extends java.lang.Thread {
        Thread() {
            super("AsyncPlayer-" + android.media.AsyncPlayer.this.mTag);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            android.media.AsyncPlayer.Command command;
            while (true) {
                synchronized (android.media.AsyncPlayer.this.mCmdQueue) {
                    command = (android.media.AsyncPlayer.Command) android.media.AsyncPlayer.this.mCmdQueue.removeFirst();
                }
                switch (command.code) {
                    case 1:
                        android.media.AsyncPlayer.this.startSound(command);
                        break;
                    case 2:
                        if (android.media.AsyncPlayer.this.mPlayer != null) {
                            long uptimeMillis = android.os.SystemClock.uptimeMillis() - command.requestTime;
                            if (uptimeMillis > 1000) {
                                android.util.Log.w(android.media.AsyncPlayer.this.mTag, "Notification stop delayed by " + uptimeMillis + "msecs");
                            }
                            android.media.AsyncPlayer.this.mPlayer.stop();
                            android.media.AsyncPlayer.this.mPlayer.release();
                            android.media.AsyncPlayer.this.mPlayer = null;
                            break;
                        } else {
                            android.util.Log.w(android.media.AsyncPlayer.this.mTag, "STOP command without a player");
                            break;
                        }
                }
                synchronized (android.media.AsyncPlayer.this.mCmdQueue) {
                    if (android.media.AsyncPlayer.this.mCmdQueue.size() == 0) {
                        android.media.AsyncPlayer.this.mThread = null;
                        android.media.AsyncPlayer.this.releaseWakeLock();
                        return;
                    }
                }
            }
        }
    }

    public AsyncPlayer(java.lang.String str) {
        if (str != null) {
            this.mTag = str;
        } else {
            this.mTag = "AsyncPlayer";
        }
    }

    public void play(android.content.Context context, android.net.Uri uri, boolean z, int i) {
        android.media.PlayerBase.deprecateStreamTypeForPlayback(i, "AsyncPlayer", "play()");
        if (context == null || uri == null) {
            return;
        }
        try {
            play(context, uri, z, new android.media.AudioAttributes.Builder().setInternalLegacyStreamType(i).build());
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.e(this.mTag, "Call to deprecated AsyncPlayer.play() method caused:", e);
        }
    }

    public void play(android.content.Context context, android.net.Uri uri, boolean z, android.media.AudioAttributes audioAttributes) throws java.lang.IllegalArgumentException {
        if (context == null || uri == null || audioAttributes == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AsyncPlayer.play() argument");
        }
        android.media.AsyncPlayer.Command command = new android.media.AsyncPlayer.Command();
        command.requestTime = android.os.SystemClock.uptimeMillis();
        command.code = 1;
        command.context = context;
        command.uri = uri;
        command.looping = z;
        command.attributes = audioAttributes;
        synchronized (this.mCmdQueue) {
            enqueueLocked(command);
            this.mState = 1;
        }
    }

    public void stop() {
        synchronized (this.mCmdQueue) {
            if (this.mState != 2) {
                android.media.AsyncPlayer.Command command = new android.media.AsyncPlayer.Command();
                command.requestTime = android.os.SystemClock.uptimeMillis();
                command.code = 2;
                enqueueLocked(command);
                this.mState = 2;
            }
        }
    }

    private void enqueueLocked(android.media.AsyncPlayer.Command command) {
        this.mCmdQueue.add(command);
        if (this.mThread == null) {
            acquireWakeLock();
            this.mThread = new android.media.AsyncPlayer.Thread();
            this.mThread.start();
        }
    }

    public void setUsesWakeLock(android.content.Context context) {
        if (this.mWakeLock != null || this.mThread != null) {
            throw new java.lang.RuntimeException("assertion failed mWakeLock=" + this.mWakeLock + " mThread=" + this.mThread);
        }
        this.mWakeLock = ((android.os.PowerManager) context.getSystemService(android.content.Context.POWER_SERVICE)).newWakeLock(1, this.mTag);
    }

    private void acquireWakeLock() {
        if (this.mWakeLock != null) {
            this.mWakeLock.acquire();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseWakeLock() {
        if (this.mWakeLock != null) {
            this.mWakeLock.release();
        }
    }
}
