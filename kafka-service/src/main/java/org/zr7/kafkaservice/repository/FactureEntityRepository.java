package org.zr7.kafkaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.zr7.kafkaservice.entities.FactureEntity;

@RepositoryRestResource
public interface FactureEntityRepository extends JpaRepository<FactureEntity, Long> {
}
