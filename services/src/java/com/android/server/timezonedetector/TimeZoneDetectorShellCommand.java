package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
class TimeZoneDetectorShellCommand extends android.os.ShellCommand {
    private final com.android.server.timezonedetector.TimeZoneDetectorService mInterface;

    TimeZoneDetectorShellCommand(com.android.server.timezonedetector.TimeZoneDetectorService timeZoneDetectorService) {
        this.mInterface = timeZoneDetectorService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        switch (str.hashCode()) {
            case -1908861832:
                if (str.equals("is_telephony_detection_supported")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1595273216:
                if (str.equals("suggest_manual_time_zone")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1589541881:
                if (str.equals("get_time_zone_state")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1366762753:
                if (str.equals("set_time_zone_state_for_tests")) {
                    c = 11;
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
            case -1264030344:
                if (str.equals("dump_metrics")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -646187524:
                if (str.equals("set_geo_detection_enabled")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -364727521:
                if (str.equals("confirm_time_zone")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 496894148:
                if (str.equals("is_geo_detection_enabled")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 596690236:
                if (str.equals("suggest_telephony_time_zone")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1133835109:
                if (str.equals("enable_telephony_fallback")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1385756017:
                if (str.equals("is_geo_detection_supported")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1830029431:
                if (str.equals("handle_location_algorithm_event")) {
                    c = 6;
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
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return handleDefaultCommands(str);
    }

    private int runIsAutoDetectionEnabled() {
        getOutPrintWriter().println(this.mInterface.getCapabilitiesAndConfig(-2).getConfiguration().isAutoDetectionEnabled());
        return 0;
    }

    private int runIsTelephonyDetectionSupported() {
        getOutPrintWriter().println(this.mInterface.isTelephonyTimeZoneDetectionSupported());
        return 0;
    }

    private int runIsGeoDetectionSupported() {
        getOutPrintWriter().println(this.mInterface.isGeoTimeZoneDetectionSupported());
        return 0;
    }

    private int runIsGeoDetectionEnabled() {
        getOutPrintWriter().println(this.mInterface.getCapabilitiesAndConfig(-2).getConfiguration().isGeoDetectionEnabled());
        return 0;
    }

    private int runSetAutoDetectionEnabled() {
        return !this.mInterface.updateConfiguration(-2, new android.app.time.TimeZoneConfiguration.Builder().setAutoDetectionEnabled(java.lang.Boolean.parseBoolean(getNextArgRequired())).build()) ? 1 : 0;
    }

    private int runSetGeoDetectionEnabled() {
        return !this.mInterface.updateConfiguration(-2, new android.app.time.TimeZoneConfiguration.Builder().setGeoDetectionEnabled(java.lang.Boolean.parseBoolean(getNextArgRequired())).build()) ? 1 : 0;
    }

    private int runHandleLocationEvent() {
        java.util.function.Supplier supplier = new java.util.function.Supplier() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                com.android.server.timezonedetector.LocationAlgorithmEvent lambda$runHandleLocationEvent$0;
                lambda$runHandleLocationEvent$0 = com.android.server.timezonedetector.TimeZoneDetectorShellCommand.this.lambda$runHandleLocationEvent$0();
                return lambda$runHandleLocationEvent$0;
            }
        };
        final com.android.server.timezonedetector.TimeZoneDetectorService timeZoneDetectorService = this.mInterface;
        java.util.Objects.requireNonNull(timeZoneDetectorService);
        return runSingleArgMethod(supplier, new java.util.function.Consumer() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.timezonedetector.TimeZoneDetectorService.this.handleLocationAlgorithmEvent((com.android.server.timezonedetector.LocationAlgorithmEvent) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.timezonedetector.LocationAlgorithmEvent lambda$runHandleLocationEvent$0() {
        return com.android.server.timezonedetector.LocationAlgorithmEvent.parseCommandLineArg(this);
    }

    private int runSuggestManualTimeZone() {
        java.util.function.Supplier supplier = new java.util.function.Supplier() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.app.timezonedetector.ManualTimeZoneSuggestion lambda$runSuggestManualTimeZone$1;
                lambda$runSuggestManualTimeZone$1 = com.android.server.timezonedetector.TimeZoneDetectorShellCommand.this.lambda$runSuggestManualTimeZone$1();
                return lambda$runSuggestManualTimeZone$1;
            }
        };
        final com.android.server.timezonedetector.TimeZoneDetectorService timeZoneDetectorService = this.mInterface;
        java.util.Objects.requireNonNull(timeZoneDetectorService);
        return runSingleArgMethod(supplier, new java.util.function.Consumer() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.timezonedetector.TimeZoneDetectorService.this.suggestManualTimeZone((android.app.timezonedetector.ManualTimeZoneSuggestion) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.app.timezonedetector.ManualTimeZoneSuggestion lambda$runSuggestManualTimeZone$1() {
        return android.app.timezonedetector.ManualTimeZoneSuggestion.parseCommandLineArg(this);
    }

    private int runSuggestTelephonyTimeZone() {
        java.util.function.Supplier supplier = new java.util.function.Supplier() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.app.timezonedetector.TelephonyTimeZoneSuggestion lambda$runSuggestTelephonyTimeZone$2;
                lambda$runSuggestTelephonyTimeZone$2 = com.android.server.timezonedetector.TimeZoneDetectorShellCommand.this.lambda$runSuggestTelephonyTimeZone$2();
                return lambda$runSuggestTelephonyTimeZone$2;
            }
        };
        final com.android.server.timezonedetector.TimeZoneDetectorService timeZoneDetectorService = this.mInterface;
        java.util.Objects.requireNonNull(timeZoneDetectorService);
        return runSingleArgMethod(supplier, new java.util.function.Consumer() { // from class: com.android.server.timezonedetector.TimeZoneDetectorShellCommand$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.timezonedetector.TimeZoneDetectorService.this.suggestTelephonyTimeZone((android.app.timezonedetector.TelephonyTimeZoneSuggestion) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.app.timezonedetector.TelephonyTimeZoneSuggestion lambda$runSuggestTelephonyTimeZone$2() {
        return android.app.timezonedetector.TelephonyTimeZoneSuggestion.parseCommandLineArg(this);
    }

    private <T> int runSingleArgMethod(java.util.function.Supplier<T> supplier, java.util.function.Consumer<T> consumer) {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            T t = supplier.get();
            if (t == null) {
                outPrintWriter.println("Error: arg not specified");
                return 1;
            }
            consumer.accept(t);
            outPrintWriter.println("Arg " + t + " injected.");
            return 0;
        } catch (java.lang.RuntimeException e) {
            outPrintWriter.println(e);
            return 1;
        }
    }

    private int runEnableTelephonyFallback() {
        this.mInterface.enableTelephonyFallback("Command line");
        return 0;
    }

    private int runGetTimeZoneState() {
        getOutPrintWriter().println(this.mInterface.getTimeZoneState());
        return 0;
    }

    private int runSetTimeZoneState() {
        this.mInterface.setTimeZoneState(android.app.time.TimeZoneState.parseCommandLineArgs(this));
        return 0;
    }

    private int runConfirmTimeZone() {
        getOutPrintWriter().println(this.mInterface.confirmTimeZone(parseTimeZoneIdArg(this)));
        return 0;
    }

    private static java.lang.String parseTimeZoneIdArg(android.os.ShellCommand shellCommand) {
        char c;
        java.lang.String str = null;
        while (true) {
            java.lang.String nextArg = shellCommand.getNextArg();
            if (nextArg != null) {
                switch (nextArg.hashCode()) {
                    case 1274807534:
                        if (nextArg.equals("--zone_id")) {
                            c = 0;
                            break;
                        }
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        str = shellCommand.getNextArgRequired();
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextArg);
                }
            } else {
                if (str == null) {
                    throw new java.lang.IllegalArgumentException("No zoneId specified.");
                }
                return str;
            }
        }
    }

    private int runDumpMetrics() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        com.android.server.timezonedetector.MetricsTimeZoneDetectorState generateMetricsState = this.mInterface.generateMetricsState();
        outPrintWriter.println("MetricsTimeZoneDetectorState:");
        outPrintWriter.println(generateMetricsState.toString());
        return 0;
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.printf("Time Zone Detector (%s) commands:\n", "time_zone_detector");
        outPrintWriter.printf("  help\n", new java.lang.Object[0]);
        outPrintWriter.printf("    Print this help text.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "is_auto_detection_enabled");
        outPrintWriter.printf("    Prints true/false according to the automatic time zone detection setting\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s true|false\n", "set_auto_detection_enabled");
        outPrintWriter.printf("    Sets the automatic time zone detection setting.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "is_telephony_detection_supported");
        outPrintWriter.printf("    Prints true/false according to whether telephony time zone detection is supported on this device.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "is_geo_detection_supported");
        outPrintWriter.printf("    Prints true/false according to whether geolocation time zone detection is supported on this device.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "is_geo_detection_enabled");
        outPrintWriter.printf("    Prints true/false according to the geolocation time zone detection setting.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s true|false\n", "set_geo_detection_enabled");
        outPrintWriter.printf("    Sets the geolocation time zone detection enabled setting.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "enable_telephony_fallback");
        outPrintWriter.printf("    Signals that telephony time zone detection fall back can be used if geolocation detection is supported and enabled.\n)", new java.lang.Object[0]);
        outPrintWriter.printf("    This is a temporary state until geolocation detection becomes \"certain\".\n", new java.lang.Object[0]);
        outPrintWriter.printf("    To have an effect this requires that the telephony fallback feature is supported on the device, see below for device_config flags.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s <location event opts>\n", "handle_location_algorithm_event");
        outPrintWriter.printf("    Simulates an event from the location time zone detection algorithm.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s <manual suggestion opts>\n", "suggest_manual_time_zone");
        outPrintWriter.printf("    Suggests a time zone as if supplied by a user manually.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s <telephony suggestion opts>\n", "suggest_telephony_time_zone");
        outPrintWriter.printf("    Simulates a time zone suggestion from the telephony time zone detection algorithm.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "get_time_zone_state");
        outPrintWriter.printf("    Returns the current time zone setting state.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s <time zone state options>\n", "set_time_zone_state_for_tests");
        outPrintWriter.printf("    Sets the current time zone state for tests.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s <--zone_id Olson ID>\n", "confirm_time_zone");
        outPrintWriter.printf("    Tries to confirms the time zone, raising the confidence.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", "dump_metrics");
        outPrintWriter.printf("    Dumps the service metrics to stdout for inspection.\n", new java.lang.Object[0]);
        outPrintWriter.println();
        com.android.server.timezonedetector.LocationAlgorithmEvent.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        android.app.timezonedetector.ManualTimeZoneSuggestion.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        android.app.timezonedetector.TelephonyTimeZoneSuggestion.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        android.app.time.TimeZoneState.printCommandLineOpts(outPrintWriter);
        outPrintWriter.println();
        outPrintWriter.printf("This service is also affected by the following device_config flags in the %s namespace:\n", "system_time");
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_FEATURE_SUPPORTED);
        outPrintWriter.printf("    Only observed if the geolocation time zone detection feature is enabled in config.\n", new java.lang.Object[0]);
        outPrintWriter.printf("    Set this to false to disable the feature.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_RUN_IN_BACKGROUND_ENABLED);
        outPrintWriter.printf("    Runs geolocation time zone detection even when it not enabled by the user. The result is not used to set the device's time zone [*]\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_SETTING_ENABLED_DEFAULT);
        outPrintWriter.printf("    Only used if the device does not have an explicit 'geolocation time zone detection enabled' setting stored [*].\n", new java.lang.Object[0]);
        outPrintWriter.printf("    The default is when unset is false.\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_LOCATION_TIME_ZONE_DETECTION_SETTING_ENABLED_OVERRIDE);
        outPrintWriter.printf("    Used to override the device's 'geolocation time zone detection enabled' setting [*].\n", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_TIME_ZONE_DETECTOR_AUTO_DETECTION_ENABLED_DEFAULT);
        outPrintWriter.printf("    Used to set the automatic time zone detection enabled default, i.e. when the device's automatic time zone detection enabled setting hasn't been set explicitly. Intended for internal testers.", new java.lang.Object[0]);
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_TIME_ZONE_DETECTOR_TELEPHONY_FALLBACK_SUPPORTED);
        outPrintWriter.printf("    Used to enable / disable support for telephony detection fallback. Also see the %s command.\n", "enable_telephony_fallback");
        outPrintWriter.printf("  %s\n", com.android.server.timedetector.ServerFlags.KEY_ENHANCED_METRICS_COLLECTION_ENABLED);
        outPrintWriter.printf("    Used to increase the detail of metrics collected / reported.\n", new java.lang.Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf("[*] To be enabled, the user must still have location = on / auto time zone detection = on.\n", new java.lang.Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf("See \"adb shell cmd device_config\" for more information on setting flags.\n", new java.lang.Object[0]);
        outPrintWriter.println();
        outPrintWriter.printf("Also see \"adb shell cmd %s help\" for lower-level location time zone commands / settings.\n", "location_time_zone_manager");
        outPrintWriter.println();
    }
}
