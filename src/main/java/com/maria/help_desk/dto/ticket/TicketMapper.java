package com.maria.help_desk.dto.ticket;

import com.maria.help_desk.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketMapper {

    public static TicketResponseDTO toDto(Ticket ticket){
        TicketResponseDTO dto = new TicketResponseDTO();

        dto.setId(ticket.getId());
        dto.setUserId(ticket.getUser().getId());
        dto.setTitle(ticket.getTitle());
        dto.setCategory(ticket.getCategory());
        dto.setStatus(ticket.getStatus());
        dto.setPriority(ticket.getPriority());
        dto.setRequestedAt(ticket.getRequestedAt());
        dto.setUpdatedAt(ticket.getUpdatedAt());

        return dto;
    }

    public static List<TicketResponseDTO> toDtos(List<Ticket> tickets){
        List<TicketResponseDTO> ticketsDto = new ArrayList<>();

        for(Ticket ticket : tickets){
            ticketsDto.add(toDto(ticket));
        }

        return ticketsDto;
    }
}
