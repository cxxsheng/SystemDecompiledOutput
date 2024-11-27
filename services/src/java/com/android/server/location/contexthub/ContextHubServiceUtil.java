package com.android.server.location.contexthub;

/* loaded from: classes2.dex */
class ContextHubServiceUtil {
    private static final java.lang.String CONTEXT_HUB_PERMISSION = "android.permission.ACCESS_CONTEXT_HUB";
    private static final java.lang.String DATE_FORMAT = "MM/dd HH:mm:ss.SSS";
    private static final java.time.format.DateTimeFormatter DATE_FORMATTER = java.time.format.DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(java.time.ZoneId.systemDefault());
    private static final char HOST_ENDPOINT_BROADCAST = 65535;
    private static final java.lang.String TAG = "ContextHubServiceUtil";

    ContextHubServiceUtil() {
    }

    static java.util.HashMap<java.lang.Integer, android.hardware.location.ContextHubInfo> createContextHubInfoMap(java.util.List<android.hardware.location.ContextHubInfo> list) {
        java.util.HashMap<java.lang.Integer, android.hardware.location.ContextHubInfo> hashMap = new java.util.HashMap<>();
        for (android.hardware.location.ContextHubInfo contextHubInfo : list) {
            hashMap.put(java.lang.Integer.valueOf(contextHubInfo.getId()), contextHubInfo);
        }
        return hashMap;
    }

    static void copyToByteArrayList(byte[] bArr, java.util.ArrayList<java.lang.Byte> arrayList) {
        arrayList.clear();
        arrayList.ensureCapacity(bArr.length);
        for (byte b : bArr) {
            arrayList.add(java.lang.Byte.valueOf(b));
        }
    }

