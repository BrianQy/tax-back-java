package cn.eli.vue.controller;

import cn.eli.vue.api.CommonResult;
import cn.eli.vue.entity.Tax;
import cn.eli.vue.service.RedisService;
import cn.eli.vue.service.TaxService;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
public class IndexController {

	@Autowired
	private TaxService taxService;

	@Autowired
	private RedisService redisService;

	@RequestMapping("/example")
	public String showUser() {
		return "example";
	}

	//@RequestMapping(value = "/calculate/{username}",method = RequestMethod.GET)
	//public String showUserForAdmin(@PathVariable String username,Model model) {
	//	System.out.println(username+"is working here now.");
	//	List<User> users = userService.selectUsers(username);
	//	model.addAttribute("user", users);
		//return "calculate";
	//	return "calculate";
	//}


		@RequestMapping(value = "/export",method = RequestMethod.POST)
		@ResponseBody
		//public CommonResult export(@RequestParam(value = "token",required = true)String token,HttpServletResponse response) throws IOException {
		public CommonResult export(@RequestHeader("token")String token,HttpServletResponse response) throws IOException {
		String username = redisService.get(token).toString();
		List<Tax> users = taxService.selectUsers(username);

		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = wb.createSheet("当月员工工资情况");

		HSSFRow row = null;

		row = sheet.createRow(0);//创建第一个单元格
		row.setHeight((short) (26.25 * 20));
		row.createCell(0).setCellValue("个税填报表");//为第一行单元格设值

		/*为标题设计空间
		* firstRow从第1行开始
		* lastRow从第0行结束
		*
		*从第1个单元格开始
		* 从第3个单元格结束
		*/
		CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 2);
		sheet.addMergedRegion(rowRegion);

		/*CellRangeAddress columnRegion = new CellRangeAddress(1,4,0,0);
		sheet.addMergedRegion(columnRegion);*/


