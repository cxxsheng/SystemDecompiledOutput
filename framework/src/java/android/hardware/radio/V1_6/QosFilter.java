package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class QosFilter {
    public java.util.ArrayList<java.lang.String> localAddresses = new java.util.ArrayList<>();
    public java.util.ArrayList<java.lang.String> remoteAddresses = new java.util.ArrayList<>();
    public android.hardware.radio.V1_6.MaybePort localPort = new android.hardware.radio.V1_6.MaybePort();
    public android.hardware.radio.V1_6.MaybePort remotePort = new android.hardware.radio.V1_6.MaybePort();
    public byte protocol = 0;
    public android.hardware.radio.V1_6.QosFilter.TypeOfService tos = new android.hardware.radio.V1_6.QosFilter.TypeOfService();
    public android.hardware.radio.V1_6.QosFilter.Ipv6FlowLabel flowLabel = new android.hardware.radio.V1_6.QosFilter.Ipv6FlowLabel();
    public android.hardware.radio.V1_6.QosFilter.IpsecSpi spi = new android.hardware.radio.V1_6.QosFilter.IpsecSpi();
    public byte direction = 0;
    public int precedence = 0;

    public static final class TypeOfService {
        private byte hidl_d = 0;
        private java.lang.Object hidl_o;

        public TypeOfService() {
            this.hidl_o = null;
            this.hidl_o = new android.internal.hidl.safe_union.V1_0.Monostate();
        }

        public static final class hidl_discriminator {
            public static final byte noinit = 0;
            public static final byte value = 1;

            public static final java.lang.String getName(byte b) {
                switch (b) {
                    case 0:
                        return "noinit";
                    case 1:
                        return "value";
                    default:
                        return "Unknown";
                }
            }

            private hidl_discriminator() {
            }
        }

        public void noinit(android.internal.hidl.safe_union.V1_0.Monostate monostate) {
            this.hidl_d = (byte) 0;
            this.hidl_o = monostate;
        }

        public android.internal.hidl.safe_union.V1_0.Monostate noinit() {
            if (this.hidl_d != 0) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.QosFilter.TypeOfService.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !android.internal.hidl.safe_union.V1_0.Monostate.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (android.internal.hidl.safe_union.V1_0.Monostate) this.hidl_o;
        }

        public void value(byte b) {
            this.hidl_d = (byte) 1;
            this.hidl_o = java.lang.Byte.valueOf(b);
        }

        public byte value() {
            if (this.hidl_d != 1) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.QosFilter.TypeOfService.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !java.lang.Byte.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return ((java.lang.Byte) this.hidl_o).byteValue();
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.radio.V1_6.QosFilter.TypeOfService.class) {
                return false;
            }
            android.hardware.radio.V1_6.QosFilter.TypeOfService typeOfService = (android.hardware.radio.V1_6.QosFilter.TypeOfService) obj;
            if (this.hidl_d == typeOfService.hidl_d && android.os.HidlSupport.deepEquals(this.hidl_o, typeOfService.hidl_o)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.hidl_o)), java.lang.Integer.valueOf(java.util.Objects.hashCode(java.lang.Byte.valueOf(this.hidl_d))));
        }

        public final java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("{");
            switch (this.hidl_d) {
                case 0:
                    sb.append(".noinit = ");
                    sb.append(noinit());
                    break;
                case 1:
                    sb.append(".value = ");
                    sb.append((int) value());
                    break;
                default:
                    throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(2L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.radio.V1_6.QosFilter.TypeOfService> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.radio.V1_6.QosFilter.TypeOfService> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 2, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.radio.V1_6.QosFilter.TypeOfService typeOfService = new android.hardware.radio.V1_6.QosFilter.TypeOfService();
                typeOfService.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 2);
                arrayList.add(typeOfService);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.hidl_d = hwBlob.getInt8(0 + j);
            switch (this.hidl_d) {
                case 0:
                    this.hidl_o = new android.internal.hidl.safe_union.V1_0.Monostate();
                    ((android.internal.hidl.safe_union.V1_0.Monostate) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 1);
                    return;
                case 1:
                    this.hidl_o = 0;
                    this.hidl_o = java.lang.Byte.valueOf(hwBlob.getInt8(j + 1));
                    return;
                default:
                    throw new java.lang.IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(2);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.QosFilter.TypeOfService> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 2);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 2);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt8(0 + j, this.hidl_d);
            switch (this.hidl_d) {
                case 0:
                    noinit().writeEmbeddedToBlob(hwBlob, j + 1);
                    return;
                case 1:
                    hwBlob.putInt8(j + 1, value());
                    return;
                default:
                    throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }
    }

    public static final class Ipv6FlowLabel {
        private byte hidl_d = 0;
        private java.lang.Object hidl_o;

        public Ipv6FlowLabel() {
            this.hidl_o = null;
            this.hidl_o = new android.internal.hidl.safe_union.V1_0.Monostate();
        }

        public static final class hidl_discriminator {
            public static final byte noinit = 0;
            public static final byte value = 1;

            public static final java.lang.String getName(byte b) {
                switch (b) {
                    case 0:
                        return "noinit";
                    case 1:
                        return "value";
                    default:
                        return "Unknown";
                }
            }

            private hidl_discriminator() {
            }
        }

        public void noinit(android.internal.hidl.safe_union.V1_0.Monostate monostate) {
            this.hidl_d = (byte) 0;
            this.hidl_o = monostate;
        }

        public android.internal.hidl.safe_union.V1_0.Monostate noinit() {
            if (this.hidl_d != 0) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.QosFilter.Ipv6FlowLabel.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !android.internal.hidl.safe_union.V1_0.Monostate.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (android.internal.hidl.safe_union.V1_0.Monostate) this.hidl_o;
        }

        public void value(int i) {
            this.hidl_d = (byte) 1;
            this.hidl_o = java.lang.Integer.valueOf(i);
        }

        public int value() {
            if (this.hidl_d != 1) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.QosFilter.Ipv6FlowLabel.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !java.lang.Integer.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return ((java.lang.Integer) this.hidl_o).intValue();
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.radio.V1_6.QosFilter.Ipv6FlowLabel.class) {
                return false;
            }
            android.hardware.radio.V1_6.QosFilter.Ipv6FlowLabel ipv6FlowLabel = (android.hardware.radio.V1_6.QosFilter.Ipv6FlowLabel) obj;
            if (this.hidl_d == ipv6FlowLabel.hidl_d && android.os.HidlSupport.deepEquals(this.hidl_o, ipv6FlowLabel.hidl_o)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.hidl_o)), java.lang.Integer.valueOf(java.util.Objects.hashCode(java.lang.Byte.valueOf(this.hidl_d))));
        }

        public final java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("{");
            switch (this.hidl_d) {
                case 0:
                    sb.append(".noinit = ");
                    sb.append(noinit());
                    break;
                case 1:
                    sb.append(".value = ");
                    sb.append(value());
                    break;
                default:
                    throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.radio.V1_6.QosFilter.Ipv6FlowLabel> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.radio.V1_6.QosFilter.Ipv6FlowLabel> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.radio.V1_6.QosFilter.Ipv6FlowLabel ipv6FlowLabel = new android.hardware.radio.V1_6.QosFilter.Ipv6FlowLabel();
                ipv6FlowLabel.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 8);
                arrayList.add(ipv6FlowLabel);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.hidl_d = hwBlob.getInt8(0 + j);
            switch (this.hidl_d) {
                case 0:
                    this.hidl_o = new android.internal.hidl.safe_union.V1_0.Monostate();
                    ((android.internal.hidl.safe_union.V1_0.Monostate) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
                    return;
                case 1:
                    this.hidl_o = 0;
                    this.hidl_o = java.lang.Integer.valueOf(hwBlob.getInt32(j + 4));
                    return;
                default:
                    throw new java.lang.IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(8);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.QosFilter.Ipv6FlowLabel> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 8);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt8(0 + j, this.hidl_d);
            switch (this.hidl_d) {
                case 0:
                    noinit().writeEmbeddedToBlob(hwBlob, j + 4);
                    return;
                case 1:
                    hwBlob.putInt32(j + 4, value());
                    return;
                default:
                    throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }
    }

    public static final class IpsecSpi {
        private byte hidl_d = 0;
        private java.lang.Object hidl_o;

        public IpsecSpi() {
            this.hidl_o = null;
            this.hidl_o = new android.internal.hidl.safe_union.V1_0.Monostate();
        }

        public static final class hidl_discriminator {
            public static final byte noinit = 0;
            public static final byte value = 1;

            public static final java.lang.String getName(byte b) {
                switch (b) {
                    case 0:
                        return "noinit";
                    case 1:
                        return "value";
                    default:
                        return "Unknown";
                }
            }

            private hidl_discriminator() {
            }
        }

        public void noinit(android.internal.hidl.safe_union.V1_0.Monostate monostate) {
            this.hidl_d = (byte) 0;
            this.hidl_o = monostate;
        }

        public android.internal.hidl.safe_union.V1_0.Monostate noinit() {
            if (this.hidl_d != 0) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.QosFilter.IpsecSpi.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !android.internal.hidl.safe_union.V1_0.Monostate.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return (android.internal.hidl.safe_union.V1_0.Monostate) this.hidl_o;
        }

        public void value(int i) {
            this.hidl_d = (byte) 1;
            this.hidl_o = java.lang.Integer.valueOf(i);
        }

        public int value() {
            if (this.hidl_d != 1) {
                throw new java.lang.IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + android.hardware.radio.V1_6.QosFilter.IpsecSpi.hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (this.hidl_o != null ? this.hidl_o.getClass().getName() : "null") + android.media.MediaMetrics.SEPARATOR);
            }
            if (this.hidl_o != null && !java.lang.Integer.class.isInstance(this.hidl_o)) {
                throw new java.lang.Error("Union is in a corrupted state.");
            }
            return ((java.lang.Integer) this.hidl_o).intValue();
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != android.hardware.radio.V1_6.QosFilter.IpsecSpi.class) {
                return false;
            }
            android.hardware.radio.V1_6.QosFilter.IpsecSpi ipsecSpi = (android.hardware.radio.V1_6.QosFilter.IpsecSpi) obj;
            if (this.hidl_d == ipsecSpi.hidl_d && android.os.HidlSupport.deepEquals(this.hidl_o, ipsecSpi.hidl_o)) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.hidl_o)), java.lang.Integer.valueOf(java.util.Objects.hashCode(java.lang.Byte.valueOf(this.hidl_d))));
        }

        public final java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("{");
            switch (this.hidl_d) {
                case 0:
                    sb.append(".noinit = ");
                    sb.append(noinit());
                    break;
                case 1:
                    sb.append(".value = ");
                    sb.append(value());
                    break;
                default:
                    throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(android.os.HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
        }

        public static final java.util.ArrayList<android.hardware.radio.V1_6.QosFilter.IpsecSpi> readVectorFromParcel(android.os.HwParcel hwParcel) {
            java.util.ArrayList<android.hardware.radio.V1_6.QosFilter.IpsecSpi> arrayList = new java.util.ArrayList<>();
            android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                android.hardware.radio.V1_6.QosFilter.IpsecSpi ipsecSpi = new android.hardware.radio.V1_6.QosFilter.IpsecSpi();
                ipsecSpi.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 8);
                arrayList.add(ipsecSpi);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
            this.hidl_d = hwBlob.getInt8(0 + j);
            switch (this.hidl_d) {
                case 0:
                    this.hidl_o = new android.internal.hidl.safe_union.V1_0.Monostate();
                    ((android.internal.hidl.safe_union.V1_0.Monostate) this.hidl_o).readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
                    return;
                case 1:
                    this.hidl_o = 0;
                    this.hidl_o = java.lang.Integer.valueOf(hwBlob.getInt32(j + 4));
                    return;
                default:
                    throw new java.lang.IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(android.os.HwParcel hwParcel) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(8);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.QosFilter.IpsecSpi> arrayList) {
            android.os.HwBlob hwBlob = new android.os.HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 8);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
            hwBlob.putInt8(0 + j, this.hidl_d);
            switch (this.hidl_d) {
                case 0:
                    noinit().writeEmbeddedToBlob(hwBlob, j + 4);
                    return;
                case 1:
                    hwBlob.putInt32(j + 4, value());
                    return;
                default:
                    throw new java.lang.Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }
    }

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.QosFilter.class) {
            return false;
        }
        android.hardware.radio.V1_6.QosFilter qosFilter = (android.hardware.radio.V1_6.QosFilter) obj;
        if (android.os.HidlSupport.deepEquals(this.localAddresses, qosFilter.localAddresses) && android.os.HidlSupport.deepEquals(this.remoteAddresses, qosFilter.remoteAddresses) && android.os.HidlSupport.deepEquals(this.localPort, qosFilter.localPort) && android.os.HidlSupport.deepEquals(this.remotePort, qosFilter.remotePort) && this.protocol == qosFilter.protocol && android.os.HidlSupport.deepEquals(this.tos, qosFilter.tos) && android.os.HidlSupport.deepEquals(this.flowLabel, qosFilter.flowLabel) && android.os.HidlSupport.deepEquals(this.spi, qosFilter.spi) && this.direction == qosFilter.direction && this.precedence == qosFilter.precedence) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.localAddresses)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.remoteAddresses)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.localPort)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.remotePort)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.protocol))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.tos)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.flowLabel)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.spi)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.direction))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.precedence))));
    }

    public final java.lang.String toString() {
        return "{.localAddresses = " + this.localAddresses + ", .remoteAddresses = " + this.remoteAddresses + ", .localPort = " + this.localPort + ", .remotePort = " + this.remotePort + ", .protocol = " + android.hardware.radio.V1_6.QosProtocol.toString(this.protocol) + ", .tos = " + this.tos + ", .flowLabel = " + this.flowLabel + ", .spi = " + this.spi + ", .direction = " + android.hardware.radio.V1_6.QosFilterDirection.toString(this.direction) + ", .precedence = " + this.precedence + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.QosFilter> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.QosFilter> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.QosFilter qosFilter = new android.hardware.radio.V1_6.QosFilter();
            qosFilter.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            arrayList.add(qosFilter);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2 + 0, true);
        this.localAddresses.clear();
        int i = 0;
        while (i < int32) {
            new java.lang.String();
            java.lang.String string = readEmbeddedBuffer.getString(i * 16);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer.handle(), r1 + 0, false);
            this.localAddresses.add(string);
            i++;
            readEmbeddedBuffer = readEmbeddedBuffer;
        }
        long j3 = j + 16;
        int int322 = hwBlob.getInt32(j3 + 8);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), 0 + j3, true);
        this.remoteAddresses.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            new java.lang.String();
            java.lang.String string2 = readEmbeddedBuffer2.getString(i2 * 16);
            hwParcel.readEmbeddedBuffer(string2.getBytes().length + 1, readEmbeddedBuffer2.handle(), r1 + 0, false);
            this.remoteAddresses.add(string2);
        }
        this.localPort.readEmbeddedFromParcel(hwParcel, hwBlob, j + 32);
        this.remotePort.readEmbeddedFromParcel(hwParcel, hwBlob, j + 44);
        this.protocol = hwBlob.getInt8(j + 56);
        this.tos.readEmbeddedFromParcel(hwParcel, hwBlob, j + 57);
        this.flowLabel.readEmbeddedFromParcel(hwParcel, hwBlob, j + 60);
        this.spi.readEmbeddedFromParcel(hwParcel, hwBlob, j + 68);
        this.direction = hwBlob.getInt8(j + 76);
        this.precedence = hwBlob.getInt32(j + 80);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.QosFilter> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 88);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        int size = this.localAddresses.size();
        long j2 = j + 0;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            hwBlob2.putString(i * 16, this.localAddresses.get(i));
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.remoteAddresses.size();
        long j3 = j + 16;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(12 + j3, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putString(i2 * 16, this.remoteAddresses.get(i2));
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
        this.localPort.writeEmbeddedToBlob(hwBlob, j + 32);
        this.remotePort.writeEmbeddedToBlob(hwBlob, j + 44);
        hwBlob.putInt8(j + 56, this.protocol);
        this.tos.writeEmbeddedToBlob(hwBlob, j + 57);
        this.flowLabel.writeEmbeddedToBlob(hwBlob, j + 60);
        this.spi.writeEmbeddedToBlob(hwBlob, j + 68);
        hwBlob.putInt8(j + 76, this.direction);
        hwBlob.putInt32(j + 80, this.precedence);
    }
}
