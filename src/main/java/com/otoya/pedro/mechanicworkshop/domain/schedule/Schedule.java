package com.otoya.pedro.mechanicworkshop.domain.schedule;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@JsonDeserialize(as = Schedule.class)
@Getter
@Setter
@ToString
public class Schedule {
    /*      M       Tu     W    Th    F
     * [ [1,3,5] ,[2,4], [6], [7,8], [9] ] => This has the order ids planned for all the week.
     */
    private List<List<Long>> dailySchedule;

}
