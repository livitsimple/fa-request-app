package com.example.fa_request_app.fa;

import org.springframework.data.jpa.repository.JpaRepository;

// This interface provides CRUD operations for FaRequestEntity (repository)
public interface FaRequestRepo extends JpaRepository<FaRequestEntity, Long> {
}