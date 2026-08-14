package savefile.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewsCollector {
    private List<NewsScope> newsScopes;
    private List<BuiltNews> builtNews;
    private LocalDate date;
    private int tension;

    public NewsCollector() {
        init();
    }

    public void init() {
        if (newsScopes == null) {
            newsScopes = new ArrayList<>();
        }
        if (builtNews == null) {
            builtNews = new ArrayList<>();
        }
    }

    public List<NewsScope> getNewsScopes() {
        return newsScopes;
    }

    public void setNewsScopes(List<NewsScope> newsScopes) {
        this.newsScopes = newsScopes;
    }

    public void addNewsScope(NewsScope newsScope) {
        this.newsScopes.add(newsScope);
    }

    public List<BuiltNews> getBuiltNews() {
        return builtNews;
    }

    public void setBuiltNews(List<BuiltNews> builtNews) {
        this.builtNews = builtNews;
    }

    public void addBuiltNews(BuiltNews builtNews) {
        this.builtNews.add(builtNews);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTension() {
        return tension;
    }

    public void setTension(int tension) {
        this.tension = tension;
    }
}
