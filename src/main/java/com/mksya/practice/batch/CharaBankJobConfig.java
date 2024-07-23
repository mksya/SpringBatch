package com.mksya.practice.batch;

import com.mksya.practice.batch.dto.CharacterDTO;
import com.mksya.practice.batch.processor.CharaItemProcessor;
import com.mksya.practice.domain.Character;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class CharaBankJobConfig {

    private final JobBuilder jobBuilder;
    private final StepBuilder stepBuilder;
    private final EntityManagerFactory entityManagerFactory;
    private final PlatformTransactionManager platformTransactionManager;

    private final CharaItemProcessor itemProcessor;

    public Job importCharacters(){
        return jobBuilder
                .incrementer(new RunIdIncrementer())
                .start(fromFileToDataBase())
                .build();
    }
    

    public Step fromFileToDataBase() {
        return stepBuilder
                .chunk(10, platformTransactionManager)
                .reader(charaBankFileReader())
                .processor(item -> itemProcessor)
                .writer(chunk -> characterJpaItemWriter())
                .build();
    }


    @Bean
    public FlatFileItemReader<CharacterDTO> charaBankFileReader() {
        return new FlatFileItemReaderBuilder<CharacterDTO>()
                .resource(new ClassPathResource("/data/data.csv"))
                .name("charaBankFileReader")
                .delimited()
                .delimiter(",")
                .names("Name", "Race", "Sex")
                .linesToSkip(1)
                .targetType(CharacterDTO.class)
                .build();
    }

    @Bean
    public void characterJpaItemWriter() {
        new JpaItemWriterBuilder<Character>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }


}
