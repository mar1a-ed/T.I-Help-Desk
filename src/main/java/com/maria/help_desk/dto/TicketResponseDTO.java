package com.maria.help_desk.dto;

import com.maria.help_desk.model.Category;
import com.maria.help_desk.model.Priority;
import com.maria.help_desk.model.Status;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketResponseDTO extends RepresentationModel<TicketResponseDTO> {

    private Long id;

    private Long userId;

    private String title;

    private Category category;

    private Status status;

    private Priority priority;

    private LocalDateTime requestedAt;

    private LocalDateTime updatedAt;
}
