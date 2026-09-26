package com.maria.help_desk.controller;

import com.maria.help_desk.dto.ticket.*;
import com.maria.help_desk.model.Ticket;
import com.maria.help_desk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<TicketResponseDTO> openTicket(@RequestBody @Valid TicketOpenDTO dto){
        Ticket ticket = ticketService.openTicket(dto);

        TicketResponseDTO ticketDto = TicketMapper.toDto(ticket);

        return ResponseEntity.ok().body(ticketDto);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPPORT')")
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> findById(@PathVariable(value = "id") Long id){
        Ticket ticket = ticketService.findById(id);

        TicketResponseDTO ticketDto = TicketMapper.toDto(ticket);
        ticketDto.add(linkTo(methodOn(TicketController.class).findAll()).withRel("List of tickets"));

        return ResponseEntity.ok().body(ticketDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> findAll(){
        List<Ticket> tickets = ticketService.findAll();

        List<TicketResponseDTO> ticketsDto = TicketMapper.toDtos(tickets);

        for(TicketResponseDTO ticket : ticketsDto){
            Long id = ticket.getId();
            ticket.add(linkTo(methodOn(TicketController.class).findById(id)).withSelfRel());
        }

        return ResponseEntity.ok().body(ticketsDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TicketResponseDTO>> findTicketByPriority(@PathVariable(value = "priority") String priority){
        List<Ticket> tickets = ticketService.findTicketByPriority(priority);

        List<TicketResponseDTO> ticketsDto = TicketMapper.toDtos(tickets);

        for(TicketResponseDTO ticket : ticketsDto){
            Long id = ticket.getId();
            ticket.add(linkTo(methodOn(TicketController.class).findById(id)).withSelfRel());
        }

        return ResponseEntity.ok().body(ticketsDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketResponseDTO>> findByStatus(@PathVariable(value = "status") String status){
        List<Ticket> tickets = ticketService.findByStatus(status);

        List<TicketResponseDTO> ticketsDto = TicketMapper.toDtos(tickets);

        for(TicketResponseDTO ticket : ticketsDto){
            Long id = ticket.getId();
            ticket.add(linkTo(methodOn(TicketController.class).findById(id)).withSelfRel());
        }

        return ResponseEntity.ok().body(ticketsDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    @GetMapping("/category/{category}")
    public ResponseEntity<List<TicketResponseDTO>> findByCategory(@PathVariable(value = "category") String category){
        List<Ticket> tickets = ticketService.findByCategory(category);

        List<TicketResponseDTO> ticketsDto = TicketMapper.toDtos(tickets);

        for(TicketResponseDTO ticket : ticketsDto){
            Long id = ticket.getId();
            ticket.add(linkTo(methodOn(TicketController.class).findById(id)).withSelfRel());
        }

        return ResponseEntity.ok().body(ticketsDto);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicketUser(@PathVariable(value = "id") Long id, @RequestBody @Valid TicketUserUpdateDTO dto){
        Ticket ticket = ticketService.updateTicketUser(id, dto);

        TicketResponseDTO ticketDto = TicketMapper.toDto(ticket);

        return ResponseEntity.ok().body(ticketDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicketAdmin(@PathVariable(value = "id") Long id, @RequestBody @Valid TicketAdminUpdateDTO dto){
        Ticket ticket = ticketService.updateTicketAdmin(id, dto);

        TicketResponseDTO ticketDto = TicketMapper.toDto(ticket);

        return ResponseEntity.ok().body(ticketDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable(value = "id") Long id){
        ticketService.deleteTicket(id);

        return ResponseEntity.ok().build();
    }
}
