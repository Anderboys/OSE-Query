package main;
import java.util.*;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;

import config.DBConfig;
import oasis.names.specification.ubl.schema.xsd.creditnote_2.CreditNoteType;
import oasis.names.specification.ubl.schema.xsd.debitnote_2.DebitNoteType;
import pe.com.efact.db.constants.ICollections;
import sunat.names.specification.ubl.peru.schema.xsd.perceptiondocuments_1.PerceptionType;
import sunat.names.specification.ubl.peru.schema.xsd.retentiondocuments_1.RetentionType;
import sunat.names.specification.ubl.peru.schema.xsd.summarydocuments_1.SummaryDocumentsType;
import sunat.names.specification.ubl.peru.schema.xsd.voideddocuments_1.VoidedDocumentsType;
import util.Util;
public class ValidadorCPE {
	
	
	private static Object RUC = "";
	private static Object TYPE = "";
	

	private static int conteo = 0;

	public static void main(String[] args) {
		try {			
		
		DBConfig dbConfig = new DBConfig();
		MongoDatabase dbW = dbConfig.connectionWeb();
		MongoDatabase dbO = dbConfig.connectionOdin();
		List<String> identifiers = new ArrayList<String>();
		boolean valCpe=false;
		RUC=args[0];
		TYPE=args[1];
		String docs[] =args[2].split(",");
		
		for(int i=0;i<docs.length;i++) {
			String id=docs[i];
			
			if(TYPE.equals("16")) {
				String[] ids=id.split("-");
				
				id=ids[0]+"-"+ids[1]+"-"+(Integer.parseInt(ids[2]));
				
			}
			
			
			if(id.startsWith("B")&&(TYPE.equals("07")||TYPE.equals("08")))
				valCpe=true;
			identifiers.add(id);
		}

//		try {
//			Thread.sleep(1000);
//		}catch(Exception e) {}
		System.out.println("============================================================================");
		if(TYPE.equals("01") || TYPE.equals("03")) {
			for(int i=0;i<identifiers.size();i++) {
				String ID=identifiers.get(i);
				try {
				String[] ids=identifiers.get(i).split("-");

				String documentTypeCode=TYPE.toString();
				Bson PartyIdentification = new Document("PartyIdentification",RUC);
				Bson DocumentType = new Document("DocumentType",documentTypeCode);
	//			System.out.println(documentReference);
				Bson Series = new Document("Series",ids[0]);
				Bson Number = new Document("Number",(Integer.parseInt(ids[1]))+"");
				PartyIdentification = Filters.and(PartyIdentification, DocumentType);
				PartyIdentification = Filters.and(PartyIdentification, Series);
				PartyIdentification = Filters.and(PartyIdentification, Number);
				MongoCursor<Document> CPE = dbO.getCollection("CPE").find(PartyIdentification).iterator();
				int count=0;
				while (CPE.hasNext()) {
					CPE.next();
					count++;
				}
//				count=0;
				if(count>0) {
					System.out.println("["+RUC+"]-["+documentTypeCode+"]-["+ID+"], Existe PADRONES, "+(count>1?"DUPLICADO":"OK"));
//					result.add("["+RUC+"]-["+documentTypeCode+"]-["+ID+"], Existe, "+(count>1?"DUPLICADO":"OK"));
					conteo++;
				}else {
					Bson fRusR = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);
					Bson fTypeR = new Document("Authorization.Services.SUNATClearing.DocumentCode",documentTypeCode);
					Bson fIdR = new Document("ID",ids[0]+"-"+(Integer.parseInt(ids[1])));
					fRusR = Filters.and(fRusR, fTypeR);
					fRusR = Filters.and(fRusR, fIdR);
					
					MongoCursor<Document> TRANSACTION = dbO.getCollection("TRANSACTION").find(fRusR).iterator();
					int codigo=0;
					String description="";
					boolean exits=false;
					while (TRANSACTION.hasNext()) {
						Document doc=TRANSACTION.next();
						codigo=Integer.parseInt(((Document) doc.get("Validation")).getString("ValidationCode"));//Validation.ValidationCode
						description=((Document) doc.get("Validation")).getString("Description");//Validation.Description
						exits=true;
					}
					if(exits) {
						System.out.println("["+RUC+"]-["+documentTypeCode+"]-["+ID+"], Existe TRANSACTION, "+(codigo>0?"ERROR=> "+description.replace("\n", " "):"OK"));
//						result.add("["+RUC+"]-["+documentTypeCode+"]-["+ID+"], Existe, "+(codigo>0?"ERROR=> "+description.replace("\n", " "):"OK TR"));
						conteo++;
					}else {
						Bson fRusCpe = new Document("PartyIdentification",RUC);
						Bson fTypeCpe = new Document("DocumentType",documentTypeCode);
						Bson fSerieCpe = new Document("Series",ids[0]);
						Bson fNumberCpe = new Document("Number",""+(Integer.parseInt(ids[1])));
						fRusCpe = Filters.and(fRusCpe, fTypeCpe);
						fRusCpe = Filters.and(fRusCpe, fSerieCpe);
						fRusCpe = Filters.and(fRusCpe, fNumberCpe);
						MongoCursor<Document> CPE_SUMMARY = dbO.getCollection("CPE_SUMMARY").find(fRusCpe).iterator();
						boolean exitsCpe=false;
						
						while (CPE_SUMMARY.hasNext()) {
							Document doc=CPE_SUMMARY.next();
							//IDSummary.ID
							ArrayList IDResumen=(ArrayList)doc.get("IDSummary");
							String idResument="";
							for(int it=0;IDResumen.size()>it;it++) {
								Document doc2=(Document)IDResumen.get(it);
								idResument=doc2.getString("ID");
							}
							String estado=doc.getString("Status");
							String fecha=doc.getString("IssueDate");
							String monto=doc.getString("PayableAmount");
							System.out.println("["+RUC+"]-["+documentTypeCode+"]-["+ID+"], Existe en CPE_SUMMARY, estado=> "+estado.replace("1", "ok")+", fecha=> "
									+fecha+", monto=> "+monto +", resumen=> "+idResument);
//							codigo=Integer.parseInt(((Document) doc.get("Validation")).getString("ValidationCode"));//Validation.ValidationCode
//							description=((Document) doc.get("Validation")).getString("Description");//Validation.Description
							exitsCpe=true;
						}
						if(!exitsCpe) {
							System.out.println("["+RUC+"]-["+documentTypeCode+"]-["+ID+"], NO SE ENCONTRO");
						}
						
//						result.add("["+RUC+"]-["+documentTypeCode+"]-["+ID+"], NO SE ENCONTRO");
						conteo++;
					}
					
				}
				
			}catch (Exception e) {
				System.out.println("["+RUC+"]-["+ID+"] => |"+e.toString());
			}
		
			}
		}else if(TYPE.equals("16")) {
			Iterable<String> iterable=identifiers;
			Bson filter = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);
			Bson fType = new Document("Authorization.Services.SUNATClearing.DocumentCode",TYPE);
//			Bson fError = new Document("Status.Status","ERROR");
			Bson fID = Filters.and(Filters.in("ID", iterable));
			filter = Filters.and(filter, fType);
//			filter = Filters.and(filter, fError);
			filter = Filters.and(filter, fID);
			MongoCursor<Document> result = dbO.getCollection("TRANSACTION").find(filter).iterator();
			while (result.hasNext()) {
				Document document=result.next();
				try {
					String ID=document.getString("ID");
					String linkXMl=((Document) document.get("Storage")).getString("XML");//Storage.XML
					SummaryDocumentsType summaryDocumentsType=Util.getSummaryDocumentsType(linkXMl);
					System.out.println(ID);
					for(int it=0;it<summaryDocumentsType.getSummaryDocumentsLine().size();it++) {
//						if(perceptionType.getSUNATPerceptionDocumentReference().get(it).getInvoiceDocumentReference()!=null) {
							try {
							String documentReference=summaryDocumentsType.getSummaryDocumentsLine().get(it).getId().getValue()
									;
							String issueDate=summaryDocumentsType.getReferenceDate().getValue().toString();
							String documentTypeCode=summaryDocumentsType.getSummaryDocumentsLine().get(it).getDocumentTypeCode().getValue();
							String TotalAmount=summaryDocumentsType.getSummaryDocumentsLine().get(it).getTotalAmount().getValue().toString();
							System.out.println(documentTypeCode+"|"+documentReference+"|"+issueDate+"|"+TotalAmount);
							}catch (Exception e) {
								
							}
					}
					conteo++;
				}catch(Exception e) {
					
				}
				System.out.println("============================================================================");
			}
		}else{
			
		Iterable<String> iterable=identifiers;
		Bson filter = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);
		Bson fType = new Document("Authorization.CPE.DocumentType",TYPE);
//		Bson fError = new Document("Status.Status","ERROR");
		Bson fID = Filters.and(Filters.in("ID", iterable));
		filter = Filters.and(filter, fType);
//		filter = Filters.and(filter, fError);
		filter = Filters.and(filter, fID);
		MongoCursor<Document> result = dbW.getCollection("TRANSACTION").find(filter).iterator();
		List<Document> documents = new ArrayList<Document>();
		while (result.hasNext()) {
			Document datos=result.next();
			String id=datos.getString("ID");
			if(id.startsWith("B")&&(TYPE.equals("07")||TYPE.equals("08")))
				valCpe=false;
			documents.add(datos);
		}
		System.out.println("");
		System.out.println("============================================================================");
		System.out.println("");
		if(!documents.isEmpty()) {
			for(int i=0;i<documents.size();i++) {
				Document document=documents.get(i);
						String UUID=((Document) document.get("Status")).getString("Ticket");//Status.Ticket
						String ID=document.getString("ID");
						Bson filter2 = new Document("UUID",UUID);
						String response=((Document) ((Document) document.get("Status")).get("Response")).getString("Description"); //Status.Response.Description
						MongoCursor<Document> result2 = dbO.getCollection("TRANSACTION").find(filter2).iterator();
						String linkXMl="";
						while (result2.hasNext()) {
							Document document2=result2.next();
							linkXMl=((Document) document2.get("Storage")).getString("XML");//Storage.XML
						}
						if(TYPE.equals("07")) {
							CreditNoteType creditNoteType=Util.getCreditNoteFromStorage(linkXMl);
							for(int it=0;it<creditNoteType.getBillingReference().size();it++) {
								if(creditNoteType.getBillingReference().get(it).getInvoiceDocumentReference()!=null) {
									try {
									String documentReference=creditNoteType.getBillingReference().get(it).getInvoiceDocumentReference().getID().getValue();
									String issueDate="";
									if(creditNoteType.getBillingReference().get(it).getInvoiceDocumentReference().getIssueDate()!=null) {
										issueDate=creditNoteType.getBillingReference().get(it).getInvoiceDocumentReference().getIssueDate().getValue().toString();
									}
									
									String documentTypeCode=creditNoteType.getBillingReference().get(it).getInvoiceDocumentReference().getDocumentTypeCode().getValue();
									String[] ids=documentReference.split("-");
									Bson PartyIdentification = new Document("PartyIdentification",RUC);
									Bson DocumentType = new Document("DocumentType",documentTypeCode);
//									System.out.println(documentReference);
									Bson Series = new Document("Series",ids[0]);
									Bson Number = new Document("Number",(Integer.parseInt(ids[1]))+"");
									PartyIdentification = Filters.and(PartyIdentification, DocumentType);
									PartyIdentification = Filters.and(PartyIdentification, Series);
									PartyIdentification = Filters.and(PartyIdentification, Number);
									MongoCursor<Document> CPE = dbO.getCollection("CPE").find(PartyIdentification).iterator();
									int count=0;
									while (CPE.hasNext()) {
										CPE.next();
										count++;
									}
									if(count>0) {
										System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", Existe PADRONES, "+(count>1?"DUPLICADO":"OK"));
										conteo++;
									}else {
										Bson fRusR = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);
										Bson fTypeR = new Document("Authorization.Services.SUNATClearing.DocumentCode",documentTypeCode);
										Bson fIdR = new Document("ID",ids[0]+"-"+(Integer.parseInt(ids[1])));
										fRusR = Filters.and(fRusR, fTypeR);
										fRusR = Filters.and(fRusR, fIdR);
										MongoCursor<Document> TRANSACTION = dbO.getCollection("TRANSACTION").find(fRusR).iterator();
										int codigo=0;
										String description="";
										boolean exits=false;
										while (TRANSACTION.hasNext()) {
											Document doc=TRANSACTION.next();
											codigo=Integer.parseInt(((Document) doc.get("Validation")).getString("ValidationCode"));//Validation.ValidationCode
											description=((Document) doc.get("Validation")).getString("Description");//Validation.Description
											exits=true;
										}
										if(exits) {
											System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", Existe TRANSACTION, "+(codigo>0?"ERROR=> "+description.replace("\n", " "):"OK"));
											conteo++;
										}else {
											System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", NO SE ENCONTRO");
											conteo++;
										}
										
									}
									
								}catch (Exception e) {
									System.out.println("["+RUC+"]-["+ID+"] => "+response.replace("\n", " ")+" |"+e.toString());
								}
								
							}
							}
						}else if(TYPE.equals("08")) {

							DebitNoteType debitNoteType=Util.getDebitNoteTypeFromStorage(linkXMl);
							for(int it=0;it<debitNoteType.getBillingReference().size();it++) {
								if(debitNoteType.getBillingReference().get(it).getInvoiceDocumentReference()!=null) {
									try {
									String documentReference=debitNoteType.getBillingReference().get(it).getInvoiceDocumentReference().getID().getValue();
									String issueDate="";
									if(debitNoteType.getBillingReference().get(it).getInvoiceDocumentReference().getIssueDate()!=null) {
										issueDate=debitNoteType.getBillingReference().get(it).getInvoiceDocumentReference().getIssueDate().getValue().toString();
									}
									String documentTypeCode=debitNoteType.getBillingReference().get(it).getInvoiceDocumentReference().getDocumentTypeCode().getValue();
									String[] ids=documentReference.split("-");
									Bson PartyIdentification = new Document("PartyIdentification",RUC);
									Bson DocumentType = new Document("DocumentType",documentTypeCode);
//									System.out.println(documentReference);
									Bson Series = new Document("Series",ids[0]);
									Bson Number = new Document("Number",(Integer.parseInt(ids[1]))+"");
									PartyIdentification = Filters.and(PartyIdentification, DocumentType);
									PartyIdentification = Filters.and(PartyIdentification, Series);
									PartyIdentification = Filters.and(PartyIdentification, Number);
									MongoCursor<Document> CPE = dbO.getCollection("CPE").find(PartyIdentification).iterator();
									int count=0;
									while (CPE.hasNext()) {
										CPE.next();
										count++;
									}
									if(count>0) {
										System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", Existe PADRONES, "+(count>1?"DUPLICADO":"OK"));
										conteo++;
									}else {
										Bson fRusR = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);
										Bson fTypeR = new Document("Authorization.Services.SUNATClearing.DocumentCode",documentTypeCode);
										Bson fIdR = new Document("ID",ids[0]+"-"+(Integer.parseInt(ids[1])));
										fRusR = Filters.and(fRusR, fTypeR);
										fRusR = Filters.and(fRusR, fIdR);
										MongoCursor<Document> TRANSACTION = dbO.getCollection("TRANSACTION").find(fRusR).iterator();
										int codigo=0;
										String description="";
										boolean exits=false;
										while (TRANSACTION.hasNext()) {
											Document doc=TRANSACTION.next();
											codigo=Integer.parseInt(((Document) doc.get("Validation")).getString("ValidationCode"));//Validation.ValidationCode
											description=((Document) doc.get("Validation")).getString("Description");//Validation.Description
											exits=true;
										}
										if(exits) {
											System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", Existe TRANSACTION, "+(codigo>0?"ERROR=> "+description.replace("\n", " "):"OK"));
											conteo++;
										}else {
											System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", NO SE ENCONTRO");
											conteo++;
										}
										
									}
									
								}catch (Exception e) {
									System.out.println("["+RUC+"]-["+ID+"] => "+response.replace("\n", " ")+" |"+e.toString());
								}
								
							}
							}
						
						}  if(TYPE.equals("20")) {
							RetentionType retentionType=Util.getRetentionTypeFromStorage(linkXMl);
							for(int it=0;it<retentionType.getSUNATRetentionDocumentReference().size();it++) {
//								if(perceptionType.getSUNATPerceptionDocumentReference().get(it).getInvoiceDocumentReference()!=null) {
									try {
									String documentReference=retentionType.getSUNATRetentionDocumentReference().get(it).getID().getValue();
									String issueDate="";
									if(retentionType.getSUNATRetentionDocumentReference().get(it).getIssueDate()!=null) {
										issueDate=retentionType.getSUNATRetentionDocumentReference().get(it).getIssueDate().getValue().toString();
									}
									
									String documentTypeCode=retentionType.getSUNATRetentionDocumentReference().get(it).getID().getSchemeID();
									String[] ids=documentReference.split("-");
									Bson PartyIdentification = new Document("PartyIdentification",RUC);
									Bson DocumentType = new Document("DocumentType",documentTypeCode);
//									System.out.println(documentReference);
									Bson Series = new Document("Series",ids[0]);
									Bson Number = new Document("Number",(Integer.parseInt(ids[1]))+"");
									PartyIdentification = Filters.and(PartyIdentification, DocumentType);
									PartyIdentification = Filters.and(PartyIdentification, Series);
									PartyIdentification = Filters.and(PartyIdentification, Number);
									MongoCursor<Document> CPE = dbO.getCollection("CPE").find(PartyIdentification).iterator();
									int count=0;
//									int status=
									ObjectId objectId=null;
									while (CPE.hasNext()) {
										Document doc=CPE.next();
										if(doc.getString("Status").equals("0"))
										objectId=doc.get("_id", ObjectId.class);
//										System.out.println(doc.toJson().toString());
										count++;
										
									}
									if(count>0) {
										System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", Existe PADRONES, "+(count>1?"DUPLICADO =>"+objectId:"OK"));
										conteo++;
									}else {
										Bson fRusR = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);
										Bson fTypeR = new Document("Authorization.Services.SUNATClearing.DocumentCode",documentTypeCode);
										Bson fIdR = new Document("ID",ids[0]+"-"+(Integer.parseInt(ids[1])));
										fRusR = Filters.and(fRusR, fTypeR);
										fRusR = Filters.and(fRusR, fIdR);
										MongoCursor<Document> TRANSACTION = dbO.getCollection("TRANSACTION").find(fRusR).iterator();
										int codigo=0;
										String description="";
										boolean exits=false;
										while (TRANSACTION.hasNext()) {
											Document doc=TRANSACTION.next();
											codigo=Integer.parseInt(((Document) doc.get("Validation")).getString("ValidationCode"));//Validation.ValidationCode
											description=((Document) doc.get("Validation")).getString("Description");//Validation.Description
											exits=true;
										}
										if(exits) {
											System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", Existe TRANSACTION ,"+(codigo>0?"ERROR=> "+description.replace("\n", " "):"OK"));
											conteo++;
										}else {
											System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", NO SE ENCONTRO");
											conteo++;
										}
										
									}
									
								}catch (Exception e) {
									System.out.println("["+RUC+"]-["+ID+"] => "+response.replace("\n", " ")+" |"+e.toString());
								}
								
