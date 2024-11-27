package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class TaskDrainer<T> {
    private static final java.lang.String TAG = "TaskDrainer";
    private final boolean DEBUG;
    private boolean mDrainFinished;
    private boolean mDraining;
    private final java.util.Set<T> mEarlyFinishedTaskSet;
    private final java.util.concurrent.Executor mExecutor;
    private final android.hardware.camera2.utils.TaskDrainer.DrainListener mListener;
    private final java.lang.Object mLock;
    private final java.lang.String mName;
    private final java.util.Set<T> mTaskSet;

    public interface DrainListener {
        void onDrained();
    }

    public TaskDrainer(java.util.concurrent.Executor executor, android.hardware.camera2.utils.TaskDrainer.DrainListener drainListener) {
        this.DEBUG = false;
        this.mTaskSet = new java.util.HashSet();
        this.mEarlyFinishedTaskSet = new java.util.HashSet();
        this.mLock = new java.lang.Object();
        this.mDraining = false;
        this.mDrainFinished = false;
        this.mExecutor = (java.util.concurrent.Executor) com.android.internal.util.Preconditions.checkNotNull(executor, "executor must not be null");
        this.mListener = (android.hardware.camera2.utils.TaskDrainer.DrainListener) com.android.internal.util.Preconditions.checkNotNull(drainListener, "listener must not be null");
        this.mName = null;
    }

    public TaskDrainer(java.util.concurrent.Executor executor, android.hardware.camera2.utils.TaskDrainer.DrainListener drainListener, java.lang.String str) {
        this.DEBUG = false;
        this.mTaskSet = new java.util.HashSet();
        this.mEarlyFinishedTaskSet = new java.util.HashSet();
        this.mLock = new java.lang.Object();
        this.mDraining = false;
        this.mDrainFinished = false;
        this.mExecutor = (java.util.concurrent.Executor) com.android.internal.util.Preconditions.checkNotNull(executor, "executor must not be null");
        this.mListener = (android.hardware.camera2.utils.TaskDrainer.DrainListener) com.android.internal.util.Preconditions.checkNotNull(drainListener, "listener must not be null");
        this.mName = str;
    }

    public void taskStarted(T t) {
        synchronized (this.mLock) {
            if (this.mDraining) {
                throw new java.lang.IllegalStateException("Can't start more tasks after draining has begun");
            }
            if (!this.mEarlyFinishedTaskSet.remove(t) && !this.mTaskSet.add(t)) {
                throw new java.lang.IllegalStateException("Task " + t + " was already started");
            }
        }
    }

    public void taskFinished(T t) {
        synchronized (this.mLock) {
            if (!this.mTaskSet.remove(t) && !this.mEarlyFinishedTaskSet.add(t)) {
                throw new java.lang.IllegalStateException("Task " + t + " was already finished");
            }
            checkIfDrainFinished();
        }
    }

    public void beginDrain() {
        synchronized (this.mLock) {
            if (!this.mDraining) {
                this.mDraining = true;
                checkIfDrainFinished();
            }
        }
    }

    private void checkIfDrainFinished() {
        if (this.mTaskSet.isEmpty() && this.mDraining && !this.mDrainFinished) {
            this.mDrainFinished = true;
            postDrained();
        }
    }

    private void postDrained() {
        this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.utils.TaskDrainer$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.hardware.camera2.utils.TaskDrainer.this.lambda$postDrained$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postDrained$0() {
        this.mListener.onDrained();
    }
}
