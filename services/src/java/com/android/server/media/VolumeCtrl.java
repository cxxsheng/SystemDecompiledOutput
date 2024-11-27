package com.android.server.media;

/* loaded from: classes2.dex */
public class VolumeCtrl {
    private static final java.lang.String ADJUST_LOWER = "lower";
    private static final java.lang.String ADJUST_RAISE = "raise";
    private static final java.lang.String ADJUST_SAME = "same";
    private static final java.lang.String LOG_E = "[E]";
    private static final java.lang.String LOG_V = "[V]";
    private static final java.lang.String TAG = "VolumeCtrl";
    public static final java.lang.String USAGE = new java.lang.String("the options are as follows: \n\t\t--stream STREAM selects the stream to control, see AudioManager.STREAM_*\n\t\t                controls AudioManager.STREAM_MUSIC if no stream is specified\n\t\t--set INDEX     sets the volume index value\n\t\t--adj DIRECTION adjusts the volume, use raise|same|lower for the direction\n\t\t--get           outputs the current volume\n\t\t--show          shows the UI during the volume change\n\texamples:\n\t\tadb shell media volume --show --stream 3 --set 11\n\t\tadb shell media volume --stream 0 --adj lower\n\t\tadb shell media volume --stream 3 --get\n");
    private static final int VOLUME_CONTROL_MODE_ADJUST = 2;
    private static final int VOLUME_CONTROL_MODE_SET = 1;

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static void run(com.android.server.media.MediaShellCommand mediaShellCommand) throws java.lang.Exception {
        boolean z;
        int i = 5;
        int i2 = 0;
        java.lang.String str = null;
        int i3 = 3;
        char c = 0;
        int i4 = 0;
        boolean z2 = false;
        while (true) {
            java.lang.String nextOption = mediaShellCommand.getNextOption();
            char c2 = 65535;
            if (nextOption != null) {
                switch (nextOption.hashCode()) {
                    case 42995463:
                        if (nextOption.equals("--adj")) {
                            c2 = 4;
                            break;
                        }
                        break;
                    case 43001270:
                        if (nextOption.equals("--get")) {
                            c2 = 1;
                            break;
                        }
                        break;
                    case 43012802:
                        if (nextOption.equals("--set")) {
                            c2 = 3;
                            break;
                        }
                        break;
                    case 1333399709:
                        if (nextOption.equals("--show")) {
                            c2 = 0;
                            break;
                        }
                        break;
                    case 1508023584:
                        if (nextOption.equals("--stream")) {
                            c2 = 2;
                            break;
                        }
                        break;
                }
                switch (c2) {
                    case 0:
                        i4 = 1;
                        break;
                    case 1:
                        mediaShellCommand.log(LOG_V, "will get volume");
                        z2 = true;
                        break;
                    case 2:
                        i3 = java.lang.Integer.decode(mediaShellCommand.getNextArgRequired()).intValue();
                        mediaShellCommand.log(LOG_V, "will control stream=" + i3 + " (" + streamName(i3) + ")");
                        break;
                    case 3:
                        i = java.lang.Integer.decode(mediaShellCommand.getNextArgRequired()).intValue();
                        mediaShellCommand.log(LOG_V, "will set volume to index=" + i);
                        c = (char) 1;
                        break;
                    case 4:
                        str = mediaShellCommand.getNextArgRequired();
                        mediaShellCommand.log(LOG_V, "will adjust volume");
                        c = 2;
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown argument " + nextOption);
                }
            } else {
                if (c != 2) {
                    i2 = 1;
                } else {
                    if (str == null) {
                        mediaShellCommand.showError("Error: no valid volume adjustment (null)");
                        return;
                    }
                    switch (str.hashCode()) {
                        case 3522662:
                            if (str.equals(ADJUST_SAME)) {
                                z = true;
                                break;
                            }
                            z = -1;
                            break;
                        case 103164673:
                            if (str.equals(ADJUST_LOWER)) {
                                z = 2;
                                break;
                            }
                            z = -1;
                            break;
                        case 108275692:
                            if (str.equals(ADJUST_RAISE)) {
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
                            i2 = 1;
                            break;
                        case true:
                            break;
                        case true:
                            i2 = -1;
                            break;
                        default:
                            mediaShellCommand.showError("Error: no valid volume adjustment, was " + str + ", expected " + ADJUST_LOWER + "|" + ADJUST_SAME + "|" + ADJUST_RAISE);
                            return;
                    }
                }
                mediaShellCommand.log(LOG_V, "Connecting to AudioService");
                android.media.IAudioService asInterface = android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.checkService("audio"));
                if (asInterface == null) {
                    mediaShellCommand.log(LOG_E, "Error type 2");
                    throw new android.util.AndroidException("Can't connect to audio service; is the system running?");
                }
                if (c == 1 && (i > asInterface.getStreamMaxVolume(i3) || i < asInterface.getStreamMinVolume(i3))) {
                    mediaShellCommand.showError(java.lang.String.format("Error: invalid volume index %d for stream %d (should be in [%d..%d])", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(asInterface.getStreamMinVolume(i3)), java.lang.Integer.valueOf(asInterface.getStreamMaxVolume(i3))));
                    return;
                }
                java.lang.String name = mediaShellCommand.getClass().getPackage().getName();
                if (c == 1) {
                    asInterface.setStreamVolume(i3, i, i4, name);
                } else if (c == 2) {
                    asInterface.adjustStreamVolume(i3, i2, i4, name);
                }
                if (z2) {
                    mediaShellCommand.log(LOG_V, "volume is " + asInterface.getStreamVolume(i3) + " in range [" + asInterface.getStreamMinVolume(i3) + ".." + asInterface.getStreamMaxVolume(i3) + "]");
                    return;
                }
                return;
            }
        }
    }

    static java.lang.String streamName(int i) {
        try {
            return android.media.AudioSystem.STREAM_NAMES[i];
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            return "invalid stream";
        }
    }
}
