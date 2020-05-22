package cn.eli.vue.service;


import cn.eli.vue.api.CommonResult;
import cn.eli.vue.entity.Tax;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TaxService {

	List<Tax> selectUsers(String user_name);


	CommonResult batchImport(String fileName, MultipartFile file, String user_name) throws IOException;
}
