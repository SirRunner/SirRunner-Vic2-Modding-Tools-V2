package savefile.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BuiltNews {
    private boolean isRead;
    private LocalDate date;
    private List<Article> articles;
    private int style;
    private int seed;
    private String titleImage;

    public BuiltNews() {
        init();
    }

    public void init() {
        if (articles == null) {
            this.articles = new ArrayList<>();
        }
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article article) {
        this.articles.add(article);
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }
}
