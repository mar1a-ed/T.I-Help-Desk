package com.maria.help_desk.dto;

import com.maria.help_desk.model.Ticket;

public class TicketMapper {

    public static TicketResponseDTO toDto(Ticket ticket){
        TicketResponseDTO dto = new TicketResponseDTO();

        dto.setUserId(ticket.getUser().getId());
        dto.setTitle(ticket.getTitle());
        dto.setCategory(ticket.getCategory());
        dto.setStatus(ticket.getStatus());
        dto.setPriority(ticket.getPriority());
        dto.setRequestedAt(ticket.getRequestedAt());
        dto.setUpdatedAt(ticket.getUpdatedAt());

        return dto;
    }
}
