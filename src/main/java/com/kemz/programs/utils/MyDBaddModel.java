package com.kemz.programs.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyDBaddModel implements CommandLineRunner {

    private final JdbcTemplate template;

    @Override
    public void run(String... args) throws Exception {

        for (int i = 1; i < 50; i++) {
            template.execute("INSERT INTO Detail(cipher, name) VALUES ('120.8700." + i + "', 'Деталь')");
        }

        for (int i = 1; i < 7; i++) {
            template.execute("INSERT INTO Program(detail_id, index, code_haas, type) values (1, '" + i + "', 'G0 G28 WO. XO.', 'токарная')");
        }

        for (int i = 1; i < 20; i++) {
            template.execute("INSERT INTO Tool(name, cipher) values ('Сверло', 'СТЦ 512 "+ i +"х8х26 HF AlTin')");
        }

        for (int i = 1; i < 10; i++) {
            template.execute("INSERT INTO tool_program values (1, "+ i +")");
        }


    }
}
