package com.android.server.soundtrigger;

/* loaded from: classes2.dex */
public abstract class SoundTriggerEvent extends com.android.server.utils.EventLogger.Event {
    @Override // com.android.server.utils.EventLogger.Event
    public com.android.server.utils.EventLogger.Event printLog(int i, java.lang.String str) {
        switch (i) {
            case 0:
                android.util.Slog.i(str, eventToString());
                return this;
            case 1:
                android.util.Slog.e(str, eventToString());
                return this;
            case 2:
                android.util.Slog.w(str, eventToString());
                return this;
            default:
                android.util.Slog.v(str, eventToString());
                return this;
        }
    }

    public static class ServiceEvent extends com.android.server.soundtrigger.SoundTriggerEvent {
        private final java.lang.String mErrorString;
        private final java.lang.String mPackageName;
        private final com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent.Type mType;

        public enum Type {
            ATTACH,
            LIST_MODULE,
            DETACH
        }

        public ServiceEvent(com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent.Type type) {
            this(type, null, null);
        }

        public ServiceEvent(com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent.Type type, java.lang.String str) {
            this(type, str, null);
        }

        public ServiceEvent(com.android.server.soundtrigger.SoundTriggerEvent.ServiceEvent.Type type, java.lang.String str, java.lang.String str2) {
            this.mType = type;
            this.mPackageName = str;
            this.mErrorString = str2;
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(java.lang.String.format("%-12s", this.mType.name()));
            if (this.mErrorString != null) {
                sb.append(" ERROR: ");
                sb.append(this.mErrorString);
            }
            if (this.mPackageName != null) {
                sb.append(" for: ");
                sb.append(this.mPackageName);
            }
            return sb.toString();
        }
    }

    public static class SessionEvent extends com.android.server.soundtrigger.SoundTriggerEvent {
        private final java.lang.String mErrorString;
        private final java.util.UUID mModelUuid;
        private final com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type mType;

        public enum Type {
            START_RECOGNITION,
            STOP_RECOGNITION,
            LOAD_MODEL,
            UNLOAD_MODEL,
            UPDATE_MODEL,
            DELETE_MODEL,
            START_RECOGNITION_SERVICE,
            STOP_RECOGNITION_SERVICE,
            GET_MODEL_STATE,
            SET_PARAMETER,
            GET_MODULE_PROPERTIES,
            DETACH,
            RECOGNITION,
            RESUME,
            RESUME_FAILED,
            PAUSE,
            PAUSE_FAILED,
            RESOURCES_AVAILABLE,
            MODULE_DIED
        }

        public SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type type, java.util.UUID uuid, java.lang.String str) {
            this.mType = type;
            this.mModelUuid = uuid;
            this.mErrorString = str;
        }

        public SessionEvent(com.android.server.soundtrigger.SoundTriggerEvent.SessionEvent.Type type, java.util.UUID uuid) {
            this(type, uuid, null);
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(java.lang.String.format("%-25s", this.mType.name()));
            if (this.mErrorString != null) {
                sb.append(" ERROR: ");
                sb.append(this.mErrorString);
            }
            if (this.mModelUuid != null) {
                sb.append(" for: ");
                sb.append(this.mModelUuid);
            }
            return sb.toString();
        }
    }
}
