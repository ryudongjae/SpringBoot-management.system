package com.example.project.repository;


import com.example.project.domain.Block;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest

class BlockRepositoryTest {
    @Autowired//각 상황의 타입에 맞는 ioC컨테이너 안에 존재하는 Bean을 자동으로 주입해준다.
    private BlockRepository blockRepository;


    @Test
    void crud(){
        Block block = new Block();
        block.setName("cris");
        block.setReason("친하지 않아서");
        block.setStartDate(LocalDate.now());
        block.setEndData(LocalDate.now());

        blockRepository.save(block);

        List<Block> blocks = blockRepository.findAll();

        assertThat(blocks.size()).isEqualTo(1);
        assertThat(blocks.get(0).getName()).isEqualTo("cris");



    }



}