//							}
							}
							
						}if(TYPE.equals("40")) {
							PerceptionType perceptionType=Util.getPerceptionTypeFromStorage(linkXMl);
							for(int it=0;it<perceptionType.getSUNATPerceptionDocumentReference().size();it++) {
//								if(perceptionType.getSUNATPerceptionDocumentReference().get(it).getInvoiceDocumentReference()!=null) {
									try {
									String documentReference=perceptionType.getSUNATPerceptionDocumentReference().get(it).getID().getValue();
									String issueDate="";
									if(perceptionType.getSUNATPerceptionDocumentReference().get(it).getIssueDate()!=null) {
										issueDate=perceptionType.getSUNATPerceptionDocumentReference().get(it).getIssueDate().getValue().toString();
									}
									String documentTypeCode=perceptionType.getSUNATPerceptionDocumentReference().get(it).getID().getSchemeID();
									String[] ids=documentReference.split("-");
									Bson PartyIdentification = new Document("PartyIdentification",RUC);
									Bson DocumentType = new Document("DocumentType",documentTypeCode);
//									System.out.println(documentReference);
									Bson Series = new Document("Series",ids[0]);
									Bson Number = new Document("Number",(Integer.parseInt(ids[1]))+"");
									PartyIdentification = Filters.and(PartyIdentification, DocumentType);
									PartyIdentification = Filters.and(PartyIdentification, Series);
									PartyIdentification = Filters.and(PartyIdentification, Number);
									MongoCursor<Document> CPE = dbO.getCollection("CPE").find(PartyIdentification).iterator();
									int count=0;
//									int status=
									ObjectId objectId=null;
									String dato="";
									while (CPE.hasNext()) {
										Document doc=CPE.next();
//										if(doc.getString("Status").equals("0"))
//										if(!doc.getString("IssueDate").contains(" "))
											try {
												objectId=doc.get("_id", ObjectId.class);
												String monto=doc.getString("PayableAmount");
												dato+=objectId.toString()+"|";
											}catch (Exception ex) {
												// TODO: handle exception
											}
//										if(count>0) {
//											objectId=doc.get("_id", ObjectId.class);
//											dato+=objectId.toString()+"|";
//										}
										
										
//										System.out.println(doc.toJson().toString());
										count++;
										
									}
									if(count>0) {
										System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", Existe PADRONES"+(count>1?"DUPLICADO =>":"OK" )+",ID="+objectId+",dato="+dato);
										conteo++;
									}else {
										Bson fRusR = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);
										Bson fTypeR = new Document("Authorization.Services.SUNATClearing.DocumentCode",documentTypeCode);
										Bson fIdR = new Document("ID",ids[0]+"-"+(Integer.parseInt(ids[1])));
										fRusR = Filters.and(fRusR, fTypeR);
										fRusR = Filters.and(fRusR, fIdR);
										MongoCursor<Document> TRANSACTION = dbO.getCollection("TRANSACTION").find(fRusR).iterator();
										int codigo=0;
										String description="";
										boolean exits=false;
										while (TRANSACTION.hasNext()) {
											Document doc=TRANSACTION.next();
											try {
												codigo=Integer.parseInt(((Document) doc.get("Validation")).getString("ValidationCode"));//Validation.ValidationCode
											}catch(Exception e) {
												codigo=((Document) doc.get("Validation")).getInteger("ValidationCode");//Validation.ValidationCode
											}
											description=((Document) doc.get("Validation")).getString("Description");//Validation.Description
											exits=true;
										}
										if(exits) {
											System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", Existe TRANSACTION"+(codigo>0?"ERROR=> "+description.replace("\n", " "):"OK"));
											conteo++;
										}else {
											System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", NO SE ENCONTRO");
											conteo++;
										}
										
									}
									
								}catch (Exception e) {
									System.out.println("["+RUC+"]-["+ID+"] => "+response.replace("\n", " ")+" |"+e.toString());
								}
								
