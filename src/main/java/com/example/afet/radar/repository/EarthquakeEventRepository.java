package com.example.afet.radar.repository;

import com.example.afet.radar.model.EarthquakeEvent;
import com.example.afet.radar.projection.EarthquakeEventView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EarthquakeEventRepository extends JpaRepository<EarthquakeEvent, String> {

    List<EarthquakeEventView> findByProvince(String province);

    List<EarthquakeEventView> findByMagnitudeBetween(Double min, Double max);

    List<EarthquakeEventView> findByDepthBetween(Double min, Double max);

    List<EarthquakeEventView> findByDateBetween(LocalDateTime start, LocalDateTime end);

    // eventID'leri çekmek için özel bir sorgu ekliyoruz
    @Query("SELECT e.eventID FROM EarthquakeEvent e")
    List<String> findAllEventIds();

    List<EarthquakeEvent> findByMagnitudeGreaterThan(Double magnitude);
}