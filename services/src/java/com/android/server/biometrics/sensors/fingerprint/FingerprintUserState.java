package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public class FingerprintUserState extends com.android.server.biometrics.sensors.BiometricUserState<android.hardware.fingerprint.Fingerprint> {
    private static final java.lang.String ATTR_DEVICE_ID = "deviceId";
    private static final java.lang.String ATTR_FINGER_ID = "fingerId";
    private static final java.lang.String ATTR_GROUP_ID = "groupId";
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String TAG = "FingerprintState";
    private static final java.lang.String TAG_FINGERPRINT = "fingerprint";
    private static final java.lang.String TAG_FINGERPRINTS = "fingerprints";

    public FingerprintUserState(android.content.Context context, int i, java.lang.String str) {
        super(context, i, str);
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    protected java.lang.String getBiometricsTag() {
        return TAG_FINGERPRINTS;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    protected int getNameTemplateResource() {
        return android.R.string.fingerprint_acquired_try_adjusting;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    protected java.util.ArrayList<android.hardware.fingerprint.Fingerprint> getCopy(java.util.ArrayList<android.hardware.fingerprint.Fingerprint> arrayList) {
        java.util.ArrayList<android.hardware.fingerprint.Fingerprint> arrayList2 = new java.util.ArrayList<>();
        java.util.Iterator<android.hardware.fingerprint.Fingerprint> it = arrayList.iterator();
        while (it.hasNext()) {
            android.hardware.fingerprint.Fingerprint next = it.next();
            arrayList2.add(new android.hardware.fingerprint.Fingerprint(next.getName(), next.getGroupId(), next.getBiometricId(), next.getDeviceId()));
        }
        return arrayList2;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    protected void doWriteState(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.lang.Exception {
        java.util.ArrayList<android.hardware.fingerprint.Fingerprint> copy;
        synchronized (this) {
            copy = getCopy(this.mBiometrics);
        }
        typedXmlSerializer.startTag((java.lang.String) null, TAG_FINGERPRINTS);
        int size = copy.size();
        for (int i = 0; i < size; i++) {
            android.hardware.fingerprint.Fingerprint fingerprint = copy.get(i);
            typedXmlSerializer.startTag((java.lang.String) null, TAG_FINGERPRINT);
            typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_FINGER_ID, fingerprint.getBiometricId());
            typedXmlSerializer.attribute((java.lang.String) null, "name", fingerprint.getName().toString());
            typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_GROUP_ID, fingerprint.getGroupId());
            typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_DEVICE_ID, fingerprint.getDeviceId());
            typedXmlSerializer.endTag((java.lang.String) null, TAG_FINGERPRINT);
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_FINGERPRINTS);
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    @com.android.internal.annotations.GuardedBy({"this"})
    protected void parseBiometricsLocked(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next != 3 || typedXmlPullParser.getDepth() > depth) {
                    if (next != 3 && next != 4 && typedXmlPullParser.getName().equals(TAG_FINGERPRINT)) {
                        this.mBiometrics.add(new android.hardware.fingerprint.Fingerprint(typedXmlPullParser.getAttributeValue((java.lang.String) null, "name"), typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_GROUP_ID), typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_FINGER_ID), typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_DEVICE_ID)));
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }
}
