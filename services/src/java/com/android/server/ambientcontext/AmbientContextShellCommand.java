package com.android.server.ambientcontext;

/* loaded from: classes.dex */
final class AmbientContextShellCommand extends android.os.ShellCommand {

    @android.annotation.NonNull
    private final com.android.server.ambientcontext.AmbientContextManagerService mService;
    private static final java.lang.String TAG = com.android.server.ambientcontext.AmbientContextShellCommand.class.getSimpleName();
    private static final android.app.ambientcontext.AmbientContextEventRequest REQUEST = new android.app.ambientcontext.AmbientContextEventRequest.Builder().addEventType(1).addEventType(2).addEventType(3).build();
    private static final int WEARABLE_AMBIENT_CONTEXT_EVENT_FOR_TESTING = 100001;
    private static final android.app.ambientcontext.AmbientContextEventRequest WEARABLE_REQUEST = new android.app.ambientcontext.AmbientContextEventRequest.Builder().addEventType(WEARABLE_AMBIENT_CONTEXT_EVENT_FOR_TESTING).build();
    private static final android.app.ambientcontext.AmbientContextEventRequest MIXED_REQUEST = new android.app.ambientcontext.AmbientContextEventRequest.Builder().addEventType(1).addEventType(WEARABLE_AMBIENT_CONTEXT_EVENT_FOR_TESTING).build();
    static final com.android.server.ambientcontext.AmbientContextShellCommand.TestableCallbackInternal sTestableCallbackInternal = new com.android.server.ambientcontext.AmbientContextShellCommand.TestableCallbackInternal();

    AmbientContextShellCommand(@android.annotation.NonNull com.android.server.ambientcontext.AmbientContextManagerService ambientContextManagerService) {
        this.mService = ambientContextManagerService;
    }

    static class TestableCallbackInternal {
        private java.util.List<android.app.ambientcontext.AmbientContextEvent> mLastEvents;
        private int mLastStatus;

        TestableCallbackInternal() {
        }

        public java.util.List<android.app.ambientcontext.AmbientContextEvent> getLastEvents() {
            return this.mLastEvents;
        }

