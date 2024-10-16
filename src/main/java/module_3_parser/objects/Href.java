package module_3_parser.objects;

import lombok.Data;

@Data
public class Href {
    private String href;
    private String target;
    private String rel;
    private String text;
    private String ariaLabel;
    private String tabIndex;
}
