package com.example.Task_Management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Страница со списком задач")
public class PageTaskResponse {
    @Schema(description = "Количество страниц c задачами", example = "5")
    private int totalPage;
    @Schema(description = "Количество задач", example = "15")
    private long totalElement;
    @Schema(description = "Текущая страница", example = "1", minimum = "1")
    private int pageNumber;
    @Schema(description = "Список задач")
    private List<TaskDto> tasks;
}
