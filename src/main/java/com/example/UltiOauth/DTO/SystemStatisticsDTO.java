package com.example.UltiOauth.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemStatisticsDTO {
    private long userCount = 0;
    private long repoCount = 0;
    private long noteCount = 0;
    private LocalDate lastUpdate =  LocalDate.now();
    String receiver;
}
