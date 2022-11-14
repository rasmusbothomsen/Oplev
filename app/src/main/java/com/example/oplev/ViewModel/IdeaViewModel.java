package com.example.oplev.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.oplev.Data.JourneyData;
import com.example.oplev.Model.Idea;
import com.example.oplev.Model.Journey;

public class IdeaViewModel extends ViewModel {
    private Idea idea;
    private JourneyData journeyData;

    public IdeaViewModel(Idea idea) {this.idea = idea; }

}
