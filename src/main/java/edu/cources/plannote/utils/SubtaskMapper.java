package edu.cources.plannote.utils;

import edu.cources.plannote.dto.SubtaskDto;
import edu.cources.plannote.entity.SubtaskEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SubtaskMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSubtaskFromDto(SubtaskDto subtaskDto, @MappingTarget SubtaskEntity subtask);
}
