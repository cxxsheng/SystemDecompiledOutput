package com.android.server.location.altitude;

/* loaded from: classes2.dex */
public class AltitudeService extends android.frameworks.location.altitude.IAltitudeService.Stub {
    private final android.location.altitude.AltitudeConverter mAltitudeConverter = new android.location.altitude.AltitudeConverter();
    private final android.content.Context mContext;

    public AltitudeService(android.content.Context context) {
        this.mContext = context;
    }

    public android.frameworks.location.altitude.AddMslAltitudeToLocationResponse addMslAltitudeToLocation(android.frameworks.location.altitude.AddMslAltitudeToLocationRequest addMslAltitudeToLocationRequest) throws android.os.RemoteException {
        android.location.Location location = new android.location.Location("");
        location.setLatitude(addMslAltitudeToLocationRequest.latitudeDegrees);
        location.setLongitude(addMslAltitudeToLocationRequest.longitudeDegrees);
        location.setAltitude(addMslAltitudeToLocationRequest.altitudeMeters);
        location.setVerticalAccuracyMeters(addMslAltitudeToLocationRequest.verticalAccuracyMeters);
        android.frameworks.location.altitude.AddMslAltitudeToLocationResponse addMslAltitudeToLocationResponse = new android.frameworks.location.altitude.AddMslAltitudeToLocationResponse();
        try {
            this.mAltitudeConverter.addMslAltitudeToLocation(this.mContext, location);
            addMslAltitudeToLocationResponse.mslAltitudeMeters = location.getMslAltitudeMeters();
            addMslAltitudeToLocationResponse.mslAltitudeAccuracyMeters = location.getMslAltitudeAccuracyMeters();
            addMslAltitudeToLocationResponse.success = true;
            return addMslAltitudeToLocationResponse;
        } catch (java.io.IOException e) {
            addMslAltitudeToLocationResponse.success = false;
            return addMslAltitudeToLocationResponse;
        }
    }

    public android.frameworks.location.altitude.GetGeoidHeightResponse getGeoidHeight(android.frameworks.location.altitude.GetGeoidHeightRequest getGeoidHeightRequest) throws android.os.RemoteException {
        try {
            return this.mAltitudeConverter.getGeoidHeight(this.mContext, getGeoidHeightRequest);
        } catch (java.io.IOException e) {
            android.frameworks.location.altitude.GetGeoidHeightResponse getGeoidHeightResponse = new android.frameworks.location.altitude.GetGeoidHeightResponse();
            getGeoidHeightResponse.success = false;
            return getGeoidHeightResponse;
        }
    }

    public java.lang.String getInterfaceHash() {
        return "e47d23f579ff7a897fb03e7e7f1c3006cfc6036b";
    }

    public int getInterfaceVersion() {
        return 2;
    }

    public static class Lifecycle extends com.android.server.SystemService {
        private static final java.lang.String SERVICE_NAME = android.frameworks.location.altitude.IAltitudeService.DESCRIPTOR + "/default";
        private com.android.server.location.altitude.AltitudeService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mService = new com.android.server.location.altitude.AltitudeService(getContext());
            publishBinderService(SERVICE_NAME, this.mService);
        }
    }
}
