package com.midterm.studentmanagementsystem.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ImageIcon;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Utils {
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
	
	public static <T> List<T> importDataToCsvFile(String filePath, Class<T> clazz) {
		List<T> data = new ArrayList<>();
		Path path = Paths.get(filePath);

		try (Reader reader = new FileReader(path.toFile()); CSVParser csvParser = CSVFormat.DEFAULT.parse(reader)) {
			String[] headers = csvParser.getHeaderMap().keySet().toArray(new String[0]);

			for (CSVRecord csvRecord : csvParser) {
				Constructor<T> constructor;
				try {
					constructor = clazz.getDeclaredConstructor();
					T obj = constructor.newInstance();

					for (int i = 0; i < headers.length; i++) {
						String header = headers[i];
						String value = csvRecord.get(i);

						try {
							Field field = clazz.getDeclaredField(header);
							field.setAccessible(true);

							field.set(obj, value);
						} catch (NoSuchFieldException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return Date.valueOf(localDate);
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
