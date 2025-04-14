package com.example.mapmate.items;

public class SlideItem {
    private final int imageUrl;
    private final int title;
    private final int description;

    public SlideItem(int imageUrl, int title, int description) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public int getTitle() {
        return title;
    }

    public int getDescription() {
        return description;
    }
}
