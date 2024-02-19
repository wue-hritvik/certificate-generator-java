package com.WUE.certificategeneratorjava.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CertificateRequestDto {

    public List <String> name;
    public String drillName;
}
