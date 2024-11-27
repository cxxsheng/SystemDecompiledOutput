package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class TaskSingleDrainer {
    private final java.lang.Object mSingleTask = new java.lang.Object();
    private final android.hardware.camera2.utils.TaskDrainer<java.lang.Object> mTaskDrainer;

    public TaskSingleDrainer(java.util.concurrent.Executor executor, android.hardware.camera2.utils.TaskDrainer.DrainListener drainListener) {
        this.mTaskDrainer = new android.hardware.camera2.utils.TaskDrainer<>(executor, drainListener);
    }

    public TaskSingleDrainer(java.util.concurrent.Executor executor, android.hardware.camera2.utils.TaskDrainer.DrainListener drainListener, java.lang.String str) {
        this.mTaskDrainer = new android.hardware.camera2.utils.TaskDrainer<>(executor, drainListener, str);
    }

    public void taskStarted() {
        this.mTaskDrainer.taskStarted(this.mSingleTask);
    }

    public void beginDrain() {
        this.mTaskDrainer.beginDrain();
    }

    public void taskFinished() {
        this.mTaskDrainer.taskFinished(this.mSingleTask);
    }
}
