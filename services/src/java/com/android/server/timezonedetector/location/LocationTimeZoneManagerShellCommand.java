package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
class LocationTimeZoneManagerShellCommand extends android.os.ShellCommand {
    private final com.android.server.timezonedetector.location.LocationTimeZoneManagerService mService;

    LocationTimeZoneManagerShellCommand(com.android.server.timezonedetector.location.LocationTimeZoneManagerService locationTimeZoneManagerService) {
        this.mService = locationTimeZoneManagerService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -385184143:
                if (str.equals("start_with_test_providers")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3540994:
                if (str.equals("stop")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 109757538:
                if (str.equals("start")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 248094771:
                if (str.equals("clear_recorded_provider_states")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 943200902:
                if (str.equals("dump_state")) {
                    c = 4;
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
        outPrintWriter.printf("Location Time Zone Manager (%s) commands for tests:\n", "location_time_zone_manager");
        outPrintWriter.printf("  help\n", new java.lang.Object[0]);
        outPrintWriter.printf("    Print this help text.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "start");
        outPrintWriter.printf("    Starts the service, creating location time zone providers.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s <primary package name|%2$s> <secondary package name|%2$s> <record states>\n", "start_with_test_providers", "@null");
        outPrintWriter.printf("    Starts the service with test provider packages configured / provider permission checks disabled.\n", new java.lang.Object[0]);
        outPrintWriter.printf("    <record states> - true|false, determines whether state recording is enabled.\n", new java.lang.Object[0]);
        outPrintWriter.printf("    See %s and %s.\n", "dump_state", "clear_recorded_provider_states");
        outPrintWriter.printf("  %s\n", "stop");
        outPrintWriter.printf("    Stops the service, destroying location time zone providers.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "clear_recorded_provider_states");
        outPrintWriter.printf("    Clears recorded provider state. See also %s and %s.\n", "start_with_test_providers", "dump_state");
        outPrintWriter.printf("    Note: This is only intended for use during testing.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s [%s]\n", "dump_state", "--proto");
        outPrintWriter.printf("    Dumps service state for tests as text or binary proto form.\n", new java.lang.Object[0]);
        outPrintWriter.printf("    See the LocationTimeZoneManagerServiceStateProto definition for details.\n", new java.lang.Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf("This service is also affected by the following device_config flags in the %s namespace:\n", "system_time");
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_PRIMARY_LTZP_MODE_OVERRIDE);
        outPrintWriter.printf("    Overrides the mode of the primary provider. Values=%s|%s\n", com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED, com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED);
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_SECONDARY_LTZP_MODE_OVERRIDE);
        outPrintWriter.printf("    Overrides the mode of the secondary provider. Values=%s|%s\n", com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED, com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED);
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_UNCERTAINTY_DELAY_MILLIS);
        outPrintWriter.printf("    Sets the amount of time the service waits when uncertain before making an 'uncertain' suggestion to the time zone detector.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_LTZP_INITIALIZATION_TIMEOUT_MILLIS);
        outPrintWriter.printf("    Sets the initialization time passed to the providers.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_LTZP_INITIALIZATION_TIMEOUT_FUZZ_MILLIS);
        outPrintWriter.printf("    Sets the amount of extra time added to the providers' initialization time.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_LTZP_EVENT_FILTERING_AGE_THRESHOLD_MILLIS);
        outPrintWriter.printf("    Sets the amount of time that must pass between equivalent LTZP events before they will be reported to the system server.\n", new java.lang.Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf("Typically, use '%s' to stop the service before setting individual flags and '%s' after to restart it.\n", "stop", "start");
        outPrintWriter.println();
        outPrintWriter.printf("See \"adb shell cmd device_config\" for more information on setting flags.\n", new java.lang.Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf("Also see \"adb shell cmd %s help\" for higher-level location time zone commands / settings.\n", "time_zone_detector");
        outPrintWriter.println();
    }

    private int runStart() {
        try {
            this.mService.start();
            getOutPrintWriter().println("Service started");
            return 0;
        } catch (java.lang.RuntimeException e) {
            reportError(e);
            return 1;
        }
    }

    private int runStartWithTestProviders() {
        try {
            this.mService.startWithTestProviders(parseProviderPackageName(getNextArgRequired()), parseProviderPackageName(getNextArgRequired()), java.lang.Boolean.parseBoolean(getNextArgRequired()));
            getOutPrintWriter().println("Service started (test mode)");
            return 0;
        } catch (java.lang.RuntimeException e) {
            reportError(e);
            return 1;
        }
    }

    private int runStop() {
        try {
            this.mService.stop();
            getOutPrintWriter().println("Service stopped");
            return 0;
        } catch (java.lang.RuntimeException e) {
            reportError(e);
            return 1;
        }
    }

    private int runClearRecordedProviderStates() {
        try {
            this.mService.clearRecordedProviderStates();
            return 0;
        } catch (java.lang.IllegalStateException e) {
            reportError(e);
            return 2;
        }
    }

    private int runDumpControllerState() {
        com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream;
        try {
            com.android.server.timezonedetector.location.LocationTimeZoneManagerServiceState stateForTests = this.mService.getStateForTests();
            if (stateForTests == null) {
                return 0;
            }
            if (java.util.Objects.equals("--proto", getNextOption())) {
                dualDumpOutputStream = new com.android.internal.util.dump.DualDumpOutputStream(new android.util.proto.ProtoOutputStream(getOutFileDescriptor()));
            } else {
                dualDumpOutputStream = new com.android.internal.util.dump.DualDumpOutputStream(new android.util.IndentingPrintWriter(getOutPrintWriter(), "  "));
            }
            if (stateForTests.getLastEvent() != null) {
                com.android.server.timezonedetector.LocationAlgorithmEvent lastEvent = stateForTests.getLastEvent();
                long start = dualDumpOutputStream.start("last_event", 1146756268033L);
                android.app.time.LocationTimeZoneAlgorithmStatus algorithmStatus = lastEvent.getAlgorithmStatus();
                long start2 = dualDumpOutputStream.start("algorithm_status", 1146756268035L);
                dualDumpOutputStream.write("status", 1159641169921L, convertDetectionAlgorithmStatusToEnumToProtoEnum(algorithmStatus.getStatus()));
                dualDumpOutputStream.end(start2);
                if (lastEvent.getSuggestion() != null) {
                    long start3 = dualDumpOutputStream.start("suggestion", 1146756268033L);
                    java.util.Iterator<java.lang.String> it = lastEvent.getSuggestion().getZoneIds().iterator();
                    while (it.hasNext()) {
                        dualDumpOutputStream.write("zone_ids", 2237677961217L, it.next());
                    }
                    dualDumpOutputStream.end(start3);
                }
                java.util.Iterator<java.lang.String> it2 = lastEvent.getDebugInfo().iterator();
                while (it2.hasNext()) {
                    dualDumpOutputStream.write("debug_info", 2237677961218L, it2.next());
                }
                dualDumpOutputStream.end(start);
            }
            writeControllerStates(dualDumpOutputStream, stateForTests.getControllerStates());
            writeProviderStates(dualDumpOutputStream, stateForTests.getPrimaryProviderStates(), "primary_provider_states", 2246267895810L);
            writeProviderStates(dualDumpOutputStream, stateForTests.getSecondaryProviderStates(), "secondary_provider_states", 2246267895811L);
            dualDumpOutputStream.flush();
            return 0;
        } catch (java.lang.RuntimeException e) {
            reportError(e);
            return 1;
        }
    }

    private static void writeControllerStates(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.util.List<java.lang.String> list) {
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            dualDumpOutputStream.write("controller_states", 2259152797700L, convertControllerStateToProtoEnum(it.next()));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int convertControllerStateToProtoEnum(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1166336595:
                if (str.equals("STOPPED")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -468307734:
                if (str.equals("PROVIDERS_INITIALIZING")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 433141802:
                if (str.equals("UNKNOWN")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 478389753:
                if (str.equals("DESTROYED")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 872357833:
                if (str.equals("UNCERTAIN")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1386911874:
                if (str.equals("CERTAIN")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1917201485:
                if (str.equals("INITIALIZING")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 2066319421:
                if (str.equals("FAILED")) {
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
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            case 6:
                return 7;
            default:
                return 0;
        }
    }

    private static void writeProviderStates(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.util.List<com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState> list, java.lang.String str, long j) {
        for (com.android.server.timezonedetector.location.LocationTimeZoneProvider.ProviderState providerState : list) {
            long start = dualDumpOutputStream.start(str, j);
            dualDumpOutputStream.write("state", 1159641169921L, convertProviderStateEnumToProtoEnum(providerState.stateEnum));
            dualDumpOutputStream.end(start);
        }
    }

    private static int convertProviderStateEnumToProtoEnum(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                throw new java.lang.IllegalArgumentException("Unknown stateEnum=" + i);
        }
    }

    private static int convertDetectionAlgorithmStatusToEnumToProtoEnum(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                throw new java.lang.IllegalArgumentException("Unknown statusEnum=" + i);
        }
    }

    private void reportError(@android.annotation.NonNull java.lang.Throwable th) {
        java.io.PrintWriter errPrintWriter = getErrPrintWriter();
        errPrintWriter.println("Error: ");
        th.printStackTrace(errPrintWriter);
    }

    @android.annotation.Nullable
    private static java.lang.String parseProviderPackageName(@android.annotation.NonNull java.lang.String str) {
        if (str.equals("@null")) {
            return null;
        }
        return str;
    }
}
