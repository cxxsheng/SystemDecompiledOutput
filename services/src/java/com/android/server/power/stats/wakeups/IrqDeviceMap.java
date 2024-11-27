package com.android.server.power.stats.wakeups;

/* loaded from: classes2.dex */
public class IrqDeviceMap {
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String TAG_DEVICE = "device";
    private static final java.lang.String TAG_IRQ_DEVICE_MAP = "irq-device-map";
    private static final java.lang.String TAG_SUBSYSTEM = "subsystem";
    private static android.util.LongSparseArray<com.android.server.power.stats.wakeups.IrqDeviceMap> sInstanceMap = new android.util.LongSparseArray<>(1);
    private final android.util.ArrayMap<java.lang.String, java.util.List<java.lang.String>> mSubsystemsForDevice = new android.util.ArrayMap<>();

    private IrqDeviceMap(android.content.res.XmlResourceParser xmlResourceParser) {
        try {
            try {
                com.android.internal.util.XmlUtils.beginDocument(xmlResourceParser, TAG_IRQ_DEVICE_MAP);
                android.util.ArraySet arraySet = new android.util.ArraySet();
                java.lang.String str = null;
                while (true) {
                    int eventType = xmlResourceParser.getEventType();
                    if (eventType != 1) {
                        if (eventType == 2 && xmlResourceParser.getName().equals(TAG_DEVICE)) {
                            str = xmlResourceParser.getAttributeValue(null, "name");
                        }
                        if (str != null && eventType == 3 && xmlResourceParser.getName().equals(TAG_DEVICE)) {
                            if (arraySet.size() > 0) {
                                this.mSubsystemsForDevice.put(str, java.util.Collections.unmodifiableList(new java.util.ArrayList(arraySet)));
                            }
                            arraySet.clear();
                            str = null;
                        }
                        if (str != null && eventType == 2 && xmlResourceParser.getName().equals(TAG_SUBSYSTEM)) {
                            xmlResourceParser.next();
                            if (xmlResourceParser.getEventType() == 4) {
                                arraySet.add(xmlResourceParser.getText());
                            }
                        }
                        xmlResourceParser.next();
                    } else {
                        xmlResourceParser.close();
                        return;
                    }
                }
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            } catch (org.xmlpull.v1.XmlPullParserException e2) {
                throw new java.lang.RuntimeException(e2);
            }
        } catch (java.lang.Throwable th) {
            xmlResourceParser.close();
            throw th;
        }
    }

    public static com.android.server.power.stats.wakeups.IrqDeviceMap getInstance(android.content.Context context, int i) {
        synchronized (com.android.server.power.stats.wakeups.IrqDeviceMap.class) {
            try {
                long j = i;
                int indexOfKey = sInstanceMap.indexOfKey(j);
                if (indexOfKey >= 0) {
                    return sInstanceMap.valueAt(indexOfKey);
                }
                com.android.server.power.stats.wakeups.IrqDeviceMap irqDeviceMap = new com.android.server.power.stats.wakeups.IrqDeviceMap(context.getResources().getXml(i));
                synchronized (com.android.server.power.stats.wakeups.IrqDeviceMap.class) {
                    sInstanceMap.put(j, irqDeviceMap);
                }
                return irqDeviceMap;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    java.util.List<java.lang.String> getSubsystemsForDevice(java.lang.String str) {
        return this.mSubsystemsForDevice.get(str);
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        android.util.LongSparseArray<com.android.server.power.stats.wakeups.IrqDeviceMap> longSparseArray;
        indentingPrintWriter.println("Irq device map:");
        indentingPrintWriter.increaseIndent();
        synchronized (com.android.server.power.stats.wakeups.IrqDeviceMap.class) {
            longSparseArray = sInstanceMap;
        }
        int indexOfValue = longSparseArray.indexOfValue(this);
        indentingPrintWriter.println("Loaded from xml resource: " + (indexOfValue >= 0 ? "0x" + java.lang.Long.toHexString(longSparseArray.keyAt(indexOfValue)) : null));
        indentingPrintWriter.println("Map:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mSubsystemsForDevice.size(); i++) {
            indentingPrintWriter.print(this.mSubsystemsForDevice.keyAt(i) + ": ");
            indentingPrintWriter.println(this.mSubsystemsForDevice.valueAt(i));
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }
}
