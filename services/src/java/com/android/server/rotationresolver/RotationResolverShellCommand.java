package com.android.server.rotationresolver;

/* loaded from: classes2.dex */
final class RotationResolverShellCommand extends android.os.ShellCommand {
    private static final int INITIAL_RESULT_CODE = -1;
    static final com.android.server.rotationresolver.RotationResolverShellCommand.TestableRotationCallbackInternal sTestableRotationCallbackInternal = new com.android.server.rotationresolver.RotationResolverShellCommand.TestableRotationCallbackInternal();

    @android.annotation.NonNull
    private final com.android.server.rotationresolver.RotationResolverManagerService mService;

    RotationResolverShellCommand(@android.annotation.NonNull com.android.server.rotationresolver.RotationResolverManagerService rotationResolverManagerService) {
        this.mService = rotationResolverManagerService;
    }

    static class TestableRotationCallbackInternal implements android.rotationresolver.RotationResolverInternal.RotationResolverCallbackInternal {
        private int mLastCallbackResultCode = -1;

        TestableRotationCallbackInternal() {
        }

        public void onSuccess(int i) {
            this.mLastCallbackResultCode = i;
        }

        public void onFailure(int i) {
            this.mLastCallbackResultCode = i;
        }

        public void reset() {
            this.mLastCallbackResultCode = -1;
        }

        public int getLastCallbackCode() {
            return this.mLastCallbackResultCode;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(@android.annotation.Nullable java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -2084150080:
                if (str.equals("get-bound-package")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 384662079:
                if (str.equals("resolve-rotation")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1104883342:
                if (str.equals("set-temporary-service")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1820466124:
                if (str.equals("get-last-resolution")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return handleDefaultCommands(str);
    }

    private int getBoundPackageName() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        android.content.ComponentName componentNameShellCommand = this.mService.getComponentNameShellCommand(java.lang.Integer.parseInt(getNextArgRequired()));
        outPrintWriter.println(componentNameShellCommand == null ? "" : componentNameShellCommand.getPackageName());
        return 0;
    }

    private int setTemporaryService() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            this.mService.resetTemporaryService(parseInt);
            outPrintWriter.println("RotationResolverService temporary reset. ");
            return 0;
        }
        int parseInt2 = java.lang.Integer.parseInt(getNextArgRequired());
        this.mService.setTemporaryService(parseInt, nextArg, parseInt2);
        outPrintWriter.println("RotationResolverService temporarily set to " + nextArg + " for " + parseInt2 + "ms");
        return 0;
    }

    private int runResolveRotation() {
        this.mService.resolveRotationShellCommand(java.lang.Integer.parseInt(getNextArgRequired()), sTestableRotationCallbackInternal, new android.service.rotationresolver.RotationResolutionRequest("", 0, 0, true, 2000L));
        return 0;
    }

    private int getLastResolution() {
        getOutPrintWriter().println(sTestableRotationCallbackInternal.getLastCallbackCode());
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Rotation Resolver commands: ");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  resolve-rotation USER_ID: request a rotation resolution.");
        outPrintWriter.println("  get-last-resolution: show the last rotation resolution result.");
        outPrintWriter.println("  get-bound-package USER_ID:");
        outPrintWriter.println("    Print the bound package that implements the service.");
        outPrintWriter.println("  set-temporary-service USER_ID [COMPONENT_NAME DURATION]");
        outPrintWriter.println("    Temporarily (for DURATION ms) changes the service implementation.");
        outPrintWriter.println("    To reset, call with just the USER_ID argument.");
    }
}
