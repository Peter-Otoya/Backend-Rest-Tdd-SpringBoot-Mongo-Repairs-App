package com.otoya.pedro.mechanicworkshop.domain.workstation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.otoya.pedro.mechanicworkshop.domain.repair.RepairMetadata;
import com.otoya.pedro.mechanicworkshop.domain.schedule.Schedule;
import com.otoya.pedro.mechanicworkshop.domain.vehicle.VehicleMetadata;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "workstations")
@JsonDeserialize(as= Workstation.class)
@Getter
@Setter
@ToString
public class Workstation {
    @Id
    private Long id;
    private List<VehicleMetadata.Category> vehiclesAccepted;
    private List<RepairMetadata.Type> repairTypesAccepted;
    private Schedule schedule;
}
