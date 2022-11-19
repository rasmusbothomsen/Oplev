package com.example.oplev.ViewModel;

import com.example.oplev.Model.Category;

import java.util.List;

public class CreateJourneyViewModel {
    private List<Category> categories;

    public CreateJourneyViewModel(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
