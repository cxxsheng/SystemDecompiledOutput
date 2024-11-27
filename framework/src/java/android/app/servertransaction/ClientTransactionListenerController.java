package android.app.servertransaction;

/* loaded from: classes.dex */
public class ClientTransactionListenerController {
    private static android.app.servertransaction.ClientTransactionListenerController sController;
    private final android.hardware.display.DisplayManagerGlobal mDisplayManager;

    public static android.app.servertransaction.ClientTransactionListenerController getInstance() {
        android.app.servertransaction.ClientTransactionListenerController clientTransactionListenerController;
        synchronized (android.app.servertransaction.ClientTransactionListenerController.class) {
            if (sController == null) {
                sController = new android.app.servertransaction.ClientTransactionListenerController(android.hardware.display.DisplayManagerGlobal.getInstance());
            }
            clientTransactionListenerController = sController;
        }
        return clientTransactionListenerController;
    }

    public static android.app.servertransaction.ClientTransactionListenerController createInstanceForTesting(android.hardware.display.DisplayManagerGlobal displayManagerGlobal) {
        return new android.app.servertransaction.ClientTransactionListenerController(displayManagerGlobal);
    }

    private ClientTransactionListenerController(android.hardware.display.DisplayManagerGlobal displayManagerGlobal) {
        this.mDisplayManager = (android.hardware.display.DisplayManagerGlobal) java.util.Objects.requireNonNull(displayManagerGlobal);
    }

    public void onDisplayChanged(int i) {
        if (!com.android.window.flags.Flags.bundleClientTransactionFlag() || android.app.ActivityThread.isSystem()) {
            return;
        }
        this.mDisplayManager.handleDisplayChangeFromWindowManager(i);
    }
}
