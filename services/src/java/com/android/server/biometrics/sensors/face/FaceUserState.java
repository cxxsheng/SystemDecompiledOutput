package com.android.server.biometrics.sensors.face;

/* loaded from: classes.dex */
public class FaceUserState extends com.android.server.biometrics.sensors.BiometricUserState<android.hardware.face.Face> {
    private static final java.lang.String ATTR_DEVICE_ID = "deviceId";
    private static final java.lang.String ATTR_FACE_ID = "faceId";
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String TAG = "FaceState";
    private static final java.lang.String TAG_FACE = "face";
    private static final java.lang.String TAG_FACES = "faces";

    public FaceUserState(android.content.Context context, int i, java.lang.String str) {
        super(context, i, str);
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    protected java.lang.String getBiometricsTag() {
        return TAG_FACES;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    protected int getNameTemplateResource() {
        return android.R.string.face_app_setting_name;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    protected java.util.ArrayList<android.hardware.face.Face> getCopy(java.util.ArrayList<android.hardware.face.Face> arrayList) {
        java.util.ArrayList<android.hardware.face.Face> arrayList2 = new java.util.ArrayList<>();
        java.util.Iterator<android.hardware.face.Face> it = arrayList.iterator();
        while (it.hasNext()) {
            android.hardware.face.Face next = it.next();
            arrayList2.add(new android.hardware.face.Face(next.getName(), next.getBiometricId(), next.getDeviceId()));
        }
        return arrayList2;
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    protected void doWriteState(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.lang.Exception {
        java.util.ArrayList<android.hardware.face.Face> copy;
        synchronized (this) {
            copy = getCopy(this.mBiometrics);
        }
        typedXmlSerializer.startTag((java.lang.String) null, TAG_FACES);
        int size = copy.size();
        for (int i = 0; i < size; i++) {
            android.hardware.face.Face face = copy.get(i);
            typedXmlSerializer.startTag((java.lang.String) null, TAG_FACE);
            typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_FACE_ID, face.getBiometricId());
            typedXmlSerializer.attribute((java.lang.String) null, "name", face.getName().toString());
            typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_DEVICE_ID, face.getDeviceId());
            typedXmlSerializer.endTag((java.lang.String) null, TAG_FACE);
        }
        typedXmlSerializer.endTag((java.lang.String) null, TAG_FACES);
    }

    @Override // com.android.server.biometrics.sensors.BiometricUserState
    @com.android.internal.annotations.GuardedBy({"this"})
    protected void parseBiometricsLocked(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next != 3 || typedXmlPullParser.getDepth() > depth) {
                    if (next != 3 && next != 4 && typedXmlPullParser.getName().equals(TAG_FACE)) {
                        this.mBiometrics.add(new android.hardware.face.Face(typedXmlPullParser.getAttributeValue((java.lang.String) null, "name"), typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_FACE_ID), typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_DEVICE_ID)));
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
