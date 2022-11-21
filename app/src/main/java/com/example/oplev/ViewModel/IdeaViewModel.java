package com.example.oplev.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.oplev.Data.JourneyData;
import com.example.oplev.Model.Idea;
import com.example.oplev.Model.Journey;

//Opdater til Kotlin class, hvor der tages udgangspunkt i createJOruneyViewModel
// Get og set metoder til idéer

public class IdeaViewModel extends ViewModel {
    private Idea idea;
    private JourneyData journeyData;

    public IdeaViewModel() {
    }

    public String ideaImage() {
        return idea.getImage().equals(null) ? "DefualtImage" : idea.getImage();
    }

    public String ideaTitle() {
        return idea.getTitle();
    }
}


