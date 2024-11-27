package com.android.server.contentcapture;

/* loaded from: classes.dex */
public final class ContentCaptureManagerServiceShellCommand extends android.os.ShellCommand {
    private final com.android.server.contentcapture.ContentCaptureManagerService mService;

    public ContentCaptureManagerServiceShellCommand(@android.annotation.NonNull com.android.server.contentcapture.ContentCaptureManagerService contentCaptureManagerService) {
        this.mService = contentCaptureManagerService;
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
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 113762:
                if (str.equals("set")) {
                    c = 3;
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
            outPrintWriter.println("ContentCapture Service (content_capture) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  get bind-instant-service-allowed");
            outPrintWriter.println("    Gets whether binding to services provided by instant apps is allowed");
            outPrintWriter.println("");
            outPrintWriter.println("  set bind-instant-service-allowed [true | false]");
            outPrintWriter.println("    Sets whether binding to services provided by instant apps is allowed");
            outPrintWriter.println("");
            outPrintWriter.println("  set temporary-service USER_ID [COMPONENT_NAME DURATION]");
            outPrintWriter.println("    Temporarily (for DURATION ms) changes the service implemtation.");
            outPrintWriter.println("    To reset, call with just the USER_ID argument.");
            outPrintWriter.println("");
            outPrintWriter.println("  set default-service-enabled USER_ID [true|false]");
            outPrintWriter.println("    Enable / disable the default service for the user.");
            outPrintWriter.println("");
            outPrintWriter.println("  get default-service-enabled USER_ID");
            outPrintWriter.println("    Checks whether the default service is enabled for the user.");
            outPrintWriter.println("");
            outPrintWriter.println("  list sessions [--user USER_ID]");
            outPrintWriter.println("    Lists all pending sessions.");
            outPrintWriter.println("");
            outPrintWriter.println("  destroy sessions [--user USER_ID]");
            outPrintWriter.println("    Destroys all pending sessions.");
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int requestGet(java.io.PrintWriter printWriter) {
        boolean z;
        java.lang.String nextArgRequired = getNextArgRequired();
        switch (nextArgRequired.hashCode()) {
            case 529654941:
                if (nextArgRequired.equals("default-service-enabled")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case 809633044:
                if (nextArgRequired.equals("bind-instant-service-allowed")) {
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
                return getBindInstantService(printWriter);
            case true:
                return getDefaultServiceEnabled(printWriter);
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
            case 529654941:
                if (nextArgRequired.equals("default-service-enabled")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 809633044:
                if (nextArgRequired.equals("bind-instant-service-allowed")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2003978041:
                if (nextArgRequired.equals("temporary-service")) {
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
                return setBindInstantService(printWriter);
            case 1:
                return setTemporaryService(printWriter);
            case 2:
                return setDefaultServiceEnabled(printWriter);
            default:
                printWriter.println("Invalid set: " + nextArgRequired);
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

    private int setTemporaryService(java.io.PrintWriter printWriter) {
        int nextIntArgRequired = getNextIntArgRequired();
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            this.mService.resetTemporaryService(nextIntArgRequired);
            return 0;
        }
        int nextIntArgRequired2 = getNextIntArgRequired();
        this.mService.setTemporaryService(nextIntArgRequired, nextArg, nextIntArgRequired2);
        printWriter.println("ContentCaptureService temporarily set to " + nextArg + " for " + nextIntArgRequired2 + "ms");
        return 0;
    }

    private int setDefaultServiceEnabled(java.io.PrintWriter printWriter) {
        int nextIntArgRequired = getNextIntArgRequired();
        boolean parseBoolean = java.lang.Boolean.parseBoolean(getNextArgRequired());
        if (!this.mService.setDefaultServiceEnabled(nextIntArgRequired, parseBoolean)) {
            printWriter.println("already " + parseBoolean);
            return 0;
        }
        return 0;
    }

    private int getDefaultServiceEnabled(java.io.PrintWriter printWriter) {
        printWriter.println(this.mService.isDefaultServiceEnabled(getNextIntArgRequired()));
        return 0;
    }

    private int requestDestroy(java.io.PrintWriter printWriter) {
        if (!isNextArgSessions(printWriter)) {
            return -1;
        }
        final int userIdFromArgsOrAllUsers = getUserIdFromArgsOrAllUsers();
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        final com.android.internal.os.IResultReceiver.Stub stub = new com.android.internal.os.IResultReceiver.Stub() { // from class: com.android.server.contentcapture.ContentCaptureManagerServiceShellCommand.1
            public void send(int i, android.os.Bundle bundle) {
                countDownLatch.countDown();
            }
        };
        return requestSessionCommon(printWriter, countDownLatch, new java.lang.Runnable() { // from class: com.android.server.contentcapture.ContentCaptureManagerServiceShellCommand$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.contentcapture.ContentCaptureManagerServiceShellCommand.this.lambda$requestDestroy$0(userIdFromArgsOrAllUsers, stub);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestDestroy$0(int i, com.android.internal.os.IResultReceiver iResultReceiver) {
        this.mService.destroySessions(i, iResultReceiver);
    }

    private int requestList(final java.io.PrintWriter printWriter) {
        if (!isNextArgSessions(printWriter)) {
            return -1;
        }
        final int userIdFromArgsOrAllUsers = getUserIdFromArgsOrAllUsers();
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        final com.android.internal.os.IResultReceiver.Stub stub = new com.android.internal.os.IResultReceiver.Stub() { // from class: com.android.server.contentcapture.ContentCaptureManagerServiceShellCommand.2
            public void send(int i, android.os.Bundle bundle) {
                java.util.Iterator<java.lang.String> it = bundle.getStringArrayList("sessions").iterator();
                while (it.hasNext()) {
                    printWriter.println(it.next());
                }
                countDownLatch.countDown();
            }
        };
        return requestSessionCommon(printWriter, countDownLatch, new java.lang.Runnable() { // from class: com.android.server.contentcapture.ContentCaptureManagerServiceShellCommand$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.contentcapture.ContentCaptureManagerServiceShellCommand.this.lambda$requestList$1(userIdFromArgsOrAllUsers, stub);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestList$1(int i, com.android.internal.os.IResultReceiver iResultReceiver) {
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
