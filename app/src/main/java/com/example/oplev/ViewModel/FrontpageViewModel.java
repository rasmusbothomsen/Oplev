package com.example.oplev.ViewModel;

import com.example.oplev.Data.JourneyData;
import com.example.oplev.Model.Category;
import com.example.oplev.Model.Journey;

import java.util.List;

public class FrontpageViewModel {
    private List<Category> categories;

    public FrontpageViewModel(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
