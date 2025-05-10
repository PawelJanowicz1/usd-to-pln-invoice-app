package com.repofetcher.usdtoplninvoiceapp.repository;

import com.repofetcher.usdtoplninvoiceapp.model.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {
    List<Computer> findByNameContainingIgnoreCase(String fragment);
    List<Computer> findByAccountingDate(LocalDate date);

    @Query("SELECT c FROM Computer c ORDER BY c.name ASC")
    List<Computer> sortByNameAsc();

    @Query("SELECT c FROM Computer c ORDER BY c.name DESC")
    List<Computer> sortByNameDesc();

    @Query("SELECT c FROM Computer c ORDER BY c.accountingDate ASC")
    List<Computer> sortByAccountingDateAsc();

    @Query("SELECT c FROM Computer c ORDER BY c.accountingDate DESC")
    List<Computer> sortByAccountingDateDesc();
}