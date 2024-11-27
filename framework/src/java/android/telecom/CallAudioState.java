package android.telecom;

/* loaded from: classes3.dex */
public final class CallAudioState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.CallAudioState> CREATOR = new android.os.Parcelable.Creator<android.telecom.CallAudioState>() { // from class: android.telecom.CallAudioState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.CallAudioState createFromParcel(android.os.Parcel parcel) {
            boolean z = parcel.readByte() != 0;
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            android.bluetooth.BluetoothDevice bluetoothDevice = (android.bluetooth.BluetoothDevice) parcel.readParcelable(java.lang.ClassLoader.getSystemClassLoader(), android.bluetooth.BluetoothDevice.class);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            parcel.readParcelableList(arrayList, java.lang.ClassLoader.getSystemClassLoader(), android.bluetooth.BluetoothDevice.class);
            return new android.telecom.CallAudioState(z, readInt, readInt2, bluetoothDevice, arrayList);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.CallAudioState[] newArray(int i) {
            return new android.telecom.CallAudioState[i];
        }
    };
    public static final int ROUTE_ALL = 31;
    public static final int ROUTE_BLUETOOTH = 2;
    public static final int ROUTE_EARPIECE = 1;
    public static final int ROUTE_SPEAKER = 8;
    public static final int ROUTE_STREAMING = 16;
    public static final int ROUTE_WIRED_HEADSET = 4;
    public static final int ROUTE_WIRED_OR_EARPIECE = 5;
    private final android.bluetooth.BluetoothDevice activeBluetoothDevice;
    private final boolean isMuted;
    private final int route;
    private final java.util.Collection<android.bluetooth.BluetoothDevice> supportedBluetoothDevices;
    private final int supportedRouteMask;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CallAudioRoute {
    }

    public CallAudioState(boolean z, int i, int i2) {
        this(z, i, i2, null, java.util.Collections.emptyList());
    }

    public CallAudioState(boolean z, int i, int i2, android.bluetooth.BluetoothDevice bluetoothDevice, java.util.Collection<android.bluetooth.BluetoothDevice> collection) {
        this.isMuted = z;
        this.route = i;
        this.supportedRouteMask = i2;
        this.activeBluetoothDevice = bluetoothDevice;
        this.supportedBluetoothDevices = collection;
    }

    public CallAudioState(android.telecom.CallAudioState callAudioState) {
        this.isMuted = callAudioState.isMuted();
        this.route = callAudioState.getRoute();
        this.supportedRouteMask = callAudioState.getSupportedRouteMask();
        this.activeBluetoothDevice = callAudioState.activeBluetoothDevice;
        this.supportedBluetoothDevices = callAudioState.getSupportedBluetoothDevices();
    }

    public CallAudioState(android.telecom.AudioState audioState) {
        this.isMuted = audioState.isMuted();
        this.route = audioState.getRoute();
        this.supportedRouteMask = audioState.getSupportedRouteMask();
        this.activeBluetoothDevice = null;
        this.supportedBluetoothDevices = java.util.Collections.emptyList();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.telecom.CallAudioState)) {
            return false;
        }
        android.telecom.CallAudioState callAudioState = (android.telecom.CallAudioState) obj;
        if (this.supportedBluetoothDevices.size() != callAudioState.supportedBluetoothDevices.size()) {
            return false;
        }
        java.util.Iterator<android.bluetooth.BluetoothDevice> it = this.supportedBluetoothDevices.iterator();
        while (it.hasNext()) {
            if (!callAudioState.supportedBluetoothDevices.contains(it.next())) {
                return false;
            }
        }
        return java.util.Objects.equals(this.activeBluetoothDevice, callAudioState.activeBluetoothDevice) && isMuted() == callAudioState.isMuted() && getRoute() == callAudioState.getRoute() && getSupportedRouteMask() == callAudioState.getSupportedRouteMask();
    }

    public java.lang.String toString() {
        return java.lang.String.format(java.util.Locale.US, "[AudioState isMuted: %b, route: %s, supportedRouteMask: %s, activeBluetoothDevice: [%s], supportedBluetoothDevices: [%s]]", java.lang.Boolean.valueOf(this.isMuted), audioRouteToString(this.route), audioRouteToString(this.supportedRouteMask), this.activeBluetoothDevice, (java.lang.String) this.supportedBluetoothDevices.stream().map(new java.util.function.Function() { // from class: android.telecom.CallAudioState$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.bluetooth.BluetoothDevice) obj).getAddress();
            }
        }).collect(java.util.stream.Collectors.joining(", ")));
    }

    public boolean isMuted() {
        return this.isMuted;
    }

    public int getRoute() {
        return this.route;
    }

    public int getSupportedRouteMask() {
        if (this.route == 16) {
            return 16;
        }
        return this.supportedRouteMask;
    }

    public int getRawSupportedRouteMask() {
        return this.supportedRouteMask;
    }

    public android.bluetooth.BluetoothDevice getActiveBluetoothDevice() {
        return this.activeBluetoothDevice;
    }

    public java.util.Collection<android.bluetooth.BluetoothDevice> getSupportedBluetoothDevices() {
        return this.supportedBluetoothDevices;
    }

    public static java.lang.String audioRouteToString(int i) {
        if (i == 0 || (i & (-32)) != 0) {
            return "UNKNOWN";
        }
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        if ((i & 1) == 1) {
            listAppend(stringBuffer, "EARPIECE");
        }
        if ((i & 2) == 2) {
            listAppend(stringBuffer, "BLUETOOTH");
        }
        if ((i & 4) == 4) {
            listAppend(stringBuffer, "WIRED_HEADSET");
        }
        if ((i & 8) == 8) {
            listAppend(stringBuffer, "SPEAKER");
        }
        if ((i & 16) == 16) {
            listAppend(stringBuffer, "STREAMING");
        }
        return stringBuffer.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.isMuted ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.route);
        parcel.writeInt(this.supportedRouteMask);
        parcel.writeParcelable(this.activeBluetoothDevice, 0);
        parcel.writeParcelableList(new java.util.ArrayList(this.supportedBluetoothDevices), 0);
    }

    private static void listAppend(java.lang.StringBuffer stringBuffer, java.lang.String str) {
        if (stringBuffer.length() > 0) {
            stringBuffer.append(", ");
        }
        stringBuffer.append(str);
    }
}
