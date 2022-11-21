package com.example.oplev.ViewModel;

import com.example.oplev.Model.Category;

import java.util.List;

// SKal laves til kotlinklasse

public class CreateIdeaViewModel {
    private List<Category> categories;

    public CreateIdeaViewModel(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
