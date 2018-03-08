package com.gaols.study.studyboot.core.pagination;

import com.gaols.study.studyboot.core.pagination.element.*;
import com.gaols.study.studyboot.utils.Strkit;
import com.gaols.study.studyboot.utils.UrlKit;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;

/**
 * 在服务端生成simplePagination.js需要的结构。
 *
 * @author gaols
 */
public class SimplePaginationRenderer extends BasePaginationRenderer {
    private String theme = "light-theme";
    private Page<?> page;

    /**
     * 最后render的结果，其中href属性的值取决于Page中的设置。
     * <p>
     * <pre>
     * {@code <div class="pagination light-theme simple-pagination">
     * <ul>
     * <li><a href="#page-8" class="page-link prev">上一页</a></li>
     * <li><a href="#page-1" class="page-link">1</a></li>
     * <li><a href="#page-2" class="page-link">2</a></li>
     * <li class=""><span class="ellipse">…</span></li>
     * <li><a href="#page-7" class="page-link">7</a></li>
     * <li><a href="#page-8" class="page-link">8</a></li>
     * <li class="active"><span class="current">9</span></li>
     * <li><a href="#page-10" class="page-link">10</a></li>
     * <li><a href="#page-11" class="page-link">11</a></li>
     * <li class=""><span class="ellipse">…</span></li>
     * <li><a href="#page-19" class="page-link">19</a></li>
     * <li><a href="#page-20" class="page-link">20</a></li>
     * <li><a href="#page-10" class="page-link next">下一页</a></li>
     * </ul>
     * </div>}
     * </pre>
     */
    public String render(Page<?> page) {
        this.page = page;
        if (page.getTotalPage() <= 0) {
            return "";
        }

        Element ul = buildUl();
        Div div = (Div) new Div().clazz(String.format("pagination %s simple-pagination", theme));
        div.addChild(ul);
        return div.render(page);
    }

    private Element buildUl() {
        Element ul = new Ul();
        int curPageNo = page.getPageNumber();
        ul.addChild(prePageLi());
        LinkedList<Li> pageLis = new LinkedList<Li>();
        if (curPageNo <= page.getTotalPage()) {
            pageLis.add(currentPageLi());
        }
        int[] d = dividePages(page.getTotalPage(), curPageNo, getDisplayedPages());
        buildLeftHalf(pageLis, d);
        buildRightHalf(pageLis, d);
        for (Li li : pageLis) {
            ul.addChild(li);
        }
        ul.addChild(nextPageLi());
        return ul;
    }

    static int[] dividePages(int totalPages, int curPageNo, int displayedPages) {
        if (curPageNo > totalPages) {
            return new int[]{totalPages, 0};
        }

        int half = (displayedPages - 1) / 2;
        int left, right;
        int leftAvail = curPageNo - 1;
        int rightAvail = totalPages - curPageNo;
        if (leftAvail >= half && rightAvail >= half) {
            left = right = half;
            if (left + right + 1 < displayedPages) {
                left++;
                left = Math.min(leftAvail, left);
            }
            if (left + right + 1 < displayedPages) {
                right++;
                right = Math.min(rightAvail, right);
            }
        } else if (leftAvail < half) {
            left = leftAvail;
            right = Math.min(displayedPages - left - 1, rightAvail);
        } else {
            right = rightAvail;
            left = Math.min(displayedPages - right - 1, leftAvail);
        }
        return new int[]{left, right};
    }

    private void buildLeftHalf(LinkedList<Li> pageLis, int[] divideInfo) {
        int curPageNo = page.getPageNumber();
        int i, left = divideInfo[0];
        for (i = curPageNo - 1; i > 0 && left > 0; i--, left--) {
            pageLis.addFirst(buildPageLi(i));
        }
        if (i > getEdges()) {
            pageLis.addFirst(ellipsePageLi());
            for (int k = getEdges(); k > 0; k--) {
                pageLis.addFirst(buildPageLi(k));
            }
        } else {
            for (int k = i; k > 0; k--) {
                pageLis.addFirst(buildPageLi(k));
            }
        }
    }

    private void buildRightHalf(LinkedList<Li> pageLis, int[] divideInfo) {
        int curPageNo = page.getPageNumber();
        int totalPages = page.getTotalPage();
        int i, right = divideInfo[1];
        for (i = curPageNo + 1; right > 0; i++, right--) {
            pageLis.addLast(buildPageLi(i));
        }
        if (totalPages - i > getEdges()) {
            pageLis.addLast(ellipsePageLi());
            for (int k = totalPages - getEdges() + 1; k <= totalPages; k++) {
                pageLis.addLast(buildPageLi(k));
            }
        } else {
            for (int k = i; k <= totalPages; k++) {
                pageLis.addLast(buildPageLi(k));
            }
        }
    }

    private Li currentPageLi() {
        Element li = new Li().clazz("active");
        new Span().clazz("current").text(String.valueOf(page.getPageNumber())).appendTo(li);
        return (Li) li;
    }

    private Li ellipsePageLi() {
        Element li = new Li().clazz("");
        new Span().clazz("ellipse").text("...").appendTo(li);
        return (Li) li;
    }

    private Li prePageLi() {
        Li prePage = new Li();
        int prePageNo = page.getPageNumber() - 1;
        prePageNo = Math.max(1, prePageNo);
        prePageNo = Math.min(prePageNo, page.getTotalPage());

        if (prePageNo == 1) {
            prePage.clazz("disabled");
            new Span().clazz("current prev").text(getPrePageLabel()).appendTo(prePage);
        } else {
            new A().href(genHref(prePageNo)).clazz("page-link prev").text(getPrePageLabel()).appendTo(prePage);
        }
        return prePage;
    }

    private Li buildPageLi(int pageNo) {
        Li li = new Li();
        new A().href(genHref(pageNo)).clazz("page-link").text(String.valueOf(pageNo)).appendTo(li);
        return li;
    }

    private String genHref(int pageNo) {
        String href = Strkit.trimNull(page.getHref());
        if (StringUtils.isNotBlank(href)) {
            href = UrlKit.build(href, "page", String.valueOf(pageNo), "pageSize", String.valueOf(page.getPageSize()));
        }
        return href;
    }

    private Li nextPageLi() {
        Li nextPageLi = new Li();
        int nextPageNo = page.getPageNumber() + 1;
        nextPageNo = Math.min(nextPageNo, page.getTotalPage());

        if (nextPageNo == page.getTotalPage()) {
            nextPageLi.clazz("disabled");
            new Span().clazz("current next").text(getNextPageLabel()).appendTo(nextPageLi);
        } else {
            new A().href(genHref(nextPageNo)).clazz("page-link next").text(getNextPageLabel()).appendTo(nextPageLi);
        }
        return nextPageLi;
    }

    public SimplePaginationRenderer theme(String theme) {
        this.theme = theme;
        return this;
    }
}
