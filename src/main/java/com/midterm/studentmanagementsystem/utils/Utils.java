package com.midterm.studentmanagementsystem.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.swing.ImageIcon;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

public class Utils<T> {
	public static void changeAvatar(File image, String email) {
		String resourcesFolderPath = "src/main/resources";
		Path userFolderPath = Paths.get(resourcesFolderPath, email);

		try {
			Files.createDirectories(userFolderPath);
		} catch (IOException ex) {
			System.out.println("Cannot create directory: " + ex.getMessage());
		}

		String imageFileName = image.getName();

		Path destinationPath = userFolderPath.resolve("avatar" + getFileExtension(imageFileName));

		try {
			Files.copy(Paths.get(image.getPath()), destinationPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static <T> List<T> importDataFromExcelFile(String filePath, Class<T> type) {
		List<T> dataList = new ArrayList<>();

		try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
			Sheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {
				if (row.getRowNum() == 0) {
					continue;
				}

				String[] rowData = new String[row.getLastCellNum()];
				SimpleDateFormat dataFormatter = new SimpleDateFormat("yyyy-MM-dd");

				for (Cell cell : row) {
					if (cell.getCellType() == CellType.NUMERIC) {
						if (DateUtil.isCellDateFormatted(cell)) {
							java.util.Date date = cell.getDateCellValue();
							Date sqlDate = new Date(date.getTime());
							String formattedDate = dataFormatter.format(sqlDate);
							rowData[cell.getColumnIndex()] = formattedDate;
						} else {
							int numericValue = (int) cell.getNumericCellValue();
							rowData[cell.getColumnIndex()] = String.valueOf(numericValue);
						}
					} else {
						rowData[cell.getColumnIndex()] = cell.toString();
					}
				}

				T object = createObjectFromRow(rowData, type);
				dataList.add(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataList;
	}

	public static <T> List<T> importDataFromCsvFile(String filePath, Class<T> type) {
		List<T> dataList = new ArrayList<>();

		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			List<String[]> data = reader.readAll();
			for (int i = 1; i < data.size(); i++) {
				T object = createObjectFromRow(data.get(i), type);
				dataList.add(object);
			}
		} catch (IOException | CsvException | NumberFormatException e) {
			e.printStackTrace();
		}

		return dataList;
	}

	private static <T> T createObjectFromRow(String[] row, Class<T> type) {
		try {
			T object = type.getDeclaredConstructor().newInstance();
			Field[] fields = type.getDeclaredFields();

			for (int i = 0; i < row.length; i++) {
				if (row[i] != null) {
					String value = row[i].trim();

					Field field = fields[i];
					field.setAccessible(true);

					try {
						if (field.getType() == int.class) {
							double doubleValue = Double.parseDouble(value);
							int intValue = (int) doubleValue;
							field.set(object, intValue);
						} else if (field.getType() == Date.class) {
							if (!value.isEmpty()) {
								field.set(object, convertToDate(value));
							}
						} else {
							field.set(object, value);
						}
					} catch (Exception e) {
						System.out.println("Error setting field " + field.getName() + " with value: " + value);
						e.printStackTrace();
					}
				}
			}

			return object;
		} catch (Exception e) {
			System.out.println("Error creating object from row");
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static <T> void exportToCSV(List<T> data, String filePath) {
		try (CSVWriter writer = new CSVWriter(new FileWriter(filePath), CSVWriter.DEFAULT_SEPARATOR,
				CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.RFC4180_LINE_END)) {
			String[] headers = getFieldNames(data.get(0).getClass());
			writer.writeNext(headers);

			for (T item : data) {
				writer.writeNext(convertToStringArray(item));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static <T> String[] convertToStringArray(T item) {
		Class<?> clazz = item.getClass();
		Field[] fields = clazz.getDeclaredFields();

		String[] values = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			try {
				Object value = fields[i].get(item);
				values[i] = (value != null) ? value.toString() : "";
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return values;
	}

	public static <T> void exportToExcel(List<T> data, String filePath) {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Sheet1");

			Row headerRow = sheet.createRow(0);
			String[] headers = getFieldNames(data.get(0).getClass());
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			for (int rowIndex = 0; rowIndex < data.size(); rowIndex++) {
				Row row = sheet.createRow(rowIndex + 1); // Start from row 1 (after header)

				T item = data.get(rowIndex);
				String[] values = convertToStringArray(item);

				for (int cellIndex = 0; cellIndex < values.length; cellIndex++) {
					Cell cell = row.createCell(cellIndex);
					cell.setCellValue(values[cellIndex]);
				}
			}

			try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
				workbook.write(fileOut);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static <T> String[] getFieldNames(Class<T> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		String[] fieldNames = new String[fields.length];

		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}

		return fieldNames;
	}

	public static boolean isValidDate(String inputDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);

		try {
			java.util.Date date = dateFormat.parse(inputDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int month = calendar.get(Calendar.MONTH) + 1; // Months are zero-based
			int year = calendar.get(Calendar.YEAR);

			if (day < 1 || day > 31) {
				return false;
			}

			if (month < 1 || month > 12) {
				return false;
			}

			if (year < 1900 || year > 2300) {
				return false;
			}

			boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

			if (month == 2) {
				if (isLeapYear && (day < 1 || day > 29)) {
					return false;
				} else if (!isLeapYear && (day < 1 || day > 28)) {
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static int calculateAge(Date dob) {
		if (dob == null) {
			return -1;
		}

		Calendar dobCalendar = Calendar.getInstance();
		dobCalendar.setTime(dob);

		Calendar currentCalendar = Calendar.getInstance();

		int age = currentCalendar.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);

		if (currentCalendar.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}

		return age;
	}

	public static Date convertToDate(String dateString) {
		List<String> patterns = Arrays.asList("yyyy-MM-dd", "M/d/yy", "MM/dd/yy", "MM/dd/yyyy", "M/d/yyyy",
				"dd-MM-yyyy");

		for (String pattern : patterns) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
				LocalDate localDate = LocalDate.parse(dateString, formatter);
				return Date.valueOf(localDate);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(dateString);
			}
		}
		return null;
	}

	public static String getFileExtension(String fileName) {
		int dotIndex = fileName.indexOf(".");
		return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
	}

	public static boolean checkImageFile(File imageFile) {
		try {
			String mimeType = Files.probeContentType(imageFile.toPath());

			if (mimeType != null && mimeType.split("/")[0].equals("image")) {
				return true;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public static ImageIcon stretchImage(BufferedImage image, int width, int height) {
		Image stretchedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon imgIcon = new ImageIcon(stretchedImage);

		return imgIcon;
	}
}
