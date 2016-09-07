package by.epam.karotki.film_rating.service.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ServiceUtils {
	
	public static void saveFromRequestFile(InputStream is,String path){
		OutputStream os = null;
		try{
			os = new FileOutputStream(new File(path));
			int i = -1;
			while((i=is.read())!=-1){
				os.write(i);
			}
			
		}catch (IOException e){
			System.out.println("IOException during save file ");
		}finally{
			try {
				os.close();
			} catch (IOException e) {
				// log
			} 
		}
		
	}

}
