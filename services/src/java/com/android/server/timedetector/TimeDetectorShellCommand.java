package com.android.server.timedetector;

/* loaded from: classes2.dex */
class TimeDetectorShellCommand extends android.os.ShellCommand {
    private final com.android.server.timedetector.TimeDetectorService mInterface;

    TimeDetectorShellCommand(com.android.server.timedetector.TimeDetectorService timeDetectorService) {
        this.mInterface = timeDetectorService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -1630622545:
                if (str.equals("suggest_telephony_time")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1316904020:
                if (str.equals("is_auto_detection_enabled")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -844157159:
                if (str.equals("suggest_network_time")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -532496502:
                if (str.equals("suggest_gnss_time")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -219775160:
                if (str.equals("set_time_state_for_tests")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -83861208:
                if (str.equals("get_time_state")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 200743238:
                if (str.equals("suggest_external_time")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 255251591:
                if (str.equals("get_network_time")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 754841328:
                if (str.equals("clear_network_time")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 909817707:
                if (str.equals("suggest_manual_time")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1433926509:
                if (str.equals("clear_system_clock_network_time")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 1860100418:
                if (str.equals("set_system_clock_network_time")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 1902269812:
                if (str.equals("set_auto_detection_enabled")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 2097306860:
                if (str.equals("confirm_time")) {
                    c = 11;
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

    private int runIsAutoDetectionEnabled() {
        getOutPrintWriter().println(this.mInterface.getCapabilitiesAndConfig().getConfiguration().isAutoDetectionEnabled());
        return 0;
    }

    private int runSetAutoDetectionEnabled() {
        return !this.mInterface.updateConfiguration(-2, new android.app.time.TimeConfiguration.Builder().setAutoDetectionEnabled(java.lang.Boolean.parseBoolean(getNextArgRequired())).build()) ? 1 : 0;
    }

    private int runSuggestManualTime() {
        java.util.function.Supplier supplier = new java.util.function.Supplier() { // from class: com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.app.timedetector.ManualTimeSuggestion lambda$runSuggestManualTime$0;
                lambda$runSuggestManualTime$0 = com.android.server.timedetector.TimeDetectorShellCommand.this.lambda$runSuggestManualTime$0();
                return lambda$runSuggestManualTime$0;
            }
        };
        final com.android.server.timedetector.TimeDetectorService timeDetectorService = this.mInterface;
        java.util.Objects.requireNonNull(timeDetectorService);
        return runSuggestTime(supplier, new java.util.function.Consumer() { // from class: com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.timedetector.TimeDetectorService.this.suggestManualTime((android.app.timedetector.ManualTimeSuggestion) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.app.timedetector.ManualTimeSuggestion lambda$runSuggestManualTime$0() {
        return android.app.timedetector.ManualTimeSuggestion.parseCommandLineArg(this);
    }

    private int runSuggestTelephonyTime() {
        java.util.function.Supplier supplier = new java.util.function.Supplier() { // from class: com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.app.timedetector.TelephonyTimeSuggestion lambda$runSuggestTelephonyTime$1;
                lambda$runSuggestTelephonyTime$1 = com.android.server.timedetector.TimeDetectorShellCommand.this.lambda$runSuggestTelephonyTime$1();
                return lambda$runSuggestTelephonyTime$1;
            }
        };
        final com.android.server.timedetector.TimeDetectorService timeDetectorService = this.mInterface;
        java.util.Objects.requireNonNull(timeDetectorService);
        return runSuggestTime(supplier, new java.util.function.Consumer() { // from class: com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.timedetector.TimeDetectorService.this.suggestTelephonyTime((android.app.timedetector.TelephonyTimeSuggestion) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.app.timedetector.TelephonyTimeSuggestion lambda$runSuggestTelephonyTime$1() {
        return android.app.timedetector.TelephonyTimeSuggestion.parseCommandLineArg(this);
    }

    private int runSuggestNetworkTime() {
        java.util.function.Supplier supplier = new java.util.function.Supplier() { // from class: com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                com.android.server.timedetector.NetworkTimeSuggestion lambda$runSuggestNetworkTime$2;
                lambda$runSuggestNetworkTime$2 = com.android.server.timedetector.TimeDetectorShellCommand.this.lambda$runSuggestNetworkTime$2();
                return lambda$runSuggestNetworkTime$2;
            }
        };
        final com.android.server.timedetector.TimeDetectorService timeDetectorService = this.mInterface;
        java.util.Objects.requireNonNull(timeDetectorService);
        return runSuggestTime(supplier, new java.util.function.Consumer() { // from class: com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.timedetector.TimeDetectorService.this.suggestNetworkTime((com.android.server.timedetector.NetworkTimeSuggestion) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.timedetector.NetworkTimeSuggestion lambda$runSuggestNetworkTime$2() {
        return com.android.server.timedetector.NetworkTimeSuggestion.parseCommandLineArg(this);
    }

    private int runGetLatestNetworkTime() {
        getOutPrintWriter().println(this.mInterface.getLatestNetworkSuggestion());
        return 0;
    }

    private int runClearLatestNetworkTime() {
        this.mInterface.clearLatestNetworkTime();
        return 0;
    }

    private int runSuggestGnssTime() {
        java.util.function.Supplier supplier = new java.util.function.Supplier() { // from class: com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                com.android.server.timedetector.GnssTimeSuggestion lambda$runSuggestGnssTime$3;
                lambda$runSuggestGnssTime$3 = com.android.server.timedetector.TimeDetectorShellCommand.this.lambda$runSuggestGnssTime$3();
                return lambda$runSuggestGnssTime$3;
            }
        };
        final com.android.server.timedetector.TimeDetectorService timeDetectorService = this.mInterface;
        java.util.Objects.requireNonNull(timeDetectorService);
        return runSuggestTime(supplier, new java.util.function.Consumer() { // from class: com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.timedetector.TimeDetectorService.this.suggestGnssTime((com.android.server.timedetector.GnssTimeSuggestion) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.timedetector.GnssTimeSuggestion lambda$runSuggestGnssTime$3() {
        return com.android.server.timedetector.GnssTimeSuggestion.parseCommandLineArg(this);
    }

    private int runSuggestExternalTime() {
        java.util.function.Supplier supplier = new java.util.function.Supplier() { // from class: com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.app.time.ExternalTimeSuggestion lambda$runSuggestExternalTime$4;
                lambda$runSuggestExternalTime$4 = com.android.server.timedetector.TimeDetectorShellCommand.this.lambda$runSuggestExternalTime$4();
                return lambda$runSuggestExternalTime$4;
            }
        };
        final com.android.server.timedetector.TimeDetectorService timeDetectorService = this.mInterface;
        java.util.Objects.requireNonNull(timeDetectorService);
        return runSuggestTime(supplier, new java.util.function.Consumer() { // from class: com.android.server.timedetector.TimeDetectorShellCommand$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.timedetector.TimeDetectorService.this.suggestExternalTime((android.app.time.ExternalTimeSuggestion) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.app.time.ExternalTimeSuggestion lambda$runSuggestExternalTime$4() {
        return android.app.time.ExternalTimeSuggestion.parseCommandLineArg(this);
    }

    private <T> int runSuggestTime(java.util.function.Supplier<T> supplier, java.util.function.Consumer<T> consumer) {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            T t = supplier.get();
            if (t == null) {
                outPrintWriter.println("Error: suggestion not specified");
                return 1;
            }
            consumer.accept(t);
            outPrintWriter.println("Suggestion " + t + " injected.");
            return 0;
        } catch (java.lang.RuntimeException e) {
            outPrintWriter.println(e);
            return 1;
        }
    }

    private int runGetTimeState() {
        getOutPrintWriter().println(this.mInterface.getTimeState());
        return 0;
    }

    private int runSetTimeState() {
        this.mInterface.setTimeState(android.app.time.TimeState.parseCommandLineArgs(this));
        return 0;
    }

    private int runConfirmTime() {
        getOutPrintWriter().println(this.mInterface.confirmTime(android.app.time.UnixEpochTime.parseCommandLineArgs(this)));
        return 0;
    }

    private int runClearSystemClockNetworkTime() {
        this.mInterface.clearNetworkTimeForSystemClockForTests();
        return 0;
    }

    private int runSetSystemClockNetworkTime() {
        com.android.server.timedetector.NetworkTimeSuggestion parseCommandLineArg = com.android.server.timedetector.NetworkTimeSuggestion.parseCommandLineArg(this);
        this.mInterface.setNetworkTimeForSystemClockForTests(parseCommandLineArg.getUnixEpochTime(), parseCommandLineArg.getUncertaintyMillis());
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.printf("Time Detector (%s) commands:\n", "time_detector");
        outPrintWriter.printf("  help\n", new java.lang.Object[0]);
        outPrintWriter.printf("    Print this help text.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "is_auto_detection_enabled");
        outPrintWriter.printf("    Prints true/false according to the automatic time detection setting.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s true|false\n", "set_auto_detection_enabled");
        outPrintWriter.printf("    Sets the automatic time detection setting.\n", new java.lang.Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf("  %s <manual suggestion opts>\n", "suggest_manual_time");
        outPrintWriter.printf("    Suggests a time as if via the \"manual\" origin.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s <telephony suggestion opts>\n", "suggest_telephony_time");
        outPrintWriter.printf("    Suggests a time as if via the \"telephony\" origin.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s <network suggestion opts>\n", "suggest_network_time");
        outPrintWriter.printf("    Suggests a time as if via the \"network\" origin.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s <gnss suggestion opts>\n", "suggest_gnss_time");
        outPrintWriter.printf("    Suggests a time as if via the \"gnss\" origin.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s <external suggestion opts>\n", "suggest_external_time");
        outPrintWriter.printf("    Suggests a time as if via the \"external\" origin.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "get_time_state");
        outPrintWriter.printf("    Returns the current time setting state.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s <time state options>\n", "set_time_state_for_tests");
        outPrintWriter.printf("    Sets the current time state for tests.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s <unix epoch time options>\n", "confirm_time");
        outPrintWriter.printf("    Tries to confirms the time, raising the confidence.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "get_network_time");
        outPrintWriter.printf("    Prints the network time information held by the detector.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "clear_network_time");
        outPrintWriter.printf("    Clears the network time information held by the detector.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s <network suggestion opts>\n", "set_system_clock_network_time");
        outPrintWriter.printf("    Sets the network time information used for SystemClock.currentNetworkTimeClock().\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "clear_system_clock_network_time");
        outPrintWriter.printf("    Clears the network time information used for SystemClock.currentNetworkTimeClock().\n", new java.lang.Object[0]);
        outPrintWriter.println();
        android.app.timedetector.ManualTimeSuggestion.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        android.app.timedetector.TelephonyTimeSuggestion.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        com.android.server.timedetector.NetworkTimeSuggestion.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        com.android.server.timedetector.GnssTimeSuggestion.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        android.app.time.ExternalTimeSuggestion.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        android.app.time.TimeState.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        android.app.time.UnixEpochTime.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        outPrintWriter.printf("This service is also affected by the following device_config flags in the %s namespace:\n", "system_time");
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_TIME_DETECTOR_LOWER_BOUND_MILLIS_OVERRIDE);
        outPrintWriter.printf("    The lower bound used to validate time suggestions when they are received.\n", new java.lang.Object[0]);
        outPrintWriter.printf("    Specified in milliseconds since the start of the Unix epoch.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_TIME_DETECTOR_ORIGIN_PRIORITIES_OVERRIDE);
        outPrintWriter.printf("    A comma separated list of origins. See TimeDetectorStrategy for details.\n", new java.lang.Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf("See \"adb shell cmd device_config\" for more information on setting flags.\n", new java.lang.Object[0]);
        outPrintWriter.println();
    }
}
