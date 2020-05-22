package cn.eli.vue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"cn.eli.vue.mapper"})
public class  VueLoginJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VueLoginJavaApplication.class, args);
	}

}
