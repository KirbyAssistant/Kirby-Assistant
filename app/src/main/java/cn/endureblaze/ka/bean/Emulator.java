package cn.endureblaze.ka.bean;

public class Emulator {
        private Integer list;
        private Integer version;
        private String EmulatorName;
        private String EmulatorImageUrl;
        private String EmulatorTag;

        public Emulator(Integer list, Integer version,String EmulatorName, String EmulatorImageUrl, String EmulatorTag)
        {
            this.list = list;
            this.version = version;
            this.EmulatorName = EmulatorName;
            this.EmulatorImageUrl = EmulatorImageUrl;
            this.EmulatorTag=EmulatorTag;
        }
        public Integer getList(){return list;}
        public Integer getVersion(){return version;}
        public String getEmulatorName()
        {
            return EmulatorName;
        }
        public String getEmulatorImageUrl()
        {
            return EmulatorImageUrl;
        }
        public String getEmulatorTag() { return EmulatorTag; }
}
