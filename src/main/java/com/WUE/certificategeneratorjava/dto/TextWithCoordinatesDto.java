package com.WUE.certificategeneratorjava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextWithCoordinatesDto {
    private String text;
    private int x;
    private int y;
}
