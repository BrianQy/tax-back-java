package cn.eli.vue.service.Impl;

import cn.eli.vue.api.CommonResult;
import cn.eli.vue.entity.Tax;
import cn.eli.vue.mapper.TaxMapper;
import cn.eli.vue.service.TaxService;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service("taxService")
public class TaxServiceImpl implements TaxService {


	@Autowired
	private TaxMapper taxMapper;


	@Override
	public List<Tax> selectUsers(String user_name) {
		return taxMapper.selectUsers(user_name);
	}


	@Transactional(readOnly = false,rollbackFor = Exception.class)
	@Override
	public CommonResult batchImport(String fileName, MultipartFile file, String user_name) throws IOException {
		boolean notNull = false;
		List<Tax> userList = new ArrayList<>();
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			//throw new MyException("上传文件格式不正确");
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		InputStream is = file.getInputStream();
		Workbook wb = null;
		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new XSSFWorkbook(is);
		}
		Sheet sheet = wb.getSheetAt(0);
		if (sheet != null) {
			notNull = true;
		}
		Tax user;
		for (int r = 2; r <= sheet.getLastRowNum(); r++) {//r = 2 表示从第三行开始循环 如果你的第三行开始是数据
			Row row = sheet.getRow(r);//通过sheet表单对象得到 行对象
			if (row == null) {
				continue;
			}

			//sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException

			user = new Tax();

			//if( row.getCell(0).getCellType() !=1){//循环时，得到每一行的单元格进行判断
			//	throw new MyException("导入失败(第"+(r+1)+"行,用户名请设为文本格式)");
			//}
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			String uid = row.getCell(0).getStringCellValue();//得到每一行第一个单元格的值
			int uid_int = Integer.parseInt(uid);
			if (uid == null || uid.isEmpty()) {//判断是否为空
				//throw new MyException("导入失败(第"+(r+1)+"行,用户名未填写)");
				return CommonResult.failed("导入失败(第\"+(r+1)+\"行,用户名未填写)");
			}
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String username = row.getCell(1).getStringCellValue();//得到每一行第一个单元格的值
			if (username == null || username.isEmpty()) {//判断是否为空
				//throw new MyException("导入失败(第"+(r+1)+"行,用户名未填写)");
				return CommonResult.failed("导入失败(第" + (r + 1) + "行,用户名未填写)");
			}

			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第二个单元格的值

			String taxBefore = row.getCell(2).getStringCellValue();
			if (taxBefore == null || taxBefore.isEmpty()) {//判断是否为空
				//throw new MyException("导入失败(第"+(r+1)+"行,未填写)");
				return CommonResult.failed("导入失败(第" + (r + 1) + "行,未填写)");
			}
			row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第二个单元格的值

			String sxyj = row.getCell(3).getStringCellValue();
			if (sxyj == null || sxyj.isEmpty()) {
				//	throw new MyException("导入失败(第"+(r+1)+"行,未填写)");
				return CommonResult.failed("导入失败(第" + (r + 1) + "行,未填写)");
			}

			row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
			String noTax = row.getCell(4).getStringCellValue();
			if (noTax == null || noTax.isEmpty()) {
				return CommonResult.failed("导入失败(第" + (r + 1) + "行,未填写)");
				//throw new MyException("导入失败(第"+(r+1)+"行,未填写)");
			}

			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			String zxkc = row.getCell(5).getStringCellValue();
			if (zxkc == null || zxkc.isEmpty()) {
				return CommonResult.failed("导入失败(第" + (r + 1) + "行,未填写)");
				//throw new MyException("导入失败(第"+(r+1)+"行,未填写)");
			}

			row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
			String qtkc = row.getCell(6).getStringCellValue();
			if (qtkc == null || qtkc.isEmpty()) {
				return CommonResult.failed("导入失败(第" + (r + 1) + "行,未填写)");
				//throw new MyException("导入失败(第"+(r+1)+"行,未填写)");
			}
			int a = Integer.parseInt(taxBefore);
			int b = Integer.parseInt(sxyj);
			int c = Integer.parseInt(noTax);
			int d = Integer.parseInt(zxkc);
			int e = Integer.parseInt(qtkc);
			int fare = a - b - c - d - e - 5000;

			String taxfare;
			String withouttax;
			if (fare <= 36000) {
				double ddd = fare * 0.03;
				ddd = (double) Math.round(ddd * 100) / 100;
				taxfare = String.valueOf(ddd);
				withouttax = String.valueOf(a - ddd);
				user.setTax(taxfare);
				user.setTaxAfter(withouttax);
			} else if (fare <= 144000) {
				double ddd = 2520 + (fare - 36000) * 0.1;
				ddd = (double) Math.round(ddd * 100) / 100;
				taxfare = String.valueOf(ddd);
				withouttax = String.valueOf(a - ddd);
				user.setTax(taxfare);
				user.setTaxAfter(withouttax);
			} else if (fare <= 300000) {
				double ddd = 16920 + (fare - 144000) * 0.2;
				ddd = (double) Math.round(ddd * 100) / 100;
				taxfare = String.valueOf(ddd);
				withouttax = String.valueOf(a - ddd);
				user.setTax(taxfare);
				user.setTaxAfter(withouttax);
			} else if (fare <= 420000) {
				double ddd = 31920 + (fare - 300000) * 0.25;
				ddd = (double) Math.round(ddd * 100) / 100;
				taxfare = String.valueOf(ddd);
				withouttax = String.valueOf(a - ddd);
				user.setTax(taxfare);
				user.setTaxAfter(withouttax);
			} else if (fare <= 660000) {
				double ddd = 52920 + (fare - 420000) * 0.3;
				ddd = (double) Math.round(ddd * 100) / 100;
				taxfare = String.valueOf(ddd);
				withouttax = String.valueOf(a - ddd);
				user.setTax(taxfare);
				user.setTaxAfter(withouttax);
			} else if (fare <= 960000) {
				double ddd = 85920 + (fare - 660000) * 0.35;
				ddd = (double) Math.round(ddd * 100) / 100;
				taxfare = String.valueOf(ddd);
				withouttax = String.valueOf(a - ddd);
				user.setTax(taxfare);
				user.setTaxAfter(withouttax);
			} else {
				double ddd = 181920 + (fare - 960000) * 0.45;
				ddd = (double) Math.round(ddd * 100) / 100;
				taxfare = String.valueOf(ddd);
				withouttax = String.valueOf(a - ddd);
				user.setTax(taxfare);
				user.setTaxAfter(withouttax);
			}
			//完整的循环一次 就组成了一个对象
			user.setUid(uid_int);
			user.setUsername(username);
			user.setTaxBefore(taxBefore);
			user.setSxyj(sxyj);
			user.setNoTax(noTax);
			user.setZxkc(zxkc);
			user.setQtkc(qtkc);
			user.setUser_name(user_name);
			userList.add(user);
		}
		for (Tax userResord : userList) {
			String name = userResord.getUsername();
			int cnt = taxMapper.selectByName(name, user_name);//需要两个参数！！！！！！
			if (cnt == 0) {
				taxMapper.addUser(userResord, user_name);
				System.out.println(" 插入 " + userResord);
			} else {
				taxMapper.updateUserByName(userResord, user_name);
				System.out.println(" 更新 " + userResord);
			}
		}
	return CommonResult.success("true","导入成功",null);
	}

}
