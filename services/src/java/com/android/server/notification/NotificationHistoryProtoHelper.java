package com.android.server.notification;

/* loaded from: classes2.dex */
final class NotificationHistoryProtoHelper {
    private static final java.lang.String TAG = "NotifHistoryProto";

    private NotificationHistoryProtoHelper() {
    }

    private static java.util.List<java.lang.String> readStringPool(android.util.proto.ProtoInputStream protoInputStream) throws java.io.IOException {
        java.util.ArrayList arrayList;
        long start = protoInputStream.start(1146756268033L);
        if (protoInputStream.nextField(1120986464257L)) {
            arrayList = new java.util.ArrayList(protoInputStream.readInt(1120986464257L));
        } else {
            arrayList = new java.util.ArrayList();
        }
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 2:
                    arrayList.add(protoInputStream.readString(2237677961218L));
                    break;
            }
        }
        protoInputStream.end(start);
        return arrayList;
    }

    private static void writeStringPool(android.util.proto.ProtoOutputStream protoOutputStream, android.app.NotificationHistory notificationHistory) {
        long start = protoOutputStream.start(1146756268033L);
        java.lang.String[] pooledStringsToWrite = notificationHistory.getPooledStringsToWrite();
        protoOutputStream.write(1120986464257L, pooledStringsToWrite.length);
        for (java.lang.String str : pooledStringsToWrite) {
            protoOutputStream.write(2237677961218L, str);
        }
        protoOutputStream.end(start);
    }

    private static void readNotification(android.util.proto.ProtoInputStream protoInputStream, java.util.List<java.lang.String> list, android.app.NotificationHistory notificationHistory, com.android.server.notification.NotificationHistoryFilter notificationHistoryFilter) throws java.io.IOException {
        long start = protoInputStream.start(2246267895811L);
        try {
            try {
                android.app.NotificationHistory.HistoricalNotification readNotification = readNotification(protoInputStream, list);
                if (notificationHistoryFilter.matchesPackageAndChannelFilter(readNotification) && notificationHistoryFilter.matchesCountFilter(notificationHistory)) {
                    notificationHistory.addNotificationToWrite(readNotification);
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Error reading notification", e);
            }
        } finally {
            protoInputStream.end(start);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0102, code lost:
    
        return r0.build();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.app.NotificationHistory.HistoricalNotification readNotification(android.util.proto.ProtoInputStream protoInputStream, java.util.List<java.lang.String> list) throws java.io.IOException {
        android.app.NotificationHistory.HistoricalNotification.Builder builder = new android.app.NotificationHistory.HistoricalNotification.Builder();
        java.lang.String str = null;
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    str = protoInputStream.readString(1138166333441L);
                    builder.setPackage(str);
                    list.add(str);
                    break;
                case 2:
                    str = list.get(protoInputStream.readInt(1120986464258L) - 1);
                    builder.setPackage(str);
                    break;
                case 3:
                    java.lang.String readString = protoInputStream.readString(1138166333443L);
                    builder.setChannelName(readString);
                    list.add(readString);
                    break;
                case 4:
                    builder.setChannelName(list.get(protoInputStream.readInt(1120986464260L) - 1));
                    break;
                case 5:
                    java.lang.String readString2 = protoInputStream.readString(1138166333445L);
                    builder.setChannelId(readString2);
                    list.add(readString2);
                    break;
                case 6:
                    builder.setChannelId(list.get(protoInputStream.readInt(1120986464262L) - 1));
                    break;
                case 7:
                    builder.setUid(protoInputStream.readInt(1120986464263L));
                    break;
                case 8:
                    builder.setUserId(protoInputStream.readInt(1120986464264L));
                    break;
                case 9:
                    builder.setPostedTimeMs(protoInputStream.readLong(1112396529673L));
                    break;
                case 10:
                    builder.setTitle(protoInputStream.readString(1138166333450L));
                    break;
                case 11:
                    builder.setText(protoInputStream.readString(1138166333451L));
                    break;
                case 12:
                    long start = protoInputStream.start(1146756268044L);
                    loadIcon(protoInputStream, builder, str);
                    protoInputStream.end(start);
                    break;
                case 13:
                    java.lang.String readString3 = protoInputStream.readString(1138166333453L);
                    builder.setConversationId(readString3);
                    list.add(readString3);
                    break;
                case 14:
                    builder.setConversationId(list.get(protoInputStream.readInt(1120986464270L) - 1));
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0068, code lost:
    
        if (r0 != 3) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x006a, code lost:
    
        if (r4 == null) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x006c, code lost:
    
        r10.setIcon(android.graphics.drawable.Icon.createWithData(r4, r2, r3));
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0095, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0075, code lost:
    
        if (r0 != 2) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0077, code lost:
    
        if (r1 == 0) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x007a, code lost:
    
        if (r6 == null) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x007c, code lost:
    
        r11 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x007f, code lost:
    
        r10.setIcon(android.graphics.drawable.Icon.createWithResource(r11, r1));
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0088, code lost:
    
        if (r0 != 4) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x008a, code lost:
    
        if (r5 == null) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x008c, code lost:
    
        r10.setIcon(android.graphics.drawable.Icon.createWithContentUri(r5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void loadIcon(android.util.proto.ProtoInputStream protoInputStream, android.app.NotificationHistory.HistoricalNotification.Builder builder, java.lang.String str) throws java.io.IOException {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        byte[] bArr = null;
        java.lang.String str2 = null;
        java.lang.String str3 = null;
        int i4 = 0;
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    i = protoInputStream.readInt(1159641169921L);
                    break;
                case 2:
                    protoInputStream.readString(1138166333442L);
                    break;
                case 3:
                    i4 = protoInputStream.readInt(1120986464259L);
                    break;
                case 4:
                    str3 = protoInputStream.readString(1138166333444L);
                    break;
                case 5:
                    bArr = protoInputStream.readBytes(1151051235333L);
                    break;
                case 6:
                    i3 = protoInputStream.readInt(1120986464262L);
                    break;
                case 7:
                    i2 = protoInputStream.readInt(1120986464263L);
                    break;
                case 8:
                    str2 = protoInputStream.readString(1138166333448L);
                    break;
            }
        }
    }

    private static void writeIcon(android.util.proto.ProtoOutputStream protoOutputStream, android.app.NotificationHistory.HistoricalNotification historicalNotification) {
        long start = protoOutputStream.start(1146756268044L);
        protoOutputStream.write(1159641169921L, historicalNotification.getIcon().getType());
        switch (historicalNotification.getIcon().getType()) {
            case 2:
                protoOutputStream.write(1120986464259L, historicalNotification.getIcon().getResId());
                if (!historicalNotification.getPackage().equals(historicalNotification.getIcon().getResPackage())) {
                    protoOutputStream.write(1138166333444L, historicalNotification.getIcon().getResPackage());
                    break;
                }
                break;
            case 3:
                protoOutputStream.write(1151051235333L, historicalNotification.getIcon().getDataBytes());
                protoOutputStream.write(1120986464262L, historicalNotification.getIcon().getDataLength());
                protoOutputStream.write(1120986464263L, historicalNotification.getIcon().getDataOffset());
                break;
            case 4:
                protoOutputStream.write(1138166333448L, historicalNotification.getIcon().getUriString());
                break;
        }
        protoOutputStream.end(start);
    }

    private static void writeNotification(android.util.proto.ProtoOutputStream protoOutputStream, java.lang.String[] strArr, android.app.NotificationHistory.HistoricalNotification historicalNotification) {
        long start = protoOutputStream.start(2246267895811L);
        int binarySearch = java.util.Arrays.binarySearch(strArr, historicalNotification.getPackage());
        if (binarySearch >= 0) {
            protoOutputStream.write(1120986464258L, binarySearch + 1);
        } else {
            android.util.Slog.w(TAG, "notification package name (" + historicalNotification.getPackage() + ") not found in string cache");
            protoOutputStream.write(1138166333441L, historicalNotification.getPackage());
        }
        int binarySearch2 = java.util.Arrays.binarySearch(strArr, historicalNotification.getChannelName());
        if (binarySearch2 >= 0) {
            protoOutputStream.write(1120986464260L, binarySearch2 + 1);
        } else {
            android.util.Slog.w(TAG, "notification channel name (" + historicalNotification.getChannelName() + ") not found in string cache");
            protoOutputStream.write(1138166333443L, historicalNotification.getChannelName());
        }
        int binarySearch3 = java.util.Arrays.binarySearch(strArr, historicalNotification.getChannelId());
        if (binarySearch3 >= 0) {
            protoOutputStream.write(1120986464262L, binarySearch3 + 1);
        } else {
            android.util.Slog.w(TAG, "notification channel id (" + historicalNotification.getChannelId() + ") not found in string cache");
            protoOutputStream.write(1138166333445L, historicalNotification.getChannelId());
        }
        if (!android.text.TextUtils.isEmpty(historicalNotification.getConversationId())) {
            int binarySearch4 = java.util.Arrays.binarySearch(strArr, historicalNotification.getConversationId());
            if (binarySearch4 >= 0) {
                protoOutputStream.write(1120986464270L, binarySearch4 + 1);
            } else {
                android.util.Slog.w(TAG, "notification conversation id (" + historicalNotification.getConversationId() + ") not found in string cache");
                protoOutputStream.write(1138166333453L, historicalNotification.getConversationId());
            }
        }
        protoOutputStream.write(1120986464263L, historicalNotification.getUid());
        protoOutputStream.write(1120986464264L, historicalNotification.getUserId());
        protoOutputStream.write(1112396529673L, historicalNotification.getPostedTimeMs());
        protoOutputStream.write(1138166333450L, historicalNotification.getTitle());
        protoOutputStream.write(1138166333451L, historicalNotification.getText());
        writeIcon(protoOutputStream, historicalNotification);
        protoOutputStream.end(start);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0020, code lost:
    
        if (r4.isFiltering() == false) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0022, code lost:
    
        r3.poolStringsFromNotifications();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0026, code lost:
    
        r3.addPooledStrings(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0029, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void read(java.io.InputStream inputStream, android.app.NotificationHistory notificationHistory, com.android.server.notification.NotificationHistoryFilter notificationHistoryFilter) throws java.io.IOException {
        android.util.proto.ProtoInputStream protoInputStream = new android.util.proto.ProtoInputStream(inputStream);
        java.util.List<java.lang.String> arrayList = new java.util.ArrayList<>();
        while (true) {
            switch (protoInputStream.nextField()) {
                case 1:
                    arrayList = readStringPool(protoInputStream);
                    break;
                case 3:
                    readNotification(protoInputStream, arrayList, notificationHistory, notificationHistoryFilter);
                    break;
            }
        }
    }

    public static void write(java.io.OutputStream outputStream, android.app.NotificationHistory notificationHistory, int i) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(outputStream);
        protoOutputStream.write(1120986464258L, i);
        writeStringPool(protoOutputStream, notificationHistory);
        java.util.List notificationsToWrite = notificationHistory.getNotificationsToWrite();
        int size = notificationsToWrite.size();
        for (int i2 = 0; i2 < size; i2++) {
            writeNotification(protoOutputStream, notificationHistory.getPooledStringsToWrite(), (android.app.NotificationHistory.HistoricalNotification) notificationsToWrite.get(i2));
        }
        protoOutputStream.flush();
    }
}
