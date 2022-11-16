package com.example.oplev.ViewModel;

import androidx.compose.runtime.Composable;
import androidx.lifecycle.ViewModel;

import com.example.oplev.Data.JourneyData;
import com.example.oplev.Model.Journey;

public class JourneyViewModel extends ViewModel {
    private Journey journey;
    private JourneyData journeyData;


    public JourneyViewModel(Journey journey) {
        this.journey = journey;
    }

    public String journeyImage(){
        return journey.getImage().equals(null) ? "DefualtImage" : journey.getImage() ;
    }

    public String journeyTitle(){
        return journey.getTitle() ;
    }

    public Object getIdeas(){
        return null;
    }

    public Object getFolders(){
        return null;
    }

}
