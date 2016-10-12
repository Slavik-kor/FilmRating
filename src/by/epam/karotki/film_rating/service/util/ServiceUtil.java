package by.epam.karotki.film_rating.service.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.karotki.film_rating.entity.Comment;

public class ServiceUtil {
	private static final Logger LOG = LogManager.getLogger();

	public static void saveFromRequestFile(InputStream is, String path) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(path));
			int i = -1;
			while ((i = is.read()) != -1) {
				os.write(i);
			}

		} catch (IOException e) {
			LOG.error("IOException during save file ");
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				LOG.error("can't close OutputSream");
			}
		}

	}

	public static String[] intListToStringArray(List<Integer> intList) {
		try {
			String[] stringArray = new String[intList.size()];
			for (int i = 0; i < stringArray.length; i++) {
				stringArray[i] = String.valueOf(intList.get(i));
			}
			return stringArray;
		} catch (NullPointerException e) {
			return null;
		}

	}
	
	public static double avg (List<Comment> list){
		double value = 0.0;
		double sum = 0.0;
		for(Comment i : list){
			sum += i.getRate();
		}
		value = sum/list.size();
		return value;
	}

}
