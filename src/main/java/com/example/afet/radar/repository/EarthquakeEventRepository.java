package com.example.afet.radar.repository;

import com.example.afet.radar.model.EarthquakeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EarthquakeEventRepository extends JpaRepository<EarthquakeEvent, String> {

    List<EarthquakeEvent> findByProvince(String province);

    // eventID'leri çekmek için özel bir sorgu ekliyoruz
    @Query("SELECT e.eventID FROM EarthquakeEvent e")
    List<String> findAllEventIds();

    List<EarthquakeEvent> findByMagnitudeGreaterThan(Double magnitude);
}