//							}
							}
						}if(TYPE.equals("17")) {
							VoidedDocumentsType voidedDocumentsType=Util.getVoidedFromStorage(linkXMl);
							for(int it=0;it<voidedDocumentsType.getVoidedDocumentsLine().size();it++) {
//								if(perceptionType.getSUNATPerceptionDocumentReference().get(it).getInvoiceDocumentReference()!=null) {
									try {
									String documentReference=voidedDocumentsType.getVoidedDocumentsLine().get(it).getDocumentSerialID().getValue()+
											"-"+voidedDocumentsType.getVoidedDocumentsLine().get(it).getDocumentNumberID().getValue()
											;
									String issueDate=voidedDocumentsType.getReferenceDate().getValue().toString();
									String documentTypeCode=voidedDocumentsType.getVoidedDocumentsLine().get(it).getDocumentTypeCode().getValue();
									String[] ids=documentReference.split("-");
									Bson PartyIdentification = new Document("PartyIdentification",RUC);
									Bson DocumentType = new Document("DocumentType",documentTypeCode);
//									System.out.println(documentReference);
									Bson Series = new Document("Series",ids[0]);
									Bson Number = new Document("Number",(Integer.parseInt(ids[1]))+"");
									PartyIdentification = Filters.and(PartyIdentification, DocumentType);
									PartyIdentification = Filters.and(PartyIdentification, Series);
									PartyIdentification = Filters.and(PartyIdentification, Number);
									MongoCursor<Document> CPE = dbO.getCollection("CPE").find(PartyIdentification).iterator();
									int count=0;
//									int status=
									ObjectId objectId=null;
									String dato="";
									while (CPE.hasNext()) {
										Document doc=CPE.next();
//										if(doc.getString("Status").equals("0"))
//										if(count>1) {
											objectId=doc.get("_id", ObjectId.class);
//											dato+=objectId.toString()+"|";
//										}
										
//										System.out.println(doc.toJson().toString());
										count++;
										
									}
									if(count>0) {
										System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", Existe PADRONES"+(count>1?"DUPLICADO =>"+objectId:"OK")+",dato="+dato);
										conteo++;
									}else {
										Bson fRusR = new Document("Authorization.AccountingSupplierParty.PartyIdentification.ID",RUC);
										Bson fTypeR = new Document("Authorization.Services.SUNATClearing.DocumentCode",documentTypeCode);
										Bson fIdR = new Document("ID",ids[0]+"-"+(Integer.parseInt(ids[1])));
										fRusR = Filters.and(fRusR, fTypeR);
										fRusR = Filters.and(fRusR, fIdR);
										MongoCursor<Document> TRANSACTION = dbO.getCollection("TRANSACTION").find(fRusR).iterator();
										int codigo=0;
										String description="";
										boolean exits=false;
										while (TRANSACTION.hasNext()) {
											Document doc=TRANSACTION.next();
											codigo=Integer.parseInt(((Document) doc.get("Validation")).getString("ValidationCode"));//Validation.ValidationCode
											description=((Document) doc.get("Validation")).getString("Description");//Validation.Description
											exits=true;
										}
										if(exits) {
											System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", Existe TRANSACTION"+(codigo>0?"ERROR=> "+description.replace("\n", " "):"OK TR"));
											conteo++;
										}else {
											System.out.println("["+RUC+"]-["+ID+"]-Reference=> "+documentTypeCode+","+documentReference+","+issueDate+", NO SE ENCONTRO");
											conteo++;
										}
										
									}
									
								}catch (Exception e) {
									System.out.println("["+RUC+"]-["+ID+"] => "+response.replace("\n", " ")+" |"+e.toString());
								}
								
