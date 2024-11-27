package android.window;

/* loaded from: classes4.dex */
public class TaskFragmentOrganizer extends android.window.WindowOrganizer {
    public static final java.lang.String KEY_ERROR_CALLBACK_OP_TYPE = "operation_type";
    public static final java.lang.String KEY_ERROR_CALLBACK_TASK_FRAGMENT_INFO = "task_fragment_info";
    public static final java.lang.String KEY_ERROR_CALLBACK_THROWABLE = "fragment_throwable";
    public static final int TASK_FRAGMENT_TRANSIT_CHANGE = 6;
    public static final int TASK_FRAGMENT_TRANSIT_CLOSE = 2;
    public static final int TASK_FRAGMENT_TRANSIT_NONE = 0;
    public static final int TASK_FRAGMENT_TRANSIT_OPEN = 1;
    private final java.util.concurrent.Executor mExecutor;
    private final android.window.ITaskFragmentOrganizer mInterface = new android.window.TaskFragmentOrganizer.AnonymousClass1();
    private final android.window.TaskFragmentOrganizerToken mToken = new android.window.TaskFragmentOrganizerToken(this.mInterface);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TaskFragmentTransitionType {
    }

    public static android.os.Bundle putErrorInfoInBundle(java.lang.Throwable th, android.window.TaskFragmentInfo taskFragmentInfo, int i) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putSerializable(KEY_ERROR_CALLBACK_THROWABLE, th);
        if (taskFragmentInfo != null) {
            bundle.putParcelable(KEY_ERROR_CALLBACK_TASK_FRAGMENT_INFO, taskFragmentInfo);
        }
        bundle.putInt(KEY_ERROR_CALLBACK_OP_TYPE, i);
        return bundle;
    }

    public TaskFragmentOrganizer(java.util.concurrent.Executor executor) {
        this.mExecutor = executor;
    }

    public java.util.concurrent.Executor getExecutor() {
        return this.mExecutor;
    }

    public void registerOrganizer() {
        try {
            getController().registerOrganizer(this.mInterface, false);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerOrganizer(boolean z) {
        try {
            getController().registerOrganizer(this.mInterface, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterOrganizer() {
        try {
            getController().unregisterOrganizer(this.mInterface);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerRemoteAnimations(android.view.RemoteAnimationDefinition remoteAnimationDefinition) {
        try {
            getController().registerRemoteAnimations(this.mInterface, remoteAnimationDefinition);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterRemoteAnimations() {
        try {
            getController().unregisterRemoteAnimations(this.mInterface);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onTransactionHandled(android.os.IBinder iBinder, android.window.WindowContainerTransaction windowContainerTransaction, int i, boolean z) {
        windowContainerTransaction.setTaskFragmentOrganizer(this.mInterface);
        try {
            getController().onTransactionHandled(iBinder, windowContainerTransaction, i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.window.WindowOrganizer
    public void applyTransaction(android.window.WindowContainerTransaction windowContainerTransaction) {
        throw new java.lang.RuntimeException("Not allowed!");
    }

    public void applyTransaction(android.window.WindowContainerTransaction windowContainerTransaction, int i, boolean z) {
        if (windowContainerTransaction.isEmpty()) {
            return;
        }
        windowContainerTransaction.setTaskFragmentOrganizer(this.mInterface);
        try {
            getController().applyTransaction(windowContainerTransaction, i, z, null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void applySystemTransaction(android.window.WindowContainerTransaction windowContainerTransaction, int i, android.window.RemoteTransition remoteTransition) {
        if (windowContainerTransaction.isEmpty()) {
            return;
        }
        windowContainerTransaction.setTaskFragmentOrganizer(this.mInterface);
        try {
            getController().applyTransaction(windowContainerTransaction, i, remoteTransition != null, remoteTransition);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onTransactionReady(android.window.TaskFragmentTransaction taskFragmentTransaction) {
        onTransactionHandled(taskFragmentTransaction.getTransactionToken(), new android.window.WindowContainerTransaction(), 0, false);
    }

    /* renamed from: android.window.TaskFragmentOrganizer$1, reason: invalid class name */
    class AnonymousClass1 extends android.window.ITaskFragmentOrganizer.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTransactionReady$0(android.window.TaskFragmentTransaction taskFragmentTransaction) {
            android.window.TaskFragmentOrganizer.this.onTransactionReady(taskFragmentTransaction);
        }

        @Override // android.window.ITaskFragmentOrganizer
        public void onTransactionReady(final android.window.TaskFragmentTransaction taskFragmentTransaction) {
            android.window.TaskFragmentOrganizer.this.mExecutor.execute(new java.lang.Runnable() { // from class: android.window.TaskFragmentOrganizer$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.window.TaskFragmentOrganizer.AnonymousClass1.this.lambda$onTransactionReady$0(taskFragmentTransaction);
                }
            });
        }
    }

    public android.window.TaskFragmentOrganizerToken getOrganizerToken() {
        return this.mToken;
    }

    private android.window.ITaskFragmentOrganizerController getController() {
        try {
            return getWindowOrganizerController().getTaskFragmentOrganizerController();
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public boolean isActivityEmbedded(android.os.IBinder iBinder) {
        try {
            return getController().isActivityEmbedded(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
