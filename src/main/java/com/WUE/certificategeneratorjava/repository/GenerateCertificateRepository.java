package com.WUE.certificategeneratorjava.repository;

import com.WUE.certificategeneratorjava.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerateCertificateRepository extends JpaRepository<Certificate, String> {

}
