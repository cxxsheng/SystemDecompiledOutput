package com.android.server.audio;

/* loaded from: classes.dex */
class AudioManagerShellCommand extends android.os.ShellCommand {
    private static final java.lang.String TAG = "AudioManagerShellCommand";
    private final com.android.server.audio.AudioService mService;

    AudioManagerShellCommand(com.android.server.audio.AudioService audioService) {
        this.mService = audioService;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public int onCommand(java.lang.String str) {
        char c;
        if (str == null) {
            return handleDefaultCommands(str);
        }
        getOutPrintWriter();
        switch (str.hashCode()) {
            case -1873164504:
                if (str.equals("set-encoded-surround-mode")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1340000401:
                if (str.equals("set-surround-format-enabled")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -460075795:
                if (str.equals("reset-sound-dose-timeout")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 865963338:
                if (str.equals("set-sound-dose-value")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 937504014:
                if (str.equals("get-is-surround-format-enabled")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1476886742:
                if (str.equals("get-sound-dose-value")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1578511132:
                if (str.equals("get-encoded-surround-mode")) {
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
            case 0:
                return setSurroundFormatEnabled();
            case 1:
                return getIsSurroundFormatEnabled();
            case 2:
                return setEncodedSurroundMode();
            case 3:
                return getEncodedSurroundMode();
            case 4:
                return setSoundDoseValue();
            case 5:
                return getSoundDoseValue();
            case 6:
                return resetSoundDoseTimeout();
            default:
                return 0;
        }
    }

    public void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Audio manager commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("  set-surround-format-enabled SURROUND_FORMAT IS_ENABLED");
        outPrintWriter.println("    Enables/disabled the SURROUND_FORMAT based on IS_ENABLED");
        outPrintWriter.println("  get-is-surround-format-enabled SURROUND_FORMAT");
        outPrintWriter.println("    Returns if the SURROUND_FORMAT is enabled");
        outPrintWriter.println("  set-encoded-surround-mode SURROUND_SOUND_MODE");
        outPrintWriter.println("    Sets the encoded surround sound mode to SURROUND_SOUND_MODE");
        outPrintWriter.println("  get-encoded-surround-mode");
        outPrintWriter.println("    Returns the encoded surround sound mode");
        outPrintWriter.println("  set-sound-dose-value");
        outPrintWriter.println("    Sets the current sound dose value");
        outPrintWriter.println("  get-sound-dose-value");
        outPrintWriter.println("    Returns the current sound dose value");
        outPrintWriter.println("  reset-sound-dose-timeout");
        outPrintWriter.println("    Resets the sound dose timeout used for momentary exposure");
    }

    private int setSurroundFormatEnabled() {
        java.lang.String nextArg = getNextArg();
        java.lang.String nextArg2 = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no surroundFormat specified");
            return 1;
        }
        if (nextArg2 == null) {
            getErrPrintWriter().println("Error: no enabled value for surroundFormat specified");
            return 1;
        }
        try {
            int parseInt = java.lang.Integer.parseInt(nextArg);
            boolean parseBoolean = java.lang.Boolean.parseBoolean(nextArg2);
            if (parseInt < 0) {
                getErrPrintWriter().println("Error: invalid value of surroundFormat");
                return 1;
            }
            ((android.media.AudioManager) this.mService.mContext.getSystemService(android.media.AudioManager.class)).setSurroundFormatEnabled(parseInt, parseBoolean);
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: wrong format specified for surroundFormat");
            return 1;
        }
    }

    private int getIsSurroundFormatEnabled() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no surroundFormat specified");
            return 1;
        }
        try {
            int parseInt = java.lang.Integer.parseInt(nextArg);
            if (parseInt < 0) {
                getErrPrintWriter().println("Error: invalid value of surroundFormat");
                return 1;
            }
            android.media.AudioManager audioManager = (android.media.AudioManager) this.mService.mContext.getSystemService(android.media.AudioManager.class);
            getOutPrintWriter().println("Value of enabled for " + parseInt + " is: " + audioManager.isSurroundFormatEnabled(parseInt));
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: wrong format specified for surroundFormat");
            return 1;
        }
    }

    private int setEncodedSurroundMode() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no encodedSurroundMode specified");
            return 1;
        }
        try {
            int parseInt = java.lang.Integer.parseInt(nextArg);
            if (parseInt < 0) {
                getErrPrintWriter().println("Error: invalid value of encodedSurroundMode");
                return 1;
            }
            ((android.media.AudioManager) this.mService.mContext.getSystemService(android.media.AudioManager.class)).setEncodedSurroundMode(parseInt);
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: wrong format specified for encoded surround mode");
            return 1;
        }
    }

    private int getEncodedSurroundMode() {
        android.media.AudioManager audioManager = (android.media.AudioManager) this.mService.mContext.getSystemService(android.media.AudioManager.class);
        getOutPrintWriter().println("Encoded surround mode: " + audioManager.getEncodedSurroundMode());
        return 0;
    }

    private int setSoundDoseValue() {
        java.lang.String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: no sound dose value specified");
            return 1;
        }
        try {
            float parseFloat = java.lang.Float.parseFloat(nextArg);
            if (parseFloat < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                getErrPrintWriter().println("Error: invalid value of sound dose");
                return 1;
            }
            ((android.media.AudioManager) this.mService.mContext.getSystemService(android.media.AudioManager.class)).setCsd(parseFloat);
            return 0;
        } catch (java.lang.NumberFormatException e) {
            getErrPrintWriter().println("Error: wrong format specified for sound dose");
            return 1;
        }
    }

    private int getSoundDoseValue() {
        android.media.AudioManager audioManager = (android.media.AudioManager) this.mService.mContext.getSystemService(android.media.AudioManager.class);
        getOutPrintWriter().println("Sound dose value: " + audioManager.getCsd());
        return 0;
    }

    private int resetSoundDoseTimeout() {
        ((android.media.AudioManager) this.mService.mContext.getSystemService(android.media.AudioManager.class)).setCsd(-1.0f);
        getOutPrintWriter().println("Reset sound dose momentary exposure timeout");
        return 0;
    }
}
