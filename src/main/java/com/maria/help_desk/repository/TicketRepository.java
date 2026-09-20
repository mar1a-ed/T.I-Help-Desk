package com.maria.help_desk.repository;

import com.maria.help_desk.model.Category;
import com.maria.help_desk.model.Priority;
import com.maria.help_desk.model.Status;
import com.maria.help_desk.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByPriority(Priority priority);

    List<Ticket> findByStatus(Status status);

    List<Ticket> findByCategory(Category category);
}
