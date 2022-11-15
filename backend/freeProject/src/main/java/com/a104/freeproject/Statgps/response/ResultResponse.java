package com.a104.freeproject.Statgps.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponse {

    private boolean flag;
    private List<GpsResultResponse> gpsList;
    private double total_dist;
    private String time_len;
    private double avg_speed;

}
