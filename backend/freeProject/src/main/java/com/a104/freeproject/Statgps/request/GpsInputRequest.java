package com.a104.freeproject.Statgps.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GpsInputRequest {

    private Long chlId;

    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime time;

    private String lat;

    private String lng;

    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime chk;

}
