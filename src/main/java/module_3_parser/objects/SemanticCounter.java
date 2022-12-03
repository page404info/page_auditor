package module_3_parser.objects;

import lombok.Data;

@Data
public class SemanticCounter {
    private Integer headerCount;
    private Integer navCount;
    private Integer mainCount;
    private Integer footerCount;

    private Integer articleCount;
    private Integer sectionCount;
    private Integer asideCount;
}
