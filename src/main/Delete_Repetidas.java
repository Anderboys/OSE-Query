package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Delete_Repetidas {
	private static final Logger LOGGER = LoggerFactory.getLogger(Delete_Repetidas.class);
	
	static List<String> Lista = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException {
		
		ArrayList<String> identifiers = new ArrayList<>();

		identifiers.add("AAAA-00000001");
		identifiers.add("AAAA-00000002");
		
//		FileReader fileData= new FileReader("/home/acalcina/Escritorio/NotaCredito.txt");
//		
//		BufferedReader bf = new BufferedReader(fileData);
//		String sCadena="";
//		 while ((sCadena = bf.readLine())!=null) {
//			
//			 identifiers.add(sCadena);			 
//			 System.out.println(sCadena);
//		 }

		 //total2
		 ArrayList<String> identifiers2 = new ArrayList<>();
		

		 	identifiers2.add("AAAA-00000001");
		 	identifiers2.add("AAAA-00000002");
		 	identifiers2.add("BBBB-00000002");
		 	identifiers2.add("BBBB-00000003");

		 	
		 	int lista1 = identifiers.size();
		 	int lista2 = identifiers2.size(); 	
		 	
		 	
		 	for(int i=0; i < lista2;i++){
				
		 		System.out.println(identifiers2.get(i));
		 		
		 		
		 		for(int j=0; j < lista1;j++){
		 			
		 			System.out.println("LIsta 1"+identifiers.get(j));
		 			
		 			if(identifiers2.get(i).equals(identifiers.get(j))){
			 			System.out.println("repetido");
			 		}else{
			 			System.out.println("No repetido");
			 			Lista.add(identifiers2.get(i));
			 		}
		 		}
			}

		
		// ------------------------- TXT ---------------------------------------
		 File archivos1 = new File("/home/acalcina/Escritorio/Norepetidas.txt");
		 FileWriter fichero1=null;
		 PrintWriter pw1=null;		
		 try {
			if(archivos1.createNewFile()){
				LOGGER.info("txt creado");
			 }	
				fichero1=new FileWriter(archivos1);
				pw1= new PrintWriter(fichero1);
				for(int it = 0; it < Lista.size(); it++){
					pw1.println(Lista.get(it));
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
		// END ------------------------- TXT ---------------------------------------
		
	}
}
