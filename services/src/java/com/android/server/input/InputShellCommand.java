package com.android.server.input;

/* loaded from: classes2.dex */
public class InputShellCommand extends android.os.ShellCommand {
    private static final int DEFAULT_BUTTON_STATE = 0;
    private static final int DEFAULT_DEVICE_ID = 0;
    private static final int DEFAULT_EDGE_FLAGS = 0;
    private static final int DEFAULT_FLAGS = 0;
    private static final int DEFAULT_META_STATE = 0;
    private static final float DEFAULT_PRECISION_X = 1.0f;
    private static final float DEFAULT_PRECISION_Y = 1.0f;
    private static final float DEFAULT_PRESSURE = 1.0f;
    private static final float DEFAULT_SIZE = 1.0f;
    private static final boolean INJECT_ASYNC = true;
    private static final boolean INJECT_SYNC = false;
    private static final java.lang.String INVALID_ARGUMENTS = "Error: Invalid arguments for command: ";
    private static final java.lang.String INVALID_DISPLAY_ARGUMENTS = "Error: Invalid arguments for display ID.";
    private static final java.util.Map<java.lang.Integer, java.lang.Integer> MODIFIER;
    private static final float NO_PRESSURE = 0.0f;
    private static final java.util.Map<java.lang.String, java.lang.Integer> SOURCES;
    private final java.util.function.BiConsumer<android.view.InputEvent, java.lang.Integer> mInputEventInjector;

