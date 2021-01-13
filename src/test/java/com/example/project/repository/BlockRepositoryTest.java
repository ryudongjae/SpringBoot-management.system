package com.example.project.repository;


import com.example.project.domain.Block;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class BlockRepositoryTest {
        @Autowired
        private BlockRepository blockRepository;

        @Test
        void crud(){
            Block block = new Block();
            block.setName("martin");
            block.setReason("친하지 않아서");
            block.setStartDate(LocalDate.now());
            block.setEndData(LocalDate.now());

            List<Block> blocks = blockRepository.findAll();

        }


}

