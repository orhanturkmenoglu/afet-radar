package com.example.afet.radar.projection;

import java.time.LocalDateTime;

public interface EarthquakeEventView {
    String getEventID();
    String getLocation();
    String getProvince();
    Double depth();
    Double getMagnitude();
    LocalDateTime getDate();
}