    static {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        arrayMap.put(113, 12288);
        arrayMap.put(114, 20480);
        arrayMap.put(57, 18);
        arrayMap.put(58, 34);
        arrayMap.put(59, 65);
        arrayMap.put(60, 129);
        arrayMap.put(117, 196608);
        arrayMap.put(118, 327680);
        MODIFIER = java.util.Collections.unmodifiableMap(arrayMap);
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        arrayMap2.put("keyboard", 257);
        arrayMap2.put("dpad", 513);
        arrayMap2.put("gamepad", java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_HANDSET));
        arrayMap2.put("touchscreen", java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3));
        arrayMap2.put("mouse", java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG1_Layer1));
        arrayMap2.put("stylus", 16386);
        arrayMap2.put("trackball", 65540);
        arrayMap2.put("touchpad", 1048584);
        arrayMap2.put("touchnavigation", 2097152);
        arrayMap2.put("joystick", 16777232);
        arrayMap2.put("rotaryencoder", 4194304);
        SOURCES = java.util.Collections.unmodifiableMap(arrayMap2);
    }

    public InputShellCommand() {
        this(new java.util.function.BiConsumer() { // from class: com.android.server.input.InputShellCommand$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.input.InputShellCommand.injectInputEvent((android.view.InputEvent) obj, (java.lang.Integer) obj2);
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    InputShellCommand(java.util.function.BiConsumer<android.view.InputEvent, java.lang.Integer> biConsumer) {
        this.mInputEventInjector = biConsumer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void injectInputEvent(android.view.InputEvent inputEvent, java.lang.Integer num) {
        android.hardware.input.InputManagerGlobal.getInstance().injectInputEvent(inputEvent, num.intValue());
    }

    private void injectKeyEvent(android.view.KeyEvent keyEvent, boolean z) {
        int i;
        if (z) {
            i = 0;
        } else {
            i = 2;
        }
        this.mInputEventInjector.accept(keyEvent, java.lang.Integer.valueOf(i));
    }

    private int getInputDeviceId(int i) {
        for (int i2 : android.view.InputDevice.getDeviceIds()) {
            if (android.view.InputDevice.getDevice(i2).supportsSource(i)) {
                return i2;
            }
        }
        return 0;
    }

    private int getDisplayId() {
        java.lang.String nextArgRequired = getNextArgRequired();
        if ("INVALID_DISPLAY".equalsIgnoreCase(nextArgRequired)) {
            return -1;
        }
        if ("DEFAULT_DISPLAY".equalsIgnoreCase(nextArgRequired)) {
            return 0;
        }
        try {
            int parseInt = java.lang.Integer.parseInt(nextArgRequired);
            if (parseInt == -1) {
                return -1;
            }
            return java.lang.Math.max(parseInt, 0);
        } catch (java.lang.NumberFormatException e) {
            throw new java.lang.IllegalArgumentException(INVALID_DISPLAY_ARGUMENTS);
        }
    }

    private void injectMotionEvent(int i, int i2, long j, long j2, float f, float f2, float f3, int i3) {
        injectMotionEvent(i, i2, j, j2, java.util.Map.of(0, java.lang.Float.valueOf(f), 1, java.lang.Float.valueOf(f2), 2, java.lang.Float.valueOf(f3)), i3);
    }

    private void injectMotionEvent(int i, int i2, long j, long j2, java.util.Map<java.lang.Integer, java.lang.Float> map, int i3) {
        int i4;
        android.view.MotionEvent.PointerProperties[] pointerPropertiesArr = new android.view.MotionEvent.PointerProperties[1];
        for (int i5 = 0; i5 < 1; i5++) {
            pointerPropertiesArr[i5] = new android.view.MotionEvent.PointerProperties();
            pointerPropertiesArr[i5].id = i5;
            pointerPropertiesArr[i5].toolType = getToolType(i);
        }
        android.view.MotionEvent.PointerCoords[] pointerCoordsArr = new android.view.MotionEvent.PointerCoords[1];
        for (int i6 = 0; i6 < 1; i6++) {
            pointerCoordsArr[i6] = new android.view.MotionEvent.PointerCoords();
            pointerCoordsArr[i6].size = 1.0f;
            for (java.util.Map.Entry<java.lang.Integer, java.lang.Float> entry : map.entrySet()) {
                pointerCoordsArr[i6].setAxisValue(entry.getKey().intValue(), entry.getValue().floatValue());
            }
        }
        if (i3 == -1 && (i & 2) != 0) {
            i4 = 0;
        } else {
            i4 = i3;
        }
        this.mInputEventInjector.accept(android.view.MotionEvent.obtain(j, j2, i2, 1, pointerPropertiesArr, pointerCoordsArr, 0, 0, 1.0f, 1.0f, getInputDeviceId(i), 0, i, i4, 0), 2);
    }

    private float lerp(float f, float f2, float f3) {
        return ((f2 - f) * f3) + f;
    }

    private int getSource(int i, int i2) {
        return i == 0 ? i2 : i;
    }

    private int getToolType(int i) {
        switch (i) {
            case com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3 /* 4098 */:
            case 1048584:
            case 2097152:
                return 1;
            case com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG1_Layer1 /* 8194 */:
            case 65540:
            case 131076:
                return 3;
            case 16386:
            case 49154:
                return 2;
            default:
                return 0;
        }
    }

    public final int onCommand(java.lang.String str) {
        int i;
        int i2;
        if (!SOURCES.containsKey(str)) {
            i = 0;
        } else {
            i = SOURCES.get(str).intValue();
            str = getNextArgRequired();
        }
        if (!"-d".equals(str)) {
            i2 = -1;
        } else {
            i2 = getDisplayId();
            str = getNextArgRequired();
        }
        try {
            if ("text".equals(str)) {
                runText(i, i2);
            } else if ("keyevent".equals(str)) {
                runKeyEvent(i, i2);
            } else if ("tap".equals(str)) {
                runTap(i, i2);
            } else if ("swipe".equals(str)) {
                runSwipe(i, i2);
            } else if ("draganddrop".equals(str)) {
                runDragAndDrop(i, i2);
            } else if ("press".equals(str)) {
                runPress(i, i2);
            } else if ("roll".equals(str)) {
                runRoll(i, i2);
            } else if ("scroll".equals(str)) {
                runScroll(i, i2);
            } else if ("motionevent".equals(str)) {
                runMotionEvent(i, i2);
            } else if ("keycombination".equals(str)) {
                runKeyCombination(i, i2);
            } else {
                handleDefaultCommands(str);
            }
            return 0;
        } catch (java.lang.NumberFormatException e) {
            throw new java.lang.IllegalArgumentException(INVALID_ARGUMENTS + str);
        }
    }

    public final void onHelp() {
        java.io.PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("Usage: input [<source>] [-d DISPLAY_ID] <command> [<arg>...]");
            outPrintWriter.println();
            outPrintWriter.println("The sources are: ");
            java.util.Iterator<java.lang.String> it = SOURCES.keySet().iterator();
            while (it.hasNext()) {
                outPrintWriter.println("      " + it.next());
            }
            outPrintWriter.println("[axis_value] represents an option specifying the value of a given axis ");
            outPrintWriter.println("      The syntax is as follows: --axis <axis_name>,<axis_value>");
            outPrintWriter.println("            where <axis_name> is the name of the axis as defined in ");
            outPrintWriter.println("            MotionEvent without the AXIS_ prefix (e.g. SCROLL, X)");
            outPrintWriter.println("      Sample [axis_values] entry: `--axis Y,3`, `--axis SCROLL,-2`");
            outPrintWriter.println();
            outPrintWriter.printf("-d: specify the display ID.\n      (Default: %d for key event, %d for motion event if not specified.)", -1, 0);
            outPrintWriter.println();
            outPrintWriter.println("The commands and default sources are:");
            outPrintWriter.println("      text <string> (Default: keyboard)");
            outPrintWriter.println("      keyevent [--longpress|--doubletap|--async|--delay <duration between keycodes in ms>] <key code number or name> ... (Default: keyboard)");
            outPrintWriter.println("      tap <x> <y> (Default: touchscreen)");
            outPrintWriter.println("      swipe <x1> <y1> <x2> <y2> [duration(ms)] (Default: touchscreen)");
            outPrintWriter.println("      draganddrop <x1> <y1> <x2> <y2> [duration(ms)] (Default: touchscreen)");
            outPrintWriter.println("      press (Default: trackball)");
            outPrintWriter.println("      roll <dx> <dy> (Default: trackball)");
            outPrintWriter.println("      motionevent <DOWN|UP|MOVE|CANCEL> <x> <y> (Default: touchscreen)");
            outPrintWriter.println("      scroll (Default: rotaryencoder). Has the following syntax:");
            outPrintWriter.println("            scroll <x> <y> [axis_value] (for pointer-based sources)");
            outPrintWriter.println("            scroll [axis_value] (for non-pointer-based sources)");
            outPrintWriter.println("            Axis options: SCROLL, HSCROLL, VSCROLL");
            outPrintWriter.println("            None or one or multiple axis value options can be specified.");
            outPrintWriter.println("            To specify multiple axes, use one axis option for per axis.");
            outPrintWriter.println("            Example: `scroll --axis VSCROLL,2 --axis SCROLL,-2.4`");
            outPrintWriter.println("      keycombination [-t duration(ms)] <key code 1> <key code 2> ... (Default: keyboard, the key order is important here.)");
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

    private void runText(int i, int i2) {
        sendText(getSource(i, 257), getNextArgRequired(), i2);
    }

    private void sendText(int i, java.lang.String str, int i2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str);
        int i3 = 0;
        boolean z = false;
        while (i3 < sb.length()) {
            if (z) {
                if (sb.charAt(i3) != 's') {
                    z = false;
                } else {
                    sb.setCharAt(i3, ' ');
                    i3--;
                    sb.deleteCharAt(i3);
                    z = false;
                }
            }
            if (sb.charAt(i3) == '%') {
                z = true;
            }
            i3++;
        }
        for (android.view.KeyEvent keyEvent : android.view.KeyCharacterMap.load(-1).getEvents(sb.toString().toCharArray())) {
            if (i != keyEvent.getSource()) {
                keyEvent.setSource(i);
            }
            keyEvent.setDisplayId(i2);
            injectKeyEvent(keyEvent, false);
        }
    }

    private void runKeyEvent(int i, int i2) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        long j;
        java.lang.String nextArgRequired = getNextArgRequired();
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        long j2 = 0;
        while (true) {
            z = true;
            if (!nextArgRequired.startsWith("--")) {
                z2 = z5;
                z3 = z6;
                z4 = z7;
                j = j2;
                break;
            }
            z5 = z5 || nextArgRequired.equals("--longpress");
            z6 = z6 || nextArgRequired.equals("--async");
            z7 = z7 || nextArgRequired.equals("--doubletap");
            if (nextArgRequired.equals("--delay")) {
                j2 = java.lang.Long.parseLong(getNextArgRequired());
            }
            nextArgRequired = getNextArg();
            if (nextArgRequired == null) {
                z2 = z5;
                z3 = z6;
                z4 = z7;
                j = j2;
                break;
            }
        }
        while (true) {
            if (!z && j > 0) {
                sleep(j);
            }
            int keyCodeFromString = android.view.KeyEvent.keyCodeFromString(nextArgRequired);
            sendKeyEvent(i, keyCodeFromString, z2, i2, z3);
            if (z4) {
                sleep(android.view.ViewConfiguration.getDoubleTapMinTime());
                sendKeyEvent(i, keyCodeFromString, z2, i2, z3);
            }
            nextArgRequired = getNextArg();
            if (nextArgRequired != null) {
                z = false;
            } else {
                return;
            }
        }
    }

    private void sendKeyEvent(int i, int i2, boolean z, int i3, boolean z2) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.view.KeyEvent keyEvent = new android.view.KeyEvent(uptimeMillis, uptimeMillis, 0, i2, 0, 0, -1, 0, 0, i);
        keyEvent.setDisplayId(i3);
        injectKeyEvent(keyEvent, z2);
        if (z) {
            sleep(android.view.ViewConfiguration.getLongPressTimeout());
            injectKeyEvent(android.view.KeyEvent.changeTimeRepeat(keyEvent, uptimeMillis + android.view.ViewConfiguration.getLongPressTimeout(), 1, 128), z2);
        }
        injectKeyEvent(android.view.KeyEvent.changeAction(keyEvent, 1), z2);
    }

    private void runTap(int i, int i2) {
        sendTap(getSource(i, com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3), java.lang.Float.parseFloat(getNextArgRequired()), java.lang.Float.parseFloat(getNextArgRequired()), i2);
    }

    private void sendTap(int i, float f, float f2, int i2) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        injectMotionEvent(i, 0, uptimeMillis, uptimeMillis, f, f2, 1.0f, i2);
        injectMotionEvent(i, 1, uptimeMillis, uptimeMillis, f, f2, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, i2);
    }

    private void runPress(int i, int i2) {
        sendTap(getSource(i, 65540), com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, i2);
    }

    private void runSwipe(int i, int i2) {
        sendSwipe(getSource(i, com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3), i2, false);
    }

    private void sendSwipe(int i, int i2, boolean z) {
        int i3;
        float parseFloat = java.lang.Float.parseFloat(getNextArgRequired());
        float parseFloat2 = java.lang.Float.parseFloat(getNextArgRequired());
        float parseFloat3 = java.lang.Float.parseFloat(getNextArgRequired());
        float parseFloat4 = java.lang.Float.parseFloat(getNextArgRequired());
        java.lang.String nextArg = getNextArg();
        int parseInt = nextArg != null ? java.lang.Integer.parseInt(nextArg) : -1;
        if (parseInt >= 0) {
            i3 = parseInt;
        } else {
            i3 = 300;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        int i4 = i3;
        injectMotionEvent(i, 0, uptimeMillis, uptimeMillis, parseFloat, parseFloat2, 1.0f, i2);
        if (z) {
            sleep(android.view.ViewConfiguration.getLongPressTimeout());
        }
        long j = uptimeMillis + i4;
        long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
        while (uptimeMillis2 < j) {
            float f = (uptimeMillis2 - uptimeMillis) / i4;
            injectMotionEvent(i, 2, uptimeMillis, uptimeMillis2, lerp(parseFloat, parseFloat3, f), lerp(parseFloat2, parseFloat4, f), 1.0f, i2);
            uptimeMillis2 = android.os.SystemClock.uptimeMillis();
        }
        injectMotionEvent(i, 1, uptimeMillis, uptimeMillis2, parseFloat3, parseFloat4, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, i2);
    }

    private void runDragAndDrop(int i, int i2) {
        sendSwipe(getSource(i, com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3), i2, true);
    }

    private void runRoll(int i, int i2) {
        sendMove(getSource(i, 65540), java.lang.Float.parseFloat(getNextArgRequired()), java.lang.Float.parseFloat(getNextArgRequired()), i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0086 A[LOOP:0: B:8:0x0053->B:16:0x0086, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void runScroll(int i, int i2) {
        char c;
        int source = getSource(i, 4194304);
        boolean z = (source & 2) == 2;
        java.util.HashMap hashMap = new java.util.HashMap();
        if (z) {
            hashMap.put(0, java.lang.Float.valueOf(java.lang.Float.parseFloat(getNextArgRequired())));
            hashMap.put(1, java.lang.Float.valueOf(java.lang.Float.parseFloat(getNextArgRequired())));
        }
        java.util.Set<java.lang.Integer> of = java.util.Set.of(10, 9, 26);
        while (true) {
            java.lang.String nextOption = getNextOption();
            if (nextOption == null) {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                injectMotionEvent(source, 8, uptimeMillis, uptimeMillis, hashMap, i2);
                return;
            }
            switch (nextOption.hashCode()) {
                case 1332878657:
                    if (nextOption.equals("--axis")) {
                        c = 0;
                        switch (c) {
                            case 0:
                                android.util.Pair<java.lang.Integer, java.lang.Float> readAxisOptionValues = readAxisOptionValues(of);
                                hashMap.put((java.lang.Integer) readAxisOptionValues.first, (java.lang.Float) readAxisOptionValues.second);
                            default:
                                throw new java.lang.IllegalArgumentException("Unsupported option: " + nextOption);
                        }
                    }
                default:
                    c = 65535;
                    switch (c) {
                    }
            }
        }
    }

    private android.util.Pair<java.lang.Integer, java.lang.Float> readAxisOptionValues(java.util.Set<java.lang.Integer> set) {
        java.lang.String nextArgRequired = getNextArgRequired();
        java.lang.String[] split = nextArgRequired.split(",");
        if (split.length != 2) {
            throw new java.lang.IllegalArgumentException("Invalid --axis option value: " + nextArgRequired);
        }
        java.lang.String str = "AXIS_" + split[0];
        int axisFromString = android.view.MotionEvent.axisFromString(str);
        if (axisFromString == -1) {
            throw new java.lang.IllegalArgumentException("Invalid axis name: " + str);
        }
        if (!set.contains(java.lang.Integer.valueOf(axisFromString))) {
            throw new java.lang.IllegalArgumentException("Unsupported axis: " + str);
        }
        return android.util.Pair.create(java.lang.Integer.valueOf(axisFromString), java.lang.Float.valueOf(java.lang.Float.parseFloat(split[1])));
    }

    private void sendMove(int i, float f, float f2, int i2) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        injectMotionEvent(i, 2, uptimeMillis, uptimeMillis, f, f2, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, i2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int getAction() {
        char c;
        java.lang.String nextArgRequired = getNextArgRequired();
        java.lang.String upperCase = nextArgRequired.toUpperCase();
        switch (upperCase.hashCode()) {
            case 2715:
                if (upperCase.equals("UP")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 2104482:
                if (upperCase.equals("DOWN")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2372561:
                if (upperCase.equals("MOVE")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1980572282:
                if (upperCase.equals("CANCEL")) {
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
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                throw new java.lang.IllegalArgumentException("Unknown action: " + nextArgRequired);
        }
    }

    private void runMotionEvent(int i, int i2) {
        float parseFloat;
        float parseFloat2;
        int source = getSource(i, com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3);
        int action = getAction();
        if (action == 0 || action == 2 || action == 1) {
            parseFloat = java.lang.Float.parseFloat(getNextArgRequired());
            parseFloat2 = java.lang.Float.parseFloat(getNextArgRequired());
        } else {
            java.lang.String nextArg = getNextArg();
            java.lang.String nextArg2 = getNextArg();
            if (nextArg != null && nextArg2 != null) {
                parseFloat = java.lang.Float.parseFloat(nextArg);
                parseFloat2 = java.lang.Float.parseFloat(nextArg2);
            } else {
                parseFloat = 0.0f;
                parseFloat2 = 0.0f;
            }
        }
        sendMotionEvent(source, action, parseFloat, parseFloat2, i2);
    }

    private void sendMotionEvent(int i, int i2, float f, float f2, int i3) {
        float f3 = (i2 == 0 || i2 == 2) ? 1.0f : 0.0f;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        injectMotionEvent(i, i2, uptimeMillis, uptimeMillis, f, f2, f3, i3);
    }

    private void runKeyCombination(int i, int i2) {
        long j;
        java.lang.String nextArgRequired = getNextArgRequired();
        if (!"-t".equals(nextArgRequired)) {
            j = 0;
        } else {
            j = java.lang.Integer.parseInt(getNextArgRequired());
            nextArgRequired = getNextArgRequired();
        }
        android.util.IntArray intArray = new android.util.IntArray();
        while (nextArgRequired != null) {
            int keyCodeFromString = android.view.KeyEvent.keyCodeFromString(nextArgRequired);
            if (keyCodeFromString == 0) {
                throw new java.lang.IllegalArgumentException("Unknown keycode: " + nextArgRequired);
            }
            intArray.add(keyCodeFromString);
            nextArgRequired = getNextArg();
        }
        if (intArray.size() < 2) {
            throw new java.lang.IllegalArgumentException("keycombination requires at least 2 keycodes");
        }
        sendKeyCombination(i, intArray, i2, j);
    }

    private void sendKeyCombination(int i, android.util.IntArray intArray, int i2, long j) {
        boolean z;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        int size = intArray.size();
        android.view.KeyEvent[] keyEventArr = new android.view.KeyEvent[size];
        java.lang.Integer num = 0;
        int i3 = 0;
        int i4 = 0;
        while (i3 < size) {
            int i5 = intArray.get(i3);
            int i6 = i3;
            java.lang.Integer num2 = num;
            android.view.KeyEvent[] keyEventArr2 = keyEventArr;
            android.view.KeyEvent keyEvent = new android.view.KeyEvent(uptimeMillis, uptimeMillis, 0, i5, 0, i4, -1, 0, 0, i);
            keyEvent.setDisplayId(i2);
            keyEventArr2[i6] = keyEvent;
            i4 |= MODIFIER.getOrDefault(java.lang.Integer.valueOf(i5), num2).intValue();
            i3 = i6 + 1;
            size = size;
            num = num2;
            keyEventArr = keyEventArr2;
            uptimeMillis = uptimeMillis;
        }
        android.view.KeyEvent[] keyEventArr3 = keyEventArr;
        long j2 = uptimeMillis;
        java.lang.Integer num3 = num;
        int i7 = size;
        int i8 = 0;
        while (true) {
            z = true;
            if (i8 >= i7) {
                break;
            }
            injectKeyEvent(keyEventArr3[i8], true);
            i8++;
        }
        sleep(j);
        int i9 = 0;
        while (i9 < i7) {
            int keyCode = keyEventArr3[i9].getKeyCode();
            int i10 = i7;
            boolean z2 = z;
            injectKeyEvent(new android.view.KeyEvent(j2, j2, 1, keyCode, 0, i4, -1, 0, 0, i), z2);
            i4 &= ~MODIFIER.getOrDefault(java.lang.Integer.valueOf(keyCode), num3).intValue();
            i9++;
            z = z2;
            i7 = i10;
        }
    }

    private void sleep(long j) {
        try {
            java.lang.Thread.sleep(j);
        } catch (java.lang.InterruptedException e) {
            throw new java.lang.RuntimeException(e);
        }
    }
}