    static byte[] createPrimitiveByteArray(java.util.ArrayList<java.lang.Byte> arrayList) {
        byte[] bArr = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            bArr[i] = arrayList.get(i).byteValue();
        }
        return bArr;
    }

    static int[] createPrimitiveIntArray(java.util.Collection<java.lang.Integer> collection) {
        int[] iArr = new int[collection.size()];
        java.util.Iterator<java.lang.Integer> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = it.next().intValue();
            i++;
        }
        return iArr;
    }

    static android.hardware.contexthub.V1_0.NanoAppBinary createHidlNanoAppBinary(android.hardware.location.NanoAppBinary nanoAppBinary) {
        android.hardware.contexthub.V1_0.NanoAppBinary nanoAppBinary2 = new android.hardware.contexthub.V1_0.NanoAppBinary();
        nanoAppBinary2.appId = nanoAppBinary.getNanoAppId();
        nanoAppBinary2.appVersion = nanoAppBinary.getNanoAppVersion();
        nanoAppBinary2.flags = nanoAppBinary.getFlags();
        nanoAppBinary2.targetChreApiMajorVersion = nanoAppBinary.getTargetChreApiMajorVersion();
        nanoAppBinary2.targetChreApiMinorVersion = nanoAppBinary.getTargetChreApiMinorVersion();
        try {
            copyToByteArrayList(nanoAppBinary.getBinaryNoHeader(), nanoAppBinary2.customBinary);
        } catch (java.lang.IndexOutOfBoundsException e) {
            android.util.Log.w(TAG, e.getMessage());
        } catch (java.lang.NullPointerException e2) {
            android.util.Log.w(TAG, "NanoApp binary was null");
        }
        return nanoAppBinary2;
    }

    static android.hardware.contexthub.NanoappBinary createAidlNanoAppBinary(android.hardware.location.NanoAppBinary nanoAppBinary) {
        android.hardware.contexthub.NanoappBinary nanoappBinary = new android.hardware.contexthub.NanoappBinary();
        nanoappBinary.nanoappId = nanoAppBinary.getNanoAppId();
        nanoappBinary.nanoappVersion = nanoAppBinary.getNanoAppVersion();
        nanoappBinary.flags = nanoAppBinary.getFlags();
        nanoappBinary.targetChreApiMajorVersion = nanoAppBinary.getTargetChreApiMajorVersion();
        nanoappBinary.targetChreApiMinorVersion = nanoAppBinary.getTargetChreApiMinorVersion();
        nanoappBinary.customBinary = new byte[0];
        try {
            nanoappBinary.customBinary = nanoAppBinary.getBinaryNoHeader();
        } catch (java.lang.IndexOutOfBoundsException e) {
            android.util.Log.w(TAG, e.getMessage());
        } catch (java.lang.NullPointerException e2) {
            android.util.Log.w(TAG, "NanoApp binary was null");
        }
        return nanoappBinary;
    }

    static java.util.List<android.hardware.location.NanoAppState> createNanoAppStateList(java.util.List<android.hardware.contexthub.V1_2.HubAppInfo> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.hardware.contexthub.V1_2.HubAppInfo hubAppInfo : list) {
            arrayList.add(new android.hardware.location.NanoAppState(hubAppInfo.info_1_0.appId, hubAppInfo.info_1_0.version, hubAppInfo.info_1_0.enabled, hubAppInfo.permissions));
        }
        return arrayList;
    }

    static java.util.List<android.hardware.location.NanoAppState> createNanoAppStateList(android.hardware.contexthub.NanoappInfo[] nanoappInfoArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.hardware.contexthub.NanoappInfo nanoappInfo : nanoappInfoArr) {
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            for (android.hardware.contexthub.NanoappRpcService nanoappRpcService : nanoappInfo.rpcServices) {
                arrayList2.add(new android.hardware.location.NanoAppRpcService(nanoappRpcService.id, nanoappRpcService.version));
            }
            arrayList.add(new android.hardware.location.NanoAppState(nanoappInfo.nanoappId, nanoappInfo.nanoappVersion, nanoappInfo.enabled, new java.util.ArrayList(java.util.Arrays.asList(nanoappInfo.permissions)), arrayList2));
        }
        return arrayList;
    }

    static android.hardware.contexthub.V1_0.ContextHubMsg createHidlContextHubMessage(short s, android.hardware.location.NanoAppMessage nanoAppMessage) {
        android.hardware.contexthub.V1_0.ContextHubMsg contextHubMsg = new android.hardware.contexthub.V1_0.ContextHubMsg();
        contextHubMsg.appName = nanoAppMessage.getNanoAppId();
        contextHubMsg.hostEndPoint = s;
        contextHubMsg.msgType = nanoAppMessage.getMessageType();
        copyToByteArrayList(nanoAppMessage.getMessageBody(), contextHubMsg.msg);
        return contextHubMsg;
    }

    static android.hardware.contexthub.ContextHubMessage createAidlContextHubMessage(short s, android.hardware.location.NanoAppMessage nanoAppMessage) {
        android.hardware.contexthub.ContextHubMessage contextHubMessage = new android.hardware.contexthub.ContextHubMessage();
        contextHubMessage.nanoappId = nanoAppMessage.getNanoAppId();
        contextHubMessage.hostEndPoint = (char) s;
        contextHubMessage.messageType = nanoAppMessage.getMessageType();
        contextHubMessage.messageBody = nanoAppMessage.getMessageBody();
        contextHubMessage.permissions = new java.lang.String[0];
        contextHubMessage.isReliable = nanoAppMessage.isReliable();
        contextHubMessage.messageSequenceNumber = nanoAppMessage.getMessageSequenceNumber();
        return contextHubMessage;
    }

    static android.hardware.location.NanoAppMessage createNanoAppMessage(android.hardware.contexthub.V1_0.ContextHubMsg contextHubMsg) {
        return android.hardware.location.NanoAppMessage.createMessageFromNanoApp(contextHubMsg.appName, contextHubMsg.msgType, createPrimitiveByteArray(contextHubMsg.msg), contextHubMsg.hostEndPoint == -1);
    }

    static android.hardware.location.NanoAppMessage createNanoAppMessage(android.hardware.contexthub.ContextHubMessage contextHubMessage) {
        return android.hardware.location.NanoAppMessage.createMessageFromNanoApp(contextHubMessage.nanoappId, contextHubMessage.messageType, contextHubMessage.messageBody, contextHubMessage.hostEndPoint == 65535, contextHubMessage.isReliable, contextHubMessage.messageSequenceNumber);
    }

    static void checkPermissions(android.content.Context context) {
        context.enforceCallingOrSelfPermission(CONTEXT_HUB_PERMISSION, "ACCESS_CONTEXT_HUB permission required to use Context Hub");
    }

    static int toTransactionResult(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
            case 4:
            default:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 5:
                return 4;
        }
    }

    static java.util.ArrayList<android.hardware.contexthub.V1_2.HubAppInfo> toHubAppInfo_1_2(java.util.ArrayList<android.hardware.contexthub.V1_0.HubAppInfo> arrayList) {
        java.util.ArrayList<android.hardware.contexthub.V1_2.HubAppInfo> arrayList2 = new java.util.ArrayList<>();
        java.util.Iterator<android.hardware.contexthub.V1_0.HubAppInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            android.hardware.contexthub.V1_0.HubAppInfo next = it.next();
            android.hardware.contexthub.V1_2.HubAppInfo hubAppInfo = new android.hardware.contexthub.V1_2.HubAppInfo();
            hubAppInfo.info_1_0.appId = next.appId;
            hubAppInfo.info_1_0.version = next.version;
            hubAppInfo.info_1_0.memUsage = next.memUsage;
            hubAppInfo.info_1_0.enabled = next.enabled;
            hubAppInfo.permissions = new java.util.ArrayList();
            arrayList2.add(hubAppInfo);
        }
        return arrayList2;
    }

    static int toContextHubEvent(int i) {
        switch (i) {
            case 1:
                return 1;
            default:
                android.util.Log.e(TAG, "toContextHubEvent: Unknown event type: " + i);
                return 0;
        }
    }

    static int toContextHubEventFromAidl(int i) {
        switch (i) {
            case 1:
                return 1;
            default:
                android.util.Log.e(TAG, "toContextHubEventFromAidl: Unknown event type: " + i);
                return 0;
        }
    }

    static java.lang.String formatDateFromTimestamp(long j) {
        return DATE_FORMATTER.format(java.time.Instant.ofEpochMilli(j));
    }
}
