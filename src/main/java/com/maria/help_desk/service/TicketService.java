package com.maria.help_desk.service;

import com.maria.help_desk.dto.TicketAdminUpdateDTO;
import com.maria.help_desk.dto.TicketOpenDTO;
import com.maria.help_desk.dto.TicketUserUpdateDTO;
import com.maria.help_desk.exception.ResourceNotFoundException;
import com.maria.help_desk.model.*;
import com.maria.help_desk.repository.TicketRepository;
import com.maria.help_desk.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

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

        user.getRequestTickets().add(ticket);

        ticketRepository.save(ticket);

        return ticket;
    }

    @Transactional
    public Ticket findById(Long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Ticket not found.")
        );

        return ticket;
    }

    @Transactional
    public List<Ticket> findAll(){
        List<Ticket> tickets = ticketRepository.findAll();

        if(tickets == null){
            throw new ResourceNotFoundException("Tickets not found.");
        }

        return tickets;
    }

    @Transactional
    public List<Ticket> findTicketByPriority(String priority){
        priority = priority.toUpperCase(Locale.ROOT);

        List<Ticket> tickets = ticketRepository.findByPriority(Priority.valueOf(priority));

        if(tickets == null){
            throw new ResourceNotFoundException("Tickets not found.");
        }

        return tickets;
    }

    @Transactional
    public List<Ticket> findByStatus(String status){
        status = status.toUpperCase(Locale.ROOT);

        List<Ticket> tickets = ticketRepository.findByStatus(Status.valueOf(status));

        if(tickets == null){
            throw new ResourceNotFoundException("Tickets not found.");
        }

        return tickets;
    }

    @Transactional
    public List<Ticket> findByCategory(String category){
        category = category.toUpperCase(Locale.ROOT);

        List<Ticket> tickets = ticketRepository.findByCategory(Category.valueOf(category));

        if(tickets == null){
            throw new ResourceNotFoundException("Tickets not found.");
        }

        return tickets;
    }

    @Transactional
    public Ticket updateTicketUser(Long id, TicketUserUpdateDTO dto){
        Ticket ticket = findById(id);

        if(dto.getTitle() != null){
            ticket.setTitle(dto.getTitle());
        }

        if(dto.getDescription() != null){
            ticket.setDescription(dto.getDescription());
        }

        if(dto.getCategory() != null){
            ticket.setCategory(dto.getCategory());
        }

        ticket.setUpdatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);

        return ticket;
    }

    @Transactional
    public Ticket updateTicketAdmin(Long id, TicketAdminUpdateDTO dto){
        Ticket ticket = findById(id);

        if(dto.getPriority() != null){
            ticket.setPriority(dto.getPriority());
        }

        if(dto.getStatus() != null){
            ticket.setStatus(dto.getStatus());
        }

        if(dto.getSupportEmail() != null){
            User user = userRepository.findByEmail(dto.getSupportEmail());

            if(!userService.verifyUserRole(user.getId(), "ROLE_SUPPORT")){
                throw new ResourceNotFoundException("The role does not match with the 'Support' role.");
            }

            ticket.setSupport(user);
        }

        ticket.setUpdatedAt(LocalDateTime.now());

        ticketRepository.save(ticket);

        return ticket;
    }

    @Transactional
    public void deleteTicket(Long id){
        Ticket ticket = findById(id);

        ticketRepository.delete(ticket);
    }
}











