package com.android.server.location;

/* loaded from: classes2.dex */
class LocationShellCommand extends com.android.modules.utils.BasicShellCommandHandler {
    private static final float DEFAULT_TEST_LOCATION_ACCURACY = 100.0f;
    private final android.content.Context mContext;
    private final com.android.server.location.LocationManagerService mService;

    LocationShellCommand(android.content.Context context, com.android.server.location.LocationManagerService locationManagerService) {
        this.mContext = context;
        java.util.Objects.requireNonNull(locationManagerService);
        this.mService = locationManagerService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands((java.lang.String) null);
        }
        switch (str.hashCode()) {
            case -1064420500:
                if (str.equals("is-location-enabled")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -547571550:
                if (str.equals("providers")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -444268534:
                if (str.equals("is-automotive-gnss-suspended")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -361391806:
                if (str.equals("set-automotive-gnss-suspended")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -84945726:
                if (str.equals("set-adas-gnss-location-enabled")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1546249012:
                if (str.equals("set-location-enabled")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1640843002:
                if (str.equals("is-adas-gnss-location-enabled")) {
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
                handleIsLocationEnabled();
                return 0;
            case 1:
                handleSetLocationEnabled();
                return 0;
            case 2:
                handleIsAdasGnssLocationEnabled();
                return 0;
            case 3:
                handleSetAdasGnssLocationEnabled();
                return 0;
            case 4:
                handleSetAutomotiveGnssSuspended();
                return 0;
            case 5:
                handleIsAutomotiveGnssSuspended();
                return 0;
            case 6:
                return parseProvidersCommand(getNextArgRequired());
            default:
                return handleDefaultCommands(str);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int parseProvidersCommand(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1669563581:
                if (str.equals("remove-test-provider")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1650104991:
                if (str.equals("set-test-provider-location")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -61579243:
                if (str.equals("set-test-provider-enabled")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 11404448:
                if (str.equals("add-test-provider")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2036447497:
                if (str.equals("send-extra-command")) {
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
            case 0:
                handleAddTestProvider();
                return 0;
            case 1:
                handleRemoveTestProvider();
                return 0;
            case 2:
                handleSetTestProviderEnabled();
                return 0;
            case 3:
                handleSetTestProviderLocation();
                return 0;
            case 4:
                handleSendExtraCommand();
                return 0;
            default:
                return handleDefaultCommands(str);
        }
    }

    private void handleIsLocationEnabled() {
        int i = -3;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if ("--user".equals(nextOption)) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                getOutPrintWriter().println(this.mService.isLocationEnabledForUser(i));
                return;
            }
        }
    }

    private void handleSetLocationEnabled() {
        boolean parseBoolean = java.lang.Boolean.parseBoolean(getNextArgRequired());
        int i = -3;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if ("--user".equals(nextOption)) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                this.mService.setLocationEnabledForUser(parseBoolean, i);
                return;
            }
        }
    }

    private void handleIsAdasGnssLocationEnabled() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new java.lang.IllegalStateException("command only recognized on automotive devices");
        }
        int i = -3;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if ("--user".equals(nextOption)) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                getOutPrintWriter().println(this.mService.isAdasGnssLocationEnabledForUser(i));
                return;
            }
        }
    }

    private void handleSetAdasGnssLocationEnabled() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new java.lang.IllegalStateException("command only recognized on automotive devices");
        }
        boolean parseBoolean = java.lang.Boolean.parseBoolean(getNextArgRequired());
        int i = -3;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                if ("--user".equals(nextOption)) {
                    i = android.os.UserHandle.parseUserArg(getNextArgRequired());
                } else {
                    throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                this.mService.setAdasGnssLocationEnabledForUser(parseBoolean, i);
                return;
            }
        }
    }

    private void handleSetAutomotiveGnssSuspended() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new java.lang.IllegalStateException("command only recognized on automotive devices");
        }
        this.mService.setAutomotiveGnssSuspended(java.lang.Boolean.parseBoolean(getNextArgRequired()));
    }

    private void handleIsAutomotiveGnssSuspended() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            throw new java.lang.IllegalStateException("command only recognized on automotive devices");
        }
        getOutPrintWriter().println(this.mService.isAutomotiveGnssSuspended());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void handleAddTestProvider() {
        char c;
        java.lang.String nextArgRequired = getNextArgRequired();
        java.util.List<java.lang.String> emptyList = java.util.Collections.emptyList();
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        int i = 1;
        int i2 = 1;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -2115952999:
                        if (nextOption.equals("--accuracy")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1786843904:
                        if (nextOption.equals("--requiresNetwork")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1474799448:
                        if (nextOption.equals("--extraAttributionTags")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1446936854:
                        if (nextOption.equals("--supportsBearing")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1194644762:
                        if (nextOption.equals("--supportsAltitude")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1086076880:
                        if (nextOption.equals("--requiresCell")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1279633236:
                        if (nextOption.equals("--hasMonetaryCost")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1483009933:
                        if (nextOption.equals("--requiresSatellite")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1601002398:
                        if (nextOption.equals("--powerRequirement")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2048042627:
                        if (nextOption.equals("--supportsSpeed")) {
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
                        z = true;
                        break;
                    case 1:
                        z2 = true;
                        break;
                    case 2:
                        z3 = true;
                        break;
                    case 3:
                        z4 = true;
                        break;
                    case 4:
                        z5 = true;
                        break;
                    case 5:
                        z6 = true;
                        break;
                    case 6:
                        z7 = true;
                        break;
                    case 7:
                        i = java.lang.Integer.parseInt(getNextArgRequired());
                        break;
                    case '\b':
                        i2 = java.lang.Integer.parseInt(getNextArgRequired());
                        break;
                    case '\t':
                        emptyList = java.util.Arrays.asList(getNextArgRequired().split(","));
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Received unexpected option: " + nextOption);
                }
            } else {
                this.mService.addTestProvider(nextArgRequired, new android.location.provider.ProviderProperties.Builder().setHasNetworkRequirement(z).setHasSatelliteRequirement(z2).setHasCellRequirement(z3).setHasMonetaryCost(z4).setHasAltitudeSupport(z5).setHasSpeedSupport(z6).setHasBearingSupport(z7).setPowerUsage(i).setAccuracy(i2).build(), emptyList, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
                return;
            }
        }
    }

    private void handleRemoveTestProvider() {
        this.mService.removeTestProvider(getNextArgRequired(), this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
    }

    private void handleSetTestProviderEnabled() {
        this.mService.setTestProviderEnabled(getNextArgRequired(), java.lang.Boolean.parseBoolean(getNextArgRequired()), this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void handleSetTestProviderLocation() {
        char c;
        java.lang.String nextArgRequired = getNextArgRequired();
        android.location.Location location = new android.location.Location(nextArgRequired);
        location.setAccuracy(DEFAULT_TEST_LOCATION_ACCURACY);
        location.setTime(java.lang.System.currentTimeMillis());
        location.setElapsedRealtimeNanos(android.os.SystemClock.elapsedRealtimeNanos());
        boolean z = false;
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case -2115952999:
                        if (nextOption.equals("--accuracy")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1333430381:
                        if (nextOption.equals("--time")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1916798293:
                        if (nextOption.equals("--location")) {
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
                    case 0:
                        java.lang.String[] split = getNextArgRequired().split(",");
                        if (split.length != 2) {
                            throw new java.lang.IllegalArgumentException("Location argument must be in the form of \"<LATITUDE>,<LONGITUDE>\", not " + java.util.Arrays.toString(split));
                        }
                        location.setLatitude(java.lang.Double.parseDouble(split[0]));
                        location.setLongitude(java.lang.Double.parseDouble(split[1]));
                        z = true;
                        break;
                    case 1:
                        location.setAccuracy(java.lang.Float.parseFloat(getNextArgRequired()));
                        break;
                    case 2:
                        location.setTime(java.lang.Long.parseLong(getNextArgRequired()));
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextOption);
                }
            } else {
                if (!z) {
                    throw new java.lang.IllegalArgumentException("Option \"--location\" is required");
                }
                this.mService.setTestProviderLocation(nextArgRequired, location, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
                return;
            }
        }
    }

    private void handleSendExtraCommand() {
        this.mService.sendExtraCommand(getNextArgRequired(), getNextArgRequired(), null);
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Location service commands:");
        outPrintWriter.println("  help or -h");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println("  is-location-enabled [--user <USER_ID>]");
        outPrintWriter.println("    Gets the master location switch enabled state. If no user is specified,");
        outPrintWriter.println("    the current user is assumed.");
        outPrintWriter.println("  set-location-enabled true|false [--user <USER_ID>]");
        outPrintWriter.println("    Sets the master location switch enabled state. If no user is specified,");
        outPrintWriter.println("    the current user is assumed.");
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            outPrintWriter.println("  is-adas-gnss-location-enabled [--user <USER_ID>]");
            outPrintWriter.println("    Gets the ADAS GNSS location enabled state. If no user is specified,");
            outPrintWriter.println("    the current user is assumed.");
            outPrintWriter.println("  set-adas-gnss-location-enabled true|false [--user <USER_ID>]");
            outPrintWriter.println("    Sets the ADAS GNSS location enabled state. If no user is specified,");
            outPrintWriter.println("    the current user is assumed.");
            outPrintWriter.println("  is-automotive-gnss-suspended");
            outPrintWriter.println("    Gets the automotive GNSS suspended state.");
            outPrintWriter.println("  set-automotive-gnss-suspended true|false");
            outPrintWriter.println("    Sets the automotive GNSS suspended state.");
        }
        outPrintWriter.println("  providers");
        outPrintWriter.println("    The providers command is followed by a subcommand, as listed below:");
        outPrintWriter.println();
        outPrintWriter.println("    add-test-provider <PROVIDER> [--requiresNetwork] [--requiresSatellite]");
        outPrintWriter.println("      [--requiresCell] [--hasMonetaryCost] [--supportsAltitude]");
        outPrintWriter.println("      [--supportsSpeed] [--supportsBearing]");
        outPrintWriter.println("      [--powerRequirement <POWER_REQUIREMENT>]");
        outPrintWriter.println("      [--extraAttributionTags <TAG>,<TAG>,...]");
        outPrintWriter.println("      Add the given test provider. Requires MOCK_LOCATION permissions which");
        outPrintWriter.println("      can be enabled by running \"adb shell appops set <uid>");
        outPrintWriter.println("      android:mock_location allow\". There are optional flags that can be");
        outPrintWriter.println("      used to configure the provider properties and additional arguments. If");
        outPrintWriter.println("      no flags are included, then default values will be used.");
        outPrintWriter.println("    remove-test-provider <PROVIDER>");
        outPrintWriter.println("      Remove the given test provider.");
        outPrintWriter.println("    set-test-provider-enabled <PROVIDER> true|false");
        outPrintWriter.println("      Sets the given test provider enabled state.");
        outPrintWriter.println("    set-test-provider-location <PROVIDER> --location <LATITUDE>,<LONGITUDE>");
        outPrintWriter.println("      [--accuracy <ACCURACY>] [--time <TIME>]");
        outPrintWriter.println("      Set location for given test provider. Accuracy and time are optional.");
        outPrintWriter.println("    send-extra-command <PROVIDER> <COMMAND>");
        outPrintWriter.println("      Sends the given extra command to the given provider.");
        outPrintWriter.println();
        outPrintWriter.println("      Common commands that may be supported by the gps provider, depending on");
        outPrintWriter.println("      hardware and software configurations:");
        outPrintWriter.println("        delete_aiding_data - requests deletion of any predictive aiding data");
        outPrintWriter.println("        force_time_injection - requests NTP time injection");
        outPrintWriter.println("        force_psds_injection - requests predictive aiding data injection");
        outPrintWriter.println("        request_power_stats - requests GNSS power stats update");
    }
}
