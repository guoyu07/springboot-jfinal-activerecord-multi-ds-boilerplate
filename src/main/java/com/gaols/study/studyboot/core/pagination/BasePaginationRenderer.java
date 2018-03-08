package com.gaols.study.studyboot.core.pagination;


/**
 * @author gaols
 */
public abstract class BasePaginationRenderer implements Renderable {
    private static final String DEFAULT_LABEL_PRE_PAGE = "上一页";
    private static final String DEFAULT_LABEL_NEXT_PAGE = "下一页";

    private int displayedPages = 5;
    private int edges = 2;
    private String prePageLabel = DEFAULT_LABEL_PRE_PAGE;
    private String nextPageLabel = DEFAULT_LABEL_NEXT_PAGE;

    /**
     * How many page numbers should be visible while navigating.
     * Minimum allowed: 3 (previous, current & next)
     */
    public BasePaginationRenderer setDisplayedPages(int displayedPages) {
        if (displayedPages <= 3) {
            displayedPages = 3;
        }
        this.displayedPages = displayedPages;
        return this;
    }

    /**
     * How many page numbers are visible at the beginning/ending of the pagination.
     * Default 2.
     */
    public BasePaginationRenderer setEdges(int edges) {
        this.edges = edges;
        return this;
    }

    /**
     * Label to be display on the previous button.
     */
    public BasePaginationRenderer setPrePageLabel(String label) {
        this.prePageLabel = label;
        return this;
    }

    /**
     * Label to be display on the next button.
     */
    public BasePaginationRenderer setNextPageLabel(String label) {
        this.nextPageLabel = label;
        return this;
    }

    public int getDisplayedPages() {
        return displayedPages;
    }

    public int getEdges() {
        return edges;
    }

    public String getPrePageLabel() {
        return prePageLabel;
    }

    public String getNextPageLabel() {
        return nextPageLabel;
    }
}
