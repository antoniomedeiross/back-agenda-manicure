package com.api.agenda.repository;

import com.api.agenda.entity.Manicure;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManicureRepository extends JpaRepository<Manicure,UUID> {
    Optional<Manicure> findByEmail(String email);
    List<Manicure> findByRole(String role);
    boolean existsByEmail(String email);
}
