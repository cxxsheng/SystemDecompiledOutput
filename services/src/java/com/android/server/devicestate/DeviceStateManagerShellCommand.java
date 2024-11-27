package com.android.server.devicestate;

/* loaded from: classes.dex */
public class DeviceStateManagerShellCommand extends android.os.ShellCommand {

    @android.annotation.Nullable
    private static android.hardware.devicestate.DeviceStateRequest sLastBaseStateRequest;

    @android.annotation.Nullable
    private static android.hardware.devicestate.DeviceStateRequest sLastRequest;
    private final android.hardware.devicestate.DeviceStateManager mClient;
    private final com.android.server.devicestate.DeviceStateManagerService mService;

    public DeviceStateManagerShellCommand(com.android.server.devicestate.DeviceStateManagerService deviceStateManagerService) {
        this.mService = deviceStateManagerService;
        this.mClient = (android.hardware.devicestate.DeviceStateManager) deviceStateManagerService.getContext().getSystemService(android.hardware.devicestate.DeviceStateManager.class);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        switch (str.hashCode()) {
            case -1906524523:
                if (str.equals("base-state")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1422060175:
                if (str.equals("print-state")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1134192350:
                if (str.equals("print-states")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -295380803:
                if (str.equals("print-states-simple")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 109757585:
                if (str.equals("state")) {
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

    private void printAllStates(java.io.PrintWriter printWriter) {
        java.util.Optional<android.hardware.devicestate.DeviceState> committedState = this.mService.getCommittedState();
        java.util.Optional<android.hardware.devicestate.DeviceState> baseState = this.mService.getBaseState();
        java.util.Optional<android.hardware.devicestate.DeviceState> overrideState = this.mService.getOverrideState();
        printWriter.println("Committed state: " + toString(committedState));
        if (overrideState.isPresent()) {
            printWriter.println("----------------------");
            printWriter.println("Base state: " + toString(baseState));
            printWriter.println("Override state: " + overrideState.get());
        }
    }

    private int runState(java.io.PrintWriter printWriter) {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            printAllStates(printWriter);
            return 0;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                if (!"reset".equals(nextArg)) {
                    android.hardware.devicestate.DeviceStateRequest build = android.hardware.devicestate.DeviceStateRequest.newBuilder(java.lang.Integer.parseInt(nextArg)).build();
                    this.mClient.requestState(build, (java.util.concurrent.Executor) null, (android.hardware.devicestate.DeviceStateRequest.Callback) null);
                    sLastRequest = build;
                } else if (sLastRequest != null) {
                    this.mClient.cancelStateRequest();
                    sLastRequest = null;
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            } catch (java.lang.NumberFormatException e) {
                getErrPrintWriter().println("Error: requested state should be an integer");
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            } catch (java.lang.IllegalArgumentException e2) {
                getErrPrintWriter().println("Error: " + e2.getMessage());
                getErrPrintWriter().println("-------------------");
                getErrPrintWriter().println("Run:");
                getErrPrintWriter().println("");
                getErrPrintWriter().println("    print-states");
                getErrPrintWriter().println("");
                getErrPrintWriter().println("to get the list of currently supported device states");
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private int runBaseState(java.io.PrintWriter printWriter) {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            printAllStates(printWriter);
            return 0;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                if (!"reset".equals(nextArg)) {
                    android.hardware.devicestate.DeviceStateRequest build = android.hardware.devicestate.DeviceStateRequest.newBuilder(java.lang.Integer.parseInt(nextArg)).build();
                    this.mClient.requestBaseStateOverride(build, (java.util.concurrent.Executor) null, (android.hardware.devicestate.DeviceStateRequest.Callback) null);
                    sLastBaseStateRequest = build;
                } else if (sLastBaseStateRequest != null) {
                    this.mClient.cancelBaseStateOverride();
                    sLastBaseStateRequest = null;
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            } catch (java.lang.NumberFormatException e) {
                getErrPrintWriter().println("Error: requested state should be an integer");
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            } catch (java.lang.IllegalArgumentException e2) {
                getErrPrintWriter().println("Error: " + e2.getMessage());
                getErrPrintWriter().println("-------------------");
                getErrPrintWriter().println("Run:");
                getErrPrintWriter().println("");
                getErrPrintWriter().println("    print-states");
                getErrPrintWriter().println("");
                getErrPrintWriter().println("to get the list of currently supported device states");
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return -1;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private int runPrintState(java.io.PrintWriter printWriter) {
        java.util.Optional<android.hardware.devicestate.DeviceState> committedState = this.mService.getCommittedState();
        if (committedState.isPresent()) {
            printWriter.println(committedState.get().getIdentifier());
            return 0;
        }
        getErrPrintWriter().println("Error: device state not available.");
        return 1;
    }

    private int runPrintStates(java.io.PrintWriter printWriter) {
        android.hardware.devicestate.DeviceState[] supportedStates = this.mService.getSupportedStates();
        printWriter.print("Supported states: [\n");
        for (android.hardware.devicestate.DeviceState deviceState : supportedStates) {
            printWriter.print("  " + deviceState + ",\n");
        }
        printWriter.println("]");
        return 0;
    }

    private int runPrintStatesSimple(java.io.PrintWriter printWriter) {
        printWriter.print((java.lang.String) java.util.Arrays.stream(this.mService.getSupportedStates()).map(new java.util.function.Function() { // from class: com.android.server.devicestate.DeviceStateManagerShellCommand$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return java.lang.Integer.valueOf(((android.hardware.devicestate.DeviceState) obj).getIdentifier());
            }
        }).map(new java.util.function.Function() { // from class: com.android.server.devicestate.DeviceStateManagerShellCommand$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((java.lang.Integer) obj).toString();
            }
        }).collect(java.util.stream.Collectors.joining(",")));
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Device state manager (device_state) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  state [reset|OVERRIDE_DEVICE_STATE]");
        outPrintWriter.println("    Return or override device state.");
        outPrintWriter.println("  print-state");
        outPrintWriter.println("    Return the current device state.");
        outPrintWriter.println("  print-states");
        outPrintWriter.println("    Return list of currently supported device states.");
        outPrintWriter.println("  print-states-simple");
        outPrintWriter.println("    Return the currently supported device states in comma separated format.");
    }

    private static java.lang.String toString(@android.annotation.NonNull java.util.Optional<android.hardware.devicestate.DeviceState> optional) {
        return optional.isPresent() ? optional.get().toString() : "(none)";
    }
}
