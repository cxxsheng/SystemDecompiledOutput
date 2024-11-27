package com.android.internal.os;

/* loaded from: classes4.dex */
public final class PowerStats {
    private static final byte PARCEL_FORMAT_VERSION = 1;
    private static final int PARCEL_FORMAT_VERSION_MASK = 255;
    private static final int STATS_ARRAY_LENGTH_MASK = 65280;
    private static final java.lang.String TAG = "PowerStats";
    private static final int UID_STATS_ARRAY_LENGTH_MASK = 16711680;
    public final com.android.internal.os.PowerStats.Descriptor descriptor;
    public long durationMs;
    public long[] stats;
    public final android.util.SparseArray<long[]> uidStats = new android.util.SparseArray<>();
    private static final com.android.internal.os.BatteryStatsHistory.VarintParceler VARINT_PARCELER = new com.android.internal.os.BatteryStatsHistory.VarintParceler();
    private static final int PARCEL_FORMAT_VERSION_SHIFT = java.lang.Integer.numberOfTrailingZeros(255);
    private static final int STATS_ARRAY_LENGTH_SHIFT = java.lang.Integer.numberOfTrailingZeros(65280);
    public static final int MAX_STATS_ARRAY_LENGTH = (1 << java.lang.Integer.bitCount(65280)) - 1;
    private static final int UID_STATS_ARRAY_LENGTH_SHIFT = java.lang.Integer.numberOfTrailingZeros(16711680);
    public static final int MAX_UID_STATS_ARRAY_LENGTH = (1 << java.lang.Integer.bitCount(16711680)) - 1;

    public static class Descriptor {
        private static final java.lang.String XML_ATTR_ID = "id";
        private static final java.lang.String XML_ATTR_NAME = "name";
        private static final java.lang.String XML_ATTR_STATS_ARRAY_LENGTH = "stats-array-length";
        private static final java.lang.String XML_ATTR_UID_STATS_ARRAY_LENGTH = "uid-stats-array-length";
        public static final java.lang.String XML_TAG_DESCRIPTOR = "descriptor";
        private static final java.lang.String XML_TAG_EXTRAS = "extras";
        public final android.os.PersistableBundle extras;
        public final java.lang.String name;
        public final int powerComponentId;
        public final int statsArrayLength;
        public final int uidStatsArrayLength;

        public Descriptor(int i, int i2, int i3, android.os.PersistableBundle persistableBundle) {
            this(i, android.os.BatteryConsumer.powerComponentIdToString(i), i2, i3, persistableBundle);
        }

        public Descriptor(int i, java.lang.String str, int i2, int i3, android.os.PersistableBundle persistableBundle) {
            if (i2 > com.android.internal.os.PowerStats.MAX_STATS_ARRAY_LENGTH) {
                throw new java.lang.IllegalArgumentException("statsArrayLength is too high. Max = " + com.android.internal.os.PowerStats.MAX_STATS_ARRAY_LENGTH);
            }
            if (i3 > com.android.internal.os.PowerStats.MAX_UID_STATS_ARRAY_LENGTH) {
                throw new java.lang.IllegalArgumentException("uidStatsArrayLength is too high. Max = " + com.android.internal.os.PowerStats.MAX_UID_STATS_ARRAY_LENGTH);
            }
            this.powerComponentId = i;
            this.name = str;
            this.statsArrayLength = i2;
            this.uidStatsArrayLength = i3;
            this.extras = persistableBundle;
        }

        public void writeSummaryToParcel(android.os.Parcel parcel) {
            parcel.writeInt(((1 << com.android.internal.os.PowerStats.PARCEL_FORMAT_VERSION_SHIFT) & 255) | ((this.statsArrayLength << com.android.internal.os.PowerStats.STATS_ARRAY_LENGTH_SHIFT) & 65280) | ((this.uidStatsArrayLength << com.android.internal.os.PowerStats.UID_STATS_ARRAY_LENGTH_SHIFT) & 16711680));
            parcel.writeInt(this.powerComponentId);
            parcel.writeString(this.name);
            this.extras.writeToParcel(parcel, 0);
        }

