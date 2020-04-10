package main;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
public class Count_File_SFTP_OSE {
	
	private static Logger logger = Logger.getLogger(Count_File_SFTP_OSE.class);
	static List<String> ListaSFTP = new ArrayList<String>();
public static void main(String[] args) {
	
	// ENVIAR TODO DE LA RUTA VIDEOS to Server AP1
	try {
		
		//XML	
		
//	String script = "scp -r -i llave_acalcina /home/acalcina/Escritorio/20557813463/* acalcina@104.130.201.179:/var/efact/sftp/20557813463/Out/";	
	
 String script ="acalcina@104.130.201.179:/var/efact/sftp/20511670072/Out/";
		
//							scp -i /home/acalcina/llave_acalcina /home/acalcina/Vídeos/* acalcina@23.253.207.57:/opt/efact/ose/storage_smb/INVOICE/		
	logger.info(script);
	Process process = Runtime.getRuntime().exec(script);
	int exitValJarS1 = process.waitFor();
			
			logger.info("SEARCH complete server 1 >>>");	
	
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
	
	 File archivos1 = new File("/home/acalcina/Escritorio/ListaSFTP.txt");
	 FileWriter fichero1=null;
	 PrintWriter pw1=null;		
	 try {
		if(archivos1.createNewFile()){
			logger.info("txt creado");
		 }	
			fichero1=new FileWriter(archivos1);
			pw1= new PrintWriter(fichero1);
			for(int it = 0; it < ListaSFTP.size(); it++){
				pw1.println(ListaSFTP.get(it));
			}
		}catch (Exception e) {
				e.printStackTrace();
		}finally {
			try{
				if(null != fichero1)
					fichero1.close();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
}

}
