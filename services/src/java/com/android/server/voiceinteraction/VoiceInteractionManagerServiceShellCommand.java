package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
final class VoiceInteractionManagerServiceShellCommand extends android.os.ShellCommand {
    private static final java.lang.String TAG = "VoiceInteractionManager";
    private static final long TIMEOUT_MS = 5000;
    private final com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub mService;

    VoiceInteractionManagerServiceShellCommand(com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub) {
        this.mService = voiceInteractionManagerServiceStub;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        switch (str.hashCode()) {
            case -1097066044:
                if (str.equals("set-debug-hotword-logging")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 3202370:
                if (str.equals("hide")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3529469:
                if (str.equals("show")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1671308008:
                if (str.equals("disable")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1718895687:
                if (str.equals("restart-detection")) {
                    c = 3;
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

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("VoiceInteraction Service (voiceinteraction) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  show");
            outPrintWriter.println("    Shows a session for the active service");
            outPrintWriter.println("");
            outPrintWriter.println("  hide");
            outPrintWriter.println("    Hides the current session");
            outPrintWriter.println("");
            outPrintWriter.println("  disable [true|false]");
            outPrintWriter.println("    Temporarily disable (when true) service");
            outPrintWriter.println("");
            outPrintWriter.println("  restart-detection");
            outPrintWriter.println("    Force a restart of a hotword detection service");
            outPrintWriter.println("");
            outPrintWriter.println("  set-debug-hotword-logging [true|false]");
            outPrintWriter.println("    Temporarily enable or disable debug logging for hotword result.");
            outPrintWriter.println("    The debug logging will be reset after one hour from last enable.");
            outPrintWriter.println("");
            outPrintWriter.close();
        } catch (java.lang.Throwable th) {
            if (outPrintWriter != null) {
                try {
                    outPrintWriter.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private int requestShow(final java.io.PrintWriter printWriter) {
        android.util.Slog.i(TAG, "requestShow()");
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger();
        com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback = new com.android.internal.app.IVoiceInteractionSessionShowCallback.Stub() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerServiceShellCommand.1
            public void onFailed() throws android.os.RemoteException {
                android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionManagerServiceShellCommand.TAG, "onFailed()");
                printWriter.println("callback failed");
                atomicInteger.set(1);
                countDownLatch.countDown();
            }

            public void onShown() throws android.os.RemoteException {
                android.util.Slog.d(com.android.server.voiceinteraction.VoiceInteractionManagerServiceShellCommand.TAG, "onShown()");
                atomicInteger.set(0);
                countDownLatch.countDown();
            }
        };
        try {
            if (!this.mService.showSessionForActiveService(new android.os.Bundle(), 0, null, iVoiceInteractionSessionShowCallback, null)) {
                printWriter.println("showSessionForActiveService() returned false");
                return 1;
            }
            if (!countDownLatch.await(TIMEOUT_MS, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                printWriter.printf("Callback not called in %d ms\n", java.lang.Long.valueOf(TIMEOUT_MS));
                return 1;
            }
            return 0;
        } catch (java.lang.Exception e) {
            return handleError(printWriter, "showSessionForActiveService()", e);
        }
    }

    private int requestHide(java.io.PrintWriter printWriter) {
        android.util.Slog.i(TAG, "requestHide()");
        try {
            this.mService.hideCurrentSession();
            return 0;
        } catch (java.lang.Exception e) {
            return handleError(printWriter, "requestHide()", e);
        }
    }

    private int requestDisable(java.io.PrintWriter printWriter) {
        boolean parseBoolean = java.lang.Boolean.parseBoolean(getNextArgRequired());
        android.util.Slog.i(TAG, "requestDisable(): " + parseBoolean);
        try {
            this.mService.setDisabled(parseBoolean);
            return 0;
        } catch (java.lang.Exception e) {
            return handleError(printWriter, "requestDisable()", e);
        }
    }

    private int requestRestartDetection(java.io.PrintWriter printWriter) {
        android.util.Slog.i(TAG, "requestRestartDetection()");
        try {
            this.mService.forceRestartHotwordDetector();
            return 0;
        } catch (java.lang.Exception e) {
            return handleError(printWriter, "requestRestartDetection()", e);
        }
    }

    private int setDebugHotwordLogging(java.io.PrintWriter printWriter) {
        boolean parseBoolean = java.lang.Boolean.parseBoolean(getNextArgRequired());
        android.util.Slog.i(TAG, "setDebugHotwordLogging(): " + parseBoolean);
        try {
            this.mService.setDebugHotwordLogging(parseBoolean);
            return 0;
        } catch (java.lang.Exception e) {
            return handleError(printWriter, "setDebugHotwordLogging()", e);
        }
    }

    private static int handleError(java.io.PrintWriter printWriter, java.lang.String str, java.lang.Exception exc) {
        android.util.Slog.e(TAG, "error calling " + str, exc);
        printWriter.printf("Error calling %s: %s\n", str, exc);
        return 1;
    }
}
