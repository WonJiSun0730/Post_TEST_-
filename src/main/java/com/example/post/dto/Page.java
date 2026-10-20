package com.example.post.dto;

/** 한 페이지 10개, 페이지 번호 묶음 5개. 빈 목록도 1페이지로 표시한다. */
public class Page {
    private final int page;
    private final int size = 10;
    private final int blockSize = 5;
    private final long totalCount;
    private final int totalPages;
    private final int startPage;
    private final int endPage;

    public Page(int requestedPage, long totalCount) {
        this.totalCount = Math.max(0L, totalCount);
        this.totalPages = (int) Math.max(1L, (this.totalCount + size - 1) / size);
        this.page = Math.min(Math.max(1, requestedPage), totalPages);
        this.startPage = ((page - 1) / blockSize) * blockSize + 1;
        this.endPage = Math.min(startPage + blockSize - 1, totalPages);
    }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalCount() { return totalCount; }
    public int getTotalPages() { return totalPages; }
    public int getStartPage() { return startPage; }
    public int getEndPage() { return endPage; }
    public long getOffset() { return (long) (page - 1) * size; }
    public boolean isHasPrevious() { return page > 1; }
    public boolean isHasNext() { return page < totalPages; }
}
