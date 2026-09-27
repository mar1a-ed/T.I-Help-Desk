package com.maria.help_desk.repository;

import com.maria.help_desk.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByPriority(Priority priority);

    List<Ticket> findByStatus(Status status);

    List<Ticket> findByCategory(Category category);
}
