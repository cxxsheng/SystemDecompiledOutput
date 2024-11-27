package com.android.server.powerstats;

/* loaded from: classes2.dex */
public class ProtoStreamUtils {
    private static final java.lang.String TAG = com.android.server.powerstats.ProtoStreamUtils.class.getSimpleName();

    static class PowerEntityUtils {
        PowerEntityUtils() {
        }

        public static byte[] getProtoBytes(android.hardware.power.stats.PowerEntity[] powerEntityArr) {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
            packProtoMessage(powerEntityArr, protoOutputStream);
            return protoOutputStream.getBytes();
        }

        public static void packProtoMessage(android.hardware.power.stats.PowerEntity[] powerEntityArr, android.util.proto.ProtoOutputStream protoOutputStream) {
            if (powerEntityArr == null) {
                return;
            }
            for (int i = 0; i < powerEntityArr.length; i++) {
                long start = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1120986464257L, powerEntityArr[i].id);
                protoOutputStream.write(1138166333442L, powerEntityArr[i].name);
                if (powerEntityArr[i].states != null) {
                    int length = powerEntityArr[i].states.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        android.hardware.power.stats.State state = powerEntityArr[i].states[i2];
                        long start2 = protoOutputStream.start(2246267895811L);
                        protoOutputStream.write(1120986464257L, state.id);
                        protoOutputStream.write(1138166333442L, state.name);
                        protoOutputStream.end(start2);
                    }
                }
                protoOutputStream.end(start);
            }
        }

        public static void print(android.hardware.power.stats.PowerEntity[] powerEntityArr) {
            if (powerEntityArr == null) {
                return;
            }
            for (int i = 0; i < powerEntityArr.length; i++) {
                android.util.Slog.d(com.android.server.powerstats.ProtoStreamUtils.TAG, "powerEntityId: " + powerEntityArr[i].id + ", powerEntityName: " + powerEntityArr[i].name);
                if (powerEntityArr[i].states != null) {
                    for (int i2 = 0; i2 < powerEntityArr[i].states.length; i2++) {
                        android.util.Slog.d(com.android.server.powerstats.ProtoStreamUtils.TAG, "  StateId: " + powerEntityArr[i].states[i2].id + ", StateName: " + powerEntityArr[i].states[i2].name);
                    }
                }
            }
        }

        public static void dumpsys(android.hardware.power.stats.PowerEntity[] powerEntityArr, java.io.PrintWriter printWriter) {
            if (powerEntityArr == null) {
                return;
            }
            for (int i = 0; i < powerEntityArr.length; i++) {
                printWriter.println("PowerEntityId: " + powerEntityArr[i].id + ", PowerEntityName: " + powerEntityArr[i].name);
                if (powerEntityArr[i].states != null) {
                    for (int i2 = 0; i2 < powerEntityArr[i].states.length; i2++) {
                        printWriter.println("  StateId: " + powerEntityArr[i].states[i2].id + ", StateName: " + powerEntityArr[i].states[i2].name);
                    }
                }
            }
        }
    }

    static class StateResidencyResultUtils {
        StateResidencyResultUtils() {
        }

        public static void adjustTimeSinceBootToEpoch(android.hardware.power.stats.StateResidencyResult[] stateResidencyResultArr, long j) {
            if (stateResidencyResultArr == null) {
                return;
            }
            for (int i = 0; i < stateResidencyResultArr.length; i++) {
                int length = stateResidencyResultArr[i].stateResidencyData.length;
                for (int i2 = 0; i2 < length; i2++) {
                    stateResidencyResultArr[i].stateResidencyData[i2].lastEntryTimestampMs += j;
                }
            }
        }

        public static byte[] getProtoBytes(android.hardware.power.stats.StateResidencyResult[] stateResidencyResultArr) {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
            packProtoMessage(stateResidencyResultArr, protoOutputStream);
            return protoOutputStream.getBytes();
        }

        public static void packProtoMessage(android.hardware.power.stats.StateResidencyResult[] stateResidencyResultArr, android.util.proto.ProtoOutputStream protoOutputStream) {
            if (stateResidencyResultArr == null) {
                return;
            }
            for (int i = 0; i < stateResidencyResultArr.length; i++) {
                int length = stateResidencyResultArr[i].stateResidencyData.length;
                long j = 2246267895810L;
                long start = protoOutputStream.start(2246267895810L);
                long j2 = 1120986464257L;
                protoOutputStream.write(1120986464257L, stateResidencyResultArr[i].id);
                int i2 = 0;
                while (i2 < length) {
                    android.hardware.power.stats.StateResidency stateResidency = stateResidencyResultArr[i].stateResidencyData[i2];
                    long start2 = protoOutputStream.start(j);
                    protoOutputStream.write(j2, stateResidency.id);
                    protoOutputStream.write(1112396529666L, stateResidency.totalTimeInStateMs);
                    protoOutputStream.write(1112396529667L, stateResidency.totalStateEntryCount);
                    protoOutputStream.write(1112396529668L, stateResidency.lastEntryTimestampMs);
                    protoOutputStream.end(start2);
                    i2++;
                    j = 2246267895810L;
                    j2 = 1120986464257L;
                }
                protoOutputStream.end(start);
            }
        }

        public static android.hardware.power.stats.StateResidencyResult[] unpackProtoMessage(byte[] bArr) throws java.io.IOException {
            android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(new java.io.ByteArrayInputStream(bArr));
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (true) {
                try {
                    int nextField = protoInputStream.nextField();
                    new android.hardware.power.stats.StateResidencyResult();
                    if (nextField == 2) {
                        long start = protoInputStream.start(2246267895810L);
                        arrayList.add(unpackStateResidencyResultProto(protoInputStream));
                        protoInputStream.end(start);
                    } else {
                        if (nextField == -1) {
                            return (android.hardware.power.stats.StateResidencyResult[]) arrayList.toArray(new android.hardware.power.stats.StateResidencyResult[arrayList.size()]);
                        }
                        android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Unhandled field in PowerStatsServiceResidencyProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                    }
                } catch (android.util.proto.WireTypeMismatchException e) {
                    android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in PowerStatsServiceResidencyProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x0051, code lost:
        
            r0.stateResidencyData = (android.hardware.power.stats.StateResidency[]) r1.toArray(new android.hardware.power.stats.StateResidency[r1.size()]);
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x0060, code lost:
        
            return r0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static android.hardware.power.stats.StateResidencyResult unpackStateResidencyResultProto(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
            android.hardware.power.stats.StateResidencyResult stateResidencyResult = new android.hardware.power.stats.StateResidencyResult();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (true) {
                try {
                } catch (android.util.proto.WireTypeMismatchException e) {
                    android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in StateResidencyResultProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                }
                switch (protoInputStream.nextField()) {
                    case -1:
                        break;
                    case 0:
                    default:
                        android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Unhandled field in StateResidencyResultProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                        continue;
                    case 1:
                        stateResidencyResult.id = protoInputStream.readInt(1120986464257L);
                        continue;
                    case 2:
                        long start = protoInputStream.start(2246267895810L);
                        arrayList.add(unpackStateResidencyProto(protoInputStream));
                        protoInputStream.end(start);
                        continue;
                }
                android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in StateResidencyResultProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x005f, code lost:
        
            return r0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static android.hardware.power.stats.StateResidency unpackStateResidencyProto(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
            android.hardware.power.stats.StateResidency stateResidency = new android.hardware.power.stats.StateResidency();
            while (true) {
                try {
                } catch (android.util.proto.WireTypeMismatchException e) {
                    android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in StateResidencyProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                }
                switch (protoInputStream.nextField()) {
                    case -1:
                        break;
                    case 0:
                    default:
                        android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Unhandled field in StateResidencyProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                        continue;
                    case 1:
                        stateResidency.id = protoInputStream.readInt(1120986464257L);
                        continue;
                    case 2:
                        stateResidency.totalTimeInStateMs = protoInputStream.readLong(1112396529666L);
                        continue;
                    case 3:
                        stateResidency.totalStateEntryCount = protoInputStream.readLong(1112396529667L);
                        continue;
                    case 4:
                        stateResidency.lastEntryTimestampMs = protoInputStream.readLong(1112396529668L);
                        continue;
                }
                android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in StateResidencyProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
            }
        }

        public static void print(android.hardware.power.stats.StateResidencyResult[] stateResidencyResultArr) {
            if (stateResidencyResultArr == null) {
                return;
            }
            for (int i = 0; i < stateResidencyResultArr.length; i++) {
                android.util.Slog.d(com.android.server.powerstats.ProtoStreamUtils.TAG, "PowerEntityId: " + stateResidencyResultArr[i].id);
                for (int i2 = 0; i2 < stateResidencyResultArr[i].stateResidencyData.length; i2++) {
                    android.util.Slog.d(com.android.server.powerstats.ProtoStreamUtils.TAG, "  StateId: " + stateResidencyResultArr[i].stateResidencyData[i2].id + ", TotalTimeInStateMs: " + stateResidencyResultArr[i].stateResidencyData[i2].totalTimeInStateMs + ", TotalStateEntryCount: " + stateResidencyResultArr[i].stateResidencyData[i2].totalStateEntryCount + ", LastEntryTimestampMs: " + stateResidencyResultArr[i].stateResidencyData[i2].lastEntryTimestampMs);
                }
            }
        }
    }

    static class ChannelUtils {
        ChannelUtils() {
        }

        public static byte[] getProtoBytes(android.hardware.power.stats.Channel[] channelArr) {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
            packProtoMessage(channelArr, protoOutputStream);
            return protoOutputStream.getBytes();
        }

        public static void packProtoMessage(android.hardware.power.stats.Channel[] channelArr, android.util.proto.ProtoOutputStream protoOutputStream) {
            if (channelArr == null) {
                return;
            }
            for (int i = 0; i < channelArr.length; i++) {
                long start = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1120986464257L, channelArr[i].id);
                protoOutputStream.write(1138166333442L, channelArr[i].name);
                protoOutputStream.write(1138166333443L, channelArr[i].subsystem);
                protoOutputStream.end(start);
            }
        }

        public static void print(android.hardware.power.stats.Channel[] channelArr) {
            if (channelArr == null) {
                return;
            }
            for (int i = 0; i < channelArr.length; i++) {
                android.util.Slog.d(com.android.server.powerstats.ProtoStreamUtils.TAG, "ChannelId: " + channelArr[i].id + ", ChannelName: " + channelArr[i].name + ", ChannelSubsystem: " + channelArr[i].subsystem);
            }
        }

        public static void dumpsys(android.hardware.power.stats.Channel[] channelArr, java.io.PrintWriter printWriter) {
            if (channelArr == null) {
                return;
            }
            for (int i = 0; i < channelArr.length; i++) {
                printWriter.println("ChannelId: " + channelArr[i].id + ", ChannelName: " + channelArr[i].name + ", ChannelSubsystem: " + channelArr[i].subsystem);
            }
        }
    }

    static class EnergyMeasurementUtils {
        EnergyMeasurementUtils() {
        }

        public static void adjustTimeSinceBootToEpoch(android.hardware.power.stats.EnergyMeasurement[] energyMeasurementArr, long j) {
            if (energyMeasurementArr == null) {
                return;
            }
            for (android.hardware.power.stats.EnergyMeasurement energyMeasurement : energyMeasurementArr) {
                energyMeasurement.timestampMs += j;
            }
        }

        public static byte[] getProtoBytes(android.hardware.power.stats.EnergyMeasurement[] energyMeasurementArr) {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
            packProtoMessage(energyMeasurementArr, protoOutputStream);
            return protoOutputStream.getBytes();
        }

        public static void packProtoMessage(android.hardware.power.stats.EnergyMeasurement[] energyMeasurementArr, android.util.proto.ProtoOutputStream protoOutputStream) {
            if (energyMeasurementArr == null) {
                return;
            }
            for (int i = 0; i < energyMeasurementArr.length; i++) {
                long start = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1120986464257L, energyMeasurementArr[i].id);
                protoOutputStream.write(1112396529666L, energyMeasurementArr[i].timestampMs);
                protoOutputStream.write(1112396529668L, energyMeasurementArr[i].durationMs);
                protoOutputStream.write(1112396529667L, energyMeasurementArr[i].energyUWs);
                protoOutputStream.end(start);
            }
        }

        public static android.hardware.power.stats.EnergyMeasurement[] unpackProtoMessage(byte[] bArr) throws java.io.IOException {
            android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(new java.io.ByteArrayInputStream(bArr));
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (true) {
                try {
                    int nextField = protoInputStream.nextField();
                    new android.hardware.power.stats.EnergyMeasurement();
                    if (nextField == 2) {
                        long start = protoInputStream.start(2246267895810L);
                        arrayList.add(unpackEnergyMeasurementProto(protoInputStream));
                        protoInputStream.end(start);
                    } else {
                        if (nextField == -1) {
                            return (android.hardware.power.stats.EnergyMeasurement[]) arrayList.toArray(new android.hardware.power.stats.EnergyMeasurement[arrayList.size()]);
                        }
                        android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Unhandled field in proto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                    }
                } catch (android.util.proto.WireTypeMismatchException e) {
                    android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in proto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x0060, code lost:
        
            return r0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static android.hardware.power.stats.EnergyMeasurement unpackEnergyMeasurementProto(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
            android.hardware.power.stats.EnergyMeasurement energyMeasurement = new android.hardware.power.stats.EnergyMeasurement();
            while (true) {
                try {
                } catch (android.util.proto.WireTypeMismatchException e) {
                    android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in EnergyMeasurementProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                }
                switch (protoInputStream.nextField()) {
                    case -1:
                        break;
                    case 0:
                    default:
                        android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Unhandled field in EnergyMeasurementProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                        continue;
                    case 1:
                        energyMeasurement.id = protoInputStream.readInt(1120986464257L);
                        continue;
                    case 2:
                        energyMeasurement.timestampMs = protoInputStream.readLong(1112396529666L);
                        continue;
                    case 3:
                        energyMeasurement.energyUWs = protoInputStream.readLong(1112396529667L);
                        continue;
                    case 4:
                        energyMeasurement.durationMs = protoInputStream.readLong(1112396529668L);
                        continue;
                }
                android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in EnergyMeasurementProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
            }
        }

        public static void print(android.hardware.power.stats.EnergyMeasurement[] energyMeasurementArr) {
            if (energyMeasurementArr == null) {
                return;
            }
            for (int i = 0; i < energyMeasurementArr.length; i++) {
                android.util.Slog.d(com.android.server.powerstats.ProtoStreamUtils.TAG, "ChannelId: " + energyMeasurementArr[i].id + ", Timestamp (ms): " + energyMeasurementArr[i].timestampMs + ", Duration (ms): " + energyMeasurementArr[i].durationMs + ", Energy (uWs): " + energyMeasurementArr[i].energyUWs);
            }
        }
    }

    static class EnergyConsumerUtils {
        EnergyConsumerUtils() {
        }

        public static byte[] getProtoBytes(android.hardware.power.stats.EnergyConsumer[] energyConsumerArr) {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
            packProtoMessage(energyConsumerArr, protoOutputStream);
            return protoOutputStream.getBytes();
        }

        public static void packProtoMessage(android.hardware.power.stats.EnergyConsumer[] energyConsumerArr, android.util.proto.ProtoOutputStream protoOutputStream) {
            if (energyConsumerArr == null) {
                return;
            }
            for (int i = 0; i < energyConsumerArr.length; i++) {
                long start = protoOutputStream.start(2246267895809L);
                protoOutputStream.write(1120986464257L, energyConsumerArr[i].id);
                protoOutputStream.write(1120986464258L, energyConsumerArr[i].ordinal);
                protoOutputStream.write(1120986464259L, (int) energyConsumerArr[i].type);
                protoOutputStream.write(1138166333444L, energyConsumerArr[i].name);
                protoOutputStream.end(start);
            }
        }

        public static android.hardware.power.stats.EnergyConsumer[] unpackProtoMessage(byte[] bArr) throws java.io.IOException {
            android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(new java.io.ByteArrayInputStream(bArr));
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (true) {
                try {
                    int nextField = protoInputStream.nextField();
                    new android.hardware.power.stats.EnergyConsumer();
                    if (nextField == 1) {
                        long start = protoInputStream.start(2246267895809L);
                        arrayList.add(unpackEnergyConsumerProto(protoInputStream));
                        protoInputStream.end(start);
                    } else {
                        if (nextField == -1) {
                            return (android.hardware.power.stats.EnergyConsumer[]) arrayList.toArray(new android.hardware.power.stats.EnergyConsumer[arrayList.size()]);
                        }
                        android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Unhandled field in proto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                    }
                } catch (android.util.proto.WireTypeMismatchException e) {
                    android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in proto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x005d, code lost:
        
            return r0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static android.hardware.power.stats.EnergyConsumer unpackEnergyConsumerProto(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
            android.hardware.power.stats.EnergyConsumer energyConsumer = new android.hardware.power.stats.EnergyConsumer();
            while (true) {
                try {
                } catch (android.util.proto.WireTypeMismatchException e) {
                    android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in EnergyConsumerProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                }
                switch (protoInputStream.nextField()) {
                    case -1:
                        break;
                    case 0:
                    default:
                        android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Unhandled field in EnergyConsumerProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                        continue;
                    case 1:
                        energyConsumer.id = protoInputStream.readInt(1120986464257L);
                        continue;
                    case 2:
                        energyConsumer.ordinal = protoInputStream.readInt(1120986464258L);
                        continue;
                    case 3:
                        energyConsumer.type = (byte) protoInputStream.readInt(1120986464259L);
                        continue;
                    case 4:
                        energyConsumer.name = protoInputStream.readString(1138166333444L);
                        continue;
                }
                android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in EnergyConsumerProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
            }
        }

        public static void print(android.hardware.power.stats.EnergyConsumer[] energyConsumerArr) {
            if (energyConsumerArr == null) {
                return;
            }
            for (int i = 0; i < energyConsumerArr.length; i++) {
                android.util.Slog.d(com.android.server.powerstats.ProtoStreamUtils.TAG, "EnergyConsumerId: " + energyConsumerArr[i].id + ", Ordinal: " + energyConsumerArr[i].ordinal + ", Type: " + ((int) energyConsumerArr[i].type) + ", Name: " + energyConsumerArr[i].name);
            }
        }

        public static void dumpsys(android.hardware.power.stats.EnergyConsumer[] energyConsumerArr, java.io.PrintWriter printWriter) {
            if (energyConsumerArr == null) {
                return;
            }
            for (int i = 0; i < energyConsumerArr.length; i++) {
                printWriter.println("EnergyConsumerId: " + energyConsumerArr[i].id + ", Ordinal: " + energyConsumerArr[i].ordinal + ", Type: " + ((int) energyConsumerArr[i].type) + ", Name: " + energyConsumerArr[i].name);
            }
        }
    }

    static class EnergyConsumerResultUtils {
        EnergyConsumerResultUtils() {
        }

        public static void adjustTimeSinceBootToEpoch(android.hardware.power.stats.EnergyConsumerResult[] energyConsumerResultArr, long j) {
            if (energyConsumerResultArr == null) {
                return;
            }
            for (android.hardware.power.stats.EnergyConsumerResult energyConsumerResult : energyConsumerResultArr) {
                energyConsumerResult.timestampMs += j;
            }
        }

        public static byte[] getProtoBytes(android.hardware.power.stats.EnergyConsumerResult[] energyConsumerResultArr, boolean z) {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
            packProtoMessage(energyConsumerResultArr, protoOutputStream, z);
            return protoOutputStream.getBytes();
        }

        public static void packProtoMessage(android.hardware.power.stats.EnergyConsumerResult[] energyConsumerResultArr, android.util.proto.ProtoOutputStream protoOutputStream, boolean z) {
            if (energyConsumerResultArr == null) {
                return;
            }
            for (int i = 0; i < energyConsumerResultArr.length; i++) {
                long start = protoOutputStream.start(2246267895810L);
                long j = 1120986464257L;
                protoOutputStream.write(1120986464257L, energyConsumerResultArr[i].id);
                protoOutputStream.write(1112396529666L, energyConsumerResultArr[i].timestampMs);
                protoOutputStream.write(1112396529667L, energyConsumerResultArr[i].energyUWs);
                if (z) {
                    int length = energyConsumerResultArr[i].attribution.length;
                    int i2 = 0;
                    while (i2 < length) {
                        android.hardware.power.stats.EnergyConsumerAttribution energyConsumerAttribution = energyConsumerResultArr[i].attribution[i2];
                        long start2 = protoOutputStream.start(2246267895812L);
                        protoOutputStream.write(j, energyConsumerAttribution.uid);
                        protoOutputStream.write(1112396529666L, energyConsumerAttribution.energyUWs);
                        protoOutputStream.end(start2);
                        i2++;
                        j = 1120986464257L;
                    }
                }
                protoOutputStream.end(start);
            }
        }

        public static android.hardware.power.stats.EnergyConsumerResult[] unpackProtoMessage(byte[] bArr) throws java.io.IOException {
            android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(new java.io.ByteArrayInputStream(bArr));
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (true) {
                try {
                    int nextField = protoInputStream.nextField();
                    new android.hardware.power.stats.EnergyConsumerResult();
                    if (nextField == 2) {
                        long start = protoInputStream.start(2246267895810L);
                        arrayList.add(unpackEnergyConsumerResultProto(protoInputStream));
                        protoInputStream.end(start);
                    } else {
                        if (nextField == -1) {
                            return (android.hardware.power.stats.EnergyConsumerResult[]) arrayList.toArray(new android.hardware.power.stats.EnergyConsumerResult[arrayList.size()]);
                        }
                        android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Unhandled field in proto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                    }
                } catch (android.util.proto.WireTypeMismatchException e) {
                    android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in proto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0046, code lost:
        
            return r0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static android.hardware.power.stats.EnergyConsumerAttribution unpackEnergyConsumerAttributionProto(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
            android.hardware.power.stats.EnergyConsumerAttribution energyConsumerAttribution = new android.hardware.power.stats.EnergyConsumerAttribution();
            while (true) {
                try {
                } catch (android.util.proto.WireTypeMismatchException e) {
                    android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in EnergyConsumerAttributionProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                }
                switch (protoInputStream.nextField()) {
                    case -1:
                        break;
                    case 0:
                    default:
                        android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Unhandled field in EnergyConsumerAttributionProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                        continue;
                    case 1:
                        energyConsumerAttribution.uid = protoInputStream.readInt(1120986464257L);
                        continue;
                    case 2:
                        energyConsumerAttribution.energyUWs = protoInputStream.readLong(1112396529666L);
                        continue;
                }
                android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in EnergyConsumerAttributionProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x006c, code lost:
        
            r0.attribution = (android.hardware.power.stats.EnergyConsumerAttribution[]) r1.toArray(new android.hardware.power.stats.EnergyConsumerAttribution[r1.size()]);
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x007b, code lost:
        
            return r0;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static android.hardware.power.stats.EnergyConsumerResult unpackEnergyConsumerResultProto(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
            android.hardware.power.stats.EnergyConsumerResult energyConsumerResult = new android.hardware.power.stats.EnergyConsumerResult();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (true) {
                try {
                } catch (android.util.proto.WireTypeMismatchException e) {
                    android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in EnergyConsumerResultProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                }
                switch (protoInputStream.nextField()) {
                    case -1:
                        break;
                    case 0:
                    default:
                        android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Unhandled field in EnergyConsumerResultProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
                        continue;
                    case 1:
                        energyConsumerResult.id = protoInputStream.readInt(1120986464257L);
                        continue;
                    case 2:
                        energyConsumerResult.timestampMs = protoInputStream.readLong(1112396529666L);
                        continue;
                    case 3:
                        energyConsumerResult.energyUWs = protoInputStream.readLong(1112396529667L);
                        continue;
                    case 4:
                        long start = protoInputStream.start(2246267895812L);
                        arrayList.add(unpackEnergyConsumerAttributionProto(protoInputStream));
                        protoInputStream.end(start);
                        continue;
                }
                android.util.Slog.e(com.android.server.powerstats.ProtoStreamUtils.TAG, "Wire Type mismatch in EnergyConsumerResultProto: " + android.util.proto.ProtoUtils.currentFieldToString(protoInputStream));
            }
        }

        public static void print(android.hardware.power.stats.EnergyConsumerResult[] energyConsumerResultArr) {
            if (energyConsumerResultArr == null) {
                return;
            }
            for (android.hardware.power.stats.EnergyConsumerResult energyConsumerResult : energyConsumerResultArr) {
                android.util.Slog.d(com.android.server.powerstats.ProtoStreamUtils.TAG, "EnergyConsumerId: " + energyConsumerResult.id + ", Timestamp (ms): " + energyConsumerResult.timestampMs + ", Energy (uWs): " + energyConsumerResult.energyUWs);
                int length = energyConsumerResult.attribution.length;
                for (int i = 0; i < length; i++) {
                    android.hardware.power.stats.EnergyConsumerAttribution energyConsumerAttribution = energyConsumerResult.attribution[i];
                    android.util.Slog.d(com.android.server.powerstats.ProtoStreamUtils.TAG, "  UID: " + energyConsumerAttribution.uid + "  Energy (uWs): " + energyConsumerAttribution.energyUWs);
                }
            }
        }
    }
}
