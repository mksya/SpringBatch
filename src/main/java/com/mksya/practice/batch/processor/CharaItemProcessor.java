package com.mksya.practice.batch.processor;

import com.mksya.practice.batch.dto.CharacterDTO;
import com.mksya.practice.batch.mapper.CharacterMapper;
import com.mksya.practice.domain.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharaItemProcessor implements ItemProcessor<CharacterDTO, Character> {

    private CharacterMapper characterMapper;

    @Override
    public Character process(CharacterDTO item) throws Exception {
        return characterMapper.mapToEntity(item);
    }
}
