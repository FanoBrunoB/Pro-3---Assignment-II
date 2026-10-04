package com.example.assignment.Repositories;

import com.example.assignment.Entities.DistributionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TraceabilityRepository extends JpaRepository<DistributionProduct, Long> {

    @Query("""
        SELECT DISTINCT c.id
        FROM DistributionProduct p
        JOIN p.compositionCutParts cp
        JOIN cp.cow c
        WHERE p.id = :productId
        ORDER BY c.id
    """)
    List<Long> findCowIdsByProductId(@Param("productId") Long productId);

    @Query("""
        SELECT DISTINCT p
        FROM DistributionProduct p
        JOIN p.compositionCutParts cp
        JOIN cp.cow c
        WHERE c.id = :cowId
        ORDER BY p.id
    """)
    List<DistributionProduct> findProductsByCowId(@Param("cowId") Long cowId);

    @Query("SELECT COUNT(c) > 0 FROM Cow c WHERE c.id = :cowId")
    boolean cowExists(@Param("cowId") Long cowId);
}