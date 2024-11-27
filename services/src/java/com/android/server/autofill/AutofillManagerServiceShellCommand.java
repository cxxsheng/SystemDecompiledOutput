package com.android.server.autofill;

/* loaded from: classes.dex */
public final class AutofillManagerServiceShellCommand extends android.os.ShellCommand {
    private final com.android.server.autofill.AutofillManagerService mService;

    public AutofillManagerServiceShellCommand(com.android.server.autofill.AutofillManagerService autofillManagerService) {
        this.mService = autofillManagerService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        switch (str.hashCode()) {
            case 102230:
                if (str.equals("get")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 113762:
                if (str.equals("set")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 3322014:
                if (str.equals("list")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 97513095:
                if (str.equals("flags")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 108404047:
                if (str.equals("reset")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1557372922:
                if (str.equals("destroy")) {
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

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("AutoFill Service (autofill) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  get log_level ");
            outPrintWriter.println("    Gets the Autofill log level (off | debug | verbose).");
            outPrintWriter.println("");
            outPrintWriter.println("  get max_partitions");
            outPrintWriter.println("    Gets the maximum number of partitions per session.");
            outPrintWriter.println("");
            outPrintWriter.println("  get max_visible_datasets");
            outPrintWriter.println("    Gets the maximum number of visible datasets in the UI.");
            outPrintWriter.println("");
            outPrintWriter.println("  get full_screen_mode");
            outPrintWriter.println("    Gets the Fill UI full screen mode");
            outPrintWriter.println("");
            outPrintWriter.println("  get fc_score [--algorithm ALGORITHM] value1 value2");
            outPrintWriter.println("    Gets the field classification score for 2 fields.");
            outPrintWriter.println("");
            outPrintWriter.println("  get bind-instant-service-allowed");
            outPrintWriter.println("    Gets whether binding to services provided by instant apps is allowed");
            outPrintWriter.println("");
            outPrintWriter.println("  get saved-password-count");
            outPrintWriter.println("    Gets the number of saved passwords in the current service.");
            outPrintWriter.println("");
            outPrintWriter.println("  set log_level [off | debug | verbose]");
            outPrintWriter.println("    Sets the Autofill log level.");
            outPrintWriter.println("");
            outPrintWriter.println("  set max_partitions number");
            outPrintWriter.println("    Sets the maximum number of partitions per session.");
            outPrintWriter.println("");
            outPrintWriter.println("  set max_visible_datasets number");
            outPrintWriter.println("    Sets the maximum number of visible datasets in the UI.");
            outPrintWriter.println("");
            outPrintWriter.println("  set full_screen_mode [true | false | default]");
            outPrintWriter.println("    Sets the Fill UI full screen mode");
            outPrintWriter.println("");
            outPrintWriter.println("  set bind-instant-service-allowed [true | false]");
            outPrintWriter.println("    Sets whether binding to services provided by instant apps is allowed");
            outPrintWriter.println("");
            outPrintWriter.println("  set temporary-augmented-service USER_ID [COMPONENT_NAME DURATION]");
            outPrintWriter.println("    Temporarily (for DURATION ms) changes the augmented autofill service implementation.");
            outPrintWriter.println("    To reset, call with just the USER_ID argument.");
            outPrintWriter.println("");
            outPrintWriter.println("  set default-augmented-service-enabled USER_ID [true|false]");
            outPrintWriter.println("    Enable / disable the default augmented autofill service for the user.");
            outPrintWriter.println("");
            outPrintWriter.println("  set temporary-detection-service USER_ID [COMPONENT_NAME DURATION]");
            outPrintWriter.println("    Temporarily (for DURATION ms) changes the autofill detection service implementation.");
            outPrintWriter.println("    To reset, call with [COMPONENT_NAME 0].");
            outPrintWriter.println("");
            outPrintWriter.println("  get default-augmented-service-enabled USER_ID");
            outPrintWriter.println("    Checks whether the default augmented autofill service is enabled for the user.");
            outPrintWriter.println("");
            outPrintWriter.println("  list sessions [--user USER_ID]");
            outPrintWriter.println("    Lists all pending sessions.");
            outPrintWriter.println("");
            outPrintWriter.println("  destroy sessions [--user USER_ID]");
            outPrintWriter.println("    Destroys all pending sessions.");
            outPrintWriter.println("");
            outPrintWriter.println("  reset");
            outPrintWriter.println("    Resets all pending sessions and cached service connections.");
            outPrintWriter.println("");
            outPrintWriter.println("  flags");
            outPrintWriter.println("    Prints out all autofill related flags.");
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

    private int requestFlags(java.io.PrintWriter printWriter) {
        if (android.service.autofill.Flags.test()) {
            printWriter.println("Hello Flag World!");
        }
        java.lang.reflect.Method[] methodArr = new java.lang.reflect.Method[0];
        try {
            for (java.lang.reflect.Method method : android.service.autofill.Flags.class.getDeclaredMethods()) {
                if (java.lang.reflect.Modifier.isPublic(method.getModifiers())) {
                    try {
                        try {
                            printWriter.print(method.getName() + ": ");
                            printWriter.print(method.invoke(null, new java.lang.Object[0]));
                        } catch (java.lang.Exception e) {
                            e.printStackTrace(printWriter);
                        }
                    } finally {
                        printWriter.println("");
                    }
                }
            }
            return 0;
        } catch (java.lang.SecurityException e2) {
            e2.printStackTrace(printWriter);
            return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int requestGet(java.io.PrintWriter printWriter) {
        char c;
        java.lang.String nextArgRequired = getNextArgRequired();
        switch (nextArgRequired.hashCode()) {
            case -2124387184:
                if (nextArgRequired.equals("fc_score")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -2006901047:
                if (nextArgRequired.equals("log_level")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1298810906:
                if (nextArgRequired.equals("full_screen_mode")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -633247282:
                if (nextArgRequired.equals("field-detection-service-enabled")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -255918237:
                if (nextArgRequired.equals("saved-password-count")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 809633044:
                if (nextArgRequired.equals("bind-instant-service-allowed")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 852405952:
                if (nextArgRequired.equals("default-augmented-service-enabled")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1393110435:
                if (nextArgRequired.equals("max_visible_datasets")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1772188804:
                if (nextArgRequired.equals("max_partitions")) {
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
            case 0:
                return getLogLevel(printWriter);
            case 1:
                return getMaxPartitions(printWriter);
            case 2:
                return getMaxVisibileDatasets(printWriter);
            case 3:
                return getFieldClassificationScore(printWriter);
            case 4:
                return getFullScreenMode(printWriter);
            case 5:
                return getBindInstantService(printWriter);
            case 6:
                return getDefaultAugmentedServiceEnabled(printWriter);
            case 7:
                return isFieldDetectionServiceEnabled(printWriter);
            case '\b':
                return getSavedPasswordCount(printWriter);
            default:
                printWriter.println("Invalid set: " + nextArgRequired);
                return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int requestSet(java.io.PrintWriter printWriter) {
        char c;
        java.lang.String nextArgRequired = getNextArgRequired();
        switch (nextArgRequired.hashCode()) {
            case -2006901047:
                if (nextArgRequired.equals("log_level")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1298810906:
                if (nextArgRequired.equals("full_screen_mode")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -571600804:
                if (nextArgRequired.equals("temporary-augmented-service")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 809633044:
                if (nextArgRequired.equals("bind-instant-service-allowed")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 852405952:
                if (nextArgRequired.equals("default-augmented-service-enabled")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1393110435:
                if (nextArgRequired.equals("max_visible_datasets")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1772188804:
                if (nextArgRequired.equals("max_partitions")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 2027866865:
                if (nextArgRequired.equals("temporary-detection-service")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return setLogLevel(printWriter);
            case 1:
                return setMaxPartitions();
            case 2:
                return setMaxVisibileDatasets();
            case 3:
                return setFullScreenMode(printWriter);
            case 4:
                return setBindInstantService(printWriter);
            case 5:
                return setTemporaryAugmentedService(printWriter);
            case 6:
                return setDefaultAugmentedServiceEnabled(printWriter);
            case 7:
                return setTemporaryDetectionService(printWriter);
            default:
                printWriter.println("Invalid set: " + nextArgRequired);
                return -1;
        }
    }

    private int getLogLevel(java.io.PrintWriter printWriter) {
        int logLevel = this.mService.getLogLevel();
        switch (logLevel) {
            case 0:
                printWriter.println("off");
                break;
            case 1:
            case 3:
            default:
                printWriter.println("unknow (" + logLevel + ")");
                break;
            case 2:
                printWriter.println("debug");
                break;
            case 4:
                printWriter.println("verbose");
                break;
        }
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int setLogLevel(java.io.PrintWriter printWriter) {
        boolean z;
        java.lang.String nextArgRequired = getNextArgRequired();
        java.lang.String lowerCase = nextArgRequired.toLowerCase();
        switch (lowerCase.hashCode()) {
            case 109935:
                if (lowerCase.equals("off")) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            case 95458899:
                if (lowerCase.equals("debug")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 351107458:
                if (lowerCase.equals("verbose")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                this.mService.setLogLevel(4);
                return 0;
            case true:
                this.mService.setLogLevel(2);
                return 0;
            case true:
                this.mService.setLogLevel(0);
                return 0;
            default:
                printWriter.println("Invalid level: " + nextArgRequired);
                return -1;
        }
    }

    private int getMaxPartitions(java.io.PrintWriter printWriter) {
        printWriter.println(this.mService.getMaxPartitions());
        return 0;
    }

    private int setMaxPartitions() {
        this.mService.setMaxPartitions(java.lang.Integer.parseInt(getNextArgRequired()));
        return 0;
    }

    private int getMaxVisibileDatasets(java.io.PrintWriter printWriter) {
        printWriter.println(this.mService.getMaxVisibleDatasets());
        return 0;
    }

    private int setMaxVisibileDatasets() {
        this.mService.setMaxVisibleDatasets(java.lang.Integer.parseInt(getNextArgRequired()));
        return 0;
    }

    private int getFieldClassificationScore(final java.io.PrintWriter printWriter) {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String nextArgRequired = getNextArgRequired();
        if ("--algorithm".equals(nextArgRequired)) {
            str2 = getNextArgRequired();
            str = getNextArgRequired();
        } else {
            str = nextArgRequired;
            str2 = null;
        }
        java.lang.String nextArgRequired2 = getNextArgRequired();
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        this.mService.calculateScore(str2, str, nextArgRequired2, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.autofill.AutofillManagerServiceShellCommand$$ExternalSyntheticLambda0
            public final void onResult(android.os.Bundle bundle) {
                com.android.server.autofill.AutofillManagerServiceShellCommand.lambda$getFieldClassificationScore$0(printWriter, countDownLatch, bundle);
            }
        }));
        return waitForLatch(printWriter, countDownLatch);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getFieldClassificationScore$0(java.io.PrintWriter printWriter, java.util.concurrent.CountDownLatch countDownLatch, android.os.Bundle bundle) {
        android.service.autofill.AutofillFieldClassificationService.Scores scores = (android.service.autofill.AutofillFieldClassificationService.Scores) bundle.getParcelable("scores", android.service.autofill.AutofillFieldClassificationService.Scores.class);
        if (scores == null) {
            printWriter.println("no score");
        } else {
            printWriter.println(scores.scores[0][0]);
        }
        countDownLatch.countDown();
    }

    private int getFullScreenMode(java.io.PrintWriter printWriter) {
        java.lang.Boolean fullScreenMode = this.mService.getFullScreenMode();
        if (fullScreenMode == null) {
            printWriter.println("default");
            return 0;
        }
        if (fullScreenMode.booleanValue()) {
            printWriter.println("true");
            return 0;
        }
        printWriter.println("false");
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int setFullScreenMode(java.io.PrintWriter printWriter) {
        char c;
        java.lang.String nextArgRequired = getNextArgRequired();
        java.lang.String lowerCase = nextArgRequired.toLowerCase();
        switch (lowerCase.hashCode()) {
            case 3569038:
                if (lowerCase.equals("true")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 97196323:
                if (lowerCase.equals("false")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1544803905:
                if (lowerCase.equals("default")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mService.setFullScreenMode(java.lang.Boolean.TRUE);
                return 0;
            case 1:
                this.mService.setFullScreenMode(java.lang.Boolean.FALSE);
                return 0;
            case 2:
                this.mService.setFullScreenMode(null);
                return 0;
            default:
                printWriter.println("Invalid mode: " + nextArgRequired);
                return -1;
        }
    }

    private int getBindInstantService(java.io.PrintWriter printWriter) {
        if (this.mService.getAllowInstantService()) {
            printWriter.println("true");
            return 0;
        }
        printWriter.println("false");
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int setBindInstantService(java.io.PrintWriter printWriter) {
        boolean z;
        java.lang.String nextArgRequired = getNextArgRequired();
        java.lang.String lowerCase = nextArgRequired.toLowerCase();
        switch (lowerCase.hashCode()) {
            case 3569038:
                if (lowerCase.equals("true")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 97196323:
                if (lowerCase.equals("false")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                this.mService.setAllowInstantService(true);
                return 0;
            case true:
                this.mService.setAllowInstantService(false);
                return 0;
            default:
                printWriter.println("Invalid mode: " + nextArgRequired);
                return -1;
        }
    }

    private int setTemporaryDetectionService(java.io.PrintWriter printWriter) {
        int nextIntArgRequired = getNextIntArgRequired();
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            this.mService.resetTemporaryDetectionService(nextIntArgRequired);
            return 0;
        }
        int nextIntArgRequired2 = getNextIntArgRequired();
        if (nextIntArgRequired2 <= 0) {
            this.mService.resetTemporaryDetectionService(nextIntArgRequired);
            return 0;
        }
        this.mService.setTemporaryDetectionService(nextIntArgRequired, nextArg, nextIntArgRequired2);
        printWriter.println("Autofill Detection Service temporarily set to " + nextArg + " for " + nextIntArgRequired2 + "ms");
        return 0;
    }

    private int isFieldDetectionServiceEnabled(java.io.PrintWriter printWriter) {
        printWriter.println(this.mService.isFieldDetectionServiceEnabledForUser(getNextIntArgRequired()));
        return 0;
    }

    private int setTemporaryAugmentedService(java.io.PrintWriter printWriter) {
        int nextIntArgRequired = getNextIntArgRequired();
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            this.mService.resetTemporaryAugmentedAutofillService(nextIntArgRequired);
            return 0;
        }
        int nextIntArgRequired2 = getNextIntArgRequired();
        this.mService.setTemporaryAugmentedAutofillService(nextIntArgRequired, nextArg, nextIntArgRequired2);
        printWriter.println("AugmentedAutofillService temporarily set to " + nextArg + " for " + nextIntArgRequired2 + "ms");
        return 0;
    }

    private int getDefaultAugmentedServiceEnabled(java.io.PrintWriter printWriter) {
        printWriter.println(this.mService.isDefaultAugmentedServiceEnabled(getNextIntArgRequired()));
        return 0;
    }

    private int setDefaultAugmentedServiceEnabled(java.io.PrintWriter printWriter) {
        int nextIntArgRequired = getNextIntArgRequired();
        boolean parseBoolean = java.lang.Boolean.parseBoolean(getNextArgRequired());
        if (!this.mService.setDefaultAugmentedServiceEnabled(nextIntArgRequired, parseBoolean)) {
            printWriter.println("already " + parseBoolean);
            return 0;
        }
        return 0;
    }

    private int getSavedPasswordCount(final java.io.PrintWriter printWriter) {
        int nextIntArgRequired = getNextIntArgRequired();
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        if (this.mService.requestSavedPasswordCount(nextIntArgRequired, new com.android.internal.os.IResultReceiver.Stub() { // from class: com.android.server.autofill.AutofillManagerServiceShellCommand.1
            public void send(int i, android.os.Bundle bundle) {
                printWriter.println("resultCode=" + i);
                if (i == 0 && bundle != null) {
                    printWriter.println("value=" + bundle.getInt("result"));
                }
                countDownLatch.countDown();
            }
        })) {
            waitForLatch(printWriter, countDownLatch);
            return 0;
        }
        return 0;
    }

    private int requestDestroy(java.io.PrintWriter printWriter) {
        if (!isNextArgSessions(printWriter)) {
            return -1;
        }
        final int userIdFromArgsOrAllUsers = getUserIdFromArgsOrAllUsers();
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        final com.android.internal.os.IResultReceiver.Stub stub = new com.android.internal.os.IResultReceiver.Stub() { // from class: com.android.server.autofill.AutofillManagerServiceShellCommand.2
            public void send(int i, android.os.Bundle bundle) {
                countDownLatch.countDown();
            }
        };
        return requestSessionCommon(printWriter, countDownLatch, new java.lang.Runnable() { // from class: com.android.server.autofill.AutofillManagerServiceShellCommand$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.AutofillManagerServiceShellCommand.this.lambda$requestDestroy$1(userIdFromArgsOrAllUsers, stub);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestDestroy$1(int i, com.android.internal.os.IResultReceiver iResultReceiver) {
        this.mService.removeAllSessions(i, iResultReceiver);
    }

    private int requestList(final java.io.PrintWriter printWriter) {
        if (!isNextArgSessions(printWriter)) {
            return -1;
        }
        final int userIdFromArgsOrAllUsers = getUserIdFromArgsOrAllUsers();
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        final com.android.internal.os.IResultReceiver.Stub stub = new com.android.internal.os.IResultReceiver.Stub() { // from class: com.android.server.autofill.AutofillManagerServiceShellCommand.3
            public void send(int i, android.os.Bundle bundle) {
                java.util.Iterator<java.lang.String> it = bundle.getStringArrayList("sessions").iterator();
                while (it.hasNext()) {
                    printWriter.println(it.next());
                }
                countDownLatch.countDown();
            }
        };
        return requestSessionCommon(printWriter, countDownLatch, new java.lang.Runnable() { // from class: com.android.server.autofill.AutofillManagerServiceShellCommand$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.AutofillManagerServiceShellCommand.this.lambda$requestList$2(userIdFromArgsOrAllUsers, stub);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestList$2(int i, com.android.internal.os.IResultReceiver iResultReceiver) {
        this.mService.listSessions(i, iResultReceiver);
    }

    private boolean isNextArgSessions(java.io.PrintWriter printWriter) {
        if (!getNextArgRequired().equals("sessions")) {
            printWriter.println("Error: invalid list type");
            return false;
        }
        return true;
    }

    private int requestSessionCommon(java.io.PrintWriter printWriter, java.util.concurrent.CountDownLatch countDownLatch, java.lang.Runnable runnable) {
        runnable.run();
        return waitForLatch(printWriter, countDownLatch);
    }

    private int waitForLatch(java.io.PrintWriter printWriter, java.util.concurrent.CountDownLatch countDownLatch) {
        try {
            if (!countDownLatch.await(5L, java.util.concurrent.TimeUnit.SECONDS)) {
                printWriter.println("Timed out after 5 seconds");
                return -1;
            }
            return 0;
        } catch (java.lang.InterruptedException e) {
            printWriter.println("System call interrupted");
            java.lang.Thread.currentThread().interrupt();
            return -1;
        }
    }

    private int requestReset() {
        this.mService.reset();
        return 0;
    }

    private int getUserIdFromArgsOrAllUsers() {
        if ("--user".equals(getNextArg())) {
            return android.os.UserHandle.parseUserArg(getNextArgRequired());
        }
        return -1;
    }

    private int getNextIntArgRequired() {
        return java.lang.Integer.parseInt(getNextArgRequired());
    }
}
