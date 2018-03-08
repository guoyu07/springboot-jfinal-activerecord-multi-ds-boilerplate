package com.gaols.study.studyboot.core.pagination;

import com.gaols.study.studyboot.web.HelloController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Page<T> {
    private int totalPage;
    private String link;
    private List<T> data;
    private int pageNumber;
    private int pageSize;
    private int totalRow;

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    public static final String DEFAULT_PAGE_SIZE = "10";

    /**
     * Constructor.
     *
     * @param pageNumber the page number
     * @param pageSize   the page size
     */
    public Page(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        bound();
    }

    public Page(int pageNumber) {
        this(pageNumber, Integer.valueOf(DEFAULT_PAGE_SIZE));
    }

    public Page(HttpServletRequest request) {
        this.pageNumber = Integer.valueOf(resolvePageNo(request));
        this.pageSize = Integer.valueOf(resolvePageSize(request));
        bound();
    }

    private String resolvePageSize(HttpServletRequest request) {
        String pageSize = request.getParameter("pageSize");
        if (StringUtils.isBlank(pageSize)) {
            logger.warn("missing parameter pageSize");
            pageSize = DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    private String resolvePageNo(HttpServletRequest request) {
        String pageNo = request.getParameter("pageNo");
        if (StringUtils.isBlank(pageNo)) {
            pageNo = request.getParameter("page");
        }
        if (StringUtils.isBlank(pageNo)) {
            logger.warn("missing parameter pageNo");
            pageNo = "1";
        }
        return pageNo;
    }

    private void bound() {
        this.pageNumber = Math.max(1, this.pageNumber);
    }

    public Page<T> href(String href) {
        this.link = href;
        return this;
    }

    public Page<T> data(List<T> data) {
        this.data = data;
        return this;
    }

    public Page<T> totalRow(int totalRow) {
        this.totalRow = totalRow;
        this.totalPage = totalRow / pageSize;
        if (totalRow % pageSize != 0) {
            this.totalPage++;
        }
        return this;
    }

    @Override
    public String toString() {
        return render();
    }

    public String render() {
        return renderer.render(this);
    }

    public void setRenderer(Renderable renderable) {
        this.renderer = renderable;
    }

    private Renderable renderer = new SimplePaginationRenderer();

    public String getHref() {
        return link;
    }

    /**
     * Return page number.
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Return page size.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Return total page.
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * Return total row.
     */
    public int getTotalRow() {
        return totalRow;
    }

    /**
     * Return the data for this page.
     */
    public List<T> getData() {
        return data;
    }

    public boolean isFirstPage() {
        return pageNumber == 1;
    }

    public boolean isLastPage() {
        return pageNumber == totalPage;
    }
}