		/*
		* 动态获取数据库列 sql语句 select COLUMN_NAME from INFORMATION_SCHEMA.Columns where table_name='user' and table_schema='test'
		* 第一个table_name 表名字
		* 第二个table_name 数据库名称
		* */
		row = sheet.createRow(1);
		row.setHeight((short) (22.50 * 20));//设置行高
		row.createCell(0).setCellValue("员工ID");//为第一个单元格设值
		row.createCell(1).setCellValue("员工姓名");//为第二个单元格设值
		row.createCell(2).setCellValue("税前工资");//为第三个单元格设值
		row.createCell(3).setCellValue("三险一金");//为第三个单元格设值
		row.createCell(4).setCellValue("免税收入");//为第三个单元格设值
		row.createCell(5).setCellValue("专项扣除");//为第三个单元格设值
		row.createCell(6).setCellValue("其他扣除");//为第三个单元格设值
		row.createCell(7).setCellValue("扣除个税");//为第三个单元格设值
		row.createCell(8).setCellValue("税后收入");//为第三个单元格设值
		for (int i = 0; i < users.size(); i++) {
			row = sheet.createRow(i + 2);
			Tax user = users.get(i);
			row.createCell(0).setCellValue(user.getUid());
			row.createCell(1).setCellValue(user.getUsername());
			row.createCell(2).setCellValue(user.getTaxBefore());
			row.createCell(3).setCellValue(user.getSxyj());
			row.createCell(4).setCellValue(user.getNoTax());
			row.createCell(5).setCellValue(user.getZxkc());
			row.createCell(6).setCellValue(user.getQtkc());
			row.createCell(7).setCellValue(user.getTax());
			row.createCell(8).setCellValue(user.getTaxAfter());


		}
		sheet.setDefaultRowHeight((short) (16.5 * 20));
		//列宽自适应
		for (int i = 0; i <= 13; i++) {
			sheet.autoSizeColumn(i);
		}

		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		OutputStream os = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment;filename=UserTax.xls");//默认Excel名称
		wb.write(os);
		os.flush();
		os.close();
		return CommonResult.success("UserTax_import",token);
	}


	@RequestMapping(value = "/template")
	@ResponseBody
	public void template(HttpServletResponse response) throws IOException {
		//List<User> users = userService.selectUsers();

		HSSFWorkbook wb1 = new HSSFWorkbook();

		HSSFSheet sheet1 = wb1.createSheet("当月员工工资情况");

		HSSFRow row = null;

		row = sheet1.createRow(0);//创建第一个单元格
		row.setHeight((short) (26.25 * 20));
		row.createCell(0).setCellValue("个税填报表（上传模版）");//为第一行单元格设值
		row.createCell(3).setCellValue("请删除示例，并填写十个以上员工信息");//为第一行单元格设值

		/*为标题设计空间
		 * firstRow从第1行开始
		 * lastRow从第0行结束
		 *
		 *从第1个单元格开始
		 * 从第3个单元格结束
		 */
		CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 2);
		sheet1.addMergedRegion(rowRegion);
		CellRangeAddress rowRegion1 = new CellRangeAddress(0, 0, 3, 6);
		sheet1.addMergedRegion(rowRegion1);
		/*CellRangeAddress columnRegion = new CellRangeAddress(1,4,0,0);
		sheet.addMergedRegion(columnRegion);*/


		/*
		 * 动态获取数据库列 sql语句 select COLUMN_NAME from INFORMATION_SCHEMA.Columns where table_name='user' and table_schema='test'
		 * 第一个table_name 表名字
		 * 第二个table_name 数据库名称
		 * */
		row = sheet1.createRow(1);
		row.setHeight((short) (22.50 * 20));//设置行高
		row.createCell(0).setCellValue("员工ID");//为第一个单元格设值
		row.createCell(1).setCellValue("员工姓名");//为第二个单元格设值
		row.createCell(2).setCellValue("税前工资");//为第三个单元格设值
		row.createCell(3).setCellValue("三险一金");//为第三个单元格设值
		row.createCell(4).setCellValue("免税收入");//为第三个单元格设值
		row.createCell(5).setCellValue("专项扣除");//为第三个单元格设值
		row.createCell(6).setCellValue("其他扣除");//为第三个单元格设值
		row.createCell(7).setCellValue("扣除个税");//为第三个单元格设值
		row.createCell(8).setCellValue("税后收入");//为第三个单元格设值

			row = sheet1.createRow(2);
			//User user = users.get(i);
			row.createCell(0).setCellValue(1);
			row.createCell(1).setCellValue("Trump");
			row.createCell(2).setCellValue("666666");
			row.createCell(3).setCellValue("1000");
			row.createCell(4).setCellValue("10000");
			row.createCell(5).setCellValue("500");
			row.createCell(6).setCellValue("1000");
			row.createCell(7).setCellValue("请勿填写");
			row.createCell(8).setCellValue("请勿填写");



		sheet1.setDefaultRowHeight((short) (16.5 * 20));
		//列宽自适应
		for (int i = 0; i <= 13; i++) {
			sheet1.autoSizeColumn(i);
		}

		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		OutputStream os = response.getOutputStream();
		response.setHeader("Content-disposition", "attachment;filename=UserTax_template.xls");//默认Excel名称
		wb1.write(os);
		os.flush();
		os.close();


	}

	@RequestMapping(value = "/import/",method = RequestMethod.POST)
	@ResponseBody
	public String exImport(@RequestHeader("token") String token,@RequestParam(value = "filename") MultipartFile file) {

		//boolean a = false;
		String username = redisService.get(token).toString();
		String fileName = file.getOriginalFilename();

		try {
			 taxService.batchImport(fileName, file,username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return username;
	}




	/**
	 * 获取样式
	 *
	 * @param hssfWorkbook
	 * @param styleNum
	 * @return
	 */
	public HSSFCellStyle getStyle(HSSFWorkbook hssfWorkbook, Integer styleNum) {
		HSSFCellStyle style = hssfWorkbook.createCellStyle();
		style.setBorderRight(BorderStyle.THIN);//右边框
		style.setBorderBottom(BorderStyle.THIN);//下边框

		HSSFFont font = hssfWorkbook.createFont();
		font.setFontName("微软雅黑");//设置字体为微软雅黑

		HSSFPalette palette = hssfWorkbook.getCustomPalette();//拿到palette颜色板,可以根据需要设置颜色
		switch (styleNum) {
			case (0): {//HorizontalAlignment
				style.setAlignment(HorizontalAlignment.CENTER_SELECTION);//跨列居中
				font.setBold(true);//粗体
				font.setFontHeightInPoints((short) 14);//字体大小
				style.setFont(font);
				palette.setColorAtIndex(HSSFColor.BLUE.index, (byte) 184, (byte) 204, (byte) 228);//替换颜色板中的颜色
				style.setFillForegroundColor(HSSFColor.BLUE.index);
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			}
			break;
			case (1): {
				font.setBold(true);//粗体
				font.setFontHeightInPoints((short) 11);//字体大小
				style.setFont(font);
			}
			break;
			case (2): {
				font.setFontHeightInPoints((short) 10);
				style.setFont(font);
			}
			break;
			case (3): {
				style.setFont(font);

				palette.setColorAtIndex(HSSFColor.GREEN.index, (byte) 0, (byte) 32, (byte) 96);//替换颜色板中的颜色
				style.setFillForegroundColor(HSSFColor.GREEN.index);
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			}
			break;
		}

		return style;
	}

}
