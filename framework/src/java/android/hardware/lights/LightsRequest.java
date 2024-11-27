package android.hardware.lights;

/* loaded from: classes2.dex */
public final class LightsRequest {
    final java.util.List<java.lang.Integer> mLightIds;
    final java.util.List<android.hardware.lights.LightState> mLightStates;
    final java.util.Map<android.hardware.lights.Light, android.hardware.lights.LightState> mRequests;

    private LightsRequest(java.util.Map<android.hardware.lights.Light, android.hardware.lights.LightState> map) {
        this.mRequests = new java.util.HashMap();
        this.mLightIds = new java.util.ArrayList();
        this.mLightStates = new java.util.ArrayList();
        this.mRequests.putAll(map);
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mRequests.keySet());
        for (int i = 0; i < arrayList.size(); i++) {
            android.hardware.lights.Light light = (android.hardware.lights.Light) arrayList.get(i);
            this.mLightIds.add(i, java.lang.Integer.valueOf(light.getId()));
            this.mLightStates.add(i, this.mRequests.get(light));
        }
    }

    public java.util.List<java.lang.Integer> getLights() {
        return this.mLightIds;
    }

    public java.util.List<android.hardware.lights.LightState> getLightStates() {
        return this.mLightStates;
    }

    public java.util.Map<android.hardware.lights.Light, android.hardware.lights.LightState> getLightsAndStates() {
        return this.mRequests;
    }

    public static final class Builder {
        final java.util.Map<android.hardware.lights.Light, android.hardware.lights.LightState> mChanges = new java.util.HashMap();

        public android.hardware.lights.LightsRequest.Builder addLight(android.hardware.lights.Light light, android.hardware.lights.LightState lightState) {
            com.android.internal.util.Preconditions.checkNotNull(light);
            com.android.internal.util.Preconditions.checkNotNull(lightState);
            this.mChanges.put(light, lightState);
            return this;
        }

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public android.hardware.lights.LightsRequest.Builder setLight(android.hardware.lights.Light light, android.hardware.lights.LightState lightState) {
            return addLight(light, lightState);
        }

        public android.hardware.lights.LightsRequest.Builder clearLight(android.hardware.lights.Light light) {
            com.android.internal.util.Preconditions.checkNotNull(light);
            this.mChanges.put(light, null);
            return this;
        }

        public android.hardware.lights.LightsRequest build() {
            return new android.hardware.lights.LightsRequest(this.mChanges);
        }
    }
}
