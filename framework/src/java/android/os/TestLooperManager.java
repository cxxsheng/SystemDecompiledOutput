package android.os;

/* loaded from: classes3.dex */
public class TestLooperManager {
    private static final android.util.ArraySet<android.os.Looper> sHeldLoopers = new android.util.ArraySet<>();
    private final java.util.concurrent.LinkedBlockingQueue<android.os.TestLooperManager.MessageExecution> mExecuteQueue = new java.util.concurrent.LinkedBlockingQueue<>();
    private final android.os.Looper mLooper;
    private boolean mLooperBlocked;
    private final android.os.MessageQueue mQueue;
    private boolean mReleased;

    public TestLooperManager(android.os.Looper looper) {
        synchronized (sHeldLoopers) {
            if (sHeldLoopers.contains(looper)) {
                throw new java.lang.RuntimeException("TestLooperManager already held for this looper");
            }
            sHeldLoopers.add(looper);
        }
        this.mLooper = looper;
        this.mQueue = this.mLooper.getQueue();
        new android.os.Handler(looper).post(new android.os.TestLooperManager.LooperHolder());
    }

    public android.os.MessageQueue getMessageQueue() {
        checkReleased();
        return this.mQueue;
    }

    @java.lang.Deprecated
    public android.os.MessageQueue getQueue() {
        return getMessageQueue();
    }

    public android.os.Message next() {
        while (!this.mLooperBlocked) {
            synchronized (this) {
                try {
                    wait();
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
        checkReleased();
        return this.mQueue.next();
    }

    public void release() {
        synchronized (sHeldLoopers) {
            sHeldLoopers.remove(this.mLooper);
        }
        checkReleased();
        this.mReleased = true;
        this.mExecuteQueue.add(new android.os.TestLooperManager.MessageExecution());
    }

    public void execute(android.os.Message message) {
        checkReleased();
        if (android.os.Looper.myLooper() == this.mLooper) {
            message.target.dispatchMessage(message);
            return;
        }
        android.os.TestLooperManager.MessageExecution messageExecution = new android.os.TestLooperManager.MessageExecution();
        messageExecution.m = message;
        synchronized (messageExecution) {
            this.mExecuteQueue.add(messageExecution);
            try {
                messageExecution.wait();
            } catch (java.lang.InterruptedException e) {
            }
            if (messageExecution.response != null) {
                throw new java.lang.RuntimeException(messageExecution.response);
            }
        }
    }

    public void recycle(android.os.Message message) {
        checkReleased();
        message.recycleUnchecked();
    }

    public boolean hasMessages(android.os.Handler handler, java.lang.Object obj, int i) {
        checkReleased();
        return this.mQueue.hasMessages(handler, i, obj);
    }

    public boolean hasMessages(android.os.Handler handler, java.lang.Object obj, java.lang.Runnable runnable) {
        checkReleased();
        return this.mQueue.hasMessages(handler, runnable, obj);
    }

    private void checkReleased() {
        if (this.mReleased) {
            throw new java.lang.RuntimeException("release() has already be called");
        }
    }

    private class LooperHolder implements java.lang.Runnable {
        private LooperHolder() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (android.os.TestLooperManager.this) {
                android.os.TestLooperManager.this.mLooperBlocked = true;
                android.os.TestLooperManager.this.notify();
            }
            while (!android.os.TestLooperManager.this.mReleased) {
                try {
                    android.os.TestLooperManager.MessageExecution messageExecution = (android.os.TestLooperManager.MessageExecution) android.os.TestLooperManager.this.mExecuteQueue.take();
                    if (messageExecution.m != null) {
                        processMessage(messageExecution);
                    }
                } catch (java.lang.InterruptedException e) {
                }
            }
            synchronized (android.os.TestLooperManager.this) {
                android.os.TestLooperManager.this.mLooperBlocked = false;
            }
        }

        private void processMessage(android.os.TestLooperManager.MessageExecution messageExecution) {
            synchronized (messageExecution) {
                try {
                    messageExecution.m.target.dispatchMessage(messageExecution.m);
                    messageExecution.response = null;
                } finally {
                    messageExecution.notifyAll();
                }
                messageExecution.notifyAll();
            }
        }
    }

    private static class MessageExecution {
        private android.os.Message m;
        private java.lang.Throwable response;

        private MessageExecution() {
        }
    }
}