        public static com.android.internal.os.PowerStats.Descriptor readSummaryFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int i = (readInt & 255) >>> com.android.internal.os.PowerStats.PARCEL_FORMAT_VERSION_SHIFT;
            if (i != 1) {
                android.util.Slog.w(com.android.internal.os.PowerStats.TAG, "Cannot read PowerStats from Parcel - the parcel format version has changed from " + i + " to 1");
                return null;
            }
            return new com.android.internal.os.PowerStats.Descriptor(parcel.readInt(), parcel.readString(), (65280 & readInt) >>> com.android.internal.os.PowerStats.STATS_ARRAY_LENGTH_SHIFT, (readInt & 16711680) >>> com.android.internal.os.PowerStats.UID_STATS_ARRAY_LENGTH_SHIFT, parcel.readPersistableBundle());
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.internal.os.PowerStats.Descriptor)) {
                return false;
            }
            com.android.internal.os.PowerStats.Descriptor descriptor = (com.android.internal.os.PowerStats.Descriptor) obj;
            return this.powerComponentId == descriptor.powerComponentId && this.statsArrayLength == descriptor.statsArrayLength && this.uidStatsArrayLength == descriptor.uidStatsArrayLength && java.util.Objects.equals(this.name, descriptor.name) && this.extras.size() == descriptor.extras.size() && android.os.Bundle.kindofEquals(this.extras, descriptor.extras);
        }

        public void writeXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.startTag(null, XML_TAG_DESCRIPTOR);
            typedXmlSerializer.attributeInt(null, "id", this.powerComponentId);
            typedXmlSerializer.attribute(null, "name", this.name);
            typedXmlSerializer.attributeInt(null, XML_ATTR_STATS_ARRAY_LENGTH, this.statsArrayLength);
            typedXmlSerializer.attributeInt(null, XML_ATTR_UID_STATS_ARRAY_LENGTH, this.uidStatsArrayLength);
            try {
                typedXmlSerializer.startTag(null, "extras");
                this.extras.saveToXml(typedXmlSerializer);
                typedXmlSerializer.endTag(null, "extras");
                typedXmlSerializer.endTag(null, XML_TAG_DESCRIPTOR);
            } catch (org.xmlpull.v1.XmlPullParserException e) {
                throw new java.io.IOException(e);
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0040, code lost:
        
            if (r0.equals("extras") != false) goto L21;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static com.android.internal.os.PowerStats.Descriptor createFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            int eventType = typedXmlPullParser.getEventType();
            int i = -1;
            java.lang.String str = null;
            android.os.PersistableBundle persistableBundle = null;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                boolean z = true;
                if (eventType != 1 && (eventType != 3 || !typedXmlPullParser.getName().equals(XML_TAG_DESCRIPTOR))) {
                    if (eventType == 2) {
                        java.lang.String name = typedXmlPullParser.getName();
                        switch (name.hashCode()) {
                            case -1289032093:
                                break;
                            case -748366993:
                                if (name.equals(XML_TAG_DESCRIPTOR)) {
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
                                i = typedXmlPullParser.getAttributeInt(null, "id");
                                str = typedXmlPullParser.getAttributeValue(null, "name");
                                i2 = typedXmlPullParser.getAttributeInt(null, XML_ATTR_STATS_ARRAY_LENGTH);
                                i3 = typedXmlPullParser.getAttributeInt(null, XML_ATTR_UID_STATS_ARRAY_LENGTH);
                                break;
                            case true:
                                persistableBundle = android.os.PersistableBundle.restoreFromXml(typedXmlPullParser);
                                break;
                        }
                    }
                    eventType = typedXmlPullParser.next();
                }
            }
            if (i == -1) {
                return null;
            }
            if (i >= 1000) {
                return new com.android.internal.os.PowerStats.Descriptor(i, str, i2, i3, persistableBundle);
            }
            if (i < 18) {
                return new com.android.internal.os.PowerStats.Descriptor(i, i2, i3, persistableBundle);
            }
            android.util.Slog.e(com.android.internal.os.PowerStats.TAG, "Unrecognized power component: " + i);
            return null;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.powerComponentId));
        }

        public java.lang.String toString() {
            if (this.extras != null) {
                this.extras.size();
            }
            return "PowerStats.Descriptor{powerComponentId=" + this.powerComponentId + ", name='" + this.name + android.text.format.DateFormat.QUOTE + ", statsArrayLength=" + this.statsArrayLength + ", uidStatsArrayLength=" + this.uidStatsArrayLength + ", extras=" + this.extras + '}';
        }
    }

    public static class DescriptorRegistry {
        private final android.util.SparseArray<com.android.internal.os.PowerStats.Descriptor> mDescriptors = new android.util.SparseArray<>();

        public void register(com.android.internal.os.PowerStats.Descriptor descriptor) {
            this.mDescriptors.put(descriptor.powerComponentId, descriptor);
        }

        public com.android.internal.os.PowerStats.Descriptor get(int i) {
            return this.mDescriptors.get(i);
        }
    }

    public PowerStats(com.android.internal.os.PowerStats.Descriptor descriptor) {
        this.descriptor = descriptor;
        this.stats = new long[descriptor.statsArrayLength];
    }

    public void writeToParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int dataPosition2 = parcel.dataPosition();
        parcel.writeInt(this.descriptor.powerComponentId);
        parcel.writeLong(this.durationMs);
        VARINT_PARCELER.writeLongArray(parcel, this.stats);
        parcel.writeInt(this.uidStats.size());
        for (int i = 0; i < this.uidStats.size(); i++) {
            parcel.writeInt(this.uidStats.keyAt(i));
            VARINT_PARCELER.writeLongArray(parcel, this.uidStats.valueAt(i));
        }
        int dataPosition3 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition3 - dataPosition2);
        parcel.setDataPosition(dataPosition3);
    }

    public static com.android.internal.os.PowerStats readFromParcel(android.os.Parcel parcel, com.android.internal.os.PowerStats.DescriptorRegistry descriptorRegistry) {
        int readInt = parcel.readInt();
        int dataPosition = parcel.dataPosition();
        int i = dataPosition + readInt;
        try {
            int readInt2 = parcel.readInt();
            com.android.internal.os.PowerStats.Descriptor descriptor = descriptorRegistry.get(readInt2);
            if (descriptor == null) {
                android.util.Slog.e(TAG, "Unsupported PowerStats for power component ID: " + readInt2);
                return null;
            }
            com.android.internal.os.PowerStats powerStats = new com.android.internal.os.PowerStats(descriptor);
            powerStats.durationMs = parcel.readLong();
            powerStats.stats = new long[descriptor.statsArrayLength];
            VARINT_PARCELER.readLongArray(parcel, powerStats.stats);
            int readInt3 = parcel.readInt();
            for (int i2 = 0; i2 < readInt3; i2++) {
                int readInt4 = parcel.readInt();
                long[] jArr = new long[descriptor.uidStatsArrayLength];
                VARINT_PARCELER.readLongArray(parcel, jArr);
                powerStats.uidStats.put(readInt4, jArr);
            }
            if (parcel.dataPosition() == i) {
                return powerStats;
            }
            android.util.Slog.e(TAG, "Corrupted PowerStats parcel. Expected length: " + readInt + ", actual length: " + (parcel.dataPosition() - dataPosition));
            return null;
        } finally {
            parcel.setDataPosition(i);
        }
    }

    public java.lang.String formatForBatteryHistory(java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("duration=").append(this.durationMs).append(" ").append(this.descriptor.name);
        if (this.stats.length > 0) {
            sb.append("=").append(java.util.Arrays.toString(this.stats));
        }
        for (int i = 0; i < this.uidStats.size(); i++) {
            sb.append(str).append(android.os.UserHandle.formatUid(this.uidStats.keyAt(i))).append(": ").append(java.util.Arrays.toString(this.uidStats.valueAt(i)));
        }
        return sb.toString();
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("PowerStats: " + this.descriptor.name + " (" + this.descriptor.powerComponentId + ')');
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("duration", java.lang.Long.valueOf(this.durationMs)).println();
        for (int i = 0; i < this.uidStats.size(); i++) {
            indentingPrintWriter.print("UID ");
            indentingPrintWriter.print(this.uidStats.keyAt(i));
            indentingPrintWriter.print(": ");
            indentingPrintWriter.print(java.util.Arrays.toString(this.uidStats.valueAt(i)));
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
    }

    public java.lang.String toString() {
        return "PowerStats: " + formatForBatteryHistory(" UID ");
    }
}
