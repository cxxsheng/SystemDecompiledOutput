package com.android.server.location.contexthub;

/* loaded from: classes2.dex */
public class ContextHubShellCommand extends android.os.ShellCommand {
    private final android.content.Context mContext;
    private final com.android.server.location.contexthub.ContextHubService mInternal;

    public ContextHubShellCommand(android.content.Context context, com.android.server.location.contexthub.ContextHubService contextHubService) {
        this.mInternal = contextHubService;
        this.mContext = context;
    }

    public int onCommand(java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_CONTEXT_HUB", "ContextHubShellCommand");
        if ("deny".equals(str)) {
            return runDisableAuth();
        }
        return handleDefaultCommands(str);
    }

    private int runDisableAuth() {
        this.mInternal.denyClientAuthState(java.lang.Integer.decode(getNextArgRequired()).intValue(), getNextArgRequired(), java.lang.Long.decode(getNextArgRequired()).longValue());
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("ContextHub commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  deny [contextHubId] [packageName] [nanoAppId]");
        outPrintWriter.println("    Immediately transitions the package's authentication state to denied so");
        outPrintWriter.println("    can no longer communciate with the nanoapp.");
    }
}
