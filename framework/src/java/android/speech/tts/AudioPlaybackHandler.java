package android.speech.tts;

/* loaded from: classes3.dex */
class AudioPlaybackHandler {
    private static final boolean DBG = false;
    private static final java.lang.String TAG = "TTS.AudioPlaybackHandler";
    private final java.util.concurrent.LinkedBlockingQueue<android.speech.tts.PlaybackQueueItem> mQueue = new java.util.concurrent.LinkedBlockingQueue<>();
    private volatile android.speech.tts.PlaybackQueueItem mCurrentWorkItem = null;
    private final java.lang.Thread mHandlerThread = new java.lang.Thread(new android.speech.tts.AudioPlaybackHandler.MessageLoop(), "TTS.AudioPlaybackThread");

    AudioPlaybackHandler() {
    }

    public void start() {
        this.mHandlerThread.start();
    }

    private void stop(android.speech.tts.PlaybackQueueItem playbackQueueItem) {
        if (playbackQueueItem == null) {
            return;
        }
        playbackQueueItem.stop(-2);
    }

    public void enqueue(android.speech.tts.PlaybackQueueItem playbackQueueItem) {
        try {
            this.mQueue.put(playbackQueueItem);
        } catch (java.lang.InterruptedException e) {
        }
    }

    public void stopForApp(java.lang.Object obj) {
        removeWorkItemsFor(obj);
        android.speech.tts.PlaybackQueueItem playbackQueueItem = this.mCurrentWorkItem;
        if (playbackQueueItem != null && playbackQueueItem.getCallerIdentity() == obj) {
            stop(playbackQueueItem);
        }
    }

    public void stop() {
        removeAllMessages();
        stop(this.mCurrentWorkItem);
    }

    public boolean isSpeaking() {
        return (this.mQueue.peek() == null && this.mCurrentWorkItem == null) ? false : true;
    }

    public void quit() {
        removeAllMessages();
        stop(this.mCurrentWorkItem);
        this.mHandlerThread.interrupt();
    }

    private void removeAllMessages() {
        this.mQueue.clear();
    }

    private void removeWorkItemsFor(java.lang.Object obj) {
        java.util.Iterator<android.speech.tts.PlaybackQueueItem> it = this.mQueue.iterator();
        while (it.hasNext()) {
            android.speech.tts.PlaybackQueueItem next = it.next();
            if (next.getCallerIdentity() == obj) {
                it.remove();
                stop(next);
            }
        }
    }

    private final class MessageLoop implements java.lang.Runnable {
        private MessageLoop() {
        }

        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    android.speech.tts.PlaybackQueueItem playbackQueueItem = (android.speech.tts.PlaybackQueueItem) android.speech.tts.AudioPlaybackHandler.this.mQueue.take();
                    android.speech.tts.AudioPlaybackHandler.this.mCurrentWorkItem = playbackQueueItem;
                    playbackQueueItem.run();
                    android.speech.tts.AudioPlaybackHandler.this.mCurrentWorkItem = null;
                } catch (java.lang.InterruptedException e) {
                    return;
                }
            }
        }
    }
}
