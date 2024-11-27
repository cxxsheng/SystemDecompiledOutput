package com.android.server.resources;

/* loaded from: classes2.dex */
public class ResourcesManagerShellCommand extends android.os.ShellCommand {
    private static final java.lang.String TAG = "ResourcesManagerShellCommand";
    private final android.content.res.IResourcesManager mInterface;

    public ResourcesManagerShellCommand(android.content.res.IResourcesManager iResourcesManager) {
        this.mInterface = iResourcesManager;
    }

    public int onCommand(java.lang.String str) {
        boolean z;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        try {
            switch (str.hashCode()) {
                case 3095028:
                    if (str.equals("dump")) {
                        z = false;
                        break;
                    }
                default:
                    z = -1;
                    break;
            }
            switch (z) {
            }
        } catch (android.os.RemoteException e) {
            errPrintWriter.println("Remote exception: " + e);
            return -1;
        } catch (java.lang.IllegalArgumentException e2) {
            errPrintWriter.println("Error: " + e2.getMessage());
            return -1;
        }
        return handleDefaultCommands(str);
    }

    private int dumpResources() throws android.os.RemoteException {
        java.lang.String nextArgRequired = getNextArgRequired();
        try {
            android.os.ParcelFileDescriptor dup = android.os.ParcelFileDescriptor.dup(getOutFileDescriptor());
            try {
                final android.os.ConditionVariable conditionVariable = new android.os.ConditionVariable();
                if (!this.mInterface.dumpResources(nextArgRequired, dup, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.resources.ResourcesManagerShellCommand$$ExternalSyntheticLambda0
                    public final void onResult(android.os.Bundle bundle) {
                        conditionVariable.open();
                    }
                }, (android.os.Handler) null))) {
                    getErrPrintWriter().println("RESOURCES DUMP FAILED on process " + nextArgRequired);
                    if (dup != null) {
                        dup.close();
                    }
                    return -1;
                }
                conditionVariable.block(5000L);
                if (dup != null) {
                    dup.close();
                    return 0;
                }
                return 0;
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Exception while dumping resources", e);
            getErrPrintWriter().println("Exception while dumping resources: " + e.getMessage());
            return -1;
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Resources manager commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  dump <PROCESS>");
        outPrintWriter.println("    Dump the Resources objects in use as well as the history of Resources");
    }
}
