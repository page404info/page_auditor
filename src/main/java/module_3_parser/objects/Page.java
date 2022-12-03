package module_3_parser.objects;

import lombok.Data;

import java.util.List;

@Data
public class Page {
    private String pageName;

    private String lang;
    private String title;
    private String description;
    private String h1;

    private String charset;
    private String viewport;
    private String faviconAttr;

    private List<String> linkList;
    private List<String> scriptList;

    private List<Img> imgList;
    private List<Video> videoList;
    private List<Href> hrefList;

    private ElementCounter elementCounter;
    private SemanticCounter semanticCounter;
}
