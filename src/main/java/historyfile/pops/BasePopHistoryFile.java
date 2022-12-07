package historyfile.pops;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class BasePopHistoryFile {
    //: TODO: Make province object
    protected String provinceName;
    //: TODO: Make province object
    protected int provinceId;
    protected List<PopHistoryPop> pops;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public void setProvinceId(String provinceId) {
        setProvinceId(Integer.parseInt(provinceId));
    }

    public List<PopHistoryPop> getPops() {
        return pops;
    }

    public void setPops(List<PopHistoryPop> pops) {
        this.pops = pops;
    }

    public void addPop(PopHistoryPop pop) {
        if (this.pops == null) {
            this.pops = new ArrayList<>();
        }

        pops.add(pop);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("# ").append(getProvinceName()).append("\n");
        builder.append(getProvinceId()).append(" = {\n");

        List<PopHistoryPop> pops = new ArrayList<>(getPops());

        pops.sort(Comparator.comparing(PopHistoryPop::getType));

        for (PopHistoryPop pop : pops) {
            builder.append(pop.toString());
        }

        builder.append("}\n\n");

        return builder.toString();
    }

    public static class PopHistoryPop {
        protected String type;
        protected String culture;
        protected String religion;
        protected int size;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCulture() {
            return culture;
        }

        public void setCulture(String culture) {
            this.culture = culture;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setSize(double size) {
            setSize((int) size);
        }

        public String toString() {

            return "\t" + getType() + " = {\n" +
                    "\t\tculture = " + getCulture() + "\n" +
                    "\t\treligion = " + getReligion() + "\n" +
                    "\t\tsize = " + getSize() + "\n" +
                    "\t}\n";
        }
    }
}
