package com.maria.help_desk.controller;

import com.maria.help_desk.dto.ticket.*;
import com.maria.help_desk.dto.user.UserResponseDTO;
import com.maria.help_desk.handler.ErrorMessage;
import com.maria.help_desk.model.Ticket;
import com.maria.help_desk.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Ticket", description = "Ticket Resources.")
@Slf4j
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Operation(summary = "Open a ticket", description = "Open a ticket.", responses = {
            @ApiResponse(responseCode = "201", description = "Successful request and return of the ticket's public data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user was not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user was not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the user was not found or does not exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<TicketResponseDTO> openTicket(@RequestBody @Valid TicketOpenDTO dto){
        Ticket ticket = ticketService.openTicket(dto);

        TicketResponseDTO ticketDto = TicketMapper.toDto(ticket);

        return ResponseEntity.status(HttpStatus.CREATED).body(ticketDto);
    }

    @Operation(summary = "Find a ticket by id", description = "Find a ticket by id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request and return of the ticket's public data and a hypermedia link.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user was not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user was not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the tickets were not found or do not exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPORT')")
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> findById(@PathVariable(value = "id") Long id){
        Ticket ticket = ticketService.findById(id);

        TicketResponseDTO ticketDto = TicketMapper.toDto(ticket);
        ticketDto.add(linkTo(methodOn(TicketController.class).findAll()).withRel("List of tickets"));

        return ResponseEntity.ok().body(ticketDto);
    }

    @Operation(summary = "Find all tickets", description = "Find all tickets.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request and return of the ticket's public data and a hypermedia link.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user was not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user was not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the tickets were not found or do not exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
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

    @Operation(summary = "Find all tickets by priority", description = "Find all tickets by priority.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request and return of the ticket's public data and a hypermedia link.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user was not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user was not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the tickets were not found or do not exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
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

    @Operation(summary = "Find all tickets by status", description = "Find all tickets by status.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request and return of the ticket's public data and a hypermedia link.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user was not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user was not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the tickets were not found or do not exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
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

    @Operation(summary = "Find all tickets by category", description = "Find all tickets by category.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request and return of the ticket's public data and a hypermedia link.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user was not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user was not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the tickets were not found or do not exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
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

    @Operation(summary = "Update the public data of the ticket", description = "Update the public data of the ticket.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request and return of the ticket's public data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user was not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user was not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the ticket was not found or does not exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicketUser(@PathVariable(value = "id") Long id, @RequestBody @Valid TicketUserUpdateDTO dto){
        Ticket ticket = ticketService.updateTicketUser(id, dto);

        TicketResponseDTO ticketDto = TicketMapper.toDto(ticket);

        return ResponseEntity.ok().body(ticketDto);
    }

    @Operation(summary = "Update the private data of the ticket", description = "Update the private data of the ticket.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request and return of the ticket's public data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDTO.class))
            ),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user was not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user was not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the ticket was not found or does not exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicketAdmin(@PathVariable(value = "id") Long id, @RequestBody @Valid TicketAdminUpdateDTO dto){
        Ticket ticket = ticketService.updateTicketAdmin(id, dto);

        TicketResponseDTO ticketDto = TicketMapper.toDto(ticket);

        return ResponseEntity.ok().body(ticketDto);
    }

    @Operation(summary = "Delete a ticket", description = "Delete a ticket.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful request."),
            @ApiResponse(responseCode = "401", description = "The request was unsuccessful because the user was not authenticated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "403", description = "The request was unsuccessful because the user was not authorized to access the resource.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            ),
            @ApiResponse(responseCode = "404", description = "The request was unsuccessful because the ticket was not found or does not exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable(value = "id") Long id){
        ticketService.deleteTicket(id);

        return ResponseEntity.ok().build();
    }
}
