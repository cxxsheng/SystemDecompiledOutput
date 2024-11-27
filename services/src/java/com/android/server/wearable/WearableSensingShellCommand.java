package com.android.server.wearable;

/* loaded from: classes.dex */
final class WearableSensingShellCommand extends android.os.ShellCommand {
    private static android.os.ParcelFileDescriptor[] sPipe;

    @android.annotation.NonNull
    private final com.android.server.wearable.WearableSensingManagerService mService;
    private static final java.lang.String TAG = com.android.server.wearable.WearableSensingShellCommand.class.getSimpleName();
    static final com.android.server.wearable.WearableSensingShellCommand.TestableCallbackInternal sTestableCallbackInternal = new com.android.server.wearable.WearableSensingShellCommand.TestableCallbackInternal();

    WearableSensingShellCommand(@android.annotation.NonNull com.android.server.wearable.WearableSensingManagerService wearableSensingManagerService) {
        this.mService = wearableSensingManagerService;
    }

    static class TestableCallbackInternal {
        private int mLastStatus;

        TestableCallbackInternal() {
        }

        public int getLastStatus() {
            return this.mLastStatus;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        public android.os.RemoteCallback createRemoteStatusCallback() {
            return new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.wearable.WearableSensingShellCommand$TestableCallbackInternal$$ExternalSyntheticLambda0
                public final void onResult(android.os.Bundle bundle) {
                    com.android.server.wearable.WearableSensingShellCommand.TestableCallbackInternal.this.lambda$createRemoteStatusCallback$0(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createRemoteStatusCallback$0(android.os.Bundle bundle) {
            int i = bundle.getInt("android.app.wearable.WearableSensingStatusBundleKey");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mLastStatus = i;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -2084150080:
                if (str.equals("get-bound-package")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1302613409:
                if (str.equals("write-to-data-stream")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1151909456:
                if (str.equals("destroy-data-stream")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1090567019:
                if (str.equals("set-data-request-rate-limit-window-size")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -482328746:
                if (str.equals("provide-data")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 586534834:
                if (str.equals("create-data-stream")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1104883342:
                if (str.equals("set-temporary-service")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1539157463:
                if (str.equals("provide-data-stream")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 2018305992:
                if (str.equals("get-last-status-code")) {
                    c = 5;
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
        outPrintWriter.println("WearableSensingCommands commands: ");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  create-data-stream: Creates a data stream to be provided.");
        outPrintWriter.println("  destroy-data-stream: Destroys a data stream if one was previously created.");
        outPrintWriter.println("  provide-data-stream USER_ID: Provides data stream to WearableSensingService.");
        outPrintWriter.println("  write-to-data-stream STRING: writes string to data stream.");
        outPrintWriter.println("  provide-data USER_ID KEY INTEGER: provide integer as data with key.");
        outPrintWriter.println("  get-last-status-code: Prints the latest request status code.");
        outPrintWriter.println("  get-bound-package USER_ID:     Print the bound package that implements the service.");
        outPrintWriter.println("  set-temporary-service USER_ID [PACKAGE_NAME] [COMPONENT_NAME DURATION]");
        outPrintWriter.println("    Temporarily (for DURATION ms) changes the service implementation.");
        outPrintWriter.println("    To reset, call with just the USER_ID argument.");
        outPrintWriter.println("  set-data-request-rate-limit-window-size WINDOW_SIZE");
        outPrintWriter.println("    Set the window size used in data request rate limiting to WINDOW_SIZE seconds.");
        outPrintWriter.println("    positive WINDOW_SIZE smaller than 20 will be automatically set to 20.");
        outPrintWriter.println("    To reset, call with 0 or a negative WINDOW_SIZE.");
    }

    private int createDataStream() {
        android.util.Slog.d(TAG, "createDataStream");
        try {
            sPipe = android.os.ParcelFileDescriptor.createPipe();
            return 0;
        } catch (java.io.IOException e) {
            android.util.Slog.d(TAG, "Failed to createDataStream.", e);
            return 0;
        }
    }

    private int destroyDataStream() {
        android.util.Slog.d(TAG, "destroyDataStream");
        try {
            if (sPipe != null) {
                sPipe[0].close();
                sPipe[1].close();
            }
        } catch (java.io.IOException e) {
            android.util.Slog.d(TAG, "Failed to destroyDataStream.", e);
        }
        return 0;
    }

    private int provideDataStream() {
        android.util.Slog.d(TAG, "provideDataStream");
        if (sPipe != null) {
            this.mService.provideDataStream(java.lang.Integer.parseInt(getNextArgRequired()), sPipe[0], sTestableCallbackInternal.createRemoteStatusCallback());
        }
        return 0;
    }

    private int writeToDataStream() {
        android.util.Slog.d(TAG, "writeToDataStream");
        if (sPipe != null) {
            try {
                new android.os.ParcelFileDescriptor.AutoCloseOutputStream(sPipe[1].dup()).write(getNextArgRequired().getBytes());
                return 0;
            } catch (java.io.IOException e) {
                android.util.Slog.d(TAG, "Failed to writeToDataStream.", e);
                return 0;
            }
        }
        return 0;
    }

    private int provideData() {
        android.util.Slog.d(TAG, "provideData");
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        java.lang.String nextArgRequired = getNextArgRequired();
        int parseInt2 = java.lang.Integer.parseInt(getNextArgRequired());
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putInt(nextArgRequired, parseInt2);
        this.mService.provideData(parseInt, persistableBundle, null, sTestableCallbackInternal.createRemoteStatusCallback());
        return 0;
    }

    private int getLastStatusCode() {
        android.util.Slog.d(TAG, "getLastStatusCode");
        getOutPrintWriter().println(sTestableCallbackInternal.getLastStatus());
        return 0;
    }

    private int setTemporaryService() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            this.mService.resetTemporaryService(parseInt);
            outPrintWriter.println("WearableSensingManagerService temporary reset. ");
            return 0;
        }
        int parseInt2 = java.lang.Integer.parseInt(getNextArgRequired());
        this.mService.setTemporaryService(parseInt, nextArg, parseInt2);
        outPrintWriter.println("WearableSensingService temporarily set to " + nextArg + " for " + parseInt2 + "ms");
        return 0;
    }

    private int getBoundPackageName() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        android.content.ComponentName componentName = this.mService.getComponentName(java.lang.Integer.parseInt(getNextArgRequired()));
        outPrintWriter.println(componentName == null ? "" : componentName.getPackageName());
        return 0;
    }

    private int setDataRequestRateLimitWindowSize() {
        android.util.Slog.d(TAG, "setDataRequestRateLimitWindowSize");
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        if (parseInt <= 0) {
            this.mService.resetDataRequestRateLimitWindowSize();
            return 0;
        }
        if (parseInt < 20) {
            parseInt = 20;
        }
        this.mService.setDataRequestRateLimitWindowSize(java.time.Duration.ofSeconds(parseInt));
        return 0;
    }
}