//							}
							}
						}
						
			
			}
		}
		
		if(valCpe) {
			for(int i=0;i<identifiers.size();i++) {
				String ID=identifiers.get(i);
				String documentTypeCode=TYPE.toString();
				try {
					
					boolean exitsCpe=false;
					if(ID.startsWith("B")) {
					String[] ids=identifiers.get(i).split("-");
					
					Bson fRusCpe = new Document("PartyIdentification",RUC);
					Bson fTypeCpe = new Document("DocumentType",documentTypeCode);
					Bson fSerieCpe = new Document("Series",ids[0]);
					Bson fNumberCpe = new Document("Number",""+(Integer.parseInt(ids[1])));
					fRusCpe = Filters.and(fRusCpe, fTypeCpe);
					fRusCpe = Filters.and(fRusCpe, fSerieCpe);
					fRusCpe = Filters.and(fRusCpe, fNumberCpe);
					MongoCursor<Document> CPE_SUMMARY = dbO.getCollection("CPE_SUMMARY").find(fRusCpe).iterator();
					
					
					while (CPE_SUMMARY.hasNext()) {
						Document doc=CPE_SUMMARY.next();
						//IDSummary.ID
						ArrayList IDResumen=(ArrayList)doc.get("IDSummary");
						String idResument="";
						for(int it=0;IDResumen.size()>it;it++) {
							Document doc2=(Document)IDResumen.get(it);
							idResument=doc2.getString("ID");
						}
						String estado=doc.getString("Status");
						String fecha=doc.getString("IssueDate");
						String monto=doc.getString("PayableAmount");
						System.out.println("["+RUC+"]-["+documentTypeCode+"]-["+ID+"], Existe en CPE_SUMMARY, estado=> "+estado.replace("1", "ok")+", fecha=> "
								+fecha+", monto=> "+monto +", resumen=> "+idResument);
//						codigo=Integer.parseInt(((Document) doc.get("Validation")).getString("ValidationCode"));//Validation.ValidationCode
//						description=((Document) doc.get("Validation")).getString("Description");//Validation.Description
						exitsCpe=true;
					}
				}
				
				if(!exitsCpe) {
					System.out.println("["+RUC+"]-["+documentTypeCode+"]-["+ID+"], NO SE ENCONTRO");
				}
				}catch(Exception e) {
					System.out.println("Error: "+"["+RUC+"]-["+documentTypeCode+"]-["+ID+"]"+" => " +e.toString());
				}
			}
		}
			
			
		}
		System.out.println("Cantidad: "+conteo);
		System.exit(0);
		}catch(Exception e) {
			System.err.println(e.toString());
			System.exit(0);
		}
	}
	
}
