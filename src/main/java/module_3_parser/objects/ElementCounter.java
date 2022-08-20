package module_3_parser.objects;

import lombok.Data;

@Data
public class ElementCounter {
    private Integer titleCount;
    private Integer descriptionCount;
    private Integer faviconCount;

    private Integer styleCount;
    private Integer linkCount;
    private Integer scriptCount;

    private Integer h1Count;
    private Integer h2Count;
    private Integer h3Count;
    private Integer h4Count;
    private Integer h5Count;
    private Integer h6Count;

    private Integer hrefCount;
    private Integer imgCount;
    private Integer inputFieldCount;
    private Integer buttonCount;
    private Integer tableCount;
    private Integer videoCount;

    private Integer charDocumentCount;
    private Integer charTextCount;
    private boolean isComment;
}
