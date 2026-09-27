package com.maria.help_desk.service;

import com.maria.help_desk.dto.ticket.TicketOpenDTO;
import com.maria.help_desk.exception.TicketNotFoundException;
import com.maria.help_desk.exception.UserNotFoundException;
import com.maria.help_desk.model.*;
import com.maria.help_desk.repository.TicketRepository;
import com.maria.help_desk.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    public void openTicketWithSuccess(){
        String title = "Computador não liga";
        String description = "Computador não liga depois que limpei embaixo dele";
        Priority priority = Priority.LOW;
        Status status = Status.OPEN;
        Category category = Category.HARDWARE;
        LocalDateTime requestedTime = LocalDateTime.now();
        LocalDateTime updatedTime = LocalDateTime.now();
        User user = new User();

        user.setId(3L);
        user.setEmail("lilian@gmail.com");

        when(userRepository.findByEmail("lilian@gmail.com")).thenReturn(user);

        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setPriority(priority);
        ticket.setStatus(status);
        ticket.setCategory(category);
        ticket.setUser(user);
        ticket.setRequestedAt(requestedTime);
        ticket.setUpdatedAt(updatedTime);

        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket ticketResult = ticketService.openTicket(new TicketOpenDTO(user.getEmail(), title, description, category));

        assertNotNull(ticketResult);
        verify(userRepository).findByEmail("lilian@gmail.com");
        verify(ticketRepository).save(ticket);
    }

    @Test
    public void openTicketWithNotFoundUser(){
        String title = "Computador não liga";
        String description = "Computador não liga depois que limpei embaixo dele";
        Priority priority = Priority.LOW;
        Status status = Status.OPEN;
        Category category = Category.HARDWARE;
        LocalDateTime requestedTime = LocalDateTime.now();
        LocalDateTime updatedTime = LocalDateTime.now();
        User user = new User();

        user.setId(77L);
        user.setEmail("lolita@gmail.com");

        when(userRepository.findByEmail("lolita@gmail.com")).thenThrow(new UserNotFoundException("User not found."));

        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setPriority(priority);
        ticket.setStatus(status);
        ticket.setCategory(category);
        ticket.setUser(user);
        ticket.setRequestedAt(requestedTime);
        ticket.setUpdatedAt(updatedTime);

        assertThrows(UserNotFoundException.class, () -> ticketService.openTicket(new TicketOpenDTO(user.getEmail(), title, description, category)));
    }

    @Test
    public void findTicketByValidId(){
        Long id = 2L;
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setTitle("Esqueceu a senha do sistema ERP");

        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));

        Ticket ticketResult = ticketService.findById(id);

        assertNotNull(ticketResult);
        assertEquals(ticket.getTitle(), ticketResult.getTitle());
    }

    @Test
    public void findTicketByInvalidId(){
        Long id = 77L;

        when(ticketRepository.findById(id)).thenThrow(new TicketNotFoundException("Ticket not found."));

        assertThrows(TicketNotFoundException.class, () -> ticketService.findById(id));
    }
}
