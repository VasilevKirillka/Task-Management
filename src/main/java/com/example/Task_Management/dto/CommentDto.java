package com.example.Task_Management.dto;

import com.example.Task_Management.entity.TaskEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Комментарии к задаче")
public class CommentDto {
    @Schema(description = "Идентификатор", example = "1")
    private Long id;
    @Schema(description = "Комментарий", example = "Где взять деньги на самолет?")
    @NotNull(message = "Поле comment не должно быть пустым")
    private String comment;
    @Schema(description = "Идентификатор задачи", example = "1")
    @NotNull(message = "Поле taskId не должно быть пустым")
    private Long taskId;
}
