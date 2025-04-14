package com.example.afet.radar.repository;

import com.example.afet.radar.model.EarthquakeEvent;
import com.example.afet.radar.projection.EarthquakeEventView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EarthquakeEventRepository extends JpaRepository<EarthquakeEvent, String> {

    List<EarthquakeEventView> findByProvince(String province);

    List<EarthquakeEventView> findByMagnitudeGreaterThan(Double magnitude);
    List<EarthquakeEventView> findByMagnitudeBetween(Double min, Double max);

    List<EarthquakeEventView> findByDepthBetween(Double min, Double max);

    List<EarthquakeEventView> findByDateBetween(LocalDateTime start, LocalDateTime end);

}