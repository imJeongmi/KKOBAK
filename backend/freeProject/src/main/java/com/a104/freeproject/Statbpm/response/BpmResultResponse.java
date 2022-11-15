package com.a104.freeproject.Statbpm.response;

import com.a104.freeproject.Statgps.response.GpsResultResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmResultResponse {
    private boolean flag;
    private List<BpmChangeForm> bpmList;
    private int maxBpm;
    private int minBpm;
    private int avgBpm;
}
