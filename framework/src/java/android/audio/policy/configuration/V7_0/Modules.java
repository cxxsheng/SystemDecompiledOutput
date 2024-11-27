package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class Modules {
    private java.util.List<android.audio.policy.configuration.V7_0.Modules.Module> module;

    public static class Module {
        private android.audio.policy.configuration.V7_0.AttachedDevices attachedDevices;
        private java.lang.String defaultOutputDevice;
        private android.audio.policy.configuration.V7_0.DevicePorts devicePorts;
        private android.audio.policy.configuration.V7_0.HalVersion halVersion;
        private android.audio.policy.configuration.V7_0.MixPorts mixPorts;
        private java.lang.String name;
        private android.audio.policy.configuration.V7_0.Routes routes;

        public android.audio.policy.configuration.V7_0.AttachedDevices getAttachedDevices() {
            return this.attachedDevices;
        }

        boolean hasAttachedDevices() {
            if (this.attachedDevices == null) {
                return false;
            }
            return true;
        }

        public void setAttachedDevices(android.audio.policy.configuration.V7_0.AttachedDevices attachedDevices) {
            this.attachedDevices = attachedDevices;
        }

        public java.lang.String getDefaultOutputDevice() {
            return this.defaultOutputDevice;
        }

        boolean hasDefaultOutputDevice() {
            if (this.defaultOutputDevice == null) {
                return false;
            }
            return true;
        }

        public void setDefaultOutputDevice(java.lang.String str) {
            this.defaultOutputDevice = str;
        }

        public android.audio.policy.configuration.V7_0.MixPorts getMixPorts() {
            return this.mixPorts;
        }

        boolean hasMixPorts() {
            if (this.mixPorts == null) {
                return false;
            }
            return true;
        }

        public void setMixPorts(android.audio.policy.configuration.V7_0.MixPorts mixPorts) {
            this.mixPorts = mixPorts;
        }

        public android.audio.policy.configuration.V7_0.DevicePorts getDevicePorts() {
            return this.devicePorts;
        }

        boolean hasDevicePorts() {
            if (this.devicePorts == null) {
                return false;
            }
            return true;
        }

        public void setDevicePorts(android.audio.policy.configuration.V7_0.DevicePorts devicePorts) {
            this.devicePorts = devicePorts;
        }

        public android.audio.policy.configuration.V7_0.Routes getRoutes() {
            return this.routes;
        }

        boolean hasRoutes() {
            if (this.routes == null) {
                return false;
            }
            return true;
        }

        public void setRoutes(android.audio.policy.configuration.V7_0.Routes routes) {
            this.routes = routes;
        }

        public java.lang.String getName() {
            return this.name;
        }

        boolean hasName() {
            if (this.name == null) {
                return false;
            }
            return true;
        }

        public void setName(java.lang.String str) {
            this.name = str;
        }

        public android.audio.policy.configuration.V7_0.HalVersion getHalVersion() {
            return this.halVersion;
        }

        boolean hasHalVersion() {
            if (this.halVersion == null) {
                return false;
            }
            return true;
        }

        public void setHalVersion(android.audio.policy.configuration.V7_0.HalVersion halVersion) {
            this.halVersion = halVersion;
        }

        static android.audio.policy.configuration.V7_0.Modules.Module read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
            int next;
            android.audio.policy.configuration.V7_0.Modules.Module module = new android.audio.policy.configuration.V7_0.Modules.Module();
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue != null) {
                module.setName(attributeValue);
            }
            java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "halVersion");
            if (attributeValue2 != null) {
                module.setHalVersion(android.audio.policy.configuration.V7_0.HalVersion.fromString(attributeValue2));
            }
            xmlPullParser.getDepth();
            while (true) {
                next = xmlPullParser.next();
                if (next == 1 || next == 3) {
                    break;
                }
                if (xmlPullParser.getEventType() == 2) {
                    java.lang.String name = xmlPullParser.getName();
                    if (name.equals("attachedDevices")) {
                        module.setAttachedDevices(android.audio.policy.configuration.V7_0.AttachedDevices.read(xmlPullParser));
                    } else if (name.equals("defaultOutputDevice")) {
                        module.setDefaultOutputDevice(android.audio.policy.configuration.V7_0.XmlParser.readText(xmlPullParser));
                    } else if (name.equals("mixPorts")) {
                        module.setMixPorts(android.audio.policy.configuration.V7_0.MixPorts.read(xmlPullParser));
                    } else if (name.equals("devicePorts")) {
                        module.setDevicePorts(android.audio.policy.configuration.V7_0.DevicePorts.read(xmlPullParser));
                    } else if (name.equals("routes")) {
                        module.setRoutes(android.audio.policy.configuration.V7_0.Routes.read(xmlPullParser));
                    } else {
                        android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                    }
                }
            }
            if (next != 3) {
                throw new javax.xml.datatype.DatatypeConfigurationException("Modules.Module is not closed");
            }
            return module;
        }
    }

    public java.util.List<android.audio.policy.configuration.V7_0.Modules.Module> getModule() {
        if (this.module == null) {
            this.module = new java.util.ArrayList();
        }
        return this.module;
    }

    static android.audio.policy.configuration.V7_0.Modules read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        android.audio.policy.configuration.V7_0.Modules modules = new android.audio.policy.configuration.V7_0.Modules();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("module")) {
                    modules.getModule().add(android.audio.policy.configuration.V7_0.Modules.Module.read(xmlPullParser));
                } else {
                    android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Modules is not closed");
        }
        return modules;
    }
}
