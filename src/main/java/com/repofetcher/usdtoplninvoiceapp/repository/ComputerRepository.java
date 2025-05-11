package com.repofetcher.usdtoplninvoiceapp.repository;

import com.repofetcher.usdtoplninvoiceapp.model.Computer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {
    Page<Computer> findByNameContainingIgnoreCaseAndAccountingDate(String name, LocalDate date, Pageable pageable);

    Page<Computer> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Computer> findByAccountingDate(LocalDate date, Pageable pageable);
}