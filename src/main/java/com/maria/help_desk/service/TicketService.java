package com.maria.help_desk.service;

import com.maria.help_desk.dto.TicketOpenDTO;
import com.maria.help_desk.exception.ResourceNotFoundException;
import com.maria.help_desk.model.Priority;
import com.maria.help_desk.model.Status;
import com.maria.help_desk.model.Ticket;
import com.maria.help_desk.model.User;
import com.maria.help_desk.repository.TicketRepository;
import com.maria.help_desk.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Ticket openTicket(TicketOpenDTO dto){
        Ticket ticket = new Ticket();

        User user = userRepository.findByEmail(dto.getUserEmail());

        if(user == null){
            throw new ResourceNotFoundException("User not found.");
        }

        ticket.setTitle(dto.getTitle());

        if(dto.getDescription() != null){
            ticket.setDescription(dto.getDescription());
        }

        ticket.setPriority(Priority.LOW);
        ticket.setStatus(Status.OPEN);
        ticket.setCategory(dto.getCategory());
        ticket.setUser(user);

        LocalDateTime requestedTime = LocalDateTime.now();
        LocalDateTime updatedTime = LocalDateTime.now();

        ticket.setRequestedAt(requestedTime);
        ticket.setUpdatedAt(updatedTime);

        ticketRepository.save(ticket);

        return ticket;
    }
}
