package com.maria.help_desk.dto;

import com.maria.help_desk.model.Category;
import com.maria.help_desk.model.Priority;
import com.maria.help_desk.model.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketResponseDTO {

    private Long userId;

    private String title;

    private Category category;

    private Status status;

    private Priority priority;

    private LocalDateTime requestedAt;

    private LocalDateTime updatedAt;
}
