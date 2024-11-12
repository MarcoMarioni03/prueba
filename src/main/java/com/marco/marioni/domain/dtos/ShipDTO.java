package com.marco.marioni.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShipDTO {
    private Integer id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public ShipDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