        public int getLastStatus() {
            return this.mLastStatus;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        public android.app.ambientcontext.IAmbientContextObserver createAmbientContextObserver() {
            return new android.app.ambientcontext.IAmbientContextObserver.Stub() { // from class: com.android.server.ambientcontext.AmbientContextShellCommand.TestableCallbackInternal.1
                public void onEvents(java.util.List<android.app.ambientcontext.AmbientContextEvent> list) throws android.os.RemoteException {
                    com.android.server.ambientcontext.AmbientContextShellCommand.TestableCallbackInternal.this.mLastEvents = list;
                    java.lang.System.out.println("Detection events available: " + list);
                }

                public void onRegistrationComplete(int i) throws android.os.RemoteException {
                    com.android.server.ambientcontext.AmbientContextShellCommand.TestableCallbackInternal.this.mLastStatus = i;
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        public android.os.RemoteCallback createRemoteStatusCallback() {
            return new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.ambientcontext.AmbientContextShellCommand$TestableCallbackInternal$$ExternalSyntheticLambda0
                public final void onResult(android.os.Bundle bundle) {
                    com.android.server.ambientcontext.AmbientContextShellCommand.TestableCallbackInternal.this.lambda$createRemoteStatusCallback$0(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createRemoteStatusCallback$0(android.os.Bundle bundle) {
            int i = bundle.getInt("android.app.ambientcontext.AmbientContextStatusBundleKey");
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
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -2048517510:
                if (str.equals("stop-detection")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1827236351:
                if (str.equals("query-mixed-service-status")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -920381716:
                if (str.equals("start-detection-wearable")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -919702712:
                if (str.equals("start-detection-mixed")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -108354651:
                if (str.equals("set-temporary-services")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 943496953:
                if (str.equals("query-wearable-service-status")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1104883342:
                if (str.equals("set-temporary-service")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1519475119:
                if (str.equals("query-service-status")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 2018305992:
                if (str.equals("get-last-status-code")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 2084757210:
                if (str.equals("start-detection")) {
                    c = 0;
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

    private int runStartDetection() {
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        java.lang.String nextArgRequired = getNextArgRequired();
        this.mService.startDetection(parseInt, REQUEST, nextArgRequired, sTestableCallbackInternal.createAmbientContextObserver());
        this.mService.newClientAdded(parseInt, REQUEST, nextArgRequired, sTestableCallbackInternal.createAmbientContextObserver());
        return 0;
    }

    private int runWearableStartDetection() {
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        java.lang.String nextArgRequired = getNextArgRequired();
        this.mService.startDetection(parseInt, WEARABLE_REQUEST, nextArgRequired, sTestableCallbackInternal.createAmbientContextObserver());
        this.mService.newClientAdded(parseInt, WEARABLE_REQUEST, nextArgRequired, sTestableCallbackInternal.createAmbientContextObserver());
        return 0;
    }

    private int runMixedStartDetection() {
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        java.lang.String nextArgRequired = getNextArgRequired();
        this.mService.startDetection(parseInt, MIXED_REQUEST, nextArgRequired, sTestableCallbackInternal.createAmbientContextObserver());
        this.mService.newClientAdded(parseInt, MIXED_REQUEST, nextArgRequired, sTestableCallbackInternal.createAmbientContextObserver());
        return 0;
    }

    private int runStopDetection() {
        this.mService.stopAmbientContextEvent(java.lang.Integer.parseInt(getNextArgRequired()), getNextArgRequired());
        return 0;
    }

    private int runQueryServiceStatus() {
        this.mService.queryServiceStatus(java.lang.Integer.parseInt(getNextArgRequired()), getNextArgRequired(), new int[]{1, 2}, sTestableCallbackInternal.createRemoteStatusCallback());
        return 0;
    }

    private int runQueryWearableServiceStatus() {
        this.mService.queryServiceStatus(java.lang.Integer.parseInt(getNextArgRequired()), getNextArgRequired(), new int[]{WEARABLE_AMBIENT_CONTEXT_EVENT_FOR_TESTING}, sTestableCallbackInternal.createRemoteStatusCallback());
        return 0;
    }

    private int runQueryMixedServiceStatus() {
        this.mService.queryServiceStatus(java.lang.Integer.parseInt(getNextArgRequired()), getNextArgRequired(), new int[]{1, WEARABLE_AMBIENT_CONTEXT_EVENT_FOR_TESTING}, sTestableCallbackInternal.createRemoteStatusCallback());
        return 0;
    }

    private int getLastStatusCode() {
        getOutPrintWriter().println(sTestableCallbackInternal.getLastStatus());
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("AmbientContextEvent commands: ");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  start-detection USER_ID PACKAGE_NAME: Starts AmbientContextEvent detection.");
        outPrintWriter.println("  start-detection-wearable USER_ID PACKAGE_NAME: Starts AmbientContextEvent detection for wearable.");
        outPrintWriter.println("  start-detection-mixed USER_ID PACKAGE_NAME:  Starts AmbientContextEvent detection for mixed events.");
        outPrintWriter.println("  stop-detection USER_ID PACKAGE_NAME: Stops AmbientContextEvent detection.");
        outPrintWriter.println("  get-last-status-code: Prints the latest request status code.");
        outPrintWriter.println("  query-service-status USER_ID PACKAGE_NAME: Prints the service status code.");
        outPrintWriter.println("  query-wearable-service-status USER_ID PACKAGE_NAME: Prints the service status code for wearable.");
        outPrintWriter.println("  query-mixed-service-status USER_ID PACKAGE_NAME: Prints the service status code for mixed events.");
        outPrintWriter.println("  get-bound-package USER_ID:     Print the bound package that implements the service.");
        outPrintWriter.println("  set-temporary-service USER_ID [PACKAGE_NAME] [COMPONENT_NAME DURATION]");
        outPrintWriter.println("    Temporarily (for DURATION ms) changes the service implementation.");
        outPrintWriter.println("    To reset, call with just the USER_ID argument.");
        outPrintWriter.println("  set-temporary-services USER_ID [FIRST_PACKAGE_NAME] [SECOND_PACKAGE_NAME] [COMPONENT_NAME DURATION]");
        outPrintWriter.println("    Temporarily (for DURATION ms) changes the service implementation.");
        outPrintWriter.println("    To reset, call with just the USER_ID argument.");
    }

    private int getBoundPackageName() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        android.content.ComponentName componentName = this.mService.getComponentName(java.lang.Integer.parseInt(getNextArgRequired()), com.android.server.ambientcontext.AmbientContextManagerPerUserService.ServiceType.DEFAULT);
        outPrintWriter.println(componentName == null ? "" : componentName.getPackageName());
        return 0;
    }

    private int setTemporaryService() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            this.mService.resetTemporaryService(parseInt);
            outPrintWriter.println("AmbientContextDetectionService temporary reset. ");
            this.mService.setDefaultServiceEnabled(parseInt, true);
            return 0;
        }
        int parseInt2 = java.lang.Integer.parseInt(getNextArgRequired());
        this.mService.setTemporaryService(parseInt, nextArg, parseInt2);
        outPrintWriter.println("AmbientContextDetectionService temporarily set to " + nextArg + " for " + parseInt2 + "ms");
        return 0;
    }

    private int setTemporaryServices() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        int parseInt = java.lang.Integer.parseInt(getNextArgRequired());
        this.mService.setDefaultServiceEnabled(parseInt, false);
        java.lang.String nextArg = getNextArg();
        java.lang.String nextArg2 = getNextArg();
        if (nextArg == null || nextArg2 == null) {
            this.mService.resetTemporaryService(parseInt);
            this.mService.setDefaultServiceEnabled(parseInt, true);
            outPrintWriter.println("AmbientContextDetectionService temporary reset.");
            return 0;
        }
        java.lang.String[] strArr = {nextArg, nextArg2};
        int parseInt2 = java.lang.Integer.parseInt(getNextArgRequired());
        this.mService.setTemporaryServices(parseInt, strArr, parseInt2);
        android.util.Slog.w(TAG, "AmbientContextDetectionService temporarily set to " + strArr[0] + " and " + strArr[1] + " for " + parseInt2 + "ms");
        outPrintWriter.println("AmbientContextDetectionService temporarily set to " + strArr[0] + " and " + strArr[1] + " for " + parseInt2 + "ms");
        return 0;
    }
}
