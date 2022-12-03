package helper;

public enum FileName {
    CONFIG("src/main/resources/config.properties"),
    OBJECT_FILE_COUNT("src/main/resources/object_file_count.properties"),
    HREF_INTERNAL("/href_internal.txt"),
    PAGE_OBJECT("page_object.json"),
    REPORT_PAGE_STRUCTURE("/report_page_structure.csv"),
    REPORT_PAGE_SEMANTIC("/report_page_semantic.csv"),
    REPORT_PAGE_IMG("/report_page_image.csv"),
    REPORT_PAGE_VIDEO("/report_page_video.csv"),
    REPORT_PAGE_HREF("/report_page_href.csv"),
    REPORT_PAGE_SEO("/report_page_seo.csv"),
    REPORT_PAGE_LOAD("/report_page_load.csv"),
    REPORT_REDIRECT("/report_redirect.csv"),
    REPORT_SITEMAP("/report_sitemap.txt"),
    REPORT_CONTACT("/href_contact.txt"),
    REPORT_HREF_EXTERNAL("/href_external.txt"),
    REPORT_CSS_INNER("/css_inner.txt"),
    REPORT_CSS_EXTERNAL("/css_external.txt")
    ;

    private String name;
    FileName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
