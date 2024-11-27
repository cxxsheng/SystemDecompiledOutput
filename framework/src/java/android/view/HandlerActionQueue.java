package android.view;

/* loaded from: classes4.dex */
public class HandlerActionQueue {
    private android.view.HandlerActionQueue.HandlerAction[] mActions;
    private int mCount;

    public void post(java.lang.Runnable runnable) {
        postDelayed(runnable, 0L);
    }

    public void postDelayed(java.lang.Runnable runnable, long j) {
        android.view.HandlerActionQueue.HandlerAction handlerAction = new android.view.HandlerActionQueue.HandlerAction(runnable, j);
        synchronized (this) {
            if (this.mActions == null) {
                this.mActions = new android.view.HandlerActionQueue.HandlerAction[4];
            }
            this.mActions = (android.view.HandlerActionQueue.HandlerAction[]) com.android.internal.util.GrowingArrayUtils.append(this.mActions, this.mCount, handlerAction);
            this.mCount++;
        }
    }

    public void removeCallbacks(java.lang.Runnable runnable) {
        synchronized (this) {
            int i = this.mCount;
            android.view.HandlerActionQueue.HandlerAction[] handlerActionArr = this.mActions;
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                if (!handlerActionArr[i3].matches(runnable)) {
                    if (i2 != i3) {
                        handlerActionArr[i2] = handlerActionArr[i3];
                    }
                    i2++;
                }
            }
            this.mCount = i2;
            while (i2 < i) {
                handlerActionArr[i2] = null;
                i2++;
            }
        }
    }

    public void executeActions(android.os.Handler handler) {
        synchronized (this) {
            android.view.HandlerActionQueue.HandlerAction[] handlerActionArr = this.mActions;
            int i = this.mCount;
            for (int i2 = 0; i2 < i; i2++) {
                android.view.HandlerActionQueue.HandlerAction handlerAction = handlerActionArr[i2];
                handler.postDelayed(handlerAction.action, handlerAction.delay);
            }
            this.mActions = null;
            this.mCount = 0;
        }
    }

    public int size() {
        return this.mCount;
    }

    public java.lang.Runnable getRunnable(int i) {
        if (i >= this.mCount) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return this.mActions[i].action;
    }

    public long getDelay(int i) {
        if (i >= this.mCount) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return this.mActions[i].delay;
    }

    private static class HandlerAction {
        final java.lang.Runnable action;
        final long delay;

        public HandlerAction(java.lang.Runnable runnable, long j) {
            this.action = runnable;
            this.delay = j;
        }

        public boolean matches(java.lang.Runnable runnable) {
            return (runnable == null && this.action == null) || (this.action != null && this.action.equals(runnable));
        }
    }
}
