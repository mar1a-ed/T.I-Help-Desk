package com.maria.help_desk.controller;

import com.maria.help_desk.dto.TicketMapper;
import com.maria.help_desk.dto.TicketOpenDTO;
import com.maria.help_desk.dto.TicketResponseDTO;
import com.maria.help_desk.model.Ticket;
import com.maria.help_desk.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponseDTO> openTicket(@RequestBody @Valid TicketOpenDTO dto){
        Ticket ticket = ticketService.openTicket(dto);

        TicketResponseDTO ticketDto = TicketMapper.toDto(ticket);

        return ResponseEntity.ok().body(ticketDto);
    }
}
