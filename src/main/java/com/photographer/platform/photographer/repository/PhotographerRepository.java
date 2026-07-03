package com.photographer.platform.photographer.repository;

import com.photographer.platform.photographer.entity.Photographer;
import com.photographer.platform.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long> {

    /**
     * Find photographer by User
     */
    Optional<Photographer> findByUser(User user);

    /**
     * Check if profile already exists
     */
    boolean existsByUser(User user);

    /**
     * Search photographers by city
     */
    List<Photographer> findByCityIgnoreCase(String city);

    /**
     * Search photographers by state
     */
    List<Photographer> findByStateIgnoreCase(String state);

    /**
     * Search photographers by verification status
     */
    List<Photographer> findByVerified(boolean verified);

}