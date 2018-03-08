package com.gaols.study.studyboot.core.pagination;

public interface Renderable {
    /**
     * Render the page
     */
    String render(Page<?> page);
}
