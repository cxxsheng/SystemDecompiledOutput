package android.window;

/* loaded from: classes4.dex */
public class WindowOrganizer {
    private static final android.util.Singleton<android.window.IWindowOrganizerController> IWindowOrganizerControllerSingleton = new android.util.Singleton<android.window.IWindowOrganizerController>() { // from class: android.window.WindowOrganizer.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public android.window.IWindowOrganizerController create() {
            try {
                return android.app.ActivityTaskManager.getService().getWindowOrganizerController();
            } catch (android.os.RemoteException e) {
                return null;
            }
        }
    };

    public void applyTransaction(android.window.WindowContainerTransaction windowContainerTransaction) {
        try {
            if (!windowContainerTransaction.isEmpty()) {
                getWindowOrganizerController().applyTransaction(windowContainerTransaction);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int applySyncTransaction(android.window.WindowContainerTransaction windowContainerTransaction, android.window.WindowContainerTransactionCallback windowContainerTransactionCallback) {
        try {
            return getWindowOrganizerController().applySyncTransaction(windowContainerTransaction, windowContainerTransactionCallback.mInterface);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.IBinder startNewTransition(int i, android.window.WindowContainerTransaction windowContainerTransaction) {
        try {
            return getWindowOrganizerController().startNewTransition(i, windowContainerTransaction);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startTransition(android.os.IBinder iBinder, android.window.WindowContainerTransaction windowContainerTransaction) {
        try {
            getWindowOrganizerController().startTransition(iBinder, windowContainerTransaction);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void finishTransition(android.os.IBinder iBinder, android.window.WindowContainerTransaction windowContainerTransaction) {
        try {
            getWindowOrganizerController().finishTransition(iBinder, windowContainerTransaction);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int startLegacyTransition(int i, android.view.RemoteAnimationAdapter remoteAnimationAdapter, android.window.WindowContainerTransactionCallback windowContainerTransactionCallback, android.window.WindowContainerTransaction windowContainerTransaction) {
        try {
            return getWindowOrganizerController().startLegacyTransition(i, remoteAnimationAdapter, windowContainerTransactionCallback.mInterface, windowContainerTransaction);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerTransitionPlayer(android.window.ITransitionPlayer iTransitionPlayer) {
        try {
            getWindowOrganizerController().registerTransitionPlayer(iTransitionPlayer);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.window.ITransitionMetricsReporter getTransitionMetricsReporter() {
        try {
            return getWindowOrganizerController().getTransitionMetricsReporter();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shareTransactionQueue() {
        try {
            android.os.IBinder applyToken = getWindowOrganizerController().getApplyToken();
            if (applyToken == null) {
                return false;
            }
            android.view.SurfaceControl.Transaction.setDefaultApplyToken(applyToken);
            return true;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static android.window.IWindowOrganizerController getWindowOrganizerController() {
        return IWindowOrganizerControllerSingleton.get();
    }
}
