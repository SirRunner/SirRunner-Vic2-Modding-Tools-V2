package utils.baseclasses;

import java.io.File;

public abstract class BaseReader {
    protected File file;
    protected boolean skipHeader = false;

    public BaseReader() {}

    public BaseReader(File file) {
        this.file = file;
    }

    public BaseReader(String filename) {
        this(new File(filename));
    }

    public abstract Object readFile() throws Exception;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
