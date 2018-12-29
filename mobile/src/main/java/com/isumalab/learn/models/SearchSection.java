package com.isumalab.learn.models;

import java.util.ArrayList;

public class SearchSection {

    private String headerTitle;
    private ArrayList<SearchCourseItem> allItemsInSection;

    public SearchSection() {
    }

    public SearchSection(String headerTitle, ArrayList<SearchCourseItem> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SearchCourseItem> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SearchCourseItem> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }
}
