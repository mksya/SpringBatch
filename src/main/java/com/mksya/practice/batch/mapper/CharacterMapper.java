package com.mksya.practice.batch.mapper;

import com.mksya.practice.batch.dto.CharacterDTO;
import com.mksya.practice.domain.Character;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CharacterMapper {

    Character mapToEntity(CharacterDTO characterDTO);

}
