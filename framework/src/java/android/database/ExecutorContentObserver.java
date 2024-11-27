package android.database;

/* loaded from: classes.dex */
public abstract class ExecutorContentObserver extends android.database.ContentObserver {
    public ExecutorContentObserver(java.util.concurrent.Executor executor) {
        super(executor, 0);
    }
}
