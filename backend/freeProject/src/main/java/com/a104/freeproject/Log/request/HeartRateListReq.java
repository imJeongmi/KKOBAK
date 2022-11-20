package com.a104.freeproject.Log.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HeartRateListReq {
    private List<Integer> heartRateList;
}
