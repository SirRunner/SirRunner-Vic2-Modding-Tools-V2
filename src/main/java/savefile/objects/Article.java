package savefile.objects;

public class Article {
    private String size;
    private NewsScope newsScope;

    public Article() {
        init();
    }

    public void init() {
        if (newsScope == null) {
            newsScope = new NewsScope();
        }
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public NewsScope getNewsScope() {
        return newsScope;
    }

    public void setNewsScope(NewsScope newsScope) {
        this.newsScope = newsScope;
    }
}